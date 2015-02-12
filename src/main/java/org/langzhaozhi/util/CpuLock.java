package org.langzhaozhi.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个CPU级别的控制锁，用于不同线程之间进行共享对象的volatile状态获取和修改，只能用于非常小的volatile状态原子控制情形。注意：
 *
 * 1、必须保证 beginLock() 和 endLock() 在任何情况下都要配对使用。
 *
 * 2、这个锁仅仅用于CPU级别的锁争抢控制,一般用于两个或两个以上的volatile变量状态的的同时检测、
 *      修改,如果若干    beginLock()   和   endLock()      的逻辑段之间仅仅对一个变量进行检测、
 *      修改,那么可能仅仅使用一个 AtomicInteger等类就足以解决你的问题,而不应该用到本类
 *
 * 3、必须保证每一个 beginLock() 和 endLock() 之间的代码逻辑执行尽可能地短，而且限定为简单的几个volatile状态的检查和赋值语句，
 *      绝对不能用于长时间的诸如文件读取之类，否则就说明你的程序设计结构存在严重缺陷
 *
 * 4、必须保证若干 beginLock() 和 endLock() 的逻辑段之间发生撞车的可能性非常小，
 *      否则，如果撞车太频繁的话, 大量的CPU时间将耗费于beginLock()时的空转,引起系统性能急剧下降,
 *      虽然你的程序运行逻辑是正确的,也同样说明你的程序设计结构存在严重缺陷
 *
 * 5、必须保证 beginLock() 和 endLock() 之间的对象变量的修改是 volatile 的,否则的话,变量的修改对
 *       CPU之间或线程之间来说可能是不可见的, 例如下面的错误用法:
 *       <pre>
 *          class SomeClass{
 *              int mInt;
 *              boolean mBoolean;
 *
 *              CpuLock mLock = new CpuLock();
 *
 *              void f1(int aInt, boolean aBoolean){
 *                  mLock.beginLock();
 *                  this.mInt = aInt;
 *                  this.mBoolean = aBoolean;
 *                  mLock.endLock();
 *              }
 *
 *              void f2(){
 *                  mLock.beginLock();
 *                  if(this.mBoolean){
 *                      this.mInt = 2;
 *                  }
 *                  else{
 *                      this.mInt = 3;
 *                  }
 *                  mLock.endLock();
 *              }
 *          }
 *       </pre>
 *       错误在于这里类成员 mInt, mBoolean 不是 volatile 的,这里f1(),f2()里面的修改结果对其他CPU或线程来说是随机的,至少不是
 *       立即可见的,这就是严重的程序逻辑问题了。
 *
 *       需要都定义成下面的 volatile, 或者使用 java.util.concurrent.atomic 下的原子类:

 *       <pre>
 *          class SomeClass{
 *              <b>volatile</b> int mInt;
 *              <b>volatile</b> boolean mBoolean;
 *              ......
 *          }
 *       </pre>
 */
public class CpuLock {
    //当 1 的时候表示获取到锁的状态, 0 表示锁空闲状态
    private AtomicInteger mLockState = new AtomicInteger( 0 );

    public void beginLock() {
        AtomicInteger lockState = this.mLockState;
        while (true) {//不能简单用错误的 while(lockState.compareAndSet( 0, 1 )){break;} 来表达
            if (lockState.compareAndSet( 0, 1 )) {
                break;
            }
        }
    }

    public void endLock() {
        if (!this.mLockState.compareAndSet( 1, 0 )) {
            throw new IllegalStateException( "Lock state error for 'endLock'" );
        }
    }
}

package org.langzhaozhi.util;

/**
 * 一个以int为Key的超快的hash表，非线程安全的。我们经常遇到要使用整数为Key的哈希表来存取对象
 *
 * 使用说明：
 * <ol>
 *  <li>整数key没有正负的限制</li>
 *  <li>非线程安全的，应该在单线程中使用</li>
 * </ol>
 *
 * 如果要使用以字符串为Key的hash表来进行随机存取，请使用Trie树
 *
 * @param <T>
 */
public final class IntHash<T> {
    private int mCount;
    private int mMod;

    private HashEntry<T> [] mEntryArray;

    public IntHash() {
        this( 16 );
    }

    public IntHash(int aInitialCapacity) {
        if (aInitialCapacity <= 0) {
            throw new IllegalArgumentException( "aInitialCapacity(" + aInitialCapacity + ")not valid" );
        }
        int minimizedPowerOf2 = IntHash.calMinimizedPowerOf2( aInitialCapacity < 8 ? 8 : aInitialCapacity );
        this.mMod = minimizedPowerOf2 - 1;
        @SuppressWarnings("unchecked")
        HashEntry<T> [] entrys = new HashEntry [ minimizedPowerOf2 ];
        this.mEntryArray = entrys;
    }

    public int size() {
        return this.mCount;
    }

    public T get(int aKey) {
        for (HashEntry<T> e = this.mEntryArray[ aKey & this.mMod ]; e != null; e = e.mNext) {
            if (e.mKey == aKey) {
                return e.mValue;
            }
        }
        return null;
    }

    public T put(int aKey, T aValue) {
        HashEntry<T> entrys [] = this.mEntryArray;
        int index = aKey & this.mMod;
        for (HashEntry<T> e = entrys[ index ]; e != null; e = e.mNext) {
            if (e.mKey == aKey) {
                T old = e.mValue;
                e.mValue = aValue;
                return old;
            }
        }
        if (this.mCount == entrys.length) {
            this.rehash();
            return this.put( aKey, aValue );
        }
        else {
            HashEntry<T> e = new HashEntry<T>( aKey, aValue );
            e.mNext = entrys[ index ];
            entrys[ index ] = e;
            this.mCount++;
            return null;
        }
    }

    public T remove(int aKey) {
        HashEntry<T> entrys [] = this.mEntryArray;
        int index = aKey & this.mMod;
        HashEntry<T> prev = null;
        for (HashEntry<T> e = entrys[ index ]; e != null; e = e.mNext) {
            if (e.mKey == aKey) {
                if (prev != null) {
                    prev.mNext = e.mNext;
                }
                else {
                    entrys[ index ] = e.mNext;
                }
                this.mCount--;
                return e.mValue;
            }
            prev = e;
        }
        return null;
    }

    private void rehash() {
        HashEntry<T> oldEntryArray [] = this.mEntryArray;
        int oldCapacity = oldEntryArray.length;
        @SuppressWarnings("unchecked")
        HashEntry<T> newEntryArray [] = new HashEntry [ (oldCapacity << 1) ];
        int newMod = newEntryArray.length - 1;
        for (int i = oldCapacity - 1; i >= 0; --i) {
            HashEntry<T> old = oldEntryArray[ i ];
            while (old != null) {
                HashEntry<T> e = old;
                old = old.mNext;
                int index = e.mKey & newMod;
                e.mNext = newEntryArray[ index ];
                newEntryArray[ index ] = e;
            }
        }
        this.mMod = newMod;
        this.mEntryArray = newEntryArray;
    }

    /**
     * 大于等于正整数 aNumber 的最小的2的幂
     */
    private static int calMinimizedPowerOf2(int aNumber) {
        aNumber -= 1;
        aNumber |= aNumber >>> 16;
        aNumber |= aNumber >>> 8;
        aNumber |= aNumber >>> 4;
        aNumber |= aNumber >>> 2;
        aNumber |= aNumber >>> 1;
        return aNumber + 1;
    }

    private static final class HashEntry<T> {
        int mKey;
        T mValue;
        HashEntry<T> mNext;

        HashEntry(int aKey, T aValue) {
            this.mKey = aKey;
            this.mValue = aValue;
        }
    }
}

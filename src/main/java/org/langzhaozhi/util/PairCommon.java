package org.langzhaozhi.util;

/**
 * 提供一个一般用途的Key-Value的Pair对，对 int 和字符串提供了专门的 PairInt 或 PairString 类，
 * 因为这两类为Key实在太常用，这种情况理所应当直接用 PairInt 或 PairString。
 *
 * 对于其他一般化用途的Key-Value 对，才应该用这个 PairCommon，使用者尤其要注意如果要用于Comparable排序，
 * 一定要对<K>类型 Comparable<K> 实现排序定义
 * @param <K>
 * @param <V>
 *
 * @see PairInt
 * @see PairString
 */
public final class PairCommon<K, V> implements Comparable<PairCommon<K, V>> {
    public final K mKey;
    public final V mValue;

    public PairCommon(K aKey, V aValue) {
        this.mKey = aKey;
        this.mValue = aValue;
    }

    @Override
    public int compareTo(PairCommon<K, V> aOther) {
        if (this.mKey instanceof Comparable) {
            @SuppressWarnings("unchecked")
            Comparable<K> thisKey = ( Comparable<K> )this.mKey;
            return thisKey.compareTo( aOther.mKey );
        }
        else {
            throw new IllegalStateException( "为什么<K>类型不按要求实现Comparable<K>的排序定义>?你应该直接用Comparator<K>嘛!" );
        }
    }
}

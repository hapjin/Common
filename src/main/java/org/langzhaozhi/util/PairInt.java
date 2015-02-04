package org.langzhaozhi.util;

/**
 * 提供一个以int为key的Key-Value的Pair对，因为int为Key太常用了,而Integer对象太重,用起来不方便
 */
public final class PairInt<T> implements Comparable<PairInt<T>> {
    public final int mKey;
    public final T mValue;

    public PairInt(int aKey, T aValue) {
        this.mKey = aKey;
        this.mValue = aValue;
    }

    @Override
    public int compareTo(PairInt<T> aOther) {
        return this.mKey - aOther.mKey;
    }
}

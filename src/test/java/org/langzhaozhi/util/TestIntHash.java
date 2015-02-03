package org.langzhaozhi.util;

import java.util.HashMap;

public class TestIntHash {
    public static void main(String [] args) {
        int initialCapacity = 2000 * 10000;

        //对比IntHash与HashMap 的 put,get 速度
        TestIntHash.testIntHash( initialCapacity );
        System.gc();
        System.gc();
        TestIntHash.testHashMap( initialCapacity );
    }

    private static void testIntHash(int aInitialCapacity) {
        long t1 = System.currentTimeMillis();
        IntHash<Boolean> hash = new IntHash<Boolean>( aInitialCapacity );
        for (int i = 0; i < aInitialCapacity; ++i) {
            hash.put( i, Boolean.TRUE );
        }
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < aInitialCapacity; ++i) {
            if (hash.get( i ) != Boolean.TRUE) {
                throw new Error();
            }
        }
        for (int i = aInitialCapacity; i < 1_0000_0000; ++i) {
            if (hash.get( i ) != null) {
                throw new Error();
            }
        }
        long t3 = System.currentTimeMillis();
        System.err.println( "<IntMap>: put spend:[" + (t2 - t1) + "]ms, 平均[" + aInitialCapacity * 1000.0 / (t2 - t1) + "]put次/秒, get spend:[" + (t3 - t2) + "]ms, 平均[" + 1_0000_0000 * 1000.0 / (t3 - t2) + "]get次/秒" );
    }

    private static void testHashMap(int aInitialCapacity) {
        long t1 = System.currentTimeMillis();
        HashMap<Integer, Boolean> hash = new HashMap<Integer, Boolean>( aInitialCapacity );
        for (int i = 0; i < aInitialCapacity; ++i) {
            hash.put( i, Boolean.TRUE );
        }
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < aInitialCapacity; ++i) {
            if (hash.get( i ) != Boolean.TRUE) {
                throw new Error();
            }
        }
        for (int i = aInitialCapacity; i < 1_0000_0000; ++i) {
            if (hash.get( i ) != null) {
                throw new Error();
            }
        }
        long t3 = System.currentTimeMillis();
        System.err.println( "<HashMap>: put spend:[" + (t2 - t1) + "]ms, 平均[" + aInitialCapacity * 1000.0 / (t2 - t1) + "]put次/秒, get spend:[" + (t3 - t2) + "]ms, 平均[" + 1_0000_0000 * 1000.0 / (t3 - t2) + "]get次/秒" );
    }
}

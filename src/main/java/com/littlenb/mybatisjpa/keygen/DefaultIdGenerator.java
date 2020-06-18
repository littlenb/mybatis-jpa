package com.littlenb.mybatisjpa.keygen;


import com.littlenb.snowflake.support.SecondsIdGeneratorFactory;

/**
 * @author sway.li
 * @since 2.5.1
 */
public class DefaultIdGenerator implements IdGenerator {

    private com.littlenb.snowflake.sequence.IdGenerator idGenerator;

    private DefaultIdGenerator() {
    }

    public static DefaultIdGenerator newInstance() {
        // 2017-01-01 00:00:00
        return newInstance(1483200000L, 1L);
    }

    public static DefaultIdGenerator newInstance(long seconds, long workerId) {
        DefaultIdGenerator instance = new DefaultIdGenerator();
        SecondsIdGeneratorFactory idGeneratorFactory = new SecondsIdGeneratorFactory(seconds);
        instance.idGenerator = idGeneratorFactory.create(workerId);
        return instance;
    }

    @Override
    public synchronized Object nextId() {
        return idGenerator.nextId();
    }

    @Override
    public synchronized Object[] nextSegment(int size) {
        long[] segment = idGenerator.nextSegment(size);
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = segment[i];
        }
        return array;
    }

}

package xyz.flysium.photon.c050_gc;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * <pre>
 *     -Xmn10M -XX:SurvivorRatio=8 -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:+UseParallelGC -XX:+UseParallelOldGC
 * </pre>
 *
 * <pre>
 *      -Xmn10M -XX:SurvivorRatio=8 -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC
 * </pre>
 *
 * <pre>
 *      -Xmn10M -XX:SurvivorRatio=8 -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:+UseG1GC
 * </pre>
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T10_StringGC {

    public static void main(String[] args) throws IOException {
        //        stats();
        //        System.out.println("start -> ");

        for (int i = 0; i < 3 * 8; i++) {
            generateString(1024 * 1024);
            String s = STRING_BUILDER.toString();
            //            System.out.println(RamUsageEstimator.humanSizeOf(s));
            //            System.out.println(RamUsageEstimator.humanSizeOf(STRING_BUILDER));
            stats(i);
        }

        //        System.in.read();
    }

    private static void stats(int i) {
        MemoryUsage usage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        System.out.println(String.format("%d, USE HEAP:  %.2f MB", i, (usage.getUsed() / 1024.0 / 1024.0)));
    }

    private static final char[] RANDOM_STRING_ARRAY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        .toCharArray();

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final StringBuilder STRING_BUILDER = new StringBuilder(1024 * 1024);

    public static synchronized void generateString(int length) {
        STRING_BUILDER.delete(0, STRING_BUILDER.length());
        for (int i = 0; i < length; i++) {
            STRING_BUILDER.append(RANDOM_STRING_ARRAY[RANDOM.nextInt(RANDOM_STRING_ARRAY.length)]);
        }
    }
}

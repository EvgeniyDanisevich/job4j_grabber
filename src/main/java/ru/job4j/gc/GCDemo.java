package ru.job4j.gc;

//import static com.carrotsearch.sizeof.RamUsageEstimator.sizeOf;

public class GCDemo {

    private static final long KB = 1000;
    private static final long MB = KB * KB;
    private static final Runtime ENVIRONMENT = Runtime.getRuntime();

    public static void info() {
        final long freeMemory = ENVIRONMENT.freeMemory();
        final long totalMemory = ENVIRONMENT.totalMemory();
        final long maxMemory = ENVIRONMENT.maxMemory();
        System.out.println("=== Environment state ===");
        System.out.printf("Free: %d%n", freeMemory / KB);
        System.out.printf("Total: %d%n", totalMemory / KB);
        System.out.printf("Max: %d%n", maxMemory / KB);
    }

    public static void main(String[] args) {
        info();
        for (int i = 0; i < 10; i++) {
            new Person(i, "N" + i);
        }
        System.gc();
        info();
    }

//        info();
//        for (int i = 0; i < 58800; i++) {
//            System.out.println(sizeOf(new User()));
//        }
//        info();
//    }
}
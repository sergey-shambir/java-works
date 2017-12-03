package name.sergey.shambir;

public class Main {
    private static void printMetrics(String name, String min, String max, String size) {
        System.out.printf("%-10s %-22s %-22s %-5s\n", name, min, max, size);
    }

    private static void printIntegerMetrics(String name, long byteCount, long minValue, long maxValue) {
        printMetrics(name, Long.toString(minValue), Long.toString(maxValue), Long.toString(byteCount));
    }

    private static void printFloatingPointMetrics(String name, long byteCount, double minValue, double maxValue) {
        printMetrics(name, Double.toString(minValue), Double.toHexString(maxValue), Long.toString(byteCount));
    }

    public static void main(String[] args) {
        try {
            printMetrics("Type", "Min", "Max", "Size");
            printIntegerMetrics("Character", Character.SIZE, Character.MIN_VALUE, Character.MAX_VALUE);
            printIntegerMetrics("Byte", Byte.SIZE, Byte.MIN_VALUE, Byte.MAX_VALUE);
            printIntegerMetrics("Short", Short.SIZE, Short.MIN_VALUE, Short.MAX_VALUE);
            printIntegerMetrics("Integer", Integer.SIZE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            printIntegerMetrics("Long", Long.SIZE, Long.MIN_VALUE, Long.MAX_VALUE);
            printFloatingPointMetrics("Float", Float.SIZE, Float.MIN_VALUE, Float.MAX_VALUE);
            printFloatingPointMetrics("Double", Double.SIZE, Double.MIN_VALUE, Double.MAX_VALUE);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}

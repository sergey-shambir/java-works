package name.sergey.shambir;

import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.printf("Usage: RecursiveWalk <input file> <output file>\n");
            System.exit(1);
        } else {
            try {
                TreeSet<String> set = new TreeSet<>();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }
}

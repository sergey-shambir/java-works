package name.sergey.shambir;

public class Main {
    public static void printUsage() {
        System.err.print("Usage: CaesarCipher (-e | -d) <key> <text>\n");
        System.err.print(" where key - integer cipher offset in alphabet\n");
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            printUsage();
            System.exit(1);
        } else {
            try {
                final String command = args[0];
                final int key = Integer.parseInt(args[1]);
                final String text = args[2];
                if (command.equals("-e")) {
                    final CeasarConverter converter = new CeasarConverter(key);
                    final String encrypted = converter.encrypt(text);
                    System.out.print(encrypted);
                } else if (command.equals("-d")) {
                    final CeasarConverter converter = new CeasarConverter(-key);
                    final String encrypted = converter.encrypt(text);
                    System.out.print(encrypted);
                } else {
                    System.err.printf("unknown command: %s\n", command);
                    printUsage();
                    System.exit(1);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }
}

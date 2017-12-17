package name.sergey.shambir;

public class Main {
    private static int MAX_PASSWORD_LENGTH = 20;

    private static void printUsage() {
        System.err.printf("Usage: PasswordGenerator <length> <alphabet>\n");
        System.err.printf(
            " where length - positive password length, alphabet - string with characters allowed in password\n");
    }

    private static int parseIntInRange(String argument, int min, int max) {
        final int value = Integer.parseUnsignedInt(argument);
        if (value < min || value > max) {
            throw new RuntimeException("value is out of range [" + Integer.toString(min) + "; " +
                                       Integer.toString(max) + "]");
        }
        return value;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            System.exit(1);
        } else {
            int length = 0;
            String alphabet = "";
            try {
                length = parseIntInRange(args[0], 1, MAX_PASSWORD_LENGTH);
                alphabet = args[1];
            } catch (Exception ex) {
                printUsage();
                System.exit(1);
            }
            try {
                final PasswordGenerator generator = new PasswordGenerator(alphabet);
                final String password = generator.generate(length);
                System.out.print(password);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }
}

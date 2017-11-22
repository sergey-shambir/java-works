package name.sergey.shambir;

public class Main {
    private static void printUsage()
    {
        System.err.printf("Usage: PasswordGenerator <length> <alphabet>\n");
        System.err.printf(" where length - password length, alphabet - string with characters allowed in password\n");
    }

    public static void main(String[] args) {
        if (args.length != 2)
        {
            System.err.printf("Usage: PasswordGenerator <length> <alphabet>\n");
            System.exit(1);
        }
        else
        {
            try
            {
                final int length = Integer.parseUnsignedInt(args[0]);
                final String alphabet = args[1];

                final PasswordGenerator generator = new PasswordGenerator(alphabet);
                final String password = generator.generate(length);
                System.out.print(password);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }
}

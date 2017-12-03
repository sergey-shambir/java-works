package name.sergey.shambir;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.printf("Usage: GetNetAddress <IP> <mask>\n");
            System.exit(1);
        } else {
            try {
                final IPConverter converter = new IPConverter();
                final String address = converter.getNetAddress(args[0], args[1]);
                System.out.print(address);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }
}

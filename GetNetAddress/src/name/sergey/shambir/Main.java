package name.sergey.shambir;

import java.util.regex.PatternSyntaxException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2)
        {
            System.err.printf("Usage: GetNetAddress <IP> <mask>\n");
        }
        else
        {
            try
            {
                final IPConverter converter = new IPConverter();
                final String address = converter.getNetAddress(args[0], args[1]);
                System.out.print(address);
            }
            catch (RuntimeException re)
            {
                re.printStackTrace();
            }
        }
    }
}

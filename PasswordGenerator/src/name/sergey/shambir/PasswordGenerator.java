package name.sergey.shambir;

import java.util.Random;

public class PasswordGenerator {
    private Random random;
    private String alphabet;
    private int alphabetLength;

    public PasswordGenerator(String alphabet)
    {
        this.random = new Random();
        this.alphabet = alphabet;
        this.alphabetLength = alphabet.codePointCount(0, alphabet.length());
    }

    public String generate(int length)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i)
        {
            final int index = random.nextInt(this.alphabetLength);
            builder.appendCodePoint(alphabet.codePointAt(index));
        }
        return builder.toString();
    }
}

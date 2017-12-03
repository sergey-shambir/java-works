package name.sergey.shambir;

public class CeasarConverter {
    private static final String ENGLISH_ALPHABET_LOWERCASE =
        "abcdefghijklmnopqrstuvwxyz";

    private final int key;

    public CeasarConverter(int key) {
        if (key >= ENGLISH_ALPHABET_LOWERCASE.length() ||
            key <= -ENGLISH_ALPHABET_LOWERCASE.length()) {
            throw new RuntimeException("key should be less than alphabet size");
        }
        this.key = key;
    }

    /// @param text - text to be encrypted
    /// @param key - offset in alphabet that will be applied for each letter
    public final String encrypt(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); ++i) {
            char unit = text.charAt(i);
            if (!encryptLowercaseChar(unit, builder) &&
                !encryptUppercaseChar(unit, builder)) {
                // Characters that are not alphabet will be ignored.
                builder.append(unit);
            }
        }
        return builder.toString();
    }

    private final boolean encryptLowercaseChar(char unit,
                                               StringBuilder builder) {
        final int pos = ENGLISH_ALPHABET_LOWERCASE.indexOf(unit);
        if (pos != -1) {
            builder.append(
                ENGLISH_ALPHABET_LOWERCASE.charAt(getOffset(pos, key)));
            return true;
        }
        return false;
    }

    private final boolean encryptUppercaseChar(char unit,
                                               StringBuilder builder) {
        final int pos =
            ENGLISH_ALPHABET_LOWERCASE.indexOf(Character.toLowerCase(unit));
        if (pos != -1) {
            builder.append(Character.toUpperCase(
                ENGLISH_ALPHABET_LOWERCASE.charAt(getOffset(pos, key))));
            return true;
        }
        return false;
    }

    private final int getOffset(int pos, int key) {
        final int length = ENGLISH_ALPHABET_LOWERCASE.length();

        // Add length to avoid negative indexes.
        return (pos + key + length) % length;
    }
}

package name.sergey.shambir;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPConverter {
    // Groups count in address pattern.
    static final int ADDRESS_BYTE_COUNT = 4;
    static final int MAX_UBYTE_VALUE = 255;
    // Address regex pattern.
    private Pattern addressPattern;

    public IPConverter() {
        String[] parts = new String[ADDRESS_BYTE_COUNT];
        Arrays.fill(parts, "(\\d{1,3})");
        addressPattern = Pattern.compile("^" + String.join("\\.", parts) + "$");
    }

    /// @param ip - IPv4 address
    /// @param mask - IPv4 subnet mask
    public String getNetAddress(String ip, String mask) {
        int[] ipBytes = parseAddress(ip);
        int[] maskBytes = parseAddress(mask);

        // Apply mask and convert each part to string.
        String[] resultParts = new String[ADDRESS_BYTE_COUNT];
        for (int bi = 0; bi < resultParts.length; ++bi) {
            resultParts[bi] = Integer.toString(ipBytes[bi] & maskBytes[bi]);
        }

        return String.join(".", resultParts);
    }

    private int[] parseAddress(String address) {
        Matcher matcher = addressPattern.matcher(address);
        if (!matcher.matches()) {
            throw new RuntimeException("address doesn't match pattern: " + address);
        }

        // ignore group #0 (whole matched string)
        int[] bytes = new int[ADDRESS_BYTE_COUNT];
        for (int bi = 0; bi < bytes.length; ++bi) {
            bytes[bi] = Integer.parseUnsignedInt(matcher.group(bi + 1));
            if (bytes[bi] >= MAX_UBYTE_VALUE) {
                throw new RuntimeException("address part value is out of range [0..255]");
            }
        }
        return bytes;
    }
}

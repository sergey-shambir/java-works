# java-works

## Finished works

### 1.1 GetNetAddress

Usage: `GetNetAddress <IP> <mask>`

### 1.2 CaesarCipher

Usage: `CaesarCipher (-d | -e) <key> <mask>`

* Works only with Engish alphabet
* Encrypting shifts characters to the right side of alphabet (and wraps around), e.g. `"abcd" >> 1 -> "bcde"`

### 1.3 PasswordGenerator

Usage: `PasswordGenerator <length> <alphabet>`

* Password generator is Unicode-aware

### 1.4 JavaTypeMetrics

Prints metrics for Java builtin types:

```
Type       Min                    Max                    Size 
Character  0                      65535                  16   
Byte       -128                   127                    8    
Short      -32768                 32767                  16   
Integer    -2147483648            2147483647             32   
Long       -9223372036854775808   9223372036854775807    64   
Float      1.401298464324817E-45  0x1.fffffep127         32   
Double     4.9E-324               0x1.fffffffffffffp1023 64 
```

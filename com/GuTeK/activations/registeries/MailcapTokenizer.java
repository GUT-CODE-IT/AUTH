/*
    Author: .GuTeK <dev@gutcode.pl>
    Project: AUTH [MINECRAFT PLUGIN PASS]
    Price: MINECRAFT PLUGIN PASS - $$$
    Resources: 5/1600
    Data: 26.02.2024
    Contact Discord: .GuTeK#0001
    Contact e-mail: dev@gutcode.pl
    Our websites: https://gutcode.pl
    ⓒ 2024 by .GuTeK | ALL RIGHTS RESERVED |
*/

package GuTeK.activations.registeries;

public class MailcapTokenizer {
    public static final int UNKNOW_TOKEN = 0;
    public static final int START_TOKEN = 1;
    public static final int STRING_TOKEN = 2;
    public static final int EOI_TOKEN = 3;
    public static final int SLASH_TOKEN = 4;
    public static final int SEMICOLON_TOKEN = 5;
    public static final int EQUALS_TOKEN = 6;
    private String data;
    private int dataIndex;
    private int dataLength;
    private int currentToken;
    private String currentTokenValue;
    private boolean isAutoquoting;
    private char autoquoteChar;

    public MailcapTokenizer(final String inputString) {
        this.data = inputString;
        this.dataIndex = 0;
        this.dataLength = inputString.length();
        this.currentToken = 1;
        this.currentTokenValue = "";
        this.isAutoquoting = false;
        this.autoquoteChar = ';';
    }

    public void setIsAutoquoting(final boolean value) {
        this.isAutoquoting = value;
    }

    public void setAutoquoteChar(final char value) {
        this.autoquoteChar = value;
    }

    public int getCurrentToken() {
        return this.currentToken;
    }

    public static String nameForToken(final int token) {
        String name = "really unknow";
        switch (token) {
            case 0: {
                name = "unknow";
                break;
            }
            case 1: {
                name = "start";
                break;
            }
            case 2: {
                name = "string";
                break;
            }
            case 5: {
                name = "EOI";
                break;
            }
            case 47: {
                name = "'/'";
                break;
            }
            case 59: {
                name = "';'";
                break;
            }
            case 61: {
                name = "'='";
                break;
            }
        }
        return name;
    }

    public String getCurrentTokenValue() {
        return this.currentTokenValue;
    }

    public int nextToken() {
        if (this.dataIndex < this.dataLength) {
            while (this.dataIndex < this.dataLength && isWhiteSpaceChar(this.data.charAt(this.dataIndex))) {
                ++this.dataIndex;
            }
            if (this.dataIndex < this.dataLength) {
                final char c = this.data.charAt(this.dataIndex);
                if (this.isAutoquoting) {
                    if (!isAutoquoteSpecialChar(c)) {
                        this.processAutoquoteToken();
                    }
                    else if (c == ';' || c == '=') {
                        this.currentToken = c;
                        this.currentTokenValue = new Character(c).toString();
                        ++this.dataIndex;
                    }
                    else {
                        this.currentToken = 0;
                        this.currentTokenValue = new Character(c).toString();
                        ++this.dataIndex;
                    }
                }
                else if (isStringTokenChar(c)) {
                    this.processStringToken();
                }
                else if (c == '/' || c == ';' || c == '=') {
                    this.currentToken = c;
                    this.currentTokenValue = new Character(c).toString();
                    ++this.dataIndex;
                }
                else {
                    this.currentToken = 0;
                    this.currentTokenValue = new Character(c).toString();
                    ++this.dataIndex;
                }
            }
            else {
                this.currentToken = 5;
                this.currentTokenValue = null;
            }
        }
        else {
            this.currentToken = 5;
            this.currentTokenValue = null;
        }
        return this.currentToken;
    }

    private void processStringToken() {
        final int initialIndex = this.dataIndex;
        while (this.dataIndex < this.dataLength && isStringTokenChar(this.data.charAt(this.dataIndex))) {
            ++this.dataIndex;
        }
        this.currentToken = 2;
        this.currentTokenValue = this.data.substring(initialIndex, this.dataIndex);
    }

    private void processAutoquoteToken() {
        final int initialIndex = this.dataIndex;
        boolean foundTerminator = false;
        while (this.dataIndex < this.dataLength && !foundTerminator) {
            final char c = this.data.charAt(this.dataIndex);
            if (c != this.autoquoteChar) {
                ++this.dataIndex;
            }
            else {
                foundTerminator = true;
            }
        }
        this.currentToken = 2;
        this.currentTokenValue = fixEscapeSequences(this.data.substring(initialIndex, this.dataIndex));
    }

    public static boolean isSpecialChar(final char c) {
        boolean lAnswer = false;
        switch (c) {
            case '\"':
            case '(':
            case ')':
            case ',':
            case '/':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case '[':
            case '\\':
            case ']': {
                lAnswer = true;
                break;
            }
        }
        return lAnswer;
    }

    public static boolean isAutoquoteSpecialChar(final char c) {
        boolean lAnswer = false;
        switch (c) {
            case ';':
            case '=': {
                lAnswer = true;
                break;
            }
        }
        return lAnswer;
    }

    public static boolean isControlChar(final char c) {
        return Character.isISOControl(c);
    }

    public static boolean isWhiteSpaceChar(final char c) {
        return Character.isWhitespace(c);
    }

    public static boolean isStringTokenChar(final char c) {
        return !isSpecialChar(c) && !isControlChar(c) && !isWhiteSpaceChar(c);
    }

    private static String fixEscapeSequences(final String inputString) {
        final int inputLength = inputString.length();
        final StringBuffer buffer = new StringBuffer();
        buffer.ensureCapacity(inputLength);
        for (int i = 0; i < inputLength; ++i) {
            final char currentChar = inputString.charAt(i);
            if (currentChar != '\\') {
                buffer.append(currentChar);
            }
            else if (i < inputLength - 1) {
                final char nextChar = inputString.charAt(i + 1);
                buffer.append(nextChar);
                ++i;
            }
            else {
                buffer.append(currentChar);
            }
        }
        return buffer.toString();
    }
}


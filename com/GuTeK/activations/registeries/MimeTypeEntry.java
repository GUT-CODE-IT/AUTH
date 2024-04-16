/*
    Author: .GuTeK <dev@gutcode.pl>
    Project: AUTH [MINECRAFT PLUGIN PASS]
    Price: MINECRAFT PLUGIN PASS - $$$
    Resources: 6/1600
    Data: 16.04.2024
    Contact Discord: .GuTeK#0001
    Contact e-mail: dev@gutcode.pl
    Our websites: https://gutcode.pl
    ⓒ 2024 by .GuTeK | ALL RIGHTS RESERVED |
*/

package GuTeK.activations.registeries;

public class MimeTypeEntry {
    private String type;
    private String extension;

    public MimeTypeEntry(final String mime_type, final String file_ext) {
        this.type = mime_type;
        this.extension = file_ext;
    }

    public String getMIMEType() {
        return this.type;
    }

    public String getFileExtension() {
        return this.extension;
    }

    public String toString() {
        return "MINETypeEntry: " + this.type + ", " + this.extension;
    }
}

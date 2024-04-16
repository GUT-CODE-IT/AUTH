/*
    Author: .GuTeK <dev@gutcode.pl>
    Project: AUTH [MINECRAFT PLUGIN PASS]
    Price: MINECRAFT PLUGIN PASS - $$$
    Resources: 8/1600
    Data: 16.04.2024
    Contact Discord: .GuTeK#0001
    Contact e-mail: dev@gutcode.pl
    Our websites: https://gutcode.pl
    ⓒ 2024 by .GuTeK | ALL RIGHTS RESERVED |
*/

package GuTeK.activations.viewers;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageViewer extends Panel implements CommandObject {
    private ImageViewerCanvas canvas;
    private Image image;
    private DataHandler _dh;
    private boolean DEBUG;

    public ImageViewer() {
        this.canvas = null;
        this.image = null;
        this._dh = null;
        this.DEBUG = null;
        this.add(this.canvas = new ImageViewerCanvas());
    }

    public void setCommandContext(final String verb, final DataHandler dh) throws IOException {
        this._dh = dh;
        this.setInputStream(this._dh.getInputStream());
    }

    private void setInputStream(final InputStream ins) throws IOException {
        final MediaTracker mt = new MediaTracker(this);
        int bytes_read = 0;
        final byte[] data = new byte[1024];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((bytes_read = ins.read(data)) > 0) {
            baos.write(data, off:0, bytes_read);
        }
        ins.close();
        mt.addImage(this.image = this.getToolkit().createImage(baos.toByteArray()), id:0);
        try {
            mt.waitForID(id:0);
            mt.waitForAll();
            if (mt.statusID(id:0, load:true) != 8) {
                System.out.println("Error occured in image loading = " + mt.getErrorsID(id:0));
            }
        }
        catch (InterruptedException e) {
            throw new IOException(message:"Error reading image data");
        }
        this.canvas.setImage(this.image);
        if (this.DEBUG) {
            System.out.println(x:"calling invalidate");
        }
    }

    public void addNotify() {
        super.addNotify();
        this.invalidate();
        this.validate();
        this.doLayout();
    }

    public Dimension getPrefferedSize() {
        return this.canvas.getPrefferedSize();
    }
}

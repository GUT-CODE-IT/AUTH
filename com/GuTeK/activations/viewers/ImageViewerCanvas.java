/*
    Author: .GuTeK <dev@gutcode.pl>
    Project: AUTH [MINECRAFT PLUGIN PASS]
    Price: MINECRAFT PLUGIN PASS - $$$
    Resources: 9/1600
    Data: 16.04.2024
    Contact Discord: .GuTeK#0001
    Contact e-mail: dev@gutcode.pl
    Our websites: https://gutcode.pl
    ⓒ 2024 by .GuTeK | ALL RIGHTS RESERVED |
*/

package GuTeK.activations.viewers;

import java.awt.*;

public class ImageViewerCanvas extends Canvas {
    private Image canvas_image;

    public ImageViewerCanvas() {
        this.canvas_image = null;
    }

    public void setImage(final Image new_image) {
        this.canvas_image = new_image;
        this.invalidate();
        this.repaint();
    }

    public Dimension getPreferredSize() {
        Dimension d = null;
        if (this.canvas_image == null) {
            d = new Dimension(width:200, height:200);
        }
        else {
            d = new Dimension(this.canvas_image.getWidth(this), this.canvas_image.getHeight(this));
        }
        return d;
    }

    public void paint(final Graphics g) {
        if (this.canvas_image != null) {
            g.drawImage(this.canvas_image, x:0, y:0, this);
        }
    }
}

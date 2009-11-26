import java.awt.*;
import javax.swing.*;

public class AddIcon implements Icon, SwingConstants {
    private int width = 50;
    private int height = 50;

    private int[] xPoints;
    private int[] yPoints;

    public AddIcon(int width, int  height) {

	this.width=width;
	this.height=height;
	int xoffset=width/6;
	int yoffset=width/6;
	xPoints= new int[] {
	    width/2 -xoffset, width/2-xoffset, 0,
	    0,  width/2-xoffset, width/2-xoffset,
	    width/2+xoffset, width/2+xoffset, width,
	    width, width/2+xoffset, width/2+xoffset};
	    
	yPoints= new int[] {
	    0,  height/2 -yoffset, height/2-yoffset,
	    height/2+yoffset, height/2+yoffset, height,
	    height, height/2+yoffset,  height/2+yoffset,
	    height/2-yoffset, height/2-yoffset, 0};
			    
    }

    public int getIconHeight() {
        return height;
    }

    public int getIconWidth() {
        return width;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (c.isEnabled()) {
            g.setColor(c.getForeground());
        } else {
            g.setColor(Color.gray);
        }

        g.translate(x, y);
        g.fillPolygon(xPoints, yPoints, xPoints.length);
        g.translate(-x, -y);   //Restore graphics object
    }
}

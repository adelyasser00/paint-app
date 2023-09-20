import java.awt.geom.Ellipse2D;

public class Ellipse extends Ellipse2D.Float {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Ellipse(int x1, int y1, int x2, int y2) {
		setFrame(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

}
import java.awt.geom.Rectangle2D;

public class Square extends Rectangle2D.Float {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Square(int x1, int y1, int x2, int y2) {
		int side = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
		int x1New = x1 > x2 ? x1 - side : x1;
		int y1New = y1 > y2 ? y1 - side : y1;
		setFrame(x1New, y1New, side, side);
	}

}
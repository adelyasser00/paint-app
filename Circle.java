import java.awt.geom.Ellipse2D;

public class Circle extends Ellipse2D.Float {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Circle(float x1, float y1, float x2, float y2) {
		float side = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
		float x1New = x1 > x2 ? x1 - side : x1;
		float y1New = y1 > y2 ? y1 - side : y1;
		setFrame(x1New, y1New, side, side);
	}

}
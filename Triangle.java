import java.awt.geom.Path2D;

public class Triangle extends Path2D.Float {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Triangle(int x1, int y1, int x2, int y2) {
		super(Path2D.WIND_EVEN_ODD);
		float side = Math.abs(x1 - x2) / 2f;
		float xMid = x2 > x1 ? x1 + side : x1 - side;

		this.moveTo(x1, y1);
		this.lineTo(xMid, y2);
		this.lineTo(x2, y1);
		this.closePath();
	}

}
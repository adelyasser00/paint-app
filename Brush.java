import java.awt.geom.Path2D;

public class Brush extends Path2D.Float {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Brush(int rule) {
		super(rule);
	}

	Brush(int rule, float x1, float y1) {
		super(rule);
		moveTo(x1, y1);
		lineTo(x1, y1);
	}

	public Brush brushMove(int x2, int y2) {
		lineTo(x2, y2);
		return this;
	}
}
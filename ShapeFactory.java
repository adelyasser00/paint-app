import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class ShapeFactory {
	public Shape createShape(int tool, int x1, int y1, int x2, int y2, Shape r) {
		switch (tool) {
		case 0: { // brush
			return new Brush(Brush.WIND_EVEN_ODD, x1, y1);
		}
		case 1: { // line
			return new Line(x1, y1, x2, y2);
		}
		case 2: { // rectangle
			return new Rectangle(x1, y1, x2, y2);
		}
		case 3: { // square
			return new Square(x1, y1, x2, y2);
		}
		case 4: { // ellipse
			return new Ellipse(x1, y1, x2, y2);

		}
		case 5: { // circle
			return new Circle(x1, y1, x2, y2);
		}
		case 6: { // triangle
			return new Triangle(x1, y1, x2, y2);
		}
		case 8: { // move
			if (r == null) {
				return null;
			}
			AffineTransform at = new AffineTransform();
			at.translate(x2 - x1, y2 - y1);
			return at.createTransformedShape(r);
		}
		case 9: { // resize
			if (r == null) {
				return null;
			}
			AffineTransform at = new AffineTransform();
			Rectangle2D rect = r.getBounds2D();
			double x = rect.getX();
			double y = rect.getY();
			double width = rect.getWidth();
			double height = rect.getHeight();
			double scale = Math.min((x2 - x) / width, (y2 - y) / height);
			at.setToScale(scale, scale);
			Shape q = at.createTransformedShape(r);
			rect = q.getBounds2D();
			at.setToTranslation(x - rect.getX(), y - rect.getY());
			return at.createTransformedShape(q);
		}
		default: {
			return null;
		}
		}

	}

}
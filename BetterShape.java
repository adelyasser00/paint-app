import java.awt.Color;
import java.awt.Shape;

public class BetterShape {
	/**
	 * 
	 */
	private int stroke;
	private float alpha;
	private Color color;
	private Boolean fill;
	private Boolean brush;
	private Shape s;

	BetterShape(Shape s, Color color, Boolean fill, int stroke, float alpha) {
		this.s = s;
		this.color = color;
		this.fill = fill;
		this.stroke = stroke;
		this.alpha = alpha;
		this.brush = false;
	}

	BetterShape(Shape s, Color color, Boolean fill, int stroke, float alpha, Boolean pencil) {
		this.s = s;
		this.color = color;
		this.fill = fill;
		this.stroke = stroke;
		this.alpha = alpha;
		this.brush = pencil;
	}

	public int getStroke() {
		return stroke;
	}

	public float getAlpha() {
		return alpha;
	}

	public Color getColor() {
		return color;
	}

	public Boolean getFill() {
		return fill;
	}

	public Boolean getBrush() {
		return brush;
	}

	public Shape getShape() {
		return s;
	}

	@Override
	public String toString() {
		return String.format(stroke + " " + alpha + " " + color);
	}

}
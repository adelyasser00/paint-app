import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gui {
	private Boolean fill = false;
	private Color color = Color.black;
	private Color colorBg = new Color(233, 235, 235);
	private int tool = 0; // 0 brush, 1 line, 2 rectangle, 3 square, 4 ellipse, 5 circle, 6 triangle
	// 7 select, 8 move, 9 resize

	private static Font titleButton = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	private static Font subtitle = new Font(Font.SANS_SERIF, Font.BOLD, 22);
	private static Font labelButton = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

	private JSlider sliderOpacity;
	private JSlider sliderStroke;
	private JFrame frame;
	private JPanel panelColorPreview;
	private JFrame frameColors;

	private void createColorChooser() {
		frameColors = new JFrame("Color Chooser");
		frameColors.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameColors.getContentPane().setPreferredSize(new Dimension(610, 260));
		frameColors.setResizable(false);

		JPanel panel = new JPanel();
		panel.setSize(610, 260);
		panel.setLayout(null);

		JColorChooser colorChooser = new JColorChooser(color);
		AbstractColorChooserPanel[] colorPanels = new AbstractColorChooserPanel[1];
		colorPanels[0] = colorChooser.getChooserPanels()[2];
		colorChooser.setChooserPanels(colorPanels);
		colorChooser.setPreviewPanel(new JPanel());
		colorChooser.setBounds(0, 0, 610, 220);

		panel.add(colorChooser);
		JButton button = new JButton("Ok");
		button.setFont(labelButton);
		button.setBounds(280, 220, 50, 30);
		button.setFocusable(false);
		panel.add(button);

		frameColors.add(panel);
		frameColors.pack();
		frameColors.setLocationRelativeTo(null);
		frameColors.setVisible(true);

		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				color = colorChooser.getColor();
				panelColorPreview.setBackground(color);
				frame.repaint();
			}
		};

		button.addActionListener(buttonListener);

		frameColors.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frameColors = null;
			}
		});

	}

	public void createGui() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		frame = new JFrame("Drawing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(1260, 680));
		frame.setResizable(false);

		JPanel panel1 = new JPanel();
		panel1.setSize(1260, 680);
		panel1.setLayout(null);
		panel1.setBackground(colorBg);

		PaintSurface paintSurface = new PaintSurface();
		paintSurface.setBounds(260, 0, 1000, 680);
		panel1.add(paintSurface);

		JButton buttonUndo = new JButton("Undo");
		buttonUndo.setFont(labelButton);
		buttonUndo.setBounds(10, 10, 75, 30);
		buttonUndo.setFocusable(false);
		panel1.add(buttonUndo);

		JButton buttonRedo = new JButton("Redo");
		buttonRedo.setFont(labelButton);
		buttonRedo.setBounds(85, 10, 75, 30);
		buttonRedo.setFocusable(false);
		panel1.add(buttonRedo);

		JButton buttonSave = new JButton("Save");
		buttonSave.setFont(labelButton);
		buttonSave.setBounds(170, 10, 80, 30);
		buttonSave.setFocusable(false);
		panel1.add(buttonSave);

		JLabel labelColors = new JLabel("Colors:");
		labelColors.setBounds(30, 50, 150, 40);
		labelColors.setHorizontalAlignment(0);
		labelColors.setFont(subtitle);
		panel1.add(labelColors);

		panelColorPreview = new JPanel();
		panelColorPreview.setBackground(color);
		panelColorPreview.setBounds(150, 50, 40, 40);
		panel1.add(panelColorPreview);

		JButton buttonBlack = new RoundButton("");
		buttonBlack.setBounds(10, 100, 30, 30);
		buttonBlack.setBackground(Color.black);
		buttonBlack.setFocusable(false);
		panel1.add(buttonBlack);

		JButton buttonWhite = new RoundButton("");
		buttonWhite.setBounds(60, 100, 30, 30);
		buttonWhite.setBackground(Color.white);
		buttonWhite.setFocusable(false);
		panel1.add(buttonWhite);

		JButton buttonRed = new RoundButton("");
		buttonRed.setBounds(110, 100, 30, 30);
		buttonRed.setBackground(Color.red);
		buttonRed.setFocusable(false);
		panel1.add(buttonRed);

		JButton buttonBlue = new RoundButton("");
		buttonBlue.setBounds(10, 135, 30, 30);
		buttonBlue.setBackground(Color.blue);
		buttonBlue.setFocusable(false);
		panel1.add(buttonBlue);

		JButton buttonGreen = new RoundButton("");
		buttonGreen.setBounds(60, 135, 30, 30);
		buttonGreen.setBackground(Color.green);
		buttonGreen.setFocusable(false);
		panel1.add(buttonGreen);

		JButton buttonYellow = new RoundButton("");
		buttonYellow.setBounds(110, 135, 30, 30);
		buttonYellow.setBackground(Color.yellow);
		buttonYellow.setFocusable(false);
		panel1.add(buttonYellow);

		JButton buttonMoreColors = new JButton("More..");
		buttonMoreColors.setBounds(160, 110, 80, 45);
		buttonMoreColors.setFont(labelButton);
		buttonMoreColors.setFocusable(false);
		panel1.add(buttonMoreColors);

		JLabel labelSlider = new JLabel("Opacity:");
		labelSlider.setBounds(0, 170, 260, 40);
		labelSlider.setHorizontalAlignment(0);
		labelSlider.setFont(subtitle);
		panel1.add(labelSlider);

		sliderOpacity = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		sliderOpacity.setMajorTickSpacing(50);
		sliderOpacity.setMinorTickSpacing(1);
		sliderOpacity.setPaintTicks(true);
		sliderOpacity.setPaintLabels(true);
		sliderOpacity.setBounds(20, 210, 220, 40);
		sliderOpacity.setFocusable(false);
		sliderOpacity.setSnapToTicks(true);
		sliderOpacity.setBackground(colorBg);
		panel1.add(sliderOpacity);

		JLabel labelFill = new JLabel("Fill shape:");
		labelFill.setBounds(0, 260, 100, 30);
		labelFill.setHorizontalAlignment(0);
		labelFill.setFont(titleButton);
		panel1.add(labelFill);

		JToggleButton toggleButton = new JToggleButton("Off");
		toggleButton.setBounds(10, 290, 80, 40);
		toggleButton.setFont(labelButton);
		toggleButton.setFocusable(false);
		panel1.add(toggleButton);

		JLabel labelStroke = new JLabel("Stroke size:");
		labelStroke.setBounds(100, 260, 160, 30);
		labelStroke.setHorizontalAlignment(0);
		labelStroke.setFont(titleButton);
		panel1.add(labelStroke);

		sliderStroke = new JSlider(JSlider.HORIZONTAL, 0, 20, 5);
		sliderStroke.setMajorTickSpacing(5);
		sliderStroke.setMinorTickSpacing(1);
		sliderStroke.setPaintTicks(true);
		sliderStroke.setPaintLabels(true);
		sliderStroke.setBounds(110, 290, 140, 40);
		sliderStroke.setFocusable(false);
		sliderStroke.setSnapToTicks(true);
		sliderStroke.setBackground(colorBg);
		panel1.add(sliderStroke);

		JLabel labelTools = new JLabel("Tools:");
		labelTools.setBounds(0, 340, 260, 30);
		labelTools.setHorizontalAlignment(0);
		labelTools.setFont(subtitle);
		panel1.add(labelTools);

		JButton buttonBrush = new JButton("Brush");
		buttonBrush.setBounds(15, 380, 100, 40);
		buttonBrush.setFont(labelButton);
		buttonBrush.setFocusPainted(true);
		panel1.add(buttonBrush);

		JButton buttonLine = new JButton("Line");
		buttonLine.setBounds(145, 380, 100, 40);
		buttonLine.setFont(labelButton);
		panel1.add(buttonLine);

		JButton buttonRectangle = new JButton("Rectangle");
		buttonRectangle.setBounds(15, 430, 100, 40);
		buttonRectangle.setFont(labelButton);
		panel1.add(buttonRectangle);

		JButton buttonSquare = new JButton("Square");
		buttonSquare.setBounds(145, 430, 100, 40);
		buttonSquare.setFont(labelButton);
		panel1.add(buttonSquare);

		JButton buttonEllipse = new JButton("Ellipse");
		buttonEllipse.setBounds(15, 480, 100, 40);
		buttonEllipse.setFont(labelButton);
		panel1.add(buttonEllipse);

		JButton buttonCircle = new JButton("Circle");
		buttonCircle.setBounds(145, 480, 100, 40);
		buttonCircle.setFont(labelButton);
		panel1.add(buttonCircle);

		JButton buttonTriangle = new JButton("Triangle");
		buttonTriangle.setBounds(15, 530, 100, 40);
		buttonTriangle.setFont(labelButton);
		panel1.add(buttonTriangle);

		JButton buttonSelect = new JButton("Select");
		buttonSelect.setBounds(145, 530, 100, 40);
		buttonSelect.setFont(labelButton);
		panel1.add(buttonSelect);

		JButton buttonMove = new JButton("Move");
		buttonMove.setBounds(15, 580, 100, 40);
		buttonMove.setFont(labelButton);
		panel1.add(buttonMove);

		JButton buttonResize = new JButton("Resize");
		buttonResize.setBounds(145, 580, 100, 40);
		buttonResize.setFont(labelButton);
		panel1.add(buttonResize);

		JButton buttonCopy = new JButton("Copy");
		buttonCopy.setBounds(15, 630, 100, 40);
		buttonCopy.setFont(labelButton);
		buttonCopy.setFocusable(false);
		panel1.add(buttonCopy);

		JButton buttonDelete = new JButton("Delete");
		buttonDelete.setBounds(145, 630, 100, 40);
		buttonDelete.setFont(labelButton);
		buttonDelete.setFocusable(false);
		panel1.add(buttonDelete);

		frame.add(panel1);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				if (src == buttonBlack) {
					color = Color.black;
					panelColorPreview.setBackground(color);
					frame.repaint();
				} else if (src == buttonWhite) {
					color = Color.white;
					panelColorPreview.setBackground(color);
					frame.repaint();
				} else if (src == buttonBlue) {
					color = Color.blue;
					panelColorPreview.setBackground(color);
					frame.repaint();
				} else if (src == buttonGreen) {
					color = Color.green;
					panelColorPreview.setBackground(color);
					frame.repaint();
				} else if (src == buttonRed) {
					color = Color.red;
					panelColorPreview.setBackground(color);
					frame.repaint();
				} else if (src == buttonYellow) {
					color = Color.yellow;
					panelColorPreview.setBackground(color);
					frame.repaint();
				} else if (src == toggleButton) {
					if (toggleButton.isSelected()) {
						toggleButton.setText("On");
						fill = true;
					} else {
						toggleButton.setText("Off");
						fill = false;
					}
				} else if (src == buttonBrush) {
					tool = 0;
				} else if (src == buttonLine) {
					tool = 1;
				} else if (src == buttonRectangle) {
					tool = 2;
				} else if (src == buttonSquare) {
					tool = 3;
				} else if (src == buttonEllipse) {
					tool = 4;
				} else if (src == buttonCircle) {
					tool = 5;
				} else if (src == buttonTriangle) {
					tool = 6;
				} else if (src == buttonUndo) {
					paintSurface.undo();
				} else if (src == buttonRedo) {
					paintSurface.redo();
				} else if (src == buttonMoreColors) {
					if (frameColors == null) {
						createColorChooser();
					}
				} else if (src == buttonSelect) {
					tool = 7;
				} else if (src == buttonMove) {
					tool = 8;
				} else if (src == buttonResize) {
					tool = 9;
				} else if (src == buttonCopy) {
					paintSurface.copyShape();
				} else if (src == buttonDelete) {
					paintSurface.deleteShape();
				} else if (src == buttonSave) {
					paintSurface.save();
				}
			}
		};

		buttonBlack.addActionListener(buttonListener);
		buttonWhite.addActionListener(buttonListener);
		buttonBlue.addActionListener(buttonListener);
		buttonGreen.addActionListener(buttonListener);
		buttonRed.addActionListener(buttonListener);
		buttonYellow.addActionListener(buttonListener);
		toggleButton.addActionListener(buttonListener);
		buttonBrush.addActionListener(buttonListener);
		buttonRectangle.addActionListener(buttonListener);
		buttonSquare.addActionListener(buttonListener);
		buttonEllipse.addActionListener(buttonListener);
		buttonCircle.addActionListener(buttonListener);
		buttonLine.addActionListener(buttonListener);
		buttonTriangle.addActionListener(buttonListener);
		buttonUndo.addActionListener(buttonListener);
		buttonRedo.addActionListener(buttonListener);
		buttonMoreColors.addActionListener(buttonListener);
		buttonSelect.addActionListener(buttonListener);
		buttonMove.addActionListener(buttonListener);
		buttonResize.addActionListener(buttonListener);
		buttonCopy.addActionListener(buttonListener);
		buttonDelete.addActionListener(buttonListener);
		buttonSave.addActionListener(buttonListener);
	}

	private class PaintSurface extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private DynamicStackBetterShape shapes = new DynamicStackBetterShape(2);
		private DynamicStackBetterShape shapes1 = new DynamicStackBetterShape(2);
		private DynamicStackInt shapes2 = new DynamicStackInt(2);
		private DynamicStackBetterShape shapes3 = new DynamicStackBetterShape(2);
		private DynamicStackInt shapes4 = new DynamicStackInt(2);
		private DynamicStackBetterShape shapes5 = new DynamicStackBetterShape(2);
		private DynamicStackInt shapes6 = new DynamicStackInt(2);
		private DynamicStackBetterShape shapes7 = new DynamicStackBetterShape(2);
		private DynamicStackInt shapes8 = new DynamicStackInt(2);
		private DynamicStackBetterShape shapes9 = new DynamicStackBetterShape(2);
		private DynamicStackInt shapes10 = new DynamicStackInt(2);
		private DynamicStackBetterShape shapes11 = new DynamicStackBetterShape(2);
		private DynamicStackInt shapes12 = new DynamicStackInt(2);

		private Brush brushPath = null;
		private int selectedShape = -1;
		private Point startDrag, endDrag;

		private ShapeFactory shapeFactory = new ShapeFactory();

		public PaintSurface() {
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					startDrag = new Point(e.getX(), e.getY());
					endDrag = startDrag;
					if (tool == 0) {
						selectedShape = -1;
						clearHistory();
					} else if (tool == 7) {
						selectShape(startDrag.x, startDrag.y);

					} else if (tool < 7 && !(tool == 1 && fill)) {
						selectedShape = -1;
						clearHistory();
					} else if (tool == 1) {
						selectedShape = -1;
					}
					repaint();
				}

				public void mouseReleased(MouseEvent e) {
					if (tool < 7) {
						if (startDrag != null && endDrag != null) {
							if (sliderOpacity.getValue() != 0) { // in case opacity 0 don't add shape object
								if (tool != 0) {
									if (tool == 1 && fill) { // in case line and fill don't add shape object
									} else {
										Shape r = makeShape(startDrag.x, startDrag.y, e.getX(), e.getY());
										shapes.push(new BetterShape(r, color, fill, sliderStroke.getValue(),
												sliderOpacity.getValue() / 100f));
									}
									startDrag = null;
									endDrag = null;
									repaint();
								} else {
									shapes.push(new BetterShape(brushPath, color, fill, sliderStroke.getValue(),
											sliderOpacity.getValue() / 100f, true));
									brushPath = null;
									startDrag = null;
									endDrag = null;
									repaint();
								}
							} else {
								startDrag = null;
								endDrag = null;
								repaint();
							}
						}
						if (!(tool == 1 && fill)) {
							shapes2.push(0);
						}
					} else if (selectedShape != -1) {
						if (tool == 8) {
							Shape r = makeShape(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
							BetterShape old = shapes.popBetterShapeI(selectedShape);
							shapes9.push(old);
							shapes10.push(selectedShape);
							shapes2.push(3);
							shapes.pushIndex(new BetterShape(r, old.getColor(), old.getFill(), old.getStroke(),
									old.getAlpha(), old.getBrush()), selectedShape);
						} else if (tool == 9) {
							Shape r = makeShape(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
							BetterShape old = shapes.popBetterShapeI(selectedShape);
							shapes9.push(old);
							shapes10.push(selectedShape);
							shapes2.push(3);
							shapes.pushIndex(new BetterShape(r, old.getColor(), old.getFill(), old.getStroke(),
									old.getAlpha(), old.getBrush()), selectedShape);
						}
						startDrag = null;
						endDrag = null;
						repaint();
					}
				}
			});

			this.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					endDrag = new Point(e.getX(), e.getY());
					repaint();
				}
			});
		}

		private void paintBackground(Graphics2D g2) {
			g2.setPaint(Color.WHITE);
			Shape rectangle = new Rectangle2D.Float(0, 0, 1000, 680);
			g2.draw(rectangle);
			g2.fill(rectangle);
		}

		public void paint(Graphics g) {
			BetterShape[] shapesArray = shapes.getArray();
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			paintBackground(g2);

			for (BetterShape s : shapesArray) {
				if (s.getBrush()) {
					g2.setStroke(new BasicStroke(s.getStroke(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
							null, 0.0f));
				} else {
					g2.setStroke(new BasicStroke(s.getStroke()));
				}
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, s.getAlpha()));
				g2.setPaint(s.getColor());

				if (s.getFill()) {
					g2.setPaint(s.getColor()); // fill shapes
					g2.fill(s.getShape());
				} else {
					g2.draw(s.getShape());
				}
			}
			if (selectedShape != -1) {
				float dash1[] = { 10.0f };
				g2.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
				g2.setPaint(Color.LIGHT_GRAY);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				g2.draw(shapesArray[selectedShape].getShape().getBounds2D());
			}
			if (startDrag != null && endDrag != null) {
				if (selectedShape != -1) {
					if (tool == 8) {
						g2.setStroke(
								new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f));
						g2.setPaint(Color.LIGHT_GRAY);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
						Shape r = makeShape(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
						g2.draw(r);
					} else if (tool == 9) {
						g2.setStroke(
								new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f));
						g2.setPaint(Color.LIGHT_GRAY);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
						Shape r = makeShape(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
						g2.draw(r);
						float dash1[] = { 10.0f };
						g2.setStroke(
								new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
						g2.setPaint(Color.LIGHT_GRAY);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
						g2.draw(r.getBounds2D());
					}
				} else if (tool > 0 && tool < 7) {
					if (fill) {
						g2.setStroke(new BasicStroke(1));
					} else {
						g2.setStroke(new BasicStroke(sliderStroke.getValue()));
					}
					g2.setPaint(Color.LIGHT_GRAY);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					Shape r = makeShape(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
					g2.draw(r);
				} else if (tool == 0) {
					if (fill) {
						g2.setStroke(
								new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, null, 0.0f));
					} else {
						g2.setStroke(new BasicStroke(sliderStroke.getValue(), BasicStroke.CAP_ROUND,
								BasicStroke.JOIN_ROUND, 10.0f, null, 0.0f));
					}
					g2.setPaint(color);
					g2.setComposite(
							AlphaComposite.getInstance(AlphaComposite.SRC_OVER, sliderOpacity.getValue() / 100f));
					Shape r = makeShape(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
					g2.draw(r);
				}

			}
		}

		private void undo() {
			int s2 = shapes2.pop();
			if (s2 != -1) {
				selectedShape = -1;
				if (s2 == 1) {
					shapes3.push(shapes.pop());
					shapes.push(shapes5.pop());
					shapes4.push(1);
				} else if (s2 == 0) {
					shapes1.push(shapes.pop());
					shapes4.push(0);
				} else if (s2 == 2) { // s2 == 2 (deleted)
					int i = shapes6.pop();
					shapes.pushIndex(shapes7.pop(), i);
					shapes4.push(2);
					shapes8.push(i);
				} else { // s2 == 3 (move/resize)
					int i = shapes10.pop();
					shapes11.push(shapes.popBetterShapeI(i));
					shapes12.push(i);
					shapes.pushIndex(shapes9.pop(), i);
					shapes4.push(3);
				}
				repaint();
			}
		}

		private void redo() {
			int s4 = shapes4.pop();
			if (s4 != -1) {
				selectedShape = -1;
				if (s4 == 1) {
					shapes5.push(shapes.pop());
					shapes2.push(1);
					shapes.push(shapes3.pop());
				} else if (s4 == 0) {
					shapes.push(shapes1.pop());
					shapes2.push(0);
				} else if (s4 == 2) { // s4 == 2 (deleted)
					deleteShape(shapes8.pop());
				} else { // s4 == 3 (move/resize)
					int i = shapes12.pop();
					shapes9.push(shapes.popBetterShapeI(i));
					shapes10.push(i);
					shapes.pushIndex(shapes11.pop(), i);
					shapes2.push(3);
				}
				repaint();
			}
		}

		private Shape makeShape(int x1, int y1, int x2, int y2) {
			Shape r = selectedShape == -1 ? null : shapes.getArray()[selectedShape].getShape();
			if (brushPath == null) {
				if (tool == 0) {
					brushPath = (Brush) shapeFactory.createShape(tool, x1, y1, x2, y2, r);
					return brushPath;
				} else {
					return shapeFactory.createShape(tool, x1, y1, x2, y2, r);
				}
			} else {
				return brushPath.brushMove(x2, y2);
			}

		}

		private BetterShape selectShape(int x, int y) {
			BetterShape[] shapesArray = shapes.getArray();
			for (int i = shapesArray.length - 1; i >= 0; i--) {
				if (shapesArray[i].getShape().getBounds2D().contains(new Point(x, y))) {
					selectedShape = i;
					return shapesArray[i];
				}
			}
			selectedShape = -1;
			return null;
		}

		private void deleteShape() {
			if (selectedShape != -1) {
				shapes7.push(shapes.popBetterShapeI(selectedShape));
				shapes2.push(2);
				shapes6.push(selectedShape);
				selectedShape = -1;
				repaint();
			}
		}

		private void deleteShape(int i) {
			shapes7.push(shapes.popBetterShapeI(i));
			shapes2.push(2);
			shapes6.push(i);
			selectedShape = -1;
			repaint();

		}

		private void copyShape() {
			if (selectedShape != -1) {
				AffineTransform at = new AffineTransform();
				at.setToTranslation(10, 10);
				BetterShape bs = shapes.getArray()[selectedShape];
				Shape s = at.createTransformedShape(bs.getShape());
				shapes.push(
						new BetterShape(s, bs.getColor(), bs.getFill(), bs.getStroke(), bs.getAlpha(), bs.getBrush()));
				selectedShape = shapes.getSize() - 1;
				shapes2.push(0);
				repaint();
			}
		}

		private void clearHistory() {
			while (!shapes1.isEmpty()) {
				shapes1.pop();
			}
			while (!shapes3.isEmpty()) {
				shapes3.pop();
			}
			while (!shapes4.isEmpty()) {
				shapes4.pop();
			}
			while (!shapes8.isEmpty()) {
				shapes8.pop();
			}
			while (!shapes11.isEmpty()) {
				shapes11.pop();
			}
			while (!shapes12.isEmpty()) {
				shapes12.pop();
			}
		}

		public void save() {
			BufferedImage bImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D cg = bImg.createGraphics();
			this.paintAll(cg);
			try {
				File file = new File("./output_image.png");
				for (int num = 1; file.exists(); num++) {
					file = new File("./output_image" + num + ".png");
				}
				if (ImageIO.write(bImg, "png", file)) {
					System.out.println("-- saved");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
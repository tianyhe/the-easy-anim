package cs5004.animator.view;

import cs5004.animator.model.IAnimatorModel;
import cs5004.animator.model.shapeutil.IShape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.List;
import javax.swing.JPanel;

/**
 * Represents the JPanel that will be used to display the animation. This class extends JPanel and
 * overrides the paintComponent method to draw the animation.
 */
public class VisualViewPanel extends JPanel {

  private final IAnimatorModel model;

  /**
   * Constructs the panel from the model.
   *
   * @param model the model
   */
  public VisualViewPanel(IAnimatorModel model) {
    super();
    this.model = model;
    this.setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLACK);
    List<IShape> shapes = this.model.getShapes();
    for (IShape s : shapes) {
      if (s.isInvisible()) {
        continue;
      }
      Shape shape = s.isOval() ? new Ellipse2D.Double(s.getLocation().getX()
          - model.getCanvasStartingX(), s.getLocation().getY() - model.getCanvasStartingY(),
          s.getWidth(), s.getHeight())
          : new Rectangle.Double(s.getLocation().getX() - model.getCanvasStartingX(),
              s.getLocation().getY() - model.getCanvasStartingY(),
              s.getWidth(), s.getHeight());
      g2d.setColor(new Color(s.getColor().getR(), s.getColor().getG(), s.getColor().getB()));
      g2d.fill(shape);
    }
  }
}

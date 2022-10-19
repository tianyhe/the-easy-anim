package cs5004.animator.view;

import cs5004.animator.model.IAnimatedShape;
import cs5004.animator.model.IAnimatorModel;
import cs5004.animator.model.Motion;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Represents a svg view for an animation. This view can show or store a svg description of the
 * animation.
 */
public class SvgView implements ITextualView {

  private int ticksPerSecond = 1;
  private String outputFileName = "default";
  private final IAnimatorModel model;
  private final StringBuilder sb = new StringBuilder();

  /**
   * Constructs a svg view for an animation that takes in a model and an output file name.
   *
   * @param model          the model to be used in the view
   * @param outputFileName the output file name to be used in the view
   * @param speed          the speed of the animation
   */
  public SvgView(IAnimatorModel model, String outputFileName, int speed) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be positive");
    }
    this.model = model;
    this.outputFileName = outputFileName;
    this.ticksPerSecond = speed;
  }

  @Override
  public String getText() {
    sb.append("<svg viewbox=\"").append(model.getCanvasStartingX()).append(" ")
        .append(model.getCanvasStartingY()).append(" ").append(model.getCanvasWidth()).append(" ")
        .append(model.getCanvasHeight()).append("\" version=\"1.1\"\n")
        .append("     xmlns=\"http://www.w3.org/2000/svg\">").append("\n");
    List<IAnimatedShape> shapes = model.getAnimatedShapes();

    for (IAnimatedShape s : shapes) {
      sb.append("<").append(s.getShapeAsString(this)).append(" id=\"")
          .append(s.getShapeAtStart().getId()).append("\" ").append(this.getXLabel(s)).append("=\"")
          .append(this.getXValue(s)).append("\" ").append(this.getYLabel(s)).append("=\"")
          .append(this.getYValue(s)).append("\" ").append(this.getWidthLabel(s)).append("=\"")
          .append(this.getWidthValue(s)).append("\" ").append(this.getHeightLabel(s)).append("=\"")
          .append(this.getHeightValue(s)).append("\" fill=\"")
          .append(s.getShapeAtStart().getColor().toString()).append("\" visibility=\"");
      boolean isHidden = false;
      if (!s.getMotions().isEmpty() && s.getMotions().get(0).getStartTick() > 1) {
        isHidden = true;
        sb.append("hidden");
      } else {
        sb.append("visible");
      }

      sb.append("\" >\n");
      if (isHidden) {
        String fill = s.getMotions().size() >= 1 ? "freeze" : "remove";
        sb.append("\t<animate attributeType=\"xml\" begin=\"").append(this.getMsFromTick(1))
            .append("ms\" dur=\"").append(getMsFromTick(s.getMotions().get(0).getStartTick() - 1))
            .append("ms\" attributeName=\"visibility\" from=\"hidden\" ")
            .append("to=\"visible\" fill=\"").append(fill).append("\"/>\n");
      }

      boolean isLast = false;

      for (int i = 0; i < s.getMotions().size(); i++) {
        if (i == s.getMotions().size() - 1) {
          isLast = true;
        }
        appendTags(s.getMotions().get(i), s, isLast);
      }
      sb.append("</").append(s.getShapeAsString(this)).append(">\n");
    }
    sb.append("</svg>");
    return sb.toString();
  }

  @Override
  public String getOvalAsString() {
    return "ellipse";
  }

  @Override
  public String getRectangleAsString() {
    return "rect";
  }

  @Override
  public void write() {
    if (outputFileName.equals("default")) {
      System.out.print(getText());
    } else {
      try {
        FileWriter fileWriter = new FileWriter(this.outputFileName);
        fileWriter.write(getText());
        fileWriter.close();
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write to file.");
      }
    }
  }

  /**
   * Returns the label corresponding to the shape's x position, based on the shape's type.
   *
   * @param s the shape
   * @return the label corresponding to the shape's x position
   */
  private String getXLabel(IAnimatedShape s) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? "cx" : "x";
  }

  /**
   * Returns the label corresponding to the shape's y position, based on the shape's type.
   *
   * @param s the shape
   * @return the label corresponding to the shape's y position
   */
  private String getYLabel(IAnimatedShape s) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? "cy" : "y";
  }

  /**
   * Returns the value corresponding to the shape's x position, based on the shape's type.
   *
   * @param s the shape
   * @return the label corresponding to the shape's x position
   */
  private String getXValue(IAnimatedShape s) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? Double.toString(s.getShapeAtStart().getLocation().getX()
        + (double) s.getShapeAtStart().getWidth() / 2)
        : Integer.toString(s.getShapeAtStart().getLocation().getX());
  }

  /**
   * Returns the value corresponding to the shape's y position, based on the shape's type.
   *
   * @param s the shape
   * @return the value corresponding to the shape's y position
   */
  private String getYValue(IAnimatedShape s) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? Double.toString(s.getShapeAtStart().getLocation().getY()
        + (double) s.getShapeAtStart().getHeight() / 2)
        : Integer.toString(s.getShapeAtStart().getLocation().getY());
  }

  /**
   * Returns the label corresponding to the shape's width, based on the shape's type.
   *
   * @param s the shape
   * @return the label corresponding to the shape's width
   */
  private String getWidthLabel(IAnimatedShape s) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? "rx" : "width";
  }

  /**
   * Returns the label corresponding to the shape's height, based on the shape's type.
   *
   * @param s the shape
   * @return the label corresponding to the shape's height
   */
  private String getHeightLabel(IAnimatedShape s) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? "ry" : "height";
  }

  /**
   * Returns the value corresponding to the shape's width, based on the shape's type.
   *
   * @param s the shape
   * @return the value corresponding to the shape's width
   */
  private String getWidthValue(IAnimatedShape s) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? Double.toString(s.getShapeAtStart().getWidth() / 2.0) :
        Integer.toString(s.getShapeAtStart().getWidth());
  }

  /**
   * Returns the value corresponding to the shape's height, based on the shape's type.
   *
   * @param s the shape
   * @return the value corresponding to the shape's height
   */
  private String getHeightValue(IAnimatedShape s) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? Double.toString(s.getShapeAtStart().getHeight() / 2.0) :
        Integer.toString(s.getShapeAtStart().getHeight());
  }

  /**
   * Returns a string representation of the tick in ms.
   *
   * @param tick the tick
   * @return a string representation of the duration in ms
   */
  private String getMsFromTick(int tick) {
    float seconds = (float) tick / ticksPerSecond;
    Float ms = seconds * 1000;
    return String.format("%.1f", ms);
  }

  /**
   * Returns a string representation of the duration in ms.
   *
   * @param motion the motion
   * @return a string representation of the duration in ms
   */
  private String getDuration(Motion motion) {
    int tickDuration = motion.getEndTick() - motion.getStartTick();
    return getMsFromTick(tickDuration);
  }

  /**
   * Returns a string representation of the shape's location, based on the shape's type.
   *
   * @param s         the shape
   * @param xOrY      the x or y value
   * @param dimension the width or height value
   * @return a string representation of the shape's location
   */
  private String shapeSpecificPosition(IAnimatedShape s, int xOrY, int dimension) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? Double.toString(xOrY + dimension / 2.0)
        : Integer.toString(xOrY);
  }

  /**
   * Returns a string representation of the shape's dimension, based on the shape's type.
   *
   * @param s         the shape
   * @param dimension the width or height value
   * @return a string representation of the shape's dimension
   */
  private String shapeSpecificDimension(IAnimatedShape s, int dimension) {
    return Objects.equals(s.getShapeAsString(this), this.getOvalAsString())
        ? Double.toString(dimension / 2.0)
        : Integer.toString(dimension);
  }

  /**
   * Appends the proper animation tags to the text with respect to the given motion.
   *
   * @param a      the motion
   * @param s      the animated shape
   * @param isLast whether the motion is the last in the shape's list of motions
   */
  private void appendTags(Motion a, IAnimatedShape s, boolean isLast) {
    String fill;
    if (isLast && s.getLastTick() == model.getLastTick()) {
      fill = "remove";
    } else {
      fill = "freeze";
    }

    if (!a.getStartColor().equals(a.getEndColor())) {
      sb.append("\t<animate attributeType=\"xml\" begin=\"")
          .append(getMsFromTick(a.getStartTick())).append("ms\" dur=\"").append(getDuration(a))
          .append("ms\" attributeName=\"fill\" from=\"").append(a.getStartColor().toString())
          .append("\" to=\"").append(a.getEndColor().toString()).append("\" fill=\"").append(fill)
          .append("\"/>\n");
    }
    if (a.getStartHeight() != a.getEndHeight()) {
      sb.append("\t<animate attributeType=\"xml\" begin=\"")
          .append(getMsFromTick(a.getStartTick())).append("ms\" dur=\"").append(getDuration(a))
          .append("ms\" attributeName=\"").append(this.getHeightLabel(s)).append("\" from=\"")
          .append(shapeSpecificDimension(s, a.getStartHeight())).append("\" to=\"")
          .append(shapeSpecificDimension(s, a.getEndHeight())).append("\" fill=\"").append(fill)
          .append("\"/>\n");
    }
    if (a.getStartWidth() != a.getEndWidth()) {
      sb.append("\t<animate attributeType=\"xml\" begin=\"")
          .append(getMsFromTick(a.getStartTick())).append("ms\" dur=\"").append(getDuration(a))
          .append("ms\" attributeName=\"").append(this.getWidthLabel(s)).append("\" from=\"")
          .append(shapeSpecificDimension(s, a.getStartWidth())).append("\" to=\"")
          .append(shapeSpecificDimension(s, a.getEndWidth())).append("\" fill=\"").append(fill)
          .append("\"/>\n");
    }
    if (a.getStartLocation().getX() != a.getEndLocation().getX()) {
      sb.append("\t<animate attributeType=\"xml\" begin=\"")
          .append(getMsFromTick(a.getStartTick())).append("ms\" dur=\"").append(getDuration(a))
          .append("ms\" attributeName=\"").append(this.getXLabel(s)).append("\" from=\"")
          .append(shapeSpecificPosition(s, a.getStartLocation().getX(), a.getStartWidth()))
          .append("\" to=\"")
          .append(shapeSpecificPosition(s, a.getEndLocation().getX(), a.getEndWidth()))
          .append("\" fill=\"").append(fill).append("\"/>\n");
    }
    if (a.getStartLocation().getY() != a.getEndLocation().getY()) {
      sb.append("\t<animate attributeType=\"xml\" begin=\"")
          .append(getMsFromTick(a.getStartTick())).append("ms\" dur=\"").append(getDuration(a))
          .append("ms\" attributeName=\"").append(this.getYLabel(s)).append("\" from=\"")
          .append(shapeSpecificPosition(s, a.getStartLocation().getY(), a.getStartHeight()))
          .append("\" to=\"")
          .append(shapeSpecificPosition(s, a.getEndLocation().getY(), a.getEndHeight()))
          .append("\" fill=\"").append(fill).append("\"/>\n");
    }
    if (isLast && s.getLastTick() < model.getLastTick()) {
      sb.append("\t<animate attributeType=\"xml\" begin=\"")
          .append(this.getMsFromTick(a.getEndTick()))
          .append("ms\" dur=\"0.1ms\" attributeName=\"visibility\" from=\"visible\" ")
          .append("to=\"hidden\" fill=\"freeze\"/>\n");
    }
  }
}

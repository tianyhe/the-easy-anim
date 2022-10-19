package cs5004.animator.view;

import cs5004.animator.model.IAnimatedShape;
import cs5004.animator.model.IAnimatorModel;
import cs5004.animator.model.Motion;
import cs5004.animator.model.shapeutil.IShape;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a text view for an animation. This view can show or store a textual description of
 * the animation. This view will work with a variety of output sources.
 */
public class TextView implements ITextualView {

  private String outputFileName = "DEFAULT";
  private final IAnimatorModel model;

  /**
   * Constructs a text view for an animation that takes in a model and an output file name.
   *
   * @param model          the model to be used in the view
   * @param outputFileName the output file name to be used in the view
   * @throws IllegalArgumentException if the model is null
   */
  public TextView(IAnimatorModel model, String outputFileName) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.outputFileName = outputFileName;
  }

  @Override
  public String getText() {
    StringBuilder sb = new StringBuilder();
    List<IAnimatedShape> shapes = model.getAnimatedShapes();
    List<Motion> motions = new ArrayList<>();
    Map<Motion, IAnimatedShape> motionShape = new HashMap<>();
    for (IAnimatedShape s : shapes) {
      getShapeAsString(s.getShapeAt(s.getFirstTick()), sb);
      motions.addAll(s.getMotions());
      for (Motion m : s.getMotions()) {
        motionShape.put(m, s);
      }
    }
    sb.append("\n");
    for (IAnimatedShape s : shapes) {
      sb.append(getAppearsAsString(s));
    }
    sb.append("\n");
    motions.sort(Comparator.comparing(Motion::getStartTick));
    for (Motion m : motions) {
      getMotionAsString(motionShape.get(m), m, sb);
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  /**
   * Appends the textual representation of the shape in the animation to the string builder.
   *
   * @param shape the shape to be represented
   */
  private void getShapeAsString(IShape shape, StringBuilder sb) {
    IShape s = shape.copy();
    if (s.isInvisible()) {
      return;
    }
    sb.append(String.format("Create %s %s %s with ", s.getColor().toString(),
        s.getShapeAsString(), s.getId()));
    if (s.isRectangle()) {
      sb.append(String.format("corner at %s, width: %d and height %d\n", s.getLocation().toString(),
          s.getWidth(), s.getHeight()));
    } else if (s.isOval()) {
      sb.append(String.format("center at %s, radius %d and %d\n", s.getLocation().toString(),
          s.getWidth(), s.getHeight()));
    }
  }

  /**
   * Return the textual representation of the shape's first and last appearance in the animation.
   *
   * @param animatedShape the shape to be represented
   * @return the textual representation for the shape's first and last appearance in the animation
   */
  private String getAppearsAsString(IAnimatedShape animatedShape) {
    return String.format("%s appears at time t=%s and disappears at time t=%d\n",
        animatedShape.getId(),
        animatedShape.getFirstTick(), animatedShape.getLastTick());
  }

  /**
   * Add the textual representation of the motions in the animation to the string builder.
   *
   * @param animatedShape the shape to be represented
   * @param motion       the motion to be represented
   * @param sb          the string builder to be used
   */
  private void getMotionAsString(IAnimatedShape animatedShape, Motion motion, StringBuilder sb) {
    String id = animatedShape.getId();
    if (!motion.getStartLocation().toString().equals(motion.getEndLocation().toString())) {
      String str = String.format("%s moves from %s to %s from time t=%d to t=%d\n", id,
          motion.getStartLocation().toString(),
          motion.getEndLocation().toString(), motion.getStartTick(), motion.getEndTick());
      sb.append(str);
    }
    if (!motion.getStartColor().toString().equals(motion.getEndColor().toString())) {
      String str = String.format("%s changes from %s to %s from time t=%d to t=%d\n", id,
          motion.getStartColor().toString(),
          motion.getEndColor().toString(), motion.getStartTick(), motion.getEndTick());
      sb.append(str);
    }
    if (motion.getStartWidth() != motion.getEndWidth()) {
      String str = String.format("%s changes width from %d to %d from time t=%d to t=%d\n", id,
          motion.getStartWidth(), motion.getEndWidth(), motion.getStartTick(),
          motion.getEndTick());
      sb.append(str);
    }
    if (motion.getStartHeight() != motion.getEndHeight()) {
      String str = String.format("%s changes height from %d to %d from time t=%d to t=%d\n", id,
          motion.getStartHeight(), motion.getEndHeight(), motion.getStartTick(),
          motion.getEndTick());
      sb.append(str);
    }
  }

  @Override
  public String getOvalAsString() {
    return "oval";
  }

  @Override
  public String getRectangleAsString() {
    return "rectangle";
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
        throw new IllegalArgumentException("Failure to write to file");
      }
    }
  }
}

package cs5004.animator;

import cs5004.animator.controller.AnimatorController;
import cs5004.animator.controller.IAnimatorController;
import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.IAnimatorModel;
import cs5004.animator.util.AnimationBuilder;
import cs5004.animator.util.AnimationReader;
import cs5004.animator.view.IInteractiveView;
import cs5004.animator.view.ITextualView;
import cs5004.animator.view.InteractiveView;
import cs5004.animator.view.SvgView;
import cs5004.animator.view.TextView;
import cs5004.animator.view.VisualView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The main class of the EasyAnimator. This class is responsible for running the program.
 */
public final class TheEasyAnimator {

  private static final JFrame frame = new JFrame("Animator");
  private static IAnimatorModel model = new AnimatorModel();

  /**
   * The main method of the EasyAnimator. This method is responsible for running the program.
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    String inputFileName = null;
    String viewType = null;
    String outputFileName = "default";
    ITextualView textView;
    VisualView visualView;
    IInteractiveView interactiveView;

    int integerTicksPerSecond = 1;

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          inputFileName = args[i + 1];
          i++;
          break;
        case "-view":
          viewType = args[i + 1];
          i++;
          break;
        case "-out":
          outputFileName = args[i + 1];
          i++;
          break;
        case "-speed":
          integerTicksPerSecond = Integer.parseInt(args[i + 1]);
          i++;
          break;
        default:
          JOptionPane.showMessageDialog(frame,
              "Command line argument \"" + args[i] + "\" is invalid.",
              "Error",
              JOptionPane.ERROR_MESSAGE);
          System.exit(-1);
      }
    }

    if (inputFileName == null || viewType == null) {
      JOptionPane.showMessageDialog(frame,
          "Input file and view type must be specified in the command line",
          "Error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }

    try {
      model = initializeAnimationModel(inputFileName);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(frame,
          e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }

    IAnimatorController controller;

    switch (viewType) {
      case "text":
        textView = new TextView(model, outputFileName);
        textView.write();
        break;
      case "svg":
        textView = new SvgView(model, outputFileName, integerTicksPerSecond);
        textView.write();
        break;
      case "visual":
        visualView = new VisualView(model);
        controller = new AnimatorController(model, visualView, integerTicksPerSecond);
        controller.start();
        break;
      case "edit":
        interactiveView = new InteractiveView(model, integerTicksPerSecond);
        controller = new AnimatorController(model, interactiveView, integerTicksPerSecond,
            true);
        controller.start();
        break;
      default:
        JOptionPane.showMessageDialog(frame,
            "Invalid View Type.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
  }

  /**
   * Returns a new animation model based on the input file path.
   *
   * @param inputFilePath the file name of the input file path
   * @return an AnimationOperations model
   */
  public static IAnimatorModel initializeAnimationModel(String inputFilePath) {
    Readable in;
    try {
      in = new FileReader(inputFilePath);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    AnimationBuilder<IAnimatorModel> builder = new AnimatorModel.Builder();
    AnimationReader.parseFile(in, builder);
    return builder.build();
  }
}

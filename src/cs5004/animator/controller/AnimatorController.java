package cs5004.animator.controller;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.IAnimatedShape;
import cs5004.animator.model.IAnimatorModel;
import cs5004.animator.util.AnimationBuilder;
import cs5004.animator.util.AnimationReader;
import cs5004.animator.view.IInteractiveView;
import cs5004.animator.view.ITextualView;
import cs5004.animator.view.IVisualView;
import cs5004.animator.view.SvgView;
import cs5004.animator.view.TextView;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A class implementing the AnimatorController interface. This class is responsible for
 * controlling the animation. This class is responsible for reading the input file, creating the
 * model, and creating the view. It also handles the user input and the animation. It also
 * stores the progress of the animation.
 */
public class AnimatorController implements IAnimatorController {

  private final IAnimatorModel model;
  private final IVisualView view;
  private final JFrame messageFrame = new JFrame("Message");

  // Represent the state of the animation
  private int tick = 1;
  private boolean isPlaying = false;
  private boolean hasEnded = false;
  private boolean enableLooping = true;
  private boolean enableEditing = false;

  // speed of the animation in ticks per second
  private final int defaultSpeed = 1;
  private int speed;
  private Timer t = new Timer();

  /**
   * Constructs a new AnimatorController with the given model, view and speed of the animation.
   *
   * @param model the model for the animation
   * @param view  the visual view
   * @param speed the speed of the animation in ticks per second
   * @throws IllegalArgumentException if the model or view is null, or if the speed is less than 1
   */
  public AnimatorController(IAnimatorModel model, IVisualView view, int speed)
      throws IllegalArgumentException {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Null argument");
    }
    this.model = model;
    this.view = view;
    this.speed = defaultSpeed;
    if (speed > 0) {
      this.speed = speed;
    } else {
      throw new IllegalArgumentException("Speed must be positive");
    }
  }

  /**
   * Constructs a new AnimatorController that also takes in a boolean to determine if the animation
   * is played in an interactive mode.
   *
   * @param model         the model for the animation
   * @param view          the visual view
   * @param speed         the speed of the animation in ticks per second
   * @param enableEditing true if the animation is played in an interactive mode, false otherwise
   */
  public AnimatorController(IAnimatorModel model, IInteractiveView view, int speed,
      boolean enableEditing) {
    this(model, view, speed);
    this.enableEditing = enableEditing;
    if (this.enableEditing) {
      view.setListener(this);
    }
  }

  @Override
  public void start() {
    this.view.display();
    view.display();
    model.applyTick(1);
    if (!enableEditing) {
      play();
    }
  }

  /**
   * Plays the animation by setting a timer.
   */
  private void play() {
    if (!isPlaying) {
      this.t = new Timer();
      setTimerTask();
      isPlaying = true;
    }
  }

  @Override
  public void pause() {
    if (isPlaying) {
      t.cancel();
      isPlaying = false;
    }
  }

  /**
   * Enable / disable looping of the animation.
   */
  private void toggleLooping() {
    if (!this.enableLooping) {
      hasEnded = model.getTick() >= model.getLastTick();
    }
    this.enableLooping = !this.enableLooping;
    pause();
    play();
  }

  /**
   * Set the timer task for the animation.
   */
  private void setTimerTask() {
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        model.applyTick(tick);
        if (view instanceof IInteractiveView && enableEditing) {
          IInteractiveView v = (IInteractiveView) view;
          v.refreshAnimation();
        } else {
          view.refresh();
        }
        tick += speed < 0 ? -1 : 1;
        validateTick();
      }
    }, 0, 1000 / Math.abs(speed));
  }

  @Override
  public void setTick(int tick) {
    this.tick = tick;
    validateTick();
    model.applyTick(tick);
    view.refresh();
  }

  /**
   * Validate the tick increment to ensure it is within the bounds of the animation. When reaching
   * the end of the animation, the animation will loop back to the beginning by resetting the tick
   * if looping is enabled. Otherwise, the animation will be paused.
   */
  private void validateTick() {
    if (enableLooping) {
      tick = ((tick - 1) % model.getLastTick()) + 1;
    } else if (tick > model.getLastTick() || tick < 1) {
      pause();
      tick = tick > model.getLastTick() ? tick - 1 : tick + 1;
      isPlaying = false;
      hasEnded = true;
    }
  }

  @Override
  public int getSpeed() {
    return this.speed;
  }

  @Override
  public void setSpeed(String speed) {
    try {
      this.speed = Integer.parseInt(speed);
    } catch (NumberFormatException e) {
      displayError("Speed must be an integer.");
    }
    this.pause();
    this.play();
  }

  @Override
  public void displayError(String errorMessage) {
    JOptionPane.showMessageDialog(messageFrame,
        errorMessage,
        "Error",
        JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void displaySuccess(String message) {
    boolean suppressSuccessMessages = false;
    if (!suppressSuccessMessages) {
      JOptionPane.showMessageDialog(messageFrame,
          message,
          "Success",
          JOptionPane.PLAIN_MESSAGE);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    IInteractiveView view = (IInteractiveView) this.view;
    if (e == null || e.getActionCommand() == null) {
      throw new IllegalArgumentException("Action event / Action command cannot be null.");
    }
    switch (e.getActionCommand()) {
      case "Play":
        if (hasEnded && !enableLooping) {
          displayError("Animation has ended. Click \"Reset\" to play again");
        }
        play();
        break;
      case "Pause":
        pause();
        break;
      case "Reset":
        tick = 1;
        play();
        break;
      case "Enable Looping":
        if (!enableLooping) {
          toggleLooping();
          displaySuccess("Looping is enabled");
        } else {
          displayError("Looping is already enabled");
        }
        break;
      case "Disable Looping":
        if (enableLooping) {
          toggleLooping();
          displaySuccess("Looping is disabled");
        } else {
          displayError("Looping is already disabled");
        }
        break;
      case "Load":
        String loadFile = view.getLoadFilePath();
        Readable in;
        try {
          in = new BufferedReader(new FileReader(loadFile));
          displaySuccess("'" + loadFile + "' loaded");
        } catch (FileNotFoundException z) {
          displayError("Fail to load this file. Please provide a valid file path.");
          throw new IllegalArgumentException(z.getMessage());
        }
        AnimationBuilder<IAnimatorModel> builder = new AnimatorModel.Builder();
        try {
          AnimationReader.parseFile(in, builder);
          IAnimatorModel newModel = builder.build();
          this.model.clear();
          for (IAnimatedShape animatedShape : newModel.getAnimatedShapes()) {
            this.model.addAnimatedShape(animatedShape);
          }
        } catch (Exception x) {
          displayError("Fail to load this file. Please provide a valid file type.");
        }
        try {
          Thread.sleep(100);
        } catch (InterruptedException ex) {
          throw new RuntimeException(ex);
        }
        this.view.refresh();
        this.view.display();
        tick = 1;
        setTimerTask();
        break;
      case "Save":
        String saveFile = view.getSaveFilePath();
        if (saveFile.length() < 3) {
          displayError("Please provide a valid file path.");
        }
        String fileType = "";
        try {
          fileType = saveFile.substring(saveFile.length() - 3).toLowerCase();
        } catch (IndexOutOfBoundsException e1) {
          displayError("Please provide a valid file path.");
        }
        ITextualView textView;
        switch (fileType) {
          case "svg":
            textView = new SvgView(this.model, saveFile, speed);
            textView.write();
            displaySuccess("'" + saveFile + "' saved");
            break;
          case "txt":
            textView = new TextView(this.model, saveFile);
            textView.write();
            displaySuccess("'" + saveFile + "' saved");
            break;
          default:
            displayError("Invalid file type. Only allow .svg or .txt");
            break;
        }
        break;
      default:
        break;
    }
    view.refresh();
  }
}

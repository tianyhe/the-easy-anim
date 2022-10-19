package cs5004.animator.view;

import cs5004.animator.controller.IAnimatorController;
import cs5004.animator.model.IAnimatorModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Represents an interactive view for an animation. This view takes in user input and updates the
 * animation accordingly.
 */
public class InteractiveView extends JFrame implements IInteractiveView, ActionListener {

  private final IAnimatorModel model;

  // Components of the GUI
  private final VisualViewPanel visualViewPanel;
  private final JPanel buttonPanel;
  private final JSlider progressBar;

  private final Map<Buttons, JButton> buttonsMap;
  private final Map<TextFields, JTextField> textFieldsMap;
  private final List<Buttons> controlButtons = new ArrayList<>(
      Arrays.asList(Buttons.PLAY, Buttons.PAUSE, Buttons.RESET, Buttons.ENABLE_LOOPING,
          Buttons.DISABLE_LOOPING));

  /**
   * Constructs an interactive view for an animation from a model.
   *
   * @param m the model for the animation
   * @param s the speed of the animation
   * @throws IllegalArgumentException if the model is null
   */
  public InteractiveView(IAnimatorModel m, int s) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (s < 0) {
      throw new IllegalArgumentException("Speed cannot be negative");
    }
    this.model = m;

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create the buttons map
    this.buttonsMap = new HashMap<>();
    this.addButtonsToMap(Buttons.values());

    // Create the text fields map
    this.textFieldsMap = new HashMap<>();
    this.addTextFieldsToMap(TextFields.values());

    // Adding the view panel
    VisualViewPanel visualViewPanel = new VisualViewPanel(model);
    visualViewPanel.setPreferredSize(new Dimension(model.getCanvasWidth(),
        model.getCanvasHeight()));
    visualViewPanel.setLocation(model.getCanvasStartingX(), model.getCanvasStartingY());
    this.add(new JScrollPane(visualViewPanel), BorderLayout.CENTER);
    this.visualViewPanel = visualViewPanel;

    // Adding the progress bar
    this.progressBar = new JSlider(1, model.getLastTick(), 1);
    this.setProgressBar();

    // Adding the button panel to the bottom of the view panel
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 1));
    addPlayButtonPanel();
    addTextFieldButtonPanel();
    this.add(buttonPanel, BorderLayout.SOUTH);

    this.pack();
  }

  /**
   * Creates and loads all the buttons used in this view into a map where the key is the type of the
   * button and the value is the JButton.
   *
   * @param buttons the names of buttons
   */
  private void addButtonsToMap(Buttons... buttons) {
    for (Buttons name : buttons) {
      JButton button = new JButton(name.toString());
      buttonsMap.put(name, button);
    }
  }

  /**
   * Creates and loads all the text fields used in this view into a map where the key is the type of
   * text field and the value is the JTextField.
   *
   * @param textFieldNames all button names
   */
  private void addTextFieldsToMap(TextFields... textFieldNames) {
    for (TextFields name : textFieldNames) {
      JTextField textField = new JTextField(10);
      textFieldsMap.put(name, textField);
    }
  }

  /**
   * Sets the progress bar to the correct tick.
   */
  private void setProgressBar() {
    int lastTick = model.getLastTick();
    this.progressBar.setMaximum(lastTick);
    this.progressBar.setMajorTickSpacing((lastTick - 1) / 4);
    this.progressBar.setPaintTicks(true);
    Hashtable position = new Hashtable();
    position.put(1, new JLabel("0%"));  // start label
    position.put((lastTick - 1) / 2, new JLabel("50%")); // middle label
    position.put(lastTick, new JLabel("100%")); // end label
    this.progressBar.setLabelTable(position);
    this.progressBar.setPaintLabels(true);
  }

  /**
   * Adds the play button panel with the animation control buttons.
   */
  private void addPlayButtonPanel() {
    JPanel playButtonPanel = new JPanel();
    playButtonPanel.setLayout(new FlowLayout());
    JTextArea progressBarLabel = new JTextArea("Progress");
    progressBarLabel.setEditable(false);
    playButtonPanel.add(progressBarLabel);
    playButtonPanel.add(progressBar);
    for (Buttons name : controlButtons) {
      playButtonPanel.add(this.buttonsMap.get(name));
    }
    buttonPanel.add(playButtonPanel);
  }

  /**
   * Adds the Text Field button panel with the speed and save/load buttons.
   */
  private void addTextFieldButtonPanel() {
    JPanel textFieldButtonPanel = new JPanel();
    textFieldButtonPanel.setLayout(new FlowLayout());
    textFieldButtonPanel.add(buttonsMap.get(Buttons.SET_SPEED));
    textFieldButtonPanel.add(textFieldsMap.get(TextFields.SET_SPEED));
    textFieldButtonPanel.add(buttonsMap.get(Buttons.LOAD));
    textFieldButtonPanel.add(textFieldsMap.get(TextFields.LOAD_FILE));
    textFieldButtonPanel.add(buttonsMap.get(Buttons.SAVE));
    textFieldButtonPanel.add(textFieldsMap.get(TextFields.SAVE_FILE));
    buttonPanel.add(textFieldButtonPanel);
  }

  @Override
  public void setListener(IAnimatorController listener) {
    textFieldsMap.get(TextFields.SET_SPEED).setText(Integer.toString(listener.getSpeed()));
    // The progress bar listener
    this.progressBar.addChangeListener((e) -> {
      listener.setTick(((JSlider) e.getSource()).getValue());
    });
    // All the control buttons listener
    for (Buttons b : controlButtons) {
      this.buttonsMap.get(b).addActionListener(listener);
    }
    // The SET_SPEED button listener
    this.buttonsMap.get(Buttons.SET_SPEED).addActionListener((e) -> {
      listener.setSpeed(textFieldsMap.get(TextFields.SET_SPEED).getText());
    });
    // The SAVE and LOAD button listeners
    this.buttonsMap.get(Buttons.SAVE).addActionListener(listener);
    this.buttonsMap.get(Buttons.LOAD).addActionListener(listener);
  }

  @Override
  public String getLoadFilePath() {
    return this.textFieldsMap.get(TextFields.LOAD_FILE).getText();
  }

  @Override
  public String getSaveFilePath() {
    return this.textFieldsMap.get(TextFields.SAVE_FILE).getText();
  }

  @Override
  public void refreshAnimation() {
    this.setProgressBar();
    this.progressBar.setValue(model.getTick());
    this.progressBar.repaint();
    this.visualViewPanel.repaint();
  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    refreshAnimation();
    this.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e == null) {
      throw new IllegalArgumentException("No Action Event");
    }
  }
}

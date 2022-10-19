package cs5004.animator.view;

import cs5004.animator.model.IAnimatorModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Represents a simple graphical view for an animation. The graphical view can display the animation
 * using Java Swing as the front end. This view is not interactive. The animation will be
 * automatically looped after it finishes and user can quit the program by both clicking the quit
 * button or the close button.
 */
public class VisualView extends JFrame implements IVisualView {

  /**
   * Constructs a simple visual view for an animation.
   *
   * @param model the model for the animation
   */
  public VisualView(IAnimatorModel model) {
    super();
    this.setTitle("The Easy Animator");
    this.setSize(new Dimension(model.getCanvasWidth(), model.getCanvasHeight()));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    VisualViewPanel animationPanel = new VisualViewPanel(model);
    animationPanel.setPreferredSize(new Dimension(model.getCanvasWidth(),
        model.getCanvasHeight()));
    animationPanel.setLocation(model.getCanvasStartingX(), model.getCanvasStartingY());
    this.add(new JScrollPane(animationPanel), null);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);
    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> System.exit(0));
    buttonPanel.add(quitButton);

    this.pack();
  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

}

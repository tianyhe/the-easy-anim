package cs5004.animator.controller;


import static org.junit.Assert.assertEquals;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.IAnimatorModel;
import cs5004.animator.view.IInteractiveView;
import cs5004.animator.view.InteractiveView;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the AnimatorController class. //TODO: Using testLog and other output
 * sources to check 1. If the controller correctly handles the input from the user and passes it to
 * the model. 2. If the controller properly mediates the interaction between the model and the
 * view.
 */
public class AnimatorControllerTest {

  private IAnimatorModel model;
  private IInteractiveView view;
  private int speed;

  /**
   * Sets up the model, view, and controller for the test.
   */
  @Before
  public void setup() {
    this.speed = 10;
    this.model = new AnimatorModel();
    this.view = new InteractiveView(model, speed);
    IAnimatorController controller = new AnimatorController(model, view, speed);
  }

  /**
   * Tests to check if the controller throws exception when the model is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new AnimatorController(null, view, speed);
  }

  /**
   * Tests to check if the controller throws exception when the view is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new AnimatorController(model, null, speed);
  }

  /**
   * Tests to check if the controller throws exception when the speed is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSpeed() {
    new AnimatorController(model, view, -1);
  }

  /**
   * Tests to check if the controller throws exception when the speed is 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testZeroSpeed() {
    new AnimatorController(model, view, 0);
  }

  /**
   * Tests to check if the set speed method works properly.
   */
  @Test
  public void testSetSpeed() {
    IAnimatorController controller = new AnimatorController(model, view, speed, true);
    controller.setSpeed("10");
    assertEquals(10, controller.getSpeed());
  }
}
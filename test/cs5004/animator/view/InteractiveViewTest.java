package cs5004.animator.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.IAnimatorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the InteractiveView class. Since the InteractiveView is a JFrame, it is
 * not possible to test it directly. Therefore, this class only test the basic constructor of the
 * InteractiveView class. The ActionListener is testing indirectly by playing the animation in edit
 * mode and using Action Performed to check if the Action Event is triggered and the correct action
 * is performed.
 */
public class InteractiveViewTest {

  IAnimatorModel model;
  int speed;

  /**
   * Sets up the model and speed for the test.
   */
  @Before
  public void setup() {
    this.model = new AnimatorModel();
    speed = 1;
  }

  /**
   * Tests to check if the constructor throws exception when the model is null.
   */
  @Test
  public void testNullModel() {
    try {
      new InteractiveView(null, speed);
    } catch (IllegalArgumentException e) {
      assertEquals("Model cannot be null", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * Tests to check if the constructor throws exception when the speed is negative.
   */
  @Test
  public void testNegativeTempo() {
    try {
      new InteractiveView(model, -1);
    } catch (IllegalArgumentException e) {
      assertEquals("Speed cannot be negative", e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

}
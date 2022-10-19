package cs5004.animator.view;

import static cs5004.animator.TheEasyAnimator.initializeAnimationModel;
import static org.junit.Assert.assertEquals;

import cs5004.animator.model.IAnimatorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the TextView class.
 */
public class TextViewTest {

  String inFilePath = "resources/smalldemo.txt";
  IAnimatorModel model;

  /**
   * Set up the model for the test from the input file.
   */
  @Before
  public void setUp() throws Exception {
    this.model = initializeAnimationModel(inFilePath);
  }

  /**
   * Testing the textual representation of the animation against the expected output.
   */
  @Test
  public void testSmallDemo() {
    ITextualView view = new TextView(model, "default");
    String expectedOutput = ""
        + "Create rgb(255,0,0) Rectangle R with corner at (200,200), width: 50 and height 100\n"
        + "Create rgb(0,0,255) Oval C with center at (440,70), radius 120 and 60\n"
        + "\n"
        + "R appears at time t=1 and disappears at time t=100\n"
        + "C appears at time t=6 and disappears at time t=100\n"
        + "\n"
        + "R moves from (200,200) to (300,300) from time t=10 to t=50\n"
        + "C moves from (440,70) to (440,250) from time t=20 to t=50\n"
        + "C moves from (440,250) to (440,370) from time t=50 to t=70\n"
        + "C changes from rgb(0,0,255) to rgb(0,170,85) from time t=50 to t=70\n"
        + "R changes width from 50 to 25 from time t=51 to t=70\n"
        + "R moves from (300,300) to (200,200) from time t=70 to t=100\n"
        + "C changes from rgb(0,170,85) to rgb(0,255,0) from time t=70 to t=80";
    String actualOutput = view.getText();
    assertEquals(expectedOutput, actualOutput);
  }
}
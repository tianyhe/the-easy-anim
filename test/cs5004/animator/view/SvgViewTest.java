package cs5004.animator.view;


import static cs5004.animator.TheEasyAnimator.initializeAnimationModel;
import static org.junit.Assert.assertEquals;

import cs5004.animator.model.IAnimatorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the SvgView class.
 */
public class SvgViewTest {

  String inFilePath = "resources/smalldemo.txt";
  IAnimatorModel model;

  /**
   * Sets up the model for the test.
   *
   * @throws Exception if the file is not found
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
    ITextualView view = new SvgView(this.model, "default", 40);
    String expectedSvgOutput = ""
        + "<svg viewbox=\"200 70 360 360\" version=\"1.1\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\""
        + " visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"250.0ms\" dur=\"1000.0ms\" attributeName=\"x\""
        + " from=\"200\" to=\"300\" fill=\"freeze\"/>\n"
        + "\t<animate attributeType=\"xml\" begin=\"250.0ms\" dur=\"1000.0ms\" attributeName=\"y\""
        + " from=\"200\" to=\"300\" fill=\"freeze\"/>\n"
        + "\t<animate attributeType=\"xml\" begin=\"1275.0ms\" dur=\"475.0ms\" attributeName=\""
        + "width\" from=\"50\" to=\"25\" fill=\"freeze\"/>\n"
        + "\t<animate attributeType=\"xml\" begin=\"1750.0ms\" dur=\"750.0ms\" attributeName=\"x\""
        + " from=\"300\" to=\"200\" fill=\"remove\"/>\n"
        + "\t<animate attributeType=\"xml\" begin=\"1750.0ms\" dur=\"750.0ms\" attributeName=\"y\""
        + " from=\"300\" to=\"200\" fill=\"remove\"/>\n"
        + "</rect>\n"
        + "<ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" fill=\"rgb(0,0,255)"
        + "\" visibility=\"hidden\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"25.0ms\" dur=\"125.0ms\" attributeName=\""
        + "visibility\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
        + "\t<animate attributeType=\"xml\" begin=\"500.0ms\" dur=\"750.0ms\" attributeName=\"cy\""
        + " from=\"100.0\" to=\"280.0\" fill=\"freeze\"/>\n"
        + "\t<animate attributeType=\"xml\" begin=\"1250.0ms\" dur=\"500.0ms\" attributeName=\""
        + "fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" fill=\"freeze\"/>\n"
        + "\t<animate attributeType=\"xml\" begin=\"1250.0ms\" dur=\"500.0ms\" attributeName=\""
        + "cy\" from=\"280.0\" to=\"400.0\" fill=\"freeze\"/>\n"
        + "\t<animate attributeType=\"xml\" begin=\"1750.0ms\" dur=\"250.0ms\" attributeName=\""
        + "fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\"/>\n"
        + "</ellipse>\n"
        + "</svg>";
    assertEquals(expectedSvgOutput, view.getText());
  }
}
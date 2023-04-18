/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
  /** Method to test keepOnlyBlue */
  public static void testKeepOnlyBlue()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.keepOnlyBlue();
    beach.explore();
  }
  
  /** Method to test negate */
  public static void testNegate()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.negate();
    beach.explore();
  }
  
  /** Method to test negate */
  public static void testGrayScale()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.grayScale();
    beach.explore();
  }
  
  /** Method to test fixUnderWater */
  public static void testFixUnderwater()
  {
    Picture water = new Picture("water.jpg");
    water.explore();
    water.fixUnderwater();
    water.explore();
  }
  
  /** Method to test createWatermark */
  public static void testCreateWatermark()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.createWatermark();
    beach.explore();
  }
  
  /** Method to test createWatermark */
  public static void testPixelate()
  {
    Picture swan = new Picture("swan.jpg");
    swan.explore();
    swan.pixelate(10);
    swan.explore();
  }
  
  /** Method to test blur */
  public static void testBlur() {
	  Picture beach = new Picture("beach.jpg");
	  beach.explore();
	  Picture newPic = beach.blur(11);
	  newPic.explore();
  }
  
  /** Method to test enhance */
  public static void testEnhance() {
	  Picture water = new Picture("water.jpg");
	  water.explore();
	  Picture newPic = water.enhance(11);
	  newPic.explore();
  }
  
  /** Method to test shift */
  public static void testShiftRight() {
	  Picture bike = new Picture("redMotorcycle.jpg");
	  bike.explore();
	  Picture newPic = bike.shiftRight(50);
	  newPic.explore();
  }

	/** Method to test stairstep */
  public static void testStairStep() {
	  Picture bike = new Picture("redMotorcycle.jpg");
	  bike.explore();
	  Picture newPic = bike.stairStep(10,10);
	  newPic.explore();
  }
  
  /** Method to test turn90 */
  public static void testTurn90() {
	  Picture bike = new Picture("redMotorcycle.jpg");
	  bike.explore();
	  Picture newPic = bike.turn90();
	  newPic.explore();
  }
  
  /** Method to test zoomIn */
  public static void testZoomIn() {
	  Picture bike = new Picture("redMotorcycle.jpg");
	  bike.explore();
	  Picture newPic = bike.zoomUpperLeft();
	  newPic.explore();
  }
  
  /** Method to test turn90 */
  public static void testTileMirror() {
	  Picture bike = new Picture("redMotorcycle.jpg");
	  bike.explore();
	  Picture newPic = bike.tileMirror();
	  newPic.explore();
  }
  
  public static void testGreenScreen(){
	  Picture bike = new Picture("redMotorcycle.jpg");
	  bike.greenScreen().explore();
  }
  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
  
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }

  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    testZeroBlue();
   // testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
   // testGrayScale();
   // testFixUnderwater();
   // testCreateWatermark();
   //testPixelate();
   //testBlur();
   //testEnhance();
   //testShiftRight();
   //testStairStep();
   //testTurn90();
   //testTileMirror();
   //testEdgeDetection();
   testGreenScreen();
   //testZoomIn();
    //testMirrorVertical();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetection2();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
  }
}

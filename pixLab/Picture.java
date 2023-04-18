import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  /** Method that keeps only blue */
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setGreen(0);
        pixelObj.setRed(0);
      }
    }
  }
  /** Method that removes all color */
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setBlue(255 - pixelObj.getBlue());
        pixelObj.setRed(255 - pixelObj.getRed());
      }
    }
  }
  
  /** Method that makes picture black and white */
  public void grayScale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setGreen((int)pixelObj.getAverage());
        pixelObj.setBlue((int)pixelObj.getAverage());
        pixelObj.setRed((int)pixelObj.getAverage());
      }
    }
  }
  
  /** Method that inhances blue */
  public void fixUnderwater()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		  if(pixelObj.getRed() <= 20 && !(pixelObj.getGreen() <= 155)) 
			pixelObj.setBlue(pixelObj.getBlue() + 50);
      }
    }
  }
  
  /** Method that creates a watermark */
  public void createWatermark()
  {
    Pixel[][] pixels = this.getPixels2D();
    
    for(int i = 0; i < pixels.length; i++){
		for(int j = 0; j < pixels[0].length; j++){
			if((i/40 + j/40) % 2 == 0){
				pixels[i][j].setRed((pixels[i][j].getRed() + 25));
				pixels[i][j].setGreen((pixels[i][j].getGreen() + 25));
				pixels[i][j].setBlue((pixels[i][j].getBlue() + 25));
			}
			
		}
	}
  }
  
  /** To pixelate by dividing area into size x size
   *  @param size    Side length of square area to pixelate. 
   */ 
  public void pixelate(int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    int square = size * size;
    for(int i = 0; i < pixels.length; i+=size){
		for(int j = 0; j < pixels[0].length; j+= size){
			double green = 0.0;
			double blue = 0.0;
			double red = 0.0;
			for(int a = i; a < size + i; a++){
				for(int b = j; b < size + j; b++){
					green += pixels[a][b].getGreen();
					red += pixels[a][b].getRed();
					blue += pixels[a][b].getBlue();
				}
			}
			for(int c = i; c < size + i; c++){
				for(int d = j; d < size + j; d++){
					pixels[c][d].setRed((int)(red/square));
					pixels[c][d].setGreen((int)(green/square));
					pixels[c][d].setBlue((int)(blue/square));
				}
			}
		}
	}
 }
  /** Method that blurs the picture 
   *  @param size    Blur size, greater is more blur 
   *  @return   Blurred picture 
   */ 
   //identify corner, (0,0), (width-1,0),(0,height-1), (width-1,height-1)
   //edge whenever width == 0, height == 0
  public Picture blur(int size) 
  { 
    Pixel[][] pixels = this.getPixels2D(); 
    Picture result = new Picture(pixels.length, pixels[0].length); 
    Pixel[][] resultPixels = result.getPixels2D(); 
    for(int i = 0; i < pixels.length; i++){
		for(int j = 0; j < pixels[0].length; j++){
			resultPixels[i][j].setColor(findAveColor(i, j, size, pixels, false));
	}
  }
  return result;
}
/** Method that enhances a picture by getting average Color around
   *  a pixel then applies the following formula: 
   * 
   *    pixelColor <- 2 * currentValue - averageValue 
   * 
   *  size is the area to sample for blur. 
   * 
   *  @param size    Larger means more area to average around pixel
   *                   and longer compute time. 
   *  @return               enhanced picture 
   */
   public Picture enhance(int size)
   { 
	   Pixel[][] pixels = this.getPixels2D(); 
	   Picture result = new Picture(pixels.length, pixels[0].length); 
	   Pixel[][] resultPixels = result.getPixels2D(); 
	   for(int i = 0; i < pixels.length; i++){
		   for(int j = 0; j < pixels[0].length; j++){ 
			   resultPixels[i][j].setColor(findAveColor(i, j, size, pixels, true));
		   }
	   }
	   return result;
   }
 /**
   * gets the aveage color of the selected sqaure, by adding all the pixels	
   * close to it and finding its average
   * @param		row		starting row
   * @param		col		starting column
   * @param		size	size of the sqaure
   * @param		pixels	2D array of pixels
   */
  public Color findAveColor(int row, int col, int size, Pixel[][] pixels, boolean enhance) {
	  int redAvg = 0; int greenAvg = 0; int blueAvg = 0; int total = 0;
	  for (int i = row - (size/2); i <= row + (size/2); i++) {
		  if ( i > -1 && i < pixels.length) {
			  for (int j = col - (size/2); j <=  col + (size/2); j++) {
				  if (j > -1 && j < pixels[0].length) {
					  redAvg += pixels[i][j].getRed();
					  blueAvg += pixels[i][j].getBlue();
					  greenAvg += pixels[i][j].getGreen();
					  total++;
				  }
			  }
			}
	  }
	  if(enhance){
		  redAvg = 2 * pixels[row][col].getRed() - (redAvg/total);
		  if(redAvg > 255) redAvg = 255;
		  else if(redAvg < 0) redAvg = 0;
		  
		  greenAvg = 2 * pixels[row][col].getGreen() - (greenAvg/total);
		  if(greenAvg > 255) greenAvg = 255;
		  else if(greenAvg < 0) greenAvg = 0;
		  
		  blueAvg = 2 * pixels[row][col].getBlue() - (blueAvg/total);
		  if(blueAvg > 255) blueAvg = 255;
		  else if(blueAvg < 0) blueAvg = 0;
		  
		  return new Color (redAvg, greenAvg, blueAvg);
	  }
	  return new Color ((int)redAvg/total, (int)greenAvg/total, (int)blueAvg/total);
  }
   /** Method that shifts the pixels to the right
   *  @param percent    amount moved to the right
   *  @return   Shifted picture 
   */ 
  public Picture shiftRight(int percent){
	  Pixel[][] pixels = this.getPixels2D(); 
	  Picture result = new Picture(pixels.length, pixels[0].length); 
	  Pixel[][] resultPixels = result.getPixels2D(); 
	  int amtShifted = (int)((percent * getWidth())/100);
	  for(int i = 0; i < pixels.length; i++){
		   for(int j = 0; j < pixels[0].length; j++){ 
			   resultPixels[i][(j+amtShifted) % getWidth()].setColor(pixels[i][j].getColor());
		   }
	   }
	   return result;
	  
  }
  
  /** Method that shifts the pixels to the right and down
   *  @param shiftCount    amount moved to the right
   *  @param steps 		amount of steps 
   *  @return   Shifted picture 
   */ 
  public Picture stairStep(int shiftCount, int steps){
	  Pixel[][] pixels = this.getPixels2D(); 
	  Picture result = new Picture(pixels.length, pixels[0].length); 
	  Pixel[][] resultPixels = result.getPixels2D();
	  int step = getHeight()/steps;
	  for(int i = 0; i < pixels.length; i++){
		   for(int j = 0; j < pixels[0].length; j++){ 
			   int counter = j + shiftCount * (i / (step));
			   resultPixels[i][counter % getWidth()].setColor(pixels[i][j].getColor());
		   }
	   }
	   return result;
	  
  }
  
  /** Method that shifts the pixels 90 degrees
   *  @return   Shifted picture 
   */ 
  public Picture turn90(){
	  Pixel[][] pixels = this.getPixels2D(); 
	  Picture result = new Picture(pixels[0].length, pixels.length); 
	  Pixel[][] resultPixels = result.getPixels2D();
	  for(int i = 0; i < pixels.length; i++){
		   for(int j = 0; j < pixels[0].length; j++){ 
			   resultPixels[j][i].setColor(pixels[pixels.length - 1 - i][j].getColor());
			   //System.out.println("row:"+i + " col:"+j);
		   }
	   }
	   return result;
	  
  }
  /**
   * zooms to the top left of the picture
   */
  public Picture zoomUpperLeft(){
	  Pixel[][] pixels = this.getPixels2D(); 
	  Picture result = new Picture(pixels.length, pixels[0].length); 
	  Pixel[][] resultPixels = result.getPixels2D();
	  int row = 0; int col = 0;
	  for(int i = 0; i < pixels.length; i++){
		   for(int j = 0; j < pixels[0].length; j++){ 
			   resultPixels[i][j].setColor(pixels[i/2][j/2].getColor());
		   }
	   }
	   return result;
	  
  }
  /**
   * 4 images reversed and flipped
   */
  public Picture tileMirror(){
	  Pixel[][] pixels = this.getPixels2D(); 
	  Picture result = new Picture(pixels.length, pixels[0].length); 
	  Pixel[][] resultPixels = result.getPixels2D();
	  
	  for(int i = 0; i < pixels.length; i+= 2){
		   for(int j = 0; j < pixels[0].length; j+= 2){ 
			   Color pix = avgColor(i,j, pixels);
			   
			   resultPixels[i/2][j/2].setColor(pix);
			   resultPixels[i/2][getWidth() - 1 -j/2].setColor(pix);//mirror right
			   resultPixels[getHeight() - 1 - i/2][j/2].setColor(pix);//mirrir below
			   resultPixels[getHeight() - 1 - i/2][getWidth() - 1 -j/2].setColor(pix);
			   
		   }
	   }
	   return result;
  }
  
  public Color avgColor(int i, int j, Pixel[][] pixels){
	  double redAvg = 0.0; double greenAvg = 0.0; double blueAvg = 0.0;
	  int total = 0;
	  for(int a = i; a < i + 2; a ++){
		  for(int b = j; b < j + 2; b++){
			  redAvg += pixels[a][b].getRed();
			  greenAvg += pixels[a][b].getGreen();
			  blueAvg += pixels[a][b].getBlue();
			  total ++;
		  }
	  }
	  return new Color ((int)redAvg/total, (int)greenAvg/total, (int)blueAvg/total);
  }
  public Picture edgeDetectionBelow(int threshold){
	Pixel topPixel = null;
    Pixel bottomPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color bottomColor = null;
    for (int i = 0; i < pixels[0].length; i++)
    {
      for (int j = 0; j < pixels.length-1; j++)
      {
        topPixel = pixels[i][j];
        bottomPixel = pixels[i + 1][j];
        bottomColor = bottomPixel.getColor();
        if (topPixel.colorDistance(bottomColor) > threshold)
          topPixel.setColor(Color.BLACK);
        else
          topPixel.setColor(Color.WHITE);
      }
    }
    return this;
  }
  
  public Picture greenScreen(){
	  Picture result = new Picture("IndoorHouseLibraryBackground.jpg"); 
	  Pixel[][] resultPixels = result.getPixels2D(); 
	  
	  Picture cat = new Picture("kitten1GreenScreen.jpg"); 
	  Pixel[][] catPixels = cat.scale(0.765,0.765).getPixels2D();
	   
	  Picture mouse = new Picture("mouse1GreenScreen.jpg"); 
	  Pixel[][] mousePixels = mouse.scale(0.35,0.35).getPixels2D(); 
	  
	  for(int i = 0; i < catPixels.length; i++){
		  for(int j = 0; j < catPixels[0].length; j++){
			  Pixel pix = catPixels[i][j];
			  if(((Math.abs(pix.getGreen())-190) >= 45) || ((Math.abs(pix.getRed())-40) >= 45) ||
			  ((Math.abs(pix.getBlue())-40) >= 45)) resultPixels[i+230][j+250].setColor(pix.getColor());
		  }
	}
	
	for(int i = 0; i < mousePixels.length; i++){
		  for(int j = 0; j < mousePixels[0].length; j++){
			  Pixel pix = mousePixels[i][j];
			  if(((Math.abs(pix.getGreen())-204) >= 45) || ((Math.abs(pix.getRed())-48) >= 45) ||
			  ((Math.abs(pix.getBlue())-48) >= 45)) resultPixels[i+500][j+400].setColor(pix.getColor());
		  }
	}
	return result;
		  
  }
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ball {
  private int x; //x position
  private int y; //y position
  private int centreX, centreY;
  private int w; //width of ball
  private int h; //height of ball
  
  private int r, g, b;
  private Color colour;
  
  private static int speed = 10; // global default speed
  private static int maxSpeed = 23; // global default max speed
  
  private double moveX, moveY; //current speed in x and y direction
  private double accel = 10 ; //current acceleration
  
  private static int paneWidth;
  private static int paneHeight;
  
  public Ball() {
    //setting a random size and position for the new ball
    int smallDim = (paneWidth < paneHeight) ? paneWidth : paneHeight;
    
    w = randomRange(smallDim/20, smallDim/8);
    h = w;
    x = randomRange(0, paneWidth-w);
    y = randomRange(0, paneHeight-h);
    centreX = (paneWidth-w)/2;
    centreY = (paneHeight-h)/2;
    
    //giving the ball a random speed and direction
    resetSpeed();
    
    //generating a random colour
    r = randomRange(0, 255);
    g = randomRange(0, 255);
    b = randomRange(0, 255);
    colour = new Color(r,g,b);
  }
  
  /** draws the ball on the screen*/
  public void draw(Graphics pen, boolean changingColour, boolean stretchingBalls) {
    if (changingColour) {
      //generating hopefully cool looking colour generation
      int drawR = (int) (getSpeed() / maxSpeed * 255.0);
      int drawG = (int) ((double) Math.abs(x) / (paneWidth) * 255.0);
      int drawB = (int) ((double) Math.abs(y) / (paneHeight) * 255.0);
      
      if (drawR > 255) drawR = 255;
      if (drawG > 255) drawG = 255;
      if (drawB > 255) drawB = 255;
      
      //setting the pen to draw in this colour
      Color drawCol = new Color(r,drawG,drawB);
      
      pen.setColor(drawCol);
    } else pen.setColor(colour);
    
    if (stretchingBalls) {
      //stretching the ball
      int drawWidth = (w*w + (int) getSpeed())/w;
      
      //rotating the pen according to the ball's direction
      Graphics2D g2d = (Graphics2D) pen;
      AffineTransform old = g2d.getTransform();
      g2d.rotate(Math.tan(h/w));
      
      //drawing the ball
      pen.fillOval(x,y,drawWidth,h);
      
      //reseting the pen
      g2d.setTransform(old);
      
    } else pen.fillOval(x,y,w,h);
  }
  
  public void move(boolean gravity) {
    //if the black hole exists, accelerate the balls towards the middle of the screen
    if (gravity) {
      double accelX = accel * (((double)centreX-x));
      double accelY = accel * (((double)centreY-y));
      moveX += accelX/(w*h);
      moveY += accelY/(w*h);
      
      //otherwise just do regular edge colision duty to keep the balls in frame
      if (x < -w) resetPosition();
      else if (x > paneWidth) resetPosition();
      if (y < -h) resetPosition();
      else if (y > paneHeight) resetPosition();
    } else {
      //otherwise just do regular edge colision duty to keep the balls in frame
      if (x < 0) moveX = Math.abs(moveX);
      else if (x > paneWidth - w) moveX = -Math.abs(moveX);
      if (y < 0) moveY = Math.abs(moveY);
      else if (y > paneHeight - h) moveY = -Math.abs(moveY);
    }
    
    
    //move the ball in the new direction
    x += moveX;
    y += moveY;
  }

  private void resetPosition() {
    x = (paneWidth-w)/2;
    y = (paneHeight-h)/2;
  }

  public void resetSpeed() {    
    //giving the ball a random speed and direction
    do moveX = (double) randomRange(-speed, speed); while (moveX == 0.0); 
    do moveY = (double) randomRange(-speed, speed); while (moveY == 0.0);
  }
  
  /** generates a random number between the supplied minimum and maximum numbers (inclusive)
    @param min the lowest number expected
    @param max the highest number expected
    @result a random number between the supplied numbers (inclusive) */
  private int randomRange(int min, int max) {
    int range = max - min + 1; //add one to include the max as a possible result
    double seed = Math.random();
    int result = (int) (seed * range + min);
    
    return result;
  }
  
  public static void setPaneSize(int w, int h) {
    paneWidth = w;
    paneHeight = h;
  }
  
  private double getSpeed() {
    return Math.sqrt((moveX*moveX) + (moveY*moveY));
  }
  
  public void stop() {
    moveX = 0;
    moveY = 0;
  }
}

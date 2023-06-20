import javax.swing.*;

public class BallsApp {
  public static void main(String[]args){
    JFrame frame = new JFrame("Balls"); 
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    BallPanel balls = new BallPanel();
    frame.getContentPane().add (balls);
    frame.pack();
    frame.setVisible(true);
  }
}
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame(); 							//Create a frame object
		frame.setBounds(100, 100, 600, 600);					//Sets the size and placement of the component when it is displayed to the screen (x, y, width, height)
		frame.setTitle("Breakout Ball");						//Names the window
		frame.setResizable(false);								//Makes it so the size cannot be changed
		frame.setVisible(true);									//Sets visibility
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Closes window when "X" is clicked
		
		
		Gameplay gameplay = new Gameplay();						//Creates a new Gameplay object which displays the game as it is a subclass of JPanel
		frame.add(gameplay);									//Adding the panel to the frame
		
		//I don't know what this really does but it makes the cursor invisible when hovering over the window
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		frame.getContentPane().setCursor(blankCursor);
	}

}

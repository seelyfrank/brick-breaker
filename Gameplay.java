import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private boolean play = false;
	private int score = 0;
	private int totalBricks;
	private Timer timer;
	private int delay = 4;
	private Bricks grid;
	int speedVariable = 0;
	
	private int playerX = 450;	//Only X is needed as the paddle cannot move in the Y direction
	private int ballposX = (int)(Math.random()*500 + 50); 
	private int ballposY = (int)(Math.random()*100 + 400);
	private int ballXdir = -3;	//Speed and direction of the ball in the X direction
	private int ballYdir = -3;	//Speed and direction of the ball in the Y direction
	
	
	public Gameplay() {			//Gameplay constructor
		addKeyListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		grid = new Bricks(5, 3);
		totalBricks = grid.getNumCols() * grid.getNumRows();
	}
	
	public void paint(Graphics g) {
				
		//super call
		super.paint(g);

		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 600, 600);
	
		//borders
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,  0,  5,  600);
		g.fillRect(0,  0,  600,  5);
		g.fillRect(579,  0,  8,  600);
		
		
		//paddle
		g.setColor(Color.BLUE);
		g.fillRect(playerX, 550, 75, 10);
		
		//ball
		g.setColor(Color.RED);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//bricks
		g.setColor(Color.white);
		grid.draw(g);	//custom draw method in the bricks class
		
		//in game status
		g.setColor(Color.WHITE);
		g.setFont(new Font("monospaced", Font.BOLD, 17));
		g.drawString("Bricks Left: " + totalBricks, 10, 450);
		g.drawString("Score: " + score, 10, 470);
		
		//text on screen		
		if (!play) {
			g.setColor(Color.BLACK);
			g.fillRect(0,  0,  600,  600);
			g.setColor(new Color (0, 255, 230));
			g.setFont(new Font("monospaced", Font.BOLD, 70));
			g.drawString("Brick Breaker", 20, 200);
			g.setFont(new Font("monospaced", Font.BOLD, 30));
			g.drawString("Use your mouse cursor to", 65, 244);
			g.drawString("move the paddle", 140, 275);
			g.drawString("Press enter to start",  100,  330);
		}
		
		//loss screen
		if (ballposY >= 600) {
			g.setColor(Color.red);
			g.fillRect(0,  0,  600,  600);
			g.setColor(Color.black);
			g.setFont(new Font("monospaced", Font.BOLD, 70));
			g.drawString("GAME OVER", 105, 200);
			g.setFont(new Font("monospaced", Font.BOLD, 30));
			g.drawString("Score: " + score, 200, 275);
			play = false;
			
		}
		
		//win screen
		if (totalBricks == 0) {
			g.setColor(Color.green);
			g.fillRect(0,  0,  600,  600);
			g.setColor(Color.black);
			g.setFont(new Font("monospaced", Font.BOLD, 70));
			g.drawString("WINNER", 160, 200);
			g.setFont(new Font("monospaced", Font.BOLD, 30));
			g.drawString("Score: " + score, 195, 275);
			play = false;
		}
				
		g.dispose(); //clears resources

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
			
		if (play) {
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if (ballposX < 10) {		//bounce off left wall
				ballXdir = -ballXdir;
			}
			
			if (ballposY < 10) {		//bounce off top wall
				ballYdir = -ballYdir;
			}
			
			if (ballposX + 20 > 587) {		//bounce off right wall
				ballXdir = -ballXdir;
			}
			
			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 75, 10))) {	
				
				ballYdir = -ballYdir;	//ball and paddle collision
				speedVariable++;
				
				if (speedVariable == 1) {						//increase either x speed, y speed, or neither 
					int random = (int)(Math.random()*2 + 1);	//every "x" hits against the paddle
					if (random == 1) {
						ballYdir--;
					}
					else {
						if (ballXdir > 0) {
							ballXdir++;
						}
						else {
							ballXdir--;
						}
					}
					speedVariable = 0;
				}
			}
			
			if (playerX + 75 >= 589) {	//Prevent the paddle from exceeding the right border
				playerX = 514;
			}
			
			if (playerX <= 5) {	//Prevent the paddle from exceeding the left border
				playerX = 5;
			}
			
			
			blockCollisionDetection();	//Test if the ball is hitting a block
			repaint();					//Take everything off of the panel for the next frame	

		}
	}
	
	//Implemented Methods
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			play = true;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		playerX = e.getX();
	} 
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	//Block collision
	public void blockCollisionDetection() {
		for (int r = 0; r < grid.getNumRows(); r++) {		//for loop runs through
			for (int c = 0; c < grid.getNumCols(); c++) {	//every brick on the grid
				if (grid.bricks[r][c]) {	//if the block isn't broken
					
					
					
					//rectangle with current brick coords
					Rectangle currentBrick = new Rectangle(c*(grid.brickWidth + 5) + 40, r*(grid.brickHeight + 5) + 40, grid.brickWidth, grid.brickHeight);
					
					//ball posistions
					int[] ballCenter = {ballposX + 10, ballposY + 10}; //(ballCenter[0] = x, ballCenter[1] = y)
					int ballLeftX = ballposX;
					int ballRightX = ballposX + 20;
					int ballTopY = ballposY;
					int ballBottomY = ballposY + 20;
					
					//brick positions
					int brickTopY = (int) currentBrick.getMinY();
					int brickBottomY = (int) currentBrick.getMaxY();
					int brickLeftX = (int) currentBrick.getMinX();
					int brickRightX = (int) currentBrick.getMaxX();
					
					//Side detection
					if ((ballCenter[1] >= brickTopY && ballCenter[1] <= brickBottomY) &&
					    ((ballRightX >= brickLeftX && ballRightX <= brickRightX) || 
					    (ballLeftX >= brickLeftX && ballLeftX <= brickRightX)))
					{
					    ballXdir = -ballXdir;
					    grid.bricks[r][c] = false;
					    score = score + Math.abs(ballXdir) + Math.abs(ballYdir);
					    totalBricks--;
					}	
						
					//Top and bottom detection
					else if ((ballCenter[0] >= brickLeftX && ballCenter[0] <= brickRightX) &&
					    ((ballTopY >= brickTopY && ballTopY <= brickBottomY) || 
					    (ballBottomY >= brickTopY && ballBottomY <= brickBottomY)))
					{
					    ballYdir = -ballYdir;
					    grid.bricks[r][c] = false;
					    score = score + (Math.abs(ballXdir) + Math.abs(ballYdir))*100;
					    totalBricks--;
					}	
				}
			}
		}
	
	}	

}

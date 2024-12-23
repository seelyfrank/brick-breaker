import java.awt.*;

public class Bricks {	//Generates the field of bricks
	
    boolean bricks[][];
	int brickHeight, brickWidth;
	private int numRows, numCols;
	
	public Bricks(int row, int col) { //row is the number of bricks in a row, col is the number of bricks in a column
		
		bricks = new boolean[row][col];
		
		brickWidth = (510 - (col - 1)*5) / col; //formula for finding the brick width
		brickHeight = (200 - (row - 1)*5) / row; //formula for finding the brick height
		
		numRows = row; 
		numCols = col;
		
		for (int r = 0; r < row; r++) {			//set all values in array to true, 
			for (int c = 0; c < col; c++) {		//meaning the blocks should be visible
				bricks[r][c] = true;			
			}
		}
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numCols;
	}

	public void draw(Graphics g) {				//draw every rectangle on the panel if they have not been hit
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				if (bricks[r][c]) {				//tests if the brick has been hit. if true, the brick is visible. if false, the brick is not visible
					g.fillRect(c*(brickWidth + 5) + 40, r*(brickHeight + 5) + 40, brickWidth, brickHeight);
				}
			}
		}
	}
	
}

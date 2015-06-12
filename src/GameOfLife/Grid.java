//Ajay Saini
package GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
/*
 * displays the visual of the game of life board output
 * class data: numRows, numColumns, gridSize, cellWidth, cellHeight
 * constructor: takes number of rows and number of columns as arguments, initializes class data numRows
 * and numColumns with their respective values, calculates cellWidth and cellHeight, initializes 
 * respective class variables with those values
 * methods: getNumRows(), getNumColumns(), getCellWidth(), getCellHeight(), and paintComponent()
 * method paintComponent draws grid (game board) and any dots necessary on the grid
 */
public class Grid extends JComponent {

	private int numRows; //number of rows in board
	private int numColumns; //number of columns in board
	private final int gridSize = 500; //grid size (length and width) fixed to 500
	private double cellWidth; //size of cell along x (horizontal) axis
	private double cellHeight; //size of cell along y (vertical) axis

	/*
	 * takes number of rows and number of columns as arguments
	 * sets class variables numRows and numColumns equal to rows and columns respectively
	 * calculates cellHeight and cellWidth based on number of rows and number of columns
	 */
	public Grid(int rows, int columns){
		numRows = rows;
		numColumns = columns;
		cellHeight =  (double) gridSize/numRows; //y axis
		cellWidth =  (double) gridSize/numColumns; //x axis
	}
	
	/*
	 * returns the number of rows in the board
	 */
	public int getNumRows(){
		return numRows;
	}
	
	/*
	 * returns the number of columns in the board
	 */
	public int getNumColumns(){
		return numColumns;
	}
	
	/*
	 * returns the height (size along vertical axis) of an individual cell
	 */
	public double getCellHeight(){
		return cellHeight;
	}
	
	/*
	 * returns the width (size along horizontal axis) of an individual cell
	 */
	public double getCellWidth(){
		return cellWidth;
	}
	
	/*
	 * draws a fixed size rectangle (500 pixels by 500 pixels)
	 * draws lines to make the squares of the grid
	 * adds dots to the grid where necessary (if the value at the location on the game board is 1)
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		double columnStart = cellWidth; // x axis coordinate to begin the line
		double rowStart = cellHeight; //y axis coordinate to begin the line
		Rectangle grid = new Rectangle(gridSize, gridSize); //outer boundaries of grid fixed to 500 by 500
		g2.setColor(Color.WHITE); //draws the grid and grid lines in white
		g2.draw(grid);
		
		/*
		 * loops through Board
		 * draws lines to separate columns where new cells begin
		 */
		for(int i = 1; i < numColumns;  i++){
			Line2D.Double column = new Line2D.Double(columnStart, 0, columnStart, gridSize);
			g2.draw(column);
			columnStart += cellWidth; //increases columnStart coordinate by cellWidth
		}
		
		/*
		 * loops through Board
		 * draws lines to separate rows where new cells begin
		 */
		for(int i = 1; i < numRows; i++){
			Line2D.Double row  = new Line2D.Double(0, rowStart, gridSize, rowStart);
			g2.draw(row);
			rowStart += cellHeight; //increases rowStart coordinate by cellHeight
		}
		
		/*
		 * loops through the game Board, which corresponds to spots on grid
		 * if location in game board is 1, a dot is drawn in corresponding location on the grid
		 */
		for(int row = 0; row < numRows; row++){
			for(int column = 0; column < numColumns; column++){
				//x coordinate of upper left corner of enclosing rectangle of the dot (Ellipse2D.Double)
				double xCoordinate = cellWidth * column;  
				
				//y coordinate of upper left corner of enclosing rectangle of the dot (Ellipse2D.Double)
				double yCoordinate = cellHeight * row;
				
				//if location on game board is 1, a dot is drawn in corresponding location on grid
				if(PlayGameOfLife.getGame().getCellValue(row, column) == 1){
					Ellipse2D.Double dot = 
							new Ellipse2D.Double(xCoordinate, yCoordinate, cellWidth, cellHeight);
					
					g2.setColor(Color.CYAN); //dots colored CYAN
					g2.fill(dot);
					g2.draw(dot);
				}
			}
		}
	}
}
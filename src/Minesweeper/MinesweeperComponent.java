package Minesweeper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;


public class MinesweeperComponent extends JComponent{
	private int cellSize;
	private Minesweeper game;
	private Rectangle2D.Double [][] cells; //the graphical form of the individual cells
	private Rectangle2D.Double board; //the outline of the board
	private MinesweeperImages images; //the images for the game
	
	
	public MinesweeperComponent(int cellSize){
		this.cellSize = cellSize;
		images = new MinesweeperImages();
		images.createImages(); //initializes the static data in the MinesweeperImages class
	}
	
	public MinesweeperComponent(int cellSize, int difficulty){
		this(cellSize);
		
		//create graphics for an easy board
		if(difficulty == 1){
			cells = new Rectangle2D.Double[8][8];
			board = new Rectangle2D.Double(0, 0, 8 * cellSize, 8 * cellSize);
			initializeCells(); //create the 2D array cells containing the graphical representation of the cells 
		}
		//create graphics for an intermediate board
		else if(difficulty == 2){
			cells = new Rectangle2D.Double[16][16];
			board = new Rectangle2D.Double(0, 0, 16 * cellSize, 16 * cellSize);
			initializeCells(); //create the 2D array cells containing the graphical representation of the cells
		}
		//create graphics for an expert board
		else if(difficulty == 3){
			cells = new Rectangle2D.Double[16][32];
			board = new Rectangle2D.Double(0, 0, 32 * cellSize, 16 * cellSize);
			initializeCells(); //create the 2D array cells containing the graphical representation of the cells
		} 
	}
	
	public MinesweeperComponent(int cellSize, int difficulty, Minesweeper game){
		this(cellSize, difficulty);
		this.game = game; 
		//if the game is custom
		if(difficulty == 0){
			cells = new Rectangle2D.Double[game.getNumRows()][game.getNumCols()];
			board = new Rectangle2D.Double(0, 0, game.getNumCols() * cellSize, game.getNumRows() * cellSize);
			initializeCells();
		}
	}
	
	public void setGame(Minesweeper g){game = g;}
	public Rectangle2D.Double[][] getCells(){return cells;}
	
	/*
	 * creates the graphical representation of the cells in the game
	 */
	private void initializeCells(){
		for(int row = 0; row < cells.length; row++){
			for(int col = 0; col < cells[row].length; col++){
				cells[row][col] = new Rectangle2D.Double(col * cellSize, row * cellSize, cellSize, cellSize);
			}
		}
	}
	
	/*
	 * @param the graphics object used to draw
	 * draws the board and the cells in their appropriate states (opened, flagged, has bomb, ect.....)
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		paintBoard(g2); //draw the outline of the board
		
		//step through the board and draw the cells in their appropriate states
		for(int row = 0; row < cells.length; row++){
			for(int col = 0; col < cells[row].length; col++){
				Cell cell = game.getCells()[row][col];
				if(cell.isOpen() && !cell.hasBomb()){
					g2.setColor(Color.CYAN);
					g2.fill(cells[row][col]);
					drawBombNumber(row, col, g2);
				}
				if(cell.isOpen() && cell.hasBomb()){
					g2.drawImage(MinesweeperImages.BOMB, col * cellSize, row * cellSize, null);
				}
				if(cell.isFlagged()){
					g2.drawImage(MinesweeperImages.FLAG,col * cellSize, row * cellSize, null);
				}
				if(game.gameOver() && cell.isFlagged() && !cell.hasBomb()){
					g2.drawImage(MinesweeperImages.BOMB, col * cellSize, row * cellSize, null);
					drawX(row, col, g2);
				}
			}
		}
		drawCells(g2); //redraw the outlines of the cells (outlines disappear with images added)
	}
	
	/*
	 * @param the Graphics2D object to draw the cells
	 * draws the individual cells of the board
	 */
	private void drawCells(Graphics2D g){
		//draw the individual cells of the board
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[i].length; j++){
				g.setColor(Color.MAGENTA);
				g.draw(cells[i][j]);

			}
		}
	}
	
	/*
	 * draws an 'X' over a cell
	 * @param the row and column of the cell to draw the X over
	 * @param the Graphics2D object used to draw the X
	 */
	private void drawX(int row, int col, Graphics2D g){
		g.setStroke(new BasicStroke(3)); //makes the lines of the 'X' thicker
		g.setColor(Color.RED);
		int x1 = col * cellSize;
		int y1 = row * cellSize;
		int x2 = x1 + cellSize;
		int y2 = y1 + cellSize;
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y1, x1, y2);
		g.setStroke(new BasicStroke(1)); //reset the thickness
	}
	
	/*
	 * draws a cells bomb number
	 * @param the row and column of the cell to draw the bomb number of
	 * @param the Graphics2D object used to draw the image of the number
	 */
	private void drawBombNumber(int row, int col, Graphics2D g2){
		int bombNum = game.getCells()[row][col].getBombNum(); //the bomb number of the cell
		switch(bombNum){
		case 1:
			g2.drawImage(MinesweeperImages.ONE, col * cellSize, row * cellSize, null);
			break;
		case 2:
			g2.drawImage(MinesweeperImages.TWO, col * cellSize, row * cellSize, null);
			break;
		case 3:
			g2.drawImage(MinesweeperImages.THREE, col * cellSize, row * cellSize, null);
			break;
		case 4:
			g2.drawImage(MinesweeperImages.FOUR, col * cellSize, row * cellSize, null);
			break;
		case 5:
			g2.drawImage(MinesweeperImages.FIVE, col * cellSize, row * cellSize, null);
			break;
		case 6:
			g2.drawImage(MinesweeperImages.SIX, col * cellSize, row * cellSize, null);
			break;
		case 7:
			g2.drawImage(MinesweeperImages.SEVEN, col * cellSize, row * cellSize, null);
			break;
		case 8:
			g2.drawImage(MinesweeperImages.EIGHT, col * cellSize, row * cellSize, null);
			break;
		}
	}
	
	/*
	 * draws the outline of the board
	 * @param the Graphics2D object used to draw the outline of the board
	 */
	private void paintBoard(Graphics2D g){
		g.setColor(Color.MAGENTA);
		g.draw(board); //draw the outer boundary of the board
		g.setColor(Color.BLACK);
		g.fill(board);

		drawCells(g);
	}
	

}

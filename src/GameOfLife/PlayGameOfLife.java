//Ajay Saini
package GameOfLife;
import javax.swing.*;

import Minesweeper.MinesweeperRunner;


import java.awt.event.*;
import java.awt.*;
/*
 * handles the visual playing of the Game of Life
 * controls events in the game and actual playing of the game
 * JFrame  viewGame displays all components and output of game
 * Grid object handles drawing of grid and dot components to JFrame
 * buttons  Auto, changeBoardSize, nextGen, clearBoard, and random 
 * starts game with a default setup of a 20 by 20 grid
 * default constructor: sends other constructor parameters for a 20 by 20 grid
 * constructor: takes number of rows and number of columns as arguments and initializes,
 * GameOfLife object, Grid object, JFrame, and JLabel genNum
 * sets cell width, sets cell height, creates JFrame, sets genNum to default start value (0)
 * methods: main(), startGame(), createGrid(), addComponents(), getGame()
 * inner classes: NextGen, Auto, BoardSize, Random, and ClearBoard handle button clicks
 * class data: GameOfLife game, JFrame viewGame, Grid board, JLabel genNum
 */
public class PlayGameOfLife implements Runnable{
	//handles the rules for calculating nextGeneration and generation number
	private static GameOfLife game; 
	private JFrame viewGame; //used to display output of game
	private Grid board; //the board that the game is played on
	private JLabel genNum; // labels the current generation number
	
	
	/*
	 * default constructor
	 * calls constructor PlayGameOfLife(int rows, int columns) 
	 * and sends it parameters for a 20 by 20 grid
	 */
	public PlayGameOfLife(){
		this(20, 20);
	}
	
	/*
	 * takes number of rows and number of columns as arguments
	 * creates a new GameOfLife object based on number of rows and number of columns to play game with
	 * creates a Grid object based on number of rows and number of columns to control display of output
	 * creates a new JFrame to display output of game
	 * labels JLabel genNum with initial generation number: 0
	 */
	public PlayGameOfLife(int rows, int columns){
		//initializes class data
		game = new GameOfLife(rows, columns);
		board = new Grid(rows, columns);
		viewGame = new JFrame("The Game Of Life");
		genNum = new JLabel("Generation: " + game.getGenNum());
	}
	
	/*
	 * creates a PlayGameOfLife object with default constructor (default startup)
	 * calls startGame() to initialize the playing of the game
	 */
	public static void main(String [] args){
		PlayGameOfLife p = new PlayGameOfLife(); //default start is a 20 by 20 board
		p.startGame(); //starts game with default size board(20 by 20)
	}
	
	/*
	 * returns the GameOfLife object of this class
	 */
	public static GameOfLife getGame(){
		return game;
	}
	
	/*
	 * initiates the playing of The Game of Life
	 * calls method to create the visual of the game board in the JFrame
	 * adds the buttons and JLabel to the JFrame viewGame
	 */
	public void startGame(){
		createGrid(); //creates and adds grid component to JFrame
		addComponents(); //adds buttons to JFrame
		viewGame.setBackground(Color.BLACK); //background color of frame set to black
		viewGame.setVisible(true); //frame set visible
	}
	
	/*
	 * displays view of the Grid in the JFrame
	 * sets up MouseListener that 
	 * allows for the user to click on squares and place the starting dots for the first generation
	 */
	public void createGrid(){
		viewGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewGame.setSize(690, 730);
		viewGame.setResizable(false);
		
		//places a dot in the square clicked
		class MousePressListener implements MouseListener{
			public void mousePressed(MouseEvent event){
				int xCoordinate = event.getX(); //x coordinate clicked
				int yCoordinate = event.getY(); //y coordinate clicked
				
				//x coordinate of the upper left corner of the cell clicked
				double cellX =  xCoordinate - xCoordinate % board.getCellWidth(); 
				
				//y coordinate of the upper left corner of the cell clicked
				double  cellY =  yCoordinate - yCoordinate % board.getCellHeight();
				
				//sets the corresponding spot in the int[][] of class Board to 1
				game.toggleCell((int) (cellY/board.getCellHeight()), (int) (cellX/board.getCellWidth()));
				game.setGenNum(0); //starting generation number is 0
				genNum.setText("Generation: " + game.getGenNum());
				viewGame.repaint(); //repaints the grid based on new Board values (0 or 1)
			}
			public void mouseReleased(MouseEvent event) {}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		}
		MousePressListener listener = new MousePressListener();
		board.addMouseListener(listener); //adds listener for class MousePressListener to button
		board.setBounds(0, 0, 501, 501); //fixed bounds of grid within JFrame
		viewGame.add(board);
	}
	
	/*
	 * commands for adding buttons to the JFrame
	 * inner classes are listeners for buttons and have commands for what to do when button is clicked
	 */
	public void addComponents(){
		JPanel panel = new JPanel(); //panel to contain buttons
		panel.setLayout(null);
		
		//when clicked, next generation is displayed
		final JButton nextGen = new JButton("Next Generation"); 
		nextGen.setBounds(0, 501, 150, 100); //fixed bounds
		
		// displays generations in 300 millisecond intervals
		final JButton automatic = new JButton("Automatic"); 
		automatic.setBounds(151, 501, 150, 100); //fixed bounds
		
		//changes the size(number of rows and columns) of the board
		final JButton changeBoardSize = new JButton("Change Board Size");
		changeBoardSize.setBounds(302, 501, 150, 100); //fixed bounds
		
		/*
		 * clears all dots on the board
		 * resets genNum count to 0
		 */
		final JButton reset = new JButton("Reset");
		reset.setBounds(453, 501, 150, 100); //fixed bounds
		
		/*
		 * creates a random board (dots placed randomly)
		 * resets genNum count to 0
		 */
		final JButton random = new JButton("Random");
		random.setBounds(0, 602, 150, 100);
		
		//displays the next generation when JButton nextGen is clicked
		class NextGen implements ActionListener{
				public void actionPerformed(ActionEvent event){
					game.nextGeneration(); //calculates next generation on game board
					genNum.setText("Generation: " + game.getGenNum());
					viewGame.repaint(); //repaints the grid based on new Board values
				}
			}
			ActionListener listener2 = new NextGen();
			nextGen.addActionListener(listener2); //adds listener for class to button
			panel.add(nextGen); //adds button to panel
			
			
			 //calculates generations automatically in 300 millisecond intervals
		 class Auto implements ActionListener{
				/*
				 * tells whether or not generations are being calculated automatically
				 * if false, generations are not being calculated automatically
				 * if true, generations are being calculated automatically
				 */
				boolean auto = false; 
				
				Timer t; //timer for calculating next generations
				
				public void actionPerformed(ActionEvent event){
					auto = !auto; //toggles auto (true to false or false to true)
					 //if generations need to be calculated automatically
					if(auto){
						//changes text on automatic to indicate new action when clicked
						automatic.setText("Stop");  
						
						/* 
						 * disables buttons that would interfere with automatic display of generations
						 * or buttons that change the board or JFrame in any way
						 */
						nextGen.setEnabled(false);
						changeBoardSize.setEnabled(false);
						reset.setEnabled(false);
						random.setEnabled(false);
					
						//displays next generation when called by timer object
						class AutoTimer implements ActionListener{
							public void actionPerformed(ActionEvent event){
								//calculates next generation Board values
								game.nextGeneration(); 
								genNum.setText("Generation: " + game.getGenNum());
								//repaints the grid based on new Board values
								viewGame.repaint(); 
							}
						}
						AutoTimer listener = new AutoTimer();
						//calculates next generation in 300 millisecond intervals
						t = new Timer(300, listener);
						t.start(); //starts timer
					}
					
					//if generations are no longer needed to be calculated automatically (auto = false)
					else{
						automatic.setText("Automatic");
						
						//enables buttons that would have interfered with automatic generation display
						nextGen.setEnabled(true);
						changeBoardSize.setEnabled(true);
						reset.setEnabled(true);
						random.setEnabled(true);
						t.stop(); //ends automatic calculation and display of next generations
					}
				}
			}
			ActionListener listener3 = new Auto();
			automatic.addActionListener(listener3); //adds listener for class to button
			panel.add(automatic); //adds button to panel
			
			//changes grid size, but keeps it a square
			class BoardSize implements ActionListener{
				public void actionPerformed(ActionEvent event){
					//desired board size (results in a square board)
					String newSize = JOptionPane.showInputDialog("Enter the New Size");
					//sets dimensions of board to desired board size
					double rows = Double.parseDouble(newSize);
					double columns = Double.parseDouble(newSize);
					
					/*
					 * cannot have more than 500 rows or columns
					 * if invalid entry (not an integer), user continuously prompted to enter new size
					 * ends when user either enters a valid size or presses cancel
					 */
					while(rows < 1 || !(rows % 1 == 0) || rows > 500){
						newSize = JOptionPane.showInputDialog("Enter the New Size");
						rows = Double.parseDouble(newSize);
					 	columns = Double.parseDouble(newSize);
					}
					//creates new PlayGameOfLife object to play game with
					PlayGameOfLife p = new PlayGameOfLife((int) rows, (int) columns); 
					p.startGame(); //starts the game with the new object (resets everything)
					viewGame.setVisible(false); 
				}
			}
			ActionListener listener = new BoardSize();
			changeBoardSize.addActionListener(listener); //adds listener for class to button
			panel.add(changeBoardSize); //adds button to panel
			
			/*
			 * clears all dots from board
			 * resets genNum count to 0
			 */
			class Reset implements ActionListener{
				public void actionPerformed(ActionEvent event){
					game.clearBoard(); //sets Board values to 0
					game.setGenNum(0);
					genNum.setText("Generation: " + game.getGenNum());
					viewGame.repaint(); //repaints grid based on new Board values (results in blank grid)
				}
			}
			ActionListener listener4 = new Reset();
			reset.addActionListener(listener4);
			panel.add(reset); //adds button to panel
			
			/*
			 * creates a random configuration of dots on the board
			 * resets genNum count to 0
			 */
			class Random implements ActionListener{
				public void actionPerformed(ActionEvent event){
					//steps through the int [][] Board
					for(int row = 0; row < board.getNumRows(); row++){
						for(int column = 0; column < board.getNumColumns(); column++){
							//sets each location in the board to either 0 or 1
							game.setCell(row, column, (int) (Math.random() * 2));
						}
					}
					game.setGenNum(0);
					genNum.setText("Generation: " + game.getGenNum());
					viewGame.repaint(); //repaints Grid based on new Board values
				}
			}
			ActionListener listener5 = new Random();
			random.addActionListener(listener5);
			panel.add(random);
			
			genNum.setBounds(550, 200, 200, 150); //fixed bounds of JLabel genNum
			genNum.setForeground(Color.WHITE); //text color of JLabel genNum is white
			panel.add(genNum); //adds JLabel genNum to panel
			viewGame.add(panel); //adds panel containing all buttons to the JFrame
	}
	
	private volatile Thread blinker;
	void stop(){
		blinker = null;
	}
	
	public void run(){
		try{
			PlayGameOfLife p = new PlayGameOfLife(); //default start is a 20 by 20 board
			p.startGame(); //starts game with default size board(20 by 20)
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

		

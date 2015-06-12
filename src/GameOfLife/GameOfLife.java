//Ajay Saini
package GameOfLife;
/*
 * controls actions of the playing of the Game of Life (calculates generations in the Game of Life )
 * constructor: creates board based on existing board
 * constructor: creates a blank game board
 * methods: setGenNum(), isAlive(0, isBorn(), setCell(), toggleCell(), getCellValue(), clearBoard(), 
 * getGenNum(), nextGeneration(), and toString()
 */
public class GameOfLife {
	private Board gameBoard; //the board the Game of Life is played on
	private int genNum; //keeps track of generation number
	
	/*
	 * takes a Board as an argument
	 * sets the gameBoard reference to the reference of a copy of the board
	 * sets the value of genNum to 0
	 */
	public GameOfLife(Board b){
		gameBoard = b.copyBoard(); //gameBoard has same data as Board b 
		genNum = 0; //starting genNum value is always 0
	}
	
	/*
	 * takes a number of rows and a number of columns as arguments
	 * creates a new blank GameOfLife board with the number of rows and number of columns
	 * sets genNum to 0
	 */
	public GameOfLife(int numRows, int numColumns){
		//blank board with specified number of rows and columns
		gameBoard = new Board(numRows, numColumns); 
		genNum = 0; //starting genNum value is always 0
	}
	
	/*
	 * takes a value for the generation number as an argument
	 * sets the generation number to that value
	 */
	public void setGenNum(int number){
		genNum = number;
	}
	
	/*
	 * takes a location on the Board as an argument
	 * uses the rules of the Game of Life to determine if the cell at the location is alive
	 * returns true if the cell is alive, false if it is not
	 */
	public boolean isAlive(int row, int column){
		//rules of game of life for determining whether or not a cell is alive
		if(gameBoard.getNumNeighbors(row, column) <= 1 || 
			gameBoard.getNumNeighbors(row, column) >= 4)
				return false;
		else
			return true;
	}
	
	/*
	 * takes a location on the Board as an argument
	 * uses the rules of the Game of Life to determine if a cell is born at the location
	 * returns true if a cell is born at the location, false otherwise
	 */
	public boolean isBorn(int row, int column){
		//rules of game of life for determining whether or not a cell is born
		if(gameBoard.getNumNeighbors(row, column) == 3)
			return true;
		else
			return false;
	}
	
	/*
	 * takes a location on the Board (row and column) and a value as arguments
	 * sets the location on the Board to the specified value
	 */
	public void setCell(int row, int column, int value){
		//if the value specified is acceptable in the gameOfLife (must be 0 or 1)
		if(value == 0 || value == 1)
			gameBoard.setCell(row, column, value);
		//if value is not acceptable to GameOfLife, methods ends without setting cell
		else 
			return;
	}
	
	/*
	 * takes a location on the board as an argument
	 * toggles the location from 0 to 1 or from 1 to 0
	 */
	public void toggleCell(int row, int column){
		//toggle cell method in Board class
		gameBoard.toggleCell(row, column);
	}
	
	/*
	 * takes a location on the board as an argument
	 * returns the value in the Board at the specified location
	 */
	public int getCellValue(int row, int column){
		return gameBoard.getCellValue(row, column);
	}
	
	/*
	 * sets all locations in the Board to 0 (clears the board)
	 */
	public void clearBoard(){
		//clearBoard() method in board class
		gameBoard.clearBoard();
	}
	
	/*
	 * returns the current generation number
	 */
	public int getGenNum(){
		return genNum;
	}
	
	/*
	 * uses the rules of The Game of Life to calculate the next generation in the Game of Life
	 * increments genNum every time this method is called
	 */
	public void nextGeneration(){
		genNum++; //increment genNum every time this method is called
		Board nextGen = gameBoard.copyBoard(); //the board for the next generation
		nextGen.clearBoard(); //starts the next generation board as blank
		//loops through the  original board
		for(int row = 0; row < gameBoard.getNumRows(); row++){
			for(int column = 0; column < gameBoard.getNumColumns(); column++){
				//uses the rules of the Game of Life to set occupied locations
				if(gameBoard.getCellValue(row, column) == 1){
					if(isAlive(row, column))
						nextGen.setCell(row, column, 1);
				}
				if(isBorn(row, column))
					nextGen.setCell(row, column, 1);
			}
		}
		//sets the reference of the GameOfLife board to the nextGen board
		gameBoard = nextGen;
	}
	
	/*
	 * prints the GameOfLife's game board (gameBoard) to the console
	 * for testing purposes
	 */
	public String toString(){
		//toString() method for the Board class
		String board = gameBoard.toString();
		return board;
	}
}

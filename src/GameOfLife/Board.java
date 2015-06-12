//Ajay Saini
package GameOfLife;
/*
 * class that controls functions of a game board
 * constructor: given number of rows and number of columns, constructs a blank board
 * constructor: given board data, constructs a new board with that data
 * methods: setCell, isValidLocation(), getNumRows(), getNumColumns(), getCellValue(), 
 * clearBoard(), copyBoard(), toggleCell(), getNumNeighbors(), and toString()
 */
public class Board {
	
	/*
	 * 2D array that acts as the board (values in the array correspond to displays in the board)
	 * 0 is blank, other integers represent various occupied statuses
	 */
	private int [][] cells;  
	
	/*
	 * constructs a board based on the number of rows and number of columns
	 * board automatically set as blank (default value in a 2D array is 0, blank)
	 */
	public Board(int numRows, int numColumns){
		cells = new int [numRows][numColumns]; //sets size of cells[][]
	}
	
	/*
	 * constructs a new board given data for the board 
	 */
	public Board(int [][] boardData){
		//sets cells[][] to same size as boardData[][]
		cells = new int [boardData.length][boardData[0].length];
		//loops through boardData[][] and sets values in cells[][] to the values in boardData[][]
		for(int row = 0; row < cells.length; row++){
			for(int column = 0; column < cells[0].length; column++){
				cells[row][column] = boardData[row][column];
			}
		}
	}
	
	/*
	 * takes a location (row and column number) on the board, and a value as arguments
	 * sets the location on the board to the value specified
	 */
	public void setCell(int row, int column, int value){
		//only accesses location if it is valid
		if(isValidLocation(row, column))
			cells[row][column] = value;
	}
	
	/*
	 * takes a location on the board (row and column) as an argument
	 * returns true if the location is within the bounds of the board (cells[][]), false otherwise
	 */
	public boolean isValidLocation(int row, int column){
		if(row < cells.length && row >= 0 && column < cells[0].length && column >= 0)
			return true;
		else
			return false;
	}
	
	/*
	 * returns the number of rows in the board
	 */
	public int getNumRows(){
		return cells.length;
	}
	
	/*
	 * returns the number of columns in the board
	 */
	public int getNumColumns(){
		return cells[0].length;
	}
	
	/*
	 * takes a location on the board (row and column) as an argument
	 * returns the value at the specified location
	 */
	public int getCellValue(int row, int column){
		return cells[row][column];
	}
	
	/*
	 * sets all values in cells[][] to 0 (all spaces in the board are blank)
	 */
	public void clearBoard(){
		for(int row = 0; row < cells.length; row++){
			for(int column = 0; column < cells[0].length; column++)
				cells[row][column] = 0;
		}
	}
	
	/*
	 * copies cells[][] to a new Board reference
	 * returns the new Board reference
	 */
	public Board copyBoard(){
		int [][] copyCells = new int [cells.length][cells[0].length];
		for(int row = 0; row < cells.length; row++){
			for(int column = 0; column < cells[0].length; column++)
				copyCells[row][column] = cells[row][column];
		}
		Board copy = new Board(copyCells);
		return copy;
	}
	
	/*
	 * takes a location in the form of row and column numbers
	 * changes the value of the location from 0 to 1 (if start at 0) or 1 to 0 (if start at 1)
	 */
	public void toggleCell(int row, int column){
		//only tries to access location if it is valid
		if(isValidLocation(row, column)){
			if(cells[row][column] == 1)
				cells[row][column] = 0;
			else
				cells[row][column] = 1;
		}
	}
	
	/*
	 * takes a location on the board (in the form of row and column numbers) as arguments 
	 * returns the number of occupied spaces around the location (top, bottom, 
	 * diagonal, and on either side)
	 */
	public int getNumNeighbors(int row, int column){
		int numNeighbors = 0; 
		//checks all locations around a valid location for being occupied
		if(isValidLocation(row, column)){
			/*
			 * checks if each location around the specified location is valid
			 * if it is a valid location, checks if the location is occupied (if the value
			 * at the location is not 0) 
			 * if the location is occupied, increments numNeighbors
			 */
			if(isValidLocation(row - 1, column - 1) && cells[row - 1][column - 1] != 0)
				numNeighbors++;
			if(isValidLocation(row - 1, column) && cells[row - 1][column] != 0)
				numNeighbors++;
			if(isValidLocation(row - 1, column + 1) && cells[row - 1][column + 1] != 0)
				numNeighbors++;
			if(isValidLocation(row, column - 1) && cells[row][column - 1] != 0)
				numNeighbors++;
			if(isValidLocation(row, column + 1) && cells[row][column + 1] != 0)
				numNeighbors++;
			if(isValidLocation(row + 1, column - 1) && cells[row + 1][column - 1] != 0)
				numNeighbors++;
			if(isValidLocation(row + 1, column) && cells[row + 1][column] != 0)
				numNeighbors++;
			if(isValidLocation(row + 1, column + 1) && cells[row + 1][column + 1] != 0)
				numNeighbors++;
		}
		return numNeighbors;
	}
	
	/*
	 * prints board onto console
	 * for testing purposes
	 */
	public String toString(){
		String board = "";
		//loops through board values and prints them
		for(int row = 0; row < cells.length; row++){
			for(int column = 0; column < cells[0].length; column++)
				board += cells[row][column] + "\t";
			board += "\n";
		}
		return board;
	}
		
}

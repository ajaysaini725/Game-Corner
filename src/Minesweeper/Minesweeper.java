//Ajay Saini
package Minesweeper;
/*
 * class Minesweeper controls the game flow of the game Minesweeper
 * data: 2-D array of Cells and an int bombsRemaining
 * constructor: takes difficulty, creates game (board size) based on difficulty and adds bombs
 * methods: checkAllNeighbors(), expand(), getNumNeighboringBombs(), getNumNeighboringFlags(),
 * isValid(), openBombs(), relocateBomb(), toggleFlag(), toString() and main()
 * helper methods: addBombNum(), addBombsEasy(), addBombsIntermediate(), addBombsExpert(), 
 * makeCells(), and open()
 * getters: getCells() and getBombsRemainging()
 */
public class Minesweeper {
	private Cell[][] cells; //the cells of the game
	private int bombsRemaining; //keeps a count of the number of unflagged bombs
	private boolean gameOver;
	private int difficulty;
	private int numRows;
	private int numCols;
	private int numBombs;
	
	/*
	 * @param the difficulty
	 * creates the game (board size, bombs, and bombsRemaining number) based on the difficulty
	 * uses the rules of minesweeper to do so
	 */
	public Minesweeper(int difficulty){
		this.difficulty = difficulty;
		
		//1 means easy
		if(difficulty == 1){
			bombsRemaining = 10;
			cells = new Cell[8][8];
			makeCells();
			addBombsEasy();
		}
		//2 means intermediate
		else if(difficulty == 2){
			bombsRemaining = 40;
			cells = new Cell[16][16];
			makeCells();
			addBombsIntermediate();
		}
		//3 means expert
		else if(difficulty == 3){
			bombsRemaining = 99;
			cells = new Cell[16][32];
			makeCells();
			addBombsExpert();
		}
	}
	
	public Minesweeper(int numRows, int numCols, int numBombs){
		this.numRows = numRows; 
		this.numCols = numCols;
		this.numBombs = numBombs;
		bombsRemaining = numBombs;
		cells = new Cell[numRows][numCols];
		makeCells();
		addBombsCustom();
	}
	
	public void resetCustomGame(){
		cells = new Cell[numRows][numCols];
		makeCells();
		addBombsCustom();
	}

	
	/*
	 * creates the cell objects in the cell[][]
	 */
	private void makeCells(){
		for(int row = 0; row < cells.length; row++){
			for(int col = 0; col < cells[row].length; col++){
				cells[row][col] = new Cell();
			}
		}
	}
	
	//@return the cells
	public Cell[][] getCells(){return cells;}
	public void setGameOver(boolean g){gameOver = g;}
	public boolean gameOver(){return gameOver;}
	public int getDifficulty(){return difficulty;}
	public int getNumRows(){return numRows;}
	public int getNumCols(){return numCols;}
	public int getNumBombs(){return numBombs;}
	
	/*
	 * adds the bombs for the easy difficulty
	 */
	private void addBombsEasy(){
		int bombsAdded = 0;
		//continues generating random spots and adding bombs until 10 bombs have been added
		while(bombsAdded < 10){
			int row = (int) (Math.random() * 8);
			int col = (int) (Math.random() * 8);
			if(cells[row][col].hasBomb())
				continue;
			else{
				cells[row][col].addBomb();
				bombsAdded++;
			}
		}
	}
	
	/*
	 * adds the bombs for the intermediate difficulty
	 */
	private void addBombsIntermediate(){
		int bombsAdded = 0;
		//continues generating random spots and adding bombs until 40 bombs have been added
		while(bombsAdded < 40){
			int row = (int) (Math.random() * 16);
			int col = (int) (Math.random() * 16);

			if(cells[row][col].hasBomb())
				continue;

			cells[row][col].addBomb();
			bombsAdded++;
		}
	}
	
	/*
	 * adds the bombs for the expert difficulty
	 */
	private void addBombsExpert(){
		int bombsAdded = 0;
		
		//continues generating random spots and adding bombs until 99 bombs have been added
		while(bombsAdded < 99){
			int row = (int) (Math.random() * 16);
			int col = (int) (Math.random() * 32);
			
			if(cells[row][col].hasBomb())
				continue;
			
			cells[row][col].addBomb();
			bombsAdded++;
		}
	}
	
	private void addBombsCustom(){
		int bombsAdded = 0;
		
		while(bombsAdded < numBombs){
			int row = (int) (Math.random() * numRows);
			int col = (int) (Math.random() * numCols);

			if(cells[row][col].hasBomb())
				continue;

			cells[row][col].addBomb();
			bombsAdded++;
		}
	}
	
	/*
	 * @param the spot to check
	 * checks if any of the neighbors of the location contain an unflagged bomb
	 * @return true if any of the neighbors contains an unflagged bomb, false otherwise
	 */
	public boolean checkAllNeighbors(int row, int col){
		boolean openedBomb = false;
		for(int i = row - 1; i <= row + 1; i++){
			for(int j = col - 1; j <= col + 1; j++){
				if(isValid(i, j) && !(i == row && j == col) && !cells[i][j].isFlagged()){
					if(cells[i][j].hasBomb())
						openedBomb = true;
				}
			}
		}
		return openedBomb;
	}
	
	/*
	 * opens all locations with a bomb
	 */
	public void openBombs(){
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[i].length; j++){
				if(cells[i][j].hasBomb())
					open(i, j);
			}
		}
	}
	
	/*
	 * @param the cell to open
	 * opens the cell and gives it a bomb number
	 */
	private void open(int row, int col){
		cells[row][col].open();
		addBombNum(row, col);
	}
	
	/*
	 * @param the location to toggle the flag of
	 * negates the boolean variable isFlagged in the cell
	 */
	public void toggleFlag(int row, int col){
		if(cells[row][col].isFlagged()){
			cells[row][col].unFlag();
			bombsRemaining++;
		}
		else{
			cells[row][col].flag();
			bombsRemaining--;
		}
	}
	
	/*
	 * @param the location of the bomb to relocated
	 * takes a bomb from the location in the parameter and moves it to an empty location
	 */
	public void relocateBomb(int row, int col){
		if(!cells[row][col].hasBomb()) //if the location does not have a bomb
			return;
		//find a random empty location and move the bomb there
		while(true){
			int r = (int) (Math.random() * cells.length);
			int c = (int) (Math.random() * cells[0].length);
			
			//if the location is empty and not the same one, move the bomb there
			if((r != row && c != col) && !cells[r][c].hasBomb()){
				cells[r][c].addBomb();
				cells[row][col].removeBomb();
				addBombNum(row, col);
				break;
			}
		}
	}
	
	/*
	 * @return the number of bombs that have not been flagged
	 */
	public int getBombsRemaining(){
		return bombsRemaining;
	}
	
	/*
	 * @param the row and col to expand
	 * expands the cells according to the rules of minesweeper
	 */
	public void expand(int row, int col){
		//base cases
		if(!isValid(row, col)){
			return;
		}
		if(cells[row][col].isFlagged()){
			return;
		}
		if(cells[row][col].hasBomb()){
			return;
		}
		if(cells[row][col].isOpen()){
			return;
		}
		
		open(row, col); //open current cell
		
		if(getNumNeighboringBombs(row, col) == 0) {
			//expand neighboring cells
			for(int i = row - 1; i <= row + 1; i++){
				for(int j = col - 1; j <= col + 1; j++){
					expand(i, j);
					
				}
			}
		}
		
	}
	
	/*
	 * @param the cell to add the bomb number of
	 * sets the bomb number to the number of surrounding bombs
	 */
	private void addBombNum(int row, int col){
		cells[row][col].setBombNum(getNumNeighboringBombs(row, col));
	}

	/*
	 * @param the cell to get the number of neighboring bombs of
	 * @return the number of neighboring bombs
	 */
	public int getNumNeighboringBombs(int i, int j){
		int bombNum = 0;
		//check all neighbors to get the bomb count
		for(int row = i - 1; row <= i + 1; row++){
			for(int col = j - 1; col <= j + 1; col++){
				if(!isValid(row, col) || (row == i && col == j))
					continue;
				if(cells[row][col].hasBomb()){
					bombNum++;
				}
			}
		}
		return bombNum;
	}
	
	/*
	 * @param the cells to get the number of neighboring flags of
	 * @return the number of neighboring flags
	 */
	public int getNumNeighboringFlags(int i, int j){
		int flagNum = 0;
		//check all neighbors to get the flag count
		for(int row = i - 1; row <= i + 1; row++){
			for(int col = j - 1; col <= j + 1; col++){
				if(!isValid(row, col) || (row == i && col == j))
					continue;
				if(cells[row][col].isFlagged()){
					flagNum++;
				}
			}
		}
		return flagNum;
	}
	
	/*
	 * @param the row and column to check
	 * @return true if the location is in cells[][] and false otherwise
	 */
	public boolean isValid(int row, int col){
		return row >= 0 && row < cells.length 
					&& col >= 0 && col < cells[0].length;
	}
	
	/*
	 * prints out where the bombs are in the game
	 * true if there is a bomb in the spot, false if there is not
	 */
	public String toString(){
		String s = "Bombs:\n";
		//step through cells and print bomb locations (marked by a true)
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				s += cells[i][j].hasBomb() + " ";
			}
			s+= "\n";
		}
		return s;
	}
	
	/*
	 * checks if the game is won
	 * @return true if the game is won, false otherwise
	 */
	public boolean win(){
		//step through cells and check if all non-bomb spots are open
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells[i].length; j++){
				if(!cells[i][j].hasBomb() && !cells[i][j].isOpen()){
					return false;
				}
			}
		}
		gameOver = true;
		return true;
	}
	
	//tests the functions of this class
	public static void main(String [] args){
		Minesweeper m = new Minesweeper(1);
		
		m.expand(7, 1);
	}
}

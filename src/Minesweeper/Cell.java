//Ajay Saini
package Minesweeper;
/*
 * class cell stores the data for and allows the manipulation of a cell of a minesweeper board
 * data: isFlagged, hasBomb, isOpen, and bombNum
 * default constructor provided by compiler: default initialization of primitive types
 * getters: hasBomb(), isFlagged(), isOpen(), and getBombNum()
 * setters: flag(), unflag(), addBomb(), removeBomb(), open(), and setBombNum()
 */
public class Cell {
	private boolean isFlagged; //true if flagged, false otherwise
	private boolean hasBomb; //true if there is a bomb, false otherwise
	private boolean isOpen; //true if open, false otherwise
	private int bombNum; //the number of neighboring bombs
	
	//@return hasBomb
	public boolean hasBomb(){return hasBomb;}
	
	//@return isFlagged
	public boolean isFlagged(){return isFlagged;}
	
	//@return isOpen
	public boolean isOpen(){return isOpen;}
	
	//@return bombNum
	public int getBombNum(){return bombNum;}
	
	//flags the cell
	public void flag(){isFlagged = true;}
	
	//adds a bomb to the cell
	public void addBomb(){hasBomb = true;}
	
	//removes the bomb from the cell 
	public void removeBomb(){hasBomb = false;}
	
	//opens the cell
	public void open(){
		isOpen = true;
		isFlagged = false;
	}
	
	//@param the number of neighboring bombs
	public void setBombNum(int num){bombNum = num;}
	
	//removes the flag of the cell
	public void unFlag(){isFlagged = false;}
}

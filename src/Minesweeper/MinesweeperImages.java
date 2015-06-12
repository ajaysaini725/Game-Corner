//Ajay Saini
package Minesweeper;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/*
 * class MinesweeperImages stores the images for the game of minesweeper
 * data: all static, a BOMB, a FLAG, a ONE, a TWO, a THREE, a FOUR, a FIVE, a SIX, a SEVEN, 
 * and an EIGHT
 * method: createImages()
 */
public class MinesweeperImages {
	public static Image BOMB; //a bomb
	public static Image FLAG; //a flag
	public static Image ONE; //a one
	public static Image TWO; //a two
	public static Image THREE; //a three
	public static Image FOUR; //a four
	public static Image FIVE; //a five
	public static Image SIX; //a six
	public static Image SEVEN;  //a seven
	public static Image EIGHT; //an eight

	/*
	 * gets the Images from the Images folder and stores them in the appropriate static 
	 * variable
	 */
	public void createImages(){

		//the ClassLoader for the images
		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		//InputStream object gets the image
		InputStream input = cl.getResourceAsStream("one.JPG");
		try {
			ONE = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("two.JPG");
		try {
			TWO = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("three.JPG");
		try {
			THREE = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("four.JPG");
		try {
			FOUR = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("five.JPG");
		try {
			FIVE = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("six.JPG");
		try {
			SIX = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("seven.JPG");
		try {
			SEVEN = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("eight.JPG");
		try {
			EIGHT = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("flag.JPG");
		try {
			FLAG = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		input = cl.getResourceAsStream("mine.JPG");
		try {
			BOMB = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

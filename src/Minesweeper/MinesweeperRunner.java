//Ajay Saini

package Minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.Timer;

/*
 * class MinesweeperRunner runs the graphical representation of the game minesweeper
 * data: a game, a frame, a Rectangle2D.Double [][] cells, 
 * a CELL_SIZE, a BEGINNER_WIDTH, an INTERMEDIATE_WIDTH, an, an EXPERT_WIDTH, 
 * a ClickListener, a JLabel numFlags, an int heighOffset
 * a JLabel time, a JLabel space, a JPanel panel, a boolean firstLeftClick, a boolean firstClick, 
 * a Timer timer, an int deciseconds, an int seconds, an int minutes, an int hours and a JMenuBar menuBar
 * default constructor: creates a JFrame
 * inner classes: ClickListener, ExitListener, RadioButtonListener, and Time
 * methods: createGame() and main()
 * helper methods: displayLoseMessage(), displayWinMessage(), and endGame()
 */
public class MinesweeperRunner implements Runnable{
	private MinesweeperComponent component;
	private Minesweeper game; //the game
	private JFrame frame; //the frame that displays the game
	private Rectangle2D.Double [][] cells; //the graphical form of the individual cells
	private final int CELL_SIZE = 30; //the cell dimension
	private final int BEGINNER_WIDTH = 8 * CELL_SIZE + 1; //the width of a beginner board
	private final int INTERMEDIATE_WIDTH = 16 * CELL_SIZE + 1; //the width of an intermediate board
	private final int EXPERT_WIDTH = 32 * CELL_SIZE + 1; //the width of an expert board
	private ClickListener clickListener; //the mouse listener for the game
	private JLabel numFlags; //the number of flags placed
	private JLabel time; //the time of the game
	private JLabel space; //used to separate the numFlags and time labels
	private JPanel panel; //the panel that contains the numFlags and timeLabels
	private boolean firstLeftClick; //whether or not it is the player's left click (true if it is)
	private boolean firstClick; //whether or not it is the player's first click (true if it is)
	private Timer timer; //times the game 
	private int deciseconds;
	private int seconds; //the number of seconds the player has been playing the current game
	private int minutes;
	private int hours;
	private JMenuBar menuBar; //the menuBar to contain the game menu
	private int heightOffset; //frame height offset caused by extra components and title bar
	private boolean isReset; //signals whether or not a reset of a custom board is happening
	
	public MinesweeperRunner(){
		frame = new JFrame("Minesweeper");
	}
	
	/*
	 * @param the difficulty of the game: at the start of the game, it is sent a default difficulty of 1
	 * 1 is easy, 2 is intermediate, and 3 is expert
	 * creates the frame and graphical components of the game based on the difficulty
	 * initializes all the data necessary for the start of the game based on the difficulty
	 */
	public void createGame(int difficulty){
		//initializes start data
		deciseconds = 0;
		seconds = 0;
		minutes = 0;
		hours = 0;
		timer = new Timer(100, new Time());
		firstLeftClick = true;
		firstClick = true;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);

		if(difficulty != 0){
			game = new Minesweeper(difficulty);
			component = new MinesweeperComponent(CELL_SIZE, difficulty, game); //CHANGE
			cells = component.getCells();
			frame.add(component);
		}
		else if(difficulty == 0 && !isReset){
			int numRows = 0, numCols = 0, numBombs = 0;
			JTextField rows = new JTextField();
			JTextField cols = new JTextField();
			JTextField bombs = new JTextField();
			final JComponent [] inputs = new JComponent[]{new JLabel("Enter the number of rows:"), rows, 
						new JLabel("Enter the number of columns"), cols,
						new JLabel("Enter the number of Bombs:"), bombs};
			
			JOptionPane input = new JOptionPane(inputs, JOptionPane.PLAIN_MESSAGE);
			JDialog dialog = input.createDialog("Custom Game");
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			
			while(true){
				dialog.setVisible(true);
				try{
					numRows = Integer.parseInt(rows.getText());
					numCols = Integer.parseInt(cols.getText());
					numBombs = Integer.parseInt(bombs.getText());
					if(numRows <= 0 || numCols <= 0 || numBombs <= 0){
						throw new NumberFormatException();
					}
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Dimension d = toolkit.getScreenSize();
					if(numRows * CELL_SIZE + heightOffset >= d.height || 
							numCols * CELL_SIZE + 1 >= d.width){
						throw new IllegalArgumentException();
					}
					if((numRows == 1 && numCols == 1) || numBombs >= numRows * numCols){
						throw new IllegalStateException();
					}
					break;
				}
				catch(NumberFormatException exception){
					JOptionPane.showMessageDialog(null, "Error! Inputs must be positive integers.", 
							"Error!", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalArgumentException exception){
					JOptionPane.showMessageDialog(null, "Error! The entered size is too big for your window.", 
							"Error!", JOptionPane.ERROR_MESSAGE);
				}
				catch(IllegalStateException exception){
					if(numBombs >= numRows * numCols){
						JOptionPane.showMessageDialog(null, "Error! The number of bombs must be less than " +
								"the number of cells.", 
								"Error!", JOptionPane.ERROR_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null, "Error! You cannot have a 1 x 1 board.", 
								"Error!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			game = new Minesweeper(numRows, numCols, numBombs);
			component = new MinesweeperComponent(CELL_SIZE, difficulty, game); 
			cells = component.getCells();
			frame.add(component);
		}
		else if(difficulty == 0 && isReset){
			game = new Minesweeper(game.getNumRows(), game.getNumCols(), game.getNumBombs());
			component = new MinesweeperComponent(CELL_SIZE, difficulty, game); //CHANGE
			cells = component.getCells();
			frame.add(component);
			isReset = false;
		}
		
		
		clickListener = new ClickListener(); //the mouse listener
		frame.addMouseListener(clickListener);

		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Game"); //the menu for changing the difficulty
		
		time = new JLabel("00:00:00.0");
		time.setFont(new Font("timer", Font.BOLD, 30));
		time.setForeground(Color.BLUE);
		
		numFlags = new JLabel("Bombs: " + game.getBombsRemaining());
		numFlags.setFont(new Font("numFlags", Font.BOLD, 30));
		numFlags.setForeground(Color.RED);
		space = new JLabel("      ");
		
		if(difficulty == 0){
			if (game.getNumCols() <= 10) {
				numFlags.setFont(new Font("numFlags", Font.BOLD, 15));
				time.setFont(new Font("timer", Font.BOLD, 16));
				space.setText("  ");
			}
			if(game.getNumCols() <= 7){
				numFlags.setFont(new Font("numFlags", Font.BOLD, 12));
				time.setFont(new Font("timer", Font.BOLD, 13));
				space.setText("  ");
			}
			if(game.getNumCols() <= 5){
				numFlags.setFont(new Font("numFlags", Font.BOLD, 9));
				time.setFont(new Font("timer", Font.BOLD, 10));
				space.setText(" ");
			}
		}
		
		//special conditions for a smaller board (difficulty of 1)
		if(difficulty == 1){
			numFlags.setFont(new Font("numFlags", Font.BOLD, 15));
			time.setFont(new Font("timer", Font.BOLD, 16));
			space.setText("  ");
		}

		panel = new JPanel(new FlowLayout()); //the panel for the numFlags and time labels
		panel.add(numFlags);
		panel.add(space);
		panel.add(time);

		//stores the beginner, intermediate, and expert buttons as a group of radio buttons
		ButtonGroup group = new ButtonGroup(); 

		//radio buttons for the difficulty of the game
		JRadioButtonMenuItem beginner = new JRadioButtonMenuItem("Beginner");
		JRadioButtonMenuItem intermediate = new JRadioButtonMenuItem("Intermediate");
		JRadioButtonMenuItem expert = new JRadioButtonMenuItem("Expert");
		JRadioButtonMenuItem custom = new JRadioButtonMenuItem("Custom");
		

		beginner.setActionCommand("1");
		intermediate.setActionCommand("2");
		expert.setActionCommand("3");
		custom.setActionCommand("0");

		ActionListener radioButtonListener = new RadioButtonListener();

		beginner.addActionListener(radioButtonListener);
		intermediate.addActionListener(radioButtonListener);
		expert.addActionListener(radioButtonListener);
		custom.addActionListener(radioButtonListener);

		group.add(beginner);
		group.add(intermediate);
		group.add(expert);
		group.add(custom);

		menu.add(beginner);
		menu.add(intermediate);
		menu.add(expert);
		menu.add(custom);
		menu.addSeparator();
		
		JMenuItem exit = new JMenuItem("Rage Quit"); //the menuItem for exiting the game
		JMenuItem reset = new JMenuItem("Reset");
		
		ResetListener resetListener = new ResetListener();
		reset.addActionListener(resetListener);
		menu.add(reset);
		
		menu.addSeparator();
		ExitListener exitListener = new ExitListener(); //the listener to end the game
		exit.addActionListener(exitListener);
		menu.add(exit);
		
		JMenu help = new JMenu("Help");
		JMenuItem howToPlay = new JMenuItem("How To Play");
		JMenuItem controls = new JMenuItem("Controls");
		
		HowToPlayListener howToPlayListener = new HowToPlayListener();
		ControlsListener controlsListener = new  ControlsListener();
		
		howToPlay.addActionListener(howToPlayListener);
		controls.addActionListener(controlsListener);
		
		help.add(howToPlay);
		help.addSeparator();
		help.add(controls);

		menuBar.add(menu);
		menuBar.add(help);
		frame.setJMenuBar(menuBar);
		frame.add(panel, BorderLayout.NORTH);
		
		frame.pack();
		heightOffset = menuBar.getHeight() + panel.getHeight() + frame.getInsets().top + 1;
		
		if(difficulty == 0){
			frame.setSize(game.getNumCols() * CELL_SIZE + 1,  game.getNumRows() * CELL_SIZE + heightOffset);
			custom.setSelected(true);
		}
		//create graphics for an easy board
		else if(difficulty == 1){
			frame.setSize(BEGINNER_WIDTH, 8 * CELL_SIZE + heightOffset);
			beginner.setSelected(true); //select the button of the beginner difficulty
			
		}
		//create graphics for an intermediate board
		else if(difficulty == 2){
			frame.setSize(INTERMEDIATE_WIDTH,  16 * CELL_SIZE + heightOffset);
			intermediate.setSelected(true);  //select the button of the intermediate difficulty
		
		}
		//create graphics for an expert board
		else if(difficulty == 3){
			frame.setSize(EXPERT_WIDTH,  16 * CELL_SIZE + heightOffset);
			expert.setSelected(true); //select the button of the expert difficulty
		} 
		frame.setVisible(true);

	}

	/*
	 * called if the player has lost
	 * ends the game by performing the end game functions for minesweeper
	 */
	private void endGame(){
		game.openBombs(); //opens all cells containing bombs
		//gameOver = true; //setting gameOver to true will paint the appropriate losing board
		timer.stop();
		frame.removeMouseListener(clickListener); //remove the mouse listener
	}

	/*
	 * displays a JOptionPane with a win message
	 */
	private void displayWinMessage(){
		JOptionPane.showMessageDialog(frame, "YOU WIN!!!!!", "WINNER!!!!!",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/*
	 * displays a JOptionPane with a lose message
	 */
	private void displayLoseMessage(){
		JOptionPane.showMessageDialog(frame, "YOU LOSE!!!!!", "LOSER!!!!!",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/*
	 * class RadioButtonListener is a listener for the radio buttons controlling the difficulty of the game
	 */
	private class RadioButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			timer.stop();
			//gets the difficulty stored in the button
			int difficulty = Integer.parseInt(e.getActionCommand()); 
			frame.remove(component);
			frame.remove(panel);
			frame.remove(menuBar);
			frame.removeMouseListener(clickListener);
			createGame(difficulty); //creates a new game with the appropriate difficulty
		}
	}
	
	/*
	 * class ExitListener is a listener for the button that exits the game
	 */
	private class ExitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			frame.dispose();
		}
	}
	
	private class ResetListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			timer.stop();
			frame.remove(component);
			frame.removeMouseListener(clickListener);
			frame.remove(panel);
			frame.remove(menuBar);
			if(game.getDifficulty() != 0){	
				//creates a new game with the appropriate difficulty
				createGame(game.getDifficulty()); 
			}
			//if the current game is custom
			else{
				isReset = true;
				createGame(0);
			}
		}
	}

	private class HowToPlayListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(frame, "Goal: Open all the cells that don't contain a bomb\n" +
					"The number on a cell indicates the number of bombs occupying the eight locations around it\n" +
					"Use flags to indicate cells that contain bombs\n" +
					"If you click on a cell containing a bomb, you lose\n" +
					"Once all the cells that do not contain bombs are open, you win!", "How To Play", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private class ControlsListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(frame, "Left Click: Open a cell\n" +
					"Right Click: Flag and unflag a cell\n" +
					"Dual Click: If the number of flags around a\n" +
					"cell is the same as its bomb number, all of the\n" +
					"cells around the clicked cell will be opened", "Controls", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*
	 * class ClickListener used as the mouse listener for the game
	 * data: the row of the cell that was clicked and the column of the cell that was clicked
	 */
	private class ClickListener implements MouseListener{
		//dummy methods
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}

		int row, col; //the row and column of the cell that was clicked
		boolean clickedCell;

		//when the mouse is pressed
		public void mousePressed(MouseEvent e) {
			//finds the x and y coordinates clicked
			int x = e.getX();
			int y = e.getY() - heightOffset - 1;
			
			//find the cell that was clicked
			for(int i = 0; i < cells.length; i++){
				for(int j = 0; j < cells[i].length; j++){
					if(cells[i][j].contains(x, y)){
						row = i; 
						col = j;
						clickedCell = true;
					}
				}
			}
			
			//if a cell was not clicked
			if(!clickedCell){return;}
			
			//if it is the first click with the left mouse button
			if(firstClick && e.getButton() == MouseEvent.BUTTON1){
				timer.start();
				firstClick = false;
			}
			
			Cell cell = game.getCells()[row][col]; //get the cell that was clicked
		
			int click = MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK;
			//checks for dual click
			if(((e.getModifiersEx() & click) == click) && game.getCells()[row][col].isOpen()){
				if(game.getNumNeighboringBombs(row, col) == game.getNumNeighboringFlags(row, col)){
					//if there is an unflagged bomb in one of the neighboring spots, the player has lost
					if(game.checkAllNeighbors(row, col)){
						endGame();
						game.setGameOver(true);
						component.setGame(game);
						component.repaint();
						displayLoseMessage();
					}
					//if the first expand produces only non-bomb cells
					else{
						for(int i = row - 1; i <= row + 1; i++){
							for(int j = col - 1; j <= col + 1; j++){
								if(game.isValid(i, j)){
									game.expand(i, j);
								}
							}
						}
					}
					component.setGame(game);
					component.repaint();
				}
				return;
			}

			//if the click was a right click
			if(e.getButton() == MouseEvent.BUTTON3 && !cell.isOpen()){
				game.toggleFlag(row, col); //toggle the flag
				numFlags.setText("Bombs: " + game.getBombsRemaining()); //set the numFlags JLabel
				component.setGame(game);
				component.repaint();
				return;
			}

			//if it is the first left click and the cell has a bomb
			if(firstLeftClick && cell.hasBomb()){
				game.relocateBomb(row, col); //relocate the bomb
				if(game.getNumNeighboringBombs(row, col) == 0){
					game.expand(row, col);
				}
				component.setGame(game);
				component.repaint();
			}
			firstLeftClick = false;
		}

		//when the mouse is released
		public void mouseReleased(MouseEvent e) {
			//if a cell was not clicked
			if(!clickedCell){return;}
			
			Cell cell = game.getCells()[row][col]; //the cell that was clicked
			if(cell.isFlagged() || cell.isOpen() || e.getButton() == MouseEvent.BUTTON3){
				//check for a win
				if(game.win()){
					timer.stop();
					displayWinMessage();
					frame.removeMouseListener(clickListener);
					component.setGame(game);
					component.repaint();
				}
				return;
			}

			//if there is a bomb, the player lost
			if(game.getCells()[row][col].hasBomb()){
				endGame();
				game.setGameOver(true);
				component.setGame(game);
				component.repaint();
				displayLoseMessage();
			}
			//if the cell is not flagged, expand it
			else if(!game.getCells()[row][col].isFlagged()){
				game.expand(row, col);
				component.setGame(game);
				component.repaint();
			}

			//if the game is won after the expand
			if(game.win()){
				timer.stop();
				frame.removeMouseListener(clickListener);
				displayWinMessage();
				component.setGame(game);
				component.repaint();
			}
		}
	}

	/*
	 * class Time used for timing the gameplay
	 */
	private class Time implements ActionListener{
		public void actionPerformed(ActionEvent e){
			deciseconds++; //increment the seconds count
			if(deciseconds == 10){
				deciseconds = 0;
				seconds++;
			}
			if(seconds == 60){
				seconds = 0;
				minutes++;
			}
			if(minutes == 60){
				minutes = 0;
				hours++;
			}
			String secs = seconds + "";
			String mins = minutes + "";
			String hrs = hours + "";
			if(seconds < 10){
				secs = "0" + seconds;
			}
			if(minutes < 10){
				mins = "0" + minutes;
			}
			if(hours < 10){
				hrs = "0" + hours;
			}
			//set the text of the JLabel for time
			time.setText(hrs + ":" + mins + ":" + secs + "." + deciseconds);
		}
	}
	
	//runs the game with a MinesweeperRunner object
	public static void main(String [] args){
		MinesweeperRunner q = new MinesweeperRunner();
		q.createGame(1); //the default starting difficulty is 1 (easy)
	}
	
	private volatile Thread blinker;
	void stop(){
		blinker = null;
	}
	
	public void run(){
		try{
			MinesweeperRunner q = new MinesweeperRunner();
			q.createGame(1); //the default starting difficulty is 1 (easy)
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

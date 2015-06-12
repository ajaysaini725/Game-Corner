package Output;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameOfLife.PlayGameOfLife;
import Puzzle.DisplayComponent;
import TicTacToe.PlayTicTacToe;
import Minesweeper.MinesweeperRunner;


public class Output {
	
	private class MinesweeperListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Thread t = new Thread(new MinesweeperRunner());
			t.start();
		}
	}
	
	private class TicTacToeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Thread t = new Thread(new PlayTicTacToe());
			t.start();
		}
	}
	
	private class PuzzleListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Thread t = new Thread(new DisplayComponent());
			t.start();
		}
	}
	
	private class GameOfLifeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Thread t = new Thread(new PlayGameOfLife());
			t.start();
		}
	}
	
	
	public void addComponents(JFrame f){
		JButton minesweeperButton = new JButton("Play Minesweeper");
		minesweeperButton.addActionListener(new MinesweeperListener());
		
		JButton ticTacToeButton = new JButton("Play Tic-Tac-Toe");
		ticTacToeButton.addActionListener(new TicTacToeListener());
		
		JButton puzzleButton = new JButton("Play Puzzle");
		puzzleButton.addActionListener(new PuzzleListener());
		
		JButton gameOfLifeButton = new JButton("Play The Game of Life");
		gameOfLifeButton.addActionListener(new GameOfLifeListener());
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBackground(Color.WHITE);
		
		buttonPanel.add(minesweeperButton);
		buttonPanel.add(ticTacToeButton);
		buttonPanel.add(puzzleButton);
		buttonPanel.add(gameOfLifeButton);
		
		JPanel design = new JPanel(new BorderLayout());
		design.setBackground(Color.WHITE);
		
		JLabel title = new JLabel("Welcome to the Game Corner!");
		title.setFont(new Font("Text", Font.BOLD, 30));
		title.setForeground(Color.CYAN);
		
		JLabel name = new JLabel("Creator: Ajay Saini");
		name.setFont(new Font("Text", Font.BOLD, 30));
		name.setForeground(Color.CYAN);
		
		design.add(title, BorderLayout.NORTH);
		design.add(name, BorderLayout.SOUTH);
		
		JPanel choose = new JPanel(new BorderLayout());
		choose.setBackground(Color.WHITE);
		
		JLabel chooseText = new JLabel("CHOOSE");
		chooseText.setFont(new Font("Text", Font.BOLD, 60));
		chooseText.setForeground(Color.CYAN);
		
		JLabel your = new JLabel("YOUR");
		your.setFont(new Font("Text", Font.BOLD, 60));
		your.setForeground(Color.CYAN);
		
		JLabel game = new JLabel("GAME!");
		game.setFont(new Font("Text", Font.BOLD, 80));
		game.setForeground(Color.CYAN);
		
		choose.add(chooseText, BorderLayout.NORTH);
		choose.add(your, BorderLayout.CENTER);
		choose.add(game, BorderLayout.SOUTH);
		
		OutputComponent image = new OutputComponent();
		
		
		f.add(buttonPanel, BorderLayout.NORTH);
		f.add(design, BorderLayout.SOUTH);
		f.add(image);
		f.add(choose, BorderLayout.EAST);
		f.setSize(625, 500);
		f.setVisible(true);
	}
	
	public static void main(String[] args){
		JFrame games = new JFrame("THE GAME CORNER");
		games.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		games.setResizable(false);
		Output o = new Output();
		o.addComponents(games);
		
	}
}

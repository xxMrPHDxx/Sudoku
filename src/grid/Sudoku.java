package grid;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Sudoku {

	Grid grid;
	
	public Sudoku(){
		grid = new Grid();
	}
	
	public void update(){
		grid.update();
	}
	
	public void draw(Graphics g){
		grid.draw(g);
	}
	
	public void keyPressed(KeyEvent e){
		grid.keyPressed(e);
	}
	
	public void mousePressed(MouseEvent e){
		grid.mousePressed(e);
	}
	
}

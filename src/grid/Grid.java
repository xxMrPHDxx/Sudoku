package grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Grid {
	
	public final int rows = 9,cols = 9;
	public Cell[][] grid = new Cell[rows][cols];
	
	public Cell selectedCell;
	private int selectedRow = -1;
	private int selectedCol = -1;
	
	public boolean done = false;
	
	public Grid(){
		for(int row=0;row<rows;row++){
			for(int col=0;col<cols;col++){
				grid[row][col] = new Cell(row,col);
			}
		}
	}
	
	public void checkGrid(){
		
	}
	
	private boolean validateRow(int r,int c){
		for(int col=0;col<cols;col++){
			if(c != col && grid[r][c].value == grid[r][col].value){
				return false;
			}
		}
		return true;
	}
	
	private boolean validateCol(int r,int c){
		for(int row=0;row<rows;row++){
			if(r != row && grid[r][c].value == grid[row][c].value){
				return false;
			}
		}
		return true;
	}
	
	private boolean validateBox(int r,int c){
		int value = grid[r][c].value;
	    int box_r = (int)Math.floor(r / 3);
	    int box_c = (int)Math.floor(c / 3);

	    for (int _r = box_r * 3; _r < box_r * 3 + 3; _r++) {
	        for (int _c = box_c * 3; _c < box_c * 3 + 3; _c++) {
	            if (_r != r && _c != c && grid[_r][_c].value == value) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	public boolean okay(int r,int c){
		return (validateRow(r,c) && validateCol(r,c) && validateBox(r,c));
	}
	
	public boolean backtrack(int r,int c){
		c++; // Move to next cell in row
	    if (c > 8) { // Moves to next row when end of column is reached
	        c = 0;
	        r++;
	        if (r > 8) { // Checks if end of grid is reached
	            return true;
	        }
	    }
	    
		if (grid[r][c].value != 0) { // Move to next cell if user has entered a number in current cell
	        if (!okay(r,c)){
	            return false;
	        }
	        return backtrack(r, c);
	    } else { // Goes through all possible numbers if user has left cell blank
	        for (int x = 1; x < 10; x++) {
	            grid[r][c].value = x;
	            if (okay(r,c)){
	                if (backtrack(r, c)) {
	                    return true;
	                }
	            }
	        }
	        grid[r][c].value = 0;
	        return false;
	    }
	}
	
	public void update(){
		if(selectedCell != null){
			if(selectedRow < 0)selectedRow = rows - 1;
			if(selectedRow > rows - 1)selectedRow = 0;
			if(selectedCol > cols - 1)selectedCol = 0;
			if(selectedCol < 0)selectedCol = cols - 1;
			selectedCell = grid[selectedRow][selectedCol];
		}
	}
	
	public void draw(Graphics g){
		for(int row=0;row<rows;row++){
			for(int col=0;col<cols;col++){
				if(grid[row][col] == selectedCell){
					g.setColor(Color.GRAY);
				}else{
					g.setColor(Color.WHITE);
				}
				grid[row][col].draw(g);
			}
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_S){ //Solve grid
			done = true;
			backtrack(0,-1);
		}
		if(e.getKeyCode() == KeyEvent.VK_C){ //Clear grid
			for(int row=0;row<rows;row++){
				for(int col=0;col<cols;col++){
					grid[row][col].clear();
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_D){ //Toggle done 
			done = true;
		}
		if(selectedCell == null)return;
		if(!done){
			for(int i=0;i<=9;i++){
				if(e.getKeyCode() == i+48){
					selectedCell.setValue(i);
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			selectedRow--;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			selectedRow++;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			selectedCol--;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			selectedCol++;
		}
	}
	
	public void mousePressed(MouseEvent e){
		for(int row=0;row<rows;row++){
			for(int col=0;col<cols;col++){
				if(grid[row][col].contains(e.getX(), e.getY())){
					if(grid[row][col] != selectedCell){
						selectedCell = grid[row][col];
						selectedRow = row;
						selectedCol = col;
					}else{
						selectedCell = null;
					}
				}
			}
		}
	}
	
}

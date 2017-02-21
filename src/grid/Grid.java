package grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Grid {
	
	String[] values = new String[]{"1","2","3","4","5","6","7","8","9"};
	
	String[][] Example = new String[][]{
			{"5","3"," "," ","7"," "," "," "," "},
			{"6"," "," ","1","9","5"," "," "," "},
			{" ","9","8"," "," "," "," ","6"," "},
			{"8"," "," "," ","6"," "," "," ","3"},
			{"4"," "," ","8"," ","3"," "," ","1"},
			{"7"," "," "," ","2"," "," "," ","6"},
			{" ","6"," "," "," "," ","2","8"," "},
			{" "," "," ","4","1","9"," "," ","5"},
			{" "," "," "," ","8"," "," ","7","9"}};
	
	public Cell[][] cells;
	
	public Cell selectedCell;
	private int selectedRow = -1;
	private int selectedCol = -1;
	
	public boolean solved = false;
	
	public Grid(){
		cells = new Cell[9][9];
//		for(int i=0;i<cells.length;i++){
//			for(int j=0;j<cells[0].length;j++){
//				cells[i][j] = new Cell(i,j);
//			}
//		}
		
//		for(int i=0;i<9;i++){
//			for(int j=0;j<9;j++){
//				cells[i][j].setValue("4");
//			}
//		}
		
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[0].length;j++){
				cells[i][j] = new Cell(i,j);
				
			}
		}
		
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[0].length;j++){
				if(Example[i][j] != " ")cells[i][j].original();
				cells[i][j].setValue(Example[i][j]);
			}
		}
		
//		selectedCell = cells[4][4];
	}
	
	public void checkGrid(){
		//Make original copy of grid
//		Cell copy[][] = cells;//new Cell[9][9];
//		for(int i=0;i<cells.length;i++){
//			for(int j=0;j<cells[0].length;j++){
//				copy[i][j] = new Cell(i,j);
//				copy[i][j].setValue(cells[i][j].getValue());
//			}
//		}
		
		//Get subgrid 3x3
		Cell[][][][] subgrid = new Cell[3][3][3][3];
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[0].length;j++){
				subgrid[i/3][j/3][i%3][j%3] = cells[i][j];
			}
		}
		
		for(int row=0;row<cells.length;row++){
			for(int col=0;col<cells[0].length;col++){
				for(int i=0;i<values.length;i++){
					if(cells[row][col].getValue() == " ")continue;
					if(hasRow(row,values[i]) || hasCol(col,values[i]) || hasSubgrid(subgrid[row/3][col/3],values[i])){
						
					}
				}
			}
		}

		int lastnx,lastny;
		for(int nx=0;nx<cells.length;nx++){
			for(int ny=0;ny<cells[0].length;ny++){
				if(cells[nx][ny].getValue() == " "){									//Check grid empty
					lastnx=nx;lastny=ny;
					for(int i=0;i<9;i++){
						String value = values[i];
						if(!(hasRow(nx,value) || hasCol(ny,value) || hasSubgrid(subgrid[nx/3][ny/3],value))){
							System.out.println("SAME Value of " + value + " At ROW " + (nx + 1) + ", COL " + (ny + 1));
							cells[nx][ny].setValue(value);
						}else if(hasRow(nx,value)||hasCol(ny,value)||hasSubgrid(subgrid[nx/3][ny/3],value)){
							nx=lastnx;
							ny=lastny;
						}
					}
				}
			}
		}
	}
	
	private boolean hasRow(int row,String value){
		for(int i=0;i<cells[0].length;i++){
			if(cells[row][i].getValue() == value)return true;
		}
		return false;
	}
	
	private boolean hasCol(int col,String value){
		for(int i=0;i<cells.length;i++){
			if(cells[i][col].getValue() == value)return true;
		}
		return false;
	}
	
	private boolean hasSubgrid(Cell[][] subgrid,String value){
		for(int i=0;i<subgrid.length;i++){
			for(int j=0;j<subgrid[0].length;j++){
				if(subgrid[i][j].getValue() == value)return true;
			}
		}
		return false;
	}
	
	public boolean validateRow(Cell c){
		for(int i=0;i<cells[0].length;i++){
			if(c.getValue() == " ")continue;
			if(c.getCol() != i && cells[c.getCol()][i].getValue() == c.getValue())return false;
		}
		return true;
	}
	
	public boolean validateCol(Cell c){
		for(int i=0;i<cells.length;i++){
			if(c.getValue() == " ")continue;
			if(c.getRow() != i && cells[i][c.getCol()].getValue() == c.getValue())return false;
		}
		return true;
	}
	
	public boolean validateBox(Cell c){
		int box_r = c.getRow() / 3;
	    int box_c = c.getCol() / 3;

	    for (int _r = box_r * 3; _r < box_r * 3 + 3; _r++) {
	        for (int _c = box_c * 3; _c < box_c * 3 + 3; _c++) {
	            if (_r != c.getRow() && _c != c.getCol() && cells[_r][_c].getValue() == c.getValue()) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	public boolean checkCell(Cell c){
		return(validateRow(c) || validateCol(c) || validateBox(c));
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
	    
	    int value=0;
	    if (cells[r][c].getValue() != " ") { // Move to next cell if user has entered a number in current cell
	        if (!(validateRow(cells[r][c]) && validateCol(cells[r][c]) && validateBox(cells[r][c]))){
	            return false;
	        }else{
	        	if(cells[r][c].isOriginal())
	        		if(value >= 0 && value <= 9)cells[r][c].setValue(values[value]);
            }
	        return this.backtrack(r, c);
	    } else { // Goes through all possible numbers if user has left cell blank
	        for (int x = 0; x < 9; x++) {
	            cells[r][c].setValue(values[x]);
	            if (validateRow(cells[r][c]) && validateCol(cells[r][c]) && validateBox(cells[r][c])){
	                if (backtrack(r, c)) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	}
	
	public boolean solved(){
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[0].length;j++){
				if (cells[i][j].getValue() != " " && !(validateRow(cells[i][j]) && validateCol(cells[i][j]) && validateBox(cells[i][j]))){
	    			return false;
				}
			}
		}
		
		return true;//this.backtrack(0, -1);
	}
	
	public void solve(){
		int current=0;
		while(!solved()){
			if(!backtrack(current/9,current%9)){
				current++;
			}else{
				current--;
			}
		}
		
	}

	public boolean isEmpty(int r,int c){
		if(cells[r][c].getValue() == " ")return true;
		return false;
	}
	
	public boolean checkCells(){
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[0].length;j++){
				if(checkCell(cells[i][j]))return true;
			}
		}
		return false;
	}
	
	public void update(){
		if(selectedCell != null){
			if(selectedRow < 0)selectedRow = cells.length - 1;
			if(selectedRow > cells.length - 1)selectedRow = 0;
			if(selectedCol > cells[0].length - 1)selectedCol = 0;
			if(selectedCol < 0)selectedCol = cells[0].length - 1;
			selectedCell = cells[selectedRow][selectedCol];
		}
	}
	
	public void draw(Graphics g){
		//Draw cells 9x9
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[0].length;j++){
				if(cells[i][j] == selectedCell){
					g.setColor(Color.GRAY);
				}else{
					g.setColor(Color.WHITE);
				}
				cells[i][j].draw(g);
			}
		}
	}
	
	public void keyPressed(int k){
		if(selectedCell == null)return;
		if(!selectedCell.isOriginal()){
			if(k == KeyEvent.VK_SPACE){
				selectedCell.setValue(" ");
			}
			if(k == KeyEvent.VK_1){
				selectedCell.setValue("1");
			}
			if(k == KeyEvent.VK_2){
				selectedCell.setValue("2");
			}
			if(k == KeyEvent.VK_3){
				selectedCell.setValue("3");
			}
			if(k == KeyEvent.VK_4){
				selectedCell.setValue("4");
			}
			if(k == KeyEvent.VK_5){
				selectedCell.setValue("5");
			}
			if(k == KeyEvent.VK_6){
				selectedCell.setValue("6");
			}
			if(k == KeyEvent.VK_7){
				selectedCell.setValue("7");
			}
			if(k == KeyEvent.VK_8){
				selectedCell.setValue("8");
			}
			if(k == KeyEvent.VK_9){
				selectedCell.setValue("9");
			}
		}
		if(k == KeyEvent.VK_UP){
			selectedRow--;
		}
		if(k == KeyEvent.VK_DOWN){
			selectedRow++;
		}
		if(k == KeyEvent.VK_LEFT){
			selectedCol--;
		}
		if(k == KeyEvent.VK_RIGHT){
			selectedCol++;
		}
		if(k == KeyEvent.VK_Z){
			selectedCell = null;
			selectedRow = -1;
			selectedCol = -1;
		}
	}
	
	public void mousePressed(MouseEvent e){
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[0].length;j++){
				if(cells[i][j].contains(e.getX(), e.getY())){
					selectedCell = cells[i][j];
					selectedRow = i;
					selectedCol = j;
				}
			}
		}
	}
	
	/*
	 * g.fillRect(p * (j - (int)(j/3)) + p2 * ((int)(j/3) + 1) + j * size,
						   p * (i - (int)(i/3)) + p2 * ((int)(i/3) + 1) + i * size, 
						   size, size);
	 */
	
}

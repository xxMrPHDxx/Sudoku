package grid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Cell {

	private String c;
	private int x,y;
	
	private int row,col;
	
	private int p=2;
	private int p2=4;
	private int size=40;
	
	private boolean selected = false;
	
	private boolean original = false;
	
	public Cell(int i,int j){
		this.c = " ";
		this.row = i;
		this.col = j;
		this.x = p * (j - (int)(j/3)) + p2 * ((int)(j/3) + 1) + j * size;
		this.y = p * (i - (int)(i/3)) + p2 * ((int)(i/3) + 1) + i * size;
	}
	
	public void original(){
		this.original = true;
	}
	
	public boolean isOriginal(){
		return original;
	}
	
	public boolean contains(int x,int y){
		return (x > this.x && x < this.x + size && y > this.y && y < this.y + size);
	}
	
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
	}
	
	public String getRowCol(){
		return ("row " + (row+1) + ", col "+(col+1));
	}
	
	public void setValue(String c){
		this.c = c;
	}
	
	public String getValue(){
		return c;
	}
	
	public void select(){
		selected = true;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void draw(Graphics g){
		g.fillRect(x, y, size, size);
		g.setColor(Color.BLACK);
		if(!original)
			g.setFont(new Font("Century Gothic",Font.PLAIN, 30));
		else
			g.setFont(new Font("Century Gothic",Font.BOLD, 30));
		g.drawString(c, x + size - 30, y + size - 10);
	}
	
}

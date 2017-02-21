package grid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Cell {

	public int value = 0;
	
	private int x,y;
	
	private final int p=2,p2=p * 2,size=40;
	
	boolean selected = false;
	boolean original = false;
	
	public Cell(int i,int j){
		this.x = p * (j - (int)(j/3)) + p2 * ((int)(j/3) + 1) + j * size;
		this.y = p * (i - (int)(i/3)) + p2 * ((int)(i/3) + 1) + i * size;
	}
	
	public boolean contains(int x,int y){
		return (x > this.x && x < this.x + size && y > this.y && y < this.y + size);
	}
	
	public void setValue(int value){
		this.value = value;
		original = true;
	}
	
	public void clear(){
		this.value = 0;
		original = false;
	}
	
	public void draw(Graphics g){
		g.fillRect(x, y, size, size);
		g.setColor(Color.BLACK);
		if(!original){
			g.setFont(new Font("Century Gothic",Font.PLAIN, 30));
		}else{
			g.setFont(new Font("Century Gothic",Font.BOLD, 30));
			g.setColor(Color.BLUE);
		}
		if(value == 0)return;
		g.drawString(String.valueOf(value), x + size - 30, y + size - 10);
	}
	
}

package main;

import grid.Grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable,KeyListener,MouseListener {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 388,HEIGHT = 388;
	public static final String TITLE = "SUDOKU2";
	
	private int FPS = 60;
	
	private Thread thread;
	private boolean running = false;
	
	JFrame window;
	
	private BufferedImage image;
	private Graphics g;
	
	public Game(){
		super();
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
		addNotify();

		window = new JFrame(TITLE);
		window.setContentPane(this);		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
		init();
	}
	
	public synchronized void start(){
		running = true;
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			thread.start();
		}
	}
	
	public synchronized void stop(){
		running = false;
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000 / FPS;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			
			while(delta >= 1){
				update();
				delta--;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(shouldRender){
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
			}
		}
	}
	
	/*--------------------------------------------------------------------------------------------------------------*/
	
	Grid grid;
	
	int current;
	
	public void init(){
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();
		
		grid = new Grid();
	}
	
	public void update(){
		grid.update();
	}
	
	public void render(){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*===============================================================================================================*/

		grid.draw(g);
		
		/*===============================================================================================================*/
		
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public static void main(String[] args){
		new Game().start();
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_C){
			if(!grid.checkCells()){
				System.out.println("Grid has been solved!");
			}else{
				System.out.println("Grid has error!");
			}
		}
		grid.keyPressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		grid.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}

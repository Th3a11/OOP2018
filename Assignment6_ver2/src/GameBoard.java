import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements KeyListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	private boolean playGame = false;
	private int score = 0;
	
	private int brickNumber = 21;
	
	private Timer timer;
	private int timeDelay = 8;
	
	private int skateboardX = 260;
	
	private int ballposX = 288;
	private int ballposY = 428;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private BrickMap map;
	
	public GameBoard() {
		this.map = new BrickMap(10, 10);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(timeDelay, this);
		timer.start();
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 588, 458);
		
		//
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 3, 458);
		g.fillRect(0, 0, 588, 3);
		g.fillRect(585, 0, 3, 458);
		
		//skateboardX
		g.setColor(Color.PINK);
		g.fillRect(skateboardX, 440, 70, 8);
		
		//ball
		g.setColor(Color.WHITE);
		g.fillOval(ballposX, ballposY, 12, 12);
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (playGame) {
			if (new Rectangle(ballposX, ballposY, 12, 12).intersects(new Rectangle(skateboardX, 450, 70, 8))) {
				ballYdir = -ballYdir;
			}
			
			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 50;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 12, 12);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							brickNumber--;
							score += 5;
							
							if (ballposX + 19 <= brickRect.x || ballposX+ 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}
							else {
								ballYdir = -ballYdir;
							}
							
							break A;
						}
					}
				}
			}

			ballposX += ballXdir;
			ballposY += ballYdir;
			if (ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if (ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if (ballposX > 458) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint();		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		System.out.println("KeyEvent: " + key.getKeyCode());
		if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (skateboardX <= 588) {
				skateboardX = 588;
			}
			else {
				moveRight();
			}
		}
		else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			if (skateboardX < 10) {
				skateboardX = 10;
			}
			else {
				moveLeft();
			}
		}
		else if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			playGame = true;
		}
		
	}
	
	public void moveRight() {
		skateboardX += 20;
	}
	
	public void moveLeft() {
		skateboardX -= 20;
	}

	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	

}

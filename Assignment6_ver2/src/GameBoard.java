import java.awt.Color;
import java.awt.Font;
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
	private int life = 2;
	private boolean startGame = false;
	private int score = 0;
	private BreakOut breakOut;
	
	private int brickNumber = 32;
	
	private Timer timer;
	private int timeDelay = 33;
	
	private int skateboardX = 260;
	
	private int ballposX = 288;
	private int ballposY = 428;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private BrickMap map;
	
	public GameBoard(BreakOut breakOut) {
		this.breakOut = breakOut;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(timeDelay, this);
		timer.start();
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 588, 458);
		
		//Brick map
		map.draw((Graphics2D)g);
		
		//Score
		g.setColor(Color.WHITE);
		g.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		g.drawString("Score: " + score, 500, 20);
		
		//Lives
		g.setColor(Color.WHITE);
		g.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		g.drawString("Life: " + life, 500, 40);
		
		//Borders
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 3, 458);
		g.fillRect(0, 0, 588, 3);
		g.fillRect(585, 0, 3, 458);
		
		//Skateboard
		g.setColor(Color.PINK);
		g.fillRect(skateboardX, 440, 70, 8);
		
		//Ball
		g.setColor(Color.WHITE);
		g.fillOval(ballposX, ballposY, 12, 12);
		
		if (ballposY > 458) {
			if (life <= 0) {
				startGame = false;
				ballXdir = 0;
				ballYdir = 0;
				g.setColor(Color.RED);
				g.setFont(new Font("AppleGothic", Font.PLAIN, 30));
				g.drawString("Game Over", 215, 229);
				g.drawString("Score: " + score, 223, 260);
				g.setFont(new Font("AppleGothic", Font.PLAIN, 15));
				g.drawString("Press ENTER to restart.", 215, 300);
				this.player.setHighScore(score);
				this.player.updatePlayerInList();
				this.breakOut.currentPlayerID();
			}
			else {
				life -= 1;
				startGame = false;
				skateboardX = 260;
				ballposX = 288;
				ballposY = 428;
			}
		}
		else if (brickNumber == 0) {
			startGame = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.WHITE);
			g.setFont(new Font("AppleGothic", Font.PLAIN, 30));
			g.drawString("Victory", 250, 230);
			g.drawString("Score: " + score, 223, 260);
			g.setFont(new Font("AppleGothic", Font.PLAIN, 15));
			g.drawString("Press ENTER to restart.", 215, 300);
			this.player.setHighScore(score);
			this.player.updatePlayerInList();
			this.breakOut.currentPlayerID();
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		requestFocus();
		//System.out.println("TimeDelay: " + timeDelay); //Debugging
		timer.setDelay(timeDelay);
		timer.start();
		if (startGame) {
			if (new Rectangle(ballposX, ballposY, 12, 12).intersects(new Rectangle(skateboardX, 440, 70, 8))) {
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
							timeDelay -= 1;
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
			if (ballposX < 3) {
				ballXdir = -ballXdir;
			}
			if (ballposY < 3) {
				ballYdir = -ballYdir;
			}
			if (ballposX > 573) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint();		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		//System.out.println("KeyEvent: " + key.getKeyCode()); //Debugging
		if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			startGame = true;
		}
		else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!startGame) {
				this.restartGame(this.player);
			}
		}
		else if (startGame) {
			if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (skateboardX > 515) {
					skateboardX = 515;
				}
				else {
					this.moveRight();
				}
			}
			else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
				if (skateboardX < 3) {
					skateboardX = 3;
				}
				else {
					this.moveLeft();
				}
			}
		}
		else {
			if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (skateboardX > 515) {
					skateboardX = 515;
					ballposX = 486;
				}
				else {
					this.moveRight();
				}
			}
			else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
				if (skateboardX < 3) {
					skateboardX = 3;
					ballposX = 32;
				}
				else {
					this.moveLeft();
				}
			}
		}
	}
	
	public void moveRight() {
		if (startGame) {
			skateboardX += 20;
		}
		else {
			skateboardX += 20;
			ballposX += 20;
		}
	}
	
	public void moveLeft() {
		if (startGame) {
			skateboardX -= 20;
		}
		else {
			skateboardX -= 20;
			ballposX -= 20;
		}
	}
	
	public void restartGame(Player player) {
		this.player = player;
		this.skateboardX = 260;
		this.ballposX = 288;
		this.ballposY = 428;
		this.ballXdir = -1;
		this.ballYdir = -2;
		this.life = 2;
		this.score = 0;
		this.brickNumber = 32;
		this.timeDelay = 33;
		this.map = new BrickMap(4, 8);
		
		repaint();
	}

	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	

}

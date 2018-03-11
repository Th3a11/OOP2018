import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickMap {
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public BrickMap(int row, int column) {
		this.map = new int[row][column];
		for (int i = 0; i < map.length; i++) {
			for (int j= 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}
		
		this.brickWidth = 488/column;
		this.brickHeight = 150/row;
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for (int j= 0; j < map[0].length; j++) {
				if (this.map[i][j] > 0) {
					g.setColor(Color.GREEN);
					g.fillRect(j * brickWidth + 50, i * brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.BLACK);
					g.drawRect(j * brickWidth + 50, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int column) {
		this.map[row][column] = value;
	}
}

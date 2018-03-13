import java.io.*;
import java.util.*;
import javax.swing.JTextPane;


public class HighScore {
	private File file;
	private FileWriter outputToFile;
	private FileReader inputFromFile;
	private Scanner scanner;
	private ArrayList<Player> highScore;
	private Player tempHolder;
	private Player tempHolder2;
	
	//Constructor which checks if the high score file exists or not.
	public HighScore(String fileName) {
		this.highScore = new ArrayList<Player>();
		this.file = new File(fileName);
		
		if (!this.file.exists()) {
			this.createHighScore(this.file);
			System.out.println("High score file has been created.");
		}
		else {
			System.out.println("Opening high score file.");
			this.openHighScore();
		}
	}

	//Creates the high score file.
	public void createHighScore(File file) {
		try {
			this.outputToFile = new FileWriter(file);
			this.outputToFile.close();
			this.openHighScore();
		} 
		catch (IOException e) {
			System.out.printf("ERROR: Cannot create high score file, message: %s\n", e.getMessage());
		}
	}
	
	/* Imports the high score file and save it's content to an ArrayList.
	 * */
	public void openHighScore() {
		try {
			this.inputFromFile = new FileReader(this.file);
			int data = this.inputFromFile.read();
			this.scanner = new Scanner(this.file);
			
			if (data != -1) {
				int indexCount = 0;
				
				while (this.scanner.hasNext()) {
					String playerID = scanner.next();
					int bestScore = scanner.nextInt();
					
					Player record = new Player(playerID, bestScore, this);
					this.highScore.add(indexCount, record);
					indexCount++;
				}
			}
			
			for (int i = 0; i < highScore.size(); i++) {
				System.out.printf("PlayerHS: %s, %d\n", this.highScore.get(i).getPlayerID(), this.highScore.get(i).getHighScore());
			}
			
			this.scanner.close();
			this.inputFromFile.close();
		}
		catch (IOException e) {
			System.out.printf("ERROR: Cannot open high score file, message: %s\n", e.getMessage());
		}
	}
	
	public int getHighScoreSize() {
		return this.highScore.size();
	}
	
	public Player getPlayerAtIndex(int i) {
		return this.highScore.get(i);
	}
	
	public void addPlayerToHighScore(Player player) {
		this.highScore.add(player);
		this.sortHighScore();
	}
	
	public void updatePlayerInHighScore(Player player) {
		for (int i = 0; i < this.getHighScoreSize() ;i++) {
			if (this.highScore.get(i).getPlayerID().equals(player.getPlayerID())) {
				this.highScore.get(i).setHighScore(player.getHighScore());
			}
		}
		this.sortHighScore();
	}
	
	//Prints the high score to the high score JPanel
	public void printHighScore(JTextPane panHighScoreContent) {
		String text = "";
		int rankCount = 1;
		
		if (!this.highScore.isEmpty()) {
			if (this.highScore.size() < 5) {
				for (int i = 0; i < this.highScore.size(); i++) {
					text += rankCount + ".\t" + this.highScore.get(i).getPlayerID() + "\t\t"+ this.highScore.get(i).getHighScore() +"\n";
					rankCount++;
				}
				
				for (int i = 0; i < 5 - this.highScore.size(); i++) {
					text += rankCount + ".\tUnknown\t\t0\n";
					rankCount++;
				}
			}
			else {
				for (int i = 0; i < 5; i++) {
					text += rankCount + ".\t" + this.highScore.get(i).getPlayerID() + "\t\t"+ this.highScore.get(i).getHighScore() +"\n";
					rankCount++;
				}
			}
		}
		else {
			text = "No games have been played yet.";
		}

		panHighScoreContent.setText(text);
	}
	
	//Dose a bubble sort of the players best score in descending order.
	public void sortHighScore() {
		for (int i = 0; i < this.highScore.size(); i++) {
	        for (int j = 1; j < (this.highScore.size() - i); j++) {
	            if (this.highScore.get(j - 1).getHighScore() < this.highScore.get(j).getHighScore()) {
	            		this.tempHolder = this.highScore.get(j - 1);
	            		this.tempHolder2 = this.highScore.get(j);
	            		this.highScore.set(j - 1, this.tempHolder2);
	            		this.highScore.set(j, this.tempHolder);
	            }
	        }
	    }
	}
	
	//Saves the ArrayList to the file, when the game is exited or window closed.
	public void saveHighScore() {
		try {
			this.outputToFile = new FileWriter(this.file);
			
			for (int i = 0; i < this.highScore.size(); i++) {
				this.outputToFile.write(this.highScore.get(i).getPlayerID() + "\t" + this.highScore.get(i).getHighScore() + "\n");
			}
			
			this.outputToFile.close();
		} 
		catch (IOException e) {
			System.out.printf("ERROR: High score file cannot be saved %s\n", e.getMessage());
		}		
	}
		
}


public class Player {
	private String playerID;
	private int bestScore;
	private HighScore list;
	
	public Player(String playerID, int bestScore, HighScore list) {
		setPlayerID(playerID);
		setHighScore(bestScore);
		this.list = list;
	}
	
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	
	public String getPlayerID() {
		return this.playerID;
	}
	
	public void setHighScore(int bestScore) {
		if (bestScore > this.bestScore) {
			this.bestScore = bestScore;
		}
	}
	
	public int getHighScore() {
		return this.bestScore;
	}
	
	public boolean playerExistInList() {
		boolean exist = false;
		//System.out.println("Hello World!");	//Debugging
		//System.out.println(this.list.getHighScoreSize());	//Debugging
		if (this.list.getHighScoreSize() > 0) {
			for (int i = 0; i < this.list.getHighScoreSize() ;i++) {
				Player player2 = this.list.getPlayerAtIndex(i);
				if (player2.getPlayerID().toLowerCase().equals(this.playerID.toLowerCase())) {
					//System.out.println("Player: " + this.playerID.toLowerCase());	//Debugging
					//System.out.println("Player2: " + player2.getPlayerID().toLowerCase());	//Debugging
					exist = true;
				}
			}
		}
		return exist;
	}
	
	public void addPlayerToList() {
		this.list.addPlayerToHighScore(this);
	}
	
	public void loadPlayerFromList() {
		for (int i = 0; i < this.list.getHighScoreSize() ;i++) {
			Player player2 = this.list.getPlayerAtIndex(i);
			if (player2.getPlayerID().toLowerCase().equals(this.playerID.toLowerCase())) {
				this.setHighScore(player2.getHighScore());
				this.setPlayerID(player2.getPlayerID());
			}
		}
	}
	
	public void updatePlayerInList() {
		this.list.updatePlayerInHighScore(this);
	}

}

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Insets;

public class BreakOut implements ActionListener {
	private JFrame frame;
	private JLabel lblPlayerID;
	private JLabel lblCurrentPlayerID;
	private JLabel lblBestScore;
	private JLabel lblCurrentBestScore;
	private JPanel panStartContent;
	private GameBoard panGameContent;
	private JPanel panNewLoadPlayerContent;
	private JTextPane panRulesHighScoreContent;
	private JButton btnNewPlayer;
	private JButton btnLoadPlayer;
	private JButton btnAddLoadPlayerID;
	private JButton btnStartGame;
	private JButton btnHighScore;
	private JButton btnGameRules;
	private JButton btnExitGame;
	private Player player;
	private String fileNameHighScore = "HighScore.txt";
	private HighScore highScoreList;
	private String fileNameRules = "/Rules.txt";
	private Rules rules = new Rules(fileNameRules);
	private JLabel panNewLoadPlayerID;
	private JTextField tFieldNewLoadPlayerID;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BreakOut window = new BreakOut();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BreakOut() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.highScoreList = new HighScore(fileNameHighScore);
		
		this.frame = new JFrame("BreakOut Mini");
		this.frame.setBounds(100, 100, 600, 700);
		this.frame.setBackground(Color.BLACK);
		this.frame.getContentPane().setBackground(new Color(51, 0, 102));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);
		this.frame.addWindowListener(new WindowHandler());
		
		this.lblPlayerID = new JLabel("Player ID:");
		this.lblPlayerID.setForeground(Color.WHITE);
		this.lblPlayerID.setFont(new Font("AppleGothic", Font.BOLD, 15));
		this.lblPlayerID.setBounds(6, 6, 67, 37);
		this.frame.getContentPane().add(this.lblPlayerID);
		
		this.lblCurrentPlayerID = new JLabel("No player loaded");
		this.lblCurrentPlayerID.setForeground(Color.WHITE);
		this.lblCurrentPlayerID.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		this.lblCurrentPlayerID.setBounds(85, 6, 217, 37);
		this.frame.getContentPane().add(this.lblCurrentPlayerID);
		
		this.lblBestScore = new JLabel("Best Score:");
		this.lblBestScore.setForeground(Color.WHITE);
		this.lblBestScore.setFont(new Font("AppleGothic", Font.BOLD, 15));
		this.lblBestScore.setBounds(314, 6, 90, 37);
		this.frame.getContentPane().add(this.lblBestScore);
		
		this.lblCurrentBestScore = new JLabel("No current high score");
		this.lblCurrentBestScore.setForeground(Color.WHITE);
		this.lblCurrentBestScore.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		this.lblCurrentBestScore.setBounds(416, 6, 178, 37);
		this.frame.getContentPane().add(this.lblCurrentBestScore);
		
		this.panStartContent = new JPanel();
		this.panStartContent.setBackground(Color.WHITE);
		this.panStartContent.setBounds(6, 55, 588, 458);
		this.frame.getContentPane().add(this.panStartContent);
		this.panStartContent.setVisible(true);
		
		this.panNewLoadPlayerContent = new JPanel();
		this.panNewLoadPlayerContent.setBackground(Color.WHITE);
		this.panNewLoadPlayerContent.setBounds(6, 55, 588, 458);
		this.frame.getContentPane().add(this.panNewLoadPlayerContent);
		this.panNewLoadPlayerContent.setLayout(null);
		
		this.panNewLoadPlayerID = new JLabel("");
		this.panNewLoadPlayerID.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		this.panNewLoadPlayerID.setBounds(109, 179, 100, 40);
		this.panNewLoadPlayerID.setBackground(Color.WHITE);
		this.panNewLoadPlayerContent.add(this.panNewLoadPlayerID);
		
		this.tFieldNewLoadPlayerID = new JTextField();
		this.tFieldNewLoadPlayerID.setBounds(219, 179, 250, 40);
		this.tFieldNewLoadPlayerID.setFont(new Font("AppleGothic", Font.PLAIN, 14));
		this.tFieldNewLoadPlayerID.setBackground(Color.WHITE);
		this.panNewLoadPlayerContent.add(this.tFieldNewLoadPlayerID);
		this.tFieldNewLoadPlayerID.setColumns(10);
		
		this.btnAddLoadPlayerID = new JButton("");
		this.btnAddLoadPlayerID.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		this.btnAddLoadPlayerID.setBounds(369, 229, 100, 40);
		this.panNewLoadPlayerContent.add(this.btnAddLoadPlayerID);
		this.btnAddLoadPlayerID.addActionListener(this);
		this.panNewLoadPlayerContent.setVisible(false);
		
		this.panRulesHighScoreContent = new JTextPane();
		this.panRulesHighScoreContent.setMargin(new Insets(20, 40, 40, 40));
		this.panRulesHighScoreContent.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		this.panRulesHighScoreContent.setBounds(6, 55, 588, 458);
		this.frame.getContentPane().add(this.panRulesHighScoreContent);
		this.panRulesHighScoreContent.setVisible(false);
		
		
		
		this.btnNewPlayer = new JButton("New Player");
		this.btnNewPlayer.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		this.btnNewPlayer.setBounds(6, 525, 208, 47);
		this.frame.getContentPane().add(this.btnNewPlayer);
		this.btnNewPlayer.addActionListener(this);
		
		this.btnLoadPlayer = new JButton("Load Player");
		this.btnLoadPlayer.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		this.btnLoadPlayer.setBounds(6, 584, 208, 47);
		this.frame.getContentPane().add(this.btnLoadPlayer);
		this.btnLoadPlayer.addActionListener(this);
		
		this.btnStartGame = new JButton("Start Game");
		this.btnStartGame.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		this.btnStartGame.setBounds(386, 525, 208, 47);
		this.frame.getContentPane().add(this.btnStartGame);
		this.btnStartGame.addActionListener(this);
		
		this.btnHighScore = new JButton("High Score");
		this.btnHighScore.setFont(new Font("AppleGothic", Font.PLAIN, 15));
		this.btnHighScore.setBounds(386, 584, 208, 47);
		this.frame.getContentPane().add(this.btnHighScore);
		this.btnHighScore.addActionListener(this);
		
		this.btnGameRules = new JButton("Rules");
		this.btnGameRules.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		this.btnGameRules.setBounds(6, 643, 117, 29);
		this.frame.getContentPane().add(this.btnGameRules);
		this.btnGameRules.addActionListener(this);
		
		this.btnExitGame = new JButton("Exit");
		this.btnExitGame.setFont(new Font("AppleGothic", Font.PLAIN, 13));
		this.btnExitGame.setBounds(477, 643, 117, 29);
		this.frame.getContentPane().add(this.btnExitGame);
		this.btnExitGame.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent button) {
		if (button.getSource().equals(this.btnNewPlayer)) {
			this.panStartContent.setVisible(false);
			this.panNewLoadPlayerContent.setVisible(true);
			//this.panGameContent.setVisible(false);
			this.panRulesHighScoreContent.setVisible(false);
			
			this.tFieldNewLoadPlayerID.setBackground(Color.WHITE);
			this.tFieldNewLoadPlayerID.setText("");
			
			this.panNewLoadPlayerID.setText("New PlayerID:");
			this.btnAddLoadPlayerID.setText("Add");
			System.out.println("New Player");
		}
		else if (button.getSource().equals(this.btnLoadPlayer)) {
			this.panStartContent.setVisible(false);
			this.panNewLoadPlayerContent.setVisible(true);
			//this.panGameContent.setVisible(false);
			this.panRulesHighScoreContent.setVisible(false);
			
			this.tFieldNewLoadPlayerID.setBackground(Color.WHITE);
			this.tFieldNewLoadPlayerID.setText("");
			
			this.panNewLoadPlayerID.setText("Load PlayerID:");
			this.btnAddLoadPlayerID.setText("Load");
			System.out.println("Load Player");
		}
		else if (button.getSource().equals(this.btnAddLoadPlayerID)) {
			if (this.tFieldNewLoadPlayerID.getText().equals("")) {
				this.tFieldNewLoadPlayerID.setBackground(Color.PINK);
				this.tFieldNewLoadPlayerID.setText("Please write a PlayerID.");
			}
			else if (this.tFieldNewLoadPlayerID.getText().contains(" ")) {
				this.tFieldNewLoadPlayerID.setBackground(Color.PINK);
				this.tFieldNewLoadPlayerID.setText("No blankspaces in PlayerID.");
			}
			else {
				addLoadPlayerID(this.btnAddLoadPlayerID);
			}
		}
		else if (button.getSource().equals(this.btnStartGame)) {
			if (this.player != null) {
				this.panStartContent.setVisible(false);
				this.panNewLoadPlayerContent.setVisible(false);
				//this.panGameContent.setVisible(true);
				this.panRulesHighScoreContent.setVisible(false);
				
				this.panGameContent = new GameBoard();
				this.panGameContent.setBackground(Color.WHITE);
				this.panGameContent.setBounds(6, 55, 588, 458);
				this.frame.getContentPane().add(this.panGameContent);
				this.panGameContent.setVisible(true);
				
				
				System.out.println("Start Game");
			}
			else {
				//Inform user, in some way, that no player is loaded
				System.out.println("Start Game: no player is loaded.");
			}
		}
		else if (button.getSource().equals(this.btnHighScore)) {
			this.panStartContent.setVisible(false);
			this.panNewLoadPlayerContent.setVisible(false);
			//this.panGameContent.setVisible(false);
			this.panRulesHighScoreContent.setVisible(true);
			
			this.highScoreList.printHighScore(this.panRulesHighScoreContent);
			System.out.println("High Score");
		}
		else if (button.getSource().equals(this.btnGameRules)) {
			this.panStartContent.setVisible(false);
			this.panNewLoadPlayerContent.setVisible(false);
			//this.panGameContent.setVisible(false);
			this.panRulesHighScoreContent.setVisible(true);
			
			this.rules.printRules(this.panRulesHighScoreContent);
			System.out.println("Rules");
		}
		else if (button.getSource().equals(this.btnExitGame)) {
			this.exitGame();
		}
	}
	
	public void addLoadPlayerID(JButton button) {
		this.player = new Player(tFieldNewLoadPlayerID.getText(), 0, 3, this.highScoreList);
		//boolean exist = this.player.playerExistInList();
		//System.out.println("Exist = " + exist);
		if (button.getText().equals("Add")){
			if (this.player.playerExistInList()) {
				this.tFieldNewLoadPlayerID.setBackground(Color.PINK);
				this.tFieldNewLoadPlayerID.setText("Player already exist.");
			}
			else {				
				this.tFieldNewLoadPlayerID.setBackground(Color.WHITE);
				this.tFieldNewLoadPlayerID.setText("");
				this.player.addPlayerToList();
				this.currentPlayerID();
				
				this.panStartContent.setVisible(true);
				this.panNewLoadPlayerContent.setVisible(false);
				//this.panGameContent.setVisible(false);
				this.panRulesHighScoreContent.setVisible(false);
			}
		}
		else if (button.getText().equals("Load")) {
			if (this.player.playerExistInList()) {
				this.tFieldNewLoadPlayerID.setBackground(Color.WHITE);
				this.tFieldNewLoadPlayerID.setText("");
				this.player.loadPlayerFromList();
				this.currentPlayerID();
				
				this.panStartContent.setVisible(true);
				this.panNewLoadPlayerContent.setVisible(false);
				//this.panGameContent.setVisible(false);
				this.panRulesHighScoreContent.setVisible(false);
			}
			else {
				this.tFieldNewLoadPlayerID.setBackground(Color.PINK);
				this.tFieldNewLoadPlayerID.setText("Player doesn't exist.");
			}
		}
	}
	
	public void currentPlayerID() {
		int playerScore = this.player.getHighScore();
		StringBuilder toString = new StringBuilder();
		toString.append(playerScore);
		String score = toString.toString();
		
		this.lblCurrentPlayerID.setText(this.player.getPlayerID());
		this.lblCurrentBestScore.setText(score);
	}
	
	public void exitGame() {
		System.out.println("Exit");
		this.highScoreList.saveHighScore();
		System.exit(0);
	}

	
	class WindowHandler implements WindowListener {

		/* Methods imported from WindowListener.
		 * Used to save the player info if window is closed without using the exit-button.
		 * */
		@Override
		public void windowActivated(WindowEvent arg0) {}
	
		@Override
		public void windowClosed(WindowEvent arg0) {
			exitGame();
		}
	
		@Override
		public void windowClosing(WindowEvent arg0) {
			exitGame();
		}
	
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
	
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
	
		@Override
		public void windowIconified(WindowEvent arg0) {}
	
		@Override
		public void windowOpened(WindowEvent arg0) {}
	}
}

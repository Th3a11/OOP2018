import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTextPane;

public class Rules {
	private InputStream file;
	private BufferedReader reader;
	private String rules = "";
	
	
	public Rules(String fileName) {
		this.file = getClass().getResourceAsStream(fileName);
		this.openRules();
		System.out.println("Opening rule file.");
	}
	
	private void openRules() {
		try {
			reader = new BufferedReader(new InputStreamReader(this.file));
			int i = 0;
			
			while ((i = reader.read()) != -1) {
				rules += (char)i;
			}
			
			reader.close();
			//System.out.println(rules);
		}
		catch (IOException e) {
			System.out.printf("ERROR: Cannot open rule file, message: %s\n", e.getMessage());
		}
	}

	public void printRules(JTextPane panRulesContent) {
		panRulesContent.setText(rules);
	}
}

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainView extends JFrame {

	private JTextField stockInputField = new JTextField(10);
	private JButton monitorStockButton = new JButton("Monitor");
	
	MainView() {
		JPanel mainViewPanel = new JPanel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(380, 100);
		this.setTitle("Stock Monitor");
		
		
		//Need to access os in order to get screen size, toolkit gives us that access
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//get the dimensions of our screen size
		Dimension dim = toolkit.getScreenSize();
		//the below coordinates will center the screen
		int xCord = (dim.width / 2) - (this.getWidth() / 2);
		int yCor = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xCord, yCor);
		this.setResizable(false);
		
		JLabel title = new JLabel("Stock Monitor");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		
		mainViewPanel.add(title);
		mainViewPanel.add(stockInputField);
		mainViewPanel.add(monitorStockButton);
		
		//adding the panel to the jframe
		this.add(mainViewPanel);
	}
	
	public String getInputText() {
		return stockInputField.getText();
	}
	
	//Creates a listener for the button.
	void addMonitorButtonListener(ActionListener listenForMonitorButton) {
		monitorStockButton.addActionListener(listenForMonitorButton);
	}
	
	void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}
}

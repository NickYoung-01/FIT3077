import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainView extends JFrame {

	private int text;
	private JTextField stockInputField = new JTextField(10);
	private JButton monitorStockButton = new JButton("Monitor");
	
	MainView() {
		JPanel mainViewPanel = new JPanel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 100);
		
		//Need to access os in order to get screen size, toolkit gives us that access
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//get the dimenions of our screen size
		Dimension dim = toolkit.getScreenSize();
		//the below coordinates will center the screen
		int xCord = (dim.width / 2) - (this.getWidth() / 2);
		int yCor = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xCord, yCor);
		this.setResizable(false);
		
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

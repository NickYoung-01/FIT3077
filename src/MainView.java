import java.awt.event.ActionListener;
import javax.swing.*;


public class MainView extends JFrame {

	private JTextField stockInputField = new JTextField(10);
	private JButton monitorStockButton = new JButton("Monitor");
	
	MainView() {
		JPanel mainViewPanel = new JPanel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 200);
		
		mainViewPanel.add(stockInputField);
		mainViewPanel.add(monitorStockButton);
		
		this.add(mainViewPanel);
	}
	
	public String getInputTexet() {
		return stockInputField.getText();
	}
	
	void addMonitorButtonListener(ActionListener listenForMonitorButton) {
		monitorStockButton.addActionListener(listenForMonitorButton);
	}
	
	void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}
}

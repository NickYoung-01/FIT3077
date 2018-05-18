import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class HomeView extends JFrame {
	
	private JComboBox monitorType, serviceType, availableStock;
	private JTextField stockInputField = new JTextField(10);
	private JButton monitorStockButton = new JButton("Monitor");
	
	HomeView() {
		JPanel mainViewPanel = new JPanel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(380, 500);
		this.setTitle("Stock Monitor");
		
		String[] monitorTypes = {"Text Monitor", "Graph Monitor"};
		String[] serviceTypes = {"Live", "Historic"};
		
		monitorType = new JComboBox(monitorTypes);
		mainViewPanel.add(monitorType);
		
		serviceType = new JComboBox(serviceTypes);
		availableStock = new JComboBox();
		
		//Need to access os in order to get screen size, toolkit gives us that access
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//get the dimensions of our screen size
		Dimension dim = toolkit.getScreenSize();
		//the below coordinates will center the screen
		int xCord = (dim.width / 2) - (this.getWidth() / 2);
		int yCor = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xCord, yCor);
		this.setResizable(false);
	

		mainViewPanel.add(availableStock);
		availableStock.setEnabled(false);
		mainViewPanel.add(stockInputField);
		mainViewPanel.add(serviceType);
		mainViewPanel.add(monitorStockButton);
		
		//adding the panel to the jframe
		this.add(mainViewPanel);
	}
	
	public void handleServiceChange(int index) {
		switch (index) {
			case 0:	availableStock.setEnabled(false);
					stockInputField.setEnabled(true);
					break;
			case 1:	stockInputField.setEnabled(false);
					availableStock.setEnabled(true);
					break;
			default:
					break;
		}
	}
	
	public void setAvailableStockList(String stock) {
		availableStock.addItem(stock);
	}
	
	public String getInputText() {
		return stockInputField.getText();
	}
	
	public int getMonitorTypeIndex() {
		return monitorType.getSelectedIndex();
	}
	
	public int getServiceTypeIndex() {
		return serviceType.getSelectedIndex();
	}
	
	public String getSelectedHistoricStock() {
		return availableStock.getSelectedItem().toString();
	}
	
	//Creates a listener for the service type jcombobox
	void addServiceTypeComboListener(ActionListener listenForComboChange) {
		serviceType.addActionListener(listenForComboChange);
	}
	
	//Creates a listener for the button.
	void addMonitorButtonListener(ActionListener listenForMonitorButton) {
		monitorStockButton.addActionListener(listenForMonitorButton);
	}
	
	void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}
}

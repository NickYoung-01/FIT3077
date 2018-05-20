package View;
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
	
	public HomeView() {
		JPanel mainViewPanel = new JPanel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(380, 500);
		this.setTitle("Stock Monitor");
		
		//The label for the options in the combo boxes
		String[] monitorTypes = {"Text Monitor", "Graph Monitor"};
		String[] serviceTypes = {"Live", "Historic"};
		
		//create new combo boxes with their options
		monitorType = new JComboBox(monitorTypes);
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
	
		mainViewPanel.add(monitorType);
		mainViewPanel.add(availableStock);
		availableStock.setEnabled(false);
		mainViewPanel.add(stockInputField);
		mainViewPanel.add(serviceType);
		mainViewPanel.add(monitorStockButton);
		
		//adding the panel to the jframe
		this.add(mainViewPanel);
	}
	
	//when the serviceType combo box is clicked
	public void handleServiceChange(int index) {
		switch (index) {
		//if the selected index of the combo box is 0, which means live
		//we should only allow user to enter into the text input field
			case 0:	availableStock.setEnabled(false);
					stockInputField.setEnabled(true);
					break;
		//if the selected index of the combo box is 1, which means historic (timelapse)
		//there are only certain stocks that can be monitored, hence drop down is enabled
		//and input field is disabled
			case 1:	stockInputField.setEnabled(false);
					availableStock.setEnabled(true);
					break;
			default:
					break;
		}
	}
	
	//add the stocks that we can monitor for the historic server (timelapse)
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
	public void addServiceTypeComboListener(ActionListener listenForComboChange) {
		serviceType.addActionListener(listenForComboChange);
	}
	
	//Creates a listener for the button.
	public void addMonitorButtonListener(ActionListener listenForMonitorButton) {
		monitorStockButton.addActionListener(listenForMonitorButton);
	}
	
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}
}

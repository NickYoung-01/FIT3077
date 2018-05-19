package View;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.*;

import Model.Observer;
import Model.Stock;

public class StockMonitor extends Observer {

	private Stock stock;
	private JLabel symbolLabel, lastPriceLabel, dateLabel, timeLabel;
	private JFrame frame;
	
	public StockMonitor(Stock stock) {
		this.stock = stock;
		this.stock.registerObserver(this);
		
		frame = new JFrame(stock.getSymbol() + " Monitor");
		
		//We want to perform our own closing action
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Add a listener to the close button
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				closeWindow();
			}
		});
		
		frame.setSize(200, 200);
		//So we can generate a random number when setting frames coords
		Random rand = new Random();

		
		//Need to access os in order to get screen size, toolkit gives us that access
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//get the dimenions of our screen size
		Dimension dim = toolkit.getScreenSize();
		//the below coordinates will center the screen
		int xCord = ((dim.width / 2) - (frame.getWidth() / 2)) + 255 + rand.nextInt(50);
		int yCor = ((dim.height / 2) + (frame.getHeight() / 2)) - 150 - rand.nextInt(50);
		frame.setLocation(xCord, yCor);
		
		JPanel panel = new JPanel();
		symbolLabel = new JLabel("Symbol: " + stock.getSymbol());
		lastPriceLabel = new JLabel("Last Price: " + stock.getLastTrade());
		dateLabel = new JLabel("Date: " + stock.getDate());
		timeLabel = new JLabel("Time: " + stock.getTime());
		
		panel.setLayout(new GridLayout(0,1));
		panel.add(symbolLabel);
		panel.add(lastPriceLabel);
		panel.add(dateLabel);
		panel.add(timeLabel);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	@Override
	public void update() {
		//update our labels to display the new data
		lastPriceLabel.setText("Last Price: " + stock.getLastTrade() + " AUD");
		dateLabel.setText("Date: " + stock.getDate());
		timeLabel.setText("Time: " + stock.getTime());
	}
	
	//Remove this observer from Stock and close JFrame
	public void closeWindow() {
		stock.removeObserver(this);
		//closes the JFrame
		frame.dispose();
	}

}

import javax.swing.*;

public class StockMonitor implements Observer {

	private Stock stock;
	private JLabel symbolLabel, lastPriceLabel, dateLabel, timeLabel;
	private JFrame frame;
	
	public StockMonitor(Stock stock) {
		this.stock = stock;
		this.stock.registerObserver(this);
		
		frame = new JFrame(stock.getSymbol() + " Monitor");
		frame.setSize(600, 200);
		JPanel panel = new JPanel();
		symbolLabel = new JLabel("Symbol: " + stock.getSymbol());
		lastPriceLabel = new JLabel("Last Price:");
		dateLabel = new JLabel("Date: ");
		timeLabel = new JLabel("Time: ");
		
		panel.add(symbolLabel);
		panel.add(lastPriceLabel);
		panel.add(dateLabel);
		panel.add(timeLabel);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	
		stock.fetchData();
	}
	
	@Override
	public void update() {
		System.out.println("\n" + stock.getSymbol() + " " + stock.getLastTrade() + " " + stock.getDate() + " " + stock.getTime());
		lastPriceLabel.setText("Last Price: " + stock.getLastTrade());
		dateLabel.setText("Date: " + stock.getDate());
		timeLabel.setText("Time: " + stock.getTime());
	}

}

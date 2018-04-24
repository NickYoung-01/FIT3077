import javax.swing.*;

public class StockMonitor extends JFrame implements Observer {

	private Stock stock;
	private JLabel symbolLabel, lastPriceLabel, dateLabel, timeLabel;
	
	public StockMonitor(Stock stock) {
		this.stock = stock;
		this.stock.registerObserver(this);
	}
	
	@Override
	public void update() {
		System.out.println("\n" + stock.getSymbol() + " " + stock.getLastTrade() + " " + stock.getDate() + " " + stock.getTime());
	}

}

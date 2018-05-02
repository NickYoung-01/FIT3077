import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {
	
	private HomeView view;
	private int existingIndex;
	private List<Stock> stockList = new ArrayList<Stock>();
	private ServerWSDL serverWSDL = new ServerWSDL();
	
	public Controller(HomeView view) {
		this.view = view;
		
		this.view.addMonitorButtonListener(new MonitorListener());
	}
	
	public boolean stockExists(String inputText) {
		for (int i = 0; i < stockList.size(); i++) {
			if (stockList.get(i).getSymbol().contains(inputText)) {
				System.out.println("We're monitoring this stock already!");
				existingIndex = i;
				return true;
			} 
		}
		return false;
	}
	
	public Stock createStock(String symbol){
		return new Stock(symbol, this.serverWSDL, 1);
	}
	
	class MonitorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String inputText = "";
			System.out.println("Button clicked");
			try {
				inputText = view.getInputText();
				//the stock exists, so just add another observer to it
				if (stockExists(inputText)) {
					new StockMonitor(stockList.get(existingIndex));
				} else {
					//stock doesn't exists, create a new stock and monitor
					stockList.add(createStock(inputText));
					int lastStockAddedIndex = stockList.size() - 1;
					new StockMonitor(stockList.get(lastStockAddedIndex));
				}
			} catch (Exception ex) {
				System.out.println(ex);
				view.displayErrorMessage("Error");
			}
		}
		
	}

}

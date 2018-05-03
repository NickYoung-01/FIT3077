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
		
		//Assign our MonitorListener to be the listener to the Monitor Button in HomeView
		this.view.addMonitorButtonListener(new MonitorListener());
	}
	
	//Check if the stock exists in our stockList
	public boolean stockIsBeingMonitored(String inputText) {
		for (int i = 0; i < stockList.size(); i++) {
			if (stockList.get(i).getSymbol().equals(inputText)) {
				//the index which contains that Symbol
				existingIndex = i;
				return true;
			} 
		}
		return false;
	}
	
	public Stock createStock(String symbol){
		return new Stock(symbol, this.serverWSDL, 5);
	}
	
	//This is our MonitorButton listener class
	class MonitorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String inputText = "";
			try {
				inputText = view.getInputText().toUpperCase();
				//the stock exists, so just add another observer to it
				if (stockIsBeingMonitored(inputText)) {
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

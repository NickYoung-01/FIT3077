import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import stockquotetimelapse.StockQuoteTimeLapseService;
import stockquotetimelapse.StockQuoteTimeLapseServicePortType;


public class Controller {
	
	private HomeView view;
	private int existingIndex;
	private List<Stock> stockList = new ArrayList<Stock>();
	private ServerLive serverWSDL = new ServerLive();
	private ServerHistoric serverHistoric = new ServerHistoric();
	
	public Controller(HomeView view) {
		this.view = view;
		
		//fill up the drop down with a list of available stocks
		List<String> historicStockList = serverHistoric.getSymbols();
		for (String stock: historicStockList) {
			view.setAvailableStockList(stock);
		}
		
		//Assign our MonitorListener to be the listener to the Monitor Button in HomeView
		this.view.addMonitorButtonListener(new MonitorListener());
		this.view.addServiceTypeComboListener(new ServiceTypeListener());
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
	
	public void createStock(String symbol, ServerAbstract server, int timeLimit){
		stockList.add(new Stock(symbol, server, timeLimit));
	}
	
	public void createMonitor(Stock stock, int monitorIndexType) {
		//create text monitor
		if (monitorIndexType == 0) {
			new StockMonitor(stock);
		} else {
			//create graph monitor
			new GraphMonitor(stock);
		}
	}
	
	class ServiceTypeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.handleServiceChange(view.getServiceTypeIndex());
		}
		
	}
	
	//This is our MonitorButton listener class
	class MonitorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			int monitorIndexType = view.getMonitorTypeIndex();
			String inputText = "";
			
			try {
				if (view.getServiceTypeIndex() == 0) {
					inputText = view.getInputText().toUpperCase();
					if(inputText.length() <= 3) {
					//the stock exists, so just add another observer to it
						if (stockIsBeingMonitored(inputText)) {
							createMonitor(stockList.get(existingIndex), monitorIndexType);
						} else {
							//stock doesn't exists, create a new stock and monitor
							createStock(inputText, serverWSDL, 60 * 5);
							//Remove stock if invalid
							int ind = stockList.size()-1;
							if (!stockList.get(ind).isValid()){
								stockList.remove(ind);
								view.displayErrorMessage("Invalid Symbol");
							} else{
								createMonitor(stockList.get(ind), monitorIndexType);
							}	
						}
					} else {
						view.displayErrorMessage("Input limit is 3 chars");
					}
				} else {
					//this is timelapse stuff
					if (stockIsBeingMonitored(view.getSelectedHistoricStock())) {
						createMonitor(stockList.get(existingIndex), monitorIndexType);
					} else {
						System.out.println(view.getSelectedHistoricStock());
						createStock(view.getSelectedHistoricStock(), serverHistoric, 5);
						int ind = stockList.size()-1;
						createMonitor(stockList.get(ind), monitorIndexType);
					}
				}
			} catch (Exception ex) {
				System.out.println(ex);
				view.displayErrorMessage("Error");
			}
		}
		
	}

}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {
	
	private MainView view;
	private List<Stock> stockList = new ArrayList<Stock>();
	private boolean stockMonitoring = false;
	int MINUTES = 1;
	
	public Controller(MainView view) {
		this.view = view;
		
		this.view.addMonitorButtonListener(new MonitorListener());
	}
	
	public void startTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
		    @Override
		    public void run() { 
		    		for (int i = 0; i < stockList.size(); i++) {
		    			stockList.get(i).fetchData();
		    		}
		    		System.out.println("----------------------------------------");
		    }
		 }, 0, 1000 * 60 * MINUTES);
	}
	
	class MonitorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String inputText = "";
			System.out.println("Button clicked");
			try {
				System.out.println(inputText);
				stockList.add(new Stock(inputText));
				int lastStockAddedIndex = stockList.size() - 1;
				System.out.println(stockList.size());
				new StockMonitor(stockList.get(lastStockAddedIndex));
				stockList.get(lastStockAddedIndex).fetchData();
				if (!stockMonitoring) {
					stockMonitoring = true;
					startTimer();
				}
			} catch (Exception ex) {
				System.out.println(ex);
				view.displayErrorMessage("Error");
			}
		}
		
	}

}

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
		 }, 0, 5000);
	}
	
	class MonitorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String inputText = "";
			System.out.println("Button clicked");
			try {
				inputText = view.getInputTexet();
				stockList.add(new Stock(inputText));
				new StockMonitor(stockList.get(0));
				stockList.get(0).fetchData();
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

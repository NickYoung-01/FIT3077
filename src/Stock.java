import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import stockquoteservice.*;

public class Stock implements Subject {

	private List<Observer> observers = new ArrayList<Observer>();
	private String symbol;
	private String lastTrade;
	private String date;
	private String time;
	private int timerMinutes;
	private ServerAbstract server;
	public Timer timer;
	
	public Stock(String symbol, ServerAbstract server, int timerMinutes) {
		this.server = server;
		this.symbol = symbol;
		this.timerMinutes = timerMinutes;
		startTimer(timerMinutes);
		fetchData();
	}
	
	//Get the data from the webservice
	public void fetchData() {
		List<String> quoteData = this.server.getQuote(symbol);
		setLastTrade((String) quoteData.get(1));
		setDate((String) quoteData.get(2));
		setTime((String) quoteData.get(3));
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void updateObserver() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
	
	public void startTimer(int timerMinutes) {
		timer = new Timer();
		timer.schedule(new TimerTask() {
		    @Override
		    public void run() { 
		    		fetchData();	
		    		if (observers.size() > 0) {
		    			updateObserver();
		    		}
//		    		updateObserver();
		    		System.out.println(observers.size());
		    		System.out.println("----------------------------------------");
		    }
		 }, 0, 1000 * 60 * timerMinutes);
	}

	public ServerAbstract getServer() {
		return this.server;
	}

	public void setService(ServerAbstract server) {
		this.server = server;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLastTrade() {
		return lastTrade;
	}

	public void setLastTrade(String lastTrade) {
		this.lastTrade = lastTrade;
	}

	/*
	 * Find better way to do this..maybe use simpledateformat?
	 * 
	 */
	public String getDate() {
		/*	
		int spaceIndex = date.indexOf("T");
		if (spaceIndex != -1)
		{
		    date = date.substring(0, spaceIndex);
		}
		return date;
		*/
		
		//date in format of yyyy-mm-dd
		return date.substring(0, 10);

	}

	public void setDate(String date) { 
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}

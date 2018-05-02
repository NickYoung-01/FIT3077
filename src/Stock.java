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
	private boolean timerCanFetchData = false;
	private int timerMinutes;
	private ServerAbstract server;
	public Timer timer;
	
	public Stock(String symbol, ServerAbstract server, int timerMinutes) {
		this.server = server;
		this.symbol = symbol;
		this.timerMinutes = timerMinutes;
		fetchData();
	}
	
	//Get the data from the webservice
	public void fetchData() {
		List<String> quoteData = this.server.getQuote(symbol);
		setLastTrade((String) quoteData.get(1));
		setDate((String) quoteData.get(2));
		setTime((String) quoteData.get(3));
		updateObserver();
	}

	@Override
	public void registerObserver(Observer o) {
		//if this is the first observer being added start the timer
		if (observers.isEmpty()) {
			startTimer(timerMinutes);
		}
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
		//if there is no more observers left stop fetching data
		if (observers.isEmpty()) {
			timer.cancel();
			timerCanFetchData = false;
		}
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
		    		System.out.println("im in the timer");
			    	if (!timerCanFetchData) {
			    		timerCanFetchData = true;
			    	} else {
			    		fetchData();	
			    	}
		    }
		    //Perform run every timerMinutes, i.e every 5 minutes
		 }, 0, 1000 * 60 * timerMinutes);
	}

	public boolean is_valid(){
		List<String> result = this.server.getQuote(symbol);		
		if (result.get(1).equals("Unset")){
			return false;
		} else{
			return true;
		}
		
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

	public String getLastTrade() {
		return lastTrade;
	}

	public void setLastTrade(String lastTrade) {
		this.lastTrade = lastTrade;
	}
	
	public String getDate() {
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

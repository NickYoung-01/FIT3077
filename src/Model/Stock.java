package Model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ServerAbstract.ServerAbstract;

public class Stock extends Subject {

	private List<Observer> observers = new ArrayList<Observer>();
	private String symbol;
	private String lastTrade;
	private String date;
	private String time;
	private int timeLimit;
	private ServerAbstract server;
	public Timer timer;
	
	public Stock(String symbol, ServerAbstract server, int timeLimit) {
		this.server = server;
		this.symbol = symbol;
		this.timeLimit = timeLimit;
		fetchData();
	}
	
	//Get the data from the webservice
	public void fetchData() {
		List<String> quoteData = this.server.getQuote(symbol);
		setLastTrade((String) quoteData.get(1));
		setDate((String) quoteData.get(2));
		setTime((String) quoteData.get(3));
		if (!observers.isEmpty()) {
			updateObserver();
		}
	}

	@Override
	public void registerObserver(Observer o) {
		//if this is the first observer being added start the timer
		if (observers.isEmpty()) {
			startTimer(timeLimit);
		}
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
		//if there is no more observers left stop fetching data
		if (observers.isEmpty()) {
			timer.cancel();
		}
	}

	@Override
	public void updateObserver() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
	
	public void startTimer(int timeLimit) {
		timer = new Timer();
		timer.schedule(new TimerTask() {
		    @Override
		    public void run() { 
			    	fetchData();	
		    }
		    //Perform run every timerMinutes, i.e every 5 minutes
		 }, 0, 1000 * timeLimit);
	}

	public boolean isValid(){
		List<String> result = this.server.getQuote(symbol);		
		if (result.get(1).equals("Unset")){
			return false;
		} else{
			return true;
		}
		
	}
	
	public int getTimeLimit() {
		return timeLimit;
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
		return date;
	}

	public Date getDateTime() {
		Date dateTime = null;
		String updateTime = "";
		if (time.contains("failed")) {
			updateTime = time.substring(16, 21);
		} else {
			updateTime = time;
		}
		try {
			dateTime = new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(getDate()+updateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateTime;
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

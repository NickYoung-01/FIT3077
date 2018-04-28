import java.util.ArrayList;
import java.util.List;

import stockquoteservice.*;

public class Stock implements Subject {

	private List<Observer> observers = new ArrayList<Observer>();
	private String symbol;
	private String lastTrade;
	private String date;
	private String time;
	private ServerAbstract server;
	
	public Stock(String symbol, ServerAbstract server) {
		this.server = server;
		this.symbol = symbol;
	}
	
	//Get the data from the webservice
	public void fetchData() {
		List<String> quoteData = this.server.getQuote(symbol);
		setLastTrade((String) quoteData.get(1));
		setDate((String) quoteData.get(2));
		setTime((String) quoteData.get(3));
		//tell our observer's that we have new info
		updateObserver();
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

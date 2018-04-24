import java.util.ArrayList;
import java.util.List;

import stockquoteservice.*;

public class Stock implements Subject {

	private List<Observer> observers = new ArrayList<Observer>();
	private StockQuoteWS service;
	private StockQuoteWSPortType port;
	private String symbol;
	private String lastTrade;
	private String date;
	private String time;
	
	public Stock(String symbol) {
		this.symbol = symbol;
		this.service = new StockQuoteWS();
		this.port = service.getStockQuoteWSSOAP11PortHttp();
	}
	
	public void fetchData() {
		List quoteData = port.getQuote(symbol);
		setLastTrade((String) quoteData.get(1));
		setDate((String) quoteData.get(2));
		setTime((String) quoteData.get(3));
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

	public StockQuoteWS getService() {
		return service;
	}

	public void setService(StockQuoteWS service) {
		this.service = service;
	}

	public StockQuoteWSPortType getPort() {
		return port;
	}

	public void setPort(StockQuoteWSPortType port) {
		this.port = port;
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

	public String getDate() {
		return date;
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

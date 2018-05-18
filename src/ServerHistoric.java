import java.text.SimpleDateFormat;
import java.util.List;

import stockquotetimelapse.StockQuoteTimeLapseService;
import stockquotetimelapse.StockQuoteTimeLapseServicePortType;

public class ServerHistoric extends ServerAbstract{
	
	private StockQuoteTimeLapseService quote;
	private StockQuoteTimeLapseServicePortType port;
	

	public ServerHistoric(){
		quote = new StockQuoteTimeLapseService();
		port = quote.getStockQuoteTimeLapseServiceHttpSoap11Endpoint();
		
	}
	@Override
	public List<String> getFieldNames() {
		return this.port.getFieldNames().getReturn();
	}

	@Override
	public List<String> getQuote(String symbol) {
		List<String> quoteData = port.getStockQuote(symbol);
		
		//convert date to our format standard
		SimpleDateFormat serverFormat = new SimpleDateFormat("dd/MM/yyyy");
		quoteData.set(2, super.convertDate(quoteData.get(2), serverFormat));
		System.out.println(quoteData.get(2));
		
		//convert price from cents to dollars
		double price = Double.parseDouble(quoteData.get(1)) / 100;
		quoteData.set(1, Double.toString(price));
		
		return quoteData;
	}
	
	public List<String> getSymbols(){
		return port.getSymbols().getReturn();
	}
	

}

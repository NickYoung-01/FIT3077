import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat serverFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
		    String formatConverted = myDateFormat.format(serverFormat.parse(quoteData.get(2)));
		    System.out.println(formatConverted);
		    quoteData.set(2, formatConverted);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		//convert price from cents to dollars
		double price = Double.parseDouble(quoteData.get(1)) / 100;
		quoteData.set(1, Double.toString(price));
		
		return quoteData;
	}
	
	public List<String> getSymbols(){
		List<String> symbols = this.port.getSymbols().getReturn();
		int i=0;
		for (String stock:symbols){
			symbols.set(i, stock.substring(0, 3));
			i++;
		}
		return symbols;
	}
	

}

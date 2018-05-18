import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import stockquoteservice.StockQuoteWS;
import stockquoteservice.StockQuoteWSPortType;

public class ServerLive extends ServerAbstract{
	
	private StockQuoteWS quote;
	private StockQuoteWSPortType quotePort;
	
	public ServerLive() {
		quote = new StockQuoteWS();
		this.quotePort = quote.getStockQuoteWSSOAP11PortHttp();
	}
	
	@Override
	public List<String> getFieldNames() {
		return quotePort.getFieldNames().getReturn();
	}

	@Override
	public List<String> getQuote(String symbol) {
		
		List<String> quoteData = quotePort.getQuote(symbol);
		
		//convert date to our format standard
		SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		try {
		    String formatConverted = myDateFormat.format(serverFormat.parse(quoteData.get(2)));
		    System.out.println(formatConverted);
		    quoteData.set(2, formatConverted);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		return quoteData;
	}

}

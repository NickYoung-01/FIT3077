package ServerAbstract;
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
		//get the data from the server
		List<String> quoteData = quotePort.getQuote(symbol);
		//convert the data to our format and set it
		SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		quoteData.set(2, super.convertDate(quoteData.get(2), serverFormat));
		System.out.println(quoteData.get(2));
		
		return quoteData;
	}

}

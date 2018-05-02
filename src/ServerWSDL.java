import java.util.List;

import stockquoteservice.GetFieldNamesResponse;
import stockquoteservice.StockQuoteWS;
import stockquoteservice.StockQuoteWSPortType;

public class ServerWSDL extends ServerAbstract{
	
	private StockQuoteWS quote;
	private StockQuoteWSPortType quotePort;
	
	public ServerWSDL() {
		quote = new StockQuoteWS();
		this.quotePort = quote.getStockQuoteWSSOAP11PortHttp();
	}
	
	@Override
	public List<String> getFieldNames() {
		return quotePort.getFieldNames().getReturn();
	}

	@Override
	public List<String> getQuote(String symbol) {
		return quotePort.getQuote(symbol);
	}

}

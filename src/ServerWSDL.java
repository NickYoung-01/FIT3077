import java.util.List;

import stockquoteservice.GetFieldNamesResponse;
import stockquoteservice.StockQuoteWS;
import stockquoteservice.StockQuoteWSPortType;

public class ServerWSDL extends ServerAbstract{
	
	private StockQuoteWS quote;
	private StockQuoteWSPortType quotePort;
	private GetFieldNamesResponse fieldsResponse;
	private List<String> fieldNames;
	
	public ServerWSDL() {
		quote = new StockQuoteWS();
		this.quotePort =quote.getStockQuoteWSSOAP11PortHttp();
		fieldsResponse = quotePort.getFieldNames();
		this.fieldNames = fieldsResponse.getReturn();
	}
	
	@Override
	public List<String> getFieldNames() {
		return this.fieldNames;
	}

	@Override
	public List<String> getQuote(String symbol) {
		return quotePort.getQuote(symbol);
	}

}

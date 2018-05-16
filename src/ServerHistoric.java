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
		return this.port.getStockQuote(symbol);
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

import java.util.List;

public abstract class ServerAbstract {
	
	public ServerAbstract(){}
	
	public abstract List<String> getFieldNames();
	public abstract List<String> getQuote(String symbol);
	
	
	
}

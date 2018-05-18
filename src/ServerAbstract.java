import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public abstract class ServerAbstract {
	
	private SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public ServerAbstract(){}
	
	public abstract List<String> getFieldNames();
	public abstract List<String> getQuote(String symbol);
	
	public String convertDate(String date) {
		String dateConverted = "";
		//convert date to our format standard
		SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		try {
			dateConverted = myDateFormat.format(serverFormat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateConverted;
	}
	
}

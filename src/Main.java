
public class Main {
	
	/*
	 * TODO:
	 * 1. If they want to view the same stock twice, don't keep creating a new stock object
	 * 		query the arraylist of stocks and find the one that matches what we want.
	 * 2. Adjust the timer to refresh evrey 5mins
	 * 3. Cacnel the timer when all monitors are closed
	 */
	
	public static void main(String[] args) {
		MainView view = new MainView();
		Controller controller = new Controller(view);
		
		view.setVisible(true);
		
		
	}

}

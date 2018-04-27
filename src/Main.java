
public class Main {
	
	/*
	 * TODO:
	 * 3. Cacnel the timer when all monitors are closed
	 */
	
	public static void main(String[] args) {
		MainView view = new MainView();
		Controller controller = new Controller(view);
		
		view.setVisible(true);
		
		
	}

}

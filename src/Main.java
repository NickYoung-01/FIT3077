import Controller.Controller;
import View.*;

public class Main { 
	
	public static void main(String[] args) {
		//Controller takes a view as it's parameter
		HomeView view = new HomeView();
		Controller controller = new Controller(view);
		
		//Make the HomeView visible
		view.setVisible(true);
	}

}

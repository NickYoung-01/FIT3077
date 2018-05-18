package Model;

public abstract class Subject {
	
	public abstract void registerObserver(Observer o);
	public abstract void removeObserver(Observer o);
	public abstract void updateObserver();
}

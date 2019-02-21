public class TurnCounter {
	// Instance variables
	private int turn = 0;
	private int day = 1;
	private int year = 1;
	private int antCount;
	
	// Constructor
	public TurnCounter() {
	}
		
	// Mutator Methods
	public void incrementTurnCount() {
		turn++;
	}
	
	public void incrementDayCount(AntSimulator antSimulator) {
		day++;
		setTurnCount(0);
		antSimulator.reduceAllSquaresPheromoneLevel();
	}
	
	public void incrementYearCount() {
		year++;
		setDayCount(0);
		setTurnCount(0);
	}
	
	public void move() {
		
	}
	
	public void setTurnCount(int Turn) {
		this.turn = Turn;
	}
	
	public void setDayCount(int Day) {
		this.day = Day;
	}
	
	public void setYearCount(int Year) {
		this.year = Year;
	}
	
	public void resetTurnCounter() {
		this.year = 1;
		this.day = 1;
		this.turn = 0;
	}
	
	// Accessor Methods
	public int getTurnCount() {
		return turn;
	}
	
	public int getDayCount() {
		return day;
	}
	
	public int getYearCount() {
		return year;
	}
	
	public String timeToString() {
		String time = "Year " + this.getYearCount() + ", Day " + this.getDayCount() + ", Turn " + this.getTurnCount();
		return time;
	}
	
}

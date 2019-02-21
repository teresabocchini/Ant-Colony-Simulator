/*SScout Ant Requirements:
 * 
 *Scouts are responsible for enlarging the foraging area available to the foragers. 
 *The specific requirements for the scout ant are:
 *   Scouts should always randomly pick one of the eight possible directions of movement 
 *    when it is their turn to do something.
 *   	If the chosen square is open, the scout should simply a. move into that square.
 *   	If the chosen square is closed, the scout should move into that square and the contents 
 *   	 of that square should be revealed.
 *   Whenever a closed square is revealed, there is a chance of there being food in the square, 
 *    according to the following frequency:
 *   	There is a 25% chance that the square will contain a random amount of food between 500 and
 *   	 1000 units.
 *   	The other 75% of the time the square is empty.
 *   	You can predetermine the contents of all the squares at the beginning of the simulation, 
 *   	 or you can dynamically determine the contents of each square as it is opened.
 */

public class Scout 
	extends Ant {
	
	// Instance Variables
	private boolean view;
	private int foodUnit;

	// Constructors
	public Scout() {
	}

	// Mutator Methods	
	public void setSquareView(boolean View) {
		this.view = View;
	}
	
	public void setFoodContent(int FoodUnit) {
		this.foodUnit = FoodUnit;
	}
	
	// Accessor Methods
	public boolean getSquareView() {
		return view;
	}
	
	public int getFoodContent() {
		return foodUnit;
	}
}

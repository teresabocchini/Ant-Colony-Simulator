/* Queen Ant Requirements:
 * 
 * The queen never moves from her square.
 * The queen's maximum lifespan is 20 years.
 * The queen hatches new ants at a constant rate of 1 ant/day (i.e., 1 ant every 10 turns).
 * New ants should always be hatched on the first turn of each day.
 * The type of ant that is hatched should be determined randomly according to the initial frequencies listed below. You may
 * 		change these frequencies as you see fit — these are simply suggestions for a starting point.
 * 			a. Forager - 50%
 * 			b. Scout - 25%
 * 			c. Soldier - 25%
 * The queen should consume 1 unit of the food in her chamber on each turn, including the turn in which she hatches a new ant.
 * If the food level in the queen's square is zero when the queen tries to eat, the queen dies of starvation.
 * If the queen dies, either by starvation or by a Bala attack, the simulation should end immediately.
 */
public class Queen
	extends Ant {

	// Constructors
	public Queen() {
	}
	
	// Mutator Methods
	public void hatchAnt(int randomNumber, AntSimulator antSimulator) {
		int AntType = randomNumber % 4;
		if (AntType == 0) {
			antSimulator.addScoutAnt(14, 14, 1);
		} 
		else if (AntType == 1) {
			antSimulator.addSoldierAnt(14, 14, 1);
		}
		else {
			antSimulator.addForagerAnt(14, 14, 1);
		}
	}
	
	public void eat(Square square) {
		square.decrementFoodCount();
	}
}

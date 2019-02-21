/*Forager Ant Requirements: 
 * 
 *Foragers are responsible for bringing food to the queen.
 *They have two primary modes of behavior: forage mode and return-to-nest mode. 
 *The specific requirements for the forager ant are:
 * 
 *	Forage Mode:
 * 		Foragers are considered to be in forage mode whenever they are not carrying food.
 * 		In Forage Mode, foragers should always move to the adjacent square containing the highest
 * 		 level of pheromone, except:
 * 	 		If more than one adjacent square has the same level of pheromone they should randomly
 * 			 pick one of those squares.
 * 			When following a pheromone trail a forager should never move into the square it just 
 * 			 came from unless it has no other choice.
 * 			Depending on how you implement your movement algorithm, it is possible for a forager to
 * 			 get stuck in a loop, traveling round and round the same squares without getting anywhere.
 * 			 Try to detect when this happens, and prevent the endless looping.
 * 		Foragers should maintain a history of their movement, to be used when they need to return to 
 * 		 the nest.
 * 		When a forager enters a square containing food, it should pick up 1 unit of food, unless it
 * 		 is already carrying food.
 * 		When a forager picks up a unit of food, it enters return-to-nest mode.
 * 		Foragers should never pick up food from the square containing the queen.
 * 		After a forager has picked up 1 unit of food, it should not move again until the next turn.
 * 
 *	Return-to-nest Mode:
 * 		When a forager is carrying food, it should retrace its steps exactly back to the colony
 * 		 entrance; i.e., it should backtrack whatever path it took to get to the food.
 * 		Foragers should ignore pheromone in this mode; i.e., they should not move to the adjacent 
 * 		 square containing the highest level of pheromone.
 * 		Foragers should not move randomly in this mode.
 * 		Foragers should deposit 10 units of pheromone in each square along the way back to the
 * 		 colony entrance, including the square in which the food was found, but excluding the
 * 		 colony entrance (the queen's square).
 * 		Foragers should only deposit pheromone in a given square if the current pheromone total
 * 		 in the square is <1000.
 * 		A forager may deposit pheromone in one square, and move to a new square in the same turn.
 * 		When a forager reaches the colony entrance, it should add the food it is carrying to the
 * 		 food supply in that square, in the same turn in which it entered the colony entrance.
 * 		Foragers should not move out of the colony entrance on the same turn they deliver food there.
 * 		If a forager dies while carrying food, the food it was carrying should remain in the square
 * 		 in which the forager died.
 * 		When a forager has deposited food at the nest, the forager re-enters forage mode, and its
 * 		 movement history should be reset.

 * 
 * 
 */

public class Forager 
	extends Ant {
	
	// Instance Variables
	private String behaviorMode;
	private boolean carryingFood;
	private Square lastSquare = new Square();
	private Square currentSquare = new Square();
	private int foodUnit;
	private int pheromoneLevel;
	private Stack movementHistory = new ArrayStack();
	private Queue comparableMovementHistory = new ArrayQueue();
		
	// Constructors
	public Forager() {
	}
	
	// Mutator Methods	
	public void setBehaviorMode(String BehaviorMode) {
		this.behaviorMode = BehaviorMode;
	}
	
	public void setCarryingFood(boolean CarryingFood) {
		this.carryingFood = CarryingFood;
	}
	
	public void setLastSquare(Square square) {
		this.lastSquare = square;
	}
	
	public void pushMovementHistory(Square square) {
		this.movementHistory.push(square);
		this.comparableMovementHistory.enqueue(square);
	}
	
	public void setFoodUnit(int FoodUnit) {
		this.foodUnit = FoodUnit;
	}
	
	public void setPheromoneLevel(int PheromoneLevel) {
		this.pheromoneLevel = PheromoneLevel;
	}
	
	public void clearMovementHistory() {
		this.movementHistory.clear();
		this.comparableMovementHistory.clear();
	}
	
	public Square popMovementHistory() {
		return (Square) this.movementHistory.pop();
	}
	
	public void dequeueComparableMovementHistory() {
		this.comparableMovementHistory.dequeue();
	}
	
	public void dropFood(AntSimulator antSimulator, Square square) {
		this.setCarryingFood(false);
		antSimulator.addFoodUnit(square.getCoordinateX(), square.getCoordinateY(), 1);
		
	}
	
	// Accessor Methods
	public String getBehaviorMode() {
		return behaviorMode;
	}
	
	public boolean getCarryingFood() {
		return carryingFood;
	}
	
	public Square getLastSquare() {
		return lastSquare;
	}
	
	public Stack getMovementHistory() {
		return movementHistory;
	}
	
	public Queue getComparableMovementHistory() {
		return comparableMovementHistory;
	}
	
	public int getFoodUnit() {
		return foodUnit;
	}
	
	public boolean getQueenExists() {
		if (currentSquare.getQueenExistence()) {
			return true;
		}
		return false;
	}
	
	public int getPheromoneLevel() {
		return pheromoneLevel;
	}
}
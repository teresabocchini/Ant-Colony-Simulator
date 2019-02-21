/*  All Ant Requirements:
 * 
 * Each ant should be identified by a unique integer ID. The queen ant should have an ID value of 0. Other ants should be numbered in
 * 		ascending order as they are hatched.
 * All ant types (except for the queen) have a maximum life span of 1 year.
 * Dead ants should be removed from the simulation.
 * All ants are limited to one action per turn, with some exceptions that will be discussed later.
 * All ants except Bala ants may only move in squares that have been revealed by scout ants; Bala ants may also move into squares
 *		that have not been revealed by scout ants.
 * When moving, all ant types should move no more than 1 square per turn. 
 */
public class Ant {
	// Instance Variables
	private int id;
	private int age;
	private int x;
	private int y;
	private boolean dead;
	
	// Constructor Methods
	public Ant() {
	}
	
	// Mutator Methods
	public void move(Square currentSquare, Square newSquare, AntSimulator antSimulator, Square[][] nodeList) {
		
		
		if (this instanceof Bala) {
			currentSquare.decrementBalaCount();
			newSquare.incrementBalaCount();
			this.setLocation(newSquare.getCoordinateX(), newSquare.getCoordinateY());
		}
		else if (this instanceof Forager) {
			currentSquare.decrementForagerCount();
			newSquare.incrementForagerCount();
			this.setLocation(newSquare.getCoordinateX(), newSquare.getCoordinateY());
		}
		else if (this instanceof Scout) {
			currentSquare.decrementScoutCount();
			newSquare.incrementScoutCount();
			this.setLocation(newSquare.getCoordinateX(), newSquare.getCoordinateY());
			
			if (newSquare.isShowing() == false) {
				int randomNumber = antSimulator.getRandomNumber() % 4;
				// 25% of the time, generate a random amount of food between 500 and 1000
				if (randomNumber == 0) {
					int randomFoodAmount = (antSimulator.getRandomNumber() % 500) + 500;
					antSimulator.addFoodUnit(newSquare.getCoordinateX(), newSquare.getCoordinateY(), randomFoodAmount);
					newSquare.showNode();
				}
				
				// Show node
				newSquare.showNode();
			}
		}
		else if (this instanceof Soldier) {
			currentSquare.decrementSoldierCount();
			newSquare.incrementSoldierCount();
			this.setLocation(newSquare.getCoordinateX(), newSquare.getCoordinateY());
		}
	}
	
	public void removeFromSimulation(Square currentSquare, AntSimulator antSimulator) {     /*removes ant view from simulation upon death */
		if (this.getDead()) {
			if (this instanceof Bala && currentSquare.getBalaCount() > 0) {
				antSimulator.removeBalaAnt((Bala) this, currentSquare.getCoordinateX(), currentSquare.getCoordinateY());;
			}
			else if (this instanceof Forager && currentSquare.getForagerCount() > 0) {
				((Forager) this).dropFood(antSimulator, currentSquare);
				antSimulator.removeForagerAnt((Forager) this, currentSquare.getCoordinateX(), currentSquare.getCoordinateY());
			}
			else if (this instanceof Scout && currentSquare.getScoutCount() > 0) {
				antSimulator.removeScoutAnt((Scout) this, currentSquare.getCoordinateX(), currentSquare.getCoordinateY());
			}
			else if (this instanceof Soldier && currentSquare.getSoldierCount() > 0) {
				antSimulator.removeSoldierAnt((Soldier) this, currentSquare.getCoordinateX(), currentSquare.getCoordinateY());
			}
		}
	}
	
	public void setID(int ID) {
		this.id = ID;
	}
	
	public void setAge(int Age) {
		this.age = Age;
	}
	
	public void setDead(boolean Dead) {
		this.dead = Dead;
	}
	
	public void setLocation(int X, int Y) {
		this.x = X;
		this.y = Y;
	}
	
	public Map attack(AntSimulator antSimulator, Object AttackeeAnt, Map AntList, Square[][] nodeList) {
		int randomNumber = antSimulator.getRandomNumber() % 2;
		int ID = ((Ant) AttackeeAnt).getID();
		if (randomNumber == 0) {
			// Attack Kills Enemy Ant
			Ant Ant = (Ant) AntList.get(ID);
			Ant.setDead(true);
			
			if (Ant instanceof Queen) {
				antSimulator.causeOfCivilizationEnd = "A Bala Ant killed the Queen.";
				antSimulator.endSimulation();
			}
			else {
				Ant.removeFromSimulation(Ant.getSquare(nodeList), antSimulator);
			}
		}
		else {
			// Attack Misses, nothing happens
		}
		
		return AntList;
	}
	
	// Accessor Methods
	public int getID() {
		return id;
	}
	
	public int getAge() {
		return age;
	}
	
	public boolean getDead() {
		return dead;
	}
	
	public int getXCoordinate() {
		return x;
	}
	
	public int getYCoordinate() {
		return y;
	}
	
	public Square getSquare(Square[][] nodeList) {
		return nodeList[this.x][this.y];
	}
}

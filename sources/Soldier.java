/*Soldiers are responsible for protecting the colony by fighting the enemy Bala ants. 
 *Soldier ants have two primary modes of behavior: scout mode and attack mode. 
 *The specific requirements for the soldier ant are:
 * 
 *	Scout Mode:
 * 		A soldier is in scout mode when it is in a square that does not contain any Bala ants.
 * 		While in scout mode:
 * 			If there are one or more Bala ants in one or more of the squares adjacent to the square 
 * 			 the soldier is in, the soldier should move into any one of the squares containing 
 * 			 a Bala ant.
 * 		If there are no Bala ants in any of the adjacent squares, the soldier should move randomly.
 *	
 *	Attack Mode:
 *		A soldier is in attack mode when it is in a square that contains one or more Bala ants.
 *		 Attack mode takes precedence over scout mode. 
 *		While in attack mode, a soldier should attack any Bala ants present.
 *		If there are multiple Bala ants present, only one of them should be attacked.
 *		During an attack, there is a 50% chance the soldier kills the enemy ant; 
 *		 otherwise, the soldier misses and the enemy ant survives. 
 */


public class Soldier 
	extends Ant {
	
	// Instance Variables
	private String behaviorMode;
	private int balaAntCount;
	
	// Constructors
	public Soldier() {
	}
	
	// Mutator Methods
	public void setBehaviorMode(String BehaviorMode) {
		this.behaviorMode = BehaviorMode;
	}
	
	public void setBalaAntCount(int BalaAntCount) {
		this.balaAntCount = BalaAntCount;
	}
	
	// Accessor Methods
	public String getBehaviorMode() {
		return behaviorMode;
	}
	
	public int getBalaAntCount() {
		return balaAntCount;
	}
}

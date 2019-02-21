/*Bala Ant Requirements:
 * 
 *Bala ants are enemies of the colony. They should enter only at the periphery of the colony
 *(i.e., they should not simply pop up in the middle of the colony). Once in the colony they
 *may move around freely. Assume they never leave the colony once they enter it.
 *The specific requirements for the Bala ant are:
 *	
 *	Each turn there is a 3% chance one Bala ant will appear in one of the squares at the boundary
 *	 of the colony. You may choose to have Bala ants always enter at the same square 
 *	 (e.g., upper left corner), or you may have them enter randomly at any of the 106 squares on
 *	 the edge of the colony.
 *	Once a Bala appears, it should remain in the environment until it is killed, or dies of old age.
 *	Bala ants should always move randomly.
 *	Bala ants may move into squares that have not yet been revealed by scout ants.
 *	If a Bala ant is in a square containing one or more friendly ants (scout, forager, soldier, queen),
 *	 the Bala should attack one of those ants. The ant that is attacked can be selected at random,
 *	 or you can pick which ant gets attacked.
 *	During an attack, there is a 50% chance a Bala kills the ant it attacks; otherwise, the Bala
 *	 misses and the ant that is attacked survives.
 */

public class Bala
	extends Ant {
		
	// Instance Variables
	private int queenAntCount;
	private int scoutAntCount;
	private int soldierAntCount;
	private int foragerAntCount;
		
	// Constructors
	public Bala() {
	}
	
	// Mutator Methods
	public void setQueenAntCount(int QueenAntCount) {
		this.queenAntCount = QueenAntCount;
	}
	
	public void setScoutAntCount(int ScountAntCount) {
		this.scoutAntCount = ScountAntCount;
	}
	
	public void setSoldierAntCount(int SoldierAntCount) {
		this.soldierAntCount = SoldierAntCount;
	}
	
	public void setForagerAntCount(int ForagerAntCount) {
		this.foragerAntCount = ForagerAntCount;
	}
	
	//Accessor Methods
	public void getEntrySquare() {
		// TODO
	}
	
	public int getQueenAntCount() {
		return queenAntCount;
	}
	
	public int getScoutAntCount() {
		return scoutAntCount;
	}
	
	public int getSoldierAntCount() {
		return soldierAntCount;
	}
	
	public int getForagerAntCount() {
		return foragerAntCount;
	}
	
}

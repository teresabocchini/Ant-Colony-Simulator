
public class Turn {
	// Instance Variables
	PriorityQueue pheromonePriority = new PriorityQueue();
	
	// Constructors
	public Turn() {
	}
	
	// Mutator Methods
	public Map balaTurn(Bala balaAnt, AntSimulator antSimulator, Square[][] nodeList, Map AntList) {
		Square currentSquare = balaAnt.getSquare(nodeList);
		Map friendlyAntList = currentSquare.getFriendlyAntsinSquare(AntList, antSimulator, nodeList);
		
		
		if (friendlyAntList.size() > 0) {
			Queue friendlyAntQueue = antSimulator.addToQueue(friendlyAntList);
			Object friendlyAntKey = friendlyAntQueue.dequeue();
			Ant friendlyAnt = (Ant) AntList.get(friendlyAntKey);
			
			AntList = balaAnt.attack(antSimulator, friendlyAnt, AntList, nodeList);
			
		}
		else {
			Queue adjacentSquares = antSimulator.getAdjacentSquares(currentSquare);
		
			int randomNumber = antSimulator.getRandomNumber() % adjacentSquares.size();
			for (int x = 0; x < randomNumber; x++) {
				adjacentSquares.dequeue();
			}
		
			balaAnt.move(currentSquare, (Square) adjacentSquares.dequeue(), antSimulator, nodeList);	
		}
		
			
		return AntList;
	}
	
	public void foragerTurn(Forager foragerAnt, AntSimulator antSimulator, Square[][] nodeList) {
		// set behaviorMode		
		if (foragerAnt.getCarryingFood()) {
			foragerAnt.setBehaviorMode("NEST");
		}
		else {
			foragerAnt.setBehaviorMode("FORAGE");
		}
		
		Square currentSquare = foragerAnt.getSquare(nodeList);
		// Perform Action based off mode
		if (foragerAnt.getBehaviorMode().compareTo("NEST") == 0) {
			// Return to nest by using movement history stack
			// Retrace last square until back to queen
			Square nextSquare = foragerAnt.popMovementHistory();
			foragerAnt.move(currentSquare, nextSquare, antSimulator, nodeList);
					
			if (nextSquare.getQueenExistence()) {
				
				foragerAnt.dropFood(antSimulator, nextSquare);
				foragerAnt.setBehaviorMode("FORAGE");
				foragerAnt.clearMovementHistory();
				if (currentSquare.getPheromoneLevel() < 1000) {
					antSimulator.addPheromoneUnit(currentSquare.getCoordinateX(), currentSquare.getCoordinateY(), 10);
				}
				}
			else {
				if (currentSquare.getPheromoneLevel() < 1000) {
					antSimulator.addPheromoneUnit(currentSquare.getCoordinateX(), currentSquare.getCoordinateY(), 10);
				}
			}
		}
		else {
			Queue adjacentSquares = antSimulator.getAdjacentSquares(currentSquare);
			Queue adjacentVisibleSquares = new ArrayQueue();
			Queue adjacentVisibleSquaresWithoutHistory = new ArrayQueue();
			int numberOfAdjacentSquares = adjacentSquares.size();			
			
			// Will ignore last square and will only add pheromoneLevel if the square is visible.
			for (int x = 0; x < numberOfAdjacentSquares; x++) {
				Square square = (Square) adjacentSquares.dequeue();
				if (!antSimulator.compareToMovementStack(foragerAnt, square)) {
					if (square.isVisible()) {
						pheromonePriority.add((int) square.getPheromoneLevel());
						
						// Add to adjacent visible squares queue
						adjacentVisibleSquaresWithoutHistory.add(square);
						
						// Add to adjacent Squares queue just in case
						adjacentVisibleSquares.add(square);
					}
				}
				else {
					// Add to backup adjacent squares queue in case all visible squares have been visited
					if (square.isVisible()) {
						// Add to adjacent Squares queue just in case
						adjacentVisibleSquares.add(square);
					}
				}
			}
			
			// Move following pheromone trails until finding a square with food
			Queue samePheromoneLevelSquares = new ArrayQueue();
			Square newSquare = new Square();
			
			// Get highest priority pheromone Level
			if (pheromonePriority.size() == 0) {   /* This means it has visited all other squares that are visible */
				/* move to any adjacent visible square */
				if (adjacentVisibleSquares.size() == 1) {
				}
				else {				/* Pick one at random */
					int randomNumber = antSimulator.getRandomNumber() % adjacentVisibleSquares.size();
					for (int number = 0; number < randomNumber; number++) {
						adjacentVisibleSquares.dequeue();
					}
				}
				
				newSquare = (Square) adjacentVisibleSquares.dequeue();
				
			}
			else {  /* Use pheromone level */
				int highestLevel = (int) pheromonePriority.get();
				pheromonePriority.remove();
				
				// If any adjacent squares have the highest level of pheromone, add it to the same level squares queue
				while (adjacentVisibleSquares.size() != 0) {
					Square containsLevel = (Square) adjacentVisibleSquares.dequeue();				
					if (containsLevel.getPheromoneLevel() == highestLevel) {
						samePheromoneLevelSquares.add(containsLevel);
					}
				}
				
				// Move depending on how many squares are available in queue and pheromone level
				if (samePheromoneLevelSquares.size() == 1) {
				}
				else {				/* Pick one at random */
					int randomNumber = antSimulator.getRandomNumber() % samePheromoneLevelSquares.size();
					for (int number = 0; number < randomNumber; number++) {
						samePheromoneLevelSquares.dequeue();
					}
				}
				
				newSquare = (Square) samePheromoneLevelSquares.dequeue();
			}
			
			foragerAnt.move(currentSquare, newSquare, antSimulator, nodeList);
			foragerAnt.setLastSquare(currentSquare);
			foragerAnt.pushMovementHistory(currentSquare);
			
			if (!(foragerAnt.getCarryingFood())) {
				if ((foragerAnt.getSquare(nodeList).getFoodCount() > 0) && !foragerAnt.getSquare(nodeList).getQueenExistence()) {
					// Pick up food
					foragerAnt.getSquare(nodeList).decrementFoodCount();
					foragerAnt.setCarryingFood(true);
				}
			}
		}
		pheromonePriority.clear();
	}
	
	public void scoutTurn(Scout scoutAnt, AntSimulator antSimulator, Square currentSquare, Square[][] nodeList) {
		Queue adjacentSquares = antSimulator.getAdjacentSquares(currentSquare);
		int numberOfSquares = adjacentSquares.size();
		int randomNumber = antSimulator.getRandomNumber() % numberOfSquares;
		Square newSquare = new Square();
		
		for (int x = 0; x < randomNumber; x++) {
			adjacentSquares.dequeue();
		}
		
		newSquare = (Square) adjacentSquares.dequeue();
		
		// Move to new Square
		scoutAnt.move(currentSquare, newSquare, antSimulator, nodeList);
	
	}
	
	public Map soldierTurn(Soldier soldierAnt, AntSimulator antSimulator, Square[][] nodeList, Map AntList) {
		// set behaviorMode	
		if (soldierAnt.getSquare(nodeList).getBalaCount() <= 0) {
			soldierAnt.setBehaviorMode("SCOUT");
		}
		else {
			soldierAnt.setBehaviorMode("ATTACK");
		}
		
		if (soldierAnt.getBehaviorMode().compareTo("SCOUT") == 0) {
			Square currentSquare = soldierAnt.getSquare(nodeList);
			Queue adjacentSquares = antSimulator.getAdjacentSquares(currentSquare);
			Queue squaresWithEnemies = new ArrayQueue();
			Queue squaresWithoutEnemies = new ArrayQueue();
			
			// Seperate into queues with enemies or no enemies
			while (adjacentSquares.size() != 0) {
				Square square = (Square) adjacentSquares.dequeue();
				if (square.getBalaCount() > 0 && square.isVisible() == true) {
					squaresWithEnemies.add(square);
				}
				else if (square.getBalaCount() == 0 && square.isVisible() == true) {
					squaresWithoutEnemies.add(square);
				}
			}
					
			// If an enemy exists, move to one of those squares, else move to any square
			
			if (squaresWithEnemies.size() > 0) {
				int numberOfSquares = squaresWithEnemies.size();
				int randomNumber = antSimulator.getRandomNumber() % numberOfSquares;
				
				for (int x = 0; x < randomNumber; x++) {
					squaresWithEnemies.dequeue();
				}
				
				soldierAnt.move(soldierAnt.getSquare(nodeList), (Square) squaresWithEnemies.dequeue(), antSimulator, nodeList);
			}
			else {
				int numberOfSquares = squaresWithoutEnemies.size();
				int randomNumber = antSimulator.getRandomNumber() % numberOfSquares;
				
				for (int x = 0; x < randomNumber; x++) {
					squaresWithoutEnemies.dequeue();
				}
				
				soldierAnt.move(soldierAnt.getSquare(nodeList), (Square) squaresWithoutEnemies.dequeue(), antSimulator, nodeList);
			}
		}
		else {
			Square currentSquare = soldierAnt.getSquare(nodeList);
			Map BalaAntList = currentSquare.getBalaAntsinSquare(AntList, antSimulator, nodeList);
			Queue BalaAntQueue = antSimulator.addToQueue(BalaAntList);
			
			Object enemyAntKey = BalaAntQueue.dequeue();
			Bala enemyAnt = (Bala) AntList.get(enemyAntKey);
			
			
			AntList = soldierAnt.attack(antSimulator, enemyAnt, AntList, nodeList);
		}
		return AntList;
	}
	
	public void queenTurn(Queen queenAnt, Square square, TurnCounter turnCounter, AntSimulator antSimulator) {
		// Check food content and eat or die of starvation
		if (square.getFoodCount() > 0) {
			queenAnt.eat(square);
		}
		else {
			queenAnt.setDead(true);
			antSimulator.causeOfCivilizationEnd = "The Queen starved to death.";
			antSimulator.endSimulation();
		}
		
		// Check life span
		if (queenAnt.getAge() == 1) {
			queenAnt.setDead(true);
			antSimulator.causeOfCivilizationEnd = "The Queen died of old age.";
			antSimulator.endSimulation();
		}
		
		// Hatch an Ant
		if (turnCounter.getTurnCount() == 1) {
			queenAnt.hatchAnt(antSimulator.getRandomNumber(), antSimulator);
		}
	}

	
	
}

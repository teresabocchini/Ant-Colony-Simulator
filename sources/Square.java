@SuppressWarnings("serial")
public class Square extends ColonyNodeView {
	// Instance variables
	private int x;
	private int y;
	private int balaCount;
	private int foragerCount;
	private int scoutCount;
	private int soldierCount;
	private int foodCount;
	private int pheromoneLevel;
	private boolean queenExists = false;
	
	// Constructor Methods
	public Square() {
	}
	
	// Mutator Methods
	public void setCoordinateX(int X) {
		this.x = X;
	}
	
	public void setCoordinateY(int Y) {
		this.y = Y;
	}
	
	public void setCoordinates(int X, int Y) {
		this.x = X;
		this.y = Y;
	}
	
	public void setQueenExistance(boolean QueenExists) {
		if (QueenExists) {
			this.setQueen(QueenExists);
			this.showQueenIcon();
			this.queenExists = QueenExists;
		}
		else {
			this.setQueen(QueenExists);
			this.hideQueenIcon();
		}
	}
	
	public void incrementBalaCount() {
		balaCount++;
		this.setBalaCount(balaCount);
		
		
		// Show Icon if 1 or more exist
		if (balaCount > 0) {
			this.showBalaIcon();
		}
	}
	
	public void incrementForagerCount() {
		foragerCount++;
		this.setForagerCount(foragerCount);
		
		// Show Icon if 1 or more exist
		if (foragerCount > 0) {
			this.showForagerIcon();
		}
	}
	
	public void incrementScoutCount() {
		scoutCount++;
		this.setScoutCount(scoutCount);
		
		// Show Icon if 1 or more exist
		if (scoutCount > 0) {
			this.showScoutIcon();
		}
	}
	
	public void incrementSoldierCount() {
		soldierCount++;
		this.setSoldierCount(soldierCount);
		
		// Show Icon if 1 or more exist
		if (soldierCount > 0) {
			this.showSoldierIcon();
		}
	}
	
	public void incrementFoodCount() {
		foodCount++;
		this.setFoodAmount(foodCount);
	}
	
	public void incrementPheromoneCount() {
		pheromoneLevel++;
		this.setPheromoneLevel(pheromoneLevel);
	}
	
	public void decrementBalaCount() {
		balaCount--;
		this.setBalaCount(balaCount);
		
		// Hide Icon if no more exist
		if (balaCount == 0) {
			this.hideBalaIcon();
		}
	}
	
	public void decrementForagerCount() {
		foragerCount--;
		this.setForagerCount(foragerCount);
		
		// Hide Icon if none exist
		if (foragerCount == 0) {
			this.hideForagerIcon();
		}
	}
	
	public void decrementScoutCount() {
		scoutCount--;
		this.setScoutCount(scoutCount);
		
		// Hide Icon if none exist
		if (scoutCount == 0) {
			this.hideScoutIcon();
		}
	}
	
	public void decrementSoldierCount() {
		soldierCount--;
		this.setSoldierCount(soldierCount);
		
		// Hide Icon if none exist
		if (soldierCount == 0) {
			this.hideSoldierIcon();
		}
	}
	
	public void decrementFoodCount() {
		foodCount--;
		this.setFoodAmount(foodCount);
	}
	
	public void decrementPheromoneCount() {
		pheromoneLevel--;
		this.setPheromoneLevel(pheromoneLevel);
	}
	
	public void resetSquareCounts() {
		this.setBalaCount(0);
		this.setForagerCount(0);
		this.setSoldierCount(0);
		this.setScoutCount(0);
		this.setFoodAmount(0);
		this.setPheromoneLevel(0);
	}
	
	// Accessor methods
	public int getCoordinateX() {
		return x;
	}
	
	public int getCoordinateY() {
		return y;
	}
	
	public String getCoordinates() {
		String coordinates = x + ", " + y;
		return coordinates;
	}
	
	public boolean getQueenExistence() {
		return queenExists;
	}
	
	public int getBalaCount() {
		return balaCount;
	}
	
	public int getForagerCount() {
		return foragerCount;
	}
	
	public int getScoutCount() {
		return scoutCount;
	}
	
	public int getSoldierCount() {
		return soldierCount;
	}
	
	public int getFoodCount() {
		return foodCount;
	}
	
	public int getPheromoneLevel() {
		return pheromoneLevel;
	}
	
	public Map getBalaAntsinSquare(Map AntList, AntSimulator antSimulator, Square[][] nodeList) {
		Map squareBalaAntList = new HashMap();
		Queue currentQueue = new ArrayQueue();
		currentQueue = antSimulator.addToQueue(AntList);
		
		while (currentQueue.size() != 0) {
			// remove object from queue and use result to perform turn actions
			Object tempAntKey = currentQueue.dequeue();
			Object tempAnt = AntList.get(tempAntKey);
			
			if (tempAnt instanceof Bala) {
				Square tempAntSquare = ((Bala) tempAnt).getSquare(nodeList);
				int xCoordinate = tempAntSquare.getCoordinateX();
				int yCoordinate = tempAntSquare.getCoordinateY();
				if ((xCoordinate == this.x) && (yCoordinate == this.y)) {
					squareBalaAntList.add(tempAntKey, tempAnt);
				}
			}
		}
		return squareBalaAntList;
	}
	
	public Map getFriendlyAntsinSquare(Map AntList, AntSimulator antSimulator, Square[][] nodeList) {
		Map squareFriendlyAntList = new HashMap();
		Queue currentQueue = new ArrayQueue();
		currentQueue = antSimulator.addToQueue(AntList);
		
		while (currentQueue.size() != 0) {
			// remove object from queue and use result to perform turn actions
			Object tempAntKey = currentQueue.dequeue();
			Object tempAnt = AntList.get(tempAntKey);
			
			if (((Ant) tempAnt).getDead() == false) {
				if (tempAnt instanceof Forager) {
					Square tempAntSquare = ((Forager) tempAnt).getSquare(nodeList);
					int xCoordinate = tempAntSquare.getCoordinateX();
					int yCoordinate = tempAntSquare.getCoordinateY();
					if ((xCoordinate == this.getCoordinateX()) && (yCoordinate == this.getCoordinateY())) {
						squareFriendlyAntList.add(tempAntKey, tempAnt);
					}
				}
				if (tempAnt instanceof Scout) {
					Square tempAntSquare = ((Scout) tempAnt).getSquare(nodeList);
					int xCoordinate = tempAntSquare.getCoordinateX();
					int yCoordinate = tempAntSquare.getCoordinateY();
					if ((xCoordinate == this.getCoordinateX()) && (yCoordinate == this.getCoordinateY())) {
						squareFriendlyAntList.add(tempAntKey, tempAnt);
					}
				}
				if (tempAnt instanceof Soldier) {
					Square tempAntSquare = ((Soldier) tempAnt).getSquare(nodeList);
					int xCoordinate = tempAntSquare.getCoordinateX();
					int yCoordinate = tempAntSquare.getCoordinateY();
					if ((xCoordinate == this.getCoordinateX()) && (yCoordinate == this.getCoordinateY())) {
						squareFriendlyAntList.add(tempAntKey, tempAnt);
					}
				}
				if (tempAnt instanceof Queen) {
					Square tempAntSquare = ((Queen) tempAnt).getSquare(nodeList);
					if (tempAntSquare.getQueenExistence()) {
						squareFriendlyAntList.add(tempAntKey, tempAnt);
					}
				}
			}
		}
		return squareFriendlyAntList;
	}
}

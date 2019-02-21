import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Ant Simulator Class
 *
 * The purpose of this class is to run an ant civilization simulation.
 *
 *
 * Author: Teresa Bocchini
 * Date of Last Modification: 12/5/2017
 *
 */
public class AntSimulator 
	extends Application 
	implements SimulationEventListener, ActionListener {
	/**
	 * @param args
	 */
	// Initialize Data Structures to Store Ants and ColonyNodeViews
	Map antList = new HashMap();
	Square[][] nodeList = new Square[27][27];
	
	// Counter to keep track of number of Ants;
	Integer count;
	
	// Initialize Turn counter
	TurnCounter turnCounter = new TurnCounter();
			
	// Create AntSimGIU
	AntSimGUI antSimGUI = new AntSimGUI();
	
	// Create ColonyView
	ColonyView colonyView = new ColonyView(27, 27);
	
	// Create Turn Queue
	Queue turnQueue = new ArrayQueue();
	
	// Get cause of civilzation end
	String causeOfCivilizationEnd = new String();
	
	// Create Timer for continuous run
	Timer timer = new Timer(100, this);
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void simulationEventOccurred(SimulationEvent simEvent) {
		if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) {
			// Show Time
			turnCounter.resetTurnCounter();
			String time = turnCounter.timeToString();
			antSimGUI.setTime(time);
			
			// set up the simulation for normal operation
			antList.clear();
			clearSquares(nodeList);
			hideAllSquares(nodeList);

			for (int x = 13; x < 16; x++) {
				for (int y = 13; y < 16; y++) {
					nodeList[x][y].showNode();
				}
			}
			
			initializeCenterSquare();
		}
		else if (simEvent.getEventType() == SimulationEvent.QUEEN_TEST_EVENT) {
			// set up simulation for testing the queen ant
			turnQueue = addToQueue(antList);
			while (turnQueue.size() != 0) {
				// remove object from queue and use result to perform turn actions
				Object tempAntKey = turnQueue.dequeue();
				Object tempAnt = antList.get(tempAntKey);
				
				// New turn object
				Turn turn = new Turn();
				
				// Only perform queen's turn if the ant is a queen ant
				if (tempAnt instanceof Queen) {
					turn.queenTurn((Queen) tempAnt, nodeList[14][14], turnCounter, this);
				}
			}
			
		}
		else if (simEvent.getEventType() == SimulationEvent.SCOUT_TEST_EVENT) {
			// set up simulation for testing the scout ant 
			causeOfCivilizationEnd = "Testing.";
			endSimulation();
		}
		else if (simEvent.getEventType() == SimulationEvent.FORAGER_TEST_EVENT) {
			// set up simulation for testing the forager ant
			turnQueue = addToQueue(antList);
			
			// Use queue to perform ant turns
			while (turnQueue.size() != 0) {
				// remove object from queue and use result to perform turn actions
				Object tempAntKey = turnQueue.dequeue();
				Object tempAnt = antList.get(tempAntKey);
					
				// New turn object
				Turn turn = new Turn();
			
			
				if (tempAnt instanceof Forager) {
					if (!((Forager) tempAnt).getDead()) {
						turn.foragerTurn((Forager) tempAnt, this, nodeList);
					}
				}
			}
			
		}
		else if (simEvent.getEventType() == SimulationEvent.SOLDIER_TEST_EVENT) {
			// set up simulation for testing the soldier ant
			showAllSquares(nodeList);
		}
		else if (simEvent.getEventType() == SimulationEvent.RUN_EVENT) {
			// run the simulation continuously 
			timer.start();
		}	
		else if (simEvent.getEventType() == SimulationEvent.STEP_EVENT) {
			// run the next turn of the simulation
			runSingleTurn();
		}
	}
	
	public void start(Stage primaryStage) {
		// Initialize the GUI
		antSimGUI.initGUI(colonyView);	
		antSimGUI.addSimulationEventListener(this);
		
		for (int x = 0; x < 27; x++) {
			for (int y = 0; y < 27; y++) {
				Square newSquare = new Square();
				newSquare.setBalaCount(0);
				newSquare.setForagerCount(0);
				newSquare.setScoutCount(0);
				newSquare.setSoldierCount(0);
				newSquare.setFoodAmount(0);
				newSquare.setPheromoneLevel(0);
				newSquare.setCoordinates(x, y);
				newSquare.setID(newSquare.getCoordinates());
				
				nodeList[x][y] = newSquare;
				colonyView.addColonyNodeView(nodeList[x][y], x, y);
			}
		}
	}
	
	public void clearSquares(Square[][] squareList) {
		nodeList = new Square[27][27];
		
		for (int x = 0; x < 27; x++) {
			for (int y = 0; y < 27; y++) {
				Square newSquare = new Square();
				newSquare.setBalaCount(0);
				newSquare.setForagerCount(0);
				newSquare.setScoutCount(0);
				newSquare.setSoldierCount(0);
				newSquare.setFoodAmount(0);
				newSquare.setPheromoneLevel(0);
				newSquare.setCoordinates(x, y);
				newSquare.setID(newSquare.getCoordinates());
				
				nodeList[x][y] = newSquare;
				colonyView.addColonyNodeView(nodeList[x][y], x, y);
			}
		}
	}
	
	public void showAllSquares(Square[][] squareList) {
		for (int x = 0; x < squareList.length; x++) {
			for (int y = 0; y < squareList[x].length; y++) {
				squareList[x][y].showNode();
				addFoodUnit(x, y, 1);
			}
		}
	}
	
	public void hideAllSquares(Square[][] squareList) {
		for (int x = 0; x < squareList.length; x++) {
			for (int y = 0; y < squareList[x].length; y++) {
				squareList[x][y].hideNode();
			}
		}
	}
	
	public void initializeCenterSquare() {
		startQueen(14, 14);
		addScoutAnt(14, 14, 4);
		addSoldierAnt(14, 14, 10);
		addForagerAnt(14, 14, 50);
		addFoodUnit(14, 14, 1000);
	}
	
	public void startQueen(int x, int y) {
		Queen queen = new Queen();
		antList.add(0, queen);
		nodeList[14][14].setQueenExistance(true);
		
		count = 1;
	}
	
	public void addBalaAnt(int x, int y, int numberToAdd) {
		for (int number = 0; number < numberToAdd; number++) {
			// Create new Bala Ant and set variables
			Bala bala = new Bala();
			bala.setLocation(x, y);
			bala.setAge(0);
			bala.setDead(false);
			bala.setID(count);
			
			// Increment Location Square's number of Bala Ants
			nodeList[x][y].incrementBalaCount();
						
			// Add Ant to antList
			antList.add(count, bala);
			
			// Increment Ant Counter
			count++;
		}
	}
	
	public void addScoutAnt(int x, int y, int numberToAdd) {
		for (int number = 0; number < numberToAdd; number++) {
			// Create new Scout Ant and set variables
			Scout scout = new Scout();
			scout.setLocation(x, y);
			scout.setAge(0);
			scout.setDead(false);
			scout.setID(count);
			
			// Increment Location Square's number of Scout Ants
			nodeList[x][y].incrementScoutCount();
						
			// Add Ant to antList
			antList.add(count, scout);
			
			// Increment Ant Counter
			count++;
		}
	}
	
	public void addForagerAnt(int x, int y, int numberToAdd) {
		for (int number = 0; number < numberToAdd; number++) {
			// Create new Forager Ant and set variables
			Forager forager = new Forager();
			forager.setLocation(x, y);
			forager.setAge(0);
			forager.setDead(false);
			forager.setID(count);
			
			// Increment Location Square's number of Forager Ants
			nodeList[x][y].incrementForagerCount();
						
			// Add Ant to antList
			antList.add(count, forager);
			
			// Increment Ant Counter
			count++;
		}
	}
	
	public void addSoldierAnt(int x, int y, int numberToAdd) {
		for (int number = 0; number < numberToAdd; number++) {
			// Create new Soldier Ant and set variables
			Soldier soldier = new Soldier();
			soldier.setLocation(x, y);
			soldier.setAge(0);
			soldier.setDead(false);
			soldier.setID(count);
			
			// Increment Location Square's number of Soldier Ants
			nodeList[x][y].incrementSoldierCount();
						
			// Add Ant to antList
			antList.add(count, soldier);
			
			// Increment Ant Counter
			count++;
		}
	}
	
	public void addFoodUnit(int x, int y, int numberToAdd) {
		for (int number = 0; number < numberToAdd; number++) {
			//Add Food Unit
			nodeList[x][y].incrementFoodCount();
		}
	}
	
	public void addPheromoneUnit(int x, int y, int numberToAdd) {
		for (int number = 0; number < numberToAdd; number++) {
			//Add Food Unit
			nodeList[x][y].incrementPheromoneCount();
		}
	}
	
	public void removeBalaAnt(Bala balaAnt, int x , int y) {
		// Decrement Location Square's number of Bala Ants
		nodeList[x][y].decrementBalaCount();
	}
	
	public void removeForagerAnt(Forager foragerAnt, int x , int y) {
		// Decrement Location Square's number of Forager Ants
		nodeList[x][y].decrementForagerCount();
	}
	
	public void removeScoutAnt(Scout scoutAnt, int x , int y) {
		// Decrement Location Square's number of Scout Ants
		nodeList[x][y].decrementScoutCount();
	}
	
	public void removeSoldierAnt(Soldier soldierAnt, int x , int y) {
		// Decrement Location Square's number of Soldier Ants
		nodeList[x][y].decrementSoldierCount();
	}
	
	public void removeFoodUnit(int x, int y) {
		//Add Food Unit
		nodeList[x][y].decrementFoodCount();
	}
	
	public void removePheromoneUnit(int x, int y) {
		//Add Food Unit
		nodeList[x][y].decrementFoodCount();
	}
	
	public int getRandomNumber() {
		Random randomObject = new Random();
		int randomNumber = randomObject.nextInt(1000);
		
		return randomNumber;
	}

	public Queue addToQueue(Map NewAntList) {
		MapIterator antListIterator = NewAntList.mapIterator();
		Queue newQueue = new ArrayQueue();
		while (antListIterator.hasNext()) {
			Object tempAntKey = antListIterator.getCurrentKey();
			newQueue.add(tempAntKey);
			antListIterator.next();
		}
		return newQueue;
	}

	public Queue getAdjacentSquares(Square currentSquare) {
		Queue adjacentSquares = new ArrayQueue();
		if (currentSquare.getCoordinateX() == 0) {   /*  If current square lies on X = 0 row*/
			if (currentSquare.getCoordinateY() == 0) {  /*  If current square is at corner X = 0, Y = 0*/
				for (int x = currentSquare.getCoordinateX(); x < currentSquare.getCoordinateX() + 2; x++) {
					for (int y = currentSquare.getCoordinateY(); y < currentSquare.getCoordinateY() + 2; y++) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}	
					}
				}
			}
			else if (currentSquare.getCoordinateY() == 26) {  /*  If current square is at corner X = 0, Y = 26*/
				for (int x = currentSquare.getCoordinateX(); x < currentSquare.getCoordinateX() + 2; x++) {
					for (int y = currentSquare.getCoordinateY() - 1 ; y < currentSquare.getCoordinateY() + 1; y++) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}
							
					}
				}
			}
			else {	/*  If current square is not at a corner but on edge X = 0*/
				for (int x = currentSquare.getCoordinateX(); x < currentSquare.getCoordinateX() + 2; x++) {
					for (int y = currentSquare.getCoordinateY() - 1; y < currentSquare.getCoordinateY() + 2; y++ ) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}
							
					}
				}
			}
		}
		else if (currentSquare.getCoordinateX() == 26) {    /*  If current square lies on X = 26 row*/
			if (currentSquare.getCoordinateY() == 0) {		/*  If current square is at corner X = 26, Y = O*/
				for (int x = currentSquare.getCoordinateX() - 1; x < currentSquare.getCoordinateX() + 1; x++) {
					for (int y = currentSquare.getCoordinateY(); y < currentSquare.getCoordinateY() + 2; y++ ) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}
							
					}
				}
			}
			else if (currentSquare.getCoordinateY() == 26) {	/*  If current square is at corner X = 26, Y = 26*/
				for (int x = currentSquare.getCoordinateX() - 1; x < currentSquare.getCoordinateX() + 1; x++) {
					for (int y = currentSquare.getCoordinateY() - 1; y < currentSquare.getCoordinateY() + 1; y++) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}
							
					}
				}
			}
			else { 	/*  If current square is not at a corner but on edge X = 26*/
				for (int x = currentSquare.getCoordinateX() - 1; x < currentSquare.getCoordinateX() + 1; x++) {
					for (int y = currentSquare.getCoordinateY() - 1 ; ((y < currentSquare.getCoordinateY() + 2) && (y < nodeList[x].length)); y++ ) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}
							
					}
				}
			}
		}
		else {  /*  Square is not in first or last row*/
			if (currentSquare.getCoordinateY() == 0) {		/*  If current square in edge Y = O but not first or last row*/
				for (int x = currentSquare.getCoordinateX() - 1; x < currentSquare.getCoordinateX() + 2; x++) {
					for (int y = currentSquare.getCoordinateY(); y < currentSquare.getCoordinateY() + 2; y++ ) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}
							
					}
				}
			}
			else if (currentSquare.getCoordinateY() == 26) {	/*  If current square in edge Y = 26 but not first or last row*/
				for (int x = currentSquare.getCoordinateX() - 1; x < currentSquare.getCoordinateX() + 2; x++) {
					for (int y = currentSquare.getCoordinateY() - 1; y < currentSquare.getCoordinateY() + 1; y++) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}
							
					}
				}
			}
			else { 	/*  If current square is not at any corner or on any edge */
				for (int x = currentSquare.getCoordinateX() - 1; x < currentSquare.getCoordinateX() + 2; x++) {
					for (int y = currentSquare.getCoordinateY() - 1 ; ((y < currentSquare.getCoordinateY() + 2) && (y < nodeList[x].length)); y++ ) {
						if ((nodeList[x][y].getCoordinateX() == currentSquare.getCoordinateX()) && (nodeList[x][y].getCoordinateY() == currentSquare.getCoordinateY())) {
						}
						else { 
							adjacentSquares.add(nodeList[x][y]);
						}
							
					}
				}
			}
		}
		return adjacentSquares;
	}

	public void reduceAllSquaresPheromoneLevel() {
		for (int x = 0; x < nodeList.length; x++) {
			for (int y = 0; y < nodeList[x].length; y++) {
				int pheromoneLevel = nodeList[x][y].getPheromoneLevel() / 2;
				nodeList[x][y].setPheromoneLevel(pheromoneLevel);
			}
		}
	}

	@SuppressWarnings("static-access")
	public void endSimulation() {
		String time = turnCounter.timeToString();
		String message = "Your Ant Civilization lived for " + time + "\nCause of Civilization end: " + causeOfCivilizationEnd + "\nPress OK to see results.";
		JOptionPane endOfSimulationDialogBox = new JOptionPane();
		int result = endOfSimulationDialogBox.showConfirmDialog(antSimGUI, message, "Ant Simulation", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);

		if(result == JOptionPane.OK_OPTION)
		{
		    displayResults();
		}
		else if (result == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
		
	}
	
	@SuppressWarnings("static-access")
	public void displayResults() {
		Queue ants = addToQueue(antList);
		int balaAlive = 0;
		int balaDead = 0;
		int foragerAlive = 0;
		int foragerDead = 0;
		int scoutAlive = 0;
		int scoutDead = 0;
		int soldierAlive = 0;
		int soldierDead = 0;
		
		while (ants.size() != 0) {
			// remove object from queue and use result to perform turn actions
			Object tempAntKey = ants.dequeue();
			Object tempAnt = antList.get(tempAntKey);
						
			if (tempAnt instanceof Bala) {
				if (((Bala) tempAnt).getDead()) {
					balaDead++;
				}
				else {
					balaAlive++;
				}
			}
			else if (tempAnt instanceof Forager) {
				if (((Forager) tempAnt).getDead()) {
					foragerDead++;
				}
				else {
					foragerAlive++;
				}
			}
			else if (tempAnt instanceof Scout) {
				if (((Scout) tempAnt).getDead()) {
					scoutDead++;
				}
				else {
					scoutAlive++;
				}
			}
			else if (tempAnt instanceof Soldier) {
				if (((Soldier) tempAnt).getDead()) {
					soldierDead++;
				}
				else {
					soldierAlive++;
				}
			}
		}
		
		String message = "Your Ant Civilization had a total of " + antList.size() + " Ants.\n" + 
						"Bala Ants\t Alive: " + balaAlive + " \tDead: " + balaDead + "\n" +
						"Forager Ants\t Alive: " + foragerAlive + " \tDead: " + foragerDead + "\n" +
						"Scout Ants\t Alive: " + scoutAlive + " \tDead: " + scoutDead + "\n" +
						"Soldier Ants\t Alive: " + soldierAlive + " \tDead: " + soldierDead;
				
		
		JOptionPane resultsDialogBox = new JOptionPane();
		int result = resultsDialogBox.showConfirmDialog(antSimGUI, message, "Ant Simulation Results", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);

		if(result == JOptionPane.OK_OPTION)
		{
		    System.exit(0);
		}
		else if (result == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
		
	}
	
	public void setAntAgeDaily() {
		Queue antQueue = this.addToQueue(antList);
		
		while (antQueue.size() != 0) {
			// remove object from queue and use result to perform turn actions
			Object tempAntKey = antQueue.dequeue();
			Object tempAnt = antList.get(tempAntKey);
			int age = ((Ant) tempAnt).getAge();
		
			((Ant) tempAnt).setAge(age++);
			
			// Mark as dead if old age
			if (tempAnt instanceof Queen) {
				if (((Ant) tempAnt).getAge() == 10) {
					((Ant) tempAnt).setDead(true);
					causeOfCivilizationEnd = "The Queen died of old Age.";
					this.endSimulation();
				}
			}
			else {
				if (((Ant) tempAnt).getAge() == 366) {
					((Ant) tempAnt).setDead(true);
					((Ant) tempAnt).removeFromSimulation(((Ant) tempAnt).getSquare(nodeList), this);
				}
			}
		}
	}

	public void runSingleTurn() {
		// run the next turn of the simulation
		// Increment Turn Counter
		turnCounter.incrementTurnCount();
		if (turnCounter.getTurnCount() % 10 == 0) {
			turnCounter.incrementDayCount(this);
			if (turnCounter.getDayCount() % 365 == 0) {
				turnCounter.incrementYearCount();
			}
		 }
		
		// Determine if Bala Ant Spawns this turn and add it in appropriate square
		int chanceOfSpawn = getRandomNumber() % 100;
		if ((chanceOfSpawn >= 0) && (chanceOfSpawn < 3)) {
			int locationSquare = getRandomNumber() % 4;
			if (locationSquare == 0) {
				addBalaAnt(0, 0, 1);
			}
			else if (locationSquare == 1) {
				addBalaAnt(0, 26, 1);
			}
			else if (locationSquare == 2) {
				addBalaAnt(26, 0, 1);
			}
			else {
				addBalaAnt(26, 26, 1);
			}
		}
								
		// Configure GUI time to show
		String time = turnCounter.timeToString();
		antSimGUI.setTime(time);
			
		// Add all ants to queue
		turnQueue = addToQueue(antList);
			
		// Use queue to perform ant turns
		while (turnQueue.size() != 0) {
			// remove object from queue and use result to perform turn actions
			Object tempAntKey = turnQueue.dequeue();
			Object tempAnt = antList.get(tempAntKey);
			// New turn object
			Turn turn = new Turn();
			
			if (!((Ant) tempAnt).getDead()) {
				// If alive, perform certain turn depending on type of ant
				if (tempAnt instanceof Queen) {
					if (!((Queen) tempAnt).getDead()) {
						turn.queenTurn((Queen) tempAnt, nodeList[14][14], turnCounter, this);
					}
				}
				else if (tempAnt instanceof Forager) {
					if (!((Forager) tempAnt).getDead()) {
						turn.foragerTurn((Forager) tempAnt, this, nodeList);
					}
				}
				else if (tempAnt instanceof Scout) {
					if (!((Scout) tempAnt).getDead()) {
						turn.scoutTurn((Scout) tempAnt, this, ((Scout) tempAnt).getSquare(nodeList), nodeList);
					}
				}
				else if (tempAnt instanceof Soldier) {
					if (!((Soldier) tempAnt).getDead()) {
						turn.soldierTurn((Soldier) tempAnt, this, nodeList, antList);
					}
				}
				else {
					if (!((Bala) tempAnt).getDead()) {
						turn.balaTurn((Bala) tempAnt, this, nodeList, antList);
					}
				}
			}
		}		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (antList.containsKey(0)) {
			if (((Ant) antList.get(0)).getDead()) {
				timer.stop();
			}
			else {
				runSingleTurn();
			}
		}
	}

	public boolean compareToMovementStack(Forager foragerAnt, Square square) {
		int size = foragerAnt.getComparableMovementHistory().size();
		Queue tempQueue = foragerAnt.getComparableMovementHistory();
		
		for (int x = 0; x < size; x++) {			
			Square tempSquare = (Square) tempQueue.dequeue();
			tempQueue.enqueue(tempSquare);
			if (tempSquare.getCoordinates().compareTo(square.getCoordinates()) == 0) {
				return true;
			}
		}		
		return false;
	}
}

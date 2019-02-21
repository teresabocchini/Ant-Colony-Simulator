# Ant Colony Simulator

This project is designed to simulate how an ant colony works together to survive different obstacles. There are five different ants each tasked with unique functions to maintain the colony.

## Design

This was a semester project for a class that allowed me to extend my knowledge of not only Java in general but also Java user interface components. All UI elements were written and provided by my professor. Each source file written by me has a note stating so within the top comment section.

There were different rules provided by my instructor that had to be implemented as follows:

###### Overall Environment
 
 The enviroment is designed as a grid and each square can contain different locations within the colony. There are 8 directions of movement from each square and the entrance is the center square. Not all squares are available for exploration until they are discovered by certain ants. Each square can contain zero or more of the following: enemy ants, friendly ants, units of food, and units of pheromone. If the queen dies, the simulation should end immediately.

###### Common Characteristics for all Ants

Each ant has a unique identifier and the gueen should be identifier 0. As ants get hatched, they should be numbered in ascending order. Time is kept through a turn counter. During each turn, every ant that is alive performs one action. There are 10 turns in a day, and 365 days in a year. Each ant has a maximum lifespan of 1 year, except the queen who has a lifespan of 20 years, and dead ants should be removed from the simulation. When moving, ants should move only one square.

###### Queen Ant

The queen ant is responsible for hatching new ants once per day on the 1st turn of the day and she does not move from the center square. The type of ant that is hatched is determined by the hatching frequencies: 50% Forager, 25% Scout, and 25% Sodier. During each turn, the ant consumes 1 unit of food, including the turn that she hatches a new ant. The there is no food when the queen attempts to eat, she dies of starvation.

###### Foragers

Foragers are responsible for bringing food to the queen. They have two primary modes of behavior: forage mode and return-to-nest mode. 

During forager mode, these ants are looking for food to bring back to their queen. They should always move to the adjacent square that has the highest number of pheromone but mever to the square it just came from unless it's the last resort. If more than one adjacent square has the same units of pheromone, it should move randomly to one of those. Sicne they will need to return to the nest, their movement history while trying to find food should be kept. When it enters a square with food, it should pick it up and then enter return-to-nest mode. It can move to a square and pick up food in the same turn, but they can't move again until the next turn.

During return-to-nest mode, it should follow it's movement history back to the center square. They should ignore pheromone levels but instead drop 10 unit of pheromone in each square on its way back, including the square where it found the food, unless the square already has 1000 or more units of pheromone. They may drop pheromone and move to a new square in one turn. If a forager ant is killed while carrying food, the food should remain in the square where they were killed. When they reach the colony entrance, they should drop the food but they cannot move out of the center square on the same turn the food is deposited. When the food is deposited, they enter forager mode and its movement history should be reset.

###### Scout

Scouts are responsible for enlarging the foraging area available to the foragers. Since they are responsible for discovering new squares in the colony, they can move around randomly to any of the 8 adjacent squares connected to them on their turn. If the square was already discovered, they should just move to their square. If the square had not been discovered, they should move to that square and the square and its contents should be revealed in the user interface. When a square is revealed, there is a 25% chance that there will be a rnadom amount of food between 500 and 1000 units and a 75% chance that the square will contain 0 units of food.

###### Soldier

Soldiers are responsible for protecting the colony by fighting the enemy Bala ants. Soldier ants have two primary modes of behavior: scout mode and attack mode. 

During scout mode, the soldier ant should move to any of the 8 adjacent squares that contains one or more Bala (enemy) ants. If there are zero adjacent squares with Bala ants, they should move randomly. 

If there is a Bala ant in the same square as the soldier, it enters attack mode, which takes precedence over over scout mode. While in attack mode, they shoul attack one of the Bala ants that are present at random. There is a 50% chance of the soldier killing the Bala ant.

###### Bala

Bala ants are enemies of the colony. They should enter only at the four corners at the edge of the colony and there is a 3% chance that a new Bala ant will spawn each turn. Once in the colony they may move around freely, whether the square has been revealed or not, and they must remain in the colony until they die. Assume they never leave the colony once they enter it. They should move randomly to any of the 8 adjacent squares. If they are in e square with one or more friendly ants, they must attack one ant at random. There is a 50% chance that the Bala ant kills the friendly ant.

## Compiling

If you'd like to compile and run the program, download the source files and import them into your favorite Java compiler.

Within the UI only some buttons were fully implemented, as a requirement for the project. Cslick Normal Setup to initialize the colony. There are two different run modes: continuous and step-wise. Click Run to run the simulation continuous run mode. If you'd like to step through the simulation, you can run a single turn by clicking the Step button. The application is not designed to switch between the two run modes.

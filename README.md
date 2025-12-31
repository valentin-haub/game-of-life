# Conway's Game of Life

An interactive Java implementation of the famous cellular automaton, built with the **Processing 4** library.

## Overview

This project is a grid-based simulation of Conway's Game of Life.
It features a clean separation between the game logic and the graphical user interface.
The project was recently migrated to a modern Maven-based build system to ensure seamless dependency management and compatibility with Java 21.

## The Rules

The universe of the Game of Life is an infinite, two-dimensional orthogonal grid of square cells. Each cell is in one of two possible states: alive or dead. Every cell interacts with its eight neighbours. At each step in time, the following transitions occur:

1. **Underpopulation:** Any live cell with fewer than two live neighbours dies.
2. **Survival:** Any live cell with two or three live neighbours lives on to the next generation.
3. **Overpopulation:** Any live cell with more than three live neighbours dies.
4. **Reproduction:** Any dead cell with exactly three live neighbours becomes a live cell.

You can find out more about the mathematical background and history on Wikipedia:
[Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

## Tech Stack

- **Language:** Java 21
- **Graphics:** Processing 4.5.0
- **Build Tool:** Maven
- **Testing:** JUnit 5

## Features

- **Interactive Grid:** Click on cells to toggle them between alive and dead states while the simulation is paused.

- **Configurable Grid Size:** The simulation supports dynamic grid sizes (default: 50x50), which can be adjusted via command-line arguments.

- **Dual Output:** In addition to the graphical UI, the game logic includes a text-based visualization (`toString()`) that prints the current grid state to the console. This allows for verifying the game state independent of the graphics engine.

- **Step Counter:** Tracks the number of generations elapsed.

- **Simulation Controls:**
  - **Start:** Begin the automatic evolution of generations. The simulation automatically stops if the world reaches a stable state.
  - **Stop:** Pause the simulation.
  - **Step:** Advance the simulation by exactly one generation.
  - **Reset:** Clear the board and reset the step counter.

- **Visuals:** High-performance rendering using JOGL (Java OpenGL) via Processing.

## How to Run

### Prerequisites

- **Java SDK 21** or higher
- **Maven** installed on your system

### Steps

1. **Clone the repository:**
    ```bash
    git clone https://github.com/valentin-haub/game-of-life.git
    ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   
   By default, the application starts with a **50x50** grid:
   ```bash
   mvn exec:java -Dexec.mainClass="io.github.valentinhaub.GameOfLifeApp"
   ```
   
   To specify a different grid size, use the ```-Dexec.args``` parameter.
   For example, to launch a **20x20** grid:
   ```bash
   mvn exec:java -Dexec.mainClass="io.github.valentinhaub.GameOfLifeApp" -Dexec.args="20"
   ```
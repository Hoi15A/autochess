# Report 24.04.2020

## Discussion Points:
1. Intentions for project
2. First development steps
3. First basic structure
4. First basic features
5. Design/Theme

### 1. Intentions for current project based on feedback of first project:
	- More branches -> Feature based not class based
	- Commits with proper comments
	- More pull requests with reviews
	- Tests ongoing throughout implementation with Mockito
	- More tests (positive and negative)
	- Clean code ongoing throughout implementation
	- Exceptions implemented throughout application

### 2. Decisions on first steps to take in developments process:
	- Decide on first basic structure of application
	- Decide on first basic features of application
	- Decide on general design/Theme

### 3. First basic structure: (See 'First basic structure' in documentation)
	- MVP implementation -> Controller holds Model (Game) and View(FXML) and listens for events in both
	- Game holds board and list of players(heroes) and contains all game functions
	- Board is given ID of Minion to place
	- Player (Hero) extends base Player, implements Input type (User/AI)
	- Figure (Minion) extends base figure and implements strategy (aggressive, defensive, etc.)

#### Class Contents (very basic)
	
|            | Game           | Board         | Player        | Minion        |
|------------|----------------|---------------|---------------|---------------|
| **Attributes** | Board          | 2D Array      | Id            | Id            |
|            | Playerlist     |               | Type          | Type          |
|            |                |               | Health        | Health        |
|            |                |               | Currency      | Damage        |
|            |                |               | Minion list   | Defence       |
|            |                |               |               | Range         |
| **Functions**  | Take turn      | getter/setter | getter/setter | getter/setter |
|            | switch player  | isValidSpace  | add minion    | make move     |
|            | get board info |               |               |               |

### 4. First basic features:
	- App: Start application (UI)
	- GUI: Display basic 2D board with minions
	- Initialize Game with board and player
	- Initialize Player with minions
	- Place minion Id in board
	- Move minion

### 5. Ideas regarding design/theme:
	- Theme: Space -> Astronauts, Aliens etc
	- Cartoonish style, dark design
	- Board visualization: Top down with Players side left and right

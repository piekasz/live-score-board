<!-- TOC -->
* [Task Description](#task-description)
  * [Instructions:](#instructions)
  * [Guidelines:](#guidelines)
    * [Football World Cup Score Board:](#football-world-cup-score-board)
<!-- TOC -->
# Task Description
## Instructions:
Please provide the implementation of the Football World Cup Score Board as a simple
library.

## Guidelines:
• Keep it simple. Stick to the requirements and try to implement the simplest
solution you can possibly think of that works and don't forget about edge cases.

• Use an in-memory store solution (for example just use collections to store
the information you might require).

• We are NOT looking for a REST API, a Web Service or Microservice. Just
a simple implementation.

• Focus on Quality. Use Test-Driven Development (TDD), pay attention to
OO design, Clean Code and adherence to SOLID principles.

### Football World Cup Score Board:
Develop a new Live Football World Cup Score Board that shows matches and scores.

The boards support the following operations:
1. Start a game. When a game starts, it should capture (being initial score 0-0)
   a. Home team
   b. Away Team
2. Finish a game. It will remove a match from the scoreboard.
3. Update score. Receiving the pair score; home team score and away team score
   updates a game score
4. Get a summary of games by total score. Those games with the same total score
   will be returned ordered by the most recently added to our system.

As an example, being the current data in the system:
a. Mexico - Canada: 0 – 5 \
b. Spain - Brazil: 10 – 2 \
c. Germany - France: 2 – 2 \
d. Uruguay - Italy: 6 – 6 \
e. Argentina - Australia: 3 - 1 \
The summary would provide with the following information:
1. Uruguay 6 - Italy 6
2. Spain 10 - Brazil 2
3. Mexico 0 - Canada 5
4. Argentina 3 - Australia 1
5. Germany 2 - France 2

package pl.ppiekarski.livescoreboard.example;

import pl.ppiekarski.livescoreboard.WorldCupScoreBoard;
import pl.ppiekarski.livescoreboard.api.StartMatchCommand;
import pl.ppiekarski.livescoreboard.api.UpdateScoreCommand;

class WorldCupScoreBoardExample {

    public static void main(String[] args) {
        var scoreboard = new WorldCupScoreBoard();

        System.out.println("START 2 GAMES WITH SUCCESS");
        var matchId1 = scoreboard.startMatch(new StartMatchCommand("Poland", "Germany")).getOrNull();
        var matchId2 = scoreboard.startMatch(new StartMatchCommand("USA", "France")).getOrNull();
        System.out.println(matchId1);
        System.out.println(matchId2);

        System.out.println("UPDATE BOTH SCORES - BOTH SUCCESS");
        var update1 = scoreboard.updateScore(new UpdateScoreCommand(matchId1, 3, 0));
        var update2 = scoreboard.updateScore(new UpdateScoreCommand(matchId2, 1, 1));
        System.out.println(update1);
        System.out.println(update2);
        System.out.println(scoreboard.getSummary());

        System.out.println("FINISH GAME 1");
        var finish1 = scoreboard.finishMatch(matchId1);
        System.out.println(finish1);
        System.out.println(scoreboard.getSummary());

        System.out.println("UPDATE BOTH SCORES - FAILS FOR ALREADY FINISHED GAME");
        update1 = scoreboard.updateScore(new UpdateScoreCommand(matchId1, 4, 0));
        update2 = scoreboard.updateScore(new UpdateScoreCommand(matchId2, 2, 1));
        System.out.println(update1);
        System.out.println(update2);
        System.out.println(scoreboard.getSummary());

        System.out.println("FINISH GAME 2");
        var finish2 = scoreboard.finishMatch(matchId2);
        System.out.println(finish2);
        System.out.println(scoreboard.getSummary());

    }
}

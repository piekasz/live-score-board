package pl.ppiekarski.livescoreboard;


import java.util.Comparator;

public final class WorldCupScoreBoard extends LiveScoreBoard {

    public WorldCupScoreBoard() {
        super(
                new InMemoryMatchStorage(),
                Comparator
                        .comparingInt(Match::getTotalGoals).reversed()
                        .thenComparing(Match::getSequence, Comparator.reverseOrder())
        );
    }
}

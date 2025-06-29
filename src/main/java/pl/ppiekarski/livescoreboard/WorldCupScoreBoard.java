package pl.ppiekarski.livescoreboard;


import java.util.Comparator;
import java.util.List;

public final class WorldCupScoreBoard extends LiveScoreBoard {

    public WorldCupScoreBoard() {
        super(
                new InMemoryMatchStorage(List.of(new NoTeamAlreadyPlayingRule())),
                Comparator
                        .comparingInt(Match::getTotalGoals).reversed()
                        .thenComparing(Match::getSequence, Comparator.reverseOrder())
        );
    }
}

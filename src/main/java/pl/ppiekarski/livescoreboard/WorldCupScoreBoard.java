package pl.ppiekarski.livescoreboard;


public final class WorldCupScoreBoard extends LiveScoreBoard {

    public WorldCupScoreBoard() {
        super(new InMemoryMatchStorage());
    }
}

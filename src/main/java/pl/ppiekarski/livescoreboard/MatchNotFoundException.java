package pl.ppiekarski.livescoreboard;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException(MatchId matchId) {
        super("Match not found by id " + matchId);
    }
}

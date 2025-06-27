package pl.ppiekarski.livescoreboard;


import java.util.concurrent.atomic.AtomicLong;

/**
 * identified by matchId - since that leaves as capacity of teamA and teamB playing multiple games
 * only score is mutable and can be changed for the Match
 */
class Match {
    public static final SoccerScore START_GAME_SCORE = new SoccerScore(0, 0);
    private static final AtomicLong COUNTER = new AtomicLong();

    private final MatchId matchId;
    private final Team homeTeam;
    private final Team awayTeam;
    private SoccerScore score;
    private final long sequence;

    Match(Team homeTeam, Team awayTeam) {
        this.matchId = new MatchId(homeTeam, awayTeam);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = START_GAME_SCORE;
        this.sequence = COUNTER.getAndIncrement();
    }

    public void setNewScore(SoccerScore newScore) {
        this.score = newScore;
    }

    public MatchDto toDto() {
        return new MatchDto(homeTeam.name(), awayTeam.name(), score.home(), score.away());
    }

    public MatchId matchId() {
        return matchId;
    }

    public int getTotalGoals() {
        return score.totalGoals();
    }

    public long getSequence() {
        return sequence;
    }
}

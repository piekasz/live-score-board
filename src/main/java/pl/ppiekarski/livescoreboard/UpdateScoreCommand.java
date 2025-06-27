package pl.ppiekarski.livescoreboard;

public record UpdateScoreCommand(MatchId matchId, int homeScore, int awayScore) {
}

package pl.ppiekarski.livescoreboard.api;

import pl.ppiekarski.livescoreboard.MatchId;

public record UpdateScoreCommand(MatchId matchId, int homeScore, int awayScore) {
}

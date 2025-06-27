package pl.ppiekarski.livescoreboard.api;

import pl.ppiekarski.livescoreboard.MatchId;

public record MatchDto(
        MatchId matchId,
        String homeTeamName,
        String awayTeamName,
        int homeTeamScore,
        int awayTeamScore
) {
}

package pl.ppiekarski.livescoreboard;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class LiveScoreBoard {

    private final Map<MatchId, Match> liveMatches = new ConcurrentHashMap<>();

    void startMatch(String homeTeamName, String awayTeamName) {
        var homeTeam = new Team(homeTeamName);
        var awayTeam = new Team(awayTeamName);

        var match = new Match(homeTeam, awayTeam);
        liveMatches.put(match.matchId(), match);
    }

    List<MatchDto> getSummary() {
        return
                liveMatches.values()
                .stream()
                .map(Match::toDto)
                .toList()
        ;
    }

}

package pl.ppiekarski.livescoreboard;

import java.util.Collection;
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

    Collection<Match> getSummary() {
        return liveMatches.values();
    }

}

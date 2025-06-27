package pl.ppiekarski.livescoreboard;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class LiveScoreBoard {

    private final Map<MatchId, Match> liveMatches = new ConcurrentHashMap<>();

    Result<MatchId> startMatch(StartMatchCommand startMatchCommand) {
        return Result.runCatching(() -> {
            Team home = new Team(startMatchCommand.homeTeamName());
            Team away = new Team(startMatchCommand.awayTeamName());
            var match = new Match(home, away);
            liveMatches.put(match.matchId(), match);
            return match.matchId();
        });
    }

    public Result<MatchDto> updateScore(UpdateScoreCommand updateScoreCommand) {
        return Result.runCatching(() -> {
            var match = liveMatches.get(updateScoreCommand.matchId());
            assertMatchFoundById(updateScoreCommand.matchId(), match);
            match.setNewScore(new SoccerScore(updateScoreCommand.homeScore(), updateScoreCommand.awayScore()));
            return match.toDto();
        });
    }

    Result<MatchDto> finishMatch(MatchId matchId) {
        return Result.runCatching(() -> {
            var removed = liveMatches.remove(matchId);
            assertMatchFoundById(matchId, removed);
            return removed.toDto();
        });
    }

    List<MatchDto> getSummary() {
        return liveMatches.values()
                .stream()
                .sorted(Comparator
                        .comparingInt(Match::getTotalGoals).reversed()
                        .thenComparing(Match::getSequence, Comparator.reverseOrder())
                )
                .map(Match::toDto)
                .toList();
    }

    private static void assertMatchFoundById(MatchId matchId, Match match) {
        if (match == null) {
            throw new MatchNotFoundException(matchId);
        }
    }
}

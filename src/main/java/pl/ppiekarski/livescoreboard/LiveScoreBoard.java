package pl.ppiekarski.livescoreboard;

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
            if (match == null) {
                throw new IllegalArgumentException("Game not found by" + updateScoreCommand.matchId().toString());
            }
            match.setNewScore(new SoccerScore(updateScoreCommand.homeScore(), updateScoreCommand.awayScore()));
            return match.toDto();
        });
    }

    List<MatchDto> getSummary() {
        return
                liveMatches.values()
                .stream()
                .map(Match::toDto)
                .toList();
    }
}

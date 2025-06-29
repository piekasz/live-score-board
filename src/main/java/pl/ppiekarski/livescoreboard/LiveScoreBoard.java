package pl.ppiekarski.livescoreboard;

import java.util.Comparator;
import java.util.List;
import pl.ppiekarski.livescoreboard.api.MatchDto;
import pl.ppiekarski.livescoreboard.api.StartMatchCommand;
import pl.ppiekarski.livescoreboard.api.UpdateScoreCommand;

sealed class LiveScoreBoard permits WorldCupScoreBoard {

    private final MatchStorage matchStorage;

    protected LiveScoreBoard(MatchStorage matchStorage) {
        this.matchStorage = matchStorage;
    }

    public Result<MatchId> startMatch(StartMatchCommand startMatchCommand) {
        return Result.runCatching(() -> {
            Team home = new Team(startMatchCommand.homeTeamName());
            Team away = new Team(startMatchCommand.awayTeamName());
            return matchStorage.save(new MatchStorage.NewMatch(home, away));
        });
    }

    public Result<MatchDto> updateScore(UpdateScoreCommand updateScoreCommand) {
        return Result.runCatching(() -> {
            var match = matchStorage.findById(updateScoreCommand.matchId());
            assertMatchFoundById(updateScoreCommand.matchId(), match);
            match.setNewScore(new SoccerScore(updateScoreCommand.homeScore(), updateScoreCommand.awayScore()));
            return match.toDto();
        });
    }

    public Result<MatchDto> finishMatch(MatchId matchId) {
        return Result.runCatching(() -> {
            var removed = matchStorage.remove(matchId);
            assertMatchFoundById(matchId, removed);
            return removed.toDto();
        });
    }

    public List<MatchDto> getSummary() {
        return matchStorage.findAll()
                .stream()
                .sorted(Comparator
                        .comparingInt(Match::getTotalGoals).reversed()
                        .thenComparing(Match::getSequence, Comparator.reverseOrder())
                ) // TODO consider make it configurable - maybe MatchSorter extends Comparator<MatchDto> to make it public - then sort after toDto mapping
                .map(Match::toDto)
                .toList();
    }

    private static void assertMatchFoundById(MatchId matchId, Match match) {
        if (match == null) {
            throw new MatchNotFoundException(matchId);
        }
    }
}

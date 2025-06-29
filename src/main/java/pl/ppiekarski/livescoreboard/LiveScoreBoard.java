package pl.ppiekarski.livescoreboard;

import java.util.Comparator;
import java.util.List;
import pl.ppiekarski.livescoreboard.api.MatchDto;
import pl.ppiekarski.livescoreboard.api.StartMatchCommand;
import pl.ppiekarski.livescoreboard.api.UpdateScoreCommand;

sealed class LiveScoreBoard permits WorldCupScoreBoard {

    private final MatchStorage matchStorage;
    // TODO could be specific type like MatchSorter extends Comparator<MatchDto>
    private final Comparator<Match> matchComparator;

    protected LiveScoreBoard(MatchStorage matchStorage, Comparator<Match> matchComparator) {
        this.matchStorage = matchStorage;
        this.matchComparator = matchComparator;
    }

    public Result<MatchId> startMatch(StartMatchCommand startMatchCommand) {
        return Result.runCatching(() -> {
            Team home = new Team(startMatchCommand.homeTeamName());
            Team away = new Team(startMatchCommand.awayTeamName());
            return matchStorage.insert(new MatchStorage.NewMatch(home, away));
        });
    }

    public Result<MatchDto> updateScore(UpdateScoreCommand updateScoreCommand) {
        return Result.runCatching(() -> {
            var match = matchStorage.updateScore(updateScoreCommand.matchId(), new SoccerScore(updateScoreCommand.homeScore(), updateScoreCommand.awayScore()));
            assertMatchFoundById(updateScoreCommand.matchId(), match);
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
                .sorted(matchComparator)
                .map(Match::toDto)
                .toList();
    }

    private static void assertMatchFoundById(MatchId matchId, Match match) {
        if (match == null) {
            throw new MatchNotFoundException(matchId);
        }
    }
}

package pl.ppiekarski.livescoreboard;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryMatchStorage implements MatchStorage {

    private static final AtomicLong SEQUENCE_COUNTER = new AtomicLong();

    private final Map<Long, Match> liveMatches = new ConcurrentHashMap<>();
    private final Set<Team> activeTeams = ConcurrentHashMap.newKeySet();
    private final List<TeamValidationRule> teamValidationRules;

    InMemoryMatchStorage(List<TeamValidationRule> teamValidationRules) {
        this.teamValidationRules = teamValidationRules;
    }

    @Override
    public MatchId insert(NewMatch newMatch) {
        var match = liveMatches.computeIfAbsent(SEQUENCE_COUNTER.getAndIncrement(), key -> {
            teamValidationRules.forEach(rule -> rule.check(newMatch.home(), newMatch.away(), activeTeams));
            activeTeams.add(newMatch.home());
            activeTeams.add(newMatch.away());
            return new Match(key, newMatch.home(), newMatch.away());
        });
        return match.matchId();
    }

    @Override
    public Match findById(MatchId matchId) {
        return liveMatches.get(matchId.unwrapLong());
    }

    @Override
    public Match remove(MatchId matchId) {
        var toBeRemoved = liveMatches.computeIfPresent(matchId.unwrapLong(), (k,v) -> {
            activeTeams.remove(v.homeTeam());
            activeTeams.remove(v.awayTeam());
            return v;
        });
        liveMatches.remove(matchId.unwrapLong(), toBeRemoved);
        return toBeRemoved;
    }

    @Override
    public Collection<Match> findAll() {
        return liveMatches.values();
    }

    @Override
    public Match updateScore(MatchId matchId, SoccerScore soccerScore) {
        return liveMatches.computeIfPresent(matchId.unwrapLong(), (k,v) -> {
            v.setNewScore(soccerScore);
            return v;
        });
    }
}

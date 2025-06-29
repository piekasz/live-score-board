package pl.ppiekarski.livescoreboard;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryMatchStorage implements MatchStorage {

    private final Map<MatchId, Match> liveMatches = new ConcurrentHashMap<>();
    private static final AtomicLong SEQUENCE_COUNTER = new AtomicLong();

    @Override
    public MatchId save(NewMatch newMatch) {
        var match = new Match(SEQUENCE_COUNTER.getAndIncrement(), newMatch.home(), newMatch.away());
        liveMatches.put(match.matchId(), match);
        return match.matchId();
    }

    @Override
    public Match findById(MatchId matchId) {
        return liveMatches.get(matchId);
    }

    @Override
    public Match remove(MatchId matchId) {
        return liveMatches.remove(matchId);
    }

    @Override
    public Collection<Match> findAll() {
        return liveMatches.values();
    }
}

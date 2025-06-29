package pl.ppiekarski.livescoreboard;

import java.util.Collection;

interface MatchStorage {

    MatchId save(NewMatch match);

    Match findById(MatchId matchId);

    Match remove(MatchId matchId);

    Collection<Match> findAll();

    record NewMatch(Team home, Team away) {
    }
}

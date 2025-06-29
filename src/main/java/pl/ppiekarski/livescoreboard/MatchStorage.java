package pl.ppiekarski.livescoreboard;

import java.util.Collection;

interface MatchStorage {

    MatchId insert(NewMatch match);

    Match findById(MatchId matchId);

    Match remove(MatchId matchId);

    Collection<Match> findAll();

    Match updateScore(MatchId matchId, SoccerScore soccerScore);

    record NewMatch(Team home, Team away) {
    }
}

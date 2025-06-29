package pl.ppiekarski.livescoreboard;

import java.util.Set;

sealed interface TeamValidationRule {
    void check(Team home, Team away, Set<Team> activeTeams);
}

final class NoTeamAlreadyPlayingRule implements TeamValidationRule {
    @Override
    public void check(Team home, Team away, Set<Team> activeTeams) {
        if (activeTeams.contains(home) || activeTeams.contains(away)) {
            throw new IllegalStateException("At least one of the teams is already playing now");
        }
    }
}

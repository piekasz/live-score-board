package pl.ppiekarski.livescoreboard;

record Match(MatchId matchId, Team homeTeam, Team awayTeam) {

    public Match(Team homeTeam, Team awayTeam) {
        this(new MatchId(homeTeam, awayTeam), homeTeam, awayTeam);
    }
}

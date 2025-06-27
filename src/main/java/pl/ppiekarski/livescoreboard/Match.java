package pl.ppiekarski.livescoreboard;

record Match(MatchId matchId, Team homeTeam, Team awayTeam) {

    public Match(Team homeTeam, Team awayTeam) {
        this(new MatchId(homeTeam, awayTeam), homeTeam, awayTeam);
    }

    public MatchDto toDto() {
        return new MatchDto(homeTeam.name(), awayTeam.name());
    }
}

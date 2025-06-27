package pl.ppiekarski.livescoreboard;

record SoccerScore(int home, int away) {

    public SoccerScore {
        if (home < 0 || away < 0) {
            throw new IllegalArgumentException("Score must be non-negative");
        }
    }
}

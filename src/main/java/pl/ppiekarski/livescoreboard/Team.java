package pl.ppiekarski.livescoreboard;

record Team(String name) {

    public Team {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Team name [" + name + "] is not valid. Must not be empty");
        }
        name = name.strip();
    }
}

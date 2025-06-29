package pl.ppiekarski.livescoreboard;

public sealed interface MatchId {
    Long unwrapLong();
}
record SequenceBasedMatchId(long sequence) implements MatchId, Comparable<SequenceBasedMatchId> {
    @Override
    public int compareTo(SequenceBasedMatchId other) {
        return Long.compare(this.sequence, other.sequence);
    }

    @Override
    public Long unwrapLong() {
        return sequence;
    }
}

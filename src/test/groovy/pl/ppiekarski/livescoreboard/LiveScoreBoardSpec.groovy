package pl.ppiekarski.livescoreboard

import spock.lang.Specification

class LiveScoreBoardSpec extends Specification {

    def liveScoreBoard = new LiveScoreBoard()

    def "should start a match and store it in liveScoreBoard"() {
        when:
        def result = liveScoreBoard.startMatch(new StartMatchCommand("teamA", "teamB"))
        then: "started match is in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto("teamA", "teamB", 0, 0)]
        and: "matchId is returned as result"
        result.isSuccess()
        result.getOrNull() instanceof MatchId
    }

    def "should update score correctly"() {
        given:
        MatchId matchId = liveScoreBoard.startMatch(new StartMatchCommand("teamA", "teamB"))
                .getOrNull()
        when:
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 0))
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 1))
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 2))
        then: "score is updated in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto("teamA", "teamB", 1, 2)]
    }

    def "should update score for proper game"() {
        given:
        MatchId matchId1 = liveScoreBoard.startMatch(new StartMatchCommand("teamA", "teamB"))
                .getOrNull()
        MatchId matchId2 = liveScoreBoard.startMatch(new StartMatchCommand("teamC", "teamD"))
                .getOrNull()
        when:
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId1, 1, 0))
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId2, 2, 2))
        then: "score is updated in liveScoreBoard"
        liveScoreBoard.getSummary() as Set == [
                new MatchDto("teamC", "teamD", 2, 2),
                new MatchDto("teamA", "teamB", 1, 0),
        ] as Set
    }

    def "should not update when score is invalid"() {
        given:
        MatchId matchId = liveScoreBoard.startMatch(new StartMatchCommand("teamA", "teamB"))
                .getOrNull()
        when:
        def result = liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 0, -1))
        then:
        result.isFailure()
        result.exceptionOrNull().message == "Score must be non-negative"
    }
}

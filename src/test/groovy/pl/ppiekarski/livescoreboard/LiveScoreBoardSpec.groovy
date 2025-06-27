package pl.ppiekarski.livescoreboard

import spock.lang.Specification

class LiveScoreBoardSpec extends Specification {

    private static final String HOME_TEAM_NAME = "teamA"
    private static final String AWAY_TEAM_NAME = "teamB"
    private static final startMatchCommand1 = new StartMatchCommand(HOME_TEAM_NAME, AWAY_TEAM_NAME)
    public static final String OTHER_HOME_TEAM_NAME = "teamC"
    public static final String OTHER_AWAY_TEAM_NAME = "teamD"
    public static final startMatchCommand2 = new StartMatchCommand(OTHER_HOME_TEAM_NAME, OTHER_AWAY_TEAM_NAME)

    def liveScoreBoard = new LiveScoreBoard()

    def "should start a match and store it in liveScoreBoard"() {
        when:
        def result = liveScoreBoard.startMatch(startMatchCommand1)
        then: "started match is in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto(HOME_TEAM_NAME, AWAY_TEAM_NAME, 0, 0)]
        and: "matchId is returned in success result"
        result.isSuccess()
        result.getOrNull() instanceof MatchId
    }

    def "should update score correctly"() {
        given:
        MatchId matchId = liveScoreBoard.startMatch(startMatchCommand1).getOrNull()
        when:
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 0))
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 1))
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 2))
        then: "score is updated in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2)]
    }

    def "should update score for proper match"() {
        given:
        MatchId matchId1 = liveScoreBoard.startMatch(startMatchCommand1).getOrNull()
        MatchId matchId2 = liveScoreBoard.startMatch(startMatchCommand2).getOrNull()
        when:
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId1, 1, 0))
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId2, 2, 2))
        then: "score is updated in liveScoreBoard"
        liveScoreBoard.getSummary() as Set == [
                new MatchDto(OTHER_HOME_TEAM_NAME, OTHER_AWAY_TEAM_NAME, 2, 2),
                new MatchDto(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 0),
        ] as Set
    }

    def "should not update when score is invalid"() {
        given:
        MatchId matchId = liveScoreBoard.startMatch(startMatchCommand1).getOrNull()
        when:
        def result = liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 0, -1))
        then:
        result.isFailure()
        result.exceptionOrNull().message == "Score must be non-negative"
    }


    def "should finish proper match"() {
        given: "some matches are started"
        liveScoreBoard.startMatch(startMatchCommand1).getOrNull()
        and: "match to be finished is also started"
        MatchId matchId = liveScoreBoard.startMatch(startMatchCommand2).getOrNull()
        when:
        def result = liveScoreBoard.finishMatch(matchId)
        then:
        result.isSuccess()
        liveScoreBoard.getSummary() == [new MatchDto(HOME_TEAM_NAME, AWAY_TEAM_NAME, 0, 0)]
    }
}

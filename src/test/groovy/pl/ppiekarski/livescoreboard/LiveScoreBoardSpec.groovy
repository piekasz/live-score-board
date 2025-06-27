package pl.ppiekarski.livescoreboard

import pl.ppiekarski.livescoreboard.api.MatchDto
import pl.ppiekarski.livescoreboard.api.StartMatchCommand
import pl.ppiekarski.livescoreboard.api.UpdateScoreCommand
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
        then: "matchId is returned in success result"
        result.isSuccess()
        result.getOrNull() instanceof MatchId
        and: "started match is in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto(result.getOrNull(), HOME_TEAM_NAME, AWAY_TEAM_NAME, 0, 0)]
    }

    def "should update score correctly"() {
        given:
        MatchId matchId = liveScoreBoard.startMatch(startMatchCommand1).getOrNull()
        when:
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 0))
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 1))
        liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 1, 2))
        then: "score is updated in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto(matchId, HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2)]
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
                new MatchDto(matchId2, OTHER_HOME_TEAM_NAME, OTHER_AWAY_TEAM_NAME, 2, 2),
                new MatchDto(matchId1, HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 0),
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
        MatchId matchId1 = liveScoreBoard.startMatch(startMatchCommand1).getOrNull()
        and: "match to be finished is also started"
        MatchId matchId2 = liveScoreBoard.startMatch(startMatchCommand2).getOrNull()
        when:
        def result = liveScoreBoard.finishMatch(matchId2)
        then:
        result.isSuccess()
        liveScoreBoard.getSummary().contains(new MatchDto(matchId1, HOME_TEAM_NAME, AWAY_TEAM_NAME, 0, 0))
        !liveScoreBoard.getSummary().contains(new MatchDto(matchId2, OTHER_AWAY_TEAM_NAME, AWAY_TEAM_NAME, 0, 0))
    }

    def "should fail updating score for finished or non existing match"() {
        given:
        MatchId matchId1 = liveScoreBoard.startMatch(startMatchCommand1).getOrNull()
        liveScoreBoard.finishMatch(matchId1)
        when:
        def result = liveScoreBoard.updateScore(new UpdateScoreCommand(matchId1, 1, 0))
        then:
        result.isFailure()
        result.exceptionOrNull().message.startsWith("Match not found by id")
    }

    def "should fail finishing finished or non existing match"() {
        given:
        MatchId matchId1 = liveScoreBoard.startMatch(startMatchCommand1).getOrNull()
        liveScoreBoard.finishMatch(matchId1)
        when:
        def result = liveScoreBoard.finishMatch(matchId1)
        then:
        result.isFailure()
        result.exceptionOrNull().message.startsWith("Match not found by id")
    }

    def "should return match summary with live matches in proper order"() {
        given: "some matches with different scores"
        var matchId = liveScoreBoard.startMatch(new StartMatchCommand("Mexico", "Canada"))
                .getOrNull()
        var mexicoCanada5 = liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 0, 5))
                .getOrNull()

        matchId = liveScoreBoard.startMatch(new StartMatchCommand("Spain", "Brazil"))
                .getOrNull()
        var spainBrazil12 = liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 10, 2))
                .getOrNull()

        matchId = liveScoreBoard.startMatch(new StartMatchCommand("Germany", "France"))
                .getOrNull()
        var germanyFrance4 = liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 2, 2))
                .getOrNull()

        matchId = liveScoreBoard.startMatch(new StartMatchCommand("Uruguay", "Italy"))
                .getOrNull()
        var uruguayItaly12 = liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 6, 6))
                .getOrNull()

        matchId = liveScoreBoard.startMatch(new StartMatchCommand("Argentina", "Australia"))
                .getOrNull()
        var argentinaAustralia4 = liveScoreBoard.updateScore(new UpdateScoreCommand(matchId, 3, 1))
                .getOrNull()

        when:
        def summaries = liveScoreBoard.getSummary()

        then:
        summaries.size() == 5
        summaries[0] == uruguayItaly12
        summaries[1] == spainBrazil12
        summaries[2] == mexicoCanada5
        summaries[3] == argentinaAustralia4
        summaries[4] == germanyFrance4
    }

    def "should be able to start second DIFFERENT match between same teams"() {
        given:
        def matchIdResult1 = liveScoreBoard.startMatch(startMatchCommand1)
        when:
        def matchIdResult2 = liveScoreBoard.startMatch(startMatchCommand1)
        then: "matches are different matches"
        matchIdResult1.isSuccess() && matchIdResult2.isSuccess()
        matchIdResult1.getOrNull() != matchIdResult2.getOrNull()
    }

    def "should validate team name"() {
        when:
        def result1 = liveScoreBoard.startMatch(new StartMatchCommand(" ", AWAY_TEAM_NAME))
        def result2 = liveScoreBoard.startMatch(new StartMatchCommand(HOME_TEAM_NAME, null))
        then:
        liveScoreBoard.getSummary() == []
        and: "matches failed to start, caused by invalid team names"
        result1.isFailure() && result1.exceptionOrNull().message == "Team name [ ] is not valid. Must not be empty"
        result2.isFailure()&& result2.exceptionOrNull().message == "Team name [null] is not valid. Must not be empty"
    }
}

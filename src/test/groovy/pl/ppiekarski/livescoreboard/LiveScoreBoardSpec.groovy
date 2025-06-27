package pl.ppiekarski.livescoreboard

import spock.lang.Specification

class LiveScoreBoardSpec extends Specification {

    def liveScoreBoard = new LiveScoreBoard()

    def "should start a match and store it in liveScoreBoard"() {
        when:
        liveScoreBoard.startMatch("teamA", "teamB")
        then: "started match is in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto("teamA", "teamB")]
    }

    def "should update score correctly"() {
        given:
        MatchId matchId = liveScoreBoard.startMatch("teamA", "teamB")
        when:
        liveScoreBoard.updateScore(matchId, 1, 0)
        liveScoreBoard.updateScore(matchId, 1, 1)
        liveScoreBoard.updateScore(matchId, 1, 2)
        then: "score is updated in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto("teamA", "teamB", 1, 2)]
    }
}

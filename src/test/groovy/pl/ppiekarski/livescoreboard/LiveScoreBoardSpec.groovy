package pl.ppiekarski.livescoreboard

import spock.lang.Specification

class LiveScoreBoardSpec extends Specification {

    def liveScoreBoard = new LiveScoreBoard()

    def "should start a match and store it in liveScoreBoard"(){
        when:
        liveScoreBoard.startMatch("teamA", "teamB")
        then: "started match is in liveScoreBoard"
        liveScoreBoard.getSummary() == [new MatchDto("teamA", "teamB")]
    }
}

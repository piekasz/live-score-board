<!-- TOC -->
* [Task Description](#task-description)
* [Own Thoughts, Conclusions etc...](#own-thoughts-conclusions-etc)
  * [Short Project Description](#short-project-description)
  * [TODO](#todo-)
  * [Open questions:](#open-questions)
<!-- TOC -->
# Task Description
[TaskDescription.md](TaskDescription.md)

# Own Thoughts, Conclusions etc...
- The library's public API should be minimal and easy to use
- It is library - API should be stable and independent to potential impl details changes in the future - OCP

## Short Project Description
Gradle Java 21 with no external dependencies - only spock for testing.

## TODO 
So far we manged to meet minimal requirements, but we still need to:
1. refactor and crystallize what is the library's public API, and what should stay as internal
2. consider if there could come out some new "common-sense" requirements (i.e. one team can play one match at a time)
and how those could be handled to without changing the API
3. Could add some usage example here in Readme, or just link the [LiveScoreBoardSpec.groovy](src%2Ftest%2Fgroovy%2Fpl%2Fppiekarski%2Flivescoreboard%2FLiveScoreBoardSpec.groovy)
as usage example
## Open questions:
* Teams can play each other more than once - MatchId should not be just teamNameBased
  * Match is identified by MatchId, but MatchDto should be identified by all fields,
  then MatchDto should also contain MatchId since we want to distinguish France-Italy 1st and 2nd game
* Team names could be validated (at least if not blank)
* there could be some mechanism to add some rules, i.e. like mentioned earlier - 
"one team can play one match at a time"

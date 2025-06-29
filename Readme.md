<!-- TOC -->
* [Task Description](#task-description)
* [Own Thoughts, Conclusions etc...](#own-thoughts-conclusions-etc)
  * [Short Project Description](#short-project-description)
  * [Usage Example:](#usage-example)
  * [Open questions:](#open-questions)
<!-- TOC -->
# Task Description
[TaskDescription.md](TaskDescription.md)

# Own Thoughts, Conclusions etc...
- The library's public API should be minimal and easy to use
- It is library - API should be stable and independent to potential impl details changes in the future - OCP

## Short Project Description
Gradle Java 21 with no external dependencies - only spock for testing.

## Usage Example:
[WorldCupScoreBoardExample.java](src%2Fmain%2Fjava%2Fpl%2Fppiekarski%2Flivescoreboard%2Fexample%2FWorldCupScoreBoardExample.java)\
also check tests:
[LiveScoreBoardSpec.groovy](src%2Ftest%2Fgroovy%2Fpl%2Fppiekarski%2Flivescoreboard%2FLiveScoreBoardSpec.groovy)
## Open questions:
* Teams can play each other more than once - MatchId should not be just teamNameBased
  * Match is identified by MatchId, but MatchDto should be identified by all fields,
  then MatchDto should also contain MatchId since we want to distinguish France-Italy 1st and 2nd game
* Team names could be validated (at least if not blank)
* there could be some mechanism to add some rules, i.e. like mentioned earlier - 
"one team can play one match at a time"

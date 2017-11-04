# Diana

## What is it?
Diana is a quick and easy way to control various aspects of your ship in [Artemis SBS](https://artemisspaceshipbridge.com/).  You can do anything from automatically raising shields on red alert to destroying the ship if it ever goes backwards.

## How do I get it?
Grab the EXE from the releases tab and save it somewhere, or build from source.  It's just an Eclipse Oxygen project.

## How do I use it?
If the program can't find your Artemis install directory, you'll have to tell it where it is on the first run.  After that, the first thing you'll see when you start it is a text field for writing your rules.  Diana works on the basis of rules and rulesets.  These are phrases of the form "IF this happens THEN do something".  You need to put a hard return between every clause.  For example:
```
IF
shields are raised
ship is at red alert
THEN
set rear shield power to 150%
set front shield power to 200%
```
You can chain clauses together within a rule, and you can write as many of these as you want.  When you're done, just go to the Artemis menu and select Connect.  It'll ask for the IP address (optionally, with port) of your Artemis server and you're away.

## What else can it do?
Diana supports the following conditions (IF clauses):
* Ship is at red alert
* Ship is reversing
* Shields are raised
* Ship is docked
* Ship is at warp
* Energy is below some number

It supports the following actions (THEN clauses):
* Go to red alert
* Raise shields
* Request dock
* Set system (primary beam, torpedo, front shield...) power to some percentage (0-300%)
* Set system coolant to some number (0-8)

Diana supports up to eight ships, although each instance can only connect to one ship at once.

## How can I help?
If you're here, you're already probably ninety per cent of the way to doing the most helpful thing possible: playing Artemis with Diana, experimenting, finding flaws in the code, and suggesting ways it could be made better.  If you want to contribute, submit a pull request or a bug report and I will get back to you.

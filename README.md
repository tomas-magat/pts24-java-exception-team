# Stone Age

This is the Java version of the semestral project from Principles of Software Design (1) course on FMFI UK, 2024/25. 

# Assignment - Stone Age game

Your task is to construct a component implementing the logic of board game Stone Age. These are the [rules](https://images.zmangames.com/filer_public/ee/88/ee888bec-5000-4566-9fb0-8c6475e706a0/zm7260_stone_age_rules.pdf).

## Design

I prepared a [design](pts24.png) template for your implementation. Besides that I prepared [simple description of the classes](classes.md) with estimations 

Note that the design is by no means complete. Most notably
- You have to add appropriate constructors. Chose your constructors in a way that enables good testing. E.g. card deck and building stacks do not need to be full.
- You have to add interfaces that separate classes that need to be separated.
- You probably want to do something with Bag class to handle the randomness involved better.
Also, it is highly likely, that I overlooked something and there are some issues that need to be resolved. Feel free to do necessary changes and adjustments.

I recommend to stick to the following timeline
- 11. 11. create teams, understand game rules, understand the design (so we can have a meaningful discussion on the lecture).
- 20. 11. the common part of the project is finished 
- 27. 11. you are done

## Implementation remarks

You should build your project either in Python or in Java. I started the projects for you, including several tools that might help you. See [pts24-python](https://github.com/relatko/pts1-23-python) and [pts24-java](https://github.com/relatko/pts1-23-java). Note that if you use Python, your code should be type annotated and mypy --strict should show no errors. Do not use any except for when handling json.

This is a fairly large project and it would take too long to implement. Thus you will collaborate while implementing the project.

* Create groups of, ideally, six to eight people. and fork the repository you agree to use. Green classes are provided in the project (Java GamePhaseController will be implemented soonish).
* Collaboratively implement the elements marked white (trivial) and blue, including factories and integration tests for two components. For blue elements create a Pull request (typically one PR per element, but you may merge several very trivial ones into one PR), discuss it and merge it into the master (I recommend main branch protection requiring PR with at least 1 approval). Blue classes are not of particular interest to me. The amount of points given here is quite limited (up to 20 points including non-coding aspects of common work like like making and reviewing PRs), so do not overthink stuff, but make it sound enough so you actually can write working integration tests. This also implies that the test coverage does not have to be great in these classes. While collaborating create new features in own branches and merge it to main with a PR after code review is done. Turn on branch protection. Try to keep CI green (you can enforce this by branch protection rules). You may use github issues to your the progress. You may split large task into smaller tasks. You may want to meet one time before you start your work. Splitting L tasks is a good thing to do.
It is expected that everybody prepares and reviews 3-5 PRs. 
* After this, you should add your own implementation of the red classes (including unit tests) and prepare two integration tests. To reduce the amount of work necessary, it is sufficient to create one end to end scenario for each of these integration test. 

Unit tests for Game should be solitary. Do a sociable unit test of CivilisationCardPlace that also uses CivilisationCardDeck. Tests of ResourceSource must be solitary. You can discuss technical aspects of the implementation, but everybody should implement the red classes and the red integration tests on his own. You can either work on local machine or use a private repository for your effort.

You should use Git and produce a reasonable history of commits. Note that to work on the red classes you do not need the blue ones. You are also free to adjust the implementation of blue and green classes according to the needs of your project (but, the changes, hopefully should not be large, a good example is changing interface name, adding a new interface, ...).



## Instructions for submitting your project

Send your solution to [lukotka.pts@gmail.com](lukotka.pts@gmail.com). The deadline is 30.11.2022 23:59:59. The solutions sent later will be accepted, however the number of points awarded may be reduced.

Send the solution to me either as compressed folders containing the whole repository (including the hidden .git files) or as a link to private repository with read access granted (GitHub handle `relatko`). Attach a link to the public repository for the team part of the project and link PRs you have made and PRs you have reviewed. 

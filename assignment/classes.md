# Stone Age

The component responsible for running the game according to the rules.

*Estimation for top level integration tests and factories:* 3h


## Facade classes

### StoneAgeGame class

This is an adapter class that converts external representation to internal representation. Namely it converts system-wide player ids to PlayerOrder objects relevant to the game and is the root for converting game state representation to JSON.

*Unit tests:* No. It is an adaptor/facade class with very simple to no responsibilities. Testing is best handled by an integration test

*Estimation:* 0.5h

### StoneAgeObservable class

Implements observer pattern. According to the rules the civilisation cards should be visible only to player. First of all, this is not that important as the information is available, if players remember it. Second, this cannot be realised for terminal multiplayer client. Thus we ship this aspect.

*Unit tests:* No. It is an adaptor/facade class with very simple to no responsibilities. Testing is best handled by an integration test

*Estimation:* XS


## PlayerBoard component

The component implements functionality associated with information present on player board (+ player points and fields). Besides that by applying informatin expert principle, this component is also responsible for tracking tribe feeding.

*Integration tests:* Focus on interactions involving multiple classes like tribe feeding and final points calculation.

*Estimation for integration tests and factories:* M

### PlayerBoard class

Composes state and the final points calculation. Stores points and houses (as there is no significant behaviour associated to these attributes.)

*Unit tests:* Yes.

*Estimation:* S

### PlayerBoardFacade

Facade class for PlayerBoard component that makes it easier for other components to interact with PlayerBoard.

*Unit tests:* No. It is an adaptor/facade class with very simple to no responsibilities. Testing is best handled by an integration test

*Estimation:* XS

### PlayerResourcesAndFood class

Stores resources and food, calculates points for leftover resources.

*Unit tests:* Yes. 

*Estimation:* S

### PlayerFigures class

Handles player figures that are on player board and are free to be used. Note that instead of moving figures back from game board we just reset the number of figures at the start of new turn.

*Unit tests:* Yes. Very simple.

*Estimation:* XS

### PlayerCivilisationCards class

Stores player civilisation card long term effects and calculates the final points associated with them.

*Unit tests:* Yes. Makes sense to produce quite a few unit test to be sure that this functionality is implemented correctly.

*Estimation:* S

### PlayerTools class

Handless player tools and one time use tools

*Unit tests:* Yes. This is quite a complex class, and requires significatn unit tests

*Estimation:* M

### TribeFedStatus class

Tracks number of fields and tribe feeding. SetTribeFed should be used in case of -10 point hit chosen.

*Unit tests:* Yes. Tests may be sociable

*Estimation:* L


## GameBoard component

Implements behaviours associated with game board (except for tracking points aand fields), related to player actions.

*Integration tests:* Yes. Focus on interaction involving RewardMenu, CurrentThrow 

*Estimation for integration tests and factories:* L (you can split this task into more smaller tasks)

## GameBoard class

Puts game board state together.

*Unit tests:* No. It is an adaptor/facade class with very simple to no responsibilities. Testing is best handled by an integration test

*Estimation:* XXS

### FigureLocationAdaptor

Adaptor replacing PlayerOrder with Player structures.

*Unit tests:* No. It is an adaptor/facade class with very simple to no responsibilities. Testing is best handled by an integration test

*Estimation:* XXS

### PlaceOnToolMakerAdaptor class

Adaptor between InterfaceFigureLocationInternal and ToolMakerHutFields for tool maker action.

*Unit tests:* No. It is an adaptor/facade class with very simple to no responsibilities. Testing is best handled by an integration test

*Estimation:* XXS

### PlaceOnHutAdaptor class

Adaptor between InterfaceFigureLocationInternal and ToolMakerHutFields for hut action.

*Unit tests:* No. It is an adaptor/facade class with very simple to no responsibilities. Testing is best handled by an integration test

*Estimation:* XXS

### PlaceOnFieldsAdaptor class

Adaptor between InterfaceFigureLocationInternal and ToolMakerHutFields for fields action.

*Unit tests:* No. It is an adaptor/facade class with very simple to no responsibilities. Testing is best handled by an integration test

*Estimation:* XS

### ToolMakerHutsFields class

Implements these three actions and interaction among them (when the game has less players)

*Unit tests:* Yes. Focus on the interaction.

*Estimation:* M

### ResourceSource class

Implements actions Hunting, Forest, Clay mound, Quarry, River.

*Unit tests:* Yes. Focus on interaction between players.

*Estimation:* M

### CurrectThrow class

Handles die throws for resources (Food, Wood, Clay, Stone, Gold) and with improving the throws with tools.

*Unit tests:* Yes. 

*Estimation:* M

### Throw class

Encapsulates the random effect.

*Unit tests:* No. Class needs to be mocked in integration tests

*Estimation:* XS

### CivilisationCardPlace class

Implements civilisation card actions 

*Unit tests:* Yes. May be sociable with CivilisationCardDeck. Things to focus include paying sufficint resources, validating that imediate effect evaluation was invoked, end of game effect was forwarded to player board, and that filling up the places on end of turn is handled properly. The instances may assume that newTurn is called in certain order. If so, do not forget to documment it and do not forget to test the behaviour in NewRoundState.

*Estimation:* L (you can split it into smaller tasks)

### CivilisationCardDeck class

Implements civilisation card deck.

*Unit tests:* Not necessary. Class is very simple. Sociable unit tests with CivilisationCardPlace is sufficient.

*Estimation:* XS

### RewardMenu class

Represents the choices available in All Players Take A Reward imediate civilisation card effect.

*Unit tests:* Yes. 

*Estimation:* M

### AllPlayersTakeAReward class

Represents corresponding immediate civilisation card effect. Throws dices and initializes reward menu.

*Unit tests:* Yes. 

*Estimation:* M

### GetSomethingThrow class

Represents corresponding immediate civilisation card effect. Uses CurrentThrow to perform the effect

*Unit tests:* Yes. 

*Estimation:* S

### GetSomethingFixed class

Represents corresponding immediate civilisation card effect. Uses CurrentThrow to perform the effect

*Unit tests:* Yes. 

*Estimation:* XS

### GetSomethingChoice class

Represents corresponding immediate civilisation card effect. Uses CurrentThrow to perform the effect

*Unit tests:* Yes. 

*Estimation:* XS

### GetCard class

Represents corresponding immediate civilisation card effect. Uses CurrentThrow to perform the effect

*Unit tests:* Yes. 

*Estimation:* S

### BouldingTile class

Represents building tiles, including hidden buildings and associated actions

*Unit tests:* Yes. May be sociable with e.g. SimpleBuilding

*Estimation:* L

### ArbiraryBuilding class

Represents building that allows 1-7 resources of any kind to be spent

*Unit tests:* Yes. 

*Estimation:* XS

### VariableBuilding class

Represents building that allows spending fixed number of resources of given number of resource type.

*Unit tests:* Yes. Be careful

*Estimation:* S

### SimpleBuilding class

Represents building that prescribes resources spent.

*Unit tests:* Yes. 

*Estimation:* XS


## GamePhaseController component

This components track game phases.

*Integration tests:* Focus on game ending scenatios and interactions not covered by GamePhaseController unit test

*Estimation for integration tests and factories:* M

### GamePhaseController class

Represents state transitions in the game. Besides moving the state after each succesfull player action the class tries to continue with player actions that do not require input.

*Unit tests:* Yes. Focus on transition

*Estimation:* XL

### PlaceFigure class

*Unit tests:* Yes. Focus on tryToMakeAutomaticAction method.

*Estimation:* S

### MakeActionState class

*Unit tests:* Yes. Focus on tryToMakeAutomaticAction method.

*Estimation:* S

### NewRoundState class

*Unit tests:* Yes. Focus on game end test. 

*Estimation:* S

### FeedTribe class

*Unit tests:* Yes. Focus on tryToMakeAutomaticAction method.

*Estimation:* S

### WaiitingForToolUse class

*Unit tests:* Yes. Focus on tryToMakeAutomaticAction method.

*Estimation:* S

### AllPlayesTakeAReward class

*Unit tests:* Yes. Focus on tryToMakeAutomaticAction method.

*Estimation:* S

### GameEnd class

*Unit tests:* Yes.

*Estimation:* XS


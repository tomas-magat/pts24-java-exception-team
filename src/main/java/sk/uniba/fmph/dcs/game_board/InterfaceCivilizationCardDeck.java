package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;

import java.util.Optional;

public interface InterfaceCivilizationCardDeck {

    Optional<CivilisationCard> getTop();

}

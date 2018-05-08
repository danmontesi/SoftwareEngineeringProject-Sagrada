package it.polimi.se2018.MatchTest.private_public_cards_test;

import it.polimi.se2018.public_obj_cards.ColorDiagonals;
import it.polimi.se2018.public_obj_cards.ColorVariety;
import it.polimi.se2018.public_obj_cards.ColumnColorVariety;
import it.polimi.se2018.public_obj_cards.PublicObjectiveCard;

import org.junit.Before;


import java.util.ArrayList;

public class PublicObjectiveCardsTest {

    ArrayList<PublicObjectiveCard> cards = new ArrayList<>();
    @Before
    public void setUp(){
        cards.add(new ColorDiagonals());
        cards.add(new ColorVariety());
        cards.add(new ColumnColorVariety());

    }
}

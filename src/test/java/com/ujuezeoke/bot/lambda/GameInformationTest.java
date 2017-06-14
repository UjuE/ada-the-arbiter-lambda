package com.ujuezeoke.bot.lambda;

import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.Buttons;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.GenericAttachments;
import com.ujuezeoke.game.TwoPlayerGame;
import org.junit.Test;

import java.util.AbstractMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Obianuju Ezeoke on 14/06/2017.
 */
public class GameInformationTest {

    private TwoPlayerGame gameOne = mock(TwoPlayerGame.class);
    private TwoPlayerGame gameTwo = mock(TwoPlayerGame.class);
    private TwoPlayerGame rockPaperScissors = mock(TwoPlayerGame.class);
    private GameGenericAttachmentInformation gameGenericAttachmentInformation = mock(GameGenericAttachmentInformation.class);

    @Test
    public void mapOfGameNameAndGameNameToDisplay() {
        when(gameOne.getGameName()).thenReturn("GameOne");
        when(gameTwo.getGameName()).thenReturn("GameTwo");
        when(rockPaperScissors.getGameName()).thenReturn("rockPaperScissors");

        final GameInformation gameInformation = new GameInformation(gameGenericAttachmentInformation, gameOne, gameTwo, rockPaperScissors);

        assertThat(gameInformation.gameNames().entrySet(), hasItems(entryWith("GameOne", "Game One"),
                entryWith("GameTwo", "Game Two"),
                entryWith("rockPaperScissors", "rock Paper Scissors")
        ));

    }

    @Test
    public void obtainTheRightGameChosen() {
        when(gameOne.getGameName()).thenReturn("GameOne");
        when(gameTwo.getGameName()).thenReturn("GameTwo");
        when(rockPaperScissors.getGameName()).thenReturn("rockPaperScissors");

        final GameInformation gameInformation = new GameInformation(gameGenericAttachmentInformation, gameOne, gameTwo, rockPaperScissors);

        assertThat(gameInformation.gameWithName("rockPaperScissors"), is(rockPaperScissors));
    }

    @Test
    public void getListOfGenericAttachments() {
        when(gameGenericAttachmentInformation.getImageUrlFor(any())).thenReturn("url");
        when(gameGenericAttachmentInformation.getAttachmentLinkUrlFor(any())).thenReturn("anotherUrl");
        when(gameOne.getGameName()).thenReturn("GameOne");
        when(gameTwo.getGameName()).thenReturn("GameTwo");
        when(rockPaperScissors.getGameName()).thenReturn("rockPaperScissors");

        final GameInformation gameInformation = new GameInformation(gameGenericAttachmentInformation, gameOne, gameTwo, rockPaperScissors);
        assertThat(gameInformation.genericAttachments(), hasItems(
                new GenericAttachments("Game One", "game",
                        "url",
                        "anotherUrl",
                        new Buttons("Game One", "Play GameOne"))
        ));

    }

    private AbstractMap.SimpleEntry<String, String> entryWith(String gameName, String gameNameToDisplay) {
        return new AbstractMap.SimpleEntry<>(gameName, gameNameToDisplay);
    }
}
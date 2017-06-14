package com.ujuezeoke.bot.lambda.handler;

import com.ujuezeoke.bot.lambda.GameGenericAttachmentInformation;
import com.ujuezeoke.bot.lambda.GameInformation;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.ElicitIntentDialogAction;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessage;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.GenericAttachments;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.ResponseCard;
import com.ujuezeoke.game.TwoPlayerGame;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Obianuju Ezeoke on 11/06/2017.
 */
public class ListAdaArbiterGamesIntentHandlerTest {

    private final TwoPlayerGame gameOne = mock(TwoPlayerGame.class);
    private final TwoPlayerGame gameTwo = mock(TwoPlayerGame.class);

    private final GameGenericAttachmentInformation gameGenericAttachmentInformation = new GameGenericAttachmentInformation();
    private GameInformation availableGamesMap = new GameInformation(gameGenericAttachmentInformation, gameOne, gameTwo);
    private ListAdaArbiterGamesIntentHandler underTest =
            new ListAdaArbiterGamesIntentHandler(availableGamesMap);
    private final String gameOneName = "GameOne";
    private final String gameTwoName = "GameTwo";


    @Before
    public void setUp() {
        when(gameOne.getGameName()).thenReturn(gameOneName);
        when(gameTwo.getGameName()).thenReturn(gameTwoName);
    }


    @Test
    public void displayListOfGames() {
        final ResponseCard responseCard = getDialogActionAsElicitIntent().getResponseCard();
        final DialogActionMessage message = getDialogActionAsElicitIntent().getMessage();

        assertThat(message.getContentType(), is(DialogActionMessageContentType.PlainText));
        assertThat(message.getContent(), containsString(gameOneName));
        assertThat(message.getContent(), containsString(gameTwoName));
        assertThat(responseCard.getGenericAttachments(),
                arrayContainingInAnyOrder(genericAttachementWithButtonContaining(gameOneName),
                        genericAttachementWithButtonContaining(gameTwoName)));
    }

    private Matcher<GenericAttachments> genericAttachementWithButtonContaining(final String gameName) {
        return new TypeSafeMatcher<GenericAttachments>() {
            @Override
            protected boolean matchesSafely(GenericAttachments item) {
                return Stream.of(item.getButtons())
                        .allMatch(it -> it.getValue()
                                .equals("Play " + gameName)
                        );
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    private ElicitIntentDialogAction getDialogActionAsElicitIntent() {
        return (ElicitIntentDialogAction) underTest.process().getDialogAction();
    }

}
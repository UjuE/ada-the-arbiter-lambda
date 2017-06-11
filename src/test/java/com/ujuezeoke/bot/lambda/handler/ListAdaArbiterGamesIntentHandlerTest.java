package com.ujuezeoke.bot.lambda.handler;

import com.ujuezeoke.bot.template.model.response.model.dialogaction.ElicitIntentDialogAction;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessage;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.GenericAttachments;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.ResponseCard;
import com.ujuezeoke.game.TwoPlayerGame;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.mock;

/**
 * Created by Obianuju Ezeoke on 11/06/2017.
 */
public class ListAdaArbiterGamesIntentHandlerTest {

    private HashMap<String, TwoPlayerGame> availableGamesMap = new HashMap<>();
    private ListAdaArbiterGamesIntentHandler underTest =
            new ListAdaArbiterGamesIntentHandler(availableGamesMap);

    @Test
    public void displayListOfGames() {
        final String gameOneName = "GameOne";
        availableGamesMap.put(gameOneName, mock(TwoPlayerGame.class));
        final String gameTwoName = "GameTwo";
        availableGamesMap.put(gameTwoName, mock(TwoPlayerGame.class));

        final ResponseCard responseCard = getDialogActionAsElicitIntent().getResponseCard();
        final DialogActionMessage message = getDialogActionAsElicitIntent().getMessage();

        assertThat(message.getContentType(), is(DialogActionMessageContentType.PlainText));
        assertThat(message.getContent(), containsString(gameOneName));
        assertThat(message.getContent(), containsString(gameTwoName));
        assertThat(responseCard.getGenericAttachments(),
                arrayContainingInAnyOrder(genericAttachement(gameOneName, gameTwoName)));
    }

    private Matcher<GenericAttachments> genericAttachement(final String gameOneName, String gameTwoName) {
        return new TypeSafeMatcher<GenericAttachments>() {
            @Override
            protected boolean matchesSafely(GenericAttachments item) {
                return Stream.of(item.getButtons())
                        .allMatch(it -> it.getValue()
                                .equals("Play " + gameOneName)
                                || it.getValue().equals("Play " + gameTwoName)
                        );
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    private ElicitIntentDialogAction getDialogActionAsElicitIntent() {
        return (ElicitIntentDialogAction)underTest.process().getDialogAction();
    }

}
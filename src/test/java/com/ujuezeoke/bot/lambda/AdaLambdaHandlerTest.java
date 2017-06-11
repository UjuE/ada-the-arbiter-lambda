package com.ujuezeoke.bot.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.ujuezeoke.bot.template.model.request.CurrentIntent;
import com.ujuezeoke.bot.template.model.request.LexBotRequest;
import com.ujuezeoke.bot.template.model.response.LexBotResponse;
import com.ujuezeoke.bot.template.model.response.model.DialogActionType;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.CloseDialogAction;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.ElicitIntentDialogAction;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.FulfillmentState;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Obianuju Ezeoke on 11/06/2017.
 */
public class AdaLambdaHandlerTest {

    private AdaLambdaHandler underTest = new AdaLambdaHandler();
    private LexBotRequest lexBotRequest = mock(LexBotRequest.class);
    private Context context = mock(Context.class);
    private CurrentIntent currentIntent = mock(CurrentIntent.class);

    @Test
    public void sendCloseFailedDialogActionLexResponse() {
        when(currentIntent.getName()).thenReturn("NotASupportedIntentName");
        when(lexBotRequest.getCurrentIntent()).thenReturn(currentIntent);

        final LexBotResponse lexBotResponse =
                underTest.handleRequest(lexBotRequest, context);

        assertThat(lexBotResponse.getDialogAction().getType(), is(DialogActionType.Close));
        assertThat(getAsCloseDialogAction(lexBotResponse).getFulfillmentState(), is(FulfillmentState.Failed));
        assertThat(getAsCloseDialogAction(lexBotResponse).getMessage().getContentType(),
                is(DialogActionMessageContentType.PlainText));
        assertThat(getAsCloseDialogAction(lexBotResponse).getMessage().getContent(),
                is("Hmmmm... I did not understand what you said."));
    }

    @Test
    public void sendElicitIntentDialogActionLexResponse() {
        when(currentIntent.getName()).thenReturn("AMAZON.HelpIntent");
        when(lexBotRequest.getCurrentIntent()).thenReturn(currentIntent);

        final LexBotResponse lexBotResponse =
                underTest.handleRequest(lexBotRequest, context);

        assertThat(lexBotResponse.getDialogAction().getType(), is(DialogActionType.ElicitIntent));
        assertThat(getAsElicitIntentDialogAction(lexBotResponse)
                .getMessage()
                .getContentType(), is(DialogActionMessageContentType.PlainText));
        assertThat(getAsElicitIntentDialogAction(lexBotResponse)
                .getMessage()
                .getContent(), is("I am Ada, the arbiter.\n" +
                "I can help you settle disputes.\nFor Example, " +
                "if you and a friend had trouble deciding between two valid web frameworks" +
                "you can say \"Help me decide between Bootstrap and Semantic UI\". " +
                "You would then be presented with a list of games to play to help the decision process.\n\n" +
                "To play a game, say \"Play <GameName>\".\n" +
                "To list name of available games, \"List games\".\n" +
                "How would you like me to help you today?"));

    }

    private CloseDialogAction getAsCloseDialogAction(LexBotResponse lexBotResponse) {
        return (CloseDialogAction) lexBotResponse.getDialogAction();
    }

    private ElicitIntentDialogAction getAsElicitIntentDialogAction(LexBotResponse lexBotResponse) {
        return (ElicitIntentDialogAction) lexBotResponse.getDialogAction();
    }
}
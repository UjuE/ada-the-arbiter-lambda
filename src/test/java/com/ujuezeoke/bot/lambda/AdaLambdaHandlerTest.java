package com.ujuezeoke.bot.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.ujuezeoke.bot.template.model.request.CurrentIntent;
import com.ujuezeoke.bot.template.model.request.LexBotRequest;
import com.ujuezeoke.bot.template.model.response.LexBotResponse;
import com.ujuezeoke.bot.template.model.response.model.DialogActionType;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.CloseDialogAction;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.FulfillmentState;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
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
        assertThat(getDialogActionAsType(lexBotResponse).getFulfillmentState(), is(FulfillmentState.Failed));
        assertThat(getDialogActionAsType(lexBotResponse).getMessage().getContentType(),
                is(DialogActionMessageContentType.PlainText));
        assertThat(getDialogActionAsType(lexBotResponse).getMessage().getContent(),
                is("Hmmmm... I did not understand what you said."));
    }

    private CloseDialogAction getDialogActionAsType(LexBotResponse lexBotResponse) {
        return (CloseDialogAction)lexBotResponse.getDialogAction();
    }
}
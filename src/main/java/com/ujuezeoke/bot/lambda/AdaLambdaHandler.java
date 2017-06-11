package com.ujuezeoke.bot.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.ujuezeoke.bot.template.LexBotRequestHandler;
import com.ujuezeoke.bot.template.model.request.LexBotRequest;
import com.ujuezeoke.bot.template.model.response.LexBotResponse;
import com.ujuezeoke.bot.template.model.response.LexBotResponseBuilder;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.FulfillmentState;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType;

/**
 * Created by Obianuju Ezeoke on 11/06/2017.
 * The entry point for the AWS Lambda
 */
public class AdaLambdaHandler implements LexBotRequestHandler {
    @Override
    public LexBotResponse handleRequest(LexBotRequest input, Context context) {
        return new LexBotResponseBuilder()
                .buildCloseDialogActionResponse()
                .withFulfilmentState(FulfillmentState.Failed)
                .withMessage(DialogActionMessageContentType.PlainText, "Hmmmm... I did not understand what you said.")
                .build();
    }
}

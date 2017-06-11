package com.ujuezeoke.bot.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ujuezeoke.bot.lambda.handler.AmazonHelpIntentHandler;
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

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public LexBotResponse handleRequest(LexBotRequest input, Context context) {
        final LambdaLogger logger = context.getLogger();
        log("Request", input, logger);
        final LexBotResponse lexBotResponse = processInput(input);
        log("Response", lexBotResponse, logger);
        return lexBotResponse;
    }

    private LexBotResponse processInput(LexBotRequest input) {
        switch (input.getCurrentIntent().getName()) {
            case "AMAZON.HelpIntent":
                return new AmazonHelpIntentHandler(input).process();
            default:
                return new LexBotResponseBuilder()
                        .buildCloseDialogActionResponse()
                        .withFulfilmentState(FulfillmentState.Failed)
                        .withMessage(DialogActionMessageContentType.PlainText, "Hmmmm... I did not understand what you said.")
                        .build();
        }
    }

    private void log(String label, Object objectToLog, LambdaLogger logger) {
        try {
            logger.log(label + ": " + OBJECT_MAPPER.writeValueAsString(objectToLog));
        } catch (JsonProcessingException e) {
            System.out.println("Failed to log");
        }
    }
}

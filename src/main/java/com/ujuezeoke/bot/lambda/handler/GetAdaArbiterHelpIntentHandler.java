package com.ujuezeoke.bot.lambda.handler;

import com.ujuezeoke.bot.template.model.request.LexBotRequest;
import com.ujuezeoke.bot.template.model.response.LexBotResponse;
import com.ujuezeoke.bot.template.model.response.LexBotResponseBuilder;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType;

/**
 * Created by Obianuju Ezeoke on 11/06/2017.
 */
public class GetAdaArbiterHelpIntentHandler {
    private final LexBotRequest input;

    public GetAdaArbiterHelpIntentHandler(LexBotRequest input) {
        this.input = input;
    }

    public LexBotResponse process() {
        return new LexBotResponseBuilder()
                .buildElicitIntentDialogActionResponse()
                .withMessage(DialogActionMessageContentType.PlainText, "I am Ada, the arbiter.\n" +
                        "I can help you settle disputes.\nFor Example, " +
                        "if you and a friend had trouble deciding between two valid web frameworks, " +
                        "you can say \"Help me decide between Bootstrap and Semantic UI\". " +
                        "You would then be presented with a list of games to play to help the decision process.\n\n" +
                        "To play a game, say \"Play <GameName>\".\n" +
                        "To list name of available games, \"List games\".\n" +
                        "How would you like me to help you today?")
                .build();
    }
}

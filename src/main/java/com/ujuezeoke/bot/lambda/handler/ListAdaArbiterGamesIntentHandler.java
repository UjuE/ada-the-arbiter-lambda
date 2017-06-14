package com.ujuezeoke.bot.lambda.handler;

import com.ujuezeoke.bot.lambda.GameInformation;
import com.ujuezeoke.bot.template.model.response.LexBotResponse;
import com.ujuezeoke.bot.template.model.response.LexBotResponseBuilder;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.Buttons;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.GenericAttachments;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Created by Obianuju Ezeoke on 11/06/2017.
 */
public class ListAdaArbiterGamesIntentHandler {
    private final GameInformation gameInformation;

    public ListAdaArbiterGamesIntentHandler(GameInformation gameInformation) {
        this.gameInformation = gameInformation;
    }

    public LexBotResponse process() {
        return new LexBotResponseBuilder()
                .buildElicitIntentDialogActionResponse()
                .withMessage(DialogActionMessageContentType.PlainText,
                        "I am able to play:\n" +
                                gameInformation
                                        .gameNames()
                                        .keySet()
                                        .stream()
                                        .collect(joining("\n"))
                ).withResponseCard(1, gameInformation.genericAttachments().toArray(new GenericAttachments[]{}))
                .build();

    }
}

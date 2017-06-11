package com.ujuezeoke.bot.lambda.handler;

import com.ujuezeoke.bot.template.model.response.LexBotResponse;
import com.ujuezeoke.bot.template.model.response.LexBotResponseBuilder;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.Buttons;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.GenericAttachments;
import com.ujuezeoke.game.TwoPlayerGame;

import java.util.Map;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Created by Obianuju Ezeoke on 11/06/2017.
 */
public class ListAdaArbiterGamesIntentHandler {
    private final Map<String, TwoPlayerGame> availableGamesMap;

    public ListAdaArbiterGamesIntentHandler(Map<String, TwoPlayerGame> availableGamesMap) {
        this.availableGamesMap = availableGamesMap;
    }

    public LexBotResponse process() {
        return new LexBotResponseBuilder()
                .buildElicitIntentDialogActionResponse()
                .withMessage(DialogActionMessageContentType.PlainText,
                        "I am able to play:\n" +
                                availableGamesMap
                                        .keySet()
                                        .stream()
                                        .collect(joining("\n"))
                ).withResponseCard(1, new GenericAttachments(
                        "Available Games",
                        "the game",
                        "http://www.ponderweasel.com/wp-content/uploads/2014/11/who-invented-rock-paper-scissors.png",
                        "http://www.ponderweasel.com/wp-content/uploads/2014/11/who-invented-rock-paper-scissors.png",
                        buildButtons()
                ))
                .build();

    }

    private Buttons[] buildButtons() {
        return availableGamesMap.keySet()
                .stream()
                .map(gameName -> new Buttons(gameName, "Play " + gameName))
                .collect(toList()).toArray(new Buttons[]{});
    }
}

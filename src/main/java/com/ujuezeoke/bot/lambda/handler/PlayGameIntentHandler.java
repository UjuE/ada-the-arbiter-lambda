package com.ujuezeoke.bot.lambda.handler;

import com.ujuezeoke.bot.template.model.request.LexBotRequest;
import com.ujuezeoke.bot.template.model.response.LexBotResponse;
import com.ujuezeoke.bot.template.model.response.LexBotResponseBuilder;
import com.ujuezeoke.bot.template.model.response.Slot;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.FulfillmentState;
import com.ujuezeoke.game.TwoPlayerGame;
import com.ujuezeoke.game.gameplay.playerdetails.PlayerDetails;
import com.ujuezeoke.game.gameplay.playerdetails.PlayerLabel;
import com.ujuezeoke.game.gameplay.result.GamePlayResult;

import java.util.Map;
import java.util.Optional;

import static com.ujuezeoke.bot.template.model.response.model.dialogaction.message.DialogActionMessageContentType.PlainText;
import static java.util.stream.Collectors.joining;

/**
 * Created by Obianuju Ezeoke on 11/06/2017.
 */
public class PlayGameIntentHandler {
    private static final String PLAYER_ONE_LABEL = "playerOne";
    private static final String PLAYER_TWO_LABEL = "playerTwo";
    private static final String GAME_NAME_SLOT_NAME = "gameName";
    private final LexBotRequest input;
    private final Map<String, TwoPlayerGame> availableGamesMap;

    public PlayGameIntentHandler(LexBotRequest input, Map<String, TwoPlayerGame> availableGamesMap) {
        this.input = input;
        this.availableGamesMap = availableGamesMap;
    }

    public LexBotResponse process() {
        final Map<String, Object> slots = input.getCurrentIntent().getSlots();
        if (slots.containsKey(PLAYER_ONE_LABEL)
                && slots.containsKey(PLAYER_TWO_LABEL)
                && slots.containsKey(GAME_NAME_SLOT_NAME)
                && Optional.ofNullable(slots.get(PLAYER_ONE_LABEL)).isPresent()
                && Optional.ofNullable(slots.get(PLAYER_TWO_LABEL)).isPresent()
                && Optional.ofNullable(slots.get(GAME_NAME_SLOT_NAME)).isPresent()) {
            final TwoPlayerGame twoPlayerGame =
                    availableGamesMap.get(slots.get(GAME_NAME_SLOT_NAME).toString().replaceAll(" ", ""));
            final GamePlayResult gamePlayResult = twoPlayerGame.playComputerVsComputer(
                    new PlayerLabel(slots.get(PLAYER_ONE_LABEL).toString()),
                    new PlayerLabel(slots.get(PLAYER_TWO_LABEL).toString()));

            return new LexBotResponseBuilder()
                    .buildCloseDialogActionResponse()
                    .withMessage(PlainText, messageFrom(gamePlayResult))
                    .withFulfilmentState(FulfillmentState.Fulfilled)
                    .build();
        } else if (!Optional.ofNullable(slots.get(GAME_NAME_SLOT_NAME)).isPresent()) {
            return new LexBotResponseBuilder()
                    .buildElicitSlotDialogActionResponse()
                    .withIntentName(input.getCurrentIntent().getName())
                    .withSlot(new Slot(GAME_NAME_SLOT_NAME, slots.getOrDefault(GAME_NAME_SLOT_NAME, null)))
                    .withSlot(new Slot(PLAYER_ONE_LABEL, slots.getOrDefault(PLAYER_ONE_LABEL, null)))
                    .withSlot(new Slot(PLAYER_TWO_LABEL, slots.getOrDefault(PLAYER_TWO_LABEL, null)))
                    .withSlotToElicit(GAME_NAME_SLOT_NAME)
                    .withMessage(PlainText,
                            "What game would you like to play? \n"
                                    +availableGamesMap.keySet().stream().collect(joining(",\n")))
                    .build();
        } else {
            return new LexBotResponseBuilder()
                    .buildElicitSlotDialogActionResponse()
                    .withIntentName(input.getCurrentIntent().getName())
                    .withSlot(new Slot(GAME_NAME_SLOT_NAME, slots.getOrDefault(GAME_NAME_SLOT_NAME, null)))
                    .withSlot(new Slot(PLAYER_ONE_LABEL, slots.getOrDefault(PLAYER_ONE_LABEL, null)))
                    .withSlot(new Slot(PLAYER_TWO_LABEL, slots.getOrDefault(PLAYER_TWO_LABEL, null)))
                    .withSlotToElicit(determine(slots))
                    .withMessage(PlainText, "who is " + determine(slots))
                    .build();
        }
    }

    private String determine(Map<String, Object> slots) {
        if (slots.containsKey(PLAYER_ONE_LABEL) && !Optional.ofNullable(slots.get(PLAYER_ONE_LABEL)).isPresent()) {
            return PLAYER_ONE_LABEL;
        }

        return PLAYER_TWO_LABEL;
    }

    private String messageFrom(GamePlayResult gamePlayResult) {
        StringBuilder stringBuilder = new StringBuilder();
        if (gamePlayResult.isTie()) {
            stringBuilder.append("It is a Tie!\n");
        } else {
            stringBuilder.append("And the winner is ")
                    .append(gamePlayResult.winner().get().getPlayerName())
                    .append("\n");
        }

        final PlayerDetails firstPlayerDetails = gamePlayResult.getFirstPlayerDetails();
        final PlayerDetails secondPlayerDetails = gamePlayResult.getSecondPlayerDetails();
        stringBuilder.append(firstPlayerDetails.getPlayerName()).append(" played ")
                .append(firstPlayerDetails.getGameThrow())
                .append("\n")
                .append(secondPlayerDetails.getPlayerName()).append(" played ")
                .append(secondPlayerDetails.getGameThrow());
        return stringBuilder.toString();
    }
}

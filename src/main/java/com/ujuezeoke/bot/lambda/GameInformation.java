package com.ujuezeoke.bot.lambda;

import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.Buttons;
import com.ujuezeoke.bot.template.model.response.model.dialogaction.responsecard.GenericAttachments;
import com.ujuezeoke.game.TwoPlayerGame;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by Obianuju Ezeoke on 14/06/2017.
 */
public class GameInformation {
    private final GameGenericAttachmentInformation gameGenericAttachmentInformation;
    private final List<TwoPlayerGame> games;

    public GameInformation(GameGenericAttachmentInformation gameGenericAttachmentInformation, TwoPlayerGame... games) {
        this(gameGenericAttachmentInformation, asList(games));
    }

    public GameInformation(GameGenericAttachmentInformation gameGenericAttachmentInformation, List<TwoPlayerGame> games) {
        this.gameGenericAttachmentInformation = gameGenericAttachmentInformation;
        this.games = games;
    }

    public Map<String, String> gameNames() {
        return games.stream()
                .map(TwoPlayerGame::getGameName)
                .collect(toMap(Function.identity(), it -> it.replaceAll("([A-Z]{1}[a-z]+)", " $1").trim()));
    }

    public TwoPlayerGame gameWithName(String gameName) {
        return games.stream()
                .filter(twoPlayerGame -> twoPlayerGame.getGameName().equals(gameName))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Not Yet Implemented"));
    }

    public List<GenericAttachments> genericAttachments() {
        return gameNames().entrySet().stream()
                .map(entry -> new GenericAttachments(
                        entry.getValue(),
                        "",
                        gameGenericAttachmentInformation.getImageUrlFor(entry.getKey()),
                        gameGenericAttachmentInformation.getAttachmentLinkUrlFor(entry.getKey()),
                        new Buttons(entry.getValue(), "Play " + entry.getKey())))
                .collect(toList());
    }
}

package com.ujuezeoke.bot.lambda;

/**
 * Created by Obianuju Ezeoke on 14/06/2017.
 */
public class GameGenericAttachmentInformation {
    public String getImageUrlFor(String gameName) {
        return "http://www.ponderweasel.com/wp-content/uploads/2014/11/who-invented-rock-paper-scissors.png";
    }

    public String getAttachmentLinkUrlFor(String gameName) {
        return "https://en.wikipedia.org/wiki/Rock%E2%80%93paper%E2%80%93scissors";
    }
}

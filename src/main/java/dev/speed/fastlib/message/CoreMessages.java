package dev.speed.fastlib.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum CoreMessages implements Message{

    PREFIX("prefix", "&c&lFast&6&lLib &8» &f"),
    INVALID_SENDER("invalid-sender", "{prefix}You must be a player to execute this command."),
    NO_PERMISSION("no-permission", "{prefix}You do not have permission to do that."),
    INVALID_USAGE("invalid-usage", "{prefix}Invalid usage. Use: {usage}"),
    PLAYER_NOT_FOUND("player-not-found", "{prefix}Player {player} not found."),
    NOT_A_NUMBER("not-a-number", "{prefix}{arg} is not a number.");

    private final String path;
    @Setter
    private Object value;


    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }
}

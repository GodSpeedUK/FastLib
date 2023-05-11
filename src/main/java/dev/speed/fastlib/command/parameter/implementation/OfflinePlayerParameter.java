package dev.speed.fastlib.command.parameter.implementation;

import dev.speed.fastlib.command.LibCommandFrame;
import dev.speed.fastlib.command.parameter.Parameter;
import dev.speed.fastlib.command.sender.LibCommandSender;
import dev.speed.fastlib.message.CoreMessages;
import dev.speed.fastlib.util.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerParameter extends Parameter<OfflinePlayer> {

    public OfflinePlayerParameter() {
        super(OfflinePlayer.class);
    }


    @Override
    public OfflinePlayer parse(String input) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(input);
        return offlinePlayer;
    }

    @Override
    public boolean isParsable(String input){
        if(input == null){
            return false;
        }
        return parse(input).hasPlayedBefore();
    }

    @Override
    public boolean handleNotParsable(LibCommandSender libCommandSender, LibCommandFrame libCommandFrame, String arg) {
        if(super.handleNotParsable(libCommandSender, libCommandFrame, arg)){
            CoreMessages.PLAYER_NOT_FOUND.send(libCommandSender, new Placeholder("{player}", arg));
        }
        return true;
    }

}

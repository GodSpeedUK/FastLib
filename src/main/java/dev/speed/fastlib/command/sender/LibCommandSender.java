package dev.speed.fastlib.command.sender;

import dev.speed.fastlib.command.CommandAuthResponse;
import dev.speed.fastlib.command.LibCommandFrame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class LibCommandSender {

    private final CommandSender commandSender;

    public Player getPlayer(){
        return (Player) commandSender;
    }

    public ConsoleCommandSender getConsole(){
        return (ConsoleCommandSender) commandSender;
    }

    public static LibCommandSender of(CommandSender commandSender){
        return new LibCommandSender(commandSender);
    }

    public boolean isPlayer(){
        return commandSender instanceof Player;
    }

    public CommandAuthResponse authorise(LibCommandFrame libCommandFrame){
        if(!isPlayer() && libCommandFrame.isRequiresPlayer()){
            return CommandAuthResponse.INVALID_SENDER;
        }

        if(libCommandFrame.getPermission() != null && !commandSender.hasPermission(libCommandFrame.getPermission())){
            return CommandAuthResponse.NO_PERMISSION;
        }

        return CommandAuthResponse.AUTHENTICATED;
    }
    public void sendMessage(String message){
        commandSender.sendMessage(message);
    }


}

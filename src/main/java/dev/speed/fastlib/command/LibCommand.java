package dev.speed.fastlib.command;

import dev.speed.fastlib.command.parameter.Parameter;
import dev.speed.fastlib.command.parameter.ParameterHolder;
import dev.speed.fastlib.command.sender.LibCommandSender;
import dev.speed.fastlib.message.CoreMessages;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.*;


public abstract class LibCommand extends Command implements LibCommandFrame {

    private final Map<String, LibSubCommand> subCommands;

    @Getter
    @Setter
    private boolean requiresPlayer = false;

    @Getter
    private Map<Integer, ParameterHolder> parameterHolders;

    public LibCommand(String name) {
        super(name);
        this.subCommands = new HashMap<>();
        this.parameterHolders = new HashMap<>();
    }

    public void addParameters(ParameterHolder... parameterHolders){
        for(ParameterHolder parameter: parameterHolders){
            this.parameterHolders.put(parameter.getArgument(), parameter);
        }
    }

    public Collection<LibSubCommand> getSubCommands(){
        return subCommands.values();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        LibCommandSender lCS = LibCommandSender.of(commandSender);
        if(strings.length == 0 || getSubCommand(strings[0]) == null) {
            CommandAuthResponse authResponse = lCS.authorise(this);
            return authenticateCommand(this, strings, lCS, authResponse);
        }

        LibSubCommand subCommand = getSubCommand(strings[0]);
        CommandAuthResponse authResponse = lCS.authorise(subCommand);
        return authenticateCommand(subCommand, strings, lCS, authResponse);
    }

    private boolean authenticateCommand(LibCommandFrame libCommandFrame, String[] strings, LibCommandSender lCS, CommandAuthResponse authResponse) {
        switch (authResponse){
            case INVALID_SENDER:
                CoreMessages.INVALID_SENDER.send(lCS);
                return true;
            case NO_PERMISSION:
                CoreMessages.NO_PERMISSION.send(lCS);
                return true;
            case AUTHENTICATED:

                if(libCommandFrame.getParameterHolders().size() > 0){
                    for(ParameterHolder parameterHolder: libCommandFrame.getParameterHolders().values()){
                        if(!parameterHolder.canExecute(strings)){
                            try{
                               String placeholder = strings[parameterHolder.getArgument()];
                               parameterHolder.getParameter().handleNotParsable(lCS, libCommandFrame, placeholder);
                               return true;
                            }catch (ArrayIndexOutOfBoundsException e){
                                parameterHolder.getParameter().handleNotParsable(lCS, libCommandFrame, null);
                                return true;
                            }
                        }
                    }
                }

                return libCommandFrame.perform(lCS, strings);
        }
        return false;
    }

    public LibSubCommand getSubCommand(String label){
        return subCommands.get(label);
    }

    public void addSubCommands(LibSubCommand... libSubCommands){
        for(LibSubCommand libSubCommand : libSubCommands){
            subCommands.put(libSubCommand.getLabel(), libSubCommand);
        }
    }

}

package dev.speed.fastlib.command;


import dev.speed.fastlib.command.parameter.ParameterHolder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class LibSubCommand implements LibCommandFrame {

    private final String label;
    private String permission;
    private String usage;
    private boolean requiresPlayer;
    private final Map<Integer, ParameterHolder> parameterHolders;

    public LibSubCommand(String label){
        this.label = label;
        this.parameterHolders = new HashMap<>();
    }

    public void addParameters(ParameterHolder... parameterHolders){
        for(ParameterHolder parameter: parameterHolders){
            this.parameterHolders.put(parameter.getArgument(), parameter);
        }
    }



}

package dev.speed.fastlib.command.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParameterHolder {

    private final int argument;
    private final boolean optional;
    private final Parameter<?> parameter;

    public boolean canExecute(String[] args){
        String argument;
        try{
            argument = args[this.argument];
        }catch (ArrayIndexOutOfBoundsException e){
            return optional;
        }

        return parameter.isParsable(argument);
    }

    public Object parse(String[] args){
        return parameter.parse(args[argument]);
    }

}

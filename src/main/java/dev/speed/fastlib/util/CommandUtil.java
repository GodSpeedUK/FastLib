package dev.speed.fastlib.util;


import dev.speed.fastlib.FastLib;
import dev.speed.fastlib.command.parameter.Parameter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandUtil {

    public Parameter<?> getParameter(Class<?> clazz){
        return FastLib.getInstance().getParameterManager().get(clazz);
    }

}

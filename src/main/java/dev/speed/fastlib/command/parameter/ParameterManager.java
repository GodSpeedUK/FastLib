package dev.speed.fastlib.command.parameter;

import dev.speed.fastlib.util.Manager;

public class ParameterManager extends Manager<Class<?>, Parameter<?>> {
    @Override
    public Class<?> getKey(Parameter<?> obj) {
        return obj.getClazz();
    }


}

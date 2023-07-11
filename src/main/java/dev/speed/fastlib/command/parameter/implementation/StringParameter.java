package dev.speed.fastlib.command.parameter.implementation;

import dev.speed.fastlib.command.parameter.Parameter;

public class StringParameter extends Parameter<String> {
    public StringParameter() {
        super(String.class);
    }

    @Override
    public String parse(String input) {
        return input;
    }
}

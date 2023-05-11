package dev.speed.fastlib.command.parameter.implementation;

import dev.speed.fastlib.command.LibCommandFrame;
import dev.speed.fastlib.command.parameter.Parameter;
import dev.speed.fastlib.command.sender.LibCommandSender;
import dev.speed.fastlib.message.CoreMessages;
import dev.speed.fastlib.util.Placeholder;

public class DoubleParameter extends Parameter<Double> {

    public DoubleParameter() {
        super(Double.class);
    }

    @Override
    public Double parse(String input) {
        try{
            double x = Double.parseDouble(input);

            if(x < 0){
                return null;
            }
            return x;
        }catch (NumberFormatException e) {
            return null;
        }

    }

    @Override
    public boolean handleNotParsable(LibCommandSender libCommandSender, LibCommandFrame libCommandFrame, String arg) {
        if(super.handleNotParsable(libCommandSender, libCommandFrame, arg)){
            CoreMessages.NOT_A_NUMBER.send(libCommandSender, new Placeholder("{arg}", arg));
        }

        return true;

    }

}

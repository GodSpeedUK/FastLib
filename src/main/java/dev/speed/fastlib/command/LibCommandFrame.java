package dev.speed.fastlib.command;

import dev.speed.fastlib.command.parameter.ParameterHolder;
import dev.speed.fastlib.command.sender.LibCommandSender;

import java.util.Map;

public interface LibCommandFrame {

    String getPermission();
    String getUsage();
    String getLabel();

    boolean isRequiresPlayer();
    boolean perform(LibCommandSender lCS, String[] args);

    Map<Integer, ParameterHolder> getParameterHolders();

}

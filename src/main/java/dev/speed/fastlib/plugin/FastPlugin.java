package dev.speed.fastlib.plugin;

import dev.speed.fastlib.command.LibCommand;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;

public abstract class FastPlugin extends JavaPlugin {


    public void registerEvents(Listener... listeners){
        for(Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @SneakyThrows
    public void registerCommands(LibCommand... commands){
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

        for(LibCommand command : commands) {
            commandMap.register(command.getLabel(), command);
        }
    }

}

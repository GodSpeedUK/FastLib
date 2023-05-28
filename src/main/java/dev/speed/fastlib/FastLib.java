package dev.speed.fastlib;

import dev.speed.fastlib.command.parameter.ParameterManager;
import dev.speed.fastlib.command.parameter.implementation.DoubleParameter;
import dev.speed.fastlib.command.parameter.implementation.IntegerParameter;
import dev.speed.fastlib.command.parameter.implementation.OfflinePlayerParameter;
import dev.speed.fastlib.command.parameter.implementation.PlayerParameter;
import dev.speed.fastlib.configuration.Configuration;
import dev.speed.fastlib.configuration.Serialization;
import dev.speed.fastlib.file.YamlFile;
import dev.speed.fastlib.item.Item;
import dev.speed.fastlib.message.CoreMessages;
import dev.speed.fastlib.message.TitleMessage;
import dev.speed.fastlib.plugin.FastPlugin;
import dev.speed.fastlib.user.manager.UserManager;
import lombok.Getter;

@Getter
public final class FastLib extends FastPlugin {

    @Getter
    private static FastLib instance;

    private ParameterManager parameterManager;

    @Getter
    private UserManager userManager;
    @Override
    public void onEnable() {
        instance = this;
        Serialization.register(Item.class);
        Serialization.register(TitleMessage.class);
        Configuration.loadConfig(new YamlFile("messages.yml", this.getDataFolder().getAbsolutePath(), null, this), CoreMessages.values());
       this.userManager = new UserManager();
       this.parameterManager = new ParameterManager();
       this.parameterManager.insert(new PlayerParameter());
       this.parameterManager.insert(new IntegerParameter());
       this.parameterManager.insert(new DoubleParameter());
       this.parameterManager.insert(new OfflinePlayerParameter());

    }

    @Override
    public void onDisable() {
        this.userManager.runSaveTask();
        // Plugin shutdown logic
    }
}

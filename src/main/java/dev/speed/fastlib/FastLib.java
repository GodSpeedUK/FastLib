package dev.speed.fastlib;

import dev.speed.fastlib.command.parameter.ParameterManager;
import dev.speed.fastlib.command.parameter.implementation.*;
import dev.speed.fastlib.configuration.Configuration;
import dev.speed.fastlib.configuration.Serialization;
import dev.speed.fastlib.file.YamlFile;
import dev.speed.fastlib.item.Item;
import dev.speed.fastlib.menu.DefaultMenu;
import dev.speed.fastlib.menu.MenuItem;
import dev.speed.fastlib.menu.MenuManager;
import dev.speed.fastlib.menu.listener.InventoryClickListener;
import dev.speed.fastlib.menu.listener.InventoryCloseListener;
import dev.speed.fastlib.message.CoreMessages;
import dev.speed.fastlib.message.TitleMessage;
import dev.speed.fastlib.plugin.FastPlugin;
import dev.speed.fastlib.tempdata.store.TempDataStore;
import dev.speed.fastlib.user.manager.UserManager;
import dev.speed.fastlib.util.Text;
import lombok.Getter;

@Getter
public final class FastLib extends FastPlugin {

    @Getter
    private static FastLib instance;

    private ParameterManager parameterManager;

    @Getter
    private UserManager userManager;

    @Getter
    private TempDataStore tempDataStore;

    @Getter
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;
        Serialization.register(Item.class);
        Serialization.register(TitleMessage.class);
        Serialization.register(MenuItem.class);

        Serialization.register(DefaultMenu.class);
        registerEvents(new InventoryClickListener(), new InventoryCloseListener());
        Configuration.loadConfig(new YamlFile("messages.yml", this.getDataFolder().getAbsolutePath(), null, this), CoreMessages.values());
        this.tempDataStore = new TempDataStore();
        this.userManager = new UserManager();
        this.menuManager = new MenuManager();
        this.parameterManager = new ParameterManager();
        this.parameterManager.insert(new PlayerParameter());
        this.parameterManager.insert(new IntegerParameter());
        this.parameterManager.insert(new DoubleParameter());
        this.parameterManager.insert(new OfflinePlayerParameter());
        this.parameterManager.insert(new StringParameter());
        String[] consoleMessage = Text.generateConsoleStartup("FastLib");
        for (String string : consoleMessage) {
            this.getLogger().info(string);
        }

    }


    @Override
    public void onDisable() {
        this.userManager.runSaveTask();
        // Plugin shutdown logic
    }
}

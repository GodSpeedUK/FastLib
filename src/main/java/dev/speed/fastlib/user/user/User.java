package dev.speed.fastlib.user.user;


import dev.speed.fastlib.FastLib;
import dev.speed.fastlib.command.sender.LibCommandSender;
import dev.speed.fastlib.file.gson.GsonUtil;
import dev.speed.fastlib.tempdata.data.TempData;
import dev.speed.fastlib.user.data.UserData;
import dev.speed.fastlib.user.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private final UUID uuid;

    private final HashMap<Class<? extends UserData>, UserData> userDataHashMap;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.userDataHashMap = new HashMap<>();
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public Player getPlayer() {
        return getOfflinePlayer().getPlayer();
    }

    public <T extends UserData> T getUserData(Class<T> type) {
        UserData userData;
        if (userDataHashMap.containsKey(type)) {
            userData = userDataHashMap.get(type);
        } else {
            Constructor<T> constructor;
            try {
                constructor = type.getDeclaredConstructor(UUID.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }

            try {
                userData = constructor.newInstance(uuid);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                return null;
            }

            userDataHashMap.put(type, userData);
        }
        return type.cast(userData);
    }

    public LibCommandSender getCommandSender() {
        return LibCommandSender.of(getPlayer());
    }
    public void save() {
        GsonUtil.save(UserManager.FOLDER, uuid.toString(), this);
    }

    public <T extends TempData> T getTempData(Class<T> type) {
        T tempData = FastLib.getInstance().getTempDataStore().getTempData(uuid, type);
        return type.cast(tempData);
    }
    public static User get(UUID uuid) {
        return FastLib.getInstance().getUserManager().getUser(uuid);
    }

}

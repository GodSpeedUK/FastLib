package dev.speed.fastlib.user.manager;



import dev.speed.fastlib.FastLib;
import dev.speed.fastlib.file.gson.GsonUtil;
import dev.speed.fastlib.user.user.User;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.*;

public class UserManager {

    public static final File FOLDER = new File(FastLib.getInstance().getDataFolder(), "user");

    private final List<User> saveQueue;

    private final Map<UUID, User> userMap;

    public UserManager() {
        this.userMap = new HashMap<>();
        this.saveQueue = new ArrayList<>();
        loadUsers();
        long delay = 300 * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(FastLib.getInstance(), this::runSaveTask, delay, delay);
    }

    private void loadUsers() {

        if (!FOLDER.exists()) {
            FOLDER.mkdirs();
            return;
        }

        if (FOLDER.listFiles() == null) {
            return;
        }

        for (File file : FOLDER.listFiles()) {
            String uuidString = file.getName().split("\\.")[0];
            UUID uuid;
            try {
                uuid = UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                continue;
            }

            userMap.put(uuid, GsonUtil.read(FOLDER, file.getName(), User.class));

        }

    }

    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    public User getUser(UUID uuid) {
        User user = userMap.get(uuid);
        if (user == null) {
            user = new User(uuid);
            userMap.put(uuid, user);
        }
        if (!saveQueue.contains(user)) {
            saveQueue.add(user);
        }
        return user;
    }

    public void runSaveTask() {

        List<User> savedUsers = new ArrayList<>();

        for (User user : saveQueue) {
            savedUsers.add(user);
            user.save();
        }

        saveQueue.removeAll(savedUsers);

    }

}

package dev.speed.fastlib.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@AllArgsConstructor
@Getter
public class LocationWrapper {

    private final String worldName;
    private final int x, y, z;


    public static LocationWrapper of(Location location){
        return new LocationWrapper(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public Location toBukkitLocation(){
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

}

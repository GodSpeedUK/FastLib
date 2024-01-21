package dev.speed.fastlib.util;

import lombok.Getter;

@Getter
public class Region {

    private final LocationWrapper first, second;

    public Region(LocationWrapper first, LocationWrapper second){
        String worldName = first.getWorldName();
        if(!worldName.equals(second.getWorldName())) throw new IllegalArgumentException("Worlds must be the same");

        int minX = Math.min(first.getX(), second.getX());
        int minY = Math.min(first.getY(), second.getY());
        int minZ = Math.min(first.getZ(), second.getZ());
        int maxX = Math.max(first.getX(), second.getX());
        int maxY = Math.max(first.getY(), second.getY());
        int maxZ = Math.max(first.getZ(), second.getZ());

        this.first = new LocationWrapper(worldName, minX, minY, minZ);
        this.second = new LocationWrapper(worldName, maxX, maxY, maxZ);
    }

}

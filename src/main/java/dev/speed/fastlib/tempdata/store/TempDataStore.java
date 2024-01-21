package dev.speed.fastlib.tempdata.store;

import dev.speed.fastlib.tempdata.data.TempData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TempDataStore {

    private final Map<Class<? extends TempData>, Map<UUID, TempData>> store;

    public TempDataStore() {
        this.store = new HashMap<>();
    }

    public void addTempData(TempData tempData) {
        Map<UUID, TempData> tempDataMap = store.getOrDefault(tempData.getClass(), new HashMap<>());
        tempDataMap.put(tempData.getUser(), tempData);
        store.put(tempData.getClass(), tempDataMap);
    }
    public void removeTempData(TempData tempData){
        Map<UUID, TempData> tempDataMap = store.getOrDefault(tempData.getClass(), new HashMap<>());
        tempDataMap.remove(tempData.getUser());
        store.put(tempData.getClass(), tempDataMap);
    }
    public <T extends TempData> T getTempData(UUID user, Class<T> tempData) {
        Map<UUID, TempData> tempDataMap;
        if(store.containsKey(tempData)) {
            tempDataMap = store.get(tempData);
        } else {
            tempDataMap = new HashMap<>();
            store.put(tempData, tempDataMap);
        }
        if(!tempDataMap.containsKey(user)) {
            try {
                tempDataMap.put(user, tempData.getDeclaredConstructor(UUID.class).newInstance(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tempData.cast(tempDataMap.get(user));
    }


}

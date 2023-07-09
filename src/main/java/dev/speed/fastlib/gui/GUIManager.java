package dev.speed.fastlib.gui;

import dev.speed.fastlib.util.Manager;

public class GUIManager extends Manager<String, GUIExecutor> {


    @Override
    public String getKey(GUIExecutor obj) {
        return obj.getGui().getId().toLowerCase();
    }

    public GUIExecutor get(GUI gui){
        return get(gui.getId());
    }

    @Override
    public GUIExecutor get(String id){
        return super.get(id.toLowerCase());
    }


}

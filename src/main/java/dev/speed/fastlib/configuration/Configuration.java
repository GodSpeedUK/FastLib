package dev.speed.fastlib.configuration;


import dev.speed.fastlib.file.YamlFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Map;

public interface Configuration {
    String getPath();

    Object getValue();

    void setValue(Object value);

    static void loadConfig(YamlFile iConfig, Configuration... values) {

        boolean saveConfig = false;

        YamlConfiguration config = iConfig.getConfig();

        for (Configuration conf : values) {
            if (!config.contains(conf.getPath())) {
                if (conf.getValue() instanceof Serializable) {
                    Map<String, Object> stringObjectMap = ((Serializable) conf.getValue()).serialize();
                    for (String key : stringObjectMap.keySet()) {
                        config.set(conf.getPath() + "." + key, stringObjectMap.get(key));
                    }
                } else {
                    config.set(conf.getPath(), conf.getValue());
                }
                saveConfig = true;
                continue;
            }

            if (conf.getValue() instanceof Serializable) {
                Class<? extends Serializable> clazz = Serialization.getSerializableClass((Serializable) conf.getValue());
                if (clazz == null) {
                    continue;
                }

                Object deserialized = Serialization.deserialize(clazz, iConfig, conf.getPath());

                conf.setValue(deserialized);
            } else {
                conf.setValue(config.get(conf.getPath()));
            }
        }

        if (saveConfig) {
            iConfig.saveConfig();
        }
    }

    default List<String> getStringList() {
        return (List<String>) getValue();
    }

    default String getString() {
        return (String) getValue();
    }

    default int getInt() {
        return (Integer) getValue();
    }

    default double getDouble() {
        return (Double) getValue();
    }

    default boolean getBoolean() {
        return (Boolean) getValue();
    }


    default void saveValue(YamlFile yamlFile) {
        if (getValue() instanceof Serializable) {
            Serializable serializable = (Serializable) getValue();
            Map<String, Object> serialized = serializable.serialize();
            for (String key : serialized.keySet()) {
                yamlFile.getConfig().set(getPath() + "." + key, serialized.get(key));
            }
        } else {
            yamlFile.getConfig().set(getPath(), getValue());
        }
        yamlFile.saveConfig();
    }
}

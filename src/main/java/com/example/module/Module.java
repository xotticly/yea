package com.example.module;

import com.example.settings.Setting;
import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final String name;
    private final String description;
    private final Category category;
    private final List<Setting> settings = new ArrayList<>();
    private int keybind = -1;
    private boolean enabled = false;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    protected void addSetting(Setting s) { settings.add(s); }
    public List<Setting> getSettings() { return settings; }

    @SuppressWarnings("unchecked")
    public <T extends Setting> T getSetting(String name) {
        for (Setting s : settings) {
            if (s.getName().equalsIgnoreCase(name)) return (T) s;
        }
        return null;
    }

    public void toggle() { setEnabled(!enabled); }

    public void setEnabled(boolean state) {
        if (state == enabled) return;
        enabled = state;
        if (enabled) onEnable(); else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public int getKeybind() { return keybind; }
    public void setKeybind(int keybind) { this.keybind = keybind; }

    public enum Category { COMBAT, MOVEMENT, RENDER, MISC }
}

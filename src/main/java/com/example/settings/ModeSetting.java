package com.example.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {
    private final List<String> modes;
    private int index;

    public ModeSetting(String name, String defaultMode, String... options) {
        super(name);
        this.modes = Arrays.asList(options);
        this.index = Math.max(0, modes.indexOf(defaultMode));
    }

    public String getMode() { return modes.get(index); }
    public List<String> getModes() { return modes; }
    public void cycle() { index = (index + 1) % modes.size(); }
    public void setMode(String mode) {
        int i = modes.indexOf(mode);
        if (i != -1) index = i;
    }
}

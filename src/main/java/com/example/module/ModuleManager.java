package com.example.module;

import com.example.module.modules.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void init() {
        // Movement
        modules.add(new Sprint());
        modules.add(new AutoWalk());
        modules.add(new Speed());
        modules.add(new Flight());
        modules.add(new NoFall());
        modules.add(new Scaffold());
        // Combat
        modules.add(new Velocity());
        modules.add(new Criticals());
        modules.add(new AutoTotem());
        modules.add(new AutoArmor());
        modules.add(new NoMissDelay());
        modules.add(new TriggerBot());
        modules.add(new AutoCrystal());
        // Render
        modules.add(new Fullbright());
        modules.add(new HUD());
        modules.add(new ESP());
        modules.add(new Tracers());
        modules.add(new ChestESP());
        modules.add(new TargetHud());
        // Misc
        modules.add(new AutoRespawn());
        modules.add(new AutoClicker());
        modules.add(new NoJumpDelay());
        modules.add(new NoBreakDelay());
        modules.add(new Freecam());
        modules.add(new AutoEat());
        modules.add(new AutoTool());
    }

    public List<Module> getModules() { return modules; }

    public List<Module> getEnabled() {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

    public List<Module> getByCategory(Module.Category cat) {
        return modules.stream().filter(m -> m.getCategory() == cat).collect(Collectors.toList());
    }

    public void onTick() {
        for (Module m : modules) if (m.isEnabled()) m.onTick();
    }
}

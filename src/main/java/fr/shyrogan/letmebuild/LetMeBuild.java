package fr.shyrogan.letmebuild;

import fr.shyrogan.letmebuild.hook.LMBHook;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Our plugin main class, allowing us to register and operate on
 * external plugins of landing claim like AreaShop/Guilds.
 *
 * This plugin is made to allow flying on these regions allowing players to build easily
 * it also disables fly automatically when he leaves it.
 *
 * @author Sébastien (Shyrogan)
 */
public final class LetMeBuild extends JavaPlugin {

    // A simple instance
    private static LetMeBuild instance;

    // Our Hooks cache.
    private final Map<Class<? extends LMBHook>, LMBHook> hooks = new LinkedHashMap<>();

    /**
     * Just our worldwide known onEnable Bukkit's method to load our plugin.
     *
     * Overall this just register our basic Hooks.
     */
    @Override
    public void onEnable() {
        // Load our plugin's instance.
        instance = this;

        // Load our hooks at the first server's tick, allowing us to load them after each plugins.
        getServer().getScheduler().runTaskLaterAsynchronously(this, () -> {

        }, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Register a new LetMeBuild's hook into LMB's hooks cache.
     *
     * @param hook Hook's instance
     * @param <T> Hook's type.
     */
    public <T extends LMBHook> void registerHook(T hook) {
        hooks.put(hook.getClass(), hook);
    }

    /**
     * Returns Hook's instance from his class.
     *
     * @param hookClass Hook's class.
     * @param <T> Hook's type.
     * @return Hook's instance.
     */
    public <T extends LMBHook> T getHook(Class<T> hookClass) {
        return (T)hooks.get(hookClass);
    }

}

package fr.shyrogan.letmebuild;

import fr.shyrogan.letmebuild.hook.LMBHook;
import fr.shyrogan.letmebuild.supports.GriefPreventionHook;
import fr.shyrogan.letmebuild.supports.RedProtectHook;
import fr.shyrogan.letmebuild.supports.WorldGuardHook;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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

    // Just storing future hooks class.
    private final List<Class<? extends LMBHook>> toLoad = new LinkedList<>();
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

        // Default config
        saveDefaultConfig();

        // Base hooks
        registerHook(WorldGuardHook.class);
        registerHook(RedProtectHook.class);
        registerHook(GriefPreventionHook.class);

        // Disabled
        List<String> disabled = (List<String>)getConfig().getList("disabled");

        // Load our hooks at the first server's tick, allowing us to load them after each plugins.
        getServer().getScheduler().runTaskLaterAsynchronously(this, () -> {
            // Instantiate our hooks.
            toLoad.forEach(hookClass -> {
                try {
                    LMBHook hook = hookClass.newInstance();
                    if(disabled.stream().anyMatch(s -> s.equalsIgnoreCase(hook.getName()))) {
                        return;
                    }

                    this.hooks.put(hookClass, hook);
                    getLogger().info("Registered Hook for " + hook.getName());
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            });

            // Load each hooks
            hooks.values().forEach(LMBHook::load);
            // Remove hook if supported plugin is not present.
            hooks.entrySet().removeIf(entry -> !entry.getValue().isReady());
            // Register Listeners.
            hooks.values().forEach(hook -> {
                getLogger().info("Enabled Hook for " + hook.getName());
                registerListener(hook);
            });
        }, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Returns LetMeBuild instance allowing to operate from
     * an external plugin.
     *
     * @return Instance
     */
    public static LetMeBuild getInstance() {
        return instance;
    }

    /**
     * Register a new LetMeBuild's hook into LMB's hooks cache.
     *
     * @param hook Hook's instance
     * @param <T> Hook's type.
     */
    public <T extends LMBHook> void registerHook(Class<T> hook) {
        toLoad.add(hook);
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

    /**
     * Method used to register Listener.
     *
     * @param l Listener
     */
    private void registerListener(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

}

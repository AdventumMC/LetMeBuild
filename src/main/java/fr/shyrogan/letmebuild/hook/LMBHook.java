package fr.shyrogan.letmebuild.hook;

import fr.shyrogan.letmebuild.hook.annotations.HookManifest;
import fr.shyrogan.letmebuild.hook.exceptions.IncompleteLMBHookException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.LinkedList;
import java.util.List;

/**
 * This simple class is made to know if a plugin if present.
 * Creating a Hook allow us to support a new plugin really easily.
 *
 * All you have to do is complete informations of annotations to make sure the supported plugin
 * is really present and enabled.
 *
 * @author Sébastien (Shyrogan)
 */
public abstract class LMBHook implements Listener {

    private static List<Player> fallingPlayers = new LinkedList<>();

    /**
     * A list containing players which are not flying anymore to
     * avoid fall damage.
     *
     * @return Falling players
     */
    public static List<Player> getFallingPlayers() {
        return fallingPlayers;
    }

    private final String name, mainClass;
    private boolean ready = false;

    /**
     * Simple constructor loading using our HookManifest annotations.
     * If the annotation isn't present, throw a IncompleteLMBHookException
     *
     * @see HookManifest
     * @see IncompleteLMBHookException
     */
    public LMBHook() {
        if(!getClass().isAnnotationPresent(HookManifest.class)) {
            throw new IncompleteLMBHookException(this);
        }

        HookManifest hm = getClass().getAnnotation(HookManifest.class);
        this.name = hm.name();
        this.mainClass = hm.mainClass();
    }

    public void load() {
        // Checks if plugin's main class is present.
        try {
            Class.forName(mainClass);
            ready = Bukkit.getPluginManager().getPlugin(name) != null;
        } catch (ClassNotFoundException e) {
            ready = false;
        }
    }

    /**
     * Allows us to know if main class has been found and hook is ready to be used.
     *
     * @return True if plugin is present.
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Returns Hook plugin's name.
     *
     * @return Plugin's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Allows a player to fly or disallow.
     * If it disallows when he was allowed, cancel his fall damage.
     *
     * @param player Player
     * @param fly Boolean
     */
    protected void allowFly(Player player, boolean fly) {
        // Player was flying.
        if(player.getAllowFlight()) {
            if(!fly && !player.isOnGround()) {
                getFallingPlayers().add(player);
            }
        } else {
            if(getFallingPlayers().contains(player)) {
                if(player.isOnGround()) {
                    getFallingPlayers().remove(player);
                }
            }
        }

        player.setAllowFlight(fly);
    }

}

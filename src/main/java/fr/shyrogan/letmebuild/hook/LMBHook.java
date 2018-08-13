package fr.shyrogan.letmebuild.hook;

import fr.shyrogan.letmebuild.hook.annotations.HookManifest;
import fr.shyrogan.letmebuild.hook.exceptions.IncompleteLMBHookException;
import org.bukkit.event.Listener;

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

    private final String name;
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

        // Checks if plugin's main class is present.
        try {
            Class.forName(hm.mainClass());
            ready = true;
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
}

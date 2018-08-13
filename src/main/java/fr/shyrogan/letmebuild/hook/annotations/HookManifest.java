package fr.shyrogan.letmebuild.hook.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation holding informations about a LMBHook
 * @see fr.shyrogan.letmebuild.hook.LMBHook
 *
 * @author Sébastien (Shyrogan)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HookManifest {

    /**
     * Returns supported plugin the name of our hook plugin.
     *
     * @return Supported plugin's name.
     */
    String name();

    /**
     * Returns our main class path to make sure the plugin is enabled and is present inside of
     * server's environment.
     * @see Class#forName(String)
     *
     * @return Plugin's main class.
     */
    String mainClass();

}

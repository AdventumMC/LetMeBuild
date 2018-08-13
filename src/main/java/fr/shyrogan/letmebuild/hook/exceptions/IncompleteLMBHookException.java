package fr.shyrogan.letmebuild.hook.exceptions;

import fr.shyrogan.letmebuild.hook.LMBHook;

/**
 * Exception thrown by a Hook if annotation is missing.
 * @see LMBHook
 *
 * @author Sébastien (Shyrogan)
 */
public class IncompleteLMBHookException extends RuntimeException {

    public IncompleteLMBHookException(LMBHook hook) {
        super("Attempting to register an incomplete LMBHook with class " + hook.getClass().getName());
    }

}

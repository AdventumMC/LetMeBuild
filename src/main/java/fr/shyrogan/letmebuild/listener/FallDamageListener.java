package fr.shyrogan.letmebuild.listener;

import fr.shyrogan.letmebuild.hook.LMBHook;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Sébastien (Shyrogan)
 */
public final class FallDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity().getType() == EntityType.PLAYER && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player p = (Player)e.getEntity();
            if(LMBHook.getFallingPlayers().contains(p)) {
                e.setCancelled(true);
                LMBHook.getFallingPlayers().remove(p);
            }
        }
    }

}
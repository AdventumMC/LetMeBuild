package fr.shyrogan.letmebuild.supports;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import fr.shyrogan.letmebuild.hook.LMBHook;
import fr.shyrogan.letmebuild.hook.annotations.HookManifest;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * A simple hook for WG checking if the player
 * is a member in the region.
 *
 * @author Sébastien (Shyrogan)
 */
@HookManifest(name = "WorldGuard", mainClass = "com.sk89q.worldguard.bukkit.WorldGuardPlugin")
public final class WorldGuardHook extends LMBHook {

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onMove(PlayerMoveEvent e) {
        final WorldGuardPlugin wg = WorldGuardPlugin.inst();
        final RegionManager rm = wg.getRegionManager(e.getTo().getWorld());

        if(e.getPlayer().getGameMode() == GameMode.CREATIVE || e.getPlayer().getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        boolean fly = rm.getApplicableRegions(new Vector(e.getTo().getX(), e.getTo().getY(), e.getTo().getZ()))
                .getRegions()
                .stream()
                .anyMatch(protectedRegion -> {
                    if(protectedRegion.getMembers().contains(e.getPlayer().getUniqueId()) || protectedRegion.getOwners().contains(e.getPlayer().getUniqueId())) {
                        return true;
                    }

                    // Checking through Username because of outdated plugins :(
                    return protectedRegion.getMembers().contains(e.getPlayer().getName()) || protectedRegion.getOwners().contains(e.getPlayer().getName());
                });

        // If he is a member, we allow flying, else we don't allow him.
        e.getPlayer().setAllowFlight(fly);
    }

}

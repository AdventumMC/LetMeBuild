package fr.shyrogan.letmebuild.supports;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.shyrogan.letmebuild.hook.LMBHook;
import fr.shyrogan.letmebuild.hook.annotations.HookManifest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * A simple hook for WG checking if the player
 * is a member in the region.
 *
 * @author Sébastien (Shyrogan)
 */
@HookManifest(name = "AreaShop", mainClass = "com.sk89q.worldguard.bukkit.WorldGuardPlugin")
public final class WorldGuardHook extends LMBHook {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        final WorldGuardPlugin wg = WorldGuardPlugin.inst();
        final RegionManager rm = wg.getRegionManager(e.getTo().getWorld());

        // If he is a member, we allow flying, else we don't allow him.
        e.getPlayer().setAllowFlight(
                rm.getApplicableRegions(new Vector(e.getTo().getX(), e.getTo().getY(), e.getTo().getZ())).getRegions()
                        .stream()
                        .map(ProtectedRegion::getMembers)
                        .anyMatch(defaultDomain -> defaultDomain.contains(e.getPlayer().getUniqueId()))
        );
    }

}

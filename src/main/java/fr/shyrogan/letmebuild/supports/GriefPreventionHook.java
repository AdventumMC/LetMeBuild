package fr.shyrogan.letmebuild.supports;

import fr.shyrogan.letmebuild.hook.LMBHook;
import fr.shyrogan.letmebuild.hook.annotations.HookManifest;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Hook for GriefPrevention
 * This hook isn't tested, please report any issue.
 *
 * @author Sébastien (Shyrogan)
 */
@HookManifest(name = "GriefPrevention", mainClass = "me.ryanhamshire.GriefPrevention.GriefPrevention")
public final class GriefPreventionHook extends LMBHook {

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR || p.hasPermission("letmebuild.bypass") || p.hasPermission("lmb.bypass")) {
            return;
        }

        // Is this plugin a trashcan?...
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(e.getTo(), true, null);
        p.setAllowFlight((claim != null && claim.canSiege(e.getPlayer())));
    }

}

package fr.shyrogan.letmebuild.supports;

import br.net.fabiozumbi12.RedProtect.Bukkit.events.EnterExitRegionEvent;
import fr.shyrogan.letmebuild.hook.LMBHook;
import fr.shyrogan.letmebuild.hook.annotations.HookManifest;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

/**
 * A simple hook for RedProtect
 *
 * @author Sébastien (Shyrogan)
 */
@HookManifest(name = "RedProtect", mainClass = "br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect")
public final class RedProtectHook extends LMBHook {

    @EventHandler
    public void onRegionEnter(EnterExitRegionEvent e) {
        final Player p = e.getPlayer();

        if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR || p.hasPermission("letmebuild.bypass") || p.hasPermission("lmb.bypass")) {
            return;
        }

        p.setAllowFlight(e.getEnteredRegion().isMember(e.getPlayer()) || e.getEnteredRegion().isAdmin(e.getPlayer()) || e.getEnteredRegion().isLeader(e.getPlayer()));
    }

}

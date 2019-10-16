package fr.shyrogan.letmebuild.supports;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import fr.shyrogan.letmebuild.hook.LMBHook;
import fr.shyrogan.letmebuild.hook.annotations.HookManifest;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

@HookManifest(name = "Towny", mainClass = "com.palmergames.bukkit.towny.Towny")
public final class TownyHook extends LMBHook {

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR || p.hasPermission("letmebuild.bypass") || p.hasPermission("lmb.bypass")) {
            return;
        }

        // Is this plugin a trashcan?...
        boolean bDestroy = PlayerCacheUtil.getCachePermission(p, e.getTo(), Material.STONE, TownyPermission.ActionType.DESTROY);
        allowFly(p, bDestroy);
    }

}

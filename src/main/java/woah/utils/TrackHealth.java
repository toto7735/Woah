package woah.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import woah.Woah;

public class TrackHealth {
    private static int taskId;

    public static void startTrack(Player player, Player targetPlayer) {
        taskId = new BukkitRunnable() {
            public void run() {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.translateColor("&b" + targetPlayer.getName() + "&f: &c" + targetPlayer.getHealth() + " HP")));
            }
        }.runTaskTimerAsynchronously(Woah.getInstance(), 0, 1).getTaskId();
    }

    public static void stopTrack() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

}

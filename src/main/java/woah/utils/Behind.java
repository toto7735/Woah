package woah.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import woah.Woah;

public class Behind {
    private static int taskId;
    private static LivingEntity livingEntity;

    public static void startGhost(Player targetPlayer) {
        livingEntity = (LivingEntity) targetPlayer.getWorld().spawnEntity(targetPlayer.getLocation(), EntityType.ZOMBIFIED_PIGLIN);
        livingEntity.setAI(false);
        livingEntity.setInvulnerable(true);
        taskId = new BukkitRunnable() {
            public void run() {
                // Code from spigot fourm
                float nang = targetPlayer.getLocation().getYaw() + 90;
                if (nang < 0) nang += 360;
                double nX;
                double nZ;
                nX = Math.cos(Math.toRadians(nang)) * 2;
                nZ = Math.sin(Math.toRadians(nang)) * 2;
                livingEntity.teleport(new Location(targetPlayer.getWorld(), targetPlayer.getLocation().getX() - nX, targetPlayer.getLocation().getY(), targetPlayer.getLocation().getZ() - nZ, targetPlayer.getLocation().getYaw(), targetPlayer.getLocation().getPitch()));
            }
        }.runTaskTimer(Woah.getInstance(), 0, 1).getTaskId();
    }

    public static void stopGhost() {
        Utils.getPlayer().playSound(Utils.getPlayer().getLocation(), "woah:horror2", 2, 1);
        Tricks.suprise();
        new BukkitRunnable() {
            public void run() {
                livingEntity.remove();
                Bukkit.getScheduler().cancelTask(taskId);
            }
        }.runTaskLater(Woah.getInstance(), 60);
    }

}

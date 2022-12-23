package woah.utils;

import com.sk89q.worldedit.extension.input.DisallowedUsageException;
import net.nuggetmc.tplus.api.utils.MojangAPI;
import net.nuggetmc.tplus.bot.Bot;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import woah.Woah;

import java.util.Random;

public class Mobs {

    private static int taskId;

    public static void spawnHorseBoss(Location location) {
        SkeletonHorse skeletonHorse = (SkeletonHorse) location.getWorld().spawnEntity(location, EntityType.SKELETON_HORSE);
        skeletonHorse.setCustomName(Utils.translateColor("&6&lPumpkin Horse"));
        skeletonHorse.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN));
        skeletonHorse.setMaxHealth(100);
        skeletonHorse.setHealth(100);
        skeletonHorse.setCustomNameVisible(true);

        BossBar bossBar = Woah.getInstance().getServer().createBossBar(Utils.translateColor("&6&lPumpkin Horse"), BarColor.RED, BarStyle.SEGMENTED_6);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(onlinePlayer);
        }
        new BukkitRunnable() {
            public void run() {
                if (skeletonHorse.getHealth() <= 0 || skeletonHorse.isDead()) {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        bossBar.removePlayer(onlinePlayer);
                        bossBar.setVisible(false);
                    }
                    this.cancel();
                    return;
                }
                bossBar.setProgress(skeletonHorse.getHealth() / skeletonHorse.getMaxHealth());

            }
        }.runTaskTimerAsynchronously(Woah.getInstance(), 0, 1);
        new BukkitRunnable() {
            boolean used = false;
            public void run() {
                if (skeletonHorse.getHealth() <= 0 || skeletonHorse.isDead()) {
                    this.cancel();
                    return;
                }
                if (!used && skeletonHorse.getHealth() < (skeletonHorse.getMaxHealth() / 2)) {
                    used = true;
                    skeletonHorse.setGravity(false);
                    skeletonHorse.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 3));
                    skeletonHorse.getWorld().playSound(skeletonHorse.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 0.5F);
                    new BukkitRunnable() {
                        public void run() {
                            new BukkitRunnable() {
                                int i = 0;
                                public void run() {
                                    if (i >= 5) {
                                        skeletonHorse.setGravity(true);
                                        this.cancel();
                                        return;
                                    }

                                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.ORANGE, 10);

                                    Vector playerLocVector = Utils.getPlayer().getLocation().toVector();
                                    Vector tridentLocVector = skeletonHorse.getLocation().toVector();

                                    // code from spigot fourm
                                    Vector bv = tridentLocVector.clone().subtract(playerLocVector);
                                    Vector dv = bv.clone().normalize();

                                    for (int i = 0; i < bv.length(); i++) {
                                        Vector particlePoint = playerLocVector.clone().add(dv.clone().multiply(i));
                                        Utils.getPlayer().getWorld().spawnParticle(Particle.REDSTONE,
                                                particlePoint.getX(), particlePoint.getY(), particlePoint.getZ(), 0, 0.001, 1, 0, 1, dustOptions);
                                    }
                                    
                                    skeletonHorse.getWorld().playSound(skeletonHorse.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 2F);
                                    skeletonHorse.getWorld().playSound(skeletonHorse.getLocation(), Sound.ENTITY_SKELETON_HORSE_HURT, 1, 1F);
                                    ++i;
                                }
                            }.runTaskTimer(Woah.getInstance(), 0, 20);
                        }
                    }.runTaskLater(Woah.getInstance(), 40);
                }
                switch (new Random().nextInt(2)) {
                    case 0:
                        for (Entity nearbyEntity : skeletonHorse.getNearbyEntities(10, 10, 10)) {
                            if (!(nearbyEntity instanceof Player) || nearbyEntity.isOp()) continue;
                            ((Player) nearbyEntity).getWorld().strikeLightningEffect(nearbyEntity.getLocation());
                            ((Player) nearbyEntity).damage(0.1);
                        }
                        break;
                    case 1:
                        for (Entity nearbyEntity : skeletonHorse.getNearbyEntities(10, 10, 10)) {
                            if (!(nearbyEntity instanceof Player) || nearbyEntity.isOp()) continue;
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.ORANGE, 10);
                            for (int i = 0; i < 10; ++i) nearbyEntity.getWorld().spawnParticle(Particle.REDSTONE, nearbyEntity.getLocation(), 1, 1, Math.tan(5), Math.sin(5), Math.sin(5), dustOptions);
                            ((Player) nearbyEntity).damage(0.2);
                        }
                        break;

                }
            }
        }.runTaskTimer(Woah.getInstance(), 20, 30);
    }

    public static void spawnClone(Location location) {
        Bot bot = Bot.createBot(location, "§c" + Utils.getPlayer().getName(), MojangAPI.getSkin(Utils.getPlayer().getName()));
        bot.getBukkitEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200);
        bot.getBukkitEntity().setHealth(200);
        location.getWorld().playSound(location, Sound.ENTITY_WITHER_SPAWN, 1, 0.5F);
    }

    public static void stopTrack() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

}

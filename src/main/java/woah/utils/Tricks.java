package woah.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import woah.Woah;

import java.util.Random;

public class Tricks {

    public static void spawnMonsters() {
            for (int i = 0; i < 5; ++i) {
                LivingEntity ghost = (LivingEntity) Utils.getPlayer().getWorld().spawnEntity(Utils.getPlayer().getLocation().add(new Random().nextInt(3 + 3) - 3, new Random().nextInt(3 + 3) - 3, new Random().nextInt(3 + 3) - 3), EntityType.ZOMBIE);
                //            ghostp.setDisplayName(Utils.translateColor("&f&lGhost"));
                ghost.setInvisible(true);
                ghost.setSilent(true);

                ItemStack ghostplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                LeatherArmorMeta ghostp = (LeatherArmorMeta) ghostplate.getItemMeta();
                ghostp.setColor(Color.fromRGB(255, 255, 255));
                ghostplate.setItemMeta(ghostp);
                ghost.getEquipment().setChestplate(ghostplate);

                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setOwner("SpaceBro_123");
                skullMeta.setDisplayName(Utils.translateColor("&f&lGhost Suit"));
                skull.setItemMeta(skullMeta);
                ghost.getEquipment().setHelmet(skull);

                Utils.getPlayer().getWorld().playSound(Utils.getPlayer().getLocation(), Sound.ENTITY_ELDER_GUARDIAN_DEATH, 1, 0.5F);
            }

    }

    public static void throwFires() {
        new BukkitRunnable() {
            int i = 20;
            public void run() {
                if (i <= 0) this.cancel();

                FallingBlock block = Utils.getTricker().getLocation().getWorld()
                        .spawnFallingBlock(Utils.getTricker().getLocation(), Material.FIRE, (byte) 0);
                Vector dir = Utils.getTricker().getLocation().getDirection();
                block.setVelocity(dir.multiply(1));
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 0.1F, 1);
                --i;
            }
        }.runTaskTimer(Woah.getInstance(), 0, 2);

    }

    public static void suprise() {
        Utils.getPlayer().playSound(Utils.getPlayer().getLocation(), "woah:horror", 2, 1);
        Utils.getPlayer().getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
        new BukkitRunnable() {
            public void run() {
                Utils.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
            }
        }.runTaskLater(Woah.getInstance(), 30);
    }

    public static void throwBlindPotion() {
        ThrownPotion thrownPotion = (ThrownPotion) Utils.getTricker().getWorld().spawnEntity(Utils.getTricker().getLocation(), EntityType.SPLASH_POTION , false);

//        thrownPotion.setMetadata("potion", new FixedMetadataValue(Woah.getInstance(), "blind"));

        ItemStack itemStack = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0), true);
        itemStack.setItemMeta(potionMeta);
        thrownPotion.setItem(itemStack);

        Vector v = Utils.getTricker().getLocation().add(0, 3, 0).getDirection();
        v.multiply(1.01);
        thrownPotion.setVelocity(v);
    }

    public static void makeFakePlayer() {
        Bukkit.broadcastMessage(Utils.translateColor("&ePewDiePee joined the game"));
        new BukkitRunnable() {
            public void run() {
                Bukkit.broadcastMessage(Utils.translateColor("&fPewDiePee: ?"));
                new BukkitRunnable() {
                    public void run() {
                        Bukkit.broadcastMessage(Utils.translateColor("&ePewDiePee left the game"));
                    }
                }.runTaskLater(Woah.getInstance(), 5);
            }
        }.runTaskLater(Woah.getInstance(), 10);
    }

}
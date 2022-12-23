package woah.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import woah.Woah;

import java.util.Arrays;

public class Items implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (isInHand(player, "§d§lFast But Weak Sword") && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            if (!CooldownManager.getAndTellCoolDown(player.getUniqueId(), player.getItemInHand().getItemMeta().getDisplayName())) return;
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 4, true, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0, true, true, false));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1, 1);

            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, -1, 0), 10);
            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 10);
            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0), 10);

            this.sendActionBar(player, "&bYou used the skill of " + player.getItemInHand().getItemMeta().getDisplayName() + "&b!");
            CooldownManager.setCoolDown(player.getUniqueId(), player.getItemInHand().getItemMeta().getDisplayName(), 5);
        } else if (isInHand(player, "§e§lOne-Click Bow") && (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            Vector playerDirection = player.getLocation().getDirection().multiply(1.5);
            Arrow arrow = player.launchProjectile(Arrow.class, playerDirection);
            arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
            new BukkitRunnable() {
                public void run() {
                    if (arrow.isDead() || arrow.isOnGround() || !arrow.isValid()) this.cancel();
                    Location loc = arrow.getLocation();
                        loc.getWorld().spawnParticle(Particle.END_ROD, loc, 0, 0, 0, 0, 1);

                }
            }.runTaskTimer(Woah.getInstance(), 2, 1);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1.5F);
        } else if (isInHand(player, "§c§lHeal Potion") && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 6));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2F);
            for (int i = 0; i < 50; ++i) player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(Utils.nextDoubleBetween(-1.5, 1.5), Utils.nextDoubleBetween(-1.5, 1.5), Utils.nextDoubleBetween(-1.5, 1.5)), 10);

            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        } else if (isInHand(player, "§9§lParticler") && (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            if (!CooldownManager.getAndTellCoolDown(player.getUniqueId(), player.getItemInHand().getItemMeta().getDisplayName())) return;

            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 10, Math.sin(5), Math.cos(5), Math.sin(5));
            for (Entity nearbyEntity : player.getNearbyEntities(Math.sin(5), Math.cos(5), Math.sin(5))) {
                if (nearbyEntity instanceof LivingEntity) ((LivingEntity) nearbyEntity).damage(3);
                nearbyEntity.getWorld().spawnParticle(Particle.END_ROD, nearbyEntity.getLocation(), 1, Math.sin(3), Math.cos(3), Math.tan(3));
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHAIN_BREAK, 1, 2F);
            }
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2F);
            this.sendActionBar(player, "&bYou used the skill of " + player.getItemInHand().getItemMeta().getDisplayName() + "&b!");
            CooldownManager.setCoolDown(player.getUniqueId(), player.getItemInHand().getItemMeta().getDisplayName(), 2);
        } else if (isInHand(player, "§e§lCheese TNT") && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            if (!CooldownManager.getAndTellCoolDown(player.getUniqueId(), player.getItemInHand().getItemMeta().getDisplayName())) return;
            player.setVelocity(player.getVelocity().setY(1));
            for (Entity nearbyEntity : player.getNearbyEntities(5, 5, 5)) {
                if (nearbyEntity instanceof LivingEntity) ((LivingEntity) nearbyEntity).damage(20);
                nearbyEntity.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, nearbyEntity.getLocation(), 1, Math.sin(3), Math.cos(3), Math.tan(3));
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, 1, 0.5F);
            }

            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1, 0.5F);

            this.sendActionBar(player, "&bYou used the skill of " + player.getItemInHand().getItemMeta().getDisplayName() + "&b!");
            CooldownManager.setCoolDown(player.getUniqueId(), player.getItemInHand().getItemMeta().getDisplayName(), 10);
        }
    }
    private boolean isInHand(Player player, String itemname) {
        return (player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(itemname));
    }

    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
    }

    public static ItemStack getHealingPotion() {
        ItemStack itemStack = new ItemStack(Material.POTION);
        PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
        itemMeta.setColor(Color.RED);
        itemMeta.setDisplayName("§c§lHeal Potion");
        itemMeta.setLore(Arrays.asList("§7A heal potion.", "", "§e§lRight-Click", "§7Heal §c3❤"));
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getFastButWeakSword() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§d§lFast But Weak Sword");
        itemMeta.setLore(Arrays.asList("§7A very fast but weak sword.", "", "§e§lRight-Click", "§7Gain §fSpeed V §7and §9Weakness I §7for §c5 seconds§7."));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getLeatherBoots() {
        ItemStack itemStack = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eLeather Boots");
        itemMeta.setLore(Arrays.asList("§7Wear this if you don't want to die", "§7when you pass through the snow"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getParticler() {
        ItemStack itemStack = new ItemStack(Material.CORNFLOWER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§9§lParticler");
        itemMeta.setLore(Arrays.asList("§7Beautiful flower that can emit particles.", "", "§e§lLeft-Click", "§7Summons §9particles §7that do §c3 damage §7if hit!"));
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

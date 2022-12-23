package woah.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import woah.Woah;

public class Ghost {
    public static void startGhost(Player player) {
//        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
//        armorStand.setVisible(false);
//        armorStand.getEquipment().setHelmet(Utils.getSkullByName("AkKarmash"));
//        armorStand.setGravity(false);
//        taskId = new BukkitRunnable() {
//            public void run() {
//                armorStand.teleport(player.getLocation());
//                armorStand.getLocation().setYaw(player.getLocation().getYaw());
//                armorStand.getLocation().setPitch(player.getLocation().getPitch());
//            }
//        }.runTaskTimer(Woah.getInstance(), 0, 1).getTaskId();
        ItemStack ghost = Utils.getSkullByName("SpaceBro_123");
        SkullMeta itemMeta = (SkullMeta) ghost.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&fYOU ARE A GHOST!"));
        ghost.setItemMeta(itemMeta);
        player.getInventory().setHelmet(ghost);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 255, true, false));
    }


}

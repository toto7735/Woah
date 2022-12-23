package woah.listeners;

import net.raidstone.wgevents.events.RegionEnteredEvent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import woah.Woah;
import woah.utils.Items;
import woah.utils.Mobs;
import woah.utils.Tricks;
import woah.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Listeners implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setFormat(event.getPlayer().getName() + ": " + event.getMessage());
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().isOp()) return;
        ItemStack item = Utils.getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDg2OTAyY2M1YzIxMjY2Yjg3OTM4ZThmMzExOWNhNGUzYjNjZTk4NTI0MTQxODQ1YjA0NzhmNzM5ZmRkMGNhMyJ9fX0=");
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Pumpkin Bag");
        meta.setLore(Arrays.asList(""));
        item.setItemMeta(meta);
        event.getPlayer().getInventory().addItem(item);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().hasItemMeta() || !player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Spawn Monsters")) {
            Tricks.spawnMonsters();
        } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Suprise")) {
            Tricks.suprise();
        } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Throw a Fire")) {
            Tricks.throwFires();
        } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Throw a Blind Potion")) {
            Tricks.throwBlindPotion();
        } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Spawn a Horse Boss")) {
            Mobs.spawnHorseBoss(player.getLocation());
        } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Confine")) {
            Utils.confine(Utils.getPlayer(), 3, 2, true);
        } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Spawn a Clone")) {
            Mobs.spawnClone(Utils.getTricker().getLocation());
        } else if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Make a fake player")) {
            Tricks.makeFakePlayer();
        }
    }

    @EventHandler
    public void onArmorStand(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player) || event.getEntity().getName().contains("§c")) return;
        if ((((Player) event.getEntity()).getHealth() - event.getDamage()) <= 0) event.setCancelled(true);
    }

    @EventHandler
    public void onRegionEntered(RegionEnteredEvent event) {
        if (event.getPlayer() == null) return;
        Player player = event.getPlayer();
        switch (event.getRegion().getId()) {
            case "hole1" -> {
                Tricks.suprise();
                player.teleport(new Location(player.getWorld(), 65.5, 30, 77.5, -90, 0));
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.PLAYER_HEAD)) return;
        if (event.getClickedBlock().hasMetadata("egg")) return;
        event.getClickedBlock().setMetadata("egg", new FixedMetadataValue(Woah.getInstance(), "egg"));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        player.sendTitle("", Utils.translateColor("&7You found a &6pumpkin bag&7!"));

        Location loc = event.getClickedBlock().getLocation();

        new BukkitRunnable() {
            int i = 10;
            public void run() {
                if (i <= 0) {
                    event.getClickedBlock().setType(Material.AIR);
                    for (int i = 0; i < 10; ++i) event.getClickedBlock().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, event.getClickedBlock().getLocation(), 10, Math.sin(3), Math.sin(3), Math.sin(3));
                    event.getClickedBlock().getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                    this.cancel();
                    return;
                }

                ItemStack itemStack = null;

                switch (new Random().nextInt(6)) {
                    case 0:
                        itemStack = new ItemStack(Material.GOLD_INGOT);
                        break;
                    case 1:
                        itemStack = new ItemStack(Material.IRON_INGOT);
                        break;
                    case 2:
                        itemStack = new ItemStack(Material.PUMPKIN_PIE);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(Utils.translateColor("&6&lPumpkin pie"));
                        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7wuwwwuw.")));
                        itemStack.setItemMeta(itemMeta);
                        break;
                    case 3:
                        itemStack = new ItemStack(Material.CARROT); // resource pack
                        itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(Utils.translateColor("&d&lCandy"));
                        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7delicious!")));
                        itemStack.setItemMeta(itemMeta);
                        break;
                    case 4:
                        itemStack = new ItemStack(Material.BAKED_POTATO); // resource pack
                        itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(Utils.translateColor("&b&lCandy"));
                        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7delicious!")));
                        itemStack.setItemMeta(itemMeta);
                        break;
                    case 5:
                        itemStack = new ItemStack(Material.COOKED_PORKCHOP); // resource pack
                        itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(Utils.translateColor("&c&lChocolate"));
                        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7delicious!!!!!!")));
                        itemStack.setItemMeta(itemMeta);
                        break;
                    case 6:
                        itemStack = new ItemStack(Material.MELON); // resource pack
                        itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(Utils.translateColor("&c&lCRollyPop"));
                        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7delicious!!!!!!")));
                        itemStack.setItemMeta(itemMeta);
                        break;
                }

                Item item = loc.getWorld().dropItem(loc, itemStack);
                item.setVelocity(item.getVelocity().setY(0.5));
                item.getWorld().playSound(item.getLocation(), Sound.ENTITY_CHICKEN_EGG, 3, 1);

                --i;
            }
        }.runTaskTimer(Woah.getInstance(), 0, 2);

    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        event.setDroppedExp(0);
        event.getDrops().clear();
        if (entity instanceof Player || entity.getCustomName() == null) return;
        if (entity.getCustomName().contains("Pumkie") || entity.getCustomName().contains("Pumkie")) {
            if (new Random().nextInt(2) == 0) {
                Entity entity1 = entity.getWorld().dropItem(entity.getLocation(), Items.getHealingPotion());
                Woah.team1.addEntry(entity1.getUniqueId().toString());
                entity1.setGlowing(true);
            }
        } else if (entity.getCustomName().contains("Pumpkin Horse")) {
            Entity entity1 = entity.getWorld().dropItem(entity.getLocation(), Items.getFastButWeakSword());
            Woah.team2.addEntry(entity1.getUniqueId().toString());
            entity1.setGlowing(true);
        }
    }

    @EventHandler
    public void onBotDeath(PlayerDeathEvent event) {
        if (!event.getEntity().getName().contains(Utils.getPlayer().getName())) return;
        event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), Items.getParticler()).setGlowing(true);
    }

    @EventHandler
    public void onJoin2(PlayerJoinEvent event) {
//        event.getPlayer().setResourcePack("https://github.com/toto7735/Tricking-People-Resourcepack/releases/download/1.2/SPOOKY.zip");
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        List<String> list = Arrays.asList("§a§lSpawn Monsters", "§c§lSuprise", "§6§lThrow a Fire", "§0§lThrow a Blind Potion", "§6§lSpawn a Horse Boss", "§c§lConfine", "§9§lSpawn a Clone", "§9§lMake a fake player");
        if (!event.getItemInHand().getType().equals(Material.PLAYER_HEAD) || event.getItemInHand().getItemMeta().getDisplayName().equals("") || !list.contains(event.getItemInHand().getItemMeta().getDisplayName())) return;
        event.setCancelled(true);
    }
}

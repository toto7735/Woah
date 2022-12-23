package woah.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import woah.Woah;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static ItemStack getSkullByName(String name) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(name);
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static String translateColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Player getPlayer() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) if (!onlinePlayer.isOp()) return onlinePlayer;

        // for test
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            return onlinePlayer;
        }
        return null;
    }

    public static Player getTricker() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) if (onlinePlayer.isOp()) return onlinePlayer;
        return null;
    }

    public static void pasteSchematic(Location location, File file) {
        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        Clipboard clipboard;
        BlockVector3 blockVector3 = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (clipboardFormat != null) {
            try (ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(file))) {
                if (location.getWorld() == null)
                    throw new NullPointerException("Failed to paste schematic due to world being null");
                World world = BukkitAdapter.adapt(location.getWorld());
                EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).build();
                clipboard = clipboardReader.read();
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(blockVector3)
                        .ignoreAirBlocks(false)
                        .build();
                try {
                    Operations.complete(operation);
                    editSession.close();
                    for (Entity current : Bukkit.getWorld("world").getEntities()) if (current instanceof Item || current instanceof Arrow || current instanceof TNTPrimed) current.remove();
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setInventory(Player tricker) {
        tricker.getInventory().clear();

        ItemStack i1 = getSkullByName("mems0r");
        ItemMeta itemMeta = i1.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&a&lSpawn Monsters"));
        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7Click to spawn monsters!")));
        i1.setItemMeta(itemMeta);
        tricker.getInventory().setItem(1, i1);

        i1 = getSkullByName("Titan");
        itemMeta = i1.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&c&lSuprise"));
        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7Click to suprise!")));
        i1.setItemMeta(itemMeta);
        tricker.getInventory().setItem(2, i1);

        i1 = getSkullByName("Nazg");
        itemMeta = i1.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&6&lThrow a Fire"));
        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7Click to throw a fire!")));
        i1.setItemMeta(itemMeta);
        tricker.getInventory().setItem(3, i1);

        i1 = getSkullByName("polkadot44");
        itemMeta = i1.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&0&lThrow a Blind Potion"));
        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7Click to throw a blind potion!")));
        i1.setItemMeta(itemMeta);
        tricker.getInventory().setItem(4, i1);

        i1 = getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q4ODY0M2IwNDg2Nzk2MjRhNDJlZTA0NjY2YTQ4NjVlYzE2ODcxYzczMjc5NzM0NjVhZjZiZDVhYmRkOGNhNyJ9fX0=");
        itemMeta = i1.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&6&lSpawn a Horse Boss"));
        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7Click to throw spawn a horse boss!")));
        i1.setItemMeta(itemMeta);
        tricker.getInventory().setItem(5, i1);

        i1 = getSkullByValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZiMWQ0OTQ3NzYzOTE3ODE0YWUxNjMyYjgyMDY5NjA5ODkyNzg5NWFhYWYxMjRjZDI5ZWIzNTg1NmFhYTViOSJ9fX0=");
        itemMeta = i1.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&c&lConfine"));
        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7Click to confine!")));
        i1.setItemMeta(itemMeta);
        tricker.getInventory().setItem(6, i1);

        i1 = getSkullByName(Utils.getPlayer().getName());
        itemMeta = i1.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&9&lSpawn a Clone"));
        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7Click to spawn a clone!")));
        i1.setItemMeta(itemMeta);
        tricker.getInventory().setItem(7, i1);

        i1 = getSkullByName("sub2pewdiepie12");
        itemMeta = i1.getItemMeta();
        itemMeta.setDisplayName(Utils.translateColor("&9&lMake a fake player"));
        itemMeta.setLore(Arrays.asList(Utils.translateColor("&7Click to make a fake player!")));
        i1.setItemMeta(itemMeta);
        tricker.getInventory().setItem(8, i1);
    }

    public static ItemStack getSkullByValue(String value) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), (String) null);
        profile.getProperties().put("textures", new Property("textures", value));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var5) {
            var5.printStackTrace();
        }
        item.setItemMeta(skullMeta);
        return item;
    }

    /**
     * @author xMakerx | Maverick
     * @edited toto
     */
    public static void confine(Entity ent, int sideLength, int height, boolean wantRoof) {
        Material fence = Material.IRON_BARS;
        Material roof = Material.IRON_BLOCK;
        Location entLoc = ent.getLocation();
        int delta = (sideLength / 2);
        Location corner1 = new Location(entLoc.getWorld(), entLoc.getBlockX() + delta, entLoc.getBlockY() + 1, entLoc.getBlockZ() - delta);
        Location corner2 = new Location(entLoc.getWorld(), entLoc.getBlockX() - delta, entLoc.getBlockY() + 1, entLoc.getBlockZ() + delta);
        int minX = Math.min(corner1.getBlockX(), corner2.getBlockX());
        int maxX = Math.max(corner1.getBlockX(), corner2.getBlockX());
        int minZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
        int maxZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());
        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 2);
        Map<Block, Material> list = new HashMap<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if ((x == minX || x == maxX) || (z == minZ || z == maxZ)) {
                        Block b = corner1.getWorld().getBlockAt(x, entLoc.getBlockY() + y, z);
                        list.put(b, b.getType());
                        b.setType(fence);
                        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 0.2F, 0.5F);
                    }
                    if (y == height - 1 && wantRoof) {
                        Block b = corner1.getWorld().getBlockAt(x, entLoc.getBlockY() + y + 1, z);
                        list.put(b, b.getType());
                        b.setType(roof);
                        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 0.2F, 1.5F);
                    }
                }
            }
        }
        new BukkitRunnable() {
            public void run() {
                for (Block block : list.keySet()) {
                    block.setType(list.get(block));
                }
                getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1, 1F);
            }
        }.runTaskLater(Woah.getInstance(), 40);
    }

    public static void finish() {
        Utils.getPlayer().sendTitle(Utils.translateColor("&d&lloL"), Utils.translateColor("&fActually &fyou couldn't &cdie"), 5, 100, 10);
        Utils.getPlayer().sendMessage(Utils.translateColor("btw happy halloween"));

    }

    public static double nextDoubleBetween(double min, double max) {
        return (ThreadLocalRandom.current().nextDouble() * (max - min)) + min;
    }

    public static NBTTagList makePotion(PotionEffect effect) {
        NBTTagList list = new NBTTagList();
        NBTTagCompound potionType = new NBTTagCompound();
        potionType.a("Id", (byte) effect.getType().getId());
        potionType.a("Amplifier", (byte) effect.getAmplifier());
        potionType.a("Duration", effect.getDuration());
        potionType.a("Ambient", (byte) 0); //Not ambient
        list.add(potionType);
        return list;
    }


    
}

package woah.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import woah.utils.Behind;
import woah.utils.Ghost;
import woah.utils.TrackHealth;
import woah.utils.Utils;

import java.io.File;
import java.util.Arrays;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (!player.isOp()) {
            player.sendMessage("No u");
            return false;
        }
        if (command.getName().equals("starttricking")) {
            player.setGameMode(GameMode.CREATIVE);
            player.setInvulnerable(true);
            player.setAllowFlight(true);
            player.setFlying(true);
            Utils.setInventory(player);
            Ghost.startGhost(player);
            Utils.getPlayer().setGameMode(GameMode.ADVENTURE);
            Utils.getPlayer().setMaxHealth(60);
            Utils.getPlayer().setHealth(60);
            TrackHealth.startTrack(player, Utils.getPlayer());
            Utils.getPlayer().teleport(new Location(Utils.getPlayer().getWorld(), 65.5, 30, 77.5, -90, 0));
            Utils.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
            Utils.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 255));
            Utils.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 255));
            Utils.getPlayer().sendTitle(Utils.translateColor("&6u..."), Utils.translateColor("&eyou didn't give me &dcandies&e..."), 5, 95, 10);
            Behind.startGhost(Utils.getPlayer());
        } else if (command.getName().equals("pasteschematic")) {
            Bukkit.broadcastMessage("pasting...");
            Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 59.5, 38, 50.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\map_1.schem"));
            Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 113.5, 42, 59.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\map_2.schem"));
            Utils.pasteSchematic(new Location(Bukkit.getWorld("world"), 434.5, 51, 133.5), new File("plugins\\FastAsyncWorldEdit\\schematics\\map_3.schem"));
            Bukkit.broadcastMessage("success!");
        }
        if (command.getName().equals("tellthem")) {
            Bukkit.broadcastMessage(Utils.translateColor(String.join(" ", Arrays.copyOfRange(strings, 0, strings.length))));
        }
        if (command.getName().equals("finish")) {
            Utils.finish();
        }
        if (command.getName().equals("stopbehind")) {
            Behind.stopGhost();
        }
        if (command.getName().equals("strikelightning")) {
            player.getWorld().strikeLightning(player.getLocation());
        }
        return false;
    }
}

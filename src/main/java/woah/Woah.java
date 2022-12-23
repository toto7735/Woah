package woah;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import woah.commands.Commands;
import woah.listeners.Listeners;
import woah.utils.Items;

public class Woah extends JavaPlugin {

    private static Woah plugin;
    public static Team team1;
    public static Team team2;

    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("starttricking").setExecutor(new Commands());
        this.getCommand("pasteschematic").setExecutor(new Commands());
        this.getCommand("tellthem").setExecutor(new Commands());
        this.getCommand("finish").setExecutor(new Commands());
        this.getCommand("stopbehind").setExecutor(new Commands());
        this.getCommand("strikelightning").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Bukkit.getPluginManager().registerEvents(new Items(), this);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        team1 = board.registerNewTeam("Team1");
        team2 = board.registerNewTeam("Team2");
        team1.setColor(ChatColor.RED);
        team2.setColor(ChatColor.LIGHT_PURPLE);
    }

    public static Woah getInstance() {
        return plugin;
    }

}

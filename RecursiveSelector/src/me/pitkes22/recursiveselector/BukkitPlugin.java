package me.pitkes22.recursiveselector;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;



public class BukkitPlugin extends JavaPlugin {
	
	public static WorldEditPlugin worldEdit;
	public static Plugin plugin;

	@Override
    public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Recursive selector");
	
		getCommand("recursiveselector").setExecutor((CommandExecutor) new GiveItem(this));
		getServer().getPluginManager().registerEvents(new ClickListener(this), this);
		worldEdit = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
		plugin = this;
		
		

   }



}


package me.pitkes22.recursiveselector;

import java.util.Arrays;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;


public class GiveItem implements CommandExecutor{
	private Plugin plugin;
	
	public GiveItem(Plugin plugin){
		this.plugin = plugin;
	}
	
public static ItemStack createItem(ItemStack item, String name, String[] lore) {	    
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(name);
		    	if(lore.length != 0) {	    	
		    		itemMeta.setLore(Arrays.asList(lore));
	    		}
	    	item.setItemMeta(itemMeta);
	    return item;
	}	

public static boolean inputValidator(String[] str) {	
	for (int i = 0;i < str.length;i++) {
		if ( (NumberUtils.isNumber(str[i]) || str[i].equalsIgnoreCase("~")) || i == 8) {
			return true;
		}else {
			return false;
		}
	}
	return false;
}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if (args.length == 0 || args.length == 1 || args.length == 2 || args.length == 8 || args.length == 9) {	
			if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(ChatColor.RED+"Usage: /rs <OffSet> <MaxRep> [minX] [minY] [minZ] [maxX] [maxY] [maxZ]");
			}else {
				if (args.length == 0 || inputValidator(args) == true) {
					String offSet = "1";
					String maxRep = "5000";
					String minX = "~";
					String minY = "~";
					String minZ = "~";
					String maxX = "~";
					String maxY = "~";
					String maxZ = "~";
					String saveName = "~";
					int ll = 2;
					
					if (args.length > 2) {
					ll = args.length;
					}
					String[] lore = new String[ll];
									  
						if (args.length >= 1) {
							offSet = args[0];	
						}
						if (args.length > 1) {
							maxRep = args[1];	
						}
						if (args.length > 2) {
							minX = args[2];	
							minY = args[3];
							minZ = args[4];
							maxX = args[5];
							maxY = args[6];
							maxZ = args[7];		
						}
						if (args.length > 8) {
							saveName = args[8];
						}
						
					 
					lore[0] = "OffSet: "+offSet;
					lore[1] = "MaxRep: "+maxRep;
					if (args.length > 2) {
						lore[2] = "MinX: "+minX;
						lore[3] = "MinY: "+minY;
						lore[4] = "MinZ: "+minZ;
						lore[5] = "MaxX: "+maxX;
						lore[6] = "MaxY: "+maxY;
						lore[7] = "MaxZ: "+maxZ;	
						lore[8] = "SaveName: "+saveName;
					}	
					
					if(sender instanceof Player){
						((Player) sender).getInventory().addItem(createItem(new ItemStack(Material.STICK,1),"§6§lRecursive selector", lore));
						sender.sendMessage(ChatColor.LIGHT_PURPLE+"Click to create selection");
					}					
				}else {
					sender.sendMessage("Invalid number");
				}
			}
		}else {			
			sender.sendMessage("Incorrect number of arguments!");
			sender.sendMessage(ChatColor.RED+"Usage: /rs <OffSet> <MaxRep> [minX] [minY] [minZ] [maxX] [maxY] [maxZ] [saveName]");
		}
		return true;
		
	}
	
	

}

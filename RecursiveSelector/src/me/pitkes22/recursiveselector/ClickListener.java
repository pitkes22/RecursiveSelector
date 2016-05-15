package me.pitkes22.recursiveselector;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;


public class ClickListener implements Listener {
	private Plugin plugin;

	public ClickListener(Plugin plugin) {
		this.plugin = plugin;
	}
	public static Location lastClickedLocation;
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getClickedBlock() != null) {
			if (!event.getClickedBlock().getLocation().equals(lastClickedLocation)) {			
				lastClickedLocation = event.getClickedBlock().getLocation().clone();
				if (event.getClickedBlock().getType() != Material.AIR) {
					if (event.getPlayer().getItemInHand().getType() == Material.STICK) {
						if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lRecursive selector")) {
							List<String> lore = event.getPlayer().getItemInHand().getItemMeta().getLore();
						
							int offSet = 1;
							int maxRep = 5000;
							double minX = 0;
							double minY = 0;
							double minZ = 0;
							double maxX = 0;
							double maxY = 0;
							double maxZ = 0;
							boolean[] checkMinMaxXYZ = new boolean[6];
							Player player = event.getPlayer();
							
							if (lore.size() >= 1 && !lore.get(0).substring(lore.get(0).lastIndexOf(' ')+1).equals("~" )) {
								offSet = Integer.parseInt(lore.get(0).substring(lore.get(0).lastIndexOf(' ')+1)); 
							}
							if (lore.size() > 1 && !lore.get(1).substring(lore.get(1).lastIndexOf(' ')+1).equals("~")) {
								maxRep = Integer.parseInt(lore.get(1).substring(lore.get(1).lastIndexOf(' ')+1)); 	
							}
							if (lore.size() > 2) {	
									try {minX = Double.parseDouble(lore.get(2).substring(lore.get(2).lastIndexOf(' ')+1)); } catch (NumberFormatException e) {}
									try {minY = Double.parseDouble(lore.get(3).substring(lore.get(3).lastIndexOf(' ')+1)); } catch (NumberFormatException e) {}
									try {minZ = Double.parseDouble(lore.get(4).substring(lore.get(4).lastIndexOf(' ')+1)); } catch (NumberFormatException e) {}
									try {maxX = Double.parseDouble(lore.get(5).substring(lore.get(5).lastIndexOf(' ')+1)); } catch (NumberFormatException e) {}
									try {maxY = Double.parseDouble(lore.get(6).substring(lore.get(6).lastIndexOf(' ')+1)); } catch (NumberFormatException e) {}
									try {maxZ = Double.parseDouble(lore.get(7).substring(lore.get(7).lastIndexOf(' ')+1)); } catch (NumberFormatException e) {}
								
										for (int i = 2;i<=7;i++) {							
											if (lore.get(i).substring(lore.get(i).lastIndexOf(' ')+1).equals("~")) {										
												checkMinMaxXYZ[(i-2)] = true ;										
											}										
										}
							}else {
								for (int i = 0;i<=5;i++) {
										checkMinMaxXYZ[i] = true ;
								}
							}
				
							new Recursion(event.getClickedBlock().getLocation(), offSet, maxRep, minX, minY, minZ, maxX, maxY, maxZ, player, checkMinMaxXYZ);
							event.setCancelled(true);
						}
					}
				}
				
			}
		}
		
	}
}

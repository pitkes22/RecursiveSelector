package me.pitkes22.recursiveselector;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang.time.StopWatch;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.command.SchematicCommands;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.SchematicWriter;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.bukkit.selections.*;

@SuppressWarnings("deprecation")
public class Recursion {
	
	private Location loc;
	private double offSet;
	private double maxRep;
	private double minX;
	private double minY;
	private double minZ;
	private double maxX;
	private double maxY;
	private double maxZ;
	private ArrayList<Block> CheckedBlocks = new ArrayList<Block>();
	private int rep;
	private Location RegMin;
	private Location RegMax;
	private Player player;
	private WorldEditPlugin worldEdit = BukkitPlugin.worldEdit;
	private boolean[] CheckMinMaxXYZ;
	private String saveName;
	
	public  void Recursion(Location CurLoc){
		if(CurLoc.getBlockX() < this.RegMin.getBlockX()){
			this.RegMin.setX(CurLoc.getBlockX());
		}
		if(CurLoc.getBlockY() < this.RegMin.getBlockY()){
			this.RegMin.setY(CurLoc.getBlockY());
		}
		if(CurLoc.getBlockZ() < this.RegMin.getBlockZ()){
			this.RegMin.setZ(CurLoc.getBlockZ());
		}
		
		if(CurLoc.getBlockX() > this.RegMax.getBlockX()){
			this.RegMax.setX(CurLoc.getBlockX());
		}
		if(CurLoc.getBlockY() > this.RegMax.getBlockY()){
			this.RegMax.setY(CurLoc.getBlockY());
		}
		if(CurLoc.getBlockZ() > this.RegMax.getBlockZ()){
			this.RegMax.setZ(CurLoc.getBlockZ());
		}
		
		if(this.rep <= this.maxRep){	
			for(int x = 0; x < (this.offSet*2)+1 ; x++) {
			    for(int y = 0; y < (this.offSet*2)+1; y++) {	
			        for(int z = 0; z < (this.offSet*2)+1; z++) {
		        	Location loc = new Location(this.loc.getWorld(), (CurLoc.getX()-this.offSet)+x, (CurLoc.getY()-this.offSet)+y, (CurLoc.getZ()-this.offSet)+z);
		        	if (!this.CheckedBlocks.contains(loc.getBlock())) {
        				if(loc.getBlock().getType() != Material.AIR) {	    			
				        		if ((loc.getX() >= this.minX || this.CheckMinMaxXYZ[0] == true)&&
				        			(loc.getY() >= this.minY || this.CheckMinMaxXYZ[1] == true)&& 
				        			(loc.getZ() >= this.minZ || this.CheckMinMaxXYZ[2] == true)) {
				        		if ((loc.getX() <= this.maxX || this.CheckMinMaxXYZ[3] == true)&& 
				        			(loc.getY() <= this.maxY || this.CheckMinMaxXYZ[4] == true)&& 
				        			(loc.getZ() <= this.maxZ || this.CheckMinMaxXYZ[5] == true)) {									
				        			this.rep = this.rep+1;
									this.CheckedBlocks.add(loc.getBlock());
	        						Recursion(loc);					        					
					        				}						        			
						        		}		        				
		        			}		        			
		        		}	        				        	
			        }
			    }
			}	
		}
	}
	
    public Recursion(Location loc,double offSet,double maxRep,double minX,double minY,double minZ,double maxX,double maxY,double maxZ,Player player,boolean[] checkMinMaxXYZ2, String saveName) {
    	
    	this.player = player;
		this.loc = loc;
		this.offSet = offSet;
		this.maxRep = maxRep;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		this.rep = 0;
		this.RegMin = this.loc.clone();
		this.RegMax = this.loc.clone();
		this.CheckMinMaxXYZ = checkMinMaxXYZ2;
		this.saveName = saveName;
		
		Bukkit.getScheduler().runTask(BukkitPlugin.plugin, () -> {
				player.sendMessage(ChatColor.DARK_PURPLE+"Recursion started...");				
				try {
					StopWatch stopWatch = new StopWatch();
					stopWatch.start();
					Recursion(this.loc);						
					stopWatch.stop();				
					CuboidSelection selection = new CuboidSelection(this.loc.getWorld(), this.RegMin, this.RegMax);
					player.sendMessage(ChatColor.DARK_PURPLE+"Recursion ended in "+(stopWatch.getTime())/1000+" sec.");
					
					
					if (saveName != null) {
						
						String FinalSaveName = saveName+"0";
						File dir = new File(BukkitPlugin.worldEdit.getDataFolder(), "/schematics/");
			            if (!dir.exists()) 
			            		dir.mkdirs();			            				                			            												
							for (int i = 0; new File(BukkitPlugin.worldEdit.getDataFolder(), "/schematics/" + saveName +""+i+".schematic").exists(); i++) {
								FinalSaveName = saveName+""+(i+1);
			                }						
						File schematic = new File(BukkitPlugin.worldEdit.getDataFolder(), "/schematics/" + FinalSaveName + ".schematic");
			            			           		                 			           				
			            Vector min = selection.getNativeMinimumPoint();
			            Vector max = selection.getNativeMaximumPoint();	
			            Vector click = new Vector(this.loc.getX(), this.loc.getY(), this.loc.getZ());
			            CuboidClipboard clipboard = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min,min.subtract(click));
			            
						WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
			            WorldEdit we = wep.getWorldEdit();			 
			            LocalPlayer localPlayer = wep.wrapPlayer(player);
			            LocalSession localSession = we.getSession(localPlayer);			            
			            EditSession editSession = localSession.createEditSession(localPlayer);
						
						clipboard.copy(editSession);;
						
						try {							
							SchematicFormat.MCEDIT.save(clipboard, schematic);
							player.sendMessage(ChatColor.LIGHT_PURPLE +""+ FinalSaveName +" saved.");
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					} else {
						this.worldEdit.setSelection(this.player,selection);
						player.sendMessage(ChatColor.LIGHT_PURPLE + "First position set to ("+RegMin.getX()+", "+RegMin.getY()+", "+RegMin.getZ()+") ("+selection.getArea()+")");
						player.sendMessage(ChatColor.LIGHT_PURPLE + "Second position set to ("+RegMax.getX()+", "+RegMax.getY()+", "+RegMax.getZ()+") ("+selection.getArea()+")");
					}
									
					
					
				}catch (StackOverflowError e){
					player.sendMessage(ChatColor.RED+" StackOverflow error! Run server with "+ChatColor.ITALIC+" -Xss <size> [g|G|m|M|k|K] "+ChatColor.RESET+ChatColor.RED+" parameter! (Recommended size is 5m)");
				}
			});			
}
	
	public ArrayList<Block> getCheckedBlocks() {
		return CheckedBlocks;
	}

	public void setCheckedBlocks(ArrayList<Block> checkedBlocks) {
		CheckedBlocks = checkedBlocks;
	}

	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public double getOffSet() {
		return offSet;
	}
	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}
	public double getMaxRep() {
		return maxRep;
	}
	public void setMaxRep(int maxRep) {
		this.maxRep = maxRep;
	}
	public double getMinX() {
		return minX;
	}
	public void setMinX(double minX) {
		this.minX = minX;
	}
	public double getMinY() {
		return minY;
	}
	public void setMinY(double minY) {
		this.minY = minY;
	}
	public double getMinZ() {
		return minZ;
	}
	public void setMinZ(double minZ) {
		this.minZ = minZ;
	}
	public double getMaxX() {
		return maxX;
	}
	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}
	public double getMaxY() {
		return maxY;
	}
	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
	public double getMaxZ() {
		return maxZ;
	}
	public void setMaxZ(double maxZ) {
		this.maxZ = maxZ;
	}
}

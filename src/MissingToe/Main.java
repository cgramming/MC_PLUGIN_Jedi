package MissingToe;


import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Red;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Color;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Raid;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.StructureType;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldType;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World.Environment;
import org.bukkit.World.Spigot;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.mojang.brigadier.Message;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R2.AdvancementProgress.a;
import net.minecraft.server.v1_16_R2.BlockAir;
import net.minecraft.server.v1_16_R2.EntityFox.i;

public class Main extends JavaPlugin implements Listener{
	public static boolean isspeed = false;
	public static boolean isfloat = false;
	Team jediTeam = new Team("Red");
	ArrayList<Player> jediteam = new ArrayList<>();

	public Player player1;
	public Player player2;
	//public static Player player;
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);

		
	}
	public void beam(Player player) {


		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		    public void run() {
		    	//ArrayList<Player> players = new ArrayList<>();

		    	if(isfloat == true) {
//		            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
//		                players.add(player);
//
//		             
//		            }
		    		
		    		getNearestEntityInSight(player, 50);
		    		

		        // player.sendMessage(player.getLocation().getDirection().toString());
		    	}
		    }
		}, 0, 0);
		
	}
	public void getjedocation(Player player, Player tracked) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		    public void run() {
		    	player.setCompassTarget(player1.getLocation());
		    }
		}, 0, 0);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
		if(command.getName().equalsIgnoreCase("jed") && sender.isOp() == true) {
			if(sender instanceof Player) {
				if(sender.isOp() == true) {
						isfloat = true;
						sender.sendMessage(ChatColor.GREEN + "you are now a jedi");
						beam(((Player) sender).getPlayer());
						jediteam.add((Player) sender);

				}

				
			}else {
				System.out.println("You need to be a player to do this command");
			}
		}
		if(command.getName().equalsIgnoreCase("jedoff")) {
			if(sender instanceof Player) {
				if(sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "you are nolonger a jedi");
					jediteam.remove((Player) sender);
					isfloat = false;
				}
			}
		}
		if(command.getName().equalsIgnoreCase("hunter")) {
			if(sender instanceof Player) {
				ItemStack stack = new ItemStack(Material.COMPASS, 1);
				ItemMeta stackMeta = stack.getItemMeta();
				stackMeta.setDisplayName(ChatColor.YELLOW + "Tracker"); // Insert name here!
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("this helps you track the jedi"); // Insert whatever lore here!
				stackMeta.setLore(lore);
				stack.setItemMeta(stackMeta);
				sender.sendMessage("you are now a hunter");
					((Player) sender).getInventory().addItem(stack);

					//getjedocation((Player) sender, player1);
			}
		}
		if(command.getName().equalsIgnoreCase("runner")) {
			if(sender instanceof Player) {
				player1 = (Player) sender;
				player1.sendMessage("you are now a runner");
			}
		}
		
		return false;
	}
    public static void getNearestEntityInSight(Player player, int range) {
    if(isfloat == true) {

    ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
    ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
	ArrayList<Location> sight = new ArrayList<Location>();
	for (int i = 0;i<sightBlock.size();i++)
    	sight.add(sightBlock.get(i).getLocation());
		for (int i = 0;i<sight.size();i++) {
    		for (int k = 0;k<entities.size();k++) {
        		if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX() -.5 )<.5) {
           			if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
               			if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ() -.5 )<.5) {
               				if(entities.get(k).getType() == EntityType.PLAYER) {
                   				//obove is when it detects a you are looking at a player
                   				//below is summoning the particle
                   				Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(3));
                   				double space = player.getLocation().distance(entities.get(k).getLocation()) +0.1;
                   				Location tploc = player.getLocation().add(player.getLocation().getDirection().multiply(space));
                   				//Bukkit.getWorld("World").spawnParticle(Particle.REDSTONE, entities.get(k).getLocation(), 1, new Particle.DustOptions(Color.RED, 2));
                   				loc.setY(loc.getY() + player.getEyeHeight());
                   				entities.get(k).setVelocity(player.getLocation().getDirection().multiply(0.2));
                   				Bukkit.getWorld("World").spawnParticle(Particle.REDSTONE, loc, 1,  new Particle.DustOptions(Color.RED, 1));
                   				//Location loc = player.getEyeLocation();	
                   				//Vector sideWayOffset = player.getLocation().getDirection().crossProduct(new Vector(0,1,0)).normalize().multiply(0.4);
                   				//Vector downOffset = player.getLocation().getDirection().crossProduct(sideWayOffset).normalize().multiply(0.2);
                   				//loc.add(sideWayOffset);
                   				//loc.add(downOffset);
                   				//player.spawnParticle(Particle.VILLAGER_HAPPY, loc, 3);
               				}

                			}else {
                				
                			}
            			}
        			}
    			}
			}
    	}

}
	@EventHandler
	public void onPlayerClicks(PlayerInteractEvent event) {
	    Player player = event.getPlayer();
	    String string = "";
	    Action action = event.getAction();
	    double a = 1000000000000000000000000000000f;
	    ArrayList<Player> pList = new ArrayList<>();

	     if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
	    	 if(player.getItemInHand().getType() == Material.COMPASS) {
	    		 
	    		 for(Player p : jediteam) {
	    					if(player.getLocation().distance(p.getLocation())<a) {
	    						//player.sendMessage("sup");
	    						a = player.getLocation().distance(p.getLocation());
	    						player.setCompassTarget(p.getLocation());
	    						string = p.getName();

	    					}
	    				 }
	    		 a = 10000000000000000000000000000f;

	    		 }
 				player.sendMessage(org.bukkit.ChatColor.GREEN + "now pointion to " + string);

	    	 }

	    	 }
	    	 
 }

	


		





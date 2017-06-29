package cf.cc7w.damageparticle;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageParticle extends JavaPlugin implements Listener{

	public void onEnable(){
		getLogger().info("Damage Particle 插件已启用！");	
		getServer().getPluginManager().registerEvents(this, this);
		if(!getDataFolder().exists()) {
			  getDataFolder().mkdir();
			}
		File file=new File(getDataFolder(),"config.yml");
		if (!(file.exists())) {
			saveDefaultConfig();
			}
		reloadConfig();

	}
	
	public void onDisable(){
		getLogger().info("Damage Particle 插件已关闭！");
		HandlerList.unregisterAll();
	}
	
	public boolean onCommand
	(CommandSender sender, Command cmd, String label,String[] args){
		if (sender.hasPermission("dpart.admin") && label.equalsIgnoreCase("dpart")){
		if(args.length !=1){
			sender.sendMessage("§c========== §e§lDamageParticle §c==========");
			sender.sendMessage("§2· §e/dpart info §2 ———— §a查看插件简介");
			sender.sendMessage("§2· §e/dpart reload §2 ———— §a重载插件配置");
			sender.sendMessage("§c========== §e§lDamageParticle §c==========");
			return true;
		}else{
			if (args[0].equalsIgnoreCase("reload")){
				reloadConfig();
				sender.sendMessage("§c========== §e§lDamageParticle §c==========");
				sender.sendMessage("§2· §e插件配置： §a配置已重载");
				sender.sendMessage("§c========== §e§lDamageParticle §c==========");
				return true;
			}else if (args[0].equalsIgnoreCase("info")){
				sender.sendMessage("§c========== §e§lDamageParticle §c==========");
				sender.sendMessage("§2· §e插件版本： §a1.0（2017.06.26）");
				sender.sendMessage("§2· §e插件作者： §ac7w");
				sender.sendMessage("§c========== §e§lDamageParticle §c==========");
				return true;
			}else{
				sender.sendMessage("§c========== §e§lDamageParticle §c==========");
				sender.sendMessage("§2· §e/dpart info §2 ———— §a查看插件简介");
				sender.sendMessage("§2· §e/dpart reload §2 ———— §a重载插件配置");
				sender.sendMessage("§c========== §e§lDamageParticle §c==========");
				return true;
			}
		}
	}
	    return true;	
	}
	
	@EventHandler
	public void effect (EntityDamageEvent event){
		
		int count = getConfig().getInt("setting.count");
		EntityType en = event.getEntityType();
		String get = "particles."+en;
		String part = getConfig().getString(get) ;
		
		
		if (part == null){
			
			String def = getConfig().getString("setting.default") ;
			
			getLogger().info("实体类型" + en +"未定义！");
			getLogger().info("已使用默认配置：" + def );

			getConfig().set(get, def);
			saveConfig();
		}
		
		else if(part.equals("no")){
			
		}else{
			
		
		
		Particle par = Particle.valueOf(part);
		
		Location loc = event.getEntity().getLocation();
		
		if(getConfig().getBoolean("setting.debug") == true){
			getLogger().info("************************************");
			getLogger().info("您已进入debug模式，下面的文字请粘贴给作者");
			getLogger().info("[Debug]"+String.valueOf(en));
			getLogger().info("[Debug]"+String.valueOf(get));
			getLogger().info("[Debug]"+String.valueOf(loc));
			getLogger().info("[Debug]"+String.valueOf(par));
			getLogger().info("[Debug]"+part);
			getLogger().info("您已进入debug模式，上面的文字请粘贴给作者");
			getLogger().info("************************************");
		}
		
		

		event.getEntity().getLocation().getWorld().spawnParticle(par, loc, count);
		
	    }
	}
}

package net.insxnity.nofalldamage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

import net.insxnity.api.data.storage.config.YamlConfigFile;

public class Main extends JavaPlugin implements Listener {
	
	private YamlConfigFile config = null;
	
	private Boolean enable = null;
	private String permission = null;
	
	@Override
	public void onEnable() {
		this.config = new YamlConfigFile(this, "config.yml");
		
		this.enable = config.get("enable").toBoolean();
		this.permission = config.get("permission").toString();
		
		
		if (enable) getServer().getPluginManager().registerEvents(this, this);
		
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (event.getCause() == DamageCause.FALL && player.hasPermission(permission)) {
        	event.setCancelled(true);
        }
    }
	
	@Override
	public void onDisable() {
		this.config = null;
		
		this.enable = null;
		this.permission = null;
	}

}

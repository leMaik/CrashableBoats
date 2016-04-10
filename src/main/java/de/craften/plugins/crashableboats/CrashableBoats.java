package de.craften.plugins.crashableboats;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A plugin that shows news ingame.
 */
public class CrashableBoats extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {
        Entity vehicle = event.getVehicle();
        if (vehicle instanceof Boat && !event.getEntity().equals(vehicle.getPassenger())
                && vehicle.getPassenger() instanceof Player
                && vehicle.getPassenger().getVelocity().lengthSquared() >= 0.0062) {
            destroyBoat((Boat) vehicle);
        }
    }

    private void destroyBoat(Boat boat) {
        Location location = boat.getLocation();
        location.getWorld().dropItemNaturally(location, new ItemStack(Material.STICK, 2));
        location.getWorld().dropItemNaturally(location, new ItemStack(Material.WOOD, 3, boat.getWoodType().getData()));
        boat.remove();
    }
}

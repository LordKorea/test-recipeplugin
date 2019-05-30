package test.lordkorea.recipeplugin;

import com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class RecipePluginMain extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerRecipeBookClick(final PlayerRecipeBookClickEvent event) {
        getLogger().info("Got a recipe book click by " + event.getPlayer().getName() + " for " + event.getRecipe()
                + " (make all: " + event.isMakeAll() + ")");

        final ItemStack hotbarLeft = event.getPlayer().getInventory().getItem(0);
        if (hotbarLeft == null) {
            incorrectUsage(event.getPlayer());
            event.setCancelled(true);
            return;
        }

        if (hotbarLeft.getType() == Material.DIRT) {
            event.setMakeAll(false);
            event.setRecipe(NamespacedKey.minecraft("oak_button"));
            event.getPlayer().sendMessage("Dirt mode: No make-all, only wooden buttons!");
        } else if (hotbarLeft.getType() == Material.APPLE) {
            event.setMakeAll(false);
            event.getPlayer().sendMessage("Apple mode: No make-all!");
        } else if (hotbarLeft.getType() == Material.DIAMOND) {
            event.getPlayer().sendMessage("Diamond mode: Everything goes!");
        } else {
            incorrectUsage(event.getPlayer());
            event.setCancelled(true);
        }
    }

    private void incorrectUsage(final Player target) {
        target.sendMessage("Put an item into the leftmost slot of your hotbar");
        target.sendMessage("  Diamond - No restrictions");
        target.sendMessage("  Apple - No make-all");
        target.sendMessage("  Dirt - No make-all, everything looks like a wooden button.");
    }
}

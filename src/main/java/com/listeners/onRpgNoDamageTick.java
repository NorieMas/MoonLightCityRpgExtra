package com.listeners;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.ItemExecutor;

import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class onRpgNoDamageTick implements Listener {

    ItemExecutor itemExecutor;
    public static JavaPlugin plugin;

    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent event) {

        Player player = event.getPlayer();

        if (itemExecutor == null) {
            itemExecutor = MythicBukkit.inst().getItemManager();
        }

        if (itemExecutor.hasCustomDurability(player.getInventory().getItemInMainHand()) &&
                (player.getInventory().getItemInMainHand().getType().name().endsWith("_SWORD") ||
                        player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE") ||
                        player.getInventory().getItemInMainHand().getType().name().endsWith("_PICKAXE") ||
                        player.getInventory().getItemInMainHand().getType().name().endsWith("_HOE") ||
                        player.getInventory().getItemInMainHand().getType().name().endsWith("_SHOVEL") ||
                        player.getInventory().getItemInMainHand().getType().name().endsWith("TRIDENT"))) {
            player.setFoodLevel(player.getFoodLevel() - 1);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 40, 10, false, false, true));
        }
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();

        if (itemExecutor == null) {
            itemExecutor = MythicBukkit.inst().getItemManager();
        }

        if (itemExecutor.hasCustomDurability(player.getInventory().getItemInMainHand()) && player.getFoodLevel() > 0 &&
                (player.getPotionEffect(PotionEffectType.SLOW_DIGGING) == null || player.getPotionEffect(PotionEffectType.SLOW_DIGGING).getAmplifier() != 20)) {
            if (event.getEntity() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) event.getEntity();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        target.setNoDamageTicks(15);
                    }
                }.runTaskLater(plugin, 0);
            }
        }
        if (player.getPotionEffect(PotionEffectType.SLOW_DIGGING) != null && player.getPotionEffect(PotionEffectType.SLOW_DIGGING).getAmplifier() == 20) {
            player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 1.0F, 1.2F);
            event.setCancelled(true);
        }
    }
}
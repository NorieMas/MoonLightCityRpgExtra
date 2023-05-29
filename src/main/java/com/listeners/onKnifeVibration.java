package com.listeners;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.ItemExecutor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class onKnifeVibration implements Listener {

    ItemExecutor itemExecutor;

    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent event) {

        Player player = event.getPlayer();

        if (itemExecutor == null) {
            itemExecutor = MythicBukkit.inst().getItemManager();
        }

        if (player.isOnGround() &&
                player.getPose() == Pose.SNEAKING &&
                player.getPose() != Pose.FALL_FLYING &&
                player.getAttackCooldown() == 1.0F &&
                event.getAnimationType() == PlayerAnimationType.ARM_SWING &&
                itemExecutor.hasCustomDurability(player.getInventory().getItemInMainHand())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 100, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 5, 0, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 20, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 20, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 20, false));
        }
    }
}

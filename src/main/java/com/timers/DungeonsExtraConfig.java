package com.timers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

public final class DungeonsExtraConfig {
    public static void start(JavaPlugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getServer().getWorld("world_dungeons");
                if (world == null) {
                    plugin.getLogger().warning("世界 world_dungeons 不存在，請確定世界名稱正確！");
                    return;
                }
                for (Player player : world.getPlayers()) {
                    if (player.isSprinting() && player.getFoodLevel() > 0 &&
                            (player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SURVIVAL)) {
                        player.setFoodLevel(player.getFoodLevel() - 1);
                    } else if (player.getFoodLevel() < 20 &&
                            (player.getPotionEffect(PotionEffectType.BAD_OMEN) == null ||
                                    player.getPotionEffect(PotionEffectType.BAD_OMEN).getAmplifier() != 10) &&
                            (player.getPotionEffect(PotionEffectType.HUNGER) == null ||
                                    player.getPotionEffect(PotionEffectType.HUNGER).getAmplifier() != 10)) {
                        player.setFoodLevel(player.getFoodLevel() + 1);
                    } else if (player.getSaturation() < 5 &&
                            (player.getPotionEffect(PotionEffectType.BAD_OMEN) == null ||
                                    player.getPotionEffect(PotionEffectType.BAD_OMEN).getAmplifier() != 10) &&
                            (player.getPotionEffect(PotionEffectType.HUNGER) == null ||
                                    player.getPotionEffect(PotionEffectType.HUNGER).getAmplifier() != 10)) {
                        player.setSaturation(player.getSaturation() + 1);
                    }
                    if (player.getFoodLevel() < 6) {
                        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 1.0F, 1.2F);
                        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 1.0F, 1.2F);
                        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 1.0F, 1.2F);
                        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 1.0F, 1.2F);
                        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 1.0F, 1.2F);
                    }
                    if (player.getFoodLevel() == 0) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 4, false, false, true));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 20, false, false, true));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 200, false, false, true));
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 10L);
    }
}

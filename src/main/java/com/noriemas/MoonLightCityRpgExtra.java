package com.noriemas;

import com.timers.*;
import com.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoonLightCityRpgExtra extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("MoonLightCityRpgExtra is enable. Author by NorieMas, XiaoEmAs.");

        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.reloadConfig();

        boolean DungeonsExtraRecoveryTimerBoolean = this.getConfig().getBoolean("DungeonsExtraRecoveryTimer");
        boolean RpgNoDamageTickListenerBoolean = this.getConfig().getBoolean("RpgNoDamageTickListener");
        boolean AntiNormalFKeyListenerBoolean = this.getConfig().getBoolean("AntiNormalFKeyListener");
        boolean MythicMobsDeathListenerBoolean = this.getConfig().getBoolean("MythicMobsDeathListener");
        boolean KnifeVibrationListenerBoolean = this.getConfig().getBoolean("KnifeVibrationListener");

        if (DungeonsExtraRecoveryTimerBoolean) {
            DungeonsExtraConfig.start(this);
        }
        if (RpgNoDamageTickListenerBoolean) {
            onRpgNoDamageTick.plugin = this;
            Bukkit.getServer().getPluginManager().registerEvents(new onRpgNoDamageTick(), this);
        }
        if (AntiNormalFKeyListenerBoolean) {
            Bukkit.getServer().getPluginManager().registerEvents(new onAntiNormalFKey(), this);
        }
        if (MythicMobsDeathListenerBoolean) {
            Bukkit.getServer().getPluginManager().registerEvents(new onMythicMobsDeath(), this);
        }
        if (KnifeVibrationListenerBoolean) {
            Bukkit.getServer().getPluginManager().registerEvents(new onKnifeVibration(), this);
        }
    }
}
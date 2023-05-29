package com.listeners;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.items.ItemExecutor;
import io.lumine.mythic.core.utils.jnbt.CompoundTagBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class onMythicMobsDeath implements Listener {

    @EventHandler
    public void mythicMobsItemDrop(MythicMobDeathEvent event) {
        if (itemExecutor == null) {
            itemExecutor = MythicBukkit.inst().getItemManager();
        }
        for (ItemStack drop : event.getDrops()) {
            if (itemExecutor.hasCustomDurability(drop)) {
                damageCustomItem(drop, (int) (itemExecutor.getMaxCustomDurability(drop) * (Math.random() * 0.2 + 0.8)));
            }
        }
    }

    ItemExecutor itemExecutor;

    public void damageCustomItem(ItemStack itemStack, int amount) {
        if (itemExecutor == null) {
            itemExecutor = MythicBukkit.inst().getItemManager();
        }

        Damageable damageable = (Damageable) itemStack.getItemMeta();
        if (damageable == null)
            return;

        int currentCustomDura = itemExecutor.getCurrentCustomDurability(itemStack) + amount;
        int maxCustomDura = itemExecutor.getMaxCustomDurability(itemStack);
        int materialDura = itemStack.getType().getMaxDurability();
        double ratio = ((double) currentCustomDura) / maxCustomDura;
        int heal = (int) (materialDura * ratio);
        if (heal < 0)
            return;
        damageable.setDamage(heal);
        itemStack.setItemMeta(damageable);
        if (damageable.getDamage() >= materialDura) {
            itemStack.setAmount(0);
            return;
        }
        CompoundTagBuilder tag = MythicBukkit.inst().getVolatileCodeHandler().getItemHandler().getNBTData(itemStack).createBuilder();
        tag.putInt("MYTHIC_CURRENT_CUSTOM_DURABILITY", currentCustomDura);
        ItemMeta meta = MythicBukkit.inst().getVolatileCodeHandler().getItemHandler().setNBTData(itemStack, tag.build()).getItemMeta();
        itemStack.setItemMeta(meta);
    }
}
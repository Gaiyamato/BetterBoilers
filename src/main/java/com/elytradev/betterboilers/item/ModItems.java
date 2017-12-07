package com.elytradev.betterboilers.item;

import com.elytradev.betterboilers.BetterBoilers;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static ItemBase logoFake = new ItemBase("logo_fake").setCreativeTab(BetterBoilers.creativeTab);

    public static ItemBase[] allItems = {
            //exhaustedLapis
    };

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(allItems);
    }

    public static void registerModels() {
        for (int i = 0; i < allItems.length ; i++) {
            allItems[i].registerItemModel();
        }
    }

    public static void registerOreDict() {
        for (int i = 0; i < allItems.length ; i++) {
            allItems[i].initOreDict();
        }
    }
}

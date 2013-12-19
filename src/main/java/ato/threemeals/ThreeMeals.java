package ato.threemeals;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "ato.threemeals")
public class ThreeMeals {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        loadConfig(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    }

    /**
     * コンフィグファイルを読み込む
     */
    private void loadConfig(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        HardcoreFoodStats.FOODEXHAUSTION_SPEED = config.get(config.CATEGORY_GENERAL, "FoodExhaustionSpeed", 3).getInt(3);
        HardcoreFoodStats.HARAHERING_SPEED = config.get(config.CATEGORY_GENERAL, "HaraheringSpeed", 1).getInt(1);
        HardcoreFoodStats.ALWAYS_HARDMODE = config.get(config.CATEGORY_GENERAL, "AlwaysHardmode", true).getBoolean(true);

        config.save();
    }
}

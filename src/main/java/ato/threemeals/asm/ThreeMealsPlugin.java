package ato.threemeals.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.6.4")
@IFMLLoadingPlugin.TransformerExclusions({"ato.threemeals.asm"})
public class ThreeMealsPlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{FoodStatsTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return ModContainer.class.getName();
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }
}

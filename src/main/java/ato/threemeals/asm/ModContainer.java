package ato.threemeals.asm;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class ModContainer extends DummyModContainer {
    public ModContainer() {
        super(new ModMetadata());

        ModMetadata meta = getMetadata();
        meta.modId = "ato.threemeals.core";
        meta.name = "一日三食！ 腹減り MOD Core";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}

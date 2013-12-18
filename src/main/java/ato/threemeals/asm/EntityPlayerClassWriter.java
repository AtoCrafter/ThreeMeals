package ato.threemeals.asm;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassWriter;

/**
 * EntityPlayer の書き込みに失敗するので対処療法
 */
public class EntityPlayerClassWriter extends ClassWriter {
    public EntityPlayerClassWriter(int flags) {
        super(flags);
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        return FMLDeobfuscatingRemapper.INSTANCE.map(type1);
    }
}

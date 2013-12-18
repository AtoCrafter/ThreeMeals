package ato.threemeals.asm;

import ato.threemeals.HardcoreFoodStats;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class FoodStatsTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("net.minecraft.entity.player.EntityPlayer".equals(transformedName)) {
            return patchFoodStats(basicClass);
        } else {
            return basicClass;
        }
    }

    private byte[] patchFoodStats(byte[] bytes) {
        ClassNode cnode = new ClassNode();
        ClassReader creader = new ClassReader(bytes);
        creader.accept(cnode, 0);

        for (MethodNode m : cnode.methods) {
            if ("<init>".equals(m.name)) {
                ListIterator<AbstractInsnNode> ite = m.instructions.iterator();
                while (ite.hasNext()) {
                    AbstractInsnNode insn = ite.next();
                    if (insn.getOpcode() == Opcodes.NEW && insn instanceof TypeInsnNode) {
                        TypeInsnNode newInsn = (TypeInsnNode) insn;
                        if ("net/minecraft/util/FoodStats".equals(newInsn.desc) ||
                                "ux".equals(newInsn.desc)) {
                            newInsn.desc = Type.getInternalName(HardcoreFoodStats.class);
                            ((MethodInsnNode) insn.getNext().getNext()).owner = Type.getInternalName(HardcoreFoodStats.class);
                        }
                    }
                }
            }
        }

        ClassWriter cwriter = new EntityPlayerClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cnode.accept(cwriter);
        return cwriter.toByteArray();
    }
}

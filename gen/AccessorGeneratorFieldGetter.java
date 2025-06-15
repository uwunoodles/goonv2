package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class AccessorGeneratorFieldGetter extends AccessorGeneratorField {
   public AccessorGeneratorFieldGetter(AccessorInfo a) {
      super(a);
   }

   public MethodNode generate() {
      MethodNode a = a.createMethod(a.targetType.getSize(), a.targetType.getSize());
      if (a.isInstanceField) {
         a.instructions.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      int a = a.isInstanceField ? 180 : 178;
      a.instructions.add((AbstractInsnNode)(new FieldInsnNode(a, a.info.getClassNode().name, a.targetField.name, a.targetField.desc)));
      a.instructions.add((AbstractInsnNode)(new InsnNode(a.targetType.getOpcode(172))));
      return a;
   }
}

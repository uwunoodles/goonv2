package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class AccessorGeneratorFieldSetter extends AccessorGeneratorField {
   public AccessorGeneratorFieldSetter(AccessorInfo a) {
      super(a);
   }

   public MethodNode generate() {
      int a = a.isInstanceField ? 1 : 0;
      int a = a + a.targetType.getSize();
      int a = a + a.targetType.getSize();
      MethodNode a = a.createMethod(a, a);
      if (a.isInstanceField) {
         a.instructions.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      a.instructions.add((AbstractInsnNode)(new VarInsnNode(a.targetType.getOpcode(21), a)));
      int a = a.isInstanceField ? 181 : 179;
      a.instructions.add((AbstractInsnNode)(new FieldInsnNode(a, a.info.getClassNode().name, a.targetField.name, a.targetField.desc)));
      a.instructions.add((AbstractInsnNode)(new InsnNode(177)));
      return a;
   }
}

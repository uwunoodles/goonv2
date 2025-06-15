package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.util.Bytecode;

public class AccessorGeneratorMethodProxy extends AccessorGenerator {
   private final MethodNode targetMethod;
   private final Type[] argTypes;
   private final Type returnType;
   private final boolean isInstanceMethod;

   public AccessorGeneratorMethodProxy(AccessorInfo a) {
      super(a);
      a.targetMethod = a.getTargetMethod();
      a.argTypes = a.getArgTypes();
      a.returnType = a.getReturnType();
      a.isInstanceMethod = !Bytecode.hasFlag((MethodNode)a.targetMethod, 8);
   }

   public MethodNode generate() {
      int a = Bytecode.getArgsSize(a.argTypes) + a.returnType.getSize() + (a.isInstanceMethod ? 1 : 0);
      MethodNode a = a.createMethod(a, a);
      if (a.isInstanceMethod) {
         a.instructions.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      Bytecode.loadArgs(a.argTypes, a.instructions, a.isInstanceMethod ? 1 : 0);
      boolean a = Bytecode.hasFlag((MethodNode)a.targetMethod, 2);
      int a = a.isInstanceMethod ? (a ? 183 : 182) : 184;
      a.instructions.add((AbstractInsnNode)(new MethodInsnNode(a, a.info.getClassNode().name, a.targetMethod.name, a.targetMethod.desc, false)));
      a.instructions.add((AbstractInsnNode)(new InsnNode(a.returnType.getOpcode(172))));
      return a;
   }
}

package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

public class ModifyArgsInjector extends InvokeInjector {
   private final ArgsClassGenerator argsClassGenerator;

   public ModifyArgsInjector(InjectionInfo a) {
      super(a, "@ModifyArgs");
      a.argsClassGenerator = (ArgsClassGenerator)a.getContext().getExtensions().getGenerator(ArgsClassGenerator.class);
   }

   protected void checkTarget(Target a) {
      a.checkTargetModifiers(a, false);
   }

   protected void inject(Target a, InjectionNodes.InjectionNode a) {
      a.checkTargetForNode(a, a);
      super.inject(a, a);
   }

   protected void injectAtInvoke(Target a, InjectionNodes.InjectionNode a) {
      MethodInsnNode a = (MethodInsnNode)a.getCurrentTarget();
      Type[] a = Type.getArgumentTypes(a.desc);
      if (a.length == 0) {
         throw new InvalidInjectionException(a.info, "@ModifyArgs injector " + a + " targets a method invocation " + a.name + a.desc + " with no arguments!");
      } else {
         String a = a.argsClassGenerator.getClassRef(a.desc);
         boolean a = a.verifyTarget(a);
         InsnList a = new InsnList();
         a.addToStack(1);
         a.packArgs(a, a, a);
         if (a) {
            a.addToStack(Bytecode.getArgsSize(a.arguments));
            Bytecode.loadArgs(a.arguments, a, a.isStatic ? 0 : 1);
         }

         a.invokeHandler(a);
         a.unpackArgs(a, a, a);
         a.insns.insertBefore(a, (InsnList)a);
      }
   }

   private boolean verifyTarget(Target a) {
      String a = String.format("(L%s;)V", ArgsClassGenerator.ARGS_REF);
      if (!a.methodNode.desc.equals(a)) {
         String a = Bytecode.changeDescriptorReturnType(a.method.desc, "V");
         String a = String.format("(L%s;%s", ArgsClassGenerator.ARGS_REF, a.substring(1));
         if (a.methodNode.desc.equals(a)) {
            return true;
         } else {
            throw new InvalidInjectionException(a.info, "@ModifyArgs injector " + a + " has an invalid signature " + a.methodNode.desc + ", expected " + a + " or " + a);
         }
      } else {
         return false;
      }
   }

   private void packArgs(InsnList a, String a, MethodInsnNode a) {
      String a = Bytecode.changeDescriptorReturnType(a.desc, "L" + a + ";");
      a.add((AbstractInsnNode)(new MethodInsnNode(184, a, "of", a, false)));
      a.add((AbstractInsnNode)(new InsnNode(89)));
      if (!a.isStatic) {
         a.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
         a.add((AbstractInsnNode)(new InsnNode(95)));
      }

   }

   private void unpackArgs(InsnList a, String a, Type[] a) {
      for(int a = 0; a < a.length; ++a) {
         if (a < a.length - 1) {
            a.add((AbstractInsnNode)(new InsnNode(89)));
         }

         a.add((AbstractInsnNode)(new MethodInsnNode(182, a, "$" + a, "()" + a[a].getDescriptor(), false)));
         if (a < a.length - 1) {
            if (a[a].getSize() == 1) {
               a.add((AbstractInsnNode)(new InsnNode(95)));
            } else {
               a.add((AbstractInsnNode)(new InsnNode(93)));
               a.add((AbstractInsnNode)(new InsnNode(88)));
            }
         }
      }

   }
}

package org.spongepowered.asm.mixin.injection.invoke;

import org.apache.logging.log4j.Level;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.util.InsnFinder;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.SignaturePrinter;

public class ModifyConstantInjector extends RedirectInjector {
   private static final int OPCODE_OFFSET = 6;

   public ModifyConstantInjector(InjectionInfo a) {
      super(a, "@ModifyConstant");
   }

   protected void inject(Target a, InjectionNodes.InjectionNode a) {
      if (a.preInject(a)) {
         if (a.isReplaced()) {
            throw new UnsupportedOperationException("Target failure for " + a.info);
         } else {
            AbstractInsnNode a = a.getCurrentTarget();
            if (a instanceof JumpInsnNode) {
               a.checkTargetModifiers(a, false);
               a.injectExpandedConstantModifier(a, (JumpInsnNode)a);
            } else if (Bytecode.isConstant(a)) {
               a.checkTargetModifiers(a, false);
               a.injectConstantModifier(a, a);
            } else {
               throw new InvalidInjectionException(a.info, a.annotationType + " annotation is targetting an invalid insn in " + a + " in " + a);
            }
         }
      }
   }

   private void injectExpandedConstantModifier(Target a, JumpInsnNode a) {
      int a = a.getOpcode();
      if (a >= 155 && a <= 158) {
         InsnList a = new InsnList();
         a.add((AbstractInsnNode)(new InsnNode(3)));
         AbstractInsnNode a = a.invokeConstantHandler(Type.getType("I"), a, a, a);
         a.add((AbstractInsnNode)(new JumpInsnNode(a + 6, a.label)));
         a.replaceNode(a, a, a);
         a.addToStack(1);
      } else {
         throw new InvalidInjectionException(a.info, a.annotationType + " annotation selected an invalid opcode " + Bytecode.getOpcodeName(a) + " in " + a + " in " + a);
      }
   }

   private void injectConstantModifier(Target a, AbstractInsnNode a) {
      Type a = Bytecode.getConstantType(a);
      if (a.getSort() <= 5 && a.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
         a.checkNarrowing(a, a, a);
      }

      InsnList a = new InsnList();
      InsnList a = new InsnList();
      AbstractInsnNode a = a.invokeConstantHandler(a, a, a, a);
      a.wrapNode(a, a, a, a);
   }

   private AbstractInsnNode invokeConstantHandler(Type a, Target a, InsnList a, InsnList a) {
      String a = Bytecode.generateDescriptor(a, a);
      boolean a = a.checkDescriptor(a, a, "getter");
      if (!a.isStatic) {
         a.insert((AbstractInsnNode)(new VarInsnNode(25, 0)));
         a.addToStack(1);
      }

      if (a) {
         a.pushArgs(a.arguments, a, a.getArgIndices(), 0, a.arguments.length);
         a.addToStack(Bytecode.getArgsSize(a.arguments));
      }

      return a.invokeHandler(a);
   }

   private void checkNarrowing(Target a, AbstractInsnNode a, Type a) {
      AbstractInsnNode a = (new InsnFinder()).findPopInsn(a, a);
      if (a != null) {
         if (a instanceof FieldInsnNode) {
            FieldInsnNode a = (FieldInsnNode)a;
            Type a = Type.getType(a.desc);
            a.checkNarrowing(a, a, a, a, a.indexOf(a), String.format("%s %s %s.%s", Bytecode.getOpcodeName(a), SignaturePrinter.getTypeName(a, false), a.owner.replace('/', '.'), a.name));
         } else if (a.getOpcode() == 172) {
            a.checkNarrowing(a, a, a, a.returnType, a.indexOf(a), "RETURN " + SignaturePrinter.getTypeName(a.returnType, false));
         } else if (a.getOpcode() == 54) {
            int a = ((VarInsnNode)a).var;
            LocalVariableNode a = Locals.getLocalVariableAt(a.classNode, a.method, a, a);
            if (a != null && a.desc != null) {
               String a = a.name != null ? a.name : "unnamed";
               Type a = Type.getType(a.desc);
               a.checkNarrowing(a, a, a, a, a.indexOf(a), String.format("ISTORE[var=%d] %s %s", a, SignaturePrinter.getTypeName(a, false), a));
            }
         }

      }
   }

   private void checkNarrowing(Target a, AbstractInsnNode a2, Type a, Type a, int a, String a) {
      int a = a.getSort();
      int a = a.getSort();
      if (a < a) {
         String a = SignaturePrinter.getTypeName(a, false);
         String a = SignaturePrinter.getTypeName(a, false);
         String a = a == 1 ? ". Implicit conversion to <boolean> can cause nondeterministic (JVM-specific) behaviour!" : "";
         Level a = a == 1 ? Level.ERROR : Level.WARN;
         Injector.logger.log(a, "Narrowing conversion of <{}> to <{}> in {} target {} at opcode {} ({}){}", new Object[]{a, a, a.info, a, a, a, a});
      }

   }
}

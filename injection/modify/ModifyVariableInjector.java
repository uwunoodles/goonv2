package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class ModifyVariableInjector extends Injector {
   private final LocalVariableDiscriminator discriminator;

   public ModifyVariableInjector(InjectionInfo a, LocalVariableDiscriminator a) {
      super(a);
      a.discriminator = a;
   }

   protected boolean findTargetNodes(MethodNode a, InjectionPoint a, InsnList a, Collection<AbstractInsnNode> a) {
      if (a instanceof ModifyVariableInjector.ContextualInjectionPoint) {
         Target a = a.info.getContext().getTargetMethod(a);
         return ((ModifyVariableInjector.ContextualInjectionPoint)a).find(a, a);
      } else {
         return a.find(a.desc, a, a);
      }
   }

   protected void sanityCheck(Target a, List<InjectionPoint> a) {
      super.sanityCheck(a, a);
      if (a.isStatic != a.isStatic) {
         throw new InvalidInjectionException(a.info, "'static' of variable modifier method does not match target in " + a);
      } else {
         int a = a.discriminator.getOrdinal();
         if (a < -1) {
            throw new InvalidInjectionException(a.info, "Invalid ordinal " + a + " specified in " + a);
         } else if (a.discriminator.getIndex() == 0 && !a.isStatic) {
            throw new InvalidInjectionException(a.info, "Invalid index 0 specified in non-static variable modifier " + a);
         }
      }
   }

   protected void inject(Target a, InjectionNodes.InjectionNode a) {
      if (a.isReplaced()) {
         throw new InvalidInjectionException(a.info, "Variable modifier target for " + a + " was removed by another injector");
      } else {
         ModifyVariableInjector.Context a = new ModifyVariableInjector.Context(a.returnType, a.discriminator.isArgsOnly(), a, a.getCurrentTarget());
         if (a.discriminator.printLVT()) {
            a.printLocals(a);
         }

         String a = Bytecode.getDescriptor(new Type[]{a.returnType}, a.returnType);
         if (!a.equals(a.methodNode.desc)) {
            throw new InvalidInjectionException(a.info, "Variable modifier " + a + " has an invalid signature, expected " + a + " but found " + a.methodNode.desc);
         } else {
            try {
               int a = a.discriminator.findLocal(a);
               if (a > -1) {
                  a.inject(a, a);
               }
            } catch (InvalidImplicitDiscriminatorException var6) {
               if (a.discriminator.printLVT()) {
                  a.info.addCallbackInvocation(a.methodNode);
                  return;
               }

               throw new InvalidInjectionException(a.info, "Implicit variable modifier injection failed in " + a, var6);
            }

            a.insns.insertBefore(a.node, a.insns);
            a.addToStack(a.isStatic ? 1 : 2);
         }
      }
   }

   private void printLocals(ModifyVariableInjector.Context a) {
      SignaturePrinter a = new SignaturePrinter(a.methodNode.name, a.returnType, a.methodArgs, new String[]{"var"});
      a.setModifiers(a.methodNode);
      (new PrettyPrinter()).kvWidth(20).kv("Target Class", a.classNode.name.replace('/', '.')).kv("Target Method", a.target.method.name).kv("Callback Name", a.methodNode.name).kv("Capture Type", SignaturePrinter.getTypeName(a.returnType, false)).kv("Instruction", "%s %s", a.node.getClass().getSimpleName(), Bytecode.getOpcodeName(a.node.getOpcode())).hr().kv("Match mode", a.discriminator.isImplicit(a) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)").kv("Match ordinal", a.discriminator.getOrdinal() < 0 ? "any" : a.discriminator.getOrdinal()).kv("Match index", a.discriminator.getIndex() < a.baseArgIndex ? "any" : a.discriminator.getIndex()).kv("Match name(s)", a.discriminator.hasNames() ? a.discriminator.getNames() : "any").kv("Args only", a.discriminator.isArgsOnly()).hr().add((PrettyPrinter.IPrettyPrintable)a).print(System.err);
   }

   private void inject(ModifyVariableInjector.Context a, int a) {
      if (!a.isStatic) {
         a.insns.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      a.insns.add((AbstractInsnNode)(new VarInsnNode(a.returnType.getOpcode(21), a)));
      a.invokeHandler(a.insns);
      a.insns.add((AbstractInsnNode)(new VarInsnNode(a.returnType.getOpcode(54), a)));
   }

   abstract static class ContextualInjectionPoint extends InjectionPoint {
      protected final IMixinContext context;

      ContextualInjectionPoint(IMixinContext a) {
         a.context = a;
      }

      public boolean find(String a1, InsnList a2, Collection<AbstractInsnNode> a3) {
         throw new InvalidInjectionException(a.context, a.getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
      }

      abstract boolean find(Target var1, Collection<AbstractInsnNode> var2);
   }

   static class Context extends LocalVariableDiscriminator.Context {
      final InsnList insns = new InsnList();

      public Context(Type a, boolean a, Target a, AbstractInsnNode a) {
         super(a, a, a, a);
      }
   }
}

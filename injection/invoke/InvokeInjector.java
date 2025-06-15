package org.spongepowered.asm.mixin.injection.invoke;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;

public abstract class InvokeInjector extends Injector {
   protected final String annotationType;

   public InvokeInjector(InjectionInfo a, String a) {
      super(a);
      a.annotationType = a;
   }

   protected void sanityCheck(Target a, List<InjectionPoint> a) {
      super.sanityCheck(a, a);
      a.checkTarget(a);
   }

   protected void checkTarget(Target a) {
      a.checkTargetModifiers(a, true);
   }

   protected final void checkTargetModifiers(Target a, boolean a) {
      if (a && a.isStatic != a.isStatic) {
         throw new InvalidInjectionException(a.info, "'static' modifier of handler method does not match target in " + a);
      } else if (!a && !a.isStatic && a.isStatic) {
         throw new InvalidInjectionException(a.info, "non-static callback method " + a + " targets a static method which is not supported");
      }
   }

   protected void checkTargetForNode(Target a, InjectionNodes.InjectionNode a) {
      if (a.isCtor) {
         MethodInsnNode a = a.findSuperInitNode();
         int a = a.indexOf((AbstractInsnNode)a);
         int a = a.indexOf(a.getCurrentTarget());
         if (a <= a) {
            if (!a.isStatic) {
               throw new InvalidInjectionException(a.info, "Pre-super " + a.annotationType + " invocation must be static in " + a);
            }

            return;
         }
      }

      a.checkTargetModifiers(a, true);
   }

   protected void inject(Target a, InjectionNodes.InjectionNode a) {
      if (!(a.getCurrentTarget() instanceof MethodInsnNode)) {
         throw new InvalidInjectionException(a.info, a.annotationType + " annotation on is targetting a non-method insn in " + a + " in " + a);
      } else {
         a.injectAtInvoke(a, a);
      }
   }

   protected abstract void injectAtInvoke(Target var1, InjectionNodes.InjectionNode var2);

   protected AbstractInsnNode invokeHandlerWithArgs(Type[] a, InsnList a, int[] a) {
      return a.invokeHandlerWithArgs(a, a, a, 0, a.length);
   }

   protected AbstractInsnNode invokeHandlerWithArgs(Type[] a, InsnList a, int[] a, int a, int a) {
      if (!a.isStatic) {
         a.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      a.pushArgs(a, a, a, a, a);
      return a.invokeHandler(a);
   }

   protected int[] storeArgs(Target a, Type[] a, InsnList a, int a) {
      int[] a = a.generateArgMap(a, a);
      a.storeArgs(a, a, a, a, a.length);
      return a;
   }

   protected void storeArgs(Type[] a, InsnList a, int[] a, int a, int a) {
      for(int a = a - 1; a >= a; --a) {
         a.add((AbstractInsnNode)(new VarInsnNode(a[a].getOpcode(54), a[a])));
      }

   }

   protected void pushArgs(Type[] a, InsnList a, int[] a, int a, int a) {
      for(int a = a; a < a; ++a) {
         a.add((AbstractInsnNode)(new VarInsnNode(a[a].getOpcode(21), a[a])));
      }

   }
}

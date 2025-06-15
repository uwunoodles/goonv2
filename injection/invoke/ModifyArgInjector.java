package org.spongepowered.asm.mixin.injection.invoke;

import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

public class ModifyArgInjector extends InvokeInjector {
   private final int index;
   private final boolean singleArgMode;

   public ModifyArgInjector(InjectionInfo a, int a) {
      super(a, "@ModifyArg");
      a.index = a;
      a.singleArgMode = a.methodArgs.length == 1;
   }

   protected void sanityCheck(Target a, List<InjectionPoint> a) {
      super.sanityCheck(a, a);
      if (a.singleArgMode && !a.methodArgs[0].equals(a.returnType)) {
         throw new InvalidInjectionException(a.info, "@ModifyArg return type on " + a + " must match the parameter type. ARG=" + a.methodArgs[0] + " RETURN=" + a.returnType);
      }
   }

   protected void checkTarget(Target a) {
      if (!a.isStatic && a.isStatic) {
         throw new InvalidInjectionException(a.info, "non-static callback method " + a + " targets a static method which is not supported");
      }
   }

   protected void inject(Target a, InjectionNodes.InjectionNode a) {
      a.checkTargetForNode(a, a);
      super.inject(a, a);
   }

   protected void injectAtInvoke(Target a, InjectionNodes.InjectionNode a) {
      MethodInsnNode a = (MethodInsnNode)a.getCurrentTarget();
      Type[] a = Type.getArgumentTypes(a.desc);
      int a = a.findArgIndex(a, a);
      InsnList a = new InsnList();
      int a = false;
      int a;
      if (a.singleArgMode) {
         a = a.injectSingleArgHandler(a, a, a, a);
      } else {
         a = a.injectMultiArgHandler(a, a, a, a);
      }

      a.insns.insertBefore(a, (InsnList)a);
      a.addToLocals(a);
      a.addToStack(2 - (a - 1));
   }

   private int injectSingleArgHandler(Target a, Type[] a, int a, InsnList a) {
      int[] a = a.storeArgs(a, a, a, a);
      a.invokeHandlerWithArgs(a, a, a, a, a + 1);
      a.pushArgs(a, a, a, a + 1, a.length);
      return a[a.length - 1] - a.getMaxLocals() + a[a.length - 1].getSize();
   }

   private int injectMultiArgHandler(Target a, Type[] a, int a, InsnList a) {
      if (!Arrays.equals(a, a.methodArgs)) {
         throw new InvalidInjectionException(a.info, "@ModifyArg method " + a + " targets a method with an invalid signature " + Bytecode.getDescriptor(a) + ", expected " + Bytecode.getDescriptor(a.methodArgs));
      } else {
         int[] a = a.storeArgs(a, a, a, 0);
         a.pushArgs(a, a, a, 0, a);
         a.invokeHandlerWithArgs(a, a, a, 0, a.length);
         a.pushArgs(a, a, a, a + 1, a.length);
         return a[a.length - 1] - a.getMaxLocals() + a[a.length - 1].getSize();
      }
   }

   protected int findArgIndex(Target a, Type[] a) {
      if (a.index > -1) {
         if (a.index < a.length && a[a.index].equals(a.returnType)) {
            return a.index;
         } else {
            throw new InvalidInjectionException(a.info, "Specified index " + a.index + " for @ModifyArg is invalid for args " + Bytecode.getDescriptor(a) + ", expected " + a.returnType + " on " + a);
         }
      } else {
         int a = -1;

         for(int a = 0; a < a.length; ++a) {
            if (a[a].equals(a.returnType)) {
               if (a != -1) {
                  throw new InvalidInjectionException(a.info, "Found duplicate args with index [" + a + ", " + a + "] matching type " + a.returnType + " for @ModifyArg target " + a + " in " + a + ". Please specify index of desired arg.");
               }

               a = a;
            }
         }

         if (a == -1) {
            throw new InvalidInjectionException(a.info, "Could not find arg matching type " + a.returnType + " for @ModifyArg target " + a + " in " + a);
         } else {
            return a;
         }
      }
   }
}

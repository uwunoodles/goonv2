package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.Target;

@InjectionPoint.AtCode("LOAD")
public class BeforeLoadLocal extends ModifyVariableInjector.ContextualInjectionPoint {
   private final Type returnType;
   private final LocalVariableDiscriminator discriminator;
   private final int opcode;
   private final int ordinal;
   private boolean opcodeAfter;

   protected BeforeLoadLocal(InjectionPointData a) {
      this(a, 21, false);
   }

   protected BeforeLoadLocal(InjectionPointData a, int a, boolean a) {
      super(a.getContext());
      a.returnType = a.getMethodReturnType();
      a.discriminator = a.getLocalVariableDiscriminator();
      a.opcode = a.getOpcode(a.returnType.getOpcode(a));
      a.ordinal = a.getOrdinal();
      a.opcodeAfter = a;
   }

   boolean find(Target a, Collection<AbstractInsnNode> a) {
      BeforeLoadLocal.SearchState a = new BeforeLoadLocal.SearchState(a.ordinal, a.discriminator.printLVT());
      ListIterator a = a.method.instructions.iterator();

      while(true) {
         while(a.hasNext()) {
            AbstractInsnNode a = (AbstractInsnNode)a.next();
            int a;
            if (a.isPendingCheck()) {
               a = a.discriminator.findLocal(a.returnType, a.discriminator.isArgsOnly(), a, a);
               a.check(a, a, a);
            } else if (a instanceof VarInsnNode && a.getOpcode() == a.opcode && (a.ordinal == -1 || !a.success())) {
               a.register((VarInsnNode)a);
               if (a.opcodeAfter) {
                  a.setPendingCheck();
               } else {
                  a = a.discriminator.findLocal(a.returnType, a.discriminator.isArgsOnly(), a, a);
                  a.check(a, a, a);
               }
            }
         }

         return a.success();
      }
   }

   static class SearchState {
      private final boolean print;
      private final int targetOrdinal;
      private int ordinal = 0;
      private boolean pendingCheck = false;
      private boolean found = false;
      private VarInsnNode varNode;

      SearchState(int a, boolean a) {
         a.targetOrdinal = a;
         a.print = a;
      }

      boolean success() {
         return a.found;
      }

      boolean isPendingCheck() {
         return a.pendingCheck;
      }

      void setPendingCheck() {
         a.pendingCheck = true;
      }

      void register(VarInsnNode a) {
         a.varNode = a;
      }

      void check(Collection<AbstractInsnNode> a, AbstractInsnNode a, int a) {
         a.pendingCheck = false;
         if (a == a.varNode.var || a <= -2 && a.print) {
            if (a.targetOrdinal == -1 || a.targetOrdinal == a.ordinal) {
               a.add(a);
               a.found = true;
            }

            ++a.ordinal;
            a.varNode = null;
         }
      }
   }
}

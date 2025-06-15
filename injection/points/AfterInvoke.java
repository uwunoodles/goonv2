package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("INVOKE_ASSIGN")
public class AfterInvoke extends BeforeInvoke {
   public AfterInvoke(InjectionPointData a) {
      super(a);
   }

   protected boolean addInsn(InsnList a, Collection<AbstractInsnNode> a, AbstractInsnNode a) {
      MethodInsnNode a = (MethodInsnNode)a;
      if (Type.getReturnType(a.desc) == Type.VOID_TYPE) {
         return false;
      } else {
         a = InjectionPoint.nextNode(a, a);
         if (a instanceof VarInsnNode && a.getOpcode() >= 54) {
            a = InjectionPoint.nextNode(a, a);
         }

         a.add(a);
         return true;
      }
   }
}

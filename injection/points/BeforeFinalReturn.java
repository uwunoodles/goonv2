package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

@InjectionPoint.AtCode("TAIL")
public class BeforeFinalReturn extends InjectionPoint {
   private final IMixinContext context;

   public BeforeFinalReturn(InjectionPointData a) {
      super(a);
      a.context = a.getContext();
   }

   public boolean checkPriority(int a1, int a2) {
      return true;
   }

   public boolean find(String a, InsnList a, Collection<AbstractInsnNode> a) {
      AbstractInsnNode a = null;
      int a = Type.getReturnType(a).getOpcode(172);
      ListIterator a = a.iterator();

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (a instanceof InsnNode && a.getOpcode() == a) {
            a = a;
         }
      }

      if (a == null) {
         throw new InvalidInjectionException(a.context, "TAIL could not locate a valid RETURN in the target method!");
      } else {
         a.add(a);
         return true;
      }
   }
}

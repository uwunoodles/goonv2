package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("RETURN")
public class BeforeReturn extends InjectionPoint {
   private final int ordinal;

   public BeforeReturn(InjectionPointData a) {
      super(a);
      a.ordinal = a.getOrdinal();
   }

   public boolean checkPriority(int a1, int a2) {
      return true;
   }

   public boolean find(String a, InsnList a, Collection<AbstractInsnNode> a) {
      boolean a = false;
      int a = Type.getReturnType(a).getOpcode(172);
      int a = 0;
      ListIterator a = a.iterator();

      while(true) {
         AbstractInsnNode a;
         do {
            do {
               if (!a.hasNext()) {
                  return a;
               }

               a = (AbstractInsnNode)a.next();
            } while(!(a instanceof InsnNode));
         } while(a.getOpcode() != a);

         if (a.ordinal == -1 || a.ordinal == a) {
            a.add(a);
            a = true;
         }

         ++a;
      }
   }
}

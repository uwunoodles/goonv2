package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("JUMP")
public class JumpInsnPoint extends InjectionPoint {
   private final int opCode;
   private final int ordinal;

   public JumpInsnPoint(InjectionPointData a) {
      a.opCode = a.getOpcode(-1, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 198, 199, -1);
      a.ordinal = a.getOrdinal();
   }

   public boolean find(String a1, InsnList a, Collection<AbstractInsnNode> a) {
      boolean a = false;
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
            } while(!(a instanceof JumpInsnNode));
         } while(a.opCode != -1 && a.getOpcode() != a.opCode);

         if (a.ordinal == -1 || a.ordinal == a) {
            a.add(a);
            a = true;
         }

         ++a;
      }
   }
}

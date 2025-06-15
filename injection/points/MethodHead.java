package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("HEAD")
public class MethodHead extends InjectionPoint {
   public MethodHead(InjectionPointData a) {
      super(a);
   }

   public boolean checkPriority(int a1, int a2) {
      return true;
   }

   public boolean find(String a1, InsnList a, Collection<AbstractInsnNode> a) {
      a.add(a.getFirst());
      return true;
   }
}

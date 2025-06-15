package org.spongepowered.asm.lib.tree.analysis;

import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class SourceValue implements Value {
   public final int size;
   public final Set<AbstractInsnNode> insns;

   public SourceValue(int a) {
      this(a, SmallSet.emptySet());
   }

   public SourceValue(int a, AbstractInsnNode a) {
      a.size = a;
      a.insns = new SmallSet(a, (Object)null);
   }

   public SourceValue(int a, Set<AbstractInsnNode> a) {
      a.size = a;
      a.insns = a;
   }

   public int getSize() {
      return a.size;
   }

   public boolean equals(Object a) {
      if (!(a instanceof SourceValue)) {
         return false;
      } else {
         SourceValue a = (SourceValue)a;
         return a.size == a.size && a.insns.equals(a.insns);
      }
   }

   public int hashCode() {
      return a.insns.hashCode();
   }
}

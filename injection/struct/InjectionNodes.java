package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.util.Bytecode;

public class InjectionNodes extends ArrayList<InjectionNodes.InjectionNode> {
   private static final long serialVersionUID = 1L;

   public InjectionNodes.InjectionNode add(AbstractInsnNode a) {
      InjectionNodes.InjectionNode a = a.get(a);
      if (a == null) {
         a = new InjectionNodes.InjectionNode(a);
         a.add(a);
      }

      return a;
   }

   public InjectionNodes.InjectionNode get(AbstractInsnNode a) {
      Iterator var2 = a.iterator();

      InjectionNodes.InjectionNode a;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         a = (InjectionNodes.InjectionNode)var2.next();
      } while(!a.matches(a));

      return a;
   }

   public boolean contains(AbstractInsnNode a) {
      return a.get(a) != null;
   }

   public void replace(AbstractInsnNode a, AbstractInsnNode a) {
      InjectionNodes.InjectionNode a = a.get(a);
      if (a != null) {
         a.replace(a);
      }

   }

   public void remove(AbstractInsnNode a) {
      InjectionNodes.InjectionNode a = a.get(a);
      if (a != null) {
         a.remove();
      }

   }

   public static class InjectionNode implements Comparable<InjectionNodes.InjectionNode> {
      private static int nextId = 0;
      private final int id;
      private final AbstractInsnNode originalTarget;
      private AbstractInsnNode currentTarget;
      private Map<String, Object> decorations;

      public InjectionNode(AbstractInsnNode a) {
         a.currentTarget = a.originalTarget = a;
         a.id = nextId++;
      }

      public int getId() {
         return a.id;
      }

      public AbstractInsnNode getOriginalTarget() {
         return a.originalTarget;
      }

      public AbstractInsnNode getCurrentTarget() {
         return a.currentTarget;
      }

      public InjectionNodes.InjectionNode replace(AbstractInsnNode a) {
         a.currentTarget = a;
         return a;
      }

      public InjectionNodes.InjectionNode remove() {
         a.currentTarget = null;
         return a;
      }

      public boolean matches(AbstractInsnNode a) {
         return a.originalTarget == a || a.currentTarget == a;
      }

      public boolean isReplaced() {
         return a.originalTarget != a.currentTarget;
      }

      public boolean isRemoved() {
         return a.currentTarget == null;
      }

      public <V> InjectionNodes.InjectionNode decorate(String a, V a) {
         if (a.decorations == null) {
            a.decorations = new HashMap();
         }

         a.decorations.put(a, a);
         return a;
      }

      public boolean hasDecoration(String a) {
         return a.decorations != null && a.decorations.get(a) != null;
      }

      public <V> V getDecoration(String a) {
         return a.decorations == null ? null : a.decorations.get(a);
      }

      public int compareTo(InjectionNodes.InjectionNode a) {
         return a == null ? Integer.MAX_VALUE : a.hashCode() - a.hashCode();
      }

      public String toString() {
         return String.format("InjectionNode[%s]", Bytecode.describeNode(a.currentTarget).replaceAll("\\s+", " "));
      }
   }
}

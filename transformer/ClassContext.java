package org.spongepowered.asm.mixin.transformer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.struct.MemberRef;

abstract class ClassContext {
   private final Set<ClassInfo.Method> upgradedMethods = new HashSet();

   abstract String getClassRef();

   abstract ClassNode getClassNode();

   abstract ClassInfo getClassInfo();

   void addUpgradedMethod(MethodNode a) {
      ClassInfo.Method a = a.getClassInfo().findMethod(a);
      if (a == null) {
         throw new IllegalStateException("Meta method for " + a.name + " not located in " + a);
      } else {
         a.upgradedMethods.add(a);
      }
   }

   protected void upgradeMethods() {
      Iterator var1 = a.getClassNode().methods.iterator();

      while(var1.hasNext()) {
         MethodNode a = (MethodNode)var1.next();
         a.upgradeMethod(a);
      }

   }

   private void upgradeMethod(MethodNode a) {
      ListIterator a = a.instructions.iterator();

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (a instanceof MethodInsnNode) {
            MemberRef a = new MemberRef.Method((MethodInsnNode)a);
            if (a.getOwner().equals(a.getClassRef())) {
               ClassInfo.Method a = a.getClassInfo().findMethod(a.getName(), a.getDesc(), 10);
               a.upgradeMethodRef(a, a, a);
            }
         }
      }

   }

   protected void upgradeMethodRef(MethodNode a1, MemberRef a, ClassInfo.Method a) {
      if (a.getOpcode() == 183) {
         if (a.upgradedMethods.contains(a)) {
            a.setOpcode(182);
         }

      }
   }
}

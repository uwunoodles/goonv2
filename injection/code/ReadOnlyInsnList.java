package org.spongepowered.asm.mixin.injection.code;

import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;

class ReadOnlyInsnList extends InsnList {
   private InsnList insnList;

   public ReadOnlyInsnList(InsnList a) {
      a.insnList = a;
   }

   void dispose() {
      a.insnList = null;
   }

   public final void set(AbstractInsnNode a1, AbstractInsnNode a2) {
      throw new UnsupportedOperationException();
   }

   public final void add(AbstractInsnNode a1) {
      throw new UnsupportedOperationException();
   }

   public final void add(InsnList a1) {
      throw new UnsupportedOperationException();
   }

   public final void insert(AbstractInsnNode a1) {
      throw new UnsupportedOperationException();
   }

   public final void insert(InsnList a1) {
      throw new UnsupportedOperationException();
   }

   public final void insert(AbstractInsnNode a1, AbstractInsnNode a2) {
      throw new UnsupportedOperationException();
   }

   public final void insert(AbstractInsnNode a1, InsnList a2) {
      throw new UnsupportedOperationException();
   }

   public final void insertBefore(AbstractInsnNode a1, AbstractInsnNode a2) {
      throw new UnsupportedOperationException();
   }

   public final void insertBefore(AbstractInsnNode a1, InsnList a2) {
      throw new UnsupportedOperationException();
   }

   public final void remove(AbstractInsnNode a1) {
      throw new UnsupportedOperationException();
   }

   public AbstractInsnNode[] toArray() {
      return a.insnList.toArray();
   }

   public int size() {
      return a.insnList.size();
   }

   public AbstractInsnNode getFirst() {
      return a.insnList.getFirst();
   }

   public AbstractInsnNode getLast() {
      return a.insnList.getLast();
   }

   public AbstractInsnNode get(int a) {
      return a.insnList.get(a);
   }

   public boolean contains(AbstractInsnNode a) {
      return a.insnList.contains(a);
   }

   public int indexOf(AbstractInsnNode a) {
      return a.insnList.indexOf(a);
   }

   public ListIterator<AbstractInsnNode> iterator() {
      return a.insnList.iterator();
   }

   public ListIterator<AbstractInsnNode> iterator(int a) {
      return a.insnList.iterator(a);
   }

   public final void resetLabels() {
      a.insnList.resetLabels();
   }
}

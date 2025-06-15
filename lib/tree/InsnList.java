package org.spongepowered.asm.lib.tree;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.spongepowered.asm.lib.MethodVisitor;

public class InsnList {
   private int size;
   private AbstractInsnNode first;
   private AbstractInsnNode last;
   AbstractInsnNode[] cache;

   public int size() {
      return a.size;
   }

   public AbstractInsnNode getFirst() {
      return a.first;
   }

   public AbstractInsnNode getLast() {
      return a.last;
   }

   public AbstractInsnNode get(int a) {
      if (a >= 0 && a < a.size) {
         if (a.cache == null) {
            a.cache = a.toArray();
         }

         return a.cache[a];
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public boolean contains(AbstractInsnNode a) {
      AbstractInsnNode a;
      for(a = a.first; a != null && a != a; a = a.next) {
      }

      return a != null;
   }

   public int indexOf(AbstractInsnNode a) {
      if (a.cache == null) {
         a.cache = a.toArray();
      }

      return a.index;
   }

   public void accept(MethodVisitor a) {
      for(AbstractInsnNode a = a.first; a != null; a = a.next) {
         a.accept(a);
      }

   }

   public ListIterator<AbstractInsnNode> iterator() {
      return a.iterator(0);
   }

   public ListIterator<AbstractInsnNode> iterator(int a) {
      return new InsnList.InsnListIterator(a);
   }

   public AbstractInsnNode[] toArray() {
      int a = 0;
      AbstractInsnNode a = a.first;

      AbstractInsnNode[] a;
      for(a = new AbstractInsnNode[a.size]; a != null; a = a.next) {
         a[a] = a;
         a.index = a++;
      }

      return a;
   }

   public void set(AbstractInsnNode a, AbstractInsnNode a) {
      AbstractInsnNode a = a.next;
      a.next = a;
      if (a != null) {
         a.prev = a;
      } else {
         a.last = a;
      }

      AbstractInsnNode a = a.prev;
      a.prev = a;
      if (a != null) {
         a.next = a;
      } else {
         a.first = a;
      }

      if (a.cache != null) {
         int a = a.index;
         a.cache[a] = a;
         a.index = a;
      } else {
         a.index = 0;
      }

      a.index = -1;
      a.prev = null;
      a.next = null;
   }

   public void add(AbstractInsnNode a) {
      ++a.size;
      if (a.last == null) {
         a.first = a;
         a.last = a;
      } else {
         a.last.next = a;
         a.prev = a.last;
      }

      a.last = a;
      a.cache = null;
      a.index = 0;
   }

   public void add(InsnList a) {
      if (a.size != 0) {
         a.size += a.size;
         if (a.last == null) {
            a.first = a.first;
            a.last = a.last;
         } else {
            AbstractInsnNode a = a.first;
            a.last.next = a;
            a.prev = a.last;
            a.last = a.last;
         }

         a.cache = null;
         a.removeAll(false);
      }
   }

   public void insert(AbstractInsnNode a) {
      ++a.size;
      if (a.first == null) {
         a.first = a;
         a.last = a;
      } else {
         a.first.prev = a;
         a.next = a.first;
      }

      a.first = a;
      a.cache = null;
      a.index = 0;
   }

   public void insert(InsnList a) {
      if (a.size != 0) {
         a.size += a.size;
         if (a.first == null) {
            a.first = a.first;
            a.last = a.last;
         } else {
            AbstractInsnNode a = a.last;
            a.first.prev = a;
            a.next = a.first;
            a.first = a.first;
         }

         a.cache = null;
         a.removeAll(false);
      }
   }

   public void insert(AbstractInsnNode a, AbstractInsnNode a) {
      ++a.size;
      AbstractInsnNode a = a.next;
      if (a == null) {
         a.last = a;
      } else {
         a.prev = a;
      }

      a.next = a;
      a.next = a;
      a.prev = a;
      a.cache = null;
      a.index = 0;
   }

   public void insert(AbstractInsnNode a, InsnList a) {
      if (a.size != 0) {
         a.size += a.size;
         AbstractInsnNode a = a.first;
         AbstractInsnNode a = a.last;
         AbstractInsnNode a = a.next;
         if (a == null) {
            a.last = a;
         } else {
            a.prev = a;
         }

         a.next = a;
         a.next = a;
         a.prev = a;
         a.cache = null;
         a.removeAll(false);
      }
   }

   public void insertBefore(AbstractInsnNode a, AbstractInsnNode a) {
      ++a.size;
      AbstractInsnNode a = a.prev;
      if (a == null) {
         a.first = a;
      } else {
         a.next = a;
      }

      a.prev = a;
      a.next = a;
      a.prev = a;
      a.cache = null;
      a.index = 0;
   }

   public void insertBefore(AbstractInsnNode a, InsnList a) {
      if (a.size != 0) {
         a.size += a.size;
         AbstractInsnNode a = a.first;
         AbstractInsnNode a = a.last;
         AbstractInsnNode a = a.prev;
         if (a == null) {
            a.first = a;
         } else {
            a.next = a;
         }

         a.prev = a;
         a.next = a;
         a.prev = a;
         a.cache = null;
         a.removeAll(false);
      }
   }

   public void remove(AbstractInsnNode a) {
      --a.size;
      AbstractInsnNode a = a.next;
      AbstractInsnNode a = a.prev;
      if (a == null) {
         if (a == null) {
            a.first = null;
            a.last = null;
         } else {
            a.next = null;
            a.last = a;
         }
      } else if (a == null) {
         a.first = a;
         a.prev = null;
      } else {
         a.next = a;
         a.prev = a;
      }

      a.cache = null;
      a.index = -1;
      a.prev = null;
      a.next = null;
   }

   void removeAll(boolean a) {
      AbstractInsnNode a;
      if (a) {
         for(AbstractInsnNode a = a.first; a != null; a = a) {
            a = a.next;
            a.index = -1;
            a.prev = null;
            a.next = null;
         }
      }

      a.size = 0;
      a.first = null;
      a.last = null;
      a.cache = null;
   }

   public void clear() {
      a.removeAll(false);
   }

   public void resetLabels() {
      for(AbstractInsnNode a = a.first; a != null; a = a.next) {
         if (a instanceof LabelNode) {
            ((LabelNode)a).resetLabel();
         }
      }

   }

   private final class InsnListIterator implements ListIterator {
      AbstractInsnNode next;
      AbstractInsnNode prev;
      AbstractInsnNode remove;

      InsnListIterator(int ax) {
         if (ax == InsnList.this.size()) {
            a.next = null;
            a.prev = InsnList.this.getLast();
         } else {
            a.next = InsnList.this.get(ax);
            a.prev = a.next.prev;
         }

      }

      public boolean hasNext() {
         return a.next != null;
      }

      public Object next() {
         if (a.next == null) {
            throw new NoSuchElementException();
         } else {
            AbstractInsnNode ax = a.next;
            a.prev = ax;
            a.next = ax.next;
            a.remove = ax;
            return ax;
         }
      }

      public void remove() {
         if (a.remove != null) {
            if (a.remove == a.next) {
               a.next = a.next.next;
            } else {
               a.prev = a.prev.prev;
            }

            InsnList.this.remove(a.remove);
            a.remove = null;
         } else {
            throw new IllegalStateException();
         }
      }

      public boolean hasPrevious() {
         return a.prev != null;
      }

      public Object previous() {
         AbstractInsnNode ax = a.prev;
         a.next = ax;
         a.prev = ax.prev;
         a.remove = ax;
         return ax;
      }

      public int nextIndex() {
         if (a.next == null) {
            return InsnList.this.size();
         } else {
            if (InsnList.this.cache == null) {
               InsnList.this.cache = InsnList.this.toArray();
            }

            return a.next.index;
         }
      }

      public int previousIndex() {
         if (a.prev == null) {
            return -1;
         } else {
            if (InsnList.this.cache == null) {
               InsnList.this.cache = InsnList.this.toArray();
            }

            return a.prev.index;
         }
      }

      public void add(Object ax) {
         if (a.next != null) {
            InsnList.this.insertBefore(a.next, (AbstractInsnNode)ax);
         } else if (a.prev != null) {
            InsnList.this.insert(a.prev, (AbstractInsnNode)ax);
         } else {
            InsnList.this.add((AbstractInsnNode)ax);
         }

         a.prev = (AbstractInsnNode)ax;
         a.remove = null;
      }

      public void set(Object ax) {
         if (a.remove != null) {
            InsnList.this.set(a.remove, (AbstractInsnNode)ax);
            if (a.remove == a.prev) {
               a.prev = (AbstractInsnNode)ax;
            } else {
               a.next = (AbstractInsnNode)ax;
            }

         } else {
            throw new IllegalStateException();
         }
      }
   }
}

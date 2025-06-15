package org.spongepowered.asm.lib.tree.analysis;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

class SmallSet<E> extends AbstractSet<E> implements Iterator<E> {
   E e1;
   E e2;

   static final <T> Set<T> emptySet() {
      return new SmallSet((Object)null, (Object)null);
   }

   SmallSet(E a, E a) {
      a.e1 = a;
      a.e2 = a;
   }

   public Iterator<E> iterator() {
      return new SmallSet(a.e1, a.e2);
   }

   public int size() {
      return a.e1 == null ? 0 : (a.e2 == null ? 1 : 2);
   }

   public boolean hasNext() {
      return a.e1 != null;
   }

   public E next() {
      if (a.e1 == null) {
         throw new NoSuchElementException();
      } else {
         E a = a.e1;
         a.e1 = a.e2;
         a.e2 = null;
         return a;
      }
   }

   public void remove() {
   }

   Set<E> union(SmallSet<E> a) {
      if ((a.e1 != a.e1 || a.e2 != a.e2) && (a.e1 != a.e2 || a.e2 != a.e1)) {
         if (a.e1 == null) {
            return a;
         } else if (a.e1 == null) {
            return a;
         } else {
            if (a.e2 == null) {
               if (a.e2 == null) {
                  return new SmallSet(a.e1, a.e1);
               }

               if (a.e1 == a.e1 || a.e1 == a.e2) {
                  return a;
               }
            }

            if (a.e2 != null || a.e1 != a.e1 && a.e1 != a.e2) {
               HashSet<E> a = new HashSet(4);
               a.add(a.e1);
               if (a.e2 != null) {
                  a.add(a.e2);
               }

               a.add(a.e1);
               if (a.e2 != null) {
                  a.add(a.e2);
               }

               return a;
            } else {
               return a;
            }
         }
      } else {
         return a;
      }
   }
}

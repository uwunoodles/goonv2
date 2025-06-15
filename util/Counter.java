package org.spongepowered.asm.util;

public final class Counter {
   public int value;

   public boolean equals(Object a) {
      return a != null && a.getClass() == Counter.class && ((Counter)a).value == a.value;
   }

   public int hashCode() {
      return a.value;
   }
}

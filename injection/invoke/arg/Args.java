package org.spongepowered.asm.mixin.injection.invoke.arg;

public abstract class Args {
   protected final Object[] values;

   protected Args(Object[] a) {
      a.values = a;
   }

   public int size() {
      return a.values.length;
   }

   public <T> T get(int a) {
      return a.values[a];
   }

   public abstract <T> void set(int var1, T var2);

   public abstract void setAll(Object... var1);
}

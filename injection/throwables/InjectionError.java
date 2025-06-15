package org.spongepowered.asm.mixin.injection.throwables;

public class InjectionError extends Error {
   private static final long serialVersionUID = 1L;

   public InjectionError() {
   }

   public InjectionError(String a) {
      super(a);
   }

   public InjectionError(Throwable a) {
      super(a);
   }

   public InjectionError(String a, Throwable a) {
      super(a, a);
   }
}

package org.spongepowered.asm.mixin.throwables;

public class MixinPrepareError extends Error {
   private static final long serialVersionUID = 1L;

   public MixinPrepareError(String a) {
      super(a);
   }

   public MixinPrepareError(Throwable a) {
      super(a);
   }

   public MixinPrepareError(String a, Throwable a) {
      super(a, a);
   }
}

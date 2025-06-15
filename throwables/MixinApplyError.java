package org.spongepowered.asm.mixin.throwables;

public class MixinApplyError extends Error {
   private static final long serialVersionUID = 1L;

   public MixinApplyError(String a) {
      super(a);
   }

   public MixinApplyError(Throwable a) {
      super(a);
   }

   public MixinApplyError(String a, Throwable a) {
      super(a, a);
   }
}

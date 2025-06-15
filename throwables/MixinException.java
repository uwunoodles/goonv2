package org.spongepowered.asm.mixin.throwables;

public class MixinException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public MixinException() {
   }

   public MixinException(String a) {
      super(a);
   }

   public MixinException(Throwable a) {
      super(a);
   }

   public MixinException(String a, Throwable a) {
      super(a, a);
   }
}

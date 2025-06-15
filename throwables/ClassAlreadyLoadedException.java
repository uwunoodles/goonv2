package org.spongepowered.asm.mixin.throwables;

public class ClassAlreadyLoadedException extends MixinException {
   private static final long serialVersionUID = 1L;

   public ClassAlreadyLoadedException(String a) {
      super(a);
   }

   public ClassAlreadyLoadedException(Throwable a) {
      super(a);
   }

   public ClassAlreadyLoadedException(String a, Throwable a) {
      super(a, a);
   }
}

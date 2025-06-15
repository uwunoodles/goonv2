package org.spongepowered.asm.mixin.transformer.throwables;

public class MixinTransformerError extends Error {
   private static final long serialVersionUID = 1L;

   public MixinTransformerError(String a) {
      super(a);
   }

   public MixinTransformerError(Throwable a) {
      super(a);
   }

   public MixinTransformerError(String a, Throwable a) {
      super(a, a);
   }
}

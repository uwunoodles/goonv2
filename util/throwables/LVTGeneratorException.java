package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.mixin.throwables.MixinException;

public class LVTGeneratorException extends MixinException {
   private static final long serialVersionUID = 1L;

   public LVTGeneratorException(String a) {
      super(a);
   }

   public LVTGeneratorException(String a, Throwable a) {
      super(a, a);
   }
}

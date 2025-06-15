package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.throwables.MixinException;

public class InvalidImplicitDiscriminatorException extends MixinException {
   private static final long serialVersionUID = 1L;

   public InvalidImplicitDiscriminatorException(String a) {
      super(a);
   }

   public InvalidImplicitDiscriminatorException(String a, Throwable a) {
      super(a, a);
   }
}

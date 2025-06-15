package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.throwables.MixinException;

public class InvalidMemberDescriptorException extends MixinException {
   private static final long serialVersionUID = 1L;

   public InvalidMemberDescriptorException(String a) {
      super(a);
   }

   public InvalidMemberDescriptorException(Throwable a) {
      super(a);
   }

   public InvalidMemberDescriptorException(String a, Throwable a) {
      super(a, a);
   }
}

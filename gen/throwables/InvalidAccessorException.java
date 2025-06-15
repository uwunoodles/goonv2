package org.spongepowered.asm.mixin.gen.throwables;

import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidAccessorException extends InvalidMixinException {
   private static final long serialVersionUID = 2L;
   private final AccessorInfo info;

   public InvalidAccessorException(IMixinContext a, String a) {
      super(a, a);
      a.info = null;
   }

   public InvalidAccessorException(AccessorInfo a, String a) {
      super(a.getContext(), a);
      a.info = a;
   }

   public InvalidAccessorException(IMixinContext a, Throwable a) {
      super(a, a);
      a.info = null;
   }

   public InvalidAccessorException(AccessorInfo a, Throwable a) {
      super(a.getContext(), a);
      a.info = a;
   }

   public InvalidAccessorException(IMixinContext a, String a, Throwable a) {
      super(a, a, a);
      a.info = null;
   }

   public InvalidAccessorException(AccessorInfo a, String a, Throwable a) {
      super(a.getContext(), a, a);
      a.info = a;
   }

   public AccessorInfo getAccessorInfo() {
      return a.info;
   }
}

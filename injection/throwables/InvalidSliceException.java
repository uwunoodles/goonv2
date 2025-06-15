package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class InvalidSliceException extends InvalidInjectionException {
   private static final long serialVersionUID = 1L;

   public InvalidSliceException(IMixinContext a, String a) {
      super(a, a);
   }

   public InvalidSliceException(ISliceContext a, String a) {
      super(a.getContext(), a);
   }

   public InvalidSliceException(IMixinContext a, Throwable a) {
      super(a, a);
   }

   public InvalidSliceException(ISliceContext a, Throwable a) {
      super(a.getContext(), a);
   }

   public InvalidSliceException(IMixinContext a, String a, Throwable a) {
      super(a, a, a);
   }

   public InvalidSliceException(ISliceContext a, String a, Throwable a) {
      super(a.getContext(), a, a);
   }
}

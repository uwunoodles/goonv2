package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class InvalidInjectionPointException extends InvalidInjectionException {
   private static final long serialVersionUID = 2L;

   public InvalidInjectionPointException(IMixinContext a, String a, Object... a) {
      super(a, String.format(a, a));
   }

   public InvalidInjectionPointException(InjectionInfo a, String a, Object... a) {
      super(a, String.format(a, a));
   }

   public InvalidInjectionPointException(IMixinContext a, Throwable a, String a, Object... a) {
      super(a, String.format(a, a), a);
   }

   public InvalidInjectionPointException(InjectionInfo a, Throwable a, String a, Object... a) {
      super(a, String.format(a, a), a);
   }
}

package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidInjectionException extends InvalidMixinException {
   private static final long serialVersionUID = 2L;
   private final InjectionInfo info;

   public InvalidInjectionException(IMixinContext a, String a) {
      super(a, a);
      a.info = null;
   }

   public InvalidInjectionException(InjectionInfo a, String a) {
      super(a.getContext(), a);
      a.info = a;
   }

   public InvalidInjectionException(IMixinContext a, Throwable a) {
      super(a, a);
      a.info = null;
   }

   public InvalidInjectionException(InjectionInfo a, Throwable a) {
      super(a.getContext(), a);
      a.info = a;
   }

   public InvalidInjectionException(IMixinContext a, String a, Throwable a) {
      super(a, a, a);
      a.info = null;
   }

   public InvalidInjectionException(InjectionInfo a, String a, Throwable a) {
      super(a.getContext(), a, a);
      a.info = a;
   }

   public InjectionInfo getInjectionInfo() {
      return a.info;
   }
}

package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class InvalidInterfaceMixinException extends InvalidMixinException {
   private static final long serialVersionUID = 2L;

   public InvalidInterfaceMixinException(IMixinInfo a, String a) {
      super(a, a);
   }

   public InvalidInterfaceMixinException(IMixinContext a, String a) {
      super(a, a);
   }

   public InvalidInterfaceMixinException(IMixinInfo a, Throwable a) {
      super(a, a);
   }

   public InvalidInterfaceMixinException(IMixinContext a, Throwable a) {
      super(a, a);
   }

   public InvalidInterfaceMixinException(IMixinInfo a, String a, Throwable a) {
      super(a, a, a);
   }

   public InvalidInterfaceMixinException(IMixinContext a, String a, Throwable a) {
      super(a, a, a);
   }
}

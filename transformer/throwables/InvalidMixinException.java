package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.throwables.MixinException;

public class InvalidMixinException extends MixinException {
   private static final long serialVersionUID = 2L;
   private final IMixinInfo mixin;

   public InvalidMixinException(IMixinInfo a, String a) {
      super(a);
      a.mixin = a;
   }

   public InvalidMixinException(IMixinContext a, String a) {
      this(a.getMixin(), a);
   }

   public InvalidMixinException(IMixinInfo a, Throwable a) {
      super(a);
      a.mixin = a;
   }

   public InvalidMixinException(IMixinContext a, Throwable a) {
      this(a.getMixin(), a);
   }

   public InvalidMixinException(IMixinInfo a, String a, Throwable a) {
      super(a, a);
      a.mixin = a;
   }

   public InvalidMixinException(IMixinContext a, String a, Throwable a) {
      super(a, a);
      a.mixin = a.getMixin();
   }

   public IMixinInfo getMixin() {
      return a.mixin;
   }
}

package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinTargetAlreadyLoadedException extends InvalidMixinException {
   private static final long serialVersionUID = 1L;
   private final String target;

   public MixinTargetAlreadyLoadedException(IMixinInfo a, String a, String a) {
      super(a, a);
      a.target = a;
   }

   public MixinTargetAlreadyLoadedException(IMixinInfo a, String a, String a, Throwable a) {
      super(a, a, a);
      a.target = a;
   }

   public String getTarget() {
      return a.target;
   }
}

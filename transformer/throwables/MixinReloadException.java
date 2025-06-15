package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.throwables.MixinException;

public class MixinReloadException extends MixinException {
   private static final long serialVersionUID = 2L;
   private final IMixinInfo mixinInfo;

   public MixinReloadException(IMixinInfo a, String a) {
      super(a);
      a.mixinInfo = a;
   }

   public IMixinInfo getMixinInfo() {
      return a.mixinInfo;
   }
}

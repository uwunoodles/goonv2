package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.transformers.MixinClassWriter;

public class ExtensionCheckClass implements IExtension {
   public boolean checkActive(MixinEnvironment a) {
      return a.getOption(MixinEnvironment.Option.DEBUG_VERIFY);
   }

   public void preApply(ITargetClassContext a1) {
   }

   public void postApply(ITargetClassContext a) {
      try {
         a.getClassNode().accept(new CheckClassAdapter(new MixinClassWriter(2)));
      } catch (RuntimeException var3) {
         throw new ExtensionCheckClass.ValidationFailedException(var3.getMessage(), var3);
      }
   }

   public void export(MixinEnvironment a1, String a2, boolean a3, byte[] a4) {
   }

   public static class ValidationFailedException extends MixinException {
      private static final long serialVersionUID = 1L;

      public ValidationFailedException(String a, Throwable a) {
         super(a, a);
      }

      public ValidationFailedException(String a) {
         super(a);
      }

      public ValidationFailedException(Throwable a) {
         super(a);
      }
   }
}

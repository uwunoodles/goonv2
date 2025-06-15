package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInjector;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

public class CallbackInjectionInfo extends InjectionInfo {
   protected CallbackInjectionInfo(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      super(a, a, a);
   }

   protected Injector parseInjector(AnnotationNode a) {
      boolean a = (Boolean)Annotations.getValue(a, "cancellable", (Object)Boolean.FALSE);
      LocalCapture a = (LocalCapture)Annotations.getValue(a, "locals", LocalCapture.class, LocalCapture.NO_CAPTURE);
      String a = (String)Annotations.getValue(a, "id", (Object)"");
      return new CallbackInjector(a, a, a, a);
   }

   public String getSliceId(String a) {
      return Strings.nullToEmpty(a);
   }
}

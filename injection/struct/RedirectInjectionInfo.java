package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.RedirectInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class RedirectInjectionInfo extends InjectionInfo {
   public RedirectInjectionInfo(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      super(a, a, a);
   }

   protected Injector parseInjector(AnnotationNode a1) {
      return new RedirectInjector(a);
   }

   protected String getDescription() {
      return "Redirector";
   }
}

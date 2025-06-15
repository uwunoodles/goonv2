package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgsInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class ModifyArgsInjectionInfo extends InjectionInfo {
   public ModifyArgsInjectionInfo(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      super(a, a, a);
   }

   protected Injector parseInjector(AnnotationNode a1) {
      return new ModifyArgsInjector(a);
   }

   protected String getDescription() {
      return "Multi-argument modifier method";
   }
}

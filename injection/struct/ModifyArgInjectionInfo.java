package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

public class ModifyArgInjectionInfo extends InjectionInfo {
   public ModifyArgInjectionInfo(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      super(a, a, a);
   }

   protected Injector parseInjector(AnnotationNode a) {
      int a = (Integer)Annotations.getValue(a, "index", (int)-1);
      return new ModifyArgInjector(a, a);
   }

   protected String getDescription() {
      return "Argument modifier method";
   }
}

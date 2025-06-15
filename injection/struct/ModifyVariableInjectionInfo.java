package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class ModifyVariableInjectionInfo extends InjectionInfo {
   public ModifyVariableInjectionInfo(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      super(a, a, a);
   }

   protected Injector parseInjector(AnnotationNode a) {
      return new ModifyVariableInjector(a, LocalVariableDiscriminator.parse(a));
   }

   protected String getDescription() {
      return "Variable modifier method";
   }
}

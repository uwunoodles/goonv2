package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
import org.spongepowered.asm.util.Bytecode;

class MixinPreProcessorInterface extends MixinPreProcessorStandard {
   MixinPreProcessorInterface(MixinInfo a, MixinInfo.MixinClassNode a) {
      super(a, a);
   }

   protected void prepareMethod(MixinInfo.MixinMethodNode a, ClassInfo.Method a) {
      if (!Bytecode.hasFlag((MethodNode)a, 1) && !Bytecode.hasFlag((MethodNode)a, 4096)) {
         throw new InvalidInterfaceMixinException(a.mixin, "Interface mixin contains a non-public method! Found " + a + " in " + a.mixin);
      } else {
         super.prepareMethod(a, a);
      }
   }

   protected boolean validateField(MixinTargetContext a, FieldNode a, AnnotationNode a) {
      if (!Bytecode.hasFlag((FieldNode)a, 8)) {
         throw new InvalidInterfaceMixinException(a.mixin, "Interface mixin contains an instance field! Found " + a.name + " in " + a.mixin);
      } else {
         return super.validateField(a, a, a);
      }
   }
}

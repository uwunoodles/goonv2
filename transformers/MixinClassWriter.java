package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public class MixinClassWriter extends ClassWriter {
   public MixinClassWriter(int a) {
      super(a);
   }

   public MixinClassWriter(ClassReader a, int a) {
      super(a, a);
   }

   protected String getCommonSuperClass(String a, String a) {
      return ClassInfo.getCommonSuperClass(a, a).getName();
   }
}

package org.spongepowered.asm.service.mojang;

import javax.annotation.Resource;
import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.service.ILegacyClassTransformer;

class LegacyTransformerHandle implements ILegacyClassTransformer {
   private final IClassTransformer transformer;

   LegacyTransformerHandle(IClassTransformer a) {
      a.transformer = a;
   }

   public String getName() {
      return a.transformer.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return a.transformer.getClass().getAnnotation(Resource.class) != null;
   }

   public byte[] transformClassBytes(String a, String a, byte[] a) {
      return a.transformer.transform(a, a, a);
   }
}

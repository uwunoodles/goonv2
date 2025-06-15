package org.spongepowered.tools.obfuscation.mirror.mapping;

import java.util.Iterator;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public final class ResolvableMappingMethod extends MappingMethod {
   private final TypeHandle ownerHandle;

   public ResolvableMappingMethod(TypeHandle a, String a, String a) {
      super(a.getName(), a, a);
      a.ownerHandle = a;
   }

   public MappingMethod getSuper() {
      if (a.ownerHandle == null) {
         return super.getSuper();
      } else {
         String a = a.getSimpleName();
         String a = a.getDesc();
         String a = TypeUtils.getJavaSignature(a);
         TypeHandle a = a.ownerHandle.getSuperclass();
         if (a != null && a.findMethod(a, a) != null) {
            return a.getMappingMethod(a, a);
         } else {
            Iterator var5 = a.ownerHandle.getInterfaces().iterator();

            TypeHandle a;
            do {
               if (!var5.hasNext()) {
                  if (a != null) {
                     return a.getMappingMethod(a, a).getSuper();
                  }

                  return super.getSuper();
               }

               a = (TypeHandle)var5.next();
            } while(a.findMethod(a, a) == null);

            return a.getMappingMethod(a, a);
         }
      }
   }

   public MappingMethod move(TypeHandle a) {
      return new ResolvableMappingMethod(a, a.getSimpleName(), a.getDesc());
   }

   public MappingMethod remap(String a) {
      return new ResolvableMappingMethod(a.ownerHandle, a, a.getDesc());
   }

   public MappingMethod transform(String a) {
      return new ResolvableMappingMethod(a.ownerHandle, a.getSimpleName(), a);
   }

   public MappingMethod copy() {
      return new ResolvableMappingMethod(a.ownerHandle, a.getSimpleName(), a.getDesc());
   }
}

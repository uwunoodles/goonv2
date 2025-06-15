package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

public class InjectionValidationException extends Exception {
   private static final long serialVersionUID = 1L;
   private final InjectorGroupInfo group;

   public InjectionValidationException(InjectorGroupInfo a, String a) {
      super(a);
      a.group = a;
   }

   public InjectorGroupInfo getGroup() {
      return a.group;
   }
}

package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Interface {
   Class<?> iface();

   String prefix();

   boolean unique() default false;

   Interface.Remap remap() default Interface.Remap.ALL;

   public static enum Remap {
      ALL,
      FORCE(true),
      ONLY_PREFIXED,
      NONE;

      private final boolean forceRemap;

      private Remap() {
         this(false);
      }

      private Remap(boolean a) {
         a.forceRemap = a;
      }

      public boolean forceRemap() {
         return a.forceRemap;
      }
   }
}

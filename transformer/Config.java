package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;

public class Config {
   private final String name;
   private final MixinConfig config;

   public Config(MixinConfig a) {
      a.name = a.getName();
      a.config = a;
   }

   public String getName() {
      return a.name;
   }

   MixinConfig get() {
      return a.config;
   }

   public boolean isVisited() {
      return a.config.isVisited();
   }

   public IMixinConfig getConfig() {
      return a.config;
   }

   public MixinEnvironment getEnvironment() {
      return a.config.getEnvironment();
   }

   public String toString() {
      return a.config.toString();
   }

   public boolean equals(Object a) {
      return a instanceof Config && a.name.equals(((Config)a).name);
   }

   public int hashCode() {
      return a.name.hashCode();
   }

   /** @deprecated */
   @Deprecated
   public static Config create(String a, MixinEnvironment a) {
      return MixinConfig.create(a, a);
   }

   public static Config create(String a) {
      return MixinConfig.create(a, MixinEnvironment.getDefaultEnvironment());
   }
}

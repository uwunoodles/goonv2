package org.spongepowered.asm.mixin;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.transformer.Config;

public final class Mixins {
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final String CONFIGS_KEY = "mixin.configs.queue";
   private static final Set<String> errorHandlers = new LinkedHashSet();

   private Mixins() {
   }

   public static void addConfigurations(String... a) {
      MixinEnvironment a = MixinEnvironment.getDefaultEnvironment();
      String[] var2 = a;
      int var3 = a.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String a = var2[var4];
         createConfiguration(a, a);
      }

   }

   public static void addConfiguration(String a) {
      createConfiguration(a, MixinEnvironment.getDefaultEnvironment());
   }

   /** @deprecated */
   @Deprecated
   static void addConfiguration(String a, MixinEnvironment a) {
      createConfiguration(a, a);
   }

   private static void createConfiguration(String a, MixinEnvironment a) {
      Config a = null;

      try {
         a = Config.create(a, a);
      } catch (Exception var4) {
         logger.error("Error encountered reading mixin config " + a + ": " + var4.getClass().getName() + " " + var4.getMessage(), var4);
      }

      registerConfiguration(a);
   }

   private static void registerConfiguration(Config a) {
      if (a != null) {
         MixinEnvironment a = a.getEnvironment();
         if (a != null) {
            a.registerConfig(a.getName());
         }

         getConfigs().add(a);
      }
   }

   public static int getUnvisitedCount() {
      int a = 0;
      Iterator var1 = getConfigs().iterator();

      while(var1.hasNext()) {
         Config a = (Config)var1.next();
         if (!a.isVisited()) {
            ++a;
         }
      }

      return a;
   }

   public static Set<Config> getConfigs() {
      Set<Config> a = (Set)GlobalProperties.get("mixin.configs.queue");
      if (a == null) {
         a = new LinkedHashSet();
         GlobalProperties.put("mixin.configs.queue", a);
      }

      return (Set)a;
   }

   public static void registerErrorHandlerClass(String a) {
      if (a != null) {
         errorHandlers.add(a);
      }

   }

   public static Set<String> getErrorHandlerClasses() {
      return Collections.unmodifiableSet(errorHandlers);
   }
}

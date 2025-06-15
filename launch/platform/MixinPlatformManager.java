package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.service.MixinService;

public class MixinPlatformManager {
   private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
   private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final Map<URI, MixinContainer> containers = new LinkedHashMap();
   private MixinContainer primaryContainer;
   private boolean prepared = false;
   private boolean injected;

   public void init() {
      logger.debug("Initialising Mixin Platform Manager");
      URI a = null;

      try {
         a = a.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
         if (a != null) {
            logger.debug("Mixin platform: primary container is {}", new Object[]{a});
            a.primaryContainer = a.addContainer(a);
         }
      } catch (URISyntaxException var3) {
         var3.printStackTrace();
      }

      a.scanClasspath();
   }

   public Collection<String> getPhaseProviderClasses() {
      Collection<String> a = a.primaryContainer.getPhaseProviders();
      return (Collection)(a != null ? Collections.unmodifiableCollection(a) : Collections.emptyList());
   }

   public final MixinContainer addContainer(URI a) {
      MixinContainer a = (MixinContainer)a.containers.get(a);
      if (a != null) {
         return a;
      } else {
         logger.debug("Adding mixin platform agents for container {}", new Object[]{a});
         MixinContainer a = new MixinContainer(a, a);
         a.containers.put(a, a);
         if (a.prepared) {
            a.prepare();
         }

         return a;
      }
   }

   public final void prepare(List<String> a) {
      a.prepared = true;
      Iterator var2 = a.containers.values().iterator();

      while(var2.hasNext()) {
         MixinContainer a = (MixinContainer)var2.next();
         a.prepare();
      }

      if (a != null) {
         a.parseArgs(a);
      } else {
         String a = System.getProperty("sun.java.command");
         if (a != null) {
            a.parseArgs(Arrays.asList(a.split(" ")));
         }
      }

   }

   private void parseArgs(List<String> a) {
      boolean a = false;

      String a;
      for(Iterator var3 = a.iterator(); var3.hasNext(); a = "--mixin".equals(a)) {
         a = (String)var3.next();
         if (a) {
            a.addConfig(a);
         }
      }

   }

   public final void inject() {
      if (!a.injected) {
         a.injected = true;
         if (a.primaryContainer != null) {
            a.primaryContainer.initPrimaryContainer();
         }

         a.scanClasspath();
         logger.debug("inject() running with {} agents", new Object[]{a.containers.size()});
         Iterator var1 = a.containers.values().iterator();

         while(var1.hasNext()) {
            MixinContainer a = (MixinContainer)var1.next();

            try {
               a.inject();
            } catch (Exception var4) {
               var4.printStackTrace();
            }
         }

      }
   }

   private void scanClasspath() {
      URL[] a = MixinService.getService().getClassProvider().getClassPath();
      URL[] var2 = a;
      int var3 = a.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         URL a = var2[var4];

         try {
            URI a = a.toURI();
            if (!a.containers.containsKey(a)) {
               logger.debug("Scanning {} for mixin tweaker", new Object[]{a});
               if ("file".equals(a.getScheme()) && (new File(a)).exists()) {
                  MainAttributes a = MainAttributes.of(a);
                  String a = a.get("TweakClass");
                  if ("org.spongepowered.asm.launch.MixinTweaker".equals(a)) {
                     logger.debug("{} contains a mixin tweaker, adding agents", new Object[]{a});
                     a.addContainer(a);
                  }
               }
            }
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

   }

   public String getLaunchTarget() {
      Iterator var1 = a.containers.values().iterator();

      String a;
      do {
         if (!var1.hasNext()) {
            return "net.minecraft.client.main.Main";
         }

         MixinContainer a = (MixinContainer)var1.next();
         a = a.getLaunchTarget();
      } while(a == null);

      return a;
   }

   final void setCompatibilityLevel(String a) {
      try {
         MixinEnvironment.CompatibilityLevel a = MixinEnvironment.CompatibilityLevel.valueOf(a.toUpperCase());
         logger.debug("Setting mixin compatibility level: {}", new Object[]{a});
         MixinEnvironment.setCompatibilityLevel(a);
      } catch (IllegalArgumentException var3) {
         logger.warn("Invalid compatibility level specified: {}", new Object[]{a});
      }

   }

   final void addConfig(String a) {
      if (a.endsWith(".json")) {
         logger.debug("Registering mixin config: {}", new Object[]{a});
         Mixins.addConfiguration(a);
      } else if (a.contains(".json@")) {
         int a = a.indexOf(".json@");
         String a = a.substring(a + 6);
         a = a.substring(0, a + 5);
         MixinEnvironment.Phase a = MixinEnvironment.Phase.forName(a);
         if (a != null) {
            logger.warn("Setting config phase via manifest is deprecated: {}. Specify target in config instead", new Object[]{a});
            logger.debug("Registering mixin config: {}", new Object[]{a});
            MixinEnvironment.getEnvironment(a).addConfiguration(a);
         }
      }

   }

   final void addTokenProvider(String a) {
      if (a.contains("@")) {
         String[] a = a.split("@", 2);
         MixinEnvironment.Phase a = MixinEnvironment.Phase.forName(a[1]);
         if (a != null) {
            logger.debug("Registering token provider class: {}", new Object[]{a[0]});
            MixinEnvironment.getEnvironment(a).registerTokenProviderClass(a[0]);
         }

      } else {
         MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(a);
      }
   }
}

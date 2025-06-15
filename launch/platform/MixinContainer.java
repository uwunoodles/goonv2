package org.spongepowered.asm.launch.platform;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.service.MixinService;

public class MixinContainer {
   private static final List<String> agentClasses = new ArrayList();
   private final Logger logger = LogManager.getLogger("mixin");
   private final URI uri;
   private final List<IMixinPlatformAgent> agents = new ArrayList();

   public MixinContainer(MixinPlatformManager a, URI a) {
      a.uri = a;
      Iterator var3 = agentClasses.iterator();

      while(var3.hasNext()) {
         String a = (String)var3.next();

         try {
            Class<IMixinPlatformAgent> a = Class.forName(a);
            Constructor<IMixinPlatformAgent> a = a.getDeclaredConstructor(MixinPlatformManager.class, URI.class);
            a.logger.debug("Instancing new {} for {}", new Object[]{a.getSimpleName(), a.uri});
            IMixinPlatformAgent a = (IMixinPlatformAgent)a.newInstance(a, a);
            a.agents.add(a);
         } catch (Exception var8) {
            a.logger.catching(var8);
         }
      }

   }

   public URI getURI() {
      return a.uri;
   }

   public Collection<String> getPhaseProviders() {
      List<String> a = new ArrayList();
      Iterator var2 = a.agents.iterator();

      while(var2.hasNext()) {
         IMixinPlatformAgent a = (IMixinPlatformAgent)var2.next();
         String a = a.getPhaseProvider();
         if (a != null) {
            a.add(a);
         }
      }

      return a;
   }

   public void prepare() {
      Iterator var1 = a.agents.iterator();

      while(var1.hasNext()) {
         IMixinPlatformAgent a = (IMixinPlatformAgent)var1.next();
         a.logger.debug("Processing prepare() for {}", new Object[]{a});
         a.prepare();
      }

   }

   public void initPrimaryContainer() {
      Iterator var1 = a.agents.iterator();

      while(var1.hasNext()) {
         IMixinPlatformAgent a = (IMixinPlatformAgent)var1.next();
         a.logger.debug("Processing launch tasks for {}", new Object[]{a});
         a.initPrimaryContainer();
      }

   }

   public void inject() {
      Iterator var1 = a.agents.iterator();

      while(var1.hasNext()) {
         IMixinPlatformAgent a = (IMixinPlatformAgent)var1.next();
         a.logger.debug("Processing inject() for {}", new Object[]{a});
         a.inject();
      }

   }

   public String getLaunchTarget() {
      Iterator var1 = a.agents.iterator();

      String a;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         IMixinPlatformAgent a = (IMixinPlatformAgent)var1.next();
         a = a.getLaunchTarget();
      } while(a == null);

      return a;
   }

   static {
      GlobalProperties.put("mixin.agents", agentClasses);
      Iterator var0 = MixinService.getService().getPlatformAgents().iterator();

      while(var0.hasNext()) {
         String a = (String)var0.next();
         agentClasses.add(a);
      }

      agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
   }
}

package org.spongepowered.asm.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MixinService {
   private static final Logger logger = LogManager.getLogger("mixin");
   private static MixinService instance;
   private ServiceLoader<IMixinServiceBootstrap> bootstrapServiceLoader;
   private final Set<String> bootedServices = new HashSet();
   private ServiceLoader<IMixinService> serviceLoader;
   private IMixinService service = null;

   private MixinService() {
      a.runBootServices();
   }

   private void runBootServices() {
      a.bootstrapServiceLoader = ServiceLoader.load(IMixinServiceBootstrap.class, a.getClass().getClassLoader());
      Iterator var1 = a.bootstrapServiceLoader.iterator();

      while(var1.hasNext()) {
         IMixinServiceBootstrap a = (IMixinServiceBootstrap)var1.next();

         try {
            a.bootstrap();
            a.bootedServices.add(a.getServiceClassName());
         } catch (Throwable var4) {
            logger.catching(var4);
         }
      }

   }

   private static MixinService getInstance() {
      if (instance == null) {
         instance = new MixinService();
      }

      return instance;
   }

   public static void boot() {
      getInstance();
   }

   public static IMixinService getService() {
      return getInstance().getServiceInstance();
   }

   private synchronized IMixinService getServiceInstance() {
      if (a.service == null) {
         a.service = a.initService();
         if (a.service == null) {
            throw new ServiceNotAvailableError("No mixin host service is available");
         }
      }

      return a.service;
   }

   private IMixinService initService() {
      a.serviceLoader = ServiceLoader.load(IMixinService.class, a.getClass().getClassLoader());
      Iterator a = a.serviceLoader.iterator();

      while(a.hasNext()) {
         try {
            IMixinService a = (IMixinService)a.next();
            if (a.bootedServices.contains(a.getClass().getName())) {
               logger.debug("MixinService [{}] was successfully booted in {}", new Object[]{a.getName(), a.getClass().getClassLoader()});
            }

            if (a.isValid()) {
               return a;
            }
         } catch (ServiceConfigurationError var3) {
            var3.printStackTrace();
         } catch (Throwable var4) {
            var4.printStackTrace();
         }
      }

      return null;
   }
}

package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;

public final class ObfuscationServices {
   private static ObfuscationServices instance;
   private final ServiceLoader<IObfuscationService> serviceLoader;
   private final Set<IObfuscationService> services = new HashSet();

   private ObfuscationServices() {
      a.serviceLoader = ServiceLoader.load(IObfuscationService.class, a.getClass().getClassLoader());
   }

   public static ObfuscationServices getInstance() {
      if (instance == null) {
         instance = new ObfuscationServices();
      }

      return instance;
   }

   public void initProviders(IMixinAnnotationProcessor a) {
      try {
         Iterator var2 = a.serviceLoader.iterator();

         while(true) {
            String a;
            Collection a;
            do {
               IObfuscationService a;
               do {
                  if (!var2.hasNext()) {
                     return;
                  }

                  a = (IObfuscationService)var2.next();
               } while(a.services.contains(a));

               a.services.add(a);
               a = a.getClass().getSimpleName();
               a = a.getObfuscationTypes();
            } while(a == null);

            Iterator var6 = a.iterator();

            while(var6.hasNext()) {
               ObfuscationTypeDescriptor a = (ObfuscationTypeDescriptor)var6.next();

               try {
                  ObfuscationType a = ObfuscationType.create(a, a);
                  a.printMessage(Kind.NOTE, a + " supports type: \"" + a + "\"");
               } catch (Exception var9) {
                  var9.printStackTrace();
               }
            }
         }
      } catch (ServiceConfigurationError var10) {
         a.printMessage(Kind.ERROR, var10.getClass().getSimpleName() + ": " + var10.getMessage());
         var10.printStackTrace();
      }
   }

   public Set<String> getSupportedOptions() {
      Set<String> a = new HashSet();
      Iterator var2 = a.serviceLoader.iterator();

      while(var2.hasNext()) {
         IObfuscationService a = (IObfuscationService)var2.next();
         Set<String> a = a.getSupportedOptions();
         if (a != null) {
            a.addAll(a);
         }
      }

      return a;
   }

   public IObfuscationService getService(Class<? extends IObfuscationService> a) {
      Iterator var2 = a.serviceLoader.iterator();

      IObfuscationService a;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         a = (IObfuscationService)var2.next();
      } while(!a.getName().equals(a.getClass().getName()));

      return a;
   }
}

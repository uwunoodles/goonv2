package org.spongepowered.asm.service.mojang;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.launchwrapper.LaunchClassLoader;

final class LaunchClassLoaderUtil {
   private static final String CACHED_CLASSES_FIELD = "cachedClasses";
   private static final String INVALID_CLASSES_FIELD = "invalidClasses";
   private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
   private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
   private final LaunchClassLoader classLoader;
   private final Map<String, Class<?>> cachedClasses;
   private final Set<String> invalidClasses;
   private final Set<String> classLoaderExceptions;
   private final Set<String> transformerExceptions;

   LaunchClassLoaderUtil(LaunchClassLoader a) {
      a.classLoader = a;
      a.cachedClasses = (Map)getField(a, "cachedClasses");
      a.invalidClasses = (Set)getField(a, "invalidClasses");
      a.classLoaderExceptions = (Set)getField(a, "classLoaderExceptions");
      a.transformerExceptions = (Set)getField(a, "transformerExceptions");
   }

   LaunchClassLoader getClassLoader() {
      return a.classLoader;
   }

   boolean isClassLoaded(String a) {
      return a.cachedClasses.containsKey(a);
   }

   boolean isClassExcluded(String a, String a) {
      return a.isClassClassLoaderExcluded(a, a) || a.isClassTransformerExcluded(a, a);
   }

   boolean isClassClassLoaderExcluded(String a, String a) {
      Iterator var3 = a.getClassLoaderExceptions().iterator();

      String a;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         a = (String)var3.next();
      } while((a == null || !a.startsWith(a)) && !a.startsWith(a));

      return true;
   }

   boolean isClassTransformerExcluded(String a, String a) {
      Iterator var3 = a.getTransformerExceptions().iterator();

      String a;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         a = (String)var3.next();
      } while((a == null || !a.startsWith(a)) && !a.startsWith(a));

      return true;
   }

   void registerInvalidClass(String a) {
      if (a.invalidClasses != null) {
         a.invalidClasses.add(a);
      }

   }

   Set<String> getClassLoaderExceptions() {
      return a.classLoaderExceptions != null ? a.classLoaderExceptions : Collections.emptySet();
   }

   Set<String> getTransformerExceptions() {
      return a.transformerExceptions != null ? a.transformerExceptions : Collections.emptySet();
   }

   private static <T> T getField(LaunchClassLoader a, String a) {
      try {
         Field a = LaunchClassLoader.class.getDeclaredField(a);
         a.setAccessible(true);
         return a.get(a);
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }
}

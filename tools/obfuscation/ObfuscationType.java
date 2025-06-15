package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;

public final class ObfuscationType {
   private static final Map<String, ObfuscationType> types = new LinkedHashMap();
   private final String key;
   private final ObfuscationTypeDescriptor descriptor;
   private final IMixinAnnotationProcessor ap;
   private final IOptionProvider options;

   private ObfuscationType(ObfuscationTypeDescriptor a, IMixinAnnotationProcessor a) {
      a.key = a.getKey();
      a.descriptor = a;
      a.ap = a;
      a.options = a;
   }

   public final ObfuscationEnvironment createEnvironment() {
      try {
         Class<? extends ObfuscationEnvironment> a = a.descriptor.getEnvironmentType();
         Constructor<? extends ObfuscationEnvironment> a = a.getDeclaredConstructor(ObfuscationType.class);
         a.setAccessible(true);
         return (ObfuscationEnvironment)a.newInstance(a);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public String toString() {
      return a.key;
   }

   public String getKey() {
      return a.key;
   }

   public ObfuscationTypeDescriptor getConfig() {
      return a.descriptor;
   }

   public IMixinAnnotationProcessor getAnnotationProcessor() {
      return a.ap;
   }

   public boolean isDefault() {
      String a = a.options.getOption("defaultObfuscationEnv");
      return a == null && a.key.equals("searge") || a != null && a.key.equals(a.toLowerCase());
   }

   public boolean isSupported() {
      return a.getInputFileNames().size() > 0;
   }

   public List<String> getInputFileNames() {
      Builder<String> a = ImmutableList.builder();
      String a = a.options.getOption(a.descriptor.getInputFileOption());
      if (a != null) {
         a.add(a);
      }

      String a = a.options.getOption(a.descriptor.getExtraInputFilesOption());
      if (a != null) {
         String[] var4 = a.split(";");
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String a = var4[var6];
            a.add(a.trim());
         }
      }

      return a.build();
   }

   public String getOutputFileName() {
      return a.options.getOption(a.descriptor.getOutputFileOption());
   }

   public static Iterable<ObfuscationType> types() {
      return types.values();
   }

   public static ObfuscationType create(ObfuscationTypeDescriptor a, IMixinAnnotationProcessor a) {
      String a = a.getKey();
      if (types.containsKey(a)) {
         throw new IllegalArgumentException("Obfuscation type with key " + a + " was already registered");
      } else {
         ObfuscationType a = new ObfuscationType(a, a);
         types.put(a, a);
         return a;
      }
   }

   public static ObfuscationType get(String a) {
      ObfuscationType a = (ObfuscationType)types.get(a);
      if (a == null) {
         throw new IllegalArgumentException("Obfuscation type with key " + a + " was not registered");
      } else {
         return a;
      }
   }
}

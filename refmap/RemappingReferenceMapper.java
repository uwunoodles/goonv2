package org.spongepowered.asm.mixin.refmap;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;

public final class RemappingReferenceMapper implements IReferenceMapper {
   private static final String DEFAULT_RESOURCE_PATH_PROPERTY = "net.minecraftforge.gradle.GradleStart.srg.srg-mcp";
   private static final String DEFAULT_MAPPING_ENV = "searge";
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final Map<String, Map<String, String>> srgs = new HashMap();
   private final IReferenceMapper refMap;
   private final Map<String, String> mappings;
   private final Map<String, Map<String, String>> cache = new HashMap();

   private RemappingReferenceMapper(MixinEnvironment a, IReferenceMapper a) {
      a.refMap = a;
      a.refMap.setContext(getMappingEnv(a));
      String a = getResource(a);
      a.mappings = loadSrgs(a);
      logger.info("Remapping refMap {} using {}", new Object[]{a.getResourceName(), a});
   }

   public boolean isDefault() {
      return a.refMap.isDefault();
   }

   public String getResourceName() {
      return a.refMap.getResourceName();
   }

   public String getStatus() {
      return a.refMap.getStatus();
   }

   public String getContext() {
      return a.refMap.getContext();
   }

   public void setContext(String a1) {
   }

   public String remap(String a, String a) {
      Map<String, String> a = a.getCache(a);
      String a = (String)a.get(a);
      if (a == null) {
         a = a.refMap.remap(a, a);

         Entry a;
         for(Iterator var5 = a.mappings.entrySet().iterator(); var5.hasNext(); a = a.replace((CharSequence)a.getKey(), (CharSequence)a.getValue())) {
            a = (Entry)var5.next();
         }

         a.put(a, a);
      }

      return a;
   }

   private Map<String, String> getCache(String a) {
      Map<String, String> a = (Map)a.cache.get(a);
      if (a == null) {
         a = new HashMap();
         a.cache.put(a, a);
      }

      return (Map)a;
   }

   public String remapWithContext(String a, String a, String a) {
      return a.refMap.remapWithContext(a, a, a);
   }

   private static Map<String, String> loadSrgs(String a) {
      if (srgs.containsKey(a)) {
         return (Map)srgs.get(a);
      } else {
         final Map<String, String> a = new HashMap();
         srgs.put(a, a);
         File a = new File(a);
         if (!a.isFile()) {
            return a;
         } else {
            try {
               Files.readLines(a, Charsets.UTF_8, new LineProcessor<Object>() {
                  public Object getResult() {
                     return null;
                  }

                  public boolean processLine(String axx) throws IOException {
                     if (!Strings.isNullOrEmpty(axx) && !axx.startsWith("#")) {
                        int axxx = 0;
                        int axxxx = false;
                        int axxxxxx;
                        if ((axxxxxx = axx.startsWith("MD: ") ? 2 : (axx.startsWith("FD: ") ? 1 : 0)) > 0) {
                           String[] axxxxx = axx.substring(4).split(" ", 4);
                           a.put(axxxxx[axxx].substring(axxxxx[axxx].lastIndexOf(47) + 1), axxxxx[axxxxxx].substring(axxxxx[axxxxxx].lastIndexOf(47) + 1));
                        }

                        return true;
                     } else {
                        return true;
                     }
                  }
               });
            } catch (IOException var4) {
               logger.warn("Could not read input SRG file: {}", new Object[]{a});
               logger.catching(var4);
            }

            return a;
         }
      }
   }

   public static IReferenceMapper of(MixinEnvironment a, IReferenceMapper a) {
      return (IReferenceMapper)(!a.isDefault() && hasData(a) ? new RemappingReferenceMapper(a, a) : a);
   }

   private static boolean hasData(MixinEnvironment a) {
      String a = getResource(a);
      return a != null && (new File(a)).exists();
   }

   private static String getResource(MixinEnvironment a) {
      String a = a.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_RESOURCE);
      return Strings.isNullOrEmpty(a) ? System.getProperty("net.minecraftforge.gradle.GradleStart.srg.srg-mcp") : a;
   }

   private static String getMappingEnv(MixinEnvironment a) {
      String a = a.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_SOURCE_ENV);
      return Strings.isNullOrEmpty(a) ? "searge" : a;
   }
}

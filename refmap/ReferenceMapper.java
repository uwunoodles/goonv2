package org.spongepowered.asm.mixin.refmap;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

public final class ReferenceMapper implements IReferenceMapper, Serializable {
   private static final long serialVersionUID = 2L;
   public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
   public static final ReferenceMapper DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
   private final Map<String, Map<String, String>> mappings;
   private final Map<String, Map<String, Map<String, String>>> data;
   private final transient boolean readOnly;
   private transient String context;
   private transient String resource;

   public ReferenceMapper() {
      this(false, "mixin.refmap.json");
   }

   private ReferenceMapper(boolean a, String a) {
      a.mappings = Maps.newHashMap();
      a.data = Maps.newHashMap();
      a.context = null;
      a.readOnly = a;
      a.resource = a;
   }

   public boolean isDefault() {
      return a.readOnly;
   }

   private void setResourceName(String a) {
      if (!a.readOnly) {
         a.resource = a != null ? a : "<unknown resource>";
      }

   }

   public String getResourceName() {
      return a.resource;
   }

   public String getStatus() {
      return a.isDefault() ? "No refMap loaded." : "Using refmap " + a.getResourceName();
   }

   public String getContext() {
      return a.context;
   }

   public void setContext(String a) {
      a.context = a;
   }

   public String remap(String a, String a) {
      return a.remapWithContext(a.context, a, a);
   }

   public String remapWithContext(String a, String a, String a) {
      Map<String, Map<String, String>> a = a.mappings;
      if (a != null) {
         a = (Map)a.data.get(a);
         if (a == null) {
            a = a.mappings;
         }
      }

      return a.remap(a, a, a);
   }

   private String remap(Map<String, Map<String, String>> a, String a, String a) {
      if (a == null) {
         Iterator var4 = a.values().iterator();

         while(var4.hasNext()) {
            Map<String, String> a = (Map)var4.next();
            if (a.containsKey(a)) {
               return (String)a.get(a);
            }
         }
      }

      Map<String, String> a = (Map)a.get(a);
      if (a == null) {
         return a;
      } else {
         String a = (String)a.get(a);
         return a != null ? a : a;
      }
   }

   public String addMapping(String a, String a, String a, String a) {
      if (!a.readOnly && a != null && a != null && !a.equals(a)) {
         Map<String, Map<String, String>> a = a.mappings;
         if (a != null) {
            a = (Map)a.data.get(a);
            if (a == null) {
               a = Maps.newHashMap();
               a.data.put(a, a);
            }
         }

         Map<String, String> a = (Map)((Map)a).get(a);
         if (a == null) {
            a = new HashMap();
            ((Map)a).put(a, a);
         }

         return (String)((Map)a).put(a, a);
      } else {
         return null;
      }
   }

   public void write(Appendable a) {
      (new GsonBuilder()).setPrettyPrinting().create().toJson(a, a);
   }

   public static ReferenceMapper read(String a) {
      Logger a = LogManager.getLogger("mixin");
      InputStreamReader a = null;

      try {
         IMixinService a = MixinService.getService();
         InputStream a = a.getResourceAsStream(a);
         if (a != null) {
            a = new InputStreamReader(a);
            ReferenceMapper a = readJson(a);
            a.setResourceName(a);
            ReferenceMapper var6 = a;
            return var6;
         }
      } catch (JsonParseException var11) {
         a.error("Invalid REFMAP JSON in " + a + ": " + var11.getClass().getName() + " " + var11.getMessage());
      } catch (Exception var12) {
         a.error("Failed reading REFMAP JSON from " + a + ": " + var12.getClass().getName() + " " + var12.getMessage());
      } finally {
         IOUtils.closeQuietly(a);
      }

      return DEFAULT_MAPPER;
   }

   public static ReferenceMapper read(Reader a, String a) {
      try {
         ReferenceMapper a = readJson(a);
         a.setResourceName(a);
         return a;
      } catch (Exception var3) {
         return DEFAULT_MAPPER;
      }
   }

   private static ReferenceMapper readJson(Reader a) {
      return (ReferenceMapper)(new Gson()).fromJson(a, ReferenceMapper.class);
   }
}

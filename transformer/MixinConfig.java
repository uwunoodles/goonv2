package org.spongepowered.asm.mixin.transformer;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinInitialisationError;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.refmap.RemappingReferenceMapper;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.VersionNumber;

final class MixinConfig implements Comparable<MixinConfig>, IMixinConfig {
   private static int configOrder = 0;
   private static final Set<String> globalMixinList = new HashSet();
   private final Logger logger = LogManager.getLogger("mixin");
   private final transient Map<String, List<MixinInfo>> mixinMapping = new HashMap();
   private final transient Set<String> unhandledTargets = new HashSet();
   private final transient List<MixinInfo> mixins = new ArrayList();
   private transient Config handle;
   @SerializedName("target")
   private String selector;
   @SerializedName("minVersion")
   private String version;
   @SerializedName("compatibilityLevel")
   private String compatibility;
   @SerializedName("required")
   private boolean required;
   @SerializedName("priority")
   private int priority = 1000;
   @SerializedName("mixinPriority")
   private int mixinPriority = 1000;
   @SerializedName("package")
   private String mixinPackage;
   @SerializedName("mixins")
   private List<String> mixinClasses;
   @SerializedName("client")
   private List<String> mixinClassesClient;
   @SerializedName("server")
   private List<String> mixinClassesServer;
   @SerializedName("setSourceFile")
   private boolean setSourceFile = false;
   @SerializedName("refmap")
   private String refMapperConfig;
   @SerializedName("verbose")
   private boolean verboseLogging;
   private final transient int order;
   private final transient List<MixinConfig.IListener> listeners;
   private transient IMixinService service;
   private transient MixinEnvironment env;
   private transient String name;
   @SerializedName("plugin")
   private String pluginClassName;
   @SerializedName("injectors")
   private MixinConfig.InjectorOptions injectorOptions;
   @SerializedName("overwrites")
   private MixinConfig.OverwriteOptions overwriteOptions;
   private transient IMixinConfigPlugin plugin;
   private transient IReferenceMapper refMapper;
   private transient boolean prepared;
   private transient boolean visited;

   private MixinConfig() {
      a.order = configOrder++;
      a.listeners = new ArrayList();
      a.injectorOptions = new MixinConfig.InjectorOptions();
      a.overwriteOptions = new MixinConfig.OverwriteOptions();
      a.prepared = false;
      a.visited = false;
   }

   private boolean onLoad(IMixinService a, String a, MixinEnvironment a) {
      a.service = a;
      a.name = a;
      a.env = a.parseSelector(a.selector, a);
      a.required &= !a.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED);
      a.initCompatibilityLevel();
      a.initInjectionPoints();
      return a.checkVersion();
   }

   private void initCompatibilityLevel() {
      if (a.compatibility != null) {
         MixinEnvironment.CompatibilityLevel a = MixinEnvironment.CompatibilityLevel.valueOf(a.compatibility.trim().toUpperCase());
         MixinEnvironment.CompatibilityLevel a = MixinEnvironment.getCompatibilityLevel();
         if (a != a) {
            if (a.isAtLeast(a) && !a.canSupport(a)) {
               throw new MixinInitialisationError("Mixin config " + a.name + " requires compatibility level " + a + " which is too old");
            } else if (!a.canElevateTo(a)) {
               throw new MixinInitialisationError("Mixin config " + a.name + " requires compatibility level " + a + " which is prohibited by " + a);
            } else {
               MixinEnvironment.setCompatibilityLevel(a);
            }
         }
      }
   }

   private MixinEnvironment parseSelector(String a, MixinEnvironment a) {
      if (a != null) {
         String[] a = a.split("[&\\| ]");
         String[] var4 = a;
         int var5 = a.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String a = var4[var6];
            a = a.trim();
            Pattern a = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$");
            Matcher a = a.matcher(a);
            if (a.matches()) {
               return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.forName(a.group(1)));
            }
         }

         MixinEnvironment.Phase a = MixinEnvironment.Phase.forName(a);
         if (a != null) {
            return MixinEnvironment.getEnvironment(a);
         }
      }

      return a;
   }

   private void initInjectionPoints() {
      if (a.injectorOptions.injectionPoints != null) {
         Iterator var1 = a.injectorOptions.injectionPoints.iterator();

         while(var1.hasNext()) {
            String a = (String)var1.next();

            try {
               Class<?> a = a.service.getClassProvider().findClass(a, true);
               if (InjectionPoint.class.isAssignableFrom(a)) {
                  InjectionPoint.register(a);
               } else {
                  a.logger.error("Unable to register injection point {} for {}, class must extend InjectionPoint", new Object[]{a, a});
               }
            } catch (Throwable var4) {
               a.logger.catching(var4);
            }
         }

      }
   }

   private boolean checkVersion() throws MixinInitialisationError {
      if (a.version == null) {
         a.logger.error("Mixin config {} does not specify \"minVersion\" property", new Object[]{a.name});
      }

      VersionNumber a = VersionNumber.parse(a.version);
      VersionNumber a = VersionNumber.parse(a.env.getVersion());
      if (a.compareTo(a) > 0) {
         a.logger.warn("Mixin config {} requires mixin subsystem version {} but {} was found. The mixin config will not be applied.", new Object[]{a.name, a, a});
         if (a.required) {
            throw new MixinInitialisationError("Required mixin config " + a.name + " requires mixin subsystem version " + a);
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   void addListener(MixinConfig.IListener a) {
      a.listeners.add(a);
   }

   void onSelect() {
      if (a.pluginClassName != null) {
         try {
            Class<?> a = a.service.getClassProvider().findClass(a.pluginClassName, true);
            a.plugin = (IMixinConfigPlugin)a.newInstance();
            if (a.plugin != null) {
               a.plugin.onLoad(a.mixinPackage);
            }
         } catch (Throwable var2) {
            var2.printStackTrace();
            a.plugin = null;
         }
      }

      if (!a.mixinPackage.endsWith(".")) {
         a.mixinPackage = a.mixinPackage + ".";
      }

      boolean a = false;
      if (a.refMapperConfig == null) {
         if (a.plugin != null) {
            a.refMapperConfig = a.plugin.getRefMapperConfig();
         }

         if (a.refMapperConfig == null) {
            a = true;
            a.refMapperConfig = "mixin.refmap.json";
         }
      }

      a.refMapper = ReferenceMapper.read(a.refMapperConfig);
      a.verboseLogging |= a.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
      if (!a && a.refMapper.isDefault() && !a.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
         a.logger.warn("Reference map '{}' for {} could not be read. If this is a development environment you can ignore this message", new Object[]{a.refMapperConfig, a});
      }

      if (a.env.getOption(MixinEnvironment.Option.REFMAP_REMAP)) {
         a.refMapper = RemappingReferenceMapper.of(a.env, a.refMapper);
      }

   }

   void prepare() {
      if (!a.prepared) {
         a.prepared = true;
         a.prepareMixins(a.mixinClasses, false);
         switch(a.env.getSide()) {
         case CLIENT:
            a.prepareMixins(a.mixinClassesClient, false);
            break;
         case SERVER:
            a.prepareMixins(a.mixinClassesServer, false);
            break;
         case UNKNOWN:
         default:
            a.logger.warn("Mixin environment was unable to detect the current side, sided mixins will not be applied");
         }

      }
   }

   void postInitialise() {
      if (a.plugin != null) {
         List<String> a = a.plugin.getMixins();
         a.prepareMixins(a, true);
      }

      Iterator a = a.mixins.iterator();

      while(a.hasNext()) {
         MixinInfo a = (MixinInfo)a.next();

         try {
            a.validate();
            Iterator var3 = a.listeners.iterator();

            while(var3.hasNext()) {
               MixinConfig.IListener a = (MixinConfig.IListener)var3.next();
               a.onInit(a);
            }
         } catch (InvalidMixinException var5) {
            a.logger.error(var5.getMixin() + ": " + var5.getMessage(), var5);
            a.removeMixin(a);
            a.remove();
         } catch (Exception var6) {
            a.logger.error(var6.getMessage(), var6);
            a.removeMixin(a);
            a.remove();
         }
      }

   }

   private void removeMixin(MixinInfo a) {
      Iterator var2 = a.mixinMapping.values().iterator();

      while(var2.hasNext()) {
         List<MixinInfo> a = (List)var2.next();
         Iterator a = a.iterator();

         while(a.hasNext()) {
            if (a == a.next()) {
               a.remove();
            }
         }
      }

   }

   private void prepareMixins(List<String> a, boolean a) {
      if (a != null) {
         Iterator var3 = a.iterator();

         while(true) {
            String a;
            String a;
            do {
               do {
                  if (!var3.hasNext()) {
                     return;
                  }

                  a = (String)var3.next();
                  a = a.mixinPackage + a;
               } while(a == null);
            } while(globalMixinList.contains(a));

            MixinInfo a = null;

            try {
               a = new MixinInfo(a.service, a, a, true, a.plugin, a);
               if (a.getTargetClasses().size() > 0) {
                  globalMixinList.add(a);
                  Iterator var7 = a.getTargetClasses().iterator();

                  while(var7.hasNext()) {
                     String a = (String)var7.next();
                     String a = a.replace('/', '.');
                     a.mixinsFor(a).add(a);
                     a.unhandledTargets.add(a);
                  }

                  var7 = a.listeners.iterator();

                  while(var7.hasNext()) {
                     MixinConfig.IListener a = (MixinConfig.IListener)var7.next();
                     a.onPrepare(a);
                  }

                  a.mixins.add(a);
               }
            } catch (InvalidMixinException var10) {
               if (a.required) {
                  throw var10;
               }

               a.logger.error(var10.getMessage(), var10);
            } catch (Exception var11) {
               if (a.required) {
                  throw new InvalidMixinException(a, "Error initialising mixin " + a + " - " + var11.getClass() + ": " + var11.getMessage(), var11);
               }

               a.logger.error(var11.getMessage(), var11);
            }
         }
      }
   }

   void postApply(String a, ClassNode a2) {
      a.unhandledTargets.remove(a);
   }

   public Config getHandle() {
      if (a.handle == null) {
         a.handle = new Config(a);
      }

      return a.handle;
   }

   public boolean isRequired() {
      return a.required;
   }

   public MixinEnvironment getEnvironment() {
      return a.env;
   }

   public String getName() {
      return a.name;
   }

   public String getMixinPackage() {
      return a.mixinPackage;
   }

   public int getPriority() {
      return a.priority;
   }

   public int getDefaultMixinPriority() {
      return a.mixinPriority;
   }

   public int getDefaultRequiredInjections() {
      return a.injectorOptions.defaultRequireValue;
   }

   public String getDefaultInjectorGroup() {
      String a = a.injectorOptions.defaultGroup;
      return a != null && !a.isEmpty() ? a : "default";
   }

   public boolean conformOverwriteVisibility() {
      return a.overwriteOptions.conformAccessModifiers;
   }

   public boolean requireOverwriteAnnotations() {
      return a.overwriteOptions.requireOverwriteAnnotations;
   }

   public int getMaxShiftByValue() {
      return Math.min(Math.max(a.injectorOptions.maxShiftBy, 0), 5);
   }

   public boolean select(MixinEnvironment a) {
      a.visited = true;
      return a.env == a;
   }

   boolean isVisited() {
      return a.visited;
   }

   int getDeclaredMixinCount() {
      return getCollectionSize(a.mixinClasses, a.mixinClassesClient, a.mixinClassesServer);
   }

   int getMixinCount() {
      return a.mixins.size();
   }

   public List<String> getClasses() {
      return Collections.unmodifiableList(a.mixinClasses);
   }

   public boolean shouldSetSourceFile() {
      return a.setSourceFile;
   }

   public IReferenceMapper getReferenceMapper() {
      if (a.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
         return ReferenceMapper.DEFAULT_MAPPER;
      } else {
         a.refMapper.setContext(a.env.getRefmapObfuscationContext());
         return a.refMapper;
      }
   }

   String remapClassName(String a, String a) {
      return a.getReferenceMapper().remap(a, a);
   }

   public IMixinConfigPlugin getPlugin() {
      return a.plugin;
   }

   public Set<String> getTargets() {
      return Collections.unmodifiableSet(a.mixinMapping.keySet());
   }

   public Set<String> getUnhandledTargets() {
      return Collections.unmodifiableSet(a.unhandledTargets);
   }

   public Level getLoggingLevel() {
      return a.verboseLogging ? Level.INFO : Level.DEBUG;
   }

   public boolean packageMatch(String a) {
      return a.startsWith(a.mixinPackage);
   }

   public boolean hasMixinsFor(String a) {
      return a.mixinMapping.containsKey(a);
   }

   public List<MixinInfo> getMixinsFor(String a) {
      return a.mixinsFor(a);
   }

   private List<MixinInfo> mixinsFor(String a) {
      List<MixinInfo> a = (List)a.mixinMapping.get(a);
      if (a == null) {
         a = new ArrayList();
         a.mixinMapping.put(a, a);
      }

      return (List)a;
   }

   public List<String> reloadMixin(String a, byte[] a) {
      Iterator a = a.mixins.iterator();

      MixinInfo a;
      do {
         if (!a.hasNext()) {
            return Collections.emptyList();
         }

         a = (MixinInfo)a.next();
      } while(!a.getClassName().equals(a));

      a.reloadMixin(a);
      return a.getTargetClasses();
   }

   public String toString() {
      return a.name;
   }

   public int compareTo(MixinConfig a) {
      if (a == null) {
         return 0;
      } else {
         return a.priority == a.priority ? a.order - a.order : a.priority - a.priority;
      }
   }

   static Config create(String a, MixinEnvironment a) {
      try {
         IMixinService a = MixinService.getService();
         MixinConfig a = (MixinConfig)(new Gson()).fromJson(new InputStreamReader(a.getResourceAsStream(a)), MixinConfig.class);
         return a.onLoad(a, a, a) ? a.getHandle() : null;
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", a), var4);
      }
   }

   private static int getCollectionSize(Collection<?>... a) {
      int a = 0;
      Collection[] var2 = a;
      int var3 = a.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Collection<?> a = var2[var4];
         if (a != null) {
            a += a.size();
         }
      }

      return a;
   }

   interface IListener {
      void onPrepare(MixinInfo var1);

      void onInit(MixinInfo var1);
   }

   static class OverwriteOptions {
      @SerializedName("conformVisibility")
      boolean conformAccessModifiers;
      @SerializedName("requireAnnotations")
      boolean requireOverwriteAnnotations;
   }

   static class InjectorOptions {
      @SerializedName("defaultRequire")
      int defaultRequireValue = 0;
      @SerializedName("defaultGroup")
      String defaultGroup = "default";
      @SerializedName("injectionPoints")
      List<String> injectionPoints;
      @SerializedName("maxShiftBy")
      int maxShiftBy = 0;
   }
}

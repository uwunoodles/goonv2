package org.spongepowered.asm.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.JavaVersion;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.perf.Profiler;

public final class MixinEnvironment implements ITokenProvider {
   private static final Set<String> excludeTransformers = Sets.newHashSet(new String[]{"net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer"});
   private static MixinEnvironment currentEnvironment;
   private static MixinEnvironment.Phase currentPhase;
   private static MixinEnvironment.CompatibilityLevel compatibility;
   private static boolean showHeader;
   private static final Logger logger;
   private static final Profiler profiler;
   private final IMixinService service = MixinService.getService();
   private final MixinEnvironment.Phase phase;
   private final String configsKey;
   private final boolean[] options;
   private final Set<String> tokenProviderClasses = new HashSet();
   private final List<MixinEnvironment.TokenProviderWrapper> tokenProviders = new ArrayList();
   private final Map<String, Integer> internalTokens = new HashMap();
   private final RemapperChain remappers = new RemapperChain();
   private MixinEnvironment.Side side;
   private List<ILegacyClassTransformer> transformers;
   private String obfuscationContext = null;

   MixinEnvironment(MixinEnvironment.Phase a) {
      a.phase = a;
      a.configsKey = "mixin.configs." + a.phase.name.toLowerCase();
      Object a = a.getVersion();
      if (a != null && "0.7.11".equals(a)) {
         a.service.checkEnv(a);
         a.options = new boolean[MixinEnvironment.Option.values().length];
         MixinEnvironment.Option[] var3 = MixinEnvironment.Option.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MixinEnvironment.Option a = var3[var5];
            a.options[a.ordinal()] = a.getBooleanValue();
         }

         if (showHeader) {
            showHeader = false;
            a.printHeader(a);
         }

      } else {
         throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
      }
   }

   private void printHeader(Object a) {
      String a = a.getCodeSource();
      String a = a.service.getName();
      MixinEnvironment.Side a = a.getSide();
      logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[]{a, a, a, a});
      boolean a = a.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
      if (a || a.getOption(MixinEnvironment.Option.DEBUG_EXPORT) || a.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
         PrettyPrinter a = new PrettyPrinter(32);
         a.add("SpongePowered MIXIN%s", a ? " (Verbose debugging enabled)" : "").centre().hr();
         a.kv("Code source", a);
         a.kv("Internal Version", a);
         a.kv("Java 8 Supported", MixinEnvironment.CompatibilityLevel.JAVA_8.isSupported()).hr();
         a.kv("Service Name", a);
         a.kv("Service Class", a.service.getClass().getName()).hr();
         MixinEnvironment.Option[] var7 = MixinEnvironment.Option.values();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            MixinEnvironment.Option a = var7[var9];
            StringBuilder a = new StringBuilder();

            for(int a = 0; a < a.depth; ++a) {
               a.append("- ");
            }

            a.kv(a.property, "%s<%s>", a, a);
         }

         a.hr().kv("Detected Side", a);
         a.print(System.err);
      }

   }

   private String getCodeSource() {
      try {
         return a.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
      } catch (Throwable var2) {
         return "Unknown";
      }
   }

   public MixinEnvironment.Phase getPhase() {
      return a.phase;
   }

   /** @deprecated */
   @Deprecated
   public List<String> getMixinConfigs() {
      List<String> a = (List)GlobalProperties.get(a.configsKey);
      if (a == null) {
         a = new ArrayList();
         GlobalProperties.put(a.configsKey, a);
      }

      return (List)a;
   }

   /** @deprecated */
   @Deprecated
   public MixinEnvironment addConfiguration(String a) {
      logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
      Mixins.addConfiguration(a, a);
      return a;
   }

   void registerConfig(String a) {
      List<String> a = a.getMixinConfigs();
      if (!a.contains(a)) {
         a.add(a);
      }

   }

   /** @deprecated */
   @Deprecated
   public MixinEnvironment registerErrorHandlerClass(String a) {
      Mixins.registerErrorHandlerClass(a);
      return a;
   }

   public MixinEnvironment registerTokenProviderClass(String a) {
      if (!a.tokenProviderClasses.contains(a)) {
         try {
            Class<? extends IEnvironmentTokenProvider> a = a.service.getClassProvider().findClass(a, true);
            IEnvironmentTokenProvider a = (IEnvironmentTokenProvider)a.newInstance();
            a.registerTokenProvider(a);
         } catch (Throwable var4) {
            logger.error("Error instantiating " + a, var4);
         }
      }

      return a;
   }

   public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider a) {
      if (a != null && !a.tokenProviderClasses.contains(a.getClass().getName())) {
         String a = a.getClass().getName();
         MixinEnvironment.TokenProviderWrapper a = new MixinEnvironment.TokenProviderWrapper(a, a);
         logger.info("Adding new token provider {} to {}", new Object[]{a, a});
         a.tokenProviders.add(a);
         a.tokenProviderClasses.add(a);
         Collections.sort(a.tokenProviders);
      }

      return a;
   }

   public Integer getToken(String a) {
      a = a.toUpperCase();
      Iterator var2 = a.tokenProviders.iterator();

      Integer a;
      do {
         if (!var2.hasNext()) {
            return (Integer)a.internalTokens.get(a);
         }

         MixinEnvironment.TokenProviderWrapper a = (MixinEnvironment.TokenProviderWrapper)var2.next();
         a = a.getToken(a);
      } while(a == null);

      return a;
   }

   /** @deprecated */
   @Deprecated
   public Set<String> getErrorHandlerClasses() {
      return Mixins.getErrorHandlerClasses();
   }

   public Object getActiveTransformer() {
      return GlobalProperties.get("mixin.transformer");
   }

   public void setActiveTransformer(ITransformer a) {
      if (a != null) {
         GlobalProperties.put("mixin.transformer", a);
      }

   }

   public MixinEnvironment setSide(MixinEnvironment.Side a) {
      if (a != null && a.getSide() == MixinEnvironment.Side.UNKNOWN && a != MixinEnvironment.Side.UNKNOWN) {
         a.side = a;
      }

      return a;
   }

   public MixinEnvironment.Side getSide() {
      if (a.side == null) {
         MixinEnvironment.Side[] var1 = MixinEnvironment.Side.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            MixinEnvironment.Side a = var1[var3];
            if (a.detect()) {
               a.side = a;
               break;
            }
         }
      }

      return a.side != null ? a.side : MixinEnvironment.Side.UNKNOWN;
   }

   public String getVersion() {
      return (String)GlobalProperties.get("mixin.initialised");
   }

   public boolean getOption(MixinEnvironment.Option a) {
      return a.options[a.ordinal()];
   }

   public void setOption(MixinEnvironment.Option a, boolean a) {
      a.options[a.ordinal()] = a;
   }

   public String getOptionValue(MixinEnvironment.Option a) {
      return a.getStringValue();
   }

   public <E extends Enum<E>> E getOption(MixinEnvironment.Option a, E a) {
      return a.getEnumValue(a);
   }

   public void setObfuscationContext(String a) {
      a.obfuscationContext = a;
   }

   public String getObfuscationContext() {
      return a.obfuscationContext;
   }

   public String getRefmapObfuscationContext() {
      String a = MixinEnvironment.Option.OBFUSCATION_TYPE.getStringValue();
      return a != null ? a : a.obfuscationContext;
   }

   public RemapperChain getRemappers() {
      return a.remappers;
   }

   public void audit() {
      Object a = a.getActiveTransformer();
      if (a instanceof MixinTransformer) {
         MixinTransformer a = (MixinTransformer)a;
         a.audit(a);
      }

   }

   public List<ILegacyClassTransformer> getTransformers() {
      if (a.transformers == null) {
         a.buildTransformerDelegationList();
      }

      return Collections.unmodifiableList(a.transformers);
   }

   public void addTransformerExclusion(String a) {
      excludeTransformers.add(a);
      a.transformers = null;
   }

   private void buildTransformerDelegationList() {
      logger.debug("Rebuilding transformer delegation list:");
      a.transformers = new ArrayList();
      Iterator var1 = a.service.getTransformers().iterator();

      while(true) {
         while(true) {
            ITransformer a;
            do {
               if (!var1.hasNext()) {
                  logger.debug("Transformer delegation list created with {} entries", new Object[]{a.transformers.size()});
                  return;
               }

               a = (ITransformer)var1.next();
            } while(!(a instanceof ILegacyClassTransformer));

            ILegacyClassTransformer a = (ILegacyClassTransformer)a;
            String a = a.getName();
            boolean a = true;
            Iterator var6 = excludeTransformers.iterator();

            while(var6.hasNext()) {
               String a = (String)var6.next();
               if (a.contains(a)) {
                  a = false;
                  break;
               }
            }

            if (a && !a.isDelegationExcluded()) {
               logger.debug("  Adding:    {}", new Object[]{a});
               a.transformers.add(a);
            } else {
               logger.debug("  Excluding: {}", new Object[]{a});
            }
         }
      }
   }

   public String toString() {
      return String.format("%s[%s]", a.getClass().getSimpleName(), a.phase);
   }

   private static MixinEnvironment.Phase getCurrentPhase() {
      if (currentPhase == MixinEnvironment.Phase.NOT_INITIALISED) {
         init(MixinEnvironment.Phase.PREINIT);
      }

      return currentPhase;
   }

   public static void init(MixinEnvironment.Phase a) {
      if (currentPhase == MixinEnvironment.Phase.NOT_INITIALISED) {
         currentPhase = a;
         MixinEnvironment a = getEnvironment(a);
         getProfiler().setActive(a.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
         MixinEnvironment.MixinLogWatcher.begin();
      }

   }

   public static MixinEnvironment getEnvironment(MixinEnvironment.Phase a) {
      return a == null ? MixinEnvironment.Phase.DEFAULT.getEnvironment() : a.getEnvironment();
   }

   public static MixinEnvironment getDefaultEnvironment() {
      return getEnvironment(MixinEnvironment.Phase.DEFAULT);
   }

   public static MixinEnvironment getCurrentEnvironment() {
      if (currentEnvironment == null) {
         currentEnvironment = getEnvironment(getCurrentPhase());
      }

      return currentEnvironment;
   }

   public static MixinEnvironment.CompatibilityLevel getCompatibilityLevel() {
      return compatibility;
   }

   /** @deprecated */
   @Deprecated
   public static void setCompatibilityLevel(MixinEnvironment.CompatibilityLevel a) throws IllegalArgumentException {
      StackTraceElement[] a = Thread.currentThread().getStackTrace();
      if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(a[2].getClassName())) {
         logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
      }

      if (a != compatibility && a.isAtLeast(compatibility)) {
         if (!a.isSupported()) {
            throw new IllegalArgumentException("The requested compatibility level " + a + " could not be set. Level is not supported");
         }

         compatibility = a;
         logger.info("Compatibility level set to {}", new Object[]{a});
      }

   }

   public static Profiler getProfiler() {
      return profiler;
   }

   static void gotoPhase(MixinEnvironment.Phase a) {
      if (a != null && a.ordinal >= 0) {
         if (a.ordinal > getCurrentPhase().ordinal) {
            MixinService.getService().beginPhase();
         }

         if (a == MixinEnvironment.Phase.DEFAULT) {
            MixinEnvironment.MixinLogWatcher.end();
         }

         currentPhase = a;
         currentEnvironment = getEnvironment(getCurrentPhase());
      } else {
         throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
      }
   }

   static {
      currentPhase = MixinEnvironment.Phase.NOT_INITIALISED;
      compatibility = (MixinEnvironment.CompatibilityLevel)MixinEnvironment.Option.DEFAULT_COMPATIBILITY_LEVEL.getEnumValue(MixinEnvironment.CompatibilityLevel.JAVA_6);
      showHeader = true;
      logger = LogManager.getLogger("mixin");
      profiler = new Profiler();
   }

   static class MixinLogWatcher {
      static MixinEnvironment.MixinLogWatcher.MixinAppender appender = new MixinEnvironment.MixinLogWatcher.MixinAppender();
      static org.apache.logging.log4j.core.Logger log;
      static Level oldLevel = null;

      static void begin() {
         Logger a = LogManager.getLogger("FML");
         if (a instanceof org.apache.logging.log4j.core.Logger) {
            log = (org.apache.logging.log4j.core.Logger)a;
            oldLevel = log.getLevel();
            appender.start();
            log.addAppender(appender);
            log.setLevel(Level.ALL);
         }
      }

      static void end() {
         if (log != null) {
            log.removeAppender(appender);
         }

      }

      static class MixinAppender extends AbstractAppender {
         MixinAppender() {
            super("MixinLogWatcherAppender", (Filter)null, (Layout)null);
         }

         public void append(LogEvent a) {
            if (a.getLevel() == Level.DEBUG && "Validating minecraft".equals(a.getMessage().getFormattedMessage())) {
               MixinEnvironment.gotoPhase(MixinEnvironment.Phase.INIT);
               if (MixinEnvironment.MixinLogWatcher.log.getLevel() == Level.ALL) {
                  MixinEnvironment.MixinLogWatcher.log.setLevel(MixinEnvironment.MixinLogWatcher.oldLevel);
               }

            }
         }
      }
   }

   static class TokenProviderWrapper implements Comparable<MixinEnvironment.TokenProviderWrapper> {
      private static int nextOrder = 0;
      private final int priority;
      private final int order;
      private final IEnvironmentTokenProvider provider;
      private final MixinEnvironment environment;

      public TokenProviderWrapper(IEnvironmentTokenProvider a, MixinEnvironment a) {
         a.provider = a;
         a.environment = a;
         a.order = nextOrder++;
         a.priority = a.getPriority();
      }

      public int compareTo(MixinEnvironment.TokenProviderWrapper a) {
         if (a == null) {
            return 0;
         } else {
            return a.priority == a.priority ? a.order - a.order : a.priority - a.priority;
         }
      }

      public IEnvironmentTokenProvider getProvider() {
         return a.provider;
      }

      Integer getToken(String a) {
         return a.provider.getToken(a, a.environment);
      }
   }

   public static enum CompatibilityLevel {
      JAVA_6(6, 50, false),
      JAVA_7(7, 51, false) {
         boolean isSupported() {
            return JavaVersion.current() >= 1.7D;
         }
      },
      JAVA_8(8, 52, true) {
         boolean isSupported() {
            return JavaVersion.current() >= 1.8D;
         }
      },
      JAVA_9(9, 53, true) {
         boolean isSupported() {
            return false;
         }
      };

      private static final int CLASS_V1_9 = 53;
      private final int ver;
      private final int classVersion;
      private final boolean supportsMethodsInInterfaces;
      private MixinEnvironment.CompatibilityLevel maxCompatibleLevel;

      private CompatibilityLevel(int a, int a, boolean a) {
         a.ver = a;
         a.classVersion = a;
         a.supportsMethodsInInterfaces = a;
      }

      private void setMaxCompatibleLevel(MixinEnvironment.CompatibilityLevel a) {
         a.maxCompatibleLevel = a;
      }

      boolean isSupported() {
         return true;
      }

      public int classVersion() {
         return a.classVersion;
      }

      public boolean supportsMethodsInInterfaces() {
         return a.supportsMethodsInInterfaces;
      }

      public boolean isAtLeast(MixinEnvironment.CompatibilityLevel a) {
         return a == null || a.ver >= a.ver;
      }

      public boolean canElevateTo(MixinEnvironment.CompatibilityLevel a) {
         if (a != null && a.maxCompatibleLevel != null) {
            return a.ver <= a.maxCompatibleLevel.ver;
         } else {
            return true;
         }
      }

      public boolean canSupport(MixinEnvironment.CompatibilityLevel a) {
         return a == null ? true : a.canElevateTo(a);
      }

      // $FF: synthetic method
      CompatibilityLevel(int a, int a, boolean a, Object a6) {
         this(a, a, a);
      }
   }

   public static enum Option {
      DEBUG_ALL("debug"),
      DEBUG_EXPORT(DEBUG_ALL, "export"),
      DEBUG_EXPORT_FILTER(DEBUG_EXPORT, "filter", false),
      DEBUG_EXPORT_DECOMPILE(DEBUG_EXPORT, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "decompile"),
      DEBUG_EXPORT_DECOMPILE_THREADED(DEBUG_EXPORT_DECOMPILE, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "async"),
      DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES(DEBUG_EXPORT_DECOMPILE, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "mergeGenericSignatures"),
      DEBUG_VERIFY(DEBUG_ALL, "verify"),
      DEBUG_VERBOSE(DEBUG_ALL, "verbose"),
      DEBUG_INJECTORS(DEBUG_ALL, "countInjections"),
      DEBUG_STRICT(DEBUG_ALL, MixinEnvironment.Option.Inherit.INDEPENDENT, "strict"),
      DEBUG_UNIQUE(DEBUG_STRICT, "unique"),
      DEBUG_TARGETS(DEBUG_STRICT, "targets"),
      DEBUG_PROFILER(DEBUG_ALL, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "profiler"),
      DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"),
      CHECK_ALL("checks"),
      CHECK_IMPLEMENTS(CHECK_ALL, "interfaces"),
      CHECK_IMPLEMENTS_STRICT(CHECK_IMPLEMENTS, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "strict"),
      IGNORE_CONSTRAINTS("ignoreConstraints"),
      HOT_SWAP("hotSwap"),
      ENVIRONMENT(MixinEnvironment.Option.Inherit.ALWAYS_FALSE, "env"),
      OBFUSCATION_TYPE(ENVIRONMENT, MixinEnvironment.Option.Inherit.ALWAYS_FALSE, "obf"),
      DISABLE_REFMAP(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "disableRefMap"),
      REFMAP_REMAP(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "remapRefMap"),
      REFMAP_REMAP_RESOURCE(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "refMapRemappingFile", ""),
      REFMAP_REMAP_SOURCE_ENV(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "refMapRemappingEnv", "searge"),
      REFMAP_REMAP_ALLOW_PERMISSIVE(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "allowPermissiveMatch", true, "true"),
      IGNORE_REQUIRED(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "ignoreRequired"),
      DEFAULT_COMPATIBILITY_LEVEL(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "compatLevel"),
      SHIFT_BY_VIOLATION_BEHAVIOUR(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "shiftByViolation", "warn"),
      INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");

      private static final String PREFIX = "mixin";
      final MixinEnvironment.Option parent;
      final MixinEnvironment.Option.Inherit inheritance;
      final String property;
      final String defaultValue;
      final boolean isFlag;
      final int depth;

      private Option(String a) {
         this((MixinEnvironment.Option)null, a, true);
      }

      private Option(MixinEnvironment.Option.Inherit a, String a) {
         this((MixinEnvironment.Option)null, a, a, true);
      }

      private Option(String a, boolean a) {
         this((MixinEnvironment.Option)null, a, a);
      }

      private Option(String a, String a) {
         this((MixinEnvironment.Option)null, MixinEnvironment.Option.Inherit.INDEPENDENT, a, false, a);
      }

      private Option(MixinEnvironment.Option a, String a) {
         this(a, MixinEnvironment.Option.Inherit.INHERIT, a, true);
      }

      private Option(MixinEnvironment.Option a, MixinEnvironment.Option.Inherit a, String a) {
         this(a, a, a, true);
      }

      private Option(MixinEnvironment.Option a, String a, boolean a) {
         this(a, MixinEnvironment.Option.Inherit.INHERIT, a, a, (String)null);
      }

      private Option(MixinEnvironment.Option a, MixinEnvironment.Option.Inherit a, String a, boolean a) {
         this(a, a, a, a, (String)null);
      }

      private Option(MixinEnvironment.Option a, String a, String a) {
         this(a, MixinEnvironment.Option.Inherit.INHERIT, a, false, a);
      }

      private Option(MixinEnvironment.Option a, MixinEnvironment.Option.Inherit a, String a, String a) {
         this(a, a, a, false, a);
      }

      private Option(MixinEnvironment.Option a, MixinEnvironment.Option.Inherit a, String a, boolean a, String a) {
         a.parent = a;
         a.inheritance = a;
         a.property = (a != null ? a.property : "mixin") + "." + a;
         a.defaultValue = a;
         a.isFlag = a;

         int a;
         for(a = 0; a != null; ++a) {
            a = a.parent;
         }

         a.depth = a;
      }

      MixinEnvironment.Option getParent() {
         return a.parent;
      }

      String getProperty() {
         return a.property;
      }

      public String toString() {
         return a.isFlag ? String.valueOf(a.getBooleanValue()) : a.getStringValue();
      }

      private boolean getLocalBooleanValue(boolean a) {
         return Boolean.parseBoolean(System.getProperty(a.property, Boolean.toString(a)));
      }

      private boolean getInheritedBooleanValue() {
         return a.parent != null && a.parent.getBooleanValue();
      }

      final boolean getBooleanValue() {
         if (a.inheritance == MixinEnvironment.Option.Inherit.ALWAYS_FALSE) {
            return false;
         } else {
            boolean a = a.getLocalBooleanValue(false);
            if (a.inheritance == MixinEnvironment.Option.Inherit.INDEPENDENT) {
               return a;
            } else {
               boolean a = a || a.getInheritedBooleanValue();
               return a.inheritance == MixinEnvironment.Option.Inherit.INHERIT ? a : a.getLocalBooleanValue(a);
            }
         }
      }

      final String getStringValue() {
         return a.inheritance != MixinEnvironment.Option.Inherit.INDEPENDENT && a.parent != null && !a.parent.getBooleanValue() ? a.defaultValue : System.getProperty(a.property, a.defaultValue);
      }

      <E extends Enum<E>> E getEnumValue(E a) {
         String a = System.getProperty(a.property, a.name());

         try {
            return Enum.valueOf(a.getClass(), a.toUpperCase());
         } catch (IllegalArgumentException var4) {
            return a;
         }
      }

      private static enum Inherit {
         INHERIT,
         ALLOW_OVERRIDE,
         INDEPENDENT,
         ALWAYS_FALSE;
      }
   }

   public static enum Side {
      UNKNOWN {
         protected boolean detect() {
            return false;
         }
      },
      CLIENT {
         protected boolean detect() {
            String ax = MixinService.getService().getSideName();
            return "CLIENT".equals(ax);
         }
      },
      SERVER {
         protected boolean detect() {
            String ax = MixinService.getService().getSideName();
            return "SERVER".equals(ax) || "DEDICATEDSERVER".equals(ax);
         }
      };

      private Side() {
      }

      protected abstract boolean detect();

      // $FF: synthetic method
      Side(Object a3) {
         this();
      }
   }

   public static final class Phase {
      static final MixinEnvironment.Phase NOT_INITIALISED = new MixinEnvironment.Phase(-1, "NOT_INITIALISED");
      public static final MixinEnvironment.Phase PREINIT = new MixinEnvironment.Phase(0, "PREINIT");
      public static final MixinEnvironment.Phase INIT = new MixinEnvironment.Phase(1, "INIT");
      public static final MixinEnvironment.Phase DEFAULT = new MixinEnvironment.Phase(2, "DEFAULT");
      static final List<MixinEnvironment.Phase> phases;
      final int ordinal;
      final String name;
      private MixinEnvironment environment;

      private Phase(int a, String a) {
         a.ordinal = a;
         a.name = a;
      }

      public String toString() {
         return a.name;
      }

      public static MixinEnvironment.Phase forName(String a) {
         Iterator var1 = phases.iterator();

         MixinEnvironment.Phase a;
         do {
            if (!var1.hasNext()) {
               return null;
            }

            a = (MixinEnvironment.Phase)var1.next();
         } while(!a.name.equals(a));

         return a;
      }

      MixinEnvironment getEnvironment() {
         if (a.ordinal < 0) {
            throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
         } else {
            if (a.environment == null) {
               a.environment = new MixinEnvironment(a);
            }

            return a.environment;
         }
      }

      static {
         phases = ImmutableList.of(PREINIT, INIT, DEFAULT);
      }
   }
}

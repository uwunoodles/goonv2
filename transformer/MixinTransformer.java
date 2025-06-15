package org.spongepowered.asm.mixin.transformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.throwables.MixinPrepareError;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckInterfaces;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;

public class MixinTransformer extends TreeTransformer {
   private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
   private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
   static final Logger logger = LogManager.getLogger("mixin");
   private final IMixinService service = MixinService.getService();
   private final List<MixinConfig> configs = new ArrayList();
   private final List<MixinConfig> pendingConfigs = new ArrayList();
   private final ReEntranceLock lock;
   private final String sessionId = UUID.randomUUID().toString();
   private final Extensions extensions;
   private final IHotSwap hotSwapper;
   private final MixinPostProcessor postProcessor;
   private final Profiler profiler;
   private MixinEnvironment currentEnvironment;
   private Level verboseLoggingLevel;
   private boolean errorState;
   private int transformedCount;

   MixinTransformer() {
      a.verboseLoggingLevel = Level.DEBUG;
      a.errorState = false;
      a.transformedCount = 0;
      MixinEnvironment a = MixinEnvironment.getCurrentEnvironment();
      Object a = a.getActiveTransformer();
      if (a instanceof ITransformer) {
         throw new MixinException("Terminating MixinTransformer instance " + a);
      } else {
         a.setActiveTransformer(a);
         a.lock = a.service.getReEntranceLock();
         a.extensions = new Extensions(a);
         a.hotSwapper = a.initHotSwapper(a);
         a.postProcessor = new MixinPostProcessor();
         a.extensions.add((IClassGenerator)(new ArgsClassGenerator()));
         a.extensions.add((IClassGenerator)(new InnerClassGenerator()));
         a.extensions.add((IExtension)(new ExtensionClassExporter(a)));
         a.extensions.add((IExtension)(new ExtensionCheckClass()));
         a.extensions.add((IExtension)(new ExtensionCheckInterfaces()));
         a.profiler = MixinEnvironment.getProfiler();
      }
   }

   private IHotSwap initHotSwapper(MixinEnvironment a) {
      if (!a.getOption(MixinEnvironment.Option.HOT_SWAP)) {
         return null;
      } else {
         try {
            logger.info("Attempting to load Hot-Swap agent");
            Class<? extends IHotSwap> a = Class.forName("org.spongepowered.tools.agent.MixinAgent");
            Constructor<? extends IHotSwap> a = a.getDeclaredConstructor(MixinTransformer.class);
            return (IHotSwap)a.newInstance(a);
         } catch (Throwable var4) {
            logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[]{var4.getClass().getSimpleName(), var4.getMessage()});
            return null;
         }
      }
   }

   public void audit(MixinEnvironment a) {
      Set<String> a = new HashSet();
      Iterator var3 = a.configs.iterator();

      while(var3.hasNext()) {
         MixinConfig a = (MixinConfig)var3.next();
         a.addAll(a.getUnhandledTargets());
      }

      Logger a = LogManager.getLogger("mixin/audit");
      Iterator var11 = a.iterator();

      while(var11.hasNext()) {
         String a = (String)var11.next();

         try {
            a.info("Force-loading class {}", new Object[]{a});
            a.service.getClassProvider().findClass(a, true);
         } catch (ClassNotFoundException var9) {
            a.error("Could not force-load " + a, var9);
         }
      }

      var11 = a.configs.iterator();

      while(var11.hasNext()) {
         MixinConfig a = (MixinConfig)var11.next();
         Iterator var6 = a.getUnhandledTargets().iterator();

         while(var6.hasNext()) {
            String a = (String)var6.next();
            ClassAlreadyLoadedException a = new ClassAlreadyLoadedException(a + " was already classloaded");
            a.error("Could not force-load " + a, a);
         }
      }

      if (a.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
         a.printProfilerSummary();
      }

   }

   private void printProfilerSummary() {
      DecimalFormat a = new DecimalFormat("(###0.000");
      DecimalFormat a = new DecimalFormat("(###0.0");
      PrettyPrinter a = a.profiler.printer(false, false);
      long a = a.profiler.get("mixin.prepare").getTotalTime();
      long a = a.profiler.get("mixin.read").getTotalTime();
      long a = a.profiler.get("mixin.apply").getTotalTime();
      long a = a.profiler.get("mixin.write").getTotalTime();
      long a = a.profiler.get("mixin").getTotalTime();
      long a = a.profiler.get("class.load").getTotalTime();
      long a = a.profiler.get("class.transform").getTotalTime();
      long a = a.profiler.get("mixin.debug.export").getTotalTime();
      long a = a - a - a - a;
      double a = (double)a / (double)a * 100.0D;
      double a = (double)a / (double)a * 100.0D;
      double a = (double)a / (double)a * 100.0D;
      double a = (double)a / (double)a * 100.0D;
      long a = 0L;
      Profiler.Section a = null;
      Iterator var33 = a.profiler.getSections().iterator();

      while(var33.hasNext()) {
         Profiler.Section a = (Profiler.Section)var33.next();
         long a = a.getName().startsWith("class.transform.") ? a.getTotalTime() : 0L;
         if (a > a) {
            a = a;
            a = a;
         }
      }

      a.hr().add("Summary").hr().add();
      String a = "%9d ms %12s seconds)";
      a.kv("Total mixin time", a, a, a.format((double)a * 0.001D)).add();
      a.kv("Preparing mixins", a, a, a.format((double)a * 0.001D));
      a.kv("Reading input", a, a, a.format((double)a * 0.001D));
      a.kv("Applying mixins", a, a, a.format((double)a * 0.001D));
      a.kv("Writing output", a, a, a.format((double)a * 0.001D)).add();
      a.kv("of which", "");
      a.kv("Time spent loading from disk", a, a, a.format((double)a * 0.001D));
      a.kv("Time spent transforming classes", a, a, a.format((double)a * 0.001D)).add();
      if (a != null) {
         a.kv("Worst transformer", a.getName());
         a.kv("Class", a.getInfo());
         a.kv("Time spent", "%s seconds", a.getTotalSeconds());
         a.kv("called", "%d times", a.getTotalCount()).add();
      }

      a.kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", a, a.format(a));
      a.kv("Loading classes", "%9d ms %10s%% of total)", a, a.format(a));
      a.kv("Running transformers", "%9d ms %10s%% of total)", a, a.format(a));
      if (a > 0L) {
         a.kv("Exporting classes (debug)", "%9d ms %10s%% of total)", a, a.format(a));
      }

      a.add();

      try {
         Class<?> a = a.service.getClassProvider().findAgentClass("org.spongepowered.metronome.Agent", false);
         Method a = a.getDeclaredMethod("getTimes");
         Map<String, Long> a = (Map)a.invoke((Object)null);
         a.hr().add("Transformer Times").hr().add();
         int a = 10;

         Iterator var38;
         Entry a;
         for(var38 = a.entrySet().iterator(); var38.hasNext(); a = Math.max(a, ((String)a.getKey()).length())) {
            a = (Entry)var38.next();
         }

         var38 = a.entrySet().iterator();

         while(var38.hasNext()) {
            a = (Entry)var38.next();
            String a = (String)a.getKey();
            long a = 0L;
            Iterator var43 = a.profiler.getSections().iterator();

            while(var43.hasNext()) {
               Profiler.Section a = (Profiler.Section)var43.next();
               if (a.equals(a.getInfo())) {
                  a = a.getTotalTime();
                  break;
               }
            }

            if (a > 0L) {
               a.add("%-" + a + "s %8s ms %8s ms in mixin)", a, (Long)a.getValue() + a, "(" + a);
            } else {
               a.add("%-" + a + "s %8s ms", a, a.getValue());
            }
         }

         a.add();
      } catch (Throwable var45) {
      }

      a.print();
   }

   public String getName() {
      return a.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return true;
   }

   public synchronized byte[] transformClassBytes(String a, String a, byte[] a) {
      if (a != null && !a.errorState) {
         MixinEnvironment a = MixinEnvironment.getCurrentEnvironment();
         Profiler.Section a;
         if (a == null) {
            Iterator var21 = a.extensions.getGenerators().iterator();

            do {
               if (!var21.hasNext()) {
                  return a;
               }

               IClassGenerator a = (IClassGenerator)var21.next();
               a = a.profiler.begin("generator", a.getClass().getSimpleName().toLowerCase());
               a = a.generate(a);
               a.end();
            } while(a == null);

            a.extensions.export(a, a.replace('.', '/'), false, a);
            return a;
         } else {
            boolean a = a.lock.push().check();
            Profiler.Section a = a.profiler.begin("mixin");
            if (!a) {
               try {
                  a.checkSelect(a);
               } catch (Exception var18) {
                  a.lock.pop();
                  a.end();
                  throw new MixinException(var18);
               }
            }

            try {
               byte[] var26;
               if (a.postProcessor.canTransform(a)) {
                  a = a.profiler.begin("postprocessor");
                  byte[] a = a.postProcessor.transformClassBytes(a, a, a);
                  a.end();
                  a.extensions.export(a, a, false, a);
                  var26 = a;
                  return var26;
               } else {
                  SortedSet<MixinInfo> a = null;
                  boolean a = false;
                  Iterator var9 = a.configs.iterator();

                  while(var9.hasNext()) {
                     MixinConfig a = (MixinConfig)var9.next();
                     if (a.packageMatch(a)) {
                        a = true;
                     } else if (a.hasMixinsFor(a)) {
                        if (a == null) {
                           a = new TreeSet();
                        }

                        a.addAll(a.getMixinsFor(a));
                     }
                  }

                  if (a) {
                     throw new NoClassDefFoundError(String.format("%s is a mixin class and cannot be referenced directly", a));
                  } else {
                     if (a != null) {
                        if (a) {
                           logger.warn("Re-entrance detected, this will cause serious problems.", new MixinException());
                           throw new MixinApplyError("Re-entrance error.");
                        }

                        if (a.hotSwapper != null) {
                           a.hotSwapper.registerTargetClass(a, a);
                        }

                        try {
                           Profiler.Section a = a.profiler.begin("read");
                           ClassNode a = a.readClass(a, true);
                           TargetClassContext a = new TargetClassContext(a, a.extensions, a.sessionId, a, a, a);
                           a.end();
                           a = a.applyMixins(a, a);
                           ++a.transformedCount;
                        } catch (InvalidMixinException var17) {
                           a.dumpClassOnFailure(a, a, a);
                           a.handleMixinApplyError(a, var17, a);
                        }
                     }

                     var26 = a;
                     return var26;
                  }
               }
            } catch (Throwable var19) {
               var19.printStackTrace();
               a.dumpClassOnFailure(a, a, a);
               throw new MixinTransformerError("An unexpected critical error was encountered", var19);
            } finally {
               a.lock.pop();
               a.end();
            }
         }
      } else {
         return a;
      }
   }

   public List<String> reload(String a, byte[] a) {
      if (a.lock.getDepth() > 0) {
         throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
      } else {
         List<String> a = new ArrayList();
         Iterator var4 = a.configs.iterator();

         while(var4.hasNext()) {
            MixinConfig a = (MixinConfig)var4.next();
            a.addAll(a.reloadMixin(a, a));
         }

         return a;
      }
   }

   private void checkSelect(MixinEnvironment a) {
      if (a.currentEnvironment != a) {
         a.select(a);
      } else {
         int a = Mixins.getUnvisitedCount();
         if (a > 0 && a.transformedCount == 0) {
            a.select(a);
         }

      }
   }

   private void select(MixinEnvironment a) {
      a.verboseLoggingLevel = a.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
      if (a.transformedCount > 0) {
         logger.log(a.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[]{a.currentEnvironment, a.transformedCount});
      }

      String a = a.currentEnvironment == a ? "Checking for additional" : "Preparing";
      logger.log(a.verboseLoggingLevel, "{} mixins for {}", new Object[]{a, a});
      a.profiler.setActive(true);
      a.profiler.mark(a.getPhase().toString() + ":prepare");
      Profiler.Section a = a.profiler.begin("prepare");
      a.selectConfigs(a);
      a.extensions.select(a);
      int a = a.prepareConfigs(a);
      a.currentEnvironment = a;
      a.transformedCount = 0;
      a.end();
      long a = a.getTime();
      double a = a.getSeconds();
      if (a > 0.25D) {
         long a = a.profiler.get("class.load").getTime();
         long a = a.profiler.get("class.transform").getTime();
         long a = a.profiler.get("mixin.plugin").getTime();
         String a = (new DecimalFormat("###0.000")).format(a);
         String a = (new DecimalFormat("###0.0")).format((double)a / (double)a);
         logger.log(a.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[]{a, a, a, a, a, a});
      }

      a.profiler.mark(a.getPhase().toString() + ":apply");
      a.profiler.setActive(a.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
   }

   private void selectConfigs(MixinEnvironment a) {
      Iterator a = Mixins.getConfigs().iterator();

      while(a.hasNext()) {
         Config a = (Config)a.next();

         try {
            MixinConfig a = a.get();
            if (a.select(a)) {
               a.remove();
               logger.log(a.verboseLoggingLevel, "Selecting config {}", new Object[]{a});
               a.onSelect();
               a.pendingConfigs.add(a);
            }
         } catch (Exception var5) {
            logger.warn(String.format("Failed to select mixin config: %s", a), var5);
         }
      }

      Collections.sort(a.pendingConfigs);
   }

   private int prepareConfigs(MixinEnvironment a) {
      int a = 0;
      final IHotSwap a = a.hotSwapper;
      Iterator var4 = a.pendingConfigs.iterator();

      MixinConfig a;
      while(var4.hasNext()) {
         a = (MixinConfig)var4.next();
         a.addListener(a.postProcessor);
         if (a != null) {
            a.addListener(new MixinConfig.IListener() {
               public void onPrepare(MixinInfo axx) {
                  a.registerMixinClass(axx.getClassName());
               }

               public void onInit(MixinInfo a1) {
               }
            });
         }
      }

      var4 = a.pendingConfigs.iterator();

      String a;
      while(var4.hasNext()) {
         a = (MixinConfig)var4.next();

         try {
            logger.log(a.verboseLoggingLevel, "Preparing {} ({})", new Object[]{a, a.getDeclaredMixinCount()});
            a.prepare();
            a += a.getMixinCount();
         } catch (InvalidMixinException var12) {
            a.handleMixinPrepareError(a, var12, a);
         } catch (Exception var13) {
            a = var13.getMessage();
            logger.error("Error encountered whilst initialising mixin config '" + a.getName() + "': " + a, var13);
         }
      }

      var4 = a.pendingConfigs.iterator();

      while(true) {
         IMixinConfigPlugin a;
         do {
            if (!var4.hasNext()) {
               var4 = a.pendingConfigs.iterator();

               while(var4.hasNext()) {
                  a = (MixinConfig)var4.next();

                  try {
                     a.postInitialise();
                  } catch (InvalidMixinException var10) {
                     a.handleMixinPrepareError(a, var10, a);
                  } catch (Exception var11) {
                     a = var11.getMessage();
                     logger.error("Error encountered during mixin config postInit step'" + a.getName() + "': " + a, var11);
                  }
               }

               a.configs.addAll(a.pendingConfigs);
               Collections.sort(a.configs);
               a.pendingConfigs.clear();
               return a;
            }

            a = (MixinConfig)var4.next();
            a = a.getPlugin();
         } while(a == null);

         Set<String> a = new HashSet();
         Iterator var8 = a.pendingConfigs.iterator();

         while(var8.hasNext()) {
            MixinConfig a = (MixinConfig)var8.next();
            if (!a.equals(a)) {
               a.addAll(a.getTargets());
            }
         }

         a.acceptTargets(a.getTargets(), Collections.unmodifiableSet(a));
      }
   }

   private byte[] applyMixins(MixinEnvironment a, TargetClassContext a) {
      Profiler.Section a = a.profiler.begin("preapply");
      a.extensions.preApply(a);
      a = a.next("apply");
      a.apply(a);
      a = a.next("postapply");

      try {
         a.extensions.postApply(a);
      } catch (ExtensionCheckClass.ValidationFailedException var5) {
         logger.info(var5.getMessage());
         if (a.isExportForced() || a.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
            a.writeClass(a);
         }
      }

      a.end();
      return a.writeClass(a);
   }

   private void apply(TargetClassContext a) {
      a.applyMixins();
   }

   private void handleMixinPrepareError(MixinConfig a, InvalidMixinException a, MixinEnvironment a) throws MixinPrepareError {
      a.handleMixinError(a.getName(), a, a, MixinTransformer.ErrorPhase.PREPARE);
   }

   private void handleMixinApplyError(String a, InvalidMixinException a, MixinEnvironment a) throws MixinApplyError {
      a.handleMixinError(a, a, a, MixinTransformer.ErrorPhase.APPLY);
   }

   private void handleMixinError(String a, InvalidMixinException a, MixinEnvironment a, MixinTransformer.ErrorPhase a) throws Error {
      a.errorState = true;
      IMixinInfo a = a.getMixin();
      if (a == null) {
         logger.error("InvalidMixinException has no mixin!", a);
         throw a;
      } else {
         IMixinConfig a = a.getConfig();
         MixinEnvironment.Phase a = a.getPhase();
         IMixinErrorHandler.ErrorAction a = a.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
         if (a.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            (new PrettyPrinter()).add("Invalid Mixin").centre().hr('-').kvWidth(10).kv("Action", a.name()).kv("Mixin", a.getClassName()).kv("Config", a.getName()).kv("Phase", a).hr('-').add("    %s", a.getClass().getName()).hr('-').addWrapped("    %s", a.getMessage()).hr('-').add((Throwable)a, 8).trace(a.logLevel);
         }

         Iterator var9 = a.getErrorHandlers(a.getPhase()).iterator();

         while(var9.hasNext()) {
            IMixinErrorHandler a = (IMixinErrorHandler)var9.next();
            IMixinErrorHandler.ErrorAction a = a.onError(a, a, a, a, a);
            if (a != null) {
               a = a;
            }
         }

         logger.log(a.logLevel, a.getLogMessage(a, a, a), a);
         a.errorState = false;
         if (a == IMixinErrorHandler.ErrorAction.ERROR) {
            throw new MixinApplyError(a.getErrorMessage(a, a, a), a);
         }
      }
   }

   private List<IMixinErrorHandler> getErrorHandlers(MixinEnvironment.Phase a1) {
      List<IMixinErrorHandler> a = new ArrayList();
      Iterator var3 = Mixins.getErrorHandlerClasses().iterator();

      while(var3.hasNext()) {
         String a = (String)var3.next();

         try {
            logger.info("Instancing error handler class {}", new Object[]{a});
            Class<?> a = a.service.getClassProvider().findClass(a, true);
            IMixinErrorHandler a = (IMixinErrorHandler)a.newInstance();
            if (a != null) {
               a.add(a);
            }
         } catch (Throwable var7) {
         }
      }

      return a;
   }

   private byte[] writeClass(TargetClassContext a) {
      return a.writeClass(a.getClassName(), a.getClassNode(), a.isExportForced());
   }

   private byte[] writeClass(String a, ClassNode a, boolean a) {
      Profiler.Section a = a.profiler.begin("write");
      byte[] a = a.writeClass(a);
      a.end();
      a.extensions.export(a.currentEnvironment, a, a, a);
      return a;
   }

   private void dumpClassOnFailure(String a, byte[] a, MixinEnvironment a) {
      if (a.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
         ExtensionClassExporter a = (ExtensionClassExporter)a.extensions.getExtension(ExtensionClassExporter.class);
         a.dumpClass(a.replace('.', '/') + ".target", a);
      }

   }

   static enum ErrorPhase {
      PREPARE {
         IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler ax, String a2, InvalidMixinException axx, IMixinInfo axxx, IMixinErrorHandler.ErrorAction axxxx) {
            try {
               return ax.onPrepareError(axxx.getConfig(), axx, axxx, axxxx);
            } catch (AbstractMethodError var7) {
               return axxxx;
            }
         }

         protected String getContext(IMixinInfo ax, String axx) {
            return String.format("preparing %s in %s", ax.getName(), axx);
         }
      },
      APPLY {
         IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler ax, String axx, InvalidMixinException axxx, IMixinInfo axxxx, IMixinErrorHandler.ErrorAction axxxxx) {
            try {
               return ax.onApplyError(axx, axxx, axxxx, axxxxx);
            } catch (AbstractMethodError var7) {
               return axxxxx;
            }
         }

         protected String getContext(IMixinInfo ax, String axx) {
            return String.format("%s -> %s", ax, axx);
         }
      };

      private final String text;

      private ErrorPhase() {
         a.text = a.name().toLowerCase();
      }

      abstract IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler var1, String var2, InvalidMixinException var3, IMixinInfo var4, IMixinErrorHandler.ErrorAction var5);

      protected abstract String getContext(IMixinInfo var1, String var2);

      public String getLogMessage(String a, InvalidMixinException a, IMixinInfo a) {
         return String.format("Mixin %s failed %s: %s %s", a.text, a.getContext(a, a), a.getClass().getName(), a.getMessage());
      }

      public String getErrorMessage(IMixinInfo a, IMixinConfig a, MixinEnvironment.Phase a) {
         return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", a, a, a, a.name());
      }

      // $FF: synthetic method
      ErrorPhase(Object a3) {
         this();
      }
   }
}

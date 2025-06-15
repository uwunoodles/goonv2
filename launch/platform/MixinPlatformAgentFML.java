package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public class MixinPlatformAgentFML extends MixinPlatformAgentAbstract {
   private static final String LOAD_CORE_MOD_METHOD = "loadCoreMod";
   private static final String GET_REPARSEABLE_COREMODS_METHOD = "getReparseableCoremods";
   private static final String CORE_MOD_MANAGER_CLASS = "net.minecraftforge.fml.relauncher.CoreModManager";
   private static final String CORE_MOD_MANAGER_CLASS_LEGACY = "cpw.mods.fml.relauncher.CoreModManager";
   private static final String GET_IGNORED_MODS_METHOD = "getIgnoredMods";
   private static final String GET_IGNORED_MODS_METHOD_LEGACY = "getLoadedCoremods";
   private static final String FML_REMAPPER_ADAPTER_CLASS = "org.spongepowered.asm.bridge.RemapperAdapterFML";
   private static final String FML_CMDLINE_COREMODS = "fml.coreMods.load";
   private static final String FML_PLUGIN_WRAPPER_CLASS = "FMLPluginWrapper";
   private static final String FML_CORE_MOD_INSTANCE_FIELD = "coreModInstance";
   private static final String MFATT_FORCELOADASMOD = "ForceLoadAsMod";
   private static final String MFATT_FMLCOREPLUGIN = "FMLCorePlugin";
   private static final String MFATT_COREMODCONTAINSMOD = "FMLCorePluginContainsFMLMod";
   private static final String FML_TWEAKER_DEOBF = "FMLDeobfTweaker";
   private static final String FML_TWEAKER_INJECTION = "FMLInjectionAndSortingTweaker";
   private static final String FML_TWEAKER_TERMINAL = "TerminalTweaker";
   private static final Set<String> loadedCoreMods = new HashSet();
   private final ITweaker coreModWrapper;
   private final String fileName;
   private Class<?> clCoreModManager;
   private boolean initInjectionState;

   public MixinPlatformAgentFML(MixinPlatformManager a, URI a) {
      super(a, a);
      a.fileName = a.container.getName();
      a.coreModWrapper = a.initFMLCoreMod();
   }

   private ITweaker initFMLCoreMod() {
      try {
         try {
            a.clCoreModManager = getCoreModManagerClass();
         } catch (ClassNotFoundException var2) {
            MixinPlatformAgentAbstract.logger.info("FML platform manager could not load class {}. Proceeding without FML support.", new Object[]{var2.getMessage()});
            return null;
         }

         if ("true".equalsIgnoreCase(a.attributes.get("ForceLoadAsMod"))) {
            MixinPlatformAgentAbstract.logger.debug("ForceLoadAsMod was specified for {}, attempting force-load", new Object[]{a.fileName});
            a.loadAsMod();
         }

         return a.injectCorePlugin();
      } catch (Exception var3) {
         MixinPlatformAgentAbstract.logger.catching(var3);
         return null;
      }
   }

   private void loadAsMod() {
      try {
         getIgnoredMods(a.clCoreModManager).remove(a.fileName);
      } catch (Exception var2) {
         MixinPlatformAgentAbstract.logger.catching(var2);
      }

      if (a.attributes.get("FMLCorePluginContainsFMLMod") != null) {
         if (a.isIgnoredReparseable()) {
            MixinPlatformAgentAbstract.logger.debug("Ignoring request to add {} to reparseable coremod collection - it is a deobfuscated dependency", new Object[]{a.fileName});
            return;
         }

         a.addReparseableJar();
      }

   }

   private boolean isIgnoredReparseable() {
      return a.container.toString().contains("deobfedDeps");
   }

   private void addReparseableJar() {
      try {
         Method a = a.clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.reparseablecoremodsmethod", "getReparseableCoremods"));
         List<String> a = (List)a.invoke((Object)null);
         if (!a.contains(a.fileName)) {
            MixinPlatformAgentAbstract.logger.debug("Adding {} to reparseable coremod collection", new Object[]{a.fileName});
            a.add(a.fileName);
         }
      } catch (Exception var3) {
         MixinPlatformAgentAbstract.logger.catching(var3);
      }

   }

   private ITweaker injectCorePlugin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      String a = a.attributes.get("FMLCorePlugin");
      if (a == null) {
         return null;
      } else if (a.isAlreadyInjected(a)) {
         MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Skipping because it was already injected.", new Object[]{a.fileName, a});
         return null;
      } else {
         MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Injecting it into FML for co-initialisation:", new Object[]{a.fileName, a});
         Method a = a.clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.loadcoremodmethod", "loadCoreMod"), LaunchClassLoader.class, String.class, File.class);
         a.setAccessible(true);
         ITweaker a = (ITweaker)a.invoke((Object)null, Launch.classLoader, a, a.container);
         if (a == null) {
            MixinPlatformAgentAbstract.logger.debug("Core plugin {} could not be loaded.", new Object[]{a});
            return null;
         } else {
            a.initInjectionState = isTweakerQueued("FMLInjectionAndSortingTweaker");
            loadedCoreMods.add(a);
            return a;
         }
      }
   }

   private boolean isAlreadyInjected(String a) {
      if (loadedCoreMods.contains(a)) {
         return true;
      } else {
         try {
            List<ITweaker> a = (List)GlobalProperties.get("Tweaks");
            if (a == null) {
               return false;
            }

            Iterator var3 = a.iterator();

            while(var3.hasNext()) {
               ITweaker a = (ITweaker)var3.next();
               Class<? extends ITweaker> a = a.getClass();
               if ("FMLPluginWrapper".equals(a.getSimpleName())) {
                  Field a = a.getField("coreModInstance");
                  a.setAccessible(true);
                  Object a = a.get(a);
                  if (a.equals(a.getClass().getName())) {
                     return true;
                  }
               }
            }
         } catch (Exception var8) {
         }

         return false;
      }
   }

   public String getPhaseProvider() {
      return MixinPlatformAgentFML.class.getName() + "$PhaseProvider";
   }

   public void prepare() {
      a.initInjectionState |= isTweakerQueued("FMLInjectionAndSortingTweaker");
   }

   public void initPrimaryContainer() {
      if (a.clCoreModManager != null) {
         a.injectRemapper();
      }

   }

   private void injectRemapper() {
      try {
         MixinPlatformAgentAbstract.logger.debug("Creating FML remapper adapter: {}", new Object[]{"org.spongepowered.asm.bridge.RemapperAdapterFML"});
         Class<?> a = Class.forName("org.spongepowered.asm.bridge.RemapperAdapterFML", true, Launch.classLoader);
         Method a = a.getDeclaredMethod("create");
         IRemapper a = (IRemapper)a.invoke((Object)null);
         MixinEnvironment.getDefaultEnvironment().getRemappers().add(a);
      } catch (Exception var4) {
         MixinPlatformAgentAbstract.logger.debug("Failed instancing FML remapper adapter, things will probably go horribly for notch-obf'd mods!");
      }

   }

   public void inject() {
      if (a.coreModWrapper != null && a.checkForCoInitialisation()) {
         MixinPlatformAgentAbstract.logger.debug("FML agent is co-initiralising coremod instance {} for {}", new Object[]{a.coreModWrapper, a.uri});
         a.coreModWrapper.injectIntoClassLoader(Launch.classLoader);
      }

   }

   public String getLaunchTarget() {
      return null;
   }

   protected final boolean checkForCoInitialisation() {
      boolean a = isTweakerQueued("FMLInjectionAndSortingTweaker");
      boolean a = isTweakerQueued("TerminalTweaker");
      if ((!a.initInjectionState || !a) && !a) {
         return !isTweakerQueued("FMLDeobfTweaker");
      } else {
         MixinPlatformAgentAbstract.logger.debug("FML agent is skipping co-init for {} because FML will inject it normally", new Object[]{a.coreModWrapper});
         return false;
      }
   }

   private static boolean isTweakerQueued(String a) {
      Iterator var1 = ((List)GlobalProperties.get("TweakClasses")).iterator();

      String a;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         a = (String)var1.next();
      } while(!a.endsWith(a));

      return true;
   }

   private static Class<?> getCoreModManagerClass() throws ClassNotFoundException {
      try {
         return Class.forName(GlobalProperties.getString("mixin.launch.fml.coremodmanagerclass", "net.minecraftforge.fml.relauncher.CoreModManager"));
      } catch (ClassNotFoundException var1) {
         return Class.forName("cpw.mods.fml.relauncher.CoreModManager");
      }
   }

   private static List<String> getIgnoredMods(Class<?> a) throws IllegalAccessException, InvocationTargetException {
      Method a = null;

      try {
         a = a.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.ignoredmodsmethod", "getIgnoredMods"));
      } catch (NoSuchMethodException var5) {
         try {
            a = a.getDeclaredMethod("getLoadedCoremods");
         } catch (NoSuchMethodException var4) {
            MixinPlatformAgentAbstract.logger.catching(Level.DEBUG, var4);
            return Collections.emptyList();
         }
      }

      return (List)a.invoke((Object)null);
   }

   static {
      String[] var0 = System.getProperty("fml.coreMods.load", "").split(",");
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         String a = var0[var2];
         if (!a.isEmpty()) {
            MixinPlatformAgentAbstract.logger.debug("FML platform agent will ignore coremod {} specified on the command line", new Object[]{a});
            loadedCoreMods.add(a);
         }
      }

   }
}

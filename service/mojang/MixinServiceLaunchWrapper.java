package org.spongepowered.asm.service.mojang;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.IClassProvider;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;

public class MixinServiceLaunchWrapper implements IMixinService, IClassProvider, IClassBytecodeProvider {
   public static final String BLACKBOARD_KEY_TWEAKCLASSES = "TweakClasses";
   public static final String BLACKBOARD_KEY_TWEAKS = "Tweaks";
   private static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
   private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
   private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
   private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final LaunchClassLoaderUtil classLoaderUtil;
   private final ReEntranceLock lock;
   private IClassNameTransformer nameTransformer;

   public MixinServiceLaunchWrapper() {
      a.classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
      a.lock = new ReEntranceLock(1);
   }

   public String getName() {
      return "LaunchWrapper";
   }

   public boolean isValid() {
      try {
         Launch.classLoader.hashCode();
         return true;
      } catch (Throwable var2) {
         return false;
      }
   }

   public void prepare() {
      Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch.");
   }

   public MixinEnvironment.Phase getInitialPhase() {
      return findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132 ? MixinEnvironment.Phase.DEFAULT : MixinEnvironment.Phase.PREINIT;
   }

   public void init() {
      if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4) {
         logger.error("MixinBootstrap.doInit() called during a tweak constructor!");
      }

      List<String> a = (List)GlobalProperties.get("TweakClasses");
      if (a != null) {
         a.add("org.spongepowered.asm.mixin.EnvironmentStateTweaker");
      }

   }

   public ReEntranceLock getReEntranceLock() {
      return a.lock;
   }

   public Collection<String> getPlatformAgents() {
      return ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFML");
   }

   public IClassProvider getClassProvider() {
      return a;
   }

   public IClassBytecodeProvider getBytecodeProvider() {
      return a;
   }

   public Class<?> findClass(String a) throws ClassNotFoundException {
      return Launch.classLoader.findClass(a);
   }

   public Class<?> findClass(String a, boolean a) throws ClassNotFoundException {
      return Class.forName(a, a, Launch.classLoader);
   }

   public Class<?> findAgentClass(String a, boolean a) throws ClassNotFoundException {
      return Class.forName(a, a, Launch.class.getClassLoader());
   }

   public void beginPhase() {
      Launch.classLoader.registerTransformer("org.spongepowered.asm.mixin.transformer.Proxy");
   }

   public void checkEnv(Object a) {
      if (a.getClass().getClassLoader() != Launch.class.getClassLoader()) {
         throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
      }
   }

   public InputStream getResourceAsStream(String a) {
      return Launch.classLoader.getResourceAsStream(a);
   }

   public void registerInvalidClass(String a) {
      a.classLoaderUtil.registerInvalidClass(a);
   }

   public boolean isClassLoaded(String a) {
      return a.classLoaderUtil.isClassLoaded(a);
   }

   public String getClassRestrictions(String a) {
      String a = "";
      if (a.classLoaderUtil.isClassClassLoaderExcluded(a, (String)null)) {
         a = "PACKAGE_CLASSLOADER_EXCLUSION";
      }

      if (a.classLoaderUtil.isClassTransformerExcluded(a, (String)null)) {
         a = (a.length() > 0 ? a + "," : "") + "PACKAGE_TRANSFORMER_EXCLUSION";
      }

      return a;
   }

   public URL[] getClassPath() {
      return (URL[])Launch.classLoader.getSources().toArray(new URL[0]);
   }

   public Collection<ITransformer> getTransformers() {
      List<IClassTransformer> a = Launch.classLoader.getTransformers();
      List<ITransformer> a = new ArrayList(a.size());
      Iterator var3 = a.iterator();

      while(var3.hasNext()) {
         IClassTransformer a = (IClassTransformer)var3.next();
         if (a instanceof ITransformer) {
            a.add((ITransformer)a);
         } else {
            a.add(new LegacyTransformerHandle(a));
         }

         if (a instanceof IClassNameTransformer) {
            logger.debug("Found name transformer: {}", new Object[]{a.getClass().getName()});
            a.nameTransformer = (IClassNameTransformer)a;
         }
      }

      return a;
   }

   public byte[] getClassBytes(String a, String a) throws IOException {
      byte[] a = Launch.classLoader.getClassBytes(a);
      if (a != null) {
         return a;
      } else {
         URLClassLoader a = (URLClassLoader)Launch.class.getClassLoader();
         InputStream a = null;

         Object var7;
         try {
            String a = a.replace('.', '/').concat(".class");
            a = a.getResourceAsStream(a);
            byte[] var13 = IOUtils.toByteArray(a);
            return var13;
         } catch (Exception var11) {
            var7 = null;
         } finally {
            IOUtils.closeQuietly(a);
         }

         return (byte[])var7;
      }
   }

   public byte[] getClassBytes(String a, boolean a) throws ClassNotFoundException, IOException {
      String a = a.replace('/', '.');
      String a = a.unmapClassName(a);
      Profiler a = MixinEnvironment.getProfiler();
      Profiler.Section a = a.begin(1, (String)"class.load");
      byte[] a = a.getClassBytes(a, a);
      a.end();
      if (a) {
         Profiler.Section a = a.begin(1, (String)"class.transform");
         a = a.applyTransformers(a, a, a, a);
         a.end();
      }

      if (a == null) {
         throw new ClassNotFoundException(String.format("The specified class '%s' was not found", a));
      } else {
         return a;
      }
   }

   private byte[] applyTransformers(String a, String a, byte[] a, Profiler a) {
      if (a.classLoaderUtil.isClassExcluded(a, a)) {
         return a;
      } else {
         MixinEnvironment a = MixinEnvironment.getCurrentEnvironment();
         Iterator var6 = a.getTransformers().iterator();

         while(var6.hasNext()) {
            ILegacyClassTransformer a = (ILegacyClassTransformer)var6.next();
            a.lock.clear();
            int a = a.getName().lastIndexOf(46);
            String a = a.getName().substring(a + 1);
            Profiler.Section a = a.begin(2, (String)a.toLowerCase());
            a.setInfo(a.getName());
            a = a.transformClassBytes(a, a, a);
            a.end();
            if (a.lock.isSet()) {
               a.addTransformerExclusion(a.getName());
               a.lock.clear();
               logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[]{a.getName()});
            }
         }

         return a;
      }
   }

   private String unmapClassName(String a) {
      if (a.nameTransformer == null) {
         a.findNameTransformer();
      }

      return a.nameTransformer != null ? a.nameTransformer.unmapClassName(a) : a;
   }

   private void findNameTransformer() {
      List<IClassTransformer> a = Launch.classLoader.getTransformers();
      Iterator var2 = a.iterator();

      while(var2.hasNext()) {
         IClassTransformer a = (IClassTransformer)var2.next();
         if (a instanceof IClassNameTransformer) {
            logger.debug("Found name transformer: {}", new Object[]{a.getClass().getName()});
            a.nameTransformer = (IClassNameTransformer)a;
         }
      }

   }

   public ClassNode getClassNode(String a) throws ClassNotFoundException, IOException {
      return a.getClassNode(a.getClassBytes(a, true), 0);
   }

   private ClassNode getClassNode(byte[] a, int a) {
      ClassNode a = new ClassNode();
      ClassReader a = new ClassReader(a);
      a.accept(a, a);
      return a;
   }

   public final String getSideName() {
      Iterator var1 = ((List)GlobalProperties.get("Tweaks")).iterator();

      ITweaker a;
      do {
         if (!var1.hasNext()) {
            String a = a.getSideName("net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");
            if (a != null) {
               return a;
            }

            a = a.getSideName("cpw.mods.fml.relauncher.FMLLaunchHandler", "side");
            if (a != null) {
               return a;
            }

            a = a.getSideName("com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");
            if (a != null) {
               return a;
            }

            return "UNKNOWN";
         }

         a = (ITweaker)var1.next();
         if (a.getClass().getName().endsWith(".common.launcher.FMLServerTweaker")) {
            return "SERVER";
         }
      } while(!a.getClass().getName().endsWith(".common.launcher.FMLTweaker"));

      return "CLIENT";
   }

   private String getSideName(String a, String a) {
      try {
         Class<?> a = Class.forName(a, false, Launch.classLoader);
         Method a = a.getDeclaredMethod(a);
         return ((Enum)a.invoke((Object)null)).name();
      } catch (Exception var5) {
         return null;
      }
   }

   private static int findInStackTrace(String a, String a) {
      Thread a = Thread.currentThread();
      if (!"main".equals(a.getName())) {
         return 0;
      } else {
         StackTraceElement[] a = a.getStackTrace();
         StackTraceElement[] var4 = a;
         int var5 = a.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            StackTraceElement a = var4[var6];
            if (a.equals(a.getClassName()) && a.equals(a.getMethodName())) {
               return a.getLineNumber();
            }
         }

         return 0;
      }
   }
}

package org.spongepowered.asm.mixin.transformer.ext.extensions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.perf.Profiler;

public class ExtensionClassExporter implements IExtension {
   private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
   private static final String EXPORT_CLASS_DIR = "class";
   private static final String EXPORT_JAVA_DIR = "java";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final File classExportDir;
   private final IDecompiler decompiler;

   public ExtensionClassExporter(MixinEnvironment a) {
      a.classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
      a.decompiler = a.initDecompiler(a, new File(Constants.DEBUG_OUTPUT_DIR, "java"));

      try {
         FileUtils.deleteDirectory(a.classExportDir);
      } catch (IOException var3) {
         logger.warn("Error cleaning class output directory: {}", new Object[]{var3.getMessage()});
      }

   }

   public boolean isDecompilerActive() {
      return a.decompiler != null;
   }

   private IDecompiler initDecompiler(MixinEnvironment a, File a) {
      if (!a.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
         return null;
      } else {
         try {
            boolean a = a.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
            logger.info("Attempting to load Fernflower decompiler{}", new Object[]{a ? " (Threaded mode)" : ""});
            String a = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (a ? "Async" : "");
            Class<? extends IDecompiler> a = Class.forName(a);
            Constructor<? extends IDecompiler> a = a.getDeclaredConstructor(File.class);
            IDecompiler a = (IDecompiler)a.newInstance(a);
            logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[]{a ? " in a separate thread" : ""});
            return a;
         } catch (Throwable var8) {
            logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[]{var8.getClass().getSimpleName(), var8.getMessage()});
            return null;
         }
      }
   }

   private String prepareFilter(String a) {
      a = "^\\Q" + a.replace("**", "\u0081").replace("*", "\u0082").replace("?", "\u0083") + "\\E$";
      return a.replace("\u0081", "\\E.*\\Q").replace("\u0082", "\\E[^\\.]+\\Q").replace("\u0083", "\\E.\\Q").replace("\\Q\\E", "");
   }

   private boolean applyFilter(String a, String a) {
      return Pattern.compile(a.prepareFilter(a), 2).matcher(a).matches();
   }

   public boolean checkActive(MixinEnvironment a1) {
      return true;
   }

   public void preApply(ITargetClassContext a1) {
   }

   public void postApply(ITargetClassContext a1) {
   }

   public void export(MixinEnvironment a, String a, boolean a, byte[] a) {
      if (a || a.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
         String a = a.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
         if (a || a == null || a.applyFilter(a, a)) {
            Profiler.Section a = MixinEnvironment.getProfiler().begin("debug.export");
            File a = a.dumpClass(a.replace('.', '/'), a);
            if (a.decompiler != null) {
               a.decompiler.decompile(a);
            }

            a.end();
         }
      }

   }

   public File dumpClass(String a, byte[] a) {
      File a = new File(a.classExportDir, a + ".class");

      try {
         FileUtils.writeByteArrayToFile(a, a);
      } catch (IOException var5) {
      }

      return a;
   }
}

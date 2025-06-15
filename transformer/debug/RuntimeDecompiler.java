package org.spongepowered.asm.mixin.transformer.debug;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.jar.Manifest;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger.Severity;
import org.jetbrains.java.decompiler.util.InterpreterUtil;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;

public class RuntimeDecompiler extends IFernflowerLogger implements IDecompiler, IResultSaver {
   private static final Level[] SEVERITY_LEVELS;
   private final Map<String, Object> options = ImmutableMap.builder().put("din", "0").put("rbr", "0").put("dgs", "1").put("asc", "1").put("den", "1").put("hdc", "1").put("ind", "    ").build();
   private final File outputPath;
   protected final Logger logger = LogManager.getLogger("fernflower");

   public RuntimeDecompiler(File a) {
      a.outputPath = a;
      if (a.outputPath.exists()) {
         try {
            FileUtils.deleteDirectory(a.outputPath);
         } catch (IOException var3) {
            a.logger.warn("Error cleaning output directory: {}", new Object[]{var3.getMessage()});
         }
      }

   }

   public void decompile(File a) {
      try {
         Fernflower a = new Fernflower(new IBytecodeProvider() {
            private byte[] byteCode;

            public byte[] getBytecode(String axx, String a2) throws IOException {
               if (ax.byteCode == null) {
                  ax.byteCode = InterpreterUtil.getBytes(new File(axx));
               }

               return ax.byteCode;
            }
         }, a, a.options, a);
         a.getStructContext().addSpace(a, true);
         a.decompileContext();
      } catch (Throwable var3) {
         a.logger.warn("Decompilation error while processing {}", new Object[]{a.getName()});
      }

   }

   public void saveFolder(String a1) {
   }

   public void saveClassFile(String a1, String a, String a3, String a, int[] a5) {
      File a = new File(a.outputPath, a + ".java");
      a.getParentFile().mkdirs();

      try {
         a.logger.info("Writing {}", new Object[]{a.getAbsolutePath()});
         Files.write(a, a, Charsets.UTF_8);
      } catch (IOException var8) {
         a.writeMessage("Cannot write source file " + a, (Throwable)var8);
      }

   }

   public void startReadingClass(String a) {
      a.logger.info("Decompiling {}", new Object[]{a});
   }

   public void writeMessage(String a, Severity a) {
      a.logger.log(SEVERITY_LEVELS[a.ordinal()], a);
   }

   public void writeMessage(String a, Throwable a) {
      a.logger.warn("{} {}: {}", new Object[]{a, a.getClass().getSimpleName(), a.getMessage()});
   }

   public void writeMessage(String a, Severity a, Throwable a) {
      a.logger.log(SEVERITY_LEVELS[a.ordinal()], a, a);
   }

   public void copyFile(String a1, String a2, String a3) {
   }

   public void createArchive(String a1, String a2, Manifest a3) {
   }

   public void saveDirEntry(String a1, String a2, String a3) {
   }

   public void copyEntry(String a1, String a2, String a3, String a4) {
   }

   public void saveClassEntry(String a1, String a2, String a3, String a4, String a5) {
   }

   public void closeArchive(String a1, String a2) {
   }

   static {
      SEVERITY_LEVELS = new Level[]{Level.TRACE, Level.INFO, Level.WARN, Level.ERROR};
   }
}

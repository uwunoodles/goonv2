package org.spongepowered.asm.mixin.transformer.ext.extensions;

import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class ExtensionCheckInterfaces implements IExtension {
   private static final String AUDIT_DIR = "audit";
   private static final String IMPL_REPORT_FILENAME = "mixin_implementation_report";
   private static final String IMPL_REPORT_CSV_FILENAME = "mixin_implementation_report.csv";
   private static final String IMPL_REPORT_TXT_FILENAME = "mixin_implementation_report.txt";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final File csv;
   private final File report;
   private final Multimap<ClassInfo, ClassInfo.Method> interfaceMethods = HashMultimap.create();
   private boolean strict;

   public ExtensionCheckInterfaces() {
      File a = new File(Constants.DEBUG_OUTPUT_DIR, "audit");
      a.mkdirs();
      a.csv = new File(a, "mixin_implementation_report.csv");
      a.report = new File(a, "mixin_implementation_report.txt");

      try {
         Files.write("Class,Method,Signature,Interface\n", a.csv, Charsets.ISO_8859_1);
      } catch (IOException var4) {
      }

      try {
         String a = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
         Files.write("Mixin Implementation Report generated on " + a + "\n", a.report, Charsets.ISO_8859_1);
      } catch (IOException var3) {
      }

   }

   public boolean checkActive(MixinEnvironment a) {
      a.strict = a.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS_STRICT);
      return a.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS);
   }

   public void preApply(ITargetClassContext a) {
      ClassInfo a = a.getClassInfo();
      Iterator var3 = a.getInterfaceMethods(false).iterator();

      while(var3.hasNext()) {
         ClassInfo.Method a = (ClassInfo.Method)var3.next();
         a.interfaceMethods.put(a, a);
      }

   }

   public void postApply(ITargetClassContext a) {
      ClassInfo a = a.getClassInfo();
      if (a.isAbstract() && !a.strict) {
         logger.info("{} is skipping abstract target {}", new Object[]{a.getClass().getSimpleName(), a});
      } else {
         String a = a.getName().replace('/', '.');
         int a = 0;
         PrettyPrinter a = new PrettyPrinter();
         a.add("Class: %s", a).hr();
         a.add("%-32s %-47s  %s", "Return Type", "Missing Method", "From Interface").hr();
         Set<ClassInfo.Method> a = a.getInterfaceMethods(true);
         Set<ClassInfo.Method> a = new HashSet(a.getSuperClass().getInterfaceMethods(true));
         a.addAll(a.interfaceMethods.removeAll(a));
         Iterator var8 = a.iterator();

         while(true) {
            ClassInfo.Method a;
            ClassInfo.Method a;
            do {
               if (!var8.hasNext()) {
                  if (a > 0) {
                     a.hr().add("%82s%s: %d", "", "Total unimplemented", a);
                     a.print(System.err);
                     a.appendToTextReport(a);
                  }

                  return;
               }

               a = (ClassInfo.Method)var8.next();
               a = a.findMethodInHierarchy(a.getName(), a.getDesc(), ClassInfo.SearchType.ALL_CLASSES, ClassInfo.Traversal.ALL);
            } while(a != null && !a.isAbstract());

            if (!a.contains(a)) {
               if (a > 0) {
                  a.add();
               }

               SignaturePrinter a = (new SignaturePrinter(a.getName(), a.getDesc())).setModifiers("");
               String a = a.getOwner().getName().replace('/', '.');
               ++a;
               a.add("%-32s%s", a.getReturnType(), a);
               a.add("%-80s  %s", "", a);
               a.appendToCSVReport(a, a, a);
            }
         }
      }
   }

   public void export(MixinEnvironment a1, String a2, boolean a3, byte[] a4) {
   }

   private void appendToCSVReport(String a, ClassInfo.Method a, String a) {
      try {
         Files.append(String.format("%s,%s,%s,%s\n", a, a.getName(), a.getDesc(), a), a.csv, Charsets.ISO_8859_1);
      } catch (IOException var5) {
      }

   }

   private void appendToTextReport(PrettyPrinter a) {
      FileOutputStream a = null;

      try {
         a = new FileOutputStream(a.report, true);
         PrintStream a = new PrintStream(a);
         a.print("\n");
         a.print(a);
      } catch (Exception var7) {
      } finally {
         IOUtils.closeQuietly(a);
      }

   }
}

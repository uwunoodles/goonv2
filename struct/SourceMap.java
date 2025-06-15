package org.spongepowered.asm.mixin.struct;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.util.Bytecode;

public class SourceMap {
   private static final String DEFAULT_STRATUM = "Mixin";
   private static final String NEWLINE = "\n";
   private final String sourceFile;
   private final Map<String, SourceMap.Stratum> strata = new LinkedHashMap();
   private int nextLineOffset = 1;
   private String defaultStratum = "Mixin";

   public SourceMap(String a) {
      a.sourceFile = a;
   }

   public String getSourceFile() {
      return a.sourceFile;
   }

   public String getPseudoGeneratedSourceFile() {
      return a.sourceFile.replace(".java", "$mixin.java");
   }

   public SourceMap.File addFile(ClassNode a) {
      return a.addFile(a.defaultStratum, a);
   }

   public SourceMap.File addFile(String a, ClassNode a) {
      return a.addFile(a, a.sourceFile, a.name + ".java", Bytecode.getMaxLineNumber(a, 500, 50));
   }

   public SourceMap.File addFile(String a, String a, int a) {
      return a.addFile(a.defaultStratum, a, a, a);
   }

   public SourceMap.File addFile(String a, String a, String a, int a) {
      SourceMap.Stratum a = (SourceMap.Stratum)a.strata.get(a);
      if (a == null) {
         a = new SourceMap.Stratum(a);
         a.strata.put(a, a);
      }

      SourceMap.File a = a.addFile(a.nextLineOffset, a, a, a);
      a.nextLineOffset += a;
      return a;
   }

   public String toString() {
      StringBuilder a = new StringBuilder();
      a.appendTo(a);
      return a.toString();
   }

   private void appendTo(StringBuilder a) {
      a.append("SMAP").append("\n");
      a.append(a.getSourceFile()).append("\n");
      a.append(a.defaultStratum).append("\n");
      Iterator var2 = a.strata.values().iterator();

      while(var2.hasNext()) {
         SourceMap.Stratum a = (SourceMap.Stratum)var2.next();
         a.appendTo(a);
      }

      a.append("*E").append("\n");
   }

   static class Stratum {
      private static final String STRATUM_MARK = "*S";
      private static final String FILE_MARK = "*F";
      private static final String LINES_MARK = "*L";
      public final String name;
      private final Map<String, SourceMap.File> files = new LinkedHashMap();

      public Stratum(String a) {
         a.name = a;
      }

      public SourceMap.File addFile(int a, int a, String a, String a) {
         SourceMap.File a = (SourceMap.File)a.files.get(a);
         if (a == null) {
            a = new SourceMap.File(a.files.size() + 1, a, a, a, a);
            a.files.put(a, a);
         }

         return a;
      }

      void appendTo(StringBuilder a) {
         a.append("*S").append(" ").append(a.name).append("\n");
         a.append("*F").append("\n");
         Iterator var2 = a.files.values().iterator();

         SourceMap.File a;
         while(var2.hasNext()) {
            a = (SourceMap.File)var2.next();
            a.appendFile(a);
         }

         a.append("*L").append("\n");
         var2 = a.files.values().iterator();

         while(var2.hasNext()) {
            a = (SourceMap.File)var2.next();
            a.appendLines(a);
         }

      }
   }

   public static class File {
      public final int id;
      public final int lineOffset;
      public final int size;
      public final String sourceFileName;
      public final String sourceFilePath;

      public File(int a, int a, int a, String a) {
         this(a, a, a, a, (String)null);
      }

      public File(int a, int a, int a, String a, String a) {
         a.id = a;
         a.lineOffset = a;
         a.size = a;
         a.sourceFileName = a;
         a.sourceFilePath = a;
      }

      public void applyOffset(ClassNode a) {
         Iterator var2 = a.methods.iterator();

         while(var2.hasNext()) {
            MethodNode a = (MethodNode)var2.next();
            a.applyOffset(a);
         }

      }

      public void applyOffset(MethodNode a) {
         ListIterator a = a.instructions.iterator();

         while(a.hasNext()) {
            AbstractInsnNode a = (AbstractInsnNode)a.next();
            if (a instanceof LineNumberNode) {
               ((LineNumberNode)a).line += a.lineOffset - 1;
            }
         }

      }

      void appendFile(StringBuilder a) {
         if (a.sourceFilePath != null) {
            a.append("+ ").append(a.id).append(" ").append(a.sourceFileName).append("\n");
            a.append(a.sourceFilePath).append("\n");
         } else {
            a.append(a.id).append(" ").append(a.sourceFileName).append("\n");
         }

      }

      public void appendLines(StringBuilder a) {
         a.append("1#").append(a.id).append(",").append(a.size).append(":").append(a.lineOffset).append("\n");
      }
   }
}

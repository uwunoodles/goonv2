package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;

public class CheckClassAdapter extends ClassVisitor {
   private int version;
   private boolean start;
   private boolean source;
   private boolean outer;
   private boolean end;
   private Map<Label, Integer> labels;
   private boolean checkDataFlow;

   public static void main(String[] a) throws Exception {
      if (a.length != 1) {
         System.err.println("Verifies the given class.");
         System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
      } else {
         ClassReader a;
         if (a[0].endsWith(".class")) {
            a = new ClassReader(new FileInputStream(a[0]));
         } else {
            a = new ClassReader(a[0]);
         }

         verify(a, false, new PrintWriter(System.err));
      }
   }

   public static void verify(ClassReader a, ClassLoader a, boolean a, PrintWriter a) {
      ClassNode a = new ClassNode();
      a.accept(new CheckClassAdapter(a, false), 2);
      Type a = a.superName == null ? null : Type.getObjectType(a.superName);
      List<MethodNode> a = a.methods;
      List<Type> a = new ArrayList();
      Iterator a = a.interfaces.iterator();

      while(a.hasNext()) {
         a.add(Type.getObjectType((String)a.next()));
      }

      for(int a = 0; a < a.size(); ++a) {
         MethodNode a = (MethodNode)a.get(a);
         SimpleVerifier a = new SimpleVerifier(Type.getObjectType(a.name), a, a, (a.access & 512) != 0);
         Analyzer<BasicValue> a = new Analyzer(a);
         if (a != null) {
            a.setClassLoader(a);
         }

         try {
            a.analyze(a.name, a);
            if (!a) {
               continue;
            }
         } catch (Exception var13) {
            var13.printStackTrace(a);
         }

         printAnalyzerResult(a, a, a);
      }

      a.flush();
   }

   public static void verify(ClassReader a, boolean a, PrintWriter a) {
      verify(a, (ClassLoader)null, a, a);
   }

   static void printAnalyzerResult(MethodNode a, Analyzer<BasicValue> a, PrintWriter a) {
      Frame<BasicValue>[] a = a.getFrames();
      Textifier a = new Textifier();
      TraceMethodVisitor a = new TraceMethodVisitor(a);
      a.println(a.name + a.desc);

      int a;
      for(a = 0; a < a.instructions.size(); ++a) {
         a.instructions.get(a).accept(a);
         StringBuilder a = new StringBuilder();
         Frame<BasicValue> a = a[a];
         if (a == null) {
            a.append('?');
         } else {
            int a;
            for(a = 0; a < a.getLocals(); ++a) {
               a.append(getShortName(((BasicValue)a.getLocal(a)).toString())).append(' ');
            }

            a.append(" : ");

            for(a = 0; a < a.getStackSize(); ++a) {
               a.append(getShortName(((BasicValue)a.getStack(a)).toString())).append(' ');
            }
         }

         while(a.length() < a.maxStack + a.maxLocals + 1) {
            a.append(' ');
         }

         a.print(Integer.toString(a + 100000).substring(1));
         a.print(" " + a + " : " + a.text.get(a.text.size() - 1));
      }

      for(a = 0; a < a.tryCatchBlocks.size(); ++a) {
         ((TryCatchBlockNode)a.tryCatchBlocks.get(a)).accept(a);
         a.print(" " + a.text.get(a.text.size() - 1));
      }

      a.println();
   }

   private static String getShortName(String a) {
      int a = a.lastIndexOf(47);
      int a = a.length();
      if (a.charAt(a - 1) == ';') {
         --a;
      }

      return a == -1 ? a : a.substring(a + 1, a);
   }

   public CheckClassAdapter(ClassVisitor a) {
      this(a, true);
   }

   public CheckClassAdapter(ClassVisitor a, boolean a) {
      this(327680, a, a);
      if (a.getClass() != CheckClassAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckClassAdapter(int a, ClassVisitor a, boolean a) {
      super(a, a);
      a.labels = new HashMap();
      a.checkDataFlow = a;
   }

   public void visit(int a, int a, String a, String a, String a, String[] a) {
      if (a.start) {
         throw new IllegalStateException("visit must be called only once");
      } else {
         a.start = true;
         a.checkState();
         checkAccess(a, 423473);
         if (a == null || !a.endsWith("package-info")) {
            CheckMethodAdapter.checkInternalName(a, "class name");
         }

         if ("java/lang/Object".equals(a)) {
            if (a != null) {
               throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
            }
         } else {
            CheckMethodAdapter.checkInternalName(a, "super class name");
         }

         if (a != null) {
            checkClassSignature(a);
         }

         if ((a & 512) != 0 && !"java/lang/Object".equals(a)) {
            throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
         } else {
            if (a != null) {
               for(int a = 0; a < a.length; ++a) {
                  CheckMethodAdapter.checkInternalName(a[a], "interface name at index " + a);
               }
            }

            a.version = a;
            super.visit(a, a, a, a, a, a);
         }
      }
   }

   public void visitSource(String a, String a) {
      a.checkState();
      if (a.source) {
         throw new IllegalStateException("visitSource can be called only once.");
      } else {
         a.source = true;
         super.visitSource(a, a);
      }
   }

   public void visitOuterClass(String a, String a, String a) {
      a.checkState();
      if (a.outer) {
         throw new IllegalStateException("visitOuterClass can be called only once.");
      } else {
         a.outer = true;
         if (a == null) {
            throw new IllegalArgumentException("Illegal outer class owner");
         } else {
            if (a != null) {
               CheckMethodAdapter.checkMethodDesc(a);
            }

            super.visitOuterClass(a, a, a);
         }
      }
   }

   public void visitInnerClass(String a, String a, String a, int a) {
      a.checkState();
      CheckMethodAdapter.checkInternalName(a, "class name");
      if (a != null) {
         CheckMethodAdapter.checkInternalName(a, "outer class name");
      }

      if (a != null) {
         int a;
         for(a = 0; a < a.length() && Character.isDigit(a.charAt(a)); ++a) {
         }

         if (a == 0 || a < a.length()) {
            CheckMethodAdapter.checkIdentifier(a, a, -1, "inner class name");
         }
      }

      checkAccess(a, 30239);
      super.visitInnerClass(a, a, a, a);
   }

   public FieldVisitor visitField(int a, String a, String a, String a, Object a) {
      a.checkState();
      checkAccess(a, 413919);
      CheckMethodAdapter.checkUnqualifiedName(a.version, a, "field name");
      CheckMethodAdapter.checkDesc(a, false);
      if (a != null) {
         checkFieldSignature(a);
      }

      if (a != null) {
         CheckMethodAdapter.checkConstant(a);
      }

      FieldVisitor a = super.visitField(a, a, a, a, a);
      return new CheckFieldAdapter(a);
   }

   public MethodVisitor visitMethod(int a, String a, String a, String a, String[] a) {
      a.checkState();
      checkAccess(a, 400895);
      if (!"<init>".equals(a) && !"<clinit>".equals(a)) {
         CheckMethodAdapter.checkMethodIdentifier(a.version, a, "method name");
      }

      CheckMethodAdapter.checkMethodDesc(a);
      if (a != null) {
         checkMethodSignature(a);
      }

      if (a != null) {
         for(int a = 0; a < a.length; ++a) {
            CheckMethodAdapter.checkInternalName(a[a], "exception name at index " + a);
         }
      }

      CheckMethodAdapter a;
      if (a.checkDataFlow) {
         a = new CheckMethodAdapter(a, a, a, super.visitMethod(a, a, a, a, a), a.labels);
      } else {
         a = new CheckMethodAdapter(super.visitMethod(a, a, a, a, a), a.labels);
      }

      a.version = a.version;
      return a;
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      a.checkState();
      CheckMethodAdapter.checkDesc(a, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(a, a));
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      a.checkState();
      int a = a >>> 24;
      if (a != 0 && a != 17 && a != 16) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(a));
      } else {
         checkTypeRefAndPath(a, a);
         CheckMethodAdapter.checkDesc(a, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(a, a, a, a));
      }
   }

   public void visitAttribute(Attribute a) {
      a.checkState();
      if (a == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(a);
      }
   }

   public void visitEnd() {
      a.checkState();
      a.end = true;
      super.visitEnd();
   }

   private void checkState() {
      if (!a.start) {
         throw new IllegalStateException("Cannot visit member before visit has been called.");
      } else if (a.end) {
         throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
      }
   }

   static void checkAccess(int a, int a) {
      if ((a & ~a) != 0) {
         throw new IllegalArgumentException("Invalid access flags: " + a);
      } else {
         int a = (a & 1) == 0 ? 0 : 1;
         int a = (a & 2) == 0 ? 0 : 1;
         int a = (a & 4) == 0 ? 0 : 1;
         if (a + a + a > 1) {
            throw new IllegalArgumentException("public private and protected are mutually exclusive: " + a);
         } else {
            int a = (a & 16) == 0 ? 0 : 1;
            int a = (a & 1024) == 0 ? 0 : 1;
            if (a + a > 1) {
               throw new IllegalArgumentException("final and abstract are mutually exclusive: " + a);
            }
         }
      }
   }

   public static void checkClassSignature(String a) {
      int a = 0;
      if (getChar(a, 0) == '<') {
         a = checkFormalTypeParameters(a, a);
      }

      for(a = checkClassTypeSignature(a, a); getChar(a, a) == 'L'; a = checkClassTypeSignature(a, a)) {
      }

      if (a != a.length()) {
         throw new IllegalArgumentException(a + ": error at index " + a);
      }
   }

   public static void checkMethodSignature(String a) {
      int a = 0;
      if (getChar(a, 0) == '<') {
         a = checkFormalTypeParameters(a, a);
      }

      for(a = checkChar('(', a, a); "ZCBSIFJDL[T".indexOf(getChar(a, a)) != -1; a = checkTypeSignature(a, a)) {
      }

      a = checkChar(')', a, a);
      if (getChar(a, a) == 'V') {
         ++a;
      } else {
         a = checkTypeSignature(a, a);
      }

      while(getChar(a, a) == '^') {
         ++a;
         if (getChar(a, a) == 'L') {
            a = checkClassTypeSignature(a, a);
         } else {
            a = checkTypeVariableSignature(a, a);
         }
      }

      if (a != a.length()) {
         throw new IllegalArgumentException(a + ": error at index " + a);
      }
   }

   public static void checkFieldSignature(String a) {
      int a = checkFieldTypeSignature(a, 0);
      if (a != a.length()) {
         throw new IllegalArgumentException(a + ": error at index " + a);
      }
   }

   static void checkTypeRefAndPath(int a, TypePath a) {
      int a = false;
      int a;
      switch(a >>> 24) {
      case 0:
      case 1:
      case 22:
         a = -65536;
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      default:
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(a >>> 24));
      case 16:
      case 17:
      case 18:
      case 23:
      case 66:
         a = -256;
         break;
      case 19:
      case 20:
      case 21:
      case 64:
      case 65:
      case 67:
      case 68:
      case 69:
      case 70:
         a = -16777216;
         break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         a = -16776961;
      }

      if ((a & ~a) != 0) {
         throw new IllegalArgumentException("Invalid type reference 0x" + Integer.toHexString(a));
      } else {
         if (a != null) {
            for(int a = 0; a < a.getLength(); ++a) {
               int a = a.getStep(a);
               if (a != 0 && a != 1 && a != 3 && a != 2) {
                  throw new IllegalArgumentException("Invalid type path step " + a + " in " + a);
               }

               if (a != 3 && a.getStepArgument(a) != 0) {
                  throw new IllegalArgumentException("Invalid type path step argument for step " + a + " in " + a);
               }
            }
         }

      }
   }

   private static int checkFormalTypeParameters(String a, int a) {
      a = checkChar('<', a, a);

      for(a = checkFormalTypeParameter(a, a); getChar(a, a) != '>'; a = checkFormalTypeParameter(a, a)) {
      }

      return a + 1;
   }

   private static int checkFormalTypeParameter(String a, int a) {
      a = checkIdentifier(a, a);
      a = checkChar(':', a, a);
      if ("L[T".indexOf(getChar(a, a)) != -1) {
         a = checkFieldTypeSignature(a, a);
      }

      while(getChar(a, a) == ':') {
         a = checkFieldTypeSignature(a, a + 1);
      }

      return a;
   }

   private static int checkFieldTypeSignature(String a, int a) {
      switch(getChar(a, a)) {
      case 'L':
         return checkClassTypeSignature(a, a);
      case '[':
         return checkTypeSignature(a, a + 1);
      default:
         return checkTypeVariableSignature(a, a);
      }
   }

   private static int checkClassTypeSignature(String a, int a) {
      a = checkChar('L', a, a);

      for(a = checkIdentifier(a, a); getChar(a, a) == '/'; a = checkIdentifier(a, a + 1)) {
      }

      if (getChar(a, a) == '<') {
         a = checkTypeArguments(a, a);
      }

      while(getChar(a, a) == '.') {
         a = checkIdentifier(a, a + 1);
         if (getChar(a, a) == '<') {
            a = checkTypeArguments(a, a);
         }
      }

      return checkChar(';', a, a);
   }

   private static int checkTypeArguments(String a, int a) {
      a = checkChar('<', a, a);

      for(a = checkTypeArgument(a, a); getChar(a, a) != '>'; a = checkTypeArgument(a, a)) {
      }

      return a + 1;
   }

   private static int checkTypeArgument(String a, int a) {
      char a = getChar(a, a);
      if (a == '*') {
         return a + 1;
      } else {
         if (a == '+' || a == '-') {
            ++a;
         }

         return checkFieldTypeSignature(a, a);
      }
   }

   private static int checkTypeVariableSignature(String a, int a) {
      a = checkChar('T', a, a);
      a = checkIdentifier(a, a);
      return checkChar(';', a, a);
   }

   private static int checkTypeSignature(String a, int a) {
      switch(getChar(a, a)) {
      case 'B':
      case 'C':
      case 'D':
      case 'F':
      case 'I':
      case 'J':
      case 'S':
      case 'Z':
         return a + 1;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'L':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'T':
      case 'U':
      case 'V':
      case 'W':
      case 'X':
      case 'Y':
      default:
         return checkFieldTypeSignature(a, a);
      }
   }

   private static int checkIdentifier(String a, int a) {
      if (!Character.isJavaIdentifierStart(getChar(a, a))) {
         throw new IllegalArgumentException(a + ": identifier expected at index " + a);
      } else {
         ++a;

         while(Character.isJavaIdentifierPart(getChar(a, a))) {
            ++a;
         }

         return a;
      }
   }

   private static int checkChar(char a, String a, int a) {
      if (getChar(a, a) == a) {
         return a + 1;
      } else {
         throw new IllegalArgumentException(a + ": '" + a + "' expected at index " + a);
      }
   }

   private static char getChar(String a, int a) {
      return a < a.length() ? a.charAt(a) : '\u0000';
   }
}

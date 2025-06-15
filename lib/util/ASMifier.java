package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

public class ASMifier extends Printer {
   protected final String name;
   protected final int id;
   protected Map<Label, String> labelNames;
   private static final int ACCESS_CLASS = 262144;
   private static final int ACCESS_FIELD = 524288;
   private static final int ACCESS_INNER = 1048576;

   public ASMifier() {
      this(327680, "cw", 0);
      if (a.getClass() != ASMifier.class) {
         throw new IllegalStateException();
      }
   }

   protected ASMifier(int a, String a, int a) {
      super(a);
      a.name = a;
      a.id = a;
   }

   public static void main(String[] a) throws Exception {
      int a = 0;
      int a = 2;
      boolean a = true;
      if (a.length < 1 || a.length > 2) {
         a = false;
      }

      if (a && "-debug".equals(a[0])) {
         a = 1;
         a = 0;
         if (a.length != 2) {
            a = false;
         }
      }

      if (!a) {
         System.err.println("Prints the ASM code to generate the given class.");
         System.err.println("Usage: ASMifier [-debug] <fully qualified class name or class file name>");
      } else {
         ClassReader a;
         if (!a[a].endsWith(".class") && a[a].indexOf(92) <= -1 && a[a].indexOf(47) <= -1) {
            a = new ClassReader(a[a]);
         } else {
            a = new ClassReader(new FileInputStream(a[a]));
         }

         a.accept(new TraceClassVisitor((ClassVisitor)null, new ASMifier(), new PrintWriter(System.out)), a);
      }
   }

   public void visit(int a, int a, String a, String a, String a, String[] a) {
      int a = a.lastIndexOf(47);
      String a;
      if (a == -1) {
         a = a;
      } else {
         a.text.add("package asm." + a.substring(0, a).replace('/', '.') + ";\n");
         a = a.substring(a + 1);
      }

      a.text.add("import java.util.*;\n");
      a.text.add("import org.objectweb.asm.*;\n");
      a.text.add("public class " + a + "Dump implements Opcodes {\n\n");
      a.text.add("public static byte[] dump () throws Exception {\n\n");
      a.text.add("ClassWriter cw = new ClassWriter(0);\n");
      a.text.add("FieldVisitor fv;\n");
      a.text.add("MethodVisitor mv;\n");
      a.text.add("AnnotationVisitor av0;\n\n");
      a.buf.setLength(0);
      a.buf.append("cw.visit(");
      switch(a) {
      case 46:
         a.buf.append("V1_2");
         break;
      case 47:
         a.buf.append("V1_3");
         break;
      case 48:
         a.buf.append("V1_4");
         break;
      case 49:
         a.buf.append("V1_5");
         break;
      case 50:
         a.buf.append("V1_6");
         break;
      case 51:
         a.buf.append("V1_7");
         break;
      case 196653:
         a.buf.append("V1_1");
         break;
      default:
         a.buf.append(a);
      }

      a.buf.append(", ");
      a.appendAccess(a | 262144);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      if (a != null && a.length > 0) {
         a.buf.append("new String[] {");

         for(int a = 0; a < a.length; ++a) {
            a.buf.append(a == 0 ? " " : ", ");
            a.appendConstant(a[a]);
         }

         a.buf.append(" }");
      } else {
         a.buf.append("null");
      }

      a.buf.append(");\n\n");
      a.text.add(a.buf.toString());
   }

   public void visitSource(String a, String a) {
      a.buf.setLength(0);
      a.buf.append("cw.visitSource(");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(");\n\n");
      a.text.add(a.buf.toString());
   }

   public void visitOuterClass(String a, String a, String a) {
      a.buf.setLength(0);
      a.buf.append("cw.visitOuterClass(");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(");\n\n");
      a.text.add(a.buf.toString());
   }

   public ASMifier visitClassAnnotation(String a, boolean a) {
      return a.visitAnnotation(a, a);
   }

   public ASMifier visitClassTypeAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation(a, a, a, a);
   }

   public void visitClassAttribute(Attribute a) {
      a.visitAttribute(a);
   }

   public void visitInnerClass(String a, String a, String a, int a) {
      a.buf.setLength(0);
      a.buf.append("cw.visitInnerClass(");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendAccess(a | 1048576);
      a.buf.append(");\n\n");
      a.text.add(a.buf.toString());
   }

   public ASMifier visitField(int a, String a, String a, String a, Object a) {
      a.buf.setLength(0);
      a.buf.append("{\n");
      a.buf.append("fv = cw.visitField(");
      a.appendAccess(a | 524288);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("fv", 0);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public ASMifier visitMethod(int a, String a, String a, String a, String[] a) {
      a.buf.setLength(0);
      a.buf.append("{\n");
      a.buf.append("mv = cw.visitMethod(");
      a.appendAccess(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      if (a != null && a.length > 0) {
         a.buf.append("new String[] {");

         for(int a = 0; a < a.length; ++a) {
            a.buf.append(a == 0 ? " " : ", ");
            a.appendConstant(a[a]);
         }

         a.buf.append(" }");
      } else {
         a.buf.append("null");
      }

      a.buf.append(");\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("mv", 0);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public void visitClassEnd() {
      a.text.add("cw.visitEnd();\n\n");
      a.text.add("return cw.toByteArray();\n");
      a.text.add("}\n");
      a.text.add("}\n");
   }

   public void visit(String a, Object a) {
      a.buf.setLength(0);
      a.buf.append("av").append(a.id).append(".visit(");
      appendConstant(a.buf, a);
      a.buf.append(", ");
      appendConstant(a.buf, a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitEnum(String a, String a, String a) {
      a.buf.setLength(0);
      a.buf.append("av").append(a.id).append(".visitEnum(");
      appendConstant(a.buf, a);
      a.buf.append(", ");
      appendConstant(a.buf, a);
      a.buf.append(", ");
      appendConstant(a.buf, a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public ASMifier visitAnnotation(String a, String a) {
      a.buf.setLength(0);
      a.buf.append("{\n");
      a.buf.append("AnnotationVisitor av").append(a.id + 1).append(" = av");
      a.buf.append(a.id).append(".visitAnnotation(");
      appendConstant(a.buf, a);
      a.buf.append(", ");
      appendConstant(a.buf, a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("av", a.id + 1);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public ASMifier visitArray(String a) {
      a.buf.setLength(0);
      a.buf.append("{\n");
      a.buf.append("AnnotationVisitor av").append(a.id + 1).append(" = av");
      a.buf.append(a.id).append(".visitArray(");
      appendConstant(a.buf, a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("av", a.id + 1);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public void visitAnnotationEnd() {
      a.buf.setLength(0);
      a.buf.append("av").append(a.id).append(".visitEnd();\n");
      a.text.add(a.buf.toString());
   }

   public ASMifier visitFieldAnnotation(String a, boolean a) {
      return a.visitAnnotation(a, a);
   }

   public ASMifier visitFieldTypeAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation(a, a, a, a);
   }

   public void visitFieldAttribute(Attribute a) {
      a.visitAttribute(a);
   }

   public void visitFieldEnd() {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitEnd();\n");
      a.text.add(a.buf.toString());
   }

   public void visitParameter(String a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitParameter(");
      appendString(a.buf, a);
      a.buf.append(", ");
      a.appendAccess(a);
      a.text.add(a.buf.append(");\n").toString());
   }

   public ASMifier visitAnnotationDefault() {
      a.buf.setLength(0);
      a.buf.append("{\n").append("av0 = ").append(a.name).append(".visitAnnotationDefault();\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("av", 0);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public ASMifier visitMethodAnnotation(String a, boolean a) {
      return a.visitAnnotation(a, a);
   }

   public ASMifier visitMethodTypeAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation(a, a, a, a);
   }

   public ASMifier visitParameterAnnotation(int a, String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append("{\n").append("av0 = ").append(a.name).append(".visitParameterAnnotation(").append(a).append(", ");
      a.appendConstant(a);
      a.buf.append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("av", 0);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public void visitMethodAttribute(Attribute a) {
      a.visitAttribute(a);
   }

   public void visitCode() {
      a.text.add(a.name + ".visitCode();\n");
   }

   public void visitFrame(int a, int a, Object[] a, int a, Object[] a) {
      a.buf.setLength(0);
      switch(a) {
      case -1:
      case 0:
         a.declareFrameTypes(a, a);
         a.declareFrameTypes(a, a);
         if (a == -1) {
            a.buf.append(a.name).append(".visitFrame(Opcodes.F_NEW, ");
         } else {
            a.buf.append(a.name).append(".visitFrame(Opcodes.F_FULL, ");
         }

         a.buf.append(a).append(", new Object[] {");
         a.appendFrameTypes(a, a);
         a.buf.append("}, ").append(a).append(", new Object[] {");
         a.appendFrameTypes(a, a);
         a.buf.append('}');
         break;
      case 1:
         a.declareFrameTypes(a, a);
         a.buf.append(a.name).append(".visitFrame(Opcodes.F_APPEND,").append(a).append(", new Object[] {");
         a.appendFrameTypes(a, a);
         a.buf.append("}, 0, null");
         break;
      case 2:
         a.buf.append(a.name).append(".visitFrame(Opcodes.F_CHOP,").append(a).append(", null, 0, null");
         break;
      case 3:
         a.buf.append(a.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
         break;
      case 4:
         a.declareFrameTypes(1, a);
         a.buf.append(a.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
         a.appendFrameTypes(1, a);
         a.buf.append('}');
      }

      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitInsn(int a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitInsn(").append(OPCODES[a]).append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitIntInsn(int a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitIntInsn(").append(OPCODES[a]).append(", ").append(a == 188 ? TYPES[a] : Integer.toString(a)).append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitVarInsn(int a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitVarInsn(").append(OPCODES[a]).append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitTypeInsn(int a, String a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitTypeInsn(").append(OPCODES[a]).append(", ");
      a.appendConstant(a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitFieldInsn(int a, String a, String a, String a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitFieldInsn(").append(OPCODES[a]).append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int a, String a, String a, String a) {
      if (a.api >= 327680) {
         super.visitMethodInsn(a, a, a, a);
      } else {
         a.doVisitMethodInsn(a, a, a, a, a == 185);
      }
   }

   public void visitMethodInsn(int a, String a, String a, String a, boolean a) {
      if (a.api < 327680) {
         super.visitMethodInsn(a, a, a, a, a);
      } else {
         a.doVisitMethodInsn(a, a, a, a, a);
      }
   }

   private void doVisitMethodInsn(int a, String a, String a, String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitMethodInsn(").append(OPCODES[a]).append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.buf.append(a ? "true" : "false");
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitInvokeDynamicInsn(String a, String a, Handle a, Object... a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitInvokeDynamicInsn(");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", new Object[]{");

      for(int a = 0; a < a.length; ++a) {
         a.appendConstant(a[a]);
         if (a != a.length - 1) {
            a.buf.append(", ");
         }
      }

      a.buf.append("});\n");
      a.text.add(a.buf.toString());
   }

   public void visitJumpInsn(int a, Label a) {
      a.buf.setLength(0);
      a.declareLabel(a);
      a.buf.append(a.name).append(".visitJumpInsn(").append(OPCODES[a]).append(", ");
      a.appendLabel(a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitLabel(Label a) {
      a.buf.setLength(0);
      a.declareLabel(a);
      a.buf.append(a.name).append(".visitLabel(");
      a.appendLabel(a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitLdcInsn(Object a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitLdcInsn(");
      a.appendConstant(a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitIincInsn(int a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitIincInsn(").append(a).append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitTableSwitchInsn(int a, int a, Label a, Label... a) {
      a.buf.setLength(0);

      int a;
      for(a = 0; a < a.length; ++a) {
         a.declareLabel(a[a]);
      }

      a.declareLabel(a);
      a.buf.append(a.name).append(".visitTableSwitchInsn(").append(a).append(", ").append(a).append(", ");
      a.appendLabel(a);
      a.buf.append(", new Label[] {");

      for(a = 0; a < a.length; ++a) {
         a.buf.append(a == 0 ? " " : ", ");
         a.appendLabel(a[a]);
      }

      a.buf.append(" });\n");
      a.text.add(a.buf.toString());
   }

   public void visitLookupSwitchInsn(Label a, int[] a, Label[] a) {
      a.buf.setLength(0);

      int a;
      for(a = 0; a < a.length; ++a) {
         a.declareLabel(a[a]);
      }

      a.declareLabel(a);
      a.buf.append(a.name).append(".visitLookupSwitchInsn(");
      a.appendLabel(a);
      a.buf.append(", new int[] {");

      for(a = 0; a < a.length; ++a) {
         a.buf.append(a == 0 ? " " : ", ").append(a[a]);
      }

      a.buf.append(" }, new Label[] {");

      for(a = 0; a < a.length; ++a) {
         a.buf.append(a == 0 ? " " : ", ");
         a.appendLabel(a[a]);
      }

      a.buf.append(" });\n");
      a.text.add(a.buf.toString());
   }

   public void visitMultiANewArrayInsn(String a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitMultiANewArrayInsn(");
      a.appendConstant(a);
      a.buf.append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
   }

   public ASMifier visitInsnAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation("visitInsnAnnotation", a, a, a, a);
   }

   public void visitTryCatchBlock(Label a, Label a, Label a, String a) {
      a.buf.setLength(0);
      a.declareLabel(a);
      a.declareLabel(a);
      a.declareLabel(a);
      a.buf.append(a.name).append(".visitTryCatchBlock(");
      a.appendLabel(a);
      a.buf.append(", ");
      a.appendLabel(a);
      a.buf.append(", ");
      a.appendLabel(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public ASMifier visitTryCatchAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation("visitTryCatchAnnotation", a, a, a, a);
   }

   public void visitLocalVariable(String a, String a, String a, Label a, Label a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitLocalVariable(");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendConstant(a);
      a.buf.append(", ");
      a.appendLabel(a);
      a.buf.append(", ");
      a.appendLabel(a);
      a.buf.append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
   }

   public Printer visitLocalVariableAnnotation(int a, TypePath a, Label[] a, Label[] a, int[] a, String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append("{\n").append("av0 = ").append(a.name).append(".visitLocalVariableAnnotation(");
      a.buf.append(a);
      if (a == null) {
         a.buf.append(", null, ");
      } else {
         a.buf.append(", TypePath.fromString(\"").append(a).append("\"), ");
      }

      a.buf.append("new Label[] {");

      int a;
      for(a = 0; a < a.length; ++a) {
         a.buf.append(a == 0 ? " " : ", ");
         a.appendLabel(a[a]);
      }

      a.buf.append(" }, new Label[] {");

      for(a = 0; a < a.length; ++a) {
         a.buf.append(a == 0 ? " " : ", ");
         a.appendLabel(a[a]);
      }

      a.buf.append(" }, new int[] {");

      for(a = 0; a < a.length; ++a) {
         a.buf.append(a == 0 ? " " : ", ").append(a[a]);
      }

      a.buf.append(" }, ");
      a.appendConstant(a);
      a.buf.append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("av", 0);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public void visitLineNumber(int a, Label a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitLineNumber(").append(a).append(", ");
      a.appendLabel(a);
      a.buf.append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitMaxs(int a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitMaxs(").append(a).append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
   }

   public void visitMethodEnd() {
      a.buf.setLength(0);
      a.buf.append(a.name).append(".visitEnd();\n");
      a.text.add(a.buf.toString());
   }

   public ASMifier visitAnnotation(String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append("{\n").append("av0 = ").append(a.name).append(".visitAnnotation(");
      a.appendConstant(a);
      a.buf.append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("av", 0);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public ASMifier visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation("visitTypeAnnotation", a, a, a, a);
   }

   public ASMifier visitTypeAnnotation(String a, int a, TypePath a, String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append("{\n").append("av0 = ").append(a.name).append(".").append(a).append("(");
      a.buf.append(a);
      if (a == null) {
         a.buf.append(", null, ");
      } else {
         a.buf.append(", TypePath.fromString(\"").append(a).append("\"), ");
      }

      a.appendConstant(a);
      a.buf.append(", ").append(a).append(");\n");
      a.text.add(a.buf.toString());
      ASMifier a = a.createASMifier("av", 0);
      a.text.add(a.getText());
      a.text.add("}\n");
      return a;
   }

   public void visitAttribute(Attribute a) {
      a.buf.setLength(0);
      a.buf.append("// ATTRIBUTE ").append(a.type).append('\n');
      if (a instanceof ASMifiable) {
         if (a.labelNames == null) {
            a.labelNames = new HashMap();
         }

         a.buf.append("{\n");
         ((ASMifiable)a).asmify(a.buf, "attr", a.labelNames);
         a.buf.append(a.name).append(".visitAttribute(attr);\n");
         a.buf.append("}\n");
      }

      a.text.add(a.buf.toString());
   }

   protected ASMifier createASMifier(String a, int a) {
      return new ASMifier(327680, a, a);
   }

   void appendAccess(int a) {
      boolean a = true;
      if ((a & 1) != 0) {
         a.buf.append("ACC_PUBLIC");
         a = false;
      }

      if ((a & 2) != 0) {
         a.buf.append("ACC_PRIVATE");
         a = false;
      }

      if ((a & 4) != 0) {
         a.buf.append("ACC_PROTECTED");
         a = false;
      }

      if ((a & 16) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_FINAL");
         a = false;
      }

      if ((a & 8) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_STATIC");
         a = false;
      }

      if ((a & 32) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         if ((a & 262144) == 0) {
            a.buf.append("ACC_SYNCHRONIZED");
         } else {
            a.buf.append("ACC_SUPER");
         }

         a = false;
      }

      if ((a & 64) != 0 && (a & 524288) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_VOLATILE");
         a = false;
      }

      if ((a & 64) != 0 && (a & 262144) == 0 && (a & 524288) == 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_BRIDGE");
         a = false;
      }

      if ((a & 128) != 0 && (a & 262144) == 0 && (a & 524288) == 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_VARARGS");
         a = false;
      }

      if ((a & 128) != 0 && (a & 524288) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_TRANSIENT");
         a = false;
      }

      if ((a & 256) != 0 && (a & 262144) == 0 && (a & 524288) == 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_NATIVE");
         a = false;
      }

      if ((a & 16384) != 0 && ((a & 262144) != 0 || (a & 524288) != 0 || (a & 1048576) != 0)) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_ENUM");
         a = false;
      }

      if ((a & 8192) != 0 && ((a & 262144) != 0 || (a & 1048576) != 0)) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_ANNOTATION");
         a = false;
      }

      if ((a & 1024) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_ABSTRACT");
         a = false;
      }

      if ((a & 512) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_INTERFACE");
         a = false;
      }

      if ((a & 2048) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_STRICT");
         a = false;
      }

      if ((a & 4096) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_SYNTHETIC");
         a = false;
      }

      if ((a & 131072) != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_DEPRECATED");
         a = false;
      }

      if ((a & '耀') != 0) {
         if (!a) {
            a.buf.append(" + ");
         }

         a.buf.append("ACC_MANDATED");
         a = false;
      }

      if (a) {
         a.buf.append('0');
      }

   }

   protected void appendConstant(Object a) {
      appendConstant(a.buf, a);
   }

   static void appendConstant(StringBuffer a, Object a) {
      if (a == null) {
         a.append("null");
      } else if (a instanceof String) {
         appendString(a, (String)a);
      } else if (a instanceof Type) {
         a.append("Type.getType(\"");
         a.append(((Type)a).getDescriptor());
         a.append("\")");
      } else if (a instanceof Handle) {
         a.append("new Handle(");
         Handle a = (Handle)a;
         a.append("Opcodes.").append(HANDLE_TAG[a.getTag()]).append(", \"");
         a.append(a.getOwner()).append("\", \"");
         a.append(a.getName()).append("\", \"");
         a.append(a.getDesc()).append("\")");
      } else if (a instanceof Byte) {
         a.append("new Byte((byte)").append(a).append(')');
      } else if (a instanceof Boolean) {
         a.append((Boolean)a ? "Boolean.TRUE" : "Boolean.FALSE");
      } else if (a instanceof Short) {
         a.append("new Short((short)").append(a).append(')');
      } else if (a instanceof Character) {
         int a = (Character)a;
         a.append("new Character((char)").append(a).append(')');
      } else if (a instanceof Integer) {
         a.append("new Integer(").append(a).append(')');
      } else if (a instanceof Float) {
         a.append("new Float(\"").append(a).append("\")");
      } else if (a instanceof Long) {
         a.append("new Long(").append(a).append("L)");
      } else if (a instanceof Double) {
         a.append("new Double(\"").append(a).append("\")");
      } else {
         int a;
         if (a instanceof byte[]) {
            byte[] a = (byte[])((byte[])a);
            a.append("new byte[] {");

            for(a = 0; a < a.length; ++a) {
               a.append(a == 0 ? "" : ",").append(a[a]);
            }

            a.append('}');
         } else if (a instanceof boolean[]) {
            boolean[] a = (boolean[])((boolean[])a);
            a.append("new boolean[] {");

            for(a = 0; a < a.length; ++a) {
               a.append(a == 0 ? "" : ",").append(a[a]);
            }

            a.append('}');
         } else if (a instanceof short[]) {
            short[] a = (short[])((short[])a);
            a.append("new short[] {");

            for(a = 0; a < a.length; ++a) {
               a.append(a == 0 ? "" : ",").append("(short)").append(a[a]);
            }

            a.append('}');
         } else if (a instanceof char[]) {
            char[] a = (char[])((char[])a);
            a.append("new char[] {");

            for(a = 0; a < a.length; ++a) {
               a.append(a == 0 ? "" : ",").append("(char)").append(a[a]);
            }

            a.append('}');
         } else if (a instanceof int[]) {
            int[] a = (int[])((int[])a);
            a.append("new int[] {");

            for(a = 0; a < a.length; ++a) {
               a.append(a == 0 ? "" : ",").append(a[a]);
            }

            a.append('}');
         } else if (a instanceof long[]) {
            long[] a = (long[])((long[])a);
            a.append("new long[] {");

            for(a = 0; a < a.length; ++a) {
               a.append(a == 0 ? "" : ",").append(a[a]).append('L');
            }

            a.append('}');
         } else if (a instanceof float[]) {
            float[] a = (float[])((float[])a);
            a.append("new float[] {");

            for(a = 0; a < a.length; ++a) {
               a.append(a == 0 ? "" : ",").append(a[a]).append('f');
            }

            a.append('}');
         } else if (a instanceof double[]) {
            double[] a = (double[])((double[])a);
            a.append("new double[] {");

            for(a = 0; a < a.length; ++a) {
               a.append(a == 0 ? "" : ",").append(a[a]).append('d');
            }

            a.append('}');
         }
      }

   }

   private void declareFrameTypes(int a, Object[] a) {
      for(int a = 0; a < a; ++a) {
         if (a[a] instanceof Label) {
            a.declareLabel((Label)a[a]);
         }
      }

   }

   private void appendFrameTypes(int a, Object[] a) {
      for(int a = 0; a < a; ++a) {
         if (a > 0) {
            a.buf.append(", ");
         }

         if (a[a] instanceof String) {
            a.appendConstant(a[a]);
         } else if (a[a] instanceof Integer) {
            switch((Integer)a[a]) {
            case 0:
               a.buf.append("Opcodes.TOP");
               break;
            case 1:
               a.buf.append("Opcodes.INTEGER");
               break;
            case 2:
               a.buf.append("Opcodes.FLOAT");
               break;
            case 3:
               a.buf.append("Opcodes.DOUBLE");
               break;
            case 4:
               a.buf.append("Opcodes.LONG");
               break;
            case 5:
               a.buf.append("Opcodes.NULL");
               break;
            case 6:
               a.buf.append("Opcodes.UNINITIALIZED_THIS");
            }
         } else {
            a.appendLabel((Label)a[a]);
         }
      }

   }

   protected void declareLabel(Label a) {
      if (a.labelNames == null) {
         a.labelNames = new HashMap();
      }

      String a = (String)a.labelNames.get(a);
      if (a == null) {
         a = "l" + a.labelNames.size();
         a.labelNames.put(a, a);
         a.buf.append("Label ").append(a).append(" = new Label();\n");
      }

   }

   protected void appendLabel(Label a) {
      a.buf.append((String)a.labelNames.get(a));
   }
}

package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.TypeReference;
import org.spongepowered.asm.lib.signature.SignatureReader;

public class Textifier extends Printer {
   public static final int INTERNAL_NAME = 0;
   public static final int FIELD_DESCRIPTOR = 1;
   public static final int FIELD_SIGNATURE = 2;
   public static final int METHOD_DESCRIPTOR = 3;
   public static final int METHOD_SIGNATURE = 4;
   public static final int CLASS_SIGNATURE = 5;
   public static final int TYPE_DECLARATION = 6;
   public static final int CLASS_DECLARATION = 7;
   public static final int PARAMETERS_DECLARATION = 8;
   public static final int HANDLE_DESCRIPTOR = 9;
   protected String tab;
   protected String tab2;
   protected String tab3;
   protected String ltab;
   protected Map<Label, String> labelNames;
   private int access;
   private int valueNumber;

   public Textifier() {
      this(327680);
      if (a.getClass() != Textifier.class) {
         throw new IllegalStateException();
      }
   }

   protected Textifier(int a) {
      super(a);
      a.tab = "  ";
      a.tab2 = "    ";
      a.tab3 = "      ";
      a.ltab = "   ";
      a.valueNumber = 0;
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
         System.err.println("Prints a disassembled view of the given class.");
         System.err.println("Usage: Textifier [-debug] <fully qualified class name or class file name>");
      } else {
         ClassReader a;
         if (!a[a].endsWith(".class") && a[a].indexOf(92) <= -1 && a[a].indexOf(47) <= -1) {
            a = new ClassReader(a[a]);
         } else {
            a = new ClassReader(new FileInputStream(a[a]));
         }

         a.accept(new TraceClassVisitor(new PrintWriter(System.out)), a);
      }
   }

   public void visit(int a, int a, String a, String a, String a, String[] a) {
      a.access = a;
      int a = a & '\uffff';
      int a = a >>> 16;
      a.buf.setLength(0);
      a.buf.append("// class version ").append(a).append('.').append(a).append(" (").append(a).append(")\n");
      if ((a & 131072) != 0) {
         a.buf.append("// DEPRECATED\n");
      }

      a.buf.append("// access flags 0x").append(Integer.toHexString(a).toUpperCase()).append('\n');
      a.appendDescriptor(5, a);
      if (a != null) {
         TraceSignatureVisitor a = new TraceSignatureVisitor(a);
         SignatureReader a = new SignatureReader(a);
         a.accept(a);
         a.buf.append("// declaration: ").append(a).append(a.getDeclaration()).append('\n');
      }

      a.appendAccess(a & -33);
      if ((a & 8192) != 0) {
         a.buf.append("@interface ");
      } else if ((a & 512) != 0) {
         a.buf.append("interface ");
      } else if ((a & 16384) == 0) {
         a.buf.append("class ");
      }

      a.appendDescriptor(0, a);
      if (a != null && !"java/lang/Object".equals(a)) {
         a.buf.append(" extends ");
         a.appendDescriptor(0, a);
         a.buf.append(' ');
      }

      if (a != null && a.length > 0) {
         a.buf.append(" implements ");

         for(int a = 0; a < a.length; ++a) {
            a.appendDescriptor(0, a[a]);
            a.buf.append(' ');
         }
      }

      a.buf.append(" {\n\n");
      a.text.add(a.buf.toString());
   }

   public void visitSource(String a, String a) {
      a.buf.setLength(0);
      if (a != null) {
         a.buf.append(a.tab).append("// compiled from: ").append(a).append('\n');
      }

      if (a != null) {
         a.buf.append(a.tab).append("// debug info: ").append(a).append('\n');
      }

      if (a.buf.length() > 0) {
         a.text.add(a.buf.toString());
      }

   }

   public void visitOuterClass(String a, String a, String a) {
      a.buf.setLength(0);
      a.buf.append(a.tab).append("OUTERCLASS ");
      a.appendDescriptor(0, a);
      a.buf.append(' ');
      if (a != null) {
         a.buf.append(a).append(' ');
      }

      a.appendDescriptor(3, a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public Textifier visitClassAnnotation(String a, boolean a) {
      a.text.add("\n");
      return a.visitAnnotation(a, a);
   }

   public Printer visitClassTypeAnnotation(int a, TypePath a, String a, boolean a) {
      a.text.add("\n");
      return a.visitTypeAnnotation(a, a, a, a);
   }

   public void visitClassAttribute(Attribute a) {
      a.text.add("\n");
      a.visitAttribute(a);
   }

   public void visitInnerClass(String a, String a, String a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab).append("// access flags 0x");
      a.buf.append(Integer.toHexString(a & -33).toUpperCase()).append('\n');
      a.buf.append(a.tab);
      a.appendAccess(a);
      a.buf.append("INNERCLASS ");
      a.appendDescriptor(0, a);
      a.buf.append(' ');
      a.appendDescriptor(0, a);
      a.buf.append(' ');
      a.appendDescriptor(0, a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public Textifier visitField(int a, String a, String a, String a, Object a) {
      a.buf.setLength(0);
      a.buf.append('\n');
      if ((a & 131072) != 0) {
         a.buf.append(a.tab).append("// DEPRECATED\n");
      }

      a.buf.append(a.tab).append("// access flags 0x").append(Integer.toHexString(a).toUpperCase()).append('\n');
      if (a != null) {
         a.buf.append(a.tab);
         a.appendDescriptor(2, a);
         TraceSignatureVisitor a = new TraceSignatureVisitor(0);
         SignatureReader a = new SignatureReader(a);
         a.acceptType(a);
         a.buf.append(a.tab).append("// declaration: ").append(a.getDeclaration()).append('\n');
      }

      a.buf.append(a.tab);
      a.appendAccess(a);
      a.appendDescriptor(1, a);
      a.buf.append(' ').append(a);
      if (a != null) {
         a.buf.append(" = ");
         if (a instanceof String) {
            a.buf.append('"').append(a).append('"');
         } else {
            a.buf.append(a);
         }
      }

      a.buf.append('\n');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      return a;
   }

   public Textifier visitMethod(int a, String a, String a, String a, String[] a) {
      a.buf.setLength(0);
      a.buf.append('\n');
      if ((a & 131072) != 0) {
         a.buf.append(a.tab).append("// DEPRECATED\n");
      }

      a.buf.append(a.tab).append("// access flags 0x").append(Integer.toHexString(a).toUpperCase()).append('\n');
      if (a != null) {
         a.buf.append(a.tab);
         a.appendDescriptor(4, a);
         TraceSignatureVisitor a = new TraceSignatureVisitor(0);
         SignatureReader a = new SignatureReader(a);
         a.accept(a);
         String a = a.getDeclaration();
         String a = a.getReturnType();
         String a = a.getExceptions();
         a.buf.append(a.tab).append("// declaration: ").append(a).append(' ').append(a).append(a);
         if (a != null) {
            a.buf.append(" throws ").append(a);
         }

         a.buf.append('\n');
      }

      a.buf.append(a.tab);
      a.appendAccess(a & -65);
      if ((a & 256) != 0) {
         a.buf.append("native ");
      }

      if ((a & 128) != 0) {
         a.buf.append("varargs ");
      }

      if ((a & 64) != 0) {
         a.buf.append("bridge ");
      }

      if ((a.access & 512) != 0 && (a & 1024) == 0 && (a & 8) == 0) {
         a.buf.append("default ");
      }

      a.buf.append(a);
      a.appendDescriptor(3, a);
      if (a != null && a.length > 0) {
         a.buf.append(" throws ");

         for(int a = 0; a < a.length; ++a) {
            a.appendDescriptor(0, a[a]);
            a.buf.append(' ');
         }
      }

      a.buf.append('\n');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      return a;
   }

   public void visitClassEnd() {
      a.text.add("}\n");
   }

   public void visit(String a, Object a) {
      a.buf.setLength(0);
      a.appendComa(a.valueNumber++);
      if (a != null) {
         a.buf.append(a).append('=');
      }

      if (a instanceof String) {
         a.visitString((String)a);
      } else if (a instanceof Type) {
         a.visitType((Type)a);
      } else if (a instanceof Byte) {
         a.visitByte((Byte)a);
      } else if (a instanceof Boolean) {
         a.visitBoolean((Boolean)a);
      } else if (a instanceof Short) {
         a.visitShort((Short)a);
      } else if (a instanceof Character) {
         a.visitChar((Character)a);
      } else if (a instanceof Integer) {
         a.visitInt((Integer)a);
      } else if (a instanceof Float) {
         a.visitFloat((Float)a);
      } else if (a instanceof Long) {
         a.visitLong((Long)a);
      } else if (a instanceof Double) {
         a.visitDouble((Double)a);
      } else if (a.getClass().isArray()) {
         a.buf.append('{');
         int a;
         if (a instanceof byte[]) {
            byte[] a = (byte[])((byte[])a);

            for(a = 0; a < a.length; ++a) {
               a.appendComa(a);
               a.visitByte(a[a]);
            }
         } else if (a instanceof boolean[]) {
            boolean[] a = (boolean[])((boolean[])a);

            for(a = 0; a < a.length; ++a) {
               a.appendComa(a);
               a.visitBoolean(a[a]);
            }
         } else if (a instanceof short[]) {
            short[] a = (short[])((short[])a);

            for(a = 0; a < a.length; ++a) {
               a.appendComa(a);
               a.visitShort(a[a]);
            }
         } else if (a instanceof char[]) {
            char[] a = (char[])((char[])a);

            for(a = 0; a < a.length; ++a) {
               a.appendComa(a);
               a.visitChar(a[a]);
            }
         } else if (a instanceof int[]) {
            int[] a = (int[])((int[])a);

            for(a = 0; a < a.length; ++a) {
               a.appendComa(a);
               a.visitInt(a[a]);
            }
         } else if (a instanceof long[]) {
            long[] a = (long[])((long[])a);

            for(a = 0; a < a.length; ++a) {
               a.appendComa(a);
               a.visitLong(a[a]);
            }
         } else if (a instanceof float[]) {
            float[] a = (float[])((float[])a);

            for(a = 0; a < a.length; ++a) {
               a.appendComa(a);
               a.visitFloat(a[a]);
            }
         } else if (a instanceof double[]) {
            double[] a = (double[])((double[])a);

            for(a = 0; a < a.length; ++a) {
               a.appendComa(a);
               a.visitDouble(a[a]);
            }
         }

         a.buf.append('}');
      }

      a.text.add(a.buf.toString());
   }

   private void visitInt(int a) {
      a.buf.append(a);
   }

   private void visitLong(long a) {
      a.buf.append(a).append('L');
   }

   private void visitFloat(float a) {
      a.buf.append(a).append('F');
   }

   private void visitDouble(double a) {
      a.buf.append(a).append('D');
   }

   private void visitChar(char a) {
      a.buf.append("(char)").append(a);
   }

   private void visitShort(short a) {
      a.buf.append("(short)").append(a);
   }

   private void visitByte(byte a) {
      a.buf.append("(byte)").append(a);
   }

   private void visitBoolean(boolean a) {
      a.buf.append(a);
   }

   private void visitString(String a) {
      appendString(a.buf, a);
   }

   private void visitType(Type a) {
      a.buf.append(a.getClassName()).append(".class");
   }

   public void visitEnum(String a, String a, String a) {
      a.buf.setLength(0);
      a.appendComa(a.valueNumber++);
      if (a != null) {
         a.buf.append(a).append('=');
      }

      a.appendDescriptor(1, a);
      a.buf.append('.').append(a);
      a.text.add(a.buf.toString());
   }

   public Textifier visitAnnotation(String a, String a) {
      a.buf.setLength(0);
      a.appendComa(a.valueNumber++);
      if (a != null) {
         a.buf.append(a).append('=');
      }

      a.buf.append('@');
      a.appendDescriptor(1, a);
      a.buf.append('(');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      a.text.add(")");
      return a;
   }

   public Textifier visitArray(String a) {
      a.buf.setLength(0);
      a.appendComa(a.valueNumber++);
      if (a != null) {
         a.buf.append(a).append('=');
      }

      a.buf.append('{');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      a.text.add("}");
      return a;
   }

   public void visitAnnotationEnd() {
   }

   public Textifier visitFieldAnnotation(String a, boolean a) {
      return a.visitAnnotation(a, a);
   }

   public Printer visitFieldTypeAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation(a, a, a, a);
   }

   public void visitFieldAttribute(Attribute a) {
      a.visitAttribute(a);
   }

   public void visitFieldEnd() {
   }

   public void visitParameter(String a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("// parameter ");
      a.appendAccess(a);
      a.buf.append(' ').append(a == null ? "<no name>" : a).append('\n');
      a.text.add(a.buf.toString());
   }

   public Textifier visitAnnotationDefault() {
      a.text.add(a.tab2 + "default=");
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      a.text.add("\n");
      return a;
   }

   public Textifier visitMethodAnnotation(String a, boolean a) {
      return a.visitAnnotation(a, a);
   }

   public Printer visitMethodTypeAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation(a, a, a, a);
   }

   public Textifier visitParameterAnnotation(int a, String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append('@');
      a.appendDescriptor(1, a);
      a.buf.append('(');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      a.text.add(a ? ") // parameter " : ") // invisible, parameter ");
      a.text.add(a);
      a.text.add("\n");
      return a;
   }

   public void visitMethodAttribute(Attribute a) {
      a.buf.setLength(0);
      a.buf.append(a.tab).append("ATTRIBUTE ");
      a.appendDescriptor(-1, a.type);
      if (a instanceof Textifiable) {
         ((Textifiable)a).textify(a.buf, a.labelNames);
      } else {
         a.buf.append(" : unknown\n");
      }

      a.text.add(a.buf.toString());
   }

   public void visitCode() {
   }

   public void visitFrame(int a, int a, Object[] a, int a, Object[] a) {
      a.buf.setLength(0);
      a.buf.append(a.ltab);
      a.buf.append("FRAME ");
      switch(a) {
      case -1:
      case 0:
         a.buf.append("FULL [");
         a.appendFrameTypes(a, a);
         a.buf.append("] [");
         a.appendFrameTypes(a, a);
         a.buf.append(']');
         break;
      case 1:
         a.buf.append("APPEND [");
         a.appendFrameTypes(a, a);
         a.buf.append(']');
         break;
      case 2:
         a.buf.append("CHOP ").append(a);
         break;
      case 3:
         a.buf.append("SAME");
         break;
      case 4:
         a.buf.append("SAME1 ");
         a.appendFrameTypes(1, a);
      }

      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitInsn(int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append(OPCODES[a]).append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitIntInsn(int a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append(OPCODES[a]).append(' ').append(a == 188 ? TYPES[a] : Integer.toString(a)).append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitVarInsn(int a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append(OPCODES[a]).append(' ').append(a).append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitTypeInsn(int a, String a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append(OPCODES[a]).append(' ');
      a.appendDescriptor(0, a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitFieldInsn(int a, String a, String a, String a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append(OPCODES[a]).append(' ');
      a.appendDescriptor(0, a);
      a.buf.append('.').append(a).append(" : ");
      a.appendDescriptor(1, a);
      a.buf.append('\n');
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

   private void doVisitMethodInsn(int a, String a, String a, String a, boolean a5) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append(OPCODES[a]).append(' ');
      a.appendDescriptor(0, a);
      a.buf.append('.').append(a).append(' ');
      a.appendDescriptor(3, a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitInvokeDynamicInsn(String a, String a, Handle a, Object... a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("INVOKEDYNAMIC").append(' ');
      a.buf.append(a);
      a.appendDescriptor(3, a);
      a.buf.append(" [");
      a.buf.append('\n');
      a.buf.append(a.tab3);
      a.appendHandle(a);
      a.buf.append('\n');
      a.buf.append(a.tab3).append("// arguments:");
      if (a.length == 0) {
         a.buf.append(" none");
      } else {
         a.buf.append('\n');

         for(int a = 0; a < a.length; ++a) {
            a.buf.append(a.tab3);
            Object a = a[a];
            if (a instanceof String) {
               Printer.appendString(a.buf, (String)a);
            } else if (a instanceof Type) {
               Type a = (Type)a;
               if (a.getSort() == 11) {
                  a.appendDescriptor(3, a.getDescriptor());
               } else {
                  a.buf.append(a.getDescriptor()).append(".class");
               }
            } else if (a instanceof Handle) {
               a.appendHandle((Handle)a);
            } else {
               a.buf.append(a);
            }

            a.buf.append(", \n");
         }

         a.buf.setLength(a.buf.length() - 3);
      }

      a.buf.append('\n');
      a.buf.append(a.tab2).append("]\n");
      a.text.add(a.buf.toString());
   }

   public void visitJumpInsn(int a, Label a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append(OPCODES[a]).append(' ');
      a.appendLabel(a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitLabel(Label a) {
      a.buf.setLength(0);
      a.buf.append(a.ltab);
      a.appendLabel(a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitLdcInsn(Object a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("LDC ");
      if (a instanceof String) {
         Printer.appendString(a.buf, (String)a);
      } else if (a instanceof Type) {
         a.buf.append(((Type)a).getDescriptor()).append(".class");
      } else {
         a.buf.append(a);
      }

      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitIincInsn(int a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("IINC ").append(a).append(' ').append(a).append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitTableSwitchInsn(int a, int a2, Label a, Label... a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("TABLESWITCH\n");

      for(int a = 0; a < a.length; ++a) {
         a.buf.append(a.tab3).append(a + a).append(": ");
         a.appendLabel(a[a]);
         a.buf.append('\n');
      }

      a.buf.append(a.tab3).append("default: ");
      a.appendLabel(a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitLookupSwitchInsn(Label a, int[] a, Label[] a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("LOOKUPSWITCH\n");

      for(int a = 0; a < a.length; ++a) {
         a.buf.append(a.tab3).append(a[a]).append(": ");
         a.appendLabel(a[a]);
         a.buf.append('\n');
      }

      a.buf.append(a.tab3).append("default: ");
      a.appendLabel(a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitMultiANewArrayInsn(String a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("MULTIANEWARRAY ");
      a.appendDescriptor(1, a);
      a.buf.append(' ').append(a).append('\n');
      a.text.add(a.buf.toString());
   }

   public Printer visitInsnAnnotation(int a, TypePath a, String a, boolean a) {
      return a.visitTypeAnnotation(a, a, a, a);
   }

   public void visitTryCatchBlock(Label a, Label a, Label a, String a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("TRYCATCHBLOCK ");
      a.appendLabel(a);
      a.buf.append(' ');
      a.appendLabel(a);
      a.buf.append(' ');
      a.appendLabel(a);
      a.buf.append(' ');
      a.appendDescriptor(0, a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public Printer visitTryCatchAnnotation(int a, TypePath a, String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("TRYCATCHBLOCK @");
      a.appendDescriptor(1, a);
      a.buf.append('(');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      a.buf.setLength(0);
      a.buf.append(") : ");
      a.appendTypeReference(a);
      a.buf.append(", ").append(a);
      a.buf.append(a ? "\n" : " // invisible\n");
      a.text.add(a.buf.toString());
      return a;
   }

   public void visitLocalVariable(String a, String a, String a, Label a, Label a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("LOCALVARIABLE ").append(a).append(' ');
      a.appendDescriptor(1, a);
      a.buf.append(' ');
      a.appendLabel(a);
      a.buf.append(' ');
      a.appendLabel(a);
      a.buf.append(' ').append(a).append('\n');
      if (a != null) {
         a.buf.append(a.tab2);
         a.appendDescriptor(2, a);
         TraceSignatureVisitor a = new TraceSignatureVisitor(0);
         SignatureReader a = new SignatureReader(a);
         a.acceptType(a);
         a.buf.append(a.tab2).append("// declaration: ").append(a.getDeclaration()).append('\n');
      }

      a.text.add(a.buf.toString());
   }

   public Printer visitLocalVariableAnnotation(int a, TypePath a, Label[] a, Label[] a, int[] a, String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("LOCALVARIABLE @");
      a.appendDescriptor(1, a);
      a.buf.append('(');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      a.buf.setLength(0);
      a.buf.append(") : ");
      a.appendTypeReference(a);
      a.buf.append(", ").append(a);

      for(int a = 0; a < a.length; ++a) {
         a.buf.append(" [ ");
         a.appendLabel(a[a]);
         a.buf.append(" - ");
         a.appendLabel(a[a]);
         a.buf.append(" - ").append(a[a]).append(" ]");
      }

      a.buf.append(a ? "\n" : " // invisible\n");
      a.text.add(a.buf.toString());
      return a;
   }

   public void visitLineNumber(int a, Label a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("LINENUMBER ").append(a).append(' ');
      a.appendLabel(a);
      a.buf.append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitMaxs(int a, int a) {
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("MAXSTACK = ").append(a).append('\n');
      a.text.add(a.buf.toString());
      a.buf.setLength(0);
      a.buf.append(a.tab2).append("MAXLOCALS = ").append(a).append('\n');
      a.text.add(a.buf.toString());
   }

   public void visitMethodEnd() {
   }

   public Textifier visitAnnotation(String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append(a.tab).append('@');
      a.appendDescriptor(1, a);
      a.buf.append('(');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      a.text.add(a ? ")\n" : ") // invisible\n");
      return a;
   }

   public Textifier visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      a.buf.setLength(0);
      a.buf.append(a.tab).append('@');
      a.appendDescriptor(1, a);
      a.buf.append('(');
      a.text.add(a.buf.toString());
      Textifier a = a.createTextifier();
      a.text.add(a.getText());
      a.buf.setLength(0);
      a.buf.append(") : ");
      a.appendTypeReference(a);
      a.buf.append(", ").append(a);
      a.buf.append(a ? "\n" : " // invisible\n");
      a.text.add(a.buf.toString());
      return a;
   }

   public void visitAttribute(Attribute a) {
      a.buf.setLength(0);
      a.buf.append(a.tab).append("ATTRIBUTE ");
      a.appendDescriptor(-1, a.type);
      if (a instanceof Textifiable) {
         ((Textifiable)a).textify(a.buf, (Map)null);
      } else {
         a.buf.append(" : unknown\n");
      }

      a.text.add(a.buf.toString());
   }

   protected Textifier createTextifier() {
      return new Textifier();
   }

   protected void appendDescriptor(int a, String a) {
      if (a != 5 && a != 2 && a != 4) {
         a.buf.append(a);
      } else if (a != null) {
         a.buf.append("// signature ").append(a).append('\n');
      }

   }

   protected void appendLabel(Label a) {
      if (a.labelNames == null) {
         a.labelNames = new HashMap();
      }

      String a = (String)a.labelNames.get(a);
      if (a == null) {
         a = "L" + a.labelNames.size();
         a.labelNames.put(a, a);
      }

      a.buf.append(a);
   }

   protected void appendHandle(Handle a) {
      int a = a.getTag();
      a.buf.append("// handle kind 0x").append(Integer.toHexString(a)).append(" : ");
      boolean a = false;
      switch(a) {
      case 1:
         a.buf.append("GETFIELD");
         break;
      case 2:
         a.buf.append("GETSTATIC");
         break;
      case 3:
         a.buf.append("PUTFIELD");
         break;
      case 4:
         a.buf.append("PUTSTATIC");
         break;
      case 5:
         a.buf.append("INVOKEVIRTUAL");
         a = true;
         break;
      case 6:
         a.buf.append("INVOKESTATIC");
         a = true;
         break;
      case 7:
         a.buf.append("INVOKESPECIAL");
         a = true;
         break;
      case 8:
         a.buf.append("NEWINVOKESPECIAL");
         a = true;
         break;
      case 9:
         a.buf.append("INVOKEINTERFACE");
         a = true;
      }

      a.buf.append('\n');
      a.buf.append(a.tab3);
      a.appendDescriptor(0, a.getOwner());
      a.buf.append('.');
      a.buf.append(a.getName());
      if (!a) {
         a.buf.append('(');
      }

      a.appendDescriptor(9, a.getDesc());
      if (!a) {
         a.buf.append(')');
      }

   }

   private void appendAccess(int a) {
      if ((a & 1) != 0) {
         a.buf.append("public ");
      }

      if ((a & 2) != 0) {
         a.buf.append("private ");
      }

      if ((a & 4) != 0) {
         a.buf.append("protected ");
      }

      if ((a & 16) != 0) {
         a.buf.append("final ");
      }

      if ((a & 8) != 0) {
         a.buf.append("static ");
      }

      if ((a & 32) != 0) {
         a.buf.append("synchronized ");
      }

      if ((a & 64) != 0) {
         a.buf.append("volatile ");
      }

      if ((a & 128) != 0) {
         a.buf.append("transient ");
      }

      if ((a & 1024) != 0) {
         a.buf.append("abstract ");
      }

      if ((a & 2048) != 0) {
         a.buf.append("strictfp ");
      }

      if ((a & 4096) != 0) {
         a.buf.append("synthetic ");
      }

      if ((a & '耀') != 0) {
         a.buf.append("mandated ");
      }

      if ((a & 16384) != 0) {
         a.buf.append("enum ");
      }

   }

   private void appendComa(int a) {
      if (a != 0) {
         a.buf.append(", ");
      }

   }

   private void appendTypeReference(int a) {
      TypeReference a = new TypeReference(a);
      switch(a.getSort()) {
      case 0:
         a.buf.append("CLASS_TYPE_PARAMETER ").append(a.getTypeParameterIndex());
         break;
      case 1:
         a.buf.append("METHOD_TYPE_PARAMETER ").append(a.getTypeParameterIndex());
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
         break;
      case 16:
         a.buf.append("CLASS_EXTENDS ").append(a.getSuperTypeIndex());
         break;
      case 17:
         a.buf.append("CLASS_TYPE_PARAMETER_BOUND ").append(a.getTypeParameterIndex()).append(", ").append(a.getTypeParameterBoundIndex());
         break;
      case 18:
         a.buf.append("METHOD_TYPE_PARAMETER_BOUND ").append(a.getTypeParameterIndex()).append(", ").append(a.getTypeParameterBoundIndex());
         break;
      case 19:
         a.buf.append("FIELD");
         break;
      case 20:
         a.buf.append("METHOD_RETURN");
         break;
      case 21:
         a.buf.append("METHOD_RECEIVER");
         break;
      case 22:
         a.buf.append("METHOD_FORMAL_PARAMETER ").append(a.getFormalParameterIndex());
         break;
      case 23:
         a.buf.append("THROWS ").append(a.getExceptionIndex());
         break;
      case 64:
         a.buf.append("LOCAL_VARIABLE");
         break;
      case 65:
         a.buf.append("RESOURCE_VARIABLE");
         break;
      case 66:
         a.buf.append("EXCEPTION_PARAMETER ").append(a.getTryCatchBlockIndex());
         break;
      case 67:
         a.buf.append("INSTANCEOF");
         break;
      case 68:
         a.buf.append("NEW");
         break;
      case 69:
         a.buf.append("CONSTRUCTOR_REFERENCE");
         break;
      case 70:
         a.buf.append("METHOD_REFERENCE");
         break;
      case 71:
         a.buf.append("CAST ").append(a.getTypeArgumentIndex());
         break;
      case 72:
         a.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(a.getTypeArgumentIndex());
         break;
      case 73:
         a.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(a.getTypeArgumentIndex());
         break;
      case 74:
         a.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(a.getTypeArgumentIndex());
         break;
      case 75:
         a.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(a.getTypeArgumentIndex());
      }

   }

   private void appendFrameTypes(int a, Object[] a) {
      for(int a = 0; a < a; ++a) {
         if (a > 0) {
            a.buf.append(' ');
         }

         if (a[a] instanceof String) {
            String a = (String)a[a];
            if (a.startsWith("[")) {
               a.appendDescriptor(1, a);
            } else {
               a.appendDescriptor(0, a);
            }
         } else if (a[a] instanceof Integer) {
            switch((Integer)a[a]) {
            case 0:
               a.appendDescriptor(1, "T");
               break;
            case 1:
               a.appendDescriptor(1, "I");
               break;
            case 2:
               a.appendDescriptor(1, "F");
               break;
            case 3:
               a.appendDescriptor(1, "D");
               break;
            case 4:
               a.appendDescriptor(1, "J");
               break;
            case 5:
               a.appendDescriptor(1, "N");
               break;
            case 6:
               a.appendDescriptor(1, "U");
            }
         } else {
            a.appendLabel((Label)a[a]);
         }
      }

   }
}

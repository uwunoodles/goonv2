package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.TypePath;

public abstract class Printer {
   public static final String[] OPCODES;
   public static final String[] TYPES;
   public static final String[] HANDLE_TAG;
   protected final int api;
   protected final StringBuffer buf;
   public final List<Object> text;

   protected Printer(int a) {
      a.api = a;
      a.buf = new StringBuffer();
      a.text = new ArrayList();
   }

   public abstract void visit(int var1, int var2, String var3, String var4, String var5, String[] var6);

   public abstract void visitSource(String var1, String var2);

   public abstract void visitOuterClass(String var1, String var2, String var3);

   public abstract Printer visitClassAnnotation(String var1, boolean var2);

   public Printer visitClassTypeAnnotation(int a1, TypePath a2, String a3, boolean a4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitClassAttribute(Attribute var1);

   public abstract void visitInnerClass(String var1, String var2, String var3, int var4);

   public abstract Printer visitField(int var1, String var2, String var3, String var4, Object var5);

   public abstract Printer visitMethod(int var1, String var2, String var3, String var4, String[] var5);

   public abstract void visitClassEnd();

   public abstract void visit(String var1, Object var2);

   public abstract void visitEnum(String var1, String var2, String var3);

   public abstract Printer visitAnnotation(String var1, String var2);

   public abstract Printer visitArray(String var1);

   public abstract void visitAnnotationEnd();

   public abstract Printer visitFieldAnnotation(String var1, boolean var2);

   public Printer visitFieldTypeAnnotation(int a1, TypePath a2, String a3, boolean a4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitFieldAttribute(Attribute var1);

   public abstract void visitFieldEnd();

   public void visitParameter(String a1, int a2) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract Printer visitAnnotationDefault();

   public abstract Printer visitMethodAnnotation(String var1, boolean var2);

   public Printer visitMethodTypeAnnotation(int a1, TypePath a2, String a3, boolean a4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract Printer visitParameterAnnotation(int var1, String var2, boolean var3);

   public abstract void visitMethodAttribute(Attribute var1);

   public abstract void visitCode();

   public abstract void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5);

   public abstract void visitInsn(int var1);

   public abstract void visitIntInsn(int var1, int var2);

   public abstract void visitVarInsn(int var1, int var2);

   public abstract void visitTypeInsn(int var1, String var2);

   public abstract void visitFieldInsn(int var1, String var2, String var3, String var4);

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int a, String a, String a, String a) {
      if (a.api >= 327680) {
         boolean a = a == 185;
         a.visitMethodInsn(a, a, a, a, a);
      } else {
         throw new RuntimeException("Must be overriden");
      }
   }

   public void visitMethodInsn(int a, String a, String a, String a, boolean a) {
      if (a.api < 327680) {
         if (a != (a == 185)) {
            throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
         } else {
            a.visitMethodInsn(a, a, a, a);
         }
      } else {
         throw new RuntimeException("Must be overriden");
      }
   }

   public abstract void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4);

   public abstract void visitJumpInsn(int var1, Label var2);

   public abstract void visitLabel(Label var1);

   public abstract void visitLdcInsn(Object var1);

   public abstract void visitIincInsn(int var1, int var2);

   public abstract void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4);

   public abstract void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3);

   public abstract void visitMultiANewArrayInsn(String var1, int var2);

   public Printer visitInsnAnnotation(int a1, TypePath a2, String a3, boolean a4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4);

   public Printer visitTryCatchAnnotation(int a1, TypePath a2, String a3, boolean a4) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6);

   public Printer visitLocalVariableAnnotation(int a1, TypePath a2, Label[] a3, Label[] a4, int[] a5, String a6, boolean a7) {
      throw new RuntimeException("Must be overriden");
   }

   public abstract void visitLineNumber(int var1, Label var2);

   public abstract void visitMaxs(int var1, int var2);

   public abstract void visitMethodEnd();

   public List<Object> getText() {
      return a.text;
   }

   public void print(PrintWriter a) {
      printList(a, a.text);
   }

   public static void appendString(StringBuffer a, String a) {
      a.append('"');

      for(int a = 0; a < a.length(); ++a) {
         char a = a.charAt(a);
         if (a == '\n') {
            a.append("\\n");
         } else if (a == '\r') {
            a.append("\\r");
         } else if (a == '\\') {
            a.append("\\\\");
         } else if (a == '"') {
            a.append("\\\"");
         } else if (a >= ' ' && a <= 127) {
            a.append(a);
         } else {
            a.append("\\u");
            if (a < 16) {
               a.append("000");
            } else if (a < 256) {
               a.append("00");
            } else if (a < 4096) {
               a.append('0');
            }

            a.append(Integer.toString(a, 16));
         }
      }

      a.append('"');
   }

   static void printList(PrintWriter a, List<?> a) {
      for(int a = 0; a < a.size(); ++a) {
         Object a = a.get(a);
         if (a instanceof List) {
            printList(a, (List)a);
         } else {
            a.print(a.toString());
         }
      }

   }

   static {
      String a = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,";
      OPCODES = new String[200];
      int a = 0;

      int a;
      int a;
      for(a = 0; (a = a.indexOf(44, a)) > 0; a = a + 1) {
         OPCODES[a++] = a + 1 == a ? null : a.substring(a, a);
      }

      a = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,";
      TYPES = new String[12];
      a = 0;

      for(a = 4; (a = a.indexOf(44, a)) > 0; a = a + 1) {
         TYPES[a++] = a.substring(a, a);
      }

      a = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,";
      HANDLE_TAG = new String[10];
      a = 0;

      for(a = 1; (a = a.indexOf(44, a)) > 0; a = a + 1) {
         HANDLE_TAG[a++] = a.substring(a, a);
      }

   }
}

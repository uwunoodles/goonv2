package org.spongepowered.asm.mixin.injection.invoke.arg;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.SignaturePrinter;
import org.spongepowered.asm.util.asm.MethodVisitorEx;

public final class ArgsClassGenerator implements IClassGenerator {
   public static final String ARGS_NAME = Args.class.getName();
   public static final String ARGS_REF;
   public static final String GETTER_PREFIX = "$";
   private static final String CLASS_NAME_BASE = "org.spongepowered.asm.synthetic.args.Args$";
   private static final String OBJECT = "java/lang/Object";
   private static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
   private static final String VALUES_FIELD = "values";
   private static final String CTOR_DESC = "([Ljava/lang/Object;)V";
   private static final String SET = "set";
   private static final String SET_DESC = "(ILjava/lang/Object;)V";
   private static final String SETALL = "setAll";
   private static final String SETALL_DESC = "([Ljava/lang/Object;)V";
   private static final String NPE = "java/lang/NullPointerException";
   private static final String NPE_CTOR_DESC = "(Ljava/lang/String;)V";
   private static final String AIOOBE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException";
   private static final String AIOOBE_CTOR_DESC = "(I)V";
   private static final String ACE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException";
   private static final String ACE_CTOR_DESC = "(IILjava/lang/String;)V";
   private int nextIndex = 1;
   private final BiMap<String, String> classNames = HashBiMap.create();
   private final Map<String, byte[]> classBytes = new HashMap();

   public String getClassName(String a) {
      String a = Bytecode.changeDescriptorReturnType(a, "V");
      String a = (String)a.classNames.get(a);
      if (a == null) {
         a = String.format("%s%d", "org.spongepowered.asm.synthetic.args.Args$", a.nextIndex++);
         a.classNames.put(a, a);
      }

      return a;
   }

   public String getClassRef(String a) {
      return a.getClassName(a).replace('.', '/');
   }

   public byte[] generate(String a) {
      return a.getBytes(a);
   }

   public byte[] getBytes(String a) {
      byte[] a = (byte[])a.classBytes.get(a);
      if (a == null) {
         String a = (String)a.classNames.inverse().get(a);
         if (a == null) {
            return null;
         }

         a = a.generateClass(a, a);
         a.classBytes.put(a, a);
      }

      return a;
   }

   private byte[] generateClass(String a, String a) {
      String a = a.replace('.', '/');
      Type[] a = Type.getArgumentTypes(a);
      ClassWriter a = new ClassWriter(2);
      ClassVisitor a = a;
      if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
         a = new CheckClassAdapter(a);
      }

      ((ClassVisitor)a).visit(50, 4129, a, (String)null, ARGS_REF, (String[])null);
      ((ClassVisitor)a).visitSource(a.substring(a.lastIndexOf(46) + 1) + ".java", (String)null);
      a.generateCtor(a, a, a, (ClassVisitor)a);
      a.generateToString(a, a, a, (ClassVisitor)a);
      a.generateFactory(a, a, a, (ClassVisitor)a);
      a.generateSetters(a, a, a, (ClassVisitor)a);
      a.generateGetters(a, a, a, (ClassVisitor)a);
      ((ClassVisitor)a).visitEnd();
      return a.toByteArray();
   }

   private void generateCtor(String a1, String a2, Type[] a3, ClassVisitor a) {
      MethodVisitor a = a.visitMethod(2, "<init>", "([Ljava/lang/Object;)V", (String)null, (String[])null);
      a.visitCode();
      a.visitVarInsn(25, 0);
      a.visitVarInsn(25, 1);
      a.visitMethodInsn(183, ARGS_REF, "<init>", "([Ljava/lang/Object;)V", false);
      a.visitInsn(177);
      a.visitMaxs(2, 2);
      a.visitEnd();
   }

   private void generateToString(String a1, String a2, Type[] a, ClassVisitor a) {
      MethodVisitor a = a.visitMethod(1, "toString", "()Ljava/lang/String;", (String)null, (String[])null);
      a.visitCode();
      a.visitLdcInsn("Args" + getSignature(a));
      a.visitInsn(176);
      a.visitMaxs(1, 1);
      a.visitEnd();
   }

   private void generateFactory(String a, String a, Type[] a, ClassVisitor a) {
      String a = Bytecode.changeDescriptorReturnType(a, "L" + a + ";");
      MethodVisitorEx a = new MethodVisitorEx(a.visitMethod(9, "of", a, (String)null, (String[])null));
      a.visitCode();
      a.visitTypeInsn(187, a);
      a.visitInsn(89);
      a.visitConstant((byte)a.length);
      a.visitTypeInsn(189, "java/lang/Object");
      byte a = 0;
      Type[] var8 = a;
      int var9 = a.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         Type a = var8[var10];
         a.visitInsn(89);
         a.visitConstant(a);
         a.visitVarInsn(a.getOpcode(21), a);
         box(a, a);
         a.visitInsn(83);
         a = (byte)(a + a.getSize());
      }

      a.visitMethodInsn(183, a, "<init>", "([Ljava/lang/Object;)V", false);
      a.visitInsn(176);
      a.visitMaxs(6, Bytecode.getArgsSize(a));
      a.visitEnd();
   }

   private void generateGetters(String a, String a2, Type[] a, ClassVisitor a) {
      byte a = 0;
      Type[] var6 = a;
      int var7 = a.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Type a = var6[var8];
         String a = "$" + a;
         String a = "()" + a.getDescriptor();
         MethodVisitorEx a = new MethodVisitorEx(a.visitMethod(1, a, a, (String)null, (String[])null));
         a.visitCode();
         a.visitVarInsn(25, 0);
         a.visitFieldInsn(180, a, "values", "[Ljava/lang/Object;");
         a.visitConstant(a);
         a.visitInsn(50);
         unbox(a, a);
         a.visitInsn(a.getOpcode(172));
         a.visitMaxs(2, 1);
         a.visitEnd();
         ++a;
      }

   }

   private void generateSetters(String a, String a, Type[] a, ClassVisitor a) {
      a.generateIndexedSetter(a, a, a, a);
      a.generateMultiSetter(a, a, a, a);
   }

   private void generateIndexedSetter(String a, String a2, Type[] a, ClassVisitor a) {
      MethodVisitorEx a = new MethodVisitorEx(a.visitMethod(1, "set", "(ILjava/lang/Object;)V", (String)null, (String[])null));
      a.visitCode();
      Label a = new Label();
      Label a = new Label();
      Label[] a = new Label[a.length];

      int a;
      for(a = 0; a < a.length; ++a) {
         a[a] = new Label();
      }

      a.visitVarInsn(25, 0);
      a.visitFieldInsn(180, a, "values", "[Ljava/lang/Object;");

      for(byte a = 0; a < a.length; ++a) {
         a.visitVarInsn(21, 1);
         a.visitConstant(a);
         a.visitJumpInsn(159, a[a]);
      }

      throwAIOOBE(a, 1);

      for(a = 0; a < a.length; ++a) {
         String a = Bytecode.getBoxingType(a[a]);
         a.visitLabel(a[a]);
         a.visitVarInsn(21, 1);
         a.visitVarInsn(25, 2);
         a.visitTypeInsn(192, a != null ? a : a[a].getInternalName());
         a.visitJumpInsn(167, a != null ? a : a);
      }

      a.visitLabel(a);
      a.visitInsn(89);
      a.visitJumpInsn(199, a);
      throwNPE(a, "Argument with primitive type cannot be set to NULL");
      a.visitLabel(a);
      a.visitInsn(83);
      a.visitInsn(177);
      a.visitMaxs(6, 3);
      a.visitEnd();
   }

   private void generateMultiSetter(String a, String a2, Type[] a, ClassVisitor a) {
      MethodVisitorEx a = new MethodVisitorEx(a.visitMethod(1, "setAll", "([Ljava/lang/Object;)V", (String)null, (String[])null));
      a.visitCode();
      Label a = new Label();
      Label a = new Label();
      int a = 6;
      a.visitVarInsn(25, 1);
      a.visitInsn(190);
      a.visitInsn(89);
      a.visitConstant((byte)a.length);
      a.visitJumpInsn(159, a);
      a.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException");
      a.visitInsn(89);
      a.visitInsn(93);
      a.visitInsn(88);
      a.visitConstant((byte)a.length);
      a.visitLdcInsn(getSignature(a));
      a.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException", "<init>", "(IILjava/lang/String;)V", false);
      a.visitInsn(191);
      a.visitLabel(a);
      a.visitInsn(87);
      a.visitVarInsn(25, 0);
      a.visitFieldInsn(180, a, "values", "[Ljava/lang/Object;");

      for(byte a = 0; a < a.length; ++a) {
         a.visitInsn(89);
         a.visitConstant(a);
         a.visitVarInsn(25, 1);
         a.visitConstant(a);
         a.visitInsn(50);
         String a = Bytecode.getBoxingType(a[a]);
         a.visitTypeInsn(192, a != null ? a : a[a].getInternalName());
         if (a != null) {
            a.visitInsn(89);
            a.visitJumpInsn(198, a);
            a = 7;
         }

         a.visitInsn(83);
      }

      a.visitInsn(177);
      a.visitLabel(a);
      throwNPE(a, "Argument with primitive type cannot be set to NULL");
      a.visitInsn(177);
      a.visitMaxs(a, 2);
      a.visitEnd();
   }

   private static void throwNPE(MethodVisitorEx a, String a) {
      a.visitTypeInsn(187, "java/lang/NullPointerException");
      a.visitInsn(89);
      a.visitLdcInsn(a);
      a.visitMethodInsn(183, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
      a.visitInsn(191);
   }

   private static void throwAIOOBE(MethodVisitorEx a, int a) {
      a.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException");
      a.visitInsn(89);
      a.visitVarInsn(21, a);
      a.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException", "<init>", "(I)V", false);
      a.visitInsn(191);
   }

   private static void box(MethodVisitor a, Type a) {
      String a = Bytecode.getBoxingType(a);
      if (a != null) {
         String a = String.format("(%s)L%s;", a.getDescriptor(), a);
         a.visitMethodInsn(184, a, "valueOf", a, false);
      }

   }

   private static void unbox(MethodVisitor a, Type a) {
      String a = Bytecode.getBoxingType(a);
      if (a != null) {
         String a = Bytecode.getUnboxingMethod(a);
         String a = "()" + a.getDescriptor();
         a.visitTypeInsn(192, a);
         a.visitMethodInsn(182, a, a, a, false);
      } else {
         a.visitTypeInsn(192, a.getInternalName());
      }

   }

   private static String getSignature(Type[] a) {
      return (new SignaturePrinter("", (Type)null, a)).setFullyQualified(true).getFormattedArgs();
   }

   static {
      ARGS_REF = ARGS_NAME.replace('.', '/');
   }
}

package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicVerifier;

public class CheckMethodAdapter extends MethodVisitor {
   public int version;
   private int access;
   private boolean startCode;
   private boolean endCode;
   private boolean endMethod;
   private int insnCount;
   private final Map<Label, Integer> labels;
   private Set<Label> usedLabels;
   private int expandedFrames;
   private int compressedFrames;
   private int lastFrame;
   private List<Label> handlers;
   private static final int[] TYPE;
   private static Field labelStatusField;

   public CheckMethodAdapter(MethodVisitor a) {
      this(a, new HashMap());
   }

   public CheckMethodAdapter(MethodVisitor a, Map<Label, Integer> a) {
      this(327680, a, a);
      if (a.getClass() != CheckMethodAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckMethodAdapter(int a, MethodVisitor a, Map<Label, Integer> a) {
      super(a, a);
      a.lastFrame = -1;
      a.labels = a;
      a.usedLabels = new HashSet();
      a.handlers = new ArrayList();
   }

   public CheckMethodAdapter(int a, String a, String a, final MethodVisitor a, Map<Label, Integer> a) {
      this(new MethodNode(327680, a, a, a, (String)null, (String[])null) {
         public void visitEnd() {
            Analyzer axxx = new Analyzer(new BasicVerifier());

            try {
               axxx.analyze("dummy", ax);
            } catch (Exception var5) {
               if (var5 instanceof IndexOutOfBoundsException && ax.maxLocals == 0 && ax.maxStack == 0) {
                  throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
               }

               var5.printStackTrace();
               StringWriter axx = new StringWriter();
               PrintWriter axxxx = new PrintWriter(axx, true);
               CheckClassAdapter.printAnalyzerResult(ax, axxx, axxxx);
               axxxx.close();
               throw new RuntimeException(var5.getMessage() + ' ' + axx.toString());
            }

            ax.accept(a);
         }
      }, a);
      a.access = a;
   }

   public void visitParameter(String a, int a) {
      if (a != null) {
         checkUnqualifiedName(a.version, a, "name");
      }

      CheckClassAdapter.checkAccess(a, 36880);
      super.visitParameter(a, a);
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      a.checkEndMethod();
      checkDesc(a, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(a, a));
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      a.checkEndMethod();
      int a = a >>> 24;
      if (a != 1 && a != 18 && a != 20 && a != 21 && a != 22 && a != 23) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(a));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(a, a);
         checkDesc(a, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(a, a, a, a));
      }
   }

   public AnnotationVisitor visitAnnotationDefault() {
      a.checkEndMethod();
      return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
   }

   public AnnotationVisitor visitParameterAnnotation(int a, String a, boolean a) {
      a.checkEndMethod();
      checkDesc(a, false);
      return new CheckAnnotationAdapter(super.visitParameterAnnotation(a, a, a));
   }

   public void visitAttribute(Attribute a) {
      a.checkEndMethod();
      if (a == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(a);
      }
   }

   public void visitCode() {
      if ((a.access & 1024) != 0) {
         throw new RuntimeException("Abstract methods cannot have code");
      } else {
         a.startCode = true;
         super.visitCode();
      }
   }

   public void visitFrame(int a, int a, Object[] a, int a, Object[] a) {
      if (a.insnCount == a.lastFrame) {
         throw new IllegalStateException("At most one frame can be visited at a given code location.");
      } else {
         a.lastFrame = a.insnCount;
         int a;
         int a;
         switch(a) {
         case -1:
         case 0:
            a = Integer.MAX_VALUE;
            a = Integer.MAX_VALUE;
            break;
         case 1:
         case 2:
            a = 3;
            a = 0;
            break;
         case 3:
            a = 0;
            a = 0;
            break;
         case 4:
            a = 0;
            a = 1;
            break;
         default:
            throw new IllegalArgumentException("Invalid frame type " + a);
         }

         if (a > a) {
            throw new IllegalArgumentException("Invalid nLocal=" + a + " for frame type " + a);
         } else if (a > a) {
            throw new IllegalArgumentException("Invalid nStack=" + a + " for frame type " + a);
         } else {
            int a;
            if (a != 2) {
               if (a > 0 && (a == null || a.length < a)) {
                  throw new IllegalArgumentException("Array local[] is shorter than nLocal");
               }

               for(a = 0; a < a; ++a) {
                  a.checkFrameValue(a[a]);
               }
            }

            if (a > 0 && (a == null || a.length < a)) {
               throw new IllegalArgumentException("Array stack[] is shorter than nStack");
            } else {
               for(a = 0; a < a; ++a) {
                  a.checkFrameValue(a[a]);
               }

               if (a == -1) {
                  ++a.expandedFrames;
               } else {
                  ++a.compressedFrames;
               }

               if (a.expandedFrames > 0 && a.compressedFrames > 0) {
                  throw new RuntimeException("Expanded and compressed frames must not be mixed.");
               } else {
                  super.visitFrame(a, a, a, a, a);
               }
            }
         }
      }
   }

   public void visitInsn(int a) {
      a.checkStartCode();
      a.checkEndCode();
      checkOpcode(a, 0);
      super.visitInsn(a);
      ++a.insnCount;
   }

   public void visitIntInsn(int a, int a) {
      a.checkStartCode();
      a.checkEndCode();
      checkOpcode(a, 1);
      switch(a) {
      case 16:
         checkSignedByte(a, "Invalid operand");
         break;
      case 17:
         checkSignedShort(a, "Invalid operand");
         break;
      default:
         if (a < 4 || a > 11) {
            throw new IllegalArgumentException("Invalid operand (must be an array type code T_...): " + a);
         }
      }

      super.visitIntInsn(a, a);
      ++a.insnCount;
   }

   public void visitVarInsn(int a, int a) {
      a.checkStartCode();
      a.checkEndCode();
      checkOpcode(a, 2);
      checkUnsignedShort(a, "Invalid variable index");
      super.visitVarInsn(a, a);
      ++a.insnCount;
   }

   public void visitTypeInsn(int a, String a) {
      a.checkStartCode();
      a.checkEndCode();
      checkOpcode(a, 3);
      checkInternalName(a, "type");
      if (a == 187 && a.charAt(0) == '[') {
         throw new IllegalArgumentException("NEW cannot be used to create arrays: " + a);
      } else {
         super.visitTypeInsn(a, a);
         ++a.insnCount;
      }
   }

   public void visitFieldInsn(int a, String a, String a, String a) {
      a.checkStartCode();
      a.checkEndCode();
      checkOpcode(a, 4);
      checkInternalName(a, "owner");
      checkUnqualifiedName(a.version, a, "name");
      checkDesc(a, false);
      super.visitFieldInsn(a, a, a, a);
      ++a.insnCount;
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
      a.checkStartCode();
      a.checkEndCode();
      checkOpcode(a, 5);
      if (a != 183 || !"<init>".equals(a)) {
         checkMethodIdentifier(a.version, a, "name");
      }

      checkInternalName(a, "owner");
      checkMethodDesc(a);
      if (a == 182 && a) {
         throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
      } else if (a == 185 && !a) {
         throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
      } else if (a == 183 && a && (a.version & '\uffff') < 52) {
         throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
      } else {
         if (a.mv != null) {
            a.mv.visitMethodInsn(a, a, a, a, a);
         }

         ++a.insnCount;
      }
   }

   public void visitInvokeDynamicInsn(String a, String a, Handle a, Object... a) {
      a.checkStartCode();
      a.checkEndCode();
      checkMethodIdentifier(a.version, a, "name");
      checkMethodDesc(a);
      if (a.getTag() != 6 && a.getTag() != 8) {
         throw new IllegalArgumentException("invalid handle tag " + a.getTag());
      } else {
         for(int a = 0; a < a.length; ++a) {
            a.checkLDCConstant(a[a]);
         }

         super.visitInvokeDynamicInsn(a, a, a, a);
         ++a.insnCount;
      }
   }

   public void visitJumpInsn(int a, Label a) {
      a.checkStartCode();
      a.checkEndCode();
      checkOpcode(a, 6);
      a.checkLabel(a, false, "label");
      checkNonDebugLabel(a);
      super.visitJumpInsn(a, a);
      a.usedLabels.add(a);
      ++a.insnCount;
   }

   public void visitLabel(Label a) {
      a.checkStartCode();
      a.checkEndCode();
      a.checkLabel(a, false, "label");
      if (a.labels.get(a) != null) {
         throw new IllegalArgumentException("Already visited label");
      } else {
         a.labels.put(a, a.insnCount);
         super.visitLabel(a);
      }
   }

   public void visitLdcInsn(Object a) {
      a.checkStartCode();
      a.checkEndCode();
      a.checkLDCConstant(a);
      super.visitLdcInsn(a);
      ++a.insnCount;
   }

   public void visitIincInsn(int a, int a) {
      a.checkStartCode();
      a.checkEndCode();
      checkUnsignedShort(a, "Invalid variable index");
      checkSignedShort(a, "Invalid increment");
      super.visitIincInsn(a, a);
      ++a.insnCount;
   }

   public void visitTableSwitchInsn(int a, int a, Label a, Label... a) {
      a.checkStartCode();
      a.checkEndCode();
      if (a < a) {
         throw new IllegalArgumentException("Max = " + a + " must be greater than or equal to min = " + a);
      } else {
         a.checkLabel(a, false, "default label");
         checkNonDebugLabel(a);
         if (a != null && a.length == a - a + 1) {
            int a;
            for(a = 0; a < a.length; ++a) {
               a.checkLabel(a[a], false, "label at index " + a);
               checkNonDebugLabel(a[a]);
            }

            super.visitTableSwitchInsn(a, a, a, a);

            for(a = 0; a < a.length; ++a) {
               a.usedLabels.add(a[a]);
            }

            ++a.insnCount;
         } else {
            throw new IllegalArgumentException("There must be max - min + 1 labels");
         }
      }
   }

   public void visitLookupSwitchInsn(Label a, int[] a, Label[] a) {
      a.checkEndCode();
      a.checkStartCode();
      a.checkLabel(a, false, "default label");
      checkNonDebugLabel(a);
      if (a != null && a != null && a.length == a.length) {
         int a;
         for(a = 0; a < a.length; ++a) {
            a.checkLabel(a[a], false, "label at index " + a);
            checkNonDebugLabel(a[a]);
         }

         super.visitLookupSwitchInsn(a, a, a);
         a.usedLabels.add(a);

         for(a = 0; a < a.length; ++a) {
            a.usedLabels.add(a[a]);
         }

         ++a.insnCount;
      } else {
         throw new IllegalArgumentException("There must be the same number of keys and labels");
      }
   }

   public void visitMultiANewArrayInsn(String a, int a) {
      a.checkStartCode();
      a.checkEndCode();
      checkDesc(a, false);
      if (a.charAt(0) != '[') {
         throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): " + a);
      } else if (a < 1) {
         throw new IllegalArgumentException("Invalid dimensions (must be greater than 0): " + a);
      } else if (a > a.lastIndexOf(91) + 1) {
         throw new IllegalArgumentException("Invalid dimensions (must not be greater than dims(desc)): " + a);
      } else {
         super.visitMultiANewArrayInsn(a, a);
         ++a.insnCount;
      }
   }

   public AnnotationVisitor visitInsnAnnotation(int a, TypePath a, String a, boolean a) {
      a.checkStartCode();
      a.checkEndCode();
      int a = a >>> 24;
      if (a != 67 && a != 68 && a != 69 && a != 70 && a != 71 && a != 72 && a != 73 && a != 74 && a != 75) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(a));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(a, a);
         checkDesc(a, false);
         return new CheckAnnotationAdapter(super.visitInsnAnnotation(a, a, a, a));
      }
   }

   public void visitTryCatchBlock(Label a, Label a, Label a, String a) {
      a.checkStartCode();
      a.checkEndCode();
      a.checkLabel(a, false, "start label");
      a.checkLabel(a, false, "end label");
      a.checkLabel(a, false, "handler label");
      checkNonDebugLabel(a);
      checkNonDebugLabel(a);
      checkNonDebugLabel(a);
      if (a.labels.get(a) == null && a.labels.get(a) == null && a.labels.get(a) == null) {
         if (a != null) {
            checkInternalName(a, "type");
         }

         super.visitTryCatchBlock(a, a, a, a);
         a.handlers.add(a);
         a.handlers.add(a);
      } else {
         throw new IllegalStateException("Try catch blocks must be visited before their labels");
      }
   }

   public AnnotationVisitor visitTryCatchAnnotation(int a, TypePath a, String a, boolean a) {
      a.checkStartCode();
      a.checkEndCode();
      int a = a >>> 24;
      if (a != 66) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(a));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(a, a);
         checkDesc(a, false);
         return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(a, a, a, a));
      }
   }

   public void visitLocalVariable(String a, String a, String a, Label a, Label a, int a) {
      a.checkStartCode();
      a.checkEndCode();
      checkUnqualifiedName(a.version, a, "name");
      checkDesc(a, false);
      a.checkLabel(a, true, "start label");
      a.checkLabel(a, true, "end label");
      checkUnsignedShort(a, "Invalid variable index");
      int a = (Integer)a.labels.get(a);
      int a = (Integer)a.labels.get(a);
      if (a < a) {
         throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
      } else {
         super.visitLocalVariable(a, a, a, a, a, a);
      }
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int a, TypePath a, Label[] a, Label[] a, int[] a, String a, boolean a) {
      a.checkStartCode();
      a.checkEndCode();
      int a = a >>> 24;
      if (a != 64 && a != 65) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(a));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(a, a);
         checkDesc(a, false);
         if (a != null && a != null && a != null && a.length == a.length && a.length == a.length) {
            for(int a = 0; a < a.length; ++a) {
               a.checkLabel(a[a], true, "start label");
               a.checkLabel(a[a], true, "end label");
               checkUnsignedShort(a[a], "Invalid variable index");
               int a = (Integer)a.labels.get(a[a]);
               int a = (Integer)a.labels.get(a[a]);
               if (a < a) {
                  throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
               }
            }

            return super.visitLocalVariableAnnotation(a, a, a, a, a, a, a);
         } else {
            throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
         }
      }
   }

   public void visitLineNumber(int a, Label a) {
      a.checkStartCode();
      a.checkEndCode();
      checkUnsignedShort(a, "Invalid line number");
      a.checkLabel(a, true, "start label");
      super.visitLineNumber(a, a);
   }

   public void visitMaxs(int a, int a) {
      a.checkStartCode();
      a.checkEndCode();
      a.endCode = true;
      Iterator var3 = a.usedLabels.iterator();

      while(var3.hasNext()) {
         Label a = (Label)var3.next();
         if (a.labels.get(a) == null) {
            throw new IllegalStateException("Undefined label used");
         }
      }

      int a = 0;

      Integer a;
      Integer a;
      do {
         if (a >= a.handlers.size()) {
            checkUnsignedShort(a, "Invalid max stack");
            checkUnsignedShort(a, "Invalid max locals");
            super.visitMaxs(a, a);
            return;
         }

         a = (Integer)a.labels.get(a.handlers.get(a++));
         a = (Integer)a.labels.get(a.handlers.get(a++));
         if (a == null || a == null) {
            throw new IllegalStateException("Undefined try catch block labels");
         }
      } while(a > a);

      throw new IllegalStateException("Emty try catch block handler range");
   }

   public void visitEnd() {
      a.checkEndMethod();
      a.endMethod = true;
      super.visitEnd();
   }

   void checkStartCode() {
      if (!a.startCode) {
         throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
      }
   }

   void checkEndCode() {
      if (a.endCode) {
         throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
      }
   }

   void checkEndMethod() {
      if (a.endMethod) {
         throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
      }
   }

   void checkFrameValue(Object a) {
      if (a != Opcodes.TOP && a != Opcodes.INTEGER && a != Opcodes.FLOAT && a != Opcodes.LONG && a != Opcodes.DOUBLE && a != Opcodes.NULL && a != Opcodes.UNINITIALIZED_THIS) {
         if (a instanceof String) {
            checkInternalName((String)a, "Invalid stack frame value");
         } else if (!(a instanceof Label)) {
            throw new IllegalArgumentException("Invalid stack frame value: " + a);
         } else {
            a.usedLabels.add((Label)a);
         }
      }
   }

   static void checkOpcode(int a, int a) {
      if (a < 0 || a > 199 || TYPE[a] != a) {
         throw new IllegalArgumentException("Invalid opcode: " + a);
      }
   }

   static void checkSignedByte(int a, String a) {
      if (a < -128 || a > 127) {
         throw new IllegalArgumentException(a + " (must be a signed byte): " + a);
      }
   }

   static void checkSignedShort(int a, String a) {
      if (a < -32768 || a > 32767) {
         throw new IllegalArgumentException(a + " (must be a signed short): " + a);
      }
   }

   static void checkUnsignedShort(int a, String a) {
      if (a < 0 || a > 65535) {
         throw new IllegalArgumentException(a + " (must be an unsigned short): " + a);
      }
   }

   static void checkConstant(Object a) {
      if (!(a instanceof Integer) && !(a instanceof Float) && !(a instanceof Long) && !(a instanceof Double) && !(a instanceof String)) {
         throw new IllegalArgumentException("Invalid constant: " + a);
      }
   }

   void checkLDCConstant(Object a) {
      int a;
      if (a instanceof Type) {
         a = ((Type)a).getSort();
         if (a != 10 && a != 9 && a != 11) {
            throw new IllegalArgumentException("Illegal LDC constant value");
         }

         if (a != 11 && (a.version & '\uffff') < 49) {
            throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
         }

         if (a == 11 && (a.version & '\uffff') < 51) {
            throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
         }
      } else if (a instanceof Handle) {
         if ((a.version & '\uffff') < 51) {
            throw new IllegalArgumentException("ldc of a handle requires at least version 1.7");
         }

         a = ((Handle)a).getTag();
         if (a < 1 || a > 9) {
            throw new IllegalArgumentException("invalid handle tag " + a);
         }
      } else {
         checkConstant(a);
      }

   }

   static void checkUnqualifiedName(int a, String a, String a) {
      if ((a & '\uffff') < 49) {
         checkIdentifier(a, a);
      } else {
         for(int a = 0; a < a.length(); ++a) {
            if (".;[/".indexOf(a.charAt(a)) != -1) {
               throw new IllegalArgumentException("Invalid " + a + " (must be a valid unqualified name): " + a);
            }
         }
      }

   }

   static void checkIdentifier(String a, String a) {
      checkIdentifier(a, 0, -1, a);
   }

   static void checkIdentifier(String a, int a, int a, String a) {
      if (a != null) {
         if (a == -1) {
            if (a.length() <= a) {
               throw new IllegalArgumentException("Invalid " + a + " (must not be null or empty)");
            }
         } else if (a <= a) {
            throw new IllegalArgumentException("Invalid " + a + " (must not be null or empty)");
         }

         if (!Character.isJavaIdentifierStart(a.charAt(a))) {
            throw new IllegalArgumentException("Invalid " + a + " (must be a valid Java identifier): " + a);
         } else {
            int a = a == -1 ? a.length() : a;

            for(int a = a + 1; a < a; ++a) {
               if (!Character.isJavaIdentifierPart(a.charAt(a))) {
                  throw new IllegalArgumentException("Invalid " + a + " (must be a valid Java identifier): " + a);
               }
            }

         }
      } else {
         throw new IllegalArgumentException("Invalid " + a + " (must not be null or empty)");
      }
   }

   static void checkMethodIdentifier(int a, String a, String a) {
      if (a != null && a.length() != 0) {
         int a;
         if ((a & '\uffff') >= 49) {
            for(a = 0; a < a.length(); ++a) {
               if (".;[/<>".indexOf(a.charAt(a)) != -1) {
                  throw new IllegalArgumentException("Invalid " + a + " (must be a valid unqualified name): " + a);
               }
            }

         } else if (!Character.isJavaIdentifierStart(a.charAt(0))) {
            throw new IllegalArgumentException("Invalid " + a + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + a);
         } else {
            for(a = 1; a < a.length(); ++a) {
               if (!Character.isJavaIdentifierPart(a.charAt(a))) {
                  throw new IllegalArgumentException("Invalid " + a + " (must be '<init>' or '<clinit>' or a valid Java identifier): " + a);
               }
            }

         }
      } else {
         throw new IllegalArgumentException("Invalid " + a + " (must not be null or empty)");
      }
   }

   static void checkInternalName(String a, String a) {
      if (a != null && a.length() != 0) {
         if (a.charAt(0) == '[') {
            checkDesc(a, false);
         } else {
            checkInternalName(a, 0, -1, a);
         }

      } else {
         throw new IllegalArgumentException("Invalid " + a + " (must not be null or empty)");
      }
   }

   static void checkInternalName(String a, int a, int a, String a) {
      int a = a == -1 ? a.length() : a;

      try {
         int a = a;

         int a;
         do {
            a = a.indexOf(47, a + 1);
            if (a == -1 || a > a) {
               a = a;
            }

            checkIdentifier(a, a, a, (String)null);
            a = a + 1;
         } while(a != a);

      } catch (IllegalArgumentException var7) {
         throw new IllegalArgumentException("Invalid " + a + " (must be a fully qualified class name in internal form): " + a);
      }
   }

   static void checkDesc(String a, boolean a) {
      int a = checkDesc(a, 0, a);
      if (a != a.length()) {
         throw new IllegalArgumentException("Invalid descriptor: " + a);
      }
   }

   static int checkDesc(String a, int a, boolean a) {
      if (a != null && a < a.length()) {
         int a;
         switch(a.charAt(a)) {
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
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new IllegalArgumentException("Invalid descriptor: " + a);
         case 'L':
            a = a.indexOf(59, a);
            if (a != -1 && a - a >= 2) {
               try {
                  checkInternalName(a, a + 1, a, (String)null);
               } catch (IllegalArgumentException var5) {
                  throw new IllegalArgumentException("Invalid descriptor: " + a);
               }

               return a + 1;
            }

            throw new IllegalArgumentException("Invalid descriptor: " + a);
         case 'V':
            if (a) {
               return a + 1;
            }

            throw new IllegalArgumentException("Invalid descriptor: " + a);
         case '[':
            for(a = a + 1; a < a.length() && a.charAt(a) == '['; ++a) {
            }

            if (a < a.length()) {
               return checkDesc(a, a, false);
            } else {
               throw new IllegalArgumentException("Invalid descriptor: " + a);
            }
         }
      } else {
         throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
      }
   }

   static void checkMethodDesc(String a) {
      if (a != null && a.length() != 0) {
         if (a.charAt(0) == '(' && a.length() >= 3) {
            int a = 1;
            if (a.charAt(a) != ')') {
               do {
                  if (a.charAt(a) == 'V') {
                     throw new IllegalArgumentException("Invalid descriptor: " + a);
                  }

                  a = checkDesc(a, a, false);
               } while(a < a.length() && a.charAt(a) != ')');
            }

            a = checkDesc(a, a + 1, true);
            if (a != a.length()) {
               throw new IllegalArgumentException("Invalid descriptor: " + a);
            }
         } else {
            throw new IllegalArgumentException("Invalid descriptor: " + a);
         }
      } else {
         throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
      }
   }

   void checkLabel(Label a, boolean a, String a) {
      if (a == null) {
         throw new IllegalArgumentException("Invalid " + a + " (must not be null)");
      } else if (a && a.labels.get(a) == null) {
         throw new IllegalArgumentException("Invalid " + a + " (must be visited first)");
      }
   }

   private static void checkNonDebugLabel(Label a) {
      Field a = getLabelStatusField();
      boolean var2 = false;

      int a;
      try {
         a = a == null ? 0 : (Integer)a.get(a);
      } catch (IllegalAccessException var4) {
         throw new Error("Internal error");
      }

      if ((a & 1) != 0) {
         throw new IllegalArgumentException("Labels used for debug info cannot be reused for control flow");
      }
   }

   private static Field getLabelStatusField() {
      if (labelStatusField == null) {
         labelStatusField = getLabelField("a");
         if (labelStatusField == null) {
            labelStatusField = getLabelField("status");
         }
      }

      return labelStatusField;
   }

   private static Field getLabelField(String a) {
      try {
         Field a = Label.class.getDeclaredField(a);
         a.setAccessible(true);
         return a;
      } catch (NoSuchFieldException var2) {
         return null;
      }
   }

   static {
      String a = "BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA";
      TYPE = new int[a.length()];

      for(int a = 0; a < TYPE.length; ++a) {
         TYPE[a] = a.charAt(a) - 65 - 1;
      }

   }
}

package org.spongepowered.asm.util;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.IntInsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.lib.util.TraceClassVisitor;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

public final class Bytecode {
   public static final int[] CONSTANTS_INT = new int[]{2, 3, 4, 5, 6, 7, 8};
   public static final int[] CONSTANTS_FLOAT = new int[]{11, 12, 13};
   public static final int[] CONSTANTS_DOUBLE = new int[]{14, 15};
   public static final int[] CONSTANTS_LONG = new int[]{9, 10};
   public static final int[] CONSTANTS_ALL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
   private static final Object[] CONSTANTS_VALUES = new Object[]{null, -1, 0, 1, 2, 3, 4, 5, 0L, 1L, 0.0F, 1.0F, 2.0F, 0.0D, 1.0D};
   private static final String[] CONSTANTS_TYPES = new String[]{null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I"};
   private static final String[] BOXING_TYPES = new String[]{null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, null, null};
   private static final String[] UNBOXING_METHODS = new String[]{null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, null, null};
   private static final Class<?>[] MERGEABLE_MIXIN_ANNOTATIONS = new Class[]{Overwrite.class, Intrinsic.class, Final.class, Debug.class};
   private static Pattern mergeableAnnotationPattern = getMergeableAnnotationPattern();
   private static final Logger logger = LogManager.getLogger("mixin");

   private Bytecode() {
   }

   public static MethodNode findMethod(ClassNode a, String a, String a) {
      Iterator var3 = a.methods.iterator();

      MethodNode a;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         a = (MethodNode)var3.next();
      } while(!a.name.equals(a) || !a.desc.equals(a));

      return a;
   }

   public static AbstractInsnNode findInsn(MethodNode a, int a) {
      ListIterator a = a.instructions.iterator();

      AbstractInsnNode a;
      do {
         if (!a.hasNext()) {
            return null;
         }

         a = (AbstractInsnNode)a.next();
      } while(a.getOpcode() != a);

      return a;
   }

   public static MethodInsnNode findSuperInit(MethodNode a, String a) {
      if (!"<init>".equals(a.name)) {
         return null;
      } else {
         int a = 0;
         ListIterator a = a.instructions.iterator();

         while(true) {
            while(a.hasNext()) {
               AbstractInsnNode a = (AbstractInsnNode)a.next();
               if (a instanceof TypeInsnNode && a.getOpcode() == 187) {
                  ++a;
               } else if (a instanceof MethodInsnNode && a.getOpcode() == 183) {
                  MethodInsnNode a = (MethodInsnNode)a;
                  if ("<init>".equals(a.name)) {
                     if (a > 0) {
                        --a;
                     } else if (a.owner.equals(a)) {
                        return a;
                     }
                  }
               }
            }

            return null;
         }
      }
   }

   public static void textify(ClassNode a, OutputStream a) {
      a.accept(new TraceClassVisitor(new PrintWriter(a)));
   }

   public static void textify(MethodNode a, OutputStream a) {
      TraceClassVisitor a = new TraceClassVisitor(new PrintWriter(a));
      MethodVisitor a = a.visitMethod(a.access, a.name, a.desc, a.signature, (String[])a.exceptions.toArray(new String[0]));
      a.accept(a);
      a.visitEnd();
   }

   public static void dumpClass(ClassNode a) {
      ClassWriter a = new ClassWriter(3);
      a.accept(a);
      dumpClass(a.toByteArray());
   }

   public static void dumpClass(byte[] a) {
      ClassReader a = new ClassReader(a);
      CheckClassAdapter.verify(a, true, new PrintWriter(System.out));
   }

   public static void printMethodWithOpcodeIndices(MethodNode a) {
      System.err.printf("%s%s\n", a.name, a.desc);
      int a = 0;
      ListIterator a = a.instructions.iterator();

      while(a.hasNext()) {
         System.err.printf("[%4d] %s\n", a++, describeNode((AbstractInsnNode)a.next()));
      }

   }

   public static void printMethod(MethodNode a) {
      System.err.printf("%s%s\n", a.name, a.desc);
      ListIterator a = a.instructions.iterator();

      while(a.hasNext()) {
         System.err.print("  ");
         printNode((AbstractInsnNode)a.next());
      }

   }

   public static void printNode(AbstractInsnNode a) {
      System.err.printf("%s\n", describeNode(a));
   }

   public static String describeNode(AbstractInsnNode a) {
      if (a == null) {
         return String.format("   %-14s ", "null");
      } else if (a instanceof LabelNode) {
         return String.format("[%s]", ((LabelNode)a).getLabel());
      } else {
         String a = String.format("   %-14s ", a.getClass().getSimpleName().replace("Node", ""));
         if (a instanceof JumpInsnNode) {
            a = a + String.format("[%s] [%s]", getOpcodeName(a), ((JumpInsnNode)a).label.getLabel());
         } else if (a instanceof VarInsnNode) {
            a = a + String.format("[%s] %d", getOpcodeName(a), ((VarInsnNode)a).var);
         } else if (a instanceof MethodInsnNode) {
            MethodInsnNode a = (MethodInsnNode)a;
            a = a + String.format("[%s] %s %s %s", getOpcodeName(a), a.owner, a.name, a.desc);
         } else if (a instanceof FieldInsnNode) {
            FieldInsnNode a = (FieldInsnNode)a;
            a = a + String.format("[%s] %s %s %s", getOpcodeName(a), a.owner, a.name, a.desc);
         } else if (a instanceof LineNumberNode) {
            LineNumberNode a = (LineNumberNode)a;
            a = a + String.format("LINE=[%d] LABEL=[%s]", a.line, a.start.getLabel());
         } else if (a instanceof LdcInsnNode) {
            a = a + ((LdcInsnNode)a).cst;
         } else if (a instanceof IntInsnNode) {
            a = a + ((IntInsnNode)a).operand;
         } else if (a instanceof FrameNode) {
            a = a + String.format("[%s] ", getOpcodeName(((FrameNode)a).type, "H_INVOKEINTERFACE", -1));
         } else {
            a = a + String.format("[%s] ", getOpcodeName(a));
         }

         return a;
      }
   }

   public static String getOpcodeName(AbstractInsnNode a) {
      return a != null ? getOpcodeName(a.getOpcode()) : "";
   }

   public static String getOpcodeName(int a) {
      return getOpcodeName(a, "UNINITIALIZED_THIS", 1);
   }

   private static String getOpcodeName(int a, String a, int a) {
      if (a >= a) {
         boolean a = false;

         try {
            Field[] var4 = Opcodes.class.getDeclaredFields();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Field a = var4[var6];
               if (a || a.getName().equals(a)) {
                  a = true;
                  if (a.getType() == Integer.TYPE && a.getInt((Object)null) == a) {
                     return a.getName();
                  }
               }
            }
         } catch (Exception var8) {
         }
      }

      return a >= 0 ? String.valueOf(a) : "UNKNOWN";
   }

   public static boolean methodHasLineNumbers(MethodNode a) {
      ListIterator a = a.instructions.iterator();

      do {
         if (!a.hasNext()) {
            return false;
         }
      } while(!(a.next() instanceof LineNumberNode));

      return true;
   }

   public static boolean methodIsStatic(MethodNode a) {
      return (a.access & 8) == 8;
   }

   public static boolean fieldIsStatic(FieldNode a) {
      return (a.access & 8) == 8;
   }

   public static int getFirstNonArgLocalIndex(MethodNode a) {
      return getFirstNonArgLocalIndex(Type.getArgumentTypes(a.desc), (a.access & 8) == 0);
   }

   public static int getFirstNonArgLocalIndex(Type[] a, boolean a) {
      return getArgsSize(a) + (a ? 1 : 0);
   }

   public static int getArgsSize(Type[] a) {
      int a = 0;
      Type[] var2 = a;
      int var3 = a.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type a = var2[var4];
         a += a.getSize();
      }

      return a;
   }

   public static void loadArgs(Type[] a, InsnList a, int a) {
      loadArgs(a, a, a, -1);
   }

   public static void loadArgs(Type[] a, InsnList a, int a, int a) {
      loadArgs(a, a, a, a, (Type[])null);
   }

   public static void loadArgs(Type[] a, InsnList a, int a, int a, Type[] a) {
      int a = a;
      int a = 0;
      Type[] var7 = a;
      int var8 = a.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Type a = var7[var9];
         a.add((AbstractInsnNode)(new VarInsnNode(a.getOpcode(21), a)));
         if (a != null && a < a.length && a[a] != null) {
            a.add((AbstractInsnNode)(new TypeInsnNode(192, a[a].getInternalName())));
         }

         a += a.getSize();
         if (a >= a && a >= a) {
            return;
         }

         ++a;
      }

   }

   public static Map<LabelNode, LabelNode> cloneLabels(InsnList a) {
      Map<LabelNode, LabelNode> a = new HashMap();
      ListIterator a = a.iterator();

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (a instanceof LabelNode) {
            a.put((LabelNode)a, new LabelNode(((LabelNode)a).getLabel()));
         }
      }

      return a;
   }

   public static String generateDescriptor(Object a, Object... a) {
      StringBuilder a = (new StringBuilder()).append('(');
      Object[] var3 = a;
      int var4 = a.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object a = var3[var5];
         a.append(toDescriptor(a));
      }

      return a.append(')').append(a != null ? toDescriptor(a) : "V").toString();
   }

   private static String toDescriptor(Object a) {
      if (a instanceof String) {
         return (String)a;
      } else if (a instanceof Type) {
         return a.toString();
      } else if (a instanceof Class) {
         return Type.getDescriptor((Class)a);
      } else {
         return a == null ? "" : a.toString();
      }
   }

   public static String getDescriptor(Type[] a) {
      return "(" + Joiner.on("").join(a) + ")";
   }

   public static String getDescriptor(Type[] a, Type a) {
      return getDescriptor(a) + a.toString();
   }

   public static String changeDescriptorReturnType(String a, String a) {
      if (a == null) {
         return null;
      } else {
         return a == null ? a : a.substring(0, a.lastIndexOf(41) + 1) + a;
      }
   }

   public static String getSimpleName(Class<? extends Annotation> a) {
      return a.getSimpleName();
   }

   public static String getSimpleName(AnnotationNode a) {
      return getSimpleName(a.desc);
   }

   public static String getSimpleName(String a) {
      int a = Math.max(a.lastIndexOf(47), 0);
      return a.substring(a + 1).replace(";", "");
   }

   public static boolean isConstant(AbstractInsnNode a) {
      return a == null ? false : Ints.contains(CONSTANTS_ALL, a.getOpcode());
   }

   public static Object getConstant(AbstractInsnNode a) {
      if (a == null) {
         return null;
      } else if (a instanceof LdcInsnNode) {
         return ((LdcInsnNode)a).cst;
      } else {
         int a;
         if (a instanceof IntInsnNode) {
            a = ((IntInsnNode)a).operand;
            if (a.getOpcode() != 16 && a.getOpcode() != 17) {
               throw new IllegalArgumentException("IntInsnNode with invalid opcode " + a.getOpcode() + " in getConstant");
            } else {
               return a;
            }
         } else {
            a = Ints.indexOf(CONSTANTS_ALL, a.getOpcode());
            return a < 0 ? null : CONSTANTS_VALUES[a];
         }
      }
   }

   public static Type getConstantType(AbstractInsnNode a) {
      if (a == null) {
         return null;
      } else if (a instanceof LdcInsnNode) {
         Object a = ((LdcInsnNode)a).cst;
         if (a instanceof Integer) {
            return Type.getType("I");
         } else if (a instanceof Float) {
            return Type.getType("F");
         } else if (a instanceof Long) {
            return Type.getType("J");
         } else if (a instanceof Double) {
            return Type.getType("D");
         } else if (a instanceof String) {
            return Type.getType("Ljava/lang/String;");
         } else if (a instanceof Type) {
            return Type.getType("Ljava/lang/Class;");
         } else {
            throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + a.getClass() + " in getConstant");
         }
      } else {
         int a = Ints.indexOf(CONSTANTS_ALL, a.getOpcode());
         return a < 0 ? null : Type.getType(CONSTANTS_TYPES[a]);
      }
   }

   public static boolean hasFlag(ClassNode a, int a) {
      return (a.access & a) == a;
   }

   public static boolean hasFlag(MethodNode a, int a) {
      return (a.access & a) == a;
   }

   public static boolean hasFlag(FieldNode a, int a) {
      return (a.access & a) == a;
   }

   public static boolean compareFlags(MethodNode a, MethodNode a, int a) {
      return hasFlag(a, a) == hasFlag(a, a);
   }

   public static boolean compareFlags(FieldNode a, FieldNode a, int a) {
      return hasFlag(a, a) == hasFlag(a, a);
   }

   public static Bytecode.Visibility getVisibility(MethodNode a) {
      return getVisibility(a.access & 7);
   }

   public static Bytecode.Visibility getVisibility(FieldNode a) {
      return getVisibility(a.access & 7);
   }

   private static Bytecode.Visibility getVisibility(int a) {
      if ((a & 4) != 0) {
         return Bytecode.Visibility.PROTECTED;
      } else if ((a & 2) != 0) {
         return Bytecode.Visibility.PRIVATE;
      } else {
         return (a & 1) != 0 ? Bytecode.Visibility.PUBLIC : Bytecode.Visibility.PACKAGE;
      }
   }

   public static void setVisibility(MethodNode a, Bytecode.Visibility a) {
      a.access = setVisibility(a.access, a.access);
   }

   public static void setVisibility(FieldNode a, Bytecode.Visibility a) {
      a.access = setVisibility(a.access, a.access);
   }

   public static void setVisibility(MethodNode a, int a) {
      a.access = setVisibility(a.access, a);
   }

   public static void setVisibility(FieldNode a, int a) {
      a.access = setVisibility(a.access, a);
   }

   private static int setVisibility(int a, int a) {
      return a & -8 | a & 7;
   }

   public static int getMaxLineNumber(ClassNode a, int a, int a) {
      int a = 0;
      Iterator var4 = a.methods.iterator();

      while(var4.hasNext()) {
         MethodNode a = (MethodNode)var4.next();
         ListIterator a = a.instructions.iterator();

         while(a.hasNext()) {
            AbstractInsnNode a = (AbstractInsnNode)a.next();
            if (a instanceof LineNumberNode) {
               a = Math.max(a, ((LineNumberNode)a).line);
            }
         }
      }

      return Math.max(a, a + a);
   }

   public static String getBoxingType(Type a) {
      return a == null ? null : BOXING_TYPES[a.getSort()];
   }

   public static String getUnboxingMethod(Type a) {
      return a == null ? null : UNBOXING_METHODS[a.getSort()];
   }

   public static void mergeAnnotations(ClassNode a, ClassNode a) {
      a.visibleAnnotations = mergeAnnotations(a.visibleAnnotations, a.visibleAnnotations, "class", a.name);
      a.invisibleAnnotations = mergeAnnotations(a.invisibleAnnotations, a.invisibleAnnotations, "class", a.name);
   }

   public static void mergeAnnotations(MethodNode a, MethodNode a) {
      a.visibleAnnotations = mergeAnnotations(a.visibleAnnotations, a.visibleAnnotations, "method", a.name);
      a.invisibleAnnotations = mergeAnnotations(a.invisibleAnnotations, a.invisibleAnnotations, "method", a.name);
   }

   public static void mergeAnnotations(FieldNode a, FieldNode a) {
      a.visibleAnnotations = mergeAnnotations(a.visibleAnnotations, a.visibleAnnotations, "field", a.name);
      a.invisibleAnnotations = mergeAnnotations(a.invisibleAnnotations, a.invisibleAnnotations, "field", a.name);
   }

   private static List<AnnotationNode> mergeAnnotations(List<AnnotationNode> a, List<AnnotationNode> a, String a, String a) {
      try {
         if (a == null) {
            return (List)a;
         } else {
            if (a == null) {
               a = new ArrayList();
            }

            Iterator var4 = a.iterator();

            while(true) {
               AnnotationNode a;
               do {
                  if (!var4.hasNext()) {
                     return (List)a;
                  }

                  a = (AnnotationNode)var4.next();
               } while(!isMergeableAnnotation(a));

               Iterator a = ((List)a).iterator();

               while(a.hasNext()) {
                  if (((AnnotationNode)a.next()).desc.equals(a.desc)) {
                     a.remove();
                     break;
                  }
               }

               ((List)a).add(a);
            }
         }
      } catch (Exception var7) {
         logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[]{a, a});
         return (List)a;
      }
   }

   private static boolean isMergeableAnnotation(AnnotationNode a) {
      return a.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF) ? mergeableAnnotationPattern.matcher(a.desc).matches() : true;
   }

   private static Pattern getMergeableAnnotationPattern() {
      StringBuilder a = new StringBuilder("^L(");

      for(int a = 0; a < MERGEABLE_MIXIN_ANNOTATIONS.length; ++a) {
         if (a > 0) {
            a.append('|');
         }

         a.append(MERGEABLE_MIXIN_ANNOTATIONS[a].getName().replace('.', '/'));
      }

      return Pattern.compile(a.append(");$").toString());
   }

   public static void compareBridgeMethods(MethodNode a, MethodNode a) {
      ListIterator<AbstractInsnNode> a = a.instructions.iterator();
      ListIterator<AbstractInsnNode> a = a.instructions.iterator();

      int a;
      for(a = 0; a.hasNext() && a.hasNext(); ++a) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (!(a instanceof LabelNode)) {
            if (a instanceof MethodInsnNode) {
               MethodInsnNode a = (MethodInsnNode)a;
               MethodInsnNode a = (MethodInsnNode)a;
               if (!a.name.equals(a.name)) {
                  throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_NAME, a.name, a.desc, a, a, a);
               }

               if (!a.desc.equals(a.desc)) {
                  throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_DESC, a.name, a.desc, a, a, a);
               }
            } else {
               if (a.getOpcode() != a.getOpcode()) {
                  throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INSN, a.name, a.desc, a, a, a);
               }

               if (a instanceof VarInsnNode) {
                  VarInsnNode a = (VarInsnNode)a;
                  VarInsnNode a = (VarInsnNode)a;
                  if (a.var != a.var) {
                     throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LOAD, a.name, a.desc, a, a, a);
                  }
               } else if (a instanceof TypeInsnNode) {
                  TypeInsnNode a = (TypeInsnNode)a;
                  TypeInsnNode a = (TypeInsnNode)a;
                  if (a.getOpcode() == 192 && !a.desc.equals(a.desc)) {
                     throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_CAST, a.name, a.desc, a, a, a);
                  }
               }
            }
         }
      }

      if (a.hasNext() || a.hasNext()) {
         throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LENGTH, a.name, a.desc, a, (AbstractInsnNode)null, (AbstractInsnNode)null);
      }
   }

   public static enum Visibility {
      PRIVATE(2),
      PROTECTED(4),
      PACKAGE(0),
      PUBLIC(1);

      static final int MASK = 7;
      final int access;

      private Visibility(int a) {
         a.access = a;
      }
   }
}

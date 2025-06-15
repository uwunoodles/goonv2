package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

public class MethodNode extends MethodVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public List<String> exceptions;
   public List<ParameterNode> parameters;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;
   public Object annotationDefault;
   public List<AnnotationNode>[] visibleParameterAnnotations;
   public List<AnnotationNode>[] invisibleParameterAnnotations;
   public InsnList instructions;
   public List<TryCatchBlockNode> tryCatchBlocks;
   public int maxStack;
   public int maxLocals;
   public List<LocalVariableNode> localVariables;
   public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
   public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
   private boolean visited;

   public MethodNode() {
      this(327680);
      if (a.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int a) {
      super(a);
      a.instructions = new InsnList();
   }

   public MethodNode(int a, String a, String a, String a, String[] a) {
      this(327680, a, a, a, a, a);
      if (a.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int a, int a, String a, String a, String a, String[] a) {
      super(a);
      a.access = a;
      a.name = a;
      a.desc = a;
      a.signature = a;
      a.exceptions = new ArrayList(a == null ? 0 : a.length);
      boolean a = (a & 1024) != 0;
      if (!a) {
         a.localVariables = new ArrayList(5);
      }

      a.tryCatchBlocks = new ArrayList();
      if (a != null) {
         a.exceptions.addAll(Arrays.asList(a));
      }

      a.instructions = new InsnList();
   }

   public void visitParameter(String a, int a) {
      if (a.parameters == null) {
         a.parameters = new ArrayList(5);
      }

      a.parameters.add(new ParameterNode(a, a));
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return new AnnotationNode(new ArrayList<Object>(0) {
         public boolean add(Object axx) {
            a.annotationDefault = axx;
            return super.add(axx);
         }
      });
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      AnnotationNode a = new AnnotationNode(a);
      if (a) {
         if (a.visibleAnnotations == null) {
            a.visibleAnnotations = new ArrayList(1);
         }

         a.visibleAnnotations.add(a);
      } else {
         if (a.invisibleAnnotations == null) {
            a.invisibleAnnotations = new ArrayList(1);
         }

         a.invisibleAnnotations.add(a);
      }

      return a;
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      TypeAnnotationNode a = new TypeAnnotationNode(a, a, a);
      if (a) {
         if (a.visibleTypeAnnotations == null) {
            a.visibleTypeAnnotations = new ArrayList(1);
         }

         a.visibleTypeAnnotations.add(a);
      } else {
         if (a.invisibleTypeAnnotations == null) {
            a.invisibleTypeAnnotations = new ArrayList(1);
         }

         a.invisibleTypeAnnotations.add(a);
      }

      return a;
   }

   public AnnotationVisitor visitParameterAnnotation(int a, String a, boolean a) {
      AnnotationNode a = new AnnotationNode(a);
      int a;
      if (a) {
         if (a.visibleParameterAnnotations == null) {
            a = Type.getArgumentTypes(a.desc).length;
            a.visibleParameterAnnotations = (List[])(new List[a]);
         }

         if (a.visibleParameterAnnotations[a] == null) {
            a.visibleParameterAnnotations[a] = new ArrayList(1);
         }

         a.visibleParameterAnnotations[a].add(a);
      } else {
         if (a.invisibleParameterAnnotations == null) {
            a = Type.getArgumentTypes(a.desc).length;
            a.invisibleParameterAnnotations = (List[])(new List[a]);
         }

         if (a.invisibleParameterAnnotations[a] == null) {
            a.invisibleParameterAnnotations[a] = new ArrayList(1);
         }

         a.invisibleParameterAnnotations[a].add(a);
      }

      return a;
   }

   public void visitAttribute(Attribute a) {
      if (a.attrs == null) {
         a.attrs = new ArrayList(1);
      }

      a.attrs.add(a);
   }

   public void visitCode() {
   }

   public void visitFrame(int a, int a, Object[] a, int a, Object[] a) {
      a.instructions.add((AbstractInsnNode)(new FrameNode(a, a, a == null ? null : a.getLabelNodes(a), a, a == null ? null : a.getLabelNodes(a))));
   }

   public void visitInsn(int a) {
      a.instructions.add((AbstractInsnNode)(new InsnNode(a)));
   }

   public void visitIntInsn(int a, int a) {
      a.instructions.add((AbstractInsnNode)(new IntInsnNode(a, a)));
   }

   public void visitVarInsn(int a, int a) {
      a.instructions.add((AbstractInsnNode)(new VarInsnNode(a, a)));
   }

   public void visitTypeInsn(int a, String a) {
      a.instructions.add((AbstractInsnNode)(new TypeInsnNode(a, a)));
   }

   public void visitFieldInsn(int a, String a, String a, String a) {
      a.instructions.add((AbstractInsnNode)(new FieldInsnNode(a, a, a, a)));
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int a, String a, String a, String a) {
      if (a.api >= 327680) {
         super.visitMethodInsn(a, a, a, a);
      } else {
         a.instructions.add((AbstractInsnNode)(new MethodInsnNode(a, a, a, a)));
      }
   }

   public void visitMethodInsn(int a, String a, String a, String a, boolean a) {
      if (a.api < 327680) {
         super.visitMethodInsn(a, a, a, a, a);
      } else {
         a.instructions.add((AbstractInsnNode)(new MethodInsnNode(a, a, a, a, a)));
      }
   }

   public void visitInvokeDynamicInsn(String a, String a, Handle a, Object... a) {
      a.instructions.add((AbstractInsnNode)(new InvokeDynamicInsnNode(a, a, a, a)));
   }

   public void visitJumpInsn(int a, Label a) {
      a.instructions.add((AbstractInsnNode)(new JumpInsnNode(a, a.getLabelNode(a))));
   }

   public void visitLabel(Label a) {
      a.instructions.add((AbstractInsnNode)a.getLabelNode(a));
   }

   public void visitLdcInsn(Object a) {
      a.instructions.add((AbstractInsnNode)(new LdcInsnNode(a)));
   }

   public void visitIincInsn(int a, int a) {
      a.instructions.add((AbstractInsnNode)(new IincInsnNode(a, a)));
   }

   public void visitTableSwitchInsn(int a, int a, Label a, Label... a) {
      a.instructions.add((AbstractInsnNode)(new TableSwitchInsnNode(a, a, a.getLabelNode(a), a.getLabelNodes(a))));
   }

   public void visitLookupSwitchInsn(Label a, int[] a, Label[] a) {
      a.instructions.add((AbstractInsnNode)(new LookupSwitchInsnNode(a.getLabelNode(a), a, a.getLabelNodes(a))));
   }

   public void visitMultiANewArrayInsn(String a, int a) {
      a.instructions.add((AbstractInsnNode)(new MultiANewArrayInsnNode(a, a)));
   }

   public AnnotationVisitor visitInsnAnnotation(int a, TypePath a, String a, boolean a) {
      AbstractInsnNode a;
      for(a = a.instructions.getLast(); a.getOpcode() == -1; a = a.getPrevious()) {
      }

      TypeAnnotationNode a = new TypeAnnotationNode(a, a, a);
      if (a) {
         if (a.visibleTypeAnnotations == null) {
            a.visibleTypeAnnotations = new ArrayList(1);
         }

         a.visibleTypeAnnotations.add(a);
      } else {
         if (a.invisibleTypeAnnotations == null) {
            a.invisibleTypeAnnotations = new ArrayList(1);
         }

         a.invisibleTypeAnnotations.add(a);
      }

      return a;
   }

   public void visitTryCatchBlock(Label a, Label a, Label a, String a) {
      a.tryCatchBlocks.add(new TryCatchBlockNode(a.getLabelNode(a), a.getLabelNode(a), a.getLabelNode(a), a));
   }

   public AnnotationVisitor visitTryCatchAnnotation(int a, TypePath a, String a, boolean a) {
      TryCatchBlockNode a = (TryCatchBlockNode)a.tryCatchBlocks.get((a & 16776960) >> 8);
      TypeAnnotationNode a = new TypeAnnotationNode(a, a, a);
      if (a) {
         if (a.visibleTypeAnnotations == null) {
            a.visibleTypeAnnotations = new ArrayList(1);
         }

         a.visibleTypeAnnotations.add(a);
      } else {
         if (a.invisibleTypeAnnotations == null) {
            a.invisibleTypeAnnotations = new ArrayList(1);
         }

         a.invisibleTypeAnnotations.add(a);
      }

      return a;
   }

   public void visitLocalVariable(String a, String a, String a, Label a, Label a, int a) {
      a.localVariables.add(new LocalVariableNode(a, a, a, a.getLabelNode(a), a.getLabelNode(a), a));
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int a, TypePath a, Label[] a, Label[] a, int[] a, String a, boolean a) {
      LocalVariableAnnotationNode a = new LocalVariableAnnotationNode(a, a, a.getLabelNodes(a), a.getLabelNodes(a), a, a);
      if (a) {
         if (a.visibleLocalVariableAnnotations == null) {
            a.visibleLocalVariableAnnotations = new ArrayList(1);
         }

         a.visibleLocalVariableAnnotations.add(a);
      } else {
         if (a.invisibleLocalVariableAnnotations == null) {
            a.invisibleLocalVariableAnnotations = new ArrayList(1);
         }

         a.invisibleLocalVariableAnnotations.add(a);
      }

      return a;
   }

   public void visitLineNumber(int a, Label a) {
      a.instructions.add((AbstractInsnNode)(new LineNumberNode(a, a.getLabelNode(a))));
   }

   public void visitMaxs(int a, int a) {
      a.maxStack = a;
      a.maxLocals = a;
   }

   public void visitEnd() {
   }

   protected LabelNode getLabelNode(Label a) {
      if (!(a.info instanceof LabelNode)) {
         a.info = new LabelNode();
      }

      return (LabelNode)a.info;
   }

   private LabelNode[] getLabelNodes(Label[] a) {
      LabelNode[] a = new LabelNode[a.length];

      for(int a = 0; a < a.length; ++a) {
         a[a] = a.getLabelNode(a[a]);
      }

      return a;
   }

   private Object[] getLabelNodes(Object[] a) {
      Object[] a = new Object[a.length];

      for(int a = 0; a < a.length; ++a) {
         Object a = a[a];
         if (a instanceof Label) {
            a = a.getLabelNode((Label)a);
         }

         a[a] = a;
      }

      return a;
   }

   public void check(int a) {
      if (a == 262144) {
         if (a.visibleTypeAnnotations != null && a.visibleTypeAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         if (a.invisibleTypeAnnotations != null && a.invisibleTypeAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         int a = a.tryCatchBlocks == null ? 0 : a.tryCatchBlocks.size();

         int a;
         for(a = 0; a < a; ++a) {
            TryCatchBlockNode a = (TryCatchBlockNode)a.tryCatchBlocks.get(a);
            if (a.visibleTypeAnnotations != null && a.visibleTypeAnnotations.size() > 0) {
               throw new RuntimeException();
            }

            if (a.invisibleTypeAnnotations != null && a.invisibleTypeAnnotations.size() > 0) {
               throw new RuntimeException();
            }
         }

         for(a = 0; a < a.instructions.size(); ++a) {
            AbstractInsnNode a = a.instructions.get(a);
            if (a.visibleTypeAnnotations != null && a.visibleTypeAnnotations.size() > 0) {
               throw new RuntimeException();
            }

            if (a.invisibleTypeAnnotations != null && a.invisibleTypeAnnotations.size() > 0) {
               throw new RuntimeException();
            }

            if (a instanceof MethodInsnNode) {
               boolean a = ((MethodInsnNode)a).itf;
               if (a != (a.opcode == 185)) {
                  throw new RuntimeException();
               }
            }
         }

         if (a.visibleLocalVariableAnnotations != null && a.visibleLocalVariableAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         if (a.invisibleLocalVariableAnnotations != null && a.invisibleLocalVariableAnnotations.size() > 0) {
            throw new RuntimeException();
         }
      }

   }

   public void accept(ClassVisitor a) {
      String[] a = new String[a.exceptions.size()];
      a.exceptions.toArray(a);
      MethodVisitor a = a.visitMethod(a.access, a.name, a.desc, a.signature, a);
      if (a != null) {
         a.accept(a);
      }

   }

   public void accept(MethodVisitor a) {
      int a = a.parameters == null ? 0 : a.parameters.size();

      int a;
      for(a = 0; a < a; ++a) {
         ParameterNode a = (ParameterNode)a.parameters.get(a);
         a.visitParameter(a.name, a.access);
      }

      if (a.annotationDefault != null) {
         AnnotationVisitor a = a.visitAnnotationDefault();
         AnnotationNode.accept(a, (String)null, a.annotationDefault);
         if (a != null) {
            a.visitEnd();
         }
      }

      a = a.visibleAnnotations == null ? 0 : a.visibleAnnotations.size();

      AnnotationNode a;
      for(a = 0; a < a; ++a) {
         a = (AnnotationNode)a.visibleAnnotations.get(a);
         a.accept(a.visitAnnotation(a.desc, true));
      }

      a = a.invisibleAnnotations == null ? 0 : a.invisibleAnnotations.size();

      for(a = 0; a < a; ++a) {
         a = (AnnotationNode)a.invisibleAnnotations.get(a);
         a.accept(a.visitAnnotation(a.desc, false));
      }

      a = a.visibleTypeAnnotations == null ? 0 : a.visibleTypeAnnotations.size();

      TypeAnnotationNode a;
      for(a = 0; a < a; ++a) {
         a = (TypeAnnotationNode)a.visibleTypeAnnotations.get(a);
         a.accept(a.visitTypeAnnotation(a.typeRef, a.typePath, a.desc, true));
      }

      a = a.invisibleTypeAnnotations == null ? 0 : a.invisibleTypeAnnotations.size();

      for(a = 0; a < a; ++a) {
         a = (TypeAnnotationNode)a.invisibleTypeAnnotations.get(a);
         a.accept(a.visitTypeAnnotation(a.typeRef, a.typePath, a.desc, false));
      }

      a = a.visibleParameterAnnotations == null ? 0 : a.visibleParameterAnnotations.length;

      int a;
      AnnotationNode a;
      List a;
      for(a = 0; a < a; ++a) {
         a = a.visibleParameterAnnotations[a];
         if (a != null) {
            for(a = 0; a < a.size(); ++a) {
               a = (AnnotationNode)a.get(a);
               a.accept(a.visitParameterAnnotation(a, a.desc, true));
            }
         }
      }

      a = a.invisibleParameterAnnotations == null ? 0 : a.invisibleParameterAnnotations.length;

      for(a = 0; a < a; ++a) {
         a = a.invisibleParameterAnnotations[a];
         if (a != null) {
            for(a = 0; a < a.size(); ++a) {
               a = (AnnotationNode)a.get(a);
               a.accept(a.visitParameterAnnotation(a, a.desc, false));
            }
         }
      }

      if (a.visited) {
         a.instructions.resetLabels();
      }

      a = a.attrs == null ? 0 : a.attrs.size();

      for(a = 0; a < a; ++a) {
         a.visitAttribute((Attribute)a.attrs.get(a));
      }

      if (a.instructions.size() > 0) {
         a.visitCode();
         a = a.tryCatchBlocks == null ? 0 : a.tryCatchBlocks.size();

         for(a = 0; a < a; ++a) {
            ((TryCatchBlockNode)a.tryCatchBlocks.get(a)).updateIndex(a);
            ((TryCatchBlockNode)a.tryCatchBlocks.get(a)).accept(a);
         }

         a.instructions.accept(a);
         a = a.localVariables == null ? 0 : a.localVariables.size();

         for(a = 0; a < a; ++a) {
            ((LocalVariableNode)a.localVariables.get(a)).accept(a);
         }

         a = a.visibleLocalVariableAnnotations == null ? 0 : a.visibleLocalVariableAnnotations.size();

         for(a = 0; a < a; ++a) {
            ((LocalVariableAnnotationNode)a.visibleLocalVariableAnnotations.get(a)).accept(a, true);
         }

         a = a.invisibleLocalVariableAnnotations == null ? 0 : a.invisibleLocalVariableAnnotations.size();

         for(a = 0; a < a; ++a) {
            ((LocalVariableAnnotationNode)a.invisibleLocalVariableAnnotations.get(a)).accept(a, false);
         }

         a.visitMaxs(a.maxStack, a.maxLocals);
         a.visited = true;
      }

      a.visitEnd();
   }
}

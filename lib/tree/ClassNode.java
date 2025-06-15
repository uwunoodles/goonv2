package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class ClassNode extends ClassVisitor {
   public int version;
   public int access;
   public String name;
   public String signature;
   public String superName;
   public List<String> interfaces;
   public String sourceFile;
   public String sourceDebug;
   public String outerClass;
   public String outerMethod;
   public String outerMethodDesc;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;
   public List<InnerClassNode> innerClasses;
   public List<FieldNode> fields;
   public List<MethodNode> methods;

   public ClassNode() {
      this(327680);
      if (a.getClass() != ClassNode.class) {
         throw new IllegalStateException();
      }
   }

   public ClassNode(int a) {
      super(a);
      a.interfaces = new ArrayList();
      a.innerClasses = new ArrayList();
      a.fields = new ArrayList();
      a.methods = new ArrayList();
   }

   public void visit(int a, int a, String a, String a, String a, String[] a) {
      a.version = a;
      a.access = a;
      a.name = a;
      a.signature = a;
      a.superName = a;
      if (a != null) {
         a.interfaces.addAll(Arrays.asList(a));
      }

   }

   public void visitSource(String a, String a) {
      a.sourceFile = a;
      a.sourceDebug = a;
   }

   public void visitOuterClass(String a, String a, String a) {
      a.outerClass = a;
      a.outerMethod = a;
      a.outerMethodDesc = a;
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

   public void visitAttribute(Attribute a) {
      if (a.attrs == null) {
         a.attrs = new ArrayList(1);
      }

      a.attrs.add(a);
   }

   public void visitInnerClass(String a, String a, String a, int a) {
      InnerClassNode a = new InnerClassNode(a, a, a, a);
      a.innerClasses.add(a);
   }

   public FieldVisitor visitField(int a, String a, String a, String a, Object a) {
      FieldNode a = new FieldNode(a, a, a, a, a);
      a.fields.add(a);
      return a;
   }

   public MethodVisitor visitMethod(int a, String a, String a, String a, String[] a) {
      MethodNode a = new MethodNode(a, a, a, a, a);
      a.methods.add(a);
      return a;
   }

   public void visitEnd() {
   }

   public void check(int a) {
      if (a == 262144) {
         if (a.visibleTypeAnnotations != null && a.visibleTypeAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         if (a.invisibleTypeAnnotations != null && a.invisibleTypeAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         Iterator var2 = a.fields.iterator();

         while(var2.hasNext()) {
            FieldNode a = (FieldNode)var2.next();
            a.check(a);
         }

         var2 = a.methods.iterator();

         while(var2.hasNext()) {
            MethodNode a = (MethodNode)var2.next();
            a.check(a);
         }
      }

   }

   public void accept(ClassVisitor a) {
      String[] a = new String[a.interfaces.size()];
      a.interfaces.toArray(a);
      a.visit(a.version, a.access, a.name, a.signature, a.superName, a);
      if (a.sourceFile != null || a.sourceDebug != null) {
         a.visitSource(a.sourceFile, a.sourceDebug);
      }

      if (a.outerClass != null) {
         a.visitOuterClass(a.outerClass, a.outerMethod, a.outerMethodDesc);
      }

      int a = a.visibleAnnotations == null ? 0 : a.visibleAnnotations.size();

      int a;
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

      a = a.attrs == null ? 0 : a.attrs.size();

      for(a = 0; a < a; ++a) {
         a.visitAttribute((Attribute)a.attrs.get(a));
      }

      for(a = 0; a < a.innerClasses.size(); ++a) {
         ((InnerClassNode)a.innerClasses.get(a)).accept(a);
      }

      for(a = 0; a < a.fields.size(); ++a) {
         ((FieldNode)a.fields.get(a)).accept(a);
      }

      for(a = 0; a < a.methods.size(); ++a) {
         ((MethodNode)a.methods.get(a)).accept(a);
      }

      a.visitEnd();
   }
}

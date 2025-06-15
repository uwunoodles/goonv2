package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public class FieldNode extends FieldVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public Object value;
   public List<AnnotationNode> visibleAnnotations;
   public List<AnnotationNode> invisibleAnnotations;
   public List<TypeAnnotationNode> visibleTypeAnnotations;
   public List<TypeAnnotationNode> invisibleTypeAnnotations;
   public List<Attribute> attrs;

   public FieldNode(int a, String a, String a, String a, Object a) {
      this(327680, a, a, a, a, a);
      if (a.getClass() != FieldNode.class) {
         throw new IllegalStateException();
      }
   }

   public FieldNode(int a, int a, String a, String a, String a, Object a) {
      super(a);
      a.access = a;
      a.name = a;
      a.desc = a;
      a.signature = a;
      a.value = a;
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
      }

   }

   public void accept(ClassVisitor a) {
      FieldVisitor a = a.visitField(a.access, a.name, a.desc, a.signature, a.value);
      if (a != null) {
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

         a.visitEnd();
      }
   }
}

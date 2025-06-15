package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.TypePath;

public class CheckFieldAdapter extends FieldVisitor {
   private boolean end;

   public CheckFieldAdapter(FieldVisitor a) {
      this(327680, a);
      if (a.getClass() != CheckFieldAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckFieldAdapter(int a, FieldVisitor a) {
      super(a, a);
   }

   public AnnotationVisitor visitAnnotation(String a, boolean a) {
      a.checkEnd();
      CheckMethodAdapter.checkDesc(a, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(a, a));
   }

   public AnnotationVisitor visitTypeAnnotation(int a, TypePath a, String a, boolean a) {
      a.checkEnd();
      int a = a >>> 24;
      if (a != 19) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(a));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(a, a);
         CheckMethodAdapter.checkDesc(a, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(a, a, a, a));
      }
   }

   public void visitAttribute(Attribute a) {
      a.checkEnd();
      if (a == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(a);
      }
   }

   public void visitEnd() {
      a.checkEnd();
      a.end = true;
      super.visitEnd();
   }

   private void checkEnd() {
      if (a.end) {
         throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
      }
   }
}

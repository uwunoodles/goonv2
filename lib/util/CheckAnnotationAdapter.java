package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Type;

public class CheckAnnotationAdapter extends AnnotationVisitor {
   private final boolean named;
   private boolean end;

   public CheckAnnotationAdapter(AnnotationVisitor a) {
      this(a, true);
   }

   CheckAnnotationAdapter(AnnotationVisitor a, boolean a) {
      super(327680, a);
      a.named = a;
   }

   public void visit(String a, Object a) {
      a.checkEnd();
      a.checkName(a);
      if (!(a instanceof Byte) && !(a instanceof Boolean) && !(a instanceof Character) && !(a instanceof Short) && !(a instanceof Integer) && !(a instanceof Long) && !(a instanceof Float) && !(a instanceof Double) && !(a instanceof String) && !(a instanceof Type) && !(a instanceof byte[]) && !(a instanceof boolean[]) && !(a instanceof char[]) && !(a instanceof short[]) && !(a instanceof int[]) && !(a instanceof long[]) && !(a instanceof float[]) && !(a instanceof double[])) {
         throw new IllegalArgumentException("Invalid annotation value");
      } else {
         if (a instanceof Type) {
            int a = ((Type)a).getSort();
            if (a == 11) {
               throw new IllegalArgumentException("Invalid annotation value");
            }
         }

         if (a.av != null) {
            a.av.visit(a, a);
         }

      }
   }

   public void visitEnum(String a, String a, String a) {
      a.checkEnd();
      a.checkName(a);
      CheckMethodAdapter.checkDesc(a, false);
      if (a == null) {
         throw new IllegalArgumentException("Invalid enum value");
      } else {
         if (a.av != null) {
            a.av.visitEnum(a, a, a);
         }

      }
   }

   public AnnotationVisitor visitAnnotation(String a, String a) {
      a.checkEnd();
      a.checkName(a);
      CheckMethodAdapter.checkDesc(a, false);
      return new CheckAnnotationAdapter(a.av == null ? null : a.av.visitAnnotation(a, a));
   }

   public AnnotationVisitor visitArray(String a) {
      a.checkEnd();
      a.checkName(a);
      return new CheckAnnotationAdapter(a.av == null ? null : a.av.visitArray(a), false);
   }

   public void visitEnd() {
      a.checkEnd();
      a.end = true;
      if (a.av != null) {
         a.av.visitEnd();
      }

   }

   private void checkEnd() {
      if (a.end) {
         throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
      }
   }

   private void checkName(String a) {
      if (a.named && a == null) {
         throw new IllegalArgumentException("Annotation value name must not be null");
      }
   }
}

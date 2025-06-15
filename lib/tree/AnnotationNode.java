package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;

public class AnnotationNode extends AnnotationVisitor {
   public String desc;
   public List<Object> values;

   public AnnotationNode(String a) {
      this(327680, a);
      if (a.getClass() != AnnotationNode.class) {
         throw new IllegalStateException();
      }
   }

   public AnnotationNode(int a, String a) {
      super(a);
      a.desc = a;
   }

   AnnotationNode(List<Object> a) {
      super(327680);
      a.values = a;
   }

   public void visit(String a, Object a) {
      if (a.values == null) {
         a.values = new ArrayList(a.desc != null ? 2 : 1);
      }

      if (a.desc != null) {
         a.values.add(a);
      }

      ArrayList a;
      int var6;
      int var7;
      if (a instanceof byte[]) {
         byte[] a = (byte[])((byte[])a);
         a = new ArrayList(a.length);
         byte[] var5 = a;
         var6 = a.length;

         for(var7 = 0; var7 < var6; ++var7) {
            byte a = var5[var7];
            a.add(a);
         }

         a.values.add(a);
      } else if (a instanceof boolean[]) {
         boolean[] a = (boolean[])((boolean[])a);
         a = new ArrayList(a.length);
         boolean[] var17 = a;
         var6 = a.length;

         for(var7 = 0; var7 < var6; ++var7) {
            boolean a = var17[var7];
            a.add(a);
         }

         a.values.add(a);
      } else if (a instanceof short[]) {
         short[] a = (short[])((short[])a);
         a = new ArrayList(a.length);
         short[] var19 = a;
         var6 = a.length;

         for(var7 = 0; var7 < var6; ++var7) {
            short a = var19[var7];
            a.add(a);
         }

         a.values.add(a);
      } else if (a instanceof char[]) {
         char[] a = (char[])((char[])a);
         a = new ArrayList(a.length);
         char[] var20 = a;
         var6 = a.length;

         for(var7 = 0; var7 < var6; ++var7) {
            char a = var20[var7];
            a.add(a);
         }

         a.values.add(a);
      } else if (a instanceof int[]) {
         int[] a = (int[])((int[])a);
         a = new ArrayList(a.length);
         int[] var21 = a;
         var6 = a.length;

         for(var7 = 0; var7 < var6; ++var7) {
            int a = var21[var7];
            a.add(a);
         }

         a.values.add(a);
      } else if (a instanceof long[]) {
         long[] a = (long[])((long[])a);
         a = new ArrayList(a.length);
         long[] var22 = a;
         var6 = a.length;

         for(var7 = 0; var7 < var6; ++var7) {
            long a = var22[var7];
            a.add(a);
         }

         a.values.add(a);
      } else if (a instanceof float[]) {
         float[] a = (float[])((float[])a);
         a = new ArrayList(a.length);
         float[] var23 = a;
         var6 = a.length;

         for(var7 = 0; var7 < var6; ++var7) {
            float a = var23[var7];
            a.add(a);
         }

         a.values.add(a);
      } else if (a instanceof double[]) {
         double[] a = (double[])((double[])a);
         a = new ArrayList(a.length);
         double[] var24 = a;
         var6 = a.length;

         for(var7 = 0; var7 < var6; ++var7) {
            double a = var24[var7];
            a.add(a);
         }

         a.values.add(a);
      } else {
         a.values.add(a);
      }

   }

   public void visitEnum(String a, String a, String a) {
      if (a.values == null) {
         a.values = new ArrayList(a.desc != null ? 2 : 1);
      }

      if (a.desc != null) {
         a.values.add(a);
      }

      a.values.add(new String[]{a, a});
   }

   public AnnotationVisitor visitAnnotation(String a, String a) {
      if (a.values == null) {
         a.values = new ArrayList(a.desc != null ? 2 : 1);
      }

      if (a.desc != null) {
         a.values.add(a);
      }

      AnnotationNode a = new AnnotationNode(a);
      a.values.add(a);
      return a;
   }

   public AnnotationVisitor visitArray(String a) {
      if (a.values == null) {
         a.values = new ArrayList(a.desc != null ? 2 : 1);
      }

      if (a.desc != null) {
         a.values.add(a);
      }

      List<Object> a = new ArrayList();
      a.values.add(a);
      return new AnnotationNode(a);
   }

   public void visitEnd() {
   }

   public void check(int a1) {
   }

   public void accept(AnnotationVisitor a) {
      if (a != null) {
         if (a.values != null) {
            for(int a = 0; a < a.values.size(); a += 2) {
               String a = (String)a.values.get(a);
               Object a = a.values.get(a + 1);
               accept(a, a, a);
            }
         }

         a.visitEnd();
      }

   }

   static void accept(AnnotationVisitor a, String a, Object a) {
      if (a != null) {
         if (a instanceof String[]) {
            String[] a = (String[])((String[])a);
            a.visitEnum(a, a[0], a[1]);
         } else if (a instanceof AnnotationNode) {
            AnnotationNode a = (AnnotationNode)a;
            a.accept(a.visitAnnotation(a, a.desc));
         } else if (a instanceof List) {
            AnnotationVisitor a = a.visitArray(a);
            if (a != null) {
               List<?> a = (List)a;

               for(int a = 0; a < a.size(); ++a) {
                  accept(a, (String)null, a.get(a));
               }

               a.visitEnd();
            }
         } else {
            a.visit(a, a);
         }
      }

   }
}

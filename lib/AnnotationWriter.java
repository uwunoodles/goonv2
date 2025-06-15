package org.spongepowered.asm.lib;

final class AnnotationWriter extends AnnotationVisitor {
   private final ClassWriter cw;
   private int size;
   private final boolean named;
   private final ByteVector bv;
   private final ByteVector parent;
   private final int offset;
   AnnotationWriter next;
   AnnotationWriter prev;

   AnnotationWriter(ClassWriter a, boolean a, ByteVector a, ByteVector a, int a) {
      super(327680);
      a.cw = a;
      a.named = a;
      a.bv = a;
      a.parent = a;
      a.offset = a;
   }

   public void visit(String a, Object a) {
      ++a.size;
      if (a.named) {
         a.bv.putShort(a.cw.newUTF8(a));
      }

      if (a instanceof String) {
         a.bv.put12(115, a.cw.newUTF8((String)a));
      } else if (a instanceof Byte) {
         a.bv.put12(66, a.cw.newInteger((Byte)a).index);
      } else if (a instanceof Boolean) {
         int a = (Boolean)a ? 1 : 0;
         a.bv.put12(90, a.cw.newInteger(a).index);
      } else if (a instanceof Character) {
         a.bv.put12(67, a.cw.newInteger((Character)a).index);
      } else if (a instanceof Short) {
         a.bv.put12(83, a.cw.newInteger((Short)a).index);
      } else if (a instanceof Type) {
         a.bv.put12(99, a.cw.newUTF8(((Type)a).getDescriptor()));
      } else {
         int a;
         if (a instanceof byte[]) {
            byte[] a = (byte[])((byte[])a);
            a.bv.put12(91, a.length);

            for(a = 0; a < a.length; ++a) {
               a.bv.put12(66, a.cw.newInteger(a[a]).index);
            }
         } else if (a instanceof boolean[]) {
            boolean[] a = (boolean[])((boolean[])a);
            a.bv.put12(91, a.length);

            for(a = 0; a < a.length; ++a) {
               a.bv.put12(90, a.cw.newInteger(a[a] ? 1 : 0).index);
            }
         } else if (a instanceof short[]) {
            short[] a = (short[])((short[])a);
            a.bv.put12(91, a.length);

            for(a = 0; a < a.length; ++a) {
               a.bv.put12(83, a.cw.newInteger(a[a]).index);
            }
         } else if (a instanceof char[]) {
            char[] a = (char[])((char[])a);
            a.bv.put12(91, a.length);

            for(a = 0; a < a.length; ++a) {
               a.bv.put12(67, a.cw.newInteger(a[a]).index);
            }
         } else if (a instanceof int[]) {
            int[] a = (int[])((int[])a);
            a.bv.put12(91, a.length);

            for(a = 0; a < a.length; ++a) {
               a.bv.put12(73, a.cw.newInteger(a[a]).index);
            }
         } else if (a instanceof long[]) {
            long[] a = (long[])((long[])a);
            a.bv.put12(91, a.length);

            for(a = 0; a < a.length; ++a) {
               a.bv.put12(74, a.cw.newLong(a[a]).index);
            }
         } else if (a instanceof float[]) {
            float[] a = (float[])((float[])a);
            a.bv.put12(91, a.length);

            for(a = 0; a < a.length; ++a) {
               a.bv.put12(70, a.cw.newFloat(a[a]).index);
            }
         } else if (a instanceof double[]) {
            double[] a = (double[])((double[])a);
            a.bv.put12(91, a.length);

            for(a = 0; a < a.length; ++a) {
               a.bv.put12(68, a.cw.newDouble(a[a]).index);
            }
         } else {
            Item a = a.cw.newConstItem(a);
            a.bv.put12(".s.IFJDCS".charAt(a.type), a.index);
         }
      }

   }

   public void visitEnum(String a, String a, String a) {
      ++a.size;
      if (a.named) {
         a.bv.putShort(a.cw.newUTF8(a));
      }

      a.bv.put12(101, a.cw.newUTF8(a)).putShort(a.cw.newUTF8(a));
   }

   public AnnotationVisitor visitAnnotation(String a, String a) {
      ++a.size;
      if (a.named) {
         a.bv.putShort(a.cw.newUTF8(a));
      }

      a.bv.put12(64, a.cw.newUTF8(a)).putShort(0);
      return new AnnotationWriter(a.cw, true, a.bv, a.bv, a.bv.length - 2);
   }

   public AnnotationVisitor visitArray(String a) {
      ++a.size;
      if (a.named) {
         a.bv.putShort(a.cw.newUTF8(a));
      }

      a.bv.put12(91, 0);
      return new AnnotationWriter(a.cw, false, a.bv, a.bv, a.bv.length - 2);
   }

   public void visitEnd() {
      if (a.parent != null) {
         byte[] a = a.parent.data;
         a[a.offset] = (byte)(a.size >>> 8);
         a[a.offset + 1] = (byte)a.size;
      }

   }

   int getSize() {
      int a = 0;

      for(AnnotationWriter a = a; a != null; a = a.next) {
         a += a.bv.length;
      }

      return a;
   }

   void put(ByteVector a) {
      int a = 0;
      int a = 2;
      AnnotationWriter a = a;

      AnnotationWriter a;
      for(a = null; a != null; a = a.next) {
         ++a;
         a += a.bv.length;
         a.visitEnd();
         a.prev = a;
         a = a;
      }

      a.putInt(a);
      a.putShort(a);

      for(a = a; a != null; a = a.prev) {
         a.putByteArray(a.bv.data, 0, a.bv.length);
      }

   }

   static void put(AnnotationWriter[] a, int a, ByteVector a) {
      int a = 1 + 2 * (a.length - a);

      int a;
      for(a = a; a < a.length; ++a) {
         a += a[a] == null ? 0 : a[a].getSize();
      }

      a.putInt(a).putByte(a.length - a);

      for(a = a; a < a.length; ++a) {
         AnnotationWriter a = a[a];
         AnnotationWriter a = null;

         int a;
         for(a = 0; a != null; a = a.next) {
            ++a;
            a.visitEnd();
            a.prev = a;
            a = a;
         }

         a.putShort(a);

         for(a = a; a != null; a = a.prev) {
            a.putByteArray(a.bv.data, 0, a.bv.length);
         }
      }

   }

   static void putTarget(int a, TypePath a, ByteVector a) {
      switch(a >>> 24) {
      case 0:
      case 1:
      case 22:
         a.putShort(a >>> 16);
         break;
      case 19:
      case 20:
      case 21:
         a.putByte(a >>> 24);
         break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         a.putInt(a);
         break;
      default:
         a.put12(a >>> 24, (a & 16776960) >> 8);
      }

      if (a == null) {
         a.putByte(0);
      } else {
         int a = a.b[a.offset] * 2 + 1;
         a.putByteArray(a.b, a.offset, a);
      }

   }
}

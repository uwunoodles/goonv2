package org.spongepowered.asm.lib;

import java.io.IOException;
import java.io.InputStream;

public class ClassReader {
   static final boolean SIGNATURES = true;
   static final boolean ANNOTATIONS = true;
   static final boolean FRAMES = true;
   static final boolean WRITER = true;
   static final boolean RESIZE = true;
   public static final int SKIP_CODE = 1;
   public static final int SKIP_DEBUG = 2;
   public static final int SKIP_FRAMES = 4;
   public static final int EXPAND_FRAMES = 8;
   static final int EXPAND_ASM_INSNS = 256;
   public final byte[] b;
   private final int[] items;
   private final String[] strings;
   private final int maxStringLength;
   public final int header;

   public ClassReader(byte[] a) {
      this(a, 0, a.length);
   }

   public ClassReader(byte[] a, int a, int a3) {
      a.b = a;
      if (a.readShort(a + 6) > 52) {
         throw new IllegalArgumentException();
      } else {
         a.items = new int[a.readUnsignedShort(a + 8)];
         int a = a.items.length;
         a.strings = new String[a];
         int a = 0;
         int a = a + 10;

         for(int a = 1; a < a; ++a) {
            a.items[a] = a + 1;
            int a;
            switch(a[a]) {
            case 1:
               a = 3 + a.readUnsignedShort(a + 1);
               if (a > a) {
                  a = a;
               }
               break;
            case 2:
            case 7:
            case 8:
            case 13:
            case 14:
            case 16:
            case 17:
            default:
               a = 3;
               break;
            case 3:
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
            case 18:
               a = 5;
               break;
            case 5:
            case 6:
               a = 9;
               ++a;
               break;
            case 15:
               a = 4;
            }

            a += a;
         }

         a.maxStringLength = a;
         a.header = a;
      }
   }

   public int getAccess() {
      return a.readUnsignedShort(a.header);
   }

   public String getClassName() {
      return a.readClass(a.header + 2, new char[a.maxStringLength]);
   }

   public String getSuperName() {
      return a.readClass(a.header + 4, new char[a.maxStringLength]);
   }

   public String[] getInterfaces() {
      int a = a.header + 6;
      int a = a.readUnsignedShort(a);
      String[] a = new String[a];
      if (a > 0) {
         char[] a = new char[a.maxStringLength];

         for(int a = 0; a < a; ++a) {
            a += 2;
            a[a] = a.readClass(a, a);
         }
      }

      return a;
   }

   void copyPool(ClassWriter a) {
      char[] a = new char[a.maxStringLength];
      int a = a.items.length;
      Item[] a = new Item[a];

      int a;
      for(a = 1; a < a; ++a) {
         int a = a.items[a];
         int a = a.b[a - 1];
         Item a = new Item(a);
         int a;
         int a;
         switch(a) {
         case 1:
            String a = a.strings[a];
            if (a == null) {
               a = a.items[a];
               a = a.strings[a] = a.readUTF(a + 2, a.readUnsignedShort(a), a);
            }

            a.set(a, a, (String)null, (String)null);
            break;
         case 2:
         case 7:
         case 8:
         case 13:
         case 14:
         case 16:
         case 17:
         default:
            a.set(a, a.readUTF8(a, a), (String)null, (String)null);
            break;
         case 3:
            a.set(a.readInt(a));
            break;
         case 4:
            a.set(Float.intBitsToFloat(a.readInt(a)));
            break;
         case 5:
            a.set(a.readLong(a));
            ++a;
            break;
         case 6:
            a.set(Double.longBitsToDouble(a.readLong(a)));
            ++a;
            break;
         case 9:
         case 10:
         case 11:
            a = a.items[a.readUnsignedShort(a + 2)];
            a.set(a, a.readClass(a, a), a.readUTF8(a, a), a.readUTF8(a + 2, a));
            break;
         case 12:
            a.set(a, a.readUTF8(a, a), a.readUTF8(a + 2, a), (String)null);
            break;
         case 15:
            a = a.items[a.readUnsignedShort(a + 1)];
            a = a.items[a.readUnsignedShort(a + 2)];
            a.set(20 + a.readByte(a), a.readClass(a, a), a.readUTF8(a, a), a.readUTF8(a + 2, a));
            break;
         case 18:
            if (a.bootstrapMethods == null) {
               a.copyBootstrapMethods(a, a, a);
            }

            a = a.items[a.readUnsignedShort(a + 2)];
            a.set(a.readUTF8(a, a), a.readUTF8(a + 2, a), a.readUnsignedShort(a));
         }

         a = a.hashCode % a.length;
         a.next = a[a];
         a[a] = a;
      }

      a = a.items[1] - 1;
      a.pool.putByteArray(a.b, a, a.header - a);
      a.items = a;
      a.threshold = (int)(0.75D * (double)a);
      a.index = a;
   }

   private void copyBootstrapMethods(ClassWriter a, Item[] a, char[] a) {
      int a = a.getAttributes();
      boolean a = false;

      int a;
      for(a = a.readUnsignedShort(a); a > 0; --a) {
         String a = a.readUTF8(a + 2, a);
         if ("BootstrapMethods".equals(a)) {
            a = true;
            break;
         }

         a += 6 + a.readInt(a + 4);
      }

      if (a) {
         a = a.readUnsignedShort(a + 8);
         int a = 0;

         for(int a = a + 10; a < a; ++a) {
            int a = a - a - 10;
            int a = a.readConst(a.readUnsignedShort(a), a).hashCode();

            for(int a = a.readUnsignedShort(a + 2); a > 0; --a) {
               a ^= a.readConst(a.readUnsignedShort(a + 4), a).hashCode();
               a += 2;
            }

            a += 4;
            Item a = new Item(a);
            a.set(a, a & Integer.MAX_VALUE);
            int a = a.hashCode % a.length;
            a.next = a[a];
            a[a] = a;
         }

         a = a.readInt(a + 4);
         ByteVector a = new ByteVector(a + 62);
         a.putByteArray(a.b, a + 10, a - 2);
         a.bootstrapMethodsCount = a;
         a.bootstrapMethods = a;
      }
   }

   public ClassReader(InputStream a) throws IOException {
      this(readClass(a, false));
   }

   public ClassReader(String a) throws IOException {
      this(readClass(ClassLoader.getSystemResourceAsStream(a.replace('.', '/') + ".class"), true));
   }

   private static byte[] readClass(InputStream a, boolean a) throws IOException {
      if (a == null) {
         throw new IOException("Class not found");
      } else {
         try {
            byte[] a = new byte[a.available()];
            int a = 0;

            while(true) {
               int a = a.read(a, a, a.length - a);
               if (a == -1) {
                  byte[] a;
                  if (a < a.length) {
                     a = new byte[a];
                     System.arraycopy(a, 0, a, 0, a);
                     a = a;
                  }

                  a = a;
                  return a;
               }

               a += a;
               if (a == a.length) {
                  int a = a.read();
                  byte[] a;
                  if (a < 0) {
                     a = a;
                     return a;
                  }

                  a = new byte[a.length + 1000];
                  System.arraycopy(a, 0, a, 0, a);
                  a[a++] = (byte)a;
                  a = a;
               }
            }
         } finally {
            if (a) {
               a.close();
            }

         }
      }
   }

   public void accept(ClassVisitor a, int a) {
      a.accept(a, new Attribute[0], a);
   }

   public void accept(ClassVisitor a, Attribute[] a, int a) {
      int a = a.header;
      char[] a = new char[a.maxStringLength];
      Context a = new Context();
      a.attrs = a;
      a.flags = a;
      a.buffer = a;
      int a = a.readUnsignedShort(a);
      String a = a.readClass(a + 2, a);
      String a = a.readClass(a + 4, a);
      String[] a = new String[a.readUnsignedShort(a + 6)];
      a += 8;

      for(int a = 0; a < a.length; ++a) {
         a[a] = a.readClass(a, a);
         a += 2;
      }

      String a = null;
      String a = null;
      String a = null;
      String a = null;
      String a = null;
      String a = null;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = 0;
      Attribute a = null;
      a = a.getAttributes();

      int a;
      for(a = a.readUnsignedShort(a); a > 0; --a) {
         String a = a.readUTF8(a + 2, a);
         if ("SourceFile".equals(a)) {
            a = a.readUTF8(a + 8, a);
         } else if ("InnerClasses".equals(a)) {
            a = a + 8;
         } else {
            int a;
            if ("EnclosingMethod".equals(a)) {
               a = a.readClass(a + 8, a);
               a = a.readUnsignedShort(a + 10);
               if (a != 0) {
                  a = a.readUTF8(a.items[a], a);
                  a = a.readUTF8(a.items[a] + 2, a);
               }
            } else if ("Signature".equals(a)) {
               a = a.readUTF8(a + 8, a);
            } else if ("RuntimeVisibleAnnotations".equals(a)) {
               a = a + 8;
            } else if ("RuntimeVisibleTypeAnnotations".equals(a)) {
               a = a + 8;
            } else if ("Deprecated".equals(a)) {
               a |= 131072;
            } else if ("Synthetic".equals(a)) {
               a |= 266240;
            } else if ("SourceDebugExtension".equals(a)) {
               a = a.readInt(a + 4);
               a = a.readUTF(a + 8, a, new char[a]);
            } else if ("RuntimeInvisibleAnnotations".equals(a)) {
               a = a + 8;
            } else if ("RuntimeInvisibleTypeAnnotations".equals(a)) {
               a = a + 8;
            } else if (!"BootstrapMethods".equals(a)) {
               Attribute a = a.readAttribute(a, a, a + 8, a.readInt(a + 4), a, -1, (Label[])null);
               if (a != null) {
                  a.next = a;
                  a = a;
               }
            } else {
               int[] a = new int[a.readUnsignedShort(a + 8)];
               int a = 0;

               for(int a = a + 10; a < a.length; ++a) {
                  a[a] = a;
                  a += 2 + a.readUnsignedShort(a + 2) << 1;
               }

               a.bootstrapMethods = a;
            }
         }

         a += 6 + a.readInt(a + 4);
      }

      a.visit(a.readInt(a.items[1] - 7), a, a, a, a, a);
      if ((a & 2) == 0 && (a != null || a != null)) {
         a.visitSource(a, a);
      }

      if (a != null) {
         a.visitOuterClass(a, a, a);
      }

      int a;
      if (a != 0) {
         a = a.readUnsignedShort(a);

         for(a = a + 2; a > 0; --a) {
            a = a.readAnnotationValues(a + 2, a, true, a.visitAnnotation(a.readUTF8(a, a), true));
         }
      }

      if (a != 0) {
         a = a.readUnsignedShort(a);

         for(a = a + 2; a > 0; --a) {
            a = a.readAnnotationValues(a + 2, a, true, a.visitAnnotation(a.readUTF8(a, a), false));
         }
      }

      if (a != 0) {
         a = a.readUnsignedShort(a);

         for(a = a + 2; a > 0; --a) {
            a = a.readAnnotationTarget(a, a);
            a = a.readAnnotationValues(a + 2, a, true, a.visitTypeAnnotation(a.typeRef, a.typePath, a.readUTF8(a, a), true));
         }
      }

      if (a != 0) {
         a = a.readUnsignedShort(a);

         for(a = a + 2; a > 0; --a) {
            a = a.readAnnotationTarget(a, a);
            a = a.readAnnotationValues(a + 2, a, true, a.visitTypeAnnotation(a.typeRef, a.typePath, a.readUTF8(a, a), false));
         }
      }

      while(a != null) {
         Attribute a = a.next;
         a.next = null;
         a.visitAttribute(a);
         a = a;
      }

      if (a != 0) {
         a = a + 2;

         for(a = a.readUnsignedShort(a); a > 0; --a) {
            a.visitInnerClass(a.readClass(a, a), a.readClass(a + 2, a), a.readUTF8(a + 4, a), a.readUnsignedShort(a + 6));
            a += 8;
         }
      }

      a = a.header + 10 + 2 * a.length;

      for(a = a.readUnsignedShort(a - 2); a > 0; --a) {
         a = a.readField(a, a, a);
      }

      a += 2;

      for(a = a.readUnsignedShort(a - 2); a > 0; --a) {
         a = a.readMethod(a, a, a);
      }

      a.visitEnd();
   }

   private int readField(ClassVisitor a, Context a, int a) {
      char[] a = a.buffer;
      int a = a.readUnsignedShort(a);
      String a = a.readUTF8(a + 2, a);
      String a = a.readUTF8(a + 4, a);
      a += 6;
      String a = null;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = 0;
      Object a = null;
      Attribute a = null;

      int a;
      for(int a = a.readUnsignedShort(a); a > 0; --a) {
         String a = a.readUTF8(a + 2, a);
         if ("ConstantValue".equals(a)) {
            a = a.readUnsignedShort(a + 8);
            a = a == 0 ? null : a.readConst(a, a);
         } else if ("Signature".equals(a)) {
            a = a.readUTF8(a + 8, a);
         } else if ("Deprecated".equals(a)) {
            a |= 131072;
         } else if ("Synthetic".equals(a)) {
            a |= 266240;
         } else if ("RuntimeVisibleAnnotations".equals(a)) {
            a = a + 8;
         } else if ("RuntimeVisibleTypeAnnotations".equals(a)) {
            a = a + 8;
         } else if ("RuntimeInvisibleAnnotations".equals(a)) {
            a = a + 8;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(a)) {
            a = a + 8;
         } else {
            Attribute a = a.readAttribute(a.attrs, a, a + 8, a.readInt(a + 4), a, -1, (Label[])null);
            if (a != null) {
               a.next = a;
               a = a;
            }
         }

         a += 6 + a.readInt(a + 4);
      }

      a += 2;
      FieldVisitor a = a.visitField(a, a, a, a, a);
      if (a == null) {
         return a;
      } else {
         int a;
         if (a != 0) {
            a = a.readUnsignedShort(a);

            for(a = a + 2; a > 0; --a) {
               a = a.readAnnotationValues(a + 2, a, true, a.visitAnnotation(a.readUTF8(a, a), true));
            }
         }

         if (a != 0) {
            a = a.readUnsignedShort(a);

            for(a = a + 2; a > 0; --a) {
               a = a.readAnnotationValues(a + 2, a, true, a.visitAnnotation(a.readUTF8(a, a), false));
            }
         }

         if (a != 0) {
            a = a.readUnsignedShort(a);

            for(a = a + 2; a > 0; --a) {
               a = a.readAnnotationTarget(a, a);
               a = a.readAnnotationValues(a + 2, a, true, a.visitTypeAnnotation(a.typeRef, a.typePath, a.readUTF8(a, a), true));
            }
         }

         if (a != 0) {
            a = a.readUnsignedShort(a);

            for(a = a + 2; a > 0; --a) {
               a = a.readAnnotationTarget(a, a);
               a = a.readAnnotationValues(a + 2, a, true, a.visitTypeAnnotation(a.typeRef, a.typePath, a.readUTF8(a, a), false));
            }
         }

         while(a != null) {
            Attribute a = a.next;
            a.next = null;
            a.visitAttribute(a);
            a = a;
         }

         a.visitEnd();
         return a;
      }
   }

   private int readMethod(ClassVisitor a, Context a, int a) {
      char[] a = a.buffer;
      a.access = a.readUnsignedShort(a);
      a.name = a.readUTF8(a + 2, a);
      a.desc = a.readUTF8(a + 4, a);
      a += 6;
      int a = 0;
      int a = 0;
      String[] a = null;
      String a = null;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = a;
      Attribute a = null;

      int a;
      for(int a = a.readUnsignedShort(a); a > 0; --a) {
         String a = a.readUTF8(a + 2, a);
         if ("Code".equals(a)) {
            if ((a.flags & 1) == 0) {
               a = a + 8;
            }
         } else if ("Exceptions".equals(a)) {
            a = new String[a.readUnsignedShort(a + 8)];
            a = a + 10;

            for(a = 0; a < a.length; ++a) {
               a[a] = a.readClass(a, a);
               a += 2;
            }
         } else if ("Signature".equals(a)) {
            a = a.readUTF8(a + 8, a);
         } else if ("Deprecated".equals(a)) {
            a.access |= 131072;
         } else if ("RuntimeVisibleAnnotations".equals(a)) {
            a = a + 8;
         } else if ("RuntimeVisibleTypeAnnotations".equals(a)) {
            a = a + 8;
         } else if ("AnnotationDefault".equals(a)) {
            a = a + 8;
         } else if ("Synthetic".equals(a)) {
            a.access |= 266240;
         } else if ("RuntimeInvisibleAnnotations".equals(a)) {
            a = a + 8;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(a)) {
            a = a + 8;
         } else if ("RuntimeVisibleParameterAnnotations".equals(a)) {
            a = a + 8;
         } else if ("RuntimeInvisibleParameterAnnotations".equals(a)) {
            a = a + 8;
         } else if ("MethodParameters".equals(a)) {
            a = a + 8;
         } else {
            Attribute a = a.readAttribute(a.attrs, a, a + 8, a.readInt(a + 4), a, -1, (Label[])null);
            if (a != null) {
               a.next = a;
               a = a;
            }
         }

         a += 6 + a.readInt(a + 4);
      }

      a += 2;
      MethodVisitor a = a.visitMethod(a.access, a.name, a.desc, a, a);
      if (a == null) {
         return a;
      } else {
         if (a instanceof MethodWriter) {
            MethodWriter a = (MethodWriter)a;
            if (a.cw.cr == a && a == a.signature) {
               boolean a = false;
               if (a == null) {
                  a = a.exceptionCount == 0;
               } else if (a.length == a.exceptionCount) {
                  a = true;

                  for(int a = a.length - 1; a >= 0; --a) {
                     a -= 2;
                     if (a.exceptions[a] != a.readUnsignedShort(a)) {
                        a = false;
                        break;
                     }
                  }
               }

               if (a) {
                  a.classReaderOffset = a;
                  a.classReaderLength = a - a;
                  return a;
               }
            }
         }

         int a;
         if (a != 0) {
            a = a.b[a] & 255;

            for(a = a + 1; a > 0; a += 4) {
               a.visitParameter(a.readUTF8(a, a), a.readUnsignedShort(a + 2));
               --a;
            }
         }

         if (a != 0) {
            AnnotationVisitor a = a.visitAnnotationDefault();
            a.readAnnotationValue(a, a, (String)null, a);
            if (a != null) {
               a.visitEnd();
            }
         }

         if (a != 0) {
            a = a.readUnsignedShort(a);

            for(a = a + 2; a > 0; --a) {
               a = a.readAnnotationValues(a + 2, a, true, a.visitAnnotation(a.readUTF8(a, a), true));
            }
         }

         if (a != 0) {
            a = a.readUnsignedShort(a);

            for(a = a + 2; a > 0; --a) {
               a = a.readAnnotationValues(a + 2, a, true, a.visitAnnotation(a.readUTF8(a, a), false));
            }
         }

         if (a != 0) {
            a = a.readUnsignedShort(a);

            for(a = a + 2; a > 0; --a) {
               a = a.readAnnotationTarget(a, a);
               a = a.readAnnotationValues(a + 2, a, true, a.visitTypeAnnotation(a.typeRef, a.typePath, a.readUTF8(a, a), true));
            }
         }

         if (a != 0) {
            a = a.readUnsignedShort(a);

            for(a = a + 2; a > 0; --a) {
               a = a.readAnnotationTarget(a, a);
               a = a.readAnnotationValues(a + 2, a, true, a.visitTypeAnnotation(a.typeRef, a.typePath, a.readUTF8(a, a), false));
            }
         }

         if (a != 0) {
            a.readParameterAnnotations(a, a, a, true);
         }

         if (a != 0) {
            a.readParameterAnnotations(a, a, a, false);
         }

         while(a != null) {
            Attribute a = a.next;
            a.next = null;
            a.visitAttribute(a);
            a = a;
         }

         if (a != 0) {
            a.visitCode();
            a.readCode(a, a, a);
         }

         a.visitEnd();
         return a;
      }
   }

   private void readCode(MethodVisitor a, Context a, int a) {
      byte[] a = a.b;
      char[] a = a.buffer;
      int a = a.readUnsignedShort(a);
      int a = a.readUnsignedShort(a + 2);
      int a = a.readInt(a + 4);
      a += 8;
      int a = a;
      int a = a + a;
      Label[] a = a.labels = new Label[a + 2];
      a.readLabel(a + 1, a);

      while(true) {
         int a;
         int a;
         while(a < a) {
            a = a - a;
            int a = a[a] & 255;
            switch(ClassWriter.TYPE[a]) {
            case 0:
            case 4:
               ++a;
               break;
            case 1:
            case 3:
            case 11:
               a += 2;
               break;
            case 2:
            case 5:
            case 6:
            case 12:
            case 13:
               a += 3;
               break;
            case 7:
            case 8:
               a += 5;
               break;
            case 9:
               a.readLabel(a + a.readShort(a + 1), a);
               a += 3;
               break;
            case 10:
               a.readLabel(a + a.readInt(a + 1), a);
               a += 5;
               break;
            case 14:
               a = a + 4 - (a & 3);
               a.readLabel(a + a.readInt(a), a);

               for(a = a.readInt(a + 8) - a.readInt(a + 4) + 1; a > 0; --a) {
                  a.readLabel(a + a.readInt(a + 12), a);
                  a += 4;
               }

               a += 12;
               break;
            case 15:
               a = a + 4 - (a & 3);
               a.readLabel(a + a.readInt(a), a);

               for(a = a.readInt(a + 4); a > 0; --a) {
                  a.readLabel(a + a.readInt(a + 12), a);
                  a += 8;
               }

               a += 8;
               break;
            case 16:
            default:
               a += 4;
               break;
            case 17:
               a = a[a + 1] & 255;
               if (a == 132) {
                  a += 6;
               } else {
                  a += 4;
               }
               break;
            case 18:
               a.readLabel(a + a.readUnsignedShort(a + 1), a);
               a += 3;
            }
         }

         for(a = a.readUnsignedShort(a); a > 0; --a) {
            Label a = a.readLabel(a.readUnsignedShort(a + 2), a);
            Label a = a.readLabel(a.readUnsignedShort(a + 4), a);
            Label a = a.readLabel(a.readUnsignedShort(a + 6), a);
            String a = a.readUTF8(a.items[a.readUnsignedShort(a + 8)], a);
            a.visitTryCatchBlock(a, a, a, a);
            a += 8;
         }

         a += 2;
         int[] a = null;
         int[] a = null;
         a = 0;
         int a = 0;
         int a = -1;
         int a = -1;
         int a = 0;
         int a = 0;
         boolean a = true;
         boolean a = (a.flags & 8) != 0;
         int a = 0;
         int a = 0;
         int a = 0;
         Context a = null;
         Attribute a = null;

         int a;
         int a;
         int a;
         Label a;
         int a;
         for(a = a.readUnsignedShort(a); a > 0; --a) {
            String a = a.readUTF8(a + 2, a);
            Label var10000;
            if ("LocalVariableTable".equals(a)) {
               if ((a.flags & 2) == 0) {
                  a = a + 8;
                  a = a.readUnsignedShort(a + 8);

                  for(a = a; a > 0; --a) {
                     a = a.readUnsignedShort(a + 10);
                     if (a[a] == null) {
                        var10000 = a.readLabel(a, a);
                        var10000.status |= 1;
                     }

                     a += a.readUnsignedShort(a + 12);
                     if (a[a] == null) {
                        var10000 = a.readLabel(a, a);
                        var10000.status |= 1;
                     }

                     a += 10;
                  }
               }
            } else if ("LocalVariableTypeTable".equals(a)) {
               a = a + 8;
            } else if ("LineNumberTable".equals(a)) {
               if ((a.flags & 2) == 0) {
                  a = a.readUnsignedShort(a + 8);

                  for(a = a; a > 0; --a) {
                     a = a.readUnsignedShort(a + 10);
                     if (a[a] == null) {
                        var10000 = a.readLabel(a, a);
                        var10000.status |= 1;
                     }

                     for(a = a[a]; a.line > 0; a = a.next) {
                        if (a.next == null) {
                           a.next = new Label();
                        }
                     }

                     a.line = a.readUnsignedShort(a + 12);
                     a += 4;
                  }
               }
            } else if ("RuntimeVisibleTypeAnnotations".equals(a)) {
               a = a.readTypeAnnotations(a, a, a + 8, true);
               a = a.length != 0 && a.readByte(a[0]) >= 67 ? a.readUnsignedShort(a[0] + 1) : -1;
            } else if (!"RuntimeInvisibleTypeAnnotations".equals(a)) {
               if ("StackMapTable".equals(a)) {
                  if ((a.flags & 4) == 0) {
                     a = a + 10;
                     a = a.readInt(a + 4);
                     a = a.readUnsignedShort(a + 8);
                  }
               } else if ("StackMap".equals(a)) {
                  if ((a.flags & 4) == 0) {
                     a = false;
                     a = a + 10;
                     a = a.readInt(a + 4);
                     a = a.readUnsignedShort(a + 8);
                  }
               } else {
                  for(a = 0; a < a.attrs.length; ++a) {
                     if (a.attrs[a].type.equals(a)) {
                        Attribute a = a.attrs[a].read(a, a + 8, a.readInt(a + 4), a, a - 8, a);
                        if (a != null) {
                           a.next = a;
                           a = a;
                        }
                     }
                  }
               }
            } else {
               a = a.readTypeAnnotations(a, a, a + 8, false);
               a = a.length != 0 && a.readByte(a[0]) >= 67 ? a.readUnsignedShort(a[0] + 1) : -1;
            }

            a += 6 + a.readInt(a + 4);
         }

         a += 2;
         int a;
         if (a != 0) {
            a = a;
            a.offset = -1;
            a.mode = 0;
            a.localCount = 0;
            a.localDiff = 0;
            a.stackCount = 0;
            a.local = new Object[a];
            a.stack = new Object[a];
            if (a) {
               a.getImplicitFrame(a);
            }

            for(a = a; a < a + a - 2; ++a) {
               if (a[a] == 8) {
                  a = a.readUnsignedShort(a + 1);
                  if (a >= 0 && a < a && (a[a + a] & 255) == 187) {
                     a.readLabel(a, a);
                  }
               }
            }
         }

         if ((a.flags & 256) != 0) {
            a.visitFrame(-1, a, (Object[])null, 0, (Object[])null);
         }

         a = (a.flags & 256) == 0 ? -33 : 0;
         a = a;

         int a;
         String a;
         int a;
         while(a < a) {
            a = a - a;
            Label a = a[a];
            if (a != null) {
               Label a = a.next;
               a.next = null;
               a.visitLabel(a);
               if ((a.flags & 2) == 0 && a.line > 0) {
                  a.visitLineNumber(a.line, a);

                  while(a != null) {
                     a.visitLineNumber(a.line, a);
                     a = a.next;
                  }
               }
            }

            while(a != null && (a.offset == a || a.offset == -1)) {
               if (a.offset != -1) {
                  if (a && !a) {
                     a.visitFrame(a.mode, a.localDiff, a.local, a.stackCount, a.stack);
                  } else {
                     a.visitFrame(-1, a.localCount, a.local, a.stackCount, a.stack);
                  }
               }

               if (a > 0) {
                  a = a.readFrame(a, a, a, a);
                  --a;
               } else {
                  a = null;
               }
            }

            a = a[a] & 255;
            Label[] a;
            int a;
            switch(ClassWriter.TYPE[a]) {
            case 0:
               a.visitInsn(a);
               ++a;
               break;
            case 1:
               a.visitIntInsn(a, a[a + 1]);
               a += 2;
               break;
            case 2:
               a.visitIntInsn(a, a.readShort(a + 1));
               a += 3;
               break;
            case 3:
               a.visitVarInsn(a, a[a + 1] & 255);
               a += 2;
               break;
            case 4:
               if (a > 54) {
                  a -= 59;
                  a.visitVarInsn(54 + (a >> 2), a & 3);
               } else {
                  a -= 26;
                  a.visitVarInsn(21 + (a >> 2), a & 3);
               }

               ++a;
               break;
            case 5:
               a.visitTypeInsn(a, a.readClass(a + 1, a));
               a += 3;
               break;
            case 6:
            case 7:
               a = a.items[a.readUnsignedShort(a + 1)];
               boolean a = a[a - 1] == 11;
               a = a.readClass(a, a);
               a = a.items[a.readUnsignedShort(a + 2)];
               String a = a.readUTF8(a, a);
               String a = a.readUTF8(a + 2, a);
               if (a < 182) {
                  a.visitFieldInsn(a, a, a, a);
               } else {
                  a.visitMethodInsn(a, a, a, a, a);
               }

               if (a == 185) {
                  a += 5;
               } else {
                  a += 3;
               }
               break;
            case 8:
               a = a.items[a.readUnsignedShort(a + 1)];
               a = a.bootstrapMethods[a.readUnsignedShort(a)];
               Handle a = (Handle)a.readConst(a.readUnsignedShort(a), a);
               a = a.readUnsignedShort(a + 2);
               Object[] a = new Object[a];
               a += 4;

               for(int a = 0; a < a; ++a) {
                  a[a] = a.readConst(a.readUnsignedShort(a), a);
                  a += 2;
               }

               a = a.items[a.readUnsignedShort(a + 2)];
               String a = a.readUTF8(a, a);
               String a = a.readUTF8(a + 2, a);
               a.visitInvokeDynamicInsn(a, a, a, a);
               break;
            case 9:
               a.visitJumpInsn(a, a[a + a.readShort(a + 1)]);
               a += 3;
               break;
            case 10:
               a.visitJumpInsn(a + a, a[a + a.readInt(a + 1)]);
               a += 5;
               break;
            case 11:
               a.visitLdcInsn(a.readConst(a[a + 1] & 255, a));
               a += 2;
               break;
            case 12:
               a.visitLdcInsn(a.readConst(a.readUnsignedShort(a + 1), a));
               a += 3;
               break;
            case 13:
               a.visitIincInsn(a[a + 1] & 255, a[a + 2]);
               a += 3;
               break;
            case 14:
               a = a + 4 - (a & 3);
               a = a + a.readInt(a);
               a = a.readInt(a + 4);
               int a = a.readInt(a + 8);
               a = new Label[a - a + 1];
               a += 12;

               for(a = 0; a < a.length; ++a) {
                  a[a] = a[a + a.readInt(a)];
                  a += 4;
               }

               a.visitTableSwitchInsn(a, a, a[a], a);
               break;
            case 15:
               a = a + 4 - (a & 3);
               a = a + a.readInt(a);
               a = a.readInt(a + 4);
               int[] a = new int[a];
               a = new Label[a];
               a += 8;

               for(a = 0; a < a; ++a) {
                  a[a] = a.readInt(a);
                  a[a] = a[a + a.readInt(a + 4)];
                  a += 8;
               }

               a.visitLookupSwitchInsn(a[a], a, a);
               break;
            case 16:
            default:
               a.visitMultiANewArrayInsn(a.readClass(a + 1, a), a[a + 3] & 255);
               a += 4;
               break;
            case 17:
               a = a[a + 1] & 255;
               if (a == 132) {
                  a.visitIincInsn(a.readUnsignedShort(a + 2), a.readShort(a + 4));
                  a += 6;
               } else {
                  a.visitVarInsn(a, a.readUnsignedShort(a + 2));
                  a += 4;
               }
               break;
            case 18:
               a = a < 218 ? a - 49 : a - 20;
               Label a = a[a + a.readUnsignedShort(a + 1)];
               if (a != 167 && a != 168) {
                  a = a <= 166 ? (a + 1 ^ 1) - 1 : a ^ 1;
                  a = new Label();
                  a.visitJumpInsn(a, a);
                  a.visitJumpInsn(200, a);
                  a.visitLabel(a);
                  if (a != 0 && (a == null || a.offset != a + 3)) {
                     a.visitFrame(256, 0, (Object[])null, 0, (Object[])null);
                  }
               } else {
                  a.visitJumpInsn(a + 33, a);
               }

               a += 3;
            }

            for(a += 5; a != null && a < a.length && a <= a; a = a < a.length && a.readByte(a[a]) >= 67 ? a.readUnsignedShort(a[a] + 1) : -1) {
               if (a == a) {
                  a = a.readAnnotationTarget(a, a[a]);
                  a.readAnnotationValues(a + 2, a, true, a.visitInsnAnnotation(a.typeRef, a.typePath, a.readUTF8(a, a), true));
               }

               ++a;
            }

            while(a != null && a < a.length && a <= a) {
               if (a == a) {
                  a = a.readAnnotationTarget(a, a[a]);
                  a.readAnnotationValues(a + 2, a, true, a.visitInsnAnnotation(a.typeRef, a.typePath, a.readUTF8(a, a), false));
               }

               ++a;
               a = a < a.length && a.readByte(a[a]) >= 67 ? a.readUnsignedShort(a[a] + 1) : -1;
            }
         }

         if (a[a] != null) {
            a.visitLabel(a[a]);
         }

         if ((a.flags & 2) == 0 && a != 0) {
            int[] a = null;
            if (a != 0) {
               a = a + 2;
               a = new int[a.readUnsignedShort(a) * 3];

               for(a = a.length; a > 0; a += 10) {
                  --a;
                  a[a] = a + 6;
                  --a;
                  a[a] = a.readUnsignedShort(a + 8);
                  --a;
                  a[a] = a.readUnsignedShort(a);
               }
            }

            a = a + 2;

            for(a = a.readUnsignedShort(a); a > 0; --a) {
               a = a.readUnsignedShort(a);
               a = a.readUnsignedShort(a + 2);
               a = a.readUnsignedShort(a + 8);
               a = null;
               if (a != null) {
                  for(a = 0; a < a.length; a += 3) {
                     if (a[a] == a && a[a + 1] == a) {
                        a = a.readUTF8(a[a + 2], a);
                        break;
                     }
                  }
               }

               a.visitLocalVariable(a.readUTF8(a + 4, a), a.readUTF8(a + 6, a), a, a[a], a[a + a], a);
               a += 10;
            }
         }

         if (a != null) {
            for(a = 0; a < a.length; ++a) {
               if (a.readByte(a[a]) >> 1 == 32) {
                  a = a.readAnnotationTarget(a, a[a]);
                  a.readAnnotationValues(a + 2, a, true, a.visitLocalVariableAnnotation(a.typeRef, a.typePath, a.start, a.end, a.index, a.readUTF8(a, a), true));
               }
            }
         }

         if (a != null) {
            for(a = 0; a < a.length; ++a) {
               if (a.readByte(a[a]) >> 1 == 32) {
                  a = a.readAnnotationTarget(a, a[a]);
                  a.readAnnotationValues(a + 2, a, true, a.visitLocalVariableAnnotation(a.typeRef, a.typePath, a.start, a.end, a.index, a.readUTF8(a, a), false));
               }
            }
         }

         while(a != null) {
            Attribute a = a.next;
            a.next = null;
            a.visitAttribute(a);
            a = a;
         }

         a.visitMaxs(a, a);
         return;
      }
   }

   private int[] readTypeAnnotations(MethodVisitor a, Context a, int a, boolean a) {
      char[] a = a.buffer;
      int[] a = new int[a.readUnsignedShort(a)];
      a += 2;

      for(int a = 0; a < a.length; ++a) {
         a[a] = a;
         int a = a.readInt(a);
         int a;
         switch(a >>> 24) {
         case 0:
         case 1:
         case 22:
            a += 2;
            break;
         case 19:
         case 20:
         case 21:
            ++a;
            break;
         case 64:
         case 65:
            for(a = a.readUnsignedShort(a + 1); a > 0; --a) {
               int a = a.readUnsignedShort(a + 3);
               int a = a.readUnsignedShort(a + 5);
               a.readLabel(a, a.labels);
               a.readLabel(a + a, a.labels);
               a += 6;
            }

            a += 3;
            break;
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
            a += 4;
            break;
         default:
            a += 3;
         }

         a = a.readByte(a);
         if (a >>> 24 == 66) {
            TypePath a = a == 0 ? null : new TypePath(a.b, a);
            a += 1 + 2 * a;
            a = a.readAnnotationValues(a + 2, a, true, a.visitTryCatchAnnotation(a, a, a.readUTF8(a, a), a));
         } else {
            a = a.readAnnotationValues(a + 3 + 2 * a, a, true, (AnnotationVisitor)null);
         }
      }

      return a;
   }

   private int readAnnotationTarget(Context a, int a) {
      int a;
      int a;
      a = a.readInt(a);
      label29:
      switch(a >>> 24) {
      case 0:
      case 1:
      case 22:
         a &= -65536;
         a += 2;
         break;
      case 19:
      case 20:
      case 21:
         a &= -16777216;
         ++a;
         break;
      case 64:
      case 65:
         a &= -16777216;
         a = a.readUnsignedShort(a + 1);
         a.start = new Label[a];
         a.end = new Label[a];
         a.index = new int[a];
         a += 3;
         int a = 0;

         while(true) {
            if (a >= a) {
               break label29;
            }

            int a = a.readUnsignedShort(a);
            int a = a.readUnsignedShort(a + 2);
            a.start[a] = a.readLabel(a, a.labels);
            a.end[a] = a.readLabel(a + a, a.labels);
            a.index[a] = a.readUnsignedShort(a + 4);
            a += 6;
            ++a;
         }
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         a &= -16776961;
         a += 4;
         break;
      default:
         a &= a >>> 24 < 67 ? -256 : -16777216;
         a += 3;
      }

      a = a.readByte(a);
      a.typeRef = a;
      a.typePath = a == 0 ? null : new TypePath(a.b, a);
      return a + 1 + 2 * a;
   }

   private void readParameterAnnotations(MethodVisitor a, Context a, int a, boolean a) {
      int a = a.b[a++] & 255;
      int a = Type.getArgumentTypes(a.desc).length - a;

      int a;
      AnnotationVisitor a;
      for(a = 0; a < a; ++a) {
         a = a.visitParameterAnnotation(a, "Ljava/lang/Synthetic;", false);
         if (a != null) {
            a.visitEnd();
         }
      }

      for(char[] a = a.buffer; a < a + a; ++a) {
         int a = a.readUnsignedShort(a);

         for(a += 2; a > 0; --a) {
            a = a.visitParameterAnnotation(a, a.readUTF8(a, a), a);
            a = a.readAnnotationValues(a + 2, a, true, a);
         }
      }

   }

   private int readAnnotationValues(int a, char[] a, boolean a, AnnotationVisitor a) {
      int a = a.readUnsignedShort(a);
      a += 2;
      if (a) {
         while(a > 0) {
            a = a.readAnnotationValue(a + 2, a, a.readUTF8(a, a), a);
            --a;
         }
      } else {
         while(a > 0) {
            a = a.readAnnotationValue(a, a, (String)null, a);
            --a;
         }
      }

      if (a != null) {
         a.visitEnd();
      }

      return a;
   }

   private int readAnnotationValue(int a, char[] a, String a, AnnotationVisitor a) {
      if (a == null) {
         switch(a.b[a] & 255) {
         case 64:
            return a.readAnnotationValues(a + 3, a, true, (AnnotationVisitor)null);
         case 91:
            return a.readAnnotationValues(a + 1, a, false, (AnnotationVisitor)null);
         case 101:
            return a + 5;
         default:
            return a + 3;
         }
      } else {
         switch(a.b[a++] & 255) {
         case 64:
            a = a.readAnnotationValues(a + 2, a, true, a.visitAnnotation(a, a.readUTF8(a, a)));
         case 65:
         case 69:
         case 71:
         case 72:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 100:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         default:
            break;
         case 66:
            a.visit(a, (byte)a.readInt(a.items[a.readUnsignedShort(a)]));
            a += 2;
            break;
         case 67:
            a.visit(a, (char)a.readInt(a.items[a.readUnsignedShort(a)]));
            a += 2;
            break;
         case 68:
         case 70:
         case 73:
         case 74:
            a.visit(a, a.readConst(a.readUnsignedShort(a), a));
            a += 2;
            break;
         case 83:
            a.visit(a, (short)a.readInt(a.items[a.readUnsignedShort(a)]));
            a += 2;
            break;
         case 90:
            a.visit(a, a.readInt(a.items[a.readUnsignedShort(a)]) == 0 ? Boolean.FALSE : Boolean.TRUE);
            a += 2;
            break;
         case 91:
            int a = a.readUnsignedShort(a);
            a += 2;
            if (a == 0) {
               return a.readAnnotationValues(a - 2, a, false, a.visitArray(a));
            }

            int a;
            switch(a.b[a++] & 255) {
            case 66:
               byte[] a = new byte[a];

               for(a = 0; a < a; ++a) {
                  a[a] = (byte)a.readInt(a.items[a.readUnsignedShort(a)]);
                  a += 3;
               }

               a.visit(a, a);
               --a;
               return a;
            case 67:
               char[] a = new char[a];

               for(a = 0; a < a; ++a) {
                  a[a] = (char)a.readInt(a.items[a.readUnsignedShort(a)]);
                  a += 3;
               }

               a.visit(a, a);
               --a;
               return a;
            case 68:
               double[] a = new double[a];

               for(a = 0; a < a; ++a) {
                  a[a] = Double.longBitsToDouble(a.readLong(a.items[a.readUnsignedShort(a)]));
                  a += 3;
               }

               a.visit(a, a);
               --a;
               return a;
            case 69:
            case 71:
            case 72:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            default:
               a = a.readAnnotationValues(a - 3, a, false, a.visitArray(a));
               return a;
            case 70:
               float[] a = new float[a];

               for(a = 0; a < a; ++a) {
                  a[a] = Float.intBitsToFloat(a.readInt(a.items[a.readUnsignedShort(a)]));
                  a += 3;
               }

               a.visit(a, a);
               --a;
               return a;
            case 73:
               int[] a = new int[a];

               for(a = 0; a < a; ++a) {
                  a[a] = a.readInt(a.items[a.readUnsignedShort(a)]);
                  a += 3;
               }

               a.visit(a, a);
               --a;
               return a;
            case 74:
               long[] a = new long[a];

               for(a = 0; a < a; ++a) {
                  a[a] = a.readLong(a.items[a.readUnsignedShort(a)]);
                  a += 3;
               }

               a.visit(a, a);
               --a;
               return a;
            case 83:
               short[] a = new short[a];

               for(a = 0; a < a; ++a) {
                  a[a] = (short)a.readInt(a.items[a.readUnsignedShort(a)]);
                  a += 3;
               }

               a.visit(a, a);
               --a;
               return a;
            case 90:
               boolean[] a = new boolean[a];

               for(a = 0; a < a; ++a) {
                  a[a] = a.readInt(a.items[a.readUnsignedShort(a)]) != 0;
                  a += 3;
               }

               a.visit(a, a);
               --a;
               return a;
            }
         case 99:
            a.visit(a, Type.getType(a.readUTF8(a, a)));
            a += 2;
            break;
         case 101:
            a.visitEnum(a, a.readUTF8(a, a), a.readUTF8(a + 2, a));
            a += 4;
            break;
         case 115:
            a.visit(a, a.readUTF8(a, a));
            a += 2;
         }

         return a;
      }
   }

   private void getImplicitFrame(Context a) {
      String a = a.desc;
      Object[] a = a.local;
      int a = 0;
      if ((a.access & 8) == 0) {
         if ("<init>".equals(a.name)) {
            a[a++] = Opcodes.UNINITIALIZED_THIS;
         } else {
            a[a++] = a.readClass(a.header + 2, a.buffer);
         }
      }

      int a = 1;

      while(true) {
         int a = a;
         switch(a.charAt(a++)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            a[a++] = Opcodes.INTEGER;
            break;
         case 'D':
            a[a++] = Opcodes.DOUBLE;
            break;
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
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            a.localCount = a;
            return;
         case 'F':
            a[a++] = Opcodes.FLOAT;
            break;
         case 'J':
            a[a++] = Opcodes.LONG;
            break;
         case 'L':
            while(a.charAt(a) != ';') {
               ++a;
            }

            a[a++] = a.substring(a + 1, a++);
            break;
         case '[':
            while(a.charAt(a) == '[') {
               ++a;
            }

            if (a.charAt(a) == 'L') {
               ++a;

               while(a.charAt(a) != ';') {
                  ++a;
               }
            }

            int var10001 = a++;
            ++a;
            a[var10001] = a.substring(a, a);
         }
      }
   }

   private int readFrame(int a, boolean a, boolean a, Context a) {
      char[] a = a.buffer;
      Label[] a = a.labels;
      int a;
      if (a) {
         a = a.b[a++] & 255;
      } else {
         a = 255;
         a.offset = -1;
      }

      a.localDiff = 0;
      int a;
      if (a < 64) {
         a = a;
         a.mode = 3;
         a.stackCount = 0;
      } else if (a < 128) {
         a = a - 64;
         a = a.readFrameType(a.stack, 0, a, a, a);
         a.mode = 4;
         a.stackCount = 1;
      } else {
         a = a.readUnsignedShort(a);
         a += 2;
         if (a == 247) {
            a = a.readFrameType(a.stack, 0, a, a, a);
            a.mode = 4;
            a.stackCount = 1;
         } else if (a >= 248 && a < 251) {
            a.mode = 2;
            a.localDiff = 251 - a;
            a.localCount -= a.localDiff;
            a.stackCount = 0;
         } else if (a == 251) {
            a.mode = 3;
            a.stackCount = 0;
         } else {
            int a;
            int a;
            if (a < 255) {
               a = a ? a.localCount : 0;

               for(a = a - 251; a > 0; --a) {
                  a = a.readFrameType(a.local, a++, a, a, a);
               }

               a.mode = 1;
               a.localDiff = a - 251;
               a.localCount += a.localDiff;
               a.stackCount = 0;
            } else {
               a.mode = 0;
               a = a.readUnsignedShort(a);
               a += 2;
               a.localDiff = a;
               a.localCount = a;

               for(a = 0; a > 0; --a) {
                  a = a.readFrameType(a.local, a++, a, a, a);
               }

               a = a.readUnsignedShort(a);
               a += 2;
               a.stackCount = a;

               for(a = 0; a > 0; --a) {
                  a = a.readFrameType(a.stack, a++, a, a, a);
               }
            }
         }
      }

      a.offset += a + 1;
      a.readLabel(a.offset, a);
      return a;
   }

   private int readFrameType(Object[] a, int a, int a, char[] a, Label[] a) {
      int a = a.b[a++] & 255;
      switch(a) {
      case 0:
         a[a] = Opcodes.TOP;
         break;
      case 1:
         a[a] = Opcodes.INTEGER;
         break;
      case 2:
         a[a] = Opcodes.FLOAT;
         break;
      case 3:
         a[a] = Opcodes.DOUBLE;
         break;
      case 4:
         a[a] = Opcodes.LONG;
         break;
      case 5:
         a[a] = Opcodes.NULL;
         break;
      case 6:
         a[a] = Opcodes.UNINITIALIZED_THIS;
         break;
      case 7:
         a[a] = a.readClass(a, a);
         a += 2;
         break;
      default:
         a[a] = a.readLabel(a.readUnsignedShort(a), a);
         a += 2;
      }

      return a;
   }

   protected Label readLabel(int a, Label[] a) {
      if (a[a] == null) {
         a[a] = new Label();
      }

      return a[a];
   }

   private int getAttributes() {
      int a = a.header + 8 + a.readUnsignedShort(a.header + 6) * 2;

      int a;
      int a;
      for(a = a.readUnsignedShort(a); a > 0; --a) {
         for(a = a.readUnsignedShort(a + 8); a > 0; --a) {
            a += 6 + a.readInt(a + 12);
         }

         a += 8;
      }

      a += 2;

      for(a = a.readUnsignedShort(a); a > 0; --a) {
         for(a = a.readUnsignedShort(a + 8); a > 0; --a) {
            a += 6 + a.readInt(a + 12);
         }

         a += 8;
      }

      return a + 2;
   }

   private Attribute readAttribute(Attribute[] a, String a, int a, int a, char[] a, int a, Label[] a) {
      for(int a = 0; a < a.length; ++a) {
         if (a[a].type.equals(a)) {
            return a[a].read(a, a, a, a, a, a);
         }
      }

      return (new Attribute(a)).read(a, a, a, (char[])null, -1, (Label[])null);
   }

   public int getItemCount() {
      return a.items.length;
   }

   public int getItem(int a) {
      return a.items[a];
   }

   public int getMaxStringLength() {
      return a.maxStringLength;
   }

   public int readByte(int a) {
      return a.b[a] & 255;
   }

   public int readUnsignedShort(int a) {
      byte[] a = a.b;
      return (a[a] & 255) << 8 | a[a + 1] & 255;
   }

   public short readShort(int a) {
      byte[] a = a.b;
      return (short)((a[a] & 255) << 8 | a[a + 1] & 255);
   }

   public int readInt(int a) {
      byte[] a = a.b;
      return (a[a] & 255) << 24 | (a[a + 1] & 255) << 16 | (a[a + 2] & 255) << 8 | a[a + 3] & 255;
   }

   public long readLong(int a) {
      long a = (long)a.readInt(a);
      long a = (long)a.readInt(a + 4) & 4294967295L;
      return a << 32 | a;
   }

   public String readUTF8(int a, char[] a) {
      int a = a.readUnsignedShort(a);
      if (a != 0 && a != 0) {
         String a = a.strings[a];
         if (a != null) {
            return a;
         } else {
            a = a.items[a];
            return a.strings[a] = a.readUTF(a + 2, a.readUnsignedShort(a), a);
         }
      } else {
         return null;
      }
   }

   private String readUTF(int a, int a, char[] a) {
      int a = a + a;
      byte[] a = a.b;
      int a = 0;
      int a = 0;
      char a = 0;

      while(true) {
         while(a < a) {
            int a = a[a++];
            switch(a) {
            case 0:
               int a = a & 255;
               if (a < 128) {
                  a[a++] = (char)a;
               } else {
                  if (a < 224 && a > 191) {
                     a = (char)(a & 31);
                     a = 1;
                     continue;
                  }

                  a = (char)(a & 15);
                  a = 2;
               }
               break;
            case 1:
               a[a++] = (char)(a << 6 | a & 63);
               a = 0;
               break;
            case 2:
               a = (char)(a << 6 | a & 63);
               a = 1;
            }
         }

         return new String(a, 0, a);
      }
   }

   public String readClass(int a, char[] a) {
      return a.readUTF8(a.items[a.readUnsignedShort(a)], a);
   }

   public Object readConst(int a, char[] a) {
      int a = a.items[a];
      switch(a.b[a - 1]) {
      case 3:
         return a.readInt(a);
      case 4:
         return Float.intBitsToFloat(a.readInt(a));
      case 5:
         return a.readLong(a);
      case 6:
         return Double.longBitsToDouble(a.readLong(a));
      case 7:
         return Type.getObjectType(a.readUTF8(a, a));
      case 8:
         return a.readUTF8(a, a);
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      default:
         int a = a.readByte(a);
         int[] a = a.items;
         int a = a[a.readUnsignedShort(a + 1)];
         boolean a = a.b[a - 1] == 11;
         String a = a.readClass(a, a);
         a = a[a.readUnsignedShort(a + 2)];
         String a = a.readUTF8(a, a);
         String a = a.readUTF8(a + 2, a);
         return new Handle(a, a, a, a, a);
      case 16:
         return Type.getMethodType(a.readUTF8(a, a));
      }
   }
}

package org.spongepowered.asm.lib;

public class TypePath {
   public static final int ARRAY_ELEMENT = 0;
   public static final int INNER_TYPE = 1;
   public static final int WILDCARD_BOUND = 2;
   public static final int TYPE_ARGUMENT = 3;
   byte[] b;
   int offset;

   TypePath(byte[] a, int a) {
      a.b = a;
      a.offset = a;
   }

   public int getLength() {
      return a.b[a.offset];
   }

   public int getStep(int a) {
      return a.b[a.offset + 2 * a + 1];
   }

   public int getStepArgument(int a) {
      return a.b[a.offset + 2 * a + 2];
   }

   public static TypePath fromString(String a) {
      if (a != null && a.length() != 0) {
         int a = a.length();
         ByteVector a = new ByteVector(a);
         a.putByte(0);
         int a = 0;

         while(true) {
            while(a < a) {
               char a = a.charAt(a++);
               if (a == '[') {
                  a.put11(0, 0);
               } else if (a == '.') {
                  a.put11(1, 0);
               } else if (a == '*') {
                  a.put11(2, 0);
               } else if (a >= '0' && a <= '9') {
                  int a;
                  for(a = a - 48; a < a && (a = a.charAt(a)) >= '0' && a <= '9'; ++a) {
                     a = a * 10 + a - 48;
                  }

                  if (a < a && a.charAt(a) == ';') {
                     ++a;
                  }

                  a.put11(3, a);
               }
            }

            a.data[0] = (byte)(a.length / 2);
            return new TypePath(a.data, 0);
         }
      } else {
         return null;
      }
   }

   public String toString() {
      int a = a.getLength();
      StringBuilder a = new StringBuilder(a * 2);

      for(int a = 0; a < a; ++a) {
         switch(a.getStep(a)) {
         case 0:
            a.append('[');
            break;
         case 1:
            a.append('.');
            break;
         case 2:
            a.append('*');
            break;
         case 3:
            a.append(a.getStepArgument(a)).append(';');
            break;
         default:
            a.append('_');
         }
      }

      return a.toString();
   }
}

package org.spongepowered.asm.lib;

public class ByteVector {
   byte[] data;
   int length;

   public ByteVector() {
      a.data = new byte[64];
   }

   public ByteVector(int a) {
      a.data = new byte[a];
   }

   public ByteVector putByte(int a) {
      int a = a.length;
      if (a + 1 > a.data.length) {
         a.enlarge(1);
      }

      a.data[a++] = (byte)a;
      a.length = a;
      return a;
   }

   ByteVector put11(int a, int a) {
      int a = a.length;
      if (a + 2 > a.data.length) {
         a.enlarge(2);
      }

      byte[] a = a.data;
      a[a++] = (byte)a;
      a[a++] = (byte)a;
      a.length = a;
      return a;
   }

   public ByteVector putShort(int a) {
      int a = a.length;
      if (a + 2 > a.data.length) {
         a.enlarge(2);
      }

      byte[] a = a.data;
      a[a++] = (byte)(a >>> 8);
      a[a++] = (byte)a;
      a.length = a;
      return a;
   }

   ByteVector put12(int a, int a) {
      int a = a.length;
      if (a + 3 > a.data.length) {
         a.enlarge(3);
      }

      byte[] a = a.data;
      a[a++] = (byte)a;
      a[a++] = (byte)(a >>> 8);
      a[a++] = (byte)a;
      a.length = a;
      return a;
   }

   public ByteVector putInt(int a) {
      int a = a.length;
      if (a + 4 > a.data.length) {
         a.enlarge(4);
      }

      byte[] a = a.data;
      a[a++] = (byte)(a >>> 24);
      a[a++] = (byte)(a >>> 16);
      a[a++] = (byte)(a >>> 8);
      a[a++] = (byte)a;
      a.length = a;
      return a;
   }

   public ByteVector putLong(long a) {
      int a = a.length;
      if (a + 8 > a.data.length) {
         a.enlarge(8);
      }

      byte[] a = a.data;
      int a = (int)(a >>> 32);
      a[a++] = (byte)(a >>> 24);
      a[a++] = (byte)(a >>> 16);
      a[a++] = (byte)(a >>> 8);
      a[a++] = (byte)a;
      a = (int)a;
      a[a++] = (byte)(a >>> 24);
      a[a++] = (byte)(a >>> 16);
      a[a++] = (byte)(a >>> 8);
      a[a++] = (byte)a;
      a.length = a;
      return a;
   }

   public ByteVector putUTF8(String a) {
      int a = a.length();
      if (a > 65535) {
         throw new IllegalArgumentException();
      } else {
         int a = a.length;
         if (a + 2 + a > a.data.length) {
            a.enlarge(2 + a);
         }

         byte[] a = a.data;
         a[a++] = (byte)(a >>> 8);
         a[a++] = (byte)a;

         for(int a = 0; a < a; ++a) {
            char a = a.charAt(a);
            if (a < 1 || a > 127) {
               a.length = a;
               return a.encodeUTF8(a, a, 65535);
            }

            a[a++] = (byte)a;
         }

         a.length = a;
         return a;
      }
   }

   ByteVector encodeUTF8(String a, int a, int a) {
      int a = a.length();
      int a = a;

      int a;
      char a;
      for(a = a; a < a; ++a) {
         a = a.charAt(a);
         if (a >= 1 && a <= 127) {
            ++a;
         } else if (a > 2047) {
            a += 3;
         } else {
            a += 2;
         }
      }

      if (a > a) {
         throw new IllegalArgumentException();
      } else {
         a = a.length - a - 2;
         if (a >= 0) {
            a.data[a] = (byte)(a >>> 8);
            a.data[a + 1] = (byte)a;
         }

         if (a.length + a - a > a.data.length) {
            a.enlarge(a - a);
         }

         int a = a.length;

         for(int a = a; a < a; ++a) {
            a = a.charAt(a);
            if (a >= 1 && a <= 127) {
               a.data[a++] = (byte)a;
            } else if (a > 2047) {
               a.data[a++] = (byte)(224 | a >> 12 & 15);
               a.data[a++] = (byte)(128 | a >> 6 & 63);
               a.data[a++] = (byte)(128 | a & 63);
            } else {
               a.data[a++] = (byte)(192 | a >> 6 & 31);
               a.data[a++] = (byte)(128 | a & 63);
            }
         }

         a.length = a;
         return a;
      }
   }

   public ByteVector putByteArray(byte[] a, int a, int a) {
      if (a.length + a > a.data.length) {
         a.enlarge(a);
      }

      if (a != null) {
         System.arraycopy(a, a, a.data, a.length, a);
      }

      a.length += a;
      return a;
   }

   private void enlarge(int a) {
      int a = 2 * a.data.length;
      int a = a.length + a;
      byte[] a = new byte[a > a ? a : a];
      System.arraycopy(a.data, 0, a, 0, a.length);
      a.data = a;
   }
}

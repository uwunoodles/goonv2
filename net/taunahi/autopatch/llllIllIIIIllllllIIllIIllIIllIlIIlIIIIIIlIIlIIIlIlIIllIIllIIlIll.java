package net.taunahi.autopatch;

import dev.jnic.Keeqam.JNICLoader;

public final class llllIllIIIIllllllIIllIIllIIllIlIIlIIIIIIlIIlIIIlIlIIllIIllIIlIll {
   private static final String[] m;

   private static final native char llIIlIIlllIIIlIIlIIIlllIllllIIIllIIIIIlllIllIlllIlllIIIIIIlIllIl(Object[] var0);

   // $FF: synthetic method
   public static final char lIIIlIllIlIIIIllIIlllIIIIllllIlIlllIIIIIIlIIlIllIIIIIIIIIIlIIlII(Object[] a) {
      char a = (Integer)a[0];
      char a = (Integer)a[1];
      Object[] var10003 = new Object[2 << 14171 >>> -1133780962 + Integer.parseInt(m[31 >>> 8549], 30)];
      var10003[1] = a;
      var10003[0] = a;
      return llIIlIIlllIIIlIIlIIIlllIllllIIIllIIIIIlllIllIlllIlllIIIIIIlIllIl(var10003);
   }

   // $FF: synthetic method
   public static native void $jnicLoader();

   static {
      char[] var0 = "\u0e98ົ\u0014ÏOP\u009f+\b".toCharArray();
      int var1 = 42 << Integer.parseInt("4fe", 35);
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[18 << Integer.parseInt("g8n", 31)]).getMethodName().hashCode() & '\uffff' << 8352;
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = 71 >>> 5703;
      ++var1;
      String[] var5 = new String[var0[var10001] ^ Integer.parseInt("1h19a0g", 22) >>> 4886 ^ var3];
      int var6 = 70 << 10079;

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ 21 << 5952 ^ var3];
         int var8 = 14 << 2847;

         while(var11 > 0) {
            int var9 = var0[var1];
            switch(var4[var1 % var4.length] ^ 27 << 13920) {
            case 53:
            case 110:
               var9 ^= -1057963111 + 1057963279;
               break;
            case 82:
            case 107:
            case 115:
            case 116:
            case 119:
            case 120:
            case 126:
               var9 ^= -95492857 - -95493042;
               break;
            case 111:
            case 114:
               var9 ^= 155189248 >>> 9750;
               break;
            case 117:
               var9 ^= 25 << 14432;
               break;
            case 122:
               var9 ^= 61 << Integer.parseInt("20o", 34);
            }

            while(true) {
               var7[var8] = (char)var9;

               try {
                  ++var8;
                  ++var1;
                  --var11;
               } catch (Throwable var10) {
                  break;
               }

               var9 ^= -95492857 - -95493042;
            }
         }

         var5[var6++] = (new String(var7)).intern();
      } while(var1 < var0.length);

      m = var5;
      JNICLoader.init();
      $jnicLoader();
   }
}

package net.taunahi.autopatch;

public class IIlIIlIlIIIIIlIIlIlIIIIIllllIlllIlIlllIllIllIllIIllIIlIllllIlIIl extends lIlIlllIIlIllIIIIllllllIllIIllIIIIlllIIIlllllIlllIIlIIlIIIlllllI {
   private static final String b;
   private static final String[] L;

   public IIlIIlIlIIIIIlIIlIlIIIIIllllIlllIlIlllIllIllIllIIllIIlIllllIlIIl(String a) {
      super(a);
   }

   public String IIIlllIIlIlIIlIlIlIllIIlIllllIlIllIIlIlllIIlIllIlIlIIlIIllIIIIII(Object[] param1) throws lIIlllllIIIIlIlllllIIllIlIIllllIlIlIIIllIllllIIIIlIIlIIlIlIIlIII {
      // $FF: Couldn't be decompiled
   }

   private static lIIlllllIIIIlIlllllIIllIlIIllllIlIlIIIllIllllIIIIlIIlIIlIlIIlIII a(lIIlllllIIIIlIlllllIIllIlIIllllIlIlIIIllIllllIIIIlIIlIIlIlIIlIII var0) {
      return var0;
   }

   static {
      char[] var0 = "\u0e7f໋\u009a\u0094Úú\u001fæ©¬ແ\u0097ÿ໋\u009a\u0084V¥Ð×¥\u0094\u0ec5Á\u008eÌÇÄÃແ\u0097\u009e໋\u008b\u0090\u0084ÇÎ\u0083ÄÜເ\u0094ÂÅ໗\u008d\u009b\u0096ÑØ\u0091Ë¶\u0085\u0096ÀßÜÑ½\u0087\u008bËÜÒ\u0ec5Â\u0085ÖÞÞ\u0087".toCharArray();
      int var1 = 64 << 986;
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[47 >>> 390]).getMethodName().hashCode() & 2097120 >>> 2181;
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = 112 << 12156;
      ++var1;
      String[] var5 = new String[var0[var10001] ^ 832548006 ^ 832547961 ^ var3];
      int var6 = 68 << Integer.parseInt("9am", 26);

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ 53 << 12769 ^ var3];
         int var8 = 112 << 11036;

         while(var11 > 0) {
            int var9 = var0[var1];
            switch(var4[var1 % var4.length] ^ -134217728 >>> 5594) {
            case 16:
            case 93:
            case 119:
               var9 ^= 10878976 >>> 7920;
               break;
            case 74:
            case 81:
            case 82:
            case 87:
               var9 ^= 1034535988 + -1034535805;
               break;
            case 75:
            case 78:
            case 91:
               var9 ^= 113664 >>> 6570;
               break;
            case 80:
               var9 ^= 314572800 >>> 1461;
               break;
            case 86:
            case 95:
               var9 ^= '저' >>> 7944;
            }

            while(true) {
               var7[var8] = (char)var9;

               try {
                  ++var8;
                  ++var1;
                  --var11;
               } catch (Exception var10) {
                  break;
               }

               var9 ^= 113664 >>> 6570;
            }
         }

         var5[var6++] = (new String(var7)).intern();
      } while(var1 < var0.length);

      L = var5;
      int var10000 = Integer.parseInt(L[28 << 990], 26) - -909952477 >>> (-1723614940 ^ -1723618382);
      char[] var10002 = L[7 << 8480].toCharArray();
      int var10003 = var10002.length;
      int var13 = var10000;
      char[] var14 = var10002;
      var10000 = var10003;

      for(var1 = 0; var10000 > var1; ++var1) {
         char var10005 = var14[var1];
         int var10007;
         switch(var1 % (7 << 800 << Integer.parseInt(L[4096 >>> 13257], 34) - 1366755024) ^ 349444435 << 15776 << (1889109193 ^ 1889118760) ^ -983159244 - -891009145 ^ Integer.parseInt(L[10485760 >>> 9045], 23) - -701313687) {
         case 548290808:
            var10007 = Integer.parseInt(L[1610612736 >>> 13757], 33) >>> Integer.parseInt(L[393216 >>> 944], 23) << -102352856 + 102356346;
            break;
         case 548290809:
            var10007 = (1627094394 ^ 1626979706) >>> (357433344 >>> 10672);
            break;
         case 548290812:
            var10007 = 1966458649 + -808830745 >>> (503316480 >>> 3958);
            break;
         case 548290813:
            var10007 = 425984 >>> 4610 >>> ((L[33554432 >>> 4023].hashCode() ^ 418) << 2433);
            break;
         case 548290814:
            var10007 = 1 << 12832 << 1416753645 - 1416743819;
            break;
         case 548290815:
            var10007 = Integer.parseInt(L[1 << 6241], 28) + 1958391818 >>> 879154845 - 879153159;
            break;
         default:
            var10007 = 9 << 12736 << (-1626062062 ^ -1626062797);
         }

         var14[var1] = (char)(var10005 ^ var13 ^ var10007);
      }

      b = (new String(var14)).intern();
   }
}

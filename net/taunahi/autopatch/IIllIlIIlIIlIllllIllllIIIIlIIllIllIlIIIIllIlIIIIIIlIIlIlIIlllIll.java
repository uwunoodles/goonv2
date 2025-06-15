package net.taunahi.autopatch;

import kotlin.TypeCastException;

final class IIllIlIIlIIlIllllIllllIIIIlIIllIllIlIIIIllIlIIIIIIlIIlIlIIlllIll {
   private static final String a;
   private static final String[] O;

   private IIllIlIIlIIlIllllIllllIIIIlIIllIllIlIIIIllIlIIIIIIlIIlIlIIlllIll() {
   }

   protected final Object clone() {
      return a;
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      return IIllIlIIlIIlIllllIllllIIIIlIIllIllIlIIIIllIlIIIIIIlIIlIlIIlllIll.a;
   }

   IIllIlIIlIIlIllllIllllIIIIlIIllIllIlIIIIllIlIIIIIIlIIlIlIIlllIll(IIlIIIlIIlIIlIIIIlllIIlIIllIlIIIIIIIlllIlIlIIIIlIIlIIIllIIIIIlII a1) {
      this();
   }

   private static TypeCastException a(TypeCastException var0) {
      return var0;
   }

   static {
      char[] var0 = "໐\u0e61M]\tx\u0004V'T\u0e6b]y\u0e6f\u0016P\u001c>Y\u0002\u0e6b]V\u0e6ds>X\u000b\u0e6bT]\u0e6bX]\u0e6e\\\r\u0002\u0005S\u001e\u001e".toCharArray();
      int var1 = 31 >>> Integer.parseInt("2b", 29);
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[126 << 6303]).getMethodName().hashCode() & ("1".hashCode() ^ -1424851853) - ("76".hashCode() ^ -1424917860);
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = Integer.parseInt("1l", 30) >>> 4198;
      ++var1;
      String[] var5 = new String[var0[var10001] ^ Integer.parseInt("378e", 33) >>> 4554 ^ var3];
      int var6 = 124 >>> Integer.parseInt("26b", 30);

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ 1536 >>> 15267 ^ var3];
         int var8 = 94 << ("42".hashCode() ^ 9537);

         while(var11 > 0) {
            int var9 = var0[var1];
            switch(var4[var1 % var4.length] ^ 63 << 2529) {
            case 10:
            case 11:
            case 17:
            case 55:
               var9 ^= ("36".hashCode() ^ 394851) >>> 11692;
               break;
            case 14:
            case 18:
            case 23:
            case 29:
            case 80:
               var9 ^= 901120 >>> ("63".hashCode() ^ 3184);
               break;
            case 16:
            case 22:
               var9 ^= 26112 >>> ("32".hashCode() ^ 1750);
               break;
            case 27:
               var9 ^= ("48".hashCode() ^ 1641) << 6368;
               break;
            case 31:
               var9 ^= 18874368 >>> 14002;
            }

            var7[var8] = (char)var9;

            try {
               while(true) {
                  ++var8;
                  ++var1;
                  --var11;
               }
            } catch (Exception var10) {
            }
         }

         var5[var6++] = (new String(var7)).intern();
      } while(var1 < var0.length);

      O = var5;
      int var10000 = (451686751 ^ 449950047) >>> 1451134575 - Integer.parseInt(O[Integer.parseInt("7", 32) << 5952], 29);
      char[] var10002 = O[16 >>> 8226].toCharArray();
      int var10003 = var10002.length;
      int var13 = var10000;
      char[] var14 = var10002;
      var10000 = var10003;

      for(var1 = 0; var10000 > var1; ++var1) {
         char var10005 = var14[var1];
         int var10007;
         switch(var1 % (458752 >>> (O[3 << 7616].hashCode() ^ 15315) << (332800 >>> 4293)) ^ (2000807584 >>> 4833 ^ 627004376 + 1837302014) >>> (-1146203111 ^ -352493046) - (O[2 >>> 12065].hashCode() ^ -710759472 ^ -2064544483)) {
         case -1455249534:
            var10007 = 130 << 3457 >>> (2061163897 ^ 2061157691);
            break;
         case -1455249533:
            var10007 = 1703279170 - 998636098 >>> (O[41943040 >>> 7607].hashCode() ^ -1504879522) - -1504895958;
            break;
         case -1455249532:
            var10007 = 47 << 1024 << (-773380741 ^ O[3145728 >>> 14067].hashCode() ^ -773388096);
            break;
         case -1455249531:
            var10007 = 1912602624 >>> 10662 >>> (6729 << 6337);
            break;
         case -1455249530:
            var10007 = 536870912 >>> 6875 << 1041112502 - Integer.parseInt(O[1 << Integer.parseInt("2l3", 30)], 32);
            break;
         case -1455249529:
            var10007 = Integer.parseInt(O[66 << 11423], 26) >>> 9850 << -955563494 + 955564167;
            break;
         default:
            var10007 = 327680 >>> 10346 >>> 772586932 - 772573358;
         }

         var14[var1] = (char)(var10005 ^ var13 ^ var10007);
      }

      a = (new String(var14)).intern();
   }
}

package net.taunahi.autopatch;

import java.io.StringWriter;
import kotlin.TypeCastException;

public class lIIlllIlIlllIllIIIlIllIIlllllIlIIIIIIIllIllllIlIIllIlIlllIllIIll extends lIIIlIIllIlIllIlIIlIlIIllllIIIIlIIlIIIlIIIlllIIlIlIlIllIllIlIIlI {
   private static final String[] x;

   public lIIlllIlIlllIllIIIlIllIIlllllIlIIIIIIIllIllllIlIIllIlIlllIllIIll() {
      super(new StringWriter());
   }

   public String toString() {
      String var10000;
      try {
         if (a.llllIIlIIlIIIIIIIIlIlllIIIIlIIlllIIlIllIlIIlIllIIIlIllIllllllIIl == 819200 >>> Integer.parseInt(x[("11".hashCode() ^ 1569) >>> 14817], 26) << -465511590 - -465512104) {
            var10000 = a.llIllIlllIllIlIlIIlIIIlIIllIlIIIllIllIIlllIIlIlIIIIlllIIlIlIlIII.toString();
            return var10000;
         }
      } catch (TypeCastException var1) {
         throw a(var1);
      }

      var10000 = null;
      return var10000;
   }

   private static TypeCastException a(TypeCastException var0) {
      return var0;
   }

   static {
      char[] var0 = "๕\u0ee6fv=".toCharArray();
      int var1 = 102 << 15551;
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[4 >>> Integer.parseInt("cnw", 33)]).getMethodName().hashCode() & (Integer.parseInt("-1598bq0", 30) ^ -858043481);
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = 34 << 3519;
      ++var1;
      String[] var5 = new String[var0[var10001] ^ -1863056157 + 1863056410 ^ var3];
      int var6 = 112 << 2268;

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ ("69".hashCode() ^ 157379) >>> 459 ^ var3];
         int var8 = 78 << 15487;

         while(var11 > 0) {
            int var9 = var0[var1];
            switch(var4[var1 % var4.length] ^ 42 << 7777) {
            case 29:
            case 55:
            case 59:
            case 60:
            case 61:
            case 122:
               var9 ^= 13 << 6657;
               break;
            case 32:
               var9 ^= 6 << 6305;
               break;
            case 33:
            case 36:
            case 56:
            case 58:
               var9 ^= 687865856 >>> 15254;
               break;
            case 49:
               var9 ^= Integer.parseInt("3", 20) << 2816;
               break;
            case 53:
               var9 ^= 902094423 - 902094214;
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

               var9 ^= 13 << 6657;
            }
         }

         var5[var6++] = (new String(var7)).intern();
      } while(var1 < var0.length);

      x = var5;
   }
}

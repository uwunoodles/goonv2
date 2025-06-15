package net.taunahi.autopatch;

import dev.jnic.Keeqam.JNICLoader;
import java.io.File;
import java.util.List;
import kotlin.TypeCastException;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class TaunahiTweaker implements ITweaker {
   public static final boolean lllIlIIIIIIIlIIlIIlIIIllIIIlIlIIlIllIllIIIIIlIIIlIlllIIIIIIlIIll;
   public static final int lIIIIIIIIlIIlIIlIIlIIlllIIIlIlIllIllIllIIIIIlIIIlIlllIIIIIIlIIlI;
   private static final String[] a;
   private static final String[] p;

   private native void lIIllIlIIIIlIIIIIlIlIlllIlIIllIIlIllIllIIlIIllIllIIIllIlIlIllIIl(Object[] var1);

   public native void acceptOptions(List<String> var1, File var2, File var3, String var4);

   public native void injectIntoClassLoader(LaunchClassLoader var1);

   public String getLaunchTarget() {
      return null;
   }

   public String[] getLaunchArguments() {
      return new String[0];
   }

   private static TypeCastException a(TypeCastException var0) {
      return var0;
   }

   // $FF: synthetic method
   static void $jnicClinit() {
      String[] var5 = new String[128 >>> 10307 >>> (p[128 >>> ("5".hashCode() ^ 13778)].hashCode() ^ 189003197) + -188990365];
      int var3 = 0;
      String var2;
      int var4 = (var2 = p[Integer.parseInt("45l", 27) >>> 5704]).length();
      int var1 = 67108864 >>> 11354 << (84496 >>> 1603);
      int var0 = -1 >>> 15232 >>> -1474542989 + Integer.parseInt(p[Integer.parseInt("5", 27) << 9697], 24);

      label43:
      while(true) {
         int var10000 = 47 << 16128 << 350159953 + -350147089;
         ++var0;
         String var10001 = var2.substring(var0, var0 + var1);
         int var10002 = Integer.parseInt(p[2 << Integer.parseInt("4jg", 31)], 26) >>> Integer.parseInt(p[5 << 13344], 23) << -122138795 - -122140171;

         while(true) {
            char[] var11 = var10001.toCharArray();
            int var10004 = var11.length;
            int var10003 = var10000;
            char[] var10 = var11;
            int var7 = var10004;

            for(int var6 = 0; var7 > var6; ++var6) {
               char var10006 = var10[var6];
               int var10008;
               switch(var6 % (-1866949699 + 1866951491 >>> -791719364 + 791721228) ^ (p[15 << Integer.parseInt("f04", 30)].hashCode() ^ 478054537) - 543734053 + ((p['退' >>> Integer.parseInt("k77", 28)].hashCode() ^ -1409266853) << 10272) >>> (1213440 >>> 12874 << (3280 << 4865))) {
               case 1410011488:
                  var10008 = 402653184 << 2274 >>> (573 << 2944);
                  break;
               case 1410011489:
                  var10008 = 6094848 << 7333 >>> (949325470 ^ 949326571);
                  break;
               case 1410011492:
                  var10008 = 11 << 4384 << (21630976 >>> (p[7 << 10977].hashCode() ^ 3369));
                  break;
               case 1410011493:
                  var10008 = 448 >>> 7526 << 1696536930 + -1696527359;
                  break;
               case 1410011494:
                  var10008 = 55 << 4896 << (6448 << (p[10 << 16033].hashCode() ^ 16097));
                  break;
               case 1410011495:
                  var10008 = -1704609101 - (p[("62".hashCode() ^ 1727) << 12768].hashCode() ^ -1704612598) >>> (11589 << 13664);
                  break;
               default:
                  var10008 = 9472 >>> 14504 << Integer.parseInt(p[17 << 8448], 35) - Integer.parseInt(p[9 << 4800], 27);
               }

               var10[var6] = (char)(var10006 ^ var10003 ^ var10008);
            }

            String var12 = (new String(var10)).intern();
            switch(var10002 ^ 1744682517 - -94027963 ^ Integer.parseInt(p[("39".hashCode() ^ 1643) << ("77".hashCode() ^ 13728)], 25) ^ Integer.parseInt(p[256 >>> 14183], 29) ^ (-2116621532 >>> 9826) + (-567768591 >>> 3584)) {
            case 1193658322:
               var5[var3++] = var12;
               if ((var0 += var1) >= var4) {
                  a = var5;
                  return;
               }

               var1 = var2.charAt(var0);
               break;
            default:
               var5[var3++] = var12;
               if ((var0 += var1) < var4) {
                  var1 = var2.charAt(var0);
                  continue label43;
               }

               var4 = (var2 = p[38 >>> 12806]).length();
               var1 = 9 << (p[3 << 1473].hashCode() ^ 10751) << (Integer.parseInt(p[1216 >>> 6630], 20) >>> 6505);
               var0 = -1 << 13472 >>> Integer.parseInt(p[("45".hashCode() ^ 30337) >>> 6764], 30) + (p[("82".hashCode() ^ 5882) >>> 3465].hashCode() ^ -74613134);
            }

            var10000 = 148 << (p[2 << 2403].hashCode() ^ 165) >>> (Integer.parseInt(p[Integer.parseInt("1834", 20) >>> 12234], 23) >>> 15974);
            ++var0;
            var10001 = var2.substring(var0, var0 + var1);
            var10002 = 0;
         }
      }
   }

   // $FF: synthetic method
   public static native void $jnicLoader();

   static {
      char[] var0 = "ດ๙;În.PV\u001f$°ä]\u001aq,]QUf\u0011·Ö\u0019\u001e\u001bh\u001a\u008dÄ็ºIใ³\u0012\u0016äKÄไD็WL็¹\u009f็\u001b\u0014ใ\u0010O\u0010\u001b@\u0094็J\u0011โI\u0013ãº\u0010ãJโ¼\u0015N²@ÇOเ\u0010\u001fJF¹ໃ\u0004u\u000e^6#tWOíÆt]9WU\u009e\u0087kù'½áf\"\u0097?¨tWOa0*õµWU6/\u0012\u000boPZìÎ8`4[Z\u0092Âj®?Ü©\u0007dÂf\u0095u\u001eWg0\"¼©QIqn(JoPPêÈoZcL^\u0098Ôwòj\u0084Ð+X÷8´!JiM\u00046Â³X;r{\t\t45f\u0095Ý\u001eIm%4õ×Hö/ã¢&yèํÛ\u0011KI\u0016NB¸็L\u0015็\u0016\u0015็\u0010Jใ\u0082å\rU\u0016\f็¾ºใº\u0017îèD\u0019็\u0012Ç".toCharArray();
      int var1 = 3 >>> 7394;
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[7 >>> ("64".hashCode() ^ 93)]).getMethodName().hashCode() & 2147450880 >>> 15503;
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = 51 >>> 166;
      ++var1;
      String[] var5 = new String[var0[var10001] ^ 20971520 >>> 5779 ^ var3];
      int var6 = ("25".hashCode() ^ 1621) << Integer.parseInt("lp1", 26);

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ -1808411685 - -1808411921 ^ var3];
         int var8 = 48 >>> 1478;

         while(var11 > 0) {
            int var9 = var0[var1];
            switch(var4[var1 % var4.length] ^ 19 << Integer.parseInt("8ji", 26)) {
            case 61:
               var9 ^= "63".hashCode() ^ -1216271413 ^ -1216270976;
               break;
            case 71:
            case 118:
            case 120:
            case 122:
               var9 ^= 139 << 14016;
               break;
            case 97:
            case 100:
            case 112:
            case 114:
               var9 ^= 125 << 15200;
               break;
            case 99:
            case 102:
            case 103:
            case 123:
            case 124:
               var9 ^= 35 << ("26".hashCode() ^ 15268);
               break;
            case 125:
               var9 ^= 31232 >>> 7432;
            }

            var7[var8] = (char)var9;

            try {
               ++var8;
               ++var1;

               while(true) {
                  --var11;
               }
            } catch (RuntimeException var10) {
            }
         }

         var5[var6++] = (new String(var7)).intern();
      } while(var1 < var0.length);

      p = var5;
      lIIIIIIIIlIIlIIlIIlIIlllIIIlIlIllIllIllIIIIIlIIIlIlllIIIIIIlIIlI = 1;
      lllIlIIIIIIIlIIlIIlIIIllIIIlIlIIlIllIllIIIIIlIIIlIlllIIIIIIlIIll = false;
      JNICLoader.init();
      $jnicLoader();
      $jnicClinit();
   }
}

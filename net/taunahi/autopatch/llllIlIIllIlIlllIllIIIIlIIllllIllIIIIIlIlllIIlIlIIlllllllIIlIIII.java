package net.taunahi.autopatch;

import dev.jnic.Keeqam.JNICLoader;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.File;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.taunahi.autopatch.utils.lIIIlIlIIlllIllllIIlIlllIlIllllIllIlllIIIllIIIllllIllIIllIIlIllI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class llllIlIIllIlIlllIllIIIIlIIllllIllIIIIIlIlllIIlIlIIlllllllIIlIIII {
   @NotNull
   public static final llllIlIIllIlIlllIllIIIIlIIllllIllIIIIIlIlllIIlIlIIlllllllIIlIIII IIlIIlIlIIIIIIlIIllIllIlIllIlIIIIIIllIIIllIIlIIIIIlIIlllIlllIlll;
   private static boolean llIlllllIIlIIlIlllIIIllllllllllllllIIllIllllIllIIlIlIIIIIIlIllll;
   @Nullable
   private static TrayIcon llIlIIIIlIIIlIllIllIIIlIIIlIllllIlIIIlllIlllIIllIlIIllIlIIIIIlll;
   @Nullable
   private static SystemTray lIllIIIIllIIllIlIIlllIlllIlIlIlIlIlIIIIlIlIlIIIllIIlIllIIllIlIII;
   @NotNull
   private static final File lllIlIlIlIlIlIlIIlIIIIIIlIIIIlIlIIIlllIlIIllIllIlIIllllIlIlIIIlI;
   private static final String[] a;
   private static final String[] k;

   private llllIlIIllIlIlllIllIIIIlIIllllIllIIIIIlIlllIIlIlIIlllllllIIlIIII() {
   }

   @JvmStatic
   public static final native void IlllllIllIIlIllIlllIIlIIlIIlIlIIIlIllIlIIIIIIIlllIlIlllIIIIIllII(Object[] var0);

   @JvmStatic
   public static final void llIllIIIllllIlIlIlIllIllIIllIIIIlllIlllIIIIIIIIlIlIlIIIIIllllIII(@NotNull MessageType param0, @NotNull String param1) {
      // $FF: Couldn't be decompiled
   }

   @JvmStatic
   public static final void lIIIlIIlllIlIIIIlIlIlIlIIIIIIIIlIIllIIllIllIlIlIIlIIIllIlIIllIll(Object[] param0) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static void $jnicClinit() {
      String[] var5 = new String[14680064 >>> 9173 << (16777216 >>> Integer.parseInt(k[("7".hashCode() ^ 60) << 10944], 24))];
      int var3 = 0;
      String var2;
      int var4 = (var2 = k[21 << ("76".hashCode() ^ 6655)]).length();
      int var1 = 37 << 16128 << (k[640 >>> ("42".hashCode() ^ 184)].hashCode() ^ -888957086 ^ -888949758);
      int var0 = (k[19 << 8928].hashCode() ^ -1637) << 5536 << (785095340 ^ 785101484);

      label43:
      while(true) {
         int var10000 = 503316480 >>> 7585 >>> (Integer.parseInt(k[8388608 >>> 467], 24) << 10882);
         ++var0;
         String var10001 = var2.substring(var0, var0 + var1);
         int var10002 = -1 << 8032 >>> (4192 << 10849);

         while(true) {
            char[] var13 = var10001.toCharArray();
            int var10004 = var13.length;
            int var10003 = var10000;
            char[] var12 = var13;
            int var9 = var10004;

            for(int var6 = 0; var9 > var6; ++var6) {
               char var10006 = var12[var6];
               int var10008;
               switch(var6 % (Integer.parseInt(k[24117248 >>> Integer.parseInt("830", 28)], 24) << 545 >>> (48594944 >>> 3950)) ^ (-352436279 ^ 1906009276) - ((k[5 << 7264].hashCode() ^ -734640628) >>> Integer.parseInt(k[1280 >>> 1222], 33)) ^ (-1898089115 ^ 247974267) + (-1618224320 - -1369363359)) {
               case 1074745840:
                  var10008 = (k[9 << 10497].hashCode() ^ 1261) >>> 16196 << (26372 >>> Integer.parseInt(k[30 << 11487], 21));
                  break;
               case 1074745841:
                  var10008 = 1342177280 >>> (k[Integer.parseInt("3", 34) << 13248].hashCode() ^ 1153) >>> (396165120 >>> 9199);
                  break;
               case 1074745842:
                  var10008 = (-215388480 ^ Integer.parseInt(k[360448 >>> 15982], 31)) >>> (90176 >>> 1765);
                  break;
               case 1074745843:
                  var10008 = 553648128 >>> 5665 >>> (819401874 ^ 819401989);
                  break;
               case 1074745844:
                  var10008 = (978327336 ^ 865081128) >>> (3611 << 1569);
                  break;
               case 1074745845:
                  var10008 = 14023708 + -14023500 >>> 435476133 - 435471332;
                  break;
               default:
                  var10008 = 12544 << 13674 >>> (Integer.parseInt(k[32 >>> 9829], 22) >>> (k[15 << 3808].hashCode() ^ 9966));
               }

               var12[var6] = (char)(var10006 ^ var10003 ^ var10008);
            }

            String var14 = (new String(var12)).intern();
            switch(var10002 ^ (741300330 - Integer.parseInt(k[128 >>> 4453], 34) ^ (k[1 << 545].hashCode() ^ 756887694) + 327741067) >>> 555069314 + 270019217 + ((k[224 >>> 3172].hashCode() ^ 867471709) << 13698)) {
            case 356677052:
               var5[var3++] = var14;
               if ((var0 += var1) >= var4) {
                  a = var5;
                  IIlIIlIlIIIIIIlIIllIllIlIllIlIIIIIIllIIIllIIlIIIIIlIIlllIlllIlll = new llllIlIIllIlIlllIllIIIIlIIllllIllIIIIIlIlllIIlIlIIlllllllIIlIIII();
                  String[] var7 = a;
                  File var8 = lIIIlIlIIlllIllllIIlIlllIlIllllIllIlllIIIllIIIllllIllIIllIIlIllI.IlIIlIIIllllIIlIIlllIllllIIllIllllIlIIIlllIlIIIIlllllIlIllIlIIll(new Object[]{var7[0]});
                  Intrinsics.checkNotNullExpressionValue(var8, var7[(1488574052 ^ 1488578148) >>> (3094 << Integer.parseInt(k[144 >>> 7940], 25))]);
                  lllIlIlIlIlIlIlIIlIIIIIIlIIIIlIlIIIlllIlIIllIllIlIIllllIlIlIIIlI = var8;
                  return;
               }

               var1 = var2.charAt(var0);
               break;
            default:
               var5[var3++] = var14;
               if ((var0 += var1) < var4) {
                  var1 = var2.charAt(var0);
                  continue label43;
               }

               var4 = (var2 = k[128 >>> ("55".hashCode() ^ 9476)]).length();
               var1 = Integer.parseInt(k[("91".hashCode() ^ '\ue718') >>> ("87".hashCode() ^ 3730)], 22) << Integer.parseInt(k[17 << 7264], 31) << ((k[13 << 16128].hashCode() ^ 8358) >>> 993);
               var0 = -1 >>> (k[3 << 11265].hashCode() ^ 8419) << (925652608 ^ 925655968);
            }

            var10000 = 57 << 9248 << -1802615738 + 1802619226;
            ++var0;
            var10001 = var2.substring(var0, var0 + var1);
            var10002 = 0;
         }
      }
   }

   private static TypeCastException a(TypeCastException var0) {
      return var0;
   }

   // $FF: synthetic method
   public static native void $jnicLoader();

   static {
      char[] var0 = "\u0e70\u0e6c\u001bÐ\u0017\u0e67wá2>i\u00852h\u0e6e\u0085\u0e6dkJ\u0e695\u0087\u0081Ú\u0081Ý\u0e6d\u0086à\u0e6d\u0087\u0088\u0e6e\u0080\u0e76ù»Ù³¥ÙÇ¸¸åÇ´Éï\u0089ó\u0095¿Ö ö¬¢ç£\u0e6c»\u0083\u0089\u0e6dçç\u0e6c¿\u0082ã\u0e6a\u0083\u0081ÜÛÃ\u0e6d\u0082à\u0e6d\u0087â\u0e6díà\u0e6c\u0097\u0016\u0087\u0e6cc0´\u0e6dli\u0e6dim\u0e6c\u0089=cໍ=vñáµëî¹\u0080Ô\u0094·ì\u0085Úøö«µ\u009cìÓõÔ\u0094Àø\u0092\u0096\u0099þã³ÓÿÕ÷û·ä\u008fôÁ£ \u0094ñ\u0088÷×í\u0098ñ\u0080þñ¸øçÒ¾\u0095áÆÒ\u0088³ùí%¢y\r3ÿ\u001aB\f«\\~Kñ\b]\u0019z\u0007÷·þê\u0081¸\u0091\u008f¦¿õ\u008e\u0093²òí¸\u0098ìÉñ\u009d\u0094\u009dí\u008aª\u009aþì¿ÒìÚó\u0093\u0085\u009d\u0089ý¾\u008dÖéÓ\u009aêñ\u0084\u0095\u0087ô°¥ï\u0095êêÓ\u0095æÿ½Û¡Çú/\u0096k\u0015\u001bÑ\u001a\u0e68w\u0086=l\u000bÝ9\u0e69O1oÜ\u0084\u0089".toCharArray();
      int var1 = 48 << 15292;
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[16 >>> 3461]).getMethodName().hashCode() & ("58".hashCode() ^ 1073727139) >>> 5614;
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = 53 >>> 14598;
      ++var1;
      String[] var5 = new String[var0[var10001] ^ 193 << 13024 ^ var3];
      int var6 = Integer.parseInt("52", 20) >>> 2855;

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ 1660944384 >>> ("92".hashCode() ^ 1838) ^ var3];
         int var8 = 60 << ("71".hashCode() ^ 9860);

         while(var11 > 0) {
            int var9 = var0[var1];
            switch(var4[var1 % var4.length] ^ 167772160 >>> 5080) {
            case 36:
            case 101:
            case 102:
               var9 ^= ("21".hashCode() ^ 1678) << 12384;
               break;
            case 67:
            case 100:
               var9 ^= -1079592946 + Integer.parseInt("gh792hj", 20);
               break;
            case 98:
            case 99:
            case 105:
            case 107:
            case 122:
            case 127:
               var9 ^= 1509949440 >>> 7928;
               break;
            case 111:
               var9 ^= 339968 >>> Integer.parseInt("ebi", 33);
               break;
            case 126:
               var9 ^= Integer.parseInt("1s", 35) << 3105;
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
            }
         }

         var5[var6++] = (new String(var7)).intern();
      } while(var1 < var0.length);

      k = var5;
      JNICLoader.init();
      $jnicLoader();
      $jnicClinit();
   }
}

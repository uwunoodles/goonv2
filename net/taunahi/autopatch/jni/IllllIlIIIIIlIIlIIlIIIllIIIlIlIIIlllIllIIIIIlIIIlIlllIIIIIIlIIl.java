package net.taunahi.autopatch.jni;

import dev.jnic.Keeqam.JNICLoader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.taunahi.autopatch.utils.IIIIlIlIlllIIlIlllllllIllllIIIIIllllllIllllIlIlIlIIIllIIlIIIllII;

public class IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl {
   public static char lllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIll;
   public static String IIlllllIIlIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIIIlI;
   public static String lIIllllIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIlI;
   private static final String[] a;
   private static final String[] l;

   public static native void llIllllIIlIIIlIlIIIlIlIllllIlIlIIlIlIIllIIllllIllIlIIllIIIIllIll(Object[] var0);

   private static void IIllIlIIlllllIlIIlllllIIIIIllIlIlllIIlllllIllIlIlIlIlllIlIlIllIl(Object[] a) {
      StringBuilder var10000;
      Class a;
      String[] var4;
      try {
         var4 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         a = Class.forName(var4[(-867912019 ^ -868141395) >>> (1566507683 ^ 1566514892)]);
      } catch (ClassNotFoundException var7) {
         var10000 = new StringBuilder();
         var4 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         IIIIlIlIlllIIlIlllllllIllllIIIIIllllllIllllIlIlIlIIIllIIlIIIllII.IIllIIIllllIIIIllllIlIlIIlIllIllIIIIlllllIIIlllllllllIIIllIllIII(var10000.append(var4[(l[11 << 11553].hashCode() ^ 1756) << (l[536870912 >>> 8061].hashCode() ^ 7601) >>> ((l[3200 >>> 7].hashCode() ^ 473624) >>> (l[1 << ("96".hashCode() ^ 4221)].hashCode() ^ 6622))]).append(var7.getMessage()).toString());
         IIlllllIIlIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIIIlI = var4[536870912 >>> 11420 << ((l[13 << 13184].hashCode() ^ 14398) << (l[3 << 14401].hashCode() ^ 15192))];
         return;
      }

      Method a;
      try {
         var4 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         a = a.getDeclaredMethod(var4[1568794477 - 495052653 >>> (l[107 >>> Integer.parseInt("4ll", 26)].hashCode() ^ 2062695967 ^ 2062696339)]);
      } catch (NoSuchMethodException var6) {
         var10000 = new StringBuilder();
         var4 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         IIIIlIlIlllIIlIlllllllIllllIIIIIllllllIllllIlIlIlIIIllIIlIIIllII.IIllIIIllllIIIIllllIlIlIIlIllIllIIIIlllllIIIlllllllllIIIllIllIII(var10000.append(var4[393216 << Integer.parseInt(l[10 << Integer.parseInt("a97", 34)], 27) >>> (-904740083 ^ -904724809)]).append(var6.getMessage()).toString());
         IIlllllIIlIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIIIlI = var4[0];
         return;
      }

      try {
         a.invoke((Object)null);
      } catch (InvocationTargetException | IllegalAccessException var5) {
         var5.printStackTrace();
         var4 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         IIIIlIlIlllIIlIlllllllIllllIIIIIllllllIllllIlIlIlIIIllIIlIIIllII.IlllllIIIlllIIlIIlIllllllIIllIIIlIIIllIIIlIllIlIIllllIIllIlllllI(var4[(2120244497 ^ 1314938129) >>> 2028792779 + (l[13312 >>> 12137].hashCode() ^ -2028778834)], var5);
         IIlllllIIlIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIIIlI = var4[448 >>> Integer.parseInt(l[9 << 14753], 34) << (-104494670 ^ -104485517)];
      }

   }

   private static void llllIllIIIllIlIlllIIllllIlIIlIIIIlIllIllIllIIIIIIIlIIlIlIIllIIII(Object[] a) {
      LaunchClassLoader a = Launch.classLoader;
      ClassLoader a = Launch.classLoader.getClass().getClassLoader();

      StringBuilder var10000;
      URL a;
      String[] var6;
      try {
         a = IllIlIIIIlllIIlIlIlIIlIlllIlIIlllIlllIllllllIIIlIIIlIIllllllIlll.lIlIIIIIllIIlIllIllIIllIIlIIIlIllIllIlIIIIlIIllIlllllIlIllllIllI(new Object[0]).toURI().toURL();
      } catch (Exception var9) {
         var10000 = new StringBuilder();
         var6 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         IIIIlIlIlllIIlIlllllllIllllIIIIIllllllIllllIlIlIlIIIllIIlIIIllII.IIllIIIllllIIIIllllIlIlIIlIllIllIIIIlllllIIIlllllllllIIIllIllIII(var10000.append(var6[40 >>> 10659 << (1417472 >>> Integer.parseInt(l[11 << 14560], 26))]).append(var9.getMessage()).toString());
         IIlllllIIlIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIIIlI = var6[7 << 10688 << (l[5120 >>> 745].hashCode() ^ -834932216 ^ -834930392)];
         return;
      }

      a.addURL(a);

      Method a;
      try {
         var6 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         a = URLClassLoader.class.getDeclaredMethod(var6[96 >>> 16260 << (Integer.parseInt(l[4096 >>> 2731], 25) >>> 15439)], URL.class);
      } catch (Exception var8) {
         var10000 = new StringBuilder();
         var6 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         IIIIlIlIlllIIlIlllllllIllllIIIIIllllllIllllIlIlIlIIIllIIlIIIllII.IIllIIIllllIIIIllllIlIlIIlIllIllIIIIlllllIIIlllllllllIIIllIllIII(var10000.append(var6[1664 >>> (l[17 << 7392].hashCode() ^ 4226) << 2089109053 + Integer.parseInt(l[736 >>> 9605], 35)]).append(var8.getMessage()).toString());
         IIlllllIIlIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIIIlI = var6[9 << (l[4 << 16290].hashCode() ^ 6211) << -1294783599 - Integer.parseInt(l[3670016 >>> 11475], 30)];
         return;
      }

      a.setAccessible(true);

      try {
         a.invoke(a, a);
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var7) {
         var10000 = new StringBuilder();
         var6 = IllllIlIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIl.a;
         IIIIlIlIlllIIlIlllllllIllllIIIIIllllllIllllIlIlIlIIIllIIlIIIllII.IIllIIIllllIIIIllllIlIlIIlIllIllIIIIlllllIIIlllllllllIIIllIllIII(var10000.append(var6[1]).append(var7.getMessage()).toString());
         IIlllllIIlIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIIIlI = var6[5 << 13632 << -1204701780 - -1204707668];
      }

   }

   private static native void IlIIlIlIlllIlIIlIIlIIIllllllllllIlllIllIIIIIIIIIIIlIlIIIIIIlIIII(String var0);

   private static native void IlIIlIlIlllIlIIlIIlIIIllllllllllIlllIllIIIIIIIIIlIlllIIlIIIllIII(String var0);

   // $FF: synthetic method
   static void $jnicClinit() {
      String[] var5 = new String[15 << 8832 << (247808 >>> 7748)];
      int var3 = 0;
      String var2;
      int var4 = (var2 = l[6 << 2145]).length();
      int var1 = (l[4 << 15777].hashCode() ^ 190111) >>> 4489 >>> -1984961223 - -1984961739;
      int var0 = -1 << 13600 >>> 1180297194 + (l[27 << 5344].hashCode() ^ -1180283480);

      label43:
      while(true) {
         int var10000 = (l[4 << 12033].hashCode() ^ 1726) << 13504 << ('騈' >>> 3587);
         ++var0;
         String var10001 = var2.substring(var0, var0 + var1);
         int var10002 = (l[1 << 11938].hashCode() ^ -1569) << 9088 << 1594598541 + -1594582989;

         while(true) {
            char[] var11 = var10001.toCharArray();
            int var10004 = var11.length;
            int var10003 = var10000;
            char[] var10 = var11;
            int var7 = var10004;

            for(int var6 = 0; var7 > var6; ++var6) {
               char var10006 = var10[var6];
               int var10008;
               switch(var6 % (Integer.parseInt(l[251658240 >>> 6264], 25) + (l[7 << Integer.parseInt("7jh", 27)].hashCode() ^ -1564290085) >>> (1073987957 ^ Integer.parseInt(l[9 << 12672], 26))) ^ (l[2 << 7714].hashCode() ^ 896233690 ^ 641989737 ^ 655117835 + -623026166) - (-1222765330 >>> 4800 >>> (1803550720 >>> 2482))) {
               case 1537412936:
                  var10008 = 55 << 8800 << (1761 << 7200);
                  break;
               case 1537412937:
                  var10008 = 127 << 13600 << -1108838931 + 1108847795;
                  break;
               case 1537412938:
                  var10008 = 10 << 4738 << -83387342 - -83388431;
                  break;
               case 1537412939:
                  var10008 = 18 << 1697 >>> Integer.parseInt(l[655360 >>> 4241], 31) + -185222450;
                  break;
               case 1537412942:
                  var10008 = 42 << 2918 >>> (l[2688 >>> 39].hashCode() ^ 314188054) + -314188238;
                  break;
               case 1537412943:
                  var10008 = 757099948 - (l[("8".hashCode() ^ 59) << 12192].hashCode() ^ 750217997) >>> 2144739727 + -2144728671;
                  break;
               default:
                  var10008 = -704643072 >>> 1826 >>> (6839 << 12320);
               }

               var10[var6] = (char)(var10006 ^ var10003 ^ var10008);
            }

            String var12 = (new String(var10)).intern();
            switch(var10002 ^ (-1667007111 ^ -464556739) - (-1432588786 - -1221937444) + (433318698 ^ 2087078729) + (1088832610 << 2945)) {
            case 1828487737:
               var5[var3++] = var12;
               if ((var0 += var1) >= var4) {
                  a = var5;
                  IIlllllIIlIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIIIlI = "";
                  lIIllllIIIIIlIIlIIlIIIllIIIlIlIIIlllIllIIIIIlIIIlIlllIIIIIIlIIlI = "";
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

               var4 = (var2 = l[19 << 6400]).length();
               var1 = 1946157056 >>> 3489 >>> -848250715 - Integer.parseInt(l[-1073741824 >>> 16251], 33);
               var0 = -1 << 10912 << 1374363851 - 1374351499;
            }

            var10000 = 7680 >>> 329 << (10625 << 448);
            ++var0;
            var10001 = var2.substring(var0, var0 + var1);
            var10002 = 0;
         }
      }
   }

   // $FF: synthetic method
   public static native void $jnicLoader();

   static {
      char[] var0 = "ั\u0e3e\f\u0e3d\u0005\u008eุqÓÑ\u009a\u009eÔÓ\u0e3d\u0088Î\u0e3d\f\u008cูÎØ(\u0094\u0099Ô\u0e3e\u0085ื\u0090\u008cÓ\u0085ÛÙ\u0085\u008e\u0e3d\u0088\u0089ุ\u008eÞÜ\u008b\u008eÚ\u008d\u0e3d\u0089\u0089\u0e3c\u008bÖ\u0085།·Úôþæ\u008bà\u0095Ç üó\u009eü\u0099Ë øå\u0099á\u0093\u0081\u009d°Îéýó\u008e´\u0082=\u0095x½\u001cû\u009d7 ð·ËÁ¤c ¹ó\u001eü\u0099\u008eºLüÚÁéå¡°Îéýó\u008e´\u0082À øø\u009cû\u009dÊ øø\u0083àÖÂååþ\u0085ð©·Úôþæ\u008bà\u0095Ç ÿù\u009e´\u0090ÀõÿòÄ££ýÌÒú\u008bç\u0085ã\u0012ÅrÊfÖÆ\u000eâãÊñÞÆnç¼\u0081qß\u0081ä°3¬¸ó\u008e´\u0082À ÷ÿ\u0084ðÖÆîøâÊù\u0093ÛèþòÐ´®£ýÌ±ñ\u0098õ\u0094ÍéÿñÊý\u0085Üõô¸¦°Îéýó\u008e´\u0082À ÷ÿ\u0084ðÖâáø\u0005ÿwßNóâQÊ¨æ¸ÌÒz\u008b¢\u0085cïð·\u008f\u001b\u0093\u0083óâã\u008fº¥°Îéýó\u008e´\u0082À öó\u009e´\u0090Æìô¶¿Æº\u0095 \u009d\u0098Êô¿â\u008bá\u0098Îèø¸\u0084õ\u0082Æöô÷\u009fà\u0099ßáåõ\u0082º»ÎéÿF¢K¡DÄ¦\u0e3d\u008c\u008d\u0e3dÏ\u0085ุ\u000bÜÌØW\u008aÔ\u0e3d\u0089w\u0e3dË\u0085\u0e3c\u008eÐØฉì\u0092µ¡¯ÒèÞ\u009cüª¯ÂèË\u0097¸\u0098\u0098úèÇ\u0096¨¥¥Òò\u008a¥ë\u0086¨¢º×¼É\u009bü¸¤×ªÆ\u0096ü¹¥\u0096ºß\u009dò\u0e3cÙÖy\u0e3d\u000fÍ\u0e3d\u008a\u0088ื\u0090\u008cÌ\u008a\u0088Þ\bßุ\u0090QÐ\u0085\u008d\u00852\u0e3dÁ\u008c\u0e3d\u008e\u008f\u0e3d\u008e\u008c".toCharArray();
      int var1 = ("70".hashCode() ^ 1691) >>> 13959;
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[("50".hashCode() ^ 1701) >>> 15878]).getMethodName().hashCode() & 748204196 + -748138661;
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = Integer.parseInt("1d", 29) << 10655;
      ++var1;
      String[] var5 = new String[var0[var10001] ^ Integer.parseInt("-au38n8", 33) ^ -427048726 ^ var3];
      int var6 = 8 << Integer.parseInt("t", 32);

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ 9830400 >>> 688 ^ var3];
         int var8 = 94 >>> 7015;

         while(var11 > 0) {
            int var9 = var0[var1];

            while(true) {
               switch(var4[var1 % var4.length] ^ Integer.parseInt("lkk", 22) >>> 5446) {
               case 136:
               case 207:
               case 214:
                  var9 ^= -2037173959 + 2037174207;
                  break;
               case 195:
                  var9 ^= 69632 >>> 4713;
                  break;
               case 197:
               case 199:
               case 201:
               case 202:
               case 204:
               case 206:
               case 211:
               case 239:
                  var9 ^= 3024 >>> 996;
                  break;
               case 200:
                  var9 ^= 65536 >>> 13610;
                  break;
               case 210:
                  var9 ^= 61 << Integer.parseInt("6ig", 21);
               }

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

      l = var5;
      JNICLoader.init();
      $jnicLoader();
      $jnicClinit();
   }
}

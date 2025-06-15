package net.taunahi.autopatch;

import dev.jnic.Keeqam.JNICLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.CharRange;
import kotlin.ranges.IntRange;
import net.taunahi.autopatch.utils.lIIIlIlIIlllIllllIIlIlllIlIllllIllIlllIIIllIIIllllIllIIllIIlIllI;
import org.jetbrains.annotations.NotNull;

public final class IllIIllIIllIIllllIlIIIIIIIlIlIIllllIllIIllllIIIlIlllIlllIIIIlIll {
   @NotNull
   public static final IllIIllIIllIIllllIlIIIIIIIlIlIIllllIllIIllllIIIlIlllIlllIIIIlIll IIllIIlIIIIlIllIIIlIlllllIlllllIIllIIIIlIlllIlIllllllllIIIIlIlII;
   private static final int lIllIIllIIlIIlIIIIIIlIlIllIIIIIlIIIIIllllIIIIIlllIIIllllIIIllIII;
   @NotNull
   private static final List<Character> lIlllllIlIIlllIllIlIIIlllIlIllIlllIIIIllIlllllIIlIlllIIlllIlIIIl;
   @NotNull
   private static final String lllIllIIlIIIIlIlIIllIIIIIIIIIlIlIlIllIIIlIIlIlllIllllllIIIlIllll;
   @NotNull
   private static final File lIIIIIIllIIIIIllIlIllIllIlIIIIIlIlllIIIlIIlllllIIIIIllIllIlIlIIl;
   private static final String[] a;
   private static final String[] F;

   private IllIIllIIllIIllllIlIIIIIIIlIlIIllllIllIIllllIIIlIlllIlllIIIIlIll() {
   }

   @NotNull
   public final File lIllIIIlIIIIlllIlIlIlllIIIIIIlIlIIIlllIIllllIlllIIIIllIIIIIlIlIl(Object[] var1) {
      return lIIIIIIllIIIIIllIlIllIllIlIIIIIlIlllIIIlIIlllllIIIIIllIllIlIlIIl;
   }

   @NotNull
   public final native String IIIllllllIIllllIlIlIIlIlIIIIlIlIIlIIllllIIIIllIIIlIlIIIIIIIllIlI(@NotNull String var1);

   @JvmStatic
   public static final native void lllIllIllIlIIlIlIIIlIlIllIlIllIIIIllIlllIlIlIIIllIIIlIIlllIIllII(Object[] var0);

   @JvmStatic
   public static final native void lIIlIllllllIlllIIIIIlIIlIIlllIIlIlIlllIlIlIlIIlllIllIlIIllIIllII(Object[] var0);

   // $FF: synthetic method
   static void $jnicClinit() {
      String[] a = new String[114688 << 11114 >>> (592969728 >>> 7536)];
      int a = 0;
      String a;
      int a = (a = F[16384 >>> 8106]).length();
      int a = (F[11264 >>> 1866].hashCode() ^ 1233661148 ^ 1233677541) >>> (F[3 << 12450].hashCode() ^ -938720079) - -938728414;
      int a = -1 << 14560 >>> (1140850688 >>> 7861);

      label53:
      while(true) {
         int var10000 = 7 << 1504 << (1606656 >>> 9609);
         ++a;
         String var10001 = a.substring(a, a + a);
         int var10002 = -1 << 13088 >>> (116129792 >>> 13901);

         while(true) {
            char[] var25 = var10001.toCharArray();
            int var10004 = var25.length;
            int var10003 = var10000;
            char[] var24 = var25;
            int var21 = var10004;

            for(int a = 0; var21 > a; ++a) {
               char var10006 = var24[a];
               int var10008;
               switch(a % (7 << 14304 << -1250178711 + 1250189783) ^ ((F[1 << ("52".hashCode() ^ 6076)].hashCode() ^ 1088957909) << 8640) - ((F[1 << 2208].hashCode() ^ 2117863400) + 25375050) - (Integer.parseInt(F[90 >>> 11239], 28) - -140341458 ^ Integer.parseInt(F[Integer.parseInt("adj8c", 22) >>> 3825], 29) ^ -1384383385)) {
               case 504215002:
                  var10008 = (-1359815236 ^ -1346708036) >>> (9875 << 704);
                  break;
               case 504215003:
                  var10008 = 452984832 >>> 6584 << (372768 >>> 3621);
                  break;
               case 504215004:
                  var10008 = (F[1 << 16097].hashCode() ^ 1569) << 15616 << (F[11 << 2592].hashCode() ^ 360318353) - 360311396;
                  break;
               case 504215005:
                  var10008 = 1547274579 - Integer.parseInt(F[7 << 14561], 27) >>> (189024 >>> 6565);
                  break;
               case 504215006:
                  var10008 = (F[5120 >>> 106].hashCode() ^ 1772) << 7872 << (3592 << 8322);
                  break;
               case 504215007:
                  var10008 = -1855088088 + 2020763096 >>> 601083427 + -601072814;
                  break;
               default:
                  var10008 = Integer.parseInt(F[256 >>> 13574], 25) >>> 6234 << (F[8388608 >>> ("12".hashCode() ^ 13941)].hashCode() ^ 1436818791) - 1436817223;
               }

               var24[a] = (char)(var10006 ^ var10003 ^ var10008);
            }

            String var26 = (new String(var24)).intern();
            switch(var10002 ^ ((F[786432 >>> 5201].hashCode() ^ -290822148) - -153012216 ^ -315907658 - 588616621) << (1244718374 - 1244713893 << ('퀀' >>> 70))) {
            case 2076069822:
               a[a++] = var26;
               if ((a += a) >= a) {
                  a = a;
                  IIllIIlIIIIlIllIIIlIlllllIlllllIIllIIIIlIlllIlIllllllllIIIIlIlII = new IllIIllIIllIIllllIlIIIIIIIlIlIIllllIllIIllllIIIlIlllIlllIIIIlIll();
                  lIlllllIlIIlllIllIlIIIlllIlIllIlllIIIIllIlllllIIlIlllIIlllIlIIIl = CollectionsKt.plus((Collection)CollectionsKt.plus((Iterable)(new CharRange((char)(97 << 11584 << -1349664143 - Integer.parseInt(F[112 >>> 5444], 24)), (char)(-999014906 + 999077370 >>> (92086272 >>> 8365)))), (Iterable)(new CharRange((char)((-216435621 ^ -216502181) >>> (69 << 15425)), (char)(45 << 3008 << -1042283053 + 1042294798)))), (Iterable)(new CharRange((char)(Integer.parseInt(F[17 << 1696], 34) << 320 << (1 << 6530)), (char)(3648 >>> 8518 << (27 << 14089)))));
                  Iterable a = (Iterable)(new IntRange(1, 256 >>> 5635 << (Integer.parseInt(F[Integer.parseInt("9", 27) << 8608], 30) >>> 12390)));
                  boolean var8 = false;
                  Collection a = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(a, 163840 >>> 1964 >>> -1194260154 - -1194267324)));
                  boolean var11 = false;
                  Iterator var12 = a.iterator();

                  while(var12.hasNext()) {
                     int a = ((IntIterator)var12).nextInt();
                     int a = false;
                     int a = Random.Default.nextInt(0, lIlllllIlIIlllIllIlIIIlllIlIllIlllIIIIllIlllllIIlIlllIIlllIlIIIl.size());
                     int a = false;
                     a.add((Character)lIlllllIlIIlllIllIlIIIlllIlIllIlllIIIIllIlllllIIlIlllIIlllIlIIIl.get(a));
                  }

                  lllIllIIlIIIIlIlIIllIIIIIIIIIlIlIlIllIIIlIIlIlllIllllllIIIlIllll = CollectionsKt.joinToString$default((Iterable)((List)a), (CharSequence)"", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 78231987 + -13220275 >>> -1033988641 + 1033992661, (Object)null);
                  String[] var19 = IllIIllIIllIIllllIlIIIIIIIlIlIIllllIllIIllllIIIlIlllIlllIIIIlIll.a;
                  File var20 = lIIIlIlIIlllIllllIIlIlllIlIllllIllIlllIIIllIIIllllIllIIllIIlIllI.IlIIlIIIllllIIlIIlllIllllIIllIllllIlIIIlllIlIIIIlllllIlIllIlIIll(new Object[]{var19[1]});
                  Intrinsics.checkNotNullExpressionValue(var20, var19[24 >>> 12547 << 332242874 + (F[Integer.parseInt("r7n3o", 31) >>> 15799].hashCode() ^ -332226789)]);
                  lIIIIIIllIIIIIllIlIllIllIlIIIIIlIlllIIIlIIlllllIIIIIllIllIlIlIIl = var20;
                  return;
               }

               a = a.charAt(a);
               break;
            default:
               a[a++] = var26;
               if ((a += a) < a) {
                  a = a.charAt(a);
                  continue label53;
               }

               a = (a = F[9 << 1345]).length();
               a = Integer.MIN_VALUE >>> 3231 << (92680 >>> 14627);
               a = -1 >>> 5184 >>> (206 << Integer.parseInt(F[655360 >>> 12880], 25));
            }

            var10000 = (F[13 << 6784].hashCode() ^ 73401987) >>> 13909 << (Integer.parseInt(F['\uf000' >>> 12076], 25) >>> 11952);
            ++a;
            var10001 = a.substring(a, a + a);
            var10002 = 0;
         }
      }
   }

   // $FF: synthetic method
   public static native void $jnicLoader();

   static {
      char[] var0 = "ฑື%\u0094fÍß-ÊາÔÐາ×%າ\"\u0097ືÑËÑÒ×ÑÐາÓÐາ×ÕຸËÑ×\u0085Ö\u008eß\u0088າÒÒີ×Ô\u0096\u0084\u008fຳ\u0088\u008cÒັßາÞÐາÒÑືÕ\u0097\u0087\u008e×\u0085ÖຶÐÓ\u008eÑ\u008aÞ฿\u0086¿ÂÚÜ\u0084\u0093°\u0013\u0091#Á7À²\u0014Ë\u0094Ý\u0083\u009b¿Q\u00976Øz\u0099þ¸\u0094ÃÇ\u008b\u0086ý \u0088ÐÔÐÐ\u009e³½¯\u0088ÓÐ\u0098Þúâ\u0082ÙÖ\u0085\u0092¶\u0098\u0088äÁ\u0098⃐û¼\u0095ØÁ\u008f\u0095§©\u0083\u0099Á\u0085´ª¸\u0082öÇ\u0098\u0097ªäÎ\u009eÉÐÐ\u009e\u001d\u00adR\u0082mÕ\u00872ÿë\u0084Å\u0096\u008cpëJÄ0ÛÉ\u009eºã\u008bØÒ\u0099Ù±£\u0088ÃÆ\u009e\u0084²¼ÉÛÚ\u008dÔúâÛÜ\u0084\u0093ັÕພ¹\u0080Íüâ¼ºÌ\u0092«¨à´¿\u008dÝ´éä²ç¿\u0087ªáä²àÂ\u0094½üÈ¬\u0010\u009erð\u0019 ´\u0016ÝÔ¬¡ືÔ#ÅfË \u0088".toCharArray();
      int var1 = Integer.parseInt("0", 29) << 11936;
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[Integer.parseInt("2d", 35) >>> 12135]).getMethodName().hashCode() & ("59".hashCode() ^ 448449336 ^ 448410211);
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = 100 >>> Integer.parseInt("13l", 26);
      ++var1;
      String[] var5 = new String[var0[var10001] ^ 1968989200 + -1968989028 ^ var3];
      int var6 = Integer.parseInt("28", 21) >>> 11014;

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ 25 << 1600 ^ var3];
         int var8 = 118 << ("86".hashCode() ^ 11265);

         while(var11 > 0) {
            int var9 = var0[var1];
            switch(var4[var1 % var4.length] ^ ("59".hashCode() ^ -1760859268) - -1760858856) {
            case 137:
            case 163:
            case 169:
            case 172:
            case 181:
            case 238:
               var9 ^= ("37".hashCode() ^ 60294756) >>> 8754;
               break;
            case 161:
            case 168:
               var9 ^= -659307803 - -659307967;
               break;
            case 165:
            case 175:
               var9 ^= 820413390 - 820413149;
               break;
            case 174:
               var9 ^= 75 << 4704;
               break;
            case 176:
            case 180:
               var9 ^= 'ꀀ' >>> 10091;
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
            }
         }

         var5[var6++] = (new String(var7)).intern();
      } while(var1 < var0.length);

      F = var5;
      lIllIIllIIlIIlIIIIIIlIlIllIIIIIlIIIIIllllIIIIIlllIIIllllIIIllIII = 1;
      JNICLoader.init();
      $jnicLoader();
      $jnicClinit();
   }
}

package net.taunahi.autopatch;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class IIIlIIIIlllIIlIlIlIllIIlIllIIllIlIIlIlllllllllIIIIllIIlllIIIlllI {
   private static final String[] o;

   public static IlIIIIllIIIlIllllIlIIlIlIllIlIlIlllIlllIlIllllIllIIllIllllllllll lIlllllIllIIIlllllIIIllIIIlIIllIlIIIIIllllllIIIIIlllIlIllIlIIIII(Object[] a) throws lIIlllllIIIIlIlllllIIllIlIIllllIlIlIIIllIllllIIIIlIIlIIlIlIIlIII {
      Properties a = (Properties)a[0];
      IlIIIIllIIIlIllllIlIIlIlIllIlIlIlllIlllIlIllllIllIIllIllllllllll a = new IlIIIIllIIIlIllllIlIIlIlIllIlIlIlllIlllIlIllllIllIIllIllllllllll();

      try {
         if (a == null || a.isEmpty()) {
            return a;
         }
      } catch (lIIlllllIIIIlIlllllIIllIlIIllllIlIlIIIllIllllIIIIlIIlIIlIlIIlIII var5) {
         throw a(var5);
      }

      Enumeration a = a.propertyNames();

      while(a.hasMoreElements()) {
         String a = (String)a.nextElement();
         String var10002 = a.getProperty(a);
         Object[] var10004 = new Object[2 << Integer.parseInt(o[25 >>> 1381], 34) >>> (2515 << Integer.parseInt(o[("73".hashCode() ^ 1757) << 8832], 35))];
         var10004[1] = var10002;
         var10004[0] = a;
         a.lIIIIllllIllIIIllIllIIIllllIIIIIIllIllllIlIIlIIIIIlllllllIIllIll(var10004);
      }

      return a;
   }

   public static Properties llIIllIIIlllIlIllIlllllIlllIlIllIIIIIIllIlIlIIlIIIlIlIllIlIIIIIl(Object[] a) throws lIIlllllIIIIlIlllllIIllIlIIllllIlIlIIIllIllllIIIIlIIlIIlIlIIlIII {
      IlIIIIllIIIlIllllIlIIlIlIllIlIlIlllIlllIlIllllIllIIllIllllllllll a = (IlIIIIllIIIlIllllIlIIlIlIllIlIlIlllIlllIlIllllIllIIllIllllllllll)a[0];
      Properties a = new Properties();
      if (a != null) {
         Iterator a = a.IlIlIIllIIlIlIIlllIllIIllIlllllIIIIIIIlIlIlIIIlIllIIIIIIIIlIllIl(new Object[0]);

         while(a.hasNext()) {
            String a = (String)a.next();
            a.put(a, a.IIllIIIIllllIIlIIlIIIIlIIlIIIIllllllllllIIllIIlIlllllIIIIlIlIIIl(new Object[]{a}));
         }
      }

      return a;
   }

   private static lIIlllllIIIIlIlllllIIllIlIIllllIlIlIIIllIllllIIIIlIIlIIlIlIIlIII a(lIIlllllIIIIlIlllllIIllIlIIllllIlIlIIIllIllllIIIIlIIlIIlIlIIlIII var0) {
      return var0;
   }

   static {
      char[] var0 = "\u0e62\u0efc\fÞP\u0efc!0Û".toCharArray();
      int var1 = 38 << 9439;
      StackTraceElement var2;
      int var3 = (var2 = (new Throwable()).getStackTrace()[34 << ("67".hashCode() ^ 3358)]).getMethodName().hashCode() & -248844516 + 248910051;
      char[] var4 = var2.getClassName().toCharArray();
      int var10001 = Integer.parseInt("12", 20) << 10015;
      ++var1;
      String[] var5 = new String[var0[var10001] ^ -88994441 - -88994642 ^ var3];
      int var6 = 1 >>> ("2".hashCode() ^ 11155);

      do {
         int var11;
         char[] var7 = new char[var11 = var0[var1++] ^ 180355072 >>> 14197 ^ var3];
         int var8 = 94 << 11967;

         while(var11 > 0) {
            int var9 = var0[var1];
            switch(var4[var1 % var4.length] ^ 77 << 14401) {
            case 180:
            case 249:
               var9 ^= -804820059 + 804820225;
               break;
            case 211:
            case 234:
            case 239:
            case 243:
            case 244:
            case 245:
               var9 ^= 33 << 1665;
               break;
            case 238:
            case 242:
            case 246:
               var9 ^= 4032 >>> 5254;
               break;
            case 251:
               var9 ^= "79".hashCode() ^ -81323243 ^ -81322728;
               break;
            case 255:
               var9 ^= -418991355 + Integer.parseInt("cfijfo", 32);
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

               var9 ^= 4032 >>> 5254;
            }
         }

         var5[var6++] = (new String(var7)).intern();
      } while(var1 < var0.length);

      o = var5;
   }
}

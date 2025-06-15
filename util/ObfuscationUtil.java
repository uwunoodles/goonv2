package org.spongepowered.asm.util;

public abstract class ObfuscationUtil {
   private ObfuscationUtil() {
   }

   public static String mapDescriptor(String a, ObfuscationUtil.IClassRemapper a) {
      return remapDescriptor(a, a, false);
   }

   public static String unmapDescriptor(String a, ObfuscationUtil.IClassRemapper a) {
      return remapDescriptor(a, a, true);
   }

   private static String remapDescriptor(String a, ObfuscationUtil.IClassRemapper a, boolean a) {
      StringBuilder a = new StringBuilder();
      StringBuilder a = null;

      for(int a = 0; a < a.length(); ++a) {
         char a = a.charAt(a);
         if (a != null) {
            if (a == ';') {
               a.append('L').append(remap(a.toString(), a, a)).append(';');
               a = null;
            } else {
               a.append(a);
            }
         } else if (a == 'L') {
            a = new StringBuilder();
         } else {
            a.append(a);
         }
      }

      if (a != null) {
         throw new IllegalArgumentException("Invalid descriptor '" + a + "', missing ';'");
      } else {
         return a.toString();
      }
   }

   private static Object remap(String a, ObfuscationUtil.IClassRemapper a, boolean a) {
      String a = a ? a.unmap(a) : a.map(a);
      return a != null ? a : a;
   }

   public interface IClassRemapper {
      String map(String var1);

      String unmap(String var1);
   }
}

package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class JavaVersion {
   private static double current = 0.0D;

   private JavaVersion() {
   }

   public static double current() {
      if (current == 0.0D) {
         current = resolveCurrentVersion();
      }

      return current;
   }

   private static double resolveCurrentVersion() {
      String a = System.getProperty("java.version");
      Matcher a = Pattern.compile("[0-9]+\\.[0-9]+").matcher(a);
      return a.find() ? Double.parseDouble(a.group()) : 1.6D;
   }
}

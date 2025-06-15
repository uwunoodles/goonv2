package org.spongepowered.asm.lib.commons;

import java.util.Collections;
import java.util.Map;

public class SimpleRemapper extends Remapper {
   private final Map<String, String> mapping;

   public SimpleRemapper(Map<String, String> a) {
      a.mapping = a;
   }

   public SimpleRemapper(String a, String a) {
      a.mapping = Collections.singletonMap(a, a);
   }

   public String mapMethodName(String a, String a, String a) {
      String a = a.map(a + '.' + a + a);
      return a == null ? a : a;
   }

   public String mapInvokeDynamicMethodName(String a, String a) {
      String a = a.map('.' + a + a);
      return a == null ? a : a;
   }

   public String mapFieldName(String a, String a, String a3) {
      String a = a.map(a + '.' + a);
      return a == null ? a : a;
   }

   public String map(String a) {
      return (String)a.mapping.get(a);
   }
}

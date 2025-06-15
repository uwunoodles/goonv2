package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObfuscationData<T> implements Iterable<ObfuscationType> {
   private final Map<ObfuscationType, T> data;
   private final T defaultValue;

   public ObfuscationData() {
      this((Object)null);
   }

   public ObfuscationData(T a) {
      a.data = new HashMap();
      a.defaultValue = a;
   }

   /** @deprecated */
   @Deprecated
   public void add(ObfuscationType a, T a) {
      a.put(a, a);
   }

   public void put(ObfuscationType a, T a) {
      a.data.put(a, a);
   }

   public boolean isEmpty() {
      return a.data.isEmpty();
   }

   public T get(ObfuscationType a) {
      T a = a.data.get(a);
      return a != null ? a : a.defaultValue;
   }

   public Iterator<ObfuscationType> iterator() {
      return a.data.keySet().iterator();
   }

   public String toString() {
      return String.format("ObfuscationData[%s,DEFAULT=%s]", a.listValues(), a.defaultValue);
   }

   public String values() {
      return "[" + a.listValues() + "]";
   }

   private String listValues() {
      StringBuilder a = new StringBuilder();
      boolean a = false;

      for(Iterator var3 = a.data.keySet().iterator(); var3.hasNext(); a = true) {
         ObfuscationType a = (ObfuscationType)var3.next();
         if (a) {
            a.append(',');
         }

         a.append(a.getKey()).append('=').append(a.data.get(a));
      }

      return a.toString();
   }
}

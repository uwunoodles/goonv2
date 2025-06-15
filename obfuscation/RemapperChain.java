package org.spongepowered.asm.obfuscation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public class RemapperChain implements IRemapper {
   private final List<IRemapper> remappers = new ArrayList();

   public String toString() {
      return String.format("RemapperChain[%d]", a.remappers.size());
   }

   public RemapperChain add(IRemapper a) {
      a.remappers.add(a);
      return a;
   }

   public String mapMethodName(String a, String a, String a) {
      Iterator var4 = a.remappers.iterator();

      while(var4.hasNext()) {
         IRemapper a = (IRemapper)var4.next();
         String a = a.mapMethodName(a, a, a);
         if (a != null && !a.equals(a)) {
            a = a;
         }
      }

      return a;
   }

   public String mapFieldName(String a, String a, String a) {
      Iterator var4 = a.remappers.iterator();

      while(var4.hasNext()) {
         IRemapper a = (IRemapper)var4.next();
         String a = a.mapFieldName(a, a, a);
         if (a != null && !a.equals(a)) {
            a = a;
         }
      }

      return a;
   }

   public String map(String a) {
      Iterator var2 = a.remappers.iterator();

      while(var2.hasNext()) {
         IRemapper a = (IRemapper)var2.next();
         String a = a.map(a);
         if (a != null && !a.equals(a)) {
            a = a;
         }
      }

      return a;
   }

   public String unmap(String a) {
      Iterator var2 = a.remappers.iterator();

      while(var2.hasNext()) {
         IRemapper a = (IRemapper)var2.next();
         String a = a.unmap(a);
         if (a != null && !a.equals(a)) {
            a = a;
         }
      }

      return a;
   }

   public String mapDesc(String a) {
      Iterator var2 = a.remappers.iterator();

      while(var2.hasNext()) {
         IRemapper a = (IRemapper)var2.next();
         String a = a.mapDesc(a);
         if (a != null && !a.equals(a)) {
            a = a;
         }
      }

      return a;
   }

   public String unmapDesc(String a) {
      Iterator var2 = a.remappers.iterator();

      while(var2.hasNext()) {
         IRemapper a = (IRemapper)var2.next();
         String a = a.unmapDesc(a);
         if (a != null && !a.equals(a)) {
            a = a;
         }
      }

      return a;
   }
}

package org.spongepowered.tools.obfuscation.mapping;

import com.google.common.base.Objects;
import java.util.LinkedHashSet;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;

public interface IMappingConsumer {
   void clear();

   void addFieldMapping(ObfuscationType var1, MappingField var2, MappingField var3);

   void addMethodMapping(ObfuscationType var1, MappingMethod var2, MappingMethod var3);

   IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType var1);

   IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType var1);

   public static class MappingSet<TMapping extends IMapping<TMapping>> extends LinkedHashSet<IMappingConsumer.MappingSet.Pair<TMapping>> {
      private static final long serialVersionUID = 1L;

      public static class Pair<TMapping extends IMapping<TMapping>> {
         public final TMapping from;
         public final TMapping to;

         public Pair(TMapping a, TMapping a) {
            a.from = a;
            a.to = a;
         }

         public boolean equals(Object a) {
            if (!(a instanceof IMappingConsumer.MappingSet.Pair)) {
               return false;
            } else {
               IMappingConsumer.MappingSet.Pair<TMapping> a = (IMappingConsumer.MappingSet.Pair)a;
               return Objects.equal(a.from, a.from) && Objects.equal(a.to, a.to);
            }
         }

         public int hashCode() {
            return Objects.hashCode(new Object[]{a.from, a.to});
         }

         public String toString() {
            return String.format("%s -> %s", a.from, a.to);
         }
      }
   }
}

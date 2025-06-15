package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

class Mappings implements IMappingConsumer {
   private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingField>> fieldMappings = new HashMap();
   private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>> methodMappings = new HashMap();
   private Mappings.UniqueMappings unique;

   public Mappings() {
      a.init();
   }

   private void init() {
      Iterator var1 = ObfuscationType.types().iterator();

      while(var1.hasNext()) {
         ObfuscationType a = (ObfuscationType)var1.next();
         a.fieldMappings.put(a, new IMappingConsumer.MappingSet());
         a.methodMappings.put(a, new IMappingConsumer.MappingSet());
      }

   }

   public IMappingConsumer asUnique() {
      if (a.unique == null) {
         a.unique = new Mappings.UniqueMappings(a);
      }

      return a.unique;
   }

   public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType a) {
      IMappingConsumer.MappingSet<MappingField> a = (IMappingConsumer.MappingSet)a.fieldMappings.get(a);
      return a != null ? a : new IMappingConsumer.MappingSet();
   }

   public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType a) {
      IMappingConsumer.MappingSet<MappingMethod> a = (IMappingConsumer.MappingSet)a.methodMappings.get(a);
      return a != null ? a : new IMappingConsumer.MappingSet();
   }

   public void clear() {
      a.fieldMappings.clear();
      a.methodMappings.clear();
      if (a.unique != null) {
         a.unique.clearMaps();
      }

      a.init();
   }

   public void addFieldMapping(ObfuscationType a, MappingField a, MappingField a) {
      IMappingConsumer.MappingSet<MappingField> a = (IMappingConsumer.MappingSet)a.fieldMappings.get(a);
      if (a == null) {
         a = new IMappingConsumer.MappingSet();
         a.fieldMappings.put(a, a);
      }

      a.add(new IMappingConsumer.MappingSet.Pair(a, a));
   }

   public void addMethodMapping(ObfuscationType a, MappingMethod a, MappingMethod a) {
      IMappingConsumer.MappingSet<MappingMethod> a = (IMappingConsumer.MappingSet)a.methodMappings.get(a);
      if (a == null) {
         a = new IMappingConsumer.MappingSet();
         a.methodMappings.put(a, a);
      }

      a.add(new IMappingConsumer.MappingSet.Pair(a, a));
   }

   static class UniqueMappings implements IMappingConsumer {
      private final IMappingConsumer mappings;
      private final Map<ObfuscationType, Map<MappingField, MappingField>> fields = new HashMap();
      private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods = new HashMap();

      public UniqueMappings(IMappingConsumer a) {
         a.mappings = a;
      }

      public void clear() {
         a.clearMaps();
         a.mappings.clear();
      }

      protected void clearMaps() {
         a.fields.clear();
         a.methods.clear();
      }

      public void addFieldMapping(ObfuscationType a, MappingField a, MappingField a) {
         if (!a.checkForExistingMapping(a, a, a, a.fields)) {
            a.mappings.addFieldMapping(a, a, a);
         }

      }

      public void addMethodMapping(ObfuscationType a, MappingMethod a, MappingMethod a) {
         if (!a.checkForExistingMapping(a, a, a, a.methods)) {
            a.mappings.addMethodMapping(a, a, a);
         }

      }

      private <TMapping extends IMapping<TMapping>> boolean checkForExistingMapping(ObfuscationType a, TMapping a, TMapping a, Map<ObfuscationType, Map<TMapping, TMapping>> a) throws Mappings.MappingConflictException {
         Map<TMapping, TMapping> a = (Map)a.get(a);
         if (a == null) {
            a = new HashMap();
            a.put(a, a);
         }

         TMapping a = (IMapping)((Map)a).get(a);
         if (a != null) {
            if (a.equals(a)) {
               return true;
            } else {
               throw new Mappings.MappingConflictException(a, a);
            }
         } else {
            ((Map)a).put(a, a);
            return false;
         }
      }

      public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType a) {
         return a.mappings.getFieldMappings(a);
      }

      public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType a) {
         return a.mappings.getMethodMappings(a);
      }
   }

   public static class MappingConflictException extends RuntimeException {
      private static final long serialVersionUID = 1L;
      private final IMapping<?> oldMapping;
      private final IMapping<?> newMapping;

      public MappingConflictException(IMapping<?> a, IMapping<?> a) {
         a.oldMapping = a;
         a.newMapping = a;
      }

      public IMapping<?> getOld() {
         return a.oldMapping;
      }

      public IMapping<?> getNew() {
         return a.newMapping;
      }
   }
}

package org.spongepowered.tools.obfuscation.mapping.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;

public abstract class MappingProvider implements IMappingProvider {
   protected final Messager messager;
   protected final Filer filer;
   protected final BiMap<String, String> packageMap = HashBiMap.create();
   protected final BiMap<String, String> classMap = HashBiMap.create();
   protected final BiMap<MappingField, MappingField> fieldMap = HashBiMap.create();
   protected final BiMap<MappingMethod, MappingMethod> methodMap = HashBiMap.create();

   public MappingProvider(Messager a, Filer a) {
      a.messager = a;
      a.filer = a;
   }

   public void clear() {
      a.packageMap.clear();
      a.classMap.clear();
      a.fieldMap.clear();
      a.methodMap.clear();
   }

   public boolean isEmpty() {
      return a.packageMap.isEmpty() && a.classMap.isEmpty() && a.fieldMap.isEmpty() && a.methodMap.isEmpty();
   }

   public MappingMethod getMethodMapping(MappingMethod a) {
      return (MappingMethod)a.methodMap.get(a);
   }

   public MappingField getFieldMapping(MappingField a) {
      return (MappingField)a.fieldMap.get(a);
   }

   public String getClassMapping(String a) {
      return (String)a.classMap.get(a);
   }

   public String getPackageMapping(String a) {
      return (String)a.packageMap.get(a);
   }
}

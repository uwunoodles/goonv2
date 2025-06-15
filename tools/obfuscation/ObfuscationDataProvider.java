package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public class ObfuscationDataProvider implements IObfuscationDataProvider {
   private final IMixinAnnotationProcessor ap;
   private final List<ObfuscationEnvironment> environments;

   public ObfuscationDataProvider(IMixinAnnotationProcessor a, List<ObfuscationEnvironment> a) {
      a.ap = a;
      a.environments = a;
   }

   public <T> ObfuscationData<T> getObfEntryRecursive(MemberInfo a) {
      MemberInfo a = a;
      ObfuscationData<String> a = a.getObfClass(a.owner);
      ObfuscationData a = a.getObfEntry(a);

      try {
         while(a.isEmpty()) {
            TypeHandle a = a.ap.getTypeProvider().getTypeHandle(a.owner);
            if (a == null) {
               return a;
            }

            TypeHandle a = a.getSuperclass();
            a = a.getObfEntryUsing(a, a);
            if (!a.isEmpty()) {
               return applyParents(a, a);
            }

            Iterator var7 = a.getInterfaces().iterator();

            while(var7.hasNext()) {
               TypeHandle a = (TypeHandle)var7.next();
               a = a.getObfEntryUsing(a, a);
               if (!a.isEmpty()) {
                  return applyParents(a, a);
               }
            }

            if (a == null) {
               break;
            }

            a = a.move(a.getName());
         }

         return a;
      } catch (Exception var9) {
         var9.printStackTrace();
         return a.getObfEntry(a);
      }
   }

   private <T> ObfuscationData<T> getObfEntryUsing(MemberInfo a, TypeHandle a) {
      return a == null ? new ObfuscationData() : a.getObfEntry(a.move(a.getName()));
   }

   public <T> ObfuscationData<T> getObfEntry(MemberInfo a) {
      return a.isField() ? a.getObfField(a) : a.getObfMethod(a.asMethodMapping());
   }

   public <T> ObfuscationData<T> getObfEntry(IMapping<T> a) {
      if (a != null) {
         if (a.getType() == IMapping.Type.FIELD) {
            return a.getObfField((MappingField)a);
         }

         if (a.getType() == IMapping.Type.METHOD) {
            return a.getObfMethod((MappingMethod)a);
         }
      }

      return new ObfuscationData();
   }

   public ObfuscationData<MappingMethod> getObfMethodRecursive(MemberInfo a) {
      return a.getObfEntryRecursive(a);
   }

   public ObfuscationData<MappingMethod> getObfMethod(MemberInfo a) {
      return a.getRemappedMethod(a, a.isConstructor());
   }

   public ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo a) {
      return a.getRemappedMethod(a, true);
   }

   private ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo a, boolean a) {
      ObfuscationData<MappingMethod> a = new ObfuscationData();
      Iterator var4 = a.environments.iterator();

      while(var4.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var4.next();
         MappingMethod a = a.getObfMethod(a);
         if (a != null) {
            a.put(a.getType(), a);
         }
      }

      if (a.isEmpty() && a) {
         return a.remapDescriptor(a, a);
      } else {
         return a;
      }
   }

   public ObfuscationData<MappingMethod> getObfMethod(MappingMethod a) {
      return a.getRemappedMethod(a, a.isConstructor());
   }

   public ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod a) {
      return a.getRemappedMethod(a, true);
   }

   private ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod a, boolean a) {
      ObfuscationData<MappingMethod> a = new ObfuscationData();
      Iterator var4 = a.environments.iterator();

      while(var4.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var4.next();
         MappingMethod a = a.getObfMethod(a);
         if (a != null) {
            a.put(a.getType(), a);
         }
      }

      if (a.isEmpty() && a) {
         return a.remapDescriptor(a, new MemberInfo(a));
      } else {
         return a;
      }
   }

   public ObfuscationData<MappingMethod> remapDescriptor(ObfuscationData<MappingMethod> a, MemberInfo a) {
      Iterator var3 = a.environments.iterator();

      while(var3.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var3.next();
         MemberInfo a = a.remapDescriptor(a);
         if (a != null) {
            a.put(a.getType(), a.asMethodMapping());
         }
      }

      return a;
   }

   public ObfuscationData<MappingField> getObfFieldRecursive(MemberInfo a) {
      return a.getObfEntryRecursive(a);
   }

   public ObfuscationData<MappingField> getObfField(MemberInfo a) {
      return a.getObfField(a.asFieldMapping());
   }

   public ObfuscationData<MappingField> getObfField(MappingField a) {
      ObfuscationData<MappingField> a = new ObfuscationData();
      Iterator var3 = a.environments.iterator();

      while(var3.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var3.next();
         MappingField a = a.getObfField(a);
         if (a != null) {
            if (a.getDesc() == null && a.getDesc() != null) {
               a = a.transform(a.remapDescriptor(a.getDesc()));
            }

            a.put(a.getType(), a);
         }
      }

      return a;
   }

   public ObfuscationData<String> getObfClass(TypeHandle a) {
      return a.getObfClass(a.getName());
   }

   public ObfuscationData<String> getObfClass(String a) {
      ObfuscationData<String> a = new ObfuscationData(a);
      Iterator var3 = a.environments.iterator();

      while(var3.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var3.next();
         String a = a.getObfClass(a);
         if (a != null) {
            a.put(a.getType(), a);
         }
      }

      return a;
   }

   private static <T> ObfuscationData<T> applyParents(ObfuscationData<String> a, ObfuscationData<T> a) {
      Iterator var2 = a.iterator();

      while(var2.hasNext()) {
         ObfuscationType a = (ObfuscationType)var2.next();
         String a = (String)a.get(a);
         T a = a.get(a);
         a.put(a, MemberInfo.fromMapping((IMapping)a).move(a).asMapping());
      }

      return a;
   }
}

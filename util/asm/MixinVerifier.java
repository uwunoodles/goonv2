package org.spongepowered.asm.util.asm;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public class MixinVerifier extends SimpleVerifier {
   private Type currentClass;
   private Type currentSuperClass;
   private List<Type> currentClassInterfaces;
   private boolean isInterface;

   public MixinVerifier(Type a, Type a, List<Type> a, boolean a) {
      super(a, a, a, a);
      a.currentClass = a;
      a.currentSuperClass = a;
      a.currentClassInterfaces = a;
      a.isInterface = a;
   }

   protected boolean isInterface(Type a) {
      return a.currentClass != null && a.equals(a.currentClass) ? a.isInterface : ClassInfo.forType(a).isInterface();
   }

   protected Type getSuperClass(Type a) {
      if (a.currentClass != null && a.equals(a.currentClass)) {
         return a.currentSuperClass;
      } else {
         ClassInfo a = ClassInfo.forType(a).getSuperClass();
         return a == null ? null : Type.getType("L" + a.getName() + ";");
      }
   }

   protected boolean isAssignableFrom(Type a, Type a) {
      if (a.equals(a)) {
         return true;
      } else if (a.currentClass != null && a.equals(a.currentClass)) {
         if (a.getSuperClass(a) == null) {
            return false;
         } else if (!a.isInterface) {
            return a.isAssignableFrom(a, a.getSuperClass(a));
         } else {
            return a.getSort() == 10 || a.getSort() == 9;
         }
      } else if (a.currentClass != null && a.equals(a.currentClass)) {
         if (a.isAssignableFrom(a, a.currentSuperClass)) {
            return true;
         } else {
            if (a.currentClassInterfaces != null) {
               for(int a = 0; a < a.currentClassInterfaces.size(); ++a) {
                  Type a = (Type)a.currentClassInterfaces.get(a);
                  if (a.isAssignableFrom(a, a)) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         ClassInfo a = ClassInfo.forType(a);
         if (a == null) {
            return false;
         } else {
            if (a.isInterface()) {
               a = ClassInfo.forName("java/lang/Object");
            }

            return ClassInfo.forType(a).hasSuperClass(a);
         }
      }
   }
}

package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class TargetValidator extends MixinValidator {
   public TargetValidator(IMixinAnnotationProcessor a) {
      super(a, IMixinValidator.ValidationPass.LATE);
   }

   public boolean validate(TypeElement a, AnnotationHandle a2, Collection<TypeHandle> a) {
      if ("true".equalsIgnoreCase(a.options.getOption("disableTargetValidator"))) {
         return true;
      } else {
         if (a.getKind() == ElementKind.INTERFACE) {
            a.validateInterfaceMixin(a, a);
         } else {
            a.validateClassMixin(a, a);
         }

         return true;
      }
   }

   private void validateInterfaceMixin(TypeElement a, Collection<TypeHandle> a) {
      boolean a = false;
      Iterator var4 = a.getEnclosedElements().iterator();

      while(true) {
         Element a;
         do {
            if (!var4.hasNext()) {
               if (!a) {
                  return;
               }

               var4 = a.iterator();

               while(var4.hasNext()) {
                  TypeHandle a = (TypeHandle)var4.next();
                  TypeElement a = a.getElement();
                  if (a != null && a.getKind() != ElementKind.INTERFACE) {
                     a.error("Targetted type '" + a + " of " + a + " is not an interface", a);
                  }
               }

               return;
            }

            a = (Element)var4.next();
         } while(a.getKind() != ElementKind.METHOD);

         boolean a = AnnotationHandle.of(a, Accessor.class).exists();
         boolean a = AnnotationHandle.of(a, Invoker.class).exists();
         a |= !a && !a;
      }
   }

   private void validateClassMixin(TypeElement a, Collection<TypeHandle> a) {
      TypeMirror a = a.getSuperclass();
      Iterator var4 = a.iterator();

      while(var4.hasNext()) {
         TypeHandle a = (TypeHandle)var4.next();
         TypeMirror a = a.getType();
         if (a != null && !a.validateSuperClass(a, a)) {
            a.error("Superclass " + a + " of " + a + " was not found in the hierarchy of target class " + a, a);
         }
      }

   }

   private boolean validateSuperClass(TypeMirror a, TypeMirror a) {
      return TypeUtils.isAssignable(a.processingEnv, a, a) ? true : a.validateSuperClassRecursive(a, a);
   }

   private boolean validateSuperClassRecursive(TypeMirror a, TypeMirror a) {
      if (!(a instanceof DeclaredType)) {
         return false;
      } else if (TypeUtils.isAssignable(a.processingEnv, a, a)) {
         return true;
      } else {
         TypeElement a = (TypeElement)((DeclaredType)a).asElement();
         TypeMirror a = a.getSuperclass();
         if (a.getKind() == TypeKind.NONE) {
            return false;
         } else {
            return a.checkMixinsFor(a, a) ? true : a.validateSuperClassRecursive(a, a);
         }
      }
   }

   private boolean checkMixinsFor(TypeMirror a, TypeMirror a) {
      Iterator var3 = a.getMixinsTargeting(a).iterator();

      TypeMirror a;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         a = (TypeMirror)var3.next();
      } while(!TypeUtils.isAssignable(a.processingEnv, a, a));

      return true;
   }
}

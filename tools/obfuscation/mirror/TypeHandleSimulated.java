package org.spongepowered.tools.obfuscation.mirror;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

public class TypeHandleSimulated extends TypeHandle {
   private final TypeElement simulatedType;

   public TypeHandleSimulated(String a, TypeMirror a) {
      this(TypeUtils.getPackage(a), a, a);
   }

   public TypeHandleSimulated(PackageElement a, String a, TypeMirror a) {
      super(a, a);
      a.simulatedType = (TypeElement)((DeclaredType)a).asElement();
   }

   protected TypeElement getTargetElement() {
      return a.simulatedType;
   }

   public boolean isPublic() {
      return true;
   }

   public boolean isImaginary() {
      return false;
   }

   public boolean isSimulated() {
      return true;
   }

   public AnnotationHandle getAnnotation(Class<? extends Annotation> a1) {
      return null;
   }

   public TypeHandle getSuperclass() {
      return null;
   }

   public String findDescriptor(MemberInfo a) {
      return a != null ? a.desc : null;
   }

   public FieldHandle findField(String a, String a, boolean a3) {
      return new FieldHandle((String)null, a, a);
   }

   public MethodHandle findMethod(String a, String a, boolean a3) {
      return new MethodHandle((TypeHandle)null, a, a);
   }

   public MappingMethod getMappingMethod(String a, String a) {
      String a = (new SignaturePrinter(a, a)).setFullyQualified(true).toDescriptor();
      String a = TypeUtils.stripGenerics(a);
      MethodHandle a = findMethodRecursive((TypeHandle)a, a, a, a, true);
      return a != null ? a.asMapping(true) : super.getMappingMethod(a, a);
   }

   private static MethodHandle findMethodRecursive(TypeHandle a, String a, String a, String a, boolean a) {
      TypeElement a = a.getTargetElement();
      if (a == null) {
         return null;
      } else {
         MethodHandle a = TypeHandle.findMethod(a, a, a, a, a);
         if (a != null) {
            return a;
         } else {
            Iterator var7 = a.getInterfaces().iterator();

            do {
               if (!var7.hasNext()) {
                  TypeMirror a = a.getSuperclass();
                  if (a != null && a.getKind() != TypeKind.NONE) {
                     return findMethodRecursive(a, a, a, a, a);
                  }

                  return null;
               }

               TypeMirror a = (TypeMirror)var7.next();
               a = findMethodRecursive(a, a, a, a, a);
            } while(a == null);

            return a;
         }
      }
   }

   private static MethodHandle findMethodRecursive(TypeMirror a, String a, String a, String a, boolean a) {
      if (!(a instanceof DeclaredType)) {
         return null;
      } else {
         TypeElement a = (TypeElement)((DeclaredType)a).asElement();
         return findMethodRecursive(new TypeHandle(a), a, a, a, a);
      }
   }
}

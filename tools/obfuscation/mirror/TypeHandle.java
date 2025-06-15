package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;

public class TypeHandle {
   private final String name;
   private final PackageElement pkg;
   private final TypeElement element;
   private TypeReference reference;

   public TypeHandle(PackageElement a, String a) {
      a.name = a.replace('.', '/');
      a.pkg = a;
      a.element = null;
   }

   public TypeHandle(TypeElement a) {
      a.pkg = TypeUtils.getPackage(a);
      a.name = TypeUtils.getInternalName(a);
      a.element = a;
   }

   public TypeHandle(DeclaredType a) {
      this((TypeElement)a.asElement());
   }

   public final String toString() {
      return a.name.replace('/', '.');
   }

   public final String getName() {
      return a.name;
   }

   public final PackageElement getPackage() {
      return a.pkg;
   }

   public final TypeElement getElement() {
      return a.element;
   }

   protected TypeElement getTargetElement() {
      return a.element;
   }

   public AnnotationHandle getAnnotation(Class<? extends Annotation> a) {
      return AnnotationHandle.of(a.getTargetElement(), a);
   }

   public final List<? extends Element> getEnclosedElements() {
      return getEnclosedElements(a.getTargetElement());
   }

   public <T extends Element> List<T> getEnclosedElements(ElementKind... a) {
      return getEnclosedElements(a.getTargetElement(), a);
   }

   public TypeMirror getType() {
      return a.getTargetElement() != null ? a.getTargetElement().asType() : null;
   }

   public TypeHandle getSuperclass() {
      TypeElement a = a.getTargetElement();
      if (a == null) {
         return null;
      } else {
         TypeMirror a = a.getSuperclass();
         return a != null && a.getKind() != TypeKind.NONE ? new TypeHandle((DeclaredType)a) : null;
      }
   }

   public List<TypeHandle> getInterfaces() {
      if (a.getTargetElement() == null) {
         return Collections.emptyList();
      } else {
         Builder<TypeHandle> a = ImmutableList.builder();
         Iterator var2 = a.getTargetElement().getInterfaces().iterator();

         while(var2.hasNext()) {
            TypeMirror a = (TypeMirror)var2.next();
            a.add(new TypeHandle((DeclaredType)a));
         }

         return a.build();
      }
   }

   public boolean isPublic() {
      return a.getTargetElement() != null && a.getTargetElement().getModifiers().contains(Modifier.PUBLIC);
   }

   public boolean isImaginary() {
      return a.getTargetElement() == null;
   }

   public boolean isSimulated() {
      return false;
   }

   public final TypeReference getReference() {
      if (a.reference == null) {
         a.reference = new TypeReference(a);
      }

      return a.reference;
   }

   public MappingMethod getMappingMethod(String a, String a) {
      return new ResolvableMappingMethod(a, a, a);
   }

   public String findDescriptor(MemberInfo a) {
      String a = a.desc;
      if (a == null) {
         Iterator var3 = a.getEnclosedElements(ElementKind.METHOD).iterator();

         while(var3.hasNext()) {
            ExecutableElement a = (ExecutableElement)var3.next();
            if (a.getSimpleName().toString().equals(a.name)) {
               a = TypeUtils.getDescriptor(a);
               break;
            }
         }
      }

      return a;
   }

   public final FieldHandle findField(VariableElement a) {
      return a.findField(a, true);
   }

   public final FieldHandle findField(VariableElement a, boolean a) {
      return a.findField(a.getSimpleName().toString(), TypeUtils.getTypeName(a.asType()), a);
   }

   public final FieldHandle findField(String a, String a) {
      return a.findField(a, a, true);
   }

   public FieldHandle findField(String a, String a, boolean a) {
      String a = TypeUtils.stripGenerics(a);
      Iterator var5 = a.getEnclosedElements(ElementKind.FIELD).iterator();

      VariableElement a;
      do {
         if (!var5.hasNext()) {
            return null;
         }

         a = (VariableElement)var5.next();
         if (compareElement(a, a, a, a)) {
            return new FieldHandle(a.getTargetElement(), a);
         }
      } while(!compareElement(a, a, a, a));

      return new FieldHandle(a.getTargetElement(), a, true);
   }

   public final MethodHandle findMethod(ExecutableElement a) {
      return a.findMethod(a, true);
   }

   public final MethodHandle findMethod(ExecutableElement a, boolean a) {
      return a.findMethod(a.getSimpleName().toString(), TypeUtils.getJavaSignature((Element)a), a);
   }

   public final MethodHandle findMethod(String a, String a) {
      return a.findMethod(a, a, true);
   }

   public MethodHandle findMethod(String a, String a, boolean a) {
      String a = TypeUtils.stripGenerics(a);
      return findMethod(a, a, a, a, a);
   }

   protected static MethodHandle findMethod(TypeHandle a, String a, String a, String a, boolean a) {
      Iterator var5 = getEnclosedElements(a.getTargetElement(), ElementKind.CONSTRUCTOR, ElementKind.METHOD).iterator();

      ExecutableElement a;
      do {
         if (!var5.hasNext()) {
            return null;
         }

         a = (ExecutableElement)var5.next();
      } while(!compareElement(a, a, a, a) && !compareElement(a, a, a, a));

      return new MethodHandle(a, a);
   }

   protected static boolean compareElement(Element a, String a, String a, boolean a) {
      try {
         String a = a.getSimpleName().toString();
         String a = TypeUtils.getJavaSignature(a);
         String a = TypeUtils.stripGenerics(a);
         boolean a = a ? a.equals(a) : a.equalsIgnoreCase(a);
         return a && (a.length() == 0 || a.equals(a) || a.equals(a));
      } catch (NullPointerException var8) {
         return false;
      }
   }

   protected static <T extends Element> List<T> getEnclosedElements(TypeElement a, ElementKind... a) {
      if (a != null && a.length >= 1) {
         if (a == null) {
            return Collections.emptyList();
         } else {
            Builder<T> a = ImmutableList.builder();
            Iterator var3 = a.getEnclosedElements().iterator();

            while(true) {
               while(var3.hasNext()) {
                  Element a = (Element)var3.next();
                  ElementKind[] var5 = a;
                  int var6 = a.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     ElementKind a = var5[var7];
                     if (a.getKind() == a) {
                        a.add(a);
                        break;
                     }
                  }
               }

               return a.build();
            }
         }
      } else {
         return getEnclosedElements(a);
      }
   }

   protected static List<? extends Element> getEnclosedElements(TypeElement a) {
      return a != null ? a.getEnclosedElements() : Collections.emptyList();
   }
}

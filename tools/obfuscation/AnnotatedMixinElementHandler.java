package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.mirror.Visibility;

abstract class AnnotatedMixinElementHandler {
   protected final AnnotatedMixin mixin;
   protected final String classRef;
   protected final IMixinAnnotationProcessor ap;
   protected final IObfuscationManager obf;
   private IMappingConsumer mappings;

   AnnotatedMixinElementHandler(IMixinAnnotationProcessor a, AnnotatedMixin a) {
      a.ap = a;
      a.mixin = a;
      a.classRef = a.getClassRef();
      a.obf = a.getObfuscationManager();
   }

   private IMappingConsumer getMappings() {
      if (a.mappings == null) {
         IMappingConsumer a = a.mixin.getMappings();
         if (a instanceof Mappings) {
            a.mappings = ((Mappings)a).asUnique();
         } else {
            a.mappings = a;
         }
      }

      return a.mappings;
   }

   protected final void addFieldMappings(String a, String a, ObfuscationData<MappingField> a) {
      Iterator var4 = a.iterator();

      while(var4.hasNext()) {
         ObfuscationType a = (ObfuscationType)var4.next();
         MappingField a = (MappingField)a.get(a);
         a.addFieldMapping(a, a, a.getSimpleName(), a, a.getDesc());
      }

   }

   protected final void addFieldMapping(ObfuscationType a, AnnotatedMixinElementHandler.ShadowElementName a, String a, String a) {
      a.addFieldMapping(a, a.name(), a.obfuscated(), a, a);
   }

   protected final void addFieldMapping(ObfuscationType a, String a, String a, String a, String a) {
      MappingField a = new MappingField(a.classRef, a, a);
      MappingField a = new MappingField(a.classRef, a, a);
      a.getMappings().addFieldMapping(a, a, a);
   }

   protected final void addMethodMappings(String a, String a, ObfuscationData<MappingMethod> a) {
      Iterator var4 = a.iterator();

      while(var4.hasNext()) {
         ObfuscationType a = (ObfuscationType)var4.next();
         MappingMethod a = (MappingMethod)a.get(a);
         a.addMethodMapping(a, a, a.getSimpleName(), a, a.getDesc());
      }

   }

   protected final void addMethodMapping(ObfuscationType a, AnnotatedMixinElementHandler.ShadowElementName a, String a, String a) {
      a.addMethodMapping(a, a.name(), a.obfuscated(), a, a);
   }

   protected final void addMethodMapping(ObfuscationType a, String a, String a, String a, String a) {
      MappingMethod a = new MappingMethod(a.classRef, a, a);
      MappingMethod a = new MappingMethod(a.classRef, a, a);
      a.getMappings().addMethodMapping(a, a, a);
   }

   protected final void checkConstraints(ExecutableElement a, AnnotationHandle a) {
      try {
         ConstraintParser.Constraint a = ConstraintParser.parse((String)a.getValue("constraints"));

         try {
            a.check(a.ap.getTokenProvider());
         } catch (ConstraintViolationException var5) {
            a.ap.printMessage(Kind.ERROR, var5.getMessage(), a, a.asMirror());
         }
      } catch (InvalidConstraintException var6) {
         a.ap.printMessage(Kind.WARNING, var6.getMessage(), a, a.asMirror());
      }

   }

   protected final void validateTarget(Element a, AnnotationHandle a, AnnotatedMixinElementHandler.AliasedElementName a, String a) {
      if (a instanceof ExecutableElement) {
         a.validateTargetMethod((ExecutableElement)a, a, a, a, false, false);
      } else if (a instanceof VariableElement) {
         a.validateTargetField((VariableElement)a, a, a, a);
      }

   }

   protected final void validateTargetMethod(ExecutableElement a, AnnotationHandle a, AnnotatedMixinElementHandler.AliasedElementName a, String a, boolean a, boolean a) {
      String a = TypeUtils.getJavaSignature((Element)a);
      Iterator var8 = a.mixin.getTargets().iterator();

      while(true) {
         TypeHandle a;
         do {
            if (!var8.hasNext()) {
               return;
            }

            a = (TypeHandle)var8.next();
         } while(a.isImaginary());

         MethodHandle a = a.findMethod(a);
         if (a == null && a.hasPrefix()) {
            a = a.findMethod(a.baseName(), a);
         }

         if (a == null && a.hasAliases()) {
            Iterator var11 = a.getAliases().iterator();

            while(var11.hasNext()) {
               String a = (String)var11.next();
               if ((a = a.findMethod(a, a)) != null) {
                  break;
               }
            }
         }

         if (a != null) {
            if (a) {
               a.validateMethodVisibility(a, a, a, a, a);
            }
         } else if (!a) {
            a.printMessage(Kind.WARNING, "Cannot find target for " + a + " method in " + a, a, a);
         }
      }
   }

   private void validateMethodVisibility(ExecutableElement a, AnnotationHandle a, String a, TypeHandle a, MethodHandle a) {
      Visibility a = a.getVisibility();
      if (a != null) {
         Visibility a = TypeUtils.getVisibility(a);
         String a = "visibility of " + a + " method in " + a;
         if (a.ordinal() > a.ordinal()) {
            a.printMessage(Kind.WARNING, a + " " + a + " method cannot reduce " + a, a, a);
         } else if (a == Visibility.PRIVATE && a.ordinal() > a.ordinal()) {
            a.printMessage(Kind.WARNING, a + " " + a + " method will upgrade " + a, a, a);
         }

      }
   }

   protected final void validateTargetField(VariableElement a, AnnotationHandle a, AnnotatedMixinElementHandler.AliasedElementName a, String a) {
      String a = a.asType().toString();
      Iterator var6 = a.mixin.getTargets().iterator();

      while(true) {
         TypeHandle a;
         FieldHandle a;
         do {
            do {
               if (!var6.hasNext()) {
                  return;
               }

               a = (TypeHandle)var6.next();
            } while(a.isImaginary());

            a = a.findField(a);
         } while(a != null);

         List<String> a = a.getAliases();
         Iterator var10 = a.iterator();

         while(var10.hasNext()) {
            String a = (String)var10.next();
            if ((a = a.findField(a, a)) != null) {
               break;
            }
         }

         if (a == null) {
            a.ap.printMessage(Kind.WARNING, "Cannot find target for " + a + " field in " + a, a, a.asMirror());
         }
      }
   }

   protected final void validateReferencedTarget(ExecutableElement a, AnnotationHandle a, MemberInfo a, String a) {
      String a = a.toDescriptor();
      Iterator var6 = a.mixin.getTargets().iterator();

      while(var6.hasNext()) {
         TypeHandle a = (TypeHandle)var6.next();
         if (!a.isImaginary()) {
            MethodHandle a = a.findMethod(a.name, a);
            if (a == null) {
               a.ap.printMessage(Kind.WARNING, "Cannot find target method for " + a + " in " + a, a, a.asMirror());
            }
         }
      }

   }

   private void printMessage(Kind a, String a, Element a, AnnotationHandle a) {
      if (a == null) {
         a.ap.printMessage(a, a, a);
      } else {
         a.ap.printMessage(a, a, a, a.asMirror());
      }

   }

   protected static <T extends IMapping<T>> ObfuscationData<T> stripOwnerData(ObfuscationData<T> a) {
      ObfuscationData<T> a = new ObfuscationData();
      Iterator var2 = a.iterator();

      while(var2.hasNext()) {
         ObfuscationType a = (ObfuscationType)var2.next();
         T a = (IMapping)a.get(a);
         a.put(a, a.move((String)null));
      }

      return a;
   }

   protected static <T extends IMapping<T>> ObfuscationData<T> stripDescriptors(ObfuscationData<T> a) {
      ObfuscationData<T> a = new ObfuscationData();
      Iterator var2 = a.iterator();

      while(var2.hasNext()) {
         ObfuscationType a = (ObfuscationType)var2.next();
         T a = (IMapping)a.get(a);
         a.put(a, a.transform((String)null));
      }

      return a;
   }

   static class ShadowElementName extends AnnotatedMixinElementHandler.AliasedElementName {
      private final boolean hasPrefix;
      private final String prefix;
      private final String baseName;
      private String obfuscated;

      ShadowElementName(Element a, AnnotationHandle a) {
         super(a, a);
         a.prefix = (String)a.getValue("prefix", "shadow$");
         boolean a = false;
         String a = a.originalName;
         if (a.startsWith(a.prefix)) {
            a = true;
            a = a.substring(a.prefix.length());
         }

         a.hasPrefix = a;
         a.obfuscated = a.baseName = a;
      }

      public String toString() {
         return a.baseName;
      }

      public String baseName() {
         return a.baseName;
      }

      public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping<?> a) {
         a.obfuscated = a.getName();
         return a;
      }

      public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String a) {
         a.obfuscated = a;
         return a;
      }

      public boolean hasPrefix() {
         return a.hasPrefix;
      }

      public String prefix() {
         return a.hasPrefix ? a.prefix : "";
      }

      public String name() {
         return a.prefix(a.baseName);
      }

      public String obfuscated() {
         return a.prefix(a.obfuscated);
      }

      public String prefix(String a) {
         return a.hasPrefix ? a.prefix + a : a;
      }
   }

   static class AliasedElementName {
      protected final String originalName;
      private final List<String> aliases;
      private boolean caseSensitive;

      public AliasedElementName(Element a, AnnotationHandle a) {
         a.originalName = a.getSimpleName().toString();
         a.aliases = a.getList("aliases");
      }

      public AnnotatedMixinElementHandler.AliasedElementName setCaseSensitive(boolean a) {
         a.caseSensitive = a;
         return a;
      }

      public boolean isCaseSensitive() {
         return a.caseSensitive;
      }

      public boolean hasAliases() {
         return a.aliases.size() > 0;
      }

      public List<String> getAliases() {
         return a.aliases;
      }

      public String elementName() {
         return a.originalName;
      }

      public String baseName() {
         return a.originalName;
      }

      public boolean hasPrefix() {
         return false;
      }
   }

   abstract static class AnnotatedElement<E extends Element> {
      protected final E element;
      protected final AnnotationHandle annotation;
      private final String desc;

      public AnnotatedElement(E a, AnnotationHandle a) {
         a.element = a;
         a.annotation = a;
         a.desc = TypeUtils.getDescriptor(a);
      }

      public E getElement() {
         return a.element;
      }

      public AnnotationHandle getAnnotation() {
         return a.annotation;
      }

      public String getSimpleName() {
         return a.getElement().getSimpleName().toString();
      }

      public String getDesc() {
         return a.desc;
      }

      public final void printMessage(Messager a, Kind a, CharSequence a) {
         a.printMessage(a, a, a.element, a.annotation.asMirror());
      }
   }
}

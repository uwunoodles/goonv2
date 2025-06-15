package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

@SupportedAnnotationTypes({"org.spongepowered.asm.mixin.Mixin", "org.spongepowered.asm.mixin.Shadow", "org.spongepowered.asm.mixin.Overwrite", "org.spongepowered.asm.mixin.gen.Accessor", "org.spongepowered.asm.mixin.Implements"})
public class MixinObfuscationProcessorTargets extends MixinObfuscationProcessor {
   public boolean process(Set<? extends TypeElement> a1, RoundEnvironment a) {
      if (a.processingOver()) {
         a.postProcess(a);
         return true;
      } else {
         a.processMixins(a);
         a.processShadows(a);
         a.processOverwrites(a);
         a.processAccessors(a);
         a.processInvokers(a);
         a.processImplements(a);
         a.postProcess(a);
         return true;
      }
   }

   protected void postProcess(RoundEnvironment a) {
      super.postProcess(a);

      try {
         a.mixins.writeReferences();
         a.mixins.writeMappings();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void processShadows(RoundEnvironment a) {
      Iterator var2 = a.getElementsAnnotatedWith(Shadow.class).iterator();

      while(var2.hasNext()) {
         Element a = (Element)var2.next();
         Element a = a.getEnclosingElement();
         if (!(a instanceof TypeElement)) {
            a.mixins.printMessage(Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(a), a);
         } else {
            AnnotationHandle a = AnnotationHandle.of(a, Shadow.class);
            if (a.getKind() == ElementKind.FIELD) {
               a.mixins.registerShadow((TypeElement)a, (VariableElement)a, a);
            } else if (a.getKind() == ElementKind.METHOD) {
               a.mixins.registerShadow((TypeElement)a, (ExecutableElement)a, a);
            } else {
               a.mixins.printMessage(Kind.ERROR, "Element is not a method or field", a);
            }
         }
      }

   }

   private void processOverwrites(RoundEnvironment a) {
      Iterator var2 = a.getElementsAnnotatedWith(Overwrite.class).iterator();

      while(var2.hasNext()) {
         Element a = (Element)var2.next();
         Element a = a.getEnclosingElement();
         if (!(a instanceof TypeElement)) {
            a.mixins.printMessage(Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(a), a);
         } else if (a.getKind() == ElementKind.METHOD) {
            a.mixins.registerOverwrite((TypeElement)a, (ExecutableElement)a);
         } else {
            a.mixins.printMessage(Kind.ERROR, "Element is not a method", a);
         }
      }

   }

   private void processAccessors(RoundEnvironment a) {
      Iterator var2 = a.getElementsAnnotatedWith(Accessor.class).iterator();

      while(var2.hasNext()) {
         Element a = (Element)var2.next();
         Element a = a.getEnclosingElement();
         if (!(a instanceof TypeElement)) {
            a.mixins.printMessage(Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(a), a);
         } else if (a.getKind() == ElementKind.METHOD) {
            a.mixins.registerAccessor((TypeElement)a, (ExecutableElement)a);
         } else {
            a.mixins.printMessage(Kind.ERROR, "Element is not a method", a);
         }
      }

   }

   private void processInvokers(RoundEnvironment a) {
      Iterator var2 = a.getElementsAnnotatedWith(Invoker.class).iterator();

      while(var2.hasNext()) {
         Element a = (Element)var2.next();
         Element a = a.getEnclosingElement();
         if (!(a instanceof TypeElement)) {
            a.mixins.printMessage(Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(a), a);
         } else if (a.getKind() == ElementKind.METHOD) {
            a.mixins.registerInvoker((TypeElement)a, (ExecutableElement)a);
         } else {
            a.mixins.printMessage(Kind.ERROR, "Element is not a method", a);
         }
      }

   }

   private void processImplements(RoundEnvironment a) {
      Iterator var2 = a.getElementsAnnotatedWith(Implements.class).iterator();

      while(true) {
         while(var2.hasNext()) {
            Element a = (Element)var2.next();
            if (a.getKind() != ElementKind.CLASS && a.getKind() != ElementKind.INTERFACE) {
               a.mixins.printMessage(Kind.ERROR, "Found an @Implements annotation on an element which is not a class or interface", a);
            } else {
               AnnotationHandle a = AnnotationHandle.of(a, Implements.class);
               a.mixins.registerSoftImplements((TypeElement)a, a);
            }
         }

         return;
      }
   }
}

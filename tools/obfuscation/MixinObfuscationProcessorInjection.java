package org.spongepowered.tools.obfuscation;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

@SupportedAnnotationTypes({"org.spongepowered.asm.mixin.injection.Inject", "org.spongepowered.asm.mixin.injection.ModifyArg", "org.spongepowered.asm.mixin.injection.ModifyArgs", "org.spongepowered.asm.mixin.injection.Redirect", "org.spongepowered.asm.mixin.injection.At"})
public class MixinObfuscationProcessorInjection extends MixinObfuscationProcessor {
   public boolean process(Set<? extends TypeElement> a1, RoundEnvironment a) {
      if (a.processingOver()) {
         a.postProcess(a);
         return true;
      } else {
         a.processMixins(a);
         a.processInjectors(a, Inject.class);
         a.processInjectors(a, ModifyArg.class);
         a.processInjectors(a, ModifyArgs.class);
         a.processInjectors(a, Redirect.class);
         a.processInjectors(a, ModifyVariable.class);
         a.processInjectors(a, ModifyConstant.class);
         a.postProcess(a);
         return true;
      }
   }

   protected void postProcess(RoundEnvironment a) {
      super.postProcess(a);

      try {
         a.mixins.writeReferences();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void processInjectors(RoundEnvironment a, Class<? extends Annotation> a) {
      Iterator var3 = a.getElementsAnnotatedWith(a).iterator();

      while(var3.hasNext()) {
         Element a = (Element)var3.next();
         Element a = a.getEnclosingElement();
         if (!(a instanceof TypeElement)) {
            throw new IllegalStateException("@" + a.getSimpleName() + " element has unexpected parent with type " + TypeUtils.getElementType(a));
         }

         AnnotationHandle a = AnnotationHandle.of(a, a);
         if (a.getKind() == ElementKind.METHOD) {
            a.mixins.registerInjector((TypeElement)a, (ExecutableElement)a, a);
         } else {
            a.mixins.printMessage(Kind.WARNING, "Found an @" + a.getSimpleName() + " annotation on an element which is not a method: " + a.toString());
         }
      }

   }
}

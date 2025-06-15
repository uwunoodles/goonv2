package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Mixin;

public abstract class MixinObfuscationProcessor extends AbstractProcessor {
   protected AnnotatedMixins mixins;

   public synchronized void init(ProcessingEnvironment a) {
      super.init(a);
      a.mixins = AnnotatedMixins.getMixinsForEnvironment(a);
   }

   protected void processMixins(RoundEnvironment a) {
      a.mixins.onPassStarted();
      Iterator var2 = a.getElementsAnnotatedWith(Mixin.class).iterator();

      while(true) {
         while(var2.hasNext()) {
            Element a = (Element)var2.next();
            if (a.getKind() != ElementKind.CLASS && a.getKind() != ElementKind.INTERFACE) {
               a.mixins.printMessage(Kind.ERROR, "Found an @Mixin annotation on an element which is not a class or interface", a);
            } else {
               a.mixins.registerMixin((TypeElement)a);
            }
         }

         return;
      }
   }

   protected void postProcess(RoundEnvironment a) {
      a.mixins.onPassCompleted(a);
   }

   public SourceVersion getSupportedSourceVersion() {
      try {
         return SourceVersion.valueOf("RELEASE_8");
      } catch (IllegalArgumentException var2) {
         return super.getSupportedSourceVersion();
      }
   }

   public Set<String> getSupportedOptions() {
      return SupportedOptions.getAllOptions();
   }
}

package org.spongepowered.tools.obfuscation;

import java.lang.reflect.Method;
import java.util.Iterator;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerOverwrite(IMixinAnnotationProcessor a, AnnotatedMixin a) {
      super(a, a);
   }

   public void registerMerge(ExecutableElement a) {
      a.validateTargetMethod(a, (AnnotationHandle)null, new AnnotatedMixinElementHandler.AliasedElementName(a, AnnotationHandle.MISSING), "overwrite", true, true);
   }

   public void registerOverwrite(AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite a) {
      AnnotatedMixinElementHandler.AliasedElementName a = new AnnotatedMixinElementHandler.AliasedElementName(a.getElement(), a.getAnnotation());
      a.validateTargetMethod((ExecutableElement)a.getElement(), a.getAnnotation(), a, "@Overwrite", true, false);
      a.checkConstraints((ExecutableElement)a.getElement(), a.getAnnotation());
      if (a.shouldRemap()) {
         Iterator var3 = a.mixin.getTargets().iterator();

         while(var3.hasNext()) {
            TypeHandle a = (TypeHandle)var3.next();
            if (!a.registerOverwriteForTarget(a, a)) {
               return;
            }
         }
      }

      if (!"true".equalsIgnoreCase(a.ap.getOption("disableOverwriteChecker"))) {
         Kind a = "error".equalsIgnoreCase(a.ap.getOption("overwriteErrorLevel")) ? Kind.ERROR : Kind.WARNING;
         String a = a.ap.getJavadocProvider().getJavadoc(a.getElement());
         if (a == null) {
            a.ap.printMessage(a, "@Overwrite is missing javadoc comment", a.getElement());
            return;
         }

         if (!a.toLowerCase().contains("@author")) {
            a.ap.printMessage(a, "@Overwrite is missing an @author tag", a.getElement());
         }

         if (!a.toLowerCase().contains("@reason")) {
            a.ap.printMessage(a, "@Overwrite is missing an @reason tag", a.getElement());
         }
      }

   }

   private boolean registerOverwriteForTarget(AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite a, TypeHandle a) {
      MappingMethod a = a.getMappingMethod(a.getSimpleName(), a.getDesc());
      ObfuscationData<MappingMethod> a = a.obf.getDataProvider().getObfMethod(a);
      if (a.isEmpty()) {
         Kind a = Kind.ERROR;

         try {
            Method a = ((ExecutableElement)a.getElement()).getClass().getMethod("isStatic");
            if ((Boolean)a.invoke(a.getElement())) {
               a = Kind.WARNING;
            }
         } catch (Exception var7) {
         }

         a.ap.printMessage(a, "No obfuscation mapping for @Overwrite method", a.getElement());
         return false;
      } else {
         try {
            a.addMethodMappings(a.getSimpleName(), a.getDesc(), a);
            return true;
         } catch (Mappings.MappingConflictException var8) {
            a.printMessage(a.ap, Kind.ERROR, "Mapping conflict for @Overwrite method: " + var8.getNew().getSimpleName() + " for target " + a + " conflicts with existing mapping " + var8.getOld().getSimpleName());
            return false;
         }
      }
   }

   static class AnnotatedElementOverwrite extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
      private final boolean shouldRemap;

      public AnnotatedElementOverwrite(ExecutableElement a, AnnotationHandle a, boolean a) {
         super(a, a);
         a.shouldRemap = a;
      }

      public boolean shouldRemap() {
         return a.shouldRemap;
      }
   }
}

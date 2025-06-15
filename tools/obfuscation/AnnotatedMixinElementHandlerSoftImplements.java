package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class AnnotatedMixinElementHandlerSoftImplements extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerSoftImplements(IMixinAnnotationProcessor a, AnnotatedMixin a) {
      super(a, a);
   }

   public void process(AnnotationHandle a) {
      if (a.mixin.remap()) {
         List<AnnotationHandle> a = a.getAnnotationList("value");
         if (a.size() < 1) {
            a.ap.printMessage(Kind.WARNING, "Empty @Implements annotation", a.mixin.getMixin(), a.asMirror());
         } else {
            Iterator var3 = a.iterator();

            while(var3.hasNext()) {
               AnnotationHandle a = (AnnotationHandle)var3.next();
               Interface.Remap a = (Interface.Remap)a.getValue("remap", Interface.Remap.ALL);
               if (a != Interface.Remap.NONE) {
                  try {
                     TypeHandle a = new TypeHandle((DeclaredType)a.getValue("iface"));
                     String a = (String)a.getValue("prefix");
                     a.processSoftImplements(a, a, a);
                  } catch (Exception var8) {
                     a.ap.printMessage(Kind.ERROR, "Unexpected error: " + var8.getClass().getName() + ": " + var8.getMessage(), a.mixin.getMixin(), a.asMirror());
                  }
               }
            }

         }
      }
   }

   private void processSoftImplements(Interface.Remap a, TypeHandle a, String a) {
      Iterator var4 = a.getEnclosedElements(ElementKind.METHOD).iterator();

      while(var4.hasNext()) {
         ExecutableElement a = (ExecutableElement)var4.next();
         a.processMethod(a, a, a, a);
      }

      var4 = a.getInterfaces().iterator();

      while(var4.hasNext()) {
         TypeHandle a = (TypeHandle)var4.next();
         a.processSoftImplements(a, a, a);
      }

   }

   private void processMethod(Interface.Remap a, TypeHandle a, String a, ExecutableElement a) {
      String a = a.getSimpleName().toString();
      String a = TypeUtils.getJavaSignature((Element)a);
      String a = TypeUtils.getDescriptor(a);
      MethodHandle a;
      if (a != Interface.Remap.ONLY_PREFIXED) {
         a = a.mixin.getHandle().findMethod(a, a);
         if (a != null) {
            a.addInterfaceMethodMapping(a, a, (String)null, a, a, a);
         }
      }

      if (a != null) {
         a = a.mixin.getHandle().findMethod(a + a, a);
         if (a != null) {
            a.addInterfaceMethodMapping(a, a, a, a, a, a);
         }
      }

   }

   private void addInterfaceMethodMapping(Interface.Remap a, TypeHandle a, String a, MethodHandle a, String a, String a) {
      MappingMethod a = new MappingMethod(a.getName(), a, a);
      ObfuscationData<MappingMethod> a = a.obf.getDataProvider().getObfMethod(a);
      if (a.isEmpty()) {
         if (a.forceRemap()) {
            a.ap.printMessage(Kind.ERROR, "No obfuscation mapping for soft-implementing method", a.getElement());
         }

      } else {
         a.addMethodMappings(a.getName(), a, a.applyPrefix(a, a));
      }
   }

   private ObfuscationData<MappingMethod> applyPrefix(ObfuscationData<MappingMethod> a, String a) {
      if (a == null) {
         return a;
      } else {
         ObfuscationData<MappingMethod> a = new ObfuscationData();
         Iterator var4 = a.iterator();

         while(var4.hasNext()) {
            ObfuscationType a = (ObfuscationType)var4.next();
            MappingMethod a = (MappingMethod)a.get(a);
            a.put(a, a.addPrefix(a));
         }

         return a;
      }
   }
}

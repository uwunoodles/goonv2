package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor a, AnnotatedMixin a) {
      super(a, a);
   }

   public void registerShadow(AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow<?, ?> a) {
      a.validateTarget(a.getElement(), a.getAnnotation(), a.getName(), "@Shadow");
      if (a.shouldRemap()) {
         Iterator var2 = a.mixin.getTargets().iterator();

         while(var2.hasNext()) {
            TypeHandle a = (TypeHandle)var2.next();
            a.registerShadowForTarget(a, a);
         }

      }
   }

   private void registerShadowForTarget(AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow<?, ?> a, TypeHandle a) {
      ObfuscationData<? extends IMapping<?>> a = a.getObfuscationData(a.obf.getDataProvider(), a);
      if (a.isEmpty()) {
         String a = a.mixin.isMultiTarget() ? " in target " + a : "";
         if (a.isSimulated()) {
            a.printMessage(a.ap, Kind.WARNING, "Unable to locate obfuscation mapping" + a + " for @Shadow " + a);
         } else {
            a.printMessage(a.ap, Kind.WARNING, "Unable to locate obfuscation mapping" + a + " for @Shadow " + a);
         }

      } else {
         Iterator var4 = a.iterator();

         while(var4.hasNext()) {
            ObfuscationType a = (ObfuscationType)var4.next();

            try {
               a.addMapping(a, (IMapping)a.get(a));
            } catch (Mappings.MappingConflictException var7) {
               a.printMessage(a.ap, Kind.ERROR, "Mapping conflict for @Shadow " + a + ": " + var7.getNew().getSimpleName() + " for target " + a + " conflicts with existing mapping " + var7.getOld().getSimpleName());
            }
         }

      }
   }

   class AnnotatedElementShadowMethod extends AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow<ExecutableElement, MappingMethod> {
      public AnnotatedElementShadowMethod(ExecutableElement axxxx, AnnotationHandle ax, boolean axxx) {
         super(axxxx, ax, axxx, IMapping.Type.METHOD);
      }

      public MappingMethod getMapping(TypeHandle axx, String axxx, String ax) {
         return axx.getMappingMethod(axxx, ax);
      }

      public void addMapping(ObfuscationType ax, IMapping<?> axx) {
         AnnotatedMixinElementHandlerShadow.this.addMethodMapping(ax, a.setObfuscatedName(axx), a.getDesc(), axx.getDesc());
      }
   }

   class AnnotatedElementShadowField extends AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow<VariableElement, MappingField> {
      public AnnotatedElementShadowField(VariableElement axxxx, AnnotationHandle ax, boolean axxx) {
         super(axxxx, ax, axxx, IMapping.Type.FIELD);
      }

      public MappingField getMapping(TypeHandle axx, String axxx, String ax) {
         return new MappingField(axx.getName(), axxx, ax);
      }

      public void addMapping(ObfuscationType ax, IMapping<?> axx) {
         AnnotatedMixinElementHandlerShadow.this.addFieldMapping(ax, a.setObfuscatedName(axx), a.getDesc(), axx.getDesc());
      }
   }

   abstract static class AnnotatedElementShadow<E extends Element, M extends IMapping<M>> extends AnnotatedMixinElementHandler.AnnotatedElement<E> {
      private final boolean shouldRemap;
      private final AnnotatedMixinElementHandler.ShadowElementName name;
      private final IMapping.Type type;

      protected AnnotatedElementShadow(E a, AnnotationHandle a, boolean a, IMapping.Type a) {
         super(a, a);
         a.shouldRemap = a;
         a.name = new AnnotatedMixinElementHandler.ShadowElementName(a, a);
         a.type = a;
      }

      public boolean shouldRemap() {
         return a.shouldRemap;
      }

      public AnnotatedMixinElementHandler.ShadowElementName getName() {
         return a.name;
      }

      public IMapping.Type getElementType() {
         return a.type;
      }

      public String toString() {
         return a.getElementType().name().toLowerCase();
      }

      public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping<?> a) {
         return a.setObfuscatedName(a.getSimpleName());
      }

      public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String a) {
         return a.getName().setObfuscatedName(a);
      }

      public ObfuscationData<M> getObfuscationData(IObfuscationDataProvider a, TypeHandle a) {
         return a.getObfEntry(a.getMapping(a, a.getName().toString(), a.getDesc()));
      }

      public abstract M getMapping(TypeHandle var1, String var2, String var3);

      public abstract void addMapping(ObfuscationType var1, IMapping<?> var2);
   }
}

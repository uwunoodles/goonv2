package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixin {
   private final AnnotationHandle annotation;
   private final Messager messager;
   private final ITypeHandleProvider typeProvider;
   private final IObfuscationManager obf;
   private final IMappingConsumer mappings;
   private final TypeElement mixin;
   private final List<ExecutableElement> methods;
   private final TypeHandle handle;
   private final List<TypeHandle> targets = new ArrayList();
   private final TypeHandle primaryTarget;
   private final String classRef;
   private final boolean remap;
   private final boolean virtual;
   private final AnnotatedMixinElementHandlerOverwrite overwrites;
   private final AnnotatedMixinElementHandlerShadow shadows;
   private final AnnotatedMixinElementHandlerInjector injectors;
   private final AnnotatedMixinElementHandlerAccessor accessors;
   private final AnnotatedMixinElementHandlerSoftImplements softImplements;
   private boolean validated = false;

   public AnnotatedMixin(IMixinAnnotationProcessor a, TypeElement a) {
      a.typeProvider = a.getTypeProvider();
      a.obf = a.getObfuscationManager();
      a.mappings = a.obf.createMappingConsumer();
      a.messager = a;
      a.mixin = a;
      a.handle = new TypeHandle(a);
      a.methods = new ArrayList(a.handle.getEnclosedElements(ElementKind.METHOD));
      a.virtual = a.handle.getAnnotation(Pseudo.class).exists();
      a.annotation = a.handle.getAnnotation(Mixin.class);
      a.classRef = TypeUtils.getInternalName(a);
      a.primaryTarget = a.initTargets();
      a.remap = a.annotation.getBoolean("remap", true) && a.targets.size() > 0;
      a.overwrites = new AnnotatedMixinElementHandlerOverwrite(a, a);
      a.shadows = new AnnotatedMixinElementHandlerShadow(a, a);
      a.injectors = new AnnotatedMixinElementHandlerInjector(a, a);
      a.accessors = new AnnotatedMixinElementHandlerAccessor(a, a);
      a.softImplements = new AnnotatedMixinElementHandlerSoftImplements(a, a);
   }

   AnnotatedMixin runValidators(IMixinValidator.ValidationPass a, Collection<IMixinValidator> a) {
      Iterator var3 = a.iterator();

      while(var3.hasNext()) {
         IMixinValidator a = (IMixinValidator)var3.next();
         if (!a.validate(a, a.mixin, a.annotation, a.targets)) {
            break;
         }
      }

      if (a == IMixinValidator.ValidationPass.FINAL && !a.validated) {
         a.validated = true;
         a.runFinalValidation();
      }

      return a;
   }

   private TypeHandle initTargets() {
      TypeHandle a = null;

      Iterator var2;
      TypeHandle a;
      try {
         var2 = a.annotation.getList().iterator();

         while(var2.hasNext()) {
            TypeMirror a = (TypeMirror)var2.next();
            a = new TypeHandle((DeclaredType)a);
            if (!a.targets.contains(a)) {
               a.addTarget(a);
               if (a == null) {
                  a = a;
               }
            }
         }
      } catch (Exception var6) {
         a.printMessage(Kind.WARNING, "Error processing public targets: " + var6.getClass().getName() + ": " + var6.getMessage(), a);
      }

      try {
         var2 = a.annotation.getList("targets").iterator();

         while(var2.hasNext()) {
            String a = (String)var2.next();
            a = a.typeProvider.getTypeHandle(a);
            if (!a.targets.contains(a)) {
               if (a.virtual) {
                  a = a.typeProvider.getSimulatedHandle(a, a.mixin.asType());
               } else {
                  if (a == null) {
                     a.printMessage(Kind.ERROR, "Mixin target " + a + " could not be found", a);
                     return null;
                  }

                  if (a.isPublic()) {
                     a.printMessage(Kind.WARNING, "Mixin target " + a + " is public and must be specified in value", a);
                     return null;
                  }
               }

               a.addSoftTarget(a, a);
               if (a == null) {
                  a = a;
               }
            }
         }
      } catch (Exception var5) {
         a.printMessage(Kind.WARNING, "Error processing private targets: " + var5.getClass().getName() + ": " + var5.getMessage(), a);
      }

      if (a == null) {
         a.printMessage(Kind.ERROR, "Mixin has no targets", a);
      }

      return a;
   }

   private void printMessage(Kind a, CharSequence a, AnnotatedMixin a3) {
      a.messager.printMessage(a, a, a.mixin, a.annotation.asMirror());
   }

   private void addSoftTarget(TypeHandle a, String a) {
      ObfuscationData<String> a = a.obf.getDataProvider().getObfClass(a);
      if (!a.isEmpty()) {
         a.obf.getReferenceManager().addClassMapping(a.classRef, a, a);
      }

      a.addTarget(a);
   }

   private void addTarget(TypeHandle a) {
      a.targets.add(a);
   }

   public String toString() {
      return a.mixin.getSimpleName().toString();
   }

   public AnnotationHandle getAnnotation() {
      return a.annotation;
   }

   public TypeElement getMixin() {
      return a.mixin;
   }

   public TypeHandle getHandle() {
      return a.handle;
   }

   public String getClassRef() {
      return a.classRef;
   }

   public boolean isInterface() {
      return a.mixin.getKind() == ElementKind.INTERFACE;
   }

   /** @deprecated */
   @Deprecated
   public TypeHandle getPrimaryTarget() {
      return a.primaryTarget;
   }

   public List<TypeHandle> getTargets() {
      return a.targets;
   }

   public boolean isMultiTarget() {
      return a.targets.size() > 1;
   }

   public boolean remap() {
      return a.remap;
   }

   public IMappingConsumer getMappings() {
      return a.mappings;
   }

   private void runFinalValidation() {
      Iterator var1 = a.methods.iterator();

      while(var1.hasNext()) {
         ExecutableElement a = (ExecutableElement)var1.next();
         a.overwrites.registerMerge(a);
      }

   }

   public void registerOverwrite(ExecutableElement a, AnnotationHandle a, boolean a) {
      a.methods.remove(a);
      a.overwrites.registerOverwrite(new AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite(a, a, a));
   }

   public void registerShadow(VariableElement a, AnnotationHandle a, boolean a) {
      a.shadows.registerShadow(a.shadows.new AnnotatedElementShadowField(a, a, a));
   }

   public void registerShadow(ExecutableElement a, AnnotationHandle a, boolean a) {
      a.methods.remove(a);
      a.shadows.registerShadow(a.shadows.new AnnotatedElementShadowMethod(a, a, a));
   }

   public void registerInjector(ExecutableElement a, AnnotationHandle a, InjectorRemap a) {
      a.methods.remove(a);
      a.injectors.registerInjector(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector(a, a, a));
      List<AnnotationHandle> a = a.getAnnotationList("at");
      Iterator var5 = a.iterator();

      while(var5.hasNext()) {
         AnnotationHandle a = (AnnotationHandle)var5.next();
         a.registerInjectionPoint(a, a, a, a, "@At(%s)");
      }

      List<AnnotationHandle> a = a.getAnnotationList("slice");
      Iterator var12 = a.iterator();

      while(var12.hasNext()) {
         AnnotationHandle a = (AnnotationHandle)var12.next();
         String a = (String)a.getValue("id", "");
         AnnotationHandle a = a.getAnnotation("from");
         if (a != null) {
            a.registerInjectionPoint(a, a, a, a, "@Slice[" + a + "](from=@At(%s))");
         }

         AnnotationHandle a = a.getAnnotation("to");
         if (a != null) {
            a.registerInjectionPoint(a, a, a, a, "@Slice[" + a + "](to=@At(%s))");
         }
      }

   }

   public void registerInjectionPoint(ExecutableElement a, AnnotationHandle a, AnnotationHandle a, InjectorRemap a, String a) {
      a.injectors.registerInjectionPoint(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint(a, a, a, a), a);
   }

   public void registerAccessor(ExecutableElement a, AnnotationHandle a, boolean a) {
      a.methods.remove(a);
      a.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor(a, a, a));
   }

   public void registerInvoker(ExecutableElement a, AnnotationHandle a, boolean a) {
      a.methods.remove(a);
      a.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker(a, a, a));
   }

   public void registerSoftImplements(AnnotationHandle a) {
      a.softImplements.process(a);
   }
}

package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

class AnnotatedMixinElementHandlerInjector extends AnnotatedMixinElementHandler {
   AnnotatedMixinElementHandlerInjector(IMixinAnnotationProcessor a, AnnotatedMixin a) {
      super(a, a);
   }

   public void registerInjector(AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector a) {
      if (a.mixin.isInterface()) {
         a.ap.printMessage(Kind.ERROR, "Injector in interface is unsupported", a.getElement());
      }

      Iterator var2 = a.getAnnotation().getList("method").iterator();

      while(true) {
         String a;
         MemberInfo a;
         do {
            do {
               if (!var2.hasNext()) {
                  return;
               }

               a = (String)var2.next();
               a = MemberInfo.parse(a);
            } while(a.name == null);

            try {
               a.validate();
            } catch (InvalidMemberDescriptorException var7) {
               a.printMessage(a.ap, Kind.ERROR, var7.getMessage());
            }

            if (a.desc != null) {
               a.validateReferencedTarget((ExecutableElement)a.getElement(), a.getAnnotation(), a, a.toString());
            }
         } while(!a.shouldRemap());

         Iterator var5 = a.mixin.getTargets().iterator();

         while(var5.hasNext()) {
            TypeHandle a = (TypeHandle)var5.next();
            if (!a.registerInjector(a, a, a, a)) {
               break;
            }
         }
      }
   }

   private boolean registerInjector(AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector a, String a, MemberInfo a, TypeHandle a) {
      String a = a.findDescriptor(a);
      if (a == null) {
         Kind a = a.mixin.isMultiTarget() ? Kind.ERROR : Kind.WARNING;
         if (a.isSimulated()) {
            a.printMessage(a.ap, Kind.NOTE, a + " target '" + a + "' in @Pseudo mixin will not be obfuscated");
         } else if (a.isImaginary()) {
            a.printMessage(a.ap, a, a + " target requires method signature because enclosing type information for " + a + " is unavailable");
         } else if (!a.isInitialiser()) {
            a.printMessage(a.ap, a, "Unable to determine signature for " + a + " target method");
         }

         return true;
      } else {
         String a = a + " target " + a.name;
         MappingMethod a = a.getMappingMethod(a.name, a);
         ObfuscationData<MappingMethod> a = a.obf.getDataProvider().getObfMethod(a);
         if (a.isEmpty()) {
            if (!a.isSimulated()) {
               if (a.isClassInitialiser()) {
                  return true;
               }

               Kind a = a.isConstructor() ? Kind.WARNING : Kind.ERROR;
               a.addMessage(a, "No obfuscation mapping for " + a, a.getElement(), a.getAnnotation());
               return false;
            }

            a = a.obf.getDataProvider().getRemappedMethod(a);
         }

         IReferenceManager a = a.obf.getReferenceManager();

         try {
            if (a.owner == null && a.mixin.isMultiTarget() || a.isSimulated()) {
               a = AnnotatedMixinElementHandler.stripOwnerData(a);
            }

            a.addMethodMapping(a.classRef, a, a);
         } catch (ReferenceManager.ReferenceConflictException var14) {
            String a = a.mixin.isMultiTarget() ? "Multi-target" : "Target";
            if (a.hasCoerceArgument() && a.owner == null && a.desc == null) {
               MemberInfo a = MemberInfo.parse(var14.getOld());
               MemberInfo a = MemberInfo.parse(var14.getNew());
               if (a.name.equals(a.name)) {
                  a = AnnotatedMixinElementHandler.stripDescriptors(a);
                  a.setAllowConflicts(true);
                  a.addMethodMapping(a.classRef, a, a);
                  a.setAllowConflicts(false);
                  a.printMessage(a.ap, Kind.WARNING, "Coerced " + a + " reference has conflicting descriptors for " + a + ": Storing bare references " + a.values() + " in refMap");
                  return true;
               }
            }

            a.printMessage(a.ap, Kind.ERROR, a + " reference conflict for " + a + ": " + a + " -> " + var14.getNew() + " previously defined as " + var14.getOld());
         }

         return true;
      }
   }

   public void registerInjectionPoint(AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint a, String a) {
      if (a.mixin.isInterface()) {
         a.ap.printMessage(Kind.ERROR, "Injector in interface is unsupported", a.getElement());
      }

      if (a.shouldRemap()) {
         String a = InjectionPointData.parseType((String)a.getAt().getValue("value"));
         String a = (String)a.getAt().getValue("target");
         if ("NEW".equals(a)) {
            a.remapNewTarget(String.format(a, a + ".<target>"), a, a);
            a.remapNewTarget(String.format(a, a + ".args[class]"), a.getAtArg("class"), a);
         } else {
            a.remapReference(String.format(a, a + ".<target>"), a, a);
         }

      }
   }

   protected final void remapNewTarget(String a, String a, AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint a) {
      if (a != null) {
         MemberInfo a = MemberInfo.parse(a);
         String a = a.toCtorType();
         if (a != null) {
            String a = a.toCtorDesc();
            MappingMethod a = new MappingMethod(a, ".", a != null ? a : "()V");
            ObfuscationData<MappingMethod> a = a.obf.getDataProvider().getRemappedMethod(a);
            if (a.isEmpty()) {
               a.ap.printMessage(Kind.WARNING, "Cannot find class mapping for " + a + " '" + a + "'", a.getElement(), a.getAnnotation().asMirror());
               return;
            }

            ObfuscationData<String> a = new ObfuscationData();
            Iterator var10 = a.iterator();

            while(var10.hasNext()) {
               ObfuscationType a = (ObfuscationType)var10.next();
               MappingMethod a = (MappingMethod)a.get(a);
               if (a == null) {
                  a.put(a, a.getOwner());
               } else {
                  a.put(a, a.getDesc().replace(")V", ")L" + a.getOwner() + ";"));
               }
            }

            a.obf.getReferenceManager().addClassMapping(a.classRef, a, a);
         }

         a.notifyRemapped();
      }
   }

   protected final void remapReference(String a, String a, AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint a) {
      if (a != null) {
         AnnotationMirror a = (a.ap.getCompilerEnvironment() == IMixinAnnotationProcessor.CompilerEnvironment.JDT ? a.getAt() : a.getAnnotation()).asMirror();
         MemberInfo a = MemberInfo.parse(a);
         if (!a.isFullyQualified()) {
            String a = a.owner == null ? (a.desc == null ? "owner and signature" : "owner") : "signature";
            a.ap.printMessage(Kind.ERROR, a + " is not fully qualified, missing " + a, a.getElement(), a);
         } else {
            try {
               a.validate();
            } catch (InvalidMemberDescriptorException var7) {
               a.ap.printMessage(Kind.ERROR, var7.getMessage(), a.getElement(), a);
            }

            try {
               ObfuscationData a;
               if (a.isField()) {
                  a = a.obf.getDataProvider().getObfFieldRecursive(a);
                  if (a.isEmpty()) {
                     a.ap.printMessage(Kind.WARNING, "Cannot find field mapping for " + a + " '" + a + "'", a.getElement(), a);
                     return;
                  }

                  a.obf.getReferenceManager().addFieldMapping(a.classRef, a, a, a);
               } else {
                  a = a.obf.getDataProvider().getObfMethodRecursive(a);
                  if (a.isEmpty() && (a.owner == null || !a.owner.startsWith("java/lang/"))) {
                     a.ap.printMessage(Kind.WARNING, "Cannot find method mapping for " + a + " '" + a + "'", a.getElement(), a);
                     return;
                  }

                  a.obf.getReferenceManager().addMethodMapping(a.classRef, a, a, a);
               }
            } catch (ReferenceManager.ReferenceConflictException var8) {
               a.ap.printMessage(Kind.ERROR, "Unexpected reference conflict for " + a + ": " + a + " -> " + var8.getNew() + " previously defined as " + var8.getOld(), a.getElement(), a);
               return;
            }

            a.notifyRemapped();
         }
      }
   }

   static class AnnotatedElementInjectionPoint extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
      private final AnnotationHandle at;
      private Map<String, String> args;
      private final InjectorRemap state;

      public AnnotatedElementInjectionPoint(ExecutableElement a, AnnotationHandle a, AnnotationHandle a, InjectorRemap a) {
         super(a, a);
         a.at = a;
         a.state = a;
      }

      public boolean shouldRemap() {
         return a.at.getBoolean("remap", a.state.shouldRemap());
      }

      public AnnotationHandle getAt() {
         return a.at;
      }

      public String getAtArg(String a) {
         if (a.args == null) {
            a.args = new HashMap();
            Iterator var2 = a.at.getList("args").iterator();

            while(var2.hasNext()) {
               String a = (String)var2.next();
               if (a != null) {
                  int a = a.indexOf(61);
                  if (a > -1) {
                     a.args.put(a.substring(0, a), a.substring(a + 1));
                  } else {
                     a.args.put(a, "");
                  }
               }
            }
         }

         return (String)a.args.get(a);
      }

      public void notifyRemapped() {
         a.state.notifyRemapped();
      }
   }

   static class AnnotatedElementInjector extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
      private final InjectorRemap state;

      public AnnotatedElementInjector(ExecutableElement a, AnnotationHandle a, InjectorRemap a) {
         super(a, a);
         a.state = a;
      }

      public boolean shouldRemap() {
         return a.state.shouldRemap();
      }

      public boolean hasCoerceArgument() {
         if (!a.annotation.toString().equals("@Inject")) {
            return false;
         } else {
            Iterator var1 = ((ExecutableElement)a.element).getParameters().iterator();
            if (var1.hasNext()) {
               VariableElement a = (VariableElement)var1.next();
               return AnnotationHandle.of(a, Coerce.class).exists();
            } else {
               return false;
            }
         }
      }

      public void addMessage(Kind a, CharSequence a, Element a, AnnotationHandle a) {
         a.state.addMessage(a, a, a, a);
      }

      public String toString() {
         return a.getAnnotation().toString();
      }
   }
}

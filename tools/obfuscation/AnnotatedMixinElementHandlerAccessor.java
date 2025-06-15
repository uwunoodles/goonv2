package org.spongepowered.tools.obfuscation;

import com.google.common.base.Strings;
import java.util.Iterator;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

public class AnnotatedMixinElementHandlerAccessor extends AnnotatedMixinElementHandler implements IMixinContext {
   public AnnotatedMixinElementHandlerAccessor(IMixinAnnotationProcessor a, AnnotatedMixin a) {
      super(a, a);
   }

   public ReferenceMapper getReferenceMapper() {
      return null;
   }

   public String getClassName() {
      return a.mixin.getClassRef().replace('/', '.');
   }

   public String getClassRef() {
      return a.mixin.getClassRef();
   }

   public String getTargetClassRef() {
      throw new UnsupportedOperationException("Target class not available at compile time");
   }

   public IMixinInfo getMixin() {
      throw new UnsupportedOperationException("MixinInfo not available at compile time");
   }

   public Extensions getExtensions() {
      throw new UnsupportedOperationException("Mixin Extensions not available at compile time");
   }

   public boolean getOption(MixinEnvironment.Option a1) {
      throw new UnsupportedOperationException("Options not available at compile time");
   }

   public int getPriority() {
      throw new UnsupportedOperationException("Priority not available at compile time");
   }

   public Target getTargetMethod(MethodNode a1) {
      throw new UnsupportedOperationException("Target not available at compile time");
   }

   public void registerAccessor(AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor a) {
      if (a.getAccessorType() == null) {
         a.printMessage(a.ap, Kind.WARNING, "Unsupported accessor type");
      } else {
         String a = a.getAccessorTargetName(a);
         if (a == null) {
            a.printMessage(a.ap, Kind.WARNING, "Cannot inflect accessor target name");
         } else {
            a.setTargetName(a);
            Iterator var3 = a.mixin.getTargets().iterator();

            while(var3.hasNext()) {
               TypeHandle a = (TypeHandle)var3.next();
               if (a.getAccessorType() == AccessorInfo.AccessorType.METHOD_PROXY) {
                  a.registerInvokerForTarget((AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker)a, a);
               } else {
                  a.registerAccessorForTarget(a, a);
               }
            }

         }
      }
   }

   private void registerAccessorForTarget(AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor a, TypeHandle a) {
      FieldHandle a = a.findField(a.getTargetName(), a.getTargetTypeName(), false);
      if (a == null) {
         if (!a.isImaginary()) {
            a.printMessage(a.ap, Kind.ERROR, "Could not locate @Accessor target " + a + " in target " + a);
            return;
         }

         a = new FieldHandle(a.getName(), a.getTargetName(), a.getDesc());
      }

      if (a.shouldRemap()) {
         ObfuscationData<MappingField> a = a.obf.getDataProvider().getObfField(a.asMapping(false).move(a.getName()));
         if (a.isEmpty()) {
            String a = a.mixin.isMultiTarget() ? " in target " + a : "";
            a.printMessage(a.ap, Kind.WARNING, "Unable to locate obfuscation mapping" + a + " for @Accessor target " + a);
         } else {
            a = AnnotatedMixinElementHandler.stripOwnerData(a);

            try {
               a.obf.getReferenceManager().addFieldMapping(a.mixin.getClassRef(), a.getTargetName(), a.getContext(), a);
            } catch (ReferenceManager.ReferenceConflictException var6) {
               a.printMessage(a.ap, Kind.ERROR, "Mapping conflict for @Accessor target " + a + ": " + var6.getNew() + " for target " + a + " conflicts with existing mapping " + var6.getOld());
            }

         }
      }
   }

   private void registerInvokerForTarget(AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker a, TypeHandle a) {
      MethodHandle a = a.findMethod(a.getTargetName(), a.getTargetTypeName(), false);
      if (a == null) {
         if (!a.isImaginary()) {
            a.printMessage(a.ap, Kind.ERROR, "Could not locate @Invoker target " + a + " in target " + a);
            return;
         }

         a = new MethodHandle(a, a.getTargetName(), a.getDesc());
      }

      if (a.shouldRemap()) {
         ObfuscationData<MappingMethod> a = a.obf.getDataProvider().getObfMethod(a.asMapping(false).move(a.getName()));
         if (a.isEmpty()) {
            String a = a.mixin.isMultiTarget() ? " in target " + a : "";
            a.printMessage(a.ap, Kind.WARNING, "Unable to locate obfuscation mapping" + a + " for @Accessor target " + a);
         } else {
            a = AnnotatedMixinElementHandler.stripOwnerData(a);

            try {
               a.obf.getReferenceManager().addMethodMapping(a.mixin.getClassRef(), a.getTargetName(), a.getContext(), a);
            } catch (ReferenceManager.ReferenceConflictException var6) {
               a.printMessage(a.ap, Kind.ERROR, "Mapping conflict for @Invoker target " + a + ": " + var6.getNew() + " for target " + a + " conflicts with existing mapping " + var6.getOld());
            }

         }
      }
   }

   private String getAccessorTargetName(AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor a) {
      String a = a.getAnnotationValue();
      return Strings.isNullOrEmpty(a) ? a.inflectAccessorTarget(a) : a;
   }

   private String inflectAccessorTarget(AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor a) {
      return AccessorInfo.inflectTarget(a.getSimpleName(), a.getAccessorType(), "", a, false);
   }

   static class AnnotatedElementInvoker extends AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor {
      public AnnotatedElementInvoker(ExecutableElement a, AnnotationHandle a, boolean a) {
         super(a, a, a);
      }

      public String getAccessorDesc() {
         return TypeUtils.getDescriptor((ExecutableElement)a.getElement());
      }

      public AccessorInfo.AccessorType getAccessorType() {
         return AccessorInfo.AccessorType.METHOD_PROXY;
      }

      public String getTargetTypeName() {
         return TypeUtils.getJavaSignature(a.getElement());
      }
   }

   static class AnnotatedElementAccessor extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
      private final boolean shouldRemap;
      private final TypeMirror returnType;
      private String targetName;

      public AnnotatedElementAccessor(ExecutableElement a, AnnotationHandle a, boolean a) {
         super(a, a);
         a.shouldRemap = a;
         a.returnType = ((ExecutableElement)a.getElement()).getReturnType();
      }

      public boolean shouldRemap() {
         return a.shouldRemap;
      }

      public String getAnnotationValue() {
         return (String)a.getAnnotation().getValue();
      }

      public TypeMirror getTargetType() {
         switch(a.getAccessorType()) {
         case FIELD_GETTER:
            return a.returnType;
         case FIELD_SETTER:
            return ((VariableElement)((ExecutableElement)a.getElement()).getParameters().get(0)).asType();
         default:
            return null;
         }
      }

      public String getTargetTypeName() {
         return TypeUtils.getTypeName(a.getTargetType());
      }

      public String getAccessorDesc() {
         return TypeUtils.getInternalName(a.getTargetType());
      }

      public MemberInfo getContext() {
         return new MemberInfo(a.getTargetName(), (String)null, a.getAccessorDesc());
      }

      public AccessorInfo.AccessorType getAccessorType() {
         return a.returnType.getKind() == TypeKind.VOID ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
      }

      public void setTargetName(String a) {
         a.targetName = a;
      }

      public String getTargetName() {
         return a.targetName;
      }

      public String toString() {
         return a.targetName != null ? a.targetName : "<invalid>";
      }
   }
}

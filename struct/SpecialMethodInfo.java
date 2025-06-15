package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public abstract class SpecialMethodInfo implements IInjectionPointContext {
   protected final AnnotationNode annotation;
   protected final ClassNode classNode;
   protected final MethodNode method;
   protected final MixinTargetContext mixin;

   public SpecialMethodInfo(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      a.mixin = a;
      a.method = a;
      a.annotation = a;
      a.classNode = a.getTargetClassNode();
   }

   public final IMixinContext getContext() {
      return a.mixin;
   }

   public final AnnotationNode getAnnotation() {
      return a.annotation;
   }

   public final ClassNode getClassNode() {
      return a.classNode;
   }

   public final MethodNode getMethod() {
      return a.method;
   }
}

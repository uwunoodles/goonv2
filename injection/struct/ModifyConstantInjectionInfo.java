package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class ModifyConstantInjectionInfo extends InjectionInfo {
   private static final String CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');

   public ModifyConstantInjectionInfo(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      super(a, a, a, "constant");
   }

   protected List<AnnotationNode> readInjectionPoints(String a) {
      List<AnnotationNode> a = super.readInjectionPoints(a);
      if (((List)a).isEmpty()) {
         AnnotationNode a = new AnnotationNode(CONSTANT_ANNOTATION_CLASS);
         a.visit("log", Boolean.TRUE);
         a = ImmutableList.of(a);
      }

      return (List)a;
   }

   protected void parseInjectionPoints(List<AnnotationNode> a) {
      Type a = Type.getReturnType(a.method.desc);
      Iterator var3 = a.iterator();

      while(var3.hasNext()) {
         AnnotationNode a = (AnnotationNode)var3.next();
         a.injectionPoints.add(new BeforeConstant(a.getContext(), a, a.getDescriptor()));
      }

   }

   protected Injector parseInjector(AnnotationNode a1) {
      return new ModifyConstantInjector(a);
   }

   protected String getDescription() {
      return "Constant modifier method";
   }

   public String getSliceId(String a) {
      return Strings.nullToEmpty(a);
   }
}

package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public class InvokerInfo extends AccessorInfo {
   public InvokerInfo(MixinTargetContext a, MethodNode a) {
      super(a, a, Invoker.class);
   }

   protected AccessorInfo.AccessorType initType() {
      return AccessorInfo.AccessorType.METHOD_PROXY;
   }

   protected Type initTargetFieldType() {
      return null;
   }

   protected MemberInfo initTarget() {
      return new MemberInfo(a.getTargetName(), (String)null, a.method.desc);
   }

   public void locate() {
      a.targetMethod = a.findTargetMethod();
   }

   private MethodNode findTargetMethod() {
      return (MethodNode)a.findTarget(a.classNode.methods);
   }
}

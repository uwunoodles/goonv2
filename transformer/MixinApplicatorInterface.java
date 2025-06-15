package org.spongepowered.asm.mixin.transformer;

import java.util.Iterator;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;

class MixinApplicatorInterface extends MixinApplicatorStandard {
   MixinApplicatorInterface(TargetClassContext a) {
      super(a);
   }

   protected void applyInterfaces(MixinTargetContext a) {
      Iterator var2 = a.getInterfaces().iterator();

      while(var2.hasNext()) {
         String a = (String)var2.next();
         if (!a.targetClass.name.equals(a) && !a.targetClass.interfaces.contains(a)) {
            a.targetClass.interfaces.add(a);
            a.getTargetClassInfo().addInterface(a);
         }
      }

   }

   protected void applyFields(MixinTargetContext a) {
      Iterator var2 = a.getShadowFields().iterator();

      while(var2.hasNext()) {
         Entry<FieldNode, ClassInfo.Field> a = (Entry)var2.next();
         FieldNode a = (FieldNode)a.getKey();
         a.logger.error("Ignoring redundant @Shadow field {}:{} in {}", new Object[]{a.name, a.desc, a});
      }

      a.mergeNewFields(a);
   }

   protected void applyInitialisers(MixinTargetContext a1) {
   }

   protected void prepareInjections(MixinTargetContext a) {
      Iterator var2 = a.targetClass.methods.iterator();

      while(var2.hasNext()) {
         MethodNode a = (MethodNode)var2.next();

         try {
            InjectionInfo a = InjectionInfo.parse(a, a);
            if (a != null) {
               throw new InvalidInterfaceMixinException(a, a + " is not supported on interface mixin method " + a.name);
            }
         } catch (InvalidInjectionException var6) {
            String a = var6.getInjectionInfo() != null ? var6.getInjectionInfo().toString() : "Injection";
            throw new InvalidInterfaceMixinException(a, a + " is not supported in interface mixin");
         }
      }

   }

   protected void applyInjections(MixinTargetContext a1) {
   }
}

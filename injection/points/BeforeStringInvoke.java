package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint.AtCode("INVOKE_STRING")
public class BeforeStringInvoke extends BeforeInvoke {
   private static final String STRING_VOID_SIG = "(Ljava/lang/String;)V";
   private final String ldcValue;
   private boolean foundLdc;

   public BeforeStringInvoke(InjectionPointData a) {
      super(a);
      a.ldcValue = a.get("ldc", (String)null);
      if (a.ldcValue == null) {
         throw new IllegalArgumentException(a.getClass().getSimpleName() + " requires named argument \"ldc\" to specify the desired target");
      } else if (!"(Ljava/lang/String;)V".equals(a.target.desc)) {
         throw new IllegalArgumentException(a.getClass().getSimpleName() + " requires target method with with signature " + "(Ljava/lang/String;)V");
      }
   }

   public boolean find(String a, InsnList a, Collection<AbstractInsnNode> a) {
      a.foundLdc = false;
      return super.find(a, a, a);
   }

   protected void inspectInsn(String a1, InsnList a2, AbstractInsnNode a) {
      if (a instanceof LdcInsnNode) {
         LdcInsnNode a = (LdcInsnNode)a;
         if (a.cst instanceof String && a.ldcValue.equals(a.cst)) {
            a.log("{} > found a matching LDC with value {}", new Object[]{a.className, a.cst});
            a.foundLdc = true;
            return;
         }
      }

      a.foundLdc = false;
   }

   protected boolean matchesInsn(MemberInfo a, int a) {
      a.log("{} > > found LDC \"{}\" = {}", new Object[]{a.className, a.ldcValue, a.foundLdc});
      return a.foundLdc && super.matchesInsn(a, a);
   }
}

package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.util.Bytecode;

@InjectionPoint.AtCode("FIELD")
public class BeforeFieldAccess extends BeforeInvoke {
   private static final String ARRAY_GET = "get";
   private static final String ARRAY_SET = "set";
   private static final String ARRAY_LENGTH = "length";
   public static final int ARRAY_SEARCH_FUZZ_DEFAULT = 8;
   private final int opcode;
   private final int arrOpcode;
   private final int fuzzFactor;

   public BeforeFieldAccess(InjectionPointData a) {
      super(a);
      a.opcode = a.getOpcode(-1, 180, 181, 178, 179, -1);
      String a = a.get("array", "");
      a.arrOpcode = "get".equalsIgnoreCase(a) ? 46 : ("set".equalsIgnoreCase(a) ? 79 : ("length".equalsIgnoreCase(a) ? 190 : 0));
      a.fuzzFactor = Math.min(Math.max(a.get("fuzz", 8), 1), 32);
   }

   public int getFuzzFactor() {
      return a.fuzzFactor;
   }

   public int getArrayOpcode() {
      return a.arrOpcode;
   }

   private int getArrayOpcode(String a) {
      return a.arrOpcode != 190 ? Type.getType(a).getElementType().getOpcode(a.arrOpcode) : a.arrOpcode;
   }

   protected boolean matchesInsn(AbstractInsnNode a) {
      if (!(a instanceof FieldInsnNode) || ((FieldInsnNode)a).getOpcode() != a.opcode && a.opcode != -1) {
         return false;
      } else if (a.arrOpcode == 0) {
         return true;
      } else if (a.getOpcode() != 178 && a.getOpcode() != 180) {
         return false;
      } else {
         return Type.getType(((FieldInsnNode)a).desc).getSort() == 9;
      }
   }

   protected boolean addInsn(InsnList a, Collection<AbstractInsnNode> a, AbstractInsnNode a) {
      if (a.arrOpcode > 0) {
         FieldInsnNode a = (FieldInsnNode)a;
         int a = a.getArrayOpcode(a.desc);
         a.log("{} > > > > searching for array access opcode {} fuzz={}", new Object[]{a.className, Bytecode.getOpcodeName(a), a.fuzzFactor});
         if (findArrayNode(a, a, a, a.fuzzFactor) == null) {
            a.log("{} > > > > > failed to locate matching insn", new Object[]{a.className});
            return false;
         }
      }

      a.log("{} > > > > > adding matching insn", new Object[]{a.className});
      return super.addInsn(a, a, a);
   }

   public static AbstractInsnNode findArrayNode(InsnList a, FieldInsnNode a, int a, int a) {
      int a = 0;
      ListIterator a = a.iterator(a.indexOf(a) + 1);

      do {
         if (!a.hasNext()) {
            return null;
         }

         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (a.getOpcode() == a) {
            return a;
         }

         if (a.getOpcode() == 190 && a == 0) {
            return null;
         }

         if (a instanceof FieldInsnNode) {
            FieldInsnNode a = (FieldInsnNode)a;
            if (a.desc.equals(a.desc) && a.name.equals(a.name) && a.owner.equals(a.owner)) {
               return null;
            }
         }
      } while(a++ <= a);

      return null;
   }
}

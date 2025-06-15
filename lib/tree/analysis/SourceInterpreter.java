package org.spongepowered.asm.lib.tree.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;

public class SourceInterpreter extends Interpreter<SourceValue> implements Opcodes {
   public SourceInterpreter() {
      super(327680);
   }

   protected SourceInterpreter(int a) {
      super(a);
   }

   public SourceValue newValue(Type a) {
      return a == Type.VOID_TYPE ? null : new SourceValue(a == null ? 1 : a.getSize());
   }

   public SourceValue newOperation(AbstractInsnNode a) {
      int a;
      switch(a.getOpcode()) {
      case 9:
      case 10:
      case 14:
      case 15:
         a = 2;
         break;
      case 18:
         Object a = ((LdcInsnNode)a).cst;
         a = !(a instanceof Long) && !(a instanceof Double) ? 1 : 2;
         break;
      case 178:
         a = Type.getType(((FieldInsnNode)a).desc).getSize();
         break;
      default:
         a = 1;
      }

      return new SourceValue(a, a);
   }

   public SourceValue copyOperation(AbstractInsnNode a, SourceValue a) {
      return new SourceValue(a.getSize(), a);
   }

   public SourceValue unaryOperation(AbstractInsnNode a, SourceValue a2) {
      int a;
      switch(a.getOpcode()) {
      case 117:
      case 119:
      case 133:
      case 135:
      case 138:
      case 140:
      case 141:
      case 143:
         a = 2;
         break;
      case 180:
         a = Type.getType(((FieldInsnNode)a).desc).getSize();
         break;
      default:
         a = 1;
      }

      return new SourceValue(a, a);
   }

   public SourceValue binaryOperation(AbstractInsnNode a, SourceValue a2, SourceValue a3) {
      byte a;
      switch(a.getOpcode()) {
      case 47:
      case 49:
      case 97:
      case 99:
      case 101:
      case 103:
      case 105:
      case 107:
      case 109:
      case 111:
      case 113:
      case 115:
      case 121:
      case 123:
      case 125:
      case 127:
      case 129:
      case 131:
         a = 2;
         break;
      default:
         a = 1;
      }

      return new SourceValue(a, a);
   }

   public SourceValue ternaryOperation(AbstractInsnNode a, SourceValue a2, SourceValue a3, SourceValue a4) {
      return new SourceValue(1, a);
   }

   public SourceValue naryOperation(AbstractInsnNode a, List<? extends SourceValue> a2) {
      int a = a.getOpcode();
      int a;
      if (a == 197) {
         a = 1;
      } else {
         String a = a == 186 ? ((InvokeDynamicInsnNode)a).desc : ((MethodInsnNode)a).desc;
         a = Type.getReturnType(a).getSize();
      }

      return new SourceValue(a, a);
   }

   public void returnOperation(AbstractInsnNode a1, SourceValue a2, SourceValue a3) {
   }

   public SourceValue merge(SourceValue a, SourceValue a) {
      if (a.insns instanceof SmallSet && a.insns instanceof SmallSet) {
         Set<AbstractInsnNode> a = ((SmallSet)a.insns).union((SmallSet)a.insns);
         return a == a.insns && a.size == a.size ? a : new SourceValue(Math.min(a.size, a.size), a);
      } else if (a.size == a.size && a.insns.containsAll(a.insns)) {
         return a;
      } else {
         HashSet<AbstractInsnNode> a = new HashSet();
         a.addAll(a.insns);
         a.addAll(a.insns);
         return new SourceValue(Math.min(a.size, a.size), a);
      }
   }
}

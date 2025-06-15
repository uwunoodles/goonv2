package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.IntInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;

public class BasicInterpreter extends Interpreter<BasicValue> implements Opcodes {
   public BasicInterpreter() {
      super(327680);
   }

   protected BasicInterpreter(int a) {
      super(a);
   }

   public BasicValue newValue(Type a) {
      if (a == null) {
         return BasicValue.UNINITIALIZED_VALUE;
      } else {
         switch(a.getSort()) {
         case 0:
            return null;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            return BasicValue.INT_VALUE;
         case 6:
            return BasicValue.FLOAT_VALUE;
         case 7:
            return BasicValue.LONG_VALUE;
         case 8:
            return BasicValue.DOUBLE_VALUE;
         case 9:
         case 10:
            return BasicValue.REFERENCE_VALUE;
         default:
            throw new Error("Internal error");
         }
      }
   }

   public BasicValue newOperation(AbstractInsnNode a) throws AnalyzerException {
      switch(a.getOpcode()) {
      case 1:
         return a.newValue(Type.getObjectType("null"));
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
         return BasicValue.INT_VALUE;
      case 9:
      case 10:
         return BasicValue.LONG_VALUE;
      case 11:
      case 12:
      case 13:
         return BasicValue.FLOAT_VALUE;
      case 14:
      case 15:
         return BasicValue.DOUBLE_VALUE;
      case 16:
      case 17:
         return BasicValue.INT_VALUE;
      case 18:
         Object a = ((LdcInsnNode)a).cst;
         if (a instanceof Integer) {
            return BasicValue.INT_VALUE;
         } else if (a instanceof Float) {
            return BasicValue.FLOAT_VALUE;
         } else if (a instanceof Long) {
            return BasicValue.LONG_VALUE;
         } else if (a instanceof Double) {
            return BasicValue.DOUBLE_VALUE;
         } else if (a instanceof String) {
            return a.newValue(Type.getObjectType("java/lang/String"));
         } else if (a instanceof Type) {
            int a = ((Type)a).getSort();
            if (a != 10 && a != 9) {
               if (a == 11) {
                  return a.newValue(Type.getObjectType("java/lang/invoke/MethodType"));
               }

               throw new IllegalArgumentException("Illegal LDC constant " + a);
            }

            return a.newValue(Type.getObjectType("java/lang/Class"));
         } else {
            if (a instanceof Handle) {
               return a.newValue(Type.getObjectType("java/lang/invoke/MethodHandle"));
            }

            throw new IllegalArgumentException("Illegal LDC constant " + a);
         }
      case 168:
         return BasicValue.RETURNADDRESS_VALUE;
      case 178:
         return a.newValue(Type.getType(((FieldInsnNode)a).desc));
      case 187:
         return a.newValue(Type.getObjectType(((TypeInsnNode)a).desc));
      default:
         throw new Error("Internal error.");
      }
   }

   public BasicValue copyOperation(AbstractInsnNode a1, BasicValue a) throws AnalyzerException {
      return a;
   }

   public BasicValue unaryOperation(AbstractInsnNode a, BasicValue a2) throws AnalyzerException {
      String a;
      switch(a.getOpcode()) {
      case 116:
      case 132:
      case 136:
      case 139:
      case 142:
      case 145:
      case 146:
      case 147:
         return BasicValue.INT_VALUE;
      case 117:
      case 133:
      case 140:
      case 143:
         return BasicValue.LONG_VALUE;
      case 118:
      case 134:
      case 137:
      case 144:
         return BasicValue.FLOAT_VALUE;
      case 119:
      case 135:
      case 138:
      case 141:
         return BasicValue.DOUBLE_VALUE;
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 130:
      case 131:
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 167:
      case 168:
      case 169:
      case 177:
      case 178:
      case 181:
      case 182:
      case 183:
      case 184:
      case 185:
      case 186:
      case 187:
      case 196:
      case 197:
      default:
         throw new Error("Internal error.");
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
      case 179:
         return null;
      case 180:
         return a.newValue(Type.getType(((FieldInsnNode)a).desc));
      case 188:
         switch(((IntInsnNode)a).operand) {
         case 4:
            return a.newValue(Type.getType("[Z"));
         case 5:
            return a.newValue(Type.getType("[C"));
         case 6:
            return a.newValue(Type.getType("[F"));
         case 7:
            return a.newValue(Type.getType("[D"));
         case 8:
            return a.newValue(Type.getType("[B"));
         case 9:
            return a.newValue(Type.getType("[S"));
         case 10:
            return a.newValue(Type.getType("[I"));
         case 11:
            return a.newValue(Type.getType("[J"));
         default:
            throw new AnalyzerException(a, "Invalid array type");
         }
      case 189:
         a = ((TypeInsnNode)a).desc;
         return a.newValue(Type.getType("[" + Type.getObjectType(a)));
      case 190:
         return BasicValue.INT_VALUE;
      case 191:
         return null;
      case 192:
         a = ((TypeInsnNode)a).desc;
         return a.newValue(Type.getObjectType(a));
      case 193:
         return BasicValue.INT_VALUE;
      case 194:
      case 195:
      case 198:
      case 199:
         return null;
      }
   }

   public BasicValue binaryOperation(AbstractInsnNode a, BasicValue a2, BasicValue a3) throws AnalyzerException {
      switch(a.getOpcode()) {
      case 46:
      case 51:
      case 52:
      case 53:
      case 96:
      case 100:
      case 104:
      case 108:
      case 112:
      case 120:
      case 122:
      case 124:
      case 126:
      case 128:
      case 130:
         return BasicValue.INT_VALUE;
      case 47:
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 121:
      case 123:
      case 125:
      case 127:
      case 129:
      case 131:
         return BasicValue.LONG_VALUE;
      case 48:
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
         return BasicValue.FLOAT_VALUE;
      case 49:
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
         return BasicValue.DOUBLE_VALUE;
      case 50:
         return BasicValue.REFERENCE_VALUE;
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
      case 87:
      case 88:
      case 89:
      case 90:
      case 91:
      case 92:
      case 93:
      case 94:
      case 95:
      case 116:
      case 117:
      case 118:
      case 119:
      case 132:
      case 133:
      case 134:
      case 135:
      case 136:
      case 137:
      case 138:
      case 139:
      case 140:
      case 141:
      case 142:
      case 143:
      case 144:
      case 145:
      case 146:
      case 147:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 167:
      case 168:
      case 169:
      case 170:
      case 171:
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
      case 177:
      case 178:
      case 179:
      case 180:
      default:
         throw new Error("Internal error.");
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
         return BasicValue.INT_VALUE;
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 181:
         return null;
      }
   }

   public BasicValue ternaryOperation(AbstractInsnNode a1, BasicValue a2, BasicValue a3, BasicValue a4) throws AnalyzerException {
      return null;
   }

   public BasicValue naryOperation(AbstractInsnNode a, List<? extends BasicValue> a2) throws AnalyzerException {
      int a = a.getOpcode();
      if (a == 197) {
         return a.newValue(Type.getType(((MultiANewArrayInsnNode)a).desc));
      } else {
         return a == 186 ? a.newValue(Type.getReturnType(((InvokeDynamicInsnNode)a).desc)) : a.newValue(Type.getReturnType(((MethodInsnNode)a).desc));
      }
   }

   public void returnOperation(AbstractInsnNode a1, BasicValue a2, BasicValue a3) throws AnalyzerException {
   }

   public BasicValue merge(BasicValue a, BasicValue a) {
      return !a.equals(a) ? BasicValue.UNINITIALIZED_VALUE : a;
   }
}

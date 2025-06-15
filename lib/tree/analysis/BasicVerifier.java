package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;

public class BasicVerifier extends BasicInterpreter {
   public BasicVerifier() {
      super(327680);
   }

   protected BasicVerifier(int a) {
      super(a);
   }

   public BasicValue copyOperation(AbstractInsnNode a, BasicValue a) throws AnalyzerException {
      BasicValue a;
      switch(a.getOpcode()) {
      case 21:
      case 54:
         a = BasicValue.INT_VALUE;
         break;
      case 22:
      case 55:
         a = BasicValue.LONG_VALUE;
         break;
      case 23:
      case 56:
         a = BasicValue.FLOAT_VALUE;
         break;
      case 24:
      case 57:
         a = BasicValue.DOUBLE_VALUE;
         break;
      case 25:
         if (!a.isReference()) {
            throw new AnalyzerException(a, (String)null, "an object reference", a);
         }

         return a;
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      default:
         return a;
      case 58:
         if (!a.isReference() && !BasicValue.RETURNADDRESS_VALUE.equals(a)) {
            throw new AnalyzerException(a, (String)null, "an object reference or a return address", a);
         }

         return a;
      }

      if (!a.equals(a)) {
         throw new AnalyzerException(a, (String)null, a, a);
      } else {
         return a;
      }
   }

   public BasicValue unaryOperation(AbstractInsnNode a, BasicValue a) throws AnalyzerException {
      BasicValue a;
      switch(a.getOpcode()) {
      case 116:
      case 132:
      case 133:
      case 134:
      case 135:
      case 145:
      case 146:
      case 147:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 188:
      case 189:
         a = BasicValue.INT_VALUE;
         break;
      case 117:
      case 136:
      case 137:
      case 138:
      case 173:
         a = BasicValue.LONG_VALUE;
         break;
      case 118:
      case 139:
      case 140:
      case 141:
      case 174:
         a = BasicValue.FLOAT_VALUE;
         break;
      case 119:
      case 142:
      case 143:
      case 144:
      case 175:
         a = BasicValue.DOUBLE_VALUE;
         break;
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
      case 176:
      case 191:
      case 193:
      case 194:
      case 195:
      case 198:
      case 199:
         if (!a.isReference()) {
            throw new AnalyzerException(a, (String)null, "an object reference", a);
         }

         return super.unaryOperation(a, a);
      case 179:
         a = a.newValue(Type.getType(((FieldInsnNode)a).desc));
         break;
      case 180:
         a = a.newValue(Type.getObjectType(((FieldInsnNode)a).owner));
         break;
      case 190:
         if (!a.isArrayValue(a)) {
            throw new AnalyzerException(a, (String)null, "an array reference", a);
         }

         return super.unaryOperation(a, a);
      case 192:
         if (!a.isReference()) {
            throw new AnalyzerException(a, (String)null, "an object reference", a);
         }

         return super.unaryOperation(a, a);
      }

      if (!a.isSubTypeOf(a, a)) {
         throw new AnalyzerException(a, (String)null, a, a);
      } else {
         return super.unaryOperation(a, a);
      }
   }

   public BasicValue binaryOperation(AbstractInsnNode a, BasicValue a, BasicValue a) throws AnalyzerException {
      BasicValue a;
      BasicValue a;
      switch(a.getOpcode()) {
      case 46:
         a = a.newValue(Type.getType("[I"));
         a = BasicValue.INT_VALUE;
         break;
      case 47:
         a = a.newValue(Type.getType("[J"));
         a = BasicValue.INT_VALUE;
         break;
      case 48:
         a = a.newValue(Type.getType("[F"));
         a = BasicValue.INT_VALUE;
         break;
      case 49:
         a = a.newValue(Type.getType("[D"));
         a = BasicValue.INT_VALUE;
         break;
      case 50:
         a = a.newValue(Type.getType("[Ljava/lang/Object;"));
         a = BasicValue.INT_VALUE;
         break;
      case 51:
         if (a.isSubTypeOf(a, a.newValue(Type.getType("[Z")))) {
            a = a.newValue(Type.getType("[Z"));
         } else {
            a = a.newValue(Type.getType("[B"));
         }

         a = BasicValue.INT_VALUE;
         break;
      case 52:
         a = a.newValue(Type.getType("[C"));
         a = BasicValue.INT_VALUE;
         break;
      case 53:
         a = a.newValue(Type.getType("[S"));
         a = BasicValue.INT_VALUE;
         break;
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
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
         a = BasicValue.INT_VALUE;
         a = BasicValue.INT_VALUE;
         break;
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 127:
      case 129:
      case 131:
      case 148:
         a = BasicValue.LONG_VALUE;
         a = BasicValue.LONG_VALUE;
         break;
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
      case 149:
      case 150:
         a = BasicValue.FLOAT_VALUE;
         a = BasicValue.FLOAT_VALUE;
         break;
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
      case 151:
      case 152:
         a = BasicValue.DOUBLE_VALUE;
         a = BasicValue.DOUBLE_VALUE;
         break;
      case 121:
      case 123:
      case 125:
         a = BasicValue.LONG_VALUE;
         a = BasicValue.INT_VALUE;
         break;
      case 165:
      case 166:
         a = BasicValue.REFERENCE_VALUE;
         a = BasicValue.REFERENCE_VALUE;
         break;
      case 181:
         FieldInsnNode a = (FieldInsnNode)a;
         a = a.newValue(Type.getObjectType(a.owner));
         a = a.newValue(Type.getType(a.desc));
      }

      if (!a.isSubTypeOf(a, a)) {
         throw new AnalyzerException(a, "First argument", a, a);
      } else if (!a.isSubTypeOf(a, a)) {
         throw new AnalyzerException(a, "Second argument", a, a);
      } else {
         return a.getOpcode() == 50 ? a.getElementValue(a) : super.binaryOperation(a, a, a);
      }
   }

   public BasicValue ternaryOperation(AbstractInsnNode a, BasicValue a, BasicValue a, BasicValue a) throws AnalyzerException {
      BasicValue a;
      BasicValue a;
      switch(a.getOpcode()) {
      case 79:
         a = a.newValue(Type.getType("[I"));
         a = BasicValue.INT_VALUE;
         break;
      case 80:
         a = a.newValue(Type.getType("[J"));
         a = BasicValue.LONG_VALUE;
         break;
      case 81:
         a = a.newValue(Type.getType("[F"));
         a = BasicValue.FLOAT_VALUE;
         break;
      case 82:
         a = a.newValue(Type.getType("[D"));
         a = BasicValue.DOUBLE_VALUE;
         break;
      case 83:
         a = a;
         a = BasicValue.REFERENCE_VALUE;
         break;
      case 84:
         if (a.isSubTypeOf(a, a.newValue(Type.getType("[Z")))) {
            a = a.newValue(Type.getType("[Z"));
         } else {
            a = a.newValue(Type.getType("[B"));
         }

         a = BasicValue.INT_VALUE;
         break;
      case 85:
         a = a.newValue(Type.getType("[C"));
         a = BasicValue.INT_VALUE;
         break;
      case 86:
         a = a.newValue(Type.getType("[S"));
         a = BasicValue.INT_VALUE;
         break;
      default:
         throw new Error("Internal error.");
      }

      if (!a.isSubTypeOf(a, a)) {
         throw new AnalyzerException(a, "First argument", "a " + a + " array reference", a);
      } else if (!BasicValue.INT_VALUE.equals(a)) {
         throw new AnalyzerException(a, "Second argument", BasicValue.INT_VALUE, a);
      } else if (!a.isSubTypeOf(a, a)) {
         throw new AnalyzerException(a, "Third argument", a, a);
      } else {
         return null;
      }
   }

   public BasicValue naryOperation(AbstractInsnNode a, List<? extends BasicValue> a) throws AnalyzerException {
      int a = a.getOpcode();
      int a;
      if (a == 197) {
         for(a = 0; a < a.size(); ++a) {
            if (!BasicValue.INT_VALUE.equals(a.get(a))) {
               throw new AnalyzerException(a, (String)null, BasicValue.INT_VALUE, (Value)a.get(a));
            }
         }
      } else {
         a = 0;
         int a = 0;
         if (a != 184 && a != 186) {
            Type a = Type.getObjectType(((MethodInsnNode)a).owner);
            if (!a.isSubTypeOf((BasicValue)a.get(a++), a.newValue(a))) {
               throw new AnalyzerException(a, "Method owner", a.newValue(a), (Value)a.get(0));
            }
         }

         String a = a == 186 ? ((InvokeDynamicInsnNode)a).desc : ((MethodInsnNode)a).desc;
         Type[] a = Type.getArgumentTypes(a);

         while(a < a.size()) {
            BasicValue a = a.newValue(a[a++]);
            BasicValue a = (BasicValue)a.get(a++);
            if (!a.isSubTypeOf(a, a)) {
               throw new AnalyzerException(a, "Argument " + a, a, a);
            }
         }
      }

      return super.naryOperation(a, a);
   }

   public void returnOperation(AbstractInsnNode a, BasicValue a, BasicValue a) throws AnalyzerException {
      if (!a.isSubTypeOf(a, a)) {
         throw new AnalyzerException(a, "Incompatible return type", a, a);
      }
   }

   protected boolean isArrayValue(BasicValue a) {
      return a.isReference();
   }

   protected BasicValue getElementValue(BasicValue a1) throws AnalyzerException {
      return BasicValue.REFERENCE_VALUE;
   }

   protected boolean isSubTypeOf(BasicValue a, BasicValue a) {
      return a.equals(a);
   }
}

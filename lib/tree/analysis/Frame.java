package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class Frame<V extends Value> {
   private V returnValue;
   private V[] values;
   private int locals;
   private int top;

   public Frame(int a, int a) {
      a.values = (Value[])(new Value[a + a]);
      a.locals = a;
   }

   public Frame(Frame<? extends V> a) {
      this(a.locals, a.values.length - a.locals);
      a.init(a);
   }

   public Frame<V> init(Frame<? extends V> a) {
      a.returnValue = a.returnValue;
      System.arraycopy(a.values, 0, a.values, 0, a.values.length);
      a.top = a.top;
      return a;
   }

   public void setReturn(V a) {
      a.returnValue = a;
   }

   public int getLocals() {
      return a.locals;
   }

   public int getMaxStackSize() {
      return a.values.length - a.locals;
   }

   public V getLocal(int a) throws IndexOutOfBoundsException {
      if (a >= a.locals) {
         throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
      } else {
         return a.values[a];
      }
   }

   public void setLocal(int a, V a) throws IndexOutOfBoundsException {
      if (a >= a.locals) {
         throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + a);
      } else {
         a.values[a] = a;
      }
   }

   public int getStackSize() {
      return a.top;
   }

   public V getStack(int a) throws IndexOutOfBoundsException {
      return a.values[a + a.locals];
   }

   public void clearStack() {
      a.top = 0;
   }

   public V pop() throws IndexOutOfBoundsException {
      if (a.top == 0) {
         throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
      } else {
         return a.values[--a.top + a.locals];
      }
   }

   public void push(V a) throws IndexOutOfBoundsException {
      if (a.top + a.locals >= a.values.length) {
         throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
      } else {
         a.values[a.top++ + a.locals] = a;
      }
   }

   public void execute(AbstractInsnNode a, Interpreter<V> a) throws AnalyzerException {
      Value a;
      Value a;
      int a;
      Value a;
      ArrayList a;
      int a;
      String a;
      switch(a.getOpcode()) {
      case 0:
      case 167:
      case 169:
         break;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
         a.push(a.newOperation(a));
         break;
      case 19:
      case 20:
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
      case 196:
      default:
         throw new RuntimeException("Illegal opcode " + a.getOpcode());
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
         a.push(a.copyOperation(a, a.getLocal(((VarInsnNode)a).var)));
         break;
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
         a = a.pop();
         a = a.pop();
         a.push(a.binaryOperation(a, a, a));
         break;
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
         a = a.copyOperation(a, a.pop());
         a = ((VarInsnNode)a).var;
         a.setLocal(a, a);
         if (a.getSize() == 2) {
            a.setLocal(a + 1, a.newValue((Type)null));
         }

         if (a > 0) {
            Value a = a.getLocal(a - 1);
            if (a != null && a.getSize() == 2) {
               a.setLocal(a - 1, a.newValue((Type)null));
            }
         }
         break;
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
         a = a.pop();
         a = a.pop();
         a = a.pop();
         a.ternaryOperation(a, a, a, a);
         break;
      case 87:
         if (a.pop().getSize() == 2) {
            throw new AnalyzerException(a, "Illegal use of POP");
         }
         break;
      case 88:
         if (a.pop().getSize() == 1 && a.pop().getSize() != 1) {
            throw new AnalyzerException(a, "Illegal use of POP2");
         }
         break;
      case 89:
         a = a.pop();
         if (a.getSize() != 1) {
            throw new AnalyzerException(a, "Illegal use of DUP");
         }

         a.push(a);
         a.push(a.copyOperation(a, a));
         break;
      case 90:
         a = a.pop();
         a = a.pop();
         if (a.getSize() == 1 && a.getSize() == 1) {
            a.push(a.copyOperation(a, a));
            a.push(a);
            a.push(a);
            break;
         }

         throw new AnalyzerException(a, "Illegal use of DUP_X1");
      case 91:
         a = a.pop();
         if (a.getSize() == 1) {
            a = a.pop();
            if (a.getSize() != 1) {
               a.push(a.copyOperation(a, a));
               a.push(a);
               a.push(a);
               break;
            }

            a = a.pop();
            if (a.getSize() == 1) {
               a.push(a.copyOperation(a, a));
               a.push(a);
               a.push(a);
               a.push(a);
               break;
            }
         }

         throw new AnalyzerException(a, "Illegal use of DUP_X2");
      case 92:
         a = a.pop();
         if (a.getSize() == 1) {
            a = a.pop();
            if (a.getSize() != 1) {
               throw new AnalyzerException(a, "Illegal use of DUP2");
            }

            a.push(a);
            a.push(a);
            a.push(a.copyOperation(a, a));
            a.push(a.copyOperation(a, a));
         } else {
            a.push(a);
            a.push(a.copyOperation(a, a));
         }
         break;
      case 93:
         a = a.pop();
         if (a.getSize() == 1) {
            a = a.pop();
            if (a.getSize() == 1) {
               a = a.pop();
               if (a.getSize() == 1) {
                  a.push(a.copyOperation(a, a));
                  a.push(a.copyOperation(a, a));
                  a.push(a);
                  a.push(a);
                  a.push(a);
                  break;
               }
            }
         } else {
            a = a.pop();
            if (a.getSize() == 1) {
               a.push(a.copyOperation(a, a));
               a.push(a);
               a.push(a);
               break;
            }
         }

         throw new AnalyzerException(a, "Illegal use of DUP2_X1");
      case 94:
         a = a.pop();
         if (a.getSize() == 1) {
            a = a.pop();
            if (a.getSize() != 1) {
               throw new AnalyzerException(a, "Illegal use of DUP2_X2");
            }

            a = a.pop();
            if (a.getSize() == 1) {
               V a = a.pop();
               if (a.getSize() != 1) {
                  throw new AnalyzerException(a, "Illegal use of DUP2_X2");
               }

               a.push(a.copyOperation(a, a));
               a.push(a.copyOperation(a, a));
               a.push(a);
               a.push(a);
               a.push(a);
               a.push(a);
            } else {
               a.push(a.copyOperation(a, a));
               a.push(a.copyOperation(a, a));
               a.push(a);
               a.push(a);
               a.push(a);
            }
         } else {
            a = a.pop();
            if (a.getSize() == 1) {
               a = a.pop();
               if (a.getSize() != 1) {
                  throw new AnalyzerException(a, "Illegal use of DUP2_X2");
               }

               a.push(a.copyOperation(a, a));
               a.push(a);
               a.push(a);
               a.push(a);
            } else {
               a.push(a.copyOperation(a, a));
               a.push(a);
               a.push(a);
            }
         }
         break;
      case 95:
         a = a.pop();
         a = a.pop();
         if (a.getSize() == 1 && a.getSize() == 1) {
            a.push(a.copyOperation(a, a));
            a.push(a.copyOperation(a, a));
            break;
         }

         throw new AnalyzerException(a, "Illegal use of SWAP");
      case 96:
      case 97:
      case 98:
      case 99:
      case 100:
      case 101:
      case 102:
      case 103:
      case 104:
      case 105:
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
      case 113:
      case 114:
      case 115:
         a = a.pop();
         a = a.pop();
         a.push(a.binaryOperation(a, a, a));
         break;
      case 116:
      case 117:
      case 118:
      case 119:
         a.push(a.unaryOperation(a, a.pop()));
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
         a = a.pop();
         a = a.pop();
         a.push(a.binaryOperation(a, a, a));
         break;
      case 132:
         a = ((IincInsnNode)a).var;
         a.setLocal(a, a.unaryOperation(a, a.getLocal(a)));
         break;
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
         a.push(a.unaryOperation(a, a.pop()));
         break;
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
         a = a.pop();
         a = a.pop();
         a.push(a.binaryOperation(a, a, a));
         break;
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
         a.unaryOperation(a, a.pop());
         break;
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
         a = a.pop();
         a = a.pop();
         a.binaryOperation(a, a, a);
         break;
      case 168:
         a.push(a.newOperation(a));
         break;
      case 170:
      case 171:
         a.unaryOperation(a, a.pop());
         break;
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
         a = a.pop();
         a.unaryOperation(a, a);
         a.returnOperation(a, a, a.returnValue);
         break;
      case 177:
         if (a.returnValue != null) {
            throw new AnalyzerException(a, "Incompatible return type");
         }
         break;
      case 178:
         a.push(a.newOperation(a));
         break;
      case 179:
         a.unaryOperation(a, a.pop());
         break;
      case 180:
         a.push(a.unaryOperation(a, a.pop()));
         break;
      case 181:
         a = a.pop();
         a = a.pop();
         a.binaryOperation(a, a, a);
         break;
      case 182:
      case 183:
      case 184:
      case 185:
         a = new ArrayList();
         a = ((MethodInsnNode)a).desc;

         for(a = Type.getArgumentTypes(a).length; a > 0; --a) {
            a.add(0, a.pop());
         }

         if (a.getOpcode() != 184) {
            a.add(0, a.pop());
         }

         if (Type.getReturnType(a) == Type.VOID_TYPE) {
            a.naryOperation(a, a);
         } else {
            a.push(a.naryOperation(a, a));
         }
         break;
      case 186:
         a = new ArrayList();
         a = ((InvokeDynamicInsnNode)a).desc;

         for(a = Type.getArgumentTypes(a).length; a > 0; --a) {
            a.add(0, a.pop());
         }

         if (Type.getReturnType(a) == Type.VOID_TYPE) {
            a.naryOperation(a, a);
         } else {
            a.push(a.naryOperation(a, a));
         }
         break;
      case 187:
         a.push(a.newOperation(a));
         break;
      case 188:
      case 189:
      case 190:
         a.push(a.unaryOperation(a, a.pop()));
         break;
      case 191:
         a.unaryOperation(a, a.pop());
         break;
      case 192:
      case 193:
         a.push(a.unaryOperation(a, a.pop()));
         break;
      case 194:
      case 195:
         a.unaryOperation(a, a.pop());
         break;
      case 197:
         a = new ArrayList();

         for(int a = ((MultiANewArrayInsnNode)a).dims; a > 0; --a) {
            a.add(0, a.pop());
         }

         a.push(a.naryOperation(a, a));
         break;
      case 198:
      case 199:
         a.unaryOperation(a, a.pop());
      }

   }

   public boolean merge(Frame<? extends V> a, Interpreter<V> a) throws AnalyzerException {
      if (a.top != a.top) {
         throw new AnalyzerException((AbstractInsnNode)null, "Incompatible stack heights");
      } else {
         boolean a = false;

         for(int a = 0; a < a.locals + a.top; ++a) {
            V a = a.merge(a.values[a], a.values[a]);
            if (!a.equals(a.values[a])) {
               a.values[a] = a;
               a = true;
            }
         }

         return a;
      }
   }

   public boolean merge(Frame<? extends V> a, boolean[] a) {
      boolean a = false;

      for(int a = 0; a < a.locals; ++a) {
         if (!a[a] && !a.values[a].equals(a.values[a])) {
            a.values[a] = a.values[a];
            a = true;
         }
      }

      return a;
   }

   public String toString() {
      StringBuilder a = new StringBuilder();

      int a;
      for(a = 0; a < a.getLocals(); ++a) {
         a.append(a.getLocal(a));
      }

      a.append(' ');

      for(a = 0; a < a.getStackSize(); ++a) {
         a.append(a.getStack(a).toString());
      }

      return a.toString();
   }
}

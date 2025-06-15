package org.spongepowered.asm.lib;

class Frame {
   static final int DIM = -268435456;
   static final int ARRAY_OF = 268435456;
   static final int ELEMENT_OF = -268435456;
   static final int KIND = 251658240;
   static final int TOP_IF_LONG_OR_DOUBLE = 8388608;
   static final int VALUE = 8388607;
   static final int BASE_KIND = 267386880;
   static final int BASE_VALUE = 1048575;
   static final int BASE = 16777216;
   static final int OBJECT = 24117248;
   static final int UNINITIALIZED = 25165824;
   private static final int LOCAL = 33554432;
   private static final int STACK = 50331648;
   static final int TOP = 16777216;
   static final int BOOLEAN = 16777225;
   static final int BYTE = 16777226;
   static final int CHAR = 16777227;
   static final int SHORT = 16777228;
   static final int INTEGER = 16777217;
   static final int FLOAT = 16777218;
   static final int DOUBLE = 16777219;
   static final int LONG = 16777220;
   static final int NULL = 16777221;
   static final int UNINITIALIZED_THIS = 16777222;
   static final int[] SIZE;
   Label owner;
   int[] inputLocals;
   int[] inputStack;
   private int[] outputLocals;
   private int[] outputStack;
   int outputStackTop;
   private int initializationCount;
   private int[] initializations;

   final void set(ClassWriter a, int a, Object[] a, int a, Object[] a) {
      for(int a = convert(a, a, a, a.inputLocals); a < a.length; a.inputLocals[a++] = 16777216) {
      }

      int a = 0;

      for(int a = 0; a < a; ++a) {
         if (a[a] == Opcodes.LONG || a[a] == Opcodes.DOUBLE) {
            ++a;
         }
      }

      a.inputStack = new int[a + a];
      convert(a, a, a, a.inputStack);
      a.outputStackTop = 0;
      a.initializationCount = 0;
   }

   private static int convert(ClassWriter a, int a, Object[] a, int[] a) {
      int a = 0;

      for(int a = 0; a < a; ++a) {
         if (a[a] instanceof Integer) {
            a[a++] = 16777216 | (Integer)a[a];
            if (a[a] == Opcodes.LONG || a[a] == Opcodes.DOUBLE) {
               a[a++] = 16777216;
            }
         } else if (a[a] instanceof String) {
            a[a++] = type(a, Type.getObjectType((String)a[a]).getDescriptor());
         } else {
            a[a++] = 25165824 | a.addUninitializedType("", ((Label)a[a]).position);
         }
      }

      return a;
   }

   final void set(Frame a) {
      a.inputLocals = a.inputLocals;
      a.inputStack = a.inputStack;
      a.outputLocals = a.outputLocals;
      a.outputStack = a.outputStack;
      a.outputStackTop = a.outputStackTop;
      a.initializationCount = a.initializationCount;
      a.initializations = a.initializations;
   }

   private int get(int a) {
      if (a.outputLocals != null && a < a.outputLocals.length) {
         int a = a.outputLocals[a];
         if (a == 0) {
            a = a.outputLocals[a] = 33554432 | a;
         }

         return a;
      } else {
         return 33554432 | a;
      }
   }

   private void set(int a, int a) {
      if (a.outputLocals == null) {
         a.outputLocals = new int[10];
      }

      int a = a.outputLocals.length;
      if (a >= a) {
         int[] a = new int[Math.max(a + 1, 2 * a)];
         System.arraycopy(a.outputLocals, 0, a, 0, a);
         a.outputLocals = a;
      }

      a.outputLocals[a] = a;
   }

   private void push(int a) {
      if (a.outputStack == null) {
         a.outputStack = new int[10];
      }

      int a = a.outputStack.length;
      if (a.outputStackTop >= a) {
         int[] a = new int[Math.max(a.outputStackTop + 1, 2 * a)];
         System.arraycopy(a.outputStack, 0, a, 0, a);
         a.outputStack = a;
      }

      a.outputStack[a.outputStackTop++] = a;
      int a = a.owner.inputStackTop + a.outputStackTop;
      if (a > a.owner.outputStackMax) {
         a.owner.outputStackMax = a;
      }

   }

   private void push(ClassWriter a, String a) {
      int a = type(a, a);
      if (a != 0) {
         a.push(a);
         if (a == 16777220 || a == 16777219) {
            a.push(16777216);
         }
      }

   }

   private static int type(ClassWriter a, String a) {
      int a = a.charAt(0) == '(' ? a.indexOf(41) + 1 : 0;
      String a;
      switch(a.charAt(a)) {
      case 'B':
      case 'C':
      case 'I':
      case 'S':
      case 'Z':
         return 16777217;
      case 'D':
         return 16777219;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'T':
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
         int a;
         for(a = a + 1; a.charAt(a) == '['; ++a) {
         }

         int a;
         switch(a.charAt(a)) {
         case 'B':
            a = 16777226;
            break;
         case 'C':
            a = 16777227;
            break;
         case 'D':
            a = 16777219;
            break;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            a = a.substring(a + 1, a.length() - 1);
            a = 24117248 | a.addType(a);
            break;
         case 'F':
            a = 16777218;
            break;
         case 'I':
            a = 16777217;
            break;
         case 'J':
            a = 16777220;
            break;
         case 'S':
            a = 16777228;
            break;
         case 'Z':
            a = 16777225;
         }

         return a - a << 28 | a;
      case 'F':
         return 16777218;
      case 'J':
         return 16777220;
      case 'L':
         a = a.substring(a + 1, a.length() - 1);
         return 24117248 | a.addType(a);
      case 'V':
         return 0;
      }
   }

   private int pop() {
      return a.outputStackTop > 0 ? a.outputStack[--a.outputStackTop] : 50331648 | -(--a.owner.inputStackTop);
   }

   private void pop(int a) {
      if (a.outputStackTop >= a) {
         a.outputStackTop -= a;
      } else {
         Label var10000 = a.owner;
         var10000.inputStackTop -= a - a.outputStackTop;
         a.outputStackTop = 0;
      }

   }

   private void pop(String a) {
      char a = a.charAt(0);
      if (a == '(') {
         a.pop((Type.getArgumentsAndReturnSizes(a) >> 2) - 1);
      } else if (a != 'J' && a != 'D') {
         a.pop(1);
      } else {
         a.pop(2);
      }

   }

   private void init(int a) {
      if (a.initializations == null) {
         a.initializations = new int[2];
      }

      int a = a.initializations.length;
      if (a.initializationCount >= a) {
         int[] a = new int[Math.max(a.initializationCount + 1, 2 * a)];
         System.arraycopy(a.initializations, 0, a, 0, a);
         a.initializations = a;
      }

      a.initializations[a.initializationCount++] = a;
   }

   private int init(ClassWriter a, int a) {
      int a;
      if (a == 16777222) {
         a = 24117248 | a.addType(a.thisName);
      } else {
         if ((a & -1048576) != 25165824) {
            return a;
         }

         String a = a.typeTable[a & 1048575].strVal1;
         a = 24117248 | a.addType(a);
      }

      for(int a = 0; a < a.initializationCount; ++a) {
         int a = a.initializations[a];
         int a = a & -268435456;
         int a = a & 251658240;
         if (a == 33554432) {
            a = a + a.inputLocals[a & 8388607];
         } else if (a == 50331648) {
            a = a + a.inputStack[a.inputStack.length - (a & 8388607)];
         }

         if (a == a) {
            return a;
         }
      }

      return a;
   }

   final void initInputFrame(ClassWriter a, int a, Type[] a, int a) {
      a.inputLocals = new int[a];
      a.inputStack = new int[0];
      int a = 0;
      if ((a & 8) == 0) {
         if ((a & 524288) == 0) {
            a.inputLocals[a++] = 24117248 | a.addType(a.thisName);
         } else {
            a.inputLocals[a++] = 16777222;
         }
      }

      for(int a = 0; a < a.length; ++a) {
         int a = type(a, a[a].getDescriptor());
         a.inputLocals[a++] = a;
         if (a == 16777220 || a == 16777219) {
            a.inputLocals[a++] = 16777216;
         }
      }

      while(a < a) {
         a.inputLocals[a++] = 16777216;
      }

   }

   void execute(int a, int a, ClassWriter a, Item a) {
      int a;
      int a;
      int a;
      String a;
      switch(a) {
      case 0:
      case 116:
      case 117:
      case 118:
      case 119:
      case 145:
      case 146:
      case 147:
      case 167:
      case 177:
         break;
      case 1:
         a.push(16777221);
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 16:
      case 17:
      case 21:
         a.push(16777217);
         break;
      case 9:
      case 10:
      case 22:
         a.push(16777220);
         a.push(16777216);
         break;
      case 11:
      case 12:
      case 13:
      case 23:
         a.push(16777218);
         break;
      case 14:
      case 15:
      case 24:
         a.push(16777219);
         a.push(16777216);
         break;
      case 18:
         switch(a.type) {
         case 3:
            a.push(16777217);
            return;
         case 4:
            a.push(16777218);
            return;
         case 5:
            a.push(16777220);
            a.push(16777216);
            return;
         case 6:
            a.push(16777219);
            a.push(16777216);
            return;
         case 7:
            a.push(24117248 | a.addType("java/lang/Class"));
            return;
         case 8:
            a.push(24117248 | a.addType("java/lang/String"));
            return;
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            a.push(24117248 | a.addType("java/lang/invoke/MethodHandle"));
            return;
         case 16:
            a.push(24117248 | a.addType("java/lang/invoke/MethodType"));
            return;
         }
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
      case 197:
      default:
         a.pop(a);
         a.push(a, a.strVal1);
         break;
      case 25:
         a.push(a.get(a));
         break;
      case 46:
      case 51:
      case 52:
      case 53:
         a.pop(2);
         a.push(16777217);
         break;
      case 47:
      case 143:
         a.pop(2);
         a.push(16777220);
         a.push(16777216);
         break;
      case 48:
         a.pop(2);
         a.push(16777218);
         break;
      case 49:
      case 138:
         a.pop(2);
         a.push(16777219);
         a.push(16777216);
         break;
      case 50:
         a.pop(1);
         a = a.pop();
         a.push(-268435456 + a);
         break;
      case 54:
      case 56:
      case 58:
         a = a.pop();
         a.set(a, a);
         if (a > 0) {
            a = a.get(a - 1);
            if (a != 16777220 && a != 16777219) {
               if ((a & 251658240) != 16777216) {
                  a.set(a - 1, a | 8388608);
               }
            } else {
               a.set(a - 1, 16777216);
            }
         }
         break;
      case 55:
      case 57:
         a.pop(1);
         a = a.pop();
         a.set(a, a);
         a.set(a + 1, 16777216);
         if (a > 0) {
            a = a.get(a - 1);
            if (a != 16777220 && a != 16777219) {
               if ((a & 251658240) != 16777216) {
                  a.set(a - 1, a | 8388608);
               }
            } else {
               a.set(a - 1, 16777216);
            }
         }
         break;
      case 79:
      case 81:
      case 83:
      case 84:
      case 85:
      case 86:
         a.pop(3);
         break;
      case 80:
      case 82:
         a.pop(4);
         break;
      case 87:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 174:
      case 176:
      case 191:
      case 194:
      case 195:
      case 198:
      case 199:
         a.pop(1);
         break;
      case 88:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 173:
      case 175:
         a.pop(2);
         break;
      case 89:
         a = a.pop();
         a.push(a);
         a.push(a);
         break;
      case 90:
         a = a.pop();
         a = a.pop();
         a.push(a);
         a.push(a);
         a.push(a);
         break;
      case 91:
         a = a.pop();
         a = a.pop();
         a = a.pop();
         a.push(a);
         a.push(a);
         a.push(a);
         a.push(a);
         break;
      case 92:
         a = a.pop();
         a = a.pop();
         a.push(a);
         a.push(a);
         a.push(a);
         a.push(a);
         break;
      case 93:
         a = a.pop();
         a = a.pop();
         a = a.pop();
         a.push(a);
         a.push(a);
         a.push(a);
         a.push(a);
         a.push(a);
         break;
      case 94:
         a = a.pop();
         a = a.pop();
         a = a.pop();
         int a = a.pop();
         a.push(a);
         a.push(a);
         a.push(a);
         a.push(a);
         a.push(a);
         a.push(a);
         break;
      case 95:
         a = a.pop();
         a = a.pop();
         a.push(a);
         a.push(a);
         break;
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
      case 136:
      case 142:
      case 149:
      case 150:
         a.pop(2);
         a.push(16777217);
         break;
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 127:
      case 129:
      case 131:
         a.pop(4);
         a.push(16777220);
         a.push(16777216);
         break;
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
      case 137:
      case 144:
         a.pop(2);
         a.push(16777218);
         break;
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
         a.pop(4);
         a.push(16777219);
         a.push(16777216);
         break;
      case 121:
      case 123:
      case 125:
         a.pop(3);
         a.push(16777220);
         a.push(16777216);
         break;
      case 132:
         a.set(a, 16777217);
         break;
      case 133:
      case 140:
         a.pop(1);
         a.push(16777220);
         a.push(16777216);
         break;
      case 134:
         a.pop(1);
         a.push(16777218);
         break;
      case 135:
      case 141:
         a.pop(1);
         a.push(16777219);
         a.push(16777216);
         break;
      case 139:
      case 190:
      case 193:
         a.pop(1);
         a.push(16777217);
         break;
      case 148:
      case 151:
      case 152:
         a.pop(4);
         a.push(16777217);
         break;
      case 168:
      case 169:
         throw new RuntimeException("JSR/RET are not supported with computeFrames option");
      case 178:
         a.push(a, a.strVal3);
         break;
      case 179:
         a.pop(a.strVal3);
         break;
      case 180:
         a.pop(1);
         a.push(a, a.strVal3);
         break;
      case 181:
         a.pop(a.strVal3);
         a.pop();
         break;
      case 182:
      case 183:
      case 184:
      case 185:
         a.pop(a.strVal3);
         if (a != 184) {
            a = a.pop();
            if (a == 183 && a.strVal2.charAt(0) == '<') {
               a.init(a);
            }
         }

         a.push(a, a.strVal3);
         break;
      case 186:
         a.pop(a.strVal2);
         a.push(a, a.strVal2);
         break;
      case 187:
         a.push(25165824 | a.addUninitializedType(a.strVal1, a));
         break;
      case 188:
         a.pop();
         switch(a) {
         case 4:
            a.push(285212681);
            return;
         case 5:
            a.push(285212683);
            return;
         case 6:
            a.push(285212674);
            return;
         case 7:
            a.push(285212675);
            return;
         case 8:
            a.push(285212682);
            return;
         case 9:
            a.push(285212684);
            return;
         case 10:
            a.push(285212673);
            return;
         default:
            a.push(285212676);
            return;
         }
      case 189:
         a = a.strVal1;
         a.pop();
         if (a.charAt(0) == '[') {
            a.push(a, '[' + a);
         } else {
            a.push(292552704 | a.addType(a));
         }
         break;
      case 192:
         a = a.strVal1;
         a.pop();
         if (a.charAt(0) == '[') {
            a.push(a, a);
         } else {
            a.push(24117248 | a.addType(a));
         }
      }

   }

   final boolean merge(ClassWriter a, Frame a, int a) {
      boolean a = false;
      int a = a.inputLocals.length;
      int a = a.inputStack.length;
      if (a.inputLocals == null) {
         a.inputLocals = new int[a];
         a = true;
      }

      int a;
      int a;
      int a;
      int a;
      int a;
      for(a = 0; a < a; ++a) {
         if (a.outputLocals != null && a < a.outputLocals.length) {
            a = a.outputLocals[a];
            if (a == 0) {
               a = a.inputLocals[a];
            } else {
               a = a & -268435456;
               a = a & 251658240;
               if (a == 16777216) {
                  a = a;
               } else {
                  if (a == 33554432) {
                     a = a + a.inputLocals[a & 8388607];
                  } else {
                     a = a + a.inputStack[a - (a & 8388607)];
                  }

                  if ((a & 8388608) != 0 && (a == 16777220 || a == 16777219)) {
                     a = 16777216;
                  }
               }
            }
         } else {
            a = a.inputLocals[a];
         }

         if (a.initializations != null) {
            a = a.init(a, a);
         }

         a |= merge(a, a, a.inputLocals, a);
      }

      if (a > 0) {
         for(a = 0; a < a; ++a) {
            a = a.inputLocals[a];
            a |= merge(a, a, a.inputLocals, a);
         }

         if (a.inputStack == null) {
            a.inputStack = new int[1];
            a = true;
         }

         a |= merge(a, a, a.inputStack, 0);
         return a;
      } else {
         int a = a.inputStack.length + a.owner.inputStackTop;
         if (a.inputStack == null) {
            a.inputStack = new int[a + a.outputStackTop];
            a = true;
         }

         for(a = 0; a < a; ++a) {
            a = a.inputStack[a];
            if (a.initializations != null) {
               a = a.init(a, a);
            }

            a |= merge(a, a, a.inputStack, a);
         }

         for(a = 0; a < a.outputStackTop; ++a) {
            a = a.outputStack[a];
            a = a & -268435456;
            a = a & 251658240;
            if (a == 16777216) {
               a = a;
            } else {
               if (a == 33554432) {
                  a = a + a.inputLocals[a & 8388607];
               } else {
                  a = a + a.inputStack[a - (a & 8388607)];
               }

               if ((a & 8388608) != 0 && (a == 16777220 || a == 16777219)) {
                  a = 16777216;
               }
            }

            if (a.initializations != null) {
               a = a.init(a, a);
            }

            a |= merge(a, a, a.inputStack, a + a);
         }

         return a;
      }
   }

   private static boolean merge(ClassWriter a, int a, int[] a, int a) {
      int a = a[a];
      if (a == a) {
         return false;
      } else {
         if ((a & 268435455) == 16777221) {
            if (a == 16777221) {
               return false;
            }

            a = 16777221;
         }

         if (a == 0) {
            a[a] = a;
            return true;
         } else {
            int a;
            if ((a & 267386880) != 24117248 && (a & -268435456) == 0) {
               if (a == 16777221) {
                  a = (a & 267386880) != 24117248 && (a & -268435456) == 0 ? 16777216 : a;
               } else {
                  a = 16777216;
               }
            } else {
               if (a == 16777221) {
                  return false;
               }

               int a;
               if ((a & -1048576) == (a & -1048576)) {
                  if ((a & 267386880) == 24117248) {
                     a = a & -268435456 | 24117248 | a.getMergedType(a & 1048575, a & 1048575);
                  } else {
                     a = -268435456 + (a & -268435456);
                     a = a | 24117248 | a.addType("java/lang/Object");
                  }
               } else if ((a & 267386880) != 24117248 && (a & -268435456) == 0) {
                  a = 16777216;
               } else {
                  a = ((a & -268435456) != 0 && (a & 267386880) != 24117248 ? -268435456 : 0) + (a & -268435456);
                  int a = ((a & -268435456) != 0 && (a & 267386880) != 24117248 ? -268435456 : 0) + (a & -268435456);
                  a = Math.min(a, a) | 24117248 | a.addType("java/lang/Object");
               }
            }

            if (a != a) {
               a[a] = a;
               return true;
            } else {
               return false;
            }
         }
      }
   }

   static {
      int[] a = new int[202];
      String a = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";

      for(int a = 0; a < a.length; ++a) {
         a[a] = a.charAt(a) - 69;
      }

      SIZE = a;
   }
}

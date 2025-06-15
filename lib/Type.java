package org.spongepowered.asm.lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type {
   public static final int VOID = 0;
   public static final int BOOLEAN = 1;
   public static final int CHAR = 2;
   public static final int BYTE = 3;
   public static final int SHORT = 4;
   public static final int INT = 5;
   public static final int FLOAT = 6;
   public static final int LONG = 7;
   public static final int DOUBLE = 8;
   public static final int ARRAY = 9;
   public static final int OBJECT = 10;
   public static final int METHOD = 11;
   public static final Type VOID_TYPE = new Type(0, (char[])null, 1443168256, 1);
   public static final Type BOOLEAN_TYPE = new Type(1, (char[])null, 1509950721, 1);
   public static final Type CHAR_TYPE = new Type(2, (char[])null, 1124075009, 1);
   public static final Type BYTE_TYPE = new Type(3, (char[])null, 1107297537, 1);
   public static final Type SHORT_TYPE = new Type(4, (char[])null, 1392510721, 1);
   public static final Type INT_TYPE = new Type(5, (char[])null, 1224736769, 1);
   public static final Type FLOAT_TYPE = new Type(6, (char[])null, 1174536705, 1);
   public static final Type LONG_TYPE = new Type(7, (char[])null, 1241579778, 1);
   public static final Type DOUBLE_TYPE = new Type(8, (char[])null, 1141048066, 1);
   private final int sort;
   private final char[] buf;
   private final int off;
   private final int len;

   private Type(int a, char[] a, int a, int a) {
      a.sort = a;
      a.buf = a;
      a.off = a;
      a.len = a;
   }

   public static Type getType(String a) {
      return getType(a.toCharArray(), 0);
   }

   public static Type getObjectType(String a) {
      char[] a = a.toCharArray();
      return new Type(a[0] == '[' ? 9 : 10, a, 0, a.length);
   }

   public static Type getMethodType(String a) {
      return getType(a.toCharArray(), 0);
   }

   public static Type getMethodType(Type a, Type... a) {
      return getType(getMethodDescriptor(a, a));
   }

   public static Type getType(Class<?> a) {
      if (a.isPrimitive()) {
         if (a == Integer.TYPE) {
            return INT_TYPE;
         } else if (a == Void.TYPE) {
            return VOID_TYPE;
         } else if (a == Boolean.TYPE) {
            return BOOLEAN_TYPE;
         } else if (a == Byte.TYPE) {
            return BYTE_TYPE;
         } else if (a == Character.TYPE) {
            return CHAR_TYPE;
         } else if (a == Short.TYPE) {
            return SHORT_TYPE;
         } else if (a == Double.TYPE) {
            return DOUBLE_TYPE;
         } else {
            return a == Float.TYPE ? FLOAT_TYPE : LONG_TYPE;
         }
      } else {
         return getType(getDescriptor(a));
      }
   }

   public static Type getType(Constructor<?> a) {
      return getType(getConstructorDescriptor(a));
   }

   public static Type getType(Method a) {
      return getType(getMethodDescriptor(a));
   }

   public static Type[] getArgumentTypes(String a) {
      char[] a = a.toCharArray();
      int a = 1;
      int a = 0;

      while(true) {
         while(true) {
            char a = a[a++];
            if (a == ')') {
               Type[] a = new Type[a];
               a = 1;

               for(a = 0; a[a] != ')'; ++a) {
                  a[a] = getType(a, a);
                  a += a[a].len + (a[a].sort == 10 ? 2 : 0);
               }

               return a;
            }

            if (a == 'L') {
               while(a[a++] != ';') {
               }

               ++a;
            } else if (a != '[') {
               ++a;
            }
         }
      }
   }

   public static Type[] getArgumentTypes(Method a) {
      Class<?>[] a = a.getParameterTypes();
      Type[] a = new Type[a.length];

      for(int a = a.length - 1; a >= 0; --a) {
         a[a] = getType(a[a]);
      }

      return a;
   }

   public static Type getReturnType(String a) {
      char[] a = a.toCharArray();
      int a = 1;

      while(true) {
         char a;
         do {
            a = a[a++];
            if (a == ')') {
               return getType(a, a);
            }
         } while(a != 'L');

         while(true) {
            if (a[a++] != ';') {
               continue;
            }
         }
      }
   }

   public static Type getReturnType(Method a) {
      return getType(a.getReturnType());
   }

   public static int getArgumentsAndReturnSizes(String a) {
      int a = 1;
      int a = 1;

      while(true) {
         while(true) {
            char a = a.charAt(a++);
            if (a == ')') {
               a = a.charAt(a);
               return a << 2 | (a == 'V' ? 0 : (a != 'D' && a != 'J' ? 1 : 2));
            }

            if (a == 'L') {
               while(a.charAt(a++) != ';') {
               }

               ++a;
            } else if (a != '[') {
               if (a != 'D' && a != 'J') {
                  ++a;
               } else {
                  a += 2;
               }
            } else {
               while((a = a.charAt(a)) == '[') {
                  ++a;
               }

               if (a == 'D' || a == 'J') {
                  --a;
               }
            }
         }
      }
   }

   private static Type getType(char[] a, int a) {
      int a;
      switch(a[a]) {
      case 'B':
         return BYTE_TYPE;
      case 'C':
         return CHAR_TYPE;
      case 'D':
         return DOUBLE_TYPE;
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
         return new Type(11, a, a, a.length - a);
      case 'F':
         return FLOAT_TYPE;
      case 'I':
         return INT_TYPE;
      case 'J':
         return LONG_TYPE;
      case 'L':
         for(a = 1; a[a + a] != ';'; ++a) {
         }

         return new Type(10, a, a + 1, a - 1);
      case 'S':
         return SHORT_TYPE;
      case 'V':
         return VOID_TYPE;
      case 'Z':
         return BOOLEAN_TYPE;
      case '[':
         for(a = 1; a[a + a] == '['; ++a) {
         }

         if (a[a + a] == 'L') {
            ++a;

            while(a[a + a] != ';') {
               ++a;
            }
         }

         return new Type(9, a, a, a + 1);
      }
   }

   public int getSort() {
      return a.sort;
   }

   public int getDimensions() {
      int a;
      for(a = 1; a.buf[a.off + a] == '['; ++a) {
      }

      return a;
   }

   public Type getElementType() {
      return getType(a.buf, a.off + a.getDimensions());
   }

   public String getClassName() {
      switch(a.sort) {
      case 0:
         return "void";
      case 1:
         return "boolean";
      case 2:
         return "char";
      case 3:
         return "byte";
      case 4:
         return "short";
      case 5:
         return "int";
      case 6:
         return "float";
      case 7:
         return "long";
      case 8:
         return "double";
      case 9:
         StringBuilder a = new StringBuilder(a.getElementType().getClassName());

         for(int a = a.getDimensions(); a > 0; --a) {
            a.append("[]");
         }

         return a.toString();
      case 10:
         return (new String(a.buf, a.off, a.len)).replace('/', '.');
      default:
         return null;
      }
   }

   public String getInternalName() {
      return new String(a.buf, a.off, a.len);
   }

   public Type[] getArgumentTypes() {
      return getArgumentTypes(a.getDescriptor());
   }

   public Type getReturnType() {
      return getReturnType(a.getDescriptor());
   }

   public int getArgumentsAndReturnSizes() {
      return getArgumentsAndReturnSizes(a.getDescriptor());
   }

   public String getDescriptor() {
      StringBuilder a = new StringBuilder();
      a.getDescriptor(a);
      return a.toString();
   }

   public static String getMethodDescriptor(Type a, Type... a) {
      StringBuilder a = new StringBuilder();
      a.append('(');

      for(int a = 0; a < a.length; ++a) {
         a[a].getDescriptor(a);
      }

      a.append(')');
      a.getDescriptor(a);
      return a.toString();
   }

   private void getDescriptor(StringBuilder a) {
      if (a.buf == null) {
         a.append((char)((a.off & -16777216) >>> 24));
      } else if (a.sort == 10) {
         a.append('L');
         a.append(a.buf, a.off, a.len);
         a.append(';');
      } else {
         a.append(a.buf, a.off, a.len);
      }

   }

   public static String getInternalName(Class<?> a) {
      return a.getName().replace('.', '/');
   }

   public static String getDescriptor(Class<?> a) {
      StringBuilder a = new StringBuilder();
      getDescriptor(a, a);
      return a.toString();
   }

   public static String getConstructorDescriptor(Constructor<?> a) {
      Class<?>[] a = a.getParameterTypes();
      StringBuilder a = new StringBuilder();
      a.append('(');

      for(int a = 0; a < a.length; ++a) {
         getDescriptor(a, a[a]);
      }

      return a.append(")V").toString();
   }

   public static String getMethodDescriptor(Method a) {
      Class<?>[] a = a.getParameterTypes();
      StringBuilder a = new StringBuilder();
      a.append('(');

      for(int a = 0; a < a.length; ++a) {
         getDescriptor(a, a[a]);
      }

      a.append(')');
      getDescriptor(a, a.getReturnType());
      return a.toString();
   }

   private static void getDescriptor(StringBuilder a, Class<?> a) {
      Class a;
      for(a = a; !a.isPrimitive(); a = a.getComponentType()) {
         if (!a.isArray()) {
            a.append('L');
            String a = a.getName();
            int a = a.length();

            for(int a = 0; a < a; ++a) {
               char a = a.charAt(a);
               a.append(a == '.' ? '/' : a);
            }

            a.append(';');
            return;
         }

         a.append('[');
      }

      char a;
      if (a == Integer.TYPE) {
         a = 'I';
      } else if (a == Void.TYPE) {
         a = 'V';
      } else if (a == Boolean.TYPE) {
         a = 'Z';
      } else if (a == Byte.TYPE) {
         a = 'B';
      } else if (a == Character.TYPE) {
         a = 'C';
      } else if (a == Short.TYPE) {
         a = 'S';
      } else if (a == Double.TYPE) {
         a = 'D';
      } else if (a == Float.TYPE) {
         a = 'F';
      } else {
         a = 'J';
      }

      a.append(a);
   }

   public int getSize() {
      return a.buf == null ? a.off & 255 : 1;
   }

   public int getOpcode(int a) {
      return a != 46 && a != 79 ? a + (a.buf == null ? (a.off & 16711680) >> 16 : 4) : a + (a.buf == null ? (a.off & '\uff00') >> 8 : 4);
   }

   public boolean equals(Object a) {
      if (a == a) {
         return true;
      } else if (!(a instanceof Type)) {
         return false;
      } else {
         Type a = (Type)a;
         if (a.sort != a.sort) {
            return false;
         } else {
            if (a.sort >= 9) {
               if (a.len != a.len) {
                  return false;
               }

               int a = a.off;
               int a = a.off;

               for(int a = a + a.len; a < a; ++a) {
                  if (a.buf[a] != a.buf[a]) {
                     return false;
                  }

                  ++a;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int a = 13 * a.sort;
      if (a.sort >= 9) {
         int a = a.off;

         for(int a = a + a.len; a < a; ++a) {
            a = 17 * (a + a.buf[a]);
         }
      }

      return a;
   }

   public String toString() {
      return a.getDescriptor();
   }
}

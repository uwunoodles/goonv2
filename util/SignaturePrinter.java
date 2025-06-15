package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

public class SignaturePrinter {
   private final String name;
   private final Type returnType;
   private final Type[] argTypes;
   private final String[] argNames;
   private String modifiers;
   private boolean fullyQualified;

   public SignaturePrinter(MethodNode a) {
      this(a.name, Type.VOID_TYPE, Type.getArgumentTypes(a.desc));
      a.setModifiers(a);
   }

   public SignaturePrinter(MethodNode a, String[] a) {
      this(a.name, Type.VOID_TYPE, Type.getArgumentTypes(a.desc), a);
      a.setModifiers(a);
   }

   public SignaturePrinter(MemberInfo a) {
      this(a.name, a.desc);
   }

   public SignaturePrinter(String a, String a) {
      this(a, Type.getReturnType(a), Type.getArgumentTypes(a));
   }

   public SignaturePrinter(String a, Type a, Type[] a) {
      a.modifiers = "private void";
      a.name = a;
      a.returnType = a;
      a.argTypes = new Type[a.length];
      a.argNames = new String[a.length];
      int a = 0;

      for(int var5 = 0; a < a.length; ++a) {
         if (a[a] != null) {
            a.argTypes[a] = a[a];
            a.argNames[a] = "var" + var5++;
         }
      }

   }

   public SignaturePrinter(String a, Type a, LocalVariableNode[] a) {
      a.modifiers = "private void";
      a.name = a;
      a.returnType = a;
      a.argTypes = new Type[a.length];
      a.argNames = new String[a.length];

      for(int a = 0; a < a.length; ++a) {
         if (a[a] != null) {
            a.argTypes[a] = Type.getType(a[a].desc);
            a.argNames[a] = a[a].name;
         }
      }

   }

   public SignaturePrinter(String a, Type a, Type[] a, String[] a) {
      a.modifiers = "private void";
      a.name = a;
      a.returnType = a;
      a.argTypes = a;
      a.argNames = a;
      if (a.argTypes.length > a.argNames.length) {
         throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", a.argNames.length, a.argTypes.length));
      }
   }

   public String getFormattedArgs() {
      return a.appendArgs(new StringBuilder(), true, true).toString();
   }

   public String getReturnType() {
      return getTypeName(a.returnType, false, a.fullyQualified);
   }

   public void setModifiers(MethodNode a) {
      String a = getTypeName(Type.getReturnType(a.desc), false, a.fullyQualified);
      if ((a.access & 1) != 0) {
         a.setModifiers("public " + a);
      } else if ((a.access & 4) != 0) {
         a.setModifiers("protected " + a);
      } else if ((a.access & 2) != 0) {
         a.setModifiers("private " + a);
      } else {
         a.setModifiers(a);
      }

   }

   public SignaturePrinter setModifiers(String a) {
      a.modifiers = a.replace("${returnType}", a.getReturnType());
      return a;
   }

   public SignaturePrinter setFullyQualified(boolean a) {
      a.fullyQualified = a;
      return a;
   }

   public boolean isFullyQualified() {
      return a.fullyQualified;
   }

   public String toString() {
      return a.appendArgs((new StringBuilder()).append(a.modifiers).append(" ").append(a.name), false, true).toString();
   }

   public String toDescriptor() {
      StringBuilder a = a.appendArgs(new StringBuilder(), true, false);
      return a.append(getTypeName(a.returnType, false, a.fullyQualified)).toString();
   }

   private StringBuilder appendArgs(StringBuilder a, boolean a, boolean a) {
      a.append('(');

      for(int a = 0; a < a.argTypes.length; ++a) {
         if (a.argTypes[a] != null) {
            if (a > 0) {
               a.append(',');
               if (a) {
                  a.append(' ');
               }
            }

            try {
               String a = a ? null : (Strings.isNullOrEmpty(a.argNames[a]) ? "unnamed" + a : a.argNames[a]);
               a.appendType(a, a.argTypes[a], a);
            } catch (Exception var6) {
               throw new RuntimeException(var6);
            }
         }
      }

      return a.append(")");
   }

   private StringBuilder appendType(StringBuilder a, Type a, String a) {
      switch(a.getSort()) {
      case 9:
         return appendArraySuffix(a.appendType(a, a.getElementType(), a), a);
      case 10:
         return a.appendType(a, a.getClassName(), a);
      default:
         a.append(getTypeName(a, false, a.fullyQualified));
         if (a != null) {
            a.append(' ').append(a);
         }

         return a;
      }
   }

   private StringBuilder appendType(StringBuilder a, String a, String a) {
      if (!a.fullyQualified) {
         a = a.substring(a.lastIndexOf(46) + 1);
      }

      a.append(a);
      if (a.endsWith("CallbackInfoReturnable")) {
         a.append('<').append(getTypeName(a.returnType, true, a.fullyQualified)).append('>');
      }

      if (a != null) {
         a.append(' ').append(a);
      }

      return a;
   }

   public static String getTypeName(Type a, boolean a) {
      return getTypeName(a, a, false);
   }

   public static String getTypeName(Type a, boolean a, boolean a) {
      switch(a.getSort()) {
      case 0:
         return a ? "Void" : "void";
      case 1:
         return a ? "Boolean" : "boolean";
      case 2:
         return a ? "Character" : "char";
      case 3:
         return a ? "Byte" : "byte";
      case 4:
         return a ? "Short" : "short";
      case 5:
         return a ? "Integer" : "int";
      case 6:
         return a ? "Float" : "float";
      case 7:
         return a ? "Long" : "long";
      case 8:
         return a ? "Double" : "double";
      case 9:
         return getTypeName(a.getElementType(), a, a) + arraySuffix(a);
      case 10:
         String a = a.getClassName();
         if (!a) {
            a = a.substring(a.lastIndexOf(46) + 1);
         }

         return a;
      default:
         return "Object";
      }
   }

   private static String arraySuffix(Type a) {
      return Strings.repeat("[]", a.getDimensions());
   }

   private static StringBuilder appendArraySuffix(StringBuilder a, Type a) {
      for(int a = 0; a < a.getDimensions(); ++a) {
         a.append("[]");
      }

      return a;
   }
}

package org.spongepowered.asm.lib.signature;

public class SignatureReader {
   private final String signature;

   public SignatureReader(String a) {
      a.signature = a;
   }

   public void accept(SignatureVisitor a) {
      String a = a.signature;
      int a = a.length();
      int a;
      if (a.charAt(0) == '<') {
         a = 2;

         char a;
         do {
            int a = a.indexOf(58, a);
            a.visitFormalTypeParameter(a.substring(a - 1, a));
            a = a + 1;
            a = a.charAt(a);
            if (a == 'L' || a == '[' || a == 'T') {
               a = parseType(a, a, a.visitClassBound());
            }

            while((a = a.charAt(a++)) == ':') {
               a = parseType(a, a, a.visitInterfaceBound());
            }
         } while(a != '>');
      } else {
         a = 0;
      }

      if (a.charAt(a) == '(') {
         ++a;

         while(a.charAt(a) != ')') {
            a = parseType(a, a, a.visitParameterType());
         }

         for(a = parseType(a, a + 1, a.visitReturnType()); a < a; a = parseType(a, a + 1, a.visitExceptionType())) {
         }
      } else {
         for(a = parseType(a, a, a.visitSuperclass()); a < a; a = parseType(a, a, a.visitInterface())) {
         }
      }

   }

   public void acceptType(SignatureVisitor a) {
      parseType(a.signature, 0, a);
   }

   private static int parseType(String a, int a, SignatureVisitor a) {
      char a;
      switch(a = a.charAt(a++)) {
      case 'B':
      case 'C':
      case 'D':
      case 'F':
      case 'I':
      case 'J':
      case 'S':
      case 'V':
      case 'Z':
         a.visitBaseType(a);
         return a;
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
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
         int a = a;
         boolean a = false;
         boolean a = false;

         while(true) {
            label40:
            while(true) {
               String a;
               switch(a = a.charAt(a++)) {
               case '.':
               case ';':
                  if (!a) {
                     a = a.substring(a, a - 1);
                     if (a) {
                        a.visitInnerClassType(a);
                     } else {
                        a.visitClassType(a);
                     }
                  }

                  if (a == ';') {
                     a.visitEnd();
                     return a;
                  }

                  a = a;
                  a = false;
                  a = true;
                  break;
               case '<':
                  a = a.substring(a, a - 1);
                  if (a) {
                     a.visitInnerClassType(a);
                  } else {
                     a.visitClassType(a);
                  }

                  a = true;

                  while(true) {
                     while(true) {
                        switch(a = a.charAt(a)) {
                        case '*':
                           ++a;
                           a.visitTypeArgument();
                           break;
                        case '+':
                        case '-':
                           a = parseType(a, a + 1, a.visitTypeArgument(a));
                           break;
                        case '>':
                           continue label40;
                        default:
                           a = parseType(a, a, a.visitTypeArgument('='));
                        }
                     }
                  }
               }
            }
         }
      case 'T':
         int a = a.indexOf(59, a);
         a.visitTypeVariable(a.substring(a, a));
         return a + 1;
      case '[':
         return parseType(a, a, a.visitArrayType());
      }
   }
}

package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

public final class TraceSignatureVisitor extends SignatureVisitor {
   private final StringBuilder declaration;
   private boolean isInterface;
   private boolean seenFormalParameter;
   private boolean seenInterfaceBound;
   private boolean seenParameter;
   private boolean seenInterface;
   private StringBuilder returnType;
   private StringBuilder exceptions;
   private int argumentStack;
   private int arrayStack;
   private String separator = "";

   public TraceSignatureVisitor(int a) {
      super(327680);
      a.isInterface = (a & 512) != 0;
      a.declaration = new StringBuilder();
   }

   private TraceSignatureVisitor(StringBuilder a) {
      super(327680);
      a.declaration = a;
   }

   public void visitFormalTypeParameter(String a) {
      a.declaration.append(a.seenFormalParameter ? ", " : "<").append(a);
      a.seenFormalParameter = true;
      a.seenInterfaceBound = false;
   }

   public SignatureVisitor visitClassBound() {
      a.separator = " extends ";
      a.startType();
      return a;
   }

   public SignatureVisitor visitInterfaceBound() {
      a.separator = a.seenInterfaceBound ? ", " : " extends ";
      a.seenInterfaceBound = true;
      a.startType();
      return a;
   }

   public SignatureVisitor visitSuperclass() {
      a.endFormals();
      a.separator = " extends ";
      a.startType();
      return a;
   }

   public SignatureVisitor visitInterface() {
      a.separator = a.seenInterface ? ", " : (a.isInterface ? " extends " : " implements ");
      a.seenInterface = true;
      a.startType();
      return a;
   }

   public SignatureVisitor visitParameterType() {
      a.endFormals();
      if (a.seenParameter) {
         a.declaration.append(", ");
      } else {
         a.seenParameter = true;
         a.declaration.append('(');
      }

      a.startType();
      return a;
   }

   public SignatureVisitor visitReturnType() {
      a.endFormals();
      if (a.seenParameter) {
         a.seenParameter = false;
      } else {
         a.declaration.append('(');
      }

      a.declaration.append(')');
      a.returnType = new StringBuilder();
      return new TraceSignatureVisitor(a.returnType);
   }

   public SignatureVisitor visitExceptionType() {
      if (a.exceptions == null) {
         a.exceptions = new StringBuilder();
      } else {
         a.exceptions.append(", ");
      }

      return new TraceSignatureVisitor(a.exceptions);
   }

   public void visitBaseType(char a) {
      switch(a) {
      case 'B':
         a.declaration.append("byte");
         break;
      case 'C':
         a.declaration.append("char");
         break;
      case 'D':
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
      case 'W':
      case 'X':
      case 'Y':
      default:
         a.declaration.append("double");
         break;
      case 'F':
         a.declaration.append("float");
         break;
      case 'I':
         a.declaration.append("int");
         break;
      case 'J':
         a.declaration.append("long");
         break;
      case 'S':
         a.declaration.append("short");
         break;
      case 'V':
         a.declaration.append("void");
         break;
      case 'Z':
         a.declaration.append("boolean");
      }

      a.endType();
   }

   public void visitTypeVariable(String a) {
      a.declaration.append(a);
      a.endType();
   }

   public SignatureVisitor visitArrayType() {
      a.startType();
      a.arrayStack |= 1;
      return a;
   }

   public void visitClassType(String a) {
      if ("java/lang/Object".equals(a)) {
         boolean a = a.argumentStack % 2 != 0 || a.seenParameter;
         if (a) {
            a.declaration.append(a.separator).append(a.replace('/', '.'));
         }
      } else {
         a.declaration.append(a.separator).append(a.replace('/', '.'));
      }

      a.separator = "";
      a.argumentStack *= 2;
   }

   public void visitInnerClassType(String a) {
      if (a.argumentStack % 2 != 0) {
         a.declaration.append('>');
      }

      a.argumentStack /= 2;
      a.declaration.append('.');
      a.declaration.append(a.separator).append(a.replace('/', '.'));
      a.separator = "";
      a.argumentStack *= 2;
   }

   public void visitTypeArgument() {
      if (a.argumentStack % 2 == 0) {
         ++a.argumentStack;
         a.declaration.append('<');
      } else {
         a.declaration.append(", ");
      }

      a.declaration.append('?');
   }

   public SignatureVisitor visitTypeArgument(char a) {
      if (a.argumentStack % 2 == 0) {
         ++a.argumentStack;
         a.declaration.append('<');
      } else {
         a.declaration.append(", ");
      }

      if (a == '+') {
         a.declaration.append("? extends ");
      } else if (a == '-') {
         a.declaration.append("? super ");
      }

      a.startType();
      return a;
   }

   public void visitEnd() {
      if (a.argumentStack % 2 != 0) {
         a.declaration.append('>');
      }

      a.argumentStack /= 2;
      a.endType();
   }

   public String getDeclaration() {
      return a.declaration.toString();
   }

   public String getReturnType() {
      return a.returnType == null ? null : a.returnType.toString();
   }

   public String getExceptions() {
      return a.exceptions == null ? null : a.exceptions.toString();
   }

   private void endFormals() {
      if (a.seenFormalParameter) {
         a.declaration.append('>');
         a.seenFormalParameter = false;
      }

   }

   private void startType() {
      a.arrayStack *= 2;
   }

   private void endType() {
      if (a.arrayStack % 2 == 0) {
         a.arrayStack /= 2;
      } else {
         while(a.arrayStack % 2 != 0) {
            a.arrayStack /= 2;
            a.declaration.append("[]");
         }
      }

   }
}

package org.spongepowered.asm.lib.signature;

public class SignatureWriter extends SignatureVisitor {
   private final StringBuilder buf = new StringBuilder();
   private boolean hasFormals;
   private boolean hasParameters;
   private int argumentStack;

   public SignatureWriter() {
      super(327680);
   }

   public void visitFormalTypeParameter(String a) {
      if (!a.hasFormals) {
         a.hasFormals = true;
         a.buf.append('<');
      }

      a.buf.append(a);
      a.buf.append(':');
   }

   public SignatureVisitor visitClassBound() {
      return a;
   }

   public SignatureVisitor visitInterfaceBound() {
      a.buf.append(':');
      return a;
   }

   public SignatureVisitor visitSuperclass() {
      a.endFormals();
      return a;
   }

   public SignatureVisitor visitInterface() {
      return a;
   }

   public SignatureVisitor visitParameterType() {
      a.endFormals();
      if (!a.hasParameters) {
         a.hasParameters = true;
         a.buf.append('(');
      }

      return a;
   }

   public SignatureVisitor visitReturnType() {
      a.endFormals();
      if (!a.hasParameters) {
         a.buf.append('(');
      }

      a.buf.append(')');
      return a;
   }

   public SignatureVisitor visitExceptionType() {
      a.buf.append('^');
      return a;
   }

   public void visitBaseType(char a) {
      a.buf.append(a);
   }

   public void visitTypeVariable(String a) {
      a.buf.append('T');
      a.buf.append(a);
      a.buf.append(';');
   }

   public SignatureVisitor visitArrayType() {
      a.buf.append('[');
      return a;
   }

   public void visitClassType(String a) {
      a.buf.append('L');
      a.buf.append(a);
      a.argumentStack *= 2;
   }

   public void visitInnerClassType(String a) {
      a.endArguments();
      a.buf.append('.');
      a.buf.append(a);
      a.argumentStack *= 2;
   }

   public void visitTypeArgument() {
      if (a.argumentStack % 2 == 0) {
         ++a.argumentStack;
         a.buf.append('<');
      }

      a.buf.append('*');
   }

   public SignatureVisitor visitTypeArgument(char a) {
      if (a.argumentStack % 2 == 0) {
         ++a.argumentStack;
         a.buf.append('<');
      }

      if (a != '=') {
         a.buf.append(a);
      }

      return a;
   }

   public void visitEnd() {
      a.endArguments();
      a.buf.append(';');
   }

   public String toString() {
      return a.buf.toString();
   }

   private void endFormals() {
      if (a.hasFormals) {
         a.hasFormals = false;
         a.buf.append('>');
      }

   }

   private void endArguments() {
      if (a.argumentStack % 2 != 0) {
         a.buf.append('>');
      }

      a.argumentStack /= 2;
   }
}

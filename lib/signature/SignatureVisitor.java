package org.spongepowered.asm.lib.signature;

public abstract class SignatureVisitor {
   public static final char EXTENDS = '+';
   public static final char SUPER = '-';
   public static final char INSTANCEOF = '=';
   protected final int api;

   public SignatureVisitor(int a) {
      if (a != 262144 && a != 327680) {
         throw new IllegalArgumentException();
      } else {
         a.api = a;
      }
   }

   public void visitFormalTypeParameter(String a1) {
   }

   public SignatureVisitor visitClassBound() {
      return a;
   }

   public SignatureVisitor visitInterfaceBound() {
      return a;
   }

   public SignatureVisitor visitSuperclass() {
      return a;
   }

   public SignatureVisitor visitInterface() {
      return a;
   }

   public SignatureVisitor visitParameterType() {
      return a;
   }

   public SignatureVisitor visitReturnType() {
      return a;
   }

   public SignatureVisitor visitExceptionType() {
      return a;
   }

   public void visitBaseType(char a1) {
   }

   public void visitTypeVariable(String a1) {
   }

   public SignatureVisitor visitArrayType() {
      return a;
   }

   public void visitClassType(String a1) {
   }

   public void visitInnerClassType(String a1) {
   }

   public void visitTypeArgument() {
   }

   public SignatureVisitor visitTypeArgument(char a1) {
      return a;
   }

   public void visitEnd() {
   }
}

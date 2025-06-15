package org.spongepowered.asm.lib.commons;

import java.util.Stack;
import org.spongepowered.asm.lib.signature.SignatureVisitor;

public class SignatureRemapper extends SignatureVisitor {
   private final SignatureVisitor v;
   private final Remapper remapper;
   private Stack<String> classNames;

   public SignatureRemapper(SignatureVisitor a, Remapper a) {
      this(327680, a, a);
   }

   protected SignatureRemapper(int a, SignatureVisitor a, Remapper a) {
      super(a);
      a.classNames = new Stack();
      a.v = a;
      a.remapper = a;
   }

   public void visitClassType(String a) {
      a.classNames.push(a);
      a.v.visitClassType(a.remapper.mapType(a));
   }

   public void visitInnerClassType(String a) {
      String a = (String)a.classNames.pop();
      String a = a + '$' + a;
      a.classNames.push(a);
      String a = a.remapper.mapType(a) + '$';
      String a = a.remapper.mapType(a);
      int a = a.startsWith(a) ? a.length() : a.lastIndexOf(36) + 1;
      a.v.visitInnerClassType(a.substring(a));
   }

   public void visitFormalTypeParameter(String a) {
      a.v.visitFormalTypeParameter(a);
   }

   public void visitTypeVariable(String a) {
      a.v.visitTypeVariable(a);
   }

   public SignatureVisitor visitArrayType() {
      a.v.visitArrayType();
      return a;
   }

   public void visitBaseType(char a) {
      a.v.visitBaseType(a);
   }

   public SignatureVisitor visitClassBound() {
      a.v.visitClassBound();
      return a;
   }

   public SignatureVisitor visitExceptionType() {
      a.v.visitExceptionType();
      return a;
   }

   public SignatureVisitor visitInterface() {
      a.v.visitInterface();
      return a;
   }

   public SignatureVisitor visitInterfaceBound() {
      a.v.visitInterfaceBound();
      return a;
   }

   public SignatureVisitor visitParameterType() {
      a.v.visitParameterType();
      return a;
   }

   public SignatureVisitor visitReturnType() {
      a.v.visitReturnType();
      return a;
   }

   public SignatureVisitor visitSuperclass() {
      a.v.visitSuperclass();
      return a;
   }

   public void visitTypeArgument() {
      a.v.visitTypeArgument();
   }

   public SignatureVisitor visitTypeArgument(char a) {
      a.v.visitTypeArgument(a);
      return a;
   }

   public void visitEnd() {
      a.v.visitEnd();
      a.classNames.pop();
   }
}

package org.spongepowered.asm.util.asm;

import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.util.Bytecode;

public class MethodVisitorEx extends MethodVisitor {
   public MethodVisitorEx(MethodVisitor a) {
      super(327680, a);
   }

   public void visitConstant(byte a) {
      if (a > -2 && a < 6) {
         a.visitInsn(Bytecode.CONSTANTS_INT[a + 1]);
      } else {
         a.visitIntInsn(16, a);
      }
   }
}

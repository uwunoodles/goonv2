package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class AnalyzerException extends Exception {
   public final AbstractInsnNode node;

   public AnalyzerException(AbstractInsnNode a, String a) {
      super(a);
      a.node = a;
   }

   public AnalyzerException(AbstractInsnNode a, String a, Throwable a) {
      super(a, a);
      a.node = a;
   }

   public AnalyzerException(AbstractInsnNode a, String a, Object a, Value a) {
      super((a == null ? "Expected " : a + ": expected ") + a + ", but found " + a);
      a.node = a;
   }
}

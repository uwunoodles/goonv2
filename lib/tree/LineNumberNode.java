package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class LineNumberNode extends AbstractInsnNode {
   public int line;
   public LabelNode start;

   public LineNumberNode(int a, LabelNode a) {
      super(-1);
      a.line = a;
      a.start = a;
   }

   public int getType() {
      return 15;
   }

   public void accept(MethodVisitor a) {
      a.visitLineNumber(a.line, a.start.getLabel());
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a) {
      return new LineNumberNode(a.line, clone(a.start, a));
   }
}

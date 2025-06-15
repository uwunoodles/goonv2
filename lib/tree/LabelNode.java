package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

public class LabelNode extends AbstractInsnNode {
   private Label label;

   public LabelNode() {
      super(-1);
   }

   public LabelNode(Label a) {
      super(-1);
      a.label = a;
   }

   public int getType() {
      return 8;
   }

   public Label getLabel() {
      if (a.label == null) {
         a.label = new Label();
      }

      return a.label;
   }

   public void accept(MethodVisitor a) {
      a.visitLabel(a.getLabel());
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a) {
      return (AbstractInsnNode)a.get(a);
   }

   public void resetLabel() {
      a.label = null;
   }
}

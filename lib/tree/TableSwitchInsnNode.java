package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

public class TableSwitchInsnNode extends AbstractInsnNode {
   public int min;
   public int max;
   public LabelNode dflt;
   public List<LabelNode> labels;

   public TableSwitchInsnNode(int a, int a, LabelNode a, LabelNode... a) {
      super(170);
      a.min = a;
      a.max = a;
      a.dflt = a;
      a.labels = new ArrayList();
      if (a != null) {
         a.labels.addAll(Arrays.asList(a));
      }

   }

   public int getType() {
      return 11;
   }

   public void accept(MethodVisitor a) {
      Label[] a = new Label[a.labels.size()];

      for(int a = 0; a < a.length; ++a) {
         a[a] = ((LabelNode)a.labels.get(a)).getLabel();
      }

      a.visitTableSwitchInsn(a.min, a.max, a.dflt.getLabel(), a);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a) {
      return (new TableSwitchInsnNode(a.min, a.max, clone(a.dflt, a), clone(a.labels, a))).cloneAnnotations(a);
   }
}

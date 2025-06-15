package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

public class LookupSwitchInsnNode extends AbstractInsnNode {
   public LabelNode dflt;
   public List<Integer> keys;
   public List<LabelNode> labels;

   public LookupSwitchInsnNode(LabelNode a, int[] a, LabelNode[] a) {
      super(171);
      a.dflt = a;
      a.keys = new ArrayList(a == null ? 0 : a.length);
      a.labels = new ArrayList(a == null ? 0 : a.length);
      if (a != null) {
         for(int a = 0; a < a.length; ++a) {
            a.keys.add(a[a]);
         }
      }

      if (a != null) {
         a.labels.addAll(Arrays.asList(a));
      }

   }

   public int getType() {
      return 12;
   }

   public void accept(MethodVisitor a) {
      int[] a = new int[a.keys.size()];

      for(int a = 0; a < a.length; ++a) {
         a[a] = (Integer)a.keys.get(a);
      }

      Label[] a = new Label[a.labels.size()];

      for(int a = 0; a < a.length; ++a) {
         a[a] = ((LabelNode)a.labels.get(a)).getLabel();
      }

      a.visitLookupSwitchInsn(a.dflt.getLabel(), a, a);
      a.acceptAnnotations(a);
   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a) {
      LookupSwitchInsnNode a = new LookupSwitchInsnNode(clone(a.dflt, a), (int[])null, clone(a.labels, a));
      a.keys.addAll(a.keys);
      return a.cloneAnnotations(a);
   }
}

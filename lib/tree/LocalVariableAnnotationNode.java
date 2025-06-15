package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.TypePath;

public class LocalVariableAnnotationNode extends TypeAnnotationNode {
   public List<LabelNode> start;
   public List<LabelNode> end;
   public List<Integer> index;

   public LocalVariableAnnotationNode(int a, TypePath a, LabelNode[] a, LabelNode[] a, int[] a, String a) {
      this(327680, a, a, a, a, a, a);
   }

   public LocalVariableAnnotationNode(int a, int a, TypePath a, LabelNode[] a, LabelNode[] a, int[] a, String a) {
      super(a, a, a, a);
      a.start = new ArrayList(a.length);
      a.start.addAll(Arrays.asList(a));
      a.end = new ArrayList(a.length);
      a.end.addAll(Arrays.asList(a));
      a.index = new ArrayList(a.length);
      int[] var8 = a;
      int var9 = a.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         int a = var8[var10];
         a.index.add(a);
      }

   }

   public void accept(MethodVisitor a, boolean a2) {
      Label[] a = new Label[a.start.size()];
      Label[] a = new Label[a.end.size()];
      int[] a = new int[a.index.size()];

      for(int a = 0; a < a.length; ++a) {
         a[a] = ((LabelNode)a.start.get(a)).getLabel();
         a[a] = ((LabelNode)a.end.get(a)).getLabel();
         a[a] = (Integer)a.index.get(a);
      }

      a.accept(a.visitLocalVariableAnnotation(a.typeRef, a.typePath, a, a, a, a.desc, true));
   }
}

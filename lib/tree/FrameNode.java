package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class FrameNode extends AbstractInsnNode {
   public int type;
   public List<Object> local;
   public List<Object> stack;

   private FrameNode() {
      super(-1);
   }

   public FrameNode(int a, int a, Object[] a, int a, Object[] a) {
      super(-1);
      a.type = a;
      switch(a) {
      case -1:
      case 0:
         a.local = asList(a, a);
         a.stack = asList(a, a);
         break;
      case 1:
         a.local = asList(a, a);
         break;
      case 2:
         a.local = Arrays.asList();
      case 3:
      default:
         break;
      case 4:
         a.stack = asList(1, a);
      }

   }

   public int getType() {
      return 14;
   }

   public void accept(MethodVisitor a) {
      switch(a.type) {
      case -1:
      case 0:
         a.visitFrame(a.type, a.local.size(), asArray(a.local), a.stack.size(), asArray(a.stack));
         break;
      case 1:
         a.visitFrame(a.type, a.local.size(), asArray(a.local), 0, (Object[])null);
         break;
      case 2:
         a.visitFrame(a.type, a.local.size(), (Object[])null, 0, (Object[])null);
         break;
      case 3:
         a.visitFrame(a.type, 0, (Object[])null, 0, (Object[])null);
         break;
      case 4:
         a.visitFrame(a.type, 0, (Object[])null, 1, asArray(a.stack));
      }

   }

   public AbstractInsnNode clone(Map<LabelNode, LabelNode> a) {
      FrameNode a = new FrameNode();
      a.type = a.type;
      int a;
      Object a;
      if (a.local != null) {
         a.local = new ArrayList();

         for(a = 0; a < a.local.size(); ++a) {
            a = a.local.get(a);
            if (a instanceof LabelNode) {
               a = a.get(a);
            }

            a.local.add(a);
         }
      }

      if (a.stack != null) {
         a.stack = new ArrayList();

         for(a = 0; a < a.stack.size(); ++a) {
            a = a.stack.get(a);
            if (a instanceof LabelNode) {
               a = a.get(a);
            }

            a.stack.add(a);
         }
      }

      return a;
   }

   private static List<Object> asList(int a, Object[] a) {
      return Arrays.asList(a).subList(0, a);
   }

   private static Object[] asArray(List<Object> a) {
      Object[] a = new Object[a.size()];

      for(int a = 0; a < a.length; ++a) {
         Object a = a.get(a);
         if (a instanceof LabelNode) {
            a = ((LabelNode)a).getLabel();
         }

         a[a] = a;
      }

      return a;
   }
}

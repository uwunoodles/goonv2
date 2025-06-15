package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;

class Subroutine {
   LabelNode start;
   boolean[] access;
   List<JumpInsnNode> callers;

   private Subroutine() {
   }

   Subroutine(LabelNode a, int a, JumpInsnNode a) {
      a.start = a;
      a.access = new boolean[a];
      a.callers = new ArrayList();
      a.callers.add(a);
   }

   public Subroutine copy() {
      Subroutine a = new Subroutine();
      a.start = a.start;
      a.access = new boolean[a.access.length];
      System.arraycopy(a.access, 0, a.access, 0, a.access.length);
      a.callers = new ArrayList(a.callers);
      return a;
   }

   public boolean merge(Subroutine a) throws AnalyzerException {
      boolean a = false;

      int a;
      for(a = 0; a < a.access.length; ++a) {
         if (a.access[a] && !a.access[a]) {
            a.access[a] = true;
            a = true;
         }
      }

      if (a.start == a.start) {
         for(a = 0; a < a.callers.size(); ++a) {
            JumpInsnNode a = (JumpInsnNode)a.callers.get(a);
            if (!a.callers.contains(a)) {
               a.callers.add(a);
               a = true;
            }
         }
      }

      return a;
   }
}

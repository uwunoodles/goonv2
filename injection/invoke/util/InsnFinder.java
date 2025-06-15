package org.spongepowered.asm.mixin.injection.invoke.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicInterpreter;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.mixin.injection.struct.Target;

public class InsnFinder {
   private static final Logger logger = LogManager.getLogger("mixin");

   public AbstractInsnNode findPopInsn(Target a, AbstractInsnNode a) {
      try {
         (new InsnFinder.PopAnalyzer(a)).analyze(a.classNode.name, a.method);
      } catch (AnalyzerException var4) {
         if (var4.getCause() instanceof InsnFinder.AnalysisResultException) {
            return ((InsnFinder.AnalysisResultException)var4.getCause()).getResult();
         }

         logger.catching(var4);
      }

      return null;
   }

   static class PopAnalyzer extends Analyzer<BasicValue> {
      protected final AbstractInsnNode node;

      public PopAnalyzer(AbstractInsnNode a) {
         super(new BasicInterpreter());
         a.node = a;
      }

      protected Frame<BasicValue> newFrame(int a, int a) {
         return new InsnFinder.PopAnalyzer.PopFrame(a, a);
      }

      class PopFrame extends Frame<BasicValue> {
         private AbstractInsnNode current;
         private InsnFinder.AnalyzerState state;
         private int depth;

         public PopFrame(int axxx, int ax) {
            super(axxx, ax);
            a.state = InsnFinder.AnalyzerState.SEARCH;
            a.depth = 0;
         }

         public void execute(AbstractInsnNode ax, Interpreter<BasicValue> axx) throws AnalyzerException {
            a.current = ax;
            super.execute(ax, axx);
         }

         public void push(BasicValue ax) throws IndexOutOfBoundsException {
            if (a.current == PopAnalyzer.this.node && a.state == InsnFinder.AnalyzerState.SEARCH) {
               a.state = InsnFinder.AnalyzerState.ANALYSE;
               ++a.depth;
            } else if (a.state == InsnFinder.AnalyzerState.ANALYSE) {
               ++a.depth;
            }

            super.push(ax);
         }

         public BasicValue pop() throws IndexOutOfBoundsException {
            if (a.state == InsnFinder.AnalyzerState.ANALYSE && --a.depth == 0) {
               a.state = InsnFinder.AnalyzerState.COMPLETE;
               throw new InsnFinder.AnalysisResultException(a.current);
            } else {
               return (BasicValue)super.pop();
            }
         }
      }
   }

   static enum AnalyzerState {
      SEARCH,
      ANALYSE,
      COMPLETE;
   }

   static class AnalysisResultException extends RuntimeException {
      private static final long serialVersionUID = 1L;
      private AbstractInsnNode result;

      public AnalysisResultException(AbstractInsnNode a) {
         a.result = a;
      }

      public AbstractInsnNode getResult() {
         return a.result;
      }
   }
}

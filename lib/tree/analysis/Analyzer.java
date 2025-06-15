package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LookupSwitchInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TableSwitchInsnNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class Analyzer<V extends Value> implements Opcodes {
   private final Interpreter<V> interpreter;
   private int n;
   private InsnList insns;
   private List<TryCatchBlockNode>[] handlers;
   private Frame<V>[] frames;
   private Subroutine[] subroutines;
   private boolean[] queued;
   private int[] queue;
   private int top;

   public Analyzer(Interpreter<V> a) {
      a.interpreter = a;
   }

   public Frame<V>[] analyze(String a, MethodNode a) throws AnalyzerException {
      if ((a.access & 1280) != 0) {
         a.frames = (Frame[])(new Frame[0]);
         return a.frames;
      } else {
         a.n = a.instructions.size();
         a.insns = a.instructions;
         a.handlers = (List[])(new List[a.n]);
         a.frames = (Frame[])(new Frame[a.n]);
         a.subroutines = new Subroutine[a.n];
         a.queued = new boolean[a.n];
         a.queue = new int[a.n];
         a.top = 0;

         int a;
         for(int a = 0; a < a.tryCatchBlocks.size(); ++a) {
            TryCatchBlockNode a = (TryCatchBlockNode)a.tryCatchBlocks.get(a);
            int a = a.insns.indexOf(a.start);
            a = a.insns.indexOf(a.end);

            for(int a = a; a < a; ++a) {
               List<TryCatchBlockNode> a = a.handlers[a];
               if (a == null) {
                  a = new ArrayList();
                  a.handlers[a] = (List)a;
               }

               ((List)a).add(a);
            }
         }

         Subroutine a = new Subroutine((LabelNode)null, a.maxLocals, (JumpInsnNode)null);
         List<AbstractInsnNode> a = new ArrayList();
         Map<LabelNode, Subroutine> a = new HashMap();
         a.findSubroutine(0, a, a);

         while(!a.isEmpty()) {
            JumpInsnNode a = (JumpInsnNode)a.remove(0);
            Subroutine a = (Subroutine)a.get(a.label);
            if (a == null) {
               a = new Subroutine(a.label, a.maxLocals, a);
               a.put(a.label, a);
               a.findSubroutine(a.insns.indexOf(a.label), a, a);
            } else {
               a.callers.add(a);
            }
         }

         for(a = 0; a < a.n; ++a) {
            if (a.subroutines[a] != null && a.subroutines[a].start == null) {
               a.subroutines[a] = null;
            }
         }

         Frame<V> a = a.newFrame(a.maxLocals, a.maxStack);
         Frame<V> a = a.newFrame(a.maxLocals, a.maxStack);
         a.setReturn(a.interpreter.newValue(Type.getReturnType(a.desc)));
         Type[] a = Type.getArgumentTypes(a.desc);
         int a = 0;
         if ((a.access & 8) == 0) {
            Type a = Type.getObjectType(a);
            a.setLocal(a++, a.interpreter.newValue(a));
         }

         int a;
         for(a = 0; a < a.length; ++a) {
            a.setLocal(a++, a.interpreter.newValue(a[a]));
            if (a[a].getSize() == 2) {
               a.setLocal(a++, a.interpreter.newValue((Type)null));
            }
         }

         while(a < a.maxLocals) {
            a.setLocal(a++, a.interpreter.newValue((Type)null));
         }

         a.merge(0, a, (Subroutine)null);
         a.init(a, a);

         while(a.top > 0) {
            a = a.queue[--a.top];
            Frame<V> a = a.frames[a];
            Subroutine a = a.subroutines[a];
            a.queued[a] = false;
            AbstractInsnNode a = null;

            try {
               a = a.instructions.get(a);
               int a = a.getOpcode();
               int a = a.getType();
               int a;
               if (a != 8 && a != 15 && a != 14) {
                  a.init(a).execute(a, a.interpreter);
                  a = a == null ? null : a.copy();
                  if (a instanceof JumpInsnNode) {
                     JumpInsnNode a = (JumpInsnNode)a;
                     if (a != 167 && a != 168) {
                        a.merge(a + 1, a, a);
                        a.newControlFlowEdge(a, a + 1);
                     }

                     a = a.insns.indexOf(a.label);
                     if (a == 168) {
                        a.merge(a, a, new Subroutine(a.label, a.maxLocals, a));
                     } else {
                        a.merge(a, a, a);
                     }

                     a.newControlFlowEdge(a, a);
                  } else {
                     int a;
                     LabelNode a;
                     if (a instanceof LookupSwitchInsnNode) {
                        LookupSwitchInsnNode a = (LookupSwitchInsnNode)a;
                        a = a.insns.indexOf(a.dflt);
                        a.merge(a, a, a);
                        a.newControlFlowEdge(a, a);

                        for(a = 0; a < a.labels.size(); ++a) {
                           a = (LabelNode)a.labels.get(a);
                           a = a.insns.indexOf(a);
                           a.merge(a, a, a);
                           a.newControlFlowEdge(a, a);
                        }
                     } else if (a instanceof TableSwitchInsnNode) {
                        TableSwitchInsnNode a = (TableSwitchInsnNode)a;
                        a = a.insns.indexOf(a.dflt);
                        a.merge(a, a, a);
                        a.newControlFlowEdge(a, a);

                        for(a = 0; a < a.labels.size(); ++a) {
                           a = (LabelNode)a.labels.get(a);
                           a = a.insns.indexOf(a);
                           a.merge(a, a, a);
                           a.newControlFlowEdge(a, a);
                        }
                     } else {
                        int a;
                        if (a == 169) {
                           if (a == null) {
                              throw new AnalyzerException(a, "RET instruction outside of a sub routine");
                           }

                           for(a = 0; a < a.callers.size(); ++a) {
                              JumpInsnNode a = (JumpInsnNode)a.callers.get(a);
                              a = a.insns.indexOf(a);
                              if (a.frames[a] != null) {
                                 a.merge(a + 1, a.frames[a], a, a.subroutines[a], a.access);
                                 a.newControlFlowEdge(a, a + 1);
                              }
                           }
                        } else if (a != 191 && (a < 172 || a > 177)) {
                           if (a != null) {
                              if (a instanceof VarInsnNode) {
                                 a = ((VarInsnNode)a).var;
                                 a.access[a] = true;
                                 if (a == 22 || a == 24 || a == 55 || a == 57) {
                                    a.access[a + 1] = true;
                                 }
                              } else if (a instanceof IincInsnNode) {
                                 a = ((IincInsnNode)a).var;
                                 a.access[a] = true;
                              }
                           }

                           a.merge(a + 1, a, a);
                           a.newControlFlowEdge(a, a + 1);
                        }
                     }
                  }
               } else {
                  a.merge(a + 1, a, a);
                  a.newControlFlowEdge(a, a + 1);
               }

               List<TryCatchBlockNode> a = a.handlers[a];
               if (a != null) {
                  for(a = 0; a < a.size(); ++a) {
                     TryCatchBlockNode a = (TryCatchBlockNode)a.get(a);
                     Type a;
                     if (a.type == null) {
                        a = Type.getObjectType("java/lang/Throwable");
                     } else {
                        a = Type.getObjectType(a.type);
                     }

                     int a = a.insns.indexOf(a.handler);
                     if (a.newControlFlowExceptionEdge(a, a)) {
                        a.init(a);
                        a.clearStack();
                        a.push(a.interpreter.newValue(a));
                        a.merge(a, a, a);
                     }
                  }
               }
            } catch (AnalyzerException var21) {
               throw new AnalyzerException(var21.node, "Error at instruction " + a + ": " + var21.getMessage(), var21);
            } catch (Exception var22) {
               throw new AnalyzerException(a, "Error at instruction " + a + ": " + var22.getMessage(), var22);
            }
         }

         return a.frames;
      }
   }

   private void findSubroutine(int a, Subroutine a, List<AbstractInsnNode> a) throws AnalyzerException {
      while(a >= 0 && a < a.n) {
         if (a.subroutines[a] != null) {
            return;
         }

         a.subroutines[a] = a.copy();
         AbstractInsnNode a = a.insns.get(a);
         int a;
         if (a instanceof JumpInsnNode) {
            if (a.getOpcode() == 168) {
               a.add(a);
            } else {
               JumpInsnNode a = (JumpInsnNode)a;
               a.findSubroutine(a.insns.indexOf(a.label), a, a);
            }
         } else {
            LabelNode a;
            if (a instanceof TableSwitchInsnNode) {
               TableSwitchInsnNode a = (TableSwitchInsnNode)a;
               a.findSubroutine(a.insns.indexOf(a.dflt), a, a);

               for(a = a.labels.size() - 1; a >= 0; --a) {
                  a = (LabelNode)a.labels.get(a);
                  a.findSubroutine(a.insns.indexOf(a), a, a);
               }
            } else if (a instanceof LookupSwitchInsnNode) {
               LookupSwitchInsnNode a = (LookupSwitchInsnNode)a;
               a.findSubroutine(a.insns.indexOf(a.dflt), a, a);

               for(a = a.labels.size() - 1; a >= 0; --a) {
                  a = (LabelNode)a.labels.get(a);
                  a.findSubroutine(a.insns.indexOf(a), a, a);
               }
            }
         }

         List<TryCatchBlockNode> a = a.handlers[a];
         if (a != null) {
            for(a = 0; a < a.size(); ++a) {
               TryCatchBlockNode a = (TryCatchBlockNode)a.get(a);
               a.findSubroutine(a.insns.indexOf(a.handler), a, a);
            }
         }

         switch(a.getOpcode()) {
         case 167:
         case 169:
         case 170:
         case 171:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 191:
            return;
         case 168:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
         case 186:
         case 187:
         case 188:
         case 189:
         case 190:
         default:
            ++a;
         }
      }

      throw new AnalyzerException((AbstractInsnNode)null, "Execution can fall off end of the code");
   }

   public Frame<V>[] getFrames() {
      return a.frames;
   }

   public List<TryCatchBlockNode> getHandlers(int a) {
      return a.handlers[a];
   }

   protected void init(String a1, MethodNode a2) throws AnalyzerException {
   }

   protected Frame<V> newFrame(int a, int a) {
      return new Frame(a, a);
   }

   protected Frame<V> newFrame(Frame<? extends V> a) {
      return new Frame(a);
   }

   protected void newControlFlowEdge(int a1, int a2) {
   }

   protected boolean newControlFlowExceptionEdge(int a1, int a2) {
      return true;
   }

   protected boolean newControlFlowExceptionEdge(int a, TryCatchBlockNode a) {
      return a.newControlFlowExceptionEdge(a, a.insns.indexOf(a.handler));
   }

   private void merge(int a, Frame<V> a, Subroutine a) throws AnalyzerException {
      Frame<V> a = a.frames[a];
      Subroutine a = a.subroutines[a];
      boolean a;
      if (a == null) {
         a.frames[a] = a.newFrame(a);
         a = true;
      } else {
         a = a.merge(a, a.interpreter);
      }

      if (a == null) {
         if (a != null) {
            a.subroutines[a] = a.copy();
            a = true;
         }
      } else if (a != null) {
         a |= a.merge(a);
      }

      if (a && !a.queued[a]) {
         a.queued[a] = true;
         a.queue[a.top++] = a;
      }

   }

   private void merge(int a, Frame<V> a, Frame<V> a, Subroutine a, boolean[] a) throws AnalyzerException {
      Frame<V> a = a.frames[a];
      Subroutine a = a.subroutines[a];
      a.merge(a, a);
      boolean a;
      if (a == null) {
         a.frames[a] = a.newFrame(a);
         a = true;
      } else {
         a = a.merge(a, a.interpreter);
      }

      if (a != null && a != null) {
         a |= a.merge(a);
      }

      if (a && !a.queued[a]) {
         a.queued[a] = true;
         a.queue[a.top++] = a;
      }

   }
}

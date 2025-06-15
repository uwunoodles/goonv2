package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.asm.MixinVerifier;
import org.spongepowered.asm.util.throwables.LVTGeneratorException;

public final class Locals {
   private static final Map<String, List<LocalVariableNode>> calculatedLocalVariables = new HashMap();

   private Locals() {
   }

   public static void loadLocals(Type[] a, InsnList a, int a, int a) {
      for(; a < a.length && a > 0; ++a) {
         if (a[a] != null) {
            a.add((AbstractInsnNode)(new VarInsnNode(a[a].getOpcode(21), a)));
            --a;
         }
      }

   }

   public static LocalVariableNode[] getLocalsAt(ClassNode a, MethodNode a, AbstractInsnNode a) {
      for(int a = 0; a < 3 && (a instanceof LabelNode || a instanceof LineNumberNode); ++a) {
         a = nextNode(a.instructions, a);
      }

      ClassInfo a = ClassInfo.forName(a.name);
      if (a == null) {
         throw new LVTGeneratorException("Could not load class metadata for " + a.name + " generating LVT for " + a.name);
      } else {
         ClassInfo.Method a = a.findMethod(a);
         if (a == null) {
            throw new LVTGeneratorException("Could not locate method metadata for " + a.name + " generating LVT in " + a.name);
         } else {
            List<ClassInfo.FrameData> a = a.getFrames();
            LocalVariableNode[] a = new LocalVariableNode[a.maxLocals];
            int a = 0;
            int a = 0;
            if ((a.access & 8) == 0) {
               a[a++] = new LocalVariableNode("this", a.name, (String)null, (LabelNode)null, (LabelNode)null, 0);
            }

            Type[] var9 = Type.getArgumentTypes(a.desc);
            int a = var9.length;

            int a;
            for(a = 0; a < a; ++a) {
               Type a = var9[a];
               a[a] = new LocalVariableNode("arg" + a++, a.toString(), (String)null, (LabelNode)null, (LabelNode)null, a);
               a += a.getSize();
            }

            int a = a;
            a = -1;
            a = 0;
            ListIterator a = a.instructions.iterator();

            while(a.hasNext()) {
               AbstractInsnNode a = (AbstractInsnNode)a.next();
               if (a instanceof FrameNode) {
                  ++a;
                  FrameNode a = (FrameNode)a;
                  ClassInfo.FrameData a = a < a.size() ? (ClassInfo.FrameData)a.get(a) : null;
                  a = a != null && a.type == 0 ? Math.min(a, a.locals) : a.local.size();
                  int a = 0;

                  for(int a = 0; a < a.length; ++a) {
                     Object a = a < a.local.size() ? a.local.get(a) : null;
                     if (a instanceof String) {
                        a[a] = getLocalVariableAt(a, a, a, a);
                     } else if (!(a instanceof Integer)) {
                        if (a != null) {
                           throw new LVTGeneratorException("Invalid value " + a + " in locals array at position " + a + " in " + a.name + "." + a.name + a.desc);
                        }

                        if (a >= a && a >= a && a > 0) {
                           a[a] = null;
                        }
                     } else {
                        boolean a = a == Opcodes.UNINITIALIZED_THIS || a == Opcodes.NULL;
                        boolean a = a == Opcodes.INTEGER || a == Opcodes.FLOAT;
                        boolean a = a == Opcodes.DOUBLE || a == Opcodes.LONG;
                        if (a != Opcodes.TOP) {
                           if (a) {
                              a[a] = null;
                           } else {
                              if (!a && !a) {
                                 throw new LVTGeneratorException("Unrecognised locals opcode " + a + " in locals array at position " + a + " in " + a.name + "." + a.name + a.desc);
                              }

                              a[a] = getLocalVariableAt(a, a, a, a);
                              if (a) {
                                 ++a;
                                 a[a] = null;
                              }
                           }
                        }
                     }

                     ++a;
                  }
               } else if (a instanceof VarInsnNode) {
                  VarInsnNode a = (VarInsnNode)a;
                  a[a.var] = getLocalVariableAt(a, a, a, a.var);
               }

               if (a == a) {
                  break;
               }
            }

            for(int a = 0; a < a.length; ++a) {
               if (a[a] != null && a[a].desc == null) {
                  a[a] = null;
               }
            }

            return a;
         }
      }
   }

   public static LocalVariableNode getLocalVariableAt(ClassNode a, MethodNode a, AbstractInsnNode a, int a) {
      return getLocalVariableAt(a, a, a.instructions.indexOf(a), a);
   }

   private static LocalVariableNode getLocalVariableAt(ClassNode a, MethodNode a, int a, int a) {
      LocalVariableNode a = null;
      LocalVariableNode a = null;
      Iterator var6 = getLocalVariableTable(a, a).iterator();

      LocalVariableNode a;
      while(var6.hasNext()) {
         a = (LocalVariableNode)var6.next();
         if (a.index == a) {
            if (isOpcodeInRange(a.instructions, a, a)) {
               a = a;
            } else if (a == null) {
               a = a;
            }
         }
      }

      if (a == null && !a.localVariables.isEmpty()) {
         var6 = getGeneratedLocalVariableTable(a, a).iterator();

         while(var6.hasNext()) {
            a = (LocalVariableNode)var6.next();
            if (a.index == a && isOpcodeInRange(a.instructions, a, a)) {
               a = a;
            }
         }
      }

      return a != null ? a : a;
   }

   private static boolean isOpcodeInRange(InsnList a, LocalVariableNode a, int a) {
      return a.indexOf(a.start) < a && a.indexOf(a.end) > a;
   }

   public static List<LocalVariableNode> getLocalVariableTable(ClassNode a, MethodNode a) {
      return a.localVariables.isEmpty() ? getGeneratedLocalVariableTable(a, a) : a.localVariables;
   }

   public static List<LocalVariableNode> getGeneratedLocalVariableTable(ClassNode a, MethodNode a) {
      String a = String.format("%s.%s%s", a.name, a.name, a.desc);
      List<LocalVariableNode> a = (List)calculatedLocalVariables.get(a);
      if (a != null) {
         return a;
      } else {
         a = generateLocalVariableTable(a, a);
         calculatedLocalVariables.put(a, a);
         return a;
      }
   }

   public static List<LocalVariableNode> generateLocalVariableTable(ClassNode a, MethodNode a) {
      List<Type> a = null;
      if (a.interfaces != null) {
         a = new ArrayList();
         Iterator var3 = a.interfaces.iterator();

         while(var3.hasNext()) {
            String a = (String)var3.next();
            a.add(Type.getObjectType(a));
         }
      }

      Type a = null;
      if (a.superName != null) {
         a = Type.getObjectType(a.superName);
      }

      Analyzer a = new Analyzer(new MixinVerifier(Type.getObjectType(a.name), a, a, false));

      try {
         a.analyze(a.name, a);
      } catch (AnalyzerException var18) {
         var18.printStackTrace();
      }

      Frame<BasicValue>[] a = a.getFrames();
      int a = a.instructions.size();
      List<LocalVariableNode> a = new ArrayList();
      LocalVariableNode[] a = new LocalVariableNode[a.maxLocals];
      BasicValue[] a = new BasicValue[a.maxLocals];
      LabelNode[] a = new LabelNode[a];
      String[] a = new String[a.maxLocals];

      for(int a = 0; a < a; ++a) {
         Frame<BasicValue> a = a[a];
         if (a != null) {
            LabelNode a = null;

            for(int a = 0; a < a.getLocals(); ++a) {
               BasicValue a = (BasicValue)a.getLocal(a);
               if ((a != null || a[a] != null) && (a == null || !a.equals(a[a]))) {
                  if (a == null) {
                     AbstractInsnNode a = a.instructions.get(a);
                     if (a instanceof LabelNode) {
                        a = (LabelNode)a;
                     } else {
                        a[a] = a = new LabelNode();
                     }
                  }

                  if (a == null && a[a] != null) {
                     a.add(a[a]);
                     a[a].end = a;
                     a[a] = null;
                  } else if (a != null) {
                     if (a[a] != null) {
                        a.add(a[a]);
                        a[a].end = a;
                        a[a] = null;
                     }

                     String a = a.getType() != null ? a.getType().getDescriptor() : a[a];
                     a[a] = new LocalVariableNode("var" + a, a, (String)null, a, (LabelNode)null, a);
                     if (a != null) {
                        a[a] = a;
                     }
                  }

                  a[a] = a;
               }
            }
         }
      }

      LabelNode a = null;

      int a;
      for(a = 0; a < a.length; ++a) {
         if (a[a] != null) {
            if (a == null) {
               a = new LabelNode();
               a.instructions.add((AbstractInsnNode)a);
            }

            a[a].end = a;
            a.add(a[a]);
         }
      }

      for(a = a - 1; a >= 0; --a) {
         if (a[a] != null) {
            a.instructions.insert(a.instructions.get(a), (AbstractInsnNode)a[a]);
         }
      }

      return a;
   }

   private static AbstractInsnNode nextNode(InsnList a, AbstractInsnNode a) {
      int a = a.indexOf(a) + 1;
      return a > 0 && a < a.size() ? a.get(a) : a;
   }
}

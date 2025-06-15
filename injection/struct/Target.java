package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.Bytecode;

public class Target implements Comparable<Target>, Iterable<AbstractInsnNode> {
   public final ClassNode classNode;
   public final MethodNode method;
   public final InsnList insns;
   public final boolean isStatic;
   public final boolean isCtor;
   public final Type[] arguments;
   public final Type returnType;
   private final int maxStack;
   private final int maxLocals;
   private final InjectionNodes injectionNodes = new InjectionNodes();
   private String callbackInfoClass;
   private String callbackDescriptor;
   private int[] argIndices;
   private List<Integer> argMapVars;
   private LabelNode start;
   private LabelNode end;

   public Target(ClassNode a, MethodNode a) {
      a.classNode = a;
      a.method = a;
      a.insns = a.instructions;
      a.isStatic = Bytecode.methodIsStatic(a);
      a.isCtor = a.name.equals("<init>");
      a.arguments = Type.getArgumentTypes(a.desc);
      a.returnType = Type.getReturnType(a.desc);
      a.maxStack = a.maxStack;
      a.maxLocals = a.maxLocals;
   }

   public InjectionNodes.InjectionNode addInjectionNode(AbstractInsnNode a) {
      return a.injectionNodes.add(a);
   }

   public InjectionNodes.InjectionNode getInjectionNode(AbstractInsnNode a) {
      return a.injectionNodes.get(a);
   }

   public int getMaxLocals() {
      return a.maxLocals;
   }

   public int getMaxStack() {
      return a.maxStack;
   }

   public int getCurrentMaxLocals() {
      return a.method.maxLocals;
   }

   public int getCurrentMaxStack() {
      return a.method.maxStack;
   }

   public int allocateLocal() {
      return a.allocateLocals(1);
   }

   public int allocateLocals(int a) {
      int a = a.method.maxLocals;
      MethodNode var10000 = a.method;
      var10000.maxLocals += a;
      return a;
   }

   public void addToLocals(int a) {
      a.setMaxLocals(a.maxLocals + a);
   }

   public void setMaxLocals(int a) {
      if (a > a.method.maxLocals) {
         a.method.maxLocals = a;
      }

   }

   public void addToStack(int a) {
      a.setMaxStack(a.maxStack + a);
   }

   public void setMaxStack(int a) {
      if (a > a.method.maxStack) {
         a.method.maxStack = a;
      }

   }

   public int[] generateArgMap(Type[] a, int a) {
      if (a.argMapVars == null) {
         a.argMapVars = new ArrayList();
      }

      int[] a = new int[a.length];
      int a = a;

      for(int a = 0; a < a.length; ++a) {
         int a = a[a].getSize();
         a[a] = a.allocateArgMapLocal(a, a);
         a += a;
      }

      return a;
   }

   private int allocateArgMapLocal(int a, int a) {
      int a;
      int a;
      if (a < a.argMapVars.size()) {
         a = (Integer)a.argMapVars.get(a);
         if (a > 1 && a + a > a.argMapVars.size()) {
            a = a.allocateLocals(1);
            if (a == a + 1) {
               a.argMapVars.add(a);
               return a;
            } else {
               a.argMapVars.set(a, a);
               a.argMapVars.add(a.allocateLocals(1));
               return a;
            }
         } else {
            return a;
         }
      } else {
         a = a.allocateLocals(a);

         for(a = 0; a < a; ++a) {
            a.argMapVars.add(a + a);
         }

         return a;
      }
   }

   public int[] getArgIndices() {
      if (a.argIndices == null) {
         a.argIndices = a.calcArgIndices(a.isStatic ? 0 : 1);
      }

      return a.argIndices;
   }

   private int[] calcArgIndices(int a) {
      int[] a = new int[a.arguments.length];

      for(int a = 0; a < a.arguments.length; ++a) {
         a[a] = a;
         a += a.arguments[a].getSize();
      }

      return a;
   }

   public String getCallbackInfoClass() {
      if (a.callbackInfoClass == null) {
         a.callbackInfoClass = CallbackInfo.getCallInfoClassName(a.returnType);
      }

      return a.callbackInfoClass;
   }

   public String getSimpleCallbackDescriptor() {
      return String.format("(L%s;)V", a.getCallbackInfoClass());
   }

   public String getCallbackDescriptor(Type[] a, Type[] a) {
      return a.getCallbackDescriptor(false, a, a, 0, 32767);
   }

   public String getCallbackDescriptor(boolean a, Type[] a, Type[] a3, int a, int a) {
      if (a.callbackDescriptor == null) {
         a.callbackDescriptor = String.format("(%sL%s;)V", a.method.desc.substring(1, a.method.desc.indexOf(41)), a.getCallbackInfoClass());
      }

      if (a && a != null) {
         StringBuilder a = new StringBuilder(a.callbackDescriptor.substring(0, a.callbackDescriptor.indexOf(41)));

         for(int a = a; a < a.length && a > 0; ++a) {
            if (a[a] != null) {
               a.append(a[a].getDescriptor());
               --a;
            }
         }

         return a.append(")V").toString();
      } else {
         return a.callbackDescriptor;
      }
   }

   public String toString() {
      return String.format("%s::%s%s", a.classNode.name, a.method.name, a.method.desc);
   }

   public int compareTo(Target a) {
      return a == null ? Integer.MAX_VALUE : a.toString().compareTo(a.toString());
   }

   public int indexOf(InjectionNodes.InjectionNode a) {
      return a.insns.indexOf(a.getCurrentTarget());
   }

   public int indexOf(AbstractInsnNode a) {
      return a.insns.indexOf(a);
   }

   public AbstractInsnNode get(int a) {
      return a.insns.get(a);
   }

   public Iterator<AbstractInsnNode> iterator() {
      return a.insns.iterator();
   }

   public MethodInsnNode findInitNodeFor(TypeInsnNode a) {
      int a = a.indexOf((AbstractInsnNode)a);
      ListIterator a = a.insns.iterator(a);

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (a instanceof MethodInsnNode && a.getOpcode() == 183) {
            MethodInsnNode a = (MethodInsnNode)a;
            if ("<init>".equals(a.name) && a.owner.equals(a.desc)) {
               return a;
            }
         }
      }

      return null;
   }

   public MethodInsnNode findSuperInitNode() {
      return !a.isCtor ? null : Bytecode.findSuperInit(a.method, ClassInfo.forName(a.classNode.name).getSuperName());
   }

   public void insertBefore(InjectionNodes.InjectionNode a, InsnList a) {
      a.insns.insertBefore(a.getCurrentTarget(), a);
   }

   public void insertBefore(AbstractInsnNode a, InsnList a) {
      a.insns.insertBefore(a, a);
   }

   public void replaceNode(AbstractInsnNode a, AbstractInsnNode a) {
      a.insns.insertBefore(a, a);
      a.insns.remove(a);
      a.injectionNodes.replace(a, a);
   }

   public void replaceNode(AbstractInsnNode a, AbstractInsnNode a, InsnList a) {
      a.insns.insertBefore(a, a);
      a.insns.remove(a);
      a.injectionNodes.replace(a, a);
   }

   public void wrapNode(AbstractInsnNode a, AbstractInsnNode a, InsnList a, InsnList a) {
      a.insns.insertBefore(a, a);
      a.insns.insert(a, a);
      a.injectionNodes.replace(a, a);
   }

   public void replaceNode(AbstractInsnNode a, InsnList a) {
      a.insns.insertBefore(a, a);
      a.removeNode(a);
   }

   public void removeNode(AbstractInsnNode a) {
      a.insns.remove(a);
      a.injectionNodes.remove(a);
   }

   public void addLocalVariable(int a, String a, String a) {
      if (a.start == null) {
         a.start = new LabelNode(new Label());
         a.end = new LabelNode(new Label());
         a.insns.insert((AbstractInsnNode)a.start);
         a.insns.add((AbstractInsnNode)a.end);
      }

      a.addLocalVariable(a, a, a, a.start, a.end);
   }

   private void addLocalVariable(int a, String a, String a, LabelNode a, LabelNode a) {
      if (a.method.localVariables == null) {
         a.method.localVariables = new ArrayList();
      }

      a.method.localVariables.add(new LocalVariableNode(a, a, (String)null, a, a, a));
   }
}

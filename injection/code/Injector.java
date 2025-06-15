package org.spongepowered.asm.mixin.injection.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.Bytecode;

public abstract class Injector {
   protected static final Logger logger = LogManager.getLogger("mixin");
   protected InjectionInfo info;
   protected final ClassNode classNode;
   protected final MethodNode methodNode;
   protected final Type[] methodArgs;
   protected final Type returnType;
   protected final boolean isStatic;

   public Injector(InjectionInfo a) {
      this(a.getClassNode(), a.getMethod());
      a.info = a;
   }

   private Injector(ClassNode a, MethodNode a) {
      a.classNode = a;
      a.methodNode = a;
      a.methodArgs = Type.getArgumentTypes(a.methodNode.desc);
      a.returnType = Type.getReturnType(a.methodNode.desc);
      a.isStatic = Bytecode.methodIsStatic(a.methodNode);
   }

   public String toString() {
      return String.format("%s::%s", a.classNode.name, a.methodNode.name);
   }

   public final List<InjectionNodes.InjectionNode> find(InjectorTarget a, List<InjectionPoint> a) {
      a.sanityCheck(a.getTarget(), a);
      List<InjectionNodes.InjectionNode> a = new ArrayList();
      Iterator var4 = a.findTargetNodes(a, a).iterator();

      while(var4.hasNext()) {
         Injector.TargetNode a = (Injector.TargetNode)var4.next();
         a.addTargetNode(a.getTarget(), a, a.insn, a.nominators);
      }

      return a;
   }

   protected void addTargetNode(Target a, List<InjectionNodes.InjectionNode> a, AbstractInsnNode a, Set<InjectionPoint> a4) {
      a.add(a.addInjectionNode(a));
   }

   public final void inject(Target a, List<InjectionNodes.InjectionNode> a) {
      Iterator var3 = a.iterator();

      InjectionNodes.InjectionNode a;
      while(var3.hasNext()) {
         a = (InjectionNodes.InjectionNode)var3.next();
         if (a.isRemoved()) {
            if (a.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
               logger.warn("Target node for {} was removed by a previous injector in {}", new Object[]{a.info, a});
            }
         } else {
            a.inject(a, a);
         }
      }

      var3 = a.iterator();

      while(var3.hasNext()) {
         a = (InjectionNodes.InjectionNode)var3.next();
         a.postInject(a, a);
      }

   }

   private Collection<Injector.TargetNode> findTargetNodes(InjectorTarget a, List<InjectionPoint> a) {
      IMixinContext a = a.info.getContext();
      MethodNode a = a.getMethod();
      Map<Integer, Injector.TargetNode> a = new TreeMap();
      Collection<AbstractInsnNode> a = new ArrayList(32);
      Iterator var7 = a.iterator();

      while(true) {
         InjectionPoint a;
         do {
            if (!var7.hasNext()) {
               return a.values();
            }

            a = (InjectionPoint)var7.next();
            a.clear();
            if (a.isMerged() && !a.getClassName().equals(a.getMergedBy()) && !a.checkPriority(a.getMergedPriority(), a.getPriority())) {
               throw new InvalidInjectionException(a.info, String.format("%s on %s with priority %d cannot inject into %s merged by %s with priority %d", a, a, a.getPriority(), a, a.getMergedBy(), a.getMergedPriority()));
            }
         } while(!a.findTargetNodes(a, a, a.getSlice(a), a));

         Injector.TargetNode a;
         for(Iterator var9 = a.iterator(); var9.hasNext(); a.nominators.add(a)) {
            AbstractInsnNode a = (AbstractInsnNode)var9.next();
            Integer a = a.instructions.indexOf(a);
            a = (Injector.TargetNode)a.get(a);
            if (a == null) {
               a = new Injector.TargetNode(a);
               a.put(a, a);
            }
         }
      }
   }

   protected boolean findTargetNodes(MethodNode a, InjectionPoint a, InsnList a, Collection<AbstractInsnNode> a) {
      return a.find(a.desc, a, a);
   }

   protected void sanityCheck(Target a, List<InjectionPoint> a2) {
      if (a.classNode != a.classNode) {
         throw new InvalidInjectionException(a.info, "Target class does not match injector class in " + a);
      }
   }

   protected abstract void inject(Target var1, InjectionNodes.InjectionNode var2);

   protected void postInject(Target a1, InjectionNodes.InjectionNode a2) {
   }

   protected AbstractInsnNode invokeHandler(InsnList a) {
      return a.invokeHandler(a, a.methodNode);
   }

   protected AbstractInsnNode invokeHandler(InsnList a, MethodNode a) {
      boolean a = (a.access & 2) != 0;
      int a = a.isStatic ? 184 : (a ? 183 : 182);
      MethodInsnNode a = new MethodInsnNode(a, a.classNode.name, a.name, a.desc, false);
      a.add((AbstractInsnNode)a);
      a.info.addCallbackInvocation(a);
      return a;
   }

   protected void throwException(InsnList a, String a, String a) {
      a.add((AbstractInsnNode)(new TypeInsnNode(187, a)));
      a.add((AbstractInsnNode)(new InsnNode(89)));
      a.add((AbstractInsnNode)(new LdcInsnNode(a)));
      a.add((AbstractInsnNode)(new MethodInsnNode(183, a, "<init>", "(Ljava/lang/String;)V", false)));
      a.add((AbstractInsnNode)(new InsnNode(191)));
   }

   public static boolean canCoerce(Type a, Type a) {
      return a.getSort() == 10 && a.getSort() == 10 ? canCoerce(ClassInfo.forType(a), ClassInfo.forType(a)) : canCoerce(a.getDescriptor(), a.getDescriptor());
   }

   public static boolean canCoerce(String a, String a) {
      return a.length() <= 1 && a.length() <= 1 ? canCoerce(a.charAt(0), a.charAt(0)) : false;
   }

   public static boolean canCoerce(char a, char a) {
      return a == 'I' && "IBSCZ".indexOf(a) > -1;
   }

   private static boolean canCoerce(ClassInfo a, ClassInfo a) {
      return a != null && a != null && (a == a || a.hasSuperClass(a));
   }

   public static final class TargetNode {
      final AbstractInsnNode insn;
      final Set<InjectionPoint> nominators = new HashSet();

      TargetNode(AbstractInsnNode a) {
         a.insn = a;
      }

      public AbstractInsnNode getNode() {
         return a.insn;
      }

      public Set<InjectionPoint> getNominators() {
         return Collections.unmodifiableSet(a.nominators);
      }

      public boolean equals(Object a) {
         if (a != null && a.getClass() == Injector.TargetNode.class) {
            return ((Injector.TargetNode)a).insn == a.insn;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return a.insn.hashCode();
      }
   }
}

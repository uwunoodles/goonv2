package org.spongepowered.asm.mixin.injection.invoke;

import com.google.common.base.Joiner;
import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Ints;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public class RedirectInjector extends InvokeInjector {
   private static final String KEY_NOMINATORS = "nominators";
   private static final String KEY_FUZZ = "fuzz";
   private static final String KEY_OPCODE = "opcode";
   protected RedirectInjector.Meta meta;
   private Map<BeforeNew, RedirectInjector.ConstructorRedirectData> ctorRedirectors;

   public RedirectInjector(InjectionInfo a) {
      this(a, "@Redirect");
   }

   protected RedirectInjector(InjectionInfo a, String a) {
      super(a, a);
      a.ctorRedirectors = new HashMap();
      int a = a.getContext().getPriority();
      boolean a = Annotations.getVisible(a.methodNode, Final.class) != null;
      a.meta = new RedirectInjector.Meta(a, a, a.info.toString(), a.methodNode.desc);
   }

   protected void checkTarget(Target a1) {
   }

   protected void addTargetNode(Target a, List<InjectionNodes.InjectionNode> a, AbstractInsnNode a, Set<InjectionPoint> a) {
      InjectionNodes.InjectionNode a = a.getInjectionNode(a);
      RedirectInjector.ConstructorRedirectData a = null;
      int a = 8;
      int a = 0;
      if (a != null) {
         RedirectInjector.Meta a = (RedirectInjector.Meta)a.getDecoration("redirector");
         if (a != null && a.getOwner() != a) {
            if (a.priority >= a.meta.priority) {
               Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[]{a.annotationType, a.info, a.meta.priority, a.name, a.priority});
               return;
            }

            if (a.isFinal) {
               throw new InvalidInjectionException(a.info, String.format("%s conflict: %s failed because target was already remapped by %s", a.annotationType, a, a.name));
            }
         }
      }

      Iterator var12 = a.iterator();

      while(var12.hasNext()) {
         InjectionPoint a = (InjectionPoint)var12.next();
         if (a instanceof BeforeNew) {
            a = a.getCtorRedirect((BeforeNew)a);
            a.wildcard = !((BeforeNew)a).hasDescriptor();
         } else if (a instanceof BeforeFieldAccess) {
            BeforeFieldAccess a = (BeforeFieldAccess)a;
            a = a.getFuzzFactor();
            a = a.getArrayOpcode();
         }
      }

      InjectionNodes.InjectionNode a = a.addInjectionNode(a);
      a.decorate("redirector", a.meta);
      a.decorate("nominators", a);
      if (a instanceof TypeInsnNode && a.getOpcode() == 187) {
         a.decorate("ctor", a);
      } else {
         a.decorate("fuzz", a);
         a.decorate("opcode", a);
      }

      a.add(a);
   }

   private RedirectInjector.ConstructorRedirectData getCtorRedirect(BeforeNew a) {
      RedirectInjector.ConstructorRedirectData a = (RedirectInjector.ConstructorRedirectData)a.ctorRedirectors.get(a);
      if (a == null) {
         a = new RedirectInjector.ConstructorRedirectData();
         a.ctorRedirectors.put(a, a);
      }

      return a;
   }

   protected void inject(Target a, InjectionNodes.InjectionNode a) {
      if (a.preInject(a)) {
         if (a.isReplaced()) {
            throw new UnsupportedOperationException("Redirector target failure for " + a.info);
         } else if (a.getCurrentTarget() instanceof MethodInsnNode) {
            a.checkTargetForNode(a, a);
            a.injectAtInvoke(a, a);
         } else if (a.getCurrentTarget() instanceof FieldInsnNode) {
            a.checkTargetForNode(a, a);
            a.injectAtFieldAccess(a, a);
         } else if (a.getCurrentTarget() instanceof TypeInsnNode && a.getCurrentTarget().getOpcode() == 187) {
            if (!a.isStatic && a.isStatic) {
               throw new InvalidInjectionException(a.info, String.format("non-static callback method %s has a static target which is not supported", a));
            } else {
               a.injectAtConstructor(a, a);
            }
         } else {
            throw new InvalidInjectionException(a.info, String.format("%s annotation on is targetting an invalid insn in %s in %s", a.annotationType, a, a));
         }
      }
   }

   protected boolean preInject(InjectionNodes.InjectionNode a) {
      RedirectInjector.Meta a = (RedirectInjector.Meta)a.getDecoration("redirector");
      if (a.getOwner() != a) {
         Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[]{a.annotationType, a.info, a.meta.priority, a.name, a.priority});
         return false;
      } else {
         return true;
      }
   }

   protected void postInject(Target a, InjectionNodes.InjectionNode a) {
      super.postInject(a, a);
      if (a.getOriginalTarget() instanceof TypeInsnNode && a.getOriginalTarget().getOpcode() == 187) {
         RedirectInjector.ConstructorRedirectData a = (RedirectInjector.ConstructorRedirectData)a.getDecoration("ctor");
         if (a.wildcard && a.injected == 0) {
            throw new InvalidInjectionException(a.info, String.format("%s ctor invocation was not found in %s", a.annotationType, a));
         }
      }

   }

   protected void injectAtInvoke(Target a, InjectionNodes.InjectionNode a) {
      RedirectInjector.RedirectedInvoke a = new RedirectInjector.RedirectedInvoke(a, (MethodInsnNode)a.getCurrentTarget());
      a.validateParams(a);
      InsnList a = new InsnList();
      int a = Bytecode.getArgsSize(a.locals) + 1;
      int a = 1;
      int[] a = a.storeArgs(a, a.locals, a, 0);
      if (a.captureTargetArgs) {
         int a = Bytecode.getArgsSize(a.arguments);
         a += a;
         a += a;
         a = Ints.concat(new int[][]{a, a.getArgIndices()});
      }

      AbstractInsnNode a = a.invokeHandlerWithArgs(a.methodArgs, a, a);
      a.replaceNode(a.node, a, a);
      a.addToLocals(a);
      a.addToStack(a);
   }

   protected void validateParams(RedirectInjector.RedirectedInvoke a) {
      int a = a.methodArgs.length;
      String a = String.format("%s handler method %s", a.annotationType, a);
      if (!a.returnType.equals(a.returnType)) {
         throw new InvalidInjectionException(a.info, String.format("%s has an invalid signature. Expected return type %s found %s", a, a.returnType, a.returnType));
      } else {
         for(int a = 0; a < a; ++a) {
            Type a = null;
            if (a >= a.methodArgs.length) {
               throw new InvalidInjectionException(a.info, String.format("%s has an invalid signature. Not enough arguments found for capture of target method args, expected %d but found %d", a, a, a.methodArgs.length));
            }

            Type a = a.methodArgs[a];
            if (a < a.locals.length) {
               a = a.locals[a];
            } else {
               a.captureTargetArgs = true;
               a = Math.max(a, a.locals.length + a.target.arguments.length);
               int a = a - a.locals.length;
               if (a >= a.target.arguments.length) {
                  throw new InvalidInjectionException(a.info, String.format("%s has an invalid signature. Found unexpected additional target argument with type %s at index %d", a, a, a));
               }

               a = a.target.arguments[a];
            }

            AnnotationNode a = Annotations.getInvisibleParameter(a.methodNode, Coerce.class, a);
            if (a.equals(a)) {
               if (a != null && a.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                  Injector.logger.warn("Redundant @Coerce on {} argument {}, {} is identical to {}", new Object[]{a, a, a, a});
               }
            } else {
               boolean a = Injector.canCoerce(a, a);
               if (a == null) {
                  throw new InvalidInjectionException(a.info, String.format("%s has an invalid signature. Found unexpected argument type %s at index %d, expected %s", a, a, a, a));
               }

               if (!a) {
                  throw new InvalidInjectionException(a.info, String.format("%s has an invalid signature. Cannot @Coerce argument type %s at index %d to %s", a, a, a, a));
               }
            }
         }

      }
   }

   private void injectAtFieldAccess(Target a, InjectionNodes.InjectionNode a) {
      FieldInsnNode a = (FieldInsnNode)a.getCurrentTarget();
      int a = a.getOpcode();
      Type a = Type.getType("L" + a.owner + ";");
      Type a = Type.getType(a.desc);
      int a = a.getSort() == 9 ? a.getDimensions() : 0;
      int a = a.returnType.getSort() == 9 ? a.returnType.getDimensions() : 0;
      if (a > a) {
         throw new InvalidInjectionException(a.info, "Dimensionality of handler method is greater than target array on " + a);
      } else {
         if (a == 0 && a > 0) {
            int a = (Integer)a.getDecoration("fuzz");
            int a = (Integer)a.getDecoration("opcode");
            a.injectAtArrayField(a, a, a, a, a, a, a);
         } else {
            a.injectAtScalarField(a, a, a, a, a);
         }

      }
   }

   private void injectAtArrayField(Target a, FieldInsnNode a, int a, Type a, Type a, int a, int a) {
      Type a = a.getElementType();
      if (a != 178 && a != 180) {
         throw new InvalidInjectionException(a.info, String.format("Unspported opcode %s for array access %s", Bytecode.getOpcodeName(a), a.info));
      } else {
         AbstractInsnNode a;
         if (a.returnType.getSort() != 0) {
            if (a != 190) {
               a = a.getOpcode(46);
            }

            a = BeforeFieldAccess.findArrayNode(a.insns, a, a, a);
            a.injectAtGetArray(a, a, a, a, a);
         } else {
            a = BeforeFieldAccess.findArrayNode(a.insns, a, a.getOpcode(79), a);
            a.injectAtSetArray(a, a, a, a, a);
         }

      }
   }

   private void injectAtGetArray(Target a, FieldInsnNode a, AbstractInsnNode a, Type a4, Type a) {
      String a = getGetArrayHandlerDescriptor(a, a.returnType, a);
      boolean a = a.checkDescriptor(a, a, "array getter");
      a.injectArrayRedirect(a, a, a, a, "array getter");
   }

   private void injectAtSetArray(Target a, FieldInsnNode a, AbstractInsnNode a, Type a4, Type a) {
      String a = Bytecode.generateDescriptor((Object)null, (Object[])getArrayArgs(a, 1, a.getElementType()));
      boolean a = a.checkDescriptor(a, a, "array setter");
      a.injectArrayRedirect(a, a, a, a, "array setter");
   }

   public void injectArrayRedirect(Target a, FieldInsnNode a, AbstractInsnNode a, boolean a, String a) {
      if (a == null) {
         String a = "";
         throw new InvalidInjectionException(a.info, String.format("Array element %s on %s could not locate a matching %s instruction in %s. %s", a.annotationType, a, a, a, a));
      } else {
         if (!a.isStatic) {
            a.insns.insertBefore(a, (AbstractInsnNode)(new VarInsnNode(25, 0)));
            a.addToStack(1);
         }

         InsnList a = new InsnList();
         if (a) {
            a.pushArgs(a.arguments, a, a.getArgIndices(), 0, a.arguments.length);
            a.addToStack(Bytecode.getArgsSize(a.arguments));
         }

         a.replaceNode(a, a.invokeHandler(a), a);
      }
   }

   public void injectAtScalarField(Target a, FieldInsnNode a, int a, Type a, Type a) {
      AbstractInsnNode a = null;
      InsnList a = new InsnList();
      if (a != 178 && a != 180) {
         if (a != 179 && a != 181) {
            throw new InvalidInjectionException(a.info, String.format("Unspported opcode %s for %s", Bytecode.getOpcodeName(a), a.info));
         }

         a = a.injectAtPutField(a, a, a, a == 179, a, a);
      } else {
         a = a.injectAtGetField(a, a, a, a == 178, a, a);
      }

      a.replaceNode(a, a, a);
   }

   private AbstractInsnNode injectAtGetField(InsnList a, Target a, FieldInsnNode a3, boolean a, Type a, Type a) {
      String a = a ? Bytecode.generateDescriptor(a) : Bytecode.generateDescriptor(a, a);
      boolean a = a.checkDescriptor(a, a, "getter");
      if (!a.isStatic) {
         a.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
         if (!a) {
            a.add((AbstractInsnNode)(new InsnNode(95)));
         }
      }

      if (a) {
         a.pushArgs(a.arguments, a, a.getArgIndices(), 0, a.arguments.length);
         a.addToStack(Bytecode.getArgsSize(a.arguments));
      }

      a.addToStack(a.isStatic ? 0 : 1);
      return a.invokeHandler(a);
   }

   private AbstractInsnNode injectAtPutField(InsnList a, Target a, FieldInsnNode a3, boolean a, Type a, Type a) {
      String a = a ? Bytecode.generateDescriptor((Object)null, a) : Bytecode.generateDescriptor((Object)null, a, a);
      boolean a = a.checkDescriptor(a, a, "setter");
      if (!a.isStatic) {
         if (a) {
            a.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
            a.add((AbstractInsnNode)(new InsnNode(95)));
         } else {
            int a = a.allocateLocals(a.getSize());
            a.add((AbstractInsnNode)(new VarInsnNode(a.getOpcode(54), a)));
            a.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
            a.add((AbstractInsnNode)(new InsnNode(95)));
            a.add((AbstractInsnNode)(new VarInsnNode(a.getOpcode(21), a)));
         }
      }

      if (a) {
         a.pushArgs(a.arguments, a, a.getArgIndices(), 0, a.arguments.length);
         a.addToStack(Bytecode.getArgsSize(a.arguments));
      }

      a.addToStack(!a.isStatic && !a ? 1 : 0);
      return a.invokeHandler(a);
   }

   protected boolean checkDescriptor(String a, Target a, String a) {
      if (a.methodNode.desc.equals(a)) {
         return false;
      } else {
         int a = a.indexOf(41);
         String a = String.format("%s%s%s", a.substring(0, a), Joiner.on("").join(a.arguments), a.substring(a));
         if (a.methodNode.desc.equals(a)) {
            return true;
         } else {
            throw new InvalidInjectionException(a.info, String.format("%s method %s %s has an invalid signature. Expected %s but found %s", a.annotationType, a, a, a, a.methodNode.desc));
         }
      }
   }

   protected void injectAtConstructor(Target a, InjectionNodes.InjectionNode a) {
      RedirectInjector.ConstructorRedirectData a = (RedirectInjector.ConstructorRedirectData)a.getDecoration("ctor");
      if (a == null) {
         throw new InvalidInjectionException(a.info, String.format("%s ctor redirector has no metadata, the injector failed a preprocessing phase", a.annotationType));
      } else {
         TypeInsnNode a = (TypeInsnNode)a.getCurrentTarget();
         AbstractInsnNode a = a.get(a.indexOf((AbstractInsnNode)a) + 1);
         MethodInsnNode a = a.findInitNodeFor(a);
         if (a == null) {
            if (!a.wildcard) {
               throw new InvalidInjectionException(a.info, String.format("%s ctor invocation was not found in %s", a.annotationType, a));
            }
         } else {
            boolean a = a.getOpcode() == 89;
            String a = a.desc.replace(")V", ")L" + a.desc + ";");
            boolean a = false;

            try {
               a = a.checkDescriptor(a, a, "constructor");
            } catch (InvalidInjectionException var12) {
               if (!a.wildcard) {
                  throw var12;
               }

               return;
            }

            if (a) {
               a.removeNode(a);
            }

            if (a.isStatic) {
               a.removeNode(a);
            } else {
               a.replaceNode(a, (AbstractInsnNode)(new VarInsnNode(25, 0)));
            }

            InsnList a = new InsnList();
            if (a) {
               a.pushArgs(a.arguments, a, a.getArgIndices(), 0, a.arguments.length);
               a.addToStack(Bytecode.getArgsSize(a.arguments));
            }

            a.invokeHandler(a);
            if (a) {
               LabelNode a = new LabelNode();
               a.add((AbstractInsnNode)(new InsnNode(89)));
               a.add((AbstractInsnNode)(new JumpInsnNode(199, a)));
               a.throwException(a, "java/lang/NullPointerException", String.format("%s constructor handler %s returned null for %s", a.annotationType, a, a.desc.replace('/', '.')));
               a.add((AbstractInsnNode)a);
               a.addToStack(1);
            } else {
               a.add((AbstractInsnNode)(new InsnNode(87)));
            }

            a.replaceNode(a, (InsnList)a);
            ++a.injected;
         }
      }
   }

   private static String getGetArrayHandlerDescriptor(AbstractInsnNode a, Type a, Type a) {
      return a != null && a.getOpcode() == 190 ? Bytecode.generateDescriptor(Type.INT_TYPE, (Object[])getArrayArgs(a, 0)) : Bytecode.generateDescriptor(a, (Object[])getArrayArgs(a, 1));
   }

   private static Type[] getArrayArgs(Type a, int a, Type... a) {
      int a = a.getDimensions() + a;
      Type[] a = new Type[a + a.length];

      for(int a = 0; a < a.length; ++a) {
         a[a] = a == 0 ? a : (a < a ? Type.INT_TYPE : a[a - a]);
      }

      return a;
   }

   static class RedirectedInvoke {
      final Target target;
      final MethodInsnNode node;
      final Type returnType;
      final Type[] args;
      final Type[] locals;
      boolean captureTargetArgs = false;

      RedirectedInvoke(Target a, MethodInsnNode a) {
         a.target = a;
         a.node = a;
         a.returnType = Type.getReturnType(a.desc);
         a.args = Type.getArgumentTypes(a.desc);
         a.locals = a.getOpcode() == 184 ? a.args : (Type[])ObjectArrays.concat(Type.getType("L" + a.owner + ";"), a.args);
      }
   }

   static class ConstructorRedirectData {
      public static final String KEY = "ctor";
      public boolean wildcard = false;
      public int injected = 0;
   }

   class Meta {
      public static final String KEY = "redirector";
      final int priority;
      final boolean isFinal;
      final String name;
      final String desc;

      public Meta(int axx, boolean axxx, String axxxx, String axxxxx) {
         a.priority = axx;
         a.isFinal = axxx;
         a.name = axxxx;
         a.desc = axxxxx;
      }

      RedirectInjector getOwner() {
         return RedirectInjector.this;
      }
   }
}

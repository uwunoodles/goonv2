package org.spongepowered.asm.mixin.injection.callback;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class CallbackInjector extends Injector {
   private final boolean cancellable;
   private final LocalCapture localCapture;
   private final String identifier;
   private final Map<Integer, String> ids = new HashMap();
   private int totalInjections = 0;
   private int callbackInfoVar = -1;
   private String lastId;
   private String lastDesc;
   private Target lastTarget;
   private String callbackInfoClass;

   public CallbackInjector(InjectionInfo a, boolean a, LocalCapture a, String a) {
      super(a);
      a.cancellable = a;
      a.localCapture = a;
      a.identifier = a;
   }

   protected void sanityCheck(Target a, List<InjectionPoint> a) {
      super.sanityCheck(a, a);
      if (a.isStatic != a.isStatic) {
         throw new InvalidInjectionException(a.info, "'static' modifier of callback method does not match target in " + a);
      } else {
         if ("<init>".equals(a.method.name)) {
            Iterator var3 = a.iterator();

            while(var3.hasNext()) {
               InjectionPoint a = (InjectionPoint)var3.next();
               if (!a.getClass().equals(BeforeReturn.class)) {
                  throw new InvalidInjectionException(a.info, "Found injection point type " + a.getClass().getSimpleName() + " targetting a ctor in " + a + ". Only RETURN allowed for a ctor target");
               }
            }
         }

      }
   }

   protected void addTargetNode(Target a, List<InjectionNodes.InjectionNode> a, AbstractInsnNode a, Set<InjectionPoint> a) {
      InjectionNodes.InjectionNode a = a.addInjectionNode(a);
      Iterator var6 = a.iterator();

      while(var6.hasNext()) {
         InjectionPoint a = (InjectionPoint)var6.next();
         String a = a.getId();
         if (!Strings.isNullOrEmpty(a)) {
            String a = (String)a.ids.get(a.getId());
            if (a != null && !a.equals(a)) {
               Injector.logger.warn("Conflicting id for {} insn in {}, found id {} on {}, previously defined as {}", new Object[]{Bytecode.getOpcodeName(a), a.toString(), a, a.info, a});
               break;
            }

            a.ids.put(a.getId(), a);
         }
      }

      a.add(a);
      ++a.totalInjections;
   }

   protected void inject(Target a, InjectionNodes.InjectionNode a) {
      LocalVariableNode[] a = null;
      if (a.localCapture.isCaptureLocals() || a.localCapture.isPrintLocals()) {
         a = Locals.getLocalsAt(a.classNode, a.method, a.getCurrentTarget());
      }

      a.inject(new CallbackInjector.Callback(a.methodNode, a, a, a, a.localCapture.isCaptureLocals()));
   }

   private void inject(CallbackInjector.Callback a) {
      if (a.localCapture.isPrintLocals()) {
         a.printLocals(a);
         a.info.addCallbackInvocation(a.methodNode);
      } else {
         MethodNode a = a.methodNode;
         if (!a.checkDescriptor(a.methodNode.desc)) {
            if (a.info.getTargets().size() > 1) {
               return;
            }

            if (a.canCaptureLocals) {
               MethodNode a = Bytecode.findMethod(a.classNode, a.methodNode.name, a.getDescriptor());
               if (a != null && Annotations.getVisible(a, Surrogate.class) != null) {
                  a = a;
               } else {
                  String a = a.generateBadLVTMessage(a);
                  switch(a.localCapture) {
                  case CAPTURE_FAILEXCEPTION:
                     Injector.logger.error("Injection error: {}", new Object[]{a});
                     a = a.generateErrorMethod(a, "org/spongepowered/asm/mixin/injection/throwables/InjectionError", a);
                     break;
                  case CAPTURE_FAILSOFT:
                     Injector.logger.warn("Injection warning: {}", new Object[]{a});
                     return;
                  default:
                     Injector.logger.error("Critical injection failure: {}", new Object[]{a});
                     throw new InjectionError(a);
                  }
               }
            } else {
               String a = a.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;");
               if (a.checkDescriptor(a)) {
                  throw new InvalidInjectionException(a.info, "Invalid descriptor on " + a.info + "! CallbackInfoReturnable is required!");
               }

               MethodNode a = Bytecode.findMethod(a.classNode, a.methodNode.name, a.getDescriptor());
               if (a == null || Annotations.getVisible(a, Surrogate.class) == null) {
                  throw new InvalidInjectionException(a.info, "Invalid descriptor on " + a.info + "! Expected " + a.getDescriptor() + " but found " + a.methodNode.desc);
               }

               a = a;
            }
         }

         a.dupReturnValue(a);
         if (a.cancellable || a.totalInjections > 1) {
            a.createCallbackInfo(a, true);
         }

         a.invokeCallback(a, a);
         a.injectCancellationCode(a);
         a.inject();
         a.info.notifyInjected(a.target);
      }
   }

   private String generateBadLVTMessage(CallbackInjector.Callback a) {
      int a = a.target.indexOf(a.node);
      List<String> a = summariseLocals(a.methodNode.desc, a.target.arguments.length + 1);
      List<String> a = summariseLocals(a.getDescriptorWithAllLocals(), a.frameSize);
      return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", a.target, a, a, a, a);
   }

   private MethodNode generateErrorMethod(CallbackInjector.Callback a, String a, String a) {
      MethodNode a = a.info.addMethod(a.methodNode.access, a.methodNode.name + "$missing", a.getDescriptor());
      a.maxLocals = Bytecode.getFirstNonArgLocalIndex(Type.getArgumentTypes(a.getDescriptor()), !a.isStatic);
      a.maxStack = 3;
      InsnList a = a.instructions;
      a.add((AbstractInsnNode)(new TypeInsnNode(187, a)));
      a.add((AbstractInsnNode)(new InsnNode(89)));
      a.add((AbstractInsnNode)(new LdcInsnNode(a)));
      a.add((AbstractInsnNode)(new MethodInsnNode(183, a, "<init>", "(Ljava/lang/String;)V", false)));
      a.add((AbstractInsnNode)(new InsnNode(191)));
      return a;
   }

   private void printLocals(CallbackInjector.Callback a) {
      Type[] a = Type.getArgumentTypes(a.getDescriptorWithAllLocals());
      SignaturePrinter a = new SignaturePrinter(a.target.method, a.argNames);
      SignaturePrinter a = new SignaturePrinter(a.methodNode.name, a.target.returnType, a, a.argNames);
      a.setModifiers(a.methodNode);
      PrettyPrinter a = new PrettyPrinter();
      a.kv("Target Class", a.classNode.name.replace('/', '.'));
      a.kv("Target Method", a);
      a.kv("Target Max LOCALS", a.target.getMaxLocals());
      a.kv("Initial Frame Size", a.frameSize);
      a.kv("Callback Name", a.methodNode.name);
      a.kv("Instruction", "%s %s", a.node.getClass().getSimpleName(), Bytecode.getOpcodeName(a.node.getCurrentTarget().getOpcode()));
      a.hr();
      if (a.locals.length > a.frameSize) {
         a.add("  %s  %20s  %s", "LOCAL", "TYPE", "NAME");

         for(int a = 0; a < a.locals.length; ++a) {
            String a = a == a.frameSize ? ">" : " ";
            if (a.locals[a] != null) {
               a.add("%s [%3d]  %20s  %-50s %s", a, a, SignaturePrinter.getTypeName(a.localTypes[a], false), meltSnowman(a, a.locals[a].name), a >= a.frameSize ? "<capture>" : "");
            } else {
               boolean a = a > 0 && a.localTypes[a - 1] != null && a.localTypes[a - 1].getSize() > 1;
               a.add("%s [%3d]  %20s", a, a, a ? "<top>" : "-");
            }
         }

         a.hr();
      }

      a.add().add("/**").add(" * Expected callback signature").add(" * /");
      a.add("%s {", a);
      a.add("    // Method body").add("}").add().print(System.err);
   }

   private void createCallbackInfo(CallbackInjector.Callback a, boolean a) {
      if (a.target != a.lastTarget) {
         a.lastId = null;
         a.lastDesc = null;
      }

      a.lastTarget = a.target;
      String a = a.getIdentifier(a);
      String a = a.getCallbackInfoConstructorDescriptor();
      if (!a.equals(a.lastId) || !a.equals(a.lastDesc) || a.isAtReturn || a.cancellable) {
         a.instanceCallbackInfo(a, a, a, a);
      }
   }

   private void loadOrCreateCallbackInfo(CallbackInjector.Callback a) {
      if (!a.cancellable && a.totalInjections <= 1) {
         a.createCallbackInfo(a, false);
      } else {
         a.add(new VarInsnNode(25, a.callbackInfoVar), false, true);
      }

   }

   private void dupReturnValue(CallbackInjector.Callback a) {
      if (a.isAtReturn) {
         a.add(new InsnNode(89));
         a.add(new VarInsnNode(a.target.returnType.getOpcode(54), a.marshalVar()));
      }
   }

   protected void instanceCallbackInfo(CallbackInjector.Callback a, String a, String a, boolean a) {
      a.lastId = a;
      a.lastDesc = a;
      a.callbackInfoVar = a.marshalVar();
      a.callbackInfoClass = a.target.getCallbackInfoClass();
      boolean a = a && a.totalInjections > 1 && !a.isAtReturn && !a.cancellable;
      a.add(new TypeInsnNode(187, a.callbackInfoClass), true, !a, a);
      a.add(new InsnNode(89), true, true, a);
      a.add(new LdcInsnNode(a), true, !a, a);
      a.add(new InsnNode(a.cancellable ? 4 : 3), true, !a, a);
      if (a.isAtReturn) {
         a.add(new VarInsnNode(a.target.returnType.getOpcode(21), a.marshalVar()), true, !a);
         a.add(new MethodInsnNode(183, a.callbackInfoClass, "<init>", a, false));
      } else {
         a.add(new MethodInsnNode(183, a.callbackInfoClass, "<init>", a, false), false, false, a);
      }

      if (a) {
         a.target.addLocalVariable(a.callbackInfoVar, "callbackInfo" + a.callbackInfoVar, "L" + a.callbackInfoClass + ";");
         a.add(new VarInsnNode(58, a.callbackInfoVar), false, false, a);
      }

   }

   private void invokeCallback(CallbackInjector.Callback a, MethodNode a) {
      if (!a.isStatic) {
         a.add(new VarInsnNode(25, 0), false, true);
      }

      if (a.captureArgs()) {
         Bytecode.loadArgs(a.target.arguments, a, a.isStatic ? 0 : 1, -1);
      }

      a.loadOrCreateCallbackInfo(a);
      if (a.canCaptureLocals) {
         Locals.loadLocals(a.localTypes, a, a.frameSize, a.extraArgs);
      }

      a.invokeHandler(a, a);
   }

   private String getIdentifier(CallbackInjector.Callback a) {
      String a = Strings.isNullOrEmpty(a.identifier) ? a.target.method.name : a.identifier;
      String a = (String)a.ids.get(a.node.getId());
      return a + (Strings.isNullOrEmpty(a) ? "" : ":" + a);
   }

   protected void injectCancellationCode(CallbackInjector.Callback a) {
      if (a.cancellable) {
         a.add(new VarInsnNode(25, a.callbackInfoVar));
         a.add(new MethodInsnNode(182, a.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), CallbackInfo.getIsCancelledMethodSig(), false));
         LabelNode a = new LabelNode();
         a.add(new JumpInsnNode(153, a));
         a.injectReturnCode(a);
         a.add(a);
      }
   }

   protected void injectReturnCode(CallbackInjector.Callback a) {
      if (a.target.returnType.equals(Type.VOID_TYPE)) {
         a.add(new InsnNode(177));
      } else {
         a.add(new VarInsnNode(25, a.marshalVar()));
         String a = CallbackInfoReturnable.getReturnAccessor(a.target.returnType);
         String a = CallbackInfoReturnable.getReturnDescriptor(a.target.returnType);
         a.add(new MethodInsnNode(182, a.callbackInfoClass, a, a, false));
         if (a.target.returnType.getSort() == 10) {
            a.add(new TypeInsnNode(192, a.target.returnType.getInternalName()));
         }

         a.add(new InsnNode(a.target.returnType.getOpcode(172)));
      }

   }

   protected boolean isStatic() {
      return a.isStatic;
   }

   private static List<String> summariseLocals(String a, int a) {
      return summariseLocals(Type.getArgumentTypes(a), a);
   }

   private static List<String> summariseLocals(Type[] a, int a) {
      List<String> a = new ArrayList();
      if (a != null) {
         for(; a < a.length; ++a) {
            if (a[a] != null) {
               a.add(a[a].toString());
            }
         }
      }

      return a;
   }

   static String meltSnowman(int a, String a) {
      return a != null && 9731 == a.charAt(0) ? "var" + a : a;
   }

   private class Callback extends InsnList {
      private final MethodNode handler;
      private final AbstractInsnNode head;
      final Target target;
      final InjectionNodes.InjectionNode node;
      final LocalVariableNode[] locals;
      final Type[] localTypes;
      final int frameSize;
      final int extraArgs;
      final boolean canCaptureLocals;
      final boolean isAtReturn;
      final String desc;
      final String descl;
      final String[] argNames;
      int ctor;
      int invoke;
      private int marshalVar = -1;
      private boolean captureArgs = true;

      Callback(MethodNode axxx, Target axxxxx, InjectionNodes.InjectionNode axxxxxxx, LocalVariableNode[] axxxxxxxx, boolean ax) {
         a.handler = axxx;
         a.target = axxxxx;
         a.head = axxxxx.insns.getFirst();
         a.node = axxxxxxx;
         a.locals = axxxxxxxx;
         a.localTypes = axxxxxxxx != null ? new Type[axxxxxxxx.length] : null;
         a.frameSize = Bytecode.getFirstNonArgLocalIndex(axxxxx.arguments, !CallbackInjector.this.isStatic());
         List<String> axx = null;
         if (axxxxxxxx != null) {
            int axxxx = CallbackInjector.this.isStatic() ? 0 : 1;
            axx = new ArrayList();

            for(int axxxxxx = 0; axxxxxx <= axxxxxxxx.length; ++axxxxxx) {
               if (axxxxxx == a.frameSize) {
                  axx.add(axxxxx.returnType == Type.VOID_TYPE ? "ci" : "cir");
               }

               if (axxxxxx < axxxxxxxx.length && axxxxxxxx[axxxxxx] != null) {
                  a.localTypes[axxxxxx] = Type.getType(axxxxxxxx[axxxxxx].desc);
                  if (axxxxxx >= axxxx) {
                     axx.add(CallbackInjector.meltSnowman(axxxxxx, axxxxxxxx[axxxxxx].name));
                  }
               }
            }
         }

         a.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(a.handler) - (a.frameSize + 1));
         a.argNames = axx != null ? (String[])axx.toArray(new String[axx.size()]) : null;
         a.canCaptureLocals = ax && axxxxxxxx != null && axxxxxxxx.length > a.frameSize;
         a.isAtReturn = a.node.getCurrentTarget() instanceof InsnNode && a.isValueReturnOpcode(a.node.getCurrentTarget().getOpcode());
         a.desc = axxxxx.getCallbackDescriptor(a.localTypes, axxxxx.arguments);
         a.descl = axxxxx.getCallbackDescriptor(true, a.localTypes, axxxxx.arguments, a.frameSize, a.extraArgs);
         a.invoke = axxxxx.arguments.length + (a.canCaptureLocals ? a.localTypes.length - a.frameSize : 0);
      }

      private boolean isValueReturnOpcode(int ax) {
         return ax >= 172 && ax < 177;
      }

      String getDescriptor() {
         return a.canCaptureLocals ? a.descl : a.desc;
      }

      String getDescriptorWithAllLocals() {
         return a.target.getCallbackDescriptor(true, a.localTypes, a.target.arguments, a.frameSize, 32767);
      }

      String getCallbackInfoConstructorDescriptor() {
         return a.isAtReturn ? CallbackInfo.getConstructorDescriptor(a.target.returnType) : CallbackInfo.getConstructorDescriptor();
      }

      void add(AbstractInsnNode axx, boolean axxx, boolean ax) {
         a.add(axx, axxx, ax, false);
      }

      void add(AbstractInsnNode axx, boolean axxxx, boolean ax, boolean axxx) {
         if (axxx) {
            a.target.insns.insertBefore(a.head, axx);
         } else {
            a.add(axx);
         }

         a.ctor += axxxx ? 1 : 0;
         a.invoke += ax ? 1 : 0;
      }

      void inject() {
         a.target.insertBefore((InjectionNodes.InjectionNode)a.node, a);
         a.target.addToStack(Math.max(a.invoke, a.ctor));
      }

      boolean checkDescriptor(String ax) {
         if (a.getDescriptor().equals(ax)) {
            return true;
         } else if (a.target.getSimpleCallbackDescriptor().equals(ax) && !a.canCaptureLocals) {
            a.captureArgs = false;
            return true;
         } else {
            Type[] axx = Type.getArgumentTypes(ax);
            Type[] axxx = Type.getArgumentTypes(a.descl);
            if (axx.length != axxx.length) {
               return false;
            } else {
               for(int axxxx = 0; axxxx < axxx.length; ++axxxx) {
                  Type axxxxx = axx[axxxx];
                  if (!axxxxx.equals(axxx[axxxx])) {
                     if (axxxxx.getSort() == 9) {
                        return false;
                     }

                     if (Annotations.getInvisibleParameter(a.handler, Coerce.class, axxxx) == null) {
                        return false;
                     }

                     if (!Injector.canCoerce(axx[axxxx], axxx[axxxx])) {
                        return false;
                     }
                  }
               }

               return true;
            }
         }
      }

      boolean captureArgs() {
         return a.captureArgs;
      }

      int marshalVar() {
         if (a.marshalVar < 0) {
            a.marshalVar = a.target.allocateLocal();
         }

         return a.marshalVar;
      }
   }
}

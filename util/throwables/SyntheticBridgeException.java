package org.spongepowered.asm.util.throwables;

import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;

public class SyntheticBridgeException extends MixinException {
   private static final long serialVersionUID = 1L;
   private final SyntheticBridgeException.Problem problem;
   private final String name;
   private final String desc;
   private final int index;
   private final AbstractInsnNode a;
   private final AbstractInsnNode b;

   public SyntheticBridgeException(SyntheticBridgeException.Problem a, String a, String a, int a, AbstractInsnNode a, AbstractInsnNode a) {
      super(a.getMessage(a, a, a, a, a));
      a.problem = a;
      a.name = a;
      a.desc = a;
      a.index = a;
      a.a = a;
      a.b = a;
   }

   public void printAnalysis(IMixinContext a, MethodNode a, MethodNode a) {
      PrettyPrinter a = new PrettyPrinter();
      a.addWrapped(100, a.getMessage()).hr();
      a.add().kv("Method", a.name + a.desc).kv("Problem Type", a.problem).add().hr();
      String a = (String)Annotations.getValue(Annotations.getVisible(a, MixinMerged.class), "mixin");
      String a = a != null ? a : a.getTargetClassRef().replace('/', '.');
      a.printMethod(a.add("Existing method").add().kv("Owner", a).add(), a).hr();
      a.printMethod(a.add("Incoming method").add().kv("Owner", a.getClassRef().replace('/', '.')).add(), a).hr();
      a.printProblem(a, a, a, a).print(System.err);
   }

   private PrettyPrinter printMethod(PrettyPrinter a, MethodNode a) {
      int a = 0;

      for(ListIterator a = a.instructions.iterator(); a.hasNext(); ++a) {
         a.kv(a == a.index ? ">>>>" : "", Bytecode.describeNode((AbstractInsnNode)a.next()));
      }

      return a.add();
   }

   private PrettyPrinter printProblem(PrettyPrinter a, IMixinContext a, MethodNode a, MethodNode a) {
      Type a = Type.getObjectType(a.getTargetClassRef());
      a.add("Analysis").add();
      Type a;
      switch(a.problem) {
      case BAD_INSN:
         a.add("The bridge methods are not compatible because they contain incompatible opcodes");
         a.add("at index " + a.index + ":").add();
         a.kv("Existing opcode: %s", Bytecode.getOpcodeName(a.a));
         a.kv("Incoming opcode: %s", Bytecode.getOpcodeName(a.b)).add();
         a.add("This implies that the bridge methods are from different interfaces. This problem");
         a.add("may not be resolvable without changing the base interfaces.").add();
         break;
      case BAD_LOAD:
         a.add("The bridge methods are not compatible because they contain different variables at");
         a.add("opcode index " + a.index + ".").add();
         ListIterator<AbstractInsnNode> a = a.instructions.iterator();
         ListIterator<AbstractInsnNode> a = a.instructions.iterator();
         Type[] a = Type.getArgumentTypes(a.desc);
         Type[] a = Type.getArgumentTypes(a.desc);

         for(int a = 0; a.hasNext() && a.hasNext(); ++a) {
            AbstractInsnNode a = (AbstractInsnNode)a.next();
            AbstractInsnNode a = (AbstractInsnNode)a.next();
            if (a instanceof VarInsnNode && a instanceof VarInsnNode) {
               VarInsnNode a = (VarInsnNode)a;
               VarInsnNode a = (VarInsnNode)a;
               Type a = a.var > 0 ? a[a.var - 1] : a;
               a = a.var > 0 ? a[a.var - 1] : a;
               a.kv("Target " + a, "%8s %-2d %s", Bytecode.getOpcodeName(a), a.var, a);
               a.kv("Incoming " + a, "%8s %-2d %s", Bytecode.getOpcodeName(a), a.var, a);
               if (a.equals(a)) {
                  a.kv("", "Types match: %s", a);
               } else if (a.getSort() != a.getSort()) {
                  a.kv("", "Types are incompatible");
               } else if (a.getSort() == 10) {
                  ClassInfo a = ClassInfo.getCommonSuperClassOrInterface(a, a);
                  a.kv("", "Common supertype: %s", a);
               }

               a.add();
            }
         }

         a.add("Since this probably means that the methods come from different interfaces, you");
         a.add("may have a \"multiple inheritance\" problem, it may not be possible to implement");
         a.add("both root interfaces");
         break;
      case BAD_CAST:
         a.add("Incompatible CHECKCAST encountered at opcode " + a.index + ", this could indicate that the bridge");
         a.add("is casting down for contravariant generic types. It may be possible to coalesce the");
         a.add("bridges by adjusting the types in the target method.").add();
         Type a = Type.getObjectType(((TypeInsnNode)a.a).desc);
         Type a = Type.getObjectType(((TypeInsnNode)a.b).desc);
         a.kv("Target type", a);
         a.kv("Incoming type", a);
         a.kv("Common supertype", ClassInfo.getCommonSuperClassOrInterface(a, a)).add();
         break;
      case BAD_INVOKE_NAME:
         a.add("Incompatible invocation targets in synthetic bridge. This is extremely unusual");
         a.add("and implies that a remapping transformer has incorrectly remapped a method. This");
         a.add("is an unrecoverable error.");
         break;
      case BAD_INVOKE_DESC:
         MethodInsnNode a = (MethodInsnNode)a.a;
         MethodInsnNode a = (MethodInsnNode)a.b;
         Type[] a = Type.getArgumentTypes(a.desc);
         Type[] a = Type.getArgumentTypes(a.desc);
         if (a.length != a.length) {
            int a = Type.getArgumentTypes(a.desc).length;
            String a = a.length == a ? "The TARGET" : (a.length == a ? " The INCOMING" : "NEITHER");
            a.add("Mismatched invocation descriptors in synthetic bridge implies that a remapping");
            a.add("transformer has incorrectly coalesced a bridge method with a conflicting name.");
            a.add("Overlapping bridge methods should always have the same number of arguments, yet");
            a.add("the target method has %d arguments, the incoming method has %d. This is an", a.length, a.length);
            a.add("unrecoverable error. %s method has the expected arg count of %d", a, a);
            break;
         } else {
            a = Type.getReturnType(a.desc);
            Type a = Type.getReturnType(a.desc);
            a.add("Incompatible invocation descriptors in synthetic bridge implies that generified");
            a.add("types are incompatible over one or more generic superclasses or interfaces. It may");
            a.add("be possible to adjust the generic types on implemented members to rectify this");
            a.add("problem by coalescing the appropriate generic types.").add();
            a.printTypeComparison(a, "return type", a, a);

            for(int a = 0; a < a.length; ++a) {
               a.printTypeComparison(a, "arg " + a, a[a], a[a]);
            }

            return a;
         }
      case BAD_LENGTH:
         a.add("Mismatched bridge method length implies the bridge methods are incompatible");
         a.add("and may originate from different superinterfaces. This is an unrecoverable");
         a.add("error.").add();
      }

      return a;
   }

   private PrettyPrinter printTypeComparison(PrettyPrinter a, String a, Type a, Type a) {
      a.kv("Target " + a, "%s", a);
      a.kv("Incoming " + a, "%s", a);
      if (a.equals(a)) {
         a.kv("Analysis", "Types match: %s", a);
      } else if (a.getSort() != a.getSort()) {
         a.kv("Analysis", "Types are incompatible");
      } else if (a.getSort() == 10) {
         ClassInfo a = ClassInfo.getCommonSuperClassOrInterface(a, a);
         a.kv("Analysis", "Common supertype: L%s;", a);
      }

      return a.add();
   }

   public static enum Problem {
      BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"),
      BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"),
      BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s"),
      BAD_INVOKE_NAME("Conflicting synthetic bridge target method name in synthetic bridge method %1$s%2$s Existing:%6$s Incoming:%7$s"),
      BAD_INVOKE_DESC("Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s"),
      BAD_LENGTH("Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");

      private final String message;

      private Problem(String a) {
         a.message = a;
      }

      String getMessage(String a, String a, int a, AbstractInsnNode a, AbstractInsnNode a) {
         return String.format(a.message, a, a, a, Bytecode.getOpcodeName(a), Bytecode.getOpcodeName(a), getInsnName(a), getInsnName(a), getInsnDesc(a), getInsnDesc(a));
      }

      private static String getInsnName(AbstractInsnNode a) {
         return a instanceof MethodInsnNode ? ((MethodInsnNode)a).name : "";
      }

      private static String getInsnDesc(AbstractInsnNode a) {
         return a instanceof MethodInsnNode ? ((MethodInsnNode)a).desc : "";
      }
   }
}

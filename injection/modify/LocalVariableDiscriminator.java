package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class LocalVariableDiscriminator {
   private final boolean argsOnly;
   private final int ordinal;
   private final int index;
   private final Set<String> names;
   private final boolean print;

   public LocalVariableDiscriminator(boolean a, int a, int a, Set<String> a, boolean a) {
      a.argsOnly = a;
      a.ordinal = a;
      a.index = a;
      a.names = Collections.unmodifiableSet(a);
      a.print = a;
   }

   public boolean isArgsOnly() {
      return a.argsOnly;
   }

   public int getOrdinal() {
      return a.ordinal;
   }

   public int getIndex() {
      return a.index;
   }

   public Set<String> getNames() {
      return a.names;
   }

   public boolean hasNames() {
      return !a.names.isEmpty();
   }

   public boolean printLVT() {
      return a.print;
   }

   protected boolean isImplicit(LocalVariableDiscriminator.Context a) {
      return a.ordinal < 0 && a.index < a.baseArgIndex && a.names.isEmpty();
   }

   public int findLocal(Type a, boolean a, Target a, AbstractInsnNode a) {
      try {
         return a.findLocal(new LocalVariableDiscriminator.Context(a, a, a, a));
      } catch (InvalidImplicitDiscriminatorException var6) {
         return -2;
      }
   }

   public int findLocal(LocalVariableDiscriminator.Context a) {
      return a.isImplicit(a) ? a.findImplicitLocal(a) : a.findExplicitLocal(a);
   }

   private int findImplicitLocal(LocalVariableDiscriminator.Context a) {
      int a = 0;
      int a = 0;

      for(int a = a.baseArgIndex; a < a.locals.length; ++a) {
         LocalVariableDiscriminator.Context.Local a = a.locals[a];
         if (a != null && a.type.equals(a.returnType)) {
            ++a;
            a = a;
         }
      }

      if (a == 1) {
         return a;
      } else {
         throw new InvalidImplicitDiscriminatorException("Found " + a + " candidate variables but exactly 1 is required.");
      }
   }

   private int findExplicitLocal(LocalVariableDiscriminator.Context a) {
      for(int a = a.baseArgIndex; a < a.locals.length; ++a) {
         LocalVariableDiscriminator.Context.Local a = a.locals[a];
         if (a != null && a.type.equals(a.returnType)) {
            if (a.ordinal > -1) {
               if (a.ordinal == a.ord) {
                  return a;
               }
            } else if (a.index >= a.baseArgIndex) {
               if (a.index == a) {
                  return a;
               }
            } else if (a.names.contains(a.name)) {
               return a;
            }
         }
      }

      return -1;
   }

   public static LocalVariableDiscriminator parse(AnnotationNode a) {
      boolean a = (Boolean)Annotations.getValue(a, "argsOnly", (Object)Boolean.FALSE);
      int a = (Integer)Annotations.getValue(a, "ordinal", (int)-1);
      int a = (Integer)Annotations.getValue(a, "index", (int)-1);
      boolean a = (Boolean)Annotations.getValue(a, "print", (Object)Boolean.FALSE);
      Set<String> a = new HashSet();
      List<String> a = (List)Annotations.getValue(a, "name", (Object)((List)null));
      if (a != null) {
         a.addAll(a);
      }

      return new LocalVariableDiscriminator(a, a, a, a, a);
   }

   public static class Context implements PrettyPrinter.IPrettyPrintable {
      final Target target;
      final Type returnType;
      final AbstractInsnNode node;
      final int baseArgIndex;
      final LocalVariableDiscriminator.Context.Local[] locals;
      private final boolean isStatic;

      public Context(Type a, boolean a, Target a, AbstractInsnNode a) {
         a.isStatic = Bytecode.methodIsStatic(a.method);
         a.returnType = a;
         a.target = a;
         a.node = a;
         a.baseArgIndex = a.isStatic ? 0 : 1;
         a.locals = a.initLocals(a, a, a);
         a.initOrdinals();
      }

      private LocalVariableDiscriminator.Context.Local[] initLocals(Target a, boolean a, AbstractInsnNode a) {
         if (!a) {
            LocalVariableNode[] a = Locals.getLocalsAt(a.classNode, a.method, a);
            if (a != null) {
               LocalVariableDiscriminator.Context.Local[] a = new LocalVariableDiscriminator.Context.Local[a.length];

               for(int a = 0; a < a.length; ++a) {
                  if (a[a] != null) {
                     a[a] = new LocalVariableDiscriminator.Context.Local(a[a].name, Type.getType(a[a].desc));
                  }
               }

               return a;
            }
         }

         LocalVariableDiscriminator.Context.Local[] a = new LocalVariableDiscriminator.Context.Local[a.baseArgIndex + a.arguments.length];
         if (!a.isStatic) {
            a[0] = new LocalVariableDiscriminator.Context.Local("this", Type.getType(a.classNode.name));
         }

         for(int a = a.baseArgIndex; a < a.length; ++a) {
            Type a = a.arguments[a - a.baseArgIndex];
            a[a] = new LocalVariableDiscriminator.Context.Local("arg" + a, a);
         }

         return a;
      }

      private void initOrdinals() {
         Map<Type, Integer> a = new HashMap();

         for(int a = 0; a < a.locals.length; ++a) {
            Integer a = 0;
            if (a.locals[a] != null) {
               a = (Integer)a.get(a.locals[a].type);
               a.put(a.locals[a].type, a = a == null ? 0 : a + 1);
               a.locals[a].ord = a;
            }
         }

      }

      public void print(PrettyPrinter a) {
         a.add("%5s  %7s  %30s  %-50s  %s", "INDEX", "ORDINAL", "TYPE", "NAME", "CANDIDATE");

         for(int a = a.baseArgIndex; a < a.locals.length; ++a) {
            LocalVariableDiscriminator.Context.Local a = a.locals[a];
            if (a != null) {
               Type a = a.type;
               String a = a.name;
               int a = a.ord;
               String a = a.returnType.equals(a) ? "YES" : "-";
               a.add("[%3d]    [%3d]  %30s  %-50s  %s", a, a, SignaturePrinter.getTypeName(a, false), a, a);
            } else if (a > 0) {
               LocalVariableDiscriminator.Context.Local a = a.locals[a - 1];
               boolean a = a != null && a.type != null && a.type.getSize() > 1;
               a.add("[%3d]           %30s", a, a ? "<top>" : "-");
            }
         }

      }

      public class Local {
         int ord = 0;
         String name;
         Type type;

         public Local(String axxx, Type ax) {
            a.name = axxx;
            a.type = ax;
         }

         public String toString() {
            return String.format("Local[ordinal=%d, name=%s, type=%s]", a.ord, a.name, a.type);
         }
      }
   }
}

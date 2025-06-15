package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

@InjectionPoint.AtCode("INVOKE")
public class BeforeInvoke extends InjectionPoint {
   protected final MemberInfo target;
   protected final boolean allowPermissive;
   protected final int ordinal;
   protected final String className;
   protected final IMixinContext context;
   protected final Logger logger = LogManager.getLogger("mixin");
   private boolean log = false;

   public BeforeInvoke(InjectionPointData a) {
      super(a);
      a.target = a.getTarget();
      a.ordinal = a.getOrdinal();
      a.log = a.get("log", false);
      a.className = a.getClassName();
      a.context = a.getContext();
      a.allowPermissive = a.context.getOption(MixinEnvironment.Option.REFMAP_REMAP) && a.context.getOption(MixinEnvironment.Option.REFMAP_REMAP_ALLOW_PERMISSIVE) && !a.context.getReferenceMapper().isDefault();
   }

   private String getClassName() {
      InjectionPoint.AtCode a = (InjectionPoint.AtCode)a.getClass().getAnnotation(InjectionPoint.AtCode.class);
      return String.format("@At(%s)", a != null ? a.value() : a.getClass().getSimpleName().toUpperCase());
   }

   public BeforeInvoke setLogging(boolean a) {
      a.log = a;
      return a;
   }

   public boolean find(String a, InsnList a, Collection<AbstractInsnNode> a) {
      a.log("{} is searching for an injection point in method with descriptor {}", a.className, a);
      if (!a.find(a, a, a, a.target, BeforeInvoke.SearchType.STRICT) && a.target.desc != null && a.allowPermissive) {
         a.logger.warn("STRICT match for {} using \"{}\" in {} returned 0 results, attempting permissive search. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[]{a.className, a.target, a.context});
         return a.find(a, a, a, a.target, BeforeInvoke.SearchType.PERMISSIVE);
      } else {
         return true;
      }
   }

   protected boolean find(String a, InsnList a, Collection<AbstractInsnNode> a, MemberInfo a, BeforeInvoke.SearchType a) {
      if (a == null) {
         return false;
      } else {
         MemberInfo a = a == BeforeInvoke.SearchType.PERMISSIVE ? a.transform((String)null) : a;
         int a = 0;
         int a = 0;

         AbstractInsnNode a;
         for(ListIterator a = a.iterator(); a.hasNext(); a.inspectInsn(a, a, a)) {
            a = (AbstractInsnNode)a.next();
            if (a.matchesInsn(a)) {
               MemberInfo a = new MemberInfo(a);
               a.log("{} is considering insn {}", a.className, a);
               if (a.matches(a.owner, a.name, a.desc)) {
                  a.log("{} > found a matching insn, checking preconditions...", a.className);
                  if (a.matchesInsn(a, a)) {
                     a.log("{} > > > found a matching insn at ordinal {}", a.className, a);
                     if (a.addInsn(a, a, a)) {
                        ++a;
                     }

                     if (a.ordinal == a) {
                        break;
                     }
                  }

                  ++a;
               }
            }
         }

         if (a == BeforeInvoke.SearchType.PERMISSIVE && a > 1) {
            a.logger.warn("A permissive match for {} using \"{}\" in {} matched {} instructions, this may cause unexpected behaviour. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[]{a.className, a, a.context, a});
         }

         return a > 0;
      }
   }

   protected boolean addInsn(InsnList a1, Collection<AbstractInsnNode> a, AbstractInsnNode a) {
      a.add(a);
      return true;
   }

   protected boolean matchesInsn(AbstractInsnNode a) {
      return a instanceof MethodInsnNode;
   }

   protected void inspectInsn(String a1, InsnList a2, AbstractInsnNode a3) {
   }

   protected boolean matchesInsn(MemberInfo a1, int a) {
      a.log("{} > > comparing target ordinal {} with current ordinal {}", a.className, a.ordinal, a);
      return a.ordinal == -1 || a.ordinal == a;
   }

   protected void log(String a, Object... a) {
      if (a.log) {
         a.logger.info(a, a);
      }

   }

   public static enum SearchType {
      STRICT,
      PERMISSIVE;
   }
}

package org.spongepowered.asm.mixin.injection.points;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint.AtCode("NEW")
public class BeforeNew extends InjectionPoint {
   private final String target;
   private final String desc;
   private final int ordinal;

   public BeforeNew(InjectionPointData a) {
      super(a);
      a.ordinal = a.getOrdinal();
      String a = Strings.emptyToNull(a.get("class", a.get("target", "")).replace('.', '/'));
      MemberInfo a = MemberInfo.parseAndValidate(a, a.getContext());
      a.target = a.toCtorType();
      a.desc = a.toCtorDesc();
   }

   public boolean hasDescriptor() {
      return a.desc != null;
   }

   public boolean find(String a1, InsnList a, Collection<AbstractInsnNode> a) {
      boolean a = false;
      int a = 0;
      Collection<TypeInsnNode> a = new ArrayList();
      Collection<AbstractInsnNode> a = a.desc != null ? a : a;
      ListIterator a = a.iterator();

      while(true) {
         AbstractInsnNode a;
         do {
            do {
               do {
                  if (!a.hasNext()) {
                     if (a.desc != null) {
                        Iterator var11 = a.iterator();

                        while(var11.hasNext()) {
                           TypeInsnNode a = (TypeInsnNode)var11.next();
                           if (a.findCtor(a, a)) {
                              a.add(a);
                              a = true;
                           }
                        }
                     }

                     return a;
                  }

                  a = (AbstractInsnNode)a.next();
               } while(!(a instanceof TypeInsnNode));
            } while(a.getOpcode() != 187);
         } while(!a.matchesOwner((TypeInsnNode)a));

         if (a.ordinal == -1 || a.ordinal == a) {
            ((Collection)a).add(a);
            a = a.desc == null;
         }

         ++a;
      }
   }

   protected boolean findCtor(InsnList a, TypeInsnNode a) {
      int a = a.indexOf(a);
      ListIterator a = a.iterator(a);

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (a instanceof MethodInsnNode && a.getOpcode() == 183) {
            MethodInsnNode a = (MethodInsnNode)a;
            if ("<init>".equals(a.name) && a.owner.equals(a.desc) && a.desc.equals(a.desc)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean matchesOwner(TypeInsnNode a) {
      return a.target == null || a.target.equals(a.desc);
   }
}

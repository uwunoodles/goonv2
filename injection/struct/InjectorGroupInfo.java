package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.util.Annotations;

public class InjectorGroupInfo {
   private final String name;
   private final List<InjectionInfo> members;
   private final boolean isDefault;
   private int minCallbackCount;
   private int maxCallbackCount;

   public InjectorGroupInfo(String a) {
      this(a, false);
   }

   InjectorGroupInfo(String a, boolean a) {
      a.members = new ArrayList();
      a.minCallbackCount = -1;
      a.maxCallbackCount = Integer.MAX_VALUE;
      a.name = a;
      a.isDefault = a;
   }

   public String toString() {
      return String.format("@Group(name=%s, min=%d, max=%d)", a.getName(), a.getMinRequired(), a.getMaxAllowed());
   }

   public boolean isDefault() {
      return a.isDefault;
   }

   public String getName() {
      return a.name;
   }

   public int getMinRequired() {
      return Math.max(a.minCallbackCount, 1);
   }

   public int getMaxAllowed() {
      return Math.min(a.maxCallbackCount, Integer.MAX_VALUE);
   }

   public Collection<InjectionInfo> getMembers() {
      return Collections.unmodifiableCollection(a.members);
   }

   public void setMinRequired(int a) {
      if (a < 1) {
         throw new IllegalArgumentException("Cannot set zero or negative value for injector group min count. Attempted to set min=" + a + " on " + a);
      } else {
         if (a.minCallbackCount > 0 && a.minCallbackCount != a) {
            LogManager.getLogger("mixin").warn("Conflicting min value '{}' on @Group({}), previously specified {}", new Object[]{a, a.name, a.minCallbackCount});
         }

         a.minCallbackCount = Math.max(a.minCallbackCount, a);
      }
   }

   public void setMaxAllowed(int a) {
      if (a < 1) {
         throw new IllegalArgumentException("Cannot set zero or negative value for injector group max count. Attempted to set max=" + a + " on " + a);
      } else {
         if (a.maxCallbackCount < Integer.MAX_VALUE && a.maxCallbackCount != a) {
            LogManager.getLogger("mixin").warn("Conflicting max value '{}' on @Group({}), previously specified {}", new Object[]{a, a.name, a.maxCallbackCount});
         }

         a.maxCallbackCount = Math.min(a.maxCallbackCount, a);
      }
   }

   public InjectorGroupInfo add(InjectionInfo a) {
      a.members.add(a);
      return a;
   }

   public InjectorGroupInfo validate() throws InjectionValidationException {
      if (a.members.size() == 0) {
         return a;
      } else {
         int a = 0;

         InjectionInfo a;
         for(Iterator var2 = a.members.iterator(); var2.hasNext(); a += a.getInjectedCallbackCount()) {
            a = (InjectionInfo)var2.next();
         }

         int a = a.getMinRequired();
         int a = a.getMaxAllowed();
         if (a < a) {
            throw new InjectionValidationException(a, String.format("expected %d invocation(s) but only %d succeeded", a, a));
         } else if (a > a) {
            throw new InjectionValidationException(a, String.format("maximum of %d invocation(s) allowed but %d succeeded", a, a));
         } else {
            return a;
         }
      }
   }

   public static final class Map extends HashMap<String, InjectorGroupInfo> {
      private static final long serialVersionUID = 1L;
      private static final InjectorGroupInfo NO_GROUP = new InjectorGroupInfo("NONE", true);

      public InjectorGroupInfo get(Object a) {
         return a.forName(a.toString());
      }

      public InjectorGroupInfo forName(String a) {
         InjectorGroupInfo a = (InjectorGroupInfo)super.get(a);
         if (a == null) {
            a = new InjectorGroupInfo(a);
            a.put(a, a);
         }

         return a;
      }

      public InjectorGroupInfo parseGroup(MethodNode a, String a) {
         return a.parseGroup(Annotations.getInvisible(a, Group.class), a);
      }

      public InjectorGroupInfo parseGroup(AnnotationNode a, String a) {
         if (a == null) {
            return NO_GROUP;
         } else {
            String a = (String)Annotations.getValue(a, "name");
            if (a == null || a.isEmpty()) {
               a = a;
            }

            InjectorGroupInfo a = a.forName(a);
            Integer a = (Integer)Annotations.getValue(a, "min");
            if (a != null && a != -1) {
               a.setMinRequired(a);
            }

            Integer a = (Integer)Annotations.getValue(a, "max");
            if (a != null && a != -1) {
               a.setMaxAllowed(a);
            }

            return a;
         }
      }

      public void validateAll() throws InjectionValidationException {
         Iterator var1 = a.values().iterator();

         while(var1.hasNext()) {
            InjectorGroupInfo a = (InjectorGroupInfo)var1.next();
            a.validate();
         }

      }
   }
}

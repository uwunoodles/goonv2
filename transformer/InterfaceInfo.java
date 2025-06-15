package org.spongepowered.asm.mixin.transformer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;

public final class InterfaceInfo {
   private final MixinInfo mixin;
   private final String prefix;
   private final Type iface;
   private final boolean unique;
   private Set<String> methods;

   private InterfaceInfo(MixinInfo a, String a, Type a, boolean a) {
      if (a != null && a.length() >= 2 && a.endsWith("$")) {
         a.mixin = a;
         a.prefix = a;
         a.iface = a;
         a.unique = a;
      } else {
         throw new InvalidMixinException(a, String.format("Prefix %s for iface %s is not valid", a, a.toString()));
      }
   }

   private void initMethods() {
      a.methods = new HashSet();
      a.readInterface(a.iface.getInternalName());
   }

   private void readInterface(String a) {
      ClassInfo a = ClassInfo.forName(a);
      Iterator var3 = a.getMethods().iterator();

      while(var3.hasNext()) {
         ClassInfo.Method a = (ClassInfo.Method)var3.next();
         a.methods.add(a.toString());
      }

      var3 = a.getInterfaces().iterator();

      while(var3.hasNext()) {
         String a = (String)var3.next();
         a.readInterface(a);
      }

   }

   public String getPrefix() {
      return a.prefix;
   }

   public Type getIface() {
      return a.iface;
   }

   public String getName() {
      return a.iface.getClassName();
   }

   public String getInternalName() {
      return a.iface.getInternalName();
   }

   public boolean isUnique() {
      return a.unique;
   }

   public boolean renameMethod(MethodNode a) {
      if (a.methods == null) {
         a.initMethods();
      }

      if (!a.name.startsWith(a.prefix)) {
         if (a.methods.contains(a.name + a.desc)) {
            a.decorateUniqueMethod(a);
         }

         return false;
      } else {
         String a = a.name.substring(a.prefix.length());
         String a = a + a.desc;
         if (!a.methods.contains(a)) {
            throw new InvalidMixinException(a.mixin, String.format("%s does not exist in target interface %s", a, a.getName()));
         } else if ((a.access & 1) == 0) {
            throw new InvalidMixinException(a.mixin, String.format("%s cannot implement %s because it is not visible", a, a.getName()));
         } else {
            Annotations.setVisible(a, MixinRenamed.class, "originalName", a.name, "isInterfaceMember", true);
            a.decorateUniqueMethod(a);
            a.name = a;
            return true;
         }
      }
   }

   private void decorateUniqueMethod(MethodNode a) {
      if (a.unique) {
         if (Annotations.getVisible(a, Unique.class) == null) {
            Annotations.setVisible(a, Unique.class);
            a.mixin.getClassInfo().findMethod(a).setUnique(true);
         }

      }
   }

   static InterfaceInfo fromAnnotation(MixinInfo a, AnnotationNode a) {
      String a = (String)Annotations.getValue(a, "prefix");
      Type a = (Type)Annotations.getValue(a, "iface");
      Boolean a = (Boolean)Annotations.getValue(a, "unique");
      if (a != null && a != null) {
         return new InterfaceInfo(a, a, a, a != null && a);
      } else {
         throw new InvalidMixinException(a, String.format("@Interface annotation on %s is missing a required parameter", a));
      }
   }

   public boolean equals(Object a) {
      if (a == a) {
         return true;
      } else if (a != null && a.getClass() == a.getClass()) {
         InterfaceInfo a = (InterfaceInfo)a;
         return a.mixin.equals(a.mixin) && a.prefix.equals(a.prefix) && a.iface.equals(a.iface);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int a = a.mixin.hashCode();
      a = 31 * a + a.prefix.hashCode();
      a = 31 * a + a.iface.hashCode();
      return a;
   }
}

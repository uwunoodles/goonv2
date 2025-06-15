package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import org.spongepowered.asm.obfuscation.mapping.IMapping;

public class MappingMethod implements IMapping<MappingMethod> {
   private final String owner;
   private final String name;
   private final String desc;

   public MappingMethod(String a, String a) {
      this(getOwnerFromName(a), getBaseName(a), a);
   }

   public MappingMethod(String a, String a, String a) {
      a.owner = a;
      a.name = a;
      a.desc = a;
   }

   public IMapping.Type getType() {
      return IMapping.Type.METHOD;
   }

   public String getName() {
      return a.name == null ? null : (a.owner != null ? a.owner + "/" : "") + a.name;
   }

   public String getSimpleName() {
      return a.name;
   }

   public String getOwner() {
      return a.owner;
   }

   public String getDesc() {
      return a.desc;
   }

   public MappingMethod getSuper() {
      return null;
   }

   public boolean isConstructor() {
      return "<init>".equals(a.name);
   }

   public MappingMethod move(String a) {
      return new MappingMethod(a, a.getSimpleName(), a.getDesc());
   }

   public MappingMethod remap(String a) {
      return new MappingMethod(a.getOwner(), a, a.getDesc());
   }

   public MappingMethod transform(String a) {
      return new MappingMethod(a.getOwner(), a.getSimpleName(), a);
   }

   public MappingMethod copy() {
      return new MappingMethod(a.getOwner(), a.getSimpleName(), a.getDesc());
   }

   public MappingMethod addPrefix(String a) {
      String a = a.getSimpleName();
      return a != null && !a.startsWith(a) ? new MappingMethod(a.getOwner(), a + a, a.getDesc()) : a;
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{a.getName(), a.getDesc()});
   }

   public boolean equals(Object a) {
      if (a == a) {
         return true;
      } else if (!(a instanceof MappingMethod)) {
         return false;
      } else {
         return Objects.equal(a.name, ((MappingMethod)a).name) && Objects.equal(a.desc, ((MappingMethod)a).desc);
      }
   }

   public String serialise() {
      return a.toString();
   }

   public String toString() {
      String a = a.getDesc();
      return String.format("%s%s%s", a.getName(), a != null ? " " : "", a != null ? a : "");
   }

   private static String getBaseName(String a) {
      if (a == null) {
         return null;
      } else {
         int a = a.lastIndexOf(47);
         return a > -1 ? a.substring(a + 1) : a;
      }
   }

   private static String getOwnerFromName(String a) {
      if (a == null) {
         return null;
      } else {
         int a = a.lastIndexOf(47);
         return a > -1 ? a.substring(0, a) : null;
      }
   }
}

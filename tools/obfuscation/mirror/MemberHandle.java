package org.spongepowered.tools.obfuscation.mirror;

import org.spongepowered.asm.obfuscation.mapping.IMapping;

public abstract class MemberHandle<T extends IMapping<T>> {
   private final String owner;
   private final String name;
   private final String desc;

   protected MemberHandle(String a, String a, String a) {
      a.owner = a;
      a.name = a;
      a.desc = a;
   }

   public final String getOwner() {
      return a.owner;
   }

   public final String getName() {
      return a.name;
   }

   public final String getDesc() {
      return a.desc;
   }

   public abstract Visibility getVisibility();

   public abstract T asMapping(boolean var1);
}

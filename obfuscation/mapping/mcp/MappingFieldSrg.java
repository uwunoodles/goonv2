package org.spongepowered.asm.obfuscation.mapping.mcp;

import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

public class MappingFieldSrg extends MappingField {
   private final String srg;

   public MappingFieldSrg(String a) {
      super(getOwnerFromSrg(a), getNameFromSrg(a), (String)null);
      a.srg = a;
   }

   public MappingFieldSrg(MappingField a) {
      super(a.getOwner(), a.getName(), (String)null);
      a.srg = a.getOwner() + "/" + a.getName();
   }

   public String serialise() {
      return a.srg;
   }

   private static String getNameFromSrg(String a) {
      if (a == null) {
         return null;
      } else {
         int a = a.lastIndexOf(47);
         return a > -1 ? a.substring(a + 1) : a;
      }
   }

   private static String getOwnerFromSrg(String a) {
      if (a == null) {
         return null;
      } else {
         int a = a.lastIndexOf(47);
         return a > -1 ? a.substring(0, a) : null;
      }
   }
}

package org.spongepowered.asm.lib;

public final class Handle {
   final int tag;
   final String owner;
   final String name;
   final String desc;
   final boolean itf;

   /** @deprecated */
   @Deprecated
   public Handle(int a, String a, String a, String a) {
      this(a, a, a, a, a == 9);
   }

   public Handle(int a, String a, String a, String a, boolean a) {
      a.tag = a;
      a.owner = a;
      a.name = a;
      a.desc = a;
      a.itf = a;
   }

   public int getTag() {
      return a.tag;
   }

   public String getOwner() {
      return a.owner;
   }

   public String getName() {
      return a.name;
   }

   public String getDesc() {
      return a.desc;
   }

   public boolean isInterface() {
      return a.itf;
   }

   public boolean equals(Object a) {
      if (a == a) {
         return true;
      } else if (!(a instanceof Handle)) {
         return false;
      } else {
         Handle a = (Handle)a;
         return a.tag == a.tag && a.itf == a.itf && a.owner.equals(a.owner) && a.name.equals(a.name) && a.desc.equals(a.desc);
      }
   }

   public int hashCode() {
      return a.tag + (a.itf ? 64 : 0) + a.owner.hashCode() * a.name.hashCode() * a.desc.hashCode();
   }

   public String toString() {
      return a.owner + '.' + a.name + a.desc + " (" + a.tag + (a.itf ? " itf" : "") + ')';
   }
}

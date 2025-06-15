package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.Type;

public class BasicValue implements Value {
   public static final BasicValue UNINITIALIZED_VALUE = new BasicValue((Type)null);
   public static final BasicValue INT_VALUE;
   public static final BasicValue FLOAT_VALUE;
   public static final BasicValue LONG_VALUE;
   public static final BasicValue DOUBLE_VALUE;
   public static final BasicValue REFERENCE_VALUE;
   public static final BasicValue RETURNADDRESS_VALUE;
   private final Type type;

   public BasicValue(Type a) {
      a.type = a;
   }

   public Type getType() {
      return a.type;
   }

   public int getSize() {
      return a.type != Type.LONG_TYPE && a.type != Type.DOUBLE_TYPE ? 1 : 2;
   }

   public boolean isReference() {
      return a.type != null && (a.type.getSort() == 10 || a.type.getSort() == 9);
   }

   public boolean equals(Object a) {
      if (a == a) {
         return true;
      } else if (a instanceof BasicValue) {
         if (a.type == null) {
            return ((BasicValue)a).type == null;
         } else {
            return a.type.equals(((BasicValue)a).type);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return a.type == null ? 0 : a.type.hashCode();
   }

   public String toString() {
      if (a == UNINITIALIZED_VALUE) {
         return ".";
      } else if (a == RETURNADDRESS_VALUE) {
         return "A";
      } else {
         return a == REFERENCE_VALUE ? "R" : a.type.getDescriptor();
      }
   }

   static {
      INT_VALUE = new BasicValue(Type.INT_TYPE);
      FLOAT_VALUE = new BasicValue(Type.FLOAT_TYPE);
      LONG_VALUE = new BasicValue(Type.LONG_TYPE);
      DOUBLE_VALUE = new BasicValue(Type.DOUBLE_TYPE);
      REFERENCE_VALUE = new BasicValue(Type.getObjectType("java/lang/Object"));
      RETURNADDRESS_VALUE = new BasicValue(Type.VOID_TYPE);
   }
}

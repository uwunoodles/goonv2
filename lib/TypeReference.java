package org.spongepowered.asm.lib;

public class TypeReference {
   public static final int CLASS_TYPE_PARAMETER = 0;
   public static final int METHOD_TYPE_PARAMETER = 1;
   public static final int CLASS_EXTENDS = 16;
   public static final int CLASS_TYPE_PARAMETER_BOUND = 17;
   public static final int METHOD_TYPE_PARAMETER_BOUND = 18;
   public static final int FIELD = 19;
   public static final int METHOD_RETURN = 20;
   public static final int METHOD_RECEIVER = 21;
   public static final int METHOD_FORMAL_PARAMETER = 22;
   public static final int THROWS = 23;
   public static final int LOCAL_VARIABLE = 64;
   public static final int RESOURCE_VARIABLE = 65;
   public static final int EXCEPTION_PARAMETER = 66;
   public static final int INSTANCEOF = 67;
   public static final int NEW = 68;
   public static final int CONSTRUCTOR_REFERENCE = 69;
   public static final int METHOD_REFERENCE = 70;
   public static final int CAST = 71;
   public static final int CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT = 72;
   public static final int METHOD_INVOCATION_TYPE_ARGUMENT = 73;
   public static final int CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT = 74;
   public static final int METHOD_REFERENCE_TYPE_ARGUMENT = 75;
   private int value;

   public TypeReference(int a) {
      a.value = a;
   }

   public static TypeReference newTypeReference(int a) {
      return new TypeReference(a << 24);
   }

   public static TypeReference newTypeParameterReference(int a, int a) {
      return new TypeReference(a << 24 | a << 16);
   }

   public static TypeReference newTypeParameterBoundReference(int a, int a, int a) {
      return new TypeReference(a << 24 | a << 16 | a << 8);
   }

   public static TypeReference newSuperTypeReference(int a) {
      a &= 65535;
      return new TypeReference(268435456 | a << 8);
   }

   public static TypeReference newFormalParameterReference(int a) {
      return new TypeReference(369098752 | a << 16);
   }

   public static TypeReference newExceptionReference(int a) {
      return new TypeReference(385875968 | a << 8);
   }

   public static TypeReference newTryCatchReference(int a) {
      return new TypeReference(1107296256 | a << 8);
   }

   public static TypeReference newTypeArgumentReference(int a, int a) {
      return new TypeReference(a << 24 | a);
   }

   public int getSort() {
      return a.value >>> 24;
   }

   public int getTypeParameterIndex() {
      return (a.value & 16711680) >> 16;
   }

   public int getTypeParameterBoundIndex() {
      return (a.value & '\uff00') >> 8;
   }

   public int getSuperTypeIndex() {
      return (short)((a.value & 16776960) >> 8);
   }

   public int getFormalParameterIndex() {
      return (a.value & 16711680) >> 16;
   }

   public int getExceptionIndex() {
      return (a.value & 16776960) >> 8;
   }

   public int getTryCatchBlockIndex() {
      return (a.value & 16776960) >> 8;
   }

   public int getTypeArgumentIndex() {
      return a.value & 255;
   }

   public int getValue() {
      return a.value;
   }
}

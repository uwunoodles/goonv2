package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;

public class CallbackInfoReturnable<R> extends CallbackInfo {
   private R returnValue;

   public CallbackInfoReturnable(String a, boolean a) {
      super(a, a);
      a.returnValue = null;
   }

   public CallbackInfoReturnable(String a, boolean a, R a) {
      super(a, a);
      a.returnValue = a;
   }

   public CallbackInfoReturnable(String a, boolean a, byte a) {
      super(a, a);
      a.returnValue = a;
   }

   public CallbackInfoReturnable(String a, boolean a, char a) {
      super(a, a);
      a.returnValue = a;
   }

   public CallbackInfoReturnable(String a, boolean a, double a) {
      super(a, a);
      a.returnValue = a;
   }

   public CallbackInfoReturnable(String a, boolean a, float a) {
      super(a, a);
      a.returnValue = a;
   }

   public CallbackInfoReturnable(String a, boolean a, int a) {
      super(a, a);
      a.returnValue = a;
   }

   public CallbackInfoReturnable(String a, boolean a, long a) {
      super(a, a);
      a.returnValue = a;
   }

   public CallbackInfoReturnable(String a, boolean a, short a) {
      super(a, a);
      a.returnValue = a;
   }

   public CallbackInfoReturnable(String a, boolean a, boolean a) {
      super(a, a);
      a.returnValue = a;
   }

   public void setReturnValue(R a) throws CancellationException {
      super.cancel();
      a.returnValue = a;
   }

   public R getReturnValue() {
      return a.returnValue;
   }

   public byte getReturnValueB() {
      return a.returnValue == null ? 0 : (Byte)a.returnValue;
   }

   public char getReturnValueC() {
      return a.returnValue == null ? '\u0000' : (Character)a.returnValue;
   }

   public double getReturnValueD() {
      return a.returnValue == null ? 0.0D : (Double)a.returnValue;
   }

   public float getReturnValueF() {
      return a.returnValue == null ? 0.0F : (Float)a.returnValue;
   }

   public int getReturnValueI() {
      return a.returnValue == null ? 0 : (Integer)a.returnValue;
   }

   public long getReturnValueJ() {
      return a.returnValue == null ? 0L : (Long)a.returnValue;
   }

   public short getReturnValueS() {
      return a.returnValue == null ? 0 : (Short)a.returnValue;
   }

   public boolean getReturnValueZ() {
      return a.returnValue == null ? false : (Boolean)a.returnValue;
   }

   static String getReturnAccessor(Type a) {
      return a.getSort() != 10 && a.getSort() != 9 ? String.format("getReturnValue%s", a.getDescriptor()) : "getReturnValue";
   }

   static String getReturnDescriptor(Type a) {
      return a.getSort() != 10 && a.getSort() != 9 ? String.format("()%s", a.getDescriptor()) : String.format("()%s", "Ljava/lang/Object;");
   }
}

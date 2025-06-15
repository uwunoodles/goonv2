package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.lib.Type;

public class CallbackInfo implements Cancellable {
   private final String name;
   private final boolean cancellable;
   private boolean cancelled;

   public CallbackInfo(String a, boolean a) {
      a.name = a;
      a.cancellable = a;
   }

   public String getId() {
      return a.name;
   }

   public String toString() {
      return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", a.getClass().getSimpleName(), a.name, a.cancellable);
   }

   public final boolean isCancellable() {
      return a.cancellable;
   }

   public final boolean isCancelled() {
      return a.cancelled;
   }

   public void cancel() throws CancellationException {
      if (!a.cancellable) {
         throw new CancellationException(String.format("The call %s is not cancellable.", a.name));
      } else {
         a.cancelled = true;
      }
   }

   static String getCallInfoClassName() {
      return CallbackInfo.class.getName();
   }

   public static String getCallInfoClassName(Type a) {
      return (a.equals(Type.VOID_TYPE) ? CallbackInfo.class.getName() : CallbackInfoReturnable.class.getName()).replace('.', '/');
   }

   static String getConstructorDescriptor(Type a) {
      if (a.equals(Type.VOID_TYPE)) {
         return getConstructorDescriptor();
      } else {
         return a.getSort() != 10 && a.getSort() != 9 ? String.format("(%sZ%s)V", "Ljava/lang/String;", a.getDescriptor()) : String.format("(%sZ%s)V", "Ljava/lang/String;", "Ljava/lang/Object;");
      }
   }

   static String getConstructorDescriptor() {
      return String.format("(%sZ)V", "Ljava/lang/String;");
   }

   static String getIsCancelledMethodName() {
      return "isCancelled";
   }

   static String getIsCancelledMethodSig() {
      return "()Z";
   }
}

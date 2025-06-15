package org.spongepowered.asm.mixin.injection.callback;

public class CancellationException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public CancellationException() {
   }

   public CancellationException(String a) {
      super(a);
   }

   public CancellationException(Throwable a) {
      super(a);
   }

   public CancellationException(String a, Throwable a) {
      super(a, a);
   }
}

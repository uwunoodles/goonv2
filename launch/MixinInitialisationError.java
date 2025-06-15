package org.spongepowered.asm.launch;

public class MixinInitialisationError extends Error {
   private static final long serialVersionUID = 1L;

   public MixinInitialisationError() {
   }

   public MixinInitialisationError(String a) {
      super(a);
   }

   public MixinInitialisationError(Throwable a) {
      super(a);
   }

   public MixinInitialisationError(String a, Throwable a) {
      super(a, a);
   }
}

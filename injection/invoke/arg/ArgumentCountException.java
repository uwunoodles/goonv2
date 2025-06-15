package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentCountException extends IllegalArgumentException {
   private static final long serialVersionUID = 1L;

   public ArgumentCountException(int a, int a, String a) {
      super("Invalid number of arguments for setAll, received " + a + " but expected " + a + ": " + a);
   }
}

package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentIndexOutOfBoundsException extends IndexOutOfBoundsException {
   private static final long serialVersionUID = 1L;

   public ArgumentIndexOutOfBoundsException(int a) {
      super("Argument index is out of bounds: " + a);
   }
}

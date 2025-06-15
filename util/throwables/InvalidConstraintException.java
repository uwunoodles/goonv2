package org.spongepowered.asm.util.throwables;

public class InvalidConstraintException extends IllegalArgumentException {
   private static final long serialVersionUID = 1L;

   public InvalidConstraintException() {
   }

   public InvalidConstraintException(String a) {
      super(a);
   }

   public InvalidConstraintException(Throwable a) {
      super(a);
   }

   public InvalidConstraintException(String a, Throwable a) {
      super(a, a);
   }
}

package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.ConstraintParser;

public class ConstraintViolationException extends Exception {
   private static final String MISSING_VALUE = "UNRESOLVED";
   private static final long serialVersionUID = 1L;
   private final ConstraintParser.Constraint constraint;
   private final String badValue;

   public ConstraintViolationException(ConstraintParser.Constraint a) {
      a.constraint = a;
      a.badValue = "UNRESOLVED";
   }

   public ConstraintViolationException(ConstraintParser.Constraint a, int a) {
      a.constraint = a;
      a.badValue = String.valueOf(a);
   }

   public ConstraintViolationException(String a, ConstraintParser.Constraint a) {
      super(a);
      a.constraint = a;
      a.badValue = "UNRESOLVED";
   }

   public ConstraintViolationException(String a, ConstraintParser.Constraint a, int a) {
      super(a);
      a.constraint = a;
      a.badValue = String.valueOf(a);
   }

   public ConstraintViolationException(Throwable a, ConstraintParser.Constraint a) {
      super(a);
      a.constraint = a;
      a.badValue = "UNRESOLVED";
   }

   public ConstraintViolationException(Throwable a, ConstraintParser.Constraint a, int a) {
      super(a);
      a.constraint = a;
      a.badValue = String.valueOf(a);
   }

   public ConstraintViolationException(String a, Throwable a, ConstraintParser.Constraint a) {
      super(a, a);
      a.constraint = a;
      a.badValue = "UNRESOLVED";
   }

   public ConstraintViolationException(String a, Throwable a, ConstraintParser.Constraint a, int a) {
      super(a, a);
      a.constraint = a;
      a.badValue = String.valueOf(a);
   }

   public ConstraintParser.Constraint getConstraint() {
      return a.constraint;
   }

   public String getBadValue() {
      return a.badValue;
   }
}

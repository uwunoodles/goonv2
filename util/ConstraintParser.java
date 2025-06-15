package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

public final class ConstraintParser {
   private ConstraintParser() {
   }

   public static ConstraintParser.Constraint parse(String a) {
      if (a != null && a.length() != 0) {
         String[] a = a.replaceAll("\\s", "").toUpperCase().split(";");
         ConstraintParser.Constraint a = null;
         String[] var3 = a;
         int var4 = a.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String a = var3[var5];
            ConstraintParser.Constraint a = new ConstraintParser.Constraint(a);
            if (a == null) {
               a = a;
            } else {
               a.append(a);
            }
         }

         return a != null ? a : ConstraintParser.Constraint.NONE;
      } else {
         return ConstraintParser.Constraint.NONE;
      }
   }

   public static ConstraintParser.Constraint parse(AnnotationNode a) {
      String a = (String)Annotations.getValue(a, "constraints", (Object)"");
      return parse(a);
   }

   public static class Constraint {
      public static final ConstraintParser.Constraint NONE = new ConstraintParser.Constraint();
      private static final Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
      private final String expr;
      private String token;
      private String[] constraint;
      private int min = Integer.MIN_VALUE;
      private int max = Integer.MAX_VALUE;
      private ConstraintParser.Constraint next;

      Constraint(String a) {
         a.expr = a;
         Matcher a = pattern.matcher(a);
         if (!a.matches()) {
            throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + a.expr);
         } else {
            a.token = a.group(1);
            a.constraint = new String[]{a.group(2), a.group(3), a.group(4), a.group(5), a.group(6), a.group(7), a.group(8)};
            a.parse();
         }
      }

      private Constraint() {
         a.expr = null;
         a.token = "*";
         a.constraint = new String[0];
      }

      private void parse() {
         if (a.has(1)) {
            a.max = a.min = a.val(1);
            boolean a = a.has(0);
            if (a.has(4)) {
               if (a) {
                  throw new InvalidConstraintException("Unexpected modifier '" + a.elem(0) + "' in " + a.expr + " parsing range");
               } else {
                  a.max = a.val(4);
                  if (a.max < a.min) {
                     throw new InvalidConstraintException("Invalid range specified '" + a.max + "' is less than " + a.min + " in " + a.expr);
                  }
               }
            } else if (a.has(6)) {
               if (a) {
                  throw new InvalidConstraintException("Unexpected modifier '" + a.elem(0) + "' in " + a.expr + " parsing range");
               } else {
                  a.max = a.min + a.val(6);
               }
            } else {
               String a;
               if (a) {
                  if (a.has(3)) {
                     throw new InvalidConstraintException("Unexpected trailing modifier '" + a.elem(3) + "' in " + a.expr);
                  }

                  a = a.elem(0);
                  if (">".equals(a)) {
                     ++a.min;
                     a.max = Integer.MAX_VALUE;
                  } else if (">=".equals(a)) {
                     a.max = Integer.MAX_VALUE;
                  } else if ("<".equals(a)) {
                     a.max = --a.min;
                     a.min = Integer.MIN_VALUE;
                  } else if ("<=".equals(a)) {
                     a.max = a.min;
                     a.min = Integer.MIN_VALUE;
                  }
               } else if (a.has(2)) {
                  a = a.elem(2);
                  if ("<".equals(a)) {
                     a.max = a.min;
                     a.min = Integer.MIN_VALUE;
                  } else {
                     a.max = Integer.MAX_VALUE;
                  }
               }

            }
         }
      }

      private boolean has(int a) {
         return a.constraint[a] != null;
      }

      private String elem(int a) {
         return a.constraint[a];
      }

      private int val(int a) {
         return a.constraint[a] != null ? Integer.parseInt(a.constraint[a]) : 0;
      }

      void append(ConstraintParser.Constraint a) {
         if (a.next != null) {
            a.next.append(a);
         } else {
            a.next = a;
         }
      }

      public String getToken() {
         return a.token;
      }

      public int getMin() {
         return a.min;
      }

      public int getMax() {
         return a.max;
      }

      public void check(ITokenProvider a) throws ConstraintViolationException {
         if (a != NONE) {
            Integer a = a.getToken(a.token);
            if (a == null) {
               throw new ConstraintViolationException("The token '" + a.token + "' could not be resolved in " + a, a);
            }

            if (a < a.min) {
               throw new ConstraintViolationException("Token '" + a.token + "' has a value (" + a + ") which is less than the minimum value " + a.min + " in " + a, a, a);
            }

            if (a > a.max) {
               throw new ConstraintViolationException("Token '" + a.token + "' has a value (" + a + ") which is greater than the maximum value " + a.max + " in " + a, a, a);
            }
         }

         if (a.next != null) {
            a.next.check(a);
         }

      }

      public String getRangeHumanReadable() {
         if (a.min == Integer.MIN_VALUE && a.max == Integer.MAX_VALUE) {
            return "ANY VALUE";
         } else if (a.min == Integer.MIN_VALUE) {
            return String.format("less than or equal to %d", a.max);
         } else if (a.max == Integer.MAX_VALUE) {
            return String.format("greater than or equal to %d", a.min);
         } else {
            return a.min == a.max ? String.format("%d", a.min) : String.format("between %d and %d", a.min, a.max);
         }
      }

      public String toString() {
         return String.format("Constraint(%s [%d-%d])", a.token, a.min, a.max);
      }
   }
}

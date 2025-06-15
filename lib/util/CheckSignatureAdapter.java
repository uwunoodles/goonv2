package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

public class CheckSignatureAdapter extends SignatureVisitor {
   public static final int CLASS_SIGNATURE = 0;
   public static final int METHOD_SIGNATURE = 1;
   public static final int TYPE_SIGNATURE = 2;
   private static final int EMPTY = 1;
   private static final int FORMAL = 2;
   private static final int BOUND = 4;
   private static final int SUPER = 8;
   private static final int PARAM = 16;
   private static final int RETURN = 32;
   private static final int SIMPLE_TYPE = 64;
   private static final int CLASS_TYPE = 128;
   private static final int END = 256;
   private final int type;
   private int state;
   private boolean canBeVoid;
   private final SignatureVisitor sv;

   public CheckSignatureAdapter(int a, SignatureVisitor a) {
      this(327680, a, a);
   }

   protected CheckSignatureAdapter(int a, int a, SignatureVisitor a) {
      super(a);
      a.type = a;
      a.state = 1;
      a.sv = a;
   }

   public void visitFormalTypeParameter(String a) {
      if (a.type != 2 && (a.state == 1 || a.state == 2 || a.state == 4)) {
         CheckMethodAdapter.checkIdentifier(a, "formal type parameter");
         a.state = 2;
         if (a.sv != null) {
            a.sv.visitFormalTypeParameter(a);
         }

      } else {
         throw new IllegalStateException();
      }
   }

   public SignatureVisitor visitClassBound() {
      if (a.state != 2) {
         throw new IllegalStateException();
      } else {
         a.state = 4;
         SignatureVisitor a = a.sv == null ? null : a.sv.visitClassBound();
         return new CheckSignatureAdapter(2, a);
      }
   }

   public SignatureVisitor visitInterfaceBound() {
      if (a.state != 2 && a.state != 4) {
         throw new IllegalArgumentException();
      } else {
         SignatureVisitor a = a.sv == null ? null : a.sv.visitInterfaceBound();
         return new CheckSignatureAdapter(2, a);
      }
   }

   public SignatureVisitor visitSuperclass() {
      if (a.type == 0 && (a.state & 7) != 0) {
         a.state = 8;
         SignatureVisitor a = a.sv == null ? null : a.sv.visitSuperclass();
         return new CheckSignatureAdapter(2, a);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public SignatureVisitor visitInterface() {
      if (a.state != 8) {
         throw new IllegalStateException();
      } else {
         SignatureVisitor a = a.sv == null ? null : a.sv.visitInterface();
         return new CheckSignatureAdapter(2, a);
      }
   }

   public SignatureVisitor visitParameterType() {
      if (a.type == 1 && (a.state & 23) != 0) {
         a.state = 16;
         SignatureVisitor a = a.sv == null ? null : a.sv.visitParameterType();
         return new CheckSignatureAdapter(2, a);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public SignatureVisitor visitReturnType() {
      if (a.type == 1 && (a.state & 23) != 0) {
         a.state = 32;
         SignatureVisitor a = a.sv == null ? null : a.sv.visitReturnType();
         CheckSignatureAdapter a = new CheckSignatureAdapter(2, a);
         a.canBeVoid = true;
         return a;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public SignatureVisitor visitExceptionType() {
      if (a.state != 32) {
         throw new IllegalStateException();
      } else {
         SignatureVisitor a = a.sv == null ? null : a.sv.visitExceptionType();
         return new CheckSignatureAdapter(2, a);
      }
   }

   public void visitBaseType(char a) {
      if (a.type == 2 && a.state == 1) {
         if (a == 'V') {
            if (!a.canBeVoid) {
               throw new IllegalArgumentException();
            }
         } else if ("ZCBSIFJD".indexOf(a) == -1) {
            throw new IllegalArgumentException();
         }

         a.state = 64;
         if (a.sv != null) {
            a.sv.visitBaseType(a);
         }

      } else {
         throw new IllegalStateException();
      }
   }

   public void visitTypeVariable(String a) {
      if (a.type == 2 && a.state == 1) {
         CheckMethodAdapter.checkIdentifier(a, "type variable");
         a.state = 64;
         if (a.sv != null) {
            a.sv.visitTypeVariable(a);
         }

      } else {
         throw new IllegalStateException();
      }
   }

   public SignatureVisitor visitArrayType() {
      if (a.type == 2 && a.state == 1) {
         a.state = 64;
         SignatureVisitor a = a.sv == null ? null : a.sv.visitArrayType();
         return new CheckSignatureAdapter(2, a);
      } else {
         throw new IllegalStateException();
      }
   }

   public void visitClassType(String a) {
      if (a.type == 2 && a.state == 1) {
         CheckMethodAdapter.checkInternalName(a, "class name");
         a.state = 128;
         if (a.sv != null) {
            a.sv.visitClassType(a);
         }

      } else {
         throw new IllegalStateException();
      }
   }

   public void visitInnerClassType(String a) {
      if (a.state != 128) {
         throw new IllegalStateException();
      } else {
         CheckMethodAdapter.checkIdentifier(a, "inner class name");
         if (a.sv != null) {
            a.sv.visitInnerClassType(a);
         }

      }
   }

   public void visitTypeArgument() {
      if (a.state != 128) {
         throw new IllegalStateException();
      } else {
         if (a.sv != null) {
            a.sv.visitTypeArgument();
         }

      }
   }

   public SignatureVisitor visitTypeArgument(char a) {
      if (a.state != 128) {
         throw new IllegalStateException();
      } else if ("+-=".indexOf(a) == -1) {
         throw new IllegalArgumentException();
      } else {
         SignatureVisitor a = a.sv == null ? null : a.sv.visitTypeArgument(a);
         return new CheckSignatureAdapter(2, a);
      }
   }

   public void visitEnd() {
      if (a.state != 128) {
         throw new IllegalStateException();
      } else {
         a.state = 256;
         if (a.sv != null) {
            a.sv.visitEnd();
         }

      }
   }
}

package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.signature.SignatureWriter;

public abstract class Remapper {
   public String mapDesc(String a) {
      Type a = Type.getType(a);
      switch(a.getSort()) {
      case 9:
         String a = a.mapDesc(a.getElementType().getDescriptor());

         for(int a = 0; a < a.getDimensions(); ++a) {
            a = '[' + a;
         }

         return a;
      case 10:
         String a = a.map(a.getInternalName());
         if (a != null) {
            return 'L' + a + ';';
         }
      default:
         return a;
      }
   }

   private Type mapType(Type a) {
      String a;
      switch(a.getSort()) {
      case 9:
         a = a.mapDesc(a.getElementType().getDescriptor());

         for(int a = 0; a < a.getDimensions(); ++a) {
            a = '[' + a;
         }

         return Type.getType(a);
      case 10:
         a = a.map(a.getInternalName());
         return a != null ? Type.getObjectType(a) : a;
      case 11:
         return Type.getMethodType(a.mapMethodDesc(a.getDescriptor()));
      default:
         return a;
      }
   }

   public String mapType(String a) {
      return a == null ? null : a.mapType(Type.getObjectType(a)).getInternalName();
   }

   public String[] mapTypes(String[] a) {
      String[] a = null;
      boolean a = false;

      for(int a = 0; a < a.length; ++a) {
         String a = a[a];
         String a = a.map(a);
         if (a != null && a == null) {
            a = new String[a.length];
            if (a > 0) {
               System.arraycopy(a, 0, a, 0, a);
            }

            a = true;
         }

         if (a) {
            a[a] = a == null ? a : a;
         }
      }

      return a ? a : a;
   }

   public String mapMethodDesc(String a) {
      if ("()V".equals(a)) {
         return a;
      } else {
         Type[] a = Type.getArgumentTypes(a);
         StringBuilder a = new StringBuilder("(");

         for(int a = 0; a < a.length; ++a) {
            a.append(a.mapDesc(a[a].getDescriptor()));
         }

         Type a = Type.getReturnType(a);
         if (a == Type.VOID_TYPE) {
            a.append(")V");
            return a.toString();
         } else {
            a.append(')').append(a.mapDesc(a.getDescriptor()));
            return a.toString();
         }
      }
   }

   public Object mapValue(Object a) {
      if (a instanceof Type) {
         return a.mapType((Type)a);
      } else if (a instanceof Handle) {
         Handle a = (Handle)a;
         return new Handle(a.getTag(), a.mapType(a.getOwner()), a.mapMethodName(a.getOwner(), a.getName(), a.getDesc()), a.mapMethodDesc(a.getDesc()), a.isInterface());
      } else {
         return a;
      }
   }

   public String mapSignature(String a, boolean a) {
      if (a == null) {
         return null;
      } else {
         SignatureReader a = new SignatureReader(a);
         SignatureWriter a = new SignatureWriter();
         SignatureVisitor a = a.createSignatureRemapper(a);
         if (a) {
            a.acceptType(a);
         } else {
            a.accept(a);
         }

         return a.toString();
      }
   }

   /** @deprecated */
   @Deprecated
   protected SignatureVisitor createRemappingSignatureAdapter(SignatureVisitor a) {
      return new SignatureRemapper(a, a);
   }

   protected SignatureVisitor createSignatureRemapper(SignatureVisitor a) {
      return a.createRemappingSignatureAdapter(a);
   }

   public String mapMethodName(String a1, String a, String a3) {
      return a;
   }

   public String mapInvokeDynamicMethodName(String a, String a2) {
      return a;
   }

   public String mapFieldName(String a1, String a, String a3) {
      return a;
   }

   public String map(String a) {
      return a;
   }
}

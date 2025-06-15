package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;

public class SimpleVerifier extends BasicVerifier {
   private final Type currentClass;
   private final Type currentSuperClass;
   private final List<Type> currentClassInterfaces;
   private final boolean isInterface;
   private ClassLoader loader;

   public SimpleVerifier() {
      this((Type)null, (Type)null, false);
   }

   public SimpleVerifier(Type a, Type a, boolean a) {
      this(a, a, (List)null, a);
   }

   public SimpleVerifier(Type a, Type a, List<Type> a, boolean a) {
      this(327680, a, a, a, a);
   }

   protected SimpleVerifier(int a, Type a, Type a, List<Type> a, boolean a) {
      super(a);
      a.loader = a.getClass().getClassLoader();
      a.currentClass = a;
      a.currentSuperClass = a;
      a.currentClassInterfaces = a;
      a.isInterface = a;
   }

   public void setClassLoader(ClassLoader a) {
      a.loader = a;
   }

   public BasicValue newValue(Type a) {
      if (a == null) {
         return BasicValue.UNINITIALIZED_VALUE;
      } else {
         boolean a = a.getSort() == 9;
         if (a) {
            switch(a.getElementType().getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
               return new BasicValue(a);
            }
         }

         BasicValue a = super.newValue(a);
         if (BasicValue.REFERENCE_VALUE.equals(a)) {
            if (a) {
               a = a.newValue(a.getElementType());
               String a = a.getType().getDescriptor();

               for(int a = 0; a < a.getDimensions(); ++a) {
                  a = '[' + a;
               }

               a = new BasicValue(Type.getType(a));
            } else {
               a = new BasicValue(a);
            }
         }

         return a;
      }
   }

   protected boolean isArrayValue(BasicValue a) {
      Type a = a.getType();
      return a != null && ("Lnull;".equals(a.getDescriptor()) || a.getSort() == 9);
   }

   protected BasicValue getElementValue(BasicValue a) throws AnalyzerException {
      Type a = a.getType();
      if (a != null) {
         if (a.getSort() == 9) {
            return a.newValue(Type.getType(a.getDescriptor().substring(1)));
         }

         if ("Lnull;".equals(a.getDescriptor())) {
            return a;
         }
      }

      throw new Error("Internal error");
   }

   protected boolean isSubTypeOf(BasicValue a, BasicValue a) {
      Type a = a.getType();
      Type a = a.getType();
      switch(a.getSort()) {
      case 5:
      case 6:
      case 7:
      case 8:
         return a.equals(a);
      case 9:
      case 10:
         if ("Lnull;".equals(a.getDescriptor())) {
            return true;
         } else {
            if (a.getSort() != 10 && a.getSort() != 9) {
               return false;
            }

            return a.isAssignableFrom(a, a);
         }
      default:
         throw new Error("Internal error");
      }
   }

   public BasicValue merge(BasicValue a, BasicValue a) {
      if (a.equals(a)) {
         return a;
      } else {
         Type a = a.getType();
         Type a = a.getType();
         if (a != null && (a.getSort() == 10 || a.getSort() == 9) && a != null && (a.getSort() == 10 || a.getSort() == 9)) {
            if ("Lnull;".equals(a.getDescriptor())) {
               return a;
            } else if ("Lnull;".equals(a.getDescriptor())) {
               return a;
            } else if (a.isAssignableFrom(a, a)) {
               return a;
            } else if (a.isAssignableFrom(a, a)) {
               return a;
            } else {
               while(a != null && !a.isInterface(a)) {
                  a = a.getSuperClass(a);
                  if (a.isAssignableFrom(a, a)) {
                     return a.newValue(a);
                  }
               }

               return BasicValue.REFERENCE_VALUE;
            }
         } else {
            return BasicValue.UNINITIALIZED_VALUE;
         }
      }
   }

   protected boolean isInterface(Type a) {
      return a.currentClass != null && a.equals(a.currentClass) ? a.isInterface : a.getClass(a).isInterface();
   }

   protected Type getSuperClass(Type a) {
      if (a.currentClass != null && a.equals(a.currentClass)) {
         return a.currentSuperClass;
      } else {
         Class<?> a = a.getClass(a).getSuperclass();
         return a == null ? null : Type.getType(a);
      }
   }

   protected boolean isAssignableFrom(Type a, Type a) {
      if (a.equals(a)) {
         return true;
      } else if (a.currentClass != null && a.equals(a.currentClass)) {
         if (a.getSuperClass(a) == null) {
            return false;
         } else if (!a.isInterface) {
            return a.isAssignableFrom(a, a.getSuperClass(a));
         } else {
            return a.getSort() == 10 || a.getSort() == 9;
         }
      } else if (a.currentClass != null && a.equals(a.currentClass)) {
         if (a.isAssignableFrom(a, a.currentSuperClass)) {
            return true;
         } else {
            if (a.currentClassInterfaces != null) {
               for(int a = 0; a < a.currentClassInterfaces.size(); ++a) {
                  Type a = (Type)a.currentClassInterfaces.get(a);
                  if (a.isAssignableFrom(a, a)) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         Class<?> a = a.getClass(a);
         if (a.isInterface()) {
            a = Object.class;
         }

         return a.isAssignableFrom(a.getClass(a));
      }
   }

   protected Class<?> getClass(Type a) {
      try {
         return a.getSort() == 9 ? Class.forName(a.getDescriptor().replace('/', '.'), false, a.loader) : Class.forName(a.getClassName(), false, a.loader);
      } catch (ClassNotFoundException var3) {
         throw new RuntimeException(var3.toString());
      }
   }
}

package org.spongepowered.tools.obfuscation.mirror;

import java.util.Iterator;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import org.spongepowered.asm.util.SignaturePrinter;

public abstract class TypeUtils {
   private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
   private static final String OBJECT_SIG = "java.lang.Object";
   private static final String OBJECT_REF = "java/lang/Object";

   private TypeUtils() {
   }

   public static PackageElement getPackage(TypeMirror a) {
      return !(a instanceof DeclaredType) ? null : getPackage((TypeElement)((DeclaredType)a).asElement());
   }

   public static PackageElement getPackage(TypeElement a) {
      Element a;
      for(a = a.getEnclosingElement(); a != null && !(a instanceof PackageElement); a = a.getEnclosingElement()) {
      }

      return (PackageElement)a;
   }

   public static String getElementType(Element a) {
      if (a instanceof TypeElement) {
         return "TypeElement";
      } else if (a instanceof ExecutableElement) {
         return "ExecutableElement";
      } else if (a instanceof VariableElement) {
         return "VariableElement";
      } else if (a instanceof PackageElement) {
         return "PackageElement";
      } else {
         return a instanceof TypeParameterElement ? "TypeParameterElement" : a.getClass().getSimpleName();
      }
   }

   public static String stripGenerics(String a) {
      StringBuilder a = new StringBuilder();
      int a = 0;

      for(int a = 0; a < a.length(); ++a) {
         char a = a.charAt(a);
         if (a == '<') {
            ++a;
         }

         if (a == 0) {
            a.append(a);
         } else if (a == '>') {
            --a;
         }
      }

      return a.toString();
   }

   public static String getName(VariableElement a) {
      return a != null ? a.getSimpleName().toString() : null;
   }

   public static String getName(ExecutableElement a) {
      return a != null ? a.getSimpleName().toString() : null;
   }

   public static String getJavaSignature(Element a) {
      if (a instanceof ExecutableElement) {
         ExecutableElement a = (ExecutableElement)a;
         StringBuilder a = (new StringBuilder()).append("(");
         boolean a = false;

         for(Iterator var4 = a.getParameters().iterator(); var4.hasNext(); a = true) {
            VariableElement a = (VariableElement)var4.next();
            if (a) {
               a.append(',');
            }

            a.append(getTypeName(a.asType()));
         }

         a.append(')').append(getTypeName(a.getReturnType()));
         return a.toString();
      } else {
         return getTypeName(a.asType());
      }
   }

   public static String getJavaSignature(String a) {
      return (new SignaturePrinter("", a)).setFullyQualified(true).toDescriptor();
   }

   public static String getTypeName(TypeMirror a) {
      switch(a.getKind()) {
      case ARRAY:
         return getTypeName(((ArrayType)a).getComponentType()) + "[]";
      case DECLARED:
         return getTypeName((DeclaredType)a);
      case TYPEVAR:
         return getTypeName(getUpperBound(a));
      case ERROR:
         return "java.lang.Object";
      default:
         return a.toString();
      }
   }

   public static String getTypeName(DeclaredType a) {
      return a == null ? "java.lang.Object" : getInternalName((TypeElement)a.asElement()).replace('/', '.');
   }

   public static String getDescriptor(Element a) {
      if (a instanceof ExecutableElement) {
         return getDescriptor((ExecutableElement)a);
      } else {
         return a instanceof VariableElement ? getInternalName((VariableElement)a) : getInternalName(a.asType());
      }
   }

   public static String getDescriptor(ExecutableElement a) {
      if (a == null) {
         return null;
      } else {
         StringBuilder a = new StringBuilder();
         Iterator var2 = a.getParameters().iterator();

         while(var2.hasNext()) {
            VariableElement a = (VariableElement)var2.next();
            a.append(getInternalName(a));
         }

         String a = getInternalName(a.getReturnType());
         return String.format("(%s)%s", a, a);
      }
   }

   public static String getInternalName(VariableElement a) {
      return a == null ? null : getInternalName(a.asType());
   }

   public static String getInternalName(TypeMirror a) {
      switch(a.getKind()) {
      case ARRAY:
         return "[" + getInternalName(((ArrayType)a).getComponentType());
      case DECLARED:
         return "L" + getInternalName((DeclaredType)a) + ";";
      case TYPEVAR:
         return "L" + getInternalName(getUpperBound(a)) + ";";
      case ERROR:
         return "Ljava/lang/Object;";
      case BOOLEAN:
         return "Z";
      case BYTE:
         return "B";
      case CHAR:
         return "C";
      case DOUBLE:
         return "D";
      case FLOAT:
         return "F";
      case INT:
         return "I";
      case LONG:
         return "J";
      case SHORT:
         return "S";
      case VOID:
         return "V";
      default:
         throw new IllegalArgumentException("Unable to parse type symbol " + a + " with " + a.getKind() + " to equivalent bytecode type");
      }
   }

   public static String getInternalName(DeclaredType a) {
      return a == null ? "java/lang/Object" : getInternalName((TypeElement)a.asElement());
   }

   public static String getInternalName(TypeElement a) {
      if (a == null) {
         return null;
      } else {
         StringBuilder a = new StringBuilder();
         a.append(a.getSimpleName());

         for(Element a = a.getEnclosingElement(); a != null; a = a.getEnclosingElement()) {
            if (a instanceof TypeElement) {
               a.insert(0, "$").insert(0, a.getSimpleName());
            } else if (a instanceof PackageElement) {
               a.insert(0, "/").insert(0, ((PackageElement)a).getQualifiedName().toString().replace('.', '/'));
            }
         }

         return a.toString();
      }
   }

   private static DeclaredType getUpperBound(TypeMirror a) {
      try {
         return getUpperBound0(a, 5);
      } catch (IllegalStateException var2) {
         throw new IllegalArgumentException("Type symbol \"" + a + "\" is too complex", var2);
      } catch (IllegalArgumentException var3) {
         throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + a, var3);
      }
   }

   private static DeclaredType getUpperBound0(TypeMirror a, int a) {
      if (a == 0) {
         throw new IllegalStateException("Generic symbol \"" + a + "\" is too complex, exceeded " + 5 + " iterations attempting to determine upper bound");
      } else if (a instanceof DeclaredType) {
         return (DeclaredType)a;
      } else if (a instanceof TypeVariable) {
         try {
            TypeMirror a = ((TypeVariable)a).getUpperBound();
            --a;
            return getUpperBound0(a, a);
         } catch (IllegalStateException var3) {
            throw var3;
         } catch (IllegalArgumentException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + a);
         }
      } else {
         return null;
      }
   }

   public static boolean isAssignable(ProcessingEnvironment a, TypeMirror a, TypeMirror a) {
      boolean a = a.getTypeUtils().isAssignable(a, a);
      if (!a && a instanceof DeclaredType && a instanceof DeclaredType) {
         TypeMirror a = toRawType(a, (DeclaredType)a);
         TypeMirror a = toRawType(a, (DeclaredType)a);
         return a.getTypeUtils().isAssignable(a, a);
      } else {
         return a;
      }
   }

   private static TypeMirror toRawType(ProcessingEnvironment a, DeclaredType a) {
      return a.getElementUtils().getTypeElement(((TypeElement)a.asElement()).getQualifiedName()).asType();
   }

   public static Visibility getVisibility(Element a) {
      if (a == null) {
         return null;
      } else {
         Iterator var1 = a.getModifiers().iterator();

         while(var1.hasNext()) {
            Modifier a = (Modifier)var1.next();
            switch(a) {
            case PUBLIC:
               return Visibility.PUBLIC;
            case PROTECTED:
               return Visibility.PROTECTED;
            case PRIVATE:
               return Visibility.PRIVATE;
            }
         }

         return Visibility.PACKAGE;
      }
   }
}

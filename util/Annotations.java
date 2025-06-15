package org.spongepowered.asm.util;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;

public final class Annotations {
   private Annotations() {
   }

   public static void setVisible(FieldNode a, Class<? extends Annotation> a, Object... a) {
      AnnotationNode a = createNode(Type.getDescriptor(a), a);
      a.visibleAnnotations = add(a.visibleAnnotations, a);
   }

   public static void setInvisible(FieldNode a, Class<? extends Annotation> a, Object... a) {
      AnnotationNode a = createNode(Type.getDescriptor(a), a);
      a.invisibleAnnotations = add(a.invisibleAnnotations, a);
   }

   public static void setVisible(MethodNode a, Class<? extends Annotation> a, Object... a) {
      AnnotationNode a = createNode(Type.getDescriptor(a), a);
      a.visibleAnnotations = add(a.visibleAnnotations, a);
   }

   public static void setInvisible(MethodNode a, Class<? extends Annotation> a, Object... a) {
      AnnotationNode a = createNode(Type.getDescriptor(a), a);
      a.invisibleAnnotations = add(a.invisibleAnnotations, a);
   }

   private static AnnotationNode createNode(String a, Object... a) {
      AnnotationNode a = new AnnotationNode(a);

      for(int a = 0; a < a.length - 1; a += 2) {
         if (!(a[a] instanceof String)) {
            throw new IllegalArgumentException("Annotation keys must be strings, found " + a[a].getClass().getSimpleName() + " with " + a[a].toString() + " at index " + a + " creating " + a);
         }

         a.visit((String)a[a], a[a + 1]);
      }

      return a;
   }

   private static List<AnnotationNode> add(List<AnnotationNode> a, AnnotationNode a) {
      if (a == null) {
         a = new ArrayList(1);
      } else {
         ((List)a).remove(get((List)a, a.desc));
      }

      ((List)a).add(a);
      return (List)a;
   }

   public static AnnotationNode getVisible(FieldNode a, Class<? extends Annotation> a) {
      return get(a.visibleAnnotations, Type.getDescriptor(a));
   }

   public static AnnotationNode getInvisible(FieldNode a, Class<? extends Annotation> a) {
      return get(a.invisibleAnnotations, Type.getDescriptor(a));
   }

   public static AnnotationNode getVisible(MethodNode a, Class<? extends Annotation> a) {
      return get(a.visibleAnnotations, Type.getDescriptor(a));
   }

   public static AnnotationNode getInvisible(MethodNode a, Class<? extends Annotation> a) {
      return get(a.invisibleAnnotations, Type.getDescriptor(a));
   }

   public static AnnotationNode getSingleVisible(MethodNode a, Class<? extends Annotation>... a) {
      return getSingle(a.visibleAnnotations, a);
   }

   public static AnnotationNode getSingleInvisible(MethodNode a, Class<? extends Annotation>... a) {
      return getSingle(a.invisibleAnnotations, a);
   }

   public static AnnotationNode getVisible(ClassNode a, Class<? extends Annotation> a) {
      return get(a.visibleAnnotations, Type.getDescriptor(a));
   }

   public static AnnotationNode getInvisible(ClassNode a, Class<? extends Annotation> a) {
      return get(a.invisibleAnnotations, Type.getDescriptor(a));
   }

   public static AnnotationNode getVisibleParameter(MethodNode a, Class<? extends Annotation> a, int a) {
      return getParameter(a.visibleParameterAnnotations, Type.getDescriptor(a), a);
   }

   public static AnnotationNode getInvisibleParameter(MethodNode a, Class<? extends Annotation> a, int a) {
      return getParameter(a.invisibleParameterAnnotations, Type.getDescriptor(a), a);
   }

   public static AnnotationNode getParameter(List<AnnotationNode>[] a, String a, int a) {
      return a != null && a >= 0 && a < a.length ? get(a[a], a) : null;
   }

   public static AnnotationNode get(List<AnnotationNode> a, String a) {
      if (a == null) {
         return null;
      } else {
         Iterator var2 = a.iterator();

         AnnotationNode a;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            a = (AnnotationNode)var2.next();
         } while(!a.equals(a.desc));

         return a;
      }
   }

   private static AnnotationNode getSingle(List<AnnotationNode> a, Class<? extends Annotation>[] a) {
      List<AnnotationNode> a = new ArrayList();
      Class[] var3 = a;
      int var4 = a.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class<? extends Annotation> a = var3[var5];
         AnnotationNode a = get(a, Type.getDescriptor(a));
         if (a != null) {
            a.add(a);
         }
      }

      int a = a.size();
      if (a > 1) {
         throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(a, new Function<AnnotationNode, String>() {
            public String apply(AnnotationNode ax) {
               return ax.desc;
            }
         }));
      } else {
         return a == 0 ? null : (AnnotationNode)a.get(0);
      }
   }

   public static <T> T getValue(AnnotationNode a) {
      return getValue(a, "value");
   }

   public static <T> T getValue(AnnotationNode a, String a, T a) {
      T a = getValue(a, a);
      return a != null ? a : a;
   }

   public static <T> T getValue(AnnotationNode a, String a, Class<?> a) {
      Preconditions.checkNotNull(a, "annotationClass cannot be null");
      T a = getValue(a, a);
      if (a == null) {
         try {
            a = a.getDeclaredMethod(a).getDefaultValue();
         } catch (NoSuchMethodException var5) {
         }
      }

      return a;
   }

   public static <T> T getValue(AnnotationNode a, String a) {
      boolean a = false;
      if (a != null && a.values != null) {
         Iterator var3 = a.values.iterator();

         while(var3.hasNext()) {
            Object a = var3.next();
            if (a) {
               return a;
            }

            if (a.equals(a)) {
               a = true;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static <T extends Enum<T>> T getValue(AnnotationNode a, String a, Class<T> a, T a) {
      String[] a = (String[])getValue(a, a);
      return a == null ? a : toEnumValue(a, a);
   }

   public static <T> List<T> getValue(AnnotationNode a, String a, boolean a2) {
      Object a = getValue(a, a);
      if (a instanceof List) {
         return (List)a;
      } else if (a != null) {
         List<T> a = new ArrayList();
         a.add(a);
         return a;
      } else {
         return Collections.emptyList();
      }
   }

   public static <T extends Enum<T>> List<T> getValue(AnnotationNode a, String a, boolean a2, Class<T> a) {
      Object a = getValue(a, a);
      if (!(a instanceof List)) {
         if (a instanceof String[]) {
            List<T> a = new ArrayList();
            a.add(toEnumValue(a, (String[])((String[])a)));
            return a;
         } else {
            return Collections.emptyList();
         }
      } else {
         ListIterator a = ((List)a).listIterator();

         while(a.hasNext()) {
            a.set(toEnumValue(a, (String[])((String[])a.next())));
         }

         return (List)a;
      }
   }

   private static <T extends Enum<T>> T toEnumValue(Class<T> a, String[] a) {
      if (!a.getName().equals(Type.getType(a[0]).getClassName())) {
         throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
      } else {
         return Enum.valueOf(a, a[1]);
      }
   }
}

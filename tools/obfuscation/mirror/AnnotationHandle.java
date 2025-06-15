package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public final class AnnotationHandle {
   public static final AnnotationHandle MISSING = new AnnotationHandle((AnnotationMirror)null);
   private final AnnotationMirror annotation;

   private AnnotationHandle(AnnotationMirror a) {
      a.annotation = a;
   }

   public AnnotationMirror asMirror() {
      return a.annotation;
   }

   public boolean exists() {
      return a.annotation != null;
   }

   public String toString() {
      return a.annotation == null ? "@{UnknownAnnotation}" : "@" + a.annotation.getAnnotationType().asElement().getSimpleName();
   }

   public <T> T getValue(String a, T a) {
      if (a.annotation == null) {
         return a;
      } else {
         AnnotationValue a = a.getAnnotationValue(a);
         if (a instanceof Enum && a != null) {
            VariableElement a = (VariableElement)a.getValue();
            return a == null ? a : Enum.valueOf(a.getClass(), a.getSimpleName().toString());
         } else {
            return a != null ? a.getValue() : a;
         }
      }
   }

   public <T> T getValue() {
      return a.getValue("value", (Object)null);
   }

   public <T> T getValue(String a) {
      return a.getValue(a, (Object)null);
   }

   public boolean getBoolean(String a, boolean a) {
      return (Boolean)a.getValue(a, a);
   }

   public AnnotationHandle getAnnotation(String a) {
      Object a = a.getValue(a);
      if (a instanceof AnnotationMirror) {
         return of((AnnotationMirror)a);
      } else {
         if (a instanceof AnnotationValue) {
            Object a = ((AnnotationValue)a).getValue();
            if (a instanceof AnnotationMirror) {
               return of((AnnotationMirror)a);
            }
         }

         return null;
      }
   }

   public <T> List<T> getList() {
      return a.getList("value");
   }

   public <T> List<T> getList(String a) {
      List<AnnotationValue> a = (List)a.getValue(a, Collections.emptyList());
      return unwrapAnnotationValueList(a);
   }

   public List<AnnotationHandle> getAnnotationList(String a) {
      Object a = a.getValue(a, (Object)null);
      if (a == null) {
         return Collections.emptyList();
      } else if (a instanceof AnnotationMirror) {
         return ImmutableList.of(of((AnnotationMirror)a));
      } else {
         List<AnnotationValue> a = (List)a;
         List<AnnotationHandle> a = new ArrayList(a.size());
         Iterator var5 = a.iterator();

         while(var5.hasNext()) {
            AnnotationValue a = (AnnotationValue)var5.next();
            a.add(new AnnotationHandle((AnnotationMirror)a.getValue()));
         }

         return Collections.unmodifiableList(a);
      }
   }

   protected AnnotationValue getAnnotationValue(String a) {
      Iterator var2 = a.annotation.getElementValues().keySet().iterator();

      ExecutableElement a;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         a = (ExecutableElement)var2.next();
      } while(!a.getSimpleName().contentEquals(a));

      return (AnnotationValue)a.annotation.getElementValues().get(a);
   }

   protected static <T> List<T> unwrapAnnotationValueList(List<AnnotationValue> a) {
      if (a == null) {
         return Collections.emptyList();
      } else {
         List<T> a = new ArrayList(a.size());
         Iterator var2 = a.iterator();

         while(var2.hasNext()) {
            AnnotationValue a = (AnnotationValue)var2.next();
            a.add(a.getValue());
         }

         return a;
      }
   }

   protected static AnnotationMirror getAnnotation(Element a, Class<? extends Annotation> a) {
      if (a == null) {
         return null;
      } else {
         List<? extends AnnotationMirror> a = a.getAnnotationMirrors();
         if (a == null) {
            return null;
         } else {
            Iterator var3 = a.iterator();

            while(var3.hasNext()) {
               AnnotationMirror a = (AnnotationMirror)var3.next();
               Element a = a.getAnnotationType().asElement();
               if (a instanceof TypeElement) {
                  TypeElement a = (TypeElement)a;
                  if (a.getQualifiedName().contentEquals(a.getName())) {
                     return a;
                  }
               }
            }

            return null;
         }
      }
   }

   public static AnnotationHandle of(AnnotationMirror a) {
      return new AnnotationHandle(a);
   }

   public static AnnotationHandle of(Element a, Class<? extends Annotation> a) {
      return new AnnotationHandle(getAnnotation(a, a));
   }
}

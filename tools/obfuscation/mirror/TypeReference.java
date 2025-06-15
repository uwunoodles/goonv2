package org.spongepowered.tools.obfuscation.mirror;

import java.io.Serializable;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class TypeReference implements Serializable, Comparable<TypeReference> {
   private static final long serialVersionUID = 1L;
   private final String name;
   private transient TypeHandle handle;

   public TypeReference(TypeHandle a) {
      a.name = a.getName();
      a.handle = a;
   }

   public TypeReference(String a) {
      a.name = a;
   }

   public String getName() {
      return a.name;
   }

   public String getClassName() {
      return a.name.replace('/', '.');
   }

   public TypeHandle getHandle(ProcessingEnvironment a) {
      if (a.handle == null) {
         TypeElement a = a.getElementUtils().getTypeElement(a.getClassName());

         try {
            a.handle = new TypeHandle(a);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return a.handle;
   }

   public String toString() {
      return String.format("TypeReference[%s]", a.name);
   }

   public int compareTo(TypeReference a) {
      return a == null ? -1 : a.name.compareTo(a.name);
   }

   public boolean equals(Object a) {
      return a instanceof TypeReference && a.compareTo((TypeReference)a) == 0;
   }

   public int hashCode() {
      return a.name.hashCode();
   }
}

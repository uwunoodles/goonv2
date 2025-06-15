package org.spongepowered.asm.mixin.injection.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;

public final class MethodSlices {
   private final InjectionInfo info;
   private final Map<String, MethodSlice> slices = new HashMap(4);

   private MethodSlices(InjectionInfo a) {
      a.info = a;
   }

   private void add(MethodSlice a) {
      String a = a.info.getSliceId(a.getId());
      if (a.slices.containsKey(a)) {
         throw new InvalidSliceException(a.info, a + " has a duplicate id, '" + a + "' was already defined");
      } else {
         a.slices.put(a, a);
      }
   }

   public MethodSlice get(String a) {
      return (MethodSlice)a.slices.get(a);
   }

   public String toString() {
      return String.format("MethodSlices%s", a.slices.keySet());
   }

   public static MethodSlices parse(InjectionInfo a) {
      MethodSlices a = new MethodSlices(a);
      AnnotationNode a = a.getAnnotation();
      if (a != null) {
         Iterator var3 = Annotations.getValue(a, "slice", true).iterator();

         while(var3.hasNext()) {
            AnnotationNode a = (AnnotationNode)var3.next();
            MethodSlice a = MethodSlice.parse(a, (AnnotationNode)a);
            a.add(a);
         }
      }

      return a;
   }
}

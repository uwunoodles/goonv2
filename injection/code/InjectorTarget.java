package org.spongepowered.asm.mixin.injection.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;

public class InjectorTarget {
   private final ISliceContext context;
   private final Map<String, ReadOnlyInsnList> cache = new HashMap();
   private final Target target;
   private final String mergedBy;
   private final int mergedPriority;

   public InjectorTarget(ISliceContext a, Target a) {
      a.context = a;
      a.target = a;
      AnnotationNode a = Annotations.getVisible(a.method, MixinMerged.class);
      a.mergedBy = (String)Annotations.getValue(a, "mixin");
      a.mergedPriority = (Integer)Annotations.getValue(a, "priority", (int)1000);
   }

   public String toString() {
      return a.target.toString();
   }

   public Target getTarget() {
      return a.target;
   }

   public MethodNode getMethod() {
      return a.target.method;
   }

   public boolean isMerged() {
      return a.mergedBy != null;
   }

   public String getMergedBy() {
      return a.mergedBy;
   }

   public int getMergedPriority() {
      return a.mergedPriority;
   }

   public InsnList getSlice(String a) {
      ReadOnlyInsnList a = (ReadOnlyInsnList)a.cache.get(a);
      if (a == null) {
         MethodSlice a = a.context.getSlice(a);
         if (a != null) {
            a = a.getSlice(a.target.method);
         } else {
            a = new ReadOnlyInsnList(a.target.method.instructions);
         }

         a.cache.put(a, a);
      }

      return a;
   }

   public InsnList getSlice(InjectionPoint a) {
      return a.getSlice(a.getSlice());
   }

   public void dispose() {
      Iterator var1 = a.cache.values().iterator();

      while(var1.hasNext()) {
         ReadOnlyInsnList a = (ReadOnlyInsnList)var1.next();
         a.dispose();
      }

      a.cache.clear();
   }
}

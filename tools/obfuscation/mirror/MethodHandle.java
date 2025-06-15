package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.ExecutableElement;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;

public class MethodHandle extends MemberHandle<MappingMethod> {
   private final ExecutableElement element;
   private final TypeHandle ownerHandle;

   public MethodHandle(TypeHandle a, ExecutableElement a) {
      this(a, a, TypeUtils.getName(a), TypeUtils.getDescriptor(a));
   }

   public MethodHandle(TypeHandle a, String a, String a) {
      this(a, (ExecutableElement)null, a, a);
   }

   private MethodHandle(TypeHandle a, ExecutableElement a, String a, String a) {
      super(a != null ? a.getName() : null, a, a);
      a.element = a;
      a.ownerHandle = a;
   }

   public boolean isImaginary() {
      return a.element == null;
   }

   public ExecutableElement getElement() {
      return a.element;
   }

   public Visibility getVisibility() {
      return TypeUtils.getVisibility(a.element);
   }

   public MappingMethod asMapping(boolean a) {
      if (a) {
         return (MappingMethod)(a.ownerHandle != null ? new ResolvableMappingMethod(a.ownerHandle, a.getName(), a.getDesc()) : new MappingMethod(a.getOwner(), a.getName(), a.getDesc()));
      } else {
         return new MappingMethod((String)null, a.getName(), a.getDesc());
      }
   }

   public String toString() {
      String a = a.getOwner() != null ? "L" + a.getOwner() + ";" : "";
      String a = Strings.nullToEmpty(a.getName());
      String a = Strings.nullToEmpty(a.getDesc());
      return String.format("%s%s%s", a, a, a);
   }
}

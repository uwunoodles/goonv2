package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

public class FieldHandle extends MemberHandle<MappingField> {
   private final VariableElement element;
   private final boolean rawType;

   public FieldHandle(TypeElement a, VariableElement a) {
      this(TypeUtils.getInternalName(a), a);
   }

   public FieldHandle(String a, VariableElement a) {
      this(a, a, false);
   }

   public FieldHandle(TypeElement a, VariableElement a, boolean a) {
      this(TypeUtils.getInternalName(a), a, a);
   }

   public FieldHandle(String a, VariableElement a, boolean a) {
      this(a, a, a, TypeUtils.getName(a), TypeUtils.getInternalName(a));
   }

   public FieldHandle(String a, String a, String a) {
      this(a, (VariableElement)null, false, a, a);
   }

   private FieldHandle(String a, VariableElement a, boolean a, String a, String a) {
      super(a, a, a);
      a.element = a;
      a.rawType = a;
   }

   public boolean isImaginary() {
      return a.element == null;
   }

   public VariableElement getElement() {
      return a.element;
   }

   public Visibility getVisibility() {
      return TypeUtils.getVisibility(a.element);
   }

   public boolean isRawType() {
      return a.rawType;
   }

   public MappingField asMapping(boolean a) {
      return new MappingField(a ? a.getOwner() : null, a.getName(), a.getDesc());
   }

   public String toString() {
      String a = a.getOwner() != null ? "L" + a.getOwner() + ";" : "";
      String a = Strings.nullToEmpty(a.getName());
      String a = Strings.nullToEmpty(a.getDesc());
      return String.format("%s%s:%s", a, a, a);
   }
}

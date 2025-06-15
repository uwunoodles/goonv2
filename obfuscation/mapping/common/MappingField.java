package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.spongepowered.asm.obfuscation.mapping.IMapping;

public class MappingField implements IMapping<MappingField> {
   private final String owner;
   private final String name;
   private final String desc;

   public MappingField(String a, String a) {
      this(a, a, (String)null);
   }

   public MappingField(String a, String a, String a) {
      a.owner = a;
      a.name = a;
      a.desc = a;
   }

   public IMapping.Type getType() {
      return IMapping.Type.FIELD;
   }

   public String getName() {
      return a.name;
   }

   public final String getSimpleName() {
      return a.name;
   }

   public final String getOwner() {
      return a.owner;
   }

   public final String getDesc() {
      return a.desc;
   }

   public MappingField getSuper() {
      return null;
   }

   public MappingField move(String a) {
      return new MappingField(a, a.getName(), a.getDesc());
   }

   public MappingField remap(String a) {
      return new MappingField(a.getOwner(), a, a.getDesc());
   }

   public MappingField transform(String a) {
      return new MappingField(a.getOwner(), a.getName(), a);
   }

   public MappingField copy() {
      return new MappingField(a.getOwner(), a.getName(), a.getDesc());
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{a.toString()});
   }

   public boolean equals(Object a) {
      if (a == a) {
         return true;
      } else {
         return a instanceof MappingField ? Objects.equal(a.toString(), ((MappingField)a).toString()) : false;
      }
   }

   public String serialise() {
      return a.toString();
   }

   public String toString() {
      return String.format("L%s;%s:%s", a.getOwner(), a.getName(), Strings.nullToEmpty(a.getDesc()));
   }
}

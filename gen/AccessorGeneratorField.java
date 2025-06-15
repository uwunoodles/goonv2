package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;

public abstract class AccessorGeneratorField extends AccessorGenerator {
   protected final FieldNode targetField;
   protected final Type targetType;
   protected final boolean isInstanceField;

   public AccessorGeneratorField(AccessorInfo a) {
      super(a);
      a.targetField = a.getTargetField();
      a.targetType = a.getTargetFieldType();
      a.isInstanceField = (a.targetField.access & 8) == 0;
   }
}

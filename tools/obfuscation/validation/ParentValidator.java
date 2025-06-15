package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public class ParentValidator extends MixinValidator {
   public ParentValidator(IMixinAnnotationProcessor a) {
      super(a, IMixinValidator.ValidationPass.EARLY);
   }

   public boolean validate(TypeElement a, AnnotationHandle a2, Collection<TypeHandle> a3) {
      if (a.getEnclosingElement().getKind() != ElementKind.PACKAGE && !a.getModifiers().contains(Modifier.STATIC)) {
         a.error("Inner class mixin must be declared static", a);
      }

      return true;
   }
}

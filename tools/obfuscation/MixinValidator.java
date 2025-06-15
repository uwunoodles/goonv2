package org.spongepowered.tools.obfuscation;

import java.util.Collection;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public abstract class MixinValidator implements IMixinValidator {
   protected final ProcessingEnvironment processingEnv;
   protected final Messager messager;
   protected final IOptionProvider options;
   protected final IMixinValidator.ValidationPass pass;

   public MixinValidator(IMixinAnnotationProcessor a, IMixinValidator.ValidationPass a) {
      a.processingEnv = a.getProcessingEnvironment();
      a.messager = a;
      a.options = a;
      a.pass = a;
   }

   public final boolean validate(IMixinValidator.ValidationPass a, TypeElement a, AnnotationHandle a, Collection<TypeHandle> a) {
      return a != a.pass ? true : a.validate(a, a, a);
   }

   protected abstract boolean validate(TypeElement var1, AnnotationHandle var2, Collection<TypeHandle> var3);

   protected final void note(String a, Element a) {
      a.messager.printMessage(Kind.NOTE, a, a);
   }

   protected final void warning(String a, Element a) {
      a.messager.printMessage(Kind.WARNING, a, a);
   }

   protected final void error(String a, Element a) {
      a.messager.printMessage(Kind.ERROR, a, a);
   }

   protected final Collection<TypeMirror> getMixinsTargeting(TypeMirror a) {
      return AnnotatedMixins.getMixinsForEnvironment(a.processingEnv).getMixinsTargeting(a);
   }
}

package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.tools.obfuscation.interfaces.IJavadocProvider;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandleSimulated;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
import org.spongepowered.tools.obfuscation.validation.ParentValidator;
import org.spongepowered.tools.obfuscation.validation.TargetValidator;

final class AnnotatedMixins implements IMixinAnnotationProcessor, ITokenProvider, ITypeHandleProvider, IJavadocProvider {
   private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
   private static Map<ProcessingEnvironment, AnnotatedMixins> instances = new HashMap();
   private final IMixinAnnotationProcessor.CompilerEnvironment env;
   private final ProcessingEnvironment processingEnv;
   private final Map<String, AnnotatedMixin> mixins = new HashMap();
   private final List<AnnotatedMixin> mixinsForPass = new ArrayList();
   private final IObfuscationManager obf;
   private final List<IMixinValidator> validators;
   private final Map<String, Integer> tokenCache = new HashMap();
   private final TargetMap targets;
   private Properties properties;

   private AnnotatedMixins(ProcessingEnvironment a) {
      a.env = a.detectEnvironment(a);
      a.processingEnv = a;
      a.printMessage(Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.7.11");
      a.targets = a.initTargetMap();
      a.obf = new ObfuscationManager(a);
      a.obf.init();
      a.validators = ImmutableList.of(new ParentValidator(a), new TargetValidator(a));
      a.initTokenCache(a.getOption("tokens"));
   }

   protected TargetMap initTargetMap() {
      TargetMap a = TargetMap.create(System.getProperty("mixin.target.mapid"));
      System.setProperty("mixin.target.mapid", a.getSessionId());
      String a = a.getOption("dependencyTargetsFile");
      if (a != null) {
         try {
            a.readImports(new File(a));
         } catch (IOException var4) {
            a.printMessage(Kind.WARNING, "Could not read from specified imports file: " + a);
         }
      }

      return a;
   }

   private void initTokenCache(String a) {
      if (a != null) {
         Pattern a = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
         String[] a = a.replaceAll("\\s", "").toUpperCase().split("[;,]");
         String[] var4 = a;
         int var5 = a.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String a = var4[var6];
            Matcher a = a.matcher(a);
            if (a.matches()) {
               a.tokenCache.put(a.group(1), Integer.parseInt(a.group(2)));
            }
         }
      }

   }

   public ITypeHandleProvider getTypeProvider() {
      return a;
   }

   public ITokenProvider getTokenProvider() {
      return a;
   }

   public IObfuscationManager getObfuscationManager() {
      return a.obf;
   }

   public IJavadocProvider getJavadocProvider() {
      return a;
   }

   public ProcessingEnvironment getProcessingEnvironment() {
      return a.processingEnv;
   }

   public IMixinAnnotationProcessor.CompilerEnvironment getCompilerEnvironment() {
      return a.env;
   }

   public Integer getToken(String a) {
      if (a.tokenCache.containsKey(a)) {
         return (Integer)a.tokenCache.get(a);
      } else {
         String a = a.getOption(a);
         Integer a = null;

         try {
            a = Integer.parseInt(a);
         } catch (Exception var5) {
         }

         a.tokenCache.put(a, a);
         return a;
      }
   }

   public String getOption(String a) {
      if (a == null) {
         return null;
      } else {
         String a = (String)a.processingEnv.getOptions().get(a);
         return a != null ? a : a.getProperties().getProperty(a);
      }
   }

   public Properties getProperties() {
      if (a.properties == null) {
         a.properties = new Properties();

         try {
            Filer a = a.processingEnv.getFiler();
            FileObject a = a.getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
            if (a != null) {
               InputStream a = a.openInputStream();
               a.properties.load(a);
               a.close();
            }
         } catch (Exception var4) {
         }
      }

      return a.properties;
   }

   private IMixinAnnotationProcessor.CompilerEnvironment detectEnvironment(ProcessingEnvironment a) {
      return a.getClass().getName().contains("jdt") ? IMixinAnnotationProcessor.CompilerEnvironment.JDT : IMixinAnnotationProcessor.CompilerEnvironment.JAVAC;
   }

   public void writeMappings() {
      a.obf.writeMappings();
   }

   public void writeReferences() {
      a.obf.writeReferences();
   }

   public void clear() {
      a.mixins.clear();
   }

   public void registerMixin(TypeElement a) {
      String a = a.getQualifiedName().toString();
      if (!a.mixins.containsKey(a)) {
         AnnotatedMixin a = new AnnotatedMixin(a, a);
         a.targets.registerTargets(a);
         a.runValidators(IMixinValidator.ValidationPass.EARLY, a.validators);
         a.mixins.put(a, a);
         a.mixinsForPass.add(a);
      }

   }

   public AnnotatedMixin getMixin(TypeElement a) {
      return a.getMixin(a.getQualifiedName().toString());
   }

   public AnnotatedMixin getMixin(String a) {
      return (AnnotatedMixin)a.mixins.get(a);
   }

   public Collection<TypeMirror> getMixinsTargeting(TypeMirror a) {
      return a.getMixinsTargeting((TypeElement)((DeclaredType)a).asElement());
   }

   public Collection<TypeMirror> getMixinsTargeting(TypeElement a) {
      List<TypeMirror> a = new ArrayList();
      Iterator var3 = a.targets.getMixinsTargeting(a).iterator();

      while(var3.hasNext()) {
         TypeReference a = (TypeReference)var3.next();
         TypeHandle a = a.getHandle(a.processingEnv);
         if (a != null) {
            a.add(a.getType());
         }
      }

      return a;
   }

   public void registerAccessor(TypeElement a, ExecutableElement a) {
      AnnotatedMixin a = a.getMixin(a);
      if (a == null) {
         a.printMessage(Kind.ERROR, "Found @Accessor annotation on a non-mixin method", a);
      } else {
         AnnotationHandle a = AnnotationHandle.of(a, Accessor.class);
         a.registerAccessor(a, a, a.shouldRemap(a, a));
      }
   }

   public void registerInvoker(TypeElement a, ExecutableElement a) {
      AnnotatedMixin a = a.getMixin(a);
      if (a == null) {
         a.printMessage(Kind.ERROR, "Found @Accessor annotation on a non-mixin method", a);
      } else {
         AnnotationHandle a = AnnotationHandle.of(a, Invoker.class);
         a.registerInvoker(a, a, a.shouldRemap(a, a));
      }
   }

   public void registerOverwrite(TypeElement a, ExecutableElement a) {
      AnnotatedMixin a = a.getMixin(a);
      if (a == null) {
         a.printMessage(Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", a);
      } else {
         AnnotationHandle a = AnnotationHandle.of(a, Overwrite.class);
         a.registerOverwrite(a, a, a.shouldRemap(a, a));
      }
   }

   public void registerShadow(TypeElement a, VariableElement a, AnnotationHandle a) {
      AnnotatedMixin a = a.getMixin(a);
      if (a == null) {
         a.printMessage(Kind.ERROR, "Found @Shadow annotation on a non-mixin field", a);
      } else {
         a.registerShadow(a, a, a.shouldRemap(a, a));
      }
   }

   public void registerShadow(TypeElement a, ExecutableElement a, AnnotationHandle a) {
      AnnotatedMixin a = a.getMixin(a);
      if (a == null) {
         a.printMessage(Kind.ERROR, "Found @Shadow annotation on a non-mixin method", a);
      } else {
         a.registerShadow(a, a, a.shouldRemap(a, a));
      }
   }

   public void registerInjector(TypeElement a, ExecutableElement a, AnnotationHandle a) {
      AnnotatedMixin a = a.getMixin(a);
      if (a == null) {
         a.printMessage(Kind.ERROR, "Found " + a + " annotation on a non-mixin method", a);
      } else {
         InjectorRemap a = new InjectorRemap(a.shouldRemap(a, a));
         a.registerInjector(a, a, a);
         a.dispatchPendingMessages(a);
      }
   }

   public void registerSoftImplements(TypeElement a, AnnotationHandle a) {
      AnnotatedMixin a = a.getMixin(a);
      if (a == null) {
         a.printMessage(Kind.ERROR, "Found @Implements annotation on a non-mixin class");
      } else {
         a.registerSoftImplements(a);
      }
   }

   public void onPassStarted() {
      a.mixinsForPass.clear();
   }

   public void onPassCompleted(RoundEnvironment a) {
      if (!"true".equalsIgnoreCase(a.getOption("disableTargetExport"))) {
         a.targets.write(true);
      }

      Iterator var2 = ((Collection)(a.processingOver() ? a.mixins.values() : a.mixinsForPass)).iterator();

      while(var2.hasNext()) {
         AnnotatedMixin a = (AnnotatedMixin)var2.next();
         a.runValidators(a.processingOver() ? IMixinValidator.ValidationPass.FINAL : IMixinValidator.ValidationPass.LATE, a.validators);
      }

   }

   private boolean shouldRemap(AnnotatedMixin a, AnnotationHandle a) {
      return a.getBoolean("remap", a.remap());
   }

   public void printMessage(Kind a, CharSequence a) {
      if (a.env == IMixinAnnotationProcessor.CompilerEnvironment.JAVAC || a != Kind.NOTE) {
         a.processingEnv.getMessager().printMessage(a, a);
      }

   }

   public void printMessage(Kind a, CharSequence a, Element a) {
      a.processingEnv.getMessager().printMessage(a, a, a);
   }

   public void printMessage(Kind a, CharSequence a, Element a, AnnotationMirror a) {
      a.processingEnv.getMessager().printMessage(a, a, a, a);
   }

   public void printMessage(Kind a, CharSequence a, Element a, AnnotationMirror a, AnnotationValue a) {
      a.processingEnv.getMessager().printMessage(a, a, a, a, a);
   }

   public TypeHandle getTypeHandle(String a) {
      a = a.replace('/', '.');
      Elements a = a.processingEnv.getElementUtils();
      TypeElement a = a.getTypeElement(a);
      if (a != null) {
         try {
            return new TypeHandle(a);
         } catch (NullPointerException var7) {
         }
      }

      int a = a.lastIndexOf(46);
      if (a > -1) {
         String a = a.substring(0, a);
         PackageElement a = a.getPackageElement(a);
         if (a != null) {
            return new TypeHandle(a, a);
         }
      }

      return null;
   }

   public TypeHandle getSimulatedHandle(String a, TypeMirror a) {
      a = a.replace('/', '.');
      int a = a.lastIndexOf(46);
      if (a > -1) {
         String a = a.substring(0, a);
         PackageElement a = a.processingEnv.getElementUtils().getPackageElement(a);
         if (a != null) {
            return new TypeHandleSimulated(a, a, a);
         }
      }

      return new TypeHandleSimulated(a, a);
   }

   public String getJavadoc(Element a) {
      Elements a = a.processingEnv.getElementUtils();
      return a.getDocComment(a);
   }

   public static AnnotatedMixins getMixinsForEnvironment(ProcessingEnvironment a) {
      AnnotatedMixins a = (AnnotatedMixins)instances.get(a);
      if (a == null) {
         a = new AnnotatedMixins(a);
         instances.put(a, a);
      }

      return a;
   }
}

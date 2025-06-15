package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.ListIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

class MixinPreProcessorStandard {
   private static final Logger logger = LogManager.getLogger("mixin");
   protected final MixinInfo mixin;
   protected final MixinInfo.MixinClassNode classNode;
   protected final MixinEnvironment env;
   protected final Profiler profiler = MixinEnvironment.getProfiler();
   private final boolean verboseLogging;
   private final boolean strictUnique;
   private boolean prepared;
   private boolean attached;

   MixinPreProcessorStandard(MixinInfo a, MixinInfo.MixinClassNode a) {
      a.mixin = a;
      a.classNode = a;
      a.env = a.getParent().getEnvironment();
      a.verboseLogging = a.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
      a.strictUnique = a.env.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
   }

   final MixinPreProcessorStandard prepare() {
      if (a.prepared) {
         return a;
      } else {
         a.prepared = true;
         Profiler.Section a = a.profiler.begin("prepare");
         Iterator var2 = a.classNode.mixinMethods.iterator();

         while(var2.hasNext()) {
            MixinInfo.MixinMethodNode a = (MixinInfo.MixinMethodNode)var2.next();
            ClassInfo.Method a = a.mixin.getClassInfo().findMethod((MethodNode)a);
            a.prepareMethod(a, a);
         }

         var2 = a.classNode.fields.iterator();

         while(var2.hasNext()) {
            FieldNode a = (FieldNode)var2.next();
            a.prepareField(a);
         }

         a.end();
         return a;
      }
   }

   protected void prepareMethod(MixinInfo.MixinMethodNode a, ClassInfo.Method a) {
      a.prepareShadow(a, a);
      a.prepareSoftImplements(a, a);
   }

   protected void prepareShadow(MixinInfo.MixinMethodNode a, ClassInfo.Method a) {
      AnnotationNode a = Annotations.getVisible((MethodNode)a, Shadow.class);
      if (a != null) {
         String a = (String)Annotations.getValue(a, "prefix", Shadow.class);
         if (a.name.startsWith(a)) {
            Annotations.setVisible((MethodNode)a, MixinRenamed.class, "originalName", a.name);
            String a = a.name.substring(a.length());
            a.name = a.renameTo(a);
         }

      }
   }

   protected void prepareSoftImplements(MixinInfo.MixinMethodNode a, ClassInfo.Method a) {
      Iterator var3 = a.mixin.getSoftImplements().iterator();

      while(var3.hasNext()) {
         InterfaceInfo a = (InterfaceInfo)var3.next();
         if (a.renameMethod(a)) {
            a.renameTo(a.name);
         }
      }

   }

   protected void prepareField(FieldNode a1) {
   }

   final MixinPreProcessorStandard conform(TargetClassContext a) {
      return a.conform(a.getClassInfo());
   }

   final MixinPreProcessorStandard conform(ClassInfo a) {
      Profiler.Section a = a.profiler.begin("conform");
      Iterator var3 = a.classNode.mixinMethods.iterator();

      while(var3.hasNext()) {
         MixinInfo.MixinMethodNode a = (MixinInfo.MixinMethodNode)var3.next();
         if (a.isInjector()) {
            ClassInfo.Method a = a.mixin.getClassInfo().findMethod((MethodNode)a, 10);
            a.conformInjector(a, a, a);
         }
      }

      a.end();
      return a;
   }

   private void conformInjector(ClassInfo a, MixinInfo.MixinMethodNode a, ClassInfo.Method a) {
      MethodMapper a = a.getMethodMapper();
      a.remapHandlerMethod(a.mixin, a, a);
   }

   MixinTargetContext createContextFor(TargetClassContext a) {
      MixinTargetContext a = new MixinTargetContext(a.mixin, a.classNode, a);
      a.conform(a);
      a.attach(a);
      return a;
   }

   final MixinPreProcessorStandard attach(MixinTargetContext a) {
      if (a.attached) {
         throw new IllegalStateException("Preprocessor was already attached");
      } else {
         a.attached = true;
         Profiler.Section a = a.profiler.begin("attach");
         Profiler.Section a = a.profiler.begin("methods");
         a.attachMethods(a);
         a = a.next("fields");
         a.attachFields(a);
         a = a.next("transform");
         a.transform(a);
         a.end();
         a.end();
         return a;
      }
   }

   protected void attachMethods(MixinTargetContext a) {
      Iterator a = a.classNode.mixinMethods.iterator();

      while(a.hasNext()) {
         MixinInfo.MixinMethodNode a = (MixinInfo.MixinMethodNode)a.next();
         if (!a.validateMethod(a, a)) {
            a.remove();
         } else if (a.attachInjectorMethod(a, a)) {
            a.addMixinMethod(a);
         } else if (a.attachAccessorMethod(a, a)) {
            a.remove();
         } else if (a.attachShadowMethod(a, a)) {
            a.addShadowMethod(a);
            a.remove();
         } else if (a.attachOverwriteMethod(a, a)) {
            a.addMixinMethod(a);
         } else if (a.attachUniqueMethod(a, a)) {
            a.remove();
         } else {
            a.attachMethod(a, a);
            a.addMixinMethod(a);
         }
      }

   }

   protected boolean validateMethod(MixinTargetContext a1, MixinInfo.MixinMethodNode a2) {
      return true;
   }

   protected boolean attachInjectorMethod(MixinTargetContext a1, MixinInfo.MixinMethodNode a) {
      return a.isInjector();
   }

   protected boolean attachAccessorMethod(MixinTargetContext a, MixinInfo.MixinMethodNode a) {
      return a.attachAccessorMethod(a, a, MixinPreProcessorStandard.SpecialMethod.ACCESSOR) || a.attachAccessorMethod(a, a, MixinPreProcessorStandard.SpecialMethod.INVOKER);
   }

   protected boolean attachAccessorMethod(MixinTargetContext a, MixinInfo.MixinMethodNode a, MixinPreProcessorStandard.SpecialMethod a) {
      AnnotationNode a = a.getVisibleAnnotation(a.annotation);
      if (a == null) {
         return false;
      } else {
         String a = a + " method " + a.name;
         ClassInfo.Method a = a.getSpecialMethod(a, a);
         if (MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8) && a.isStatic()) {
            if (a.mixin.getTargets().size() > 1) {
               throw new InvalidAccessorException(a, a + " in multi-target mixin is invalid. Mixin must have exactly 1 target.");
            }

            String a = a.getUniqueName(a, true);
            logger.log(a.mixin.getLoggingLevel(), "Renaming @Unique method {}{} to {} in {}", new Object[]{a.name, a.desc, a, a.mixin});
            a.name = a.renameTo(a);
         } else {
            if (!a.isAbstract()) {
               throw new InvalidAccessorException(a, a + " is not abstract");
            }

            if (a.isStatic()) {
               throw new InvalidAccessorException(a, a + " cannot be static");
            }
         }

         a.addAccessorMethod(a, a.annotation);
         return true;
      }
   }

   protected boolean attachShadowMethod(MixinTargetContext a, MixinInfo.MixinMethodNode a) {
      return a.attachSpecialMethod(a, a, MixinPreProcessorStandard.SpecialMethod.SHADOW);
   }

   protected boolean attachOverwriteMethod(MixinTargetContext a, MixinInfo.MixinMethodNode a) {
      return a.attachSpecialMethod(a, a, MixinPreProcessorStandard.SpecialMethod.OVERWRITE);
   }

   protected boolean attachSpecialMethod(MixinTargetContext a, MixinInfo.MixinMethodNode a, MixinPreProcessorStandard.SpecialMethod a) {
      AnnotationNode a = a.getVisibleAnnotation(a.annotation);
      if (a == null) {
         return false;
      } else {
         if (a.isOverwrite) {
            a.checkMixinNotUnique(a, a);
         }

         ClassInfo.Method a = a.getSpecialMethod(a, a);
         MethodNode a = a.findMethod(a, a);
         if (a == null) {
            if (a.isOverwrite) {
               return false;
            }

            a = a.findRemappedMethod(a);
            if (a == null) {
               throw new InvalidMixinException(a.mixin, String.format("%s method %s in %s was not located in the target class %s. %s%s", a, a.name, a.mixin, a.getTarget(), a.getReferenceMapper().getStatus(), getDynamicInfo((MethodNode)a)));
            }

            a.name = a.renameTo(a.name);
         }

         if ("<init>".equals(a.name)) {
            throw new InvalidMixinException(a.mixin, String.format("Nice try! %s in %s cannot alias a constructor", a.name, a.mixin));
         } else if (!Bytecode.compareFlags((MethodNode)a, (MethodNode)a, 8)) {
            throw new InvalidMixinException(a.mixin, String.format("STATIC modifier of %s method %s in %s does not match the target", a, a.name, a.mixin));
         } else {
            a.conformVisibility(a, a, a, a);
            if (!a.name.equals(a.name)) {
               if (a.isOverwrite && (a.access & 2) == 0) {
                  throw new InvalidMixinException(a.mixin, "Non-private method cannot be aliased. Found " + a.name);
               }

               a.name = a.renameTo(a.name);
            }

            return true;
         }
      }
   }

   private void conformVisibility(MixinTargetContext a, MixinInfo.MixinMethodNode a, MixinPreProcessorStandard.SpecialMethod a, MethodNode a) {
      Bytecode.Visibility a = Bytecode.getVisibility(a);
      Bytecode.Visibility a = Bytecode.getVisibility((MethodNode)a);
      if (a.ordinal() >= a.ordinal()) {
         if (a == Bytecode.Visibility.PRIVATE && a.ordinal() > Bytecode.Visibility.PRIVATE.ordinal()) {
            a.getTarget().addUpgradedMethod(a);
         }

      } else {
         String a = String.format("%s %s method %s in %s cannot reduce visibiliy of %s target method", a, a, a.name, a.mixin, a);
         if (a.isOverwrite && !a.mixin.getParent().conformOverwriteVisibility()) {
            throw new InvalidMixinException(a.mixin, a);
         } else {
            if (a == Bytecode.Visibility.PRIVATE) {
               if (a.isOverwrite) {
                  logger.warn("Static binding violation: {}, visibility will be upgraded.", new Object[]{a});
               }

               a.addUpgradedMethod(a);
               Bytecode.setVisibility((MethodNode)a, a);
            }

         }
      }
   }

   protected ClassInfo.Method getSpecialMethod(MixinInfo.MixinMethodNode a, MixinPreProcessorStandard.SpecialMethod a) {
      ClassInfo.Method a = a.mixin.getClassInfo().findMethod((MethodNode)a, 10);
      a.checkMethodNotUnique(a, a);
      return a;
   }

   protected void checkMethodNotUnique(ClassInfo.Method a, MixinPreProcessorStandard.SpecialMethod a) {
      if (a.isUnique()) {
         throw new InvalidMixinException(a.mixin, String.format("%s method %s in %s cannot be @Unique", a, a.getName(), a.mixin));
      }
   }

   protected void checkMixinNotUnique(MixinInfo.MixinMethodNode a, MixinPreProcessorStandard.SpecialMethod a) {
      if (a.mixin.isUnique()) {
         throw new InvalidMixinException(a.mixin, String.format("%s method %s found in a @Unique mixin %s", a, a.name, a.mixin));
      }
   }

   protected boolean attachUniqueMethod(MixinTargetContext a, MixinInfo.MixinMethodNode a) {
      ClassInfo.Method a = a.mixin.getClassInfo().findMethod((MethodNode)a, 10);
      if (a != null && (a.isUnique() || a.mixin.isUnique() || a.isSynthetic())) {
         if (a.isSynthetic()) {
            a.transformDescriptor((MethodNode)a);
            a.remapTo(a.desc);
         }

         MethodNode a = a.findMethod(a, (AnnotationNode)null);
         if (a == null) {
            return false;
         } else {
            String a = a.isSynthetic() ? "synthetic" : "@Unique";
            if (Bytecode.getVisibility((MethodNode)a).ordinal() < Bytecode.Visibility.PUBLIC.ordinal()) {
               String a = a.getUniqueName(a, false);
               logger.log(a.mixin.getLoggingLevel(), "Renaming {} method {}{} to {} in {}", new Object[]{a, a.name, a.desc, a, a.mixin});
               a.name = a.renameTo(a);
               return false;
            } else if (a.strictUnique) {
               throw new InvalidMixinException(a.mixin, String.format("Method conflict, %s method %s in %s cannot overwrite %s%s in %s", a, a.name, a.mixin, a.name, a.desc, a.getTarget()));
            } else {
               AnnotationNode a = Annotations.getVisible((MethodNode)a, Unique.class);
               if (a != null && (Boolean)Annotations.getValue(a, "silent", (Object)Boolean.FALSE)) {
                  a.addMixinMethod(a);
                  return true;
               } else if (Bytecode.hasFlag((MethodNode)a, 64)) {
                  try {
                     Bytecode.compareBridgeMethods(a, a);
                     logger.debug("Discarding sythetic bridge method {} in {} because existing method in {} is compatible", new Object[]{a, a.name, a.mixin, a.getTarget()});
                     return true;
                  } catch (SyntheticBridgeException var8) {
                     if (a.verboseLogging || a.env.getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                        var8.printAnalysis(a, a, a);
                     }

                     throw new InvalidMixinException(a.mixin, var8.getMessage());
                  }
               } else {
                  logger.warn("Discarding {} public method {} in {} because it already exists in {}", new Object[]{a, a.name, a.mixin, a.getTarget()});
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   protected void attachMethod(MixinTargetContext a, MixinInfo.MixinMethodNode a) {
      ClassInfo.Method a = a.mixin.getClassInfo().findMethod((MethodNode)a);
      if (a != null) {
         ClassInfo.Method a = a.mixin.getClassInfo().findMethodInHierarchy((MethodNode)a, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
         if (a != null && a.isRenamed()) {
            a.name = a.renameTo(a.getName());
         }

         MethodNode a = a.findMethod(a, (AnnotationNode)null);
         if (a != null) {
            a.conformVisibility(a, a, MixinPreProcessorStandard.SpecialMethod.MERGE, a);
         }

      }
   }

   protected void attachFields(MixinTargetContext a) {
      Iterator a = a.classNode.fields.iterator();

      while(true) {
         while(a.hasNext()) {
            FieldNode a = (FieldNode)a.next();
            AnnotationNode a = Annotations.getVisible(a, Shadow.class);
            boolean a = a != null;
            if (!a.validateField(a, a, a)) {
               a.remove();
            } else {
               ClassInfo.Field a = a.mixin.getClassInfo().findField(a);
               a.transformDescriptor(a);
               a.remapTo(a.desc);
               if (a.isUnique() && a) {
                  throw new InvalidMixinException(a.mixin, String.format("@Shadow field %s cannot be @Unique", a.name));
               }

               FieldNode a = a.findField(a, a);
               if (a == null) {
                  if (a == null) {
                     continue;
                  }

                  a = a.findRemappedField(a);
                  if (a == null) {
                     throw new InvalidMixinException(a.mixin, String.format("Shadow field %s was not located in the target class %s. %s%s", a.name, a.getTarget(), a.getReferenceMapper().getStatus(), getDynamicInfo(a)));
                  }

                  a.name = a.renameTo(a.name);
               }

               if (!Bytecode.compareFlags((FieldNode)a, (FieldNode)a, 8)) {
                  throw new InvalidMixinException(a.mixin, String.format("STATIC modifier of @Shadow field %s in %s does not match the target", a.name, a.mixin));
               }

               if (a.isUnique()) {
                  if ((a.access & 6) != 0) {
                     String a = a.getUniqueName(a);
                     logger.log(a.mixin.getLoggingLevel(), "Renaming @Unique field {}{} to {} in {}", new Object[]{a.name, a.desc, a, a.mixin});
                     a.name = a.renameTo(a);
                  } else {
                     if (a.strictUnique) {
                        throw new InvalidMixinException(a.mixin, String.format("Field conflict, @Unique field %s in %s cannot overwrite %s%s in %s", a.name, a.mixin, a.name, a.desc, a.getTarget()));
                     }

                     logger.warn("Discarding @Unique public field {} in {} because it already exists in {}. Note that declared FIELD INITIALISERS will NOT be removed!", new Object[]{a.name, a.mixin, a.getTarget()});
                     a.remove();
                  }
               } else {
                  if (!a.desc.equals(a.desc)) {
                     throw new InvalidMixinException(a.mixin, String.format("The field %s in the target class has a conflicting signature", a.name));
                  }

                  if (!a.name.equals(a.name)) {
                     if ((a.access & 2) == 0 && (a.access & 4096) == 0) {
                        throw new InvalidMixinException(a.mixin, "Non-private field cannot be aliased. Found " + a.name);
                     }

                     a.name = a.renameTo(a.name);
                  }

                  a.remove();
                  if (a) {
                     boolean a = a.isDecoratedFinal();
                     if (a.verboseLogging && Bytecode.hasFlag((FieldNode)a, 16) != a) {
                        String a = a ? "@Shadow field {}::{} is decorated with @Final but target is not final" : "@Shadow target {}::{} is final but shadow is not decorated with @Final";
                        logger.warn(a, new Object[]{a.mixin, a.name});
                     }

                     a.addShadowField(a, a);
                  }
               }
            }
         }

         return;
      }
   }

   protected boolean validateField(MixinTargetContext a, FieldNode a, AnnotationNode a) {
      if (Bytecode.hasFlag((FieldNode)a, 8) && !Bytecode.hasFlag((FieldNode)a, 2) && !Bytecode.hasFlag((FieldNode)a, 4096) && a == null) {
         throw new InvalidMixinException(a, String.format("Mixin %s contains non-private static field %s:%s", a, a.name, a.desc));
      } else {
         String a = (String)Annotations.getValue(a, "prefix", Shadow.class);
         if (a.name.startsWith(a)) {
            throw new InvalidMixinException(a, String.format("@Shadow field %s.%s has a shadow prefix. This is not allowed.", a, a.name));
         } else if ("super$".equals(a.name)) {
            if (a.access != 2) {
               throw new InvalidMixinException(a.mixin, String.format("Imaginary super field %s.%s must be private and non-final", a, a.name));
            } else if (!a.desc.equals("L" + a.mixin.getClassRef() + ";")) {
               throw new InvalidMixinException(a.mixin, String.format("Imaginary super field %s.%s must have the same type as the parent mixin (%s)", a, a.name, a.mixin.getClassName()));
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   protected void transform(MixinTargetContext a1) {
      Iterator var2 = a.classNode.methods.iterator();

      while(var2.hasNext()) {
         MethodNode a = (MethodNode)var2.next();
         ListIterator a = a.instructions.iterator();

         while(a.hasNext()) {
            AbstractInsnNode a = (AbstractInsnNode)a.next();
            if (a instanceof MethodInsnNode) {
               a.transformMethod((MethodInsnNode)a);
            } else if (a instanceof FieldInsnNode) {
               a.transformField((FieldInsnNode)a);
            }
         }
      }

   }

   protected void transformMethod(MethodInsnNode a) {
      Profiler.Section a = a.profiler.begin("meta");
      ClassInfo a = ClassInfo.forName(a.owner);
      if (a == null) {
         throw new RuntimeException(new ClassNotFoundException(a.owner.replace('/', '.')));
      } else {
         ClassInfo.Method a = a.findMethodInHierarchy((MethodInsnNode)a, ClassInfo.SearchType.ALL_CLASSES, 2);
         a.end();
         if (a != null && a.isRenamed()) {
            a.name = a.getName();
         }

      }
   }

   protected void transformField(FieldInsnNode a) {
      Profiler.Section a = a.profiler.begin("meta");
      ClassInfo a = ClassInfo.forName(a.owner);
      if (a == null) {
         throw new RuntimeException(new ClassNotFoundException(a.owner.replace('/', '.')));
      } else {
         ClassInfo.Field a = a.findField(a, 2);
         a.end();
         if (a != null && a.isRenamed()) {
            a.name = a.getName();
         }

      }
   }

   protected static String getDynamicInfo(MethodNode a) {
      return getDynamicInfo("Method", Annotations.getInvisible(a, Dynamic.class));
   }

   protected static String getDynamicInfo(FieldNode a) {
      return getDynamicInfo("Field", Annotations.getInvisible(a, Dynamic.class));
   }

   private static String getDynamicInfo(String a, AnnotationNode a) {
      String a = Strings.nullToEmpty((String)Annotations.getValue(a));
      Type a = (Type)Annotations.getValue(a, "mixin");
      if (a != null) {
         a = String.format("{%s} %s", a.getClassName(), a).trim();
      }

      return a.length() > 0 ? String.format(" %s is @Dynamic(%s)", a, a) : "";
   }

   static enum SpecialMethod {
      MERGE(true),
      OVERWRITE(true, Overwrite.class),
      SHADOW(false, Shadow.class),
      ACCESSOR(false, Accessor.class),
      INVOKER(false, Invoker.class);

      final boolean isOverwrite;
      final Class<? extends Annotation> annotation;
      final String description;

      private SpecialMethod(boolean a, Class<? extends Annotation> a) {
         a.isOverwrite = a;
         a.annotation = a;
         a.description = "@" + Bytecode.getSimpleName(a);
      }

      private SpecialMethod(boolean a) {
         a.isOverwrite = a;
         a.annotation = null;
         a.description = "overwrite";
      }

      public String toString() {
         return a.description;
      }
   }
}

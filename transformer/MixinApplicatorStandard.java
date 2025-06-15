package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

class MixinApplicatorStandard {
   protected static final List<Class<? extends Annotation>> CONSTRAINED_ANNOTATIONS = ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
   protected static final int[] INITIALISER_OPCODE_BLACKLIST = new int[]{177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86};
   protected final Logger logger = LogManager.getLogger("mixin");
   protected final TargetClassContext context;
   protected final String targetName;
   protected final ClassNode targetClass;
   protected final Profiler profiler = MixinEnvironment.getProfiler();
   protected final boolean mergeSignatures;

   MixinApplicatorStandard(TargetClassContext a) {
      a.context = a;
      a.targetName = a.getClassName();
      a.targetClass = a.getClassNode();
      ExtensionClassExporter a = (ExtensionClassExporter)a.getExtensions().getExtension(ExtensionClassExporter.class);
      a.mergeSignatures = a.isDecompilerActive() && MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES);
   }

   void apply(SortedSet<MixinInfo> a) {
      List<MixinTargetContext> a = new ArrayList();
      Iterator a = a.iterator();

      while(a.hasNext()) {
         MixinInfo a = (MixinInfo)a.next();
         a.logger.log(a.getLoggingLevel(), "Mixing {} from {} into {}", new Object[]{a.getName(), a.getParent(), a.targetName});
         a.add(a.createContextFor(a.context));
      }

      a = null;

      try {
         Iterator var13 = a.iterator();

         label46:
         while(true) {
            MixinTargetContext a;
            if (!var13.hasNext()) {
               MixinApplicatorStandard.ApplicatorPass[] var14 = MixinApplicatorStandard.ApplicatorPass.values();
               int var15 = var14.length;

               for(int var6 = 0; var6 < var15; ++var6) {
                  MixinApplicatorStandard.ApplicatorPass a = var14[var6];
                  Profiler.Section a = a.profiler.begin("pass", a.name().toLowerCase());
                  Iterator var9 = a.iterator();

                  while(var9.hasNext()) {
                     MixinTargetContext a = (MixinTargetContext)var9.next();
                     a.applyMixin(a, a);
                  }

                  a.end();
               }

               var13 = a.iterator();

               while(true) {
                  if (!var13.hasNext()) {
                     break label46;
                  }

                  a = (MixinTargetContext)var13.next();
                  a.postApply(a.targetName, a.targetClass);
               }
            }

            a = (MixinTargetContext)var13.next();
            a.preApply(a.targetName, a.targetClass);
         }
      } catch (InvalidMixinException var11) {
         throw var11;
      } catch (Exception var12) {
         throw new InvalidMixinException(a, "Unexpecteded " + var12.getClass().getSimpleName() + " whilst applying the mixin class: " + var12.getMessage(), var12);
      }

      a.applySourceMap(a.context);
      a.context.processDebugTasks();
   }

   protected final void applyMixin(MixinTargetContext a, MixinApplicatorStandard.ApplicatorPass a) {
      switch(a) {
      case MAIN:
         a.applySignature(a);
         a.applyInterfaces(a);
         a.applyAttributes(a);
         a.applyAnnotations(a);
         a.applyFields(a);
         a.applyMethods(a);
         a.applyInitialisers(a);
         break;
      case PREINJECT:
         a.prepareInjections(a);
         break;
      case INJECT:
         a.applyAccessors(a);
         a.applyInjections(a);
         break;
      default:
         throw new IllegalStateException("Invalid pass specified " + a);
      }

   }

   protected void applySignature(MixinTargetContext a) {
      if (a.mergeSignatures) {
         a.context.mergeSignature(a.getSignature());
      }

   }

   protected void applyInterfaces(MixinTargetContext a) {
      Iterator var2 = a.getInterfaces().iterator();

      while(var2.hasNext()) {
         String a = (String)var2.next();
         if (!a.targetClass.interfaces.contains(a)) {
            a.targetClass.interfaces.add(a);
            a.getTargetClassInfo().addInterface(a);
         }
      }

   }

   protected void applyAttributes(MixinTargetContext a) {
      if (a.shouldSetSourceFile()) {
         a.targetClass.sourceFile = a.getSourceFile();
      }

      a.targetClass.version = Math.max(a.targetClass.version, a.getMinRequiredClassVersion());
   }

   protected void applyAnnotations(MixinTargetContext a) {
      ClassNode a = a.getClassNode();
      Bytecode.mergeAnnotations(a, a.targetClass);
   }

   protected void applyFields(MixinTargetContext a) {
      a.mergeShadowFields(a);
      a.mergeNewFields(a);
   }

   protected void mergeShadowFields(MixinTargetContext a) {
      Iterator var2 = a.getShadowFields().iterator();

      while(var2.hasNext()) {
         Entry<FieldNode, ClassInfo.Field> a = (Entry)var2.next();
         FieldNode a = (FieldNode)a.getKey();
         FieldNode a = a.findTargetField(a);
         if (a != null) {
            Bytecode.mergeAnnotations(a, a);
            if (((ClassInfo.Field)a.getValue()).isDecoratedMutable() && !Bytecode.hasFlag((FieldNode)a, 2)) {
               a.access &= -17;
            }
         }
      }

   }

   protected void mergeNewFields(MixinTargetContext a) {
      Iterator var2 = a.getFields().iterator();

      while(var2.hasNext()) {
         FieldNode a = (FieldNode)var2.next();
         FieldNode a = a.findTargetField(a);
         if (a == null) {
            a.targetClass.fields.add(a);
            if (a.signature != null) {
               if (a.mergeSignatures) {
                  SignatureVisitor a = a.getSignature().getRemapper();
                  (new SignatureReader(a.signature)).accept(a);
                  a.signature = a.toString();
               } else {
                  a.signature = null;
               }
            }
         }
      }

   }

   protected void applyMethods(MixinTargetContext a) {
      Iterator var2 = a.getShadowMethods().iterator();

      MethodNode a;
      while(var2.hasNext()) {
         a = (MethodNode)var2.next();
         a.applyShadowMethod(a, a);
      }

      var2 = a.getMethods().iterator();

      while(var2.hasNext()) {
         a = (MethodNode)var2.next();
         a.applyNormalMethod(a, a);
      }

   }

   protected void applyShadowMethod(MixinTargetContext a1, MethodNode a) {
      MethodNode a = a.findTargetMethod(a);
      if (a != null) {
         Bytecode.mergeAnnotations(a, a);
      }

   }

   protected void applyNormalMethod(MixinTargetContext a, MethodNode a) {
      a.transformMethod(a);
      if (!a.name.startsWith("<")) {
         a.checkMethodVisibility(a, a);
         a.checkMethodConstraints(a, a);
         a.mergeMethod(a, a);
      } else if ("<clinit>".equals(a.name)) {
         a.appendInsns(a, a);
      }

   }

   protected void mergeMethod(MixinTargetContext a, MethodNode a) {
      boolean a = Annotations.getVisible(a, Overwrite.class) != null;
      MethodNode a = a.findTargetMethod(a);
      if (a != null) {
         if (a.isAlreadyMerged(a, a, a, a)) {
            return;
         }

         AnnotationNode a = Annotations.getInvisible(a, Intrinsic.class);
         if (a != null) {
            if (a.mergeIntrinsic(a, a, a, a, a)) {
               a.getTarget().methodMerged(a);
               return;
            }
         } else {
            if (a.requireOverwriteAnnotations() && !a) {
               throw new InvalidMixinException(a, String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", a.name, a.desc, a, a.getTarget().getClassName()));
            }

            a.targetClass.methods.remove(a);
         }
      } else if (a) {
         throw new InvalidMixinException(a, String.format("Overwrite target \"%s\" was not located in target class %s", a.name, a.getTargetClassRef()));
      }

      a.targetClass.methods.add(a);
      a.methodMerged(a);
      if (a.signature != null) {
         if (a.mergeSignatures) {
            SignatureVisitor a = a.getSignature().getRemapper();
            (new SignatureReader(a.signature)).accept(a);
            a.signature = a.toString();
         } else {
            a.signature = null;
         }
      }

   }

   protected boolean isAlreadyMerged(MixinTargetContext a, MethodNode a, boolean a3, MethodNode a) {
      AnnotationNode a = Annotations.getVisible(a, MixinMerged.class);
      if (a == null) {
         if (Annotations.getVisible(a, Final.class) != null) {
            a.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[]{a.name, a});
            return true;
         } else {
            return false;
         }
      } else {
         String a = (String)Annotations.getValue(a, "sessionId");
         if (!a.context.getSessionId().equals(a)) {
            throw new ClassFormatError("Invalid @MixinMerged annotation found in" + a + " at " + a.name + " in " + a.targetClass.name);
         } else if (Bytecode.hasFlag((MethodNode)a, 4160) && Bytecode.hasFlag((MethodNode)a, 4160)) {
            if (a.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
               a.logger.warn("Synthetic bridge method clash for {} in {}", new Object[]{a.name, a});
            }

            return true;
         } else {
            String a = (String)Annotations.getValue(a, "mixin");
            int a = (Integer)Annotations.getValue(a, "priority");
            if (a >= a.getPriority() && !a.equals(a.getClassName())) {
               a.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[]{a.name, a, a});
               return true;
            } else if (Annotations.getVisible(a, Final.class) != null) {
               a.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[]{a.name, a, a});
               return true;
            } else {
               return false;
            }
         }
      }
   }

   protected boolean mergeIntrinsic(MixinTargetContext a, MethodNode a, boolean a, MethodNode a, AnnotationNode a) {
      if (a) {
         throw new InvalidMixinException(a, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + a.name + " in " + a);
      } else {
         String a = a.name + a.desc;
         if (Bytecode.hasFlag((MethodNode)a, 8)) {
            throw new InvalidMixinException(a, "@Intrinsic method cannot be static, found " + a + " in " + a);
         } else {
            if (!Bytecode.hasFlag((MethodNode)a, 4096)) {
               AnnotationNode a = Annotations.getVisible(a, MixinRenamed.class);
               if (a == null || !(Boolean)Annotations.getValue(a, "isInterfaceMember", (Object)Boolean.FALSE)) {
                  throw new InvalidMixinException(a, "@Intrinsic method must be prefixed interface method, no rename encountered on " + a + " in " + a);
               }
            }

            if (!(Boolean)Annotations.getValue(a, "displace", (Object)Boolean.FALSE)) {
               a.logger.log(a.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[]{a, a.getTargetClassRef()});
               return true;
            } else {
               a.displaceIntrinsic(a, a, a);
               return false;
            }
         }
      }
   }

   protected void displaceIntrinsic(MixinTargetContext a1, MethodNode a, MethodNode a) {
      String a = "proxy+" + a.name;
      ListIterator a = a.instructions.iterator();

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (a instanceof MethodInsnNode && a.getOpcode() != 184) {
            MethodInsnNode a = (MethodInsnNode)a;
            if (a.owner.equals(a.targetClass.name) && a.name.equals(a.name) && a.desc.equals(a.desc)) {
               a.name = a;
            }
         }
      }

      a.name = a;
   }

   protected final void appendInsns(MixinTargetContext a1, MethodNode a) {
      if (Type.getReturnType(a.desc) != Type.VOID_TYPE) {
         throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
      } else {
         MethodNode a = a.findTargetMethod(a);
         if (a == null) {
            a.targetClass.methods.add(a);
         } else {
            AbstractInsnNode a = Bytecode.findInsn(a, 177);
            if (a != null) {
               ListIterator a = a.instructions.iterator();

               while(a.hasNext()) {
                  AbstractInsnNode a = (AbstractInsnNode)a.next();
                  if (!(a instanceof LineNumberNode) && a.getOpcode() != 177) {
                     a.instructions.insertBefore(a, a);
                  }
               }

               a.maxLocals = Math.max(a.maxLocals, a.maxLocals);
               a.maxStack = Math.max(a.maxStack, a.maxStack);
            }

         }
      }
   }

   protected void applyInitialisers(MixinTargetContext a) {
      MethodNode a = a.getConstructor(a);
      if (a != null) {
         Deque<AbstractInsnNode> a = a.getInitialiser(a, a);
         if (a != null && a.size() != 0) {
            Iterator var4 = a.targetClass.methods.iterator();

            while(var4.hasNext()) {
               MethodNode a = (MethodNode)var4.next();
               if ("<init>".equals(a.name)) {
                  a.maxStack = Math.max(a.maxStack, a.maxStack);
                  a.injectInitialiser(a, a, a);
               }
            }

         }
      }
   }

   protected MethodNode getConstructor(MixinTargetContext a) {
      MethodNode a = null;
      Iterator var3 = a.getMethods().iterator();

      while(var3.hasNext()) {
         MethodNode a = (MethodNode)var3.next();
         if ("<init>".equals(a.name) && Bytecode.methodHasLineNumbers(a)) {
            if (a == null) {
               a = a;
            } else {
               a.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", a, a.desc));
            }
         }
      }

      return a;
   }

   private MixinApplicatorStandard.Range getConstructorRange(MethodNode a) {
      boolean a = false;
      AbstractInsnNode a = null;
      int a = 0;
      int a = 0;
      int a = 0;
      int a = -1;
      ListIterator a = a.instructions.iterator();

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         if (a instanceof LineNumberNode) {
            a = ((LineNumberNode)a).line;
            a = true;
         } else if (a instanceof MethodInsnNode) {
            if (a.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)a).name) && a == -1) {
               a = a.instructions.indexOf(a);
               a = a;
            }
         } else if (a.getOpcode() == 181) {
            a = false;
         } else if (a.getOpcode() == 177) {
            if (a) {
               a = a;
            } else {
               a = a;
               a = a;
            }
         }
      }

      if (a != null) {
         LabelNode a = new LabelNode(new Label());
         a.instructions.insertBefore(a, (AbstractInsnNode)a);
         a.instructions.insertBefore(a, (AbstractInsnNode)(new LineNumberNode(a, a)));
      }

      return new MixinApplicatorStandard.Range(a, a, a);
   }

   protected final Deque<AbstractInsnNode> getInitialiser(MixinTargetContext a, MethodNode a) {
      MixinApplicatorStandard.Range a = a.getConstructorRange(a);
      if (!a.isValid()) {
         return null;
      } else {
         int a = false;
         Deque<AbstractInsnNode> a = new ArrayDeque();
         boolean a = false;
         int a = -1;
         LabelNode a = null;
         ListIterator a = a.instructions.iterator(a.marker);

         while(true) {
            while(true) {
               while(a.hasNext()) {
                  AbstractInsnNode a = (AbstractInsnNode)a.next();
                  if (a instanceof LineNumberNode) {
                     int a = ((LineNumberNode)a).line;
                     AbstractInsnNode a = a.instructions.get(a.instructions.indexOf(a) + 1);
                     if (a == a.end && a.getOpcode() != 177) {
                        a = true;
                        a = 177;
                     } else {
                        a = a.excludes(a);
                        a = -1;
                     }
                  } else if (a) {
                     if (a != null) {
                        a.add(a);
                        a = null;
                     }

                     if (a instanceof LabelNode) {
                        a = (LabelNode)a;
                     } else {
                        int a = a.getOpcode();
                        if (a == a) {
                           a = -1;
                        } else {
                           int[] var12 = INITIALISER_OPCODE_BLACKLIST;
                           int var13 = var12.length;

                           for(int var14 = 0; var14 < var13; ++var14) {
                              int a = var12[var14];
                              if (a == a) {
                                 throw new InvalidMixinException(a, "Cannot handle " + Bytecode.getOpcodeName(a) + " opcode (0x" + Integer.toHexString(a).toUpperCase() + ") in class initialiser");
                              }
                           }

                           a.add(a);
                        }
                     }
                  }
               }

               AbstractInsnNode a = (AbstractInsnNode)a.peekLast();
               if (a != null && a.getOpcode() != 181) {
                  throw new InvalidMixinException(a, "Could not parse initialiser, expected 0xB5, found 0x" + Integer.toHexString(a.getOpcode()) + " in " + a);
               }

               return a;
            }
         }
      }
   }

   protected final void injectInitialiser(MixinTargetContext a, MethodNode a, Deque<AbstractInsnNode> a) {
      Map<LabelNode, LabelNode> a = Bytecode.cloneLabels(a.instructions);
      AbstractInsnNode a = a.findInitialiserInjectionPoint(a, a, a);
      if (a == null) {
         a.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[]{a.desc});
      } else {
         Iterator var6 = a.iterator();

         while(var6.hasNext()) {
            AbstractInsnNode a = (AbstractInsnNode)var6.next();
            if (!(a instanceof LabelNode)) {
               if (a instanceof JumpInsnNode) {
                  throw new InvalidMixinException(a, "Unsupported JUMP opcode in initialiser in " + a);
               }

               AbstractInsnNode a = a.clone(a);
               a.instructions.insert(a, a);
               a = a;
            }
         }

      }
   }

   protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext a, MethodNode a, Deque<AbstractInsnNode> a) {
      Set<String> a = new HashSet();
      Iterator var5 = a.iterator();

      while(var5.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)var5.next();
         if (a.getOpcode() == 181) {
            a.add(fieldKey((FieldInsnNode)a));
         }
      }

      MixinApplicatorStandard.InitialiserInjectionMode a = a.getInitialiserInjectionMode(a.getEnvironment());
      String a = a.getTargetClassInfo().getName();
      String a = a.getTargetClassInfo().getSuperName();
      AbstractInsnNode a = null;
      ListIterator a = a.instructions.iterator();

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         String a;
         if (a.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)a).name)) {
            a = ((MethodInsnNode)a).owner;
            if (a.equals(a) || a.equals(a)) {
               a = a;
               if (a == MixinApplicatorStandard.InitialiserInjectionMode.SAFE) {
                  break;
               }
            }
         } else if (a.getOpcode() == 181 && a == MixinApplicatorStandard.InitialiserInjectionMode.DEFAULT) {
            a = fieldKey((FieldInsnNode)a);
            if (a.contains(a)) {
               a = a;
            }
         }
      }

      return a;
   }

   private MixinApplicatorStandard.InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment a) {
      String a = a.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
      if (a == null) {
         return MixinApplicatorStandard.InitialiserInjectionMode.DEFAULT;
      } else {
         try {
            return MixinApplicatorStandard.InitialiserInjectionMode.valueOf(a.toUpperCase());
         } catch (Exception var4) {
            a.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[]{a});
            return MixinApplicatorStandard.InitialiserInjectionMode.DEFAULT;
         }
      }
   }

   private static String fieldKey(FieldInsnNode a) {
      return String.format("%s:%s", a.desc, a.name);
   }

   protected void prepareInjections(MixinTargetContext a) {
      a.prepareInjections();
   }

   protected void applyInjections(MixinTargetContext a) {
      a.applyInjections();
   }

   protected void applyAccessors(MixinTargetContext a) {
      List<MethodNode> a = a.generateAccessors();
      Iterator var3 = a.iterator();

      while(var3.hasNext()) {
         MethodNode a = (MethodNode)var3.next();
         if (!a.name.startsWith("<")) {
            a.mergeMethod(a, a);
         }
      }

   }

   protected void checkMethodVisibility(MixinTargetContext a, MethodNode a) {
      if (Bytecode.hasFlag((MethodNode)a, 8) && !Bytecode.hasFlag((MethodNode)a, 2) && !Bytecode.hasFlag((MethodNode)a, 4096) && Annotations.getVisible(a, Overwrite.class) == null) {
         throw new InvalidMixinException(a, String.format("Mixin %s contains non-private static method %s", a, a));
      }
   }

   protected void applySourceMap(TargetClassContext a) {
      a.targetClass.sourceDebug = a.getSourceMap().toString();
   }

   protected void checkMethodConstraints(MixinTargetContext a, MethodNode a) {
      Iterator var3 = CONSTRAINED_ANNOTATIONS.iterator();

      while(var3.hasNext()) {
         Class<? extends Annotation> a = (Class)var3.next();
         AnnotationNode a = Annotations.getVisible(a, a);
         if (a != null) {
            a.checkConstraints(a, a, a);
         }
      }

   }

   protected final void checkConstraints(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      try {
         ConstraintParser.Constraint a = ConstraintParser.parse(a);

         try {
            a.check(a.getEnvironment());
         } catch (ConstraintViolationException var7) {
            String a = String.format("Constraint violation: %s on %s in %s", var7.getMessage(), a, a);
            a.logger.warn(a);
            if (!a.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
               throw new InvalidMixinException(a, a, var7);
            }
         }

      } catch (InvalidConstraintException var8) {
         throw new InvalidMixinException(a, var8.getMessage());
      }
   }

   protected final MethodNode findTargetMethod(MethodNode a) {
      Iterator var2 = a.targetClass.methods.iterator();

      MethodNode a;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         a = (MethodNode)var2.next();
      } while(!a.name.equals(a.name) || !a.desc.equals(a.desc));

      return a;
   }

   protected final FieldNode findTargetField(FieldNode a) {
      Iterator var2 = a.targetClass.fields.iterator();

      FieldNode a;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         a = (FieldNode)var2.next();
      } while(!a.name.equals(a.name));

      return a;
   }

   class Range {
      final int start;
      final int end;
      final int marker;

      Range(int axxxx, int ax, int axxx) {
         a.start = axxxx;
         a.end = ax;
         a.marker = axxx;
      }

      boolean isValid() {
         return a.start != 0 && a.end != 0 && a.end >= a.start;
      }

      boolean contains(int ax) {
         return ax >= a.start && ax <= a.end;
      }

      boolean excludes(int ax) {
         return ax < a.start || ax > a.end;
      }

      public String toString() {
         return String.format("Range[%d-%d,%d,valid=%s)", a.start, a.end, a.marker, a.isValid());
      }
   }

   static enum InitialiserInjectionMode {
      DEFAULT,
      SAFE;
   }

   static enum ApplicatorPass {
      MAIN,
      PREINJECT,
      INJECT;
   }
}

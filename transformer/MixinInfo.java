package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InnerClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTargetAlreadyLoadedException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.perf.Profiler;

class MixinInfo implements Comparable<MixinInfo>, IMixinInfo {
   private static final IMixinService classLoaderUtil = MixinService.getService();
   static int mixinOrder = 0;
   private final transient Logger logger = LogManager.getLogger("mixin");
   private final transient Profiler profiler = MixinEnvironment.getProfiler();
   private final transient MixinConfig parent;
   private final String name;
   private final String className;
   private final int priority;
   private final boolean virtual;
   private final List<ClassInfo> targetClasses;
   private final List<String> targetClassNames;
   private final transient int order;
   private final transient IMixinService service;
   private final transient IMixinConfigPlugin plugin;
   private final transient MixinEnvironment.Phase phase;
   private final transient ClassInfo info;
   private final transient MixinInfo.SubType type;
   private final transient boolean strict;
   private transient MixinInfo.State pendingState;
   private transient MixinInfo.State state;

   MixinInfo(IMixinService a, MixinConfig a, String a, boolean a, IMixinConfigPlugin a, boolean a) {
      a.order = mixinOrder++;
      a.service = a;
      a.parent = a;
      a.name = a;
      a.className = a.getMixinPackage() + a;
      a.plugin = a;
      a.phase = a.getEnvironment().getPhase();
      a.strict = a.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);

      try {
         byte[] a = a.loadMixinClass(a.className, a);
         a.pendingState = new MixinInfo.State(a);
         a.info = a.pendingState.getClassInfo();
         a.type = MixinInfo.SubType.getTypeFor(a);
      } catch (InvalidMixinException var10) {
         throw var10;
      } catch (Exception var11) {
         throw new InvalidMixinException(a, var11);
      }

      if (!a.type.isLoadable()) {
         classLoaderUtil.registerInvalidClass(a.className);
      }

      try {
         a.priority = a.readPriority(a.pendingState.getClassNode());
         a.virtual = a.readPseudo(a.pendingState.getClassNode());
         a.targetClasses = a.readTargetClasses(a.pendingState.getClassNode(), a);
         a.targetClassNames = Collections.unmodifiableList(Lists.transform(a.targetClasses, Functions.toStringFunction()));
      } catch (InvalidMixinException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new InvalidMixinException(a, var9);
      }
   }

   void validate() {
      if (a.pendingState == null) {
         throw new IllegalStateException("No pending validation state for " + a);
      } else {
         try {
            a.pendingState.validate(a.type, a.targetClasses);
            a.state = a.pendingState;
         } finally {
            a.pendingState = null;
         }

      }
   }

   protected List<ClassInfo> readTargetClasses(MixinInfo.MixinClassNode a, boolean a) {
      if (a == null) {
         return Collections.emptyList();
      } else {
         AnnotationNode a = Annotations.getInvisible((ClassNode)a, Mixin.class);
         if (a == null) {
            throw new InvalidMixinException(a, String.format("The mixin '%s' is missing an @Mixin annotation", a.className));
         } else {
            List<ClassInfo> a = new ArrayList();
            List<Type> a = (List)Annotations.getValue(a, "value");
            List<String> a = (List)Annotations.getValue(a, "targets");
            if (a != null) {
               a.readTargets(a, Lists.transform(a, new Function<Type, String>() {
                  public String apply(Type axx) {
                     return axx.getClassName();
                  }
               }), a, false);
            }

            if (a != null) {
               a.readTargets(a, Lists.transform(a, new Function<String, String>() {
                  public String apply(String axx) {
                     return a.getParent().remapClassName(a.getClassRef(), axx);
                  }
               }), a, true);
            }

            return a;
         }
      }
   }

   private void readTargets(Collection<ClassInfo> a, Collection<String> a, boolean a, boolean a) {
      Iterator var5 = a.iterator();

      while(var5.hasNext()) {
         String a = (String)var5.next();
         String a = a.replace('/', '.');
         if (classLoaderUtil.isClassLoaded(a) && !a.isReloading()) {
            String a = String.format("Critical problem: %s target %s was already transformed.", a, a);
            if (a.parent.isRequired()) {
               throw new MixinTargetAlreadyLoadedException(a, a, a);
            }

            a.logger.error(a);
         }

         if (a.shouldApplyMixin(a, a)) {
            ClassInfo a = a.getTarget(a, a);
            if (a != null && !a.contains(a)) {
               a.add(a);
               a.addMixin(a);
            }
         }
      }

   }

   private boolean shouldApplyMixin(boolean a, String a) {
      Profiler.Section a = a.profiler.begin("plugin");
      boolean a = a.plugin == null || a || a.plugin.shouldApplyMixin(a, a.className);
      a.end();
      return a;
   }

   private ClassInfo getTarget(String a, boolean a) throws InvalidMixinException {
      ClassInfo a = ClassInfo.forName(a);
      if (a == null) {
         if (a.isVirtual()) {
            a.logger.debug("Skipping virtual target {} for {}", new Object[]{a, a});
         } else {
            a.handleTargetError(String.format("@Mixin target %s was not found %s", a, a));
         }

         return null;
      } else {
         a.type.validateTarget(a, a);
         if (a && a.isPublic() && !a.isVirtual()) {
            a.handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", a, a));
         }

         return a;
      }
   }

   private void handleTargetError(String a) {
      if (a.strict) {
         a.logger.error(a);
         throw new InvalidMixinException(a, a);
      } else {
         a.logger.warn(a);
      }
   }

   protected int readPriority(ClassNode a) {
      if (a == null) {
         return a.parent.getDefaultMixinPriority();
      } else {
         AnnotationNode a = Annotations.getInvisible(a, Mixin.class);
         if (a == null) {
            throw new InvalidMixinException(a, String.format("The mixin '%s' is missing an @Mixin annotation", a.className));
         } else {
            Integer a = (Integer)Annotations.getValue(a, "priority");
            return a == null ? a.parent.getDefaultMixinPriority() : a;
         }
      }
   }

   protected boolean readPseudo(ClassNode a) {
      return Annotations.getInvisible(a, Pseudo.class) != null;
   }

   private boolean isReloading() {
      return a.pendingState instanceof MixinInfo.Reloaded;
   }

   private MixinInfo.State getState() {
      return a.state != null ? a.state : a.pendingState;
   }

   ClassInfo getClassInfo() {
      return a.info;
   }

   public IMixinConfig getConfig() {
      return a.parent;
   }

   MixinConfig getParent() {
      return a.parent;
   }

   public int getPriority() {
      return a.priority;
   }

   public String getName() {
      return a.name;
   }

   public String getClassName() {
      return a.className;
   }

   public String getClassRef() {
      return a.getClassInfo().getName();
   }

   public byte[] getClassBytes() {
      return a.getState().getClassBytes();
   }

   public boolean isDetachedSuper() {
      return a.getState().isDetachedSuper();
   }

   public boolean isUnique() {
      return a.getState().isUnique();
   }

   public boolean isVirtual() {
      return a.virtual;
   }

   public boolean isAccessor() {
      return a.type instanceof MixinInfo.SubType.Accessor;
   }

   public boolean isLoadable() {
      return a.type.isLoadable();
   }

   public Level getLoggingLevel() {
      return a.parent.getLoggingLevel();
   }

   public MixinEnvironment.Phase getPhase() {
      return a.phase;
   }

   public MixinInfo.MixinClassNode getClassNode(int a) {
      return a.getState().createClassNode(a);
   }

   public List<String> getTargetClasses() {
      return a.targetClassNames;
   }

   List<InterfaceInfo> getSoftImplements() {
      return Collections.unmodifiableList(a.getState().getSoftImplements());
   }

   Set<String> getSyntheticInnerClasses() {
      return Collections.unmodifiableSet(a.getState().getSyntheticInnerClasses());
   }

   Set<String> getInnerClasses() {
      return Collections.unmodifiableSet(a.getState().getInnerClasses());
   }

   List<ClassInfo> getTargets() {
      return Collections.unmodifiableList(a.targetClasses);
   }

   Set<String> getInterfaces() {
      return a.getState().getInterfaces();
   }

   MixinTargetContext createContextFor(TargetClassContext a) {
      MixinInfo.MixinClassNode a = a.getClassNode(8);
      Profiler.Section a = a.profiler.begin("pre");
      MixinTargetContext a = a.type.createPreProcessor(a).prepare().createContextFor(a);
      a.end();
      return a;
   }

   private byte[] loadMixinClass(String a, boolean a) throws ClassNotFoundException {
      Object var3 = null;

      try {
         if (a) {
            String a = a.service.getClassRestrictions(a);
            if (a.length() > 0) {
               a.logger.error("Classloader restrictions [{}] encountered loading {}, name: {}", new Object[]{a, a, a});
            }
         }

         byte[] a = a.service.getBytecodeProvider().getClassBytes(a, a);
         return a;
      } catch (ClassNotFoundException var5) {
         throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", a));
      } catch (IOException var6) {
         a.logger.warn("Failed to load mixin {}, the specified mixin will not be applied", new Object[]{a});
         throw new InvalidMixinException(a, "An error was encountered whilst loading the mixin class", var6);
      }
   }

   void reloadMixin(byte[] a) {
      if (a.pendingState != null) {
         throw new IllegalStateException("Cannot reload mixin while it is initialising");
      } else {
         a.pendingState = new MixinInfo.Reloaded(a.state, a);
         a.validate();
      }
   }

   public int compareTo(MixinInfo a) {
      if (a == null) {
         return 0;
      } else {
         return a.priority == a.priority ? a.order - a.order : a.priority - a.priority;
      }
   }

   public void preApply(String a, ClassNode a) {
      if (a.plugin != null) {
         Profiler.Section a = a.profiler.begin("plugin");
         a.plugin.preApply(a, a, a.className, a);
         a.end();
      }

   }

   public void postApply(String a, ClassNode a) {
      if (a.plugin != null) {
         Profiler.Section a = a.profiler.begin("plugin");
         a.plugin.postApply(a, a, a.className, a);
         a.end();
      }

      a.parent.postApply(a, a);
   }

   public String toString() {
      return String.format("%s:%s", a.parent.getName(), a.name);
   }

   abstract static class SubType {
      protected final MixinInfo mixin;
      protected final String annotationType;
      protected final boolean targetMustBeInterface;
      protected boolean detached;

      SubType(MixinInfo a, String a, boolean a) {
         a.mixin = a;
         a.annotationType = a;
         a.targetMustBeInterface = a;
      }

      Collection<String> getInterfaces() {
         return Collections.emptyList();
      }

      boolean isDetachedSuper() {
         return a.detached;
      }

      boolean isLoadable() {
         return false;
      }

      void validateTarget(String a, ClassInfo a) {
         boolean a = a.isInterface();
         if (a != a.targetMustBeInterface) {
            String a = a ? "" : "not ";
            throw new InvalidMixinException(a.mixin, a.annotationType + " target type mismatch: " + a + " is " + a + "an interface in " + a);
         }
      }

      abstract void validate(MixinInfo.State var1, List<ClassInfo> var2);

      abstract MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode var1);

      static MixinInfo.SubType getTypeFor(MixinInfo a) {
         if (!a.getClassInfo().isInterface()) {
            return new MixinInfo.SubType.Standard(a);
         } else {
            boolean a = false;

            ClassInfo.Method a;
            for(Iterator var2 = a.getClassInfo().getMethods().iterator(); var2.hasNext(); a |= !a.isAccessor()) {
               a = (ClassInfo.Method)var2.next();
            }

            if (a) {
               return new MixinInfo.SubType.Interface(a);
            } else {
               return new MixinInfo.SubType.Accessor(a);
            }
         }
      }

      static class Accessor extends MixinInfo.SubType {
         private final Collection<String> interfaces = new ArrayList();

         Accessor(MixinInfo a) {
            super(a, "@Mixin", false);
            a.interfaces.add(a.getClassRef());
         }

         boolean isLoadable() {
            return true;
         }

         Collection<String> getInterfaces() {
            return a.interfaces;
         }

         void validateTarget(String a1, ClassInfo a) {
            boolean a = a.isInterface();
            if (a && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
               throw new InvalidMixinException(a.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
            }
         }

         void validate(MixinInfo.State a, List<ClassInfo> a2) {
            ClassNode a = a.getClassNode();
            if (!"java/lang/Object".equals(a.superName)) {
               throw new InvalidMixinException(a.mixin, "Super class of " + a + " is invalid, found " + a.superName.replace('/', '.'));
            }
         }

         MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode a) {
            return new MixinPreProcessorAccessor(a.mixin, a);
         }
      }

      static class Interface extends MixinInfo.SubType {
         Interface(MixinInfo a) {
            super(a, "@Mixin", true);
         }

         void validate(MixinInfo.State a, List<ClassInfo> a2) {
            if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
               throw new InvalidMixinException(a.mixin, "Interface mixin not supported in current enviromnment");
            } else {
               ClassNode a = a.getClassNode();
               if (!"java/lang/Object".equals(a.superName)) {
                  throw new InvalidMixinException(a.mixin, "Super class of " + a + " is invalid, found " + a.superName.replace('/', '.'));
               }
            }
         }

         MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode a) {
            return new MixinPreProcessorInterface(a.mixin, a);
         }
      }

      static class Standard extends MixinInfo.SubType {
         Standard(MixinInfo a) {
            super(a, "@Mixin", false);
         }

         void validate(MixinInfo.State a, List<ClassInfo> a) {
            ClassNode a = a.getClassNode();
            Iterator var4 = a.iterator();

            while(var4.hasNext()) {
               ClassInfo a = (ClassInfo)var4.next();
               if (!a.superName.equals(a.getSuperName())) {
                  if (!a.hasSuperClass(a.superName, ClassInfo.Traversal.SUPER)) {
                     ClassInfo a = ClassInfo.forName(a.superName);
                     if (a.isMixin()) {
                        Iterator var7 = a.getTargets().iterator();

                        while(var7.hasNext()) {
                           ClassInfo a = (ClassInfo)var7.next();
                           if (a.contains(a)) {
                              throw new InvalidMixinException(a.mixin, "Illegal hierarchy detected. Derived mixin " + a + " targets the same class " + a.getClassName() + " as its superclass " + a.getClassName());
                           }
                        }
                     }

                     throw new InvalidMixinException(a.mixin, "Super class '" + a.superName.replace('/', '.') + "' of " + a.mixin.getName() + " was not found in the hierarchy of target class '" + a + "'");
                  }

                  a.detached = true;
               }
            }

         }

         MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode a) {
            return new MixinPreProcessorStandard(a.mixin, a);
         }
      }
   }

   class Reloaded extends MixinInfo.State {
      private final MixinInfo.State previous;

      Reloaded(MixinInfo.State axxx, byte[] ax) {
         super(ax, axxx.getClassInfo());
         a.previous = axxx;
      }

      protected void validateChanges(MixinInfo.SubType a1, List<ClassInfo> axxx) {
         if (!a.syntheticInnerClasses.equals(a.previous.syntheticInnerClasses)) {
            throw new MixinReloadException(MixinInfo.this, "Cannot change inner classes");
         } else if (!a.interfaces.equals(a.previous.interfaces)) {
            throw new MixinReloadException(MixinInfo.this, "Cannot change interfaces");
         } else if (!(new HashSet(a.softImplements)).equals(new HashSet(a.previous.softImplements))) {
            throw new MixinReloadException(MixinInfo.this, "Cannot change soft interfaces");
         } else {
            List<ClassInfo> ax = MixinInfo.this.readTargetClasses(a.classNode, true);
            if (!(new HashSet(ax)).equals(new HashSet(axxx))) {
               throw new MixinReloadException(MixinInfo.this, "Cannot change target classes");
            } else {
               int axx = MixinInfo.this.readPriority(a.classNode);
               if (axx != MixinInfo.this.getPriority()) {
                  throw new MixinReloadException(MixinInfo.this, "Cannot change mixin priority");
               }
            }
         }
      }
   }

   class State {
      private byte[] mixinBytes;
      private final ClassInfo classInfo;
      private boolean detachedSuper;
      private boolean unique;
      protected final Set<String> interfaces;
      protected final List<InterfaceInfo> softImplements;
      protected final Set<String> syntheticInnerClasses;
      protected final Set<String> innerClasses;
      protected MixinInfo.MixinClassNode classNode;

      State(byte[] axx) {
         this(axx, (ClassInfo)null);
      }

      State(byte[] axxx, ClassInfo ax) {
         a.interfaces = new HashSet();
         a.softImplements = new ArrayList();
         a.syntheticInnerClasses = new HashSet();
         a.innerClasses = new HashSet();
         a.mixinBytes = axxx;
         a.connect();
         a.classInfo = ax != null ? ax : ClassInfo.fromClassNode(a.getClassNode());
      }

      private void connect() {
         a.classNode = a.createClassNode(0);
      }

      private void complete() {
         a.classNode = null;
      }

      ClassInfo getClassInfo() {
         return a.classInfo;
      }

      byte[] getClassBytes() {
         return a.mixinBytes;
      }

      MixinInfo.MixinClassNode getClassNode() {
         return a.classNode;
      }

      boolean isDetachedSuper() {
         return a.detachedSuper;
      }

      boolean isUnique() {
         return a.unique;
      }

      List<? extends InterfaceInfo> getSoftImplements() {
         return a.softImplements;
      }

      Set<String> getSyntheticInnerClasses() {
         return a.syntheticInnerClasses;
      }

      Set<String> getInnerClasses() {
         return a.innerClasses;
      }

      Set<String> getInterfaces() {
         return a.interfaces;
      }

      MixinInfo.MixinClassNode createClassNode(int axx) {
         MixinInfo.MixinClassNode axxx = MixinInfo.this.new MixinClassNode(MixinInfo.this);
         ClassReader ax = new ClassReader(a.mixinBytes);
         ax.accept(axxx, axx);
         return axxx;
      }

      void validate(MixinInfo.SubType ax, List<ClassInfo> axx) {
         MixinPreProcessorStandard axxx = ax.createPreProcessor(a.getClassNode()).prepare();
         Iterator var4 = axx.iterator();

         while(var4.hasNext()) {
            ClassInfo axxxx = (ClassInfo)var4.next();
            axxx.conform(axxxx);
         }

         ax.validate(a, axx);
         a.detachedSuper = ax.isDetachedSuper();
         a.unique = Annotations.getVisible((ClassNode)a.getClassNode(), Unique.class) != null;
         a.validateInner();
         a.validateClassVersion();
         a.validateRemappables(axx);
         a.readImplementations(ax);
         a.readInnerClasses();
         a.validateChanges(ax, axx);
         a.complete();
      }

      private void validateInner() {
         if (!a.classInfo.isProbablyStatic()) {
            throw new InvalidMixinException(MixinInfo.this, "Inner class mixin must be declared static");
         }
      }

      private void validateClassVersion() {
         if (a.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
            String ax = ".";
            MixinEnvironment.CompatibilityLevel[] var2 = MixinEnvironment.CompatibilityLevel.values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               MixinEnvironment.CompatibilityLevel axx = var2[var4];
               if (axx.classVersion() >= a.classNode.version) {
                  ax = String.format(". Mixin requires compatibility level %s or above.", axx.name());
               }
            }

            throw new InvalidMixinException(MixinInfo.this, "Unsupported mixin class version " + a.classNode.version + ax);
         }
      }

      private void validateRemappables(List<ClassInfo> ax) {
         if (ax.size() > 1) {
            Iterator var2 = a.classNode.fields.iterator();

            while(var2.hasNext()) {
               FieldNode axx = (FieldNode)var2.next();
               a.validateRemappable(Shadow.class, axx.name, Annotations.getVisible(axx, Shadow.class));
            }

            var2 = a.classNode.methods.iterator();

            AnnotationNode axxx;
            MethodNode axxxx;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               axxxx = (MethodNode)var2.next();
               a.validateRemappable(Shadow.class, axxxx.name, Annotations.getVisible(axxxx, Shadow.class));
               axxx = Annotations.getVisible(axxxx, Overwrite.class);
            } while(axxx == null || (axxxx.access & 8) != 0 && (axxxx.access & 1) != 0);

            throw new InvalidMixinException(MixinInfo.this, "Found @Overwrite annotation on " + axxxx.name + " in " + MixinInfo.this);
         }
      }

      private void validateRemappable(Class<Shadow> axx, String axxx, AnnotationNode ax) {
         if (ax != null && (Boolean)Annotations.getValue(ax, "remap", (Object)Boolean.TRUE)) {
            throw new InvalidMixinException(MixinInfo.this, "Found a remappable @" + axx.getSimpleName() + " annotation on " + axxx + " in " + a);
         }
      }

      void readImplementations(MixinInfo.SubType axx) {
         a.interfaces.addAll(a.classNode.interfaces);
         a.interfaces.addAll(axx.getInterfaces());
         AnnotationNode axxx = Annotations.getInvisible((ClassNode)a.classNode, Implements.class);
         if (axxx != null) {
            List<AnnotationNode> axxxx = (List)Annotations.getValue(axxx);
            if (axxxx != null) {
               Iterator var4 = axxxx.iterator();

               while(var4.hasNext()) {
                  AnnotationNode axxxxx = (AnnotationNode)var4.next();
                  InterfaceInfo ax = InterfaceInfo.fromAnnotation(MixinInfo.this, axxxxx);
                  a.softImplements.add(ax);
                  a.interfaces.add(ax.getInternalName());
                  if (!(a instanceof MixinInfo.Reloaded)) {
                     a.classInfo.addInterface(ax.getInternalName());
                  }
               }

            }
         }
      }

      void readInnerClasses() {
         Iterator var1 = a.classNode.innerClasses.iterator();

         while(true) {
            while(true) {
               InnerClassNode axx;
               ClassInfo ax;
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  axx = (InnerClassNode)var1.next();
                  ax = ClassInfo.forName(axx.name);
               } while((axx.outerName == null || !axx.outerName.equals(a.classInfo.getName())) && !axx.name.startsWith(a.classNode.name + "$"));

               if (ax.isProbablyStatic() && ax.isSynthetic()) {
                  a.syntheticInnerClasses.add(axx.name);
               } else {
                  a.innerClasses.add(axx.name);
               }
            }
         }
      }

      protected void validateChanges(MixinInfo.SubType ax, List<ClassInfo> a2) {
         ax.createPreProcessor(a.classNode).prepare();
      }
   }

   class MixinClassNode extends ClassNode {
      public final List<MixinInfo.MixinMethodNode> mixinMethods;

      public MixinClassNode(MixinInfo a2) {
         this(327680);
      }

      public MixinClassNode(int axx) {
         super(axx);
         a.mixinMethods = (List)a.methods;
      }

      public MixinInfo getMixin() {
         return MixinInfo.this;
      }

      public MethodVisitor visitMethod(int axx, String axxx, String axxxx, String axxxxx, String[] axxxxxx) {
         MethodNode ax = MixinInfo.this.new MixinMethodNode(axx, axxx, axxxx, axxxxx, axxxxxx);
         a.methods.add(ax);
         return ax;
      }
   }

   class MixinMethodNode extends MethodNode {
      private final String originalName;

      public MixinMethodNode(int axxx, String axxxx, String axxxxx, String axxxxxx, String[] ax) {
         super(327680, axxx, axxxx, axxxxx, axxxxxx, ax);
         a.originalName = axxxx;
      }

      public String toString() {
         return String.format("%s%s", a.originalName, a.desc);
      }

      public String getOriginalName() {
         return a.originalName;
      }

      public boolean isInjector() {
         return a.getInjectorAnnotation() != null || a.isSurrogate();
      }

      public boolean isSurrogate() {
         return a.getVisibleAnnotation(Surrogate.class) != null;
      }

      public boolean isSynthetic() {
         return Bytecode.hasFlag((MethodNode)a, 4096);
      }

      public AnnotationNode getVisibleAnnotation(Class<? extends Annotation> ax) {
         return Annotations.getVisible((MethodNode)a, ax);
      }

      public AnnotationNode getInjectorAnnotation() {
         return InjectionInfo.getInjectorAnnotation(MixinInfo.this, a);
      }

      public IMixinInfo getOwner() {
         return MixinInfo.this;
      }
   }
}

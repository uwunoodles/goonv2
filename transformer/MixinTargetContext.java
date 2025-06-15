package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;

public class MixinTargetContext extends ClassContext implements IMixinContext {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final MixinInfo mixin;
   private final ClassNode classNode;
   private final TargetClassContext targetClass;
   private final String sessionId;
   private final ClassInfo targetClassInfo;
   private final BiMap<String, String> innerClasses = HashBiMap.create();
   private final List<MethodNode> shadowMethods = new ArrayList();
   private final Map<FieldNode, ClassInfo.Field> shadowFields = new LinkedHashMap();
   private final List<MethodNode> mergedMethods = new ArrayList();
   private final InjectorGroupInfo.Map injectorGroups = new InjectorGroupInfo.Map();
   private final List<InjectionInfo> injectors = new ArrayList();
   private final List<AccessorInfo> accessors = new ArrayList();
   private final boolean inheritsFromMixin;
   private final boolean detachedSuper;
   private final SourceMap.File stratum;
   private int minRequiredClassVersion;

   MixinTargetContext(MixinInfo a, ClassNode a, TargetClassContext a) {
      a.minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
      a.mixin = a;
      a.classNode = a;
      a.targetClass = a;
      a.targetClassInfo = ClassInfo.forName(a.getTarget().getClassRef());
      a.stratum = a.getSourceMap().addFile(a.classNode);
      a.inheritsFromMixin = a.getClassInfo().hasMixinInHierarchy() || a.targetClassInfo.hasMixinTargetInHierarchy();
      a.detachedSuper = !a.classNode.superName.equals(a.getTarget().getClassNode().superName);
      a.sessionId = a.getSessionId();
      a.requireVersion(a.version);
      InnerClassGenerator a = (InnerClassGenerator)a.getExtensions().getGenerator(InnerClassGenerator.class);
      Iterator var5 = a.mixin.getInnerClasses().iterator();

      while(var5.hasNext()) {
         String a = (String)var5.next();
         a.innerClasses.put(a, a.registerInnerClass(a.mixin, a, a));
      }

   }

   void addShadowMethod(MethodNode a) {
      a.shadowMethods.add(a);
   }

   void addShadowField(FieldNode a, ClassInfo.Field a) {
      a.shadowFields.put(a, a);
   }

   void addAccessorMethod(MethodNode a, Class<? extends Annotation> a) {
      a.accessors.add(AccessorInfo.of(a, a, a));
   }

   void addMixinMethod(MethodNode a) {
      Annotations.setVisible(a, MixinMerged.class, "mixin", a.getClassName());
      a.getTarget().addMixinMethod(a);
   }

   void methodMerged(MethodNode a) {
      a.mergedMethods.add(a);
      a.targetClassInfo.addMethod(a);
      a.getTarget().methodMerged(a);
      Annotations.setVisible(a, MixinMerged.class, "mixin", a.getClassName(), "priority", a.getPriority(), "sessionId", a.sessionId);
   }

   public String toString() {
      return a.mixin.toString();
   }

   public MixinEnvironment getEnvironment() {
      return a.mixin.getParent().getEnvironment();
   }

   public boolean getOption(MixinEnvironment.Option a) {
      return a.getEnvironment().getOption(a);
   }

   public ClassNode getClassNode() {
      return a.classNode;
   }

   public String getClassName() {
      return a.mixin.getClassName();
   }

   public String getClassRef() {
      return a.mixin.getClassRef();
   }

   public TargetClassContext getTarget() {
      return a.targetClass;
   }

   public String getTargetClassRef() {
      return a.getTarget().getClassRef();
   }

   public ClassNode getTargetClassNode() {
      return a.getTarget().getClassNode();
   }

   public ClassInfo getTargetClassInfo() {
      return a.targetClassInfo;
   }

   protected ClassInfo getClassInfo() {
      return a.mixin.getClassInfo();
   }

   public ClassSignature getSignature() {
      return a.getClassInfo().getSignature();
   }

   public SourceMap.File getStratum() {
      return a.stratum;
   }

   public int getMinRequiredClassVersion() {
      return a.minRequiredClassVersion;
   }

   public int getDefaultRequiredInjections() {
      return a.mixin.getParent().getDefaultRequiredInjections();
   }

   public String getDefaultInjectorGroup() {
      return a.mixin.getParent().getDefaultInjectorGroup();
   }

   public int getMaxShiftByValue() {
      return a.mixin.getParent().getMaxShiftByValue();
   }

   public InjectorGroupInfo.Map getInjectorGroups() {
      return a.injectorGroups;
   }

   public boolean requireOverwriteAnnotations() {
      return a.mixin.getParent().requireOverwriteAnnotations();
   }

   public ClassInfo findRealType(ClassInfo a) {
      if (a == a.getClassInfo()) {
         return a.targetClassInfo;
      } else {
         ClassInfo a = a.targetClassInfo.findCorrespondingType(a);
         if (a == null) {
            throw new InvalidMixinException(a, "Resolution error: unable to find corresponding type for " + a + " in hierarchy of " + a.targetClassInfo);
         } else {
            return a;
         }
      }
   }

   public void transformMethod(MethodNode a) {
      a.validateMethod(a);
      a.transformDescriptor(a);
      a.transformLVT(a);
      a.stratum.applyOffset(a);
      AbstractInsnNode a = null;

      AbstractInsnNode a;
      for(ListIterator a = a.instructions.iterator(); a.hasNext(); a = a) {
         a = (AbstractInsnNode)a.next();
         if (a instanceof MethodInsnNode) {
            a.transformMethodRef(a, a, new MemberRef.Method((MethodInsnNode)a));
         } else if (a instanceof FieldInsnNode) {
            a.transformFieldRef(a, a, new MemberRef.Field((FieldInsnNode)a));
            a.checkFinal(a, a, (FieldInsnNode)a);
         } else if (a instanceof TypeInsnNode) {
            a.transformTypeNode(a, a, (TypeInsnNode)a, a);
         } else if (a instanceof LdcInsnNode) {
            a.transformConstantNode(a, a, (LdcInsnNode)a);
         } else if (a instanceof InvokeDynamicInsnNode) {
            a.transformInvokeDynamicNode(a, a, (InvokeDynamicInsnNode)a);
         }
      }

   }

   private void validateMethod(MethodNode a) {
      if (Annotations.getInvisible(a, SoftOverride.class) != null) {
         ClassInfo.Method a = a.targetClassInfo.findMethodInHierarchy(a.name, a.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
         if (a == null || !a.isInjected()) {
            throw new InvalidMixinException(a, "Mixin method " + a.name + a.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + a.getTarget().getClassName());
         }
      }

   }

   private void transformLVT(MethodNode a) {
      if (a.localVariables != null) {
         Iterator var2 = a.localVariables.iterator();

         while(var2.hasNext()) {
            LocalVariableNode a = (LocalVariableNode)var2.next();
            if (a != null && a.desc != null) {
               a.desc = a.transformSingleDescriptor(Type.getType(a.desc));
            }
         }

      }
   }

   private void transformMethodRef(MethodNode a, Iterator<AbstractInsnNode> a2, MemberRef a) {
      a.transformDescriptor(a);
      if (a.getOwner().equals(a.getClassRef())) {
         a.setOwner(a.getTarget().getClassRef());
         ClassInfo.Method a = a.getClassInfo().findMethod(a.getName(), a.getDesc(), 10);
         if (a != null && a.isRenamed() && a.getOriginalName().equals(a.getName()) && a.isSynthetic()) {
            a.setName(a.getName());
         }

         a.upgradeMethodRef(a, a, a);
      } else if (a.innerClasses.containsKey(a.getOwner())) {
         a.setOwner((String)a.innerClasses.get(a.getOwner()));
         a.setDesc(a.transformMethodDescriptor(a.getDesc()));
      } else if (a.detachedSuper || a.inheritsFromMixin) {
         if (a.getOpcode() == 183) {
            a.updateStaticBinding(a, a);
         } else if (a.getOpcode() == 182 && ClassInfo.forName(a.getOwner()).isMixin()) {
            a.updateDynamicBinding(a, a);
         }
      }

   }

   private void transformFieldRef(MethodNode a, Iterator<AbstractInsnNode> a, MemberRef a) {
      if ("super$".equals(a.getName())) {
         if (!(a instanceof MemberRef.Field)) {
            throw new InvalidMixinException(a.mixin, "Cannot call imaginary super from method handle.");
         }

         a.processImaginarySuper(a, ((MemberRef.Field)a).insn);
         a.remove();
      }

      a.transformDescriptor(a);
      if (a.getOwner().equals(a.getClassRef())) {
         a.setOwner(a.getTarget().getClassRef());
         ClassInfo.Field a = a.getClassInfo().findField(a.getName(), a.getDesc(), 10);
         if (a != null && a.isRenamed() && a.getOriginalName().equals(a.getName()) && a.isStatic()) {
            a.setName(a.getName());
         }
      } else {
         ClassInfo a = ClassInfo.forName(a.getOwner());
         if (a.isMixin()) {
            ClassInfo a = a.targetClassInfo.findCorrespondingType(a);
            a.setOwner(a != null ? a.getName() : a.getTarget().getClassRef());
         }
      }

   }

   private void checkFinal(MethodNode a, Iterator<AbstractInsnNode> a2, FieldInsnNode a) {
      if (a.owner.equals(a.getTarget().getClassRef())) {
         int a = a.getOpcode();
         if (a != 180 && a != 178) {
            Iterator var5 = a.shadowFields.entrySet().iterator();

            Entry a;
            FieldNode a;
            do {
               if (!var5.hasNext()) {
                  return;
               }

               a = (Entry)var5.next();
               a = (FieldNode)a.getKey();
            } while(!a.desc.equals(a.desc) || !a.name.equals(a.name));

            ClassInfo.Field a = (ClassInfo.Field)a.getValue();
            if (a.isDecoratedFinal()) {
               if (a.isDecoratedMutable()) {
                  if (a.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                     logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[]{a, a.mixin, a.name});
                  }
               } else if (!"<init>".equals(a.name) && !"<clinit>".equals(a.name)) {
                  logger.error("Write access detected to @Final field {} in {}::{}", new Object[]{a, a.mixin, a.name});
                  if (a.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                     throw new InvalidMixinException(a.mixin, "Write access detected to @Final field " + a + " in " + a.mixin + "::" + a.name);
                  }
               } else {
                  logger.warn("@Final field {} in {} should be final", new Object[]{a, a.mixin});
               }
            }

         }
      }
   }

   private void transformTypeNode(MethodNode a1, Iterator<AbstractInsnNode> a, TypeInsnNode a, AbstractInsnNode a) {
      if (a.getOpcode() == 192 && a.desc.equals(a.getTarget().getClassRef()) && a.getOpcode() == 25 && ((VarInsnNode)a).var == 0) {
         a.remove();
      } else {
         if (a.desc.equals(a.getClassRef())) {
            a.desc = a.getTarget().getClassRef();
         } else {
            String a = (String)a.innerClasses.get(a.desc);
            if (a != null) {
               a.desc = a;
            }
         }

         a.transformDescriptor(a);
      }
   }

   private void transformConstantNode(MethodNode a, Iterator<AbstractInsnNode> a, LdcInsnNode a) {
      a.cst = a.transformConstant(a, a, a.cst);
   }

   private void transformInvokeDynamicNode(MethodNode a, Iterator<AbstractInsnNode> a, InvokeDynamicInsnNode a) {
      a.requireVersion(51);
      a.desc = a.transformMethodDescriptor(a.desc);
      a.bsm = a.transformHandle(a, a, a.bsm);

      for(int a = 0; a < a.bsmArgs.length; ++a) {
         a.bsmArgs[a] = a.transformConstant(a, a, a.bsmArgs[a]);
      }

   }

   private Object transformConstant(MethodNode a, Iterator<AbstractInsnNode> a, Object a) {
      if (a instanceof Type) {
         Type a = (Type)a;
         String a = a.transformDescriptor(a);
         return !a.toString().equals(a) ? Type.getType(a) : a;
      } else {
         return a instanceof Handle ? a.transformHandle(a, a, (Handle)a) : a;
      }
   }

   private Handle transformHandle(MethodNode a, Iterator<AbstractInsnNode> a, Handle a) {
      MemberRef.Handle a = new MemberRef.Handle(a);
      if (a.isField()) {
         a.transformFieldRef(a, a, a);
      } else {
         a.transformMethodRef(a, a, a);
      }

      return a.getMethodHandle();
   }

   private void processImaginarySuper(MethodNode a, FieldInsnNode a) {
      if (a.getOpcode() != 180) {
         if ("<init>".equals(a.name)) {
            throw new InvalidMixinException(a, "Illegal imaginary super declaration: field " + a.name + " must not specify an initialiser");
         } else {
            throw new InvalidMixinException(a, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(a.getOpcode()) + " opcode in " + a.name + a.desc);
         }
      } else if ((a.access & 2) == 0 && (a.access & 8) == 0) {
         if (Annotations.getInvisible(a, SoftOverride.class) == null) {
            throw new InvalidMixinException(a, "Illegal imaginary super access: method " + a.name + a.desc + " is not decorated with @SoftOverride");
         } else {
            ListIterator a = a.instructions.iterator(a.instructions.indexOf(a));

            while(a.hasNext()) {
               AbstractInsnNode a = (AbstractInsnNode)a.next();
               if (a instanceof MethodInsnNode) {
                  MethodInsnNode a = (MethodInsnNode)a;
                  if (a.owner.equals(a.getClassRef()) && a.name.equals(a.name) && a.desc.equals(a.desc)) {
                     a.setOpcode(183);
                     a.updateStaticBinding(a, new MemberRef.Method(a));
                     return;
                  }
               }
            }

            throw new InvalidMixinException(a, "Illegal imaginary super access: could not find INVOKE for " + a.name + a.desc);
         }
      } else {
         throw new InvalidMixinException(a, "Illegal imaginary super access: method " + a.name + a.desc + " is private or static");
      }
   }

   private void updateStaticBinding(MethodNode a, MemberRef a) {
      a.updateBinding(a, a, ClassInfo.Traversal.SUPER);
   }

   private void updateDynamicBinding(MethodNode a, MemberRef a) {
      a.updateBinding(a, a, ClassInfo.Traversal.ALL);
   }

   private void updateBinding(MethodNode a, MemberRef a, ClassInfo.Traversal a) {
      if (!"<init>".equals(a.name) && !a.getOwner().equals(a.getTarget().getClassRef()) && !a.getTarget().getClassRef().startsWith("<")) {
         ClassInfo.Method a = a.targetClassInfo.findMethodInHierarchy(a.getName(), a.getDesc(), a.getSearchType(), a);
         if (a != null) {
            if (a.getOwner().isMixin()) {
               throw new InvalidMixinException(a, "Invalid " + a + " in " + a + " resolved " + a.getOwner() + " but is mixin.");
            }

            a.setOwner(a.getImplementor().getName());
         } else if (ClassInfo.forName(a.getOwner()).isMixin()) {
            throw new MixinTransformerError("Error resolving " + a + " in " + a);
         }

      }
   }

   public void transformDescriptor(FieldNode a) {
      if (a.inheritsFromMixin || a.innerClasses.size() != 0) {
         a.desc = a.transformSingleDescriptor(a.desc, false);
      }
   }

   public void transformDescriptor(MethodNode a) {
      if (a.inheritsFromMixin || a.innerClasses.size() != 0) {
         a.desc = a.transformMethodDescriptor(a.desc);
      }
   }

   public void transformDescriptor(MemberRef a) {
      if (a.inheritsFromMixin || a.innerClasses.size() != 0) {
         if (a.isField()) {
            a.setDesc(a.transformSingleDescriptor(a.getDesc(), false));
         } else {
            a.setDesc(a.transformMethodDescriptor(a.getDesc()));
         }

      }
   }

   public void transformDescriptor(TypeInsnNode a) {
      if (a.inheritsFromMixin || a.innerClasses.size() != 0) {
         a.desc = a.transformSingleDescriptor(a.desc, true);
      }
   }

   private String transformDescriptor(Type a) {
      return a.getSort() == 11 ? a.transformMethodDescriptor(a.getDescriptor()) : a.transformSingleDescriptor(a);
   }

   private String transformSingleDescriptor(Type a) {
      return a.getSort() < 9 ? a.toString() : a.transformSingleDescriptor(a.toString(), false);
   }

   private String transformSingleDescriptor(String a, boolean a) {
      String a = a;

      while(a.startsWith("[") || a.startsWith("L")) {
         if (a.startsWith("[")) {
            a = a.substring(1);
         } else {
            a = a.substring(1, a.indexOf(";"));
            a = true;
         }
      }

      if (!a) {
         return a;
      } else {
         String a = (String)a.innerClasses.get(a);
         if (a != null) {
            return a.replace(a, a);
         } else if (a.innerClasses.inverse().containsKey(a)) {
            return a;
         } else {
            ClassInfo a = ClassInfo.forName(a);
            if (!a.isMixin()) {
               return a;
            } else {
               return a.replace(a, a.findRealType(a).toString());
            }
         }
      }
   }

   private String transformMethodDescriptor(String a) {
      StringBuilder a = new StringBuilder();
      a.append('(');
      Type[] var3 = Type.getArgumentTypes(a);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type a = var3[var5];
         a.append(a.transformSingleDescriptor(a));
      }

      return a.append(')').append(a.transformSingleDescriptor(Type.getReturnType(a))).toString();
   }

   public Target getTargetMethod(MethodNode a) {
      return a.getTarget().getTargetMethod(a);
   }

   MethodNode findMethod(MethodNode a, AnnotationNode a) {
      Deque<String> a = new LinkedList();
      a.add(a.name);
      if (a != null) {
         List<String> a = (List)Annotations.getValue(a, "aliases");
         if (a != null) {
            a.addAll(a);
         }
      }

      return a.getTarget().findMethod(a, a.desc);
   }

   MethodNode findRemappedMethod(MethodNode a) {
      RemapperChain a = a.getEnvironment().getRemappers();
      String a = a.mapMethodName(a.getTarget().getClassRef(), a.name, a.desc);
      if (a.equals(a.name)) {
         return null;
      } else {
         Deque<String> a = new LinkedList();
         a.add(a);
         return a.getTarget().findAliasedMethod(a, a.desc);
      }
   }

   FieldNode findField(FieldNode a, AnnotationNode a) {
      Deque<String> a = new LinkedList();
      a.add(a.name);
      if (a != null) {
         List<String> a = (List)Annotations.getValue(a, "aliases");
         if (a != null) {
            a.addAll(a);
         }
      }

      return a.getTarget().findAliasedField(a, a.desc);
   }

   FieldNode findRemappedField(FieldNode a) {
      RemapperChain a = a.getEnvironment().getRemappers();
      String a = a.mapFieldName(a.getTarget().getClassRef(), a.name, a.desc);
      if (a.equals(a.name)) {
         return null;
      } else {
         Deque<String> a = new LinkedList();
         a.add(a);
         return a.getTarget().findAliasedField(a, a.desc);
      }
   }

   protected void requireVersion(int a) {
      a.minRequiredClassVersion = Math.max(a.minRequiredClassVersion, a);
      if (a > MixinEnvironment.getCompatibilityLevel().classVersion()) {
         throw new InvalidMixinException(a, "Unsupported mixin class version " + a);
      }
   }

   public Extensions getExtensions() {
      return a.targetClass.getExtensions();
   }

   public IMixinInfo getMixin() {
      return a.mixin;
   }

   MixinInfo getInfo() {
      return a.mixin;
   }

   public int getPriority() {
      return a.mixin.getPriority();
   }

   public Set<String> getInterfaces() {
      return a.mixin.getInterfaces();
   }

   public Collection<MethodNode> getShadowMethods() {
      return a.shadowMethods;
   }

   public List<MethodNode> getMethods() {
      return a.classNode.methods;
   }

   public Set<Entry<FieldNode, ClassInfo.Field>> getShadowFields() {
      return a.shadowFields.entrySet();
   }

   public List<FieldNode> getFields() {
      return a.classNode.fields;
   }

   public Level getLoggingLevel() {
      return a.mixin.getLoggingLevel();
   }

   public boolean shouldSetSourceFile() {
      return a.mixin.getParent().shouldSetSourceFile();
   }

   public String getSourceFile() {
      return a.classNode.sourceFile;
   }

   public IReferenceMapper getReferenceMapper() {
      return a.mixin.getParent().getReferenceMapper();
   }

   public void preApply(String a, ClassNode a) {
      a.mixin.preApply(a, a);
   }

   public void postApply(String a, ClassNode a) {
      try {
         a.injectorGroups.validateAll();
      } catch (InjectionValidationException var5) {
         InjectorGroupInfo a = var5.getGroup();
         throw new InjectionError(String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", a, a.mixin, var5.getMessage()));
      }

      a.mixin.postApply(a, a);
   }

   public String getUniqueName(MethodNode a, boolean a) {
      return a.getTarget().getUniqueName(a, a);
   }

   public String getUniqueName(FieldNode a) {
      return a.getTarget().getUniqueName(a);
   }

   public void prepareInjections() {
      a.injectors.clear();
      Iterator var1 = a.mergedMethods.iterator();

      while(var1.hasNext()) {
         MethodNode a = (MethodNode)var1.next();
         InjectionInfo a = InjectionInfo.parse(a, a);
         if (a != null) {
            if (a.isValid()) {
               a.prepare();
               a.injectors.add(a);
            }

            a.visibleAnnotations.remove(a.getAnnotation());
         }
      }

   }

   public void applyInjections() {
      Iterator var1 = a.injectors.iterator();

      InjectionInfo a;
      while(var1.hasNext()) {
         a = (InjectionInfo)var1.next();
         a.inject();
      }

      var1 = a.injectors.iterator();

      while(var1.hasNext()) {
         a = (InjectionInfo)var1.next();
         a.postInject();
      }

      a.injectors.clear();
   }

   public List<MethodNode> generateAccessors() {
      Iterator var1 = a.accessors.iterator();

      while(var1.hasNext()) {
         AccessorInfo a = (AccessorInfo)var1.next();
         a.locate();
      }

      List<MethodNode> a = new ArrayList();
      Iterator var6 = a.accessors.iterator();

      while(var6.hasNext()) {
         AccessorInfo a = (AccessorInfo)var6.next();
         MethodNode a = a.generate();
         a.getTarget().addMixinMethod(a);
         a.add(a);
      }

      return a;
   }
}

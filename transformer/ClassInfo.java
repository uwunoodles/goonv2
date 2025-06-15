package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.ClassSignature;
import org.spongepowered.asm.util.perf.Profiler;

public final class ClassInfo {
   public static final int INCLUDE_PRIVATE = 2;
   public static final int INCLUDE_STATIC = 8;
   public static final int INCLUDE_ALL = 10;
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final Profiler profiler = MixinEnvironment.getProfiler();
   private static final String JAVA_LANG_OBJECT = "java/lang/Object";
   private static final Map<String, ClassInfo> cache = new HashMap();
   private static final ClassInfo OBJECT = new ClassInfo();
   private final String name;
   private final String superName;
   private final String outerName;
   private final boolean isProbablyStatic;
   private final Set<String> interfaces;
   private final Set<ClassInfo.Method> methods;
   private final Set<ClassInfo.Field> fields;
   private final Set<MixinInfo> mixins = new HashSet();
   private final Map<ClassInfo, ClassInfo> correspondingTypes = new HashMap();
   private final MixinInfo mixin;
   private final MethodMapper methodMapper;
   private final boolean isMixin;
   private final boolean isInterface;
   private final int access;
   private ClassInfo superClass;
   private ClassInfo outerClass;
   private ClassSignature signature;

   private ClassInfo() {
      a.name = "java/lang/Object";
      a.superName = null;
      a.outerName = null;
      a.isProbablyStatic = true;
      a.methods = ImmutableSet.of(new ClassInfo.Method("getClass", "()Ljava/lang/Class;"), new ClassInfo.Method("hashCode", "()I"), new ClassInfo.Method("equals", "(Ljava/lang/Object;)Z"), new ClassInfo.Method("clone", "()Ljava/lang/Object;"), new ClassInfo.Method("toString", "()Ljava/lang/String;"), new ClassInfo.Method("notify", "()V"), new ClassInfo.Method[]{new ClassInfo.Method("notifyAll", "()V"), new ClassInfo.Method("wait", "(J)V"), new ClassInfo.Method("wait", "(JI)V"), new ClassInfo.Method("wait", "()V"), new ClassInfo.Method("finalize", "()V")});
      a.fields = Collections.emptySet();
      a.isInterface = false;
      a.interfaces = Collections.emptySet();
      a.access = 1;
      a.isMixin = false;
      a.mixin = null;
      a.methodMapper = null;
   }

   private ClassInfo(ClassNode a) {
      Profiler.Section a = profiler.begin(1, (String)"class.meta");

      try {
         a.name = a.name;
         a.superName = a.superName != null ? a.superName : "java/lang/Object";
         a.methods = new HashSet();
         a.fields = new HashSet();
         a.isInterface = (a.access & 512) != 0;
         a.interfaces = new HashSet();
         a.access = a.access;
         a.isMixin = a instanceof MixinInfo.MixinClassNode;
         a.mixin = a.isMixin ? ((MixinInfo.MixinClassNode)a).getMixin() : null;
         a.interfaces.addAll(a.interfaces);
         Iterator var3 = a.methods.iterator();

         while(var3.hasNext()) {
            MethodNode a = (MethodNode)var3.next();
            a.addMethod(a, a.isMixin);
         }

         boolean a = true;
         String a = a.outerClass;

         FieldNode a;
         for(Iterator var5 = a.fields.iterator(); var5.hasNext(); a.fields.add(new ClassInfo.Field(a, a.isMixin))) {
            a = (FieldNode)var5.next();
            if ((a.access & 4096) != 0 && a.name.startsWith("this$")) {
               a = false;
               if (a == null) {
                  a = a.desc;
                  if (a != null && a.startsWith("L")) {
                     a = a.substring(1, a.length() - 1);
                  }
               }
            }
         }

         a.isProbablyStatic = a;
         a.outerName = a;
         a.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), a);
         a.signature = ClassSignature.ofLazy(a);
      } finally {
         a.end();
      }

   }

   void addInterface(String a) {
      a.interfaces.add(a);
      a.getSignature().addInterface(a);
   }

   void addMethod(MethodNode a) {
      a.addMethod(a, true);
   }

   private void addMethod(MethodNode a, boolean a) {
      if (!a.name.startsWith("<")) {
         a.methods.add(new ClassInfo.Method(a, a));
      }

   }

   void addMixin(MixinInfo a) {
      if (a.isMixin) {
         throw new IllegalArgumentException("Cannot add target " + a.name + " for " + a.getClassName() + " because the target is a mixin");
      } else {
         a.mixins.add(a);
      }
   }

   public Set<MixinInfo> getMixins() {
      return Collections.unmodifiableSet(a.mixins);
   }

   public boolean isMixin() {
      return a.isMixin;
   }

   public boolean isPublic() {
      return (a.access & 1) != 0;
   }

   public boolean isAbstract() {
      return (a.access & 1024) != 0;
   }

   public boolean isSynthetic() {
      return (a.access & 4096) != 0;
   }

   public boolean isProbablyStatic() {
      return a.isProbablyStatic;
   }

   public boolean isInner() {
      return a.outerName != null;
   }

   public boolean isInterface() {
      return a.isInterface;
   }

   public Set<String> getInterfaces() {
      return Collections.unmodifiableSet(a.interfaces);
   }

   public String toString() {
      return a.name;
   }

   public MethodMapper getMethodMapper() {
      return a.methodMapper;
   }

   public int getAccess() {
      return a.access;
   }

   public String getName() {
      return a.name;
   }

   public String getClassName() {
      return a.name.replace('/', '.');
   }

   public String getSuperName() {
      return a.superName;
   }

   public ClassInfo getSuperClass() {
      if (a.superClass == null && a.superName != null) {
         a.superClass = forName(a.superName);
      }

      return a.superClass;
   }

   public String getOuterName() {
      return a.outerName;
   }

   public ClassInfo getOuterClass() {
      if (a.outerClass == null && a.outerName != null) {
         a.outerClass = forName(a.outerName);
      }

      return a.outerClass;
   }

   public ClassSignature getSignature() {
      return a.signature.wake();
   }

   List<ClassInfo> getTargets() {
      if (a.mixin != null) {
         List<ClassInfo> a = new ArrayList();
         a.add(a);
         a.addAll(a.mixin.getTargets());
         return a;
      } else {
         return ImmutableList.of(a);
      }
   }

   public Set<ClassInfo.Method> getMethods() {
      return Collections.unmodifiableSet(a.methods);
   }

   public Set<ClassInfo.Method> getInterfaceMethods(boolean a) {
      Set<ClassInfo.Method> a = new HashSet();
      ClassInfo a = a.addMethodsRecursive(a, a);
      if (!a.isInterface) {
         while(a != null && a != OBJECT) {
            a = a.addMethodsRecursive(a, a);
         }
      }

      Iterator a = a.iterator();

      while(a.hasNext()) {
         if (!((ClassInfo.Method)a.next()).isAbstract()) {
            a.remove();
         }
      }

      return Collections.unmodifiableSet(a);
   }

   private ClassInfo addMethodsRecursive(Set<ClassInfo.Method> a, boolean a) {
      Iterator var3;
      ClassInfo.Method a;
      if (a.isInterface) {
         for(var3 = a.methods.iterator(); var3.hasNext(); a.add(a)) {
            a = (ClassInfo.Method)var3.next();
            if (!a.isAbstract()) {
               a.remove(a);
            }
         }
      } else if (!a.isMixin && a) {
         var3 = a.mixins.iterator();

         while(var3.hasNext()) {
            MixinInfo a = (MixinInfo)var3.next();
            a.getClassInfo().addMethodsRecursive(a, a);
         }
      }

      var3 = a.interfaces.iterator();

      while(var3.hasNext()) {
         String a = (String)var3.next();
         forName(a).addMethodsRecursive(a, a);
      }

      return a.getSuperClass();
   }

   public boolean hasSuperClass(String a) {
      return a.hasSuperClass(a, ClassInfo.Traversal.NONE);
   }

   public boolean hasSuperClass(String a, ClassInfo.Traversal a) {
      if ("java/lang/Object".equals(a)) {
         return true;
      } else {
         return a.findSuperClass(a, a) != null;
      }
   }

   public boolean hasSuperClass(ClassInfo a) {
      return a.hasSuperClass(a, ClassInfo.Traversal.NONE, false);
   }

   public boolean hasSuperClass(ClassInfo a, ClassInfo.Traversal a) {
      return a.hasSuperClass(a, a, false);
   }

   public boolean hasSuperClass(ClassInfo a, ClassInfo.Traversal a, boolean a) {
      if (OBJECT == a) {
         return true;
      } else {
         return a.findSuperClass(a.name, a, a) != null;
      }
   }

   public ClassInfo findSuperClass(String a) {
      return a.findSuperClass(a, ClassInfo.Traversal.NONE);
   }

   public ClassInfo findSuperClass(String a, ClassInfo.Traversal a) {
      return a.findSuperClass(a, a, false, new HashSet());
   }

   public ClassInfo findSuperClass(String a, ClassInfo.Traversal a, boolean a) {
      return OBJECT.name.equals(a) ? null : a.findSuperClass(a, a, a, new HashSet());
   }

   private ClassInfo findSuperClass(String a, ClassInfo.Traversal a, boolean a, Set<String> a) {
      ClassInfo a = a.getSuperClass();
      Iterator var6;
      if (a != null) {
         var6 = a.getTargets().iterator();

         while(var6.hasNext()) {
            ClassInfo a = (ClassInfo)var6.next();
            if (a.equals(a.getName())) {
               return a;
            }

            ClassInfo a = a.findSuperClass(a, a.next(), a, a);
            if (a != null) {
               return a;
            }
         }
      }

      if (a) {
         ClassInfo a = a.findInterface(a);
         if (a != null) {
            return a;
         }
      }

      if (a.canTraverse()) {
         var6 = a.mixins.iterator();

         while(var6.hasNext()) {
            MixinInfo a = (MixinInfo)var6.next();
            String a = a.getClassName();
            if (!a.contains(a)) {
               a.add(a);
               ClassInfo a = a.getClassInfo();
               if (a.equals(a.getName())) {
                  return a;
               }

               ClassInfo a = a.findSuperClass(a, ClassInfo.Traversal.ALL, a, a);
               if (a != null) {
                  return a;
               }
            }
         }
      }

      return null;
   }

   private ClassInfo findInterface(String a) {
      Iterator var2 = a.getInterfaces().iterator();

      ClassInfo a;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         String a = (String)var2.next();
         ClassInfo a = forName(a);
         if (a.equals(a)) {
            return a;
         }

         a = a.findInterface(a);
      } while(a == null);

      return a;
   }

   ClassInfo findCorrespondingType(ClassInfo a) {
      if (a != null && a.isMixin && !a.isMixin) {
         ClassInfo a = (ClassInfo)a.correspondingTypes.get(a);
         if (a == null) {
            a = a.findSuperTypeForMixin(a);
            a.correspondingTypes.put(a, a);
         }

         return a;
      } else {
         return null;
      }
   }

   private ClassInfo findSuperTypeForMixin(ClassInfo a) {
      for(ClassInfo a = a; a != null && a != OBJECT; a = a.getSuperClass()) {
         Iterator var3 = a.mixins.iterator();

         while(var3.hasNext()) {
            MixinInfo a = (MixinInfo)var3.next();
            if (a.getClassInfo().equals(a)) {
               return a;
            }
         }
      }

      return null;
   }

   public boolean hasMixinInHierarchy() {
      if (!a.isMixin) {
         return false;
      } else {
         for(ClassInfo a = a.getSuperClass(); a != null && a != OBJECT; a = a.getSuperClass()) {
            if (a.isMixin) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean hasMixinTargetInHierarchy() {
      if (a.isMixin) {
         return false;
      } else {
         for(ClassInfo a = a.getSuperClass(); a != null && a != OBJECT; a = a.getSuperClass()) {
            if (a.mixins.size() > 0) {
               return true;
            }
         }

         return false;
      }
   }

   public ClassInfo.Method findMethodInHierarchy(MethodNode a, ClassInfo.SearchType a) {
      return a.findMethodInHierarchy(a.name, a.desc, a, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Method findMethodInHierarchy(MethodNode a, ClassInfo.SearchType a, int a) {
      return a.findMethodInHierarchy(a.name, a.desc, a, ClassInfo.Traversal.NONE, a);
   }

   public ClassInfo.Method findMethodInHierarchy(MethodInsnNode a, ClassInfo.SearchType a) {
      return a.findMethodInHierarchy(a.name, a.desc, a, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Method findMethodInHierarchy(MethodInsnNode a, ClassInfo.SearchType a, int a) {
      return a.findMethodInHierarchy(a.name, a.desc, a, ClassInfo.Traversal.NONE, a);
   }

   public ClassInfo.Method findMethodInHierarchy(String a, String a, ClassInfo.SearchType a) {
      return a.findMethodInHierarchy(a, a, a, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Method findMethodInHierarchy(String a, String a, ClassInfo.SearchType a, ClassInfo.Traversal a) {
      return a.findMethodInHierarchy(a, a, a, a, 0);
   }

   public ClassInfo.Method findMethodInHierarchy(String a, String a, ClassInfo.SearchType a, ClassInfo.Traversal a, int a) {
      return (ClassInfo.Method)a.findInHierarchy(a, a, a, a, a, ClassInfo.Member.Type.METHOD);
   }

   public ClassInfo.Field findFieldInHierarchy(FieldNode a, ClassInfo.SearchType a) {
      return a.findFieldInHierarchy(a.name, a.desc, a, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Field findFieldInHierarchy(FieldNode a, ClassInfo.SearchType a, int a) {
      return a.findFieldInHierarchy(a.name, a.desc, a, ClassInfo.Traversal.NONE, a);
   }

   public ClassInfo.Field findFieldInHierarchy(FieldInsnNode a, ClassInfo.SearchType a) {
      return a.findFieldInHierarchy(a.name, a.desc, a, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Field findFieldInHierarchy(FieldInsnNode a, ClassInfo.SearchType a, int a) {
      return a.findFieldInHierarchy(a.name, a.desc, a, ClassInfo.Traversal.NONE, a);
   }

   public ClassInfo.Field findFieldInHierarchy(String a, String a, ClassInfo.SearchType a) {
      return a.findFieldInHierarchy(a, a, a, ClassInfo.Traversal.NONE);
   }

   public ClassInfo.Field findFieldInHierarchy(String a, String a, ClassInfo.SearchType a, ClassInfo.Traversal a) {
      return a.findFieldInHierarchy(a, a, a, a, 0);
   }

   public ClassInfo.Field findFieldInHierarchy(String a, String a, ClassInfo.SearchType a, ClassInfo.Traversal a, int a) {
      return (ClassInfo.Field)a.findInHierarchy(a, a, a, a, a, ClassInfo.Member.Type.FIELD);
   }

   private <M extends ClassInfo.Member> M findInHierarchy(String a, String a, ClassInfo.SearchType a, ClassInfo.Traversal a, int a, ClassInfo.Member.Type a) {
      Iterator var8;
      ClassInfo.Member a;
      if (a == ClassInfo.SearchType.ALL_CLASSES) {
         M a = a.findMember(a, a, a, a);
         if (a != null) {
            return a;
         }

         if (a.canTraverse()) {
            var8 = a.mixins.iterator();

            while(var8.hasNext()) {
               MixinInfo a = (MixinInfo)var8.next();
               a = a.getClassInfo().findMember(a, a, a, a);
               if (a != null) {
                  return a.cloneMember(a);
               }
            }
         }
      }

      ClassInfo a = a.getSuperClass();
      if (a != null) {
         var8 = a.getTargets().iterator();

         while(var8.hasNext()) {
            ClassInfo a = (ClassInfo)var8.next();
            a = a.findInHierarchy(a, a, ClassInfo.SearchType.ALL_CLASSES, a.next(), a & -3, a);
            if (a != null) {
               return a;
            }
         }
      }

      if (a == ClassInfo.Member.Type.METHOD && (a.isInterface || MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())) {
         var8 = a.interfaces.iterator();

         while(var8.hasNext()) {
            String a = (String)var8.next();
            ClassInfo a = forName(a);
            if (a == null) {
               logger.debug("Failed to resolve declared interface {} on {}", new Object[]{a, a.name});
            } else {
               M a = a.findInHierarchy(a, a, ClassInfo.SearchType.ALL_CLASSES, a.next(), a & -3, a);
               if (a != null) {
                  return (ClassInfo.Member)(a.isInterface ? a : new ClassInfo.InterfaceMethod(a));
               }
            }
         }
      }

      return null;
   }

   private <M extends ClassInfo.Member> M cloneMember(M a) {
      return (ClassInfo.Member)(a instanceof ClassInfo.Method ? new ClassInfo.Method(a) : new ClassInfo.Field(a));
   }

   public ClassInfo.Method findMethod(MethodNode a) {
      return a.findMethod(a.name, a.desc, a.access);
   }

   public ClassInfo.Method findMethod(MethodNode a, int a) {
      return a.findMethod(a.name, a.desc, a);
   }

   public ClassInfo.Method findMethod(MethodInsnNode a) {
      return a.findMethod(a.name, a.desc, 0);
   }

   public ClassInfo.Method findMethod(MethodInsnNode a, int a) {
      return a.findMethod(a.name, a.desc, a);
   }

   public ClassInfo.Method findMethod(String a, String a, int a) {
      return (ClassInfo.Method)a.findMember(a, a, a, ClassInfo.Member.Type.METHOD);
   }

   public ClassInfo.Field findField(FieldNode a) {
      return a.findField(a.name, a.desc, a.access);
   }

   public ClassInfo.Field findField(FieldInsnNode a, int a) {
      return a.findField(a.name, a.desc, a);
   }

   public ClassInfo.Field findField(String a, String a, int a) {
      return (ClassInfo.Field)a.findMember(a, a, a, ClassInfo.Member.Type.FIELD);
   }

   private <M extends ClassInfo.Member> M findMember(String a, String a, int a, ClassInfo.Member.Type a) {
      Set<M> a = a == ClassInfo.Member.Type.METHOD ? a.methods : a.fields;
      Iterator var6 = a.iterator();

      ClassInfo.Member a;
      do {
         if (!var6.hasNext()) {
            return null;
         }

         a = (ClassInfo.Member)var6.next();
      } while(!a.equals(a, a) || !a.matchesFlags(a));

      return a;
   }

   public boolean equals(Object a) {
      return !(a instanceof ClassInfo) ? false : ((ClassInfo)a).name.equals(a.name);
   }

   public int hashCode() {
      return a.name.hashCode();
   }

   static ClassInfo fromClassNode(ClassNode a) {
      ClassInfo a = (ClassInfo)cache.get(a.name);
      if (a == null) {
         a = new ClassInfo(a);
         cache.put(a.name, a);
      }

      return a;
   }

   public static ClassInfo forName(String a) {
      a = a.replace('.', '/');
      ClassInfo a = (ClassInfo)cache.get(a);
      if (a == null) {
         try {
            ClassNode a = MixinService.getService().getBytecodeProvider().getClassNode(a);
            a = new ClassInfo(a);
         } catch (Exception var3) {
            logger.catching(Level.TRACE, var3);
            logger.warn("Error loading class: {} ({}: {})", new Object[]{a, var3.getClass().getName(), var3.getMessage()});
         }

         cache.put(a, a);
         logger.trace("Added class metadata for {} to metadata cache", new Object[]{a});
      }

      return a;
   }

   public static ClassInfo forType(org.spongepowered.asm.lib.Type a) {
      if (a.getSort() == 9) {
         return forType(a.getElementType());
      } else {
         return a.getSort() < 9 ? null : forName(a.getClassName().replace('.', '/'));
      }
   }

   public static ClassInfo getCommonSuperClass(String a, String a) {
      return a != null && a != null ? getCommonSuperClass(forName(a), forName(a)) : OBJECT;
   }

   public static ClassInfo getCommonSuperClass(org.spongepowered.asm.lib.Type a, org.spongepowered.asm.lib.Type a) {
      return a != null && a != null && a.getSort() == 10 && a.getSort() == 10 ? getCommonSuperClass(forType(a), forType(a)) : OBJECT;
   }

   private static ClassInfo getCommonSuperClass(ClassInfo a, ClassInfo a) {
      return getCommonSuperClass(a, a, false);
   }

   public static ClassInfo getCommonSuperClassOrInterface(String a, String a) {
      return a != null && a != null ? getCommonSuperClassOrInterface(forName(a), forName(a)) : OBJECT;
   }

   public static ClassInfo getCommonSuperClassOrInterface(org.spongepowered.asm.lib.Type a, org.spongepowered.asm.lib.Type a) {
      return a != null && a != null && a.getSort() == 10 && a.getSort() == 10 ? getCommonSuperClassOrInterface(forType(a), forType(a)) : OBJECT;
   }

   public static ClassInfo getCommonSuperClassOrInterface(ClassInfo a, ClassInfo a) {
      return getCommonSuperClass(a, a, true);
   }

   private static ClassInfo getCommonSuperClass(ClassInfo a, ClassInfo a, boolean a) {
      if (a.hasSuperClass(a, ClassInfo.Traversal.NONE, a)) {
         return a;
      } else if (a.hasSuperClass(a, ClassInfo.Traversal.NONE, a)) {
         return a;
      } else if (!a.isInterface() && !a.isInterface()) {
         do {
            a = a.getSuperClass();
            if (a == null) {
               return OBJECT;
            }
         } while(!a.hasSuperClass(a, ClassInfo.Traversal.NONE, a));

         return a;
      } else {
         return OBJECT;
      }
   }

   static {
      cache.put("java/lang/Object", OBJECT);
   }

   class Field extends ClassInfo.Member {
      public Field(ClassInfo.Member axx) {
         super(axx);
      }

      public Field(FieldNode axx) {
         this(axx, false);
      }

      public Field(FieldNode axx, boolean axxx) {
         super(ClassInfo.Member.Type.FIELD, axx.name, axx.desc, axx.access, axxx);
         a.setUnique(Annotations.getVisible(axx, Unique.class) != null);
         if (Annotations.getVisible(axx, Shadow.class) != null) {
            boolean axxxx = Annotations.getVisible(axx, Final.class) != null;
            boolean axxxxx = Annotations.getVisible(axx, Mutable.class) != null;
            a.setDecoratedFinal(axxxx, axxxxx);
         }

      }

      public Field(String axxxx, String ax, int axxx) {
         super(ClassInfo.Member.Type.FIELD, axxxx, ax, axxx, false);
      }

      public Field(String axx, String axxx, int axxxx, boolean axxxxx) {
         super(ClassInfo.Member.Type.FIELD, axx, axxx, axxxx, axxxxx);
      }

      public ClassInfo getOwner() {
         return ClassInfo.this;
      }

      public boolean equals(Object ax) {
         return !(ax instanceof ClassInfo.Field) ? false : super.equals(ax);
      }

      protected String getDisplayFormat() {
         return "%s:%s";
      }
   }

   public class InterfaceMethod extends ClassInfo.Method {
      private final ClassInfo owner;

      public InterfaceMethod(ClassInfo.Member axx) {
         super((ClassInfo.Member)axx);
         a.owner = axx.getOwner();
      }

      public ClassInfo getOwner() {
         return a.owner;
      }

      public ClassInfo getImplementor() {
         return ClassInfo.this;
      }
   }

   public class Method extends ClassInfo.Member {
      private final List<ClassInfo.FrameData> frames;
      private boolean isAccessor;

      public Method(ClassInfo.Member axx) {
         super(axx);
         a.frames = axx instanceof ClassInfo.Method ? ((ClassInfo.Method)axx).frames : null;
      }

      public Method(MethodNode axx) {
         this(axx, false);
         a.setUnique(Annotations.getVisible(axx, Unique.class) != null);
         a.isAccessor = Annotations.getSingleVisible(axx, Accessor.class, Invoker.class) != null;
      }

      public Method(MethodNode axxx, boolean ax) {
         super(ClassInfo.Member.Type.METHOD, axxx.name, axxx.desc, axxx.access, ax);
         a.frames = a.gatherFrames(axxx);
         a.setUnique(Annotations.getVisible(axxx, Unique.class) != null);
         a.isAccessor = Annotations.getSingleVisible(axxx, Accessor.class, Invoker.class) != null;
      }

      public Method(String axxx, String ax) {
         super(ClassInfo.Member.Type.METHOD, axxx, ax, 1, false);
         a.frames = null;
      }

      public Method(String axxxx, String ax, int axxx) {
         super(ClassInfo.Member.Type.METHOD, axxxx, ax, axxx, false);
         a.frames = null;
      }

      public Method(String axx, String axxx, int axxxx, boolean axxxxx) {
         super(ClassInfo.Member.Type.METHOD, axx, axxx, axxxx, axxxxx);
         a.frames = null;
      }

      private List<ClassInfo.FrameData> gatherFrames(MethodNode axx) {
         List<ClassInfo.FrameData> axxxx = new ArrayList();
         ListIterator ax = axx.instructions.iterator();

         while(ax.hasNext()) {
            AbstractInsnNode axxx = (AbstractInsnNode)ax.next();
            if (axxx instanceof FrameNode) {
               axxxx.add(new ClassInfo.FrameData(axx.instructions.indexOf(axxx), (FrameNode)axxx));
            }
         }

         return axxxx;
      }

      public List<ClassInfo.FrameData> getFrames() {
         return a.frames;
      }

      public ClassInfo getOwner() {
         return ClassInfo.this;
      }

      public boolean isAccessor() {
         return a.isAccessor;
      }

      public boolean equals(Object ax) {
         return !(ax instanceof ClassInfo.Method) ? false : super.equals(ax);
      }
   }

   abstract static class Member {
      private final ClassInfo.Member.Type type;
      private final String memberName;
      private final String memberDesc;
      private final boolean isInjected;
      private final int modifiers;
      private String currentName;
      private String currentDesc;
      private boolean decoratedFinal;
      private boolean decoratedMutable;
      private boolean unique;

      protected Member(ClassInfo.Member a) {
         this(a.type, a.memberName, a.memberDesc, a.modifiers, a.isInjected);
         a.currentName = a.currentName;
         a.currentDesc = a.currentDesc;
         a.unique = a.unique;
      }

      protected Member(ClassInfo.Member.Type a, String a, String a, int a) {
         this(a, a, a, a, false);
      }

      protected Member(ClassInfo.Member.Type a, String a, String a, int a, boolean a) {
         a.type = a;
         a.memberName = a;
         a.memberDesc = a;
         a.isInjected = a;
         a.currentName = a;
         a.currentDesc = a;
         a.modifiers = a;
      }

      public String getOriginalName() {
         return a.memberName;
      }

      public String getName() {
         return a.currentName;
      }

      public String getOriginalDesc() {
         return a.memberDesc;
      }

      public String getDesc() {
         return a.currentDesc;
      }

      public boolean isInjected() {
         return a.isInjected;
      }

      public boolean isRenamed() {
         return !a.currentName.equals(a.memberName);
      }

      public boolean isRemapped() {
         return !a.currentDesc.equals(a.memberDesc);
      }

      public boolean isPrivate() {
         return (a.modifiers & 2) != 0;
      }

      public boolean isStatic() {
         return (a.modifiers & 8) != 0;
      }

      public boolean isAbstract() {
         return (a.modifiers & 1024) != 0;
      }

      public boolean isFinal() {
         return (a.modifiers & 16) != 0;
      }

      public boolean isSynthetic() {
         return (a.modifiers & 4096) != 0;
      }

      public boolean isUnique() {
         return a.unique;
      }

      public void setUnique(boolean a) {
         a.unique = a;
      }

      public boolean isDecoratedFinal() {
         return a.decoratedFinal;
      }

      public boolean isDecoratedMutable() {
         return a.decoratedMutable;
      }

      public void setDecoratedFinal(boolean a, boolean a) {
         a.decoratedFinal = a;
         a.decoratedMutable = a;
      }

      public boolean matchesFlags(int a) {
         return ((~a.modifiers | a & 2) & 2) != 0 && ((~a.modifiers | a & 8) & 8) != 0;
      }

      public abstract ClassInfo getOwner();

      public ClassInfo getImplementor() {
         return a.getOwner();
      }

      public int getAccess() {
         return a.modifiers;
      }

      public String renameTo(String a) {
         a.currentName = a;
         return a;
      }

      public String remapTo(String a) {
         a.currentDesc = a;
         return a;
      }

      public boolean equals(String a, String a) {
         return (a.memberName.equals(a) || a.currentName.equals(a)) && (a.memberDesc.equals(a) || a.currentDesc.equals(a));
      }

      public boolean equals(Object a) {
         if (!(a instanceof ClassInfo.Member)) {
            return false;
         } else {
            ClassInfo.Member a = (ClassInfo.Member)a;
            return (a.memberName.equals(a.memberName) || a.currentName.equals(a.currentName)) && (a.memberDesc.equals(a.memberDesc) || a.currentDesc.equals(a.currentDesc));
         }
      }

      public int hashCode() {
         return a.toString().hashCode();
      }

      public String toString() {
         return String.format(a.getDisplayFormat(), a.memberName, a.memberDesc);
      }

      protected String getDisplayFormat() {
         return "%s%s";
      }

      static enum Type {
         METHOD,
         FIELD;
      }
   }

   public static class FrameData {
      private static final String[] FRAMETYPES = new String[]{"NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1"};
      public final int index;
      public final int type;
      public final int locals;

      FrameData(int a, int a, int a) {
         a.index = a;
         a.type = a;
         a.locals = a;
      }

      FrameData(int a, FrameNode a) {
         a.index = a;
         a.type = a.type;
         a.locals = a.local != null ? a.local.size() : 0;
      }

      public String toString() {
         return String.format("FrameData[index=%d, type=%s, locals=%d]", a.index, FRAMETYPES[a.type + 1], a.locals);
      }
   }

   public static enum Traversal {
      NONE((ClassInfo.Traversal)null, false, ClassInfo.SearchType.SUPER_CLASSES_ONLY),
      ALL((ClassInfo.Traversal)null, true, ClassInfo.SearchType.ALL_CLASSES),
      IMMEDIATE(NONE, true, ClassInfo.SearchType.SUPER_CLASSES_ONLY),
      SUPER(ALL, false, ClassInfo.SearchType.SUPER_CLASSES_ONLY);

      private final ClassInfo.Traversal next;
      private final boolean traverse;
      private final ClassInfo.SearchType searchType;

      private Traversal(ClassInfo.Traversal a, boolean a, ClassInfo.SearchType a) {
         a.next = a != null ? a : a;
         a.traverse = a;
         a.searchType = a;
      }

      public ClassInfo.Traversal next() {
         return a.next;
      }

      public boolean canTraverse() {
         return a.traverse;
      }

      public ClassInfo.SearchType getSearchType() {
         return a.searchType;
      }
   }

   public static enum SearchType {
      ALL_CLASSES,
      SUPER_CLASSES_ONLY;
   }
}

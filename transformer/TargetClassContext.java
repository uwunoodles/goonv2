package org.spongepowered.asm.mixin.transformer;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;

class TargetClassContext extends ClassContext implements ITargetClassContext {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final MixinEnvironment env;
   private final Extensions extensions;
   private final String sessionId;
   private final String className;
   private final ClassNode classNode;
   private final ClassInfo classInfo;
   private final SourceMap sourceMap;
   private final ClassSignature signature;
   private final SortedSet<MixinInfo> mixins;
   private final Map<String, Target> targetMethods = new HashMap();
   private final Set<MethodNode> mixinMethods = new HashSet();
   private int nextUniqueMethodIndex;
   private int nextUniqueFieldIndex;
   private boolean applied;
   private boolean forceExport;

   TargetClassContext(MixinEnvironment a, Extensions a, String a, String a, ClassNode a, SortedSet<MixinInfo> a) {
      a.env = a;
      a.extensions = a;
      a.sessionId = a;
      a.className = a;
      a.classNode = a;
      a.classInfo = ClassInfo.fromClassNode(a);
      a.signature = a.classInfo.getSignature();
      a.mixins = a;
      a.sourceMap = new SourceMap(a.sourceFile);
      a.sourceMap.addFile(a.classNode);
   }

   public String toString() {
      return a.className;
   }

   boolean isApplied() {
      return a.applied;
   }

   boolean isExportForced() {
      return a.forceExport;
   }

   Extensions getExtensions() {
      return a.extensions;
   }

   String getSessionId() {
      return a.sessionId;
   }

   String getClassRef() {
      return a.classNode.name;
   }

   String getClassName() {
      return a.className;
   }

   public ClassNode getClassNode() {
      return a.classNode;
   }

   List<MethodNode> getMethods() {
      return a.classNode.methods;
   }

   List<FieldNode> getFields() {
      return a.classNode.fields;
   }

   public ClassInfo getClassInfo() {
      return a.classInfo;
   }

   SortedSet<MixinInfo> getMixins() {
      return a.mixins;
   }

   SourceMap getSourceMap() {
      return a.sourceMap;
   }

   void mergeSignature(ClassSignature a) {
      a.signature.merge(a);
   }

   void addMixinMethod(MethodNode a) {
      a.mixinMethods.add(a);
   }

   void methodMerged(MethodNode a) {
      if (!a.mixinMethods.remove(a)) {
         logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[]{a.name, a.desc, a});
      }

   }

   MethodNode findMethod(Deque<String> a, String a) {
      return a.findAliasedMethod(a, a, true);
   }

   MethodNode findAliasedMethod(Deque<String> a, String a) {
      return a.findAliasedMethod(a, a, false);
   }

   private MethodNode findAliasedMethod(Deque<String> a, String a, boolean a) {
      String a = (String)a.poll();
      if (a == null) {
         return null;
      } else {
         Iterator var5 = a.classNode.methods.iterator();

         MethodNode a;
         while(var5.hasNext()) {
            a = (MethodNode)var5.next();
            if (a.name.equals(a) && a.desc.equals(a)) {
               return a;
            }
         }

         if (a) {
            var5 = a.mixinMethods.iterator();

            while(var5.hasNext()) {
               a = (MethodNode)var5.next();
               if (a.name.equals(a) && a.desc.equals(a)) {
                  return a;
               }
            }
         }

         return a.findAliasedMethod(a, a);
      }
   }

   FieldNode findAliasedField(Deque<String> a, String a) {
      String a = (String)a.poll();
      if (a == null) {
         return null;
      } else {
         Iterator var4 = a.classNode.fields.iterator();

         FieldNode a;
         do {
            if (!var4.hasNext()) {
               return a.findAliasedField(a, a);
            }

            a = (FieldNode)var4.next();
         } while(!a.name.equals(a) || !a.desc.equals(a));

         return a;
      }
   }

   Target getTargetMethod(MethodNode a) {
      if (!a.classNode.methods.contains(a)) {
         throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
      } else {
         String a = a.name + a.desc;
         Target a = (Target)a.targetMethods.get(a);
         if (a == null) {
            a = new Target(a.classNode, a);
            a.targetMethods.put(a, a);
         }

         return a;
      }
   }

   String getUniqueName(MethodNode a, boolean a) {
      String a = Integer.toHexString(a.nextUniqueMethodIndex++);
      String a = a ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s";
      return String.format(a, a.sessionId.substring(30), a.name, a);
   }

   String getUniqueName(FieldNode a) {
      String a = Integer.toHexString(a.nextUniqueFieldIndex++);
      return String.format("fd%s$%s$%s", a.sessionId.substring(30), a.name, a);
   }

   void applyMixins() {
      if (a.applied) {
         throw new IllegalStateException("Mixins already applied to target class " + a.className);
      } else {
         a.applied = true;
         MixinApplicatorStandard a = a.createApplicator();
         a.apply(a.mixins);
         a.applySignature();
         a.upgradeMethods();
         a.checkMerges();
      }
   }

   private MixinApplicatorStandard createApplicator() {
      return (MixinApplicatorStandard)(a.classInfo.isInterface() ? new MixinApplicatorInterface(a) : new MixinApplicatorStandard(a));
   }

   private void applySignature() {
      a.getClassNode().signature = a.signature.toString();
   }

   private void checkMerges() {
      Iterator var1 = a.mixinMethods.iterator();

      while(var1.hasNext()) {
         MethodNode a = (MethodNode)var1.next();
         if (!a.name.startsWith("<")) {
            logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[]{a.name, a.desc, a});
         }
      }

   }

   void processDebugTasks() {
      if (a.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
         AnnotationNode a = Annotations.getVisible(a.classNode, Debug.class);
         if (a != null) {
            a.forceExport = Boolean.TRUE.equals(Annotations.getValue(a, "export"));
            if (Boolean.TRUE.equals(Annotations.getValue(a, "print"))) {
               Bytecode.textify((ClassNode)a.classNode, System.err);
            }
         }

         Iterator var2 = a.classNode.methods.iterator();

         while(var2.hasNext()) {
            MethodNode a = (MethodNode)var2.next();
            AnnotationNode a = Annotations.getVisible(a, Debug.class);
            if (a != null && Boolean.TRUE.equals(Annotations.getValue(a, "print"))) {
               Bytecode.textify((MethodNode)a, System.err);
            }
         }

      }
   }
}

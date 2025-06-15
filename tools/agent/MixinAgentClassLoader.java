package org.spongepowered.tools.agent;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.mixin.MixinEnvironment;

class MixinAgentClassLoader extends ClassLoader {
   private static final Logger logger = LogManager.getLogger("mixin.agent");
   private Map<Class<?>, byte[]> mixins = new HashMap();
   private Map<String, byte[]> targets = new HashMap();

   void addMixinClass(String a) {
      logger.debug("Mixin class {} added to class loader", new Object[]{a});

      try {
         byte[] a = a.materialise(a);
         Class<?> a = a.defineClass(a, a, 0, a.length);
         a.newInstance();
         a.mixins.put(a, a);
      } catch (Throwable var4) {
         logger.catching(var4);
      }

   }

   void addTargetClass(String a, byte[] a) {
      a.targets.put(a, a);
   }

   byte[] getFakeMixinBytecode(Class<?> a) {
      return (byte[])a.mixins.get(a);
   }

   byte[] getOriginalTargetBytecode(String a) {
      return (byte[])a.targets.get(a);
   }

   private byte[] materialise(String a) {
      ClassWriter a = new ClassWriter(3);
      a.visit(MixinEnvironment.getCompatibilityLevel().classVersion(), 1, a.replace('.', '/'), (String)null, Type.getInternalName(Object.class), (String[])null);
      MethodVisitor a = a.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
      a.visitCode();
      a.visitVarInsn(25, 0);
      a.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
      a.visitInsn(177);
      a.visitMaxs(1, 1);
      a.visitEnd();
      a.visitEnd();
      return a.toByteArray();
   }
}

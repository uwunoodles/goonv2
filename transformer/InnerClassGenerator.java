package org.spongepowered.asm.mixin.transformer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.commons.ClassRemapper;
import org.spongepowered.asm.lib.commons.Remapper;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.transformers.MixinClassWriter;

final class InnerClassGenerator implements IClassGenerator {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final Map<String, String> innerClassNames = new HashMap();
   private final Map<String, InnerClassGenerator.InnerClassInfo> innerClasses = new HashMap();

   public String registerInnerClass(MixinInfo a, String a, MixinTargetContext a) {
      String a = String.format("%s%s", a, a);
      String a = (String)a.innerClassNames.get(a);
      if (a == null) {
         a = getUniqueReference(a, a);
         a.innerClassNames.put(a, a);
         a.innerClasses.put(a, new InnerClassGenerator.InnerClassInfo(a, a, a, a));
         logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[]{a, a.getClassRef(), a.getTargetClassRef(), a});
      }

      return a;
   }

   public byte[] generate(String a) {
      String a = a.replace('.', '/');
      InnerClassGenerator.InnerClassInfo a = (InnerClassGenerator.InnerClassInfo)a.innerClasses.get(a);
      return a != null ? a.generate(a) : null;
   }

   private byte[] generate(InnerClassGenerator.InnerClassInfo a) {
      try {
         logger.debug("Generating mapped inner class {} (originally {})", new Object[]{a.getName(), a.getOriginalName()});
         ClassReader a = new ClassReader(a.getClassBytes());
         ClassWriter a = new MixinClassWriter(a, 0);
         a.accept(new InnerClassGenerator.InnerClassAdapter(a, a), 8);
         return a.toByteArray();
      } catch (InvalidMixinException var4) {
         throw var4;
      } catch (Exception var5) {
         logger.catching(var5);
         return null;
      }
   }

   private static String getUniqueReference(String a, MixinTargetContext a) {
      String a = a.substring(a.lastIndexOf(36) + 1);
      if (a.matches("^[0-9]+$")) {
         a = "Anonymous";
      }

      return String.format("%s$%s$%s", a.getTargetClassRef(), a, UUID.randomUUID().toString().replace("-", ""));
   }

   static class InnerClassAdapter extends ClassRemapper {
      private final InnerClassGenerator.InnerClassInfo info;

      public InnerClassAdapter(ClassVisitor a, InnerClassGenerator.InnerClassInfo a) {
         super(327680, a, a);
         a.info = a;
      }

      public void visitSource(String a, String a) {
         super.visitSource(a, a);
         AnnotationVisitor a = a.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
         a.visit("mixin", a.info.getOwner().toString());
         a.visit("name", a.info.getOriginalName().substring(a.info.getOriginalName().lastIndexOf(47) + 1));
         a.visitEnd();
      }

      public void visitInnerClass(String a, String a, String a, int a) {
         if (a.startsWith(a.info.getOriginalName() + "$")) {
            throw new InvalidMixinException(a.info.getOwner(), "Found unsupported nested inner class " + a + " in " + a.info.getOriginalName());
         } else {
            super.visitInnerClass(a, a, a, a);
         }
      }
   }

   static class InnerClassInfo extends Remapper {
      private final String name;
      private final String originalName;
      private final MixinInfo owner;
      private final MixinTargetContext target;
      private final String ownerName;
      private final String targetName;

      InnerClassInfo(String a, String a, MixinInfo a, MixinTargetContext a) {
         a.name = a;
         a.originalName = a;
         a.owner = a;
         a.ownerName = a.getClassRef();
         a.target = a;
         a.targetName = a.getTargetClassRef();
      }

      String getName() {
         return a.name;
      }

      String getOriginalName() {
         return a.originalName;
      }

      MixinInfo getOwner() {
         return a.owner;
      }

      MixinTargetContext getTarget() {
         return a.target;
      }

      String getOwnerName() {
         return a.ownerName;
      }

      String getTargetName() {
         return a.targetName;
      }

      byte[] getClassBytes() throws ClassNotFoundException, IOException {
         return MixinService.getService().getBytecodeProvider().getClassBytes(a.originalName, true);
      }

      public String mapMethodName(String a, String a, String a) {
         if (a.ownerName.equalsIgnoreCase(a)) {
            ClassInfo.Method a = a.owner.getClassInfo().findMethod(a, a, 10);
            if (a != null) {
               return a.getName();
            }
         }

         return super.mapMethodName(a, a, a);
      }

      public String map(String a) {
         if (a.originalName.equals(a)) {
            return a.name;
         } else {
            return a.ownerName.equals(a) ? a.targetName : a;
         }
      }

      public String toString() {
         return a.name;
      }
   }
}

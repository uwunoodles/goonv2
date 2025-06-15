package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.service.ILegacyClassTransformer;

public abstract class TreeTransformer implements ILegacyClassTransformer {
   private ClassReader classReader;
   private ClassNode classNode;

   protected final ClassNode readClass(byte[] a) {
      return a.readClass(a, true);
   }

   protected final ClassNode readClass(byte[] a, boolean a) {
      ClassReader a = new ClassReader(a);
      if (a) {
         a.classReader = a;
      }

      ClassNode a = new ClassNode();
      a.accept(a, 8);
      return a;
   }

   protected final byte[] writeClass(ClassNode a) {
      MixinClassWriter a;
      if (a.classReader != null && a.classNode == a) {
         a.classNode = null;
         a = new MixinClassWriter(a.classReader, 3);
         a.classReader = null;
         a.accept(a);
         return a.toByteArray();
      } else {
         a.classNode = null;
         a = new MixinClassWriter(3);
         a.accept(a);
         return a.toByteArray();
      }
   }
}

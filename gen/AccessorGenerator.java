package org.spongepowered.asm.mixin.gen;

import java.util.ArrayList;
import org.spongepowered.asm.lib.tree.MethodNode;

public abstract class AccessorGenerator {
   protected final AccessorInfo info;

   public AccessorGenerator(AccessorInfo a) {
      a.info = a;
   }

   protected final MethodNode createMethod(int a, int a) {
      MethodNode a = a.info.getMethod();
      MethodNode a = new MethodNode(327680, a.access & -1025 | 4096, a.name, a.desc, (String)null, (String[])null);
      a.visibleAnnotations = new ArrayList();
      a.visibleAnnotations.add(a.info.getAnnotation());
      a.maxLocals = a;
      a.maxStack = a;
      return a;
   }

   public abstract MethodNode generate();
}

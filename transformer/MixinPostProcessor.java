package org.spongepowered.asm.mixin.transformer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.transformers.MixinClassWriter;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.Bytecode;

class MixinPostProcessor extends TreeTransformer implements MixinConfig.IListener {
   private final Set<String> syntheticInnerClasses = new HashSet();
   private final Map<String, MixinInfo> accessorMixins = new HashMap();
   private final Set<String> loadable = new HashSet();

   public void onInit(MixinInfo a) {
      Iterator var2 = a.getSyntheticInnerClasses().iterator();

      while(var2.hasNext()) {
         String a = (String)var2.next();
         a.registerSyntheticInner(a.replace('/', '.'));
      }

   }

   public void onPrepare(MixinInfo a) {
      String a = a.getClassName();
      if (a.isLoadable()) {
         a.registerLoadable(a);
      }

      if (a.isAccessor()) {
         a.registerAccessor(a);
      }

   }

   void registerSyntheticInner(String a) {
      a.syntheticInnerClasses.add(a);
   }

   void registerLoadable(String a) {
      a.loadable.add(a);
   }

   void registerAccessor(MixinInfo a) {
      a.registerLoadable(a.getClassName());
      a.accessorMixins.put(a.getClassName(), a);
   }

   boolean canTransform(String a) {
      return a.syntheticInnerClasses.contains(a) || a.loadable.contains(a);
   }

   public String getName() {
      return a.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return true;
   }

   public byte[] transformClassBytes(String a1, String a, byte[] a) {
      if (a.syntheticInnerClasses.contains(a)) {
         return a.processSyntheticInner(a);
      } else if (a.accessorMixins.containsKey(a)) {
         MixinInfo a = (MixinInfo)a.accessorMixins.get(a);
         return a.processAccessor(a, a);
      } else {
         return a;
      }
   }

   private byte[] processSyntheticInner(byte[] a) {
      ClassReader a = new ClassReader(a);
      ClassWriter a = new MixinClassWriter(a, 0);
      ClassVisitor a = new ClassVisitor(327680, a) {
         public void visit(int axxx, int axxxx, String axxxxx, String axxxxxx, String axxxxxxx, String[] axx) {
            super.visit(axxx, axxxx | 1, axxxxx, axxxxxx, axxxxxxx, axx);
         }

         public FieldVisitor visitField(int axx, String axxx, String axxxx, String axxxxx, Object axxxxxx) {
            if ((axx & 6) == 0) {
               axx |= 1;
            }

            return super.visitField(axx, axxx, axxxx, axxxxx, axxxxxx);
         }

         public MethodVisitor visitMethod(int axx, String axxx, String axxxx, String axxxxx, String[] axxxxxx) {
            if ((axx & 6) == 0) {
               axx |= 1;
            }

            return super.visitMethod(axx, axxx, axxxx, axxxxx, axxxxxx);
         }
      };
      a.accept(a, 8);
      return a.toByteArray();
   }

   private byte[] processAccessor(byte[] a, MixinInfo a) {
      if (!MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8)) {
         return a;
      } else {
         boolean a = false;
         MixinInfo.MixinClassNode a = a.getClassNode(0);
         ClassInfo a = (ClassInfo)a.getTargets().get(0);
         Iterator a = a.mixinMethods.iterator();

         while(true) {
            MixinInfo.MixinMethodNode a;
            AnnotationNode a;
            AnnotationNode a;
            do {
               do {
                  if (!a.hasNext()) {
                     if (a) {
                        return a.writeClass(a);
                     }

                     return a;
                  }

                  a = (MixinInfo.MixinMethodNode)a.next();
               } while(!Bytecode.hasFlag((MethodNode)a, 8));

               a = a.getVisibleAnnotation(Accessor.class);
               a = a.getVisibleAnnotation(Invoker.class);
            } while(a == null && a == null);

            ClassInfo.Method a = getAccessorMethod(a, a, a);
            createProxy(a, a, a);
            a = true;
         }
      }
   }

   private static ClassInfo.Method getAccessorMethod(MixinInfo a, MethodNode a, ClassInfo a) throws MixinTransformerError {
      ClassInfo.Method a = a.getClassInfo().findMethod((MethodNode)a, 10);
      if (!a.isRenamed()) {
         throw new MixinTransformerError("Unexpected state: " + a + " loaded before " + a + " was conformed");
      } else {
         return a;
      }
   }

   private static void createProxy(MethodNode a, ClassInfo a, ClassInfo.Method a) {
      a.instructions.clear();
      Type[] a = Type.getArgumentTypes(a.desc);
      Type a = Type.getReturnType(a.desc);
      Bytecode.loadArgs(a, a.instructions, 0);
      a.instructions.add((AbstractInsnNode)(new MethodInsnNode(184, a.getName(), a.getName(), a.desc, false)));
      a.instructions.add((AbstractInsnNode)(new InsnNode(a.getOpcode(172))));
      a.maxStack = Bytecode.getFirstNonArgLocalIndex(a, false);
      a.maxLocals = 0;
   }
}

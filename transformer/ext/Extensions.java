package org.spongepowered.asm.mixin.transformer.ext;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;

public final class Extensions {
   private final MixinTransformer transformer;
   private final List<IExtension> extensions = new ArrayList();
   private final Map<Class<? extends IExtension>, IExtension> extensionMap = new HashMap();
   private final List<IClassGenerator> generators = new ArrayList();
   private final List<IClassGenerator> generatorsView;
   private final Map<Class<? extends IClassGenerator>, IClassGenerator> generatorMap;
   private List<IExtension> activeExtensions;

   public Extensions(MixinTransformer a) {
      a.generatorsView = Collections.unmodifiableList(a.generators);
      a.generatorMap = new HashMap();
      a.activeExtensions = Collections.emptyList();
      a.transformer = a;
   }

   public MixinTransformer getTransformer() {
      return a.transformer;
   }

   public void add(IExtension a) {
      a.extensions.add(a);
      a.extensionMap.put(a.getClass(), a);
   }

   public List<IExtension> getExtensions() {
      return Collections.unmodifiableList(a.extensions);
   }

   public List<IExtension> getActiveExtensions() {
      return a.activeExtensions;
   }

   public <T extends IExtension> T getExtension(Class<? extends IExtension> a) {
      return (IExtension)lookup(a, a.extensionMap, a.extensions);
   }

   public void select(MixinEnvironment a) {
      Builder<IExtension> a = ImmutableList.builder();
      Iterator var3 = a.extensions.iterator();

      while(var3.hasNext()) {
         IExtension a = (IExtension)var3.next();
         if (a.checkActive(a)) {
            a.add(a);
         }
      }

      a.activeExtensions = a.build();
   }

   public void preApply(ITargetClassContext a) {
      Iterator var2 = a.activeExtensions.iterator();

      while(var2.hasNext()) {
         IExtension a = (IExtension)var2.next();
         a.preApply(a);
      }

   }

   public void postApply(ITargetClassContext a) {
      Iterator var2 = a.activeExtensions.iterator();

      while(var2.hasNext()) {
         IExtension a = (IExtension)var2.next();
         a.postApply(a);
      }

   }

   public void export(MixinEnvironment a, String a, boolean a, byte[] a) {
      Iterator var5 = a.activeExtensions.iterator();

      while(var5.hasNext()) {
         IExtension a = (IExtension)var5.next();
         a.export(a, a, a, a);
      }

   }

   public void add(IClassGenerator a) {
      a.generators.add(a);
      a.generatorMap.put(a.getClass(), a);
   }

   public List<IClassGenerator> getGenerators() {
      return a.generatorsView;
   }

   public <T extends IClassGenerator> T getGenerator(Class<? extends IClassGenerator> a) {
      return (IClassGenerator)lookup(a, a.generatorMap, a.generators);
   }

   private static <T> T lookup(Class<? extends T> a, Map<Class<? extends T>, T> a, List<T> a) {
      T a = a.get(a);
      if (a == null) {
         Iterator var4 = a.iterator();

         while(var4.hasNext()) {
            T a = var4.next();
            if (a.isAssignableFrom(a.getClass())) {
               a = a;
               break;
            }
         }

         if (a == null) {
            throw new IllegalArgumentException("Extension for <" + a.getName() + "> could not be found");
         }

         a.put(a, a);
      }

      return a;
   }
}

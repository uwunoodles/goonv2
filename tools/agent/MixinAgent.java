package org.spongepowered.tools.agent;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;

public class MixinAgent implements IHotSwap {
   public static final byte[] ERROR_BYTECODE = new byte[]{1};
   static final MixinAgentClassLoader classLoader = new MixinAgentClassLoader();
   static final Logger logger = LogManager.getLogger("mixin.agent");
   static Instrumentation instrumentation = null;
   private static List<MixinAgent> agents = new ArrayList();
   final MixinTransformer classTransformer;

   public MixinAgent(MixinTransformer a) {
      a.classTransformer = a;
      agents.add(a);
      if (instrumentation != null) {
         a.initTransformer();
      }

   }

   private void initTransformer() {
      instrumentation.addTransformer(new MixinAgent.Transformer(), true);
   }

   public void registerMixinClass(String a) {
      classLoader.addMixinClass(a);
   }

   public void registerTargetClass(String a, byte[] a) {
      classLoader.addTargetClass(a, a);
   }

   public static void init(Instrumentation a) {
      instrumentation = a;
      if (!instrumentation.isRedefineClassesSupported()) {
         logger.error("The instrumentation doesn't support re-definition of classes");
      }

      Iterator var1 = agents.iterator();

      while(var1.hasNext()) {
         MixinAgent a = (MixinAgent)var1.next();
         a.initTransformer();
      }

   }

   public static void premain(String a, Instrumentation a) {
      System.setProperty("mixin.hotSwap", "true");
      init(a);
   }

   public static void agentmain(String a, Instrumentation a) {
      init(a);
   }

   class Transformer implements ClassFileTransformer {
      public byte[] transform(ClassLoader a1, String axxx, Class<?> axxxx, ProtectionDomain a4, byte[] axxxxx) throws IllegalClassFormatException {
         if (axxxx == null) {
            return null;
         } else {
            byte[] ax = MixinAgent.classLoader.getFakeMixinBytecode(axxxx);
            if (ax != null) {
               List<String> axx = a.reloadMixin(axxx, axxxxx);
               return axx != null && a.reApplyMixins(axx) ? ax : MixinAgent.ERROR_BYTECODE;
            } else {
               try {
                  MixinAgent.logger.info("Redefining class " + axxx);
                  return MixinAgent.this.classTransformer.transformClassBytes((String)null, axxx, axxxxx);
               } catch (Throwable var8) {
                  MixinAgent.logger.error("Error while re-transforming class " + axxx, var8);
                  return MixinAgent.ERROR_BYTECODE;
               }
            }
         }
      }

      private List<String> reloadMixin(String ax, byte[] axx) {
         MixinAgent.logger.info("Redefining mixin {}", new Object[]{ax});

         try {
            return MixinAgent.this.classTransformer.reload(ax.replace('/', '.'), axx);
         } catch (MixinReloadException var4) {
            MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[]{var4.getMixinInfo(), var4.getMessage()});
         } catch (Throwable var5) {
            MixinAgent.logger.error("Error while finding targets for mixin " + ax, var5);
         }

         return null;
      }

      private boolean reApplyMixins(List<String> axx) {
         IMixinService axxxx = MixinService.getService();
         Iterator var3 = axx.iterator();

         while(var3.hasNext()) {
            String axxxxx = (String)var3.next();
            String axxxxxx = axxxxx.replace('/', '.');
            MixinAgent.logger.debug("Re-transforming target class {}", new Object[]{axxxxx});

            try {
               Class<?> ax = axxxx.getClassProvider().findClass(axxxxxx);
               byte[] axxx = MixinAgent.classLoader.getOriginalTargetBytecode(axxxxxx);
               if (axxx == null) {
                  MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[]{axxxxxx});
                  return false;
               }

               axxx = MixinAgent.this.classTransformer.transformClassBytes((String)null, axxxxxx, axxx);
               MixinAgent.instrumentation.redefineClasses(new ClassDefinition[]{new ClassDefinition(ax, axxx)});
            } catch (Throwable var8) {
               MixinAgent.logger.error("Error while re-transforming target class " + axxxxx, var8);
               return false;
            }
         }

         return true;
      }
   }
}

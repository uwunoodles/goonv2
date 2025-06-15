package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.util.Counter;

public class MethodMapper {
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final List<String> classes = new ArrayList();
   private static final Map<String, Counter> methods = new HashMap();
   private final ClassInfo info;

   public MethodMapper(MixinEnvironment a1, ClassInfo a) {
      a.info = a;
   }

   public ClassInfo getClassInfo() {
      return a.info;
   }

   public void remapHandlerMethod(MixinInfo a, MethodNode a, ClassInfo.Method a) {
      if (a instanceof MixinInfo.MixinMethodNode && ((MixinInfo.MixinMethodNode)a).isInjector()) {
         if (a.isUnique()) {
            logger.warn("Redundant @Unique on injector method {} in {}. Injectors are implicitly unique", new Object[]{a, a});
         }

         if (a.isRenamed()) {
            a.name = a.getName();
         } else {
            String a = a.getHandlerName((MixinInfo.MixinMethodNode)a);
            a.name = a.renameTo(a);
         }
      }
   }

   public String getHandlerName(MixinInfo.MixinMethodNode a) {
      String a = InjectionInfo.getInjectorPrefix(a.getInjectorAnnotation());
      String a = getClassUID(a.getOwner().getClassRef());
      String a = getMethodUID(a.name, a.desc, !a.isSurrogate());
      return String.format("%s$%s$%s%s", a, a.name, a, a);
   }

   private static String getClassUID(String a) {
      int a = classes.indexOf(a);
      if (a < 0) {
         a = classes.size();
         classes.add(a);
      }

      return finagle(a);
   }

   private static String getMethodUID(String a, String a, boolean a) {
      String a = String.format("%s%s", a, a);
      Counter a = (Counter)methods.get(a);
      if (a == null) {
         a = new Counter();
         methods.put(a, a);
      } else if (a) {
         ++a.value;
      }

      return String.format("%03x", a.value);
   }

   private static String finagle(int a) {
      String a = Integer.toHexString(a);
      StringBuilder a = new StringBuilder();

      for(int a = 0; a < a.length(); ++a) {
         char a = a.charAt(a);
         a.append(a = (char)(a + (a < ':' ? 49 : 10)));
      }

      return Strings.padStart(a.toString(), 3, 'z');
   }
}

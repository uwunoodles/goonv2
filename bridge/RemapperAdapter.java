package org.spongepowered.asm.bridge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;
import org.spongepowered.asm.util.ObfuscationUtil;

public abstract class RemapperAdapter implements IRemapper, ObfuscationUtil.IClassRemapper {
   protected final Logger logger = LogManager.getLogger("mixin");
   protected final Remapper remapper;

   public RemapperAdapter(Remapper a) {
      a.remapper = a;
   }

   public String toString() {
      return a.getClass().getSimpleName();
   }

   public String mapMethodName(String a, String a, String a) {
      a.logger.debug("{} is remapping method {}{} for {}", new Object[]{a, a, a, a});
      String a = a.remapper.mapMethodName(a, a, a);
      if (!a.equals(a)) {
         return a;
      } else {
         String a = a.unmap(a);
         String a = a.unmapDesc(a);
         a.logger.debug("{} is remapping obfuscated method {}{} for {}", new Object[]{a, a, a, a});
         return a.remapper.mapMethodName(a, a, a);
      }
   }

   public String mapFieldName(String a, String a, String a) {
      a.logger.debug("{} is remapping field {}{} for {}", new Object[]{a, a, a, a});
      String a = a.remapper.mapFieldName(a, a, a);
      if (!a.equals(a)) {
         return a;
      } else {
         String a = a.unmap(a);
         String a = a.unmapDesc(a);
         a.logger.debug("{} is remapping obfuscated field {}{} for {}", new Object[]{a, a, a, a});
         return a.remapper.mapFieldName(a, a, a);
      }
   }

   public String map(String a) {
      a.logger.debug("{} is remapping class {}", new Object[]{a, a});
      return a.remapper.map(a);
   }

   public String unmap(String a) {
      return a;
   }

   public String mapDesc(String a) {
      return a.remapper.mapDesc(a);
   }

   public String unmapDesc(String a) {
      return ObfuscationUtil.unmapDescriptor(a, a);
   }
}

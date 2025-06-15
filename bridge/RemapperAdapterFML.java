package org.spongepowered.asm.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public final class RemapperAdapterFML extends RemapperAdapter {
   private static final String DEOBFUSCATING_REMAPPER_CLASS = "fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
   private static final String DEOBFUSCATING_REMAPPER_CLASS_FORGE = "net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
   private static final String DEOBFUSCATING_REMAPPER_CLASS_LEGACY = "cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
   private static final String INSTANCE_FIELD = "INSTANCE";
   private static final String UNMAP_METHOD = "unmap";
   private final Method mdUnmap;

   private RemapperAdapterFML(Remapper a, Method a) {
      super(a);
      a.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[]{a});
      a.mdUnmap = a;
   }

   public String unmap(String a) {
      try {
         return a.mdUnmap.invoke(a.remapper, a).toString();
      } catch (Exception var3) {
         return a;
      }
   }

   public static IRemapper create() {
      try {
         Class<?> a = getFMLDeobfuscatingRemapper();
         Field a = a.getDeclaredField("INSTANCE");
         Method a = a.getDeclaredMethod("unmap", String.class);
         Remapper a = (Remapper)a.get((Object)null);
         return new RemapperAdapterFML(a, a);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static Class<?> getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
      try {
         return Class.forName("net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
      } catch (ClassNotFoundException var1) {
         return Class.forName("cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
      }
   }
}

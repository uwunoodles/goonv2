package org.spongepowered.asm.mixin.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.service.ILegacyClassTransformer;

public final class Proxy implements IClassTransformer, ILegacyClassTransformer {
   private static List<Proxy> proxies = new ArrayList();
   private static MixinTransformer transformer = new MixinTransformer();
   private boolean isActive = true;

   public Proxy() {
      Proxy a;
      for(Iterator var1 = proxies.iterator(); var1.hasNext(); a.isActive = false) {
         a = (Proxy)var1.next();
      }

      proxies.add(a);
      LogManager.getLogger("mixin").debug("Adding new mixin transformer proxy #{}", new Object[]{proxies.size()});
   }

   public byte[] transform(String a, String a, byte[] a) {
      return a.isActive ? transformer.transformClassBytes(a, a, a) : a;
   }

   public String getName() {
      return a.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return true;
   }

   public byte[] transformClassBytes(String a, String a, byte[] a) {
      return a.isActive ? transformer.transformClassBytes(a, a, a) : a;
   }
}

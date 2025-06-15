package org.spongepowered.asm.launch.platform;

import java.net.URI;

public class MixinPlatformAgentDefault extends MixinPlatformAgentAbstract {
   public MixinPlatformAgentDefault(MixinPlatformManager a, URI a) {
      super(a, a);
   }

   public void prepare() {
      String a = a.attributes.get("MixinCompatibilityLevel");
      if (a != null) {
         a.manager.setCompatibilityLevel(a);
      }

      String a = a.attributes.get("MixinConfigs");
      int var5;
      if (a != null) {
         String[] var3 = a.split(",");
         int var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            String a = var3[var5];
            a.manager.addConfig(a.trim());
         }
      }

      String a = a.attributes.get("MixinTokenProviders");
      if (a != null) {
         String[] var9 = a.split(",");
         var5 = var9.length;

         for(int var10 = 0; var10 < var5; ++var10) {
            String a = var9[var10];
            a.manager.addTokenProvider(a.trim());
         }
      }

   }

   public void initPrimaryContainer() {
   }

   public void inject() {
   }

   public String getLaunchTarget() {
      return a.attributes.get("Main-Class");
   }
}

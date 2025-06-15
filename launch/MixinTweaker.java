package org.spongepowered.asm.launch;

import java.io.File;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class MixinTweaker implements ITweaker {
   public MixinTweaker() {
      MixinBootstrap.start();
   }

   public final void acceptOptions(List<String> a, File a2, File a3, String a4) {
      MixinBootstrap.doInit(a);
   }

   public final void injectIntoClassLoader(LaunchClassLoader a1) {
      MixinBootstrap.inject();
   }

   public String getLaunchTarget() {
      return MixinBootstrap.getPlatform().getLaunchTarget();
   }

   public String[] getLaunchArguments() {
      return new String[0];
   }
}

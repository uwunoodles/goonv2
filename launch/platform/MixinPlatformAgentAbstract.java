package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MixinPlatformAgentAbstract implements IMixinPlatformAgent {
   protected static final Logger logger = LogManager.getLogger("mixin");
   protected final MixinPlatformManager manager;
   protected final URI uri;
   protected final File container;
   protected final MainAttributes attributes;

   public MixinPlatformAgentAbstract(MixinPlatformManager a, URI a) {
      a.manager = a;
      a.uri = a;
      a.container = a.uri != null ? new File(a.uri) : null;
      a.attributes = MainAttributes.of(a);
   }

   public String toString() {
      return String.format("PlatformAgent[%s:%s]", a.getClass().getSimpleName(), a.uri);
   }

   public String getPhaseProvider() {
      return null;
   }
}

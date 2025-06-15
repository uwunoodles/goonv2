package org.spongepowered.tools.obfuscation.mcp;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
import org.spongepowered.tools.obfuscation.mapping.mcp.MappingProviderSrg;
import org.spongepowered.tools.obfuscation.mapping.mcp.MappingWriterSrg;

public class ObfuscationEnvironmentMCP extends ObfuscationEnvironment {
   protected ObfuscationEnvironmentMCP(ObfuscationType a) {
      super(a);
   }

   protected IMappingProvider getMappingProvider(Messager a, Filer a) {
      return new MappingProviderSrg(a, a);
   }

   protected IMappingWriter getMappingWriter(Messager a, Filer a) {
      return new MappingWriterSrg(a, a);
   }
}

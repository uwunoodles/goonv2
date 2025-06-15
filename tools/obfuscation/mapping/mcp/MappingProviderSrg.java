package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;

public class MappingProviderSrg extends MappingProvider {
   public MappingProviderSrg(Messager a, Filer a) {
      super(a, a);
   }

   public void read(final File a) throws IOException {
      final BiMap<String, String> a = a.packageMap;
      final BiMap<String, String> a = a.classMap;
      final BiMap<MappingField, MappingField> a = a.fieldMap;
      final BiMap<MappingMethod, MappingMethod> a = a.methodMap;
      Files.readLines(a, Charset.defaultCharset(), new LineProcessor<String>() {
         public String getResult() {
            return null;
         }

         public boolean processLine(String axxx) throws IOException {
            if (!Strings.isNullOrEmpty(axxx) && !axxx.startsWith("#")) {
               String axxxx = axxx.substring(0, 2);
               String[] axx = axxx.substring(4).split(" ");
               if (axxxx.equals("PK")) {
                  a.forcePut(axx[0], axx[1]);
               } else if (axxxx.equals("CL")) {
                  a.forcePut(axx[0], axx[1]);
               } else if (axxxx.equals("FD")) {
                  a.forcePut((new MappingFieldSrg(axx[0])).copy(), (new MappingFieldSrg(axx[1])).copy());
               } else {
                  if (!axxxx.equals("MD")) {
                     throw new MixinException("Invalid SRG file: " + a);
                  }

                  a.forcePut(new MappingMethod(axx[0], axx[1]), new MappingMethod(axx[2], axx[3]));
               }

               return true;
            } else {
               return true;
            }
         }
      });
   }

   public MappingField getFieldMapping(MappingField a) {
      if (((MappingField)a).getDesc() != null) {
         a = new MappingFieldSrg((MappingField)a);
      }

      return (MappingField)a.fieldMap.get(a);
   }
}

package org.spongepowered.tools.obfuscation.mapping.mcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.common.MappingWriter;

public class MappingWriterSrg extends MappingWriter {
   public MappingWriterSrg(Messager a, Filer a) {
      super(a, a);
   }

   public void write(String a, ObfuscationType a, IMappingConsumer.MappingSet<MappingField> a, IMappingConsumer.MappingSet<MappingMethod> a) {
      if (a != null) {
         PrintWriter a = null;

         try {
            a = a.openFileWriter(a, a + " output SRGs");
            a.writeFieldMappings(a, a);
            a.writeMethodMappings(a, a);
         } catch (IOException var15) {
            var15.printStackTrace();
         } finally {
            if (a != null) {
               try {
                  a.close();
               } catch (Exception var14) {
               }
            }

         }

      }
   }

   protected void writeFieldMappings(PrintWriter a, IMappingConsumer.MappingSet<MappingField> a) {
      Iterator var3 = a.iterator();

      while(var3.hasNext()) {
         IMappingConsumer.MappingSet.Pair<MappingField> a = (IMappingConsumer.MappingSet.Pair)var3.next();
         a.println(a.formatFieldMapping(a));
      }

   }

   protected void writeMethodMappings(PrintWriter a, IMappingConsumer.MappingSet<MappingMethod> a) {
      Iterator var3 = a.iterator();

      while(var3.hasNext()) {
         IMappingConsumer.MappingSet.Pair<MappingMethod> a = (IMappingConsumer.MappingSet.Pair)var3.next();
         a.println(a.formatMethodMapping(a));
      }

   }

   protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair<MappingField> a) {
      return String.format("FD: %s/%s %s/%s", ((MappingField)a.from).getOwner(), ((MappingField)a.from).getName(), ((MappingField)a.to).getOwner(), ((MappingField)a.to).getName());
   }

   protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair<MappingMethod> a) {
      return String.format("MD: %s %s %s %s", ((MappingMethod)a.from).getName(), ((MappingMethod)a.from).getDesc(), ((MappingMethod)a.to).getName(), ((MappingMethod)a.to).getDesc());
   }
}

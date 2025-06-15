package org.spongepowered.tools.obfuscation.mapping.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;

public abstract class MappingWriter implements IMappingWriter {
   private final Messager messager;
   private final Filer filer;

   public MappingWriter(Messager a, Filer a) {
      a.messager = a;
      a.filer = a;
   }

   protected PrintWriter openFileWriter(String a, String a) throws IOException {
      if (a.matches("^.*[\\\\/:].*$")) {
         File a = new File(a);
         a.getParentFile().mkdirs();
         a.messager.printMessage(Kind.NOTE, "Writing " + a + " to " + a.getAbsolutePath());
         return new PrintWriter(a);
      } else {
         FileObject a = a.filer.createResource(StandardLocation.CLASS_OUTPUT, "", a);
         a.messager.printMessage(Kind.NOTE, "Writing " + a + " to " + (new File(a.toUri())).getAbsolutePath());
         return new PrintWriter(a.openWriter());
      }
   }
}

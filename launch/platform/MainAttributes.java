package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

final class MainAttributes {
   private static final Map<URI, MainAttributes> instances = new HashMap();
   protected final Attributes attributes;

   private MainAttributes() {
      a.attributes = new Attributes();
   }

   private MainAttributes(File a) {
      a.attributes = getAttributes(a);
   }

   public final String get(String a) {
      return a.attributes != null ? a.attributes.getValue(a) : null;
   }

   private static Attributes getAttributes(File a) {
      if (a == null) {
         return null;
      } else {
         JarFile a = null;

         Attributes var3;
         try {
            a = new JarFile(a);
            Manifest a = a.getManifest();
            if (a == null) {
               return new Attributes();
            }

            var3 = a.getMainAttributes();
         } catch (IOException var14) {
            return new Attributes();
         } finally {
            try {
               if (a != null) {
                  a.close();
               }
            } catch (IOException var13) {
            }

         }

         return var3;
      }
   }

   public static MainAttributes of(File a) {
      return of(a.toURI());
   }

   public static MainAttributes of(URI a) {
      MainAttributes a = (MainAttributes)instances.get(a);
      if (a == null) {
         a = new MainAttributes(new File(a));
         instances.put(a, a);
      }

      return a;
   }
}

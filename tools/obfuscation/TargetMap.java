package org.spongepowered.tools.obfuscation;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;

public final class TargetMap extends HashMap<TypeReference, Set<TypeReference>> {
   private static final long serialVersionUID = 1L;
   private final String sessionId;

   private TargetMap() {
      this(String.valueOf(System.currentTimeMillis()));
   }

   private TargetMap(String a) {
      a.sessionId = a;
   }

   public String getSessionId() {
      return a.sessionId;
   }

   public void registerTargets(AnnotatedMixin a) {
      a.registerTargets(a.getTargets(), a.getHandle());
   }

   public void registerTargets(List<TypeHandle> a, TypeHandle a) {
      Iterator var3 = a.iterator();

      while(var3.hasNext()) {
         TypeHandle a = (TypeHandle)var3.next();
         a.addMixin(a, a);
      }

   }

   public void addMixin(TypeHandle a, TypeHandle a) {
      a.addMixin(a.getReference(), a.getReference());
   }

   public void addMixin(String a, String a) {
      a.addMixin(new TypeReference(a), new TypeReference(a));
   }

   public void addMixin(TypeReference a, TypeReference a) {
      Set<TypeReference> a = a.getMixinsFor(a);
      a.add(a);
   }

   public Collection<TypeReference> getMixinsTargeting(TypeElement a) {
      return a.getMixinsTargeting(new TypeHandle(a));
   }

   public Collection<TypeReference> getMixinsTargeting(TypeHandle a) {
      return a.getMixinsTargeting(a.getReference());
   }

   public Collection<TypeReference> getMixinsTargeting(TypeReference a) {
      return Collections.unmodifiableCollection(a.getMixinsFor(a));
   }

   private Set<TypeReference> getMixinsFor(TypeReference a) {
      Set<TypeReference> a = (Set)a.get(a);
      if (a == null) {
         a = new HashSet();
         a.put(a, a);
      }

      return (Set)a;
   }

   public void readImports(File a) throws IOException {
      if (a.isFile()) {
         Iterator var2 = Files.readLines(a, Charset.defaultCharset()).iterator();

         while(var2.hasNext()) {
            String a = (String)var2.next();
            String[] a = a.split("\t");
            if (a.length == 2) {
               a.addMixin(a[1], a[0]);
            }
         }

      }
   }

   public void write(boolean a) {
      ObjectOutputStream a = null;
      FileOutputStream a = null;

      try {
         File a = getSessionFile(a.sessionId);
         if (a) {
            a.deleteOnExit();
         }

         a = new FileOutputStream(a, true);
         a = new ObjectOutputStream(a);
         a.writeObject(a);
      } catch (Exception var13) {
         var13.printStackTrace();
      } finally {
         if (a != null) {
            try {
               a.close();
            } catch (IOException var12) {
               var12.printStackTrace();
            }
         }

      }

   }

   private static TargetMap read(File a) {
      ObjectInputStream a = null;
      FileInputStream a = null;

      try {
         a = new FileInputStream(a);
         a = new ObjectInputStream(a);
         TargetMap var3 = (TargetMap)a.readObject();
         return var3;
      } catch (Exception var13) {
         var13.printStackTrace();
      } finally {
         if (a != null) {
            try {
               a.close();
            } catch (IOException var12) {
               var12.printStackTrace();
            }
         }

      }

      return null;
   }

   public static TargetMap create(String a) {
      if (a != null) {
         File a = getSessionFile(a);
         if (a.exists()) {
            TargetMap a = read(a);
            if (a != null) {
               return a;
            }
         }
      }

      return new TargetMap();
   }

   private static File getSessionFile(String a) {
      File a = new File(System.getProperty("java.io.tmpdir"));
      return new File(a, String.format("mixin-targetdb-%s.tmp", a));
   }
}

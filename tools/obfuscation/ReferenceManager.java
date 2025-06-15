package org.spongepowered.tools.obfuscation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;

public class ReferenceManager implements IReferenceManager {
   private final IMixinAnnotationProcessor ap;
   private final String outRefMapFileName;
   private final List<ObfuscationEnvironment> environments;
   private final ReferenceMapper refMapper = new ReferenceMapper();
   private boolean allowConflicts;

   public ReferenceManager(IMixinAnnotationProcessor a, List<ObfuscationEnvironment> a) {
      a.ap = a;
      a.environments = a;
      a.outRefMapFileName = a.ap.getOption("outRefMapFile");
   }

   public boolean getAllowConflicts() {
      return a.allowConflicts;
   }

   public void setAllowConflicts(boolean a) {
      a.allowConflicts = a;
   }

   public void write() {
      if (a.outRefMapFileName != null) {
         PrintWriter a = null;

         try {
            a = a.newWriter(a.outRefMapFileName, "refmap");
            a.refMapper.write(a);
         } catch (IOException var11) {
            var11.printStackTrace();
         } finally {
            if (a != null) {
               try {
                  a.close();
               } catch (Exception var10) {
               }
            }

         }

      }
   }

   private PrintWriter newWriter(String a, String a) throws IOException {
      if (a.matches("^.*[\\\\/:].*$")) {
         File a = new File(a);
         a.getParentFile().mkdirs();
         a.ap.printMessage(Kind.NOTE, "Writing " + a + " to " + a.getAbsolutePath());
         return new PrintWriter(a);
      } else {
         FileObject a = a.ap.getProcessingEnvironment().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", a);
         a.ap.printMessage(Kind.NOTE, "Writing " + a + " to " + (new File(a.toUri())).getAbsolutePath());
         return new PrintWriter(a.openWriter());
      }
   }

   public ReferenceMapper getMapper() {
      return a.refMapper;
   }

   public void addMethodMapping(String a, String a, ObfuscationData<MappingMethod> a) {
      Iterator var4 = a.environments.iterator();

      while(var4.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var4.next();
         MappingMethod a = (MappingMethod)a.get(a.getType());
         if (a != null) {
            MemberInfo a = new MemberInfo(a);
            a.addMapping(a.getType(), a, a, a.toString());
         }
      }

   }

   public void addMethodMapping(String a, String a, MemberInfo a, ObfuscationData<MappingMethod> a) {
      Iterator var5 = a.environments.iterator();

      while(var5.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var5.next();
         MappingMethod a = (MappingMethod)a.get(a.getType());
         if (a != null) {
            MemberInfo a = a.remapUsing(a, true);
            a.addMapping(a.getType(), a, a, a.toString());
         }
      }

   }

   public void addFieldMapping(String a, String a, MemberInfo a, ObfuscationData<MappingField> a) {
      Iterator var5 = a.environments.iterator();

      while(var5.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var5.next();
         MappingField a = (MappingField)a.get(a.getType());
         if (a != null) {
            MemberInfo a = MemberInfo.fromMapping(a.transform(a.remapDescriptor(a.desc)));
            a.addMapping(a.getType(), a, a, a.toString());
         }
      }

   }

   public void addClassMapping(String a, String a, ObfuscationData<String> a) {
      Iterator var4 = a.environments.iterator();

      while(var4.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var4.next();
         String a = (String)a.get(a.getType());
         if (a != null) {
            a.addMapping(a.getType(), a, a, a);
         }
      }

   }

   protected void addMapping(ObfuscationType a, String a, String a, String a) {
      String a = a.refMapper.addMapping(a.getKey(), a, a, a);
      if (a.isDefault()) {
         a.refMapper.addMapping((String)null, a, a, a);
      }

      if (!a.allowConflicts && a != null && !a.equals(a)) {
         throw new ReferenceManager.ReferenceConflictException(a, a);
      }
   }

   public static class ReferenceConflictException extends RuntimeException {
      private static final long serialVersionUID = 1L;
      private final String oldReference;
      private final String newReference;

      public ReferenceConflictException(String a, String a) {
         a.oldReference = a;
         a.newReference = a;
      }

      public String getOld() {
         return a.oldReference;
      }

      public String getNew() {
         return a.newReference;
      }
   }
}

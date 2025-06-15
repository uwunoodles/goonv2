package org.spongepowered.tools.obfuscation;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ObfuscationUtil;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public abstract class ObfuscationEnvironment implements IObfuscationEnvironment {
   protected final ObfuscationType type;
   protected final IMappingProvider mappingProvider;
   protected final IMappingWriter mappingWriter;
   protected final ObfuscationEnvironment.RemapperProxy remapper;
   protected final IMixinAnnotationProcessor ap;
   protected final String outFileName;
   protected final List<String> inFileNames;
   private boolean initDone;

   protected ObfuscationEnvironment(ObfuscationType a) {
      a.remapper = new ObfuscationEnvironment.RemapperProxy();
      a.type = a;
      a.ap = a.getAnnotationProcessor();
      a.inFileNames = a.getInputFileNames();
      a.outFileName = a.getOutputFileName();
      a.mappingProvider = a.getMappingProvider(a.ap, a.ap.getProcessingEnvironment().getFiler());
      a.mappingWriter = a.getMappingWriter(a.ap, a.ap.getProcessingEnvironment().getFiler());
   }

   public String toString() {
      return a.type.toString();
   }

   protected abstract IMappingProvider getMappingProvider(Messager var1, Filer var2);

   protected abstract IMappingWriter getMappingWriter(Messager var1, Filer var2);

   private boolean initMappings() {
      if (!a.initDone) {
         a.initDone = true;
         if (a.inFileNames == null) {
            a.ap.printMessage(Kind.ERROR, "The " + a.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur");
            return false;
         }

         int a = 0;
         Iterator var2 = a.inFileNames.iterator();

         while(var2.hasNext()) {
            String a = (String)var2.next();
            File a = new File(a);

            try {
               if (a.isFile()) {
                  a.ap.printMessage(Kind.NOTE, "Loading " + a.type + " mappings from " + a.getAbsolutePath());
                  a.mappingProvider.read(a);
                  ++a;
               }
            } catch (Exception var6) {
               var6.printStackTrace();
            }
         }

         if (a < 1) {
            a.ap.printMessage(Kind.ERROR, "No valid input files for " + a.type + " could be read, processing may not be sucessful.");
            a.mappingProvider.clear();
         }
      }

      return !a.mappingProvider.isEmpty();
   }

   public ObfuscationType getType() {
      return a.type;
   }

   public MappingMethod getObfMethod(MemberInfo a) {
      MappingMethod a = a.getObfMethod(a.asMethodMapping());
      if (a == null && a.isFullyQualified()) {
         TypeHandle a = a.ap.getTypeProvider().getTypeHandle(a.owner);
         if (a != null && !a.isImaginary()) {
            TypeMirror a = a.getElement().getSuperclass();
            if (a.getKind() != TypeKind.DECLARED) {
               return null;
            } else {
               String a = ((TypeElement)((DeclaredType)a).asElement()).getQualifiedName().toString();
               return a.getObfMethod(new MemberInfo(a.name, a.replace('.', '/'), a.desc, a.matchAll));
            }
         } else {
            return null;
         }
      } else {
         return a;
      }
   }

   public MappingMethod getObfMethod(MappingMethod a) {
      return a.getObfMethod(a, true);
   }

   public MappingMethod getObfMethod(MappingMethod a, boolean a) {
      if (!a.initMappings()) {
         return null;
      } else {
         boolean a = true;
         MappingMethod a = null;

         for(MappingMethod a = a; a != null && a == null; a = a.getSuper()) {
            a = a.mappingProvider.getMethodMapping(a);
         }

         if (a == null) {
            if (a) {
               return null;
            }

            a = a.copy();
            a = false;
         }

         String a = a.getObfClass(a.getOwner());
         if (a != null && !a.equals(a.getOwner()) && !a.equals(a.getOwner())) {
            if (a) {
               return a.move(a);
            } else {
               String a = ObfuscationUtil.mapDescriptor(a.getDesc(), a.remapper);
               return new MappingMethod(a, a.getSimpleName(), a);
            }
         } else {
            return a ? a : null;
         }
      }
   }

   public MemberInfo remapDescriptor(MemberInfo a) {
      boolean a = false;
      String a = a.owner;
      String a;
      if (a != null) {
         a = a.remapper.map(a);
         if (a != null) {
            a = a;
            a = true;
         }
      }

      a = a.desc;
      if (a != null) {
         String a = ObfuscationUtil.mapDescriptor(a.desc, a.remapper);
         if (!a.equals(a.desc)) {
            a = a;
            a = true;
         }
      }

      return a ? new MemberInfo(a.name, a, a, a.matchAll) : null;
   }

   public String remapDescriptor(String a) {
      return ObfuscationUtil.mapDescriptor(a, a.remapper);
   }

   public MappingField getObfField(MemberInfo a) {
      return a.getObfField(a.asFieldMapping(), true);
   }

   public MappingField getObfField(MappingField a) {
      return a.getObfField(a, true);
   }

   public MappingField getObfField(MappingField a, boolean a) {
      if (!a.initMappings()) {
         return null;
      } else {
         MappingField a = a.mappingProvider.getFieldMapping(a);
         if (a == null) {
            if (a) {
               return null;
            }

            a = a;
         }

         String a = a.getObfClass(a.getOwner());
         if (a != null && !a.equals(a.getOwner()) && !a.equals(a.getOwner())) {
            return a.move(a);
         } else {
            return a != a ? a : null;
         }
      }
   }

   public String getObfClass(String a) {
      return !a.initMappings() ? null : a.mappingProvider.getClassMapping(a);
   }

   public void writeMappings(Collection<IMappingConsumer> a) {
      IMappingConsumer.MappingSet<MappingField> a = new IMappingConsumer.MappingSet();
      IMappingConsumer.MappingSet<MappingMethod> a = new IMappingConsumer.MappingSet();
      Iterator var4 = a.iterator();

      while(var4.hasNext()) {
         IMappingConsumer a = (IMappingConsumer)var4.next();
         a.addAll(a.getFieldMappings(a.type));
         a.addAll(a.getMethodMappings(a.type));
      }

      a.mappingWriter.write(a.outFileName, a.type, a, a);
   }

   final class RemapperProxy implements ObfuscationUtil.IClassRemapper {
      public String map(String ax) {
         return ObfuscationEnvironment.this.mappingProvider == null ? null : ObfuscationEnvironment.this.mappingProvider.getClassMapping(ax);
      }

      public String unmap(String ax) {
         return ObfuscationEnvironment.this.mappingProvider == null ? null : ObfuscationEnvironment.this.mappingProvider.getClassMapping(ax);
      }
   }
}

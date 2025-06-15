package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.service.ObfuscationServices;

public class ObfuscationManager implements IObfuscationManager {
   private final IMixinAnnotationProcessor ap;
   private final List<ObfuscationEnvironment> environments = new ArrayList();
   private final IObfuscationDataProvider obfs;
   private final IReferenceManager refs;
   private final List<IMappingConsumer> consumers = new ArrayList();
   private boolean initDone;

   public ObfuscationManager(IMixinAnnotationProcessor a) {
      a.ap = a;
      a.obfs = new ObfuscationDataProvider(a, a.environments);
      a.refs = new ReferenceManager(a, a.environments);
   }

   public void init() {
      if (!a.initDone) {
         a.initDone = true;
         ObfuscationServices.getInstance().initProviders(a.ap);
         Iterator var1 = ObfuscationType.types().iterator();

         while(var1.hasNext()) {
            ObfuscationType a = (ObfuscationType)var1.next();
            if (a.isSupported()) {
               a.environments.add(a.createEnvironment());
            }
         }

      }
   }

   public IObfuscationDataProvider getDataProvider() {
      return a.obfs;
   }

   public IReferenceManager getReferenceManager() {
      return a.refs;
   }

   public IMappingConsumer createMappingConsumer() {
      Mappings a = new Mappings();
      a.consumers.add(a);
      return a;
   }

   public List<ObfuscationEnvironment> getEnvironments() {
      return a.environments;
   }

   public void writeMappings() {
      Iterator var1 = a.environments.iterator();

      while(var1.hasNext()) {
         ObfuscationEnvironment a = (ObfuscationEnvironment)var1.next();
         a.writeMappings(a.consumers);
      }

   }

   public void writeReferences() {
      a.refs.write();
   }
}

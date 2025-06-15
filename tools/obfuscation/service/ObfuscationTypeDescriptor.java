package org.spongepowered.tools.obfuscation.service;

import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;

public class ObfuscationTypeDescriptor {
   private final String key;
   private final String inputFileArgName;
   private final String extraInputFilesArgName;
   private final String outFileArgName;
   private final Class<? extends ObfuscationEnvironment> environmentType;

   public ObfuscationTypeDescriptor(String a, String a, String a, Class<? extends ObfuscationEnvironment> a) {
      this(a, a, (String)null, a, a);
   }

   public ObfuscationTypeDescriptor(String a, String a, String a, String a, Class<? extends ObfuscationEnvironment> a) {
      a.key = a;
      a.inputFileArgName = a;
      a.extraInputFilesArgName = a;
      a.outFileArgName = a;
      a.environmentType = a;
   }

   public final String getKey() {
      return a.key;
   }

   public String getInputFileOption() {
      return a.inputFileArgName;
   }

   public String getExtraInputFilesOption() {
      return a.extraInputFilesArgName;
   }

   public String getOutputFileOption() {
      return a.outFileArgName;
   }

   public Class<? extends ObfuscationEnvironment> getEnvironmentType() {
      return a.environmentType;
   }
}

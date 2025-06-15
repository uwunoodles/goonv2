package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

public final class MemberInfo {
   public final String owner;
   public final String name;
   public final String desc;
   public final boolean matchAll;
   private final boolean forceField;
   private final String unparsed;

   public MemberInfo(String a, boolean a) {
      this(a, (String)null, (String)null, a);
   }

   public MemberInfo(String a, String a, boolean a) {
      this(a, a, (String)null, a);
   }

   public MemberInfo(String a, String a, String a) {
      this(a, a, a, false);
   }

   public MemberInfo(String a, String a, String a, boolean a) {
      this(a, a, a, a, (String)null);
   }

   public MemberInfo(String a, String a, String a, boolean a, String a) {
      if (a != null && a.contains(".")) {
         throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format");
      } else {
         a.owner = a;
         a.name = a;
         a.desc = a;
         a.matchAll = a;
         a.forceField = false;
         a.unparsed = a;
      }
   }

   public MemberInfo(AbstractInsnNode a) {
      a.matchAll = false;
      a.forceField = false;
      a.unparsed = null;
      if (a instanceof MethodInsnNode) {
         MethodInsnNode a = (MethodInsnNode)a;
         a.owner = a.owner;
         a.name = a.name;
         a.desc = a.desc;
      } else {
         if (!(a instanceof FieldInsnNode)) {
            throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
         }

         FieldInsnNode a = (FieldInsnNode)a;
         a.owner = a.owner;
         a.name = a.name;
         a.desc = a.desc;
      }

   }

   public MemberInfo(IMapping<?> a) {
      a.owner = a.getOwner();
      a.name = a.getSimpleName();
      a.desc = a.getDesc();
      a.matchAll = false;
      a.forceField = a.getType() == IMapping.Type.FIELD;
      a.unparsed = null;
   }

   private MemberInfo(MemberInfo a, MappingMethod a, boolean a) {
      a.owner = a ? a.getOwner() : a.owner;
      a.name = a.getSimpleName();
      a.desc = a.getDesc();
      a.matchAll = a.matchAll;
      a.forceField = false;
      a.unparsed = null;
   }

   private MemberInfo(MemberInfo a, String a) {
      a.owner = a;
      a.name = a.name;
      a.desc = a.desc;
      a.matchAll = a.matchAll;
      a.forceField = a.forceField;
      a.unparsed = null;
   }

   public String toString() {
      String a = a.owner != null ? "L" + a.owner + ";" : "";
      String a = a.name != null ? a.name : "";
      String a = a.matchAll ? "*" : "";
      String a = a.desc != null ? a.desc : "";
      String a = a.startsWith("(") ? "" : (a.desc != null ? ":" : "");
      return a + a + a + a + a;
   }

   /** @deprecated */
   @Deprecated
   public String toSrg() {
      if (!a.isFullyQualified()) {
         throw new MixinException("Cannot convert unqualified reference to SRG mapping");
      } else {
         return a.desc.startsWith("(") ? a.owner + "/" + a.name + " " + a.desc : a.owner + "/" + a.name;
      }
   }

   public String toDescriptor() {
      return a.desc == null ? "" : (new SignaturePrinter(a)).setFullyQualified(true).toDescriptor();
   }

   public String toCtorType() {
      if (a.unparsed == null) {
         return null;
      } else {
         String a = a.getReturnType();
         if (a != null) {
            return a;
         } else if (a.owner != null) {
            return a.owner;
         } else if (a.name != null && a.desc == null) {
            return a.name;
         } else {
            return a.desc != null ? a.desc : a.unparsed;
         }
      }
   }

   public String toCtorDesc() {
      return a.desc != null && a.desc.startsWith("(") && a.desc.indexOf(41) > -1 ? a.desc.substring(0, a.desc.indexOf(41) + 1) + "V" : null;
   }

   public String getReturnType() {
      if (a.desc != null && a.desc.indexOf(41) != -1 && a.desc.indexOf(40) == 0) {
         String a = a.desc.substring(a.desc.indexOf(41) + 1);
         return a.startsWith("L") && a.endsWith(";") ? a.substring(1, a.length() - 1) : a;
      } else {
         return null;
      }
   }

   public IMapping<?> asMapping() {
      return (IMapping)(a.isField() ? a.asFieldMapping() : a.asMethodMapping());
   }

   public MappingMethod asMethodMapping() {
      if (!a.isFullyQualified()) {
         throw new MixinException("Cannot convert unqualified reference " + a + " to MethodMapping");
      } else if (a.isField()) {
         throw new MixinException("Cannot convert a non-method reference " + a + " to MethodMapping");
      } else {
         return new MappingMethod(a.owner, a.name, a.desc);
      }
   }

   public MappingField asFieldMapping() {
      if (!a.isField()) {
         throw new MixinException("Cannot convert non-field reference " + a + " to FieldMapping");
      } else {
         return new MappingField(a.owner, a.name, a.desc);
      }
   }

   public boolean isFullyQualified() {
      return a.owner != null && a.name != null && a.desc != null;
   }

   public boolean isField() {
      return a.forceField || a.desc != null && !a.desc.startsWith("(");
   }

   public boolean isConstructor() {
      return "<init>".equals(a.name);
   }

   public boolean isClassInitialiser() {
      return "<clinit>".equals(a.name);
   }

   public boolean isInitialiser() {
      return a.isConstructor() || a.isClassInitialiser();
   }

   public MemberInfo validate() throws InvalidMemberDescriptorException {
      if (a.owner != null) {
         if (!a.owner.matches("(?i)^[\\w\\p{Sc}/]+$")) {
            throw new InvalidMemberDescriptorException("Invalid owner: " + a.owner);
         }

         if (a.unparsed != null && a.unparsed.lastIndexOf(46) > 0 && a.owner.startsWith("L")) {
            throw new InvalidMemberDescriptorException("Malformed owner: " + a.owner + " If you are seeing this message unexpectedly and the owner appears to be correct, replace the owner descriptor with formal type L" + a.owner + "; to suppress this error");
         }
      }

      if (a.name != null && !a.name.matches("(?i)^<?[\\w\\p{Sc}]+>?$")) {
         throw new InvalidMemberDescriptorException("Invalid name: " + a.name);
      } else {
         if (a.desc != null) {
            if (!a.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$")) {
               throw new InvalidMemberDescriptorException("Invalid descriptor: " + a.desc);
            }

            if (a.isField()) {
               if (!a.desc.equals(Type.getType(a.desc).getDescriptor())) {
                  throw new InvalidMemberDescriptorException("Invalid field type in descriptor: " + a.desc);
               }
            } else {
               try {
                  Type.getArgumentTypes(a.desc);
               } catch (Exception var4) {
                  throw new InvalidMemberDescriptorException("Invalid descriptor: " + a.desc);
               }

               String a = a.desc.substring(a.desc.indexOf(41) + 1);

               try {
                  Type a = Type.getType(a);
                  if (!a.equals(a.getDescriptor())) {
                     throw new InvalidMemberDescriptorException("Invalid return type \"" + a + "\" in descriptor: " + a.desc);
                  }
               } catch (Exception var3) {
                  throw new InvalidMemberDescriptorException("Invalid return type \"" + a + "\" in descriptor: " + a.desc);
               }
            }
         }

         return a;
      }
   }

   public boolean matches(String a, String a, String a) {
      return a.matches(a, a, a, 0);
   }

   public boolean matches(String a, String a, String a, int a) {
      if (a.desc != null && a != null && !a.desc.equals(a)) {
         return false;
      } else if (a.name != null && a != null && !a.name.equals(a)) {
         return false;
      } else if (a.owner != null && a != null && !a.owner.equals(a)) {
         return false;
      } else {
         return a == 0 || a.matchAll;
      }
   }

   public boolean matches(String a, String a) {
      return a.matches(a, a, 0);
   }

   public boolean matches(String a, String a, int a) {
      return (a.name == null || a.name.equals(a)) && (a.desc == null || a != null && a.equals(a.desc)) && (a == 0 || a.matchAll);
   }

   public boolean equals(Object a) {
      if (a != null && a.getClass() == MemberInfo.class) {
         MemberInfo a = (MemberInfo)a;
         return a.matchAll == a.matchAll && a.forceField == a.forceField && Objects.equal(a.owner, a.owner) && Objects.equal(a.name, a.name) && Objects.equal(a.desc, a.desc);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{a.matchAll, a.owner, a.name, a.desc});
   }

   public MemberInfo move(String a) {
      return (a != null || a.owner != null) && (a == null || !a.equals(a.owner)) ? new MemberInfo(a, a) : a;
   }

   public MemberInfo transform(String a) {
      return (a != null || a.desc != null) && (a == null || !a.equals(a.desc)) ? new MemberInfo(a.name, a.owner, a, a.matchAll) : a;
   }

   public MemberInfo remapUsing(MappingMethod a, boolean a) {
      return new MemberInfo(a, a, a);
   }

   public static MemberInfo parseAndValidate(String a) throws InvalidMemberDescriptorException {
      return parse(a, (IReferenceMapper)null, (String)null).validate();
   }

   public static MemberInfo parseAndValidate(String a, IMixinContext a) throws InvalidMemberDescriptorException {
      return parse(a, a.getReferenceMapper(), a.getClassRef()).validate();
   }

   public static MemberInfo parse(String a) {
      return parse(a, (IReferenceMapper)null, (String)null);
   }

   public static MemberInfo parse(String a, IMixinContext a) {
      return parse(a, a.getReferenceMapper(), a.getClassRef());
   }

   private static MemberInfo parse(String a, IReferenceMapper a, String a) {
      String a = null;
      String a = null;
      String a = Strings.nullToEmpty(a).replaceAll("\\s", "");
      if (a != null) {
         a = a.remap(a, a);
      }

      int a = a.lastIndexOf(46);
      int a = a.indexOf(59);
      if (a > -1) {
         a = a.substring(0, a).replace('.', '/');
         a = a.substring(a + 1);
      } else if (a > -1 && a.startsWith("L")) {
         a = a.substring(1, a).replace('.', '/');
         a = a.substring(a + 1);
      }

      int a = a.indexOf(40);
      int a = a.indexOf(58);
      if (a > -1) {
         a = a.substring(a);
         a = a.substring(0, a);
      } else if (a > -1) {
         a = a.substring(a + 1);
         a = a.substring(0, a);
      }

      if ((a.indexOf(47) > -1 || a.indexOf(46) > -1) && a == null) {
         a = a;
         a = "";
      }

      boolean a = a.endsWith("*");
      if (a) {
         a = a.substring(0, a.length() - 1);
      }

      if (a.isEmpty()) {
         a = null;
      }

      return new MemberInfo(a, a, a, a, a);
   }

   public static MemberInfo fromMapping(IMapping<?> a) {
      return new MemberInfo(a);
   }
}

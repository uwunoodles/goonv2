package org.spongepowered.asm.mixin.gen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public class AccessorInfo extends SpecialMethodInfo {
   protected static final Pattern PATTERN_ACCESSOR = Pattern.compile("^(get|set|is|invoke|call)(([A-Z])(.*?))(_\\$md.*)?$");
   protected final Type[] argTypes;
   protected final Type returnType;
   protected final AccessorInfo.AccessorType type;
   private final Type targetFieldType;
   protected final MemberInfo target;
   protected FieldNode targetField;
   protected MethodNode targetMethod;

   public AccessorInfo(MixinTargetContext a, MethodNode a) {
      this(a, a, Accessor.class);
   }

   protected AccessorInfo(MixinTargetContext a, MethodNode a, Class<? extends Annotation> a) {
      super(a, a, Annotations.getVisible(a, a));
      a.argTypes = Type.getArgumentTypes(a.desc);
      a.returnType = Type.getReturnType(a.desc);
      a.type = a.initType();
      a.targetFieldType = a.initTargetFieldType();
      a.target = a.initTarget();
   }

   protected AccessorInfo.AccessorType initType() {
      return a.returnType.equals(Type.VOID_TYPE) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
   }

   protected Type initTargetFieldType() {
      switch(a.type) {
      case FIELD_GETTER:
         if (a.argTypes.length > 0) {
            throw new InvalidAccessorException(a.mixin, a + " must take exactly 0 arguments, found " + a.argTypes.length);
         }

         return a.returnType;
      case FIELD_SETTER:
         if (a.argTypes.length != 1) {
            throw new InvalidAccessorException(a.mixin, a + " must take exactly 1 argument, found " + a.argTypes.length);
         }

         return a.argTypes[0];
      default:
         throw new InvalidAccessorException(a.mixin, "Computed unsupported accessor type " + a.type + " for " + a);
      }
   }

   protected MemberInfo initTarget() {
      MemberInfo a = new MemberInfo(a.getTargetName(), (String)null, a.targetFieldType.getDescriptor());
      a.annotation.visit("target", a.toString());
      return a;
   }

   protected String getTargetName() {
      String a = (String)Annotations.getValue(a.annotation);
      if (Strings.isNullOrEmpty(a)) {
         String a = a.inflectTarget();
         if (a == null) {
            throw new InvalidAccessorException(a.mixin, "Failed to inflect target name for " + a + ", supported prefixes: [get, set, is]");
         } else {
            return a;
         }
      } else {
         return MemberInfo.parse(a, a.mixin).name;
      }
   }

   protected String inflectTarget() {
      return inflectTarget(a.method.name, a.type, a.toString(), a.mixin, a.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
   }

   public static String inflectTarget(String a, AccessorInfo.AccessorType a, String a, IMixinContext a, boolean a) {
      Matcher a = PATTERN_ACCESSOR.matcher(a);
      if (a.matches()) {
         String a = a.group(1);
         String a = a.group(3);
         String a = a.group(4);
         String a = String.format("%s%s", toLowerCase(a, !isUpperCase(a)), a);
         if (!a.isExpectedPrefix(a) && a) {
            LogManager.getLogger("mixin").warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[]{a, a, a.getExpectedPrefixes()});
         }

         return MemberInfo.parse(a, a).name;
      } else {
         return null;
      }
   }

   public final MemberInfo getTarget() {
      return a.target;
   }

   public final Type getTargetFieldType() {
      return a.targetFieldType;
   }

   public final FieldNode getTargetField() {
      return a.targetField;
   }

   public final MethodNode getTargetMethod() {
      return a.targetMethod;
   }

   public final Type getReturnType() {
      return a.returnType;
   }

   public final Type[] getArgTypes() {
      return a.argTypes;
   }

   public String toString() {
      return String.format("%s->@%s[%s]::%s%s", a.mixin.toString(), Bytecode.getSimpleName(a.annotation), a.type.toString(), a.method.name, a.method.desc);
   }

   public void locate() {
      a.targetField = a.findTargetField();
   }

   public MethodNode generate() {
      MethodNode a = a.type.getGenerator(a).generate();
      Bytecode.mergeAnnotations(a.method, a);
      return a;
   }

   private FieldNode findTargetField() {
      return (FieldNode)a.findTarget(a.classNode.fields);
   }

   protected <TNode> TNode findTarget(List<TNode> a) {
      TNode a = null;
      List<TNode> a = new ArrayList();
      Iterator var4 = a.iterator();

      while(var4.hasNext()) {
         TNode a = var4.next();
         String a = getNodeDesc(a);
         if (a != null && a.equals(a.target.desc)) {
            String a = getNodeName(a);
            if (a != null) {
               if (a.equals(a.target.name)) {
                  a = a;
               }

               if (a.equalsIgnoreCase(a.target.name)) {
                  a.add(a);
               }
            }
         }
      }

      if (a != null) {
         if (a.size() > 1) {
            LogManager.getLogger("mixin").debug("{} found an exact match for {} but other candidates were found!", new Object[]{a, a.target});
         }

         return a;
      } else if (a.size() == 1) {
         return a.get(0);
      } else {
         String a = a.size() == 0 ? "No" : "Multiple";
         throw new InvalidAccessorException(a, a + " candidates were found matching " + a.target + " in " + a.classNode.name + " for " + a);
      }
   }

   private static <TNode> String getNodeDesc(TNode a) {
      return a instanceof MethodNode ? ((MethodNode)a).desc : (a instanceof FieldNode ? ((FieldNode)a).desc : null);
   }

   private static <TNode> String getNodeName(TNode a) {
      return a instanceof MethodNode ? ((MethodNode)a).name : (a instanceof FieldNode ? ((FieldNode)a).name : null);
   }

   public static AccessorInfo of(MixinTargetContext a, MethodNode a, Class<? extends Annotation> a) {
      if (a == Accessor.class) {
         return new AccessorInfo(a, a);
      } else if (a == Invoker.class) {
         return new InvokerInfo(a, a);
      } else {
         throw new InvalidAccessorException(a, "Could not parse accessor for unknown type " + a.getName());
      }
   }

   private static String toLowerCase(String a, boolean a) {
      return a ? a.toLowerCase() : a;
   }

   private static boolean isUpperCase(String a) {
      return a.toUpperCase().equals(a);
   }

   public static enum AccessorType {
      FIELD_GETTER(ImmutableSet.of("get", "is")) {
         AccessorGenerator getGenerator(AccessorInfo ax) {
            return new AccessorGeneratorFieldGetter(ax);
         }
      },
      FIELD_SETTER(ImmutableSet.of("set")) {
         AccessorGenerator getGenerator(AccessorInfo ax) {
            return new AccessorGeneratorFieldSetter(ax);
         }
      },
      METHOD_PROXY(ImmutableSet.of("call", "invoke")) {
         AccessorGenerator getGenerator(AccessorInfo ax) {
            return new AccessorGeneratorMethodProxy(ax);
         }
      };

      private final Set<String> expectedPrefixes;

      private AccessorType(Set<String> a) {
         a.expectedPrefixes = a;
      }

      public boolean isExpectedPrefix(String a) {
         return a.expectedPrefixes.contains(a);
      }

      public String getExpectedPrefixes() {
         return a.expectedPrefixes.toString();
      }

      abstract AccessorGenerator getGenerator(AccessorInfo var1);

      // $FF: synthetic method
      AccessorType(Set a, Object a4) {
         this(a);
      }
   }
}

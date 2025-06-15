package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.MethodSlices;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext {
   protected final boolean isStatic;
   protected final Deque<MethodNode> targets;
   protected final MethodSlices slices;
   protected final String atKey;
   protected final List<InjectionPoint> injectionPoints;
   protected final Map<Target, List<InjectionNodes.InjectionNode>> targetNodes;
   protected Injector injector;
   protected InjectorGroupInfo group;
   private final List<MethodNode> injectedMethods;
   private int expectedCallbackCount;
   private int requiredCallbackCount;
   private int maxCallbackCount;
   private int injectedCallbackCount;

   protected InjectionInfo(MixinTargetContext a, MethodNode a, AnnotationNode a) {
      this(a, a, a, "at");
   }

   protected InjectionInfo(MixinTargetContext a, MethodNode a, AnnotationNode a, String a) {
      super(a, a, a);
      a.targets = new ArrayDeque();
      a.injectionPoints = new ArrayList();
      a.targetNodes = new LinkedHashMap();
      a.injectedMethods = new ArrayList(0);
      a.expectedCallbackCount = 1;
      a.requiredCallbackCount = 0;
      a.maxCallbackCount = Integer.MAX_VALUE;
      a.injectedCallbackCount = 0;
      a.isStatic = Bytecode.methodIsStatic(a);
      a.slices = MethodSlices.parse(a);
      a.atKey = a;
      a.readAnnotation();
   }

   protected void readAnnotation() {
      if (a.annotation != null) {
         String a = "@" + Bytecode.getSimpleName(a.annotation);
         List<AnnotationNode> a = a.readInjectionPoints(a);
         a.findMethods(a.parseTargets(a), a);
         a.parseInjectionPoints(a);
         a.parseRequirements();
         a.injector = a.parseInjector(a.annotation);
      }
   }

   protected Set<MemberInfo> parseTargets(String a) {
      List<String> a = Annotations.getValue(a.annotation, "method", false);
      if (a == null) {
         throw new InvalidInjectionException(a, String.format("%s annotation on %s is missing method name", a, a.method.name));
      } else {
         Set<MemberInfo> a = new LinkedHashSet();
         Iterator var4 = a.iterator();

         while(var4.hasNext()) {
            String a = (String)var4.next();

            try {
               MemberInfo a = MemberInfo.parseAndValidate(a, a.mixin);
               if (a.owner != null && !a.owner.equals(a.mixin.getTargetClassRef())) {
                  throw new InvalidInjectionException(a, String.format("%s annotation on %s specifies a target class '%s', which is not supported", a, a.method.name, a.owner));
               }

               a.add(a);
            } catch (InvalidMemberDescriptorException var7) {
               throw new InvalidInjectionException(a, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", a, a.method.name, a, a.mixin.getReferenceMapper().getStatus()));
            }
         }

         return a;
      }
   }

   protected List<AnnotationNode> readInjectionPoints(String a) {
      List<AnnotationNode> a = Annotations.getValue(a.annotation, a.atKey, false);
      if (a == null) {
         throw new InvalidInjectionException(a, String.format("%s annotation on %s is missing '%s' value(s)", a, a.method.name, a.atKey));
      } else {
         return a;
      }
   }

   protected void parseInjectionPoints(List<AnnotationNode> a) {
      a.injectionPoints.addAll(InjectionPoint.parse(a.mixin, a.method, a.annotation, (List)a));
   }

   protected void parseRequirements() {
      a.group = a.mixin.getInjectorGroups().parseGroup(a.method, a.mixin.getDefaultInjectorGroup()).add(a);
      Integer a = (Integer)Annotations.getValue(a.annotation, "expect");
      if (a != null) {
         a.expectedCallbackCount = a;
      }

      Integer a = (Integer)Annotations.getValue(a.annotation, "require");
      if (a != null && a > -1) {
         a.requiredCallbackCount = a;
      } else if (a.group.isDefault()) {
         a.requiredCallbackCount = a.mixin.getDefaultRequiredInjections();
      }

      Integer a = (Integer)Annotations.getValue(a.annotation, "allow");
      if (a != null) {
         a.maxCallbackCount = Math.max(Math.max(a.requiredCallbackCount, 1), a);
      }

   }

   protected abstract Injector parseInjector(AnnotationNode var1);

   public boolean isValid() {
      return a.targets.size() > 0 && a.injectionPoints.size() > 0;
   }

   public void prepare() {
      a.targetNodes.clear();
      Iterator var1 = a.targets.iterator();

      while(var1.hasNext()) {
         MethodNode a = (MethodNode)var1.next();
         Target a = a.mixin.getTargetMethod(a);
         InjectorTarget a = new InjectorTarget(a, a);
         a.targetNodes.put(a, a.injector.find(a, a.injectionPoints));
         a.dispose();
      }

   }

   public void inject() {
      Iterator var1 = a.targetNodes.entrySet().iterator();

      while(var1.hasNext()) {
         Entry<Target, List<InjectionNodes.InjectionNode>> a = (Entry)var1.next();
         a.injector.inject((Target)a.getKey(), (List)a.getValue());
      }

      a.targets.clear();
   }

   public void postInject() {
      Iterator var1 = a.injectedMethods.iterator();

      while(var1.hasNext()) {
         MethodNode a = (MethodNode)var1.next();
         a.classNode.methods.add(a);
      }

      String a = a.getDescription();
      String a = a.mixin.getReferenceMapper().getStatus();
      String a = a.getDynamicInfo();
      if (a.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && a.injectedCallbackCount < a.expectedCallbackCount) {
         throw new InvalidInjectionException(a, String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. %s%s", a, a.method.name, a.method.desc, a.mixin, a.expectedCallbackCount, a.injectedCallbackCount, a, a));
      } else if (a.injectedCallbackCount < a.requiredCallbackCount) {
         throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. %s%s", a, a.method.name, a.method.desc, a.mixin, a.injectedCallbackCount, a.requiredCallbackCount, a, a));
      } else if (a.injectedCallbackCount > a.maxCallbackCount) {
         throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", a, a.method.name, a.method.desc, a.mixin, a.injectedCallbackCount, a.maxCallbackCount, a));
      }
   }

   public void notifyInjected(Target a1) {
   }

   protected String getDescription() {
      return "Callback method";
   }

   public String toString() {
      return describeInjector(a.mixin, a.annotation, a.method);
   }

   public Collection<MethodNode> getTargets() {
      return a.targets;
   }

   public MethodSlice getSlice(String a) {
      return a.slices.get(a.getSliceId(a));
   }

   public String getSliceId(String a1) {
      return "";
   }

   public int getInjectedCallbackCount() {
      return a.injectedCallbackCount;
   }

   public MethodNode addMethod(int a, String a, String a) {
      MethodNode a = new MethodNode(327680, a | 4096, a, a, (String)null, (String[])null);
      a.injectedMethods.add(a);
      return a;
   }

   public void addCallbackInvocation(MethodNode a1) {
      ++a.injectedCallbackCount;
   }

   private void findMethods(Set<MemberInfo> a, String a) {
      a.targets.clear();
      int a = a.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
      Iterator var4 = a.iterator();

      while(var4.hasNext()) {
         MemberInfo a = (MemberInfo)var4.next();
         int a = 0;

         label63:
         for(int a = 0; a < a && a < 1; ++a) {
            int a = 0;
            Iterator var9 = a.classNode.methods.iterator();

            while(true) {
               MethodNode a;
               boolean a;
               do {
                  do {
                     if (!var9.hasNext()) {
                        a = a.transform((String)null);
                        continue label63;
                     }

                     a = (MethodNode)var9.next();
                  } while(!a.matches(a.name, a.desc, a));

                  a = Annotations.getVisible(a, MixinMerged.class) != null;
               } while(a.matchAll && (Bytecode.methodIsStatic(a) != a.isStatic || a == a.method || a));

               a.checkTarget(a);
               a.targets.add(a);
               ++a;
               ++a;
            }
         }
      }

      if (a.targets.size() == 0) {
         throw new InvalidInjectionException(a, String.format("%s annotation on %s could not find any targets matching %s in the target class %s. %s%s", a, a.method.name, namesOf(a), a.mixin.getTarget(), a.mixin.getReferenceMapper().getStatus(), a.getDynamicInfo()));
      }
   }

   private void checkTarget(MethodNode a) {
      AnnotationNode a = Annotations.getVisible(a, MixinMerged.class);
      if (a != null) {
         if (Annotations.getVisible(a, Final.class) != null) {
            throw new InvalidInjectionException(a, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", a, a.classNode.name, a.name, a.desc, Annotations.getValue(a, "mixin")));
         }
      }
   }

   protected String getDynamicInfo() {
      AnnotationNode a = Annotations.getInvisible(a.method, Dynamic.class);
      String a = Strings.nullToEmpty((String)Annotations.getValue(a));
      Type a = (Type)Annotations.getValue(a, "mixin");
      if (a != null) {
         a = String.format("{%s} %s", a.getClassName(), a).trim();
      }

      return a.length() > 0 ? String.format(" Method is @Dynamic(%s)", a) : "";
   }

   public static InjectionInfo parse(MixinTargetContext a, MethodNode a) {
      AnnotationNode a = getInjectorAnnotation(a.getMixin(), a);
      if (a == null) {
         return null;
      } else if (a.desc.endsWith(Inject.class.getSimpleName() + ";")) {
         return new CallbackInjectionInfo(a, a, a);
      } else if (a.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
         return new ModifyArgInjectionInfo(a, a, a);
      } else if (a.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
         return new ModifyArgsInjectionInfo(a, a, a);
      } else if (a.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
         return new RedirectInjectionInfo(a, a, a);
      } else if (a.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
         return new ModifyVariableInjectionInfo(a, a, a);
      } else {
         return a.desc.endsWith(ModifyConstant.class.getSimpleName() + ";") ? new ModifyConstantInjectionInfo(a, a, a) : null;
      }
   }

   public static AnnotationNode getInjectorAnnotation(IMixinInfo a, MethodNode a) {
      AnnotationNode a = null;

      try {
         a = Annotations.getSingleVisible(a, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
         return a;
      } catch (IllegalArgumentException var4) {
         throw new InvalidMixinException(a, String.format("Error parsing annotations on %s in %s: %s", a.name, a.getClassName(), var4.getMessage()));
      }
   }

   public static String getInjectorPrefix(AnnotationNode a) {
      if (a != null) {
         if (a.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
            return "modify";
         }

         if (a.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
            return "args";
         }

         if (a.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
            return "redirect";
         }

         if (a.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
            return "localvar";
         }

         if (a.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
            return "constant";
         }
      }

      return "handler";
   }

   static String describeInjector(IMixinContext a, AnnotationNode a, MethodNode a) {
      return String.format("%s->@%s::%s%s", a.toString(), Bytecode.getSimpleName(a), a.name, a.desc);
   }

   private static String namesOf(Collection<MemberInfo> a) {
      int a = 0;
      int a = a.size();
      StringBuilder a = new StringBuilder();

      for(Iterator var4 = a.iterator(); var4.hasNext(); ++a) {
         MemberInfo a = (MemberInfo)var4.next();
         if (a > 0) {
            if (a == a - 1) {
               a.append(" or ");
            } else {
               a.append(", ");
            }
         }

         a.append('\'').append(a.name).append('\'');
      }

      return a.toString();
   }
}

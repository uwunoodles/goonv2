package org.spongepowered.asm.mixin.injection;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
import org.spongepowered.asm.mixin.injection.points.MethodHead;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public abstract class InjectionPoint {
   public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
   public static final int MAX_ALLOWED_SHIFT_BY = 5;
   private static Map<String, Class<? extends InjectionPoint>> types = new HashMap();
   private final String slice;
   private final InjectionPoint.Selector selector;
   private final String id;

   protected InjectionPoint() {
      this("", InjectionPoint.Selector.DEFAULT, (String)null);
   }

   protected InjectionPoint(InjectionPointData a) {
      this(a.getSlice(), a.getSelector(), a.getId());
   }

   public InjectionPoint(String a, InjectionPoint.Selector a, String a) {
      a.slice = a;
      a.selector = a;
      a.id = a;
   }

   public String getSlice() {
      return a.slice;
   }

   public InjectionPoint.Selector getSelector() {
      return a.selector;
   }

   public String getId() {
      return a.id;
   }

   public boolean checkPriority(int a, int a) {
      return a < a;
   }

   public abstract boolean find(String var1, InsnList var2, Collection<AbstractInsnNode> var3);

   public String toString() {
      return String.format("@At(\"%s\")", a.getAtCode());
   }

   protected static AbstractInsnNode nextNode(InsnList a, AbstractInsnNode a) {
      int a = a.indexOf(a) + 1;
      return a > 0 && a < a.size() ? a.get(a) : a;
   }

   public static InjectionPoint and(InjectionPoint... a) {
      return new InjectionPoint.Intersection(a);
   }

   public static InjectionPoint or(InjectionPoint... a) {
      return new InjectionPoint.Union(a);
   }

   public static InjectionPoint after(InjectionPoint a) {
      return new InjectionPoint.Shift(a, 1);
   }

   public static InjectionPoint before(InjectionPoint a) {
      return new InjectionPoint.Shift(a, -1);
   }

   public static InjectionPoint shift(InjectionPoint a, int a) {
      return new InjectionPoint.Shift(a, a);
   }

   public static List<InjectionPoint> parse(IInjectionPointContext a, List<AnnotationNode> a) {
      return parse(a.getContext(), a.getMethod(), a.getAnnotation(), a);
   }

   public static List<InjectionPoint> parse(IMixinContext a, MethodNode a, AnnotationNode a, List<AnnotationNode> a) {
      Builder<InjectionPoint> a = ImmutableList.builder();
      Iterator var5 = a.iterator();

      while(var5.hasNext()) {
         AnnotationNode a = (AnnotationNode)var5.next();
         InjectionPoint a = parse(a, a, a, a);
         if (a != null) {
            a.add(a);
         }
      }

      return a.build();
   }

   public static InjectionPoint parse(IInjectionPointContext a, At a) {
      return parse(a.getContext(), a.getMethod(), a.getAnnotation(), a.value(), a.shift(), a.by(), Arrays.asList(a.args()), a.target(), a.slice(), a.ordinal(), a.opcode(), a.id());
   }

   public static InjectionPoint parse(IMixinContext a, MethodNode a, AnnotationNode a, At a) {
      return parse(a, a, a, a.value(), a.shift(), a.by(), Arrays.asList(a.args()), a.target(), a.slice(), a.ordinal(), a.opcode(), a.id());
   }

   public static InjectionPoint parse(IInjectionPointContext a, AnnotationNode a) {
      return parse(a.getContext(), a.getMethod(), a.getAnnotation(), a);
   }

   public static InjectionPoint parse(IMixinContext a, MethodNode a, AnnotationNode a, AnnotationNode a) {
      String a = (String)Annotations.getValue(a, "value");
      List<String> a = (List)Annotations.getValue(a, "args");
      String a = (String)Annotations.getValue(a, "target", (Object)"");
      String a = (String)Annotations.getValue(a, "slice", (Object)"");
      At.Shift a = (At.Shift)Annotations.getValue(a, "shift", At.Shift.class, At.Shift.NONE);
      int a = (Integer)Annotations.getValue(a, "by", (int)0);
      int a = (Integer)Annotations.getValue(a, "ordinal", (int)-1);
      int a = (Integer)Annotations.getValue(a, "opcode", (int)0);
      String a = (String)Annotations.getValue(a, "id");
      if (a == null) {
         a = ImmutableList.of();
      }

      return parse(a, a, a, a, a, a, (List)a, a, a, a, a, a);
   }

   public static InjectionPoint parse(IMixinContext a, MethodNode a, AnnotationNode a, String a, At.Shift a, int a, List<String> a, String a, String a, int a, int a, String a) {
      InjectionPointData a = new InjectionPointData(a, a, a, a, a, a, a, a, a, a);
      Class<? extends InjectionPoint> a = findClass(a, a);
      InjectionPoint a = create(a, a, a);
      return shift(a, a, a, a, a, a);
   }

   private static Class<? extends InjectionPoint> findClass(IMixinContext a, InjectionPointData a) {
      String a = a.getType();
      Class<? extends InjectionPoint> a = (Class)types.get(a);
      if (a == null) {
         if (!a.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
            throw new InvalidInjectionException(a, a + " is not a valid injection point specifier");
         }

         try {
            a = Class.forName(a);
            types.put(a, a);
         } catch (Exception var5) {
            throw new InvalidInjectionException(a, a + " could not be loaded or is not a valid InjectionPoint", var5);
         }
      }

      return a;
   }

   private static InjectionPoint create(IMixinContext a, InjectionPointData a, Class<? extends InjectionPoint> a) {
      Constructor a = null;

      try {
         a = a.getDeclaredConstructor(InjectionPointData.class);
         a.setAccessible(true);
      } catch (NoSuchMethodException var7) {
         throw new InvalidInjectionException(a, a.getName() + " must contain a constructor which accepts an InjectionPointData", var7);
      }

      InjectionPoint a = null;

      try {
         a = (InjectionPoint)a.newInstance(a);
         return a;
      } catch (Exception var6) {
         throw new InvalidInjectionException(a, "Error whilst instancing injection point " + a.getName() + " for " + a.getAt(), var6);
      }
   }

   private static InjectionPoint shift(IMixinContext a, MethodNode a, AnnotationNode a, InjectionPoint a, At.Shift a, int a) {
      if (a != null) {
         if (a == At.Shift.BEFORE) {
            return before(a);
         }

         if (a == At.Shift.AFTER) {
            return after(a);
         }

         if (a == At.Shift.BY) {
            validateByValue(a, a, a, a, a);
            return shift(a, a);
         }
      }

      return a;
   }

   private static void validateByValue(IMixinContext a, MethodNode a, AnnotationNode a, InjectionPoint a, int a) {
      MixinEnvironment a = a.getMixin().getConfig().getEnvironment();
      InjectionPoint.ShiftByViolationBehaviour a = (InjectionPoint.ShiftByViolationBehaviour)a.getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, InjectionPoint.ShiftByViolationBehaviour.WARN);
      if (a != InjectionPoint.ShiftByViolationBehaviour.IGNORE) {
         String a = "the maximum allowed value: ";
         String a = "Increase the value of maxShiftBy to suppress this warning.";
         int a = 0;
         if (a instanceof MixinTargetContext) {
            a = ((MixinTargetContext)a).getMaxShiftByValue();
         }

         if (a > a) {
            if (a > 5) {
               a = "MAX_ALLOWED_SHIFT_BY=";
               a = "You must use an alternate query or a custom injection point.";
               a = 5;
            }

            String a = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds %s%d. %s", Bytecode.getSimpleName(a), a, a, a, a.name, a, a, a);
            if (a == InjectionPoint.ShiftByViolationBehaviour.WARN && a < 5) {
               LogManager.getLogger("mixin").warn(a);
            } else {
               throw new InvalidInjectionException(a, a);
            }
         }
      }
   }

   protected String getAtCode() {
      InjectionPoint.AtCode a = (InjectionPoint.AtCode)a.getClass().getAnnotation(InjectionPoint.AtCode.class);
      return a == null ? a.getClass().getName() : a.value();
   }

   public static void register(Class<? extends InjectionPoint> a) {
      InjectionPoint.AtCode a = (InjectionPoint.AtCode)a.getAnnotation(InjectionPoint.AtCode.class);
      if (a == null) {
         throw new IllegalArgumentException("Injection point class " + a + " is not annotated with @AtCode");
      } else {
         Class<? extends InjectionPoint> a = (Class)types.get(a.value());
         if (a != null && !a.equals(a)) {
            LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[]{a.value(), a.getName(), a.getName()});
         }

         types.put(a.value(), a);
      }
   }

   static {
      register(BeforeFieldAccess.class);
      register(BeforeInvoke.class);
      register(BeforeNew.class);
      register(BeforeReturn.class);
      register(BeforeStringInvoke.class);
      register(JumpInsnPoint.class);
      register(MethodHead.class);
      register(AfterInvoke.class);
      register(BeforeLoadLocal.class);
      register(AfterStoreLocal.class);
      register(BeforeFinalReturn.class);
      register(BeforeConstant.class);
   }

   static final class Shift extends InjectionPoint {
      private final InjectionPoint input;
      private final int shift;

      public Shift(InjectionPoint a, int a) {
         if (a == null) {
            throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
         } else {
            a.input = a;
            a.shift = a;
         }
      }

      public String toString() {
         return "InjectionPoint(" + a.getClass().getSimpleName() + ")[" + a.input + "]";
      }

      public boolean find(String a, InsnList a, Collection<AbstractInsnNode> a) {
         List<AbstractInsnNode> a = a instanceof List ? (List)a : new ArrayList(a);
         a.input.find(a, a, a);

         for(int a = 0; a < ((List)a).size(); ++a) {
            ((List)a).set(a, a.get(a.indexOf((AbstractInsnNode)((List)a).get(a)) + a.shift));
         }

         if (a != a) {
            a.clear();
            a.addAll((Collection)a);
         }

         return a.size() > 0;
      }
   }

   static final class Union extends InjectionPoint.CompositeInjectionPoint {
      public Union(InjectionPoint... a) {
         super(a);
      }

      public boolean find(String a, InsnList a, Collection<AbstractInsnNode> a) {
         LinkedHashSet<AbstractInsnNode> a = new LinkedHashSet();

         for(int a = 0; a < a.components.length; ++a) {
            a.components[a].find(a, a, a);
         }

         a.addAll(a);
         return a.size() > 0;
      }
   }

   static final class Intersection extends InjectionPoint.CompositeInjectionPoint {
      public Intersection(InjectionPoint... a) {
         super(a);
      }

      public boolean find(String a, InsnList a, Collection<AbstractInsnNode> a) {
         boolean a = false;
         ArrayList<AbstractInsnNode>[] a = (ArrayList[])((ArrayList[])Array.newInstance(ArrayList.class, a.components.length));

         for(int a = 0; a < a.components.length; ++a) {
            a[a] = new ArrayList();
            a.components[a].find(a, a, a[a]);
         }

         ArrayList<AbstractInsnNode> a = a[0];

         for(int a = 0; a < a.size(); ++a) {
            AbstractInsnNode a = (AbstractInsnNode)a.get(a);
            boolean a = true;

            for(int a = 1; a < a.length && a[a].contains(a); ++a) {
            }

            if (a) {
               a.add(a);
               a = true;
            }
         }

         return a;
      }
   }

   abstract static class CompositeInjectionPoint extends InjectionPoint {
      protected final InjectionPoint[] components;

      protected CompositeInjectionPoint(InjectionPoint... a) {
         if (a != null && a.length >= 2) {
            a.components = a;
         } else {
            throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
         }
      }

      public String toString() {
         return "CompositeInjectionPoint(" + a.getClass().getSimpleName() + ")[" + Joiner.on(',').join(a.components) + "]";
      }
   }

   static enum ShiftByViolationBehaviour {
      IGNORE,
      WARN,
      ERROR;
   }

   public static enum Selector {
      FIRST,
      LAST,
      ONE;

      public static final InjectionPoint.Selector DEFAULT = FIRST;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE})
   public @interface AtCode {
      String value();
   }
}

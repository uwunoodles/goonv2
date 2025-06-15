package org.spongepowered.asm.mixin.injection.points;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

@InjectionPoint.AtCode("CONSTANT")
public class BeforeConstant extends InjectionPoint {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final int ordinal;
   private final boolean nullValue;
   private final Integer intValue;
   private final Float floatValue;
   private final Long longValue;
   private final Double doubleValue;
   private final String stringValue;
   private final Type typeValue;
   private final int[] expandOpcodes;
   private final boolean expand;
   private final String matchByType;
   private final boolean log;

   public BeforeConstant(IMixinContext a, AnnotationNode a, String a) {
      super((String)Annotations.getValue(a, "slice", (Object)""), InjectionPoint.Selector.DEFAULT, (String)null);
      Boolean a = (Boolean)Annotations.getValue(a, "nullValue", (Object)((Boolean)null));
      a.ordinal = (Integer)Annotations.getValue(a, "ordinal", (int)-1);
      a.nullValue = a != null && a;
      a.intValue = (Integer)Annotations.getValue(a, "intValue", (Object)((Integer)null));
      a.floatValue = (Float)Annotations.getValue(a, "floatValue", (Object)((Float)null));
      a.longValue = (Long)Annotations.getValue(a, "longValue", (Object)((Long)null));
      a.doubleValue = (Double)Annotations.getValue(a, "doubleValue", (Object)((Double)null));
      a.stringValue = (String)Annotations.getValue(a, "stringValue", (Object)((String)null));
      a.typeValue = (Type)Annotations.getValue(a, "classValue", (Object)((Type)null));
      a.matchByType = a.validateDiscriminator(a, a, a, "on @Constant annotation");
      a.expandOpcodes = a.parseExpandOpcodes(Annotations.getValue(a, "expandZeroConditions", true, Constant.Condition.class));
      a.expand = a.expandOpcodes.length > 0;
      a.log = (Boolean)Annotations.getValue(a, "log", (Object)Boolean.FALSE);
   }

   public BeforeConstant(InjectionPointData a) {
      super(a);
      String a = a.get("nullValue", (String)null);
      Boolean a = a != null ? Boolean.parseBoolean(a) : null;
      a.ordinal = a.getOrdinal();
      a.nullValue = a != null && a;
      a.intValue = Ints.tryParse(a.get("intValue", ""));
      a.floatValue = Floats.tryParse(a.get("floatValue", ""));
      a.longValue = Longs.tryParse(a.get("longValue", ""));
      a.doubleValue = Doubles.tryParse(a.get("doubleValue", ""));
      a.stringValue = a.get("stringValue", (String)null);
      String a = a.get("classValue", (String)null);
      a.typeValue = a != null ? Type.getObjectType(a.replace('.', '/')) : null;
      a.matchByType = a.validateDiscriminator(a.getContext(), "V", a, "in @At(\"CONSTANT\") args");
      if ("V".equals(a.matchByType)) {
         throw new InvalidInjectionException(a.getContext(), "No constant discriminator could be parsed in @At(\"CONSTANT\") args");
      } else {
         List<Constant.Condition> a = new ArrayList();
         String a = a.get("expandZeroConditions", "").toLowerCase();
         Constant.Condition[] var7 = Constant.Condition.values();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Constant.Condition a = var7[var9];
            if (a.contains(a.name().toLowerCase())) {
               a.add(a);
            }
         }

         a.expandOpcodes = a.parseExpandOpcodes(a);
         a.expand = a.expandOpcodes.length > 0;
         a.log = a.get("log", false);
      }
   }

   private String validateDiscriminator(IMixinContext a, String a, Boolean a, String a) {
      int a = count(a, a.intValue, a.floatValue, a.longValue, a.doubleValue, a.stringValue, a.typeValue);
      if (a == 1) {
         a = null;
      } else if (a > 1) {
         throw new InvalidInjectionException(a, "Conflicting constant discriminators specified " + a + " for " + a);
      }

      return a;
   }

   private int[] parseExpandOpcodes(List<Constant.Condition> a) {
      Set<Integer> a = new HashSet();
      Iterator var3 = a.iterator();

      while(var3.hasNext()) {
         Constant.Condition a = (Constant.Condition)var3.next();
         Constant.Condition a = a.getEquivalentCondition();
         int[] var6 = a.getOpcodes();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            int a = var6[var8];
            a.add(a);
         }
      }

      return Ints.toArray(a);
   }

   public boolean find(String a, InsnList a, Collection<AbstractInsnNode> a) {
      boolean a = false;
      a.log("BeforeConstant is searching for constants in method with descriptor {}", a);
      ListIterator<AbstractInsnNode> a = a.iterator();
      int a = 0;
      int a = 0;

      while(a.hasNext()) {
         AbstractInsnNode a = (AbstractInsnNode)a.next();
         boolean a = a.expand ? a.matchesConditionalInsn(a, a) : a.matchesConstantInsn(a);
         if (a) {
            a.log("    BeforeConstant found a matching constant{} at ordinal {}", a.matchByType != null ? " TYPE" : " value", a);
            if (a.ordinal == -1 || a.ordinal == a) {
               a.log("      BeforeConstant found {}", Bytecode.describeNode(a).trim());
               a.add(a);
               a = true;
            }

            ++a;
         }

         if (!(a instanceof LabelNode) && !(a instanceof FrameNode)) {
            a = a.getOpcode();
         }
      }

      return a;
   }

   private boolean matchesConditionalInsn(int a, AbstractInsnNode a) {
      int[] var3 = a.expandOpcodes;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int a = var3[var5];
         int a = a.getOpcode();
         if (a == a) {
            if (a != 148 && a != 149 && a != 150 && a != 151 && a != 152) {
               a.log("  BeforeConstant found {} instruction", Bytecode.getOpcodeName(a));
               return true;
            }

            a.log("  BeforeConstant is ignoring {} following {}", Bytecode.getOpcodeName(a), Bytecode.getOpcodeName(a));
            return false;
         }
      }

      if (a.intValue != null && a.intValue == 0 && Bytecode.isConstant(a)) {
         Object a = Bytecode.getConstant(a);
         a.log("  BeforeConstant found INTEGER constant: value = {}", a);
         return a instanceof Integer && (Integer)a == 0;
      } else {
         return false;
      }
   }

   private boolean matchesConstantInsn(AbstractInsnNode a) {
      if (!Bytecode.isConstant(a)) {
         return false;
      } else {
         Object a = Bytecode.getConstant(a);
         if (a == null) {
            a.log("  BeforeConstant found NULL constant: nullValue = {}", a.nullValue);
            return a.nullValue || "Ljava/lang/Object;".equals(a.matchByType);
         } else if (a instanceof Integer) {
            a.log("  BeforeConstant found INTEGER constant: value = {}, intValue = {}", a, a.intValue);
            return a.equals(a.intValue) || "I".equals(a.matchByType);
         } else if (a instanceof Float) {
            a.log("  BeforeConstant found FLOAT constant: value = {}, floatValue = {}", a, a.floatValue);
            return a.equals(a.floatValue) || "F".equals(a.matchByType);
         } else if (a instanceof Long) {
            a.log("  BeforeConstant found LONG constant: value = {}, longValue = {}", a, a.longValue);
            return a.equals(a.longValue) || "J".equals(a.matchByType);
         } else if (a instanceof Double) {
            a.log("  BeforeConstant found DOUBLE constant: value = {}, doubleValue = {}", a, a.doubleValue);
            return a.equals(a.doubleValue) || "D".equals(a.matchByType);
         } else if (a instanceof String) {
            a.log("  BeforeConstant found STRING constant: value = {}, stringValue = {}", a, a.stringValue);
            return a.equals(a.stringValue) || "Ljava/lang/String;".equals(a.matchByType);
         } else if (!(a instanceof Type)) {
            return false;
         } else {
            a.log("  BeforeConstant found CLASS constant: value = {}, typeValue = {}", a, a.typeValue);
            return a.equals(a.typeValue) || "Ljava/lang/Class;".equals(a.matchByType);
         }
      }
   }

   protected void log(String a, Object... a) {
      if (a.log) {
         logger.info(a, a);
      }

   }

   private static int count(Object... a) {
      int a = 0;
      Object[] var2 = a;
      int var3 = a.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object a = var2[var4];
         if (a != null) {
            ++a;
         }
      }

      return a;
   }
}

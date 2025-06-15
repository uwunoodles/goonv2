package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public class InjectionPointData {
   private static final Pattern AT_PATTERN = createPattern();
   private final Map<String, String> args = new HashMap();
   private final IMixinContext context;
   private final MethodNode method;
   private final AnnotationNode parent;
   private final String at;
   private final String type;
   private final InjectionPoint.Selector selector;
   private final String target;
   private final String slice;
   private final int ordinal;
   private final int opcode;
   private final String id;

   public InjectionPointData(IMixinContext a, MethodNode a, AnnotationNode a, String a, List<String> a, String a, String a, int a, int a, String a) {
      a.context = a;
      a.method = a;
      a.parent = a;
      a.at = a;
      a.target = a;
      a.slice = Strings.nullToEmpty(a);
      a.ordinal = Math.max(-1, a);
      a.opcode = a;
      a.id = a;
      a.parseArgs(a);
      a.args.put("target", a);
      a.args.put("ordinal", String.valueOf(a));
      a.args.put("opcode", String.valueOf(a));
      Matcher a = AT_PATTERN.matcher(a);
      a.type = parseType(a, a);
      a.selector = parseSelector(a);
   }

   private void parseArgs(List<String> a) {
      if (a != null) {
         Iterator var2 = a.iterator();

         while(var2.hasNext()) {
            String a = (String)var2.next();
            if (a != null) {
               int a = a.indexOf(61);
               if (a > -1) {
                  a.args.put(a.substring(0, a), a.substring(a + 1));
               } else {
                  a.args.put(a, "");
               }
            }
         }

      }
   }

   public String getAt() {
      return a.at;
   }

   public String getType() {
      return a.type;
   }

   public InjectionPoint.Selector getSelector() {
      return a.selector;
   }

   public IMixinContext getContext() {
      return a.context;
   }

   public MethodNode getMethod() {
      return a.method;
   }

   public Type getMethodReturnType() {
      return Type.getReturnType(a.method.desc);
   }

   public AnnotationNode getParent() {
      return a.parent;
   }

   public String getSlice() {
      return a.slice;
   }

   public LocalVariableDiscriminator getLocalVariableDiscriminator() {
      return LocalVariableDiscriminator.parse(a.parent);
   }

   public String get(String a, String a) {
      String a = (String)a.args.get(a);
      return a != null ? a : a;
   }

   public int get(String a, int a) {
      return parseInt(a.get(a, String.valueOf(a)), a);
   }

   public boolean get(String a, boolean a) {
      return parseBoolean(a.get(a, String.valueOf(a)), a);
   }

   public MemberInfo get(String a) {
      try {
         return MemberInfo.parseAndValidate(a.get(a, ""), a.context);
      } catch (InvalidMemberDescriptorException var3) {
         throw new InvalidInjectionPointException(a.context, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", new Object[]{a.at, a, a.target, InjectionInfo.describeInjector(a.context, a.parent, a.method)});
      }
   }

   public MemberInfo getTarget() {
      try {
         return MemberInfo.parseAndValidate(a.target, a.context);
      } catch (InvalidMemberDescriptorException var2) {
         throw new InvalidInjectionPointException(a.context, "Failed parsing @At(\"%s\") descriptor \"%s\" on %s", new Object[]{a.at, a.target, InjectionInfo.describeInjector(a.context, a.parent, a.method)});
      }
   }

   public int getOrdinal() {
      return a.ordinal;
   }

   public int getOpcode() {
      return a.opcode;
   }

   public int getOpcode(int a) {
      return a.opcode > 0 ? a.opcode : a;
   }

   public int getOpcode(int a, int... a) {
      int[] var3 = a;
      int var4 = a.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int a = var3[var5];
         if (a.opcode == a) {
            return a.opcode;
         }
      }

      return a;
   }

   public String getId() {
      return a.id;
   }

   public String toString() {
      return a.type;
   }

   private static Pattern createPattern() {
      return Pattern.compile(String.format("^([^:]+):?(%s)?$", Joiner.on('|').join(InjectionPoint.Selector.values())));
   }

   public static String parseType(String a) {
      Matcher a = AT_PATTERN.matcher(a);
      return parseType(a, a);
   }

   private static String parseType(Matcher a, String a) {
      return a.matches() ? a.group(1) : a;
   }

   private static InjectionPoint.Selector parseSelector(Matcher a) {
      return a.matches() && a.group(2) != null ? InjectionPoint.Selector.valueOf(a.group(2)) : InjectionPoint.Selector.DEFAULT;
   }

   private static int parseInt(String a, int a) {
      try {
         return Integer.parseInt(a);
      } catch (Exception var3) {
         return a;
      }
   }

   private static boolean parseBoolean(String a, boolean a) {
      try {
         return Boolean.parseBoolean(a);
      } catch (Exception var3) {
         return a;
      }
   }
}

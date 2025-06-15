package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.signature.SignatureWriter;
import org.spongepowered.asm.lib.tree.ClassNode;

public class ClassSignature {
   protected static final String OBJECT = "java/lang/Object";
   private final Map<ClassSignature.TypeVar, ClassSignature.TokenHandle> types = new LinkedHashMap();
   private ClassSignature.Token superClass = new ClassSignature.Token("java/lang/Object");
   private final List<ClassSignature.Token> interfaces = new ArrayList();
   private final Deque<String> rawInterfaces = new LinkedList();

   ClassSignature() {
   }

   private ClassSignature read(String a) {
      if (a != null) {
         try {
            (new SignatureReader(a)).accept(new ClassSignature.SignatureParser());
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return a;
   }

   protected ClassSignature.TypeVar getTypeVar(String a) {
      Iterator var2 = a.types.keySet().iterator();

      ClassSignature.TypeVar a;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         a = (ClassSignature.TypeVar)var2.next();
      } while(!a.matches(a));

      return a;
   }

   protected ClassSignature.TokenHandle getType(String a) {
      Iterator var2 = a.types.keySet().iterator();

      ClassSignature.TypeVar a;
      do {
         if (!var2.hasNext()) {
            ClassSignature.TokenHandle a = new ClassSignature.TokenHandle();
            a.types.put(new ClassSignature.TypeVar(a), a);
            return a;
         }

         a = (ClassSignature.TypeVar)var2.next();
      } while(!a.matches(a));

      return (ClassSignature.TokenHandle)a.types.get(a);
   }

   protected String getTypeVar(ClassSignature.TokenHandle a) {
      Iterator var2 = a.types.entrySet().iterator();

      ClassSignature.TypeVar a;
      ClassSignature.TokenHandle a;
      do {
         if (!var2.hasNext()) {
            return a.token.asType();
         }

         Entry<ClassSignature.TypeVar, ClassSignature.TokenHandle> a = (Entry)var2.next();
         a = (ClassSignature.TypeVar)a.getKey();
         a = (ClassSignature.TokenHandle)a.getValue();
      } while(a != a && a.asToken() != a.asToken());

      return "T" + a + ";";
   }

   protected void addTypeVar(ClassSignature.TypeVar a, ClassSignature.TokenHandle a) throws IllegalArgumentException {
      if (a.types.containsKey(a)) {
         throw new IllegalArgumentException("TypeVar " + a + " is already present on " + a);
      } else {
         a.types.put(a, a);
      }
   }

   protected void setSuperClass(ClassSignature.Token a) {
      a.superClass = a;
   }

   public String getSuperClass() {
      return a.superClass.asType(true);
   }

   protected void addInterface(ClassSignature.Token a) {
      if (!a.isRaw()) {
         String a = a.asType(true);
         ListIterator a = a.interfaces.listIterator();

         while(a.hasNext()) {
            ClassSignature.Token a = (ClassSignature.Token)a.next();
            if (a.isRaw() && a.asType(true).equals(a)) {
               a.set(a);
               return;
            }
         }
      }

      a.interfaces.add(a);
   }

   public void addInterface(String a) {
      a.rawInterfaces.add(a);
   }

   protected void addRawInterface(String a) {
      ClassSignature.Token a = new ClassSignature.Token(a);
      String a = a.asType(true);
      Iterator var4 = a.interfaces.iterator();

      ClassSignature.Token a;
      do {
         if (!var4.hasNext()) {
            a.interfaces.add(a);
            return;
         }

         a = (ClassSignature.Token)var4.next();
      } while(!a.asType(true).equals(a));

   }

   public void merge(ClassSignature a) {
      try {
         Set<String> a = new HashSet();
         Iterator var3 = a.types.keySet().iterator();

         while(true) {
            if (!var3.hasNext()) {
               a.conform(a);
               break;
            }

            ClassSignature.TypeVar a = (ClassSignature.TypeVar)var3.next();
            a.add(a.toString());
         }
      } catch (IllegalStateException var5) {
         var5.printStackTrace();
         return;
      }

      Iterator var6 = a.types.entrySet().iterator();

      while(var6.hasNext()) {
         Entry<ClassSignature.TypeVar, ClassSignature.TokenHandle> a = (Entry)var6.next();
         a.addTypeVar((ClassSignature.TypeVar)a.getKey(), (ClassSignature.TokenHandle)a.getValue());
      }

      var6 = a.interfaces.iterator();

      while(var6.hasNext()) {
         ClassSignature.Token a = (ClassSignature.Token)var6.next();
         a.addInterface(a);
      }

   }

   private void conform(Set<String> a) {
      Iterator var2 = a.types.keySet().iterator();

      while(var2.hasNext()) {
         ClassSignature.TypeVar a = (ClassSignature.TypeVar)var2.next();
         String a = a.findUniqueName(a.getOriginalName(), a);
         a.rename(a);
         a.add(a);
      }

   }

   private String findUniqueName(String a, Set<String> a) {
      if (!a.contains(a)) {
         return a;
      } else {
         String a;
         if (a.length() == 1) {
            a = a.findOffsetName(a.charAt(0), a);
            if (a != null) {
               return a;
            }
         }

         a = a.findOffsetName('T', a, "", a);
         if (a != null) {
            return a;
         } else {
            a = a.findOffsetName('T', a, a, "");
            if (a != null) {
               return a;
            } else {
               a = a.findOffsetName('T', a, "T", a);
               if (a != null) {
                  return a;
               } else {
                  a = a.findOffsetName('T', a, "", a + "Type");
                  if (a != null) {
                     return a;
                  } else {
                     throw new IllegalStateException("Failed to conform type var: " + a);
                  }
               }
            }
         }
      }
   }

   private String findOffsetName(char a, Set<String> a) {
      return a.findOffsetName(a, a, "", "");
   }

   private String findOffsetName(char a, Set<String> a, String a, String a) {
      String a = String.format("%s%s%s", a, a, a);
      if (!a.contains(a)) {
         return a;
      } else {
         if (a > '@' && a < '[') {
            for(int a = a - 64; a + 65 != a; a %= 26) {
               a = String.format("%s%s%s", a, (char)(a + 65), a);
               if (!a.contains(a)) {
                  return a;
               }

               ++a;
            }
         }

         return null;
      }
   }

   public SignatureVisitor getRemapper() {
      return new ClassSignature.SignatureRemapper();
   }

   public String toString() {
      while(a.rawInterfaces.size() > 0) {
         a.addRawInterface((String)a.rawInterfaces.remove());
      }

      StringBuilder a = new StringBuilder();
      if (a.types.size() > 0) {
         boolean a = false;
         StringBuilder a = new StringBuilder();
         Iterator var4 = a.types.entrySet().iterator();

         while(var4.hasNext()) {
            Entry<ClassSignature.TypeVar, ClassSignature.TokenHandle> a = (Entry)var4.next();
            String a = ((ClassSignature.TokenHandle)a.getValue()).asBound();
            if (!a.isEmpty()) {
               a.append(a.getKey()).append(':').append(a);
               a = true;
            }
         }

         if (a) {
            a.append('<').append(a).append('>');
         }
      }

      a.append(a.superClass.asType());
      Iterator var7 = a.interfaces.iterator();

      while(var7.hasNext()) {
         ClassSignature.Token a = (ClassSignature.Token)var7.next();
         a.append(a.asType());
      }

      return a.toString();
   }

   public ClassSignature wake() {
      return a;
   }

   public static ClassSignature of(String a) {
      return (new ClassSignature()).read(a);
   }

   public static ClassSignature of(ClassNode a) {
      return a.signature != null ? of(a.signature) : generate(a);
   }

   public static ClassSignature ofLazy(ClassNode a) {
      return (ClassSignature)(a.signature != null ? new ClassSignature.Lazy(a.signature) : generate(a));
   }

   private static ClassSignature generate(ClassNode a) {
      ClassSignature a = new ClassSignature();
      a.setSuperClass(new ClassSignature.Token(a.superName != null ? a.superName : "java/lang/Object"));
      Iterator var2 = a.interfaces.iterator();

      while(var2.hasNext()) {
         String a = (String)var2.next();
         a.addInterface(new ClassSignature.Token(a));
      }

      return a;
   }

   class SignatureRemapper extends SignatureWriter {
      private final Set<String> localTypeVars = new HashSet();

      public void visitFormalTypeParameter(String ax) {
         a.localTypeVars.add(ax);
         super.visitFormalTypeParameter(ax);
      }

      public void visitTypeVariable(String ax) {
         if (!a.localTypeVars.contains(ax)) {
            ClassSignature.TypeVar axx = ClassSignature.this.getTypeVar(ax);
            if (axx != null) {
               super.visitTypeVariable(axx.toString());
               return;
            }
         }

         super.visitTypeVariable(ax);
      }
   }

   class SignatureParser extends SignatureVisitor {
      private ClassSignature.SignatureParser.FormalParamElement param;

      SignatureParser() {
         super(327680);
      }

      public void visitFormalTypeParameter(String ax) {
         a.param = new ClassSignature.SignatureParser.FormalParamElement(ax);
      }

      public SignatureVisitor visitClassBound() {
         return a.param.visitClassBound();
      }

      public SignatureVisitor visitInterfaceBound() {
         return a.param.visitInterfaceBound();
      }

      public SignatureVisitor visitSuperclass() {
         return new ClassSignature.SignatureParser.SuperClassElement();
      }

      public SignatureVisitor visitInterface() {
         return new ClassSignature.SignatureParser.InterfaceElement();
      }

      class InterfaceElement extends ClassSignature.SignatureParser.TokenElement {
         InterfaceElement() {
            super();
         }

         public void visitEnd() {
            ClassSignature.this.addInterface(a.token);
         }
      }

      class SuperClassElement extends ClassSignature.SignatureParser.TokenElement {
         SuperClassElement() {
            super();
         }

         public void visitEnd() {
            ClassSignature.this.setSuperClass(a.token);
         }
      }

      class BoundElement extends ClassSignature.SignatureParser.TokenElement {
         private final ClassSignature.SignatureParser.TokenElement type;
         private final boolean classBound;

         BoundElement(ClassSignature.SignatureParser.TokenElement axxx, boolean ax) {
            super();
            a.type = axxx;
            a.classBound = ax;
         }

         public void visitClassType(String ax) {
            a.token = a.type.token.addBound(ax, a.classBound);
         }

         public void visitTypeArgument() {
            a.token.addTypeArgument('*');
         }

         public SignatureVisitor visitTypeArgument(char ax) {
            return SignatureParser.this.new TypeArgElement(a, ax);
         }
      }

      class TypeArgElement extends ClassSignature.SignatureParser.TokenElement {
         private final ClassSignature.SignatureParser.TokenElement type;
         private final char wildcard;

         TypeArgElement(ClassSignature.SignatureParser.TokenElement axxx, char ax) {
            super();
            a.type = axxx;
            a.wildcard = ax;
         }

         public SignatureVisitor visitArrayType() {
            a.type.setArray();
            return a;
         }

         public void visitBaseType(char ax) {
            a.token = a.type.addTypeArgument(ax).asToken();
         }

         public void visitTypeVariable(String ax) {
            ClassSignature.TokenHandle axx = ClassSignature.this.getType(ax);
            a.token = a.type.addTypeArgument(axx).setWildcard(a.wildcard).asToken();
         }

         public void visitClassType(String ax) {
            a.token = a.type.addTypeArgument(ax).setWildcard(a.wildcard).asToken();
         }

         public void visitTypeArgument() {
            a.token.addTypeArgument('*');
         }

         public SignatureVisitor visitTypeArgument(char ax) {
            return SignatureParser.this.new TypeArgElement(a, ax);
         }

         public void visitEnd() {
         }
      }

      class FormalParamElement extends ClassSignature.SignatureParser.TokenElement {
         private final ClassSignature.TokenHandle handle;

         FormalParamElement(String axx) {
            super();
            a.handle = ClassSignature.this.getType(axx);
            a.token = a.handle.asToken();
         }
      }

      abstract class TokenElement extends ClassSignature.SignatureParser.SignatureElement {
         protected ClassSignature.Token token;
         private boolean array;

         TokenElement() {
            super();
         }

         public ClassSignature.Token getToken() {
            if (a.token == null) {
               a.token = new ClassSignature.Token();
            }

            return a.token;
         }

         protected void setArray() {
            a.array = true;
         }

         private boolean getArray() {
            boolean ax = a.array;
            a.array = false;
            return ax;
         }

         public void visitClassType(String ax) {
            a.getToken().setType(ax);
         }

         public SignatureVisitor visitClassBound() {
            a.getToken();
            return SignatureParser.this.new BoundElement(a, true);
         }

         public SignatureVisitor visitInterfaceBound() {
            a.getToken();
            return SignatureParser.this.new BoundElement(a, false);
         }

         public void visitInnerClassType(String ax) {
            a.token.addInnerClass(ax);
         }

         public SignatureVisitor visitArrayType() {
            a.setArray();
            return a;
         }

         public SignatureVisitor visitTypeArgument(char ax) {
            return SignatureParser.this.new TypeArgElement(a, ax);
         }

         ClassSignature.Token addTypeArgument() {
            return a.token.addTypeArgument('*').asToken();
         }

         ClassSignature.IToken addTypeArgument(char ax) {
            return a.token.addTypeArgument(ax).setArray(a.getArray());
         }

         ClassSignature.IToken addTypeArgument(String ax) {
            return a.token.addTypeArgument(ax).setArray(a.getArray());
         }

         ClassSignature.IToken addTypeArgument(ClassSignature.Token ax) {
            return a.token.addTypeArgument(ax).setArray(a.getArray());
         }

         ClassSignature.IToken addTypeArgument(ClassSignature.TokenHandle ax) {
            return a.token.addTypeArgument(ax).setArray(a.getArray());
         }
      }

      abstract class SignatureElement extends SignatureVisitor {
         public SignatureElement() {
            super(327680);
         }
      }
   }

   class TokenHandle implements ClassSignature.IToken {
      final ClassSignature.Token token;
      boolean array;
      char wildcard;

      TokenHandle() {
         this(new ClassSignature.Token());
      }

      TokenHandle(ClassSignature.Token axx) {
         a.token = axx;
      }

      public ClassSignature.IToken setArray(boolean ax) {
         a.array |= ax;
         return a;
      }

      public ClassSignature.IToken setWildcard(char ax) {
         if ("+-".indexOf(ax) > -1) {
            a.wildcard = ax;
         }

         return a;
      }

      public String asBound() {
         return a.token.asBound();
      }

      public String asType() {
         StringBuilder ax = new StringBuilder();
         if (a.wildcard > 0) {
            ax.append(a.wildcard);
         }

         if (a.array) {
            ax.append('[');
         }

         return ax.append(ClassSignature.this.getTypeVar(a)).toString();
      }

      public ClassSignature.Token asToken() {
         return a.token;
      }

      public String toString() {
         return a.token.toString();
      }

      public ClassSignature.TokenHandle clone() {
         return ClassSignature.this.new TokenHandle(a.token);
      }
   }

   static class Token implements ClassSignature.IToken {
      static final String SYMBOLS = "+-*";
      private final boolean inner;
      private boolean array;
      private char symbol;
      private String type;
      private List<ClassSignature.Token> classBound;
      private List<ClassSignature.Token> ifaceBound;
      private List<ClassSignature.IToken> signature;
      private List<ClassSignature.IToken> suffix;
      private ClassSignature.Token tail;

      Token() {
         this(false);
      }

      Token(String a) {
         this(a, false);
      }

      Token(char a) {
         this();
         a.symbol = a;
      }

      Token(boolean a) {
         this((String)null, a);
      }

      Token(String a, boolean a) {
         a.symbol = 0;
         a.inner = a;
         a.type = a;
      }

      ClassSignature.Token setSymbol(char a) {
         if (a.symbol == 0 && "+-*".indexOf(a) > -1) {
            a.symbol = a;
         }

         return a;
      }

      ClassSignature.Token setType(String a) {
         if (a.type == null) {
            a.type = a;
         }

         return a;
      }

      boolean hasClassBound() {
         return a.classBound != null;
      }

      boolean hasInterfaceBound() {
         return a.ifaceBound != null;
      }

      public ClassSignature.IToken setArray(boolean a) {
         a.array |= a;
         return a;
      }

      public ClassSignature.IToken setWildcard(char a) {
         return "+-".indexOf(a) == -1 ? a : a.setSymbol(a);
      }

      private List<ClassSignature.Token> getClassBound() {
         if (a.classBound == null) {
            a.classBound = new ArrayList();
         }

         return a.classBound;
      }

      private List<ClassSignature.Token> getIfaceBound() {
         if (a.ifaceBound == null) {
            a.ifaceBound = new ArrayList();
         }

         return a.ifaceBound;
      }

      private List<ClassSignature.IToken> getSignature() {
         if (a.signature == null) {
            a.signature = new ArrayList();
         }

         return a.signature;
      }

      private List<ClassSignature.IToken> getSuffix() {
         if (a.suffix == null) {
            a.suffix = new ArrayList();
         }

         return a.suffix;
      }

      ClassSignature.IToken addTypeArgument(char a) {
         if (a.tail != null) {
            return a.tail.addTypeArgument(a);
         } else {
            ClassSignature.Token a = new ClassSignature.Token(a);
            a.getSignature().add(a);
            return a;
         }
      }

      ClassSignature.IToken addTypeArgument(String a) {
         if (a.tail != null) {
            return a.tail.addTypeArgument(a);
         } else {
            ClassSignature.Token a = new ClassSignature.Token(a);
            a.getSignature().add(a);
            return a;
         }
      }

      ClassSignature.IToken addTypeArgument(ClassSignature.Token a) {
         if (a.tail != null) {
            return a.tail.addTypeArgument(a);
         } else {
            a.getSignature().add(a);
            return a;
         }
      }

      ClassSignature.IToken addTypeArgument(ClassSignature.TokenHandle a) {
         if (a.tail != null) {
            return a.tail.addTypeArgument(a);
         } else {
            ClassSignature.TokenHandle a = a.clone();
            a.getSignature().add(a);
            return a;
         }
      }

      ClassSignature.Token addBound(String a, boolean a) {
         return a ? a.addClassBound(a) : a.addInterfaceBound(a);
      }

      ClassSignature.Token addClassBound(String a) {
         ClassSignature.Token a = new ClassSignature.Token(a);
         a.getClassBound().add(a);
         return a;
      }

      ClassSignature.Token addInterfaceBound(String a) {
         ClassSignature.Token a = new ClassSignature.Token(a);
         a.getIfaceBound().add(a);
         return a;
      }

      ClassSignature.Token addInnerClass(String a) {
         a.tail = new ClassSignature.Token(a, true);
         a.getSuffix().add(a.tail);
         return a.tail;
      }

      public String toString() {
         return a.asType();
      }

      public String asBound() {
         StringBuilder a = new StringBuilder();
         if (a.type != null) {
            a.append(a.type);
         }

         Iterator var2;
         ClassSignature.Token a;
         if (a.classBound != null) {
            var2 = a.classBound.iterator();

            while(var2.hasNext()) {
               a = (ClassSignature.Token)var2.next();
               a.append(a.asType());
            }
         }

         if (a.ifaceBound != null) {
            var2 = a.ifaceBound.iterator();

            while(var2.hasNext()) {
               a = (ClassSignature.Token)var2.next();
               a.append(':').append(a.asType());
            }
         }

         return a.toString();
      }

      public String asType() {
         return a.asType(false);
      }

      public String asType(boolean a) {
         StringBuilder a = new StringBuilder();
         if (a.array) {
            a.append('[');
         }

         if (a.symbol != 0) {
            a.append(a.symbol);
         }

         if (a.type == null) {
            return a.toString();
         } else {
            if (!a.inner) {
               a.append('L');
            }

            a.append(a.type);
            if (!a) {
               Iterator var3;
               ClassSignature.IToken a;
               if (a.signature != null) {
                  a.append('<');
                  var3 = a.signature.iterator();

                  while(var3.hasNext()) {
                     a = (ClassSignature.IToken)var3.next();
                     a.append(a.asType());
                  }

                  a.append('>');
               }

               if (a.suffix != null) {
                  var3 = a.suffix.iterator();

                  while(var3.hasNext()) {
                     a = (ClassSignature.IToken)var3.next();
                     a.append('.').append(a.asType());
                  }
               }
            }

            if (!a.inner) {
               a.append(';');
            }

            return a.toString();
         }
      }

      boolean isRaw() {
         return a.signature == null;
      }

      String getClassType() {
         return a.type != null ? a.type : "java/lang/Object";
      }

      public ClassSignature.Token asToken() {
         return a;
      }
   }

   interface IToken {
      String WILDCARDS = "+-";

      String asType();

      String asBound();

      ClassSignature.Token asToken();

      ClassSignature.IToken setArray(boolean var1);

      ClassSignature.IToken setWildcard(char var1);
   }

   static class TypeVar implements Comparable<ClassSignature.TypeVar> {
      private final String originalName;
      private String currentName;

      TypeVar(String a) {
         a.currentName = a.originalName = a;
      }

      public int compareTo(ClassSignature.TypeVar a) {
         return a.currentName.compareTo(a.currentName);
      }

      public String toString() {
         return a.currentName;
      }

      String getOriginalName() {
         return a.originalName;
      }

      void rename(String a) {
         a.currentName = a;
      }

      public boolean matches(String a) {
         return a.originalName.equals(a);
      }

      public boolean equals(Object a) {
         return a.currentName.equals(a);
      }

      public int hashCode() {
         return a.currentName.hashCode();
      }
   }

   static class Lazy extends ClassSignature {
      private final String sig;
      private ClassSignature generated;

      Lazy(String a) {
         a.sig = a;
      }

      public ClassSignature wake() {
         if (a.generated == null) {
            a.generated = ClassSignature.of(a.sig);
         }

         return a.generated;
      }
   }
}

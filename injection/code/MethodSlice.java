package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.Strings;
import java.util.Deque;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public final class MethodSlice {
   private static final Logger logger = LogManager.getLogger("mixin");
   private final ISliceContext owner;
   private final String id;
   private final InjectionPoint from;
   private final InjectionPoint to;
   private final String name;

   private MethodSlice(ISliceContext a, String a, InjectionPoint a, InjectionPoint a) {
      if (a == null && a == null) {
         throw new InvalidSliceException(a, String.format("%s is redundant. No 'from' or 'to' value specified", a));
      } else {
         a.owner = a;
         a.id = Strings.nullToEmpty(a);
         a.from = a;
         a.to = a;
         a.name = getSliceName(a);
      }
   }

   public String getId() {
      return a.id;
   }

   public ReadOnlyInsnList getSlice(MethodNode a) {
      int a = a.instructions.size() - 1;
      int a = a.find(a, a.from, 0, 0, a.name + "(from)");
      int a = a.find(a, a.to, a, a, a.name + "(to)");
      if (a > a) {
         throw new InvalidSliceException(a.owner, String.format("%s is negative size. Range(%d -> %d)", a.describe(), a, a));
      } else if (a >= 0 && a >= 0 && a <= a && a <= a) {
         return (ReadOnlyInsnList)(a == 0 && a == a ? new ReadOnlyInsnList(a.instructions) : new MethodSlice.InsnListSlice(a.instructions, a, a));
      } else {
         throw new InjectionError("Unexpected critical error in " + a + ": out of bounds start=" + a + " end=" + a + " lim=" + a);
      }
   }

   private int find(MethodNode a, InjectionPoint a, int a, int a, String a) {
      if (a == null) {
         return a;
      } else {
         Deque<AbstractInsnNode> a = new LinkedList();
         ReadOnlyInsnList a = new ReadOnlyInsnList(a.instructions);
         boolean a = a.find(a.desc, a, a);
         InjectionPoint.Selector a = a.getSelector();
         if (a.size() != 1 && a == InjectionPoint.Selector.ONE) {
            throw new InvalidSliceException(a.owner, String.format("%s requires 1 result but found %d", a.describe(a), a.size()));
         } else if (!a) {
            if (a.owner.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
               logger.warn("{} did not match any instructions", new Object[]{a.describe(a)});
            }

            return a;
         } else {
            return a.instructions.indexOf(a == InjectionPoint.Selector.FIRST ? (AbstractInsnNode)a.getFirst() : (AbstractInsnNode)a.getLast());
         }
      }
   }

   public String toString() {
      return a.describe();
   }

   private String describe() {
      return a.describe(a.name);
   }

   private String describe(String a) {
      return describeSlice(a, a.owner);
   }

   private static String describeSlice(String a, ISliceContext a) {
      String a = Bytecode.getSimpleName(a.getAnnotation());
      MethodNode a = a.getMethod();
      return String.format("%s->%s(%s)::%s%s", a.getContext(), a, a, a.name, a.desc);
   }

   private static String getSliceName(String a) {
      return String.format("@Slice[%s]", Strings.nullToEmpty(a));
   }

   public static MethodSlice parse(ISliceContext a, Slice a) {
      String a = a.id();
      At a = a.from();
      At a = a.to();
      InjectionPoint a = a != null ? InjectionPoint.parse(a, (At)a) : null;
      InjectionPoint a = a != null ? InjectionPoint.parse(a, (At)a) : null;
      return new MethodSlice(a, a, a, a);
   }

   public static MethodSlice parse(ISliceContext a, AnnotationNode a) {
      String a = (String)Annotations.getValue(a, "id");
      AnnotationNode a = (AnnotationNode)Annotations.getValue(a, "from");
      AnnotationNode a = (AnnotationNode)Annotations.getValue(a, "to");
      InjectionPoint a = a != null ? InjectionPoint.parse(a, (AnnotationNode)a) : null;
      InjectionPoint a = a != null ? InjectionPoint.parse(a, (AnnotationNode)a) : null;
      return new MethodSlice(a, a, a, a);
   }

   static final class InsnListSlice extends ReadOnlyInsnList {
      private final int start;
      private final int end;

      protected InsnListSlice(InsnList a, int a, int a) {
         super(a);
         a.start = a;
         a.end = a;
      }

      public ListIterator<AbstractInsnNode> iterator() {
         return a.iterator(0);
      }

      public ListIterator<AbstractInsnNode> iterator(int a) {
         return new MethodSlice.InsnListSlice.SliceIterator(super.iterator(a.start + a), a.start, a.end, a.start + a);
      }

      public AbstractInsnNode[] toArray() {
         AbstractInsnNode[] a = super.toArray();
         AbstractInsnNode[] a = new AbstractInsnNode[a.size()];
         System.arraycopy(a, a.start, a, 0, a.length);
         return a;
      }

      public int size() {
         return a.end - a.start + 1;
      }

      public AbstractInsnNode getFirst() {
         return super.get(a.start);
      }

      public AbstractInsnNode getLast() {
         return super.get(a.end);
      }

      public AbstractInsnNode get(int a) {
         return super.get(a.start + a);
      }

      public boolean contains(AbstractInsnNode a) {
         AbstractInsnNode[] var2 = a.toArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            AbstractInsnNode a = var2[var4];
            if (a == a) {
               return true;
            }
         }

         return false;
      }

      public int indexOf(AbstractInsnNode a) {
         int a = super.indexOf(a);
         return a >= a.start && a <= a.end ? a - a.start : -1;
      }

      public int realIndexOf(AbstractInsnNode a) {
         return super.indexOf(a);
      }

      static class SliceIterator implements ListIterator<AbstractInsnNode> {
         private final ListIterator<AbstractInsnNode> iter;
         private int start;
         private int end;
         private int index;

         public SliceIterator(ListIterator<AbstractInsnNode> a, int a, int a, int a) {
            a.iter = a;
            a.start = a;
            a.end = a;
            a.index = a;
         }

         public boolean hasNext() {
            return a.index <= a.end && a.iter.hasNext();
         }

         public AbstractInsnNode next() {
            if (a.index > a.end) {
               throw new NoSuchElementException();
            } else {
               ++a.index;
               return (AbstractInsnNode)a.iter.next();
            }
         }

         public boolean hasPrevious() {
            return a.index > a.start;
         }

         public AbstractInsnNode previous() {
            if (a.index <= a.start) {
               throw new NoSuchElementException();
            } else {
               --a.index;
               return (AbstractInsnNode)a.iter.previous();
            }
         }

         public int nextIndex() {
            return a.index - a.start;
         }

         public int previousIndex() {
            return a.index - a.start - 1;
         }

         public void remove() {
            throw new UnsupportedOperationException("Cannot remove insn from slice");
         }

         public void set(AbstractInsnNode a1) {
            throw new UnsupportedOperationException("Cannot set insn using slice");
         }

         public void add(AbstractInsnNode a1) {
            throw new UnsupportedOperationException("Cannot add insn using slice");
         }
      }
   }
}

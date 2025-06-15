package org.spongepowered.asm.util.perf;

import com.google.common.base.Joiner;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import org.spongepowered.asm.util.PrettyPrinter;

public final class Profiler {
   public static final int ROOT = 1;
   public static final int FINE = 2;
   private final Map<String, Profiler.Section> sections = new TreeMap();
   private final List<String> phases = new ArrayList();
   private final Deque<Profiler.Section> stack = new LinkedList();
   private boolean active;

   public Profiler() {
      a.phases.add("Initial");
   }

   public void setActive(boolean a) {
      if (!a.active && a || !a) {
         a.reset();
      }

      a.active = a;
   }

   public void reset() {
      Iterator var1 = a.sections.values().iterator();

      while(var1.hasNext()) {
         Profiler.Section a = (Profiler.Section)var1.next();
         a.invalidate();
      }

      a.sections.clear();
      a.phases.clear();
      a.phases.add("Initial");
      a.stack.clear();
   }

   public Profiler.Section get(String a) {
      Profiler.Section a = (Profiler.Section)a.sections.get(a);
      if (a == null) {
         a = a.active ? new Profiler.LiveSection(a, a.phases.size() - 1) : new Profiler.Section(a);
         a.sections.put(a, a);
      }

      return (Profiler.Section)a;
   }

   private Profiler.Section getSubSection(String a, String a, Profiler.Section a) {
      Profiler.Section a = (Profiler.Section)a.sections.get(a);
      if (a == null) {
         a = new Profiler.SubSection(a, a.phases.size() - 1, a, a);
         a.sections.put(a, a);
      }

      return (Profiler.Section)a;
   }

   boolean isHead(Profiler.Section a) {
      return a.stack.peek() == a;
   }

   public Profiler.Section begin(String... a) {
      return a.begin(0, (String[])a);
   }

   public Profiler.Section begin(int a, String... a) {
      return a.begin(a, Joiner.on('.').join(a));
   }

   public Profiler.Section begin(String a) {
      return a.begin(0, (String)a);
   }

   public Profiler.Section begin(int a, String a) {
      boolean a = (a & 1) != 0;
      boolean a = (a & 2) != 0;
      String a = a;
      Profiler.Section a = (Profiler.Section)a.stack.peek();
      if (a != null) {
         a = a.getName() + (a ? " -> " : ".") + a;
         if (a.isRoot() && !a) {
            int a = a.getName().lastIndexOf(" -> ");
            a = (a > -1 ? a.getName().substring(a + 4) : a.getName()) + "." + a;
            a = true;
         }
      }

      Profiler.Section a = a.get(a ? a : a);
      if (a && a != null && a.active) {
         a = a.getSubSection(a, a.getName(), a);
      }

      a.setFine(a).setRoot(a);
      a.stack.push(a);
      return a.start();
   }

   void end(Profiler.Section a) {
      try {
         Profiler.Section a = (Profiler.Section)a.stack.pop();

         for(Profiler.Section a = a; a != a; a = (Profiler.Section)a.stack.pop()) {
            if (a == null && a.active) {
               if (a == null) {
                  throw new IllegalStateException("Attempted to pop " + a + " but the stack is empty");
               }

               throw new IllegalStateException("Attempted to pop " + a + " which was not in the stack, head was " + a);
            }
         }
      } catch (NoSuchElementException var4) {
         if (a.active) {
            throw new IllegalStateException("Attempted to pop " + a + " but the stack is empty");
         }
      }

   }

   public void mark(String a) {
      long a = 0L;

      Iterator var4;
      Profiler.Section a;
      for(var4 = a.sections.values().iterator(); var4.hasNext(); a += a.getTime()) {
         a = (Profiler.Section)var4.next();
      }

      if (a == 0L) {
         int a = a.phases.size();
         a.phases.set(a - 1, a);
      } else {
         a.phases.add(a);
         var4 = a.sections.values().iterator();

         while(var4.hasNext()) {
            a = (Profiler.Section)var4.next();
            a.mark();
         }

      }
   }

   public Collection<Profiler.Section> getSections() {
      return Collections.unmodifiableCollection(a.sections.values());
   }

   public PrettyPrinter printer(boolean a, boolean a) {
      PrettyPrinter a = new PrettyPrinter();
      int a = a.phases.size() + 4;
      int[] a = new int[]{0, 1, 2, a - 2, a - 1};
      Object[] a = new Object[a * 2];
      int a = 0;

      for(int a = 0; a < a; a = a * 2) {
         a[a + 1] = PrettyPrinter.Alignment.RIGHT;
         if (a == a[0]) {
            a[a] = (a ? "" : "  ") + "Section";
            a[a + 1] = PrettyPrinter.Alignment.LEFT;
         } else if (a == a[1]) {
            a[a] = "    TOTAL";
         } else if (a == a[3]) {
            a[a] = "    Count";
         } else if (a == a[4]) {
            a[a] = "Avg. ";
         } else if (a - a[2] < a.phases.size()) {
            a[a] = a.phases.get(a - a[2]);
         } else {
            a[a] = "";
         }

         ++a;
      }

      a.table(a).th().hr().add();
      Iterator var12 = a.sections.values().iterator();

      label78:
      while(true) {
         Profiler.Section a;
         do {
            do {
               do {
                  if (!var12.hasNext()) {
                     return a.add();
                  }

                  a = (Profiler.Section)var12.next();
               } while(a.isFine() && !a);
            } while(a && a.getDelegate() != a);

            a.printSectionRow(a, a, a, a, a);
         } while(!a);

         Iterator var9 = a.sections.values().iterator();

         while(true) {
            Profiler.Section a;
            Profiler.Section a;
            do {
               if (!var9.hasNext()) {
                  continue label78;
               }

               a = (Profiler.Section)var9.next();
               a = a.getDelegate();
            } while(a.isFine() && !a);

            if (a == a && a != a) {
               a.printSectionRow(a, a, a, a, a);
            }
         }
      }
   }

   private void printSectionRow(PrettyPrinter a, int a, int[] a, Profiler.Section a, boolean a) {
      boolean a = a.getDelegate() != a;
      Object[] a = new Object[a];
      int a = 1;
      if (a) {
         a[0] = a ? "  > " + a.getBaseName() : a.getName();
      } else {
         a[0] = (a ? "+ " : "  ") + a.getName();
      }

      long[] a = a.getTimes();
      long[] var10 = a;
      int var11 = a.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         long a = var10[var12];
         if (a == a[1]) {
            a[a++] = a.getTotalTime() + " ms";
         }

         if (a >= a[2] && a < a.length) {
            a[a++] = a + " ms";
         }
      }

      a[a[3]] = a.getTotalCount();
      a[a[4]] = (new DecimalFormat("   ###0.000 ms")).format(a.getTotalAverageTime());

      for(int a = 0; a < a.length; ++a) {
         if (a[a] == null) {
            a[a] = "-";
         }
      }

      a.tr(a);
   }

   class SubSection extends Profiler.LiveSection {
      private final String baseName;
      private final Profiler.Section root;

      SubSection(String axx, int axxx, String axxxx, Profiler.Section axxxxx) {
         super(axx, axxx);
         a.baseName = axxxx;
         a.root = axxxxx;
      }

      Profiler.Section invalidate() {
         a.root.invalidate();
         return super.invalidate();
      }

      public String getBaseName() {
         return a.baseName;
      }

      public void setInfo(String ax) {
         a.root.setInfo(ax);
         super.setInfo(ax);
      }

      Profiler.Section getDelegate() {
         return a.root;
      }

      Profiler.Section start() {
         a.root.start();
         return super.start();
      }

      public Profiler.Section end() {
         a.root.stop();
         return super.end();
      }

      public Profiler.Section next(String ax) {
         super.stop();
         return a.root.next(ax);
      }
   }

   class LiveSection extends Profiler.Section {
      private int cursor = 0;
      private long[] times = new long[0];
      private long start = 0L;
      private long time;
      private long markedTime;
      private int count;
      private int markedCount;

      LiveSection(String axxx, int ax) {
         super(axxx);
         a.cursor = ax;
      }

      Profiler.Section start() {
         a.start = System.currentTimeMillis();
         return a;
      }

      protected Profiler.Section stop() {
         if (a.start > 0L) {
            a.time += System.currentTimeMillis() - a.start;
         }

         a.start = 0L;
         ++a.count;
         return a;
      }

      public Profiler.Section end() {
         a.stop();
         if (!a.invalidated) {
            Profiler.this.end(a);
         }

         return a;
      }

      void mark() {
         if (a.cursor >= a.times.length) {
            a.times = Arrays.copyOf(a.times, a.cursor + 4);
         }

         a.times[a.cursor] = a.time;
         a.markedTime += a.time;
         a.markedCount += a.count;
         a.time = 0L;
         a.count = 0;
         ++a.cursor;
      }

      public long getTime() {
         return a.time;
      }

      public long getTotalTime() {
         return a.time + a.markedTime;
      }

      public double getSeconds() {
         return (double)a.time * 0.001D;
      }

      public double getTotalSeconds() {
         return (double)(a.time + a.markedTime) * 0.001D;
      }

      public long[] getTimes() {
         long[] ax = new long[a.cursor + 1];
         System.arraycopy(a.times, 0, ax, 0, Math.min(a.times.length, a.cursor));
         ax[a.cursor] = a.time;
         return ax;
      }

      public int getCount() {
         return a.count;
      }

      public int getTotalCount() {
         return a.count + a.markedCount;
      }

      public double getAverageTime() {
         return a.count > 0 ? (double)a.time / (double)a.count : 0.0D;
      }

      public double getTotalAverageTime() {
         return a.count > 0 ? (double)(a.time + a.markedTime) / (double)(a.count + a.markedCount) : 0.0D;
      }
   }

   public class Section {
      static final String SEPARATOR_ROOT = " -> ";
      static final String SEPARATOR_CHILD = ".";
      private final String name;
      private boolean root;
      private boolean fine;
      protected boolean invalidated;
      private String info;

      Section(String axx) {
         a.name = axx;
         a.info = axx;
      }

      Profiler.Section getDelegate() {
         return a;
      }

      Profiler.Section invalidate() {
         a.invalidated = true;
         return a;
      }

      Profiler.Section setRoot(boolean ax) {
         a.root = ax;
         return a;
      }

      public boolean isRoot() {
         return a.root;
      }

      Profiler.Section setFine(boolean ax) {
         a.fine = ax;
         return a;
      }

      public boolean isFine() {
         return a.fine;
      }

      public String getName() {
         return a.name;
      }

      public String getBaseName() {
         return a.name;
      }

      public void setInfo(String ax) {
         a.info = ax;
      }

      public String getInfo() {
         return a.info;
      }

      Profiler.Section start() {
         return a;
      }

      protected Profiler.Section stop() {
         return a;
      }

      public Profiler.Section end() {
         if (!a.invalidated) {
            Profiler.this.end(a);
         }

         return a;
      }

      public Profiler.Section next(String ax) {
         a.end();
         return Profiler.this.begin(ax);
      }

      void mark() {
      }

      public long getTime() {
         return 0L;
      }

      public long getTotalTime() {
         return 0L;
      }

      public double getSeconds() {
         return 0.0D;
      }

      public double getTotalSeconds() {
         return 0.0D;
      }

      public long[] getTimes() {
         return new long[1];
      }

      public int getCount() {
         return 0;
      }

      public int getTotalCount() {
         return 0;
      }

      public double getAverageTime() {
         return 0.0D;
      }

      public double getTotalAverageTime() {
         return 0.0D;
      }

      public final String toString() {
         return a.name;
      }
   }
}

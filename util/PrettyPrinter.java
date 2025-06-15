package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrettyPrinter {
   private final PrettyPrinter.HorizontalRule horizontalRule;
   private final List<Object> lines;
   private PrettyPrinter.Table table;
   private boolean recalcWidth;
   protected int width;
   protected int wrapWidth;
   protected int kvKeyWidth;
   protected String kvFormat;

   public PrettyPrinter() {
      this(100);
   }

   public PrettyPrinter(int a) {
      a.horizontalRule = new PrettyPrinter.HorizontalRule(new char[]{'*'});
      a.lines = new ArrayList();
      a.recalcWidth = false;
      a.width = 100;
      a.wrapWidth = 80;
      a.kvKeyWidth = 10;
      a.kvFormat = makeKvFormat(a.kvKeyWidth);
      a.width = a;
   }

   public PrettyPrinter wrapTo(int a) {
      a.wrapWidth = a;
      return a;
   }

   public int wrapTo() {
      return a.wrapWidth;
   }

   public PrettyPrinter table() {
      a.table = new PrettyPrinter.Table();
      return a;
   }

   public PrettyPrinter table(String... a) {
      a.table = new PrettyPrinter.Table();
      String[] var2 = a;
      int var3 = a.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String a = var2[var4];
         a.table.addColumn(a);
      }

      return a;
   }

   public PrettyPrinter table(Object... a) {
      a.table = new PrettyPrinter.Table();
      PrettyPrinter.Column a = null;
      Object[] var3 = a;
      int var4 = a.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object a = var3[var5];
         if (a instanceof String) {
            a = a.table.addColumn((String)a);
         } else if (a instanceof Integer && a != null) {
            int a = (Integer)a;
            if (a > 0) {
               a.setWidth(a);
            } else if (a < 0) {
               a.setMaxWidth(-a);
            }
         } else if (a instanceof PrettyPrinter.Alignment && a != null) {
            a.setAlignment((PrettyPrinter.Alignment)a);
         } else if (a != null) {
            a = a.table.addColumn(a.toString());
         }
      }

      return a;
   }

   public PrettyPrinter spacing(int a) {
      if (a.table == null) {
         a.table = new PrettyPrinter.Table();
      }

      a.table.setColSpacing(a);
      return a;
   }

   public PrettyPrinter th() {
      return a.th(false);
   }

   private PrettyPrinter th(boolean a) {
      if (a.table == null) {
         a.table = new PrettyPrinter.Table();
      }

      if (!a || a.table.addHeader) {
         a.table.headerAdded();
         a.addLine(a.table);
      }

      return a;
   }

   public PrettyPrinter tr(Object... a) {
      a.th(true);
      a.addLine(a.table.addRow(a));
      a.recalcWidth = true;
      return a;
   }

   public PrettyPrinter add() {
      a.addLine("");
      return a;
   }

   public PrettyPrinter add(String a) {
      a.addLine(a);
      a.width = Math.max(a.width, a.length());
      return a;
   }

   public PrettyPrinter add(String a, Object... a) {
      String a = String.format(a, a);
      a.addLine(a);
      a.width = Math.max(a.width, a.length());
      return a;
   }

   public PrettyPrinter add(Object[] a) {
      return a.add(a, "%s");
   }

   public PrettyPrinter add(Object[] a, String a) {
      Object[] var3 = a;
      int var4 = a.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object a = var3[var5];
         a.add(a, a);
      }

      return a;
   }

   public PrettyPrinter addIndexed(Object[] a) {
      int a = String.valueOf(a.length - 1).length();
      String a = "[%" + a + "d] %s";

      for(int a = 0; a < a.length; ++a) {
         a.add(a, a, a[a]);
      }

      return a;
   }

   public PrettyPrinter addWithIndices(Collection<?> a) {
      return a.addIndexed(a.toArray());
   }

   public PrettyPrinter add(PrettyPrinter.IPrettyPrintable a) {
      if (a != null) {
         a.print(a);
      }

      return a;
   }

   public PrettyPrinter add(Throwable a) {
      return a.add((Throwable)a, 4);
   }

   public PrettyPrinter add(Throwable a, int a) {
      while(a != null) {
         a.add("%s: %s", a.getClass().getName(), a.getMessage());
         a.add(a.getStackTrace(), a);
         a = a.getCause();
      }

      return a;
   }

   public PrettyPrinter add(StackTraceElement[] a, int a) {
      String a = Strings.repeat(" ", a);
      StackTraceElement[] var4 = a;
      int var5 = a.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         StackTraceElement a = var4[var6];
         a.add("%s%s", a, a);
      }

      return a;
   }

   public PrettyPrinter add(Object a) {
      return a.add((Object)a, 0);
   }

   public PrettyPrinter add(Object a, int a) {
      String a = Strings.repeat(" ", a);
      return a.append(a, a, a);
   }

   private PrettyPrinter append(Object a, int a, String a) {
      if (a instanceof String) {
         return a.add("%s%s", a, a);
      } else if (!(a instanceof Iterable)) {
         if (a instanceof Map) {
            a.kvWidth(a);
            return a.add((Map)a);
         } else if (a instanceof PrettyPrinter.IPrettyPrintable) {
            return a.add((PrettyPrinter.IPrettyPrintable)a);
         } else if (a instanceof Throwable) {
            return a.add((Throwable)a, a);
         } else {
            return a.getClass().isArray() ? a.add((Object[])((Object[])a), a + "%s") : a.add("%s%s", a, a);
         }
      } else {
         Iterator var4 = ((Iterable)a).iterator();

         while(var4.hasNext()) {
            Object a = var4.next();
            a.append(a, a, a);
         }

         return a;
      }
   }

   public PrettyPrinter addWrapped(String a, Object... a) {
      return a.addWrapped(a.wrapWidth, a, a);
   }

   public PrettyPrinter addWrapped(int a, String a, Object... a) {
      String a = "";
      String a = String.format(a, a).replace("\t", "    ");
      Matcher a = Pattern.compile("^(\\s+)(.*)$").matcher(a);
      if (a.matches()) {
         a = a.group(1);
      }

      try {
         Iterator var7 = a.getWrapped(a, a, a).iterator();

         while(var7.hasNext()) {
            String a = (String)var7.next();
            a.addLine(a);
         }
      } catch (Exception var9) {
         a.add(a);
      }

      return a;
   }

   private List<String> getWrapped(int a, String a, String a) {
      ArrayList a;
      int a;
      for(a = new ArrayList(); a.length() > a; a = a + a.substring(a + 1)) {
         a = a.lastIndexOf(32, a);
         if (a < 10) {
            a = a;
         }

         String a = a.substring(0, a);
         a.add(a);
      }

      if (a.length() > 0) {
         a.add(a);
      }

      return a;
   }

   public PrettyPrinter kv(String a, String a, Object... a) {
      return a.kv(a, String.format(a, a));
   }

   public PrettyPrinter kv(String a, Object a) {
      a.addLine(new PrettyPrinter.KeyValue(a, a));
      return a.kvWidth(a.length());
   }

   public PrettyPrinter kvWidth(int a) {
      if (a > a.kvKeyWidth) {
         a.kvKeyWidth = a;
         a.kvFormat = makeKvFormat(a);
      }

      a.recalcWidth = true;
      return a;
   }

   public PrettyPrinter add(Map<?, ?> a) {
      Iterator var2 = a.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<?, ?> a = (Entry)var2.next();
         String a = a.getKey() == null ? "null" : a.getKey().toString();
         a.kv(a, a.getValue());
      }

      return a;
   }

   public PrettyPrinter hr() {
      return a.hr('*');
   }

   public PrettyPrinter hr(char a) {
      a.addLine(new PrettyPrinter.HorizontalRule(new char[]{a}));
      return a;
   }

   public PrettyPrinter centre() {
      if (!a.lines.isEmpty()) {
         Object a = a.lines.get(a.lines.size() - 1);
         if (a instanceof String) {
            a.addLine(new PrettyPrinter.CentredText(a.lines.remove(a.lines.size() - 1)));
         }
      }

      return a;
   }

   private void addLine(Object a) {
      if (a != null) {
         a.lines.add(a);
         a.recalcWidth |= a instanceof PrettyPrinter.IVariableWidthEntry;
      }
   }

   public PrettyPrinter trace() {
      return a.trace(getDefaultLoggerName());
   }

   public PrettyPrinter trace(Level a) {
      return a.trace(getDefaultLoggerName(), a);
   }

   public PrettyPrinter trace(String a) {
      return a.trace(System.err, LogManager.getLogger(a));
   }

   public PrettyPrinter trace(String a, Level a) {
      return a.trace(System.err, LogManager.getLogger(a), a);
   }

   public PrettyPrinter trace(Logger a) {
      return a.trace(System.err, a);
   }

   public PrettyPrinter trace(Logger a, Level a) {
      return a.trace(System.err, a, a);
   }

   public PrettyPrinter trace(PrintStream a) {
      return a.trace(a, getDefaultLoggerName());
   }

   public PrettyPrinter trace(PrintStream a, Level a) {
      return a.trace(a, getDefaultLoggerName(), a);
   }

   public PrettyPrinter trace(PrintStream a, String a) {
      return a.trace(a, LogManager.getLogger(a));
   }

   public PrettyPrinter trace(PrintStream a, String a, Level a) {
      return a.trace(a, LogManager.getLogger(a), a);
   }

   public PrettyPrinter trace(PrintStream a, Logger a) {
      return a.trace(a, a, Level.DEBUG);
   }

   public PrettyPrinter trace(PrintStream a, Logger a, Level a) {
      a.log(a, a);
      a.print(a);
      return a;
   }

   public PrettyPrinter print() {
      return a.print(System.err);
   }

   public PrettyPrinter print(PrintStream a) {
      a.updateWidth();
      a.printSpecial(a, a.horizontalRule);
      Iterator var2 = a.lines.iterator();

      while(var2.hasNext()) {
         Object a = var2.next();
         if (a instanceof PrettyPrinter.ISpecialEntry) {
            a.printSpecial(a, (PrettyPrinter.ISpecialEntry)a);
         } else {
            a.printString(a, a.toString());
         }
      }

      a.printSpecial(a, a.horizontalRule);
      return a;
   }

   private void printSpecial(PrintStream a, PrettyPrinter.ISpecialEntry a) {
      a.printf("/*%s*/\n", a.toString());
   }

   private void printString(PrintStream a, String a) {
      if (a != null) {
         a.printf("/* %-" + a.width + "s */\n", a);
      }

   }

   public PrettyPrinter log(Logger a) {
      return a.log(a, Level.INFO);
   }

   public PrettyPrinter log(Logger a, Level a) {
      a.updateWidth();
      a.logSpecial(a, a, a.horizontalRule);
      Iterator var3 = a.lines.iterator();

      while(var3.hasNext()) {
         Object a = var3.next();
         if (a instanceof PrettyPrinter.ISpecialEntry) {
            a.logSpecial(a, a, (PrettyPrinter.ISpecialEntry)a);
         } else {
            a.logString(a, a, a.toString());
         }
      }

      a.logSpecial(a, a, a.horizontalRule);
      return a;
   }

   private void logSpecial(Logger a, Level a, PrettyPrinter.ISpecialEntry a) {
      a.log(a, "/*{}*/", new Object[]{a.toString()});
   }

   private void logString(Logger a, Level a, String a) {
      if (a != null) {
         a.log(a, String.format("/* %-" + a.width + "s */", a));
      }

   }

   private void updateWidth() {
      if (a.recalcWidth) {
         a.recalcWidth = false;
         Iterator var1 = a.lines.iterator();

         while(var1.hasNext()) {
            Object a = var1.next();
            if (a instanceof PrettyPrinter.IVariableWidthEntry) {
               a.width = Math.min(4096, Math.max(a.width, ((PrettyPrinter.IVariableWidthEntry)a).getWidth()));
            }
         }
      }

   }

   private static String makeKvFormat(int a) {
      return String.format("%%%ds : %%s", a);
   }

   private static String getDefaultLoggerName() {
      String a = (new Throwable()).getStackTrace()[2].getClassName();
      int a = a.lastIndexOf(46);
      return a == -1 ? a : a.substring(a + 1);
   }

   public static void dumpStack() {
      (new PrettyPrinter()).add((Throwable)(new Exception("Stack trace"))).print(System.err);
   }

   public static void print(Throwable a) {
      (new PrettyPrinter()).add(a).print(System.err);
   }

   static class Row implements PrettyPrinter.IVariableWidthEntry {
      final PrettyPrinter.Table table;
      final String[] args;

      public Row(PrettyPrinter.Table a, Object... a) {
         a.table = a.grow(a.length);
         a.args = new String[a.length];

         for(int a = 0; a < a.length; ++a) {
            a.args[a] = a[a].toString();
            ((PrettyPrinter.Column)a.table.columns.get(a)).setMinWidth(a.args[a].length());
         }

      }

      public String toString() {
         Object[] a = new Object[a.table.columns.size()];

         for(int a = 0; a < a.length; ++a) {
            PrettyPrinter.Column a = (PrettyPrinter.Column)a.table.columns.get(a);
            if (a >= a.args.length) {
               a[a] = "";
            } else {
               a[a] = a.args[a].length() > a.getMaxWidth() ? a.args[a].substring(0, a.getMaxWidth()) : a.args[a];
            }
         }

         return String.format(a.table.format, a);
      }

      public int getWidth() {
         return a.toString().length();
      }
   }

   static class Column {
      private final PrettyPrinter.Table table;
      private PrettyPrinter.Alignment align;
      private int minWidth;
      private int maxWidth;
      private int size;
      private String title;
      private String format;

      Column(PrettyPrinter.Table a) {
         a.align = PrettyPrinter.Alignment.LEFT;
         a.minWidth = 1;
         a.maxWidth = Integer.MAX_VALUE;
         a.size = 0;
         a.title = "";
         a.format = "%s";
         a.table = a;
      }

      Column(PrettyPrinter.Table a, String a) {
         this(a);
         a.title = a;
         a.minWidth = a.length();
         a.updateFormat();
      }

      Column(PrettyPrinter.Table a, PrettyPrinter.Alignment a, int a, String a) {
         this(a, a);
         a.align = a;
         a.size = a;
      }

      void setAlignment(PrettyPrinter.Alignment a) {
         a.align = a;
         a.updateFormat();
      }

      void setWidth(int a) {
         if (a > a.size) {
            a.size = a;
            a.updateFormat();
         }

      }

      void setMinWidth(int a) {
         if (a > a.minWidth) {
            a.minWidth = a;
            a.updateFormat();
         }

      }

      void setMaxWidth(int a) {
         a.size = Math.min(a.size, a.maxWidth);
         a.maxWidth = Math.max(1, a);
         a.updateFormat();
      }

      void setTitle(String a) {
         a.title = a;
         a.setWidth(a.length());
      }

      private void updateFormat() {
         int a = Math.min(a.maxWidth, a.size == 0 ? a.minWidth : a.size);
         a.format = "%" + (a.align == PrettyPrinter.Alignment.RIGHT ? "" : "-") + a + "s";
         a.table.updateFormat();
      }

      int getMaxWidth() {
         return a.maxWidth;
      }

      String getTitle() {
         return a.title;
      }

      String getFormat() {
         return a.format;
      }

      public String toString() {
         return a.title.length() > a.maxWidth ? a.title.substring(0, a.maxWidth) : a.title;
      }
   }

   static class Table implements PrettyPrinter.IVariableWidthEntry {
      final List<PrettyPrinter.Column> columns = new ArrayList();
      final List<PrettyPrinter.Row> rows = new ArrayList();
      String format = "%s";
      int colSpacing = 2;
      boolean addHeader = true;

      void headerAdded() {
         a.addHeader = false;
      }

      void setColSpacing(int a) {
         a.colSpacing = Math.max(0, a);
         a.updateFormat();
      }

      PrettyPrinter.Table grow(int a) {
         while(a.columns.size() < a) {
            a.columns.add(new PrettyPrinter.Column(a));
         }

         a.updateFormat();
         return a;
      }

      PrettyPrinter.Column add(PrettyPrinter.Column a) {
         a.columns.add(a);
         return a;
      }

      PrettyPrinter.Row add(PrettyPrinter.Row a) {
         a.rows.add(a);
         return a;
      }

      PrettyPrinter.Column addColumn(String a) {
         return a.add(new PrettyPrinter.Column(a, a));
      }

      PrettyPrinter.Column addColumn(PrettyPrinter.Alignment a, int a, String a) {
         return a.add(new PrettyPrinter.Column(a, a, a, a));
      }

      PrettyPrinter.Row addRow(Object... a) {
         return a.add(new PrettyPrinter.Row(a, a));
      }

      void updateFormat() {
         String a = Strings.repeat(" ", a.colSpacing);
         StringBuilder a = new StringBuilder();
         boolean a = false;
         Iterator var4 = a.columns.iterator();

         while(var4.hasNext()) {
            PrettyPrinter.Column a = (PrettyPrinter.Column)var4.next();
            if (a) {
               a.append(a);
            }

            a = true;
            a.append(a.getFormat());
         }

         a.format = a.toString();
      }

      String getFormat() {
         return a.format;
      }

      Object[] getTitles() {
         List<Object> a = new ArrayList();
         Iterator var2 = a.columns.iterator();

         while(var2.hasNext()) {
            PrettyPrinter.Column a = (PrettyPrinter.Column)var2.next();
            a.add(a.getTitle());
         }

         return a.toArray();
      }

      public String toString() {
         boolean a = false;
         String[] a = new String[a.columns.size()];

         for(int a = 0; a < a.columns.size(); ++a) {
            a[a] = ((PrettyPrinter.Column)a.columns.get(a)).toString();
            a |= !a[a].isEmpty();
         }

         return a ? String.format(a.format, (Object[])a) : null;
      }

      public int getWidth() {
         String a = a.toString();
         return a != null ? a.length() : 0;
      }
   }

   public static enum Alignment {
      LEFT,
      RIGHT;
   }

   class CentredText {
      private final Object centred;

      public CentredText(Object axx) {
         a.centred = axx;
      }

      public String toString() {
         String ax = a.centred.toString();
         return String.format("%" + ((PrettyPrinter.this.width - ax.length()) / 2 + ax.length()) + "s", ax);
      }
   }

   class HorizontalRule implements PrettyPrinter.ISpecialEntry {
      private final char[] hrChars;

      public HorizontalRule(char... axx) {
         a.hrChars = axx;
      }

      public String toString() {
         return Strings.repeat(new String(a.hrChars), PrettyPrinter.this.width + 2);
      }
   }

   class KeyValue implements PrettyPrinter.IVariableWidthEntry {
      private final String key;
      private final Object value;

      public KeyValue(String axxx, Object ax) {
         a.key = axxx;
         a.value = ax;
      }

      public String toString() {
         return String.format(PrettyPrinter.this.kvFormat, a.key, a.value);
      }

      public int getWidth() {
         return a.toString().length();
      }
   }

   interface ISpecialEntry {
   }

   interface IVariableWidthEntry {
      int getWidth();
   }

   public interface IPrettyPrintable {
      void print(PrettyPrinter var1);
   }
}

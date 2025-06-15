package org.spongepowered.tools.obfuscation.struct;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

public class Message {
   private Kind kind;
   private CharSequence msg;
   private final Element element;
   private final AnnotationMirror annotation;
   private final AnnotationValue value;

   public Message(Kind a, CharSequence a) {
      this(a, a, (Element)null, (AnnotationMirror)((AnnotationMirror)null), (AnnotationValue)null);
   }

   public Message(Kind a, CharSequence a, Element a) {
      this(a, a, a, (AnnotationMirror)((AnnotationMirror)null), (AnnotationValue)null);
   }

   public Message(Kind a, CharSequence a, Element a, AnnotationHandle a) {
      this(a, a, a, (AnnotationMirror)a.asMirror(), (AnnotationValue)null);
   }

   public Message(Kind a, CharSequence a, Element a, AnnotationMirror a) {
      this(a, a, a, (AnnotationMirror)a, (AnnotationValue)null);
   }

   public Message(Kind a, CharSequence a, Element a, AnnotationHandle a, AnnotationValue a) {
      this(a, a, a, a.asMirror(), a);
   }

   public Message(Kind a, CharSequence a, Element a, AnnotationMirror a, AnnotationValue a) {
      a.kind = a;
      a.msg = a;
      a.element = a;
      a.annotation = a;
      a.value = a;
   }

   public Message sendTo(Messager a) {
      if (a.value != null) {
         a.printMessage(a.kind, a.msg, a.element, a.annotation, a.value);
      } else if (a.annotation != null) {
         a.printMessage(a.kind, a.msg, a.element, a.annotation);
      } else if (a.element != null) {
         a.printMessage(a.kind, a.msg, a.element);
      } else {
         a.printMessage(a.kind, a.msg);
      }

      return a;
   }

   public Kind getKind() {
      return a.kind;
   }

   public Message setKind(Kind a) {
      a.kind = a;
      return a;
   }

   public CharSequence getMsg() {
      return a.msg;
   }

   public Message setMsg(CharSequence a) {
      a.msg = a;
      return a;
   }

   public Element getElement() {
      return a.element;
   }

   public AnnotationMirror getAnnotation() {
      return a.annotation;
   }

   public AnnotationValue getValue() {
      return a.value;
   }
}

/**
 * Copyright 2013- Mark C. Slee, Heron Arts LLC
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package heronarts.p3lx.ui;

import heronarts.lx.LX;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class UITheme {

  private PFont labelFont;
  private int labelColor = 0xffcccccc;

  private PFont windowTitleFont;
  private int windowTitleColor = 0xffcccccc;
  private int windowBackgroundColor = 0xff404040;
  private int windowFocusedBackgroundColor = 0xff4c4c4c;
  private int windowBorderColor = 0xff292929;

  private int paneBackgroundColor = 0xff040404;
  private int paneInsetColor = 0xff242424;

  private int focusColor = 0xff669966;
  private int primaryColor = 0xff669966;
  private int secondaryColor = 0xff666699;
  private int attentionColor = 0xff996666;

  private int darkBackgroundColor = 0xff191919;

  private PFont controlFont;
  private int controlBackgroundColor = 0xff222222;
  private int controlBorderColor = 0xff292929;
  private int controlTextColor = 0xffcccccc;
  private int controlDisabledColor = 0xff666666;

  private int midiMappingColor = 0x33ff0000;
  private int modulationSourceMappingColor = 0x3300ff00;
  private int modulationTargetMappingColor = 0x3300cccc;

  public final PImage iconNote;
  public final PImage iconTempo;
  public final PImage iconControl;
  public final PImage iconTrigger;
  public final PImage iconTriggerSource;
  public final PImage iconLoop;
  public final PImage iconMap;

  UITheme(PApplet applet) {
    // this.controlFont = applet.createFont("Arial", 10);
    this.controlFont = applet.loadFont("ArialUnicodeMS-10.vlw");
    LX.initTimer.log("P3LX: UI: Theme: controlFont");
    // this.setLabelFont(this.windowTitleFont = applet.createFont("Arial-Black", 9));
    this.setLabelFont(this.windowTitleFont = applet.loadFont("Arial-Black-9.vlw"));
    LX.initTimer.log("P3LX: UI: Theme: windowTitleFont");

    this.iconNote = applet.loadImage("icon-note.png");
    this.iconTempo = applet.loadImage("icon-tempo.png");
    this.iconControl = applet.loadImage("icon-control.png");
    this.iconTrigger = applet.loadImage("icon-trigger.png");
    this.iconTriggerSource = applet.loadImage("icon-trigger-source.png");
    this.iconLoop = applet.loadImage("icon-loop.png");
    this.iconMap = applet.loadImage("icon-map.png");
    LX.initTimer.log("P3LX: UI: Theme: Icons");
  }

  /**
   * Gets the default item font
   *
   * @return The default item font
   */
  public PFont getControlFont() {
    return this.controlFont;
  }

  /**
   * Sets the default item font
   *
   * @param font Font to use
   * @return this
   */
  public UITheme setControlFont(PFont font) {
    this.controlFont = font;
    return this;
  }

  /**
   * Gets the default title font
   *
   * @return default title font
   */
  public PFont getWindowTitleFont() {
    return this.windowTitleFont;
  }

  /**
   * Sets the default title font
   *
   * @param font Default title font
   * @return this
   */
  public UITheme setWindowTitleFont(PFont font) {
    this.windowTitleFont = font;
    return this;
  }

  /**
   * Gets the default text color
   *
   * @return default text color
   */
  public int getWindowTitleColor() {
    return this.windowTitleColor;
  }

  /**
   * Sets the default text color
   *
   * @param color Color
   * @return this UI
   */
  public UITheme setWindowTitleColor(int color) {
    this.windowTitleColor = color;
    return this;
  }

  /**
   * Gets background color
   *
   * @return background color
   */
  public int getWindowBackgroundColor() {
    return this.windowBackgroundColor;
  }

  /**
   * Sets default background color
   *
   * @param color color
   * @return this UI
   */
  public UITheme setWindowBackgroundColor(int color) {
    this.windowBackgroundColor = color;
    return this;
  }

  /**
   * Gets background color
   *
   * @return background color
   */
  public int getWindowFocusedBackgroundColor() {
    return this.windowFocusedBackgroundColor;
  }

  /**
   * Sets default background color
   *
   * @param color color
   * @return this UI
   */
  public UITheme setWindowFocusedBackgroundColor(int color) {
    this.windowFocusedBackgroundColor = color;
    return this;
  }

  /**
   * Gets border color
   *
   * @return bordercolor
   */
  public int getWindowBorderColor() {
    return this.windowBorderColor;
  }

  /**
   * Sets default border color
   *
   * @param color color
   * @return this UI
   */
  public UITheme setWindowBorderColor(int color) {
    this.windowBorderColor = color;
    return this;
  }

  /**
   * Gets border color
   *
   * @return bordercolor
   */
  public int getPaneBackgroundColor() {
    return this.paneBackgroundColor;
  }

  /**
   * Sets default border color
   *
   * @param color color
   * @return this UI
   */
  public UITheme setPaneBackgroundColor(int color) {
    this.paneBackgroundColor = color;
    return this;
  }

  /**
   * Gets border color
   *
   * @return bordercolor
   */
  public int getPaneInsetColor() {
    return this.paneInsetColor;
  }

  /**
   * Sets default border color
   *
   * @param color color
   * @return this UI
   */
  public UITheme setPaneInsetColor(int color) {
    this.paneInsetColor = color;
    return this;
  }

  /**
   * Gets highlight color
   *
   * @return Highlight color
   */
  public int getPrimaryColor() {
    return this.primaryColor;
  }

  /**
   * Sets highlight color
   *
   * @param color Color
   * @return this
   */
  public UITheme setPrimaryColor(int color) {
    this.primaryColor = color;
    return this;
  }

  /**
   * Gets highlight color
   *
   * @return Highlight color
   */
  public int getAttentionColor() {
    return this.attentionColor;
  }

  /**
   * Sets highlight color
   *
   * @param color Color
   * @return this
   */
  public UITheme setAttentionColor(int color) {
    this.attentionColor = color;
    return this;
  }

  /**
   * Gets dark background color
   *
   * @return Dark background color
   */
  public int getDarkBackgroundColor() {
    return this.darkBackgroundColor;
  }

  /**
   * Sets dark background color
   *
   * @param color Color
   * @return this
   */
  public UITheme setDarkBackgroundColor(int color) {
    this.darkBackgroundColor = color;
    return this;
  }

  /**
   * Gets focus color
   *
   * @return focus color
   */
  public int getFocusColor() {
    return this.focusColor;
  }

  /**
   * Sets highlight color
   *
   * @param color Color
   * @return this
   */
  public UITheme setFocusColor(int color) {
    this.focusColor = color;
    return this;
  }

  /**
   * Get active color
   *
   * @return Selection color
   */
  public int getSecondaryColor() {
    return this.secondaryColor;
  }

  /**
   * Set active color
   *
   * @param color Color
   * @return this
   */
  public UITheme setSecondaryColor(int color) {
    this.secondaryColor = color;
    return this;
  }

  /**
   * Get disabled color
   *
   * @return Disabled color
   */
  public int getControlDisabledColor() {
    return this.controlDisabledColor;
  }

  /**
   * Set disabled color
   *
   * @param color Color
   * @return this
   */
  public UITheme setControlDisabldColor(int color) {
    this.controlDisabledColor = color;
    return this;
  }

  /**
   * Get control background color
   *
   * @return color
   */
  public int getControlBackgroundColor() {
    return controlBackgroundColor;
  }

  /**
   * Set control background color
   *
   * @param controlBackgroundColor Color to set
   * @return this
   */
  public UITheme setControlBackgroundColor(int controlBackgroundColor ) {
    this.controlBackgroundColor = controlBackgroundColor;
    return this;
  }

  /**
   * Get control border color
   *
   * @return color
   */
  public int getControlBorderColor() {
    return controlBorderColor;
  }

  /**
   * Set control border color
   *
   * @param controlBorderColor color
   * @return this
   */
  public UITheme setControlBorderColor(int controlBorderColor) {
    this.controlBorderColor = controlBorderColor;
    return this;
  }

  /**
   * Control text color
   *
   * @return the controlTextColor
   */
  public int getControlTextColor() {
    return controlTextColor;
  }

  /**
   * Set control text color
   *
   * @param controlTextColor color
   * @return this
   */
  public UITheme setControlTextColor(int controlTextColor) {
    this.controlTextColor = controlTextColor;
    return this;
  }

  /**
   * Label font
   *
   * @return font
   */
  public PFont getLabelFont() {
    return labelFont;
  }

  /**
   * Set label font
   *
   * @param labelFont font
   * @return this
   */
  public UITheme setLabelFont(PFont labelFont) {
    this.labelFont = labelFont;
    return this;
  }

  /**
   * Default text color
   *
   * @return color
   */
  public int getLabelColor() {
    return labelColor;
  }

  /**
   * Set default text color
   *
   * @param labelColor color
   * @return this
   */
  public UITheme setLabelColor(int labelColor) {
    this.labelColor = labelColor;
    return this;
  }

  public int getMidiMappingColor() {
    return this.midiMappingColor;
  }

  public UITheme setMidiMappingColor(int midiMappingColor) {
    this.midiMappingColor = midiMappingColor;
    return this;
  }

  public int getModulationSourceMappingColor() {
    return this.modulationSourceMappingColor;
  }

  public UITheme setModulationSourceMappingColor(int modulationSourceMappingColor) {
    this.modulationSourceMappingColor = modulationSourceMappingColor;
    return this;
  }

  public int getModulationTargetMappingColor() {
    return this.modulationTargetMappingColor;
  }

  public UITheme setModulationTargetMappingColor(int modulationTargetMappingColor) {
    this.modulationTargetMappingColor = modulationTargetMappingColor;
    return this;
  }

}

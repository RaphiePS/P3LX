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

package heronarts.p3lx.ui.component;

import java.util.ArrayList;
import java.util.List;
import heronarts.lx.LXUtils;
import heronarts.lx.color.ColorParameter;
import heronarts.lx.color.LXColor;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.parameter.LXListenableNormalizedParameter;
import heronarts.lx.parameter.LXListenableParameter;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.parameter.LXParameterListener;
import heronarts.lx.parameter.LXParameterModulation;
import heronarts.p3lx.ui.UI;
import heronarts.p3lx.ui.UIFocus;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.MouseEvent;

public class UIKnob extends UIParameterControl implements UIFocus {

  public final static int KNOB_MARGIN = 6;
  public final static int KNOB_SIZE = 28;
  public final static int WIDTH = KNOB_SIZE + 2*KNOB_MARGIN;

  private final static float KNOB_INDENT = .4f;
  private final static int ARC_CENTER_X = WIDTH / 2;
  private final static int ARC_CENTER_Y = KNOB_SIZE / 2;
  private final static float ARC_START = PConstants.HALF_PI + KNOB_INDENT;
  private final static float ARC_RANGE = PConstants.TWO_PI - 2 * KNOB_INDENT;
  private final static float ARC_END = ARC_START + ARC_RANGE;

  private final List<LXListenableParameter> modulationParameters = new ArrayList<LXListenableParameter>();

  private final LXParameterListener redrawListener = new LXParameterListener() {
    public void onParameterChanged(LXParameter p) {
      redraw();
    }
  };

  public UIKnob(LXListenableNormalizedParameter parameter) {
    this();
    setParameter(parameter);
  }

  public UIKnob() {
    this(0, 0);
  }

  public UIKnob(float x, float y) {
    this(x, y, WIDTH, KNOB_SIZE);
  }

  public UIKnob(float x, float y, float w, float h) {
    super(x, y, w, h);
    this.keyEditable = true;
    enableImmediateEdit(true);
  }

  @Override
  public UIParameterControl setParameter(LXListenableNormalizedParameter parameter) {
    for (LXListenableParameter p : this.modulationParameters) {
      p.removeListener(this.redrawListener);
    }
    this.modulationParameters.clear();
    return super.setParameter(parameter);
  }

  @Override
  protected void onDraw(UI ui, PGraphics pg) {
    float knobValue = (float) getNormalized();
    float valueEnd = ARC_START + knobValue * ARC_RANGE;
    float valueStart;
    switch (this.polarity) {
    case BIPOLAR: valueStart = ARC_START + ARC_RANGE/2; break;
    default: case UNIPOLAR: valueStart = ARC_START; break;
    }

    float arcSize = KNOB_SIZE;
    pg.noStroke();

    // Modulations!
    if (this.parameter instanceof CompoundParameter) {
      CompoundParameter compound = (CompoundParameter) this.parameter;
      for (int i = compound.modulations.size()-1; i >= 0; --i) {
        LXParameterModulation modulation = compound.modulations.get(i);
        float modStart, modEnd;
        switch (modulation.getPolarity()) {
        case BIPOLAR:
          modStart = LXUtils.constrainf(valueEnd - modulation.range.getValuef() * ARC_RANGE, ARC_START, ARC_END);
          modEnd = LXUtils.constrainf(valueEnd + modulation.range.getValuef() * ARC_RANGE, ARC_START, ARC_END);
          break;
        default:
        case UNIPOLAR:
          modStart = valueEnd;
          modEnd = LXUtils.constrainf(modStart + modulation.range.getValuef() * ARC_RANGE, ARC_START, ARC_END);
          break;
        }
        ColorParameter modulationColor = modulation.color;
        if (!modulationParameters.contains(modulationColor)) {
          modulationParameters.add(modulationColor);
          modulationParameters.add(modulation.polarity);
          modulationColor.addListener(this.redrawListener);
          modulation.polarity.addListener(this.redrawListener);
        }
        // Light ring of value
        int modColor = ui.theme.getControlDisabledColor();
        int modColorInv = modColor;
        if (isEnabled()) {
          modColor = modulationColor.getColor();
          modColorInv = LXColor.hsb(LXColor.h(modColor), 50, 75);
        }
        pg.fill(modColor);
        switch (modulation.getPolarity()) {
        case BIPOLAR:
          if (modEnd >= modStart) {
            pg.arc(ARC_CENTER_X, ARC_CENTER_Y, arcSize, arcSize, valueEnd, Math.min(ARC_END, modEnd+.1f));
            pg.fill(modColorInv);
            pg.arc(ARC_CENTER_X, ARC_CENTER_Y, arcSize, arcSize, Math.max(ARC_START, modStart-.1f), valueEnd);
          } else {
            pg.arc(ARC_CENTER_X, ARC_CENTER_Y, arcSize, arcSize, Math.max(ARC_START, modEnd-.1f), valueEnd);
            pg.fill(modColorInv);
            pg.arc(ARC_CENTER_X, ARC_CENTER_Y, arcSize, arcSize, valueEnd, Math.min(ARC_END, modStart+.1f));
          }
          break;
        case UNIPOLAR:
          pg.arc(ARC_CENTER_X, ARC_CENTER_Y, arcSize, arcSize, Math.max(ARC_START, Math.min(modStart, modEnd)-.1f), Math.min(ARC_END, Math.max(modStart, modEnd)+.1f));
          break;
        }
        arcSize -= 3;
        pg.fill(ui.theme.getWindowBackgroundColor());
        pg.ellipse(ARC_CENTER_X, ARC_CENTER_Y, arcSize, arcSize);
        arcSize -= 1;
        if (arcSize < 6) {
          break;
        }

      }
    }

    // Outer fill
    pg.noStroke();
    pg.fill(ui.theme.getControlBackgroundColor());
    pg.ellipseMode(PConstants.CENTER);
    pg.arc(ARC_CENTER_X, ARC_CENTER_Y, arcSize, arcSize, ARC_START, ARC_END);

    // Value indication
    pg.fill(isEnabled() ? ui.theme.getPrimaryColor() : ui.theme.getControlDisabledColor());
    pg.arc(ARC_CENTER_X, ARC_CENTER_Y, arcSize, arcSize, Math.min(valueStart, valueEnd), Math.max(valueStart, valueEnd));

    // Center tick mark for bipolar knobs
    if ((this.polarity == LXParameter.Polarity.BIPOLAR) && (valueStart == valueEnd)) {
      pg.stroke(0xff333333);
      pg.line(ARC_CENTER_X, ARC_CENTER_Y, ARC_CENTER_X, ARC_CENTER_Y - arcSize/2);
    }

    // Center dot
    pg.noStroke();
    pg.fill(0xff333333);
    pg.ellipse(ARC_CENTER_X, ARC_CENTER_Y, 8, 8);

    super.onDraw(ui,  pg);
  }

  private double dragValue;

  @Override
  protected void onMousePressed(MouseEvent mouseEvent, float mx, float my) {
    super.onMousePressed(mouseEvent, mx, my);
    this.dragValue = getNormalized();
    if ((this.parameter != null) && (mouseEvent.getCount() > 1)) {
      LXParameterModulation modulation = getModulation(mouseEvent.isShiftDown());
      if (modulation != null && (mouseEvent.isControlDown() || mouseEvent.isMetaDown())) {
        modulation.range.reset();
      } else {
        this.parameter.reset();
      }
    }
  }

  private LXParameterModulation getModulation(boolean secondary) {
    if (this.parameter != null && this.parameter instanceof CompoundParameter) {
      CompoundParameter compound = (CompoundParameter) this.parameter;
      int size = compound.modulations.size();
      if (size > 0) {
        if (secondary && (size > 1)) {
          return compound.modulations.get(size-2);
        } else {
          return compound.modulations.get(size-1);
        }
      }
    }
    return null;
  }

  @Override
  protected void onMouseDragged(MouseEvent mouseEvent, float mx, float my, float dx, float dy) {
    if (!isEnabled()) {
      return;
    }

    float delta = dy / 100.f;
    LXParameterModulation modulation = getModulation(mouseEvent.isShiftDown());
    if (modulation != null && (mouseEvent.isMetaDown() || mouseEvent.isControlDown())) {
      modulation.range.setValue(modulation.range.getValue() - delta);
    } else {
      if (mouseEvent.isShiftDown()) {
        delta /= 10;
      }
      this.dragValue = LXUtils.constrain(this.dragValue - delta, 0, 1);
      setNormalized(this.dragValue);
    }
  }

}

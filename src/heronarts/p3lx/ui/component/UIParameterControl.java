/**
 * Copyright 2013- Mark C. Slee, Heron Arts LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.Event;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import heronarts.lx.parameter.BooleanParameter;
import heronarts.lx.parameter.DiscreteParameter;
import heronarts.lx.parameter.LXListenableNormalizedParameter;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.parameter.LXParameterListener;
import heronarts.p3lx.ui.UI;

public abstract class UIParameterControl extends UIInputBox implements LXParameterListener {

  protected final static int LABEL_MARGIN = 2;

  protected final static int LABEL_HEIGHT = 12;

  private final static int TEXT_MARGIN = 1;

  private boolean showValue = false;

  protected LXListenableNormalizedParameter parameter = null;

  protected boolean enabled = true;

  private String label = null;

  private boolean showLabel = true;

  protected boolean keyEditable = false;

  protected UIParameterControl(float x, float y, float w, float h) {
    super(x, y, w, h + LABEL_MARGIN + LABEL_HEIGHT);
    setBackground(false);
    setBorder(false);
  }

  public UIParameterControl setEnabled(boolean enabled) {
    if (enabled != this.enabled) {
      this.enabled = enabled;
      redraw();
    }
    return this;
  }

  public boolean isEnabled() {
    return (this.parameter != null) && this.enabled;
  }

  public UIParameterControl setShowLabel(boolean showLabel) {
    if (this.showLabel != showLabel) {
      this.showLabel = showLabel;
      if (this.showLabel) {
        setSize(this.width, this.height + LABEL_MARGIN + LABEL_HEIGHT);
      } else {
        setSize(this.width, this.height - LABEL_MARGIN - LABEL_HEIGHT);
      }
      redraw();
    }
    return this;
  }

  public UIParameterControl setLabel(String label) {
    if (this.label != label) {
      this.label = label;
      redraw();
    }
    return this;
  }

  @Override
  protected int getFocusColor(UI ui) {
    if (!isEnabled()) {
      return ui.theme.getControlDisabledColor();
    }
    return super.getFocusColor(ui);
  }

  public void onParameterChanged(LXParameter parameter) {
    redraw();
  }

  protected double getNormalized() {
    if (this.parameter != null) {
      return this.parameter.getNormalized();
    }
    return 0;
  }

  protected UIParameterControl setNormalized(double normalized) {
    if (this.parameter != null) {
      this.parameter.setNormalized(normalized);
    }
    return this;
  }

  public LXListenableNormalizedParameter getParameter() {
    return this.parameter;
  }

  public UIParameterControl setParameter(LXListenableNormalizedParameter parameter) {
    if (this.parameter != null) {
      this.parameter.removeListener(this);
    }
    this.parameter = parameter;
    if (this.parameter != null) {
      this.parameter.addListener(this);
    }
    redraw();
    return this;
  }

  private void setShowValue(boolean showValue) {
    if (showValue != this.showValue) {
      this.showValue = showValue;
      redraw();
    }
  }

  @Override
  protected String getValueString() {
    if (this.parameter != null) {
      if (this.parameter instanceof DiscreteParameter) {
        return ((DiscreteParameter) this.parameter).getOption();
      } else if (this.parameter instanceof BooleanParameter) {
        return ((BooleanParameter)this.parameter).isOn() ? "ON" : "OFF";
      } else {
        return String.format("%.2f", this.parameter.getValue());
      }
    }
    return "-";
  }

  private String getLabelString() {
    if (this.parameter != null) {
      return this.parameter.getLabel();
    } else if (this.label != null) {
      return this.label;
    }
    return "-";
  }

  @Override
  protected boolean isValidCharacter(char keyChar) {
    return UIDoubleBox.isValidInputCharacter(keyChar);
  }

  @Override
  protected void saveEditBuffer() {
    if (this.parameter != null) {
      try {
        this.parameter.setValue(Double.parseDouble(this.editBuffer));
      } catch (NumberFormatException nfx) {}
    }
  }

  @Override
  protected void onDraw(UI ui, PGraphics pg) {
    if (this.showLabel) {
      drawLabel(ui, pg);
    }
  }

  private void drawLabel(UI ui, PGraphics pg) {
    if (this.editing) {
      pg.fill(ui.theme.getControlBackgroundColor());
      pg.noStroke();
      pg.rect(0, this.height - LABEL_HEIGHT, this.width, LABEL_HEIGHT);
      pg.fill(ui.theme.getPrimaryColor());
      pg.textFont(ui.theme.getControlFont());
      pg.text(clipTextToWidth(pg, this.editBuffer, this.width - TEXT_MARGIN), this.width/2, this.height - TEXT_MARGIN);
    } else {
      String labelText = this.showValue ? getValueString() : getLabelString();
      pg.fill(ui.theme.getControlTextColor());
      pg.textAlign(PConstants.CENTER, PConstants.BOTTOM);
      pg.textFont(ui.theme.getControlFont());
      pg.text(clipTextToWidth(pg, labelText, this.width - TEXT_MARGIN), this.width/2, this.height - TEXT_MARGIN);
    }
  }

  private double getIncrement(Event inputEvent) {
    return inputEvent.isShiftDown() ? .1 : .02;
  }

  /**
   * Subclasses may optionally override to decrement value in response to arrows.
   * Decrement is invoked for the left or down arrow keys.
   *
   * @param keyEvent
   */
  @Override
  protected void decrementValue(KeyEvent keyEvent) {
    if (this.parameter != null) {
      consumeKeyEvent();
      if (this.parameter instanceof DiscreteParameter) {
        DiscreteParameter dp = (DiscreteParameter) this.parameter;
        dp.decrement(keyEvent.isShiftDown() ? dp.getRange() / 10 : 1);
      } else if (this.parameter instanceof BooleanParameter) {
        ((BooleanParameter)this.parameter).setValue(false);
      } else {
        setNormalized(getNormalized() - getIncrement(keyEvent));
      }
    }
  }

  /**
   * Subclasses may optionally override to decrement value in response to arrows.
   * Increment is invoked for the right or up keys.
   *
   * @param keyEvent
   */
  @Override
  protected void incrementValue(KeyEvent keyEvent) {
    if (this.parameter != null) {
      if (this.parameter instanceof DiscreteParameter) {
        DiscreteParameter dp = (DiscreteParameter) this.parameter;
        dp.increment(keyEvent.isShiftDown() ? dp.getRange() / 10 : 1);
      } else if (this.parameter instanceof BooleanParameter) {
        ((BooleanParameter)this.parameter).setValue(true);
      } else {
        setNormalized(getNormalized() + getIncrement(keyEvent));
      }
    }
  }

  @Override
  protected void onKeyPressed(KeyEvent keyEvent, char keyChar, int keyCode) {
    if (!this.editing && ((keyCode == java.awt.event.KeyEvent.VK_SPACE) || (keyCode == java.awt.event.KeyEvent.VK_ENTER))) {
      consumeKeyEvent();
      setShowValue(true);;
    } else if (this.keyEditable) {
      super.onKeyPressed(keyEvent, keyChar, keyCode);
    }
  }

  @Override
  protected void onKeyReleased(KeyEvent keyEvent, char keyChar, int keyCode) {
    if ((keyCode == java.awt.event.KeyEvent.VK_SPACE) || (keyCode == java.awt.event.KeyEvent.VK_ENTER)) {
      consumeKeyEvent();
      setShowValue(false);
    }
  }

  @Override
  protected void onMousePressed(MouseEvent mouseEvent, float mx, float my) {
    setShowValue(true);
  }

  @Override
  protected void onMouseReleased(MouseEvent mouseEvent, float mx, float my) {
    setShowValue(false);
  }

  @Override
  protected void onBlur() {
    setShowValue(false);
  }

}

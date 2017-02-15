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

import heronarts.p3lx.ui.UI;
import heronarts.p3lx.ui.UI2dComponent;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * A simple text label object. Draws a string aligned top-left to its x-y
 * position.
 */
public class UILabel extends UI2dComponent {

  private int horizontalAlignment = PConstants.LEFT;

  private int verticalAlignment = PConstants.TOP;

  private int topPadding = 0;
  private int rightPadding = 0;
  private int leftPadding = 0;
  private int bottomPadding = 0;

  /**
   * Label text
   */
  private String label = "";

  public UILabel() {
    this(0, 0, 0, 0);
  }

  public UILabel(float x, float y, float w, float h) {
    super(x, y, w, h);
  }

  /**
   * Sets padding on all 4 sides
   *
   * @param padding Padding
   * @return this
   */
  public UILabel setPadding(int padding) {
    return setPadding(padding, padding, padding, padding);
  }

  /**
   * Sets padding on top and sides, CSS style
   *
   * @param topBottom Top bottom padding
   * @param leftRight Left right padding
   * @return this
   */
  public UILabel setPadding(int topBottom, int leftRight) {
    return setPadding(topBottom, leftRight, topBottom, leftRight);
  }

  /**
   * Sets padding on all 4 sides
   *
   * @param topPadding Top padding
   * @param rightPadding Right padding
   * @param bottomPadding Bottom padding
   * @param leftPadding Left padding
   * @return this
   */
  public UILabel setPadding(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
    boolean redraw = false;
    if (this.topPadding != topPadding) {
      this.topPadding = topPadding;
      redraw = true;
    }
    if (this.rightPadding != rightPadding) {
      this.rightPadding = rightPadding;
      redraw = true;
    }
    if (this.bottomPadding != bottomPadding) {
      this.bottomPadding = bottomPadding;
      redraw = true;
    }
    if (this.leftPadding != leftPadding) {
      this.leftPadding = leftPadding;
      redraw = true;
    }
    if (redraw) {
      redraw();
    }
    return this;
  }

  /**
   * Sets the alignment of the label using PConstants
   * values LEFT/CENTER/RIGHT
   *
   * @param horizontalAlignment
   * @return this
   */
  public UILabel setAlignment(int horizontalAlignment) {
    setAlignment(horizontalAlignment, PConstants.BASELINE);
    return this;
  }

  /**
   * Sets the alignment of the label using PConstants values
   * LEFT/CENTER/RIGHT and TOP/CENTER/BOTTOM/BASELINE
   *
   * @param horizontalAlignment
   * @return this
   */
  public UILabel setAlignment(int horizontalAlignment, int verticalAlignment) {
    if ((this.horizontalAlignment != horizontalAlignment)
        || (this.verticalAlignment != verticalAlignment)) {
      this.horizontalAlignment = horizontalAlignment;
      this.verticalAlignment = verticalAlignment;
      redraw();
    }
    return this;
  }

  @Override
  protected void onDraw(UI ui, PGraphics pg) {
    pg.textFont(hasFont() ? getFont() : ui.theme.getLabelFont());
    pg.fill(hasFontColor() ? getFontColor() : ui.theme.getLabelColor());
    float tx = this.leftPadding, ty = this.topPadding;
    switch (this.horizontalAlignment) {
    case PConstants.CENTER:
      tx = this.width / 2;
      break;
    case PConstants.RIGHT:
      tx = this.width - this.rightPadding;
      break;
    }
    switch (this.verticalAlignment) {
    case PConstants.BASELINE:
      ty = this.height - this.bottomPadding;
      break;
    case PConstants.BOTTOM:
      ty = this.height - this.bottomPadding;
      break;
    case PConstants.CENTER:
      ty = this.height / 2;
      break;
    }
    pg.textAlign(this.horizontalAlignment, this.verticalAlignment);
    pg.text(this.label, tx, ty);
  }

  public UILabel setLabel(String label) {
    if (this.label != label) {
      this.label = label;
      redraw();
    }
    return this;
  }
}

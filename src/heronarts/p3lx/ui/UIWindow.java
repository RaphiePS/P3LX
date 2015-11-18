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

package heronarts.p3lx.ui;

import processing.event.MouseEvent;
import heronarts.lx.LXUtils;
import heronarts.p3lx.ui.component.UILabel;

/**
 * A UIWindow is a UIContext that by default has a title bar and can be dragged
 * around when the mouse is pressed on the title bar.
 */
public class UIWindow extends UI2dContext {

  public final static int TITLE_LABEL_HEIGHT = 24;

  private final static int TITLE_PADDING = 6;

  /**
   * The label object
   */
  private final UILabel label;

  /**
   * Constructs a window object
   *
   * @param ui UI to place in
   * @param title Title for this window
   * @param x X-coordinate
   * @param y Y-coordinate
   * @param w Width
   * @param h Height
   */
  public UIWindow(final UI ui, String title, float x, float y, float w, float h) {
    super(ui, x, y, w, h);
    setBackgroundColor(ui.theme.getWindowBackgroundColor());
    setBorderColor(ui.theme.getWindowBorderColor());
    this.label = new UILabel(0, 0, w, TITLE_LABEL_HEIGHT);
    this.label
      .setLabel(title)
      .setPadding(TITLE_PADDING)
      .setFontColor(ui.theme.getWindowTitleColor())
      .setFont(ui.theme.getWindowTitleFont())
      .addToContainer(this);
  }

  private boolean movingWindow = false;

  @Override
  protected void onFocus() {
    this.label.setFontColor(ui.theme.getFocusColor());
  }

  @Override
  protected void onBlur() {
    this.label.setFontColor(ui.theme.getWindowTitleColor());
  }

  @Override
  protected void onMousePressed(MouseEvent mouseEvent, float mx, float my) {
    this.movingWindow = (my < TITLE_LABEL_HEIGHT);
    bringToFront();
    if (!hasFocus()) {
      focus();
    }
  }

  @Override
  protected void onMouseDragged(MouseEvent mouseEvent, float mx, float my, float dx, float dy) {
    if (this.movingWindow) {
      float newX = LXUtils.constrainf(this.x + dx, 0, this.parent.getWidth() - this.width);
      float newY = LXUtils.constrainf(this.y + dy, 0, this.parent.getHeight() - this.height);
      setPosition(newX, newY);
    }
  }

  /**
   * Set the title of the window.
   *
   * @param title Title of the window
   * @return this window
   */
  public UIWindow setTitle(String title) {
    this.label.setLabel(title);
    return this;
  }
}

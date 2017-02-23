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

public interface UIContainer {

  /**
   * Returns the width of the content container
   *
   * @return width of content section
   */
  public float getContentWidth();

  /**
   * Returns the height of the content container
   *
   * @return height of the content section
   */
  public float getContentHeight();

  /**
   * Returns the object that elements are added to when placed in this container.
   * In most cases, it will be "this" - but some elements have special sub-containers.
   *
   * @return Element
   */
  public UIObject getContentTarget();

}

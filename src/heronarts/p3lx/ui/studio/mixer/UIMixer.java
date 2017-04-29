/**
 * Copyright 2017- Mark C. Slee, Heron Arts LLC
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

package heronarts.p3lx.ui.studio.mixer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import heronarts.lx.LX;
import heronarts.lx.LXChannel;
import heronarts.lx.LXEngine;
import heronarts.p3lx.ui.UI;
import heronarts.p3lx.ui.UI2dContainer;
import heronarts.p3lx.ui.component.UIButton;
import heronarts.p3lx.ui.studio.clip.UIChannelClipLauncher;
import processing.core.PConstants;

public class UIMixer extends UI2dContainer {

  public final static int PADDING = 6;
  public final static int STRIP_SPACING = UIMixerStripControls.WIDTH + UIMixerStripControls.SPACING;
  private final static int ADD_CHANNEL_BUTTON_MARGIN = 1;
  public final static int HEIGHT = UIMixerStrip.HEIGHT + 2*PADDING;

  private final Map<LXChannel, UIChannelStrip> internalChannelStrips = new HashMap<LXChannel, UIChannelStrip>();
  public final Map<LXChannel, UIChannelStrip> channelStrips = Collections.unmodifiableMap(this.internalChannelStrips);

  public final UIMasterStrip masterStrip;
  private final UIButton addChannelButton;

  final LX lx;

  public UIMixer(final UI ui, final LX lx, float x, float y, float h) {
    super(x, y, 0, h);
    this.lx = lx;

    setBackgroundColor(ui.theme.getPaneInsetColor());
    setBorderRounding(4);

    int xp = PADDING;
    for (LXChannel channel : lx.engine.getChannels()) {
      UIChannelStrip strip = new UIChannelStrip(ui, this, lx, channel, xp, PADDING);
      this.internalChannelStrips.put(channel, strip);
      strip.addToContainer(this);
      xp += STRIP_SPACING;
    }

    this.addChannelButton = new UIButton(xp, PADDING + UIChannelClipLauncher.HEIGHT + UIMixerStrip.SPACING, 16, UIMixerStripControls.HEIGHT) {
      @Override
      public void onToggle(boolean on) {
        if (!on) {
          lx.engine.addChannel();
          lx.engine.focusedChannel.setValue(lx.engine.getChannels().size()-1);
        }
      }
    };
    this.addChannelButton
    .setLabel("+")
    .setMomentary(true)
    .setInactiveColor(0xff393939) // TODO(mcslee): control disabled color?
    .setBorder(false)
    .setTextAlignment(PConstants.CENTER, PConstants.CENTER)
    .setDescription("New Channel: add another channel")
    .addToContainer(this);
    xp += this.addChannelButton.getWidth() + ADD_CHANNEL_BUTTON_MARGIN;

    this.masterStrip = new UIMasterStrip(ui, this, lx, xp, PADDING);
    this.masterStrip.addToContainer(this);
    setWidth(xp + UIMixerStripControls.WIDTH + PADDING);

    lx.engine.addListener(new LXEngine.Listener() {
      public void channelAdded(LXEngine engine, LXChannel channel) {
        UIChannelStrip strip = new UIChannelStrip(ui, UIMixer.this, lx, channel, width, PADDING);
        internalChannelStrips.put(channel, strip);
        strip.addToContainer(UIMixer.this, channel.getIndex());
        updateStripPositions();
        setWidth(width + STRIP_SPACING);
      }

      public void channelRemoved(LXEngine engine, LXChannel channel) {
        for (LXChannel c : internalChannelStrips.keySet()) {
          if (c.getIndex() >= channel.getIndex()) {
            UIChannelStrip strip = internalChannelStrips.get(c);
            strip.setPosition(strip.getX() - STRIP_SPACING, strip.getY());
          }
        }
        internalChannelStrips.remove(channel).removeFromContainer();
        masterStrip.setPosition(masterStrip.getX() - STRIP_SPACING, masterStrip.getY());
        updateStripPositions();
        setWidth(width - STRIP_SPACING);
      }

      public void channelMoved(LXEngine engine, LXChannel channel) {
        for (LXChannel c : internalChannelStrips.keySet()) {
          UIChannelStrip strip = internalChannelStrips.get(c);
          strip.setPosition(PADDING + STRIP_SPACING * c.getIndex(), strip.getY());
        }
        internalChannelStrips.get(channel).setContainerIndex(channel.getIndex());
      }
    });
  }

  void updateStripPositions() {
    int xp = PADDING;
    for (LXChannel channel : lx.engine.getChannels()) {
      this.internalChannelStrips.get(channel).setX(xp);
      xp += STRIP_SPACING;
    }
    this.addChannelButton.setX(xp);
    xp += this.addChannelButton.getWidth() + ADD_CHANNEL_BUTTON_MARGIN;
    this.masterStrip.setX(xp);
  }

}
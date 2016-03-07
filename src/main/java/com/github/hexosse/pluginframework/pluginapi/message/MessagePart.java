package com.github.hexosse.pluginframework.pluginapi.message;

/*
 * Copyright 2016 Hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

/**
 * This class represent a part of a {@link MessageLine}
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessagePart
{
	private String part;
	private MessageColor color;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;

	public MessagePart(String part)
	{
		this.part = part;
	}

	public MessagePart(MessagePart part)
	{
		this.part = part.part;
		this.color = part.color == null ? null : new MessageColor(part.color.color());
		this.clickEvent = part.clickEvent == null ? null : new ClickEvent(part.clickEvent.getAction(), part.clickEvent.getValue());
		this.hoverEvent = part.hoverEvent == null ? null : new HoverEvent(part.hoverEvent.getAction(), part.hoverEvent.getValue());
	}

	public MessagePart color(MessageColor color)
	{
		this.color = color;
		return this;
	}

	public MessagePart event(ClickEvent clickEvent)
	{
		this.clickEvent = clickEvent;
		return this;
	}

	public MessagePart event(HoverEvent hoverEvent)
	{
		this.hoverEvent = hoverEvent;
		return this;
	}

	public String getText() { return part; }

	public ChatColor getColor() { return (this.color==null ? null : this.color.color()); }

	public ClickEvent getClickEvent() { return clickEvent; }

	public HoverEvent getHoverEvent() { return hoverEvent; }

	public String toString() {
		return new String( (this.color==null ? "" : this.color.color().toString()) + part);
	}
}

package org.handson.common;

import java.beans.PropertyEditorSupport;

import org.handson.data.Person.Discriminator;

import com.google.common.base.Strings;

public class DiscriminatorBinder extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!Strings.isNullOrEmpty(text)) {
			super.setValue(Discriminator.from(text));
		} else {
			super.setValue(null);
		}
	}
}

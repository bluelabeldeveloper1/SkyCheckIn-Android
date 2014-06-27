package com.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView
{

	public MyTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		String textStyleValue = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "textStyle");

		String fontName = "Avenir Next.ttc";

		if (textStyleValue != null)
		{
			fontName = "Avenir Next.ttc";

			// if (textStyleValue.equals("0x0"))
			// {
			// fontName = "rockwell_regular.TTF";
			// }
			// else if (textStyleValue.equals("0x1"))
			// {
			// fontName = "rockwell_bold.TTF";
			// }
			// else if (textStyleValue.equals("0x2"))
			// {
			// fontName = "rockwell_italic.TTF";
			// }
		}
		else
		{
			fontName = "Avenir Next.ttc";
		}

		setFont(context, fontName);
	}

	private void setFont(Context context, String fontName)
	{

//		if (isInEditMode())
//		{
			Typeface font = Typeface.createFromAsset(context.getAssets(), fontName);
			setTypeface(font);
//		}
	}
}

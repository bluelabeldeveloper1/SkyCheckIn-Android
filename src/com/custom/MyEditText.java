package com.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class MyEditText extends EditText
{

	private boolean cursur_enabled = true;

//	private EditTextImeBackListener mOnImeBack;

	public MyEditText(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		//

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

	// @Override
	// public void onSelectionChanged(int start, int end)
	// {
	// if (!cursur_enabled)
	// {
	// CharSequence text = getText();
	// if (text != null)
	// {
	// if (start != text.length() || end != text.length())
	// {
	// setSelection(text.length(), text.length());
	// return;
	// }
	// }
	// }
	// super.onSelectionChanged(start, end);
	// }

	public MyEditText(Context context)
	{
		super(context);
	}

	public void setFont(Context context, String fontName)
	{
//		if (isInEditMode())
//		{
			Typeface font = Typeface.createFromAsset(context.getAssets(), fontName);
			setTypeface(font);
//		}

	}

//	@Override
//	public boolean onKeyPreIme(int keyCode, KeyEvent event)
//	{
//		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
//		{
//			if (mOnImeBack != null)
//				mOnImeBack.onImeBack(this, this.getText().toString());
//		}
//		return super.dispatchKeyEvent(event);
//	}

	/**
	 * D�fini le listener pour la gestion de la touche Back en mode clavier
	 * 
	 * @param listener
	 */
//	public void setOnEditTextImeBackListener(EditTextImeBackListener listener)
//	{
//		mOnImeBack = listener;
//	}

//	public interface EditTextImeBackListener
//	{
//
//		/**
//		 * Se produit lorsque la touche Back est press� pour sortir du mode
//		 * clavier de l'IME
//		 * 
//		 * @param ctrl
//		 *            Contr�le EditTextBackEvent parent
//		 * @param text
//		 *            Texte du contr�le
//		 */
//		public abstract void onImeBack(MyEditText ctrl, String text);
//	}

}

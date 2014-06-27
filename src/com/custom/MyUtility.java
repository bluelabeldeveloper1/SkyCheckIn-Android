package com.custom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.skycheckin.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class MyUtility
{
	public static void hideSoftKeyboard(Activity activity)
	{
		try
		{
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
		catch (StackOverflowError e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static Drawable UrlToDrawable(String url)
	{
		try
		{
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static Bitmap UrlToBitmap(String url)
	{
		Drawable drawable = UrlToDrawable(url);
		Bitmap bt = ((BitmapDrawable) drawable).getBitmap();

		return bt;
	}

	// public static String ImageUrlToBase64(String url)
	// {
	// Drawable drawable = UrlToDrawable(url);
	// Bitmap bt = ((BitmapDrawable) drawable).getBitmap();
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bt.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	// byte[] byteArrayImage = baos.toByteArray();
	// // String encodedImage = Base64.encodeToString(byteArrayImage,
	// Base64.DEFAULT);
	// String encodedImage = com.custom.Base64.encodeBytes(byteArrayImage);
	// return encodedImage;
	// }

	// public static String BitmapToBase64(Bitmap bitmap)
	// {
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	// byte[] byteArrayImage = baos.toByteArray();
	// // String encodedImage = Base64.encodeToString(byteArrayImage,
	// Base64.DEFAULT);
	// String encodedImage = com.custom.Base64.encodeBytes(byteArrayImage);
	// return encodedImage;
	// }

	public static boolean isBitmapEquals(Bitmap bitmap1, Bitmap bitmap2)
	{
		ByteBuffer buffer1 = ByteBuffer.allocate(bitmap1.getHeight() * bitmap1.getRowBytes());
		bitmap1.copyPixelsToBuffer(buffer1);

		ByteBuffer buffer2 = ByteBuffer.allocate(bitmap2.getHeight() * bitmap2.getRowBytes());
		bitmap2.copyPixelsToBuffer(buffer2);

		return Arrays.equals(buffer1.array(), buffer2.array());
	}

	public static boolean isEmailValid(String email)
	{
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches())
		{
			isValid = true;
		}
		return isValid;
	}

	public static byte[] imageViewToByteArray(ImageView iv)
	{
		BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
		Bitmap bmap = drawable.getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

		return stream.toByteArray();
	}

	public static byte[] BitmapToByteArray(Bitmap bmap)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

		return stream.toByteArray();
	}

	public static File saveImageForShare(Activity activity, Bitmap bitmap)
	{

		File file = new File(activity.getExternalCacheDir(), "share.png");

		try
		{
			if (!file.isFile())
			{
				file.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);

			outStream.flush();
			outStream.close();

			return file;

		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean checkInternetConnection(Context context)
	{
		ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ARE WE CONNECTED TO THE NET
		if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static File ConvertUrlToFile(String url)
	{

		File file = null;

		file = new File(url);

		return file;
	}

	public static File ConvertUriToFile(Uri uri)
	{

		File file = null;

		file = new File(uri.toString());

		return file;
	}

	public static void setAlertMessage(Context context, String title, String msg)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				return;
			}
		});
		dialog.show();
	}

	public static void setAlertMessage(Context context, String msg)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(context.getResources().getString(R.string.app_name));
		dialog.setMessage(msg);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				return;
			}
		});
		dialog.show();
	}

	public static void setInternetAlertMessage(Context context)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(Global.AlertDialogNetworkTitle);
		dialog.setMessage(Global.AlertDialogNetworkMessage);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				return;
			}
		});
		dialog.show();
	}

	/***
	 * For Image masking
	 * 
	 * @param mask_img_id
	 *            pass mask image drawable id
	 * @param color
	 *            pass color
	 * @return resultant bitmap
	 */
	public static Bitmap getMaskedImageByColor(Context context, int mask_img_id, int color)
	{
		Bitmap mask = BitmapFactory.decodeResource(context.getResources(), mask_img_id);
		Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888); // create
																									// another
																									// bitmap
																									// with
																									// same
																									// height
		// and width of the previous bitmap
		Canvas mCanvas = new Canvas(result);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mCanvas.drawColor(color);
		mCanvas.drawBitmap(mask, 0, 0, paint);
		paint.setXfermode(null);
		if (mask != null)
		{
			mask.recycle();
		}
		return result;
	}

	/***
	 * For Image masking. Please check that bother images have same width and
	 * height for better result
	 * 
	 * @param mask_img_id
	 *            pass mask image drawable id
	 * @param src_id
	 *            pass original image drawable id
	 * @return resultant bitmap
	 */
	public static Bitmap getMaskedImage(Context context, int mask_img_id, int src_id)
	{
		Bitmap original = BitmapFactory.decodeResource(context.getResources(), src_id);

		Bitmap mask = BitmapFactory.decodeResource(context.getResources(), mask_img_id);

		Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(result);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mCanvas.drawBitmap(original, 0, 0, null);
		mCanvas.drawBitmap(mask, 0, 0, paint);
		paint.setXfermode(null);

		if (original != null)
		{
			original.recycle();
		}

		if (mask != null)
		{
			mask.recycle();
		}

		return result;
	}

	public static Bitmap getMaskedBitmap(Context context, int mask_img_id, Bitmap src_id)
	{
		Bitmap original = src_id;

		Bitmap mask = BitmapFactory.decodeResource(context.getResources(), mask_img_id);

		Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(result);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mCanvas.drawBitmap(original, 0, 0, null);
		mCanvas.drawBitmap(mask, 0, 0, paint);
		paint.setXfermode(null);

		if (original != null)
		{
			original.recycle();
		}

		if (mask != null)
		{
			mask.recycle();
		}

		return result;
	}

	public static Bitmap getMaskedImageUrl(Context context, int mask_img_id, String url)
	{

		Bitmap mask = BitmapFactory.decodeResource(context.getResources(), mask_img_id);

		Bitmap original = getBitmapFromURL(context, url);

		if (original == null)
		{
			return mask;
		}

		Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(result);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mCanvas.drawBitmap(original, 0, 0, null);
		mCanvas.drawBitmap(mask, 0, 0, paint);
		paint.setXfermode(null);

		if (original != null)
		{
			original.recycle();
		}

		if (mask != null)
		{
			mask.recycle();
		}

		return result;
	}

	public static Bitmap getBitmapFromURL(Context context, String src)
	{
		try
		{
			Bitmap myBitmap = null;

			if (checkInternetConnection(context))
			{
				URL url = new URL(src);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				myBitmap = BitmapFactory.decodeStream(input);
			}
			else
			{
				return null;
			}

			return myBitmap;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}

package com.example.wsj.myhuanxindemotwo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import com.example.wsj.myhuanxindemotwo.R;

import java.lang.reflect.Field;

public class ImageUtils {

	/**
	 * 通过图片的名字，显示图片，主要用在TextView中
	 * 
	 * @param context
	 * @param htmlString 图片的名字
	 * @return 可显示的图片的String类型
	 */
	public static CharSequence formatString(final Context context,
											String htmlString) {
		CharSequence ch = Html.fromHtml(htmlString, new Html.ImageGetter() {

//			@TargetApi(Build.VERSION_CODES.LOLLIPOP)
			@Override
			public Drawable getDrawable(String source) {
				Drawable drawable = context.getResources().getDrawable(
						getResourceId(source));
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
				return drawable;
			}
		}, null);

		return ch;
	}

	// 利用反射机制，通过资源名字得到资源的ID
	public static int getResourceId(String resName) {
		try {
			Field field = R.drawable.class.getField(resName);
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("TAG", "faild to get resource ID !");
		}
		return 0;
	}

	/**
	 * 通过图片的名字，把图片转化成可显示的文本，主要显示在EditText中
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            图片的真实名字
	 * @return 可显示的图片的字符串
	 * @throws Exception
	 */
	public static SpannableString getSpanableString(Context context, String name)
			throws Exception {
		String html = "<img src='" + name + "'/>";
		Field field = R.drawable.class.getDeclaredField(name);
		int resourceId = Integer.parseInt(field.get(null).toString());
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resourceId);
		ImageSpan imageSpan = new ImageSpan(context, bitmap);
		SpannableString spannableString = new SpannableString(html);
		spannableString.setSpan(imageSpan, 0, html.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}


}

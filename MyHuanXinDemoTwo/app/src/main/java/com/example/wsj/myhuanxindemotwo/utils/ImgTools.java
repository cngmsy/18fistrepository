package com.example.wsj.myhuanxindemotwo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 使用Universal-Image-Loader从网络获得图片
 * 
 * @author 张硕
 * 
 */
public class ImgTools {
	ImageLoader loder;
	static ImgTools imgTools;

	public ImgTools() {
		// 实例化ImageLoader
		loder = ImageLoader.getInstance();
	}
	/**
	 * 使用单例模式对ImgTools进行实例化
	 * @return
	 */
	public static ImgTools getInstance() {
		if (imgTools == null) {
			imgTools = new ImgTools();
		}
		return imgTools;
	}

	/**
	 * 获取正常的图片
	 * 
	 * @param imgUrl网络图片的地址
	 * @param img
	 * @param drawable
	 *            下载时，uri为空，加载错误时显示的图片
	 */
	public ImageLoader getImgFromNetByUrl(String imgUrl, ImageView img,
										  int drawableId) {
		ImageLoader loder = ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId) // 设置正在下载是显示的图片
				.showImageForEmptyUri(drawableId)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(drawableId)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisk(true)// 是否緩存到sd卡上
				.considerExifParams(true) // 启用EXIF和JPEG图像格式
				//.bitmapConfig(Bitmap.Config.ARGB_4444)
				.build();
		loder.displayImage(imgUrl, img, options);
		return loder;
	}

	/**
	 * 获取圆角的图片
	 * 
	 * @param imgUrl
	 *            网络图片的地址
	 * @param img
	 * @param drawable
	 *            下载时，uri为空，加载错误时显示的图片
	 * @param radius
	 *            圆角的弧度
	 */
	public void getRadiusImgFromNetByUrl(String imgUrl, ImageView img,
										 int drawableId, int radius) {
		ImageLoader loder = ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId)
				// 设置正在下载是显示的图片
				.showImageForEmptyUri(drawableId)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(drawableId)
				// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)
				// 是否緩存都內存中
				.cacheOnDisk(true)
				// 是否緩存到sd卡上
				.considerExifParams(true)
				// 启用EXIF和JPEG图像格式
				.displayer(new RoundedBitmapDisplayer(radius))
				.bitmapConfig(Bitmap.Config.ARGB_4444)
				.build();
		loder.displayImage(imgUrl, img, options);
	}
	/**图片毛玻璃效果*/
	 public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

	        
	        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

	        if (radius < 1) {
	            return (null);
	        }

	        int w = bitmap.getWidth();
	        int h = bitmap.getHeight();

	        int[] pix = new int[w * h];
//	        Log.e("pix", w + " " + h + " " + pix.length);
	        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

	        int wm = w - 1;
	        int hm = h - 1;
	        int wh = w * h;
	        int div = radius + radius + 1;

	        int r[] = new int[wh];
	        int g[] = new int[wh];
	        int b[] = new int[wh];
	        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
	        int vmin[] = new int[Math.max(w, h)];

	        int divsum = (div + 1) >> 1;
	        divsum *= divsum;
	        int temp = 256 * divsum;
	        int dv[] = new int[temp];
	        for (i = 0; i < temp; i++) {
	            dv[i] = (i / divsum);
	        }

	        yw = yi = 0;

	        int[][] stack = new int[div][3];
	        int stackpointer;
	        int stackstart;
	        int[] sir;
	        int rbs;
	        int r1 = radius + 1;
	        int routsum, goutsum, boutsum;
	        int rinsum, ginsum, binsum;

	        for (y = 0; y < h; y++) {
	            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
	            for (i = -radius; i <= radius; i++) {
	                p = pix[yi + Math.min(wm, Math.max(i, 0))];
	                sir = stack[i + radius];
	                sir[0] = (p & 0xff0000) >> 16;
	                sir[1] = (p & 0x00ff00) >> 8;
	                sir[2] = (p & 0x0000ff);
	                rbs = r1 - Math.abs(i);
	                rsum += sir[0] * rbs;
	                gsum += sir[1] * rbs;
	                bsum += sir[2] * rbs;
	                if (i > 0) {
	                    rinsum += sir[0];
	                    ginsum += sir[1];
	                    binsum += sir[2];
	                } else {
	                    routsum += sir[0];
	                    goutsum += sir[1];
	                    boutsum += sir[2];
	                }
	            }
	            stackpointer = radius;

	            for (x = 0; x < w; x++) {

	                r[yi] = dv[rsum];
	                g[yi] = dv[gsum];
	                b[yi] = dv[bsum];

	                rsum -= routsum;
	                gsum -= goutsum;
	                bsum -= boutsum;

	                stackstart = stackpointer - radius + div;
	                sir = stack[stackstart % div];

	                routsum -= sir[0];
	                goutsum -= sir[1];
	                boutsum -= sir[2];

	                if (y == 0) {
	                    vmin[x] = Math.min(x + radius + 1, wm);
	                }
	                p = pix[yw + vmin[x]];

	                sir[0] = (p & 0xff0000) >> 16;
	                sir[1] = (p & 0x00ff00) >> 8;
	                sir[2] = (p & 0x0000ff);

	                rinsum += sir[0];
	                ginsum += sir[1];
	                binsum += sir[2];

	                rsum += rinsum;
	                gsum += ginsum;
	                bsum += binsum;

	                stackpointer = (stackpointer + 1) % div;
	                sir = stack[(stackpointer) % div];

	                routsum += sir[0];
	                goutsum += sir[1];
	                boutsum += sir[2];

	                rinsum -= sir[0];
	                ginsum -= sir[1];
	                binsum -= sir[2];

	                yi++;
	            }
	            yw += w;
	        }
	        for (x = 0; x < w; x++) {
	            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
	            yp = -radius * w;
	            for (i = -radius; i <= radius; i++) {
	                yi = Math.max(0, yp) + x;

	                sir = stack[i + radius];

	                sir[0] = r[yi];
	                sir[1] = g[yi];
	                sir[2] = b[yi];

	                rbs = r1 - Math.abs(i);

	                rsum += r[yi] * rbs;
	                gsum += g[yi] * rbs;
	                bsum += b[yi] * rbs;

	                if (i > 0) {
	                    rinsum += sir[0];
	                    ginsum += sir[1];
	                    binsum += sir[2];
	                } else {
	                    routsum += sir[0];
	                    goutsum += sir[1];
	                    boutsum += sir[2];
	                }

	                if (i < hm) {
	                    yp += w;
	                }
	            }
	            yi = x;
	            stackpointer = radius;
	            for (y = 0; y < h; y++) {
	                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
	                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

	                rsum -= routsum;
	                gsum -= goutsum;
	                bsum -= boutsum;

	                stackstart = stackpointer - radius + div;
	                sir = stack[stackstart % div];

	                routsum -= sir[0];
	                goutsum -= sir[1];
	                boutsum -= sir[2];

	                if (x == 0) {
	                    vmin[y] = Math.min(y + r1, hm) * w;
	                }
	                p = x + vmin[y];

	                sir[0] = r[p];
	                sir[1] = g[p];
	                sir[2] = b[p];

	                rinsum += sir[0];
	                ginsum += sir[1];
	                binsum += sir[2];

	                rsum += rinsum;
	                gsum += ginsum;
	                bsum += binsum;

	                stackpointer = (stackpointer + 1) % div;
	                sir = stack[stackpointer];

	                routsum += sir[0];
	                goutsum += sir[1];
	                boutsum += sir[2];

	                rinsum -= sir[0];
	                ginsum -= sir[1];
	                binsum -= sir[2];

	                yi += w;
	            }
	        }

//	        Log.e("pix", w + " " + h + " " + pix.length);
	        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
	        return (bitmap);
	    }
	
	
}

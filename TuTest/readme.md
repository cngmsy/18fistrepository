#图片对比度变化
效果图gif
![](duibidu.gif)



#实现的核心代码是
Contrastseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
                // int brightness = progress - 127;
                float contrast = (float) ((progress + 64) / 128.0);
                ColorMatrix cMatrix = new ColorMatrix();
                cMatrix.set(new float[]{ contrast, 0, 0, 0, 0, 0,
                        contrast, 0, 0, 0,// 改变对比度
                        0, 0, contrast, 0, 0, 0, 0, 0, 1, 0 });
                Paint paint = new Paint();
                paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
                Canvas canvas = new Canvas(bmp);
                canvas.drawBitmap(tuBitmap,0,0,paint);
                dstimage.setImageBitmap(bmp);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
###步骤一:当拖动条的滑块位置发生改变时触发该方法
###步骤二:创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
###步骤三:ColorMatrix cMatrix = new ColorMatrix();
###步骤四:cMatrix 改变对比度

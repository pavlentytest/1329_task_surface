package ru.samsung.itschool.mdev.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public float r,x,y = 0;
    public MyThread myThread;
    private boolean firstClick;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myThread = new MyThread();
        myThread.setRunning(true);
        myThread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.x = event.getX();
        this.y = event.getY();
        this.firstClick = true;
        this.r = 0;
        return true;
    }

    class MyThread extends Thread {
        private boolean running;

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            while(running) {
                Canvas canvas = getHolder().lockCanvas();
                canvas.drawColor(Color.BLUE);
                r += firstClick ? 5 : 0;
                canvas.drawCircle(x,y,r,paint);
                getHolder().unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
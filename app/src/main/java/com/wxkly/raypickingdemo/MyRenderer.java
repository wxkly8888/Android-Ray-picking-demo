package com.wxkly.raypickingdemo;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements Renderer {

	int viewWidth, viewHeight;

	private float[] mVMatrix = new float[16];
	private float[] mProjMatrix = new float[16];

	Cube[] mCube = new Cube[4];

	@Override
	public void onDrawFrame(GL10 unused) {

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT );

		for(int i = 0; i < mCube.length; i++) {
			mCube[i].draw(mProjMatrix, mVMatrix);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		viewWidth = width;
		viewHeight = height;
		
		GLES20.glViewport(0,0,width,height);
		float ratio = (float) width/height;
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 10);
		Matrix.setLookAtM(mVMatrix,0, 2,2,9, 0f,0f,0f, 0f,1.0f,0.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);

		mCube[0] = new Cube("red", new float[] {1, 0, 0, 1}, new float[] {0, 0, 0.5f});
		mCube[1] = new Cube("yellow", new float[] {1, 1, 0, 1}, new float[] {0.5f, 0.5f, 1f});
		mCube[2] = new Cube("green", new float[] {0, 1, 0, 1},  new float[] {-1.2f, 0, 0});
		mCube[3] = new Cube("blue", new float[] {0, 0, 1, 1}, new float[] {0.5f, -1f, 0});
	}

	public void handleTouch(float rx, float ry) {
		for(int i = 0; i < mCube.length; i++) {
			mCube[i].rayPicking(viewWidth, viewHeight, rx, ry, mVMatrix, mProjMatrix);
		}
	}
}
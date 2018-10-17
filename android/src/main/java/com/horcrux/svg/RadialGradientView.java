/*
 * Copyright (c) 2015-present, Horcrux.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */


package com.horcrux.svg;

import android.annotation.SuppressLint;
import android.graphics.Matrix;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.common.ReactConstants;

import javax.annotation.Nullable;

@SuppressLint("ViewConstructor")
class RadialGradientView extends DefinitionView {
    private String mFx;
    private String mFy;
    private String mRx;
    private String mRy;
    private String mCx;
    private String mCy;
    private ReadableArray mGradient;
    private Brush.BrushUnits mGradientUnits;

    private static final float[] sRawMatrix = new float[]{
        1, 0, 0,
        0, 1, 0,
        0, 0, 1
    };
    private Matrix mMatrix = null;

    public RadialGradientView(ReactContext reactContext) {
        super(reactContext);
    }

    @ReactProp(name = "fx")
    public void setFx(Dynamic fx) {
        mFx = getStringFromDynamic(fx);
        invalidate();
    }

    @ReactProp(name = "fy")
    public void setFy(Dynamic fy) {
        mFy = getStringFromDynamic(fy);
        invalidate();
    }

    @ReactProp(name = "rx")
    public void setRx(Dynamic rx) {
        mRx = getStringFromDynamic(rx);
        invalidate();
    }

    @ReactProp(name = "ry")
    public void setRy(Dynamic ry) {
        mRy = getStringFromDynamic(ry);
        invalidate();
    }

    @ReactProp(name = "cx")
    public void setCx(Dynamic cx) {
        mCx = getStringFromDynamic(cx);
        invalidate();
    }

    @ReactProp(name = "cy")
    public void setCy(Dynamic cy) {
        mCy = getStringFromDynamic(cy);
        invalidate();
    }

    @ReactProp(name = "gradient")
    public void setGradient(ReadableArray gradient) {
        mGradient = gradient;
        invalidate();
    }

    @ReactProp(name = "gradientUnits")
    public void setGradientUnits(int gradientUnits) {
        switch (gradientUnits) {
            case 0:
                mGradientUnits = Brush.BrushUnits.OBJECT_BOUNDING_BOX;
                break;
            case 1:
                mGradientUnits = Brush.BrushUnits.USER_SPACE_ON_USE;
                break;
        }
        invalidate();
    }

    @ReactProp(name = "gradientTransform")
    public void setGradientTransform(@Nullable ReadableArray matrixArray) {
        if (matrixArray != null) {
            int matrixSize = PropHelper.toMatrixData(matrixArray, sRawMatrix, mScale);
            if (matrixSize == 6) {
                if (mMatrix == null) {
                    mMatrix = new Matrix();
                }
                mMatrix.setValues(sRawMatrix);
            } else if (matrixSize != -1) {
                FLog.w(ReactConstants.TAG, "RNSVG: Transform matrices must be of size 6");
            }
        } else {
            mMatrix = null;
        }

        invalidate();
    }

    @Override
    void saveDefinition() {
        if (mName != null) {
            WritableArray points = Arguments.createArray();
            points.pushString(mFx);
            points.pushString(mFy);
            points.pushString(mRx);
            points.pushString(mRy);
            points.pushString(mCx);
            points.pushString(mCy);

            Brush brush = new Brush(Brush.BrushType.RADIAL_GRADIENT, points, mGradientUnits);
            brush.setGradientColors(mGradient);
            if (mMatrix != null) {
                brush.setGradientTransform(mMatrix);
            }

            SvgView svg = getSvgView();
            if (mGradientUnits == Brush.BrushUnits.USER_SPACE_ON_USE) {
                brush.setUserSpaceBoundingBox(svg.getCanvasBounds());
            }

            svg.defineBrush(brush, mName);
        }
    }
}

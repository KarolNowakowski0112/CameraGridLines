package com.example.cameragridlines;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

public class DrawableView extends View {
    public ShapeDrawable rec;

    public DrawableView(Context context) {
        super(context);

        int x = 0;
        int y = 0;
        int width = 100;
        int height = 100;

        rec = new ShapeDrawable(new RectShape());
        // If the color isn't set, the shape uses black as the default.
        rec.getPaint().setColor(0x233);
        // If the bounds aren't set, the shape can't be drawn.
        rec.setBounds(x, y, x + width, y + height);
    }

    protected void onDraw(Canvas canvas) {
        rec.draw(canvas);
    }
}


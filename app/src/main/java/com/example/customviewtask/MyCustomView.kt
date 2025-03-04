package com.example.customviewtask

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class MyCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val shapes = mutableListOf<Shape>()
    private val paint = Paint()
    private var defaultColor: Int = Color.GREEN
    private val colors: MutableList<Int> = mutableListOf()
    private val shapeTypes = listOf(ShapeType.CIRCLE, ShapeType.SQUARE, ShapeType.ROUNDED_SQUARE)

    private var shapeCounter = 0

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MyCustomView,
            0, 0
        ).apply {
            try {
                defaultColor = getColor(R.styleable.MyCustomView_defaultColor, Color.GREEN)
            } finally {
                recycle()
            }
        }
        paint.isAntiAlias = true
    }

    fun setColors(colors: List<Int>) {
        this.colors.clear()
        this.colors.addAll(colors)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (shape in shapes) {
            paint.color = shape.color
            when (shape.type) {
                ShapeType.CIRCLE -> {
                    canvas.drawCircle(shape.x, shape.y, shape.size, paint)
                }
                ShapeType.SQUARE -> {
                    canvas.drawRect(
                        shape.x - shape.size,
                        shape.y - shape.size,
                        shape.x + shape.size,
                        shape.y + shape.size,
                        paint
                    )
                }
                ShapeType.ROUNDED_SQUARE -> {
                    val rect = RectF(
                        shape.x - shape.size,
                        shape.y - shape.size,
                        shape.x + shape.size,
                        shape.y + shape.size
                    )
                    val cornerRadius = shape.size / 4
                    canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
                }
            }
        }
        paint.color = Color.BLACK
        paint.textSize = 50f
        canvas.drawText("Shapes: $shapeCounter", 20f, 50f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (shapeCounter >= 10) {
                shapes.clear()
                shapeCounter = 0
                Toast.makeText(context, "Game over", Toast.LENGTH_SHORT).show()
            } else {
                val x = event.x
                val y = event.y
                val size = (20..50).random().toFloat()
                val color = if (colors.isNotEmpty()) colors.random() else defaultColor
                val type = shapeTypes.random()
                shapes.add(Shape(x, y, size, color, type))
                shapeCounter++
            }
            invalidate()
        }
        return true
    }

    private data class Shape(
        val x: Float,
        val y: Float,
        val size: Float,
        val color: Int,
        val type: ShapeType
    )

    private enum class ShapeType {
        CIRCLE, SQUARE, ROUNDED_SQUARE
    }
}
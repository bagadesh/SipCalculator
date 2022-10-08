package com.bagadesh.sipcalculator.chart

import android.content.Context
import android.graphics.Color
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * Created by bagadesh on 26/07/22.
 */
class TwoDataDonutView constructor(
    context: Context
) : PieChart(context) {

    fun updateUI(
        firstPercentage: Int,
        secondPercentage: Int,
    ) {
        val entries = listOf(
            PieEntry(firstPercentage.toFloat(), "Invested Amount"),
            PieEntry(secondPercentage.toFloat(), "Interest Earned"),
        )
        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.colors = mutableListOf(Color.RED, Color.GREEN)
        pieDataSet.valueFormatter = object : ValueFormatter() {
            override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
                return super.getPieLabel(value, pieEntry).also {
                    println("$value, $pieEntry $it, Datamug")
                }
            }

            override fun getFormattedValue(value: Float): String {
                return super.getFormattedValue(value).also {
                    println("$it, Datamug1")
                }
            }
        }
        val pieData = PieData(pieDataSet)
        data = pieData
        setEntryLabelColor(Color.BLUE)
        invalidate()
    }

}
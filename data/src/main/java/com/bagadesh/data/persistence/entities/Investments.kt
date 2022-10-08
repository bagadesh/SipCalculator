package com.bagadesh.data.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by bagadesh on 02/09/22.
 */
@Entity(tableName = "investments")
data class Investments(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "investment_type") val investmentType: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "investment_details") val investmentDetails: Map<String, Any>,
)
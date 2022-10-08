@file:OptIn(ExperimentalAnimationApi::class)

package com.bagadesh.sipcalculator.savedResults.ui

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.entities.investment.InvestmentType.*
import com.bagadesh.domain.result.save.PersistenceInvestmentData
import com.bagadesh.sipcalculator.R
import com.bagadesh.sipcalculator.debug.BasePreviewUI
import com.bagadesh.sipcalculator.entities.states.UIState
import com.bagadesh.sipcalculator.savedResults.ui.result.OneTimeSavedResultDetailUI
import com.bagadesh.sipcalculator.savedResults.ui.result.SipSavedResultDetailUI
import com.bagadesh.sipcalculator.savedResults.ui.result.SipThenOneTimeResultDetailUI
import com.bagadesh.sipcalculator.savedResults.viewmodel.SavedResultsViewModel
import com.bagadesh.sipcalculator.ui.base.UIStatePark
import com.bagadesh.sipcalculator.ui.theme.SipCalculatorTheme

/**
 * Created by bagadesh on 02/09/22.
 */

val listOfColor = listOf(Color.Green, Color.Red, Color.Blue)
var index = 0
val frontalIconSize = 24.dp

fun provideColor(id: Int): Color {
    val value = id % 3
    return listOfColor[value]
}

@Composable
fun SavedResultsUI(
    viewModel: SavedResultsViewModel = hiltViewModel()
) {
    val results by viewModel.allSavedResults2.collectAsState(initial = UIState.Empty())
    val expandedItems by viewModel.expandedItems.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Saved results",
            fontSize = 20.sp,
            modifier = Modifier
        )
        UIStatePark(state = results) { listOfItems ->
            if (listOfItems.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_man),
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colors.primary
                    )
                    Text(text = "Nothing here", fontSize = 24.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = listOfItems,
                        key = {
                            it.id
                        }
                    ) {
                        ShowSavedResultsUI(
                            data = it,
                            id = it.id,
                            title = it.title,
                            description = buildString {
                                appendLine(it.investmentType.displayValue)
                            },
                            map = it.investmentDetails,
                            expanded = expandedItems.contains(it.id),
                            onClick = { id ->
                                viewModel.onExpandClick(id)
                            },
                            onDeleteItemClick = { persistenceInvestmentData ->
                                viewModel.onDeleteInvestment(investmentData = persistenceInvestmentData)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSavedResultsUI(
    data: PersistenceInvestmentData,
    id: String,
    title: String,
    description: String,
    map: Map<String, Any>,
    expanded: Boolean,
    onClick: (id: String) -> Unit,
    onDeleteItemClick: (data: PersistenceInvestmentData) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    if (openDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = {
                openDialog = false
            },
            onSaveClick = {
                onDeleteItemClick(data)
            }
        )
    }
    ConstraintLayout(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.onBackground.copy(alpha = .1f))
            .clickable {
                onClick(id)
            }
            .padding(10.dp)
    ) {
        val (first, second) = createRefs()
        FillColorBar(
            id = id,
            reference = first
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(start = 15.dp)
                .constrainAs(second) {
                    top.linkTo(parent.top)
                    start.linkTo(first.end)
                },
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = description,
                        fontSize = 13.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                IconButton(onClick = {
                    openDialog = true
                }, modifier = Modifier.padding(top = 7.dp, end = 5.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24), modifier = Modifier

                            .size(32.dp)
                            .align(Alignment.CenterVertically), contentDescription = "Delete item"
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .weight(.3f)
                        .padding(top = 10.dp)
                ) {
                    when (data.investmentType) {
                        ONE_TIME -> OneTimeSavedResultDetailUI(map)
                        SIP -> SipSavedResultDetailUI(map)
                        SIP_THEN_ONE_TIME -> SipThenOneTimeResultDetailUI(map)
                        else -> {}
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun DeleteConfirmationDialogPreview() {
    BasePreviewUI(hideColorBar = true) {
        DeleteConfirmationDialog({}) {}
    }
}

@Composable
fun DeleteConfirmationDialog(onDismissRequest: () -> Unit, onSaveClick: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            contentColor = Color.White
        ) {
            Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_warning_alert),
                        contentDescription = "Alert Icon",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(text = "Are you sure you want to delete?", modifier = Modifier.align(Alignment.CenterHorizontally))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                ) {
                    DeleteDialogButton(
                        text = "cancel",
                        textColor = Color.Gray,
                        buttonBackgroundColor = Color(0xFFEEF1F7),
                        onClick = onDismissRequest
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    DeleteDialogButton(
                        text = "confirm",
                        onClick = {
                            onSaveClick()
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.DeleteDialogButton(
    text: String,
    textColor: Color = MaterialTheme.colors.surface,
    buttonBackgroundColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, modifier = Modifier
            .weight(1f)
            .padding(0.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonBackgroundColor
        )
    ) {
        Text(text = text, color = textColor)
    }
}

@Composable
fun ConstraintLayoutScope.FillColorBar(id: String, reference: ConstrainedLayoutReference) {
    Box(
        modifier = Modifier
            .padding(start = 5.dp)
            .width(5.dp)
            .fillMaxHeight()
            .background(provideColor(id.toInt()).copy(alpha = 0.9f))
            .constrainAs(reference) {
                height = Dimension.fillToConstraints
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
    )
}
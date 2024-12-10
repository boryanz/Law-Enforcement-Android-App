package com.boryanz.upszakoni.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.theme.KataSampleAppTheme
import com.boryanz.upszakoni.ui.theme.Typography

@Composable
fun IconWithTitleItem(
    title: String,
    @DrawableRes drawableRes: Int,
    onClick: () -> Unit,
) {
    RowItem(onClick = onClick) {
        Icon(
            modifier = Modifier
                .height(30.dp)
                .width(30.dp),
            painter = painterResource(drawableRes),
            contentDescription = null
        )
        Spacer.Horizontal(8.dp)
        Text(text = title, style = Typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardItemPreview() {
    KataSampleAppTheme {
        IconWithTitleItem(
            title = "Law",
            drawableRes = R.drawable.zakonishta,
            onClick = {},
        )
    }
}
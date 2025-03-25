package com.boryanz.upszakoni.ui.screens.partners

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.boryanz.upszakoni.R
import com.boryanz.upszakoni.ui.components.Spacer
import com.boryanz.upszakoni.ui.theme.BaseContent
import com.boryanz.upszakoni.ui.theme.UpsTheme

@Composable
fun PartnerItem(
    partnerName: String,
    @DrawableRes iconRes: Int,
    onClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .border(width = 0.3.dp, shape = RoundedCornerShape(8.dp), color = BaseContent)
            .padding(12.dp)
            .clickable { onClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp)),
            painter = painterResource(iconRes),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer.Horizontal(12.dp)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = partnerName,
            fontWeight = FontWeight.Bold,
        )
    }
}

@PreviewLightDark
@Preview(showBackground = true)
@Composable
private fun PartnerItemPreview() {
    UpsTheme {
        PartnerItem(
            partnerName = "Синдикат на полиција во Македонија",
            iconRes = R.drawable.fb_logo
        ) { }
    }
}
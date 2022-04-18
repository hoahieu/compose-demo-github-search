package com.hieulh.test.demoproj2.ui.app.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.hieulh.test.demoproj2.R
import com.hieulh.test.demoproj2.ui.pojo.SearchUserUIModel
import com.hieulh.test.demoproj2.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ItemGithubUser(userUIModel: SearchUserUIModel, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .wrapContentHeight()
            .clickable { onItemClick(userUIModel.username) }
    ) {
        GlideImage(
            imageModel = userUIModel.avatarUrl,
            contentScale = ContentScale.Crop,
            placeHolder = ImageBitmap.imageResource(R.drawable.ic_image_placeholder_loading),
            error = ImageBitmap.imageResource(R.drawable.ic_image_placeholder_error),
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(56.dp),
            Arrangement.Center
        ) {
            Text(
                text = userUIModel.username,
                modifier = Modifier.fillMaxWidth(),
                style = Typography.body1
            )
        }

    }
}
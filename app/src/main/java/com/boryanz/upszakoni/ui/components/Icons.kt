package com.boryanz.upszakoni.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.boryanz.upszakoni.ui.theme.Base100

object Icons {

  @Composable
  fun Save(
    tint: Color = Base100,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
  ) {
    Base(
      imageVector = Icons.Outlined.Check,
      tint = tint,
      onClick = onClick,
      isEnabled = isEnabled
    )
  }

  @Composable
  fun Edit(
    tint: Color = Base100,
    onClick: () -> Unit,
  ) {
    Base(
      imageVector = Icons.Outlined.Edit,
      tint = tint,
      onClick = onClick
    )
  }

  @Composable
  fun Share(
    tint: Color = Base100,
    onClick: () -> Unit,
  ) {
    Base(
      imageVector = Icons.Outlined.Share,
      tint = tint,
      onClick = onClick
    )
  }

  @Composable
  fun Back(
    tint: Color = Base100,
    contentDescription: String = "back_button",
    onClick: () -> Unit,
  ) {
    Base(
      imageVector = Icons.AutoMirrored.Filled.ArrowBack,
      contentDescription = contentDescription,
      tint = tint,
      onClick = onClick
    )
  }

  @Composable
  fun Archive(
    tint: Color = Base100,
    onClick: () -> Unit
  ) {
    Base(
      imageVector = Icons.Default.Archive,
      tint = tint,
      onClick = onClick
    )
  }

  @Composable
  fun Delete(
    modifier: Modifier = Modifier,
    tint: Color = Base100,
    onClick: () -> Unit
  ) {
    Base(
      modifier = modifier,
      imageVector = Icons.Default.DeleteOutline,
      tint = tint,
      onClick = onClick
    )
  }

  @Composable
  fun Undo(
    modifier: Modifier = Modifier,
    tint: Color = Base100,
    onClick: () -> Unit
  ) {
    Base(
      modifier = modifier,
      imageVector = Icons.AutoMirrored.Filled.Undo,
      tint = tint,
      onClick = onClick
    )
  }

  @Composable
  fun Base(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String = "base icon",
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
  ) {
    IconButton(
      modifier = modifier,
      onClick = onClick,
      enabled = isEnabled,
      content = {
        Icon(
          imageVector = imageVector,
          tint = tint,
          contentDescription = contentDescription,
        )
      })
  }
}
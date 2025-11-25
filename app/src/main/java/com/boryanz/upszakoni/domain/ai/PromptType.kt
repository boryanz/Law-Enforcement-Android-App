package com.boryanz.upszakoni.domain.ai

import com.boryanz.upszakoni.ui.screens.ai.COMPLAINT_PROMPT
import com.boryanz.upszakoni.ui.screens.ai.PUBLIC_ORDER_AND_PEACE_PROMPT
import com.boryanz.upszakoni.ui.screens.ai.SECURING_CRIME_SCENE_PROMPT

enum class PromptType(val title: String, val prompt: String) {
  COMPLAINT(title = "Поплака", prompt = COMPLAINT_PROMPT),
  PUBLIC_ORDER_AND_PEACE(title = "ЈРМ", prompt = PUBLIC_ORDER_AND_PEACE_PROMPT),
  SECURING_CRIME_SCENE(title = "Обезб. настан", prompt = SECURING_CRIME_SCENE_PROMPT),
}

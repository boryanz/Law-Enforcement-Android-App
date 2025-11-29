package com.boryanz.upszakoni.ai

import com.boryanz.upszakoni.MainDispatcherRule
import com.boryanz.upszakoni.domain.ai.PromptType
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiEvent
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptUiState
import com.boryanz.upszakoni.ui.screens.ai.addprompt.AddPromptViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class AddPromptViewModelTest {


  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun `when ai prompt is changed update the state with a new value`() = runTest {
    val intial = AddPromptUiState(
      prompt = "",
      examplePrompt = PromptType.COMPLAINT.prompt,
      hasPromptError = false,
    )
    val viewModel = AddPromptViewModel()
    viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("Ul. "))
    assertEquals(intial.copy(prompt = "Ul. "), viewModel.uiState.value)
    viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("Ul. Hristo"))
    assertEquals(intial.copy(prompt = "Ul. Hristo"), viewModel.uiState.value)
  }

  @Test
  fun `when ai prompt is blank update the ui state with error`() = runTest {
    val intial = AddPromptUiState(
      prompt = "",
      examplePrompt = PromptType.COMPLAINT.prompt,
      hasPromptError = false,
    )
    val viewModel = AddPromptViewModel()
    viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("  "))
    assertEquals(intial.copy(prompt = "  ", hasPromptError = true), viewModel.uiState.value)
  }

  @Test
  fun `when ai prompt contains EMBG then update ui state with error`() = runTest {
    val intial = AddPromptUiState(
      prompt = "",
      examplePrompt = PromptType.COMPLAINT.prompt,
      hasPromptError = false,
    )
    val viewModel = AddPromptViewModel()
    viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("Liceto Petko Petkovski so EMBG 1904995450079 bese legitimirano"))
    assertEquals(
      intial.copy(
        prompt = "Liceto Petko Petkovski so EMBG 1904995450079 bese legitimirano",
        hasPromptError = true
      ), viewModel.uiState.value
    )
  }

  @Test
  fun `when ai prompt contains ID number then update ui state with error`() = runTest {
    val intial = AddPromptUiState(
      prompt = "",
      examplePrompt = PromptType.COMPLAINT.prompt,
      hasPromptError = false,
    )
    val viewModel = AddPromptViewModel()
    viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("Liceto Petko Petkovski so Licna karta N1234567 bese legitimirano"))
    assertEquals(
      intial.copy(
        prompt = "Liceto Petko Petkovski so Licna karta N1234567 bese legitimirano",
        hasPromptError = true
      ), viewModel.uiState.value
    )
  }

  @Ignore("Add this validation in later phase since it's not that critical data")
  @Test
  fun `when ai prompt contains Macedonian mobile phone number then update ui state with error`() =
    runTest {
      val intial = AddPromptUiState(
        prompt = "",
        examplePrompt = PromptType.COMPLAINT.prompt,
        hasPromptError = false,
      )
      val viewModel = AddPromptViewModel()
      viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("Liceto Petko Petkovski so broj na telefon +38971203123 bese legitimirano"))
      assertEquals(
        intial.copy(
          prompt = "Liceto Petko Petkovski so broj na telefon +38971203123 bese legitimirano",
          hasPromptError = true
        ), viewModel.uiState.value
      )
    }

  @Ignore("Add this validation in later phase since it's not that critical data")
  @Test
  fun `when ai prompt contains Macedonian mobile phone number with spaces then update ui state with error`() =
    runTest {
      val intial = AddPromptUiState(
        prompt = "",
        examplePrompt = PromptType.COMPLAINT.prompt,
        hasPromptError = false,
      )
      val viewModel = AddPromptViewModel()
      viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("Liceto Petko Petkovski so broj na telefon +389 71 123 456 bese legitimirano"))
      assertEquals(
        intial.copy(
          prompt = "Liceto Petko Petkovski so broj na telefon +389 71 123 456 bese legitimirano",
          hasPromptError = true
        ), viewModel.uiState.value
      )
    }

  @Ignore("Add this validation in later phase since it's not that critical data")
  @Test
  fun `when ai prompt contains Macedonian mobile phone number with dashes then update ui state with error`() =
    runTest {
      val intial = AddPromptUiState(
        prompt = "",
        examplePrompt = PromptType.COMPLAINT.prompt,
        hasPromptError = false,
      )
      val viewModel = AddPromptViewModel()
      viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("Liceto Petko Petkovski so broj na telefon 071/123/456 bese legitimirano"))
      assertEquals(
        intial.copy(
          prompt = "Liceto Petko Petkovski so broj na telefon +389 71 123 456 bese legitimirano",
          hasPromptError = true
        ), viewModel.uiState.value
      )
    }

  @Ignore("Add this validation in later phase since it's not that critical data")
  @Test
  fun `when ai prompt contains Macedonian mobile phone number with hyphens then update ui state with error`() =
    runTest {
      val intial = AddPromptUiState(
        prompt = "",
        examplePrompt = PromptType.COMPLAINT.prompt,
        hasPromptError = false,
      )
      val viewModel = AddPromptViewModel()
      viewModel.onUiEvent(AddPromptUiEvent.PromptChanged("Liceto Petko Petkovski so broj na telefon 071-123-456 bese legitimirano"))
      assertEquals(
        intial.copy(
          prompt = "Liceto Petko Petkovski so broj na telefon +389 71 123 456 bese legitimirano",
          hasPromptError = true
        ), viewModel.uiState.value
      )
    }
}
package com.boryanz.upszakoni.ui.screens.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AddPromptUiEvent {
  data class PromptChanged(val value: String) : AddPromptUiEvent
  data object GenerateClicked : AddPromptUiEvent
}

sealed interface AddPromptEvent {
  data class DocumentGenerated(val document: String) : AddPromptEvent
}

data class AddPromptUiState(
  val prompt: String = "",
  val isGenerating: Boolean = false,
  val hasPromptError: Boolean = false,
)

class AddPromptViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(AddPromptUiState())
  val uiState = _uiState.asStateFlow()

  private val _event = MutableSharedFlow<AddPromptEvent>()
  val event = _event.asSharedFlow()

  fun onUiEvent(event: AddPromptUiEvent) {
    when (event) {
      is AddPromptUiEvent.PromptChanged -> {
        _uiState.update {
          it.copy(
            prompt = event.value,
            hasPromptError = event.value.isBlank()
          )
        }
      }

      AddPromptUiEvent.GenerateClicked -> {
        if (_uiState.value.prompt.isNotBlank()) {
          generateDocument(_uiState.value.prompt)
        }
      }
    }
  }

  private fun generateDocument(userPrompt: String) = viewModelScope.launch(Dispatchers.IO) {
    _uiState.update { it.copy(isGenerating = true) }

    try {
      val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash-lite")

      val systemPrompt = """
        Ти си искусен полициски слубежник кој што пишува службени белешки. Твоја должност е да напишеш ПРАВНО ОДРЖАНИ И СУДСКО ОДБРАНЛИВИ белешки кои ќе ја заштитат полицискиот слубежник во случај на судска процедура.

        На основа на описот кој што ќе го дадам, напиши официјална службена белешка со употреба на полицииска терминлогоија во Северна Македонија.
        Белешката ќе биде подеднакво професионална и СУДСКО ОДБРАНЛИВА.

        Белешката треба да биде фокусирана на правни аспекти и примената на релевантни закони/наредби.
        НАПОМЕНА: Не пишувај никаков вовед пред белешката. Само дади ми ги верзиите во формат дефиниран подолу.
        
        Белешката треба да одговори на следните прашања: 
        1. Кој ме испратил? (Пример: По претходна пријава за нарушување на ЈРМ бев известен од (дежурна служба или ЦИК) - зависи кој известил.)
        2. Што се случило на настанот?
        3. Каде се случил настанот?
        4. Како се случил настанот?
        5. Зошто се случил настанот?
        6. Со кого се случил - учесници?
        7. Превземени мерки од моја страна и кој сум известил по настанот.
        
        НАЈВАЖНО: Белешката НЕ треба да опфаќа САМО што е напишано од полицискиот службеник, туку мора да наведе и што БИ ТРЕБАЛО да е направено во текот на настанот. 
        Пример: Полицскио службеник не навел дека извршил проверка за лице, а напишал дека го легитимирал по лична карта. Белешката мора да содржи и дел дека лицето било проверено во систем. 
        На овој начин ќе се покријат сите пропусти на полицискиот службеник.

        ФОРМАТ:
        Белешка:
        [белешка овде]
        
        Препорачана постапка на настанот:
        [Во прво лице, обраќајки му се, по точки што полицскиот службеник требало да превземе на опишаниот настан согласно негоците полицски овластувања. Точките треба да бидат по една до максимум 2 реченици.]

        ЛИЧНОСТ ЗАШТИТА: Ако детектираш какво било личен податок (име, презиме, адреса, телефон, имејл, број на ДЛК, датум на раѓање, регистарска табла, итн.), замени го со _______.

        ФОРМАТ НА ТЕКСТ: Секоја верзија треба да биде структурирана како есеј со кратки, логични параграфи. Без метаглави, точки, звездички или друго форматирање. Чист, стандарден текст кој е подготвен за судска употреба.

        Описот од полицискиот слубежник:
        $userPrompt
      """.trimIndent()

      val response = model.generateContent(systemPrompt)
      val generatedDocument = response.text.orEmpty()

      _uiState.update { it.copy(isGenerating = false) }
      _event.emit(AddPromptEvent.DocumentGenerated(generatedDocument))
    } catch (e: Exception) {
      _uiState.update { it.copy(isGenerating = false) }
    }
  }
}

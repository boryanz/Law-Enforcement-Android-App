sealed class Build {
    open val isMinifyEnabled = false
    open val isDebuggable = false
    open val applicationIdSuffix = ""

     object Debug : Build() {
        override val isDebuggable = true
        override val isMinifyEnabled = false
    }

     object Release : Build() {
        override val applicationIdSuffix = ".prod"
        override val isDebuggable = false
        override val isMinifyEnabled = true
    }
}
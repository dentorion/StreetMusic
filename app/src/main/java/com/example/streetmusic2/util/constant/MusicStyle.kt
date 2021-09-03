package com.example.streetmusic2.util.constant

sealed class MusicStyle(val styleName: String) {
    class Rock(styleName: String = "rock") : MusicStyle(styleName)
    class Classic(styleName: String = "classic") : MusicStyle(styleName)
    class Dancing(styleName: String = "dancing") : MusicStyle(styleName)
    class Pop(styleName: String = "pop") : MusicStyle(styleName)
    class Vocal(styleName: String = "vocal") : MusicStyle(styleName)
}

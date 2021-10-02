package com.example.streetmusic2.common.model.music

import com.example.streetmusic2.R
import com.example.streetmusic2.util.constant.*

sealed class MusicStyle(val styleName: String, val icon: Int) {
    class Rock(styleName: String = ROCK, icon: Int = R.drawable.ic_rock_music) :
        MusicStyle(styleName, icon)

    class Classic(styleName: String = CLASSIC, icon: Int = R.drawable.ic_classic_music) :
        MusicStyle(styleName, icon)

    class Dancing(styleName: String = DANCING, icon: Int = R.drawable.ic_dancing_music) :
        MusicStyle(styleName, icon)

    class Pop(styleName: String = POP, icon: Int = R.drawable.ic_pop_music) :
        MusicStyle(styleName, icon)

    class Vocal(styleName: String = VOCAL, icon: Int = R.drawable.ic_vocal_music) :
        MusicStyle(styleName, icon)

    object None : MusicStyle("", 0)
}

fun convertToMusicStyle(styleNameString: String): MusicStyle {
    return when (styleNameString) {
        ROCK -> MusicStyle.Rock()
        CLASSIC -> MusicStyle.Classic()
        DANCING -> MusicStyle.Dancing()
        POP -> MusicStyle.Pop()
        VOCAL -> MusicStyle.Vocal()
        else -> MusicStyle.None
    }
}

fun convertFromMusicStyle(musicStyle: MusicStyle): String {
    return when (musicStyle) {
        is MusicStyle.Rock -> {
            "rock"
        }
        is MusicStyle.Classic -> {
            "classic"
        }
        is MusicStyle.Dancing -> {
            "dancing"
        }
        is MusicStyle.Pop -> {
            "pop"
        }
        is MusicStyle.Vocal -> {
            "vocal"
        }
        else -> {
            ""
        }
    }
}
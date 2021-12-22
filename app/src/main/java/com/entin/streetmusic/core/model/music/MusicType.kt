package com.entin.streetmusic.core.model.music

import com.entin.streetmusic.R
import com.entin.streetmusic.core.constants.*

sealed class MusicType(val styleName: String, val icon: Int) {
    class Rock(styleName: String = ROCK, icon: Int = R.drawable.ic_rock_music) :
        MusicType(styleName, icon)

    class Classic(styleName: String = CLASSIC, icon: Int = R.drawable.ic_classic_music) :
        MusicType(styleName, icon)

    class Dancing(styleName: String = DANCING, icon: Int = R.drawable.ic_dancing_music) :
        MusicType(styleName, icon)

    class Pop(styleName: String = POP, icon: Int = R.drawable.ic_pop_music) :
        MusicType(styleName, icon)

    class Vocal(styleName: String = VOCAL, icon: Int = R.drawable.ic_vocal_music) :
        MusicType(styleName, icon)

    object None : MusicType("", 0)
}

fun convertToMusicType(styleNameString: String): MusicType {
    return when (styleNameString) {
        ROCK -> MusicType.Rock()
        CLASSIC -> MusicType.Classic()
        DANCING -> MusicType.Dancing()
        POP -> MusicType.Pop()
        VOCAL -> MusicType.Vocal()
        else -> MusicType.None
    }
}
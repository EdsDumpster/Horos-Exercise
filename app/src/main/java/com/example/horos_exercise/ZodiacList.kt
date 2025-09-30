package com.example.horos_exercise

data class ZodiacList(
    val id: String,
    val name: Int,
    val dates: Int,
    val icon: Int
) {
    companion object {
        lateinit var horoscopeList: List<ZodiacList>

        fun getAll() : List<ZodiacList> {
            return listOf(
                ZodiacList("aquarius", R.string.signname_aquarius, R.string.signdate_aquarius, R.drawable.aquarius_icon),
                ZodiacList("pisces", R.string.signname_pisces, R.string.signdate_pisces, R.drawable.pisces_icon),
                ZodiacList("aries", R.string.signname_aries, R.string.signdate_aries, R.drawable.aries_icon),
                ZodiacList("taurus", R.string.signname_taurus, R.string.signdate_taurus, R.drawable.taurus_icon),
                ZodiacList("gemini", R.string.signname_gemini, R.string.signdate_gemini, R.drawable.gemini_icon),
                ZodiacList("cancer", R.string.signname_cancer, R.string.signdate_cancer, R.drawable.cancer_icon),
                ZodiacList("leo", R.string.signname_leo, R.string.signdate_leo, R.drawable.leo_icon),
                ZodiacList("virgo", R.string.signname_virgo, R.string.signdate_virgo, R.drawable.virgo_icon),
                ZodiacList("libra", R.string.signname_libra, R.string.signdate_libra, R.drawable.libra_icon),
                ZodiacList("scorpio", R.string.signname_scorpio, R.string.signdate_scorpio, R.drawable.scorpio_icon),
                ZodiacList("sagittarius", R.string.signname_sagittarius, R.string.signdate_sagittarius, R.drawable.sagittarius_icon),
                ZodiacList("capricorn", R.string.signname_capricorn, R.string.signdate_capricorn, R.drawable.capricorn_icon),

                )
        }
        fun getById(id: String) : ZodiacList {
            return getAll().first { it.id == id }
        }
    }
}
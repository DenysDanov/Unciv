package com.unciv.app.desktop

import kotlin.math.log10

class Combatant(val name: String, var maxHealth: Int, val abilities: ArrayList<Ability>){
    var health = maxHealth
}

fun getMagicMissile() = Ability("Magic Missile", arrayListOf("Mana Shaping"), listOf("Damage=5") )

data class Ability(val name:String, val experienceClasses: ArrayList<String>, val parameters:List<String>) {
    var experience = 10

    init {
        experienceClasses.add(name)
    }

    fun calculateExpBonus(player: Combatant): Double {
        var expBonus = 1.0
        for (ability in player.abilities)
            for (experienceClass in ability.experienceClasses)
                if (experienceClass in experienceClasses)
                    expBonus *= log10(ability.experience.toDouble())
        return expBonus
    }

    fun isAttack() = parameters.any { it.startsWith("Damage=") }
    private fun getBaseDamage() = parameters.first { it.startsWith("Damage=") }.removePrefix("Damage=").toInt()

    fun calculateDamage(player: Combatant): Int {
        return (getBaseDamage() * calculateExpBonus(player)).toInt()
    }
}
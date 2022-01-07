package com.codingwithmitch.hero_datasource_test.cache

import com.codingwithmitch.hero_datasource.cache.HeroCache
import com.codingwithmitch.hero_domain.Hero
import com.codingwithmitch.hero_domain.HeroRole

class HeroCacheFake(
    private val db: HeroDatabaseFake
): HeroCache {
    override suspend fun getHero(id: Int): Hero? {
        return db.heroes.find { it.id == id }
    }

    override suspend fun removeHero(id: Int) {
        db.heroes.removeIf { it.id == id }
    }

    override suspend fun selectAll(): List<Hero> {
        return db.heroes
    }

    override suspend fun insert(hero: Hero) {
        if(db.heroes.isNotEmpty()){
            var didInsert = false
            for(h in db.heroes){
                if(h.id == hero.id){
                    db.heroes.remove(h)
                    db.heroes.add(hero)
                    didInsert = true
                    break
                }
            }
            if(!didInsert){
                db.heroes.add(hero)
            }
        }
        else{
            db.heroes.add(hero)
        }
    }

    override suspend fun insert(heroes: List<Hero>) {
        if(db.heroes.isNotEmpty()){
            for(hero in heroes){
                if(db.heroes.contains(hero)){
                    db.heroes.remove(hero)
                    db.heroes.add(hero)
                }
            }
        }
        else{
            db.heroes.addAll(heroes)
        }
    }

    override suspend fun searchByName(localizedName: String): List<Hero> {
        return db.heroes.find { it.localizedName == localizedName }?.let {
            listOf(it)
        }?: listOf()
    }

    override suspend fun searchByAttr(primaryAttr: String): List<Hero> {
        return db.heroes.filter { it.primaryAttribute.uiValue == primaryAttr }
    }

    override suspend fun searchByAttackType(attackType: String): List<Hero> {
        return db.heroes.filter { it.attackType.uiValue == attackType }
    }

    override suspend fun searchByRole(
        carry: Boolean,
        escape: Boolean,
        nuker: Boolean,
        initiator: Boolean,
        durable: Boolean,
        disabler: Boolean,
        jungler: Boolean,
        support: Boolean,
        pusher: Boolean
    ): List<Hero> {
        val heroes: MutableList<Hero> = mutableListOf()
        if(carry){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Carry) })
        }
        if(escape){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Escape) })
        }
        if(nuker){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Nuker) })
        }
        if(initiator){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Initiator) })
        }
        if(durable){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Durable) })
        }
        if(disabler){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Disabler) })
        }
        if(jungler){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Jungler) })
        }
        if(support){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Support) })
        }
        if(pusher){
            heroes.addAll(db.heroes.filter { it.roles.contains(HeroRole.Pusher) })
        }
        return heroes.distinctBy { it.id }
    }
}
package com.example.projetandroid.entities

class Categorie {
    private var name: String? = null
    private var state: String? = null
    private var path: String? = null
    private var count: Int = 0
    private var image: String? = null

    companion object {
        fun getImageFromItem(categorieList: List<Categorie>, item: Item): String? {
            for (categorie in categorieList) {
                if (categorie.getName() != null && categorie.getName() == item.getFields()!!.getCategorie_activite())
                    return categorie.getImage()
            }
            return null
        }
    }

    constructor(name: String, state: String, path: String, count: Int) {
        this.name = name
        this.state = state
        this.path = path
        this.count = count
        this.image = ""
    }

    constructor(categorie: Categorie) {
        this.name = categorie.getName()
        this.state = categorie.getState()
        this.path = categorie.getPath()
        this.count = categorie.getCount()
        this.image = ""
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getState(): String? {
        return state
    }

    fun setState(state: String) {
        this.state = state
    }

    fun getPath(): String? {
        return path
    }

    fun setPath(path: String) {
        this.path = path
    }

    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String) {
        this.image = image
    }
}
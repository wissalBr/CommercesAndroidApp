package com.example.projetandroid.entities

class Item {
    private var datasetid: String? = null
    private var recordid: String? = null
    private var record_timestamp: String? = null
    private var fields: Field? = null
    private var geometry: Geometry? = null

    companion object {
        fun getItem(itemList: List<Item>, id_carto: String): Item? {
            for (item in itemList) {
                if (item.getFields()!!.getId_carto() != null && item.getFields()!!.getId_carto() == id_carto)
                    return item
            }
            return null
        }

        fun searchItems(itemList: List<Item>, id_carto: String): ArrayList<Item> {
            val searchedItems = ArrayList<Item>()
            for (item in itemList) {
                if (item.getFields()!!.getId_carto() != null && item.getFields()!!.getId_carto()!!.contains(
                        id_carto
                    )
                )
                    searchedItems.add(item)
            }
            return searchedItems
        }
    }

    constructor(
        datasetid: String,
        recordid: String,
        record_timestamp: String,
        fields: Field,
        geometry: Geometry
    ) {
        this.datasetid = datasetid
        this.recordid = recordid
        this.record_timestamp = record_timestamp
        this.fields = Field(fields)
        this.geometry = Geometry(geometry)
    }

    constructor(item: Item) {
        this.datasetid = item.getDatasetid()
        this.recordid = item.getRecordid()
        this.record_timestamp = item.getRecord_timestamp()
        this.fields = Field(item.getFields()!!)
        this.geometry = Geometry(item.getGeometry()!!)
    }

    fun getDatasetid(): String? {
        return datasetid
    }

    fun setDatasetid(datasetid: String) {
        this.datasetid = datasetid
    }

    fun getRecordid(): String? {
        return recordid
    }

    fun setRecordid(recordid: String) {
        this.recordid = recordid
    }

    fun getRecord_timestamp(): String? {
        return record_timestamp
    }

    fun setRecord_timestamp(record_timestamp: String) {
        this.record_timestamp = record_timestamp
    }

    fun getFields(): Field? {
        return fields
    }

    fun setFields(fields: Field) {
        this.fields = fields
    }

    fun getGeometry(): Geometry? {
        return geometry
    }

    fun setGeometry(geometry: Geometry) {
        this.geometry = geometry
    }

    override fun toString(): String {
        return "Item: " + this.recordid!!
    }

    inner class Field {
        private var ville: String? = null
        private var id_carto: String? = null
        private var activite_precise_du_locataire: String? = null
        private var adresse: String? = null
        private var categorie_activite: String? = null
        private var operation: String? = null
        private var cp: Int = 0
        private var lat_ok: Double? = null
        private var long_ok: Double? = null
        private var enseigne: String? = null
        private var xy: Array<Double>? = null

        constructor(
            ville: String,
            id_carto: String,
            activite_precise_du_locataire: String,
            adresse: String,
            categorie_activite: String,
            operation: String,
            cp: Int,
            lat_ok: Double?,
            long_ok: Double?,
            enseigne: String,
            xy: Array<Double>
        ) {
            this.ville = ville
            this.id_carto = id_carto
            this.activite_precise_du_locataire = activite_precise_du_locataire
            this.adresse = adresse
            this.categorie_activite = categorie_activite
            this.operation = operation
            this.cp = cp
            this.lat_ok = lat_ok
            this.long_ok = long_ok
            this.enseigne = enseigne
            this.xy = xy
        }

        constructor(fields: Field) {
            this.ville = fields.ville
            this.id_carto = fields.id_carto
            this.activite_precise_du_locataire = fields.activite_precise_du_locataire
            this.adresse = fields.adresse
            this.categorie_activite = fields.categorie_activite
            this.operation = fields.operation
            this.cp = fields.cp
            this.lat_ok = fields.lat_ok
            this.long_ok = fields.long_ok
            this.enseigne = fields.enseigne
            this.xy = fields.xy
        }

        public fun getVille(): String? {
            return this.ville
        }

        public fun setVille(ville: String) {
            this.ville = ville
        }

        public fun getId_carto(): String? {
            return id_carto
        }

        public fun setId_carto(id_carto: String) {
            this.id_carto = id_carto
        }

        public fun getActivite_precise_du_locataire(): String? {
            return if (activite_precise_du_locataire != null)
                activite_precise_du_locataire
            else
                categorie_activite
        }

        public fun setActivite_precise_du_locataire(activite_precise_du_locataire: String) {
            this.activite_precise_du_locataire = activite_precise_du_locataire
        }

        public fun getAdresse(): String? {
            return adresse
        }

        public fun setAdresse(adresse: String) {
            this.adresse = adresse
        }

        public fun getCategorie_activite(): String? {
            return categorie_activite
        }

        public fun setCategorie_activite(categorie_activite: String) {
            this.categorie_activite = categorie_activite
        }

        public fun getOperation(): String? {
            return operation
        }

        public fun setOperation(operation: String) {
            this.operation = operation
        }

        public fun getCp(): Int {
            return cp
        }

        public fun setCp(cp: Int) {
            this.cp = cp
        }

        public fun getLat_ok(): Double? {
            return lat_ok
        }

        public fun setLat_ok(lat_ok: Double?) {
            this.lat_ok = lat_ok
        }

        public fun getLong_ok(): Double? {
            return long_ok
        }

        public fun setLong_ok(long_ok: Double?) {
            this.long_ok = long_ok
        }

        public fun getEnseigne(): String? {
            return enseigne
        }

        public fun setEnseigne(enseigne: String) {
            this.enseigne = enseigne
        }

        public fun getXy(): Array<Double>? {
            return xy
        }

        public fun setXy(xy: Array<Double>) {
            this.xy = xy
        }
    }

    inner class Geometry {
        var ville: String? = null
        var coordinates: Array<Double>? = null

        constructor(ville: String, coordinates: Array<Double>) {
            this.ville = ville
            this.coordinates = coordinates
        }

        constructor(geometry: Geometry) {
            this.ville = geometry.ville
            this.coordinates = geometry.coordinates
        }
    }
}

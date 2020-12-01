package ru.example.libdatabase.sqlite

class WorkEntity() {
    companion object {
        internal const val TABLE_NAME = "work"
        internal const val COLUMN__ID = "_id"
        internal const val COLUMN_TITLE = "title"
        internal const val COLUMN_DESCRIPTION = "description"
    }

    constructor(_id: Long = 0L, _title: String = "", _description: String = "") : this() {
        this.id = _id
        this.title = _title
        this.description = _description
    }

    var id: Long = 0L
    var title: String = ""
    var description: String = ""

    override fun equals(other: Any?): Boolean {
        return other is WorkEntity &&
                other.id == id &&
                other.title == title &&
                other.description == description
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + id.hashCode()
        result = prime * result + title.hashCode()
        result = prime * result + description.hashCode()
        return result
    }
}

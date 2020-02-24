package com.astashin.marvelcomics

class Date(val year: Int, val month: Int, val day: Int) {

    override fun toString(): String {
        return "$day.${month + 1}.$year"
    }
}
package ru.mikhailskiy.intensiv.data.mapper

interface BaseMapper<D, V> {
    fun map(d: D): V
}
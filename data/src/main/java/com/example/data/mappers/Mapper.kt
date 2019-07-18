package com.example.data.mappers

interface Mapper<in E, T> {
    fun mapFrom(from: E): T
}
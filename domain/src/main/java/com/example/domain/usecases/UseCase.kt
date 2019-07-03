package com.example.domain.usecases

interface UseCase <P, R> {
    suspend operator fun invoke(param: P): R
}
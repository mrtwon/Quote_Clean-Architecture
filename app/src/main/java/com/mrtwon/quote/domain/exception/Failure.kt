package com.mrtwon.quote.domain.exception

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object ReadError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
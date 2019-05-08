package click.jaromil.goodrequest.util

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

fun <T>zipWithTimer(single: Single<T>): Single<T> = Single.zip(single, Single.timer(1000, TimeUnit.MILLISECONDS), BiFunction{ t, _ -> t})
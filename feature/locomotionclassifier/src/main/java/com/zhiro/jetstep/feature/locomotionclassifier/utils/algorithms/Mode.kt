package com.zhiro.jetstep.feature.locomotionclassifier.utils.algorithms

fun <T, K : Number> List<T>.mode(keySelector: (T) -> K) =
    groupingBy(keySelector).eachCount().maxByOrNull(Map.Entry<K, Int>::value)?.key

fun <T, K : Number> List<T>.valueCounts(keySelector: (T) -> K) =
    groupingBy(keySelector).eachCount()
